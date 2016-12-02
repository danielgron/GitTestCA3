angular.module('myApp.watchflow')
        .filter('functionfilter', function () {
            return function (input, param) {
                var filtered = [];
                angular.forEach(input, function (value, key) {
                    angular.forEach(value.watchFunctions, function(watchfunctionUserHas){
                        window.console.log(watchfunctionUserHas);
                       if(watchfunctionUserHas.functionName === param){
                           filtered.push(value);
                       }
                    });
                    });
                

                
                return filtered;
            };
        });