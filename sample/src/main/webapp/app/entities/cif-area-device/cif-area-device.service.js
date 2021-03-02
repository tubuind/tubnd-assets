(function() {
    'use strict';
    angular
        .module('astcoreApp')
        .factory('CifAreaDevice', CifAreaDevice);

    CifAreaDevice.$inject = ['$resource', 'DateUtils'];

    function CifAreaDevice ($resource, DateUtils) {
        var resourceUrl =  'api/cif-area-devices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startdate = DateUtils.convertLocalDateFromServer(data.startdate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startdate = DateUtils.convertLocalDateToServer(copy.startdate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startdate = DateUtils.convertLocalDateToServer(copy.startdate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
