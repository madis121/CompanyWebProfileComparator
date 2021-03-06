<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="modal-dialog modal-lg" role="document">
	<div class="modal-content">
		<div class="modal-header">
			<h3 class="modal-title col-11 text-center" id="profile-edit-label"><spring:message code="profile.edit.modal.title" /></h3>
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
			</button>
		</div>
		<div class="modal-body">
			<div class="row">
				<div id="profile-edit-content" class="col-md-12">
					<form id="update-profile" action="update-profile" method="post">
						<input name="id" type="hidden" value="${profile.id}">
						<div class="form-group row col-md-12">
							<label class="col-form-label col-md-2"><spring:message code="profile.edit.modal.name" /></label>
							<input class="form-control col-md-10" name="name" type="text" value="${profile.name}">
						</div>
						<div class="form-group row col-md-12">
							<label class="col-form-label col-md-2"><spring:message code="profile.edit.modal.keywords" /></label>
							<input class="form control col-md-10" name="keywords" data-role="tagsinput" value="${commaSeperatedKeywords}">
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="col-md-12">
				<button class="btn btn-dark btn-lg float-left" data-dismiss="modal"><spring:message code="common.back" /></button>
				<button class="btn btn-dark btn-lg float-right" onclick="saveProfileChanges();"><spring:message code="common.save" /></button>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		initializeInputTags();
		$('.bootstrap-tagsinput').addClass('col-md-10 keywords');
	});
</script>