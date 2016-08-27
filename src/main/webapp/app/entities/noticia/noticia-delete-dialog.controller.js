(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('NoticiaDeleteController',NoticiaDeleteController);

    NoticiaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Noticia'];

    function NoticiaDeleteController($uibModalInstance, entity, Noticia) {
        var vm = this;

        vm.noticia = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Noticia.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
