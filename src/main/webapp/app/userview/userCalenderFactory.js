angular.module('myApp.usercalendar')
        .factory('userCalendarFactory', function ($http) {

            var username = "coordinator"; //Hardcoded for testing
            var userCalendarFactory = {};
            var urlBase = "api/watch/" + username;
            var watchList = [];

            userCalendarFactory.getWatchlist = function () {

                return watchList;
            };

            userCalendarFactory.getShifts = function () {
                return $http.get('api/watch/shifts/' + username);
            };

            userCalendarFactory.getWatch = function (date, userName) {
                return $http.get('api/watch/' + date + '/' + userName);
            };

            userCalendarFactory.addEvent = function (watch) {
                watchList.push(watch);
            };

            userCalendarFactory.setWatchList = function (watch) {
                watchList = watch;

            };

            userCalendarFactory.setBlocked = function (watch, userName) {
                var blockedWatches = [];
                var startHours = watch.startTime.getHours();
                var startMinutes = watch.startTime.getMinutes();
                var endHours = watch.endTime.getHours()
                var endMinutes = watch.endTime.getMinutes();
                watch.start.setHours(startHours);
                watch.start.setMinutes(startMinutes);
                var interval = 1;
                if (watch.radioModel == 'inter') {
                    interval = 1;
                } else {
                    interval = 7;
                }
                watch.end.setDate(watch.end.getDate() + 1);


                while (watch.start <= watch.end) {
                    blockWatch = {};
                    blockWatch.start = new Date(watch.start);
                    blockWatch.start.setHours(startHours);
                    blockWatch.start.setMinutes(startMinutes);
                    blockWatch.end = new Date(watch.start);
                    blockWatch.end.setHours(endHours);
                    blockWatch.end.setMinutes(endMinutes);
                    blockWatch.color = 'red';

                    blockWatch.allDay = false;
                    blockWatch.samarit = {};
                    blockWatch.samarit.userName = userName;
                    blockWatch.title = 'blocked interval';
                    blockedWatches.push(blockWatch);
                    watch.start.setDate(watch.start.getDate() + interval);
                }

                var jsonString = JSON.stringify(blockedWatches);
                return  $http.post("api/watch/interval", jsonString);

            };

            userCalendarFactory.getEvents = function () {

                return $http.get('api/watch/coordinator');
            };

            userCalendarFactory.setAvailable = function (watch) {
                var jsonObject = JSON.stringify(watch);

                return $http.post(urlBase, jsonObject);
            };



            return userCalendarFactory;
        });