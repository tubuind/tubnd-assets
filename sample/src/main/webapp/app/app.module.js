(function() {
    'use strict';

    angular
        .module('astcoreApp', [
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            'angular-echarts',
            'ng-echarts',
            'angular-loading-bar',
            'rzModule'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler', 'DateUtils', '$rootScope'];

    function run(stateHandler, translationHandler, DateUtils, $rootScope) {
        //Set dateformat
        $rootScope.dateformat = DateUtils.dateformat();

        stateHandler.initialize();
        translationHandler.initialize();
    }
})();
