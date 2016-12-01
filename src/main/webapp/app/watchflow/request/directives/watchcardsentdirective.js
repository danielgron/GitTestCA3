angular.module('myApp.watchflow')

        .directive('shiftType', function () {

            return {
                controller: 'RequestController',
                controllerAs: 'requestCtrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/shifttype.html'

            };
        })

        ;