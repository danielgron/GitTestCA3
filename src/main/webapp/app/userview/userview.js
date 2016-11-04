angular.module('myApp.event', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/userview', {
                    templateUrl: "app/userview/template/userview.html",
                    controller: "EventCtrl"
                });
            }])
        .controller('EventCtrl', ['$scope', '$locale', 'eventFactory', function ($scope, $locale, eventFactory) {

               
               

            }])
        .factory('eventFactory', function ($http) {
            eventFactory = {};

            eventFactory.saveEvent = function (event) {
                var jsonString = JSON.stringify(event);
                return $http.post('api/event', jsonString);


            };
            return eventFactory;
        });
