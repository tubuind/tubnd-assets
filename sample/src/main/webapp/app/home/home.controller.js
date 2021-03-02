(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'AutoHeightUtils'];

    function HomeController ($scope, Principal, LoginService, $state, AutoHeightUtils) {
        var vm = this;

        //Auto height
        setTimeout(function() {
            AutoHeightUtils.heightUi();
        }, 1000);

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();

            window.location.reload();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
