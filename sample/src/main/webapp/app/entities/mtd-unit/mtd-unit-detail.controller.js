(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdUnitDetailController', MtdUnitDetailController);

    MtdUnitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MtdUnit', 'MtdDevicegroup'];

    function MtdUnitDetailController($scope, $rootScope, $stateParams, previousState, entity, MtdUnit, MtdDevicegroup) {
        var vm = this;

        vm.mtdUnit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:mtdUnitUpdate', function(event, result) {
            vm.mtdUnit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
