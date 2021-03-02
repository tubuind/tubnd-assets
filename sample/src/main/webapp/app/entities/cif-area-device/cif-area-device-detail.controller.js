(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifAreaDeviceDetailController', CifAreaDeviceDetailController);

    CifAreaDeviceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CifAreaDevice', 'CifArea', 'CifDevice'];

    function CifAreaDeviceDetailController($scope, $rootScope, $stateParams, previousState, entity, CifAreaDevice, CifArea, CifDevice) {
        var vm = this;

        vm.cifAreaDevice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:cifAreaDeviceUpdate', function(event, result) {
            vm.cifAreaDevice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
