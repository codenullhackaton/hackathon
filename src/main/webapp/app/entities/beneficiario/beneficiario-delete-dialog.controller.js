(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('BeneficiarioDeleteController',BeneficiarioDeleteController);

    BeneficiarioDeleteController.$inject = ['$uibModalInstance', 'entity', 'Beneficiario'];

    function BeneficiarioDeleteController($uibModalInstance, entity, Beneficiario) {
        var vm = this;

        vm.beneficiario = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Beneficiario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
