(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdCountryDialogController', MtdCountryDialogController);

    MtdCountryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MtdCountry', 'MtdProvince'];

    function MtdCountryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MtdCountry, MtdProvince) {
        var vm = this;

        vm.mtdCountry = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.mtdprovinces = MtdProvince.query();

        if (vm.mtdCountry.active === null) {
            vm.mtdCountry.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mtdCountry.id !== null) {
                MtdCountry.update(vm.mtdCountry, onSaveSuccess, onSaveError);
            } else {
                MtdCountry.save(vm.mtdCountry, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:mtdCountryUpdate', result);
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
