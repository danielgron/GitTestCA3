angular.module('myApp.watchflow')
        .controller('singlenewWatchCardController', controller);

controller.$inject = ['newWatchCardFactory', '$routeParams'];

function controller(newWatchCardFactory, $routeParams) {
    //** Bindable Variables ****
    var self = this;
    self.clickedShift = {};
    self.getclicked = getclicked;
    self.testing = "Hello";
    self.eventId = $routeParams.param;


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

}
