(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('NoticiaDetailController', NoticiaDetailController);

    NoticiaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Noticia'];

    function NoticiaDetailController($scope, $rootScope, $stateParams, previousState, entity, Noticia) {
        var vm = this;

        vm.noticia = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hackathonApp:noticiaUpdate', function(event, result) {
            vm.noticia = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
