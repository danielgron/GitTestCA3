angular.module('myApp.watchflow')

        .directive('details', function () {

            return {
                controller: 'RequestController',
                controllerAs: 'ctrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/details.html'

            };
        })

        ;