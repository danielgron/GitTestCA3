/*
 *  AngularJs Fullcalendar Wrapper for the JQuery FullCalendar
 *  API @ http://arshaw.com/fullcalendar/
 *
 *  Angular Calendar Directive that takes in the [eventSources] nested array object as the ng-model and watches it deeply changes.
 *       Can also take in multiple event urls as a source object(s) and feed the events per view.
 *       The calendar will watch any eventSource array and update itself when a change is made.
 *
 */

angular.module('myApp.usercalendar', ['ngRoute', 'ui.usercalendar'])

        .constant('uiUserCalendarConfig', {
            calendars: {}
        })

        .controller('UserCalendarCtrl', ['$scope', '$locale', 'uiUserCalendarConfig', '$location', 'userCalendarFactory',
            function ($scope, $locale, uiUserCalendarConfig, $location, userCalendarFactory) {
                //This is where we configure how the calender behaves

                var date = new Date();
                var d = date.getDate();
                var m = date.getMonth();
                var y = date.getFullYear();
                $scope.avail = true;


                $scope.header = 'test';


                var email = 'coorinator';
                $scope.eventSource = {
                     url: 'api/watch/' + email
                };
                
                
                $scope.test = function () {
                    
                    //Using the calenderobject to store variable across scopes
                    uiUserCalendarConfig.calendars['userCalender'].avail = !uiUserCalendarConfig.calendars['userCalender'].avail;
                    //And setting localscope 
                    $scope.avail = uiUserCalendarConfig.calendars['userCalender'].avail;
                    window.console.log(uiUserCalendarConfig.calendars['userCalender'].avail);


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
                        dayRender: $scope.dayRender,
                        dayClick: $scope.dayClick,
                        renderEvent: $scope.renderEvent
                    }
                };

                $scope.dayRender = function (date,cell){
                    
                };
                
                $scope.renderEvent = function(){
                    
                };
                
                $scope.dayClick = function (date, jsEvent, view)
                {
                    if(uiUserCalendarConfig.calendars['userCalender'].avail){
                    uiUserCalendarConfig.calendars['userCalender'].fullCalendar('gotoDate', date);
                    uiUserCalendarConfig.calendars['userCalender'].fullCalendar('changeView', 'agendaDay');
                    } else {
                        var watch = {};
                        watch.title = "";
                        watch.samarit = {};
                        watch.start = date;
                        watch.samarit.email = "coordinator";
                        watch.isAvailable = false;
                        
                        userCalendarFactory.setAvailable(watch);
                        $scope.renderEvent(watch);
                      
                       
                    }
                };

                //For using buttons to redirect
                $scope.go = function (path) {
                    $location.path(path);
                };


                $scope.watches = [];

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
                    window.console.log(calendar);

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

