(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('DeviceInfoSearch', DeviceInfoSearch);

    DeviceInfoSearch.$inject = ['$resource'];

    function DeviceInfoSearch($resource) {
        var resourceUrl =  'api/_search/device-infos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
