(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mtd-customergroup', {
            parent: 'entity',
            url: '/mtd-customergroup?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdCustomergroup.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-customergroup/mtd-customergroups.html',
                    controller: 'MtdCustomergroupController',
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
                    $translatePartialLoader.addPart('mtdCustomergroup');
                    $translatePartialLoader.addPart('enumStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mtd-customergroup-detail', {
            parent: 'mtd-customergroup',
            url: '/mtd-customergroup/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdCustomergroup.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-customergroup/mtd-customergroup-detail.html',
                    controller: 'MtdCustomergroupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mtdCustomergroup');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MtdCustomergroup', function($stateParams, MtdCustomergroup) {
                    return MtdCustomergroup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mtd-customergroup',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mtd-customergroup-detail.edit', {
            parent: 'mtd-customergroup-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-customergroup/mtd-customergroup-dialog.html',
                    controller: 'MtdCustomergroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdCustomergroup', function(MtdCustomergroup) {
                            return MtdCustomergroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-customergroup.new', {
            parent: 'mtd-customergroup',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-customergroup/mtd-customergroup-dialog.html',
                    controller: 'MtdCustomergroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                custgroupname: null,
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
                    $state.go('mtd-customergroup', null, { reload: 'mtd-customergroup' });
                }, function() {
                    $state.go('mtd-customergroup');
                });
            }]
        })
        .state('mtd-customergroup.edit', {
            parent: 'mtd-customergroup',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-customergroup/mtd-customergroup-dialog.html',
                    controller: 'MtdCustomergroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdCustomergroup', function(MtdCustomergroup) {
                            return MtdCustomergroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-customergroup', null, { reload: 'mtd-customergroup' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-customergroup.delete', {
            parent: 'mtd-customergroup',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-customergroup/mtd-customergroup-delete-dialog.html',
                    controller: 'MtdCustomergroupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MtdCustomergroup', function(MtdCustomergroup) {
                            return MtdCustomergroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-customergroup', null, { reload: 'mtd-customergroup' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
