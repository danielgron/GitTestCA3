angular.module('myApp.watchflow')

        .controller('AllRequestsController',allRequestsController)
        .factory('requestFactory', requestFactory);
allRequestsController.$inject = ['requestFactory', '$location', '$http'];
requestFactory.$inject = ['$http'];
function allRequestsController(requestFactory, $location, $http) {
    var promise = requestFactory.getRequests();
    var self = this;
    self.test = "Controller is working";
    self.requests = [];

    promise.then(
            function successCallback(res) {
                self.requests = res.data;
            },
            function errorCallback(res) {
                console.log("ERROR");
                console.log(res.data.statusText);

            });
}



function requestFactory($http) {


    var service = {

        getRequests: getRequests
    };



    function getRequests() {
        return $http.get("api/request/");
    }
    ;
    return service;
}