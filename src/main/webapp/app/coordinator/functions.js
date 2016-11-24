angular.module('myApp.functions', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/coordinator/newfunction', {
                    templateUrl: "app/coordinator/template/functions.html"

                });
            }])

        .controller("functionctrl", ['$http','UserFactory', 'Functionsfactory', function ($http ,UserFactory, Functionsfactory) {
                var self = this;
                self.allDepartmentFunctions;
                self.newFunction = {};
                self.newFunction.department = {};
                self.newFunction.department.nameOfDepartment = UserFactory.getDepartment();
                getAllDepartmentFunctions();
                self.createNewFunction = function () {
                    $http({
                        method: 'POST',
                        url: 'api/coordinator/functions',
                        data: self.newFunction
                    }).then(function successCallback(response) {
                        // this callback will be called asynchronously
                        // when the response is available
                        alert("succes");
                    }, function errorCallback(response) {
                        // called asynchronously if an error occurs
                        // or server returns response with an error status.
                        console.log("Error: " + response.statusCode );
                    });
                };

                function getAllDepartmentFunctions() {
                    Functionsfactory.getFunctionsFromDepartment(UserFactory.getDepartment())
                            .then(function (response) {
                                self.allDepartmentFunctions = response.data;
                            }), function (error) {
                        console.log("Error" + error);
                    };
                }
            }])


        .factory('Functionsfactory', function ($http) {
            var getFunctionsFromDepartment = function (department) {
                return $http.get("api/coordinator/functions/" + department);
            };
            return {
                getFunctionsFromDepartment
            };
        });

                    