(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('MtdWardSearch', MtdWardSearch);

    MtdWardSearch.$inject = ['$resource'];

    function MtdWardSearch($resource) {
        var resourceUrl =  'api/_search/mtd-wards/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
