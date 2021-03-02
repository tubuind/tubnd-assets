(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cif-device', {
            parent: 'entity',
            url: '/cif-device?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.cifDevice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cif-device/cif-devices.html',
                    controller: 'CifDeviceController',
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
                    $translatePartialLoader.addPart('cifDevice');
                    $translatePartialLoader.addPart('enumStatus');
                    $translatePartialLoader.addPart('enumActionType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cif-device-detail', {
            parent: 'cif-device',
            url: '/cif-device/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.cifDevice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cif-device/cif-device-detail.html',
                    controller: 'CifDeviceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cifDevice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CifDevice', function($stateParams, CifDevice) {
                    return CifDevice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cif-device',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cif-device-detail.edit', {
            parent: 'cif-device-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-device/cif-device-dialog.html',
                    controller: 'CifDeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['CifDevice', function(CifDevice) {
                            return CifDevice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cif-device.new', {
            parent: 'cif-device',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-device/cif-device-dialog.html',
                    controller: 'CifDeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                actiontype: null,
                                startdate: null,
                                enddate: null,
                                isdel: null,
                                createby: null,
                                createdate: null,
                                lastmodifyby: null,
                                lastmodifydate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cif-device', null, { reload: 'cif-device' });
                }, function() {
                    $state.go('cif-device');
                });
            }]
        })
        .state('cif-device.edit', {
            parent: 'cif-device',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-device/cif-device-dialog.html',
                    controller: 'CifDeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['CifDevice', function(CifDevice) {
                            return CifDevice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cif-device', null, { reload: 'cif-device' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cif-device.delete', {
            parent: 'cif-device',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-device/cif-device-delete-dialog.html',
                    controller: 'CifDeviceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CifDevice', function(CifDevice) {
                            return CifDevice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cif-device', null, { reload: 'cif-device' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
