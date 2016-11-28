angular.module('myApp.watchflow')
        .controller('newWatchCardController', watchCardController);


watchCardController.$inject = ['newWatchCardFactory', '$location'];

function watchCardController(newWatchCardFactory, $location) {


    var self = this;

    self.hello = "Hello World";
    self.shifts = [];
    self.getShifts = getShifts;
    self.go = go;
    
    
    
    // TO Do when Entering////
    self.getShifts();



    function getShifts() {
        newWatchCardFactory.loadEvents()
                .then(function successCallback(res) {
                    self.shifts = res.data;
        }, function errorCallBack(error){
           console.log("Error in callback: " + error.code); 
        });
        
        
        
    }
    
     function go(shift){
         newWatchCardFactory.setShift(shift);
         $location.path("/singleNewWatchCard");
     };

}






