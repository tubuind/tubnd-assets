(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifMasterDialogController', CifMasterDialogController);

    CifMasterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CifMaster', 'MtdWard', 'MtdOrganization', 'MtdEcosectors', 'MtdCustomergroup', 'CifDevice'];

    function CifMasterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CifMaster, MtdWard, MtdOrganization, MtdEcosectors, MtdCustomergroup, CifDevice) {
        var vm = this;

        vm.cifMaster = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.lstCust = CifMaster.query();

        vm.mtdwards = MtdWard.query();
        vm.mtdorganizations = MtdOrganization.query();
        vm.mtdecosectors = MtdEcosectors.query();
        vm.mtdcustomergroups = MtdCustomergroup.query();
        vm.cifdevices = CifDevice.query();

        if (vm.cifMaster.active === null) {
            vm.cifMaster.active = 1;
        }

        if (vm.cifMaster.customertype === null) {
            vm.cifMaster.customertype = "KH";
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cifMaster.id !== null) {
                CifMaster.update(vm.cifMaster, onSaveSuccess, onSaveError);
            } else {
                CifMaster.save(vm.cifMaster, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:cifMasterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.birthday = false;
        vm.datePickerOpenStatus.identifydate = false;
        vm.datePickerOpenStatus.createdate = false;
        vm.datePickerOpenStatus.lastmodifydate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
