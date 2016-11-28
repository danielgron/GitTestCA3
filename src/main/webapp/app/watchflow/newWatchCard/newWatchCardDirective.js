angular.module('myApp.watchflow')
        .directive('newWatchCardDir',watchCardDir);

function watchCardDir(){
           return{
        restrict : "AE",
        templateUrl : 'app/watchflow/newWatchCard/newWatchCard.html',
        controller: "newWatchCardController",
        controllerAs : "ctrl"
           };                 
};      