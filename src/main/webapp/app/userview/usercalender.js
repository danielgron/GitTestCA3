/*
 *  AngularJs Fullcalendar Wrapper for the JQuery FullCalendar
 *  API @ http://arshaw.com/fullcalendar/
 *
 *  Angular Calendar Directive that takes in the [eventSources] nested array object as the ng-model and watches it deeply changes.
 *       Can also take in multiple event urls as a source object(s) and feed the events per view.
 *       The calendar will watch any eventSource array and update itself when a change is made.
 *
 */

angular.module('myApp.usercalendar', ['ngRoute', 'ui.calendar', 'angularMoment', 'bsLoadingOverlay'])



        .controller('UserCalendarCtrl', function ($scope, moment, $locale, uiCalendarConfig, $location, userCalendarFactory, $filter, UserFactory, bsLoadingOverlayService, $uibModal) {

            //****Bindable members****//
            $scope.eventSources1 = [];
            $scope.watchList = [];
            $scope.watchList = userCalendarFactory.getWatchlist();

            $scope.user = {};
            $scope.user = UserFactory.getCreatedUser();


            //****Event models and sources****//
            //Declaring main eventsource
            $scope.eventSources1 = [$scope.watchList];
            $scope.eventSource = {
                url: 'api/watch/' + $scope.user.userName,
                color: 'red'
            };

            /* event sources array*/
            $scope.eventSources1 = [$scope.watchList, $scope.eventSource];

            $scope.go = function (path) {
                $location.path(path);
            };

            //****The calender config****//
            $scope.uiConfig = {
                calendar: {
                    contentHeight: 400,
                    editable: false,
                    locale: 'da',
                    aspectRatio: 1,
                    timezone: 'local',
                    header: {
                        left: 'title',
                        center: '',
                        right: 'today prev,next'
                    },
                    eventDrop: $scope.alertOnDrop,
                    eventResize: $scope.alertOnResize,
                    eventRender: $scope.eventRender,
                    dayRender: $scope.dayRender,
                    dayClick: $scope.setUnavailForWatch,
                    renderEvent: $scope.renderEvent,
                    selectOverlap: $scope.selectOverlap,
                    rerenderEvents: $scope.rerenderEvents,
                    eventAfterAllRender: $scope.eventAfterAllRender,
                    eventDestroy: $scope.eventDestroy,
                    viewRender: $scope.viewRender,
                    eventClick: $scope.eventClick

                }
            };

            //****Full calender callbacks, declared in uiConfig****//
            $scope.eventClick = function (event) {
                if (event.title == "unavail" && event.allDay) {

                    $scope.setUnavailForWatch(event.start);
                } else if (!event.allDay) {

                }
                ;

            };


            $scope.eventAfterAllRender = function () {
                bsLoadingOverlayService.stop();

            };

            $scope.viewRender = function (view, element) {
                bsLoadingOverlayService.start();

            };

            $scope.eventDestroy = function (event) {
                var dateString = event.start.format("YYYY-MM-DD");

                angular.element(document).find('.fc-day[data-date="' + dateString + '"]').css('background-color', '');
            };

            $scope.eventRender = function (event, element) {
                var dateString = event.start.format("YYYY-MM-DD");
                if (event.allDay) {
                    angular.element(document).find('.fc-day[data-date="' + dateString + '"]').css('background-color', event.color);
                }

            };

            $scope.removeEvent = function (watch) {
                var i;
                uiCalendarConfig.calendars['userCalender'].fullCalendar('removeEvents', watch.id);
                if ($scope.watchList.length > 0) {
                    $scope.watchList.forEach(function (item, index) {
                        if (item.id == watch.id) {
                            $scope.watchList.splice(index, 1);
                        }
                    });
                }
            };

            //This method is for setting a whole day to unavail, by clicking it
            //TO-DO refactor into more methods, and move logic to factory
            $scope.setUnavailForWatch = function (date, jsEvent, view) {
                var dateString = date.format("YYYY-MM-DD");
                var watch = {};
                if (moment().format('YYYY-MM-DD') === date.format('YYYY-MM-DD') || date.isAfter(moment())) {
                    // This allows today and future date
                } else {
                   return;
                }
                //Start spinner before restcall
                bsLoadingOverlayService.start();

                userCalendarFactory.getWatch(dateString, UserFactory.getUserName()).then(function (successResponse) {

                    watch = successResponse.data;
                    if (watch === null) {
                        setWatch(date);
                    } else {
                        $scope.removeEvent(watch);
                        bsLoadingOverlayService.stop();
                    }
                }, function (errorResponse) {
                    bsLoadingOverlayService.stop();

                });

                var setWatch = function (date) {
                    var watch = {};
                    watch.title = "unavail";
                    watch.samarit = {};
                    watch.start = date;
                    window.console.log(watch.start);

//                    watch.start.setHours(00);
//                    watch.start.setMinutes(0);
                    watch.samarit.userName = $scope.user.userName;
                    watch.allDay = true;
                    watch.color = 'red';
                    userCalendarFactory.setAvailable(watch).then(function (response) {
                        watch = response.data;
                        window.console.log(watch);
                        window.console.log("great succes" + response.data);
                        $scope.watchList.push(watch);
                        bsLoadingOverlayService.stop();
                    }, function (response) {
                        bsLoadingOverlayService.stop();
                    });
                };
            };

            $scope.dayRender = function (date, cell) {
                cell.attr('bs-loading-overlay', 'true');
            };

            $scope.setOccupiedDetail = function (date) {
                window.console.log(date);
                var startHours = date.startTime.getHours();
                var startMinutes = date.startTime.getMinutes();
                var endHours = date.endTime.getHours()
                var endMinutes = date.endTime.getMinutes();
                
                
                var watch = {};

                //Start spinner before restcall
                bsLoadingOverlayService.start();
                //Configure occupied object
                watch.title = "Blocked";
                watch.samarit = {};
                watch.start = date.start;
                watch.start.setHours(startHours);
                watch.start.setMinutes(startMinutes);
                watch.end = new Date(date.start);
                watch.end.setHours(endHours);
                watch.end.setMinutes(endMinutes);
                watch.samarit.userName = $scope.user.userName;
                watch.allDay = false;
                watch.color = 'red';
                
                window.console.log("Date set " + watch.start);
                window.console.log("Date set " + watch.end);
                userCalendarFactory.setAvailable(watch).then(function (response) {
                    watch = response.data;
                    $scope.watchList.push(watch);
                    bsLoadingOverlayService.stop();
                }, function (response) {
                    bsLoadingOverlayService.stop();
                });
            };


            //Change the view between month, week and day
            $scope.changeView = function (view, calendar) {
                uiCalendarConfig.calendars[calendar].fullCalendar('changeView', view);
            };

            $scope.open = function (size, parentSelector) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    ariaLabelledBy: 'modal-title',
                    ariaDescribedBy: 'modal-body',
                    templateUrl: 'app/userview/template/addspecifictime.html',
                    controller: 'AddTimeDayController',
                    size: 'md',
                    controllerAs: 'atCtrl'

                });

                modalInstance.result.then(function (selectedItem) {
                    $scope.setOccupiedDetail(selectedItem);
                }, function () {

                });
            };

        }
        );

//RouteProvider
angular.module('myApp.usercalendar').config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/userview', {
            templateUrl: "app/userview/template/userview.html",
            controller: "UserCalendarCtrl"
        });
    }]);



//***Custom functions and prototypes***//
//Spinner setup
angular.module('myApp.usercalendar').run(function (bsLoadingOverlayService) {
    bsLoadingOverlayService.setGlobalConfig({
        templateUrl: 'app/templates/loading-overlay-template.html'
    });
});



//Prototype remove from array
Array.prototype.remove = function (v) {
    if (this.indexOf(v) != -1) {
        this.splice(this.indexOf(v), 1);
        return true;
    }
    return false;
};
