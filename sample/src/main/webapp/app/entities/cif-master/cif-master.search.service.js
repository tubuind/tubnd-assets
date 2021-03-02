(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('CifMasterSearch', CifMasterSearch);

    CifMasterSearch.$inject = ['$resource'];

    function CifMasterSearch($resource) {
        var resourceUrl =  'api/_search/cif-masters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
