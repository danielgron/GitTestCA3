angular.module('myApp.singleevent', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/singleevent', {
                    templateUrl: "app/event/template/singleeventview.html",
                    controller: "SingleEvtCtrl"
                });
            }])
        
         .controller('SingleEvtCtrl', ['$scope', '$locale', 'calendarFactory', function ($scope, $locale, calendarFactory) {
                 var self = this;
                 self.eventid = calendarFactory.getClickedEventid();
                 
                 
            }]);
                
        
