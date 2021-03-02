(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('CifAreaSearch', CifAreaSearch);

    CifAreaSearch.$inject = ['$resource'];

    function CifAreaSearch($resource) {
        var resourceUrl =  'api/_search/cif-areas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
