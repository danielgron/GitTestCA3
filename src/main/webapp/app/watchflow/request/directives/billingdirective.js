angular.module('myApp.watchflow')

        .directive('billing', function () {

            return {
                controller: 'BillingController',
                controllerAs: 'ctrl',
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/billing.html'

            };
        })

        ;