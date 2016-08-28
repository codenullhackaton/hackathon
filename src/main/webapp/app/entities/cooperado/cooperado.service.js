(function() {
    'use strict';
    angular
        .module('hackathonApp')
        .factory('Cooperado', Cooperado);

    Cooperado.$inject = ['$resource', 'DateUtils'];

    function Cooperado ($resource, DateUtils) {
        var resourceUrl =  'api/cooperados/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.adesao = DateUtils.convertLocalDateFromServer(data.adesao);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.adesao = DateUtils.convertLocalDateToServer(data.adesao);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.adesao = DateUtils.convertLocalDateToServer(data.adesao);
                    return angular.toJson(data);
                }
            },
            'getByLogin': {
                method: 'GET',
                url: 'api/cooperados-by-login/:login',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.adesao = DateUtils.convertLocalDateFromServer(data.adesao);
                    }
                    return data;
                }
            },
        });
    }
})();
