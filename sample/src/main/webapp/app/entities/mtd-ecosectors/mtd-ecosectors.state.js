(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mtd-ecosectors', {
            parent: 'entity',
            url: '/mtd-ecosectors?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdEcosectors.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-ecosectors/mtd-ecosectors.html',
                    controller: 'MtdEcosectorsController',
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
                    $translatePartialLoader.addPart('mtdEcosectors');
                    $translatePartialLoader.addPart('enumStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mtd-ecosectors-detail', {
            parent: 'mtd-ecosectors',
            url: '/mtd-ecosectors/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdEcosectors.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-ecosectors/mtd-ecosectors-detail.html',
                    controller: 'MtdEcosectorsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mtdEcosectors');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MtdEcosectors', function($stateParams, MtdEcosectors) {
                    return MtdEcosectors.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mtd-ecosectors',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mtd-ecosectors-detail.edit', {
            parent: 'mtd-ecosectors-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-ecosectors/mtd-ecosectors-dialog.html',
                    controller: 'MtdEcosectorsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdEcosectors', function(MtdEcosectors) {
                            return MtdEcosectors.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-ecosectors.new', {
            parent: 'mtd-ecosectors',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-ecosectors/mtd-ecosectors-dialog.html',
                    controller: 'MtdEcosectorsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                econame: null,
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
                    $state.go('mtd-ecosectors', null, { reload: 'mtd-ecosectors' });
                }, function() {
                    $state.go('mtd-ecosectors');
                });
            }]
        })
        .state('mtd-ecosectors.edit', {
            parent: 'mtd-ecosectors',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-ecosectors/mtd-ecosectors-dialog.html',
                    controller: 'MtdEcosectorsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdEcosectors', function(MtdEcosectors) {
                            return MtdEcosectors.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-ecosectors', null, { reload: 'mtd-ecosectors' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-ecosectors.delete', {
            parent: 'mtd-ecosectors',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-ecosectors/mtd-ecosectors-delete-dialog.html',
                    controller: 'MtdEcosectorsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MtdEcosectors', function(MtdEcosectors) {
                            return MtdEcosectors.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-ecosectors', null, { reload: 'mtd-ecosectors' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
