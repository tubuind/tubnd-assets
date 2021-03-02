(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('DeviceTransactionDialogController', DeviceTransactionDialogController);

    DeviceTransactionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DeviceTransaction', 'DeviceInfo'];

    function DeviceTransactionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DeviceTransaction, DeviceInfo) {
        var vm = this;

        vm.deviceTransaction = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.deviceinfos = DeviceInfo.query();

        if (vm.deviceTransaction.active === null) {
            vm.deviceTransaction.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.deviceTransaction.id !== null) {
                DeviceTransaction.update(vm.deviceTransaction, onSaveSuccess, onSaveError);
            } else {
                DeviceTransaction.save(vm.deviceTransaction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:deviceTransactionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.transdate = false;
        vm.datePickerOpenStatus.valuedate = false;
        vm.datePickerOpenStatus.createdate = false;
        vm.datePickerOpenStatus.lastmodifydate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
