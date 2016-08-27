(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('EspecialidadeDialogController', EspecialidadeDialogController);

    EspecialidadeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Especialidade'];

    function EspecialidadeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Especialidade) {
        var vm = this;

        vm.especialidade = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.especialidade.id !== null) {
                Especialidade.update(vm.especialidade, onSaveSuccess, onSaveError);
            } else {
                Especialidade.save(vm.especialidade, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hackathonApp:especialidadeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
