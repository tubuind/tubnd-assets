(function() {
    'use strict';
    angular
        .module('astcoreApp')
        .factory('CifDevice', CifDevice);

    CifDevice.$inject = ['$resource', 'DateUtils'];

    function CifDevice ($resource, DateUtils) {
        var resourceUrl =  'api/cif-devices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startdate = DateUtils.convertLocalDateFromServer(data.startdate);
                        data.enddate = DateUtils.convertLocalDateFromServer(data.enddate);
                        data.createdate = DateUtils.convertLocalDateFromServer(data.createdate);
                        data.lastmodifydate = DateUtils.convertLocalDateFromServer(data.lastmodifydate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startdate = DateUtils.convertLocalDateToServer(copy.startdate);
                    copy.enddate = DateUtils.convertLocalDateToServer(copy.enddate);
                    copy.createdate = DateUtils.convertLocalDateToServer(copy.createdate);
                    copy.lastmodifydate = DateUtils.convertLocalDateToServer(copy.lastmodifydate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startdate = DateUtils.convertLocalDateToServer(copy.startdate);
                    copy.enddate = DateUtils.convertLocalDateToServer(copy.enddate);
                    copy.createdate = DateUtils.convertLocalDateToServer(copy.createdate);
                    copy.lastmodifydate = DateUtils.convertLocalDateToServer(copy.lastmodifydate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
