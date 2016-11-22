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
                     calendarFactory.getAvaliableSamaritsForEvent(self.eventid)
                             .then(function(response){
                                 self.avaliableSam = response.data;
                                 if(self.avaliableSam.length === 0){
                                     self.avaliableSam.push({userName:"Non Avaliable"});
                                 }
                     }), function(error){
                     console.log("Error" + error);
                     };
                         
                     
                 };
                 
                 
            }]);
                
        
