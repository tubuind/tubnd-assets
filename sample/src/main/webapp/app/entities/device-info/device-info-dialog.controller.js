(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('DeviceInfoDialogController', DeviceInfoDialogController);

    DeviceInfoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DeviceInfo', 'MtdDevicetype', 'MtdDevicegroup', 'DeviceTransaction', 'CifDevice'];

    function DeviceInfoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DeviceInfo, MtdDevicetype, MtdDevicegroup, DeviceTransaction, CifDevice) {
        var vm = this;

        vm.deviceInfo = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.mtddevicetypes = MtdDevicetype.query();
        vm.mtddevicegroups = MtdDevicegroup.query();
        vm.devicetransactions = DeviceTransaction.query();
        vm.cifdevices = CifDevice.query();

        if (vm.deviceInfo.active === null) {
            vm.deviceInfo.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.deviceInfo.id !== null) {
                DeviceInfo.update(vm.deviceInfo, onSaveSuccess, onSaveError);
            } else {
                DeviceInfo.save(vm.deviceInfo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:deviceInfoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.makedate = false;
        vm.datePickerOpenStatus.createdate = false;
        vm.datePickerOpenStatus.lastmodifydate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
