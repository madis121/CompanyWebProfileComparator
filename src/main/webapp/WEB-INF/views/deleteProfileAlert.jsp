<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="modal-dialog modal-lg" role="document">
	<div class="modal-content">
		<div class="modal-header">
			<h3 class="modal-title col-11 text-center" id="profile-delete-label"><spring:message code="profile.delete.modal.title" /></h3>
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
			</button>
		</div>
		<div class="modal-body"></div>
		<div class="modal-footer">
			<div id="new-profile-buttons" class="col-md-12">
				<a href="javascript:void(0)" class="btn btn-dark btn-lg float-left" data-dismiss="modal"><spring:message code="common.back" /></a>
				<a href="javascript:void(0)" class="btn btn-dark btn-lg float-right" onclick="deleteProfile('${profile.id}');"><spring:message code="common.delete" /></a>
			</div>
		</div>
	</div>
</div>