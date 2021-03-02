(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .directive('jhiMaxlength', jhiMaxlength);

        jhiMaxlength.$inject = ['$translate', '$locale', 'tmhDynamicLocale', 'Principal'];

    function jhiMaxlength($translate, $locale, tmhDynamicLocale, Principal) {
        var directive = {
            priority: 1,
            restrict: 'A',
            require: 'ngModel',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs, ngModel) {
            scope.$watch(function(){
                return ngModel.$viewValue;
            }, function(newValue, oldValue){
                if(newValue != null){
                    if(newValue.length > attrs.jhiMaxlength){
                        ngModel.$setViewValue(oldValue);
                        ngModel.$render();
                    }
                }
            }, true);            
          
        }
    }
})();
