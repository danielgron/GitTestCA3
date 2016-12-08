angular.module('myApp.watchflow')
        .controller('PendingController', pendingcontroller);

pendingcontroller.$inject = ['pendingFactory', '$location'];


function pendingcontroller(pendingFactory, $location) {


    //**Bindable Variables****
    var self = this;
    self.shifts = [];
    self.searchText;
    self.sortType = "name";
    self.sortReverse  = false;



    ///***Function Calls****
    self.getShifts = getShifts;
    self.go = go;

    //** Exceute on Enter *****
    getShifts();


    //*** Functions*****
    function getShifts() {
        pendingFactory.loadEvents()
                .then(
                        function successCallback(res) {
                            self.shifts = res.data;
                        }, function errorCallBack(error) {
                    console.log("Error in callback: " + error.data.error.code);
                });
    }

    function go(path) {
        window.console.log(path);
        //pendingFactory.setShift(shift);
        $location.path(path);
    };


}
;


