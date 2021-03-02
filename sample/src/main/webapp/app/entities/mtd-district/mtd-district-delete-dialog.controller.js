(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdDistrictDeleteController',MtdDistrictDeleteController);

    MtdDistrictDeleteController.$inject = ['$uibModalInstance', 'entity', 'MtdDistrict'];

    function MtdDistrictDeleteController($uibModalInstance, entity, MtdDistrict) {
        var vm = this;

        vm.mtdDistrict = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MtdDistrict.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
