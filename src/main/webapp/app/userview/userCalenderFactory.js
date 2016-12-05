angular.module('myApp.usercalendar')
        .factory('userCalendarFactory', function ($http) {
            
            var username = "coordinator"; //Hardcoded for testing
            var userCalendarFactory = {};
            var urlBase = "api/watch/"+username;
            var watchList = [];
            
            userCalendarFactory.getWatchlist = function(){
            
                return watchList;
            };
            
            userCalendarFactory.getShifts = function(){
                return $http.get('api/watch/shifts/' + username);
            };
            
            userCalendarFactory.getWatch = function(date,userName){
               return $http.get('api/watch/'+date +'/'+userName);
            };
            
            userCalendarFactory.addEvent = function(watch){
                watchList.push(watch);
            };
            
            userCalendarFactory.setWatchList = function(watch){
                watchList = watch;
                
            };

            userCalendarFactory.getEvents = function () {
                
                return $http.get('api/watch/coordinator');
            };
            
            userCalendarFactory.setAvailable = function(watch){
                var  jsonObject = JSON.stringify(watch);

              return $http.post(urlBase,jsonObject);
            };
            
            

            return userCalendarFactory;
        });