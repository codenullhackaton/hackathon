(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('CooperadoDetailController', CooperadoDetailController);

    CooperadoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cooperado', 'Especialidade'];

    function CooperadoDetailController($scope, $rootScope, $stateParams, previousState, entity, Cooperado, Especialidade) {
        var vm = this;

        vm.cooperado = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hackathonApp:cooperadoUpdate', function(event, result) {
            vm.cooperado = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
