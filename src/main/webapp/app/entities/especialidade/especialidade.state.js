(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('especialidade', {
            parent: 'entity',
            url: '/especialidade?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Especialidades'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/especialidade/especialidades.html',
                    controller: 'EspecialidadeController',
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
        .state('especialidade-detail', {
            parent: 'entity',
            url: '/especialidade/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Especialidade'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/especialidade/especialidade-detail.html',
                    controller: 'EspecialidadeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Especialidade', function($stateParams, Especialidade) {
                    return Especialidade.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'especialidade',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('especialidade-detail.edit', {
            parent: 'especialidade-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/especialidade/especialidade-dialog.html',
                    controller: 'EspecialidadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Especialidade', function(Especialidade) {
                            return Especialidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('especialidade.new', {
            parent: 'especialidade',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/especialidade/especialidade-dialog.html',
                    controller: 'EspecialidadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descricao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('especialidade', null, { reload: 'especialidade' });
                }, function() {
                    $state.go('especialidade');
                });
            }]
        })
        .state('especialidade.edit', {
            parent: 'especialidade',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/especialidade/especialidade-dialog.html',
                    controller: 'EspecialidadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Especialidade', function(Especialidade) {
                            return Especialidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('especialidade', null, { reload: 'especialidade' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('especialidade.delete', {
            parent: 'especialidade',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/especialidade/especialidade-delete-dialog.html',
                    controller: 'EspecialidadeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Especialidade', function(Especialidade) {
                            return Especialidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('especialidade', null, { reload: 'especialidade' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
