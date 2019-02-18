define([
    'directives/passwordMatch',
    'directives/uniqueLogin',
    'services/users',
    'services/logger',
    'services/handlers'
], function() {
    'use strict';

    function UsersController($scope, $state, $stateParams, UsersFactory) {

        UsersFactory.findAllUsers().$promise.then(function(r) {
            $scope.users = r;
        });
    }

    UsersController.$inject = ['$scope', '$state', '$stateParams', 'UsersFactory'];
    return UsersController;
});