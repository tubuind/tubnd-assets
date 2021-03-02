(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('MtdEcosectorsSearch', MtdEcosectorsSearch);

    MtdEcosectorsSearch.$inject = ['$resource'];

    function MtdEcosectorsSearch($resource) {
        var resourceUrl =  'api/_search/mtd-ecosectors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
