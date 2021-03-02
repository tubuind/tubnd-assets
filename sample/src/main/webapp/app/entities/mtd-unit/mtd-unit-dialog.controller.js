(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdUnitDialogController', MtdUnitDialogController);

    MtdUnitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MtdUnit', 'MtdDevicegroup'];

    function MtdUnitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MtdUnit, MtdDevicegroup) {
        var vm = this;

        vm.mtdUnit = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.mtddevicegroups = MtdDevicegroup.query();

        if (vm.mtdUnit.active === null) {
            vm.mtdUnit.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mtdUnit.id !== null) {
                MtdUnit.update(vm.mtdUnit, onSaveSuccess, onSaveError);
            } else {
                MtdUnit.save(vm.mtdUnit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:mtdUnitUpdate', result);
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
