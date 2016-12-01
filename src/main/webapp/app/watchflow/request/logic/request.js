angular.module('myApp.watchflow')

        .controller('RequestController', requestController);
requestController.$inject = ['$scope', 'requestFactory', 'newWatchCardFactory'];

function requestController($scope, requestFactory, newWatchCardFactory) {

    //**Bindable Variables****
    var self = this;
    self.resources = [];

    ///***Function Calls****
    self.getAllRedCrossLevels = getAllRedCrossLevels;
    self.moveResource = moveResource;
    self.moveResourceBack = moveResourceBack;
    self.createEventFromRequest = createEventFromRequest;

    //** Exceute on Enter *****
    self.request = requestFactory.getRequest();
    self.getResources = getResources();

    //*** Functions*****

    function getAllRedCrossLevels() {
        newWatchCardFactory.getAllRedCrossLevelsFromFac()
                .then(
                        function successCallback(res) {
                            self.allRedCrossLevels = res.data;
                        }, function errorCallBack(error) {
                    console.log("Error in callback: " + error.code);
                });
    }

    function getResources() {
        requestFactory.getResources(self.request)
                .then(
                        function successCallback(res) {
                            self.resources = res.data;
                        }, function errorCallBack(error) {
                    console.log("Error in callback: " + error.code);
                });
    }

    function moveResource() {
        var selected = self.selected; // -- Variable that is created by selecting
        if (selected != null) {

            self.avalibleResources.remove(selected);
            self.clickedShift.resources.push(selected);
        }
    }
    ;

    function moveResourceBack(resource) {
        self.avalibleResources.push(resource);
        self.clickedShift.resources.remove(resource);
    }
    ;

    function createEventFromRequest() {
        requestFactory.createEventFromRequest(self.request)
                .then(
                        function successCallback(res) {
                            self.resources = res.data;
                        }, function errorCallBack(error) {
                    console.log("Error in callback: " + error.code);
                });
    }
    //** Not sorted**





}
;