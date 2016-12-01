angular.module('myApp.watchflow')
        .factory('pendingFactory', pendingFactory);
        
        pendingFactory.$inject = ['$http'];


function pendingFactory($http){
    var clickedShift;
    
    var service = {
        loadEvents: loadEvents,
        getClickedShift : getClickedShift,
        setShift: setShift,
        getEvent: getEvent,
        getAvaliableSamaritsForEvent: getAvaliableSamaritsForEvent,
        getFunctionsForDepartment : getFunctionsForDepartment,
        saveWatches : saveWatches,
        postNewWatchFunctions: postNewWatchFunctions
    };
    
    return service;
    
    //*** Functions ****
    function loadEvents(){
       return $http.get("api/watchflow/events/Pending");
    };
    
    function getEvent(id){
        return $http.get("api/event/staffedevent/"+id);
    }
    
    function getAvailable(date){
        return $http.get("api/");
    }
    
    function getClickedShift(){
        return clickedShift;
    }
    
    function setShift(shift){
        clickedShift = shift;
    }
    
   function getAvaliableSamaritsForEvent(id){
        return $http.get("api/coordinator/" + id);
    };
    function getFunctionsForDepartment(){
        return $http.get("api/watchflow/functions");
    }
    
    function saveWatches(id,samarits){
        var jsonString = JSON.stringify(samarits);
        return $http.post("api/watchflow/"+id,jsonString);
    }
    function postNewWatchFunctions(data){
        return $http.post("api/watchflow/functions", data);
    }
    
}
