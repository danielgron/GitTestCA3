angular.module('myApp.calendar').factory('calendarFactory', function ($http) {
    var calendarFactory = {};
    var urlBase = "api/event";
    var clickedEventid = 0;

    var getEvents = function () {
            return $http.get(urlBase);
        };

     var getClickedEventid = function () {
            return clickedEventid;
        };

       var setClickedEventId = function (id) {
            clickedEventid = id;
        };
        
    return {
        getEvents,
        getClickedEventid,
        setClickedEventId
    };
});