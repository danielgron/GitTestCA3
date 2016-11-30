/*
 * This is the controller for assignquatity.html
 */

angular.module('myApp.watchflow')
        .controller('quantityController', quantityController);

quantityController.$inject = ['newWatchCardFactory', '$location'];



function quantityController(newWatchCardFactory, $location){
    
    //**Bindable Variables****
   var self = this;
   self.clickedShift = {};
   self.allRedCrossLevels =[];
   self.avalibleResources = [];
   
   
   ///***Function Calls****
   self.getAllRedCrossLevels = getAllRedCrossLevels;
   self.getclicked = getclicked;
   self.getAvalibleResources = getAvalibleResources;
   self.moveResource = moveResource;
   self.moveResourceBack = moveResourceBack;
   self.saveChanges = saveChanges;
   
   //** Exceute on Enter *****
    getclicked();
    getAvalibleResources();
   
   
   //*** Functions*****
   function getclicked(){
       self.clickedShift = newWatchCardFactory.getClickedShift();
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
       newWatchCardFactory.getAvalibleResources(self.clickedShift.id)
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
