define([
    'services/auth',
    'directives/autoFocus',
    'services/api'
], function() {
    'use strict';

    function LoginController($scope, $state, AuthFactory, ListService) {

        $scope.DoLogin = function() {
            var mc = $scope.$root['ModalController'],
                username = document.getElementById('username').value,
                password = document.getElementById('password').value;

            if(!username || !password) {
                return mc.error('Вы должны указать логин и пароль.');
            }

            AuthFactory.login({login: username, password: password}).$promise.then(function(r) {
                $scope.password = null;
                if (r.id) {
                    delete r.password;
                    $scope.$root.AuthService.setUser(r);
                    $scope.$root.StaticDataService.initData().then(function() {
                        ListService.initData(function() {
                            if($scope.$root.previousState) {
                                $state.go($scope.$root.previousState.name, $scope.$root.previousState.params);
                            }
                            else {
                                $state.go('home');
                            }
                        });
                    });
                } else {
                    mc.error('Ошибка авторизации. Неправильный логин или пароль.');
                }
            });
        };
    }

    LoginController.$inject = ['$scope', '$state', 'AuthFactory', 'ListService'];
    return LoginController;
});