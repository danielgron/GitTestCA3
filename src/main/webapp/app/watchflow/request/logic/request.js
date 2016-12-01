angular.module('myApp.watchflow')

        .controller('RequestController',requestController);
requestController.$inject = ['$scope','requestFactory','newWatchCardFactory'];

function requestController($scope,requestFactory,newWatchCardFactory) {
    
    //**Bindable Variables****
     var self = this;
     self.resources=[];
    
    ///***Function Calls****
     self.getAllRedCrossLevels = getAllRedCrossLevels;
    
    //** Exceute on Enter *****
     self.request = requestFactory.getRequest();
     self.resources = getResources();
    
     //*** Functions*****
     
     function getAllRedCrossLevels(){
       newWatchCardFactory.getAllRedCrossLevelsFromFac()
       .then(
           function successCallback(res) {
                    self.allRedCrossLevels = res.data;
        }, function errorCallBack(error){
           console.log("Error in callback: " + error.code); 
        });
   }
   
   function getResources(){
      requestFactory.getResources(self.request)
       .then(
           function successCallback(res) {
                    self.allRedCrossLevels = res.data;
        }, function errorCallBack(error){
           console.log("Error in callback: " + error.code); 
        });
   }
     //** Not sorted**
    
    
    
     
   
};