(function () {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'calendarConfig'];

    function HomeController($scope, Principal, LoginService, $state, calendarConfig) {
        var vm = this;

        vm.graficosView = '/app/home/graficos.html';
        vm.agendaView = '/app/home/agenda.html';
        vm.noticiasView = '/app/home/noticias.html';

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function register() {
            $state.go('register');
        }

        // TODO AGENDA
        vm.eventsBack = [];
        function buscarConsultasPorCooperado() {
            // Consulta.getConsultasByCooperado(onSuccess, onError);
            // function onSuccess(data, headers) {
            //     return vm.consultas = data;
            // }
            // function onError(error) {
            //     AlertService.error(error.data.message);
            // }
        }

        vm.calendarView = 'month';
        vm.viewDate = new Date();
        var actions = [{
            label: '<i class=\'glyphicon glyphicon-pencil\'></i>',
            onClick: function(args) {
                alert.show('Edited', args.calendarEvent);
            }
        }, {
            label: '<i class=\'glyphicon glyphicon-remove\'></i>',
            onClick: function(args) {
                alert.show('Deleted', args.calendarEvent);
            }
        }];

        vm.events = [
            {
                title: 'Consulta 1',
                color: calendarConfig.colorTypes.warning,
                startsAt: moment().startOf('week').subtract(2, 'days').add(8, 'hours').toDate(),
                endsAt: moment().startOf('week').add(1, 'week').add(9, 'hours').toDate(),
                draggable: true,
                resizable: true
            }, {
                title: '<i class="glyphicon glyphicon-asterisk"></i> <span class="text-primary">Consulta 2</span>, with a <i>html</i> title',
                color: calendarConfig.colorTypes.info,
                startsAt: moment().subtract(1, 'day').toDate(),
                endsAt: moment().add(5, 'days').toDate(),
                draggable: true,
                resizable: true
            }, {
                title: 'Consulta 3',
                color: calendarConfig.colorTypes.important,
                startsAt: moment().startOf('day').add(7, 'hours').toDate(),
                endsAt: moment().startOf('day').add(19, 'hours').toDate(),
                recursOn: 'year',
                draggable: true,
                resizable: true
            }
        ];

        vm.eventClicked = function(event) {
            alert.show('Clicked', event);
        };

        vm.eventEdited = function(event) {
            alert.show('Edited', event);
        };

        vm.eventDeleted = function(event) {
            alert.show('Deleted', event);
        };

        vm.eventTimesChanged = function(event) {
            alert.show('Dropped or resized', event);
        };

        vm.toggle = function($event, field, event) {
            $event.preventDefault();
            $event.stopPropagation();
            event[field] = !event[field];
        };
    }
})();
