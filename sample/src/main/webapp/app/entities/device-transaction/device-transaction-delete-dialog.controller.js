(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('DeviceTransactionDeleteController',DeviceTransactionDeleteController);

    DeviceTransactionDeleteController.$inject = ['$uibModalInstance', 'entity', 'DeviceTransaction'];

    function DeviceTransactionDeleteController($uibModalInstance, entity, DeviceTransaction) {
        var vm = this;

        vm.deviceTransaction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DeviceTransaction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
