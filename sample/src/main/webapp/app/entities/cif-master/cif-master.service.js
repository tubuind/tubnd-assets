(function() {
    'use strict';
    angular
        .module('astcoreApp')
        .factory('CifMaster', CifMaster);

    CifMaster.$inject = ['$resource', 'DateUtils'];

    function CifMaster ($resource, DateUtils) {
        var resourceUrl =  'api/cif-masters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.birthday = DateUtils.convertLocalDateFromServer(data.birthday);
                        data.identifydate = DateUtils.convertLocalDateFromServer(data.identifydate);
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
                    copy.birthday = DateUtils.convertLocalDateToServer(copy.birthday);
                    copy.identifydate = DateUtils.convertLocalDateToServer(copy.identifydate);
                    copy.createdate = DateUtils.convertLocalDateToServer(copy.createdate);
                    copy.lastmodifydate = DateUtils.convertLocalDateToServer(copy.lastmodifydate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.birthday = DateUtils.convertLocalDateToServer(copy.birthday);
                    copy.identifydate = DateUtils.convertLocalDateToServer(copy.identifydate);
                    copy.createdate = DateUtils.convertLocalDateToServer(copy.createdate);
                    copy.lastmodifydate = DateUtils.convertLocalDateToServer(copy.lastmodifydate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
