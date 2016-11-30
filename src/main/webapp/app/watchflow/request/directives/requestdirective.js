angular.module('myApp.watchflow')

        .directive('request', function () {
console.log("requestDirective");
            return {
                
                controller: 'RequestController',
                controllerAs: 'ctrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/request.html',
                scope: {
                    request: '@'
                }

            };
        })

        ;