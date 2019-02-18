define(['angular'], function(angular) {
    'use strict';

    var services = angular.module('app.utils', []);

    services.factory('KAPUtils', function(ngTableParams, $q, $filter) {

        return {

            'generateYearListFromYear' : function(startYear){
                var fixedDate = new Date(Date.now());
                var yearList = [];

                if(fixedDate.getFullYear() < startYear){
                    console.log("Error: starting year (" + startYear + ") can't be greater than current year (" + fixedDate.getFullYear() +")");
                    return yearList;
                }

                for (var i = fixedDate.getFullYear() + 1; i >= startYear; i--) {
                    yearList.push(i);
                }

                return yearList;
            },

            'generateYearListPeriod' : function(startYear, endYear){

                var yearList = [];

                if(endYear < startYear){
                    console.log("Error: starting year (" + startYear + ") can't be greater than ending year (" + endYear +")");
                    return yearList;
                }

                for (var i = endYear; i >= startYear; i--) {
                    yearList.push(i);
                }

                return yearList;
            },

            'fixDate' : function(d) {
                return angular.isDate(d) ? d.getTime() : d;
            },

            'toDbDate' : function(d) {
                return angular.isDate(d) ? $filter('date')(d, 'yyyy-MM-dd') : null;
            },

            'toDateFormat' : function(d, format) {
                return angular.isDate(d) ? $filter('date')(d, format) : null;
            },

            // KAP-1053
            'toDbDateWithTime' : function(d) {
                return angular.isDate(d) ? $filter('date')(d, 'yyyy-MM-dd HH:mm:ss') : null;
            },

            // makes date from string "2014-01" to "янв. 2014"
            'dateFromNumToName': function(str, noChange) {
                var date = '';
                if (angular.isDate(str)) {
                    date = str;
                } else if (angular.isString(str)) {
                    var parsed = str.split('-');
                    if (parsed.length == 2) {
                        date = new Date(parsed[0], parseInt(parsed[1], 10) - 1, 15);
                    }
                }
                if (angular.isDate(date)) {
                    var formatted = $filter('date')(date, "MMM yyyy");
                    if (!noChange) {
                        formatted = formatted.replace('марта', 'мар.').replace('мая', 'май').replace('июня', 'июн.').replace('июля', 'июл.');
                    }
                    return formatted;
                }
                return str;
            },

            'monthFromDate': function(date, lowerCase) {
                var month = {
                    nominative: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь']
                };
                var returnMonth = "";
                if (angular.isDate(date)) {
                    var returnMonth = month.nominative[date.getMonth()]
                }

                return lowerCase ? returnMonth.toLowerCase() : returnMonth;
            },

            'focusById'  : function(id) {
                setTimeout(function() {
                    jQuery('#'+id).focus();
                }, 100);
            },

            'selectTab' : function(tab, tabset) {
                if(tab && tabset.hasOwnProperty(tab)) {
                    for(var i in tabset) {
                        tabset[i] = false;
                    }
                    tabset[tab] = true;
                }
            },

            'gridView' : function(m, p, s, ap, settings, f) {

                function getSort(o) {
                    var r = [], i;
                    for(i in o) {
                        r.push(i+','+o[i]);
                    }
                    return r;
                };

                return new ngTableParams({page:1, count:10, sorting:s, filter: f?f:{}}, {total:0,

                    getData: function ($defer, params) {

                        if(settings && settings.notLoadDataFirstTime === true) {
                            settings.notLoadDataFirstTime = false;
                            return $q.reject();
                        }

                        params.isLoading = true;

                        p.page = params.page() - 1;
                        p.size = params.count();
                        p.sort = getSort(params.sorting());

                        //p.filter = getSort(params.filter());

                        var f = params.filter();
                        for(var i in f) {
                            p[i] = f[i];
                            if(angular.isDate(p[i])) {
                                p[i] = $filter('date')(p[i], 'yyyy-MM-dd');
                            }
                        }

                        if(ap && ap.additionParams) {
                            for(var i in ap.additionParams) {
                                p[i] = ap.additionParams[i];
                            }
                        }

                        m(p).$promise.then(function(r) {

                            params.isLoading = false;

                            if(!r.content || !r.content.length) {
                                params.total(0);
                                $defer.resolve([]);
                                return $q.reject();

                            }
                            if(r.page.totalPages < params.$params.page) {
                                if(params.page() - 1 > 0) {
                                    params.page(params.page() - 1);
                                }
                                return $q.reject();
                            }
                            params.total(r.page.totalElements);
                            $defer.resolve(r.content);
                        });
                    }
                });

            },

            'sum': function(array, property) {
                var sum = 0;

                if (Array.isArray(array)) {
                    if (property) {
                        for(var i=0; i<array.length; i++) {
                            if (array[i].hasOwnProperty(property)) {
                                sum += array[i][property];
                            }
                        }
                    } else {
                        for(var i=0; i<array.length; i++) {
                            sum += array[i];
                        }
                    }
                }

                return sum;
            },
            /* merges two or more arrays by some key
            * e.g. arr1 = [{id: 1, val: 'v1'}, {id: 2, val: 'v2'}] and arr2 = [{id: 1, val: 'val1'}, {id: 3, val: 'v3'}]
            * mergeArrayById(arr1, arr2) will return [{id: 1, val: 'v1'}, {id: 2, val: 'v2'}, {id: 3, val: 'v3'}]
            * */
            'mergeArrayById': function(array1, array2, fieldName) {
                var result = [], map = {},
                    idField = angular.isArray(arguments[arguments.length - 1]) ? 'id' : arguments[arguments.length - 1];

                for (var i=0; i<arguments.length; i++) {
                    var arr = arguments[i];
                    if (angular.isArray(arr)) {
                        for (var j=0; j<arr.length; j++) {
                            var key = arr[j][idField];
                            if (!map[key]) {
                                map[key] = 1;
                                result.push(arr[j]);
                            }
                        }
                    }
                }
                return result;
            }
        };
    });
});