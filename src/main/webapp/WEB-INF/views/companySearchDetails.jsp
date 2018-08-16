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
					
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="col-md-12">
				<button class="btn btn-dark btn-lg float-left" role="button" data-dismiss="modal"><spring:message code="common.back" /></button>
				<button class="btn btn-dark btn-lg float-right" role="button"><spring:message code="common.search" /></button>
			</div>
		</div>
	</div>
</div>