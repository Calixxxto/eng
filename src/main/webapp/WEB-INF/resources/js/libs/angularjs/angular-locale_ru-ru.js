'use strict';
angular.module("ngLocale", [], ["$provide", function($provide) {
    var PLURAL_CATEGORY = {ZERO: "zero", ONE: "one", TWO: "two", FEW: "few", MANY: "many", OTHER: "other"};
    $provide.value("$locale", {
        "DATETIME_FORMATS": {
            "AMPMS": [
                "до полудня",
                "после полудня"
            ],
            "DAY": [
                "воскресенье",
                "понедельник",
                "вторник",
                "среда",
                "четверг",
                "пятница",
                "суббота"
            ],
            "MONTH": [
                "января",
                "февраля",
                "марта",
                "апреля",
                "мая",
                "июня",
                "июля",
                "августа",
                "сентября",
                "октября",
                "ноября",
                "декабря"
            ],
            "SHORTDAY": [
                "вс",
                "пн",
                "вт",
                "ср",
                "чт",
                "пт",
                "сб"
            ],
            "SHORTMONTH": [
                "янв.",
                "февр.",
                "марта",
                "апр.",
                "мая",
                "июня",
                "июля",
                "авг.",
                "сент.",
                "окт.",
                "нояб.",
                "дек."
            ],
            "fullDate": "EEEE, d MMMM y 'г'.",
            "longDate": "d MMMM y 'г'.",
            "medium": "dd.MM.yyyy H:mm:ss",
            "mediumDate": "dd.MM.yyyy",
            "mediumTime": "H:mm:ss",
            "short": "dd.MM.yy H:mm",
            "shortDate": "dd.MM.yy",
            "shortTime": "H:mm"
        },
        "NUMBER_FORMATS": {
            "CURRENCY_SYM": "руб.",
            "DECIMAL_SEP": ",",
            "GROUP_SEP": " ",
            "PATTERNS": [
                {
                    "gSize": 3,
                    "lgSize": 3,
                    "macFrac": 0,
                    "maxFrac": 3,
                    "minFrac": 0,
                    "minInt": 1,
                    "negPre": "-",
                    "negSuf": "",
                    "posPre": "",
                    "posSuf": ""
                },
                {
                    "gSize": 3,
                    "lgSize": 3,
                    "macFrac": 0,
                    "maxFrac": 2,
                    "minFrac": 2,
                    "minInt": 1,
                    "negPre": "-",
                    "negSuf": " ¤",
                    "posPre": "",
                    "posSuf": " ¤"
                }
            ]
        },
        "id": "ru-ru",
        "pluralCat": function (n) {  if (n % 10 == 1 && n % 100 != 11) {   return PLURAL_CATEGORY.ONE;  }  if (n == (n | 0) && n % 10 >= 2 && n % 10 <= 4 && (n % 100 < 12 || n % 100 > 14)) {   return PLURAL_CATEGORY.FEW;  }  if (n % 10 == 0 || n == (n | 0) && n % 10 >= 5 && n % 10 <= 9 || n == (n | 0) && n % 100 >= 11 && n % 100 <= 14) {   return PLURAL_CATEGORY.MANY;  }  return PLURAL_CATEGORY.OTHER;}
    });
}]);