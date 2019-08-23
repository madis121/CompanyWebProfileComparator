// TODO: replace vanilla JS?
function validateInput(isValid, element, text) {
	if (!isValid) {
		element.setAttribute('data-toggle', 'tooltip');
		element.setAttribute('data-original-title', text);
		initializeTooltips();
	} else {
		element.removeAttribute('data-toggle');
		element.removeAttribute('data-original-title');
	}
}

function validateKeywordsInput(isValid, element, text) {
	if (!isValid) {
		element.setAttribute('data-toggle', 'tooltip');
		element.setAttribute('data-original-title', text);
		element.classList.add('contains-errors');
		initializeTooltips();
	} else {
		element.removeAttribute('data-toggle');
		element.removeAttribute('data-original-title');
		element.classList.remove('contains-errors');
	}
}

// TODO: replace jQuery?
function initializeTooltips() {
	$("[data-toggle=tooltip").tooltip();
}

function initializeInputTags() {
	$('input[data-role="tagsinput"]').tagsinput('items');
}

function addClassToInputTags(selector, htmlClass) {
	if (!!selector && !!htmlClass) {
		$(selector + ' .bootstrap-tagsinput').addClass(htmlClass);
	}
}

function showErrorNotification(text) {
	$.notify({ message: '<i class="fas fa-exclamation-triangle"></i>' + text }, { type: 'danger' });
}

function displayNotifications() {
//		if ('${action}' == PROFILE_SAVED && '${not hasErrors}') {
//			$.notify('<i class="fas fa-check"></i> <spring:message code="notification.profile.saved" />');
//		}
//		if ('${action}' == PROFILE_UPDATED && '${not hasErrors}') {
//			$.notify('<i class="fas fa-check"></i> <spring:message code="notification.profile.updated" />');
//		}
//		if ('${action}' == PROFILE_DELETED && '${not hasErrors}') {
//			$.notify('<i class="fas fa-check"></i> <spring:message code="notification.profile.deleted" />');
//		}
}