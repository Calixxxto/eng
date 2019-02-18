define(['angular'], function(angular) {
    'use strict';

    var services = angular.module('app.users', ['ngResource']);

    services.factory('UsersFactory', function ($resource) {
        return $resource('repository/users', {}, {
            query: {method: 'GET'},
            findAllUsers: {method: 'GET', url: 'api/users'}
        })
    });

    services.factory('UserFactory', function ($resource) {
        return $resource('repository/users/:id', {}, {
            show: {method: 'GET'}
        })
    });

});