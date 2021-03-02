(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdEcosectorsDetailController', MtdEcosectorsDetailController);

    MtdEcosectorsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MtdEcosectors', 'CifMaster'];

    function MtdEcosectorsDetailController($scope, $rootScope, $stateParams, previousState, entity, MtdEcosectors, CifMaster) {
        var vm = this;

        vm.mtdEcosectors = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:mtdEcosectorsUpdate', function(event, result) {
            vm.mtdEcosectors = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
