angular.module('myApp.watchflow')

        .directive('watchCardSent', function () {

            return {
                controller: 'RequestController',
                controllerAs: 'requestCtrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/watchcardsent.html'

            };
        })

        ;