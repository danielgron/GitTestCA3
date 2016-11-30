angular.module('myApp.watchflow')

        .directive('contact', function () {

            return {
                controller: 'RequestController',
                controllerAs: 'ctrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/contact.html'

            };
        })

        ;