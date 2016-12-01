angular.module('myApp.watchflow')
        .controller('TEMPfunctionCTRL', TEMPfunctionCTRL);

TEMPfunctionCTRL.$inject = ['pendingFactory', '$location', '$routeParams', ];


function TEMPfunctionCTRL(pendingFactory, $location, $routeParams) {


    //**Bindable Variables***
    var self = this;
    self.hello = "Hello World!";
    self.params = $routeParams.param;
    self.functionsforDepartment = [];
    self.clickedEvent = {};
    self.samaritter = [];
    self.selectedSamForFunction = [];

    ///***Function Calls****
    self.getclickedEvent = getclickedEvent;
    self.getFunctionsForDepartment = getFunctionsForDepartment;
    self.moveFunction = moveFunction;
    self.movefuncBack = movefuncBack;
    self.testSamaritter = testSamaritter;
    self.samaritSelectedForFunction = samaritSelectedForFunction;

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
                        }, function errorCallBack(errorResponse) {
                    console.log("Error in callback: " + errorResponse.data.error.code);
                });
    }
    ;

    function moveFunction() {
        var selected = self.selected; // -- Variable that is created by selecting
        if (selected != null) {
            var functionString = selected.functionName;
            self.clickedEvent.watchFunctions.push(functionString);
            self.selected = null;
        }
    }

    function movefuncBack(func) {
        self.clickedEvent.watchFunctions.remove(func);
    }
    
    function samaritSelectedForFunction(index){
       var selected = self.selectedSamForFunction[index];
       self.clickedEvent.samaritter.remove(selected);
    }
    
    function testSamaritter(){
         var obj = new Object();
         obj.userName = "TestUser VagtLeder";
         obj.watchFunctions = [];
         var functionObj = new Object();
         functionObj.id = 1;
         functionObj = "VagtLeder";
         obj.watchFunctions.push(functionObj);
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

}