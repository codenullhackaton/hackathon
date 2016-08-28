(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$ocLazyLoadProvider'];

    function stateConfig($stateProvider, $ocLazyLoadProvider) {
        $ocLazyLoadProvider.config({
            // Set to true if you want to see what and when is dynamically loaded
            debug: false
        });

        $stateProvider.state('app', {
            abstract: true,
            views: {
                'navbar@': {
                    templateUrl: 'app/layouts/navbar/navbar.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                },
                'menu@': {
                    templateUrl: 'app/layouts/menu/menu.html'
                }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                loadPlugin: function ($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        {
                            files: ['content/js/chartJs/Chart.min.js']
                        },
                        {
                            name: 'angles',
                            files: ['content/js/chartJs/angles.js']
                        }
                    ]);
                }
            }
        });
    }
})();
