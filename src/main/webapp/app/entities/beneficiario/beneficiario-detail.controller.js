(function () {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('BeneficiarioDetailController', BeneficiarioDetailController);

    BeneficiarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Beneficiario', 'Consulta'];

    function BeneficiarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Beneficiario, Consulta) {
        var vm = this;

        vm.beneficiario = entity;
        vm.previousState = previousState.name;
        vm.chart = {};
        vm.chart.doughnutData = [];

        function recuperarConsultas() {
            Consulta.getConsultasPorBeneficiario({idBeneficiario: vm.beneficiario.id}, successRecuperarConsultas);
        }

        recuperarConsultas();

        function getRandomColor() {
            var letters = '0123456789ABCDEF';
            var color = '#';
            for (var i = 0; i < 6; i++) {
                color += letters[Math.floor(Math.random() * 16)];
            }
            return color;
        }

        function successRecuperarConsultas(data) {
            vm.consultas = data;
            var valores = {};
            angular.forEach(vm.consultas, function (consulta) {
                if (valores[consulta.procedimento.descricao]) {
                    valores[consulta.procedimento.descricao].valor = valores[consulta.procedimento.descricao].valor + consulta.procedimento.valor;
                } else {
                    valores[consulta.procedimento.descricao] = {
                        valor: consulta.procedimento.valor, nome: consulta.procedimento.descricao
                    }
                    ;
                }
            });
            console.log(valores);

            angular.forEach(valores, function (consulta) {
                vm.chart.doughnutData.push(
                    {
                        value: consulta.valor,
                        color: getRandomColor(),
                        highlight: getRandomColor(),
                        label: consulta.nome
                    }
                );
            });
        }

        var unsubscribe = $rootScope.$on('hackathonApp:beneficiarioUpdate', function (event, result) {
            vm.beneficiario = result;


        });
        $scope.$on('$destroy', unsubscribe);


        vm.chart.doughnutOptions = {
            segmentShowStroke: true,
            segmentStrokeColor: "#fff",
            segmentStrokeWidth: 2,
            percentageInnerCutout: 45, // vm.chart is 0 for Pie charts
            animationSteps: 100,
            animationEasing: "easeOutBounce",
            animateRotate: true,
            animateScale: false
        };


    }


})();
