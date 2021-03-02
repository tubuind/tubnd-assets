(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdDistrictDialogController', MtdDistrictDialogController);

    MtdDistrictDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MtdDistrict', 'MtdProvince', 'MtdWard'];

    function MtdDistrictDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MtdDistrict, MtdProvince, MtdWard) {
        var vm = this;

        vm.mtdDistrict = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.mtdprovinces = MtdProvince.query();
        vm.mtdwards = MtdWard.query();

        if (vm.mtdDistrict.active === null) {
            vm.mtdDistrict.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mtdDistrict.id !== null) {
                MtdDistrict.update(vm.mtdDistrict, onSaveSuccess, onSaveError);
            } else {
                MtdDistrict.save(vm.mtdDistrict, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:mtdDistrictUpdate', result);
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
