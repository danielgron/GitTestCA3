angular.module('myApp.watchflow')
        .controller('singlenewWatchCardController', controller);

controller.$inject = ['newWatchCardFactory'];

function controller(newWatchCardFactory){
    var self = this;
    self.clickedShift = {};
    self.getclicked = getclicked;
    self.testing = "Hello";
    
    
    //**** To DO when Entering ******
    self.getclicked();
    
    
    
    function getclicked(){
        self.clickedShift = newWatchCardFactory.getClickedShift();
    }
    
}
