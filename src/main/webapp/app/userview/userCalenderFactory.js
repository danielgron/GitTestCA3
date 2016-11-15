angular.module('myApp.usercalendar')
        .factory('userCalendarFactory', function ($http) {
            
            var username = "coordinator"; //Hardcoded for testing
            var userCalendarFactory = {};
            var urlBase = "api/watch/"+username;

            userCalendarFactory.getEvents = function () {
                return $http.get(urlBase);
            };
            
            userCalendarFactory.setAvailable = function(watch){
                var jsonObject = JSON.stringify(watch);
              return $http.post(urlBase,watch);  
            };

            return userCalendarFactory;
        });