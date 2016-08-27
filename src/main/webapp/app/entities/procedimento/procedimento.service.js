(function() {
    'use strict';
    angular
        .module('hackathonApp')
        .factory('Procedimento', Procedimento);

    Procedimento.$inject = ['$resource'];

    function Procedimento ($resource) {
        var resourceUrl =  'api/procedimentos/:id';

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
