angular.module('myApp.watchflow')
// *** Declaration
.factory('requestFactory', requestFactory);


// *** Injection *** 
requestFactory.$inject = ['$http', '$location'];



// *** Main Function ***
function requestFactory($http, $location) {
    var chosenRequest;

    var service = {
        getRequests: getRequests,
        getRequest: getRequest,
        go: go,
        goApproved: goApproved,
        getResources: getResources,
        createEventFromRequest: createEventFromRequest
    }
    ;
    
    function getRequests() {
        return $http.get("api/request/");
    }
    ;
    
    function getResources(request) {
        window.console.log(request);
        
        
        return $http.get("api/request/resource/" + request.eventstart + "/" + request.eventend + "/");
    }
    ;
    
    function getRequest() {
        //console.log(chosenRequest);
        return chosenRequest;
    }
    ;

    function createEventFromRequest() {
        chosenRequest.requestStatus="PROCCESED";
        var jsonString = angular.toJson(chosenRequest);
        return $http.post("api/request/requesttoevent/",jsonString);
    }
    ;

    function go(request) {
        //console.log("CLICK");
        chosenRequest = request;
        $location.path("/request");
    }
    ;
    
    function goApproved(request) {
        //console.log("CLICK");
        chosenRequest = request;
        $location.path("/approvedrequest");
    }
    ;
    
    return service;
}
