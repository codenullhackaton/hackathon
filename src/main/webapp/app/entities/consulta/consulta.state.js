(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('consulta', {
            parent: 'entity',
            url: '/consulta?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Consultas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/consulta/consultas.html',
                    controller: 'ConsultaController',
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
        .state('consulta-detail', {
            parent: 'entity',
            url: '/consulta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Consulta'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/consulta/consulta-detail.html',
                    controller: 'ConsultaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Consulta', function($stateParams, Consulta) {
                    return Consulta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'consulta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('consulta-detail.edit', {
            parent: 'consulta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consulta/consulta-dialog.html',
                    controller: 'ConsultaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Consulta', function(Consulta) {
                            return Consulta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('consulta.new', {
            parent: 'consulta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consulta/consulta-dialog.html',
                    controller: 'ConsultaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                localidade: null,
                                criadoEm: null,
                                dataConsulta: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('consulta', null, { reload: 'consulta' });
                }, function() {
                    $state.go('consulta');
                });
            }]
        })
        .state('consulta.edit', {
            parent: 'consulta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consulta/consulta-dialog.html',
                    controller: 'ConsultaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Consulta', function(Consulta) {
                            return Consulta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('consulta', null, { reload: 'consulta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('consulta.delete', {
            parent: 'consulta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/consulta/consulta-delete-dialog.html',
                    controller: 'ConsultaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Consulta', function(Consulta) {
                            return Consulta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('consulta', null, { reload: 'consulta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
