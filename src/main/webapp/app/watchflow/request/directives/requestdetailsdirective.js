angular.module('myApp.watchflow')

        .directive('requestdetails', function () {

            return {
                //controller: 'RequestController',
                //controllerAs: 'requestCtrl',
                //bindToController: true,
                restrict: 'E',
                replace: 'true',
                templateUrl: 'app/watchflow/request/templates/requestdetails.html'

            };
        })

        ;