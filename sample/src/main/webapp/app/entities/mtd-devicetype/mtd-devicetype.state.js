(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mtd-devicetype', {
            parent: 'entity',
            url: '/mtd-devicetype?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdDevicetype.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-devicetype/mtd-devicetypes.html',
                    controller: 'MtdDevicetypeController',
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
                    $translatePartialLoader.addPart('mtdDevicetype');
                    $translatePartialLoader.addPart('enumStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mtd-devicetype-detail', {
            parent: 'mtd-devicetype',
            url: '/mtd-devicetype/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdDevicetype.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-devicetype/mtd-devicetype-detail.html',
                    controller: 'MtdDevicetypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mtdDevicetype');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MtdDevicetype', function($stateParams, MtdDevicetype) {
                    return MtdDevicetype.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mtd-devicetype',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mtd-devicetype-detail.edit', {
            parent: 'mtd-devicetype-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-devicetype/mtd-devicetype-dialog.html',
                    controller: 'MtdDevicetypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdDevicetype', function(MtdDevicetype) {
                            return MtdDevicetype.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-devicetype.new', {
            parent: 'mtd-devicetype',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-devicetype/mtd-devicetype-dialog.html',
                    controller: 'MtdDevicetypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                devicetypename: null,
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
                    $state.go('mtd-devicetype', null, { reload: 'mtd-devicetype' });
                }, function() {
                    $state.go('mtd-devicetype');
                });
            }]
        })
        .state('mtd-devicetype.edit', {
            parent: 'mtd-devicetype',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-devicetype/mtd-devicetype-dialog.html',
                    controller: 'MtdDevicetypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdDevicetype', function(MtdDevicetype) {
                            return MtdDevicetype.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-devicetype', null, { reload: 'mtd-devicetype' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-devicetype.delete', {
            parent: 'mtd-devicetype',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-devicetype/mtd-devicetype-delete-dialog.html',
                    controller: 'MtdDevicetypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MtdDevicetype', function(MtdDevicetype) {
                            return MtdDevicetype.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-devicetype', null, { reload: 'mtd-devicetype' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
