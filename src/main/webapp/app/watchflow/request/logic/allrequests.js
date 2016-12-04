angular.module('myApp.watchflow')

        .controller('AllRequestsController', allRequestsController)
        .filter('allRequestFilter', allRequestFilter)
        .filter('allApprovedFilter', allApprovedFilter)
        .filter('allSentFilter', allSentFilter);


allRequestsController.$inject = ['requestFactory', '$location'];

function allRequestsController(requestFactory, $location) {
    var promise = requestFactory.getRequests();

    // *** Bindable
    var self = this;
    self.requests = [];
    self.go = requestFactory.go;
    self.goApproved = requestFactory.goApproved;

    promise.then(
            function successCallback(res) {
                self.requests = res.data;
            },
            function errorCallback(res) {
                console.log("ERROR");
                console.log(res.data.statusText);
            });
}


// Filters to get the relevant requests for the different views

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