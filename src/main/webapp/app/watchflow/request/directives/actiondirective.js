angular.module('myApp.watchflow')

        .directive('times', function () {

            return {
                controller: 'RequestController',
                controllerAs: 'requestCtrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/times.html'

            };
        })

        ;