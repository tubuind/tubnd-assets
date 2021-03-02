(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cif-master', {
            parent: 'entity',
            url: '/cif-master?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.cifMaster.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cif-master/cif-masters.html',
                    controller: 'CifMasterController',
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
                    $translatePartialLoader.addPart('cifMaster');
                    $translatePartialLoader.addPart('enumStatus');
                    $translatePartialLoader.addPart('enumSex');
                    $translatePartialLoader.addPart('enumCustType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cif-master-detail', {
            parent: 'cif-master',
            url: '/cif-master/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.cifMaster.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cif-master/cif-master-detail.html',
                    controller: 'CifMasterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cifMaster');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CifMaster', function($stateParams, CifMaster) {
                    return CifMaster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cif-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cif-master-detail.edit', {
            parent: 'cif-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-master/cif-master-dialog.html',
                    controller: 'CifMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['CifMaster', function(CifMaster) {
                            return CifMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cif-master.new', {
            parent: 'cif-master',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-master/cif-master-dialog.html',
                    controller: 'CifMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                customercode: null,
                                customername: null,
                                sex: null,
                                birthday: null,
                                identifycode: null,
                                identifydate: null,
                                address: null,
                                mobilenum: null,
                                phonenum: null,
                                customertype: null,
                                custparents: null,
                                note: null,
                                custlatitude: null,
                                custlongitude: null,
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
                    $state.go('cif-master', null, { reload: 'cif-master' });
                }, function() {
                    $state.go('cif-master');
                });
            }]
        })
        .state('cif-master.edit', {
            parent: 'cif-master',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-master/cif-master-dialog.html',
                    controller: 'CifMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CifMaster', function(CifMaster) {
                            return CifMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cif-master', null, { reload: 'cif-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cif-master.delete', {
            parent: 'cif-master',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cif-master/cif-master-delete-dialog.html',
                    controller: 'CifMasterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CifMaster', function(CifMaster) {
                            return CifMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cif-master', null, { reload: 'cif-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
