angular.module('myApp.watchflow')

        .controller('eventController',eventController)
;

eventController.$inject = ['$scope'];


function eventController($scope){
    $scope.request;
        };