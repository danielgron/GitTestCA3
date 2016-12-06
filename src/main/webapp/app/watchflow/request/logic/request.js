angular.module('myApp.watchflow')

        .controller('RequestController', RequestController);
RequestController.$inject = ['$scope', 'requestFactory', 'newWatchCardFactory','$location'];

function RequestController($scope, requestFactory, newWatchCardFactory, $location) {

    //**Bindable Variables****
    var self = this;
    self.resources = [];
    self.readOnly = true;

    ///***Function Calls****
    self.getAllRedCrossLevels = getAllRedCrossLevels;
    self.moveResource = moveResource;
    self.moveResourceBack = moveResourceBack;
    self.createEventFromRequest = createEventFromRequest;
    self.setReadOnly = requestFactory.setReadOnly;
    self.goHome = goHome;
    self.approveRequest = requestFactory.approveRequest;

    //** Exceute on Enter *****
    self.request = requestFactory.getRequest();
    self.getResources = getResources();
    self.readOnly = requestFactory.getReadOnly();

    //*** Functions*****

    function setRead() {
        self.readOnly = true;
    }

    function readOnlyFalse() {
        self.readOnly = false;
    }
    function getAllRedCrossLevels() {
        newWatchCardFactory.getAllRedCrossLevelsFromFac()
                .then(
                        function successCallback(res) {
                            self.allRedCrossLevels = res.data;
                        }, function errorCallBack(error) {
                    console.log("Error in callback: " + error.code);
                });
    }
    ;

    function getResources() {
        requestFactory.getResources(self.request)
                .then(
                        function successCallback(res) {
                            self.resources = res.data;
                        }, function errorCallBack(error) {
                    console.log("Error in callback: " + error.code);
                });
    }
    ;

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

        requestFactory.createEventFromRequest()
                .then(
                        function successCallback(res) {
                            //self.resources = res.data;
                        }, function errorCallBack(error) {
                    self.request.requestStatus = "APPROVED";
                    console.log("Error in callback: " + error.code);
                });
    }
    ;
    //** Not sorted**


function goHome() {
        $location.path("/watchflow");
    }
    ;


}
;