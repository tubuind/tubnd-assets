(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdOrganizationDetailController', MtdOrganizationDetailController);

    MtdOrganizationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MtdOrganization', 'CifMaster'];

    function MtdOrganizationDetailController($scope, $rootScope, $stateParams, previousState, entity, MtdOrganization, CifMaster) {
        var vm = this;

        vm.mtdOrganization = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:mtdOrganizationUpdate', function(event, result) {
            vm.mtdOrganization = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
