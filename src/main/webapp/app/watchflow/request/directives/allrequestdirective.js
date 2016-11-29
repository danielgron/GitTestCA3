angular.module('myApp.watchflow')

        .directive('allrequests', function () {

            return {
                controller: 'AllRequestsController',
                controllerAs: 'ctrl',
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/template/allrequests.html'

            };
        })

        ;