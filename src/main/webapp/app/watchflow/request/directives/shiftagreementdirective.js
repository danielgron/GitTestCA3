angular.module('myApp.watchflow')

        .directive('shiftAgreement', function () {

            return {
                controller: 'RequestController',
                controllerAs: 'requestCtrl',
                bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/shiftagreement.html'

            };
        })

        ;