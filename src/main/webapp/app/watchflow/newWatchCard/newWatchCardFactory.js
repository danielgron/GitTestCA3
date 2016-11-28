angular.module('myApp.watchflow')
        .factory('newWatchCardFactory', watchFactory);
        
        watchFactory.$inject = ['$http'];


function watchFactory($http){
    var clickedShift;
    
    var service = {
        loadEvents: loadEvents,
        getClickedShift : getClickedShift,
        setShift: setShift
    };
    
    return service;
    
    
    function loadEvents(){
       return $http.get("api/watchflow/events/ReadyToCreate");
    };
    
    function getClickedShift(){
        return clickedShift;
    }
    
    function setShift(shift){
        clickedShift = shift;
    }
    
    
};


