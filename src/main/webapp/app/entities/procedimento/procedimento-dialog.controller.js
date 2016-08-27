(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('ProcedimentoDialogController', ProcedimentoDialogController);

    ProcedimentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Procedimento'];

    function ProcedimentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Procedimento) {
        var vm = this;

        vm.procedimento = entity;
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
            if (vm.procedimento.id !== null) {
                Procedimento.update(vm.procedimento, onSaveSuccess, onSaveError);
            } else {
                Procedimento.save(vm.procedimento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hackathonApp:procedimentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
