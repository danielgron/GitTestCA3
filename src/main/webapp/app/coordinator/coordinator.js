'use strict';

var app = angular.module('myApp.coordinator', ['ngRoute']);

app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/coordinator', {
            templateUrl: 'app/coordinator/coordinator.html',
            controller: 'CoordinatorCtrl',
            controllerAs: 'ctrl'
        });
        $routeProvider.when('/coordinator/newuser', {
            templateUrl: 'app/coordinator/createnewuser.html',
            controller: 'CoordinatorCtrl',
            controllerAs: 'ctrl'
        });
        $routeProvider.when('/coordinator/viewnewuser', {
            templateUrl: 'app/coordinator/viewnewuser.html',
            controller: 'CoordinatorCtrl',
            controllerAs: 'ctrl'
        });
    }]);

app.controller('CoordinatorCtrl', ["$scope", "$http", 'UserFactory', function ($scope, $http, UserFactory) {
        var self = this;
        self.newUser = {};
        self.newUser.department ={nameOfDepartment: UserFactory.getDepartment()}; // Gets the department of the Admin!
        self.createUser = function () {
            var userTobesend = self.newUser;
            var jsonString = JSON.stringify(userTobesend);
            $http({
            method: 'POST',
            data: jsonString,
            url: 'api/coordinator'
          }).then(function successCallback(res) {
            $scope.data = res.data;
            console.log($scope.data);
          }, function errorCallback(res) {
            $scope.error = res.status + ": "+ res.data.statusText;
            console.log("ERROR");
            console.log($scope.error);

            });
        };

    }]);


