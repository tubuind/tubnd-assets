(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cif-area-device', {
            parent: 'entity',
            url: '/cif-area-device?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.cifAreaDevice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cif-area-device/cif-area-devices.html',
                    controller: 'CifAreaDeviceController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cifAreaDevice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cif-area-device-detail', {
            parent: 'cif-area-device',
            url: '/cif-area-device/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.cifAreaDevice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cif-area-device/cif-area-device-detail.html',
                    controller: 'CifAreaDeviceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cifAreaDevice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CifAreaDevice', function($stateParams, CifAreaDevice) {
                    return CifAreaDevice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cif-area-device',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cif-area-device-detail.edit', {
            parent: 'cif-area-device-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-area-device/cif-area-device-dialog.html',
                    controller: 'CifAreaDeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CifAreaDevice', function(CifAreaDevice) {
                            return CifAreaDevice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cif-area-device.new', {
            parent: 'cif-area-device',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-area-device/cif-area-device-dialog.html',
                    controller: 'CifAreaDeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                devicecode: null,
                                startdate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cif-area-device', null, { reload: 'cif-area-device' });
                }, function() {
                    $state.go('cif-area-device');
                });
            }]
        })
        .state('cif-area-device.edit', {
            parent: 'cif-area-device',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-area-device/cif-area-device-dialog.html',
                    controller: 'CifAreaDeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CifAreaDevice', function(CifAreaDevice) {
                            return CifAreaDevice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cif-area-device', null, { reload: 'cif-area-device' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cif-area-device.delete', {
            parent: 'cif-area-device',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-area-device/cif-area-device-delete-dialog.html',
                    controller: 'CifAreaDeviceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CifAreaDevice', function(CifAreaDevice) {
                            return CifAreaDevice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cif-area-device', null, { reload: 'cif-area-device' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
