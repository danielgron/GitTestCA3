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

//             




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
                        eventAfterAllRender: $scope.eventAfterRender
                    }
                };


                $scope.addWatch = function (watch) {

                };

                $scope.eventRender = function (event, element) {
//                    // event.start is already a moment.js object
                    // we can apply .format()
                    var dateString = event.start.format("YYYY-MM-DD");
                    window.console.log(dateString);
                    
                    result = angular.element(document).find('.fc-day[data-date="' + dateString + '"]').css('background-color', 'red');//element.find('.fc-day[data-date="' + dateString + '"]');
                    //result.css('background-color', 'black');
                            //document.getElementsByClassName('.fc-day[data-date=' + dateString + ']');
                    window.console.log(result);

                    //$(view.el[0]).find('.fc-day[data-date=' + dateString + ']').css('background-color', '#FAA732');
                };
                eventAfterRender: $scope.eventAfterRender = function () {
                    $scope.rerenderEvents();
                };
                $scope.rerenderEvents = function () {

                };

                //This method is for setting a whole day to unavail, by clicking it
                //TO-DO - remove when clicking day with event.
                $scope.setUnavailForWatch = function (date, jsEvent, view) {
                    window.console.log();
                    this.css('background-color', 'red');
                    var watch = {};

                    watch.title = "unavail";
                    watch.samarit = {};
                    watch.start = date;

                    watch.samarit.userName = "coordinator";
                    watch.allDay = true;
                    // watch.rendering = 'background';
                    watch.color = 'red';
                    // watch.stick = true

                    userCalendarFactory.setAvailable(watch).then(function (response) {
                        watch = response.data;
                        window.console.log("great succes" + response.data);
                    }, function (response) {
                        window.console.log("great failure" + response);
                    });

                    watch.stick = true;
                    //  watch.rendering = 'background';
                    $scope.watchList.push(watch);
                    $scope.rerenderEvents();

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

