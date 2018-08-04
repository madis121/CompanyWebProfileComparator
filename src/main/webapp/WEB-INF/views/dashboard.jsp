<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>
	
	<div class="container">
		<div class="row">
			<div class="col-md-6 offset-md-4">
				<div class="form-group">
					<form id="websites" action="process" method="post">
						<input type="text" name="website" class="form-control col-md-10 margin-1" index="0"><a href="javascript:void(0)" onclick="appendInput();"><i class="fas fa-plus fa-2x"></i></a>
						<a href="javascript:void(0)" id="submit" class="btn btn-primary btn-block col-md-10 margin-1" onclick="submitForm();">Submit</a>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="processingModal" tabindex="-1" role="dialog" aria-labelledby="processingModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<!-- <h5 class="modal-title" id="processingModalLabel"></h5> -->
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body text-center">
					 <h5>Mine munni</h5>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	
<script>
	$(document).ready(function() {
	    <c:if test="${not empty processedWebsites}">
	    	console.log("not empty");
	    	$("#processingModal").modal('show');
	    </c:if>
	});

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
		$('#websites').submit();
	}
</script>
	
</custom:html>
