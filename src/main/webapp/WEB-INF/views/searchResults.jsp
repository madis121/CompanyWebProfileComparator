<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>

<div class="container">
	<div class="row">
		<div class="heading col-md-12">
			<h3><spring:message code="searchResults.title" /></h3>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th scope="col" class="narrow">#</th>
						<th scope="col"><spring:message code="searchResults.name" /></th>
						<th scope="col"><spring:message code="searchResults.created" /></th>
						<th scope="col" class="narrow"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${searchResults}" var="searchResult" varStatus="status">
						<tr>
							<td scope="col">${status.index + 1}</td>
							<td>${searchResult.name}</td>
							<td><joda:format value="${searchResult.created}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
							<td><a href="javascript:void(0)" onclick="openSearchResultDetails('${searchResult.id}');"><i class="fas fa-info"></i></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<div class="modal fade" id="search-result-details-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="search-result-details-label" aria-hidden="true"></div>

<script>
	function openSearchResultDetails(id) {
	    $('#search-result-details-modal').empty();
	    $('#search-result-details-modal').load('${pageContext.request.contextPath}/search-results/details?id=' + id);
	    $('#search-result-details-modal').modal('show');
	}
</script>

</custom:html>