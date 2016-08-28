(function() {
    'use strict';
    angular
        .module('hackathonApp')
        .factory('Consulta', Consulta);

    Consulta.$inject = ['$resource', 'DateUtils'];

    function Consulta ($resource, DateUtils) {
        var resourceUrl =  'api/consultas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataConsulta = DateUtils.convertLocalDateFromServer(data.dataConsulta);
                        data.criadoEm = DateUtils.convertLocalDateFromServer(data.criadoEm);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dataConsulta = DateUtils.convertLocalDateToServer(data.dataConsulta);
                    data.criadoEm = DateUtils.convertLocalDateToServer(data.criadoEm);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dataConsulta = DateUtils.convertLocalDateToServer(data.dataConsulta);
                    data.criadoEm = DateUtils.convertLocalDateToServer(data.criadoEm);
                    return angular.toJson(data);
                }
            }
        });
    }
})();