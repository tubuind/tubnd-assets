(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cif-area', {
            parent: 'entity',
            url: '/cif-area?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.cifArea.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cif-area/cif-areas.html',
                    controller: 'CifAreaController',
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
                    $translatePartialLoader.addPart('cifArea');
                    $translatePartialLoader.addPart('enumStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cif-area-detail', {
            parent: 'cif-area',
            url: '/cif-area/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.cifArea.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cif-area/cif-area-detail.html',
                    controller: 'CifAreaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cifArea');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CifArea', function($stateParams, CifArea) {
                    return CifArea.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cif-area',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cif-area-detail.edit', {
            parent: 'cif-area-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-area/cif-area-dialog.html',
                    controller: 'CifAreaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CifArea', function(CifArea) {
                            return CifArea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cif-area.new', {
            parent: 'cif-area',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-area/cif-area-dialog.html',
                    controller: 'CifAreaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                cifareaparent: null,
                                cifareacode: null,
                                cifareaname: null,
                                cifareadesc: null,
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
                    $state.go('cif-area', null, { reload: 'cif-area' });
                }, function() {
                    $state.go('cif-area');
                });
            }]
        })
        .state('cif-area.edit', {
            parent: 'cif-area',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-area/cif-area-dialog.html',
                    controller: 'CifAreaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['CifArea', function(CifArea) {
                            return CifArea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cif-area', null, { reload: 'cif-area' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cif-area.delete', {
            parent: 'cif-area',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-area/cif-area-delete-dialog.html',
                    controller: 'CifAreaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CifArea', function(CifArea) {
                            return CifArea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cif-area', null, { reload: 'cif-area' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
