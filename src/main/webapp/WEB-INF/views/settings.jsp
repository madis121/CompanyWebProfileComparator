<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>

<div class="container">
	<div class="row">
		<div class="col-md-12 heading">
			<h3><spring:message code="settings.title" /></h3>
		</div>
	</div>
	
	<div class="row">
		<form:form id="settings-form" action="" method="POST" modelAttribute="settings">
			<div class="col-md-12 sub-heading">
				<h5><spring:message code="settings.webscraper" /></h5>
			</div>
			<div class="col-md-12">
				<form:input class="form-control col-md-7" path="id" type="hidden"></form:input>
				<form:input class="form-control col-md-7" path="created" type="hidden"></form:input>
				<form:input class="form-control col-md-7" path="updated" type="hidden"></form:input>
				<div class="form-group row col-md-12">
					<form:label class="col-form-label col-md-5" path="webScraperMaxPagesToSearch"><spring:message code="settings.webscraper.maxPagesToSearch" /></form:label>
					<form:input class="form-control col-md-7" path="webScraperMaxPagesToSearch" type="number" min="10" max="50"></form:input>
				</div>
				<div class="form-group row col-md-12">
					<form:label class="col-form-label col-md-5" path="webScraperMinKeywordLength"><spring:message code="settings.webscraper.ignoreWordsWithLength" /></form:label>
					<form:input class="form-control col-md-7" path="webScraperMinKeywordLength" type="number"></form:input>
				</div>
				<div class="form-group row col-md-12">
					<form:label class="col-form-label col-md-5" path="webScraperIgnoredHTMLElements"><spring:message code="settings.webscraper.ignoredHtmlElements" /></form:label>
					<form:textarea class="form-control col-md-7" path="webScraperIgnoredHTMLElements" rows="4" cols="100"></form:textarea>
				</div>
				<div class="form-group row col-md-12">
					<form:label class="col-form-label col-md-5" path="webScraperIgnoredKeywords"><spring:message code="settings.webscraper.redundantWords" /></form:label>
					<form:textarea class="form-control col-md-7" path="webScraperIgnoredKeywords" rows="4" cols="100"></form:textarea>
				</div>
			</div>
		</form:form>
		<div class="col-md-12">
			<button class="btn btn-dark btn-lg fixed-bottom-right" onclick="validateAndSubmit();"><spring:message code="common.save" /></button>
		</div>
	</div>
</div>

<script>
$(function() {
	initializeTooltips();
});

function validateAndSubmit() {
	var webScraperMaxPagesToSearch = $('#settings-form [name="webScraperMaxPagesToSearch"]');
	var webScraperMinKeywordLength = $('#settings-form [name="webScraperMinKeywordLength"]');
	var webScrapedIgnoredHTMLElements = $('#settings-form [name="webScrapedIgnoredHTMLElements"]');
	var webScraperIgnoredKeywords = $('#settings-form [name="webScraperIgnoredKeywords"]');
	var validationError = false;
	
	if (!webScraperMaxPagesToSearch.val()) {
		addValidationErrorMessage(webScraperMaxPagesToSearch, '<spring:message code="settings.webscraper.maxPagesToSearch.validation.error" />');
		validationError = true;
	} else {
		removeValidationErrorMessage(webScraperMaxPagesToSearch);
	}
	
	if (!webScraperMinKeywordLength.val()) {
		addValidationErrorMessage(webScraperMinKeywordLength, '<spring:message code="settings.webscraper.ignoreWordsWithLength.validation.error" />');
		validationError = true;
	} else {
		removeValidationErrorMessage(webScraperMinKeywordLength);
	}
	
	var expression = '^[a-zA-Z0-9,]*$';
	var regex = new RegExp(expression, "i");
	
	if (!regex.test(webScrapedIgnoredHTMLElements.val())) {
		addValidationErrorMessage(webScrapedIgnoredHTMLElements, '<spring:message code="settings.webscraper.ignoredHtmlElements.validation.error" />');
		validationError = true;
	} else {
		removeValidationErrorMessage(webScrapedIgnoredHTMLElements);
	}
	
	if (!regex.test(webScraperIgnoredKeywords.val())) {
		addValidationErrorMessage(webScraperIgnoredKeywords, '<spring:message code="settings.webscraper.redundantWords.validation.error" />');
		validationError = true;
	} else {
		removeValidationErrorMessage(webScraperIgnoredKeywords);
	}
	
	if (!validationError) {
		submitForm();
	}
}

function submitForm() {
	$('#settings-form').submit();
}
</script>

</custom:html>