(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .directive('numberConverter', numberConverter);

    numberConverter.$inject = ['$translate', '$locale', 'tmhDynamicLocale', 'Principal'];

    function numberConverter($translate, $locale, tmhDynamicLocale, Principal) {
        var directive = {
            priority: 1,
            restrict: 'A',
            require: 'ngModel',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function(val) {
                return val != null ? parseInt(val, 10) : null;
            });
            ngModel.$formatters.push(function(val) {
                return val != null ? '' + val : null;
            });
        }
    }
})();
