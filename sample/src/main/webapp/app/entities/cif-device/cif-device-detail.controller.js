(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifDeviceDetailController', CifDeviceDetailController);

    CifDeviceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CifDevice', 'CifMaster', 'DeviceInfo'];

    function CifDeviceDetailController($scope, $rootScope, $stateParams, previousState, entity, CifDevice, CifMaster, DeviceInfo) {
        var vm = this;

        vm.cifDevice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:cifDeviceUpdate', function(event, result) {
            vm.cifDevice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
