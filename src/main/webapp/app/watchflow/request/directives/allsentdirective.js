/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


angular.module('myApp.watchflow')

        .directive('allSent', function () {

            return {
                controller: 'AllRequestsController',
                controllerAs: 'allRequestCtrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/allsent.html'

            };
        })

        ;