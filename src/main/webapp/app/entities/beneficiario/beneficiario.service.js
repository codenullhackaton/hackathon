(function() {
    'use strict';
    angular
        .module('hackathonApp')
        .factory('Beneficiario', Beneficiario);

    Beneficiario.$inject = ['$resource'];

    function Beneficiario ($resource) {
        var resourceUrl =  'api/beneficiarios/:id';

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
