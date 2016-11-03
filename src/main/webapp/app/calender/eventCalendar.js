/*
 *  AngularJs Fullcalendar Wrapper for the JQuery FullCalendar
 *  API @ http://arshaw.com/fullcalendar/
 *
 *  Angular Calendar Directive that takes in the [eventSources] nested array object as the ng-model and watches it deeply changes.
 *       Can also take in multiple event urls as a source object(s) and feed the events per view.
 *       The calendar will watch any eventSource array and update itself when a change is made.
 *
 */

angular.module('myApp.calendar', ['ngRoute', 'ui.calendar'])

        .constant('uiCalendarConfig', {
            calendars: {}
        })

        .controller('CalendarCtrl', ['$scope', '$locale', 'uiCalendarConfig', 'calendarFactory',
            function ($scope, $locale, uiCalendarConfig, calendarFactory) {
                //This is where we configure how the calender behaves
                var date = new Date();
                var d = date.getDate();
                var m = date.getMonth();
                var y = date.getFullYear();
                $scope.uiCalendarConfig = uiCalendarConfig;
                $scope.eventSources = [$scope.events];

                $scope.eventSource = {
                    url: 'api/event'
                };


                $scope.uiConfig = {
                    calendar: {
                        height: 650,
                        editable: false,
                        locale: 'da',
                        header: {
                            left: 'title',
                            center: '',
                            right: 'today prev,next'
                        },
                        eventClick: $scope.alertOnEventClick,
                        eventDrop: $scope.alertOnDrop,
                        eventResize: $scope.alertOnResize,
                        eventRender: $scope.eventRender,
                        dayClick: $scope.dayClick
                    }
                };
                $scope.dayClick = function (date,jsEvent,view,cell) {
                    window.console.log(view);
                    this.css('background-color', 'red');
                };


                $scope.events = [];
                calendarFactory.getEvents().then(function (response) {
                    $scope.events = response.data;
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
                    events: [
                        {type: 'party', title: 'Lunch', start: new Date(y, m, d, 12, 0), end: new Date(y, m, d, 14, 0), allDay: false},
                        {type: 'party', title: 'Lunch 2', start: new Date(y, m, d, 12, 0), end: new Date(y, m, d, 14, 0), allDay: false},
                        {type: 'party', title: 'Click for Google', start: new Date(y, m, 28), end: new Date(y, m, 29), url: 'http://google.com/'}
                    ]
                };

                //Change the view between month, week and day
                $scope.changeView = function (view, calendar) {
                    uiCalendarConfig.calendars[calendar].fullCalendar('changeView', view);
                };

                $scope.next = function (calendar) {
                    uiCalendarConfig.calendars[calendar].fullCalendar('next');
                };

                $scope.prev = function () {
                    uiCalendarConfig.calendars[calender].fullCalender('prev')
                };


                /* event sources array*/
                $scope.eventSources = [$scope.events, $scope.eventSource, $scope.eventsF];
                $scope.eventSources2 = [$scope.calEventsExt, $scope.eventsF, $scope.events];


            }
        ])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/calender', {
                    templateUrl: "app/calender/calendertemplate.html",
                    controller: "CalendarCtrl"
                });
            }]);

