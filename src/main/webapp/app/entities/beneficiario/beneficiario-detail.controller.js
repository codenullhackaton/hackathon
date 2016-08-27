(function() {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('BeneficiarioDetailController', BeneficiarioDetailController);

    BeneficiarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Beneficiario'];

    function BeneficiarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Beneficiario) {
        var vm = this;

        vm.beneficiario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hackathonApp:beneficiarioUpdate', function(event, result) {
            vm.beneficiario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
