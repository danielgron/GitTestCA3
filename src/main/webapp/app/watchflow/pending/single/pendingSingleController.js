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

    ///***Function Calls****
    self.getEvent = getEvent;
    self.getSamarits = getSamarits;
    self.add = add;


    //** Exceute on Enter *****
    getEvent(self.id);
    getSamarits(self.id);


    //*** Functions*****
    function getEvent(id) {

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

    function add(samarit) {
        window.console.log(samarit);
        self.samaritOnWatch.push(samarit);

        self.samarits.forEach(function (item, index) {
            window.console.log(item);
            if (item !== null) {


                if (samarit.userName==='test') {
                    self.samarits.splice(index, 1);
                }
            }
        });

    }

}