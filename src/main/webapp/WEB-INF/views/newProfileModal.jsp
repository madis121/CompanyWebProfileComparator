<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="modal fade" id="newProfileModal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
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
					<div class="col-md-4 offset-md-2 mb-5 profile-option" ng-show="dom.profileOptions.isShow">
						<label class="radio-inline" data-toggle="tooltip" title="<spring:message code="new.profile.modal.radio.1.tooltip" />">
							<input type="radio" name="profile-option" value="1" ng-model="profileOption" ng-checked="profileOption == 1" ng-change="chooseProfileCreation()"> 
							<spring:message code="new.profile.modal.radio.1" /> <i class="fas fa-question-circle"></i>
						</label>
					</div>
					<div class="col-md-4 mb-5 profile-option" ng-show="dom.profileOptions.isShow">
						<label class="radio-inline" data-toggle="tooltip" title="<spring:message code="new.profile.modal.radio.2.tooltip" />">
							<input type="radio" name="profile-option" value="2" ng-model="profileOption" ng-checked="profileOption == 2" ng-change="chooseProfileCreation()"> 
							<spring:message code="new.profile.modal.radio.2" /> <i class="fas fa-question-circle"></i>
						</label>
					</div>
					
					<div class="col-md-12">
						<div id="newProfileContent" class="col-md-12" ng-show="dom.newProfileContent.isShow">
							<form id="websites" name="websitesForm" novalidate>
								<div class="form-group">
									<ul class="website-list">
										<li ng-repeat="website in websites">
											<input type="text" name="website" class="form-control col-md-10 margin-10" data-index="{{website.i}}" ng-model="website.url" ng-required="{{true}}" ng-pattern="urlRegex" ng-class="isValidWebsite(website)">
											<a href="javascript:void(0)" ng-if="$index == 0" ng-click="appendInput()"><i class="fas fa-plus fa-2x"></i></a>
											<a href="javascript:void(0)" ng-if="$index != 0" ng-click="removeInput(website.i)"><i class="fas fa-minus fa-2x"></i></a>
										</li>
									</ul>
								</div>
							</form>
							<button class="btn btn-dark btn-block col-md-10 margin-10" ng-click="collectData(websitesForm.$valid)"><spring:message code="new.profile.modal.create" /></button>
						</div>
						
						<div ng-show="dom.newProfileSpinner.isShow" id="newProfileSpinner" class="col-md-11 text-center">
							<i class="fas fa-spinner fa-spin fa-5x"></i>
						</div>
						
						<div id="newProfileResult" class="col-md-12" ng-show="dom.newProfileResult.isShow">
							<form name="createProfile">
								<div class="form-group row col-md-12">
									<label class="col-form-label col-md-2"><spring:message code="new.profile.modal.name" /></label>
									<input class="form-control col-md-10" name="name" type="text" ng-model="generatedProfile.name">
								</div>
								<div class="form-group row col-md-12">
									<label class="col-form-label col-md-2"><spring:message code="new.profile.modal.keywords" /></label>
									<input class="form control col-md-10" name="keywords" data-role="tagsinput" ng-model="generatedProfile.keywords">
								</div>
							</form>
						</div>
						
						<div id="newProfileClean" class="col-md-12" ng-show="dom.newProfileClean.isShow">
							<form name="createProfileClean">
								<div class="form-group row col-md-12">
									<label class="col-form-label col-md-2"><spring:message code="new.profile.modal.name" /></label>
									<input class="form-control col-md-10" name="name" type="text" ng-model="cleanProfile.name">
								</div>
								<div class="form-group row col-md-12">
									<label class="col-form-label col-md-2"><spring:message code="new.profile.modal.keywords" /></label>
									<input class="form control col-md-10" name="keywords" data-role="tagsinput" ng-model="cleanProfile.keywords">
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div id="newProfileButtons" class="col-md-12" ng-show="dom.newProfileButtons.isShow">
					<button class="btn btn-dark btn-lg float-left" ng-click="clearProfile()"><spring:message code="new.profile.modal.clear" /></button>
					<button class="btn btn-dark btn-lg float-right" ng-click="saveProfile()"><spring:message code="new.profile.modal.save" /></button>
				</div>
			</div>
		</div>
	</div>
</div>