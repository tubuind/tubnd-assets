(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdDevicetypeDetailController', MtdDevicetypeDetailController);

    MtdDevicetypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MtdDevicetype', 'DeviceInfo'];

    function MtdDevicetypeDetailController($scope, $rootScope, $stateParams, previousState, entity, MtdDevicetype, DeviceInfo) {
        var vm = this;

        vm.mtdDevicetype = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:mtdDevicetypeUpdate', function(event, result) {
            vm.mtdDevicetype = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
