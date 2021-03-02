(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mtd-unit', {
            parent: 'entity',
            url: '/mtd-unit?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdUnit.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-unit/mtd-units.html',
                    controller: 'MtdUnitController',
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
                    $translatePartialLoader.addPart('mtdUnit');
                    $translatePartialLoader.addPart('enumStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mtd-unit-detail', {
            parent: 'mtd-unit',
            url: '/mtd-unit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdUnit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-unit/mtd-unit-detail.html',
                    controller: 'MtdUnitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mtdUnit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MtdUnit', function($stateParams, MtdUnit) {
                    return MtdUnit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mtd-unit',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mtd-unit-detail.edit', {
            parent: 'mtd-unit-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-unit/mtd-unit-dialog.html',
                    controller: 'MtdUnitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdUnit', function(MtdUnit) {
                            return MtdUnit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-unit.new', {
            parent: 'mtd-unit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-unit/mtd-unit-dialog.html',
                    controller: 'MtdUnitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                unitcode: null,
                                unitname: null,
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
                    $state.go('mtd-unit', null, { reload: 'mtd-unit' });
                }, function() {
                    $state.go('mtd-unit');
                });
            }]
        })
        .state('mtd-unit.edit', {
            parent: 'mtd-unit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-unit/mtd-unit-dialog.html',
                    controller: 'MtdUnitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdUnit', function(MtdUnit) {
                            return MtdUnit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-unit', null, { reload: 'mtd-unit' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-unit.delete', {
            parent: 'mtd-unit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-unit/mtd-unit-delete-dialog.html',
                    controller: 'MtdUnitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MtdUnit', function(MtdUnit) {
                            return MtdUnit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-unit', null, { reload: 'mtd-unit' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
