<%@ page import="com.awrank.web.backend.controller.UrlConst" %>
<%--
  User: a_polyakov
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.min.js"></script>
    <script type="text/javascript" src="http://jquery-json.googlecode.com/files/jquery.json-2.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/awrank.js"></script>
</head>
<body onload="fDictionaryListOnload()">

<select id="language">
    <option></option>
</select>
<input type="text" id="code"/>
<br>
<textarea id="text" cols="70"></textarea>
<br>
<input type="button" id="butSave" value="Save" onclick="fDictionaryListSave()" disabled="disabled"/>
<input type="button" id="butAdd" value="Add" onclick="fDictionaryListAdd()"/>

<br>
<br>

<table id="table" border="1">
    <tr>
        <th></th>
        <th>language</th>
        <th>code</th>
        <th>text</th>
        <th></th>
    </tr>
</table>

</body>

<script type="text/javascript">

    var dictionaryList = null;
    var dictionaryListSelect = null;

    function fDictionaryListOnload() {

        setContextPath('<%=request.getContextPath()%>');

        postJson('<%=UrlConst.URL_LANGUAGE_LIST%>', null, function (data) {
            var languageList = data.languageList;
            var language = $('#language');
            for (var i = 0; i < languageList.length; i++) {
                var item = languageList[i];
                language.append('<option value="' + item.name + '">' + item.name + '</option>');
            }
        });

        postJson('<%=UrlConst.URL_DICTIONARY_LIST%>', null, function (data) {
            dictionaryList = data.dictionaryList;
            for (var i = 0; i < dictionaryList.length; i++) {
                var item = dictionaryList[i];
                var index = i + 1;
                fDictionaryListAddRow(index, item);
            }
        });

    }

    function fDictionaryListAddRow(index, dictionary) {
        $('#table').append('<tr id="' + dictionary.id + '">' +
                '<td><a href="#" class="index" onclick="fDictionaryListSelect(' + dictionary.id + ')">' + index + '</a></td>' +
                '<td><a href="#" class="language_code" onclick="fDictionaryListSelect(' + dictionary.id + ')">' + dictionary.language_code + '</a></td>' +
                '<td><a href="#" class="code" onclick="fDictionaryListSelect(' + dictionary.id + ')">' + dictionary.code + '</a></td>' +
                '<td><a href="#" class="text" onclick="fDictionaryListSelect(' + dictionary.id + ')">' + dictionary.text + '</a></td>' +
                '<td><input type="button" onclick="fDictionaryListDelete(' + dictionary.id + ')" value="Delete"></input></td>' +
                '</tr>');
    }

    function fDictionaryListSelect(id) {
        dictionaryListSelect = fDictionaryListFind(id);
        if (dictionaryListSelect != null) {
            $('#language').val(dictionaryListSelect.language_code);
            $('#code').val(dictionaryListSelect.code);
            $('#text').val(dictionaryListSelect.text);
            $('#butSave').removeAttr('disabled');
        }
    }

    function fDictionaryListFind(id) {
        var find = null;
        for (var i = 0; find == null && i < dictionaryList.length; i++) {
            if (dictionaryList[i].id == id)
                find = dictionaryList[i];
        }
        return find;
    }

    function fDictionaryListSave() {
        if (dictionaryListSelect != null) {
            dictionaryListSelect.language_code = $('#language').val();
            dictionaryListSelect.code = $('#code').val();
            dictionaryListSelect.text = $('#text').val();
            postJson('<%=UrlConst.URL_DICTIONARY_UPDATE%>', {dictionary: dictionaryListSelect}, function (data) {
                var tr = $('#' + dictionaryListSelect.id);
                tr.find("td a.language_code").text(dictionaryListSelect.language_code);
                tr.find("td a.code").text(dictionaryListSelect.code);
                tr.find("td a.text").text(dictionaryListSelect.text);
            });
        }
    }
    function fDictionaryListAdd() {
        var newDictionary = {object_type: 'DICTIONARY', language_code: $('#language').val(), code: $('#code').val(), text: $('#text').val()}
        postJson('<%=UrlConst.URL_DICTIONARY_INSERT%>', {dictionary: newDictionary}, function (data) {
            dictionaryList.push(data.dictionary);
            dictionaryListSelect = data.dictionary;
            var index = Number($('#table tr:last td:first a.index').text()) + 1;
            fDictionaryListAddRow(index, dictionaryListSelect);
        });
    }

    function fDictionaryListDelete(id) {
        var dictionary = fDictionaryListFind(id);
        if (dictionary != null) {
            postJson('<%=UrlConst.URL_DICTIONARY_DELETE%>', {dictionary: dictionary}, function (data) {
                $('#' + id).remove();
                if (dictionaryListSelect != null && dictionaryListSelect.id == id) {
                    dictionaryListSelect = null;
                    $('#butSave').attr('disabled', 'disabled');
                }

                for (var i = 0; i < dictionaryList.length; i++) {
                    if (dictionaryList[i].id == id) {
                        dictionaryList.splice(i, 1);
                        i = dictionaryList.length;
                    }
                }

                var trList = $('#table tr');
                for (var i = 0; i < trList.length; i++) {
                    $(trList[i]).find('td a.index').text(i);
                }
            });
        }
    }

</script>
</html>