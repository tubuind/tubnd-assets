(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('MtdCustomergroupSearch', MtdCustomergroupSearch);

    MtdCustomergroupSearch.$inject = ['$resource'];

    function MtdCustomergroupSearch($resource) {
        var resourceUrl =  'api/_search/mtd-customergroups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
