(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdWardDeleteController',MtdWardDeleteController);

    MtdWardDeleteController.$inject = ['$uibModalInstance', 'entity', 'MtdWard'];

    function MtdWardDeleteController($uibModalInstance, entity, MtdWard) {
        var vm = this;

        vm.mtdWard = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MtdWard.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
