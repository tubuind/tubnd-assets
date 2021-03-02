(function() {
    'use strict';

    angular
        .module('astcoreApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mtd-country', {
            parent: 'entity',
            url: '/mtd-country?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdCountry.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-country/mtd-countries.html',
                    controller: 'MtdCountryController',
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
                    $translatePartialLoader.addPart('mtdCountry');
                    $translatePartialLoader.addPart('enumStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mtd-country-detail', {
            parent: 'mtd-country',
            url: '/mtd-country/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'astcoreApp.mtdCountry.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mtd-country/mtd-country-detail.html',
                    controller: 'MtdCountryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mtdCountry');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MtdCountry', function($stateParams, MtdCountry) {
                    return MtdCountry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mtd-country',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mtd-country-detail.edit', {
            parent: 'mtd-country-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-country/mtd-country-dialog.html',
                    controller: 'MtdCountryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdCountry', function(MtdCountry) {
                            return MtdCountry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-country.new', {
            parent: 'mtd-country',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-country/mtd-country-dialog.html',
                    controller: 'MtdCountryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: function () {
                            return {
                                countryname: null,
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
                    $state.go('mtd-country', null, { reload: 'mtd-country' });
                }, function() {
                    $state.go('mtd-country');
                });
            }]
        })
        .state('mtd-country.edit', {
            parent: 'mtd-country',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-country/mtd-country-dialog.html',
                    controller: 'MtdCountryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['MtdCountry', function(MtdCountry) {
                            return MtdCountry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-country', null, { reload: 'mtd-country' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mtd-country.delete', {
            parent: 'mtd-country',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mtd-country/mtd-country-delete-dialog.html',
                    controller: 'MtdCountryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MtdCountry', function(MtdCountry) {
                            return MtdCountry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mtd-country', null, { reload: 'mtd-country' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
