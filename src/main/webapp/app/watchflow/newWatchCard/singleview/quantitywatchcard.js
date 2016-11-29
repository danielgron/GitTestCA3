/*
 * This is the controller for assignquatity.html
 */

angular.module('myApp.watchflow')
        .controller('quantityController', quantityController);

quantityController.$inject = ['newWatchCardFactory'];



function quantityController(newWatchCardFactory){
    
    //**Bindable Variables****
   var self = this;
   self.clickedShift = {};
   self.allRedCrossLevels =[];
   self.avalibleResources = [];
   self.selectedResources = [];
   
   
   ///***Function Calls****
   self.getAllRedCrossLevels = getAllRedCrossLevels;
   self.getclicked = getclicked;
   self.getAvalibleResources = getAvalibleResources;
   self.moveResource = moveResource;
   self.moveResourceBack = moveResourceBack;
   
   //** Exceute on Enter *****
    getclicked();
    getAllRedCrossLevels();
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
     self.selectedResources.push(selected);
     }
   };
   
   function moveResourceBack(resource){
     self.avalibleResources.push(resource);
     self.selectedResources.remove(resource);
   };
   
}
