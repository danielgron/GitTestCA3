angular.module('myApp.watchflow')

        .directive('event', function () {

            return {
                controller: 'eventController',
                controllerAs: 'ctrl',
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/template/event.html'

            };
        })

        ;