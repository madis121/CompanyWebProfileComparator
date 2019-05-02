<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="modal fade" id="newProfileModal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" ng-controller="DashboardController" ng-cloak>
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title col-11 text-center"><spring:message code="new.profile.modal.title" /></h3>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
				</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div ng-show="dom.profileOptions.isShow" class="col-md-4 offset-md-2 mb-5 profile-option">
						<label class="radio-inline" data-toggle="tooltip" title="<spring:message code="new.profile.modal.radio.1.tooltip" />">
							<input ng-model="profileOption" ng-checked="profileOption == 1" ng-change="chooseProfileCreation()" type="radio" name="profile-option" value="1"> 
							<spring:message code="new.profile.modal.radio.1" /> <i class="fas fa-question-circle"></i>
						</label>
					</div>
					<div ng-show="dom.profileOptions.isShow" class="col-md-4 mb-5 profile-option">
						<label class="radio-inline" data-toggle="tooltip" title="<spring:message code="new.profile.modal.radio.2.tooltip" />">
							<input ng-model="profileOption" ng-checked="profileOption == 2" ng-change="chooseProfileCreation()" type="radio" name="profile-option" value="2"> 
							<spring:message code="new.profile.modal.radio.2" /> <i class="fas fa-question-circle"></i>
						</label>
					</div>
					
					<div class="col-md-12">
						<div ng-show="dom.newProfileContent.isShow" id="newProfileContent" class="col-md-12">
							<form id="websites">
								<div class="form-group">
									<ul class="website-list">
										<li ng-repeat="website in websites">
											<input ng-model="website.url" type="text" name="website" class="form-control col-md-10 margin-10" data-index="{{website.i}}">
											<a ng-if="$index == 0" href="javascript:void(0)" ng-click="appendInput()"><i class="fas fa-plus fa-2x"></i></a>
											<a ng-if="$index != 0" href="javascript:void(0)" ng-click="removeInput(website.i)"><i class="fas fa-minus fa-2x"></i></a>
										</li>
									</ul>
								</div>
							</form>
							<button class="btn btn-dark btn-block col-md-10 margin-10" ng-click="collectData()"><spring:message code="new.profile.modal.create" /></button>
						</div>
						
						<div ng-show="dom.newProfileSpinner.isShow" id="newProfileSpinner" class="col-md-11 text-center">
							<i class="fas fa-spinner fa-spin fa-5x"></i>
						</div>
						
						<div ng-show="dom.newProfileResult.isShow" id="newProfileResult" class="col-md-12">
							<form id="createProfile" action="create-profile" method="post">
								<input name="urls" type="hidden">
								<div class="form-group row col-md-12">
									<label class="col-form-label col-md-2"><spring:message code="new.profile.modal.name" /></label>
									<input class="form-control col-md-10" name="name" type="text">
								</div>
								<div class="form-group row col-md-12">
									<label class="col-form-label col-md-2"><spring:message code="new.profile.modal.keywords" /></label>
									<input class="form control col-md-10" name="keywords" data-role="tagsinput">
								</div>
							</form>
						</div>
						
						<div ng-show="dom.newProfileClean.isShow" id="newProfileClean" class="col-md-12">
							<form id="createProfileClean" action="create-profile" method="post">
								<input name="urls" type="hidden">
								<div class="form-group row col-md-12">
									<label class="col-form-label col-md-2"><spring:message code="new.profile.modal.name" /></label>
									<input class="form-control col-md-10" name="name" type="text">
								</div>
								<div class="form-group row col-md-12">
									<label class="col-form-label col-md-2"><spring:message code="new.profile.modal.keywords" /></label>
									<input class="form control col-md-10" name="keywords" data-role="tagsinput">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div ng-show="dom.newProfileButtons.isShow" id="newProfileButtons" class="col-md-12">
					<button class="btn btn-dark btn-lg float-left" ng-click="clearProfile()"><spring:message code="new.profile.modal.clear" /></button>
					<button class="btn btn-dark btn-lg float-right" onclick="saveProfile();"><spring:message code="new.profile.modal.save" /></button>
				</div>
			</div>
		</div>
	</div>
</div>