angular.module('myApp.watchflow')

        .controller('RequestController', RequestController);
RequestController.$inject = ['$scope', 'requestFactory', 'newWatchCardFactory','$location'];

function RequestController($scope, requestFactory, newWatchCardFactory, $location) {

    //**Bindable Variables****
    var self = this;
    self.request = {};
    self.availableResources = [];
    self.clickedShift = {};
    self.readOnly = true;

    ///***Function Calls****
    self.getAllRedCrossLevels = getAllRedCrossLevels;
    self.moveResource = moveResource;
    self.moveResourceBack = moveResourceBack;
    self.createEventFromRequest = createEventFromRequest;
    self.setReadOnly = requestFactory.setReadOnly;
    self.goHome = goHome;
    self.approveRequest = requestFactory.approveRequest;
    
    
   self.getAvailableResources = getResources;
   self.moveResource = moveResource;
   self.moveResourceBack = moveResourceBack;

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
                            self.availableResources = res.data;
                        }, function errorCallBack(error) {
                    console.log("Error in callback: " + error.code);
                });
    }
    ;

    function moveResource() {
        var selected = self.selected; // -- Variable that is created by selecting
        if (selected != null) {
            if (typeof(self.request.resources) == "undefined"){
                self.request.resources=[];
            }

            self.availableResources.remove(selected);
            self.request.resources.push(selected);
        }
    }
    ;

    function moveResourceBack(resource) {
        self.availableResources.push(resource);
        self.request.resources.remove(resource);
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