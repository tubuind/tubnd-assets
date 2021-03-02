(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdCountryDeleteController',MtdCountryDeleteController);

    MtdCountryDeleteController.$inject = ['$uibModalInstance', 'entity', 'MtdCountry'];

    function MtdCountryDeleteController($uibModalInstance, entity, MtdCountry) {
        var vm = this;

        vm.mtdCountry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MtdCountry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
