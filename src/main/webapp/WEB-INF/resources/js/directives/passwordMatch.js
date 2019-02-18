define(['modules/app'], function(app) {
    'use strict';

    app.directive('passwordMatch', [function () {
        return {
            restrict: 'A',
            scope: true,
            require: 'ngModel',
            link: function (scope, el , attrs, ctrl) {
                var checker = function () {
                    var p1 = scope.$eval(attrs.ngModel), p2 = scope.$eval(attrs.passwordMatch);
                    return p1 == p2;
                };
                scope.$watch(checker, function(n) {
                    ctrl.$setValidity("unique", n);
                });
            }
        };
    }]);

});