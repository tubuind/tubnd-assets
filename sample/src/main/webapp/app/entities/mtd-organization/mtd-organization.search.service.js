(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('MtdOrganizationSearch', MtdOrganizationSearch);

    MtdOrganizationSearch.$inject = ['$resource'];

    function MtdOrganizationSearch($resource) {
        var resourceUrl =  'api/_search/mtd-organizations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
