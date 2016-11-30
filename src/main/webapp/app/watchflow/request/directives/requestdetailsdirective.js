angular.module('myApp.watchflow')

        .directive('details', function () {

            return {
                controller: 'detailsController',
                controllerAs: 'ctrl',
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/details.html'

            };
        })

        ;