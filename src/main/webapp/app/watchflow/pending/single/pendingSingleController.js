angular.module('myApp.watchflow')
        .controller('PendingSingleCtrl', PendingSingleCtrl);

PendingSingleCtrl.$inject = ['pendingFactory', '$location'];


function PendingSingleCtrl(pendingFactory, $location){
  
  
   //**Bindable Variables****
   var self = this;
   self.clickedShift = {};
   
   
   
   
   ///***Function Calls****
   self.getclicked = getclicked();
   
   //** Exceute on Enter *****
    getclicked();
   
   
   //*** Functions*****
      function getclicked(){
       self.clickedShift = pendingFactory.getClickedShift();
   }

}