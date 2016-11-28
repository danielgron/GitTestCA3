angular.module('myApp.watchflow')
        .controller('newWatchCardController', watchCardController);


watchCardController.$inject = ['newWatchCardFactory'];

function watchCardController(newWatchCardFactory) {


    var self = this;

    self.hello = "Hello World";
    self.shifts = [];
    self.getShifts = getShifts;
    self.getShifts();



    function getShifts() {
        newWatchCardFactory.loadEvents()
                .then(function successCallback(res) {
                    self.shifts = res.data;
        }, function errorCallBack(error){
           console.log("Error in callback: " + error.code); 
        });
        
        
    }

}






