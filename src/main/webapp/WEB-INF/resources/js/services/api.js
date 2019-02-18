define(['angular'], function(angular) {
    'use strict';

    var services = angular.module('app.api', ['ngResource']);

    services.factory('APIFactory', function ($resource) {
        return $resource('', {}, {
            reference: {method: 'GET', url: 'api/reference'},
            getAppInfo: {method: 'GET', url: 'api/root/appinfo'}
        });
    });


    /* List of service urls. Returning data shouldn't differ during work of the application.
    *  Include here your url with key and you may
    *  hope that you will have the following static method of ListService
    *  E.g. put
    *  {projects: {method: 'GET', url: 'repository/projects'}
    *  So, you can call ListService.getProjects or ListService.getData('projects')
    * */
    var listUrls = {
        projectWorkStatuses: {method: 'GET', url: 'repository/repairstatuses/search/findByProjectSeqNotNullOrderByProjectSeqAsc'},
        smWorkStatuses: {method: 'GET', url: 'repository/repairstatuses/search/findBySmSeqNotNullOrderBySmSeqAsc'},

        repairTypes: {method: 'GET', url: 'repository/repairtypes'},

        districts: {method: 'GET', url: 'repository/districts'},
        districtsReg: {method: 'GET', url: 'repository/districtsreg'},
        repairWorkKinds: {method: 'GET', url: 'repository/repairWorkKind'},

        globalDepartments: {method: 'GET', url: 'repository/userDepartmentsGlobal/search/findByRepairWorkShowTrueOrderByName'},

        teiElements: {method: 'GET', url: 'repository/teielements', params: {page: 0, size: 250}}

        // contractors: {method: 'GET', url: 'repository/contractors'}

    };

    // service to load all lists, which use more than one time
    services.factory('ListFactory', function ($resource) {
        return $resource('', {}, listUrls);
    });

    services.service('ListService', function(ListFactory) {
        var list_data = {},
            countRequest = 0,
            countResponse = 0;


        function getData(o) {
            return list_data[o] ? list_data[o] : null;
        }

        this.getData = function (o) {
            return getData(o);
        };

        this.initData = function(thenDo) {
            var th = this;
            angular.forEach(listUrls, function(value, key){
                countRequest ++;
                ListFactory[key]().$promise.then(function (r) {
                    countResponse++;
                    list_data[key] = r.content ? r.content : r;
                    //console.log(key + ' = ');
                    //console.log(r);
                    var methodName = 'get' + key.charAt(0).toUpperCase() + key.slice(1);
                    th[methodName] = function() {
                        return getData(key);
                    };
                    if (countResponse == countRequest && angular.isFunction(thenDo)) {
                        thenDo();
                    }
                });
            });
        };
    });

    services.factory('SaveStateFactory', function () {
        return {
            set: function(key, value) {
                this[key] = value;
            },
            get: function(key) {
                return (this[key] || '');
            }
        }
    });

    services.service('StaticDataService', function(APIFactory) {
        var _data = null;

        function getData(o) {
            return _data && _data[o] ? _data[o] : null;
        }

        this.initData = function() {
            return APIFactory.reference().$promise.then(function (r) {
                _data = r;
            });
        };

        this.getRoles = function() {
            return getData('roles');
        };

        this.getBillingSessionStatuses = function() {
            return getData('billingSessionStatuses');
        };

        this.getPaymentSessionStatuses = function() {
            return getData('paymentSessionStatuses');
        };

        this.getPaymentRegistryUploadStatuses = function() {
            return getData('paymentRegistryUploadStatuses');
        };

        this.getRepairWorkTypes = function() {
            return getData('repairWorkTypes');
        };

        this.getRepairWorkSources = function() {
            return getData('repairWorkSources');
        };

        this.getRepairDecisionTypes = function() {
            return getData('repairDecisionTypes');
        };

        this.getRepairDecisionForms = function() {
            return getData('repairDecisionForms');
        };

        this.getRepairPlanStatuses = function() {
            return getData('repairPlanStatuses');
        };

    });

    services.service('BillingChartsService', function() {

        var colors = {
            fee: {vckp:'#7cb5ec', pes:'#ff6600', other:'#8085e9'},
            payment: {vckp:'#f15c80', pes:'#ed561b', pes_cpp:'#6fc061', pes_cpp9:'#50b432',sber:'#37e8c3',
                      post:'#434348', pscb:'#ceb511', open:'#f7a35c', other:'#8085e9'}
        };

        this.getBillingChartsColors = function() {
            return colors;
        }

        this.getSeriesConfig = function(data) {
            var categories = [], config = {};

            angular.forEach(data, function(v, k) {
                categories.push(k);

                angular.forEach(v.summary, function(item) {

                    var bCode = item.billingCode,
                        agentCode = item.agent && item.agent.code ? item.agent.code : 'other';

                    if (!config[bCode]) {
                        config[bCode] = {};
                    }
                    if (!config[bCode][agentCode]) {
                        config[bCode][agentCode] = {
                            name: bCode==='fee' ?
                                (agentCode==='other' ? 'Прочие начисления':'Начисления '+item.agent.name) :
                                (agentCode==='other' ? 'Остальные взносы':'Взносы '+item.agent.name)
                        };
                        if (colors[bCode] && colors[bCode][agentCode]) {
                            config[bCode][agentCode].color = colors[bCode][agentCode];
                        }
                    }
                    if (!config[bCode][agentCode].data) {
                        config[bCode][agentCode].data = [];
                    }
                    config[bCode][agentCode].data.push(item.amount);
                });
            });

            return {categories: categories, config: config};
        }


    });

});