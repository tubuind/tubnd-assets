(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdDevicetypeDialogController', MtdDevicetypeDialogController);

    MtdDevicetypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MtdDevicetype', 'DeviceInfo'];

    function MtdDevicetypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MtdDevicetype, DeviceInfo) {
        var vm = this;

        vm.mtdDevicetype = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.deviceinfos = DeviceInfo.query();

        if (vm.mtdDevicetype.active === null) {
            vm.mtdDevicetype.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mtdDevicetype.id !== null) {
                MtdDevicetype.update(vm.mtdDevicetype, onSaveSuccess, onSaveError);
            } else {
                MtdDevicetype.save(vm.mtdDevicetype, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:mtdDevicetypeUpdate', result);
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
