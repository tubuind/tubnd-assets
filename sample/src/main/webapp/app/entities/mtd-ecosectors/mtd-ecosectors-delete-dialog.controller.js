(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdEcosectorsDeleteController',MtdEcosectorsDeleteController);

    MtdEcosectorsDeleteController.$inject = ['$uibModalInstance', 'entity', 'MtdEcosectors'];

    function MtdEcosectorsDeleteController($uibModalInstance, entity, MtdEcosectors) {
        var vm = this;

        vm.mtdEcosectors = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MtdEcosectors.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
