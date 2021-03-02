(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('CifAreaDetailController', CifAreaDetailController);

    CifAreaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CifArea', 'CifMaster', 'CifAreaDevice'];

    function CifAreaDetailController($scope, $rootScope, $stateParams, previousState, entity, CifArea, CifMaster, CifAreaDevice) {
        var vm = this;

        vm.cifArea = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:cifAreaUpdate', function(event, result) {
            vm.cifArea = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
