angular.module('myApp.usercalendar')
        .factory('userCalendarFactory', function ($http) {
            
            var username = "coordinator"; //Hardcoded for testing
            var userCalendarFactory = {};
            var urlBase = "api/watch/"+username;
            var watches =[];
            
            userCalendarFactory.getWatches = function(){
                return watches;
            };
            
            userCalendarFactory.addWatch = function(watch){
                watches.push(watch);
            };

            userCalendarFactory.getEvents = function () {
                
                return $http.get('api/event');
            };
            
            userCalendarFactory.setAvailable = function(watch){
                var  jsonObject = JSON.stringify(watch);
                                window.console.log(urlBase);

              return $http.post(urlBase,jsonObject);
            };

            return userCalendarFactory;
        });