angular.module('myApp.watchflow')
        .factory('pendingFactory', pendingFactory);
        
        pendingFactory.$inject = ['$http'];


function pendingFactory($http){
    var clickedShift;
    
    var service = {
        loadEvents: loadEvents,
        getClickedShift : getClickedShift,
        setShift: setShift,
        getFunctionsForDepartment : getFunctionsForDepartment,
        getEvent: getEvent
    };
    
    return service;
    
    //*** Functions ****
    function loadEvents(){
       return $http.get("api/watchflow/events/Pending");
    };
    
    function getEvent(id){
        return $http.get("api/event/staffedevent/"+id);
    }
    
    function getClickedShift(){
        return clickedShift;
    }
    
    function setShift(shift){
        clickedShift = shift;
    }
    
    function getFunctionsForDepartment(){
        return $http.get("api/watchflow/functions");
    }
    
}