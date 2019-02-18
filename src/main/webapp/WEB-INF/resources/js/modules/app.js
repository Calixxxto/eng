define([

    'angular',
    'ui-bootstrap',
    'ui-router',
    'ng-table',
    'ui-select2',
    'angular-animate',
    'slider',
    'angular-resource',
    'services/utils',
    'services/auth',
    'services/api',
    'ngMask'

], function(angular) {
    'use strict';

    return angular.module('app', [
        'ui.bootstrap',
        'ui.router',
        'ui.select2',
        'ui-rangeSlider',
        'ngTable',
        'blockUI',
        'ngAnimate',
        'toastr',
        'yaMap',
        'ngMask',
        'angularFileUpload',
        'app.users',
        'app.utils',
        'app.auth',
        'app.logger',
        'app.handlers',
        'app.api'
    ])

    .config(function(blockUIConfigProvider) {
        blockUIConfigProvider.message('Пожалуйста подождите ...');
        blockUIConfigProvider.delay(400);
        //blockUIConfigProvider.autoBlock(false);
        blockUIConfigProvider.requestFilter(function(config) {
            if(config.url.match(/^api\/flat\/search\/findByProfileEmailContains($|\/).*/)){
                return true;
            }
            if( config.url.match(/^api\/location($|\/).*/)
                || config.url.match(/^api\/address\/flats\/search($|\/).*/)
                || config.url.match(/^api\/flat\/search($|\/).*/)
                || config.url === 'api/auctionplan/numberslist'
                || config.url === 'api/auctioncontract/numberslist'
                || config.url === 'api/users/findByNameAndAddress'
                || config.url === 'repository/streetsReg/search/findAllByNameStartsWith'
                || config.url === 'repository/lifts/search/findAllByLiftNumberStartsWithIgnoreCase'
                || config.url === 'repository/contractors/search/findByInnContainsAndActiveTrue'
                || config.url.match(/^api\/dashboard($|\/).*/)
                || config.url.match(/^api\/repair\/search($|\/).*/)
                || config.url.match(/^repository\/flats\/search\/findByAccount($|\/).*/)
                || config.url.match(/^repository\/contractors\/search($|\/).*/)
                || config.url.match(/^repository\/lots\/search\/findByNameContainingIgnoreCase($|\/).*/)
                || config.url.match(/^repository\/banks\/search($|\/).*/)
                || config.url.match(/^repository\/teirepairs($|\/).*/)
                || config.url == 'template/typeahead/typeahead-match.html'
                || config.url == 'resources/partials/houses/banksDropdown.html'
                || config.url == 'resources/partials/houses/flatsSearchTemplate.html'
                || config.url == 'resources/partials/houses/specAccountDropdown.html'
                || config.url == 'resources/partials/flatOwners/flatOwnerDropdown.html'
                || config.url.match(/^flatSearchTemplate($|\/).*/)
                || config.url.match(/^api\/payment\/session($|\/).*/)
                || config.url.match(/^api\/billing\/session\/inprogress($|\/).*/)
                || config.url.match(/^repository\/specAccounts\/search($|\/).*/)
                || config.url.match(/^repository\/flatowners\/search($|\/).*/)) {
                return false;
            }
        });
    })

    .config(function(toastrConfig) {
            angular.extend(toastrConfig, {
                positionClass: 'toast-top-right-ext',
                timeOut: 3000
            });
    })

    .filter('percentage', ['$filter', function($filter) {
        return function(input, decimals) {
            return $filter('number')(input, decimals)+'%';
        };
    }])

    .filter('bool', ['$filter', function($filter) {
        return function(input) {
            return input === true ? 'Да': (input === false ? 'Нет' : '');
        };
    }])

    .filter('sumOfValue', function () {
        return function (data, key) {
            if (typeof (data) === 'undefined' || typeof (key) === 'undefined') {
                return 0;
            }
            if(data.length == 0){
                return 0;
            }
            var sum = 0;
            for (var i = 0; i < data.length; i++) {
                sum = sum + data[i][key];
            }
            return sum;
        }
    })

    // KAP-1973
    .filter('excludeAgents', function() {
        return function (agents, exclude) {
            if (!angular.isArray(agents) || !angular.isArray(exclude)) {
                return agents;
            }

            var criteria = new RegExp('^(' + exclude.join('|') + ')$');

            var result = [];
            angular.forEach(agents, function(agent, key) {
                if (!agent.code || !agent.code.match || !agent.code.match(criteria)) {
                    result.push(agent);
                }
            });

            return result;
        }
    })

    // KAP-861?focusedCommentId=88787
    .filter('excludeAgentsWithParent', function() {
        return function (agents) {
            var result = [];
            angular.forEach(agents, function(agent, key) {
                if (!agent.parentId) {
                    result.push(agent);
                }
            });

            return result;
        }
    })

    .filter('repairWorkFilesFilter', function(){
        return function(data, objectSubId){
            var result = [];
            angular.forEach(data, function (item) {
                if(objectSubId){
                    if(item.objectSubId == objectSubId){
                        result.push(item);
                    }
                }else
                    result.push(item);
            });
            return result;
        }
    })

    .controller('APPController', ['$scope', '$state', '$stateParams', 'AuthFactory', 'AuthService', 'StaticDataService', 'ListService', '$locale', 'toastr', 'KAPUtils',
        function($scope, $state, $stateParams, AuthFactory, AuthService, StaticDataService, ListService, $locale, toastr, KAPUtils) {

        $scope.$root.AuthService = AuthService;
        $scope.$root.$state = $state;
        $scope.$root.StaticDataService = StaticDataService;
        $scope.$root.Locale = $locale;
        $scope.$root.toastr = toastr;
        $scope.$root.KAPUtils = KAPUtils;

        $scope.$root.$on('$stateChangeStart', function(e, toState, toParams, fromState, fromParams) {
            if(!AuthService.isAuth() && toState.name !== 'login' && toState.name !== 'logout') {
                e.preventDefault();
                AuthFactory.loggedin().$promise.then(function(r) {
                    if (r.id) {
                        delete r.password;
                        AuthService.setUser(r);
                        StaticDataService.initData().then(function() {
                            ListService.initData(function() {
                                $state.go(toState.name, toParams);
                            });
                        });
                    } else {
                        $scope.$root.previousState = {name: toState.name, params: toParams};
                        $state.go('login');
                    }
                });
            }
        });

        $scope.goLogin = function() {
            // prevent writing 'login' to the previousState
            if ($state.$current.name != 'login') {
                $scope.$root.previousState = {name: $state.$current.name, params: $state.$current.params};
            }
            $state.go('login');
        };

        $scope.goLogout = function() {
            $state.go('logout');
        };

        $scope.goHome = function() {
            $state.go('home');
        };

        $scope.$root['APPController'] = $scope;
    }])

    .controller('APPNotificationController', ['$scope', '$state', '$stateParams', '$interval', 'AuthFactory', 'AuthService',
        function($scope, $state, $stateParams, $interval, AuthFactory, AuthService) {

        $scope.handlingChargesInterval = null;
        $scope.handlingCharges = false;

        $scope.$root.$on('$stateChangeStart', function(e, toState, toParams, fromState, fromParams) {
            if(AuthService.isAuth() && !$scope.handlingChargesInterval) {

                $scope.handlingChargesInterval = $interval(function() {

                    AuthFactory.inprogress().$promise.then(function(r) {
                        $scope.handlingCharges = r.status;
                    });

                }, 1000 * 30);

            }
        });

    }])

    .controller('ModalController', ['$scope', '$modal', '$sce', '$state', function($scope, $modal, $sce, $state) {

        $scope.open = function(config, options) {

            options = options || {};
            config.body = $sce.trustAsHtml(config.body);

            $modal.open({
                templateUrl: 'modalTemplate.html',
                windowClass: options.windowClass || '',
                controller: function($scope, $modalInstance, config) {
                    $scope.config = config;
                    $scope.close = function() {
                        $modalInstance.dismiss('close');
                    };
                    if(!config.noFooter && config.buttons && config.buttons.length) {
                        for (var i=0;i<config.buttons.length; i++) {
                            var btn = angular.copy(config.buttons[i]);
                            if (!btn.noClose) {
                                config.buttons[i].click = function() {
                                    if(typeof this.action == 'function') {
                                        this.action();
                                    }
                                    $scope.close();
                                }
                            }
                        }
                    }
                },
                resolve: {
                    config: function() {
                        return config;
                    }
                }
            });
        };

        $scope.error = function(message, details) {
            var b = [
                {text: 'OK', cls: 'btn-primary'}
            ];
            if(details) {
                b.unshift({text: 'Подробнее', cls: 'btn-default', action: details});
            }
            $scope.open({
                noHeader: 'true',
                body: '<i class="fa fa-times-circle fa-2x text-danger pull-left"></i><div class="modalText">' + message + '</div>',
                buttons:b
            });
        };

        $scope.confirm = function(message, yes) {
            $scope.open({
                noHeader: 'true',
                body: '<i class="fa fa-question-circle fa-2x text-info pull-left"></i><div class="modalText">' + message + '</div>',
                buttons: [
                    {text: 'Да', action: yes, cls: 'btn-primary'},
                    {text: 'Нет'}
                ]
            });
        };

        $scope.confirmYesAndNo = function(message, yes, no) {
            $scope.open({
                noHeader: 'true',
                body: '<i class="fa fa-question-circle fa-2x text-info pull-left"></i><div class="modalText">' + message + '</div>',
                buttons: [
                    {text: 'Да', action: yes, cls: 'btn-primary'},
                    {text: 'Нет', action: no, cls: 'btn-primary'}
                ]
            });
        };

        $scope.areYouSure = function(yes) {
            $scope.open({
                noHeader: 'true',
                body: '<i class="fa fa-question-circle fa-2x text-danger pull-left"></i><div class="modalText">Вы уверены?</div>',
                buttons: [
                    {text: 'Да', action: yes, cls: 'btn-danger'},
                    {text: 'Нет'}
                ]
            });
        };

        $scope.errorPanel = function(header, message) {
            $scope.open({
                noHeader: 'true',
                body:
                '<div class="panel panel-danger">' +
                    '<div class="panel-heading">' + header + '</div>' +
                    '<div class="panel-body">' + message + '</div>' +
                '</div>',
                buttons: [
                    {text: 'ОК'}
                ]
            });
        };

        $scope.alert = function(message) {
            $scope.open({
                noHeader: 'true',
                body: '<i class="fa fa-info-circle fa-2x text-info pull-left"></i><div class="modalText">'+message+'</div>',
                buttons: [
                    {text: 'ОК'}
                ]
            });
        };

        $scope.$root['ModalController'] = $scope;
    }]);

});