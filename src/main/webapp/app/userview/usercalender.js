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



        .controller('UserCalendarCtrl', ['$scope', 'moment', '$locale', 'uiCalendarConfig', '$location', 'userCalendarFactory', '$filter', 'UserFactory',
            function ($scope, moment, $locale, uiCalendarConfig, $location, userCalendarFactory, $filter, UserFactory) {
                //This is where we configure how the calender behaves
                $scope.eventSources1 = [];
                $scope.watchList = [];
                $scope.watchList = userCalendarFactory.getWatchlist();
                $scope.eventSources1 = [$scope.watchList];
                var email = 'coordinator';
                $scope.user = {};
                $scope.user = UserFactory.getCreatedUser();

                $scope.eventSource = {
                    url: 'api/watch/coordinator',
                    color: 'red'
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
                        renderEvent: $scope.renderEvent,
                        selectOverlap: $scope.selectOverlap,
                        rerenderEvents: $scope.rerenderEvents,
                        eventAfterAllRender: $scope.eventAfterRender,
                        eventDestroy: $scope.eventDestroy
                    }
                };


                $scope.eventDestroy = function (event) {
                    var dateString = event.start.format("YYYY-MM-DD");

                    angular.element(document).find('.fc-day[data-date="' + dateString + '"]').css('background-color', '');
                }

                $scope.eventRender = function (event, element) {
                    var dateString = event.start.format("YYYY-MM-DD");

                    angular.element(document).find('.fc-day[data-date="' + dateString + '"]').css('background-color', 'red');
                };


                $scope.removeEvent = function (watch) {
                    var i;
                    uiCalendarConfig.calendars['userCalender'].fullCalendar('removeEvents', watch.id);
                    if ($scope.watchList.length > 0) {
                        $scope.watchList.forEach(function(item, index){
                            if(item.id==watch.id){
                                $scope.watchList.splice(index,1);                            }
                            
                        });
                    }
                    

                };

                //This method is for setting a whole day to unavail, by clicking it
                //TO-DO - remove when clicking day with event.
                $scope.setUnavailForWatch = function (date, jsEvent, view) {
                    var dateString = date.format("YYYY-MM-DD");
                    var watch = {};
                    userCalendarFactory.getWatch(dateString, UserFactory.getUserName()).then(function (successResponse) {
                        watch = successResponse.data;
                        window.console.log(watch);
                        if (watch === null) {
                            setWatch(date);
                        } else {
                            window.console.log(watch.id);
                            $scope.removeEvent(watch);
                        }
                    }, function (errorResponse) {

                    });

                    var setWatch = function (date) {
                        var watch = {};
                        watch.title = "unavail";
                        watch.samarit = {};
                        watch.start = date;

                        watch.samarit.userName = "coordinator";
                        watch.allDay = true;
                        watch.color = 'red';

                        userCalendarFactory.setAvailable(watch).then(function (response) {
                            watch = response.data;
                            window.console.log(watch);
                            window.console.log("great succes" + response.data);
                            $scope.watchList.push(watch);
                        }, function (response) {
                        });


                    };


                };

                $scope.dayRender = function (date, cell) {

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
                $scope.eventSources1 = [$scope.watchList, $scope.eventSource];


            }
        ])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/userview', {
                    templateUrl: "app/userview/template/userview.html",
                    controller: "UserCalendarCtrl"
                });
            }]);

Array.prototype.remove = function (v) {
    if (this.indexOf(v) != -1) {
        this.splice(this.indexOf(v), 1);
        return true;
    }
    return false;
};