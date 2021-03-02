(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('CifAreaDeviceSearch', CifAreaDeviceSearch);

    CifAreaDeviceSearch.$inject = ['$resource'];

    function CifAreaDeviceSearch($resource) {
        var resourceUrl =  'api/_search/cif-area-devices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
