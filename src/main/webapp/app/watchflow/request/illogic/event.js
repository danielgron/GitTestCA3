angular.module('myApp.watchflow')

        .controller('eventController',eventController);

eventController.$inject = ['$scope'];


function eventController($scope){
    console.log("EventCtrl");
    var self = this;
    self.request=$scope.request;
        };