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


        $stateProvider.state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
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
