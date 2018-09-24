<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<div class="modal-dialog modal-lg" role="document">
	<div class="modal-content">
		<div class="modal-header">
			<h3 class="modal-title col-11 text-center" id="search-result-details-label">
				<c:out value="${searchResult.name}" /> (<joda:format value="${searchResult.created}" pattern="dd-MM-yyyy HH:mm:ss" />)
			</h3>
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
			</button>
		</div>
		<div class="modal-body">
			<div class="row">
				<div id="search-result-details-content" class="col-md-12">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th scope="col" class="narrow">#</th>
								<th scope="col"><spring:message code="searchResult.details.name" /></th>
								<th scope="col"><spring:message code="searchResult.details.website" /></th>
								<th scope="col"><spring:message code="searchResult.details.phone" /></th>
								<th scope="col"><spring:message code="searchResult.details.similarity" /></th>
								<th scope="col"><spring:message code="searchResult.details.subjects" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${searchResult.companyProfiles}" var="companyProfile" varStatus="status">
								<tr>
									<td scope="col">${status.index + 1}</td>
									<td><c:out value="${companyProfile.name}" /></td>
									<td><a href="${companyProfile.website}" target="_blank"><c:out value="${companyProfile.website}" /></a></td>
									<td><c:out value="${companyProfile.phone}" /></td>
									<td><c:out value="${companyProfile.similarity}" /></td>
									<td></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="col-md-12">
				<button class="btn btn-dark btn-lg float-left" data-dismiss="modal"><spring:message code="common.back" /></button>
			</div>
		</div>
	</div>
</div>