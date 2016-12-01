angular.module('myApp.watchflow')

        .controller('AllRequestsController', allRequestsController)
        .factory('requestFactory', requestFactory)
        .filter('allRequestFilter', allRequestFilter)
        .filter('allApprovedFilter', allApprovedFilter)
        .filter('allSentFilter', allSentFilter);
allRequestsController.$inject = ['requestFactory', '$location'];
requestFactory.$inject = ['$http', '$location'];
function allRequestsController(requestFactory, $location) {
    var promise = requestFactory.getRequests();
    var self = this;
    //self.test = "Controller is working";
    self.requests = [];
    self.go = requestFactory.go;

    promise.then(
            function successCallback(res) {
                self.requests = res.data;
            },
            function errorCallback(res) {
                console.log("ERROR");
                console.log(res.data.statusText);

            });
}



function requestFactory($http, $location) {
    var chosenRequest;

    var service = {
        getRequests: getRequests,
        getRequest: getRequest,
        go: go,
        getResources: getResources,
        createEventFromRequest: createEventFromRequest
    };



    function getRequests() {
        return $http.get("api/request/");
    }
    ;
    function getResources(request) {
        return $http.get("api/request/resource/" + request.eventstart + "/" + request.eventend + "/");
    }
    ;
    function getRequest() {
        //console.log(chosenRequest);
        return chosenRequest;
    }
    ;

    function createEventFromRequest() {
        return $http.post("api/request/requesttoevent/");
    }



    function go(request) {
        //console.log("CLICK");
        chosenRequest = request;
        $location.path("/request");
    }
    ;
    return service;
}

function allRequestFilter() {

    // Create the return function and set the required parameter name to **input**
    return function (input) {
        var requests = [];
        // Using the angular.forEach method, go through the array of data and perform the operation of figuring out if the language is statically or dynamically typed.
        angular.forEach(input, function (request) {
            if (request.requestStatus == 'RECIEVED')
                requests.push(request);
            //console.log("Added request");

        });
        return (requests);
    };
}
;
function allApprovedFilter() {

    // Create the return function and set the required parameter name to **input**
    return function (input) {
        var requests = [];
        // Using the angular.forEach method, go through the array of data and perform the operation of figuring out if the language is statically or dynamically typed.
        angular.forEach(input, function (request) {
            if (request.requestStatus == 'APPROVED')
                requests.push(request);
            //console.log("Added request");

        });
        return (requests);
    };
}
;
function allSentFilter() {
    // Create the return function and set the required parameter name to **input**
    return function (input) {
        var requests = [];

        // Using the angular.forEach method, go through the array of data and perform the operation of figuring out if the language is statically or dynamically typed.
        angular.forEach(input, function (request) {
            if (request.requestStatus == 'SENT')
                requests.push(request);
            //console.log("Added request");

        });
        return (requests);
    };
}
;