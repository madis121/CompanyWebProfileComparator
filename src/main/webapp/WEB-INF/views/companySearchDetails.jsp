<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="modal-dialog modal-lg" role="document">
	<div class="modal-content">
		<div class="modal-header">
			<h3 class="modal-title col-11 text-center" id="company-search-details-label"><spring:message code="companySearch.details.title" /></h3>
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
			</button>
		</div>
		<div class="modal-body">
			<div class="row">
				<div id="company-search-details-content" class="col-md-12">
					<form id="find-similar-companies" action="company-search/find" method="get">
						<input name="urls" type="hidden" value="${commaSeperatedURLs}">
						<div class="form-group row col-md-12">
							<label class="col-form-label col-md-3"><spring:message code="companySearch.details.profile.name" /></label>
							<input class="form-control col-md-9" name="name" type="text" value="${profile.name}" readonly>
						</div>
						<div class="form-group row col-md-12">
							<label class="col-form-label col-md-3"><spring:message code="companySearch.details.keywords" /></label>
							<input class="form control col-md-9" name="keywords" data-role="tagsinput" value="${commaSeperatedKeywords}">
						</div>
						<div class="form-group row col-md-12">
							<label class="col-form-label col-md-3"><spring:message code="companySearch.details.country" /></label>
							<select class="form-control col-md-9" name="country">
								<option value="" disabled selected hidden><spring:message code="companySearch.details.country.placeholder" /></option>
								<c:forEach items="${countries}" var="country">
									<option value="${country.code}"><c:out value="${country.name}" /></option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group row col-md-12">
							<label class="col-form-label col-md-3" data-toggle="tooltip" title="<spring:message code="companySearch.details.contacts.tooltip" />">
								<spring:message code="companySearch.details.contacts" /> <i class="fas fa-question-circle"></i>
							</label>
							<input class="form-control col-md-9" name="contacts" type="text">
						</div>
					</form>
				</div>
				
				<i id="company-search-spinner" class="fas fa-spinner fa-spin fa-5x col-md-11 text-center" style="display:none;"></i>
				
				<div id="company-search-result" class="col-md-12" style="display:none;">
					<form id="save-company-search-result" action="company-search/save-result" method="post">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th scope="col" class="narrow">#</th>
									<th scope="col"><spring:message code="companySearch.result.website" /></th>
									<th scope="col"><spring:message code="companySearch.result.similarity" /></th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</form>
				</div>
				
				<div id="company-search-result-error" class="col-md-12 text-center" style="display:none;">
					<h5><spring:message code="companySearch.result.error.api.daily.limit.exceeded" /></h5>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="col-md-12">
				<div id="company-search-buttons">
					<button class="btn btn-dark btn-lg float-left" data-dismiss="modal"><spring:message code="common.back" /></button>
					<button class="btn btn-dark btn-lg float-right" onclick="validateAndSubmit();"><spring:message code="common.search" /></button>
				</div>
				<div id="company-search-result-buttons" style="display:none;">
					<button class="btn btn-dark btn-lg float-left" data-dismiss="modal"><spring:message code="common.back" /></button>
					<button class="btn btn-dark btn-lg float-right" onclick="saveSearchResult();"><spring:message code="common.save" /></button>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		initializeTooltips();
		initializeInputTags();
		$('.bootstrap-tagsinput').addClass('col-md-9');
	});
	
	function validateAndSubmit() {
		var keywords = $('#find-similar-companies [name="keywords"]');
		var country = $('#find-similar-companies [name="country"]');
		var contacts = $('#find-similar-companies [name="contacts"]');
		var contactsExpression = '^[a-zA-Z0-9]';
		var contactsRegex = new RegExp(contactsExpression, "i");
		var validationError = false;
		
		if (keywords.tagsinput('items').length < 1) {
			addValidationErrorMessage(keywords.prev(), '<spring:message code="companySearch.details.keywords.validation.error" />');
			validationError = true;
		} else {
			removeValidationErrorMessage(keywords.prev());
		}
		
		if (!country.val()) {
			addValidationErrorMessage(country, '<spring:message code="companySearch.details.country.validation.error" />');
			validationError = true;
		} else {
			removeValidationErrorMessage(country);
		}
		
		if (!contactsRegex.test(contacts.val())) {
			addValidationErrorMessage(contacts, '<spring:message code="companySearch.details.contacts.validation.error" />');
			validationError = true;
		} else {
			removeValidationErrorMessage(contacts);
		}
		
		if (!validationError) {
			submitForm();
		}
	} 
	
	function submitForm() {
		$('#company-search-details-content').hide();
		$('#company-search-buttons').hide();
		$('#company-search-spinner').show();
		
		$.ajax({
			method : 'GET',
			url : 'company-search/find',
			data :  $('#find-similar-companies').serialize(),
			async : true,
			success : function(websites) {
				console.log(websites);
				$.each(websites, function(i, website) {
					var tbody = $('#company-search-result tbody');
					var row = $('<tr></tr>').appendTo(tbody);
					$('<td></td>').text(i + 1).appendTo(row);
					$('<td></td>').prepend($('<a></a>').attr({ 'href': website, 'target': '_blank' }).text(website)).appendTo(row);
					$('<td></td>').appendTo(row);
				});
				
				$('#company-search-spinner').hide();
				$('#company-search-result').show();
				$('#company-search-result-buttons').show();
			},
			statusCode : {
				403: function() {
					$('#company-search-spinner').hide();
					$('#company-search-result-error').show();
					$('#company-search-result-buttons').show();
				}
			} 
		});
	}
	
	function saveSearchResult() {
		$.each($('#find-similar-companies input, #find-similar-companies select'), function(i, obj) {
			if ($(this).is('input')) {
				$(this).clone().attr('type', 'hidden').appendTo($('#save-company-search-result'));
			} else {
				var input = '<input type="hidden" name="' + $(this).prop('name') + '" value="' + $(this).val() + '">';
				$('#save-company-search-result').append(input);
			}
		});
		
		$('#save-company-search-result').submit();
	}
</script>