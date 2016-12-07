angular.module('myApp.watchflow')
        .controller('newWatchCardController', watchCardController);


watchCardController.$inject = ['newWatchCardFactory', '$location'];

function watchCardController(newWatchCardFactory, $location) {


    var self = this;

    self.shifts = [];
    self.getShifts = getShifts;
    self.details = details;
    self.staffing = staffing;
    self.searchText;
    self.sortType = "name";
    self.sortReverse  = false;
    

    
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
    
     function details(path){
//         newWatchCardFactory.setShift(shift);
//         $location.path("/singleNewWatchCard");
         $location.path(path);
     };
     
     function staffing(shift){
         newWatchCardFactory.setShift(shift);
         $location.path("/assignquatity");
     };
     
     }






