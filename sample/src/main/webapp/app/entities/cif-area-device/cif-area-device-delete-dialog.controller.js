(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifAreaDeviceDeleteController',CifAreaDeviceDeleteController);

    CifAreaDeviceDeleteController.$inject = ['$uibModalInstance', 'entity', 'CifAreaDevice'];

    function CifAreaDeviceDeleteController($uibModalInstance, entity, CifAreaDevice) {
        var vm = this;

        vm.cifAreaDevice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CifAreaDevice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
