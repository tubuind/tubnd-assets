(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifDeviceDialogController', CifDeviceDialogController);

    CifDeviceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CifDevice', 'CifMaster', 'DeviceInfo'];

    function CifDeviceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CifDevice, CifMaster, DeviceInfo) {
        var vm = this;

        vm.cifDevice = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cifmasters = CifMaster.query();
        vm.deviceinfos = DeviceInfo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cifDevice.id !== null) {
                CifDevice.update(vm.cifDevice, onSaveSuccess, onSaveError);
            } else {
                CifDevice.save(vm.cifDevice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:cifDeviceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startdate = false;
        vm.datePickerOpenStatus.enddate = false;
        vm.datePickerOpenStatus.createdate = false;
        vm.datePickerOpenStatus.lastmodifydate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
