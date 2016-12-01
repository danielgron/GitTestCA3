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
        var samarit1 = {};
        samarit1 = samarit;
        // window.console.log("from add: " + samarit);
        window.console.log(role);
        window.console.log(samarit1);
        if(role!==null&&samarit!=null){
            samarit1.role= role;
        }
        self.samaritOnWatch.push(samarit1);
        self.samarits.remove(samarit1);
        //self.samarits = self.samarits.remove(samarit);
    }
    
    function remove(samarit){
        window.console.log(samarit);
    }

    function save() {
        bsLoadingOverlayService.start();
        pendingFactory.saveWatches(self.samaritOnWatch.push)
                .then(function (successResponse) {
                    window.console.log(successResponse);
                }, function (errorResponse) {
                    window.console.log("Error in callback: " + errorResponse.data.error.code);

                });


    }


}