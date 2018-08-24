<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>

<div class="container">
	<div class="row">
		<div class="heading col-md-12">
			<h3><spring:message code="results.title" /></h3>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th scope="col" class="narrow">#</th>
						<th scope="col"><spring:message code="results.website" /></th>
						<th scope="col"><spring:message code="results.similarity" /></th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
</div>

</custom:html>