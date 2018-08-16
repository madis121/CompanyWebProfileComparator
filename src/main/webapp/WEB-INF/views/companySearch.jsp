<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>

<div class="container">
	<div class="row">
		<div class="heading col-md-12">
			<h3><spring:message code="companySearch.title" /></h3>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<div class="form row">
				<label class="col-form-label col-md-2"><spring:message code="companySearch.company" /></label>
				<select class="form-control col-md-6" id="select-profile">
					<option value="" disabled selected hidden><spring:message code="companySearch.select.placeholder" /></option>
					<c:forEach items="${profiles}" var="profile">
						<option value="${profile.id}"><c:out value="${profile.name}" /></option>
					</c:forEach>
				</select>
				<button class="btn btn-primary inline" id="open-search-details" onclick="openCompanySearchDetailsModal();" disabled><spring:message code="companySearch.select.open" /></button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="company-search-details-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="company-search-details-label" aria-hidden="true"></div>

<script>
	$(document).ready(function() {
		console.log('${pageContext.request.contextPath}');
		$('#select-profile').on('change', function() {
			isOptionSelected();
		});
	});

	function isOptionSelected() {
		if ($('#select-profile').val()) {
			$('#open-search-details').removeAttr('disabled');
		} else {
			if (!$('#open-search-details').attr('disabled')) {
				$('#open-search-details').attr('disabled', 'disabled');
			}
		}
	}
	
	function openCompanySearchDetailsModal() {
	    $('#company-search-details-modal').empty();
	    $('#company-search-details-modal').load('${pageContext.request.contextPath}/company-search/details?profileId=' + $('#select-profile').val());
	    $('#company-search-details-modal').modal('show');
	}
</script>

</custom:html>