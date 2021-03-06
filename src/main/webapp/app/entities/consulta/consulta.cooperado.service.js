/**
 * Created by Mailson on 28/08/2016.
 */
(function() {
    'use strict';
    angular
        .module('hackathonApp')
        .factory('ConsultaCooperado', ConsultaCooperado);

    ConsultaCooperado.$inject = ['$resource', 'DateUtils'];

    function ConsultaCooperado ($resource, DateUtils) {
        var resourceUrl =  'api/consulta/cooperado/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'consultasPorCooperado': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'consultasPorCooperadoAgenda': {
                url: 'api/consultas/agenda/cooperado/:id',
                method: 'GET',
                isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'consultasResumoPorCooperado': {
                url: 'api/consulta/cooperado-resumo/:id',
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        console.log("buscando line chart", data);
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'consultasResumoPorCooperadoCotas': {
                url: 'api/consulta/cooperado-resumo-cotas/:id',
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'consultasResumoGeral': {
                url: 'api/consulta/cooperado-resumo-geral',
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();
