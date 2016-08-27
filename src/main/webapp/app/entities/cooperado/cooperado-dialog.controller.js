(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('CooperadoDialogController', CooperadoDialogController);

    CooperadoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cooperado', 'Especialidade'];

    function CooperadoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cooperado, Especialidade) {
        var vm = this;

        vm.cooperado = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.especialidades = Especialidade.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cooperado.id !== null) {
                Cooperado.update(vm.cooperado, onSaveSuccess, onSaveError);
            } else {
                Cooperado.save(vm.cooperado, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hackathonApp:cooperadoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.adesao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
