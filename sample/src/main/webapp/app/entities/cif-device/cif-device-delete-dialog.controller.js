(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifDeviceDeleteController',CifDeviceDeleteController);

    CifDeviceDeleteController.$inject = ['$uibModalInstance', 'entity', 'CifDevice'];

    function CifDeviceDeleteController($uibModalInstance, entity, CifDevice) {
        var vm = this;

        vm.cifDevice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CifDevice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
