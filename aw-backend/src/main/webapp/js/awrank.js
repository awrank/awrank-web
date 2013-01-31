var contextPath = null;

var l_dic_language = null;
var l_dic_all = null;
var l_dic = null;

var oldPost = {};

function setLanguage(language_code) {
    l_dic_language = language_code;
    l_dic = l_dic_all[l_dic_language];
}

function setContextPath(newContextPath) {
    contextPath = newContextPath + '/api/';
}

function getMessage(text) {
    if (l_dic_all == undefined) {
        $.ajax(
            contextPath + 'dictionary/list', {
                type: 'post',
                async: false,
                dataType: 'json',
                success: function (data) {
                    var list = data.dictionaryList;
                    l_dic_all = {};
                    for (var i = 0; i < list.length; i++) {
                        var item = list[i];
                        var l_dic_all_item = l_dic_all[item.language_code];
                        if (l_dic_all_item == undefined) {
                            l_dic_all_item = {};
                            l_dic_all[item.language_code] = l_dic_all_item;
                        }
                        l_dic_all_item[item.code] = item.text;
                    }
                    setLanguage('RU');
                },
                error: function (errorData, textStatus) {
                    alert('Error init dictionary');
                }
            });
    }
    var m = l_dic[text];
    if (m == undefined) {
        m = text;
    }
    return m;
}

function postJson(url, data, success, disableElementId) {
    loadingShow(disableElementId);
    var dataJson = null;
    if (data != null)
        dataJson = $.toJSON(data);
    $.ajax(
        contextPath + url, {
            type: 'post',
            data: {data: dataJson},
            dataType: 'json',
            success: success,
            error: function (errorData, textStatus) {
                switch (errorData.status) {
                    case 401:
                        oldPost.push({url: url, data: data, success: success, disableElementId: disableElementId});
                        //TODO
                        alert(getMessage('ERROR') + '401');
//                        loginShow();

                        break;
                    case 403:
                        alert(getMessage('ERROR') + ': ' + getMessage('ERROR_ACCESS'));
                        break;
                    case 500:
                        var text = errorData.responseText
                        if (text != null) {
                            var o1 = $.evalJSON(text);
                            if (o1 != null && o1.error != null && o1.error.message != null) {
                                text = getMessage(o1.error.message);
                            }
                        }
                        alert(getMessage('ERROR') + ': ' + text);
                        break;
                    default:
                        alert(getMessage('ERROR') + ': ' + getMessage('ERROR_NETWORK'));
                        break;
                }
            },
            complete: function () {
                loadingHide();
            }
        });
}

var loadingIndex = 0;

function loadingShow(id) {
    var loading = $('#loading');
    if (loading[0] == null) {
        loading = $('<table id="loading">\n' +
            '<tr>\n' +
            '<td align="center" valign="middle">\n' +
            'Loading...\n' +
            '</td>\n' +
            '</tr>\n' +
            '</table>');
        $('body').append(loading);
    }
    var top = 0;
    var left = 0;
    var width = '100%';
    var height = '100%';
    if (id !== undefined || id != null) {
        var el = $('#' + id);
        if (el != null) {
            top = el.offset().top;
            left = el.offset().left;
            width = el.width();
            height = el.height();
        }
    }
    loading.animate({opacity: 'show', top: top, left: left, width: width, height: height}, 100);
    loadingIndex++;
}

function loadingHide() {
    loadingIndex--;
    if (loadingIndex == 0)
        $('#loading').animate({opacity: 'hide'}, 200);
}

function dateTimeToString(date) {
    if (!(date instanceof Date)) {
        date = new Date(date);
    }

    var f00 = function (i) {
        if (i < 10)
            i = '0' + i;
        return i;
    }
    return f00(date.getDate()) + '.' +
        f00(date.getMonth() + 1) + '.' +
        date.getFullYear() + ' ' +
        f00(date.getHours()) + ':' +
        f00(date.getMinutes()) + ':' +
        f00(date.getSeconds());
}