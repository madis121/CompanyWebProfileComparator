<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>
	
<div class="container" ng-controller="DashboardController" ng-cloak>
	<div class="row">
		<div class="heading col-md-12">
			<h3><spring:message code="dashboard.title" /></h3>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th scope="col" class="narrow">#</th>
						<th scope="col"><spring:message code="dashboard.profiles.table.name" /></th>
						<th scope="col"><spring:message code="dashboard.profiles.table.lastUpdated" /></th>
						<th scope="col" class="narrow">&nbsp;</th>
						<th scope="col" class="narrow">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="profile in profiles">
						<td scope="col">{{$index + 1}}</td>
						<td>{{profile.name}}</td>
						<td>{{profile.updated}}</td>
						<td><a href="javascript:void(0)" ng-click="openProfileModal(profile.id)"><i class="fas fa-edit"></i></a></td>
						<td><a href="javascript:void(0)" ng-click="openProfileModal(profile.id)"><i class="fas fa-trash"></i></a>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<!--
	<div class="row">
		<div class="col-md-12">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th scope="col" class="narrow">#</th>
						<th scope="col"><spring:message code="dashboard.profiles.table.name" /></th>
						<th scope="col"><spring:message code="dashboard.profiles.table.lastUpdated" /></th>
						<th scope="col" class="narrow">&nbsp;</th>
						<th scope="col" class="narrow">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${profiles}" var="profile" varStatus="status">
						<tr>
							<td scope="col">${status.index + 1}</td>
							<td>${profile.name}</td>
							<td><joda:format value="${profile.updated}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
							<td><a href="javascript:void(0)" onclick="openProfileModal('${profile.id}');"><i class="fas fa-edit"></i></a></td>
							<td><a href="javascript:void(0)" onclick="deleteProfileAlert('${profile.id}');"><i class="fas fa-trash"></i></a>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	-->
</div>

<button class="btn btn-dark btn-lg fixed-bottom-right" onclick="openNewProfileModal();"><spring:message code="new.profile.modal.create" /></button>

<form id="delete-profile" action="delete-profile" method="post">
	<input name="id" type="hidden">
</form>

<jsp:include page="newProfileModal.jsp" />

<div class="modal fade" id="profileEditModal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="profile-edit-label" aria-hidden="true"></div>
<div class="modal fade" id="profileDeleteModal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="profile-delete-label" aria-hidden="true"></div>

