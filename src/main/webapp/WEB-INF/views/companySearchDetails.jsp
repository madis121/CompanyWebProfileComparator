<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="modal fade" id="companySearchDetailsModal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title col-11 text-center"><spring:message code="companySearch.details.title" /></h3>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
				</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div id="company-search-details-content" class="col-md-12" ng-show="dom.companySearchDetailsContent.isShow">
						<form id="find-similar-companies" name="findSimilarCompaniesForm" action="company-search/find" method="get">
							<input name="urls" type="hidden" value="${commaSeperatedURLs}">
							<div class="form-group row col-md-12">
								<label class="col-form-label col-md-3"><spring:message code="companySearch.details.profile.name" /></label>
								<input class="form-control col-md-9" name="name" type="text" ng-model="companySearch.name" readonly>
							</div>
							<div class="form-group row col-md-12">
								<label class="col-form-label col-md-3"><spring:message code="companySearch.details.keywords" /></label>
								<input class="form control col-md-9" name="keywords" data-role="tagsinput" ng-model="companySearch.keywords">
							</div>
							<div class="form-group row col-md-12">
								<label class="col-form-label col-md-3"><spring:message code="companySearch.details.country" /></label>
								<select class="form-control col-md-9" name="country" ng-model="companySearch.country">
									<option value="" disabled selected hidden><spring:message code="companySearch.details.country.placeholder" /></option>
									<option ng-repeat="country in countries" value="{{country.code}}">{{country.name}}</option>
								</select>
							</div>
							<div class="form-group row col-md-12">
								<label class="col-form-label col-md-3" data-toggle="tooltip" title="<spring:message code="companySearch.details.contacts.tooltip" />">
									<spring:message code="companySearch.details.contacts" /> <i class="fas fa-question-circle"></i>
								</label>
								<input class="form-control col-md-9" name="contacts" type="text" ng-model="companySearch.contacts">
							</div>
						</form>
					</div>
					
					<i id="company-search-spinner" class="fas fa-spinner fa-spin fa-5x col-md-11 text-center" style="display:none;" ng-show="dom.companySearchSpinner.isShow"></i>
					
					<div id="company-search-result" class="col-md-12" style="display:none;" ng-show="dom.companySearchResults.isShow">
						<form id="save-company-search-result" action="company-search/save-result" method="post">
							<div class="form-group row col-md-12">
								<label class="col-form-label col-md-3"><spring:message code="companySearch.result.name" /></label>
								<input class="form-control col-md-9" name="name" type="text" value="${profile.name}">
							</div>
							<table class="table table-striped table-bordered">
								<thead>
									<tr>
										<th scope="col" class="narrow">#</th>
										<th scope="col"><spring:message code="companySearch.result.website" /></th>
										<%-- <th scope="col"><spring:message code="companySearch.result.similarity" /></th> --%>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</form>
					</div>
					
					<div id="company-search-result-error" class="col-md-12 text-center" ng-show="companySearchError"></div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="col-md-12">
					<div id="company-search-buttons" ng-show="dom.companySearchButtons.isShow">
						<button class="btn btn-dark btn-lg float-left" data-dismiss="modal"><spring:message code="common.back" /></button>
						<button class="btn btn-dark btn-lg float-right" ng-click="findSimilarCompanies()"><spring:message code="common.search" /></button>
					</div>
					<div id="company-search-result-buttons" ng-show="dom.companySearchResultButtons.isShow">
						<button class="btn btn-dark btn-lg float-left" data-dismiss="modal"><spring:message code="common.back" /></button>
						<button class="btn btn-dark btn-lg float-right save-button" ng-click="saveSearchResults()"><spring:message code="common.save" /></button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
// 	$(document).ready(function() {
// 		initializeTooltips();
// 		initializeInputTags();
// 		$('.bootstrap-tagsinput').addClass('col-md-9 keywords');
// 	});
	
// 	function validateAndSubmit() {
// 		var keywords = $('#find-similar-companies [name="keywords"]').val().split(",");
// 		var country = $('#find-similar-companies [name="country"]');
// 		var contacts = $('#find-similar-companies [name="contacts"]');
// 		var contactsExpression = '^[a-zA-Z0-9]';
// 		var contactsRegex = new RegExp(contactsExpression, "i");
// 		var validationError = false;
		
		
// 		if (keywords.length < 5) {
// 			addValidationErrorMessage($('#find-similar-companies .keywords'), '<spring:message code="companySearch.details.keywords.validation.error" />');
// 			validationError = true;
// 		} else {
// 			removeValidationErrorMessage($('#find-similar-companies .keywords'));
// 		}
		
// 		if (!country.val()) {
// 			addValidationErrorMessage(country, '<spring:message code="companySearch.details.country.validation.error" />');
// 			validationError = true;
// 		} else {
// 			removeValidationErrorMessage(country);
// 		}
		
// 		/*
// 		if (!contactsRegex.test(contacts.val())) {
// 			addValidationErrorMessage(contacts, '<spring:message code="companySearch.details.contacts.validation.error" />');
// 			validationError = true;
// 		} else {
// 			removeValidationErrorMessage(contacts);
// 		}
// 		*/
		
// 		if (!validationError) {
// 			submitForm();
// 		}
// 	} 
	
// 	function submitForm() {
// 		$('#company-search-details-content').hide();
// 		$('#company-search-buttons').hide();
// 		$('#company-search-spinner').show();
		
// 		$.ajax({
// 			method : 'GET',
// 			url : 'company-search/find',
// 			data :  $('#find-similar-companies').serialize(),
// 			async : true,
// 			success : function(data) {
// 				console.log(data);
// 				$.each(data, function(i, website) {
// 					var tbody = $('#company-search-result tbody');
// 					var row = $('<tr></tr>').appendTo(tbody);
// 					$('<td></td>').text(i + 1).appendTo(row);
// 					$('<td></td>').prepend($('<a></a>').attr({ 'href': website.website, 'target': '_blank' }).text(website.website)).appendTo(row);
// 					//$('<td></td>').text(website.similarity).appendTo(row);
// 				});
				
// 				$('#company-search-spinner').hide();
// 				$('#company-search-result').show();
// 				$('#company-search-result-buttons .save-button').show();
// 				$('#company-search-result-buttons').show();
// 			},
// 			error : function(request, status, error) {
// 				$('#company-search-result-error').html('');
// 				$('#company-search-result-error').append('<h5>' + request.responseJSON.statusText + '</h5>');
// 				$('#company-search-spinner').hide();
// 				$('#company-search-result-error').show();
// 				$('#company-search-result-buttons .save-button').hide();
// 				$('#company-search-result-buttons').show();
// 			}
// 		});
// 	}
	
// 	function saveSearchResult() {
// 		$.each($('#find-similar-companies input, #find-similar-companies select'), function(i, obj) {
// 			if ($(this).is('input')) {
// 				if ($(this).prop('name') != 'name') {
// 					$(this).clone().attr('type', 'hidden').appendTo($('#save-company-search-result'));
// 				}
// 			} else {
// 				var input = '<input type="hidden" name="' + $(this).prop('name') + '" value="' + $(this).val() + '">';
// 				$('#save-company-search-result').append(input);
// 			}
// 		});
		
// 		$('#save-company-search-result').submit();
// 	}
</script>