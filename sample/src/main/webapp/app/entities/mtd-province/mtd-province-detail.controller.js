(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdProvinceDetailController', MtdProvinceDetailController);

    MtdProvinceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MtdProvince', 'MtdCountry', 'MtdDistrict'];

    function MtdProvinceDetailController($scope, $rootScope, $stateParams, previousState, entity, MtdProvince, MtdCountry, MtdDistrict) {
        var vm = this;

        vm.mtdProvince = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:mtdProvinceUpdate', function(event, result) {
            vm.mtdProvince = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
