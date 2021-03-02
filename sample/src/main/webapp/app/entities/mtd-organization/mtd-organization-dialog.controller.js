(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdOrganizationDialogController', MtdOrganizationDialogController);

    MtdOrganizationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MtdOrganization', 'CifMaster', 'MtdProvince', 'MtdDistrict', 'MtdWard'];

    function MtdOrganizationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MtdOrganization, CifMaster, MtdProvince, MtdDistrict, MtdWard) {
        var vm = this;

        vm.mtdOrganization = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        vm.onChangeProvince = onChangeProvince;
        vm.onChangeDistrict = onChangeDistrict;

        vm.cifmasters = CifMaster.query();
        vm.lstOrg = MtdOrganization.query();

        vm.lstProvince = MtdProvince.query();
        // vm.lstDistrict = MtdDistrict.query();
        // vm.lstWard = MtdWard.query();

        vm.mtdOrganizationTmp = {};

        if (vm.mtdOrganization.active === null) {
            vm.mtdOrganization.active = 1;
        }

        if (vm.mtdOrganization.mtdProvinceId) {
            vm.mtdOrganizationTmp.province = vm.mtdOrganization.mtdProvinceId;
        }

        if (vm.mtdOrganization.mtdDistrictId) {
            vm.mtdOrganizationTmp.district = vm.mtdOrganization.mtdDistrictId;
            onChangeProvince();
        }

        if (vm.mtdOrganization.mtdWardId) {
            onChangeDistrict();
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mtdOrganization.id !== null) {
                MtdOrganization.update(vm.mtdOrganization, onSaveSuccess, onSaveError);
            } else {
                MtdOrganization.save(vm.mtdOrganization, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:mtdOrganizationUpdate', result);
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

        function onChangeProvince() {
            vm.lstDistrict = MtdDistrict.query({pathMethod: 'get-by-province-id', id: vm.mtdOrganizationTmp.province});
        }

        function onChangeDistrict() {
            vm.lstWard = MtdWard.query({pathMethod: 'get-by-district-id', id: vm.mtdOrganizationTmp.district});
        }
    }
})();
