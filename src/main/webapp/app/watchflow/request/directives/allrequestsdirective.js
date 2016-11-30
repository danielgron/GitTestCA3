angular.module('myApp.watchflow')

        .directive('allRequests', function () {

            return {
                controller: 'AllRequestsController',
                controllerAs: 'allRequestCtrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/allrequests.html'

            };
        })

        ;