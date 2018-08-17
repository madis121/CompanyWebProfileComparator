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
			<form id="find-similar-companies" action="company-search/find" method="get">
				<div class="row">
					<div id="company-search-details-content" class="col-md-12">
						<div class="form-group row col-md-12">
							<label class="col-form-label col-md-3"><spring:message code="companySearch.details.profile.name" /></label>
							<input class="form-control col-md-9" name="name" type="text" value="${profile.name}" disabled>
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
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<div class="col-md-12">
				<button class="btn btn-dark btn-lg float-left" data-dismiss="modal"><spring:message code="common.back" /></button>
				<button class="btn btn-dark btn-lg float-right" onclick="submitForm();"><spring:message code="common.search" /></button>
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
	
	function submitForm() {
		$.ajax({
			method : 'GET',
			url : 'company-search/find',
			data :  $('#find-similar-companies').serialize(),
			async : true,
			success : function(data) {
				alert(data);
			}
		});
	}
</script>