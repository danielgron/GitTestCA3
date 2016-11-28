angular.module('myApp.watchflow')

        .directive('addedInfo', function () {

            return {
                controller: 'AddedInfoController',
                controllerAs: 'ctrl',
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/template/addedInfo.html'

            };
        })

        ;