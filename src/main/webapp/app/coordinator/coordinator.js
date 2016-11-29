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

app.controller('CoordinatorCtrl', ['$window',"$scope", "$http", 'UserFactory', function ($window,$scope, $http, UserFactory) {
        var self = this;
        self.newUser = {};
        self.newUser.department ={nameOfDepartment: UserFactory.getDepartment()}; // Gets the department of the Admin!
        self.createdUser = UserFactory.getCreatedUser();
        self.createUser = function () {
            var userTobesend = self.newUser;
            var jsonString = JSON.stringify(userTobesend);
            $http({
            method: 'POST',
            data: jsonString,
            url: 'api/coordinator'
          }).then(function successCallback(res) {
            self.data = res.data;
            UserFactory.setCreatedUser(self.data);
            $window.location.href = '#/coordinator/viewnewuser';
          }, function errorCallback(res) {
            $scope.error = res.status + ": "+ res.data.statusText;
            console.log("ERROR");
            console.log($scope.error);

            });
        };

    }]);



