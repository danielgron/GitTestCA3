angular.module('myApp.watchflow')
        .filter('samaritlevel', function () {
            return function (input, param) {
                var filtered = [];
                angular.forEach(input, function (value, key) {
                    angular.forEach(value.redCrossLevel, function (value1, key) {
                        if (param === value1.level) {
                            filtered.push(value);
                        } 
                    });
                });

                
                return filtered;
            };
        });