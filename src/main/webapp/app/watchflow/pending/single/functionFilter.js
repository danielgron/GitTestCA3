angular.module('myApp.watchflow')
        .filter('functionfilter', function () {
            return function (input, param) {
                var filtered = [];
                angular.forEach(input, function (value, key) {
                    angular.forEach(value.watchFunctions, function(watchfunctionUserHas){
                       if(watchfunctionUserHas === param){
                           filtered.push(value);
                       }
                    });
                    });
                

                
                return filtered;
            };
        });