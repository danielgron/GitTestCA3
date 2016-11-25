angular.module('myApp.usercalendar')
        .controller('AddTimeDayController', function ($uibModalInstance,$scope) {

            //Bindable models
            var $scope = this;
            $scope.watch = {};
            $scope.watch.start = new Date();
            $scope.watch.end = new Date();
            $scope.watch.start.setHours(16);
            $scope.watch.start.setMinutes(0);
            $scope.watch.end.setHours(22);
            $scope.watch.end.setMinutes(0);
            //For timepicker
            $scope.hstep = 1;
            $scope.mstep = 5;


            $scope.popup1 = {
                opened: false
            };

            $scope.popup2 = {
                opened: false
            };


            $scope.open1 = function () {
                $scope.popup1.opened = true;
            };

            $scope.setDate = function (year, month, day) {
               
              
                
                tempDate1 = $scope.watch.end;
                $scope.watch.end = $scope.watch.start;
                $scope.watch.end.setHours(tempDate1.getHours());
                $scope.watch.end.setMinutes(tempDate1.getMinutes());

            };
            $scope.inlineOptions = {
                customClass: getDayClass,
                minDate: new Date(),
                showWeeks: true
            };

            $scope.dateOptions = {
                formatYear: 'yy',
                maxDate: new Date(2020, 5, 22),
                minDate: new Date(),
                startingDay: 1
            };

            function getDayClass(data) {
                var date = data.date,
                        mode = data.mode;
                if (mode === 'day') {
                    var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

                    for (var i = 0; i < $scope.watch.length; i++) {
                        var currentDay = new Date($scope.watch[i].date).setHours(0, 0, 0, 0);

                        if (dayToCheck === currentDay) {
                            return $scope.watch[i].status;
                        }
                    }
                }
                return '';
            }

            $scope.ok = function () {
                $uibModalInstance.close($scope.watch);
            };


        });



angular.module('myApp.usercalendar').config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/addtimeofday', {
            templateUrl: "app/userview/template/addspecifictime.html",
            controller: "AddTimeDayController"
        });
    }]);