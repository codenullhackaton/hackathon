(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('NoticiaDialogController', NoticiaDialogController);

    NoticiaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Noticia'];

    function NoticiaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Noticia) {
        var vm = this;

        vm.noticia = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.noticia.id !== null) {
                Noticia.update(vm.noticia, onSaveSuccess, onSaveError);
            } else {
                Noticia.save(vm.noticia, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hackathonApp:noticiaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.criadoEm = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
