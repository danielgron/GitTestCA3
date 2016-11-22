angular.module('myApp.singleevent', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/singleevent', {
                    templateUrl: "app/event/template/singleeventview.html"
                    
                });
            }])
        
         .controller('SingleEvtCtrl', ['$scope', '$locale', 'calendarFactory', function ($scope, $locale, calendarFactory) {
                 var self = this;
                 self.eventid = calendarFactory.getClickedEventid();
                 self.event;
                 getClickedEvent();
                 self.avaliableSam = [];
                 getAvalibleSam();
                 
                 function getClickedEvent(){
                     calendarFactory.getSingleEvent(self.eventid)
                             .then(function (response){
                                 self.event = response.data;
                     }), function (error){
                         console.log("Error" + error);
                     };
                 }
                 
                 function getAvalibleSam(){
                     var sam1 = {name:"Hans", redcrosslevel : "Teamleder"};
                     var sam2 = {name:"Grete", redcrosslevel : "Samarit"};
                   self.avaliableSam.push(sam1);
                   self.avaliableSam.push(sam2);
                 };
                 
                 
            }]);
                
        
