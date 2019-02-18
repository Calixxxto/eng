define(['modules/app'], function(app) {
    'use strict';

    app.directive('autoFocus', function() {
        return {
            restrict: 'A',
            link: {
                post: function postLink(scope, element, attr) {
                    element[0].focus();
                }
            }
        };
    });

});