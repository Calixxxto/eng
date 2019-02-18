define(['services/auth'], function() {
    'use strict';

    function LogoutController($scope, $state, AuthFactory) {

        AuthFactory.logout().$promise.then(function () {
            $scope.$root.AuthService.resetUser();
            $state.go('login');
        });
    }

    LogoutController.$inject = ['$scope', '$state', 'AuthFactory'];
    return LogoutController;
});