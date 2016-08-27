(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('ProcedimentoDeleteController',ProcedimentoDeleteController);

    ProcedimentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Procedimento'];

    function ProcedimentoDeleteController($uibModalInstance, entity, Procedimento) {
        var vm = this;

        vm.procedimento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Procedimento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
