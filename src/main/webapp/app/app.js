'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'ngAnimate',
    'angular-jwt',
    'ui.bootstrap',
    'myApp.security',
   
    
    'myApp.filters',
    'myApp.directives',
    'myApp.factories',
    'myApp.services',
    'myApp.calendar',
    'myApp.coordinator',
    'myApp.event',
    'myApp.functions',
    'myApp.resources',
    'myApp.usercalendar',
    'myApp.singleevent',
    'angularMoment',
    'myApp.watchflow'
])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.otherwise({redirectTo: '/view1'});
            }])
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('AuthInterceptor');
        });


