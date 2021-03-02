(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('DeviceInfoDeleteController',DeviceInfoDeleteController);

    DeviceInfoDeleteController.$inject = ['$uibModalInstance', 'entity', 'DeviceInfo'];

    function DeviceInfoDeleteController($uibModalInstance, entity, DeviceInfo) {
        var vm = this;

        vm.deviceInfo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DeviceInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
