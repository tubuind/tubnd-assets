(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdDistrictDetailController', MtdDistrictDetailController);

    MtdDistrictDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MtdDistrict', 'MtdProvince', 'MtdWard'];

    function MtdDistrictDetailController($scope, $rootScope, $stateParams, previousState, entity, MtdDistrict, MtdProvince, MtdWard) {
        var vm = this;

        vm.mtdDistrict = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:mtdDistrictUpdate', function(event, result) {
            vm.mtdDistrict = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
