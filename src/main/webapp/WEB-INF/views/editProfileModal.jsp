<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="modal fade" id="profileEditModal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title col-11 text-center"><spring:message code="profile.edit.modal.title" /></h3>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
				</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div id="profile-edit-content" class="col-md-12">
						<form name="updateProfileForm" novalidate>
							<div class="form-group row col-md-12">
								<label class="col-form-label col-md-2"><spring:message code="profile.edit.modal.name" /></label>
								<input class="form-control col-md-10" name="name" type="text" ng-model="editProfile.name">
							</div>
							<div class="form-group row col-md-12">
								<label class="col-form-label col-md-2"><spring:message code="profile.edit.modal.keywords" /></label>
								<input class="form control col-md-10" name="keywords" data-role="tagsinput" ng-model="editProfile.keywords" ng-required="{{true}}" ng-class="isEditProfileKeywordsValid()">
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="col-md-12">
					<button class="btn btn-dark btn-lg float-left" data-dismiss="modal"><spring:message code="common.back" /></button>
					<button class="btn btn-dark btn-lg float-right" ng-click="updateProfile(updateProfileForm.$valid)"><spring:message code="common.save" /></button>
				</div>
			</div>
		</div>
	</div>
</div>