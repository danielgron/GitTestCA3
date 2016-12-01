angular.module('myApp.watchflow')

        .directive('action', function () {

            return {
                //controller: 'RequestController',
                //controllerAs: 'requestCtrl',
                //bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/action.html'

            };
        })

        ;