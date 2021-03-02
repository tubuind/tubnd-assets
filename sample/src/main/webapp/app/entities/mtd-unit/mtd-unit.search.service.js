(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('MtdUnitSearch', MtdUnitSearch);

    MtdUnitSearch.$inject = ['$resource'];

    function MtdUnitSearch($resource) {
        var resourceUrl =  'api/_search/mtd-units/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
