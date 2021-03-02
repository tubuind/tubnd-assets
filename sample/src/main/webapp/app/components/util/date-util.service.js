(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('DateUtils', DateUtils);

    DateUtils.$inject = ['$filter'];

    function DateUtils($filter) {

        var service = {
            convertDateTimeFromServer: convertDateTimeFromServer,
            convertLocalDateFromServer: convertLocalDateFromServer,
            convertLocalDateToServer: convertLocalDateToServer,
            dateformat: dateformat
        };

        return service;

        function convertDateTimeFromServer(date) {
            if (date) {
                return new Date(date);
            } else {
                return null;
            }
        }

        function convertLocalDateFromServer(date) {
            if (date && date.length > 10) {
                var dateFull = date.substring(0, 19).split('T');
                var dateString = dateFull[0].split('-');
                var timeString = dateFull[1].split(':');

                console.log('date : ', date);

                return new Date(dateString[0], dateString[1] - 1, dateString[2], timeString[0], timeString[1], timeString[2]);
            }

            return new Date(date);

            // if (date) {
            //     var dateString = date.split('-');
            //     return new Date(dateString[0], dateString[1] - 1, dateString[2]);
            // }
            // return null;
        }

        function convertLocalDateToServer(date) {
            if (date) {
                return $filter('date')(date, 'yyyy-MM-dd');
            } else {
                return null;
            }
        }

        function dateformat() {
            return 'dd-MM-yyyy';
        }
    }

})();
