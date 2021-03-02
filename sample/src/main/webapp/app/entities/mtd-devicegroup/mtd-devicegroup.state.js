(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mtd-devicegroup', {
            parent: 'entity',
            url: '/mtd-devicegroup?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdDevicegroup.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-devicegroup/mtd-devicegroups.html',
                    controller: 'MtdDevicegroupController',
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
                    $translatePartialLoader.addPart('mtdDevicegroup');
                    $translatePartialLoader.addPart('enumStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mtd-devicegroup-detail', {
            parent: 'mtd-devicegroup',
            url: '/mtd-devicegroup/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdDevicegroup.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-devicegroup/mtd-devicegroup-detail.html',
                    controller: 'MtdDevicegroupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mtdDevicegroup');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MtdDevicegroup', function($stateParams, MtdDevicegroup) {
                    return MtdDevicegroup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mtd-devicegroup',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mtd-devicegroup-detail.edit', {
            parent: 'mtd-devicegroup-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-devicegroup/mtd-devicegroup-dialog.html',
                    controller: 'MtdDevicegroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdDevicegroup', function(MtdDevicegroup) {
                            return MtdDevicegroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-devicegroup.new', {
            parent: 'mtd-devicegroup',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-devicegroup/mtd-devicegroup-dialog.html',
                    controller: 'MtdDevicegroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                devicegroupname: null,
                                unitcode: null,
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
                    $state.go('mtd-devicegroup', null, { reload: 'mtd-devicegroup' });
                }, function() {
                    $state.go('mtd-devicegroup');
                });
            }]
        })
        .state('mtd-devicegroup.edit', {
            parent: 'mtd-devicegroup',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-devicegroup/mtd-devicegroup-dialog.html',
                    controller: 'MtdDevicegroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdDevicegroup', function(MtdDevicegroup) {
                            return MtdDevicegroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-devicegroup', null, { reload: 'mtd-devicegroup' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-devicegroup.delete', {
            parent: 'mtd-devicegroup',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-devicegroup/mtd-devicegroup-delete-dialog.html',
                    controller: 'MtdDevicegroupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MtdDevicegroup', function(MtdDevicegroup) {
                            return MtdDevicegroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-devicegroup', null, { reload: 'mtd-devicegroup' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
