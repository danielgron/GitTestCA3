angular.module('myApp.watchflow')
        .controller('PendingSingleCtrl', PendingSingleCtrl);

PendingSingleCtrl.$inject = ['pendingFactory', '$location', '$routeParams'];


function PendingSingleCtrl(pendingFactory, $location, $routeParams) {


    //**Bindable Variables****
    var self = this;
    self.clickedShift = {};
    self.params = $routeParams.param;
    self.shift = {};
    window.console.log(self.params);

    ///***Function Calls****
    //self.getclicked = getclicked();

    //** Exceute on Enter *****
    getEvent();


    //*** Functions*****
    function getEvent() {
        pendingFactory.getEvent(self.params).then(function(successResponse) {
                            self.shift = successResponse.data;
                            window.console.log(self.shift);
                        }, function (errorResponse) {
                    console.log("Error in callback: " + errorResponse.data.error.code);
                });
    }

}