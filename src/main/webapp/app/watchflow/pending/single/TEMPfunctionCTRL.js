angular.module('myApp.watchflow')
        .controller('TEMPfunctionCTRL', TEMPfunctionCTRL);

TEMPfunctionCTRL.$inject = ['pendingFactory', '$location', '$routeParams', ];


function TEMPfunctionCTRL(pendingFactory, $location, $routeParams) {


    //**Bindable Variables***
    var self = this;
    self.params = $routeParams.param;
    self.functionsforDepartment = [];
    self.clickedEvent = {};
    self.samaritter = [];
    self.selectedSamaritForFunction = [];
    self.excistingFunctions= [];

    ///***Function Calls****
    self.getclickedEvent = getclickedEvent;
    self.getFunctionsForDepartment = getFunctionsForDepartment;
    self.moveFunction = moveFunction;
    self.deleteFunctionFromWatch = deleteFunctionFromWatch;
    self.testSamaritter = testSamaritter;
    self.samaritSelectedForFunction = samaritSelectedForFunction;
    self.sendFunctions = sendWatchFunctions;
    self.mapToStart = mapToStart;

    //** Exceute on Enter *****
    getFunctionsForDepartment();
    getclickedEvent();
    testSamaritter();

    //*** Functions*****
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
                            if(self.clickedEvent.watchFunctions == null){
                                self.clickedEvent.watchFunctions = [];
                            }
                            if(self.clickedEvent.watchFunctions.length !== 0){
                                angular.forEach(self.clickedEvent.watchFunctions, function(value,key){
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
            if(self.clickedEvent.watchFunctions.length === 0){
                newFunctionForWatch.id=1;
            }
            else{
                newFunctionForWatch.id=self.clickedEvent.watchFunctions.length+1;
            }
            newFunctionForWatch.samaritUserName = "Ikke Besat";
            self.clickedEvent.watchFunctions.push(newFunctionForWatch);
            self.selected = null;
        }
    }

    function deleteFunctionFromWatch(func) {
        self.clickedEvent.watchFunctions.remove(func);
    }
    
    function samaritSelectedForFunction(idofFunction, index){
       var selected = self.selectedSamaritForFunction[index];
       mapSamaritToFunction(selected, idofFunction);
    }
    
    function testSamaritter(){
         var obj = new Object();
         obj.userName = "TestUser VagtLeder&Chaffør";
         obj.watchFunctions = [];
         var functionObj = new Object();
         functionObj.id = 1;
         functionObj = "VagtLeder";
         obj.watchFunctions.push(functionObj);
         var functionObj2 = new Object();
         functionObj2.id = 2;
         functionObj2 = "Chaffør med Trailer";
         obj.watchFunctions.push(functionObj2);
         self.samaritter.push(obj);
         
         var ge = new Object();
         ge.userName = "TestUser Chaffør";
         ge.watchFunctions = [];
         var functionObj2 = new Object();
         functionObj2.id = 1;
         functionObj2 = "Chaffør med Trailer";
         ge.watchFunctions.push(functionObj2);
         self.samaritter.push(ge);
 
    }
    
    function mapSamaritToFunction(selected, idofFunction){
        angular.forEach(self.clickedEvent.watchFunctions, function (value, key) {
            if(value.id === idofFunction){
                self.clickedEvent.watchFunctions[key].samaritUserName = selected.userName;
            }
        });
    }
    
    function sendWatchFunctions(){
        
         pendingFactory.postNewWatchFunctions(self.clickedEvent)
                .then(
                        function successCallback(res) {
                            console.log("Succes Callback");
                            $location.path("/watchflow");
                        }, function errorCallBack(errorResponse) {
                    console.log("Error in callback: " + errorResponse.data.error.code);
                });
    }
    
    function mapToStart(value){
        var obj = new Object();
        obj.userName = value.samaritUserName;
        obj.functionName = value.functionName;        
        self.excistingFunctions.push(obj);
    }
    
}