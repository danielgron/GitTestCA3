angular.module('myApp.watchflow')

        .controller('RequestController',requestController);
requestController.$inject = ['$scope','requestFactory'];

function requestController($scope,requestFactory) {
    //console.log("RequestController");
     var self = this;
     self.request = requestFactory.getRequest();
   
};