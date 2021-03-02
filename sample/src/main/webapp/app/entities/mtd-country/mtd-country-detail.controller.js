(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('MtdCountryDetailController', MtdCountryDetailController);

    MtdCountryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MtdCountry', 'MtdProvince'];

    function MtdCountryDetailController($scope, $rootScope, $stateParams, previousState, entity, MtdCountry, MtdProvince) {
        var vm = this;

        vm.mtdCountry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('astcoreApp:mtdCountryUpdate', function(event, result) {
            vm.mtdCountry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
