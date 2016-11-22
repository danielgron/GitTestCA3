/*
 *  AngularJs Fullcalendar Wrapper for the JQuery FullCalendar
 *  API @ http://arshaw.com/fullcalendar/
 *
 *  Angular Calendar Directive that takes in the [eventSources] nested array object as the ng-model and watches it deeply changes.
 *       Can also take in multiple event urls as a source object(s) and feed the events per view.
 *       The calendar will watch any eventSource array and update itself when a change is made.
 *
 */

angular.module('myApp.usercalendar', ['ngRoute', 'ui.calendar', 'angularMoment'])



        .controller('UserCalendarCtrl', ['$scope', 'moment', '$locale', 'uiCalendarConfig', '$location', 'userCalendarFactory', '$filter',
            function ($scope, moment, $locale, uiCalendarConfig, $location, userCalendarFactory, $filter) {
                //This is where we configure how the calender behaves
                $scope.eventSources1 = [];
                $scope.watchList = [];
                $scope.watchList = userCalendarFactory.getWatchlist();
                $scope.eventSources1 = [$scope.watchList];
                var email = 'coordinator';

//                userCalendarFactory.getEvents().then(function (response) {
//                    
//                    angular.forEach(response.data, function(value,key){
//                        $scope.watchList.push(value);
//                    })
//                   // $scope.watchList = tempWatchList;
//
//                }, function (error) {
//                    $scope.status = 'Unable to load customer data: ' + error.message;
//                });




                $scope.eventSource = {
                    url: 'api/watch/coordinator'
                };

                $scope.uiConfig = {
                    calendar: {
                        contentHeight: 400,
                        editable: false,
                        locale: 'da',
                        aspectRatio: 1,
                        header: {
                            left: 'title',
                            center: '',
                            right: 'today prev,next'
                        },
                        eventClick: $scope.alertOnEventClick,
                        eventDrop: $scope.alertOnDrop,
                        eventResize: $scope.alertOnResize,
                        eventRender: $scope.eventRender,
                        dayRender: $scope.dayRender,
                        dayClick: $scope.setUnavailForWatch,
                        renderEvent: $scope.renderEvent
                    }
                };


                $scope.addWatch = function (watch) {

                };
                $scope.eventRender = function(event,element){
                    if(event.title === 'unavail'){
                        event.rendering = 'background';
                    }
                }


                //This method is for setting a whole day to unavail, by clicking it
                $scope.setUnavailForWatch = function (date, jsEvent, view) {


                    var watch = {};
                    watch.title = "unavail";
                    watch.samarit = {};
                    watch.start = date;
                    
                    watch.samarit.userName = "coordinator";
                    watch.isAvailable = false;
                    watch.allDay = true;
                    watch.rendering = 'background';
                    watch.color = 'red';
                   // watch.stick = true

                    userCalendarFactory.setAvailable(watch).then(function (response) {
                        watch = response.data;
                        window.console.log("great succes" + response.data);
                    }, function (response) {
                        window.console.log("great failure" + response);
                    });

                    watch.stick = true;
                    watch.rendering = 'background';
                    $scope.watchList.push(watch);

                };




                $scope.dayRender = function (date, cell) {

                };
                $scope.renderEvent = function () {

                };

                //For using buttons to redirect
                $scope.go = function (path) {
                    $location.path(path);
                };







                //Change the view between month, week and day
                $scope.changeView = function (view, calendar) {
                    uiCalendarConfig.calendars[calendar].fullCalendar('changeView', view);
                };

                /* event sources array*/
                /* event sources array*/
                $scope.eventSources1 = [$scope.watchList, $scope.eventSource];

                window.console.log("EventScopes");
                window.console.log($scope.watchList);
            }
        ])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/userview', {
                    templateUrl: "app/userview/template/userview.html",
                    controller: "UserCalendarCtrl"
                });
            }]);

