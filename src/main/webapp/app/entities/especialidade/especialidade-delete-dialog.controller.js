(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('EspecialidadeDeleteController',EspecialidadeDeleteController);

    EspecialidadeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Especialidade'];

    function EspecialidadeDeleteController($uibModalInstance, entity, Especialidade) {
        var vm = this;

        vm.especialidade = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Especialidade.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
