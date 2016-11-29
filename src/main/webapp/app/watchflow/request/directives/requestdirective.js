angular.module('myApp.watchflow')

        .directive('request', function () {

            return {
                controller: 'requestController',
                controllerAs: 'ctrl',
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/template/request.html',
                scope: {
                    request: '@'
                }

            };
        })

        ;