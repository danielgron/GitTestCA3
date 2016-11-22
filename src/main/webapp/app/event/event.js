angular.module('myApp.event', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/addevent', {
                    templateUrl: "app/event/template/addevent.html",
                    controller: "EventCtrl"
                });
            }])
        .controller('EventCtrl', ['$scope', '$locale', 'eventFactory', 'UserFactory', function ($scope, $locale, eventFactory, UserFactory) {

                $scope.event = {};
                $scope.event.start = new Date();
                $scope.event.end = new Date();
                $scope.event.start.setHours(16);
                $scope.event.start.setMinutes(0);
                $scope.event.end.setHours(22);
                $scope.event.end.setMinutes(0);
                $scope.event.department ={};
                $scope.event.department.nameOfDepartment = UserFactory.getDepartment();

                $scope.today = function () {
                    $scope.event.start = new Date();
                    $scope.event.end = $scope.event.start;
                };

                $scope.saveEvent = function () {
                    eventFactory.saveEvent($scope.event)
                            .then(function () {
                                alert('succes');
                            }, function () {
                                alert('crash and burn!');
                            });
                };

                $scope.reset = function () {
                    $scope.event = {};
                    $scope.event = {};
                    $scope.event.start = new Date();
                    $scope.event.end = new Date();
                    $scope.event.start.setHours(16);
                    $scope.event.start.setMinutes(0);
                    $scope.event.end.setHours(22);
                    $scope.event.end.setMinutes(0);
                }

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
                    var tempDate = new Date();
                    tempDate = $scope.event.start;
                    $scope.event.start = new Date(year, month, day);
                    $scope.event.start.setHours(tempDate.getHours());
                    $scope.event.start.setMinutes(tempDate.getMinutes());
                    tempDate = $scope.event.end;
                    $scope.event.end = $scope.date;
                    $scope.event.end.setHours(tempDate.getHours());
                    $scope.event.end.setMinutes(tempDate.getMinutes());

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
                $scope.hstep = 1;
                $scope.mstep = 5;



                $scope.customers = [];
                $scope.customers.push({name: 'København', id: '1'});
                $scope.customers.push({name: '2København', id: '2'});
                $scope.customers.push({name: '3København', id: '3'});

            }])
        .factory('eventFactory', function ($http) {
            eventFactory = {};

            eventFactory.saveEvent = function (event) {
                var jsonString = JSON.stringify(event);
                return $http.post('api/event', jsonString);


            };
            return eventFactory;
        });
