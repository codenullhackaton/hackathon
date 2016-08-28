(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('beneficiario', {
            parent: 'entity',
            url: '/beneficiario?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Beneficiarios'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beneficiario/beneficiarios.html',
                    controller: 'BeneficiarioController',
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
            }
        })
        .state('beneficiario-detail', {
            parent: 'entity',
            url: '/beneficiario/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Beneficiario'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/beneficiario/beneficiario-detail.html',
                    controller: 'BeneficiarioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Beneficiario', function($stateParams, Beneficiario) {
                    return Beneficiario.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'beneficiario',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
            .state('beneficiario-detail-cooperado', {
                parent: 'entity',
                url: '/beneficiario-cooperado/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Beneficiario'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/beneficiario/beneficiario-detail.html',
                        controller: 'BeneficiarioDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Beneficiario', function($stateParams, Beneficiario) {
                        return Beneficiario.getPorCooperado({id : $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'beneficiario',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
        .state('beneficiario-detail.edit', {
            parent: 'beneficiario-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficiario/beneficiario-dialog.html',
                    controller: 'BeneficiarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Beneficiario', function(Beneficiario) {
                            return Beneficiario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('beneficiario.new', {
            parent: 'beneficiario',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficiario/beneficiario-dialog.html',
                    controller: 'BeneficiarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                endereco: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('beneficiario', null, { reload: 'beneficiario' });
                }, function() {
                    $state.go('beneficiario');
                });
            }]
        })
        .state('beneficiario.edit', {
            parent: 'beneficiario',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficiario/beneficiario-dialog.html',
                    controller: 'BeneficiarioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Beneficiario', function(Beneficiario) {
                            return Beneficiario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beneficiario', null, { reload: 'beneficiario' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('beneficiario.delete', {
            parent: 'beneficiario',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/beneficiario/beneficiario-delete-dialog.html',
                    controller: 'BeneficiarioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Beneficiario', function(Beneficiario) {
                            return Beneficiario.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('beneficiario', null, { reload: 'beneficiario' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
