define(['modules/app'], function(app) {
    'use strict';

    app.directive('uniqueLogin', ['UsersFactory', '$timeout', function(UsersFactory, $timeout) {
        return {
            require: 'ngModel',
            scope: true,
            restrict: 'A',
            link: function (scope, el, attrs, ctrl) {

                var waitTime = scope.$eval(attrs.uniqueLoginWaitMs) || 500;
                var oldValue = null;
                var timeoutPromise;

                var findLogin = function(v) {
                    UsersFactory.findByLogin({login: v}, function (r) {
                        var isValid = true;
                        if(r && r.login){
                            isValid = false;
                        }
                        ctrl.$setValidity('uniqueLogin', isValid);
                    });
                };

                ctrl.$parsers.push(function(inputValue) {
                    if(oldValue === null) {
                        oldValue = ctrl.$modelValue;
                    }
                    if(inputValue && inputValue != oldValue && inputValue.length > 4) {

                        if(timeoutPromise) {
                            $timeout.cancel(timeoutPromise);
                        }
                        timeoutPromise = $timeout(function () {
                            findLogin(inputValue);
                        }, waitTime);

                    } else {
                        ctrl.$setValidity('uniqueLogin', true);
                    }
                    return inputValue;
                });
            }
        };
    }]);

});