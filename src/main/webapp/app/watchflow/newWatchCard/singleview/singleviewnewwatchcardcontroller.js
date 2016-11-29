angular.module('myApp.watchflow')
        .controller('singlenewWatchCardController', controller);

controller.$inject = ['newWatchCardFactory'];

function controller(newWatchCardFactory){
    //** Bindable Variables ****
    var self = this;
    self.clickedShift = {};
    self.getclicked = getclicked;
    self.testing = "Hello";
    
    
    //**** To DO when Entering ******
    self.getclicked();
    
    
    //** Functions ****
    function getclicked(){
        self.clickedShift = newWatchCardFactory.getClickedShift();
    }
    
}
