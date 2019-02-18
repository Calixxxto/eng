define(['angular', 'log4javascript'], function(angular, log4javascript) {
    'use strict';

    var services = angular.module('app.logger', []);

    services.factory('$log', function () {
          var logger = log4javascript.getLogger('kap-ui');

          var ajaxAppender = new log4javascript.AjaxAppender('system/client/log');
          logger.addAppender(ajaxAppender);

          return logger;
    });
});