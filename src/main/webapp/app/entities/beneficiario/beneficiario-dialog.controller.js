(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('BeneficiarioDialogController', BeneficiarioDialogController);

    BeneficiarioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Beneficiario'];

    function BeneficiarioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Beneficiario) {
        var vm = this;

        vm.beneficiario = entity;
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
            if (vm.beneficiario.id !== null) {
                Beneficiario.update(vm.beneficiario, onSaveSuccess, onSaveError);
            } else {
                Beneficiario.save(vm.beneficiario, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hackathonApp:beneficiarioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
