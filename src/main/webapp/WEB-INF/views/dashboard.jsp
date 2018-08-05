<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>
	
	<div class="container">
		<div class="row">
		</div>
	</div>
	
	<button class="btn btn-dark btn-lg fixed-bottom-right" onclick="openNewProfileModal();">Koosta profiil</button>

	<div class="modal fade" id="new-profile-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="new-profile-label" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="new-profile-label">Koosta profiil</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
					</button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div id="new-profile-content" class="col-md-12">
							<div class="form-group">
								<form id="websites" action="process" method="post">
									<input type="text" name="website" class="form-control col-md-10 margin-1" index="0"><a href="javascript:void(0)" onclick="appendInput();"><i class="fas fa-plus fa-2x"></i></a>
									<a href="javascript:void(0)" id="submit" class="btn btn-dark btn-block col-md-10 margin-1" onclick="submitForm();">Submit</a>
								</form>
							</div>
						</div>
					</div>
					
					<div class="text-center">
						<i id="new-profile-spinner" class="fas fa-spinner fa-spin fa-5x" style="display:none;"></i>
						<div id="new-profile-result" style="display:none;">
							<h2>Mine munni</h2>
						</div>
					</div>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	
<script>
	$(document).ready(function() {
		
	});
	
	function openNewProfileModal() {
		$('#new-profile-modal').modal('show');
	}

	function appendInput() {
		var length = $('[index]').length;
		var html = '<input type="text" name="website" class="form-control col-md-10 margin-1" index="' + length + '">' +
			'<a href="javascript:void(0)" onclick="removeInput(' + length + ');"><i class="fas fa-minus fa-2x"></i></a>';
		$('#submit').before(html);
	}
	
	function removeInput(index) {
		$($('[index]')[index]).next().remove();
		$($('[index]')[index]).remove();
	}
	
	function submitForm() {
		$.ajax({
			method : 'POST',
			url : 'process',
			data :  $('#websites').serialize(),
			success : function(response) {
				$('#new-profile-content').hide();
		    	$('#new-profile-spinner').show();
		    	
		    	setTimeout(function() {
		    		$('#new-profile-spinner').hide();
		    		$('#new-profile-result').show();
		    	}, 3000);
			}
		});
	}
</script>
	
</custom:html>
