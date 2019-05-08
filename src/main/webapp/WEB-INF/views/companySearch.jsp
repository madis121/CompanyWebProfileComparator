<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>

<div class="container" ng-controller="CompanySearchController" ng-cloak>
	<div class="row">
		<div class="heading col-md-12">
			<h3><spring:message code="companySearch.title" /></h3>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<div class="form row">
				<label class="col-form-label col-md-1"><spring:message code="companySearch.profile" /></label>
				<select class="form-control col-md-7" id="select-profile" ng-model="selectedProfileId" required>
					<option value="" disabled selected hidden><spring:message code="companySearch.select.placeholder" /></option>
					<option ng-repeat="profile in profiles" value="{{profile.id}}">{{profile.name}}</option>
				</select>
				<button class="btn btn-primary inline" id="open-search-details" ng-disabled="!!!selectedProfileId" ng-click="openCompanySearchDetailsModal()"><spring:message code="companySearch.select.open" /></button>
			</div>
		</div>
	</div>
	
	<jsp:include page="companySearchDetails.jsp" />
</div>

<script>
// 	$(document).ready(function() {
// 		$('#select-profile').on('change', function() {
// 			isOptionSelected();
// 		});
// 	});

// 	function isOptionSelected() {
// 		if ($('#select-profile').val()) {
// 			$('#open-search-details').removeAttr('disabled');
// 		} else {
// 			if (!$('#open-search-details').attr('disabled')) {
// 				$('#open-search-details').attr('disabled', 'disabled');
// 			}
// 		}
// 	}
	
// 	function openCompanySearchDetailsModal() {
// 	    $('#company-search-details-modal').empty();
// 	    $('#company-search-details-modal').load('${pageContext.request.contextPath}/company-search/details?profileId=' + $('#select-profile').val());
// 	    $('#company-search-details-modal').modal('show');
// 	}
</script>

</custom:html>