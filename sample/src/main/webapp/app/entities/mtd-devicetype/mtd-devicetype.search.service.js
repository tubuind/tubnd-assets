(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .factory('MtdDevicetypeSearch', MtdDevicetypeSearch);

    MtdDevicetypeSearch.$inject = ['$resource'];

    function MtdDevicetypeSearch($resource) {
        var resourceUrl =  'api/_search/mtd-devicetypes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
