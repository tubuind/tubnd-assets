(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService) {
        var vm = this;

        vm.menuItems = [
            {
                title: 'global.menu.entities.dashboard',
                href: 'home',
                icon: 'fa fa-dashboard',
                id: 'dashboard-management',
                subItems: []
            },
            {
                title: 'global.menu.entities.userManagement',
                href: 'user-management',
                icon: 'fa fa-users',
                id: 'user-management',
                subItems: [
                    {
                        title: 'global.menu.entities.users',
                        href: 'user-management'
                    },
                    {
                        title: 'global.menu.entities.roles',
                        href: 'jhi-authority'
                    },
                    {
                        title: 'global.menu.entities.permissions',
                        href: 'permission'
                    }
                ]
            },
            {
                title: 'global.menu.entities.customerManagement',
                href: 'customer-management',
                icon: 'fa fa-user-circle-o',
                id: 'customer-management',
                subItems: [
                    {
                        title: 'global.menu.entities.mtdEcosectors',
                        href: 'mtd-ecosectors'
                    },
                    {
                        title: 'global.menu.entities.mtdCustomergroup',
                        href: 'mtd-customergroup'
                    },
                    {
                        title: 'global.menu.entities.cifMaster',
                        href: 'cif-master'
                    },
                    {
                        title: 'global.menu.entities.cifArea',
                        href: 'cif-area'
                    }
                ]
            },
            {
                title: 'global.menu.entities.areaManagement',
                href: 'area-management',
                icon: 'fa fa-map',
                id: 'area-management',
                subItems: [
                    {
                        title: 'global.menu.entities.mtdCountry',
                        href: 'mtd-country'
                    },
                    {
                        title: 'global.menu.entities.mtdProvince',
                        href: 'mtd-province'
                    },
                    {
                        title: 'global.menu.entities.mtdDistrict',
                        href: 'mtd-district'
                    },
                    {
                        title: 'global.menu.entities.mtdWard',
                        href: 'mtd-ward'
                    }
                ]
            },
            {
                title: 'global.menu.entities.deviceManagement',
                href: 'device-management',
                icon: 'fa fa-database',
                id: 'device-management',
                subItems: [
                    {
                        title: 'global.menu.entities.mtdUnit',
                        href: 'mtd-unit'
                    },
                    {
                        title: 'global.menu.entities.mtdDevicegroup',
                        href: 'mtd-devicegroup'
                    },
                    {
                        title: 'global.menu.entities.mtdDevicetype',
                        href: 'mtd-devicetype'
                    },
                    {
                        title: 'global.menu.entities.deviceInfo',
                        href: 'device-info'
                    },
                    {
                        title: 'global.menu.entities.cifAreaDevice',
                        href: 'cif-area-device'
                    },
                    {
                        title: 'global.menu.entities.deviceTransaction',
                        href: 'device-transaction'
                    },
                    {
                        title: 'global.menu.entities.cifDevice',
                        href: 'cif-device'
                    }
                ]
            },
            {
                title: 'global.menu.entities.operationManagement',
                href: 'operation-management',
                icon: 'fa fa-database',
                id: 'operation-management',
                subItems: [
                    {
                        title: 'global.menu.entities.mtdOrganization',
                        href: 'mtd-organization'
                    }
                ]
            },
            {
                title: 'global.menu.entities.settingManagement',
                href: 'sys-parameter',
                icon: 'fa fa-cogs',
                id: 'sys-parameter',
                subItems: []
            }
        ];

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        Principal.identity().then(function(account) {
            if (account != null) {
                vm.userName = account.login;
            }
        });

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;
        vm.includePath = includePath;

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

        function includePath(menuItems) {
            var result = false;
            //var paths = params.split(",");
            if (menuItems.length > 0) {
                menuItems.forEach(function(menuItem) {
                    if (vm.$state.includes(menuItem.href)) {
                        result = true;
                    }
                });
            }

            return result;
        }
    }
})();