<script>
	var PROFILE_SAVED = "profileSaved";
	var PROFILE_UPDATED = "profileUpdated";
	var PROFILE_DELETED = "profileDeleted";

	$(document).ready(function() {
		initializeTooltips();
		displayNotifications();
		chooseProfileCreation();
		$('.bootstrap-tagsinput').addClass('col-md-10 keywords');
	});
	
	function displayNotifications() {
		if ('${action}' == PROFILE_SAVED && '${not hasErrors}') {
			$.notify('<i class="fas fa-check"></i> <spring:message code="notification.profile.saved" />');
		}
		if ('${action}' == PROFILE_UPDATED && '${not hasErrors}') {
			$.notify('<i class="fas fa-check"></i> <spring:message code="notification.profile.updated" />');
		}
		if ('${action}' == PROFILE_DELETED && '${not hasErrors}') {
			$.notify('<i class="fas fa-check"></i> <spring:message code="notification.profile.deleted" />');
		}
		if ('${hasErrors}' == 'true') {
			$.notify({ message: '<i class="fas fa-exclamation-triangle"></i> <spring:message code="notification.common.error" />' }, { type: 'danger' });
		}
	}

	function openNewProfileModal() {
		$('#newProfileModal').modal('show');
	}
	
	function chooseProfileCreation() {
		$('[name="profile-option"]').click(function() {
			if ($(this).val() == 1) {
				$('#newProfileClean').hide();
				if ($('#createProfile [name="keywords"]').val().length > 0) {
					$('#newProfileContent').hide();
					$('#newProfileResult').show();
					$('#newProfileButtons').show();
				} else {
					$('#newProfileResult').hide();
					$('#newProfileButtons').hide();
					$('#newProfileContent').show();
				}
			} else {
				$('#newProfileContent').hide();
				$('#newProfileResult').hide();
				$('#newProfileClean').show();
				$('#newProfileButtons').show();
			}
		});
	}

	function appendInput() {
		var length = $('#websites [data-index]').length;
		var html = '<input type="text" name="website" class="form-control col-md-10 margin-10" data-index="' + length + '">' +
			'<a href="javascript:void(0)" ng-click="removeInput(' + length + ')"><i class="fas fa-minus fa-2x"></i></a>';
		$(html).appendTo('#websites');
	}
	
	function removeInput(index) {
		$($('[data-index]')[index]).next().remove();
		$($('[data-index]')[index]).remove();
	}
	
	function validateAndSubmit() {
		var expression = '^(?!mailto:)(?:(?:http|https|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?:(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[0-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))|localhost)(?::\\d{2,5})?(?:(/|\\?|#)[^\\s]*)?$';
		var regex = new RegExp(expression, "i");
		var validationError = false;
		
		$.each($('#websites [name="website"]'), function(i, obj) {
			if (!regex.test($(this).val())) {
				addValidationErrorMessage($(this), '<spring:message code="new.profile.modal.url.validation.error" />');
				validationError = true;
			} else {
				removeValidationErrorMessage($(this));
			}
		});
		
		if (!validationError) {
			submitForm();
		}
	} 
	
	function submitForm() {
		$('.profile-option').hide();
		$('#newProfileContent').hide();
    	$('#newProfileSpinner').show();
    	
		$.ajax({
			method : 'GET',
			url : 'collect-data',
			data :  $('#websites').serialize(),
			async : true,
			success : function(data) {
				$.each(data.keywords, function(i, keyword) {
					$('#createProfile [name="keywords"]').tagsinput('add', keyword.word);
				});
				
				$('#createProfile [name="urls"]').val(data.websites.toString());
	    		$('#newProfileSpinner').hide();
				$('#newProfileButtons').show();
	    		$('#newProfileResult').show();
	    		$('.profile-option').show();
			}
		});
	}
	
	function removeBadge(badge) {
		$(badge).parent().remove();
	}
	
	function clearProfile() {
		if ($('[name="profile-option"]:checked').val() == 1) {
			$.each($('#websites [name="website"]'), function(i, obj) {
				if ($(this).data('index') != '0') {
					$(this).next().remove();
					$(this).remove();
				} else {
					$(this).val('');
				}
			});
			$('#createProfile [name="name"]').val('');
			$('#createProfile [name="keywords"]').tagsinput('removeAll');
			$('#createProfile [name="urls"]').val('');
			$('#newProfileResult').hide();
			$('#newProfileButtons').hide();
			$('#newProfileContent').show();
		} else {
			$('#createProfileClean [name="name"]').val('');
			$('#createProfileClean [name="keywords"]').tagsinput('removeAll');
		}
	}
	
	function saveProfile() {
		if ($('[name="profile-option"]:checked').val() == 1) {
			var keywords = $('#createProfile [name="keywords"]').val().split(",");
			if (keywords.length < 5) {
				addValidationErrorMessage($('#createProfile .keywords'), '<spring:message code="new.profile.modal.error.must.contain.keywords" />');
				return;
			} else {
				removeValidationErrorMessage($('#createProfile .keywords'));
			}
			$('#createProfile').submit();
		} else {
			var keywords = $('#createProfileClean [name="keywords"]').val().split(",");
			if (keywords.length < 5) {
				addValidationErrorMessage($('#createProfileClean .keywords'), '<spring:message code="new.profile.modal.error.must.contain.keywords" />');
				return;
			} else {
				removeValidationErrorMessage($('#createProfileClean .keywords'));
			}
			$('#createProfileClean').submit();
		}
	}
	
	function openProfileModal(profileId) {
	    $('#profileEditModal').empty();
	    $('#profileEditModal').load('${pageContext.request.contextPath}/open-profile?id=' + profileId);
	    $('#profileEditModal').modal('show');
	}
	
	function saveProfileChanges() {
		var keywords = $('#profile-edit-content [name="keywords"]').val().split(",");
		if (keywords.length < 5) {
			addValidationErrorMessage($('#profile-edit-content .keywords'), '<spring:message code="new.profile.modal.error.must.contain.keywords" />');
			return;
		} else {
			removeValidationErrorMessage($('#profile-edit-content .keywords'));
		}
		$('#update-profile').submit();
	}
	
	function deleteProfileAlert(profileId) {
	    $('#profileEditModal').empty();
	    $('#profileEditModal').load('${pageContext.request.contextPath}/delete-profile-alert?id=' + profileId);
	    $('#profileEditModal').modal('show');
	}
	
	function deleteProfile(profileId) {
		$('#delete-profile [name="id"]').val(profileId);
		$('#delete-profile').submit();
	}
</script>
	
</custom:html>
