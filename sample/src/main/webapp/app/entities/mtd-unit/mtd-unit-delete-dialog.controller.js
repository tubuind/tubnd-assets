(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdUnitDeleteController',MtdUnitDeleteController);

    MtdUnitDeleteController.$inject = ['$uibModalInstance', 'entity', 'MtdUnit'];

    function MtdUnitDeleteController($uibModalInstance, entity, MtdUnit) {
        var vm = this;

        vm.mtdUnit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MtdUnit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
