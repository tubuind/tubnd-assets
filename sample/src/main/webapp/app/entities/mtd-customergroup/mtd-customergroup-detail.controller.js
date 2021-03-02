(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdCustomergroupDetailController', MtdCustomergroupDetailController);

    MtdCustomergroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MtdCustomergroup', 'CifMaster'];

    function MtdCustomergroupDetailController($scope, $rootScope, $stateParams, previousState, entity, MtdCustomergroup, CifMaster) {
        var vm = this;

        vm.mtdCustomergroup = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:mtdCustomergroupUpdate', function(event, result) {
            vm.mtdCustomergroup = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
