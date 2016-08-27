(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('ProcedimentoDetailController', ProcedimentoDetailController);

    ProcedimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Procedimento'];

    function ProcedimentoDetailController($scope, $rootScope, $stateParams, previousState, entity, Procedimento) {
        var vm = this;

        vm.procedimento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hackathonApp:procedimentoUpdate', function(event, result) {
            vm.procedimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
