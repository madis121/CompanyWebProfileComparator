<!-- https://bootstrapious.com/p/bootstrap-sidebar -->
<div id="sidebar">
	<div class="sidebar-header">
		<h3>&nbsp;</h3>
	</div>

	<ul class="list-unstyled components">
		<li>
			<a href="${pagecontext.request.contextpath}">Profiilid</a>
		</li>
	</ul>
	
	<a href="javascript:void(0)" id="sidebar-collapse">
		<i class="fas fa-arrow-left fa-2x"></i>
	</a>
</div>

<script>
	$(document).ready(function() {
		/*
		$("#sidebar-collapse").click(function() {
			console.log("click");
			if (!$("#sidebar").hasClass("active")) {
				$("#sidebar").addClass("active");
			} else {
				$("#sidebar").removeClass("active");
			}
		});
		*/
	});
</script>