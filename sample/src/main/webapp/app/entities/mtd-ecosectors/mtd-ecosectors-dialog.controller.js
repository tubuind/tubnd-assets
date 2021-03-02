(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdEcosectorsDialogController', MtdEcosectorsDialogController);

    MtdEcosectorsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MtdEcosectors', 'CifMaster'];

    function MtdEcosectorsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MtdEcosectors, CifMaster) {
        var vm = this;

        vm.mtdEcosectors = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cifmasters = CifMaster.query();

        if (vm.mtdEcosectors.active === null) {
            vm.mtdEcosectors.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mtdEcosectors.id !== null) {
                MtdEcosectors.update(vm.mtdEcosectors, onSaveSuccess, onSaveError);
            } else {
                MtdEcosectors.save(vm.mtdEcosectors, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:mtdEcosectorsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdate = false;
        vm.datePickerOpenStatus.lastmodifydate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
