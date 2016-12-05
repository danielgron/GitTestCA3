(function () {
    'use strict';
    angular.module('redCrossApp').service('indexService', ['$http', indexService]);

    function indexService($http) {

        //#region "Constructor"
        var service = {
            saveData: saveData
        }
        return service;
        //#endregion

        function saveData(items, onSuccess, onError) {
            var config = {
                headers: {
                    headers: { 'Content-Type': 'application/json' }
                }
            }
            $http.post('/..api/demoall/', items, config)
                .success(function (data, status, headers) {
                    onSuccess(data)
                })
                .error(function (data, status, header, config) {
                    onError(data)
                });
        }
    }
})();