(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdCustomergroupDeleteController',MtdCustomergroupDeleteController);

    MtdCustomergroupDeleteController.$inject = ['$uibModalInstance', 'entity', 'MtdCustomergroup'];

    function MtdCustomergroupDeleteController($uibModalInstance, entity, MtdCustomergroup) {
        var vm = this;

        vm.mtdCustomergroup = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MtdCustomergroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
