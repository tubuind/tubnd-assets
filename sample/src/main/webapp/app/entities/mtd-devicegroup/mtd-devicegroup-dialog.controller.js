(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdDevicegroupDialogController', MtdDevicegroupDialogController);

    MtdDevicegroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MtdDevicegroup', 'DeviceInfo', 'MtdUnit'];

    function MtdDevicegroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MtdDevicegroup, DeviceInfo, MtdUnit) {
        var vm = this;

        vm.mtdDevicegroup = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.deviceinfos = DeviceInfo.query();
        vm.mtdunits = MtdUnit.query();

        if (vm.mtdDevicegroup.active === null) {
            vm.mtdDevicegroup.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mtdDevicegroup.id !== null) {
                MtdDevicegroup.update(vm.mtdDevicegroup, onSaveSuccess, onSaveError);
            } else {
                MtdDevicegroup.save(vm.mtdDevicegroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:mtdDevicegroupUpdate', result);
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
