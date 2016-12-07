angular.module('myApp.watchflow')
        .controller('singlenewWatchCardController', controller);

controller.$inject = ['newWatchCardFactory', '$routeParams', '$location'];

function controller(newWatchCardFactory, $routeParams, $location) {
    //** Bindable Variables ****
    var self = this;
    self.clickedShift = {};
    self.testing = "Hello";
    self.eventId = $routeParams.param;
    
    
    //*** Function Declarations *****
    self.getclicked = getclicked;
    self.save = save;


    //**** To DO when Entering ******
    self.getclicked(self.eventId);


    //** Functions ****
    function getclicked(id) {
        newWatchCardFactory.getEventfromId(id)
                .then(
                        function successCallback(res) {
                            self.clickedShift = res.data;
                        }, function errorCallBack(errorResponse) {
                    console.log("Error in callback: " + errorResponse.data.error.code);
                });
    }
    
    function save(event){
        newWatchCardFactory.saveCommentCatering(event)
         .then(
                        function successCallback(res) {
                            console.log("Succes");
                            $location.path("/watchflow");
                        }, function errorCallBack(errorResponse) {
                    console.log("Error in callback: " + errorResponse.data.error.code);
                });
    }

}
