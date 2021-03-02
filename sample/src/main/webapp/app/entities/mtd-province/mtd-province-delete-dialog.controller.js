(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdProvinceDeleteController',MtdProvinceDeleteController);

    MtdProvinceDeleteController.$inject = ['$uibModalInstance', 'entity', 'MtdProvince'];

    function MtdProvinceDeleteController($uibModalInstance, entity, MtdProvince) {
        var vm = this;

        vm.mtdProvince = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MtdProvince.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
