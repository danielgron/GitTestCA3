(function () {
    'use strict';

    angular.module('redCrossApp', [
        // Angular modules
        'ui.bootstrap',
        'ui.bootstrap.datetimepicker',
        'angularValidator',
        //'ngRoute'

        // Custom modules 

        // 3rd Party Modules

    ]).config(['$httpProvider', function ($httpProvider) {
        //Enable cross domain calls
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
    }]);
})();