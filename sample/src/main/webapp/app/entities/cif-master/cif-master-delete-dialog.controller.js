(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifMasterDeleteController',CifMasterDeleteController);

    CifMasterDeleteController.$inject = ['$uibModalInstance', 'entity', 'CifMaster'];

    function CifMasterDeleteController($uibModalInstance, entity, CifMaster) {
        var vm = this;

        vm.cifMaster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CifMaster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
