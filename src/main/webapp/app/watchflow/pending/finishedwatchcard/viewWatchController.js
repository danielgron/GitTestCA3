angular.module('myApp.watchflow')
        .controller('viewWatchController', viewWatchController);

viewWatchController.$inject = ['pendingFactory', '$location', '$routeParams', 'bsLoadingOverlayService'];


function viewWatchController(pendingFactory, $location, $routeParams) {


    //**Bindable Variables****
    var self = this;
    self.id = $routeParams.param;
    self.shift = {};
    self.watchStartDate;
    self.watchStartTime;
    self.endDate;
    self.endTime;

    ///***Function Declarations****
    self.getEvent = getEvent;
    self.convertDateStartString = convertDateStartString;

    //** Exceute on Enter *****
    getEvent(self.id);


    //*** Functions*****
    function getEvent(id) {
        window.console.log('test getEvent');
        pendingFactory.getEvent(id).then(function (successResponse) {
            self.shift = successResponse.data;
            convertDateStartString();
        }, function (errorResponse) {
            console.log("Error in callback: " + errorResponse.data.error.code);
        });
    }
    
    function convertDateStartString(){
        var watchStart = self.shift.watchStart;
        if(watchStart != null){
            
        var startDateArray =  watchStart.split(" ");
        self.watchStartDate = startDateArray[0];
        self.watchStartTime = startDateArray[1];
        var shiftend = self.shift.end;
        var endDateArray = shiftend.split(" ");
        self.endDate = endDateArray[0];
        self.endTime = endDateArray[1];
        }
    }

}