(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('CooperadoDeleteController',CooperadoDeleteController);

    CooperadoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cooperado'];

    function CooperadoDeleteController($uibModalInstance, entity, Cooperado) {
        var vm = this;

        vm.cooperado = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cooperado.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
