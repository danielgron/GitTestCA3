angular.module('myApp.watchflow')

        .directive('watchCard', function () {

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