define(['angular'], function(angular) {
    'use strict';

    var services = angular.module('app.auth', ['ngResource']);

    services.factory('AuthFactory', function ($resource) {
        return $resource('api/login', {}, {
            login: {method: 'POST'},
            logout: {method: 'POST', url: 'api/logout'},
            loggedin: {method: 'GET', url: 'api/loggedin'},
            inprogress: {method: 'GET', url: 'api/billing/session/inprogress'}
        });
    });

    services.service('AuthService', function($filter) {
        var _user = null;

        var pmap = {

        };

        this.setUser = function(u) {
            return _user = u;
        };

        this.getUser = function() {
            return _user;
        };

        this.resetUser = function() {
            _user = null;
        };

        this.isAuth = function() {
            return _user !== null;
        };

        this.isAdmin = function() {
            return ($filter('filter')(_user.roles, {roleName:'ADMIN'}, true)).length > 0;
        };

        this.isAccessTo = function(obj, isWrite) {

            isWrite = isWrite !== true ? false : true;

            if(!this.isAuth()) {
                return false;
            }

            if(!pmap[obj]) {
                return true;
            }

            var i, r;
            for(i=0;i<_user.roles.length;i++) {
                r = pmap[obj][_user.roles[i].roleName];
                if(r && ((!isWrite && r >= 1) || (isWrite && r == 2))) {
                    return true;
                }
            }

            return false;
        };

        this.isWriteAccessTo = function(obj) {
            return this.isAccessTo(obj, true);
        }

    });

});