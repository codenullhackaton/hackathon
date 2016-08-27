(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('ConsultaDeleteController',ConsultaDeleteController);

    ConsultaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Consulta'];

    function ConsultaDeleteController($uibModalInstance, entity, Consulta) {
        var vm = this;

        vm.consulta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Consulta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
