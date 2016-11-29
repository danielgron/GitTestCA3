angular.module('myApp.watchflow')
        .factory('newWatchCardFactory', watchFactory);
        
        watchFactory.$inject = ['$http'];


function watchFactory($http){
    var clickedShift;
    
    var service = {
        loadEvents: loadEvents,
        getClickedShift : getClickedShift,
        setShift: setShift,
        getAllRedCrossLevelsFromFac: getAllRedCrossLevelsFromFac,
        getAvalibleResources: getAvalibleResources
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
    
    function getAllRedCrossLevelsFromFac(){
        return $http.get("api/watchflow/redcrooslevel");
    }
    
    function getAvalibleResources(id){
      return  $http.get("api/Resource/" + id);
    };
    
    
};


