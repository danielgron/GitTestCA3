angular.module('myApp.watchflow')
        .controller('PendingSingleCtrl', PendingSingleCtrl);

PendingSingleCtrl.$inject = ['pendingFactory', '$location', '$routeParams', 'bsLoadingOverlayService'];


function PendingSingleCtrl(pendingFactory, $location, $routeParams, bsLoadingOverlayService) {


    //**Bindable Variables****
    var self = this;
    self.id = $routeParams.param;
    self.shift = {};
    self.samarits = [];
    self.selectedSamarit;
    self.samaritOnWatch = [];
    self.selected = [];
    self.params = $routeParams.param;
    self.functionsforDepartment = [];
    self.clickedEvent = {};
    //self.samaritter = [];
    self.selectedSamaritForFunction = [];
    self.excistingFunctions = [];
    self.newFunctions = [];

    ///***Function Calls****
    self.getEvent = getEvent;
    self.getSamarits = getSamarits;
    self.add = add;
    self.save = save;
    self.remove = remove;
    self.reset = reset;
    self.getclickedEvent = getclickedEvent;
    self.getFunctionsForDepartment = getFunctionsForDepartment;
    self.moveFunction = moveFunction;
    self.deleteFunctionFromWatch = deleteFunctionFromWatch;
    self.samaritSelectedForFunction = samaritSelectedForFunction;
    self.sendFunctions = sendWatchFunctions;
    self.mapToStart = mapToStart;
    self.mapToEventWatchFunctions = mapToEventWatchFunctions;
    self.deleteFromExcistingFunctions = deleteFromExcistingFunctions;


    //** Exceute on Enter *****
    getEvent(self.id);
    getSamarits(self.id);
    getFunctionsForDepartment();
    getclickedEvent();


    //*** Functions*****
    function getEvent(id) {
        window.console.log('test getEvent');
        pendingFactory.getEvent(id).then(function (successResponse) {
            self.shift = successResponse.data;
        }, function (errorResponse) {
            console.log("Error in callback: " + errorResponse.data.error.code);
        });
    }

    function getSamarits(id) {
        bsLoadingOverlayService.start();

        pendingFactory.getAvaliableSamaritsForEvent(id).then(function (successResponse) {
            self.samarits = successResponse.data;
            bsLoadingOverlayService.stop();


        }, function (errorResponse) {
            bsLoadingOverlayService.stop();

            window.console.log("Error in callback: " + errorResponse.data.error.code);
        });

    }

    function add(samarit, role) {

        if (role !== null && samarit != null) {
            samarit.role = role;
            self.samaritOnWatch.push(samarit);
            self.samarits.remove(samarit);
        }

    }

    function remove(samarit) {
        self.samarits.push(samarit);
        self.samaritOnWatch.remove(samarit);

    }

    function save() {
        bsLoadingOverlayService.start();
        pendingFactory.saveWatches(self.id, self.samaritOnWatch)
                .then(function (successResponse) {
                    window.console.log(successResponse);
                    bsLoadingOverlayService.stop();
                }, function (errorResponse) {
                    bsLoadingOverlayService.stop();
                    window.console.log("Error in callback: " + errorResponse.data.error.code);

                });
    }
    function reset() {
        self.samaritOnWatch = [];
        getSamarits(self.id);
    }

    function getFunctionsForDepartment() {
        pendingFactory.getFunctionsForDepartment()
                .then(
                        function successCallback(res) {
                            self.functionsforDepartment = res.data;
                        }, function errorCallBack(errorResponse) {
                    console.log("Error in callback: " + errorResponse.data.error.code);
                });
    }
    ;

    function getclickedEvent() {
        pendingFactory.getEvent(self.params)
                .then(
                        function successCallback(res) {
                            self.clickedEvent = res.data;
                            if (self.clickedEvent.watchFunctions == null) {
                                self.clickedEvent.watchFunctions = [];
                            }
                            if (self.clickedEvent.watchFunctions.length !== 0) {
                                angular.forEach(self.clickedEvent.watchFunctions, function (value, key) {
                                    mapToStart(value);
                                });
                                self.clickedEvent.watchFunctions = [];
                            }
                        }, function errorCallBack(errorResponse) {
                    console.log("Error in callback: " + errorResponse.data.error.code);
                });
    }
    ;

    function moveFunction() {
        var selected = self.selected; // -- Variable that is created by selecting
        if (selected != null) {
            var functionString = selected.functionName;
            var newFunctionForWatch = new Object();
            newFunctionForWatch.functionName = functionString;
            if (self.newFunctions.length === 0) {
                newFunctionForWatch.id = 1;
            } else {
                newFunctionForWatch.id = self.newFunctions.length + 1;
            }
            newFunctionForWatch.samaritUserName = "Ikke Besat";
            self.newFunctions.push(newFunctionForWatch);
            self.selected = null;
        }
    }

    function deleteFunctionFromWatch(func) {
        self.newFunctions.remove(func);
    }

    function deleteFromExcistingFunctions(func) {
        self.excistingFunctions.remove(func);
    }

    function samaritSelectedForFunction(idofFunction, index) {
        var selectedObj = self.selectedSamaritForFunction[index];
        self.selectedSamaritForFunction.remove(selectedObj);
        var samaritUserName = selectedObj.firstName + ' ' +      selectedObj.lastName;
        mapSamaritToFunction(samaritUserName, idofFunction);
        
    }


    function mapSamaritToFunction(samaritUserName, idofFunction) {
        angular.forEach(self.newFunctions, function (value, key) {
            if (value.id === idofFunction) {
        var obj = new Object();
        obj.samaritUserName = samaritUserName;
        obj.functionName = value.functionName;
        self.excistingFunctions.push(obj);
            }
            //remove from newFunctions
            angular.forEach(self.newFunctions, function (value, key) {
                if(value.id === idofFunction){
                    self.newFunctions.remove(value);
                }
            });
        });
    }

    function sendWatchFunctions() {

        pendingFactory.postNewWatchFunctions(self.clickedEvent)
                .then(
                        function successCallback(res) {
                            console.log("Succes Callback");
                            $location.path("/watchflow");
                        }, function errorCallBack(errorResponse) {
                    console.log("Error in callback: " + errorResponse.data.error.code);
                });
    }

    function mapToStart(value) {
        var obj = new Object();
        obj.samaritUserName = value.samaritUserName;
        obj.functionName = value.functionName;
        self.excistingFunctions.push(obj);
    }

    function mapToEventWatchFunctions() {
        angular.forEach(self.excistingFunctions, function (value, key) {
            self.clickedEvent.watchFunctions.push(value);
        });
        mapNewFunctions();
    }

    function mapNewFunctions() {
        angular.forEach(self.newFunctions, function (value, key) {
            self.clickedEvent.watchFunctions.push(value);
        });
        sendWatchFunctions();
    }

}