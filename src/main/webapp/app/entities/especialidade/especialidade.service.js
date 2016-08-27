(function() {
    'use strict';
    angular
        .module('hackathonApp')
        .factory('Especialidade', Especialidade);

    Especialidade.$inject = ['$resource'];

    function Especialidade ($resource) {
        var resourceUrl =  'api/especialidades/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
