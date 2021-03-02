(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('CifDeviceSearch', CifDeviceSearch);

    CifDeviceSearch.$inject = ['$resource'];

    function CifDeviceSearch($resource) {
        var resourceUrl =  'api/_search/cif-devices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
