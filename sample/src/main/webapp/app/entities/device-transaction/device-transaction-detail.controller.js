(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('DeviceTransactionDetailController', DeviceTransactionDetailController);

    DeviceTransactionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DeviceTransaction', 'DeviceInfo'];

    function DeviceTransactionDetailController($scope, $rootScope, $stateParams, previousState, entity, DeviceTransaction, DeviceInfo) {
        var vm = this;

        vm.deviceTransaction = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:deviceTransactionUpdate', function(event, result) {
            vm.deviceTransaction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
