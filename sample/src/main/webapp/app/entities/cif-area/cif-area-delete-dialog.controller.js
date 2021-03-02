(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifAreaDeleteController',CifAreaDeleteController);

    CifAreaDeleteController.$inject = ['$uibModalInstance', 'entity', 'CifArea'];

    function CifAreaDeleteController($uibModalInstance, entity, CifArea) {
        var vm = this;

        vm.cifArea = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CifArea.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
