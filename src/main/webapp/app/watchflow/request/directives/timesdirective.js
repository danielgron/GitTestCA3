angular.module('myApp.watchflow')

        .directive('times', function () {

            return {
                controller: 'TimesController',
                controllerAs: 'ctrl',
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/template/times.html'

            };
        })

        ;