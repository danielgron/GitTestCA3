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


                $scope.events = [
                    {title: 'All Day Event', start: new Date(y, m, 1)},
                    {title: 'Long Event', start: new Date(y, m, d - 5), end: new Date(y, m, d - 2)},
                    {id: 999, title: 'Repeating Event', start: new Date(y, m, d - 3, 16, 0), allDay: false},
                    {id: 999, title: 'Repeating Event', start: new Date(y, m, d + 4, 16, 0), allDay: false},
                    {title: 'Birthday Party', start: new Date(y, m, d + 1, 19, 0), end: new Date(y, m, d + 1, 22, 30), allDay: false},
                    {title: 'Click for Google', start: new Date(y, m, 28), end: new Date(y, m, 29), url: 'http://google.com/'}
                ];
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

angular.module('myApp.calendar').factory('calendarFactory', function ($http) {
    var calendarFactory = {};
    var urlBase = "api/events";

    calendarFactory.getEvents = function () {
        return $http.get(urlBase);
    };

    return calendarFactory;
});