/*
 * This is the controller for assignquatity.html
 */

angular.module('myApp.watchflow')
        .controller('quantityController', quantityController);

quantityController.$inject = ['newWatchCardFactory', '$location', '$routeParams'];



function quantityController(newWatchCardFactory, $location, $routeParams){
    
    //**Bindable Variables****
   var self = this;
   self.clickedShift = {};
   self.allRedCrossLevels =[];
   self.avalibleResources = [];
   self.eventId = $routeParams.param;
   
   
   ///***Function Calls****
   self.getAllRedCrossLevels = getAllRedCrossLevels;
   self.getclicked = getclicked;
   self.getAvalibleResources = getAvalibleResources;
   self.moveResource = moveResource;
   self.moveResourceBack = moveResourceBack;
   self.saveChanges = saveChanges;
   
   //** Exceute on Enter *****
    getclicked(self.eventId);
    getAvalibleResources();
   
   
   //*** Functions*****
   function getclicked(id) {
        newWatchCardFactory.getEventfromId(id)
                .then(
                        function successCallback(res) {
                            self.clickedShift = res.data;
                        }, function errorCallBack(errorResponse) {
                    console.log("Error in callback: " + errorResponse.data.error.code);
                });
    }
   
   function getAllRedCrossLevels(){
       newWatchCardFactory.getAllRedCrossLevelsFromFac()
       .then(
           function successCallback(res) {
                    self.allRedCrossLevels = res.data;
        }, function errorCallBack(error){
           console.log("Error in callback: " + error.code); 
        });
   }
   
   
   
   function getAvalibleResources(){
       newWatchCardFactory.getAvalibleResources(self.eventId)
       .then(
           function successCallback(res) {
                    self.avalibleResources = res.data;
        }, function errorCallBack(error){
           console.log("Error in callback: " + error.code); 
        });
   }
     
   function moveResource(){
     var selected = self.selected; // -- Variable that is created by selecting
     if(selected != null){
         
     self.avalibleResources.remove(selected);
     self.clickedShift.resources.push(selected);
     }
   };
   
   function moveResourceBack(resource){
     self.avalibleResources.push(resource);
     self.clickedShift.resources.remove(resource);
   };
   
   /*
    * Saves the Now editted 
    * Object to the database!
    */
   function saveChanges(){
       // Start the spinner!!!
       newWatchCardFactory.sendDataFromQuantityWatch(self.clickedShift)
       .then(
           function successCallback(res) {
               console.log("Succes - should stop spinner!");
               $location.path("/watchflow");
        }, function errorCallBack(error){
           console.log("Error in callback: " + error.data.error.code); 
           console.log("Stop Spinner!");
        });
   }
   
}
