(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifAreaDialogController', CifAreaDialogController);

    CifAreaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CifArea', 'CifMaster', 'CifAreaDevice'];

    function CifAreaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CifArea, CifMaster, CifAreaDevice) {
        var vm = this;

        vm.cifArea = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cifmasters = CifMaster.query();
        vm.cifareadevices = CifAreaDevice.query();
        vm.lstArea = CifArea.query(function(){
            if (vm.cifArea.id !== null) {
                angular.forEach(vm.lstArea, function(value, key){
                    if(value.id === vm.cifArea.id){
                        vm.lstArea.splice(key, 1);
                    }
                });
            }        
        });

        if (vm.cifArea.active === null) {
            vm.cifArea.active = 1;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cifArea.id !== null) {
                CifArea.update(vm.cifArea, onSaveSuccess, onSaveError);
            } else {
                CifArea.save(vm.cifArea, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:cifAreaUpdate', result);
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
