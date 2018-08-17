<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>
	
<div class="container">
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
</div>

<button class="btn btn-dark btn-lg fixed-bottom-right" onclick="openNewProfileModal();"><spring:message code="new.profile.modal.create" /></button>

<div class="modal fade" id="new-profile-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="new-profile-label" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title col-11 text-center" id="new-profile-label"><spring:message code="new.profile.modal.title" /></h3>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
				</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div id="new-profile-content" class="col-md-12">
						<form id="websites" autocomplete="on">
							<div class="form-group">
								<input type="text" name="website" class="form-control col-md-10 margin-10" index="0"><a href="javascript:void(0)" onclick="appendInput();"><i class="fas fa-plus fa-2x"></i></a>
							</div>
						</form>
						<button id="collect-data" class="btn btn-dark btn-block col-md-10 margin-10" onclick="validateAndSubmit();"><spring:message code="new.profile.modal.create" /></button>
					</div>
					
					<i id="new-profile-spinner" class="fas fa-spinner fa-spin fa-5x col-md-11 text-center" style="display:none;"></i>
					
					<div id="new-profile-result" class="col-md-12" style="display:none;">
						<form id="create-profile" action="create-profile" method="post">
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
			<div class="modal-footer">
				<div id="new-profile-buttons" class="col-md-12" style="display:none;">
					<button class="btn btn-dark btn-lg float-left" onclick="clearProfile();"><spring:message code="new.profile.modal.clear" /></button>
					<button class="btn btn-dark btn-lg float-right" onclick="saveProfile();"><spring:message code="new.profile.modal.save" /></button>
				</div>
			</div>
		</div>
	</div>
</div>

<form id="delete-profile" action="delete-profile" method="post">
	<input name="id" type="hidden">
</form>

<div class="modal fade" id="profile-edit-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="profile-edit-label" aria-hidden="true"></div>
<div class="modal fade" id="profile-delete-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="profile-delete-label" aria-hidden="true"></div>

<script>
	var PROFILE_SAVED = "profileSaved";
	var PROFILE_UPDATED = "profileUpdated";
	var PROFILE_DELETED = "profileDeleted";

	$(document).ready(function() {
		displayNotifications();
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
		$('#new-profile-modal').modal('show');
	}

	function appendInput() {
		var length = $('[index]').length;
		var html = '<input type="text" name="website" class="form-control col-md-10 margin-10" index="' + length + '">' +
			'<a href="javascript:void(0)" onclick="removeInput(' + length + ');"><i class="fas fa-minus fa-2x"></i></a>';
		$('#collect-data').before(html);
	}
	
	function removeInput(index) {
		$($('[index]')[index]).next().remove();
		$($('[index]')[index]).remove();
	}
	
	function validateAndSubmit() {
		var expression = '^(?!mailto:)(?:(?:http|https|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?:(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[0-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))|localhost)(?::\\d{2,5})?(?:(/|\\?|#)[^\\s]*)?$';
		var regex = new RegExp(expression, "i");
		var validationError = false;
		
		$.each($('#websites [name="website"]'), function(i, obj) {
			if (!regex.test($(this).val())) {
				$(this).attr({
					'data-toggle': 'tooltip',
					'title': '<spring:message code="new.profile.modal.url.validation.error" />'
				});
				$(this).addClass('contains-errors');
				initializeTooltips();
				validationError = true;
			} else {
				$(this).removeAttr('data-toggle title');
				$(this).removeClass('contains-errors');
			}
		});
		
		if (!validationError) {
			submitForm();
		}
	} 
	
	function submitForm() {
		$('#new-profile-content').hide();
    	$('#new-profile-spinner').show();
    	
		$.ajax({
			method : 'GET',
			url : 'collect-data',
			data :  $('#websites').serialize(),
			async : true,
			success : function(websites) {
				console.log(websites);
				var urls = [];

				$.each(websites, function(i, website) {
					urls.push(website.url);
					
					$.each(website.keywords, function(j, object) {
			    		$('#create-profile [name="keywords"]').tagsinput('add', object.word);
					});
				});
				
				$('#create-profile [name="urls"]').val(urls.toString());
				$('.bootstrap-tagsinput').addClass('col-md-10');;
	    		$('#new-profile-spinner').hide();
				$('#new-profile-buttons').show();
	    		$('#new-profile-result').show();
			}
		});
	}
	
	function removeBadge(badge) {
		$(badge).parent().remove();
	}
	
	function clearProfile() {
		$('#create-profile [name="name"]').val('');
		$('#create-profile [name="keywords"]').tagsinput('removeAll');
		$('#create-profile [name="urls"]').val('');
		$('#new-profile-buttons').hide();
		$('#new-profile-result').hide();
		$('#new-profile-content').show();
		
		$.each($('[name="website"]'), function(i, obj) {
			if ($(this).attr('index') != "0") {
				$(this).next().remove();
				$(this).remove();
			} else {
				$(this).val('');
			}
		});
	}
	
	function saveProfile() {	
		$('#create-profile').submit();
	}
	
	function openProfileModal(profileId) {
	    $('#profile-edit-modal').empty();
	    $('#profile-edit-modal').load('${pageContext.request.contextPath}/open-profile?id=' + profileId);
	    $('#profile-edit-modal').modal('show');
	}
	
	function saveProfileChanges() {
		$('#update-profile').submit();
	}
	
	function deleteProfileAlert(profileId) {
	    $('#profile-edit-modal').empty();
	    $('#profile-edit-modal').load('${pageContext.request.contextPath}/delete-profile-alert?id=' + profileId);
	    $('#profile-edit-modal').modal('show');
	}
	
	function deleteProfile(profileId) {
		$('#delete-profile [name="id"]').val(profileId);
		$('#delete-profile').submit();
	}
</script>
	
</custom:html>
