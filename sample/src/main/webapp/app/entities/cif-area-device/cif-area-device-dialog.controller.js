(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifAreaDeviceDialogController', CifAreaDeviceDialogController);

    CifAreaDeviceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'CifAreaDevice', 'CifArea', 'CifDevice'];

    function CifAreaDeviceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, CifAreaDevice, CifArea, CifDevice) {
        var vm = this;

        vm.cifAreaDevice = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cifareas = CifArea.query();
        vm.cifdevices = CifDevice.query({filter: 'cifareadevice-is-null'});
        $q.all([vm.cifAreaDevice.$promise, vm.cifdevices.$promise]).then(function() {
            if (!vm.cifAreaDevice.cifDeviceId) {
                return $q.reject();
            }
            return CifDevice.get({id : vm.cifAreaDevice.cifDeviceId}).$promise;
        }).then(function(cifDevice) {
            vm.cifdevices.push(cifDevice);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cifAreaDevice.id !== null) {
                CifAreaDevice.update(vm.cifAreaDevice, onSaveSuccess, onSaveError);
            } else {
                CifAreaDevice.save(vm.cifAreaDevice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('astcoreApp:cifAreaDeviceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startdate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
