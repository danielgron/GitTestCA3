angular.module('myApp.watchflow')

        .directive('offer', function () {

            return {
                //controller: 'RequestController',
                //controllerAs: 'requestCtrl',
                //bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/offer.html'

            };
        })

        ;