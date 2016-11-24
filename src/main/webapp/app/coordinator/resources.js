/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module('myApp.resources', [])

        .controller("Resourcectrl", ['ResourceFactory','$scope', function ( ResourceFactory,$scope) {
                var self = this;
                ResourceFactory.setEvent($scope.event);
                console.log($scope.event);
                
                
        }])
        .factory('ResourceFactory', function ($http) {
    var event;
    var getAvailableResources = function getAvailableResources(){
      return $http.get("api/coordinator/availableResources/" + event);
    };
    var setEvent = function getAvailableResources(e){
      event = e;
    };
    return {
      getAvailableResources: getAvailableResources,
      setEvent: setEvent
    };
  });
        


;