angular.module('myApp.event', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/addevent', {
                    templateUrl: "app/calender/template/addevent.html",
                    controller: "EventCtrl"
                });
            }])
        .controller('EventCtrl', ['$scope', '$locale', 'eventFactory', function ($scope, $locale, eventFactory) {

                $scope.event = {};
                $scope.event.date = new Date();
                $scope.event.start = new Date();
                $scope.event.end = new Date();
                 $scope.event.date.setHours(16);
                $scope.event.date.setMinutes(0);

                $scope.today = function () {
                    $scope.event.date = new Date();
                    $scope.event.start = $scope.event.date;
                    $scope.event.end = $scope.event.date;
                };

                $scope.saveEvent = function(){
                    eventFactory.saveEvent($scope.event)
                    .then(function(){
                        alert('succes');
                    }, function(){
                        alert('crash and burn!');
                    });
                };
                        
                $scope.reset = function () {
                    $scope.event = {};
                    $scope.event = {};
                    $scope.event.date = new Date();
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
                    $scope.event.date = new Date(year, month, day);
                    $scope.event.date.setHours($scope.event.start.getHours());
                    $scope.event.date.setMinutes()($scope.event.start.getMinutes());
                    $scope.event.start = $scope.event.date;
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
                $scope.event.start.setHours(16);
                $scope.event.start.setMinutes(0);
                $scope.event.end.setHours(22);
                $scope.event.end.setMinutes(0);

                $scope.customers = [];
                $scope.customers.push({name: 'København', id: '1'});
                $scope.customers.push({name: '2København', id: '2'});
                $scope.customers.push({name: '3København', id: '3'});

                $scope.save = eventFactory;
            }])
        .factory('eventFactory', function ($http) {
            eventFactory = {};

            eventFactory.saveEvent = function (event) {
            var jsonString = JSON.stringify(event);
                return $http.post('api/event', jsonString);

                
            };
            return eventFactory;
        });
