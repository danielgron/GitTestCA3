/*
 *  AngularJs Fullcalendar Wrapper for the JQuery FullCalendar
 *  API @ http://arshaw.com/fullcalendar/
 *
 *  Angular Calendar Directive that takes in the [eventSources] nested array object as the ng-model and watches it deeply changes.
 *       Can also take in multiple event urls as a source object(s) and feed the events per view.
 *       The calendar will watch any eventSource array and update itself when a change is made.
 *
 */

angular.module('myApp.usercalendar', ['ngRoute', 'ui.usercalendar', 'angularMoment'])

        .constant('uiUserCalendarConfig', {
            calendars: {}
        })

        .controller('UserCalendarCtrl', ['$scope', 'moment', '$locale', 'uiUserCalendarConfig', '$location', 'userCalendarFactory', '$filter',
            function ($scope, moment, $locale, uiUserCalendarConfig, $location, userCalendarFactory, $filter) {
                //This is where we configure how the calender behaves

                var date = new Date();
                var d = date.getDate();
                var m = date.getMonth();
                var y = date.getFullYear();
                $scope.watches = userCalendarFactory.getWatches;
                $scope.eventSources = [$scope.watches];

                $scope.eventList = {};



                $scope.addWatch = function (watch) {

                   userCalendarFactory.addWatch(watch);
                };

                $scope.calendarDate = [
                    {
                        events: [
                            {
                                title: 'From',
                                start: '2015-01-31',
                                allDay: true,
                                rendering: 'background',
                                backgroundColor: '#f26522'
                            }
                        ]
                    }
                ];

                $scope.setCalDate = function (date, jsEvent, view) {
                    var selectedDate = moment(date).format('YYYY-MM-DD');				    // set dateFrom based on user click on calendar
                   // $scope.calendarDate[0].events[0].start = selectedDate;				    // update Calendar event dateFrom
                   // $scope.selectedDate = $filter('date')(selectedDate, 'yyyy-MM-dd');
                   // $scope.watches.push($scope.calendarDate);
                    window.console.log($scope.watches);

                    var watch = {};
                    watch.title;
                    watch.samarit = {};
                    watch.start = date;
                    watch.end = date;
                    watch.samarit.userName = "coordinator";
                    watch.isAvailable = false;
                    watch.allDay = true;
                    userCalendarFactory.setAvailable(watch).then(function (response) {
                        window.console.log("great succes" + response.data);
                    }, function (response) {
                        window.console.log("great failure" + response);
                    });

                    $scope.addWatch(watch);


                };

                $scope.header = 'test';
                var email = 'coordinator';

                $scope.eventSource = {
                    url: 'api/watch/' + email
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
                        dayClick: $scope.setCalDate,
                        renderEvent: $scope.renderEvent
                    }
                };
                $scope.dayRender = function (date, cell) {

                };
                $scope.renderEvent = function () {

                };



//                $scope.dayClick = function (date, jsEvent, view)
//                {
//                    if (uiUserCalendarConfig.calendars['userCalender'].avail) {
//                        uiUserCalendarConfig.calendars['userCalender'].fullCalendar('gotoDate', date);
//                        uiUserCalendarConfig.calendars['userCalender'].fullCalendar('changeView', 'agendaDay');
//                    } else {
//                        var watch = {};
//                watch.title = "";
//                watch.samarit = {};
//                watch.start = date;
//                watch.samarit.email = "coordinator";
//                watch.isAvailable = false;
//                userCalendarFactory.setAvailable(watch);
//
//                $scope.addWatch(watch);
//                    }
//                };
                //For using buttons to redirect
                $scope.go = function (path) {
                    $location.path(path);
                };
                userCalendarFactory.getEvents().then(function (response) {
                    $scope.watches = response.data;
                }, function (error) {
                    $scope.status = 'Unable to load customer data: ' + error.message;
                });
                /* event source that calls a function on every view switch */
                $scope.eventsF = function (start, end, timezone, callback) {
                    var s = new Date(start).getTime() / 1000;
                    var e = new Date(end).getTime() / 1000;
                    var m = new Date(start).getMonth();
                    var events = [{title: 'Feed Me ' + m, start: s + (50000), end: s + (100000), allDay: false, className: ['customFeed']}];
                    callback(events);
                };
                $scope.calEventsExt = {
                    color: '#f00',
                    textColor: 'yellow',
                    events: []
                };
                //Change the view between month, week and day
                $scope.changeView = function (view, calendar) {
                    uiUserCalendarConfig.calendars[calendar].fullCalendar('changeView', view);
                };
                /* event sources array*/
                $scope.eventSources = [$scope.watches, $scope.eventSource, $scope.eventsF];
            }
        ])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/userview', {
                    templateUrl: "app/userview/template/userview.html",
                    controller: "UserCalendarCtrl"
                });
            }]);

