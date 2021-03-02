(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifMasterDetailController', CifMasterDetailController);

    CifMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CifMaster', 'MtdWard', 'MtdOrganization', 'MtdEcosectors', 'MtdCustomergroup', 'CifDevice'];

    function CifMasterDetailController($scope, $rootScope, $stateParams, previousState, entity, CifMaster, MtdWard, MtdOrganization, MtdEcosectors, MtdCustomergroup, CifDevice) {
        var vm = this;

        vm.cifMaster = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:cifMasterUpdate', function(event, result) {
            vm.cifMaster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
