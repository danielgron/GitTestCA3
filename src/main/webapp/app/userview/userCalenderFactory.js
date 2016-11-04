angular.module('myApp.usercalendar')
        .factory('userCalendarFactory', function ($http) {
            var userCalendarFactory = {};
            var urlBase = "api/event";

            userCalendarFactory.getEvents = function () {
                return $http.get(urlBase);
            };
            userCalendarFactory.setAvailable = function(date){
                
            };

            return userCalendarFactory;
        });