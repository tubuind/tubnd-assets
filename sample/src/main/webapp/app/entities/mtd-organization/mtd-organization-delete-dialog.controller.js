(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdOrganizationDeleteController',MtdOrganizationDeleteController);

    MtdOrganizationDeleteController.$inject = ['$uibModalInstance', 'entity', 'MtdOrganization'];

    function MtdOrganizationDeleteController($uibModalInstance, entity, MtdOrganization) {
        var vm = this;

        vm.mtdOrganization = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MtdOrganization.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
