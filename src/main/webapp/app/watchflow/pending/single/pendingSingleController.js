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
    self.save = save;
    self.remove = remove;
    self.reset = reset;


    //** Exceute on Enter *****
    getEvent(self.id);
    getSamarits(self.id);


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
        pendingFactory.saveWatches(self.id,self.samaritOnWatch)
                .then(function (successResponse) {
                    window.console.log(successResponse);
             bsLoadingOverlayService.stop();
                }, function (errorResponse) {
                     bsLoadingOverlayService.stop();
                    window.console.log("Error in callback: " + errorResponse.data.error.code);

                });
    }
    function reset(){
         self.samaritOnWatch = [];
         getSamarits(self.id);
    }


}