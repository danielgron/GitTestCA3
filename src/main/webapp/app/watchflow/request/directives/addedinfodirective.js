angular.module('myApp.watchflow')

        .directive('addedInfo', function () {

            return {
                controller: 'RequestController',
                controllerAs: 'ctrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/addedinfo.html'

            };
        })

        ;