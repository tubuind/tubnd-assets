(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdDevicegroupDetailController', MtdDevicegroupDetailController);

    MtdDevicegroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MtdDevicegroup', 'DeviceInfo', 'MtdUnit'];

    function MtdDevicegroupDetailController($scope, $rootScope, $stateParams, previousState, entity, MtdDevicegroup, DeviceInfo, MtdUnit) {
        var vm = this;

        vm.mtdDevicegroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:mtdDevicegroupUpdate', function(event, result) {
            vm.mtdDevicegroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
