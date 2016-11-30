angular.module('myApp.watchflow')

        .directive('contact', function () {

            return {
                controller: 'ContactController',
                controllerAs: 'ctrl',
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/contact.html'

            };
        })

        ;