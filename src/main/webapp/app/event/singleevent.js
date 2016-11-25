angular.module('myApp.singleevent', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/singleevent', {
                    templateUrl: "app/event/template/singleeventview.html"

                });
            }])

        .controller('SingleEvtCtrl', ['$scope', '$locale', 'calendarFactory', 'bsLoadingOverlayService', function ($scope, $locale, calendarFactory, bsLoadingOverlayService) {
                var self = this;
                self.eventid = calendarFactory.getClickedEventid();
                self.event;
                getClickedEvent();
                self.avaliableSam = [];
                getAvalibleSam();

                function getClickedEvent() {
                    calendarFactory.getSingleEvent(self.eventid)
                            .then(function (response) {
                                self.event = response.data;
                            }), function (error) {
                        console.log("Error" + error);
                    };
                }

                function getAvalibleSam() {
                    bsLoadingOverlayService.start({referenceId: "samarit"});
                    calendarFactory.getAvaliableSamaritsForEvent(self.eventid)
                            .then(function (response) {
                                self.avaliableSam = response.data;
                                if (self.avaliableSam.length === 0) {
                                    self.avaliableSam.push({userName: "Non Avaliable"});
                                }
                                bsLoadingOverlayService.stop({referenceId: "samarit"});

                            }), function (error) {
                        bsLoadingOverlayService.stop({referenceId: "samarit"});

                        console.log("Error" + error);
                    };


                }
                ;


            }]);


