'use strict';

angular.module('myApp.watchflow', ['ngRoute']);



angular.module('myApp.watchflow').controller('WatchFlowCtrl', [function () {


    }]);


angular.module('myApp.watchflow').config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/watchflow', {
            templateUrl: 'app/watchflow/templates/watchflow.html',
            controller: 'WatchFlowCtrl',
            controllerAs: 'wfc'
        });
    }]);


