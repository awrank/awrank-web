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
var oldRequest = [];

$(document).ajaxError(function (event, request, settings, exception) {
	switch (request.status) {
		case 401:
			if (settings.url != contextPath + 'user/login' && settings.url != contextPath + 'user/logout') {
				oldRequest.push(settings);
			}
			if ($('#divLogin').length <= 0 || $('#divLogin').hasClass('hidden')) {
				awrankRouter.navigate('login', {trigger: true});
			}
			else {
				alertError(getMessage('ERROR'), getMessage('LOGIN_WRONG_UID_OR_PASSWORD'))
			}
			break;
		case 403:
			alertError(getMessage('ERROR'), getMessage('ERROR_ACCESS'));
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
	$('div[name=alert-error]').each(function () {
		var divError = $(this);
		if (divError.closest('div.hidden').length <= 0) {
			divError.append('<div class="alert alert-error">' +
				'<button type="button" class="close" data-dismiss="alert">&times;</button>' +
				'<h4>' + title + '</h4>' +
				text +
				'</div>')
		}
	});
	awrankDebug(title + text);
}

function alertWarning(title, text) {
	$('div[name=alert-warning]').each(function () {
		var divError = $(this);
		if (divError.closest('div.hidden').length <= 0) {
			divError.append('<div class="alert alert-block">' +
				'<button type="button" class="close" data-dismiss="alert">&times;</button>' +
				'<h4>' + title + '</h4>' +
				text +
				'</div>')
		}
	});
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

// =========================== SEND REQUEST ===================================

function send_user_login(uid, password) {
	awrankPost("user/login", {uid: uid, password: password}, function (data) {
		$('#divLogin').addClass('hidden');
		awrankRouter.navigate('', {trigger: true});
		var options;
		while ((options = oldRequest.shift()) != null) {
			$.ajax(options);
		}
	})
}

function send_user_logout() {
	awrankGet('user/logout');
}


// =============================== UI =========================================
/**
 * find div by id if not exist load from file (append div to target by targetSelector)
 * @param targetSelector
 * @param divId
 * @return div
 */
function fIndexLoad(targetSelector, divId) {
	var div = $('#' + divId);
	if (div[0] == null) {
		div = $('<div id="' + divId + '"/>');
		div.load(divId + '.html', function () {
			localizeLoadContent(div);
			$(targetSelector).append(div);
		});
	}
	div.find('[name=alert-error]').empty();
	div.find('[name=alert-warning]').empty();
	return div;
}
/**
 * find div by id if not exist load from file (append div to rightContent)
 * set div visible (other hide)
 * @param divId
 * @return div
 */
function fIndexRightContentSelectDiv(divId) {
	$('#rightContent>:not(.hidden)').addClass('hidden');
	var div = fIndexLoad('#rightContent', divId);
	div.removeClass('hidden');
	return div;
}
/**
 * select menu item (other deselect)
 * @param menuItemId
 */
function fIndexMenuActive(menuItemId) {
	$('#menu li.active').removeClass('active');
	$('#' + menuItemId).addClass('active');
}

// ============================== ROUTING =====================================
Backbone.history.start();
var CRouter = Backbone.Router.extend({
	routes: {
		"login": "login",
		"register": "register",
		"forget_password": "forget_password",
		"pricing": "pricing",
		"session": "session",
		"dictionary": "dictionary",
		'*path': 'defaultRoute'
	},
	login: function () {
		$('#divRegister').addClass('hidden');
		$('#divForgetPassword').addClass('hidden');
		var div = fIndexLoad('body', 'divLogin');
		div.removeClass('hidden');
	},
	register: function () {
		$('#divLogin').addClass('hidden');
		$('#divForgetPassword').addClass('hidden');
		var div = fIndexLoad('body', 'divRegister');
		div.removeClass('hidden');
	},
	forget_password: function () {
		$('#divLogin').addClass('hidden');
		$('#divRegister').addClass('hidden');
		var div = fIndexLoad('body', 'divForgetPassword');
		div.removeClass('hidden');
	},
	pricing: function () {
		fIndexMenuActive('menuItemPricing');
		fIndexRightContentSelectDiv('divPricing');
	},
	session: function () {
		fIndexMenuActive('menuItemSession');
		fIndexRightContentSelectDiv('divSession');
	},
	dictionary: function () {
		fIndexMenuActive('menuItemDictionary');
		fIndexRightContentSelectDiv('divDictionary');
	},
	defaultRoute: function () {
//		awrankRouter.navigate('pricing', {trigger: true});
	}
});
var awrankRouter = new CRouter();


