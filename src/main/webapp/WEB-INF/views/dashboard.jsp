<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<custom:html>
	
<div class="container" ng-controller="DashboardController" ng-cloak>
	<div class="row">
		<div class="heading col-md-12">
			<h3><spring:message code="dashboard.title" /></h3>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th scope="col" class="narrow">#</th>
						<th scope="col"><spring:message code="dashboard.profiles.table.name" /></th>
						<th scope="col"><spring:message code="dashboard.profiles.table.lastUpdated" /></th>
						<th scope="col" class="narrow">&nbsp;</th>
						<th scope="col" class="narrow">&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="profile in profiles">
						<td scope="col">{{$index + 1}}</td>
						<td>{{profile.name}}</td>
						<td>{{profile.updated}}</td>
						<td><a href="javascript:void(0)" ng-click="openEditProfileModal(profile.id)"><i class="fas fa-edit"></i></a></td>
						<td><a href="javascript:void(0)" ng-click="openDeleteProfileModal(profile.id)"><i class="fas fa-trash"></i></a>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<jsp:include page="newProfileModal.jsp" />
	<jsp:include page="editProfileModal.jsp" />
	<jsp:include page="deleteProfileModal.jsp" />
	
	<button class="btn btn-dark btn-lg fixed-bottom-right" ng-click="openNewProfileModal()"><spring:message code="new.profile.modal.create" /></button>
</div>

</custom:html>
