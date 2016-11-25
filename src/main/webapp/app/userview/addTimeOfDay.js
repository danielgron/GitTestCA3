angular.module('myApp.usercalendar')
        .controller('AddTimeDayController', function ($uibModalInstance) {

            //Bindable models
            var $ctrl = this;
            $ctrl.watch = {};
            $ctrl.watch.start = new Date();
            $ctrl.watch.end = new Date();
            $ctrl.watch.start.setHours(16);
            $ctrl.watch.start.setMinutes(0);
            $ctrl.watch.end.setHours(22);
            $ctrl.watch.end.setMinutes(0);
            //For timepicker
            $ctrl.hstep = 1;
            $ctrl.mstep = 5;
            $ctrl.valid = ($ctrl.watch.start < $ctrl.watch.end);


            $ctrl.popup1 = {
                opened: false
            };

            $ctrl.popup2 = {
                opened: false
            };


            $ctrl.open1 = function () {
                $ctrl.popup1.opened = true;
            };

            $ctrl.setDate = function (year, month, day) {
                var tempDate = new Date();
                tempDate = $ctrl.watch.start;
                $ctrl.watch.watch = new Date(year, month, day);
                $ctrl.watch.start.setHours(tempDate.getHours());
                $ctrl.watch.start.setMinutes(tempDate.getMinutes());
                tempDate = $ctrl.watch.end;
                $ctrl.watch.end = $ctrl.date;
                $ctrl.watch.end.setHours(tempDate.getHours());
                $ctrl.watch.end.setMinutes(tempDate.getMinutes());

            };
            $ctrl.inlineOptions = {
                customClass: getDayClass,
                minDate: new Date(),
                showWeeks: true
            };

            $ctrl.dateOptions = {
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

                    for (var i = 0; i < $ctrl.watch.length; i++) {
                        var currentDay = new Date($ctrl.watch[i].date).setHours(0, 0, 0, 0);

                        if (dayToCheck === currentDay) {
                            return $ctrl.watch[i].status;
                        }
                    }
                }
                return '';
            }

            $ctrl.ok = function () {
                $uibModalInstance.close($ctrl.watch);
            };


        });



angular.module('myApp.usercalendar').config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/addtimeofday', {
            templateUrl: "app/userview/template/addspecifictime.html",
            controller: "AddTimeDayController"
        });
    }]);