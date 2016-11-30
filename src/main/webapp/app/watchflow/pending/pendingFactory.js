angular.module('myApp.watchflow')
        .factory('pendingFactory', pendingFactory);
        
        pendingFactory.$inject = ['$http'];


function pendingFactory($http){
    var clickedShift;
    
    var service = {
        loadEvents: loadEvents,
        getClickedShift : getClickedShift,
        setShift: setShift
    };
    
    return service;
    
    //*** Functions ****
    function loadEvents(){
       return $http.get("api/watchflow/events/Pending");
    };
    
    function getClickedShift(){
        return clickedShift;
    }
    
    function setShift(shift){
        clickedShift = shift;
    }
    
}