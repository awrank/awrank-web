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

function alertSuccess(title, text) {
	$('div[name=alert-success]').each(function () {
		var divError = $(this);
		if (divError.closest('div.hidden').length <= 0) {
			divError.append('<div class="alert alert-success">' +
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
	var result = '';
	if (date != null) {
		if (!(date instanceof Date)) {
			date = new Date(date);
		}

		var f00 = function (i) {
			if (i < 10)
				i = '0' + i;
			return i;
		}
		result = f00(date.getDate()) + '.' +
			f00(date.getMonth() + 1) + '.' +
			date.getFullYear() + ' ' +
			f00(date.getHours()) + ':' +
			f00(date.getMinutes()) + ':' +
			f00(date.getSeconds());
	}
	return result;
}

// ============================ VALIDATORS ===================================

function validateEmail(email) {
	// var re = [a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+(?:[A-Z]{2}|com|org|net|gov|mil|biz|info|mobi|name|aero|jobs|museum)\b
	var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	return re.test(email);
}

// =========================== SEND REQUEST ===================================

function send_user_login(uid, password) {
	awrankPost("user/login", {uid: uid, password: password}, function (data) {

		alertSuccess(getMessage('WELCOME'), getMessage('YOU_LOGGED_IN_SUCCESSFULLY'));

		$('#divLogin').addClass('hidden');
		awrankRouter.previous();
		var options;
		while ((options = oldRequest.shift()) != null) {
			$.ajax(options);
		}
	})
}

function send_user_register(dataform) {

	//alert("in send_user_register "+JSON.stringify(dataform));
	awrankPost("user/add2", dataform, function (data) {

		//if(data.error != null)
		//alert(JSON.stringify(data));
		//alert(data.result);

		if (data.result == "failure") {

			alertError(getMessage('ERROR'), getMessage(data.reason));

		}
		else if (data.result == "ok") {

			$('#divRegister').addClass('hidden');

			alertSuccess(getMessage('WELCOME'), getMessage(data.reason));

//			awrankRouter.navigate('', {trigger: true});
			var options;
			while ((options = oldRequest.shift()) != null) {
				$.ajax(options);
			}
		}


	})
}

function send_user_profile(dataform) {

	awrankPost("rest/profile/userdata/update2", dataform, function (data) {

		if (data.result == "failure") {

			alertError(getMessage('ERROR'), getMessage(data.reason));
		}
		else if (data.result == "ok") {
			
			alertSuccess(getMessage('PROFILE_UPDATED_SUCCESSFULLY'), getMessage(data.reason));
		}
	})
}

function send_user_new_email(dataform) {

	awrankPost("rest/profile/changeemailmanual2", dataform, function (data) {

		if (data.result == "failure") {

			alertError(getMessage('ERROR'), getMessage(data.reason));
		}
		else if (data.result == "ok") {
			
			alertSuccess(getMessage('PROFILE_EMAIL_UPDATED_SUCCESSFULLY'), getMessage(data.reason));
		}
	})
}

function send_user_logout() {
	awrankGet('user/logout');
}


// =============================== UI =========================================

function fIndexClearMessages(divId) {

	var div = $('#' + divId);
	div.find('[name=alert-error]').empty();
	div.find('[name=alert-warning]').empty();
	div.find('[name=alert-success]').empty();
	return true;
}

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
	div.find('[name=alert-success]').empty();
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
/**
 * fit <div id="pagerId" class="pagination pagination-centered"></div>
 * @param pagerId element
 * @param page current page
 * @param lastPage
 * @param onSelectPage function name (<a onclick="' + onSelectPage + '(' + i + ')">)
 */
function renderPageButton(pagerId, page, lastPage, onSelectPage) {
//						1 2 3 4 5 6 7 8 9
//						1 . 4 5 6 7 8 . 11
	var pager = $('#' + pagerId);
	pager.empty();
	var s = '<ul>';
	if (lastPage > 1) {
		if (page > 1)
			s += '<li><a onclick="' + onSelectPage + '(' + (page - 1) + ')"><i class="icon-backward"></i></a></li>';
		var firstViewPage;
		if (lastPage > 9 && page > 5) {
			s += '<li><a onclick="' + onSelectPage + '(1)">1</a></li>';
			s += '<li class="disabled"><a>...</a></li>';
			firstViewPage = Math.min(lastPage - 6, page - 2);
		}
		else {
			firstViewPage = 1;
		}
		var lastViewPage = Math.min(firstViewPage + 4, lastPage);
		if (lastPage > 9 && (lastPage - page) >= 5) {
			lastViewPage = Math.max(7, page + 2);
		}
		else {
			lastViewPage = lastPage;
		}
		for (var i = firstViewPage; i <= lastViewPage; i++) {
			if (i == page) {
				s += '<li class="active"><a>' + i + '</a></li>';
			}
			else {
				s += '<li><a onclick="' + onSelectPage + '(' + i + ')">' + i + '</a></li>';
			}
		}
		if (lastPage > 9 && (lastPage - page) >= 5) {
			s += '<li class="disabled"><a>...</a></li>';
			s += '<li><a onclick="' + onSelectPage + '(' + lastPage + ')">' + lastPage + '</a></li>';
		}
		if (page < lastPage) {
			s += '<li><a onclick="' + onSelectPage + '(' + (page + 1) + ')"><i class="icon-forward"></i></a></li>';
		}
	}
	s += '</ul>';
	pager.append(s);
}


// ============================== ROUTING =====================================

var CRouter = Backbone.Router.extend({
	initialize: function (options) {
		this.history = [];
	},
	previous: function () {
		if (this.history.length > 2) {
			var current = this.history.shift();
			var previous = this.history.shift();
			this.navigate(previous, {trigger: true});
		}
	},
	routes: {
		"login": "login",
		"register": "register",
        "termsOfService": "termsOfService",
		"forgot_password": "forgot_password",
        "home": "home",
		"order": "order",
		"profile": "profile",
		"application": "application",
		"requestHistory": "requestHistory",
		"sessionHistory": "sessionHistory",
		"payment_history": "payment_history",
		"dictionary": "dictionary",
		"userList": "userList",
		"manageUsers": "manageUsers",
		"logs": "logs",
		'*path': 'defaultRoute'
	},
	login: function () {
		this.history.push(Backbone.history.fragment);
		$('#divRegister').addClass('hidden');
		$('#divForgotPassword').addClass('hidden');
		var div = fIndexLoad('body', 'divLogin');
		div.removeClass('hidden');
	},
	register: function () {
		this.history.push(Backbone.history.fragment);
		$('#divLogin').addClass('hidden');
		$('#divForgotPassword').addClass('hidden');
        $('#divTermsOfService').addClass('hidden');
		var div = fIndexLoad('body', 'divRegister');
		div.removeClass('hidden');
	},
    termsOfService: function () {
        this.history.push(Backbone.history.fragment);
        $('#divRegister').addClass('hidden');
        var div = fIndexLoad('body', 'divTermsOfService');
        div.removeClass('hidden');
    },
	forgot_password: function () {
		this.history.push(Backbone.history.fragment);
		$('#divLogin').addClass('hidden');
		$('#divRegister').addClass('hidden');
		var div = fIndexLoad('body', 'divForgotPassword');
		div.removeClass('hidden');
	},
    home: function () {
        this.history.push(Backbone.history.fragment);
        fIndexMenuActive('menuItemHome');
        fIndexRightContentSelectDiv('divHome');
    },
	order: function () {
		this.history.push(Backbone.history.fragment);
		fIndexMenuActive('menuItemOrder');
		fIndexRightContentSelectDiv('divOrder');
	},
	profile: function () {
		this.history.push(Backbone.history.fragment);
		fIndexMenuActive('menuItemProfile');
		fIndexRightContentSelectDiv('divProfile');
	},
	application: function () {
		this.history.push(Backbone.history.fragment);
		fIndexMenuActive('menuItemApplication');
		fIndexRightContentSelectDiv('divApplication');
	},
	requestHistory: function () {
		this.history.push(Backbone.history.fragment);
		fIndexMenuActive('menuItemRequestHistory');
		fIndexRightContentSelectDiv('divRequestHistory');
	},
	sessionHistory: function () {
		this.history.push(Backbone.history.fragment);
		fIndexMenuActive('menuItemSessionHistory');
		fIndexRightContentSelectDiv('divSessionHistory');
	},
	payment_history: function () {
		this.history.push(Backbone.history.fragment);
		fIndexMenuActive('menuPaymentHistory');
		fIndexRightContentSelectDiv('divPaymentHistory');
	},
	dictionary: function () {
		this.history.push(Backbone.history.fragment);
		fIndexMenuActive('menuItemDictionary');
		fIndexRightContentSelectDiv('divDictionary');
	},
	userList: function () {
		this.history.push(Backbone.history.fragment);
		fIndexMenuActive('menuItemUserList');
		fIndexRightContentSelectDiv('divUserList');
	},
	manageUsers: function () {
		this.history.push(Backbone.history.fragment);
		fIndexMenuActive('menuItemManageUsers');
		fIndexRightContentSelectDiv('divManageUsers');
	},
	logs: function () {
		this.history.push(Backbone.history.fragment);
		fIndexMenuActive('menuItemLogs');
		fIndexRightContentSelectDiv('divLogs');
	},
	defaultRoute: function () {
		this.history.push(Backbone.history.fragment);
		//awrankRouter.navigate('order', {trigger: true});
        awrankRouter.navigate('home', {trigger: true});
	}
});



