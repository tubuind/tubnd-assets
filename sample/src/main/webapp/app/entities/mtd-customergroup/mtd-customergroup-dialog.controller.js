(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdCustomergroupDialogController', MtdCustomergroupDialogController);

    MtdCustomergroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MtdCustomergroup', 'CifMaster'];

    function MtdCustomergroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MtdCustomergroup, CifMaster) {
        var vm = this;

        vm.mtdCustomergroup = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cifmasters = CifMaster.query();

        if (vm.mtdCustomergroup.active === null) {
            vm.mtdCustomergroup.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mtdCustomergroup.id !== null) {
                MtdCustomergroup.update(vm.mtdCustomergroup, onSaveSuccess, onSaveError);
            } else {
                MtdCustomergroup.save(vm.mtdCustomergroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:mtdCustomergroupUpdate', result);
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
