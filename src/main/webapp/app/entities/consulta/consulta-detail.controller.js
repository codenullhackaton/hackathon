(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('ConsultaDetailController', ConsultaDetailController);

    ConsultaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Consulta', 'Procedimento', 'Cooperado', 'Beneficiario'];

    function ConsultaDetailController($scope, $rootScope, $stateParams, previousState, entity, Consulta, Procedimento, Cooperado, Beneficiario) {
        var vm = this;

        vm.consulta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hackathonApp:consultaUpdate', function(event, result) {
            vm.consulta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
