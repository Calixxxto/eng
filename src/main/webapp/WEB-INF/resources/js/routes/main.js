define([

    'modules/app',
    'controllers/LoginController',
    'controllers/LogoutController',
    'controllers/UsersController'

], function (app,
             LoginController,
             LogoutController,
             UsersController
) {
    'use strict';

    app.config(['$stateProvider', '$urlRouterProvider',

        function ($stateProvider, $urlRouterProvider) {

            $urlRouterProvider.otherwise('/home');
            $urlRouterProvider.when('/users', '/users/list');

            $stateProvider.state('login', {
                url: '/login',
                templateUrl: 'resources/partials/login.html?' + _ver,
                controller: LoginController
            })
                .state('logout', {
                    url: '/logout',
                    controller: LogoutController
                })
                .state('inprogress', {
                    url: '/inprogress',
                    templateUrl: 'resources/partials/inprogress.html?' + _ver
                })
                .state('home', {
                    url: '/home',
                    templateUrl: 'resources/partials/home.html?' + _ver
                })

                .state('users', {
                    url: '/users',
                    templateUrl: 'resources/partials/users/users.html?' + _ver
                })
                .state('users.list', {
                    url: '/list',
                    templateUrl: 'resources/partials/users/users.list.html?' + _ver,
                    controller: UsersController
                })
                .state('users.create', {
                    url: '/create',
                    templateUrl: 'resources/partials/users/users.edit.html?' + _ver,
                    controller: UsersController
                })
                .state('users.edit', {
                    url: '/edit',
                    templateUrl: 'resources/partials/users/users.edit.html?' + _ver,
                    controller: UsersController
                })
                .state('users.update', {
                    url: '/update/:id',
                    templateUrl: 'resources/partials/users/users.edit.html?' + _ver,
                    controller: UsersController
                })
                .state('users.view', {
                    url: '/:id',
                    templateUrl: 'resources/partials/users/users.view.html?' + _ver,
                    controller: UsersController
                })

        }

    ]);

    app.config(['$provide', '$httpProvider',
        function ($provide, $httpProvider) {
            $provide.factory('errHttpInterceptor', function ($q, $rootScope, HttpErrorHandlerService) {
                return {
                    'responseError': function (rejection) {
                        if (rejection.status == 401) {
                            $rootScope.APPController.goLogout();
                        }
                        else if (rejection.status == 403) {
                            var forbiddenUrl = rejection.config.url;
                            if (forbiddenUrl === 'api/login') {
                                // KAP-1834 user is disabled
                                $rootScope.ModalController.error('<b>Учетная запись заблокирована. Вход в систему невозможен. </b>' +
                                    'Для получения доступа к системе необходимо обратиться к администратору АСУ.');
                            } else {
                                $rootScope.ModalController.error('Ошибка доступа');
                                $rootScope.APPController.goHome();
                            }
                        }
                        else if (rejection.status == 500 || rejection.status == 415) {
                            var customHandler = HttpErrorHandlerService.get(rejection.config.method, rejection.config.url);
                            if (customHandler) {
                                customHandler();
                            } else {
                                var msg = 'Произошла непредвиденная ошибка.',
                                    title = '<b>' + rejection.status + ' ' + rejection.statusText + '</b>';
                                if (rejection.data) {
                                    if (rejection.data.indexOf("flat account is closed") > -1) {
                                        title = '<b>Операция невозможна!</b>';
                                        msg = "Лицевой счёт закрыт или уже разделён.";
                                    }
                                }
                                $rootScope.$root['ModalController'].error(title + '<br />' + msg,
                                    function () {
                                        var w = window.open();
                                        w.document.write(rejection.data);
                                        w.stop();
                                    }
                                );
                            }
                        }
                        else if (rejection.status == 503) {
                            $rootScope.ModalController.error('Операция временно недоступна');
                        }
                        else if (rejection.data && rejection.data.message) {
                            $rootScope.$root['ModalController'].error('<b>' + rejection.status + ' ' + rejection.statusText + '</b><br />' + rejection.data.message);
                        }
                        return $q.reject(rejection);
                    }
                }
            });

            $httpProvider.interceptors.push('errHttpInterceptor');

            // KAP-1053
            $provide.factory('dateHttpInterceptor', function ($q, $rootScope) {
                return {
                    'request': function (config) {
                        if ((config.method == 'POST' || config.method == 'PATCH' || config.method == 'PUT') && config.data) {
                            angular.forEach(config.data, function (value, key) {
                                if (angular.isDate(value)) {
                                    config.data[key] = $rootScope.KAPUtils.toDbDateWithTime(value);
                                }
                            });
                        }
                        return config;
                    }
                }
            });

            $httpProvider.interceptors.push('dateHttpInterceptor');
        }
    ]);

});