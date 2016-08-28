(function () {
    'use strict';

    angular
        .module('hackathonApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$window', '$scope', 'Noticia', 'Principal', 'Consulta', 'Auth', '$state', '$rootScope', '$timeout', 'calendarConfig', 'AlertService', 'moment', '$ocLazyLoad', 'ConsultaCooperado'];

    function HomeController($window, $scope, Noticia, Principal, Consulta, Auth, $state, $rootScope, $timeout, calendarConfig, AlertService, moment, $ocLazyLoad, ConsultaCooperado) {

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

        vm.graficosView = '/app/home/graficos.html';
        vm.agendaView = '/app/home/agenda.html';
        vm.noticiasView = '/app/home/noticias.html';

        vm.account = null;
        vm.isAuthenticated = null;

        vm.register = register;
        var lineChart = ConsultaCooperado.consultasPorCooperado({id: 1000}, onSuccessLineChart);

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
            });
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


        /**
         * Data for Polar chart
         */
        vm.chart.polarData = [
            {
                value: 300,
                color: "#a3e1d4",
                highlight: "#1ab394",
                label: "App"
            },
            {
                value: 140,
                color: "#dedede",
                highlight: "#1ab394",
                label: "Software"
            },
            {
                value: 200,
                color: "#A4CEE8",
                highlight: "#1ab394",
                label: "Laptop"
            }
        ];

        /**
         * Options for Polar chart
         */
        vm.chart.polarOptions = {
            scaleShowLabelBackdrop: true,
            scaleBackdropColor: "rgba(255,255,255,0.75)",
            scaleBeginAtZero: true,
            scaleBackdropPaddingY: 1,
            scaleBackdropPaddingX: 1,
            scaleShowLine: true,
            segmentShowStroke: true,
            segmentStrokeColor: "#fff",
            segmentStrokeWidth: 2,
            animationSteps: 100,
            animationEasing: "easeOutBounce",
            animateRotate: true,
            animateScale: false
        };

        /**
         * Data for Doughnut chart
         */
        vm.chart.doughnutData = [
            {
                value: 300,
                color: "#a3e1d4",
                highlight: "#1ab394",
                label: "App"
            },
            {
                value: 50,
                color: "#dedede",
                highlight: "#1ab394",
                label: "Software"
            },
            {
                value: 100,
                color: "#A4CEE8",
                highlight: "#1ab394",
                label: "Laptop"
            }
        ];

        /**
         * Options for Doughnut chart
         */
        vm.chart.doughnutOptions = {
            segmentShowStroke: true,
            segmentStrokeColor: "#fff",
            segmentStrokeWidth: 2,
            percentageInnerCutout: 45, // vm.chart is 0 for Pie charts
            animationSteps: 100,
            animationEasing: "easeOutBounce",
            animateRotate: true,
            animateScale: false
        };

        /**
         * Data for Line chart
         */
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

        vm.chart.lineDataDashboard4 = {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            datasets: [
                {
                    label: "Example dataset",
                    fillColor: "rgba(220,220,220,0.5)",
                    strokeColor: "rgba(220,220,220,1)",
                    pointColor: "rgba(220,220,220,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(220,220,220,1)",
                    data: [65, 59, 40, 51, 36, 25, 40]
                },
                {
                    label: "Example dataset",
                    fillColor: "rgba(26,179,148,0.5)",
                    strokeColor: "rgba(26,179,148,0.7)",
                    pointColor: "rgba(26,179,148,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(26,179,148,1)",
                    data: [48, 48, 60, 39, 56, 37, 30]
                }
            ]
        };

        /**
         * Options for Line chart
         */
        this.lineOptions = {
            scaleShowGridLines: true,
            scaleGridLineColor: "rgba(0,0,0,.05)",
            scaleGridLineWidth: 1,
            bezierCurve: true,
            bezierCurveTension: 0.4,
            pointDot: true,
            pointDotRadius: 4,
            pointDotStrokeWidth: 1,
            pointHitDetectionRadius: 20,
            datasetStroke: true,
            datasetStrokeWidth: 2,
            datasetFill: true
        };

        /**
         * Options for Bar chart
         */
        vm.chart.barOptions = {
            scaleBeginAtZero: true,
            scaleShowGridLines: true,
            scaleGridLineColor: "rgba(0,0,0,.05)",
            scaleGridLineWidth: 1,
            barShowStroke: true,
            barStrokeWidth: 2,
            barValueSpacing: 5,
            barDatasetSpacing: 1
        };

        /**
         * Data for Bar chart
         */
        vm.chart.barData = {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            datasets: [
                {
                    label: "My First dataset",
                    fillColor: "rgba(220,220,220,0.5)",
                    strokeColor: "rgba(220,220,220,0.8)",
                    highlightFill: "rgba(220,220,220,0.75)",
                    highlightStroke: "rgba(220,220,220,1)",
                    data: [65, 59, 80, 81, 56, 55, 40]
                },
                {
                    label: "My Second dataset",
                    fillColor: "rgba(26,179,148,0.5)",
                    strokeColor: "rgba(26,179,148,0.8)",
                    highlightFill: "rgba(26,179,148,0.75)",
                    highlightStroke: "rgba(26,179,148,1)",
                    data: [28, 48, 40, 19, 86, 27, 90]
                }
            ]
        };

        /**
         * Data for Radar chart
         */
        vm.chart.radarData = {
            labels: ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"],
            datasets: [
                {
                    label: "My First dataset",
                    fillColor: "rgba(220,220,220,0.2)",
                    strokeColor: "rgba(220,220,220,1)",
                    pointColor: "rgba(220,220,220,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(220,220,220,1)",
                    data: [65, 59, 90, 81, 56, 55, 40]
                },
                {
                    label: "My Second dataset",
                    fillColor: "rgba(26,179,148,0.2)",
                    strokeColor: "rgba(26,179,148,1)",
                    pointColor: "rgba(26,179,148,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(151,187,205,1)",
                    data: [28, 48, 40, 19, 96, 27, 100]
                }
            ]
        };

        /**
         * Options for Radar chart
         */
        vm.chart.radarOptions = {
            scaleShowLine: true,
            angleShowLineOut: true,
            scaleShowLabels: false,
            scaleBeginAtZero: true,
            angleLineColor: "rgba(0,0,0,.1)",
            angleLineWidth: 1,
            pointLabelFontFamily: "'Arial'",
            pointLabelFontStyle: "normal",
            pointLabelFontSize: 10,
            pointLabelFontColor: "#666",
            pointDot: true,
            pointDotRadius: 3,
            pointDotStrokeWidth: 1,
            pointHitDetectionRadius: 20,
            datasetStroke: true,
            datasetStrokeWidth: 2,
            datasetFill: true
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

        buscarConsultarPorCooperadoParaAgenda();
        function buscarConsultarPorCooperadoParaAgenda() {
            ConsultaCooperado.consultasPorCooperadoAgenda({id: 1000}, onSuccessAgenda);
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

    }
})();
