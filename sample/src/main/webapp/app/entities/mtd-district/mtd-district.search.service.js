(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('MtdDistrictSearch', MtdDistrictSearch);

    MtdDistrictSearch.$inject = ['$resource'];

    function MtdDistrictSearch($resource) {
        var resourceUrl =  'api/_search/mtd-districts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
