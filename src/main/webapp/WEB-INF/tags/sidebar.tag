<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- https://bootstrapious.com/p/bootstrap-sidebar -->
<div id="sidebar">
	<div class="sidebar-header">
		<h3>&nbsp;</h3>
	</div>

	<ul class="components">
		<li class="sidebar-item">
			<a href="${pageContext.request.contextPath}"><i class="fas fa-address-card"></i>&nbsp;<span><spring:message code="sidebar.profiles" /></span></a>
		</li>
	</ul>
	
	<a href="javascript:void(0)" id="sidebar-collapse">
		<i class="fas fa-arrow-left fa-2x"></i>
	</a>
</div>

<script>
	$(document).ready(function() {
		$('#sidebar-collapse').click(function() {
			if (!$('#sidebar').hasClass('active')) {
				$('#sidebar').addClass('active');
				$('#sidebar-collapse').addClass('active');
				$('#header').addClass('active');
				
				$.each($('#sidebar .sidebar-item'), function(i, obj) {
					$(this).addClass('active');
				});
			} else {
				$('#sidebar').removeClass('active');
				$('#sidebar-collapse').removeClass('active');
				$('#header').removeClass('active');
				
				$.each($('#sidebar .sidebar-item'), function(i, obj) {
					$(this).removeClass('active');
				});
			}
		});
	});
</script>