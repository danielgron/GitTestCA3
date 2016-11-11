angular.module('myApp.usercalendar')
        .factory('userCalendarFactory', function ($http) {
            var userCalendarFactory = {};
            var urlBase = "api/watch";

            userCalendarFactory.getEvents = function () {
                return $http.get(urlBase);
            };
            
            userCalendarFactory.setAvailable = function(watch){
                var jsonObject = JSON.stringify(watch);
              return $http.post(urlBase,watch);  
            };

            return userCalendarFactory;
        });