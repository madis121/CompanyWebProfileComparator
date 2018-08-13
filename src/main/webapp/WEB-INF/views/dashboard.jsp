<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>
	
	<div class="container">
		<div class="row">
			<c:if test="${action eq 'save'}">
				<div class="alert alert-success alert-dismissible fade show col-12 text-center">
				  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				  <strong><spring:message code="dashboard.create.new.profile.success" /></strong>
				</div>
			</c:if>
			
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th scope="col">#</th>
						<th scope="col"><spring:message code="dashboard.profiles.table.name" /></th>
						<th scope="col"><spring:message code="dashboard.profiles.table.lastUpdated" /></th>
						<th scope="col">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${profiles}" var="profile" varStatus="loop">
						<tr>
							<td scope="col">${loop.index}</td>
							<td>${profile.name}</td>
							<td><joda:format value="${profile.updated}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
							<td><a href="javascript:void(0)" onclick="showProfileEditModal('${profile.id}');"><i class="fas fa-edit"></i></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
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
							<div class="form-group">
								<form id="websites" autocomplete="on">
									<input type="text" name="website" class="form-control col-md-10 margin-10" index="0"><a href="javascript:void(0)" onclick="appendInput();"><i class="fas fa-plus fa-2x"></i></a>
									<a href="javascript:void(0)" id="submit" class="btn btn-dark btn-block col-md-10 margin-10" onclick="validateAndSubmit();"><spring:message code="new.profile.modal.create" /></a>
								</form>
							</div>
						</div>
						
						<i id="new-profile-spinner" class="fas fa-spinner fa-spin fa-5x col-md-11 text-center" style="display:none;"></i>
						<div id="new-profile-result" class="col-md-12" style="display:none;"></div>
					</div>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="test-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="test-modal-label" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title col-11 text-center" id="test-modal-label">Test</h3>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">

						</div>
					</div>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="profile-edit-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="profile-edit-label" aria-hidden="true"></div>
	
<script>
	function openNewProfileModal() {
		$('#new-profile-modal').modal('show');
	}

	function appendInput() {
		var length = $('[index]').length;
		var html = '<input type="text" name="website" class="form-control col-md-10 margin-10" index="' + length + '">' +
			'<a href="javascript:void(0)" onclick="removeInput(' + length + ');"><i class="fas fa-minus fa-2x"></i></a>';
		$('#submit').before(html);
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
			success : function(websites) {
				console.log(websites);

				$.each(websites, function(i, website) {
					var id = 'website-' + i;
					var div = '<div id="' + id + '" class="website-data"></div>';
					$(div).appendTo($('#new-profile-result'));
					
					var title = '<h5>' + website.url + '</h5><hr>';
					$(title).appendTo($('#' + id));
					
					$.each(website.keywords, function(j, object) {
			    		var badge = '<span class="badge badge-dark keyword" keyword="' + object.word + '">' + object.word + ' <a href="javascript:void(0)" onclick="removeBadge(this);">&times;</a></span>';
			    		$(badge).appendTo($('#' + id));
					});
				});
				
				var clearProfileButton = '<a class="btn btn-dark btn-lg profile-btn float-left" onclick="clearProfile();"><spring:message code="new.profile.modal.clear" /></a>';
				$(clearProfileButton).appendTo($('#new-profile-result'));
				
				var saveProfileButton = '<a class="btn btn-dark btn-lg profile-btn float-right" onclick="saveProfile();"><spring:message code="new.profile.modal.save" /></a>';
				$(saveProfileButton).appendTo($('#new-profile-result'));
				
	    		$('#new-profile-spinner').hide();
	    		$('#new-profile-result').show();
			}
		});
	}
	
	function removeBadge(badge) {
		$(badge).parent().remove();
	}
	
	function clearProfile() {
		$('#new-profile-result').html('');
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
		var $form = $('<form />', { action: 'save-profile', method: 'POST' });
		var name = $('<input />', { name: 'name', type: 'hidden', value: 'saved-profile' });
		$form.append(name);
		
		$.each($('#new-profile-result h5'), function(i, obj) {
			var url = $('<input />', { name: 'url', type: 'hidden', value: $(this).html() });
			$form.append(url);
		});
		
		$.each($('#new-profile-result [keyword]'), function(i, obj) {
			var keyword = $('<input />', { name: 'keyword', type: 'hidden', value: $(this).attr('keyword') });
			$form.append(keyword);
		});
		
		$form.appendTo($('#new-profile-result'));
		$form.submit();
	}
	
	function showProfileEditModal(profileId) {
	    $('#profile-edit-modal').empty();
	    $('#profile-edit-modal').load('${pageContext.request.contextPath}/edit-profile?id=' + profileId);
	    $('#profile-edit-modal').modal('show');
	}
	
	function saveProfileChanges() {
		console.log('saveProfileChanges');
	}
</script>
	
</custom:html>
