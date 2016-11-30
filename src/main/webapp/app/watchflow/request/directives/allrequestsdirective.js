angular.module('myApp.watchflow')

        .directive('allRequests', function () {

            return {
                controller: 'AllRequestsController',
                controllerAs: 'allRequestCtrl',
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/allrequests.html'

            };
        })

        ;