angular.module('myApp.watchflow')

        .directive('event', function () {

            return {
                
                controller: 'RequestController',
                controllerAs: 'ctrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/event.html'

            };
        })

        ;