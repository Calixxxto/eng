define(['angular'], function(angular) {
    'use strict';

    var services = angular.module('app.handlers', []);

    services.service('HttpErrorHandlerService', function () {

        var handlers = { GET: [], POST: [], PATCH: [], DELETE: [] };

        this.get = function(method, url) {
            if (!method || !url) return;
            for (var i=0; i<handlers[method].length; i++) {
                if (handlers[method][i].urlRegExp.test(url)) {
                    return handlers[method][i].handler;
                }
            }
            return null;
        };

        this.register = function(method, urlPattern, handler) {
            if (!method || !urlPattern || !handler) return;
            for (var i=0; i<handlers[method].length; i++) {
                if (handlers[method][i].urlPattern === urlPattern) {
                    handlers[method].splice(i,1);
                }
            }
            handlers[method].push({urlPattern: urlPattern, urlRegExp: new RegExp(urlPattern), handler: handler});
        }
    });

});