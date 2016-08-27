'use strict';

describe('Controller Tests', function() {

    describe('Consulta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockConsulta, MockProcedimento, MockCooperado, MockBeneficiario;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockConsulta = jasmine.createSpy('MockConsulta');
            MockProcedimento = jasmine.createSpy('MockProcedimento');
            MockCooperado = jasmine.createSpy('MockCooperado');
            MockBeneficiario = jasmine.createSpy('MockBeneficiario');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Consulta': MockConsulta,
                'Procedimento': MockProcedimento,
                'Cooperado': MockCooperado,
                'Beneficiario': MockBeneficiario
            };
            createController = function() {
                $injector.get('$controller')("ConsultaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hackathonApp:consultaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
