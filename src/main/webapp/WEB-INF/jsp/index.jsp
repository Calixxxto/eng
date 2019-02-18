<%@ page isELIgnored="false" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Автоматизированная Система Управления Фонда</title>
    <link rel="stylesheet" href="${rootURL}resources/css/app/app.css"/>
    <link rel="shortcut icon" href="${rootURL}resources/css/images/favicon.ico" type="image/vnd.microsoft.icon"/>
    <script>
        var _ver = '${manifest['Implementation-Version']}-${manifest['Implementation-Revision']}';
    </script>
</head>

<body>

<div ng-controller="APPController" style="display:none"></div>

<div ng-controller="ModalController">
    <script type="text/ng-template" id="modalTemplate.html">
        <div class="modal-header" ng-show="!config.noHeader"><a class="close" href="#" ng-click="close()">×</a>
            <h3>Капитальный Ремонт</h3>
        </div>
        <div class="modal-body" ng-bind-html="config.body"></div>
        <div class="modal-footer" ng-show="!config.noFooter">
            <button ng-repeat="(ind, button) in config.buttons" class="btn {{button.cls}}" ng-click="button.click()">
                {{ button.text }}
            </button>
        </div>
    </script>
</div>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a ui-sref-active="active" class="navbar-brand" ui-sref="home"
               title="Некоммерческая организация «Фонд – региональный оператор капитального ремонта общего имущества в многоквартирных домах»"></a>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li ui-sref-active="active"><a ui-sref="houses" ng-show="AuthService.isAccessTo('houses')" ng-cloak><i class="fa fa-search"></i>&nbsp;Поиск дома</a></li>
                <li ui-sref-active="active"><a ui-sref="regProgram" ng-show="AuthService.isAccessTo('regProgram')" ng-cloak><i class="fa fa-search"></i>&nbsp;Рег. программа</a></li>
                <li ng-class="{active: $state.includes('repairs')}"><a ui-sref="repairs.list" ng-show="AuthService.isAccessTo('repairs')" ng-cloak><i class="fa fa-book"></i>&nbsp;Крат. план</a></li>

            </ul>
            <ul class="nav navbar-nav navbar-right clearfix" ng-show="AuthService.isAuth()" ng-cloak>

                <li ui-sref-active="active">
                    <a ui-sref="users.edit" ng-cloak>
                        <i class="fa fa-user"></i>
                        <span style="width: 65px; display: inline-block; overflow: hidden;" tooltip="{{AuthService.getUser().login}}" tooltip-placement="bottom" ng-bind="AuthService.getUser().login"></span>
                    </a>
                </li>

                <li ui-sref-active="active"><a ui-sref="logout" ng-show="AuthService.isAuth()" ng-cloak tooltip="Выход" tooltip-placement="bottom"><i class="fa fa-sign-out"></i></a></li>
            </ul>
        </div>
    </div>

    <div ui-view></div>
</div>


<% if (request.getServerName().equals("127.0.0.1") || request.getServerName().equals("localhost")) { %>
<script src="${rootURL}resources/js/libs/requirejs/require.js" data-main="${rootURL}resources/js/app.js"></script>
<% } else {%>
<script src="${rootURL}resources/js/libs/requirejs/require.js"
        data-main="${rootURL}resources/js/all.js?${manifest['Implementation-Version']}-${manifest['Implementation-Revision']}"></script>
<% } %>

</body>
</html>

