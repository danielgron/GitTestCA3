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
        $routeProvider.when('/singleNewWatchCard',{
           templateUrl: 'app/watchflow/newWatchCard/singleview/singlenewwatchcard.html' 
        });
        $routeProvider.when('/assignquatity',{
           templateUrl: 'app/watchflow/newWatchCard/singleview/assignquatity.html' 
        });
        $routeProvider.when('/request',{
           templateUrl: 'app/watchflow/request/templates/request.html' 
       });
       $routeProvider.when('/singlepending/:param',{
           templateUrl: 'app/watchflow/pending/single/pendingsingle.html',
           controller: 'PendingSingleCtrl',
           controllerAs: 'pendingCtrl'
       });
    }]);


