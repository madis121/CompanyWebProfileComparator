<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- https://bootstrapious.com/p/bootstrap-sidebar -->
<div id="sidebar">
	<div class="sidebar-header">
		<h3>&nbsp;</h3>
	</div>

	<ul class="components">
		<li class="sidebar-item">
			<a href="${pageContext.request.contextPath}/"><i class="fas fa-address-card"></i>&nbsp;<span><spring:message code="sidebar.profiles" /></span></a>
		</li>
		<li class="sidebar-item">
			<a href="${pageContext.request.contextPath}/company-search"><i class="fas fa-search"></i>&nbsp;<span><spring:message code="sidebar.search" /></span></a>
		</li>
		<li class="sidebar-item">
			<a href="${pageContext.request.contextPath}/search-results"><i class="fas fa-vials"></i>&nbsp;<span><spring:message code="sidebar.results" /></span></a>
		</li>
	</ul>
</div>