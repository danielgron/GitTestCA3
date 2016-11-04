angular.module('myApp.calendar').factory('calendarFactory', function ($http) {
    var calendarFactory = {};
    var urlBase = "api/event";

    calendarFactory.getEvents = function () {
        return $http.get(urlBase);
    };

    return calendarFactory;
});