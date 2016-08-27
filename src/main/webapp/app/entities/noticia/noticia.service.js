(function() {
    'use strict';
    angular
        .module('hackathonApp')
        .factory('Noticia', Noticia);

    Noticia.$inject = ['$resource', 'DateUtils'];

    function Noticia ($resource, DateUtils) {
        var resourceUrl =  'api/noticias/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.criadoEm = DateUtils.convertLocalDateFromServer(data.criadoEm);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.criadoEm = DateUtils.convertLocalDateToServer(data.criadoEm);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.criadoEm = DateUtils.convertLocalDateToServer(data.criadoEm);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
