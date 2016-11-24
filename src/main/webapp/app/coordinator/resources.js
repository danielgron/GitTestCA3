/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module('myApp.resources', [])

        .controller("resourcectrl", ['ResourceFactory', function ( ResourceFactory) {
                
                
                
        }])
        .directive('resources', function() {
  return {
    restrict: 'E',
    templateUrl: 'template/resources.html'

  };
})
        


;