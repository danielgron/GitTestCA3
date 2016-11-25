/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module('myApp.resources', [])

        .controller("Resourcectrl", ['ResourceFactory', '$scope', '$http','bsLoadingOverlayService', function (ResourceFactory, $scope, $http,bsLoadingOverlayService) {
                var self = this;
                ResourceFactory.setEvent($scope.event);
                console.log($scope.event);
                //self.getAvailableResources = ResourceFactory.getAvailableResources();
                //console.log(self.getAvailableResources);
                self.availableResources = []; 
                function getAvailableResources() {
                    bsLoadingOverlayService.start({referenceId: "ressource"});

                    ResourceFactory.getAvailableResources()
                            .then(function (response) {
                                self.availableResources = response.data;
                                bsLoadingOverlayService.stop({referenceId: "ressource"});

                            }), function (error) {
                        bsLoadingOverlayService.stop({referenceId: "ressource"});

                        console.log("Error" + error);
                    };
                }
                getAvailableResources();

                self.changeResource = function (res) {
                    console.log("Click")
                    $http({
                        method: 'POST',
                        url: 'api/Resource/changeResShift/' + $scope.event + '/' + res.id,
                        data: self.newFunction
                    }).then(function successCallback(response) {
                        // this callback will be called asynchronously
                        // when the response is available
                        alert("succes");
                    }, function errorCallback(response) {
                        // called asynchronously if an error occurs
                        // or server returns response with an error status.

                        console.log("Error: " + response.statusCode);
                    });
                };

            }])

        .factory('ResourceFactory', function ($http) {
            var event;
            var getAvailableResources = function getAvailableResources() {
                return $http.get("api/Resource/" + event);
            };
            var setEvent = function getAvailableResources(e) {
                event = e;
            };

            return {
                getAvailableResources: getAvailableResources,
                setEvent: setEvent
            };
        });



;