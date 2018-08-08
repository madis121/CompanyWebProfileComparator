<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>
	
	<div class="container">
		<div class="row">
		</div>
	</div>
	
	<button class="btn btn-dark btn-lg fixed-bottom-right" onclick="openNewProfileModal();">Loo profiil</button>

	<div class="modal fade" id="new-profile-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="new-profile-label" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title col-11 text-center" id="new-profile-label">Uue profiili loomine</h3>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div id="new-profile-content" class="col-md-12">
							<div class="form-group">
								<form id="websites" action="process" method="post">
									<input type="text" name="website" class="form-control col-md-10 margin-10" index="0"><a href="javascript:void(0)" onclick="appendInput();"><i class="fas fa-plus fa-2x"></i></a>
									<a href="javascript:void(0)" id="submit" class="btn btn-dark btn-block col-md-10 margin-10" onclick="validateAndSubmit();">Submit</a>
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
					'title': 'Sisestatud URL on vigane (http/https puudu?)'
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
					var div = '<div id="' + id + '"></div>';
					$(div).appendTo($('#new-profile-result'));
					
					var title = '<h5>' + website.url + '</h5><hr>';
					$(title).appendTo($('#' + id));
					
					$.each(website.keywords, function(j, object) {
			    		var badge = '<span class="badge badge-dark profile-badge">' + object.word + ' <a href="javascript:void(0)" onclick="removeBadge(this);">&times;</a></span>';
			    		$(badge).appendTo($('#' + id));
					});
				});
				
				var clearProfileButton = '<button id="clear-profile" class="btn btn-dark btn-lg btn-block" onclick="clearProfile();">Puhasta profiil</button>';
				$(clearProfileButton).appendTo($('#new-profile-result'));
				
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
</script>
	
</custom:html>
