(function() {
    'use strict';
    angular
        .module('astcoreApp')
        .factory('DeviceTransaction', DeviceTransaction);

    DeviceTransaction.$inject = ['$resource', 'DateUtils'];

    function DeviceTransaction ($resource, DateUtils) {
        var resourceUrl =  'api/device-transactions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.transdate = DateUtils.convertLocalDateFromServer(data.transdate);
                        data.valuedate = DateUtils.convertLocalDateFromServer(data.valuedate);
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
                    copy.transdate = DateUtils.convertLocalDateToServer(copy.transdate);
                    copy.valuedate = DateUtils.convertLocalDateToServer(copy.valuedate);
                    copy.createdate = DateUtils.convertLocalDateToServer(copy.createdate);
                    copy.lastmodifydate = DateUtils.convertLocalDateToServer(copy.lastmodifydate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.transdate = DateUtils.convertLocalDateToServer(copy.transdate);
                    copy.valuedate = DateUtils.convertLocalDateToServer(copy.valuedate);
                    copy.createdate = DateUtils.convertLocalDateToServer(copy.createdate);
                    copy.lastmodifydate = DateUtils.convertLocalDateToServer(copy.lastmodifydate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
