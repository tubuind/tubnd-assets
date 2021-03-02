(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('MtdCountrySearch', MtdCountrySearch);

    MtdCountrySearch.$inject = ['$resource'];

    function MtdCountrySearch($resource) {
        var resourceUrl =  'api/_search/mtd-countries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
