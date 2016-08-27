(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('noticia', {
            parent: 'entity',
            url: '/noticia?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Noticias'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/noticia/noticias.html',
                    controller: 'NoticiaController',
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
        .state('noticia-detail', {
            parent: 'entity',
            url: '/noticia/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Noticia'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/noticia/noticia-detail.html',
                    controller: 'NoticiaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Noticia', function($stateParams, Noticia) {
                    return Noticia.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'noticia',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('noticia-detail.edit', {
            parent: 'noticia-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noticia/noticia-dialog.html',
                    controller: 'NoticiaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Noticia', function(Noticia) {
                            return Noticia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('noticia.new', {
            parent: 'noticia',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noticia/noticia-dialog.html',
                    controller: 'NoticiaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                titulo: null,
                                criadoEm: null,
                                conteudo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('noticia', null, { reload: 'noticia' });
                }, function() {
                    $state.go('noticia');
                });
            }]
        })
        .state('noticia.edit', {
            parent: 'noticia',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noticia/noticia-dialog.html',
                    controller: 'NoticiaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Noticia', function(Noticia) {
                            return Noticia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('noticia', null, { reload: 'noticia' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('noticia.delete', {
            parent: 'noticia',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/noticia/noticia-delete-dialog.html',
                    controller: 'NoticiaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Noticia', function(Noticia) {
                            return Noticia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('noticia', null, { reload: 'noticia' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
