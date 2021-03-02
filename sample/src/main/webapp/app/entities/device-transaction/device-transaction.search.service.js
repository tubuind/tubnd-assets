(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('DeviceTransactionSearch', DeviceTransactionSearch);

    DeviceTransactionSearch.$inject = ['$resource'];

    function DeviceTransactionSearch($resource) {
        var resourceUrl =  'api/_search/device-transactions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
