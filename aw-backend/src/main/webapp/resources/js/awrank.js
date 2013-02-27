var contextPath = "./";

function setContextPath(newContextPath) {
	contextPath = newContextPath + '/';
}

function awrankGet(url, data, success, disableElementId) {
	awrankAjax('GET', url, data, success, disableElementId);
}
function awrankPost(url, data, success, disableElementId) {
	awrankAjax('POST', url, data, success, disableElementId);
}
function awrankPut(url, data, success, disableElementId) {
	awrankAjax('PUT', url, data, success, disableElementId);
}
function awrankDelete(url, data, success, disableElementId) {
	awrankAjax('DELETE', url, data, success, disableElementId);
}

function awrankAjax(type, url, data, success, disableElementId) {
//	loadingShow(disableElementId);
	var dataJson = null;
	if (data != null) {
		dataJson = $.toJSON(data);
	}
	$.ajax({
		url: contextPath + url,
		type: type,
		data: dataJson,
		dataType: "json",
		contentType: "application/json",
		success: success
	});
}

// save query to execute them after authentication
var oldPost = [];

$(document).ajaxError(function (event, request, settings, exception) {
	switch (request.status) {
		case 401:
			oldPost.push(settings);
			//TODO
			alert('401');
//              loginShow();

			break;
		case 403:
			alertError(getMessage('ERROR'), +getMessage('ERROR_ACCESS'));
			break;
		case 500:
			var text = request.responseText
			if (text != null) {
				try {
					var o1 = $.evalJSON(text);
					if (o1 != null && o1.error != null && o1.error.message != null) {
						text = getMessage(o1.error.message);
					}
				} catch (e) {
				}
			}
			alertError(getMessage('ERROR'), text);
			break;
		default:
			alertError(getMessage('ERROR'), request.status + " : " + getMessage('ERROR_NETWORK'));
			break;
	}
});

function activeAjaxListener() {
	$(document).ajaxStart(function () {
		loadingShow();
	})
	$(document).ajaxComplete(function () {
//$(document).ajaxStop(function () {
		loadingHide();
	})
}

function alertError(title, text) {
	$('div[name=alert-error]').append('<div class="alert alert-error">' +
		'<button type="button" class="close" data-dismiss="alert">&times;</button>' +
		'<h4>' + title + '</h4>' +
		text +
		'</div>');
	awrankDebug(title + text);
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
	if (loadingIndex > 0)
		loadingIndex--;
	if (loadingIndex == 0)
		$('#loading').animate({opacity: 'hide'}, 200);
}


function awrankDebug(o) {
	// in production need commented console.error
//	console.log(o);
	console.error(o);
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