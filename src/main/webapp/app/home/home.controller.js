(function () {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$window', '$scope', 'Noticia', 'Principal', 'Consulta', 'Auth', '$state', '$rootScope', '$timeout', 'calendarConfig', 'AlertService', 'moment', '$ocLazyLoad', 'ConsultaCooperado', 'Cooperado'];

    function HomeController($window, $scope, Noticia, Principal, Consulta, Auth, $state, $rootScope, $timeout, calendarConfig, AlertService, moment, $ocLazyLoad, ConsultaCooperado, Cooperado) {

        var vm = this;


        vm.authenticationError = false;
        vm.cancel = cancel;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.register = register;
        vm.rememberMe = true;
        vm.requestResetPassword = requestResetPassword;
        vm.username = null;
        vm.resumo = {};

        vm.graficosView = '/app/home/graficos.html';
        vm.agendaView = '/app/home/agenda.html';
        vm.noticiasView = '/app/home/noticias.html';

        vm.account = null;
        vm.isAuthenticated = null;

        vm.register = register;
      /*  var lineChart = ConsultaCooperado.consultasPorCooperado({id: 1000}, onSuccessLineChart);
        var resmo = ConsultaCooperado.consultasResumoPorCooperado({id: 1000}, onSuccessResumo);
        var cotas = ConsultaCooperado.consultasResumoPorCooperadoCotas({id: 1000}, onSuccessLineCotas);
        var geral = ConsultaCooperado.consultasResumoGeral(onSuccessGeral);
*/
        $scope.$on('authenticationSuccess', function () {
            getAccount();
            getNoticias();
        });

        getAccount();
        getNoticias();

        $timeout(function () {
            angular.element('#username').focus();
        });

        $window.moment = $window.moment || moment;
        $ocLazyLoad.load('https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/locale/pt-br.js').then(function () {
            moment.locale('pt-br', {
                week: {
                    dow: 1 // Monday is the first day of the week
                }
            });
            moment.locale('pt-br'); // change the locale to french
        });

        function cancel() {
            vm.credentials = {
                username: null,
                password: null,
                rememberMe: true
            };
            vm.authenticationError = false;
        }

        function login(event) {
            event.preventDefault();
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;

                if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                    $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                    $state.go('home');
                }

                $rootScope.$broadcast('authenticationSuccess');

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is succesful, go to stored previousState and clear previousState
                if (Auth.getPreviousState()) {
                    var previousState = Auth.getPreviousState();
                    Auth.resetPreviousState();
                    $state.go(previousState.name, previousState.params);
                }
            }).catch(function () {
                vm.authenticationError = true;
            });
        }

        function register() {
            $uibModalInstance.dismiss('cancel');
            $state.go('register');
        }

        function requestResetPassword() {
            $uibModalInstance.dismiss('cancel');
            $state.go('requestReset');
        }

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                console.log("vai buscar o cooperado", account.login);
                Cooperado.getByLogin({login: account.login}, succesCooperadoByLogin, errorCooperadoByLogin);
            });
        }

        function succesCooperadoByLogin(data) {
            vm.cooperado= data;
            ConsultaCooperado.consultasPorCooperado({id: vm.cooperado.id}, onSuccessLineChart);
            ConsultaCooperado.consultasResumoPorCooperado({id: vm.cooperado.id}, onSuccessResumo);
            ConsultaCooperado.consultasResumoPorCooperadoCotas({id: vm.cooperado.id}, onSuccessLineCotas);
            ConsultaCooperado.consultasResumoGeral(onSuccessGeral);
            buscarConsultarPorCooperadoParaAgenda(vm.cooperado.id);
        }

        function errorCooperadoByLogin(error) {
            console.log("erro ao buscar o cooperado", error);
        }

        function getNoticias() {
            Noticia.query(onSuccess, onError);
            function onSuccess(data, headers) {
                console.log('NOTICIAS: ' + data);
                vm.noticias = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function register() {
            $state.go('register');
        }


        vm.chart = {};

        vm.chart.lineData = {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            /*labels: lineChart.labels,*/
            datasets: [
                {
                    label: "Example dataset",
                    fillColor: "rgba(26,179,148,0.5)",
                    strokeColor: "rgba(26,179,148,0.7)",
                    pointColor: "rgba(26,179,148,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(26,179,148,1)",
                    data: [28, 48, 40, 19, 86, 27, 90]
                    //data: vm.lineChart.dados
                }
            ]
        };


        this.lineOptions = {
            scaleShowGridLines : true,
            scaleGridLineColor : "rgba(0,0,0,.05)",
            scaleGridLineWidth : 1,
            bezierCurve : true,
            bezierCurveTension : 0.4,
            pointDot : true,
            pointDotRadius : 4,
            pointDotStrokeWidth : 1,
            pointHitDetectionRadius : 20,
            datasetStroke : true,
            datasetStrokeWidth : 2,
            datasetFill : true,
            responsivel : true
        };


        vm.chart.lineDataGeral = {
            labels: ["January", "February", "March", "April", "May", "June", "July", "August", "Sept", "Octuber"],
            /*labels: lineChart.labels,*/
            datasets: [
                {
                    label: "Example dataset",
                    fillColor: "rgba(26,179,148,0.5)",
                    strokeColor: "rgba(26,179,148,0.7)",
                    pointColor: "rgba(26,179,148,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(26,179,148,1)",
                   // data: [28, 48, 40, 19, 86, 27, 90, 95, 80, 100]
                    //data: vm.lineChart.dados
                },
                {
                    label: "Example dataset 2",
                    fillColor: "rgba(220,220,220,0.5)",
                    strokeColor: "rgba(220,220,220,1)",
                    pointColor: "rgba(220,220,220,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(220,220,220,1)",
                  //  data: [28, 48, 40, 19, 86, 27, 90]
                }
            ]
        };

        vm.chart.lineDataCotas = {
            labels: ["January", "February", "March", "April", "May", "June", "July", "August", "Sept", "Octuber"],
            /*labels: lineChart.labels,*/
            datasets: [
                {
                    label: "Example dataset",
                    fillColor: "rgba(26,179,148,0.5)",
                    strokeColor: "rgba(26,179,148,0.7)",
                    pointColor: "rgba(26,179,148,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(26,179,148,1)",
               //     data: [28, 48, 40, 19, 86, 27, 90, 95, 80, 100]
                    //data: vm.lineChart.dados
                },
                {
                    label: "Example dataset 2",
                    fillColor: "rgba(220,220,220,0.5)",
                    strokeColor: "rgba(220,220,220,1)",
                    pointColor: "rgba(220,220,220,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(220,220,220,1)",
                 //   data: [28, 48, 40, 19, 86, 27, 90]
                }
            ]
        };

        function onSuccessLineChart(data) {
            vm.chart.lineData.labels = data.labels;
            vm.chart.lineData.datasets[0].data = data.dados;
        }


        // TODO AGENDA
        vm.calendarView = 'month';
        vm.viewDate = new Date();

        var actions = [{
            label: '<i class=\'glyphicon glyphicon-pencil\'></i>',
            onClick: function (args) {
                alert.show('Edited', args.calendarEvent);
            }
        }, {
            label: '<i class=\'glyphicon glyphicon-remove\'></i>',
            onClick: function (args) {
                alert.show('Deleted', args.calendarEvent);
            }
        }];

        vm.temCompromissoHoje = false;
        vm.eventosAgenda = [];

        function buscarConsultarPorCooperadoParaAgenda(id) {
            ConsultaCooperado.consultasPorCooperadoAgenda({id: id}, onSuccessAgenda);
            function onSuccessAgenda(data) {
                vm.consultasAgenda = data;

                for (var i = 0; i < vm.consultasAgenda.length; i++) {
                    var dataConsulta = new Date(vm.consultasAgenda[i].dataConsulta).setHours(0, 0, 0, 0);
                    var dataAtual = new Date().setHours(0, 0, 0, 0);
                    if (dataConsulta == dataAtual) {
                        vm.temCompromissoHoje = true;
                    }

                    var evento = {
                        title: vm.consultasAgenda[i].localidade,
                        color: calendarConfig.colorTypes.warning,
                        startsAt: vm.consultasAgenda[i].dataConsulta,
                        endsAt: vm.consultasAgenda[i].dataConsulta,
                        draggable: true,
                        resizable: true
                    };
                    vm.eventosAgenda.push(evento);
                }
            }
        }

        vm.eventClicked = function (event) {
            alert.show('Clicked', event);
        };

        vm.eventEdited = function (event) {
            alert.show('Edited', event);
        };

        vm.eventDeleted = function (event) {
            alert.show('Deleted', event);
        };

        vm.eventTimesChanged = function (event) {
            alert.show('Dropped or resized', event);
        };

        vm.toggle = function ($event, field, event) {
            $event.preventDefault();
            $event.stopPropagation();
            event[field] = !event[field];
        };
        function onSuccessResumo(data){
            console.log("", data);
            vm.resumo = data;
        }

        function onSuccessLineCotas(data){
            console.log("", data);
            vm.chart.lineDataGeral.labels = data.labels;
            vm.chart.lineDataGeral.datasets[0].data = data.dados;
            vm.chart.lineDataGeral.datasets[1].data = data.dadosSecundarios;
        }

        function onSuccessGeral(data){
            console.log("", data);
            vm.chart.lineDataGeral.labels = data.labels;
            vm.chart.lineDataGeral.datasets[0].data = data.dados;
            vm.chart.lineDataGeral.datasets[1].data = data.dadosSecundarios;
        }

        function onSuccessLineCotas(data){
            console.log("", data);
            vm.chart.lineDataCotas.labels = data.labels;
            vm.chart.lineDataCotas.datasets[0].data = data.dados;
            vm.chart.lineDataCotas.datasets[1].data = data.dadosSecundarios;
        }

    }
})();
