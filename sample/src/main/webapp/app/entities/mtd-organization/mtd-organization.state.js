(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mtd-organization', {
            parent: 'entity',
            url: '/mtd-organization?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdOrganization.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-organization/mtd-organizations.html',
                    controller: 'MtdOrganizationController',
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
                    $translatePartialLoader.addPart('mtdOrganization');
                    $translatePartialLoader.addPart('enumStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mtd-organization-detail', {
            parent: 'mtd-organization',
            url: '/mtd-organization/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdOrganization.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-organization/mtd-organization-detail.html',
                    controller: 'MtdOrganizationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mtdOrganization');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MtdOrganization', function($stateParams, MtdOrganization) {
                    return MtdOrganization.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mtd-organization',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mtd-organization-detail.edit', {
            parent: 'mtd-organization-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-organization/mtd-organization-dialog.html',
                    controller: 'MtdOrganizationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdOrganization', function(MtdOrganization) {
                            return MtdOrganization.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-organization.new', {
            parent: 'mtd-organization',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-organization/mtd-organization-dialog.html',
                    controller: 'MtdOrganizationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                organizationcode: null,
                                organizationname: null,
                                address: null,
                                mobilenum: null,
                                phonenum: null,
                                parents: null,
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
                    $state.go('mtd-organization', null, { reload: 'mtd-organization' });
                }, function() {
                    $state.go('mtd-organization');
                });
            }]
        })
        .state('mtd-organization.edit', {
            parent: 'mtd-organization',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-organization/mtd-organization-dialog.html',
                    controller: 'MtdOrganizationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdOrganization', function(MtdOrganization) {
                            return MtdOrganization.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-organization', null, { reload: 'mtd-organization' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-organization.delete', {
            parent: 'mtd-organization',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-organization/mtd-organization-delete-dialog.html',
                    controller: 'MtdOrganizationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MtdOrganization', function(MtdOrganization) {
                            return MtdOrganization.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-organization', null, { reload: 'mtd-organization' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
