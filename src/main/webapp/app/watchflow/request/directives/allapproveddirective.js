angular.module('myApp.watchflow')

        .directive('allApproved', function () {

            return {
                controller: 'AllRequestsController',
                controllerAs: 'allRequestCtrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/allapproved.html'

            };
        })

        ;