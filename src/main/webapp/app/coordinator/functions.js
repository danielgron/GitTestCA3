angular.module('myApp.functions', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/coordinator/newfunction', {
                    templateUrl: "app/coordinator/template/functions.html"

                });
            }])

        .controller("functionctrl", [ 'UserFactory', 'Functionsfactory', function ( UserFactory, Functionsfactory) {
                var self = this;
                self.allDepartmentFunctions;
                getAllDepartmentFunctions();
                
                function getAllDepartmentFunctions(){
                    Functionsfactory.getFunctionsFromDepartment(UserFactory.getDepartment())
                            .then(function (response){
                                self.allDepartmentFunctions = response.data;
                    }), function(error){
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

                    