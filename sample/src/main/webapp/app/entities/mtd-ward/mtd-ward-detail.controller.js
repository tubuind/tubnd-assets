(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdWardDetailController', MtdWardDetailController);

    MtdWardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MtdWard', 'MtdDistrict', 'CifMaster'];

    function MtdWardDetailController($scope, $rootScope, $stateParams, previousState, entity, MtdWard, MtdDistrict, CifMaster) {
        var vm = this;

        vm.mtdWard = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:mtdWardUpdate', function(event, result) {
            vm.mtdWard = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
