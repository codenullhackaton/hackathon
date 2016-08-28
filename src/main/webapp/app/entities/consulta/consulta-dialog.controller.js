(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('ConsultaDialogController', ConsultaDialogController);

    ConsultaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Consulta', 'Procedimento', 'Cooperado', 'Beneficiario'];

    function ConsultaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Consulta, Procedimento, Cooperado, Beneficiario) {
        var vm = this;

        vm.consulta = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.procedimentos = Procedimento.query();
        vm.cooperados = Cooperado.query();
        vm.beneficiarios = Beneficiario.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.consulta.id !== null) {
                Consulta.update(vm.consulta, onSaveSuccess, onSaveError);
            } else {
                Consulta.save(vm.consulta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hackathonApp:consultaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.criadoEm = false;
        vm.datePickerOpenStatus.dataConsulta = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
