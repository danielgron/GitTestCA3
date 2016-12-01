angular.module('myApp.watchflow')

        .directive('approvedRequest', function () {

            return {
                controller: 'RequestController',
                controllerAs: 'requestCtrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/approvedrequest.html'

            };
        })

        ;