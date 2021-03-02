(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdWardDialogController', MtdWardDialogController);

    MtdWardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MtdWard', 'MtdDistrict', 'CifMaster'];

    function MtdWardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MtdWard, MtdDistrict, CifMaster) {
        var vm = this;

        vm.mtdWard = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.mtddistricts = MtdDistrict.query();
        vm.cifmasters = CifMaster.query();

        if (vm.mtdWard.active === null) {
            vm.mtdWard.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mtdWard.id !== null) {
                MtdWard.update(vm.mtdWard, onSaveSuccess, onSaveError);
            } else {
                MtdWard.save(vm.mtdWard, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:mtdWardUpdate', result);
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
