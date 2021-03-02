(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .directive('authenticateClass', authenticateClass);

    authenticateClass.$inject = ['$translate', '$locale', 'tmhDynamicLocale', 'Principal'];

    function authenticateClass($translate, $locale, tmhDynamicLocale, Principal) {
        var directive = {
            restrict: 'A',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            var authenticateType = attrs.authenticateClass;
            var isAuthenticated = Principal.isAuthenticated();

            scope.$watch(function() {

                isAuthenticated = Principal.isAuthenticated();
                if (isAuthenticated) {
                    element.addClass('authenticate-active');
                    element.removeClass('authenticate-not-active');
                } else {
                    element.addClass('authenticate-not-active');
                    element.removeClass('authenticate-active');
                }

            });
        }
    }
})();
