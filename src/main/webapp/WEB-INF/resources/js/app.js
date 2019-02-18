requirejs.config({
    'baseUrl': 'resources/js',
    'paths': {
        'angular': 'libs/angularjs/angular',
        'angular-resource': 'libs/angularjs/angular-resource',
        'angular-locale': 'libs/angularjs/angular-locale_ru-ru',
        'angular-block-ui': 'libs/angular-block-ui/angular-block-ui',
        'angular-animate': 'libs/angular-animate/angular-animate',
        'angular-toastr': 'libs/angular-toastr/angular-toastr',
        'ui-bootstrap': 'libs/ui-bootstrap/ui-bootstrap-tpls-0.9.0',
        'ui-router': 'libs/ui-router/angular-ui-router',
        'ng-table': 'libs/ng-table/ng-table',
        'highcharts': 'libs/highcharts/highcharts-all',
        'jquery' : 'libs/jquery/jquery-2.1.1.min',
        'select2' : 'libs/select2/select2',
        'select2_locale_ru' : 'libs/select2/select2_locale_ru',
        'ui-select2' : 'libs/ui-select2/select2',
        'slider' : 'libs/slider/angular.rangeSlider',
        'bootstrap' : 'libs/bootstrap/bootstrap',
        'nav-bar-collapse-fix' : 'libs/bootstrap/nav-bar-collapse-fix',
        'angular-file-upload': 'libs/angular-file-upload/angular-file-upload',
        'log4javascript': 'libs/log4javascript/log4javascript',
        'yaMap': 'libs/ymap/ya-map-2.1',
        'ngMask' : 'libs/ngMask/ngMask'
    },
    'shim' : {
        'angular' : {'exports' : 'angular', deps: ['jquery']},
        'jquery': {'exports' : 'jquery'},
        'bootstrap': {'exports' : 'bootstrap', deps: ['jquery']},
        'nav-bar-collapse-fix': {deps: ['bootstrap']},
        'angular-resource': ['angular'],
        'angular-locale': ['angular'],
        'angular-block-ui': ['angular'],
        'angular-animate': ['angular'],
        'angular-toastr': ['angular'],
        'ui-bootstrap' : ['angular'],
        'ui-router' : ['angular'],
        'ng-table' : ['angular'],
        'select2_locale_ru' : ['select2'],
        'ui-select2' : ['angular', 'select2_locale_ru'],
        'slider' : ['angular'],
        'yaMap' : ['angular'],
        'ngMask' : ['angular']

    }
});

require(['angular', 'angular-locale', 'angular-block-ui', 'angular-toastr', 'highcharts', 'routes/main', 'bootstrap',
         'nav-bar-collapse-fix', 'angular-file-upload', 'yaMap', 'ngMask'] , function (angular) {
    angular.bootstrap(document , ['app']);
});