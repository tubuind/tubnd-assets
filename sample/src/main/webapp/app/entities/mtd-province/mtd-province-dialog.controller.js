(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdProvinceDialogController', MtdProvinceDialogController);

    MtdProvinceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MtdProvince', 'MtdCountry', 'MtdDistrict'];

    function MtdProvinceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MtdProvince, MtdCountry, MtdDistrict) {
        var vm = this;

        vm.mtdProvince = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.mtdcountries = MtdCountry.query();
        vm.mtddistricts = MtdDistrict.query();

        if (vm.mtdProvince.active === null) {
            vm.mtdProvince.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mtdProvince.id !== null) {
                MtdProvince.update(vm.mtdProvince, onSaveSuccess, onSaveError);
            } else {
                MtdProvince.save(vm.mtdProvince, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:mtdProvinceUpdate', result);
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
