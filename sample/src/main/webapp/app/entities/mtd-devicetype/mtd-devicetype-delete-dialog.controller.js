(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdDevicetypeDeleteController',MtdDevicetypeDeleteController);

    MtdDevicetypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'MtdDevicetype'];

    function MtdDevicetypeDeleteController($uibModalInstance, entity, MtdDevicetype) {
        var vm = this;

        vm.mtdDevicetype = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MtdDevicetype.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
