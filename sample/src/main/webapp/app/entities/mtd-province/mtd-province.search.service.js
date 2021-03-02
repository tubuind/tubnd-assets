(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('MtdProvinceSearch', MtdProvinceSearch);

    MtdProvinceSearch.$inject = ['$resource'];

    function MtdProvinceSearch($resource) {
        var resourceUrl =  'api/_search/mtd-provinces/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
