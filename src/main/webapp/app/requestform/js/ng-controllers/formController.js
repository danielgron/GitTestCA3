(function () {
    'use strict';
    angular
        .module('redCrossApp')
        .controller('indexController', ['$location', 'indexService', indexController]);
    function indexController($location, indexService) {
        var vm = this;

        vm.isError = false;
        vm.isSuccess = false;
        vm.items = {};
        vm.items.contact = {};
        vm.items.invoice = {};
        vm.dateTimePickers = {
            timepickerOptions: {
                readonlyInput: true,
                showMeridian: true
            },
            datepickerOptions: {
                showWeeks: false,
                startingDay: 1,
                dateDisabled: function(data) {
                    return (data.mode === 'day' && (new Date().toDateString() == data.date.toDateString()));
                },
            },
            timePicker1: false,
            timePicker2: false,
            timePicker3: false,
            timePicker4: false,
            datePicker1: false,
        };
        //validator functions
        vm.validFourDigitZip = function (zip, checkLength) {
            if (!zip) return true;
            var zipCode = zip.toString();
            if (zipCode.length == checkLength)
                return true;
            else
                return false
        };
        vm.validateEmail = function(email) {
            var re = /\S+@\S+\.\S+/;
            return re.test(email);
        }
        vm.saveDetails = function () {
            indexService.saveData(vm.items, function (response) {
                vm.isSuccess = true;
            }, function (error) {
                     alert("Internal Error");
                    console.log(error.error.code);
                    console.log("Message: " + error.error.message);
            })
        }
    }
})();
