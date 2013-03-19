/**
 * @author Alex Polyakov
 * localize ui
 */
var l_dic_language = null;
var l_dic_all = null;
var l_dic = null;

function setLanguage(language_code) {
	initDictionaryMessage();
	l_dic_language = language_code;
	l_dic = l_dic_all[l_dic_language];
	// replace tag contents
	$('[lngt]').each(function () {
		var o = $(this);
		var s = getMessage(o.attr('lngt'));
		o.text(s);
	});
	// set input value
	$('[lngv]').each(function () {
		var o = $(this);
		var s = getMessage(o.attr('lngv'));
		o.val(s);
	});
	// set attribute placeholder
	$('[lngp]').each(function () {
		var o = $(this);
		var s = getMessage(o.attr('lngp'));
		o.attr('placeholder', s);
	});
	// set attribute title
	$('[lngtt]').each(function () {
		var o = $(this);
		var s = getMessage(o.attr('lngtt'));
		o.attr('title', s);
	});
}

function localizeLoadContent(content) {
	// replace tag contents
	
	content.find('[lngt]').each(function () {
		var o = $(this);
		var s = getMessage(o.attr('lngt'));
		o.text(s);
	});
	// set input value
	content.find('[lngv]').each(function () {
		var o = $(this);
		var s = getMessage(o.attr('lngv'));
		o.val(s);
	});
	// set attribute placeholder
	content.find('[lngp]').each(function () {
		var o = $(this);
		var s = getMessage(o.attr('lngp'));
		o.attr('placeholder', s);
	});
	// set attribute title
	content.find('[lngtt]').each(function () {
		var o = $(this);
		var s = getMessage(o.attr('lngtt'));
		o.attr('title', s);
	});
}

function initDictionaryMessage() {
	if (l_dic_all == null) {
		reloadDictionaryMessage();
	}
}

function reloadDictionaryMessage() {
	if (contextPath != null) {
		$.ajax(
			contextPath + 'rest/dictionary', {
				type: 'get',
				async: false,
				dataType: 'json',
				success: function (list) {
					l_dic_all = {};
					for (var i = 0; i < list.length; i++) {
						var item = list[i];
						var l_dic_all_item = l_dic_all[item.language];
						if (l_dic_all_item == undefined) {
							l_dic_all_item = {};
							l_dic_all[item.language] = l_dic_all_item;
						}
						l_dic_all_item[item.code] = item.text;
					}
				},
				error: function (errorData, textStatus) {
					awrankDebug('Error init dictionary');
				}
			});
	} else {
		awrankDebug('Error load dictionary before set contextPath');
	}
}

function getMessage(text) {
	initDictionaryMessage();
	var m = null;
	if (l_dic != null) {
		m = l_dic[text];
	}
	if (m == null) {
		m = text;
	}
	return m;
}
