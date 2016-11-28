angular.module('myApp.watchflow')

        .directive('contact', function () {

            return {
                controller: 'contactController',
                controllerAs: 'ctrl',
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/template/contact.html'

            };
        })

        ;