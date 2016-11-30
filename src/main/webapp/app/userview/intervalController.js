angular.module('myApp.usercalendar')
        .controller('AddTimeDayController', function ($uibModalInstance) {
            var vm = this;
            //Bindable models
            var vm = this;
            vm.watch = {};
            vm.watch.start = new Date();
            vm.watch.end = new Date();
            vm.watch.start.setHours(16);
            vm.watch.start.setMinutes(0);
            vm.watch.end.setHours(22);
            vm.watch.end.setMinutes(0);
            //For timepicker
            vm.hstep = 1;
            vm.mstep = 5;


            vm.popup1 = {
                opened: false
            };

            vm.popup2 = {
                opened: false
            };


            vm.open1 = function () {
                vm.popup1.opened = true;
            };

            vm.setDate = function (year, month, day) {
               
              
                
                tempDate1 = vm.watch.end;
                vm.watch.end = vm.watch.start;
                vm.watch.end.setHours(tempDate1.getHours());
                vm.watch.end.setMinutes(tempDate1.getMinutes());

            };
            vm.inlineOptions = {
                customClass: getDayClass,
                minDate: new Date(),
                showWeeks: true
            };

            vm.dateOptions = {
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

                    for (var i = 0; i < vm.watch.length; i++) {
                        var currentDay = new Date(vm.watch[i].date).setHours(0, 0, 0, 0);

                        if (dayToCheck === currentDay) {
                            return vm.watch[i].status;
                        }
                    }
                }
                return '';
            }

            vm.ok = function () {
                $uibModalInstance.close(vm.watch);
            };


        });



angular.module('myApp.usercalendar').config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/addtimeofday', {
            templateUrl: "app/userview/template/addspecifictime.html",
            controller: "AddTimeDayController"
        });
    }]);