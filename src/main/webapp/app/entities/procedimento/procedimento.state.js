(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('procedimento', {
            parent: 'entity',
            url: '/procedimento?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Procedimentos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/procedimento/procedimentos.html',
                    controller: 'ProcedimentoController',
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
        .state('procedimento-detail', {
            parent: 'entity',
            url: '/procedimento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Procedimento'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/procedimento/procedimento-detail.html',
                    controller: 'ProcedimentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Procedimento', function($stateParams, Procedimento) {
                    return Procedimento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'procedimento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('procedimento-detail.edit', {
            parent: 'procedimento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedimento/procedimento-dialog.html',
                    controller: 'ProcedimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Procedimento', function(Procedimento) {
                            return Procedimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('procedimento.new', {
            parent: 'procedimento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedimento/procedimento-dialog.html',
                    controller: 'ProcedimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descricao: null,
                                duracao: null,
                                valor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('procedimento', null, { reload: 'procedimento' });
                }, function() {
                    $state.go('procedimento');
                });
            }]
        })
        .state('procedimento.edit', {
            parent: 'procedimento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedimento/procedimento-dialog.html',
                    controller: 'ProcedimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Procedimento', function(Procedimento) {
                            return Procedimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('procedimento', null, { reload: 'procedimento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('procedimento.delete', {
            parent: 'procedimento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/procedimento/procedimento-delete-dialog.html',
                    controller: 'ProcedimentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Procedimento', function(Procedimento) {
                            return Procedimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('procedimento', null, { reload: 'procedimento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
