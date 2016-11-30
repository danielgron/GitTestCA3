angular.module('myApp.watchflow')
        .directive('pendingDirective',watchCardDir);

function watchCardDir(){
           return{
        restrict : "AE",
        templateUrl : 'app/watchflow/pending/pendingwatchcards.html',
        controller: "PendingController",
        controllerAs : "ctrl"
           };                 
};      

