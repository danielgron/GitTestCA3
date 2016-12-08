angular.module('myApp.watchflow')
// *** Declaration
.factory('requestFactory', requestFactory);


// *** Injection *** 
requestFactory.$inject = ['$http', '$location'];



// *** Main Function ***
function requestFactory($http, $location) {
    var chosenRequest;
    var readOnly;

    var service = {
        getRequests: getRequests,
        getRequest: getRequest,
        updateRequest: updateRequest,
        go: go,
        goApproved: goApproved,
        goSent: goSent,
        getResources: getResources,
        approveRequest: approveRequest,
        rejectRequest: rejectRequest,
        createEventFromRequest: createEventFromRequest,
        setReadOnly: setReadOnly,
        getReadOnly: getReadOnly
    }
    ;
    
    function getReadOnly() {
        return readOnly;
    }
    ;
    
    function setReadOnly(newValue) {
        readOnly=newValue;
    }
    ;
    
    function getRequests() {
        return $http.get("api/request/");
    }
    ;
    
    function updateRequest(request){
        console.log("Update Request!");
        return $http.put("api/request/",request);
    }
    
    function getResources(request) {
        window.console.log(request);
        return $http.get("api/request/resource/" + request.eventstart + "/" + request.eventend + "/");
    }
    ;
    
    function getRequest() {
        return chosenRequest;
    }
    ;
    
    function approveRequest() {
        return $http.post("api/request/requesttoapproved/"+chosenRequest.id);
    }
    ;
    
    function rejectRequest() {
        return $http.post("api/request/requesttorejected/"+chosenRequest.id);
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
    
    function goSent(request) {
        //console.log("CLICK");
        chosenRequest = request;
        $location.path("/sentrequest");
    }
    ;
    
    return service;
}
