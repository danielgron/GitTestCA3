angular.module('myApp.event', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/addevent', {
                    templateUrl: "app/calender/template/addevent.html",
                    controller: "AddCtrl"
                });
            }])
        .controller('AddCtrl', ['$scope', '$locale', 'eventFactory', function ($scope, $locale, eventFactory) {
                $scope.today = function () {
                    $scope.dt = new Date();
                };
                $scope.today();

                $scope.popup1 = {
                    opened: false
                };

                $scope.popup2 = {
                    opened: false
                };

                $scope.open1 = function () {
                    $scope.popup1.opened = true;
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

                $scope.setDate = function (year, month, day) {
                    $scope.dt = new Date(year, month, day);
                };

                function getDayClass(data) {
                    var date = data.date,
                            mode = data.mode;
                    if (mode === 'day') {
                        var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

                        for (var i = 0; i < $scope.events.length; i++) {
                            var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                            if (dayToCheck === currentDay) {
                                return $scope.events[i].status;
                            }
                        }
                    }

                    return '';
                }

                //For timepicker
                $scope.mytimestart = new Date();
                $scope.mytimeend = new Date();
                $scope.hstep = 1;
                $scope.mstep = 5;
                $scope.mytimestart.setHours(16);
                $scope.mytimestart.setMinutes(0);
                $scope.mytimeend.setHours(22);
                $scope.mytimeend.setMinutes(0);

                $scope.customers = [];
                $scope.customers.push({name: 'København', id: '1'});
                $scope.customers.push({name: '2København', id: '2'});
                $scope.customers.push({name: '3København', id: '3'});

                $scope.save = eventFactory;
            }])
        .factory('eventFactory', function ($http) {
            eventFactory = {};
            
            eventFactory.saveEvent = function(event){
                
            }
            return eventFactory;
        });
