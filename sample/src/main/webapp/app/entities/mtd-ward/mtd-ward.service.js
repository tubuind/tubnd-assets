(function() {
    'use strict';
    angular
        .module('astcoreApp')
        .factory('MtdWard', MtdWard);

    MtdWard.$inject = ['$resource', 'DateUtils'];

    function MtdWard ($resource, DateUtils) {
        var resourceUrl =  'api/mtd-wards/:pathMethod/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
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
                    copy.createdate = DateUtils.convertLocalDateToServer(copy.createdate);
                    copy.lastmodifydate = DateUtils.convertLocalDateToServer(copy.lastmodifydate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createdate = DateUtils.convertLocalDateToServer(copy.createdate);
                    copy.lastmodifydate = DateUtils.convertLocalDateToServer(copy.lastmodifydate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
