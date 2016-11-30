angular.module('myApp.watchflow')

        .controller('AllRequestsController',allRequestsController)
        .factory('requestFactory', requestFactory);
allRequestsController.$inject = ['requestFactory', '$location', '$http'];
requestFactory.$inject = ['$http','$location'];
function allRequestsController(requestFactory, $location, $http) {
    var promise = requestFactory.getRequests();
    var self = this;
    self.test = "Controller is working";
    self.requests = [];
    self.go=requestFactory.go;

    promise.then(
            function successCallback(res) {
                self.requests = res.data;
            },
            function errorCallback(res) {
                console.log("ERROR");
                console.log(res.data.statusText);

            });
}



function requestFactory($http,$location) {
var chosenRequest = {};

    var service = {
        
        getRequests: getRequests,
        go: go
    };



    function getRequests() {
        return $http.get("api/request/");
    };
    
    function go(request){
        console.log("CLICK");
        chosenRequest=request;
         $location.path("/request");
     };
    return service;
}