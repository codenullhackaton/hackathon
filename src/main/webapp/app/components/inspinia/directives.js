(function () {
    'use strict';
    angular
        .module('hackathonApp')
        .component('sideNavigation', sideNavigation);

    sideNavigation.$inject = ['$timeout'];

    function sideNavigation($timeout) {
        return {
            restrict: 'A',
            link: function (scope, element) {
                // Call the metsiMenu plugin and plug it to sidebar navigation
                $timeout(function () {
                    element.metisMenu();

                });
            }
        };
    };

})();
