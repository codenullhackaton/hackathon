(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cooperado', {
            parent: 'entity',
            url: '/cooperado?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cooperados'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cooperado/cooperados.html',
                    controller: 'CooperadoController',
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
        .state('cooperado-detail', {
            parent: 'entity',
            url: '/cooperado/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cooperado'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cooperado/cooperado-detail.html',
                    controller: 'CooperadoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Cooperado', function($stateParams, Cooperado) {
                    return Cooperado.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cooperado',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cooperado-detail.edit', {
            parent: 'cooperado-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cooperado/cooperado-dialog.html',
                    controller: 'CooperadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cooperado', function(Cooperado) {
                            return Cooperado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cooperado.new', {
            parent: 'cooperado',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cooperado/cooperado-dialog.html',
                    controller: 'CooperadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                crm: null,
                                valorCota: null,
                                adesao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cooperado', null, { reload: 'cooperado' });
                }, function() {
                    $state.go('cooperado');
                });
            }]
        })
        .state('cooperado.edit', {
            parent: 'cooperado',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cooperado/cooperado-dialog.html',
                    controller: 'CooperadoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cooperado', function(Cooperado) {
                            return Cooperado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cooperado', null, { reload: 'cooperado' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cooperado.delete', {
            parent: 'cooperado',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cooperado/cooperado-delete-dialog.html',
                    controller: 'CooperadoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cooperado', function(Cooperado) {
                            return Cooperado.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cooperado', null, { reload: 'cooperado' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
