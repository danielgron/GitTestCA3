angular.module('myApp.watchflow')
        .factory('newWatchCardFactory', watchFactory);
        
        watchFactory.$inject = ['$http'];


function watchFactory($http){
    
    var service = {
        loadEvents: loadEvents
    };
    
    return service;
    
    
    function loadEvents(){
       return $http.get("api/watchflow/events/ReadyToCreate");
    };
};


