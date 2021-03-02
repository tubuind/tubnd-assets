(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('MtdDevicegroupSearch', MtdDevicegroupSearch);

    MtdDevicegroupSearch.$inject = ['$resource'];

    function MtdDevicegroupSearch($resource) {
        var resourceUrl =  'api/_search/mtd-devicegroups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
