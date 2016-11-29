angular.module('myApp.watchflow')

        .controller('AllRequestsController',[allRequestsController]);
allRequestsController.$inject = ['requestFactory', '$location','$http'];
function allRequestsController(requestFactory, $location, $http){
                
        }

angular.module('myApp.watchflow')
        .Factory('requestFactory', requestFactory);

requestFactory.$inject = ['$inject'];
function requestFactory($http){
    
}