angular.module('myApp.watchflow')

        .directive('billing', function () {

            return {
                controller: 'RequestController',
                controllerAs: 'ctrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/billing.html'

            };
        })

        ;