(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdDevicegroupDeleteController',MtdDevicegroupDeleteController);

    MtdDevicegroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'MtdDevicegroup'];

    function MtdDevicegroupDeleteController($uibModalInstance, entity, MtdDevicegroup) {
        var vm = this;

        vm.mtdDevicegroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MtdDevicegroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
