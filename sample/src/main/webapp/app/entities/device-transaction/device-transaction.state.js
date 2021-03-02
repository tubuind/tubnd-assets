(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('device-transaction', {
            parent: 'entity',
            url: '/device-transaction?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.deviceTransaction.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/device-transaction/device-transactions.html',
                    controller: 'DeviceTransactionController',
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
                    $translatePartialLoader.addPart('deviceTransaction');
                    $translatePartialLoader.addPart('enumStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('device-transaction-detail', {
            parent: 'device-transaction',
            url: '/device-transaction/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.deviceTransaction.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/device-transaction/device-transaction-detail.html',
                    controller: 'DeviceTransactionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('deviceTransaction');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DeviceTransaction', function($stateParams, DeviceTransaction) {
                    return DeviceTransaction.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'device-transaction',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('device-transaction-detail.edit', {
            parent: 'device-transaction-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-transaction/device-transaction-dialog.html',
                    controller: 'DeviceTransactionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['DeviceTransaction', function(DeviceTransaction) {
                            return DeviceTransaction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('device-transaction.new', {
            parent: 'device-transaction',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-transaction/device-transaction-dialog.html',
                    controller: 'DeviceTransactionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                transdate: null,
                                devicecode: null,
                                currentvalue: null,
                                valuedate: null,
                                active: null,
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
                    $state.go('device-transaction', null, { reload: 'device-transaction' });
                }, function() {
                    $state.go('device-transaction');
                });
            }]
        })
        .state('device-transaction.edit', {
            parent: 'device-transaction',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-transaction/device-transaction-dialog.html',
                    controller: 'DeviceTransactionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['DeviceTransaction', function(DeviceTransaction) {
                            return DeviceTransaction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('device-transaction', null, { reload: 'device-transaction' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('device-transaction.delete', {
            parent: 'device-transaction',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-transaction/device-transaction-delete-dialog.html',
                    controller: 'DeviceTransactionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DeviceTransaction', function(DeviceTransaction) {
                            return DeviceTransaction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('device-transaction', null, { reload: 'device-transaction' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
