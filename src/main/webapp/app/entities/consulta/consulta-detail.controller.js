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
        vm.notaFiscal = notaFiscal;
        vm.boleto = boleto;

        function notaFiscal() {
            window.open('http://localhost:8080/content/images/nota-fiscal.png');
        }

        function boleto() {
            window.open('http://localhost:8080/content/images/boleto.jpg');
        }

        var unsubscribe = $rootScope.$on('hackathonApp:consultaUpdate', function(event, result) {
            vm.consulta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
