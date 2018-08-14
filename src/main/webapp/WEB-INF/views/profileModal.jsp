<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="modal-dialog modal-lg" role="document">
	<div class="modal-content">
		<form:form id="update-profile" action="update-profile" method="post" modelAttribute="profile">
			<div class="modal-header">
				<h3 class="modal-title col-11 text-center" id="profile-edit-label"><spring:message code="profile.edit.modal.title" /></h3>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
				</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div id="profile-edit-content" class="col-md-12">
						<div class="form-group row col-md-12">
							<form:label path="name" class="col-form-label col-md-2"><spring:message code="profile.edit.modal.name" /></form:label>
							<form:input path="name" type="text" class="form-control col-md-10" id="" />
							<c:forEach items="${profile.urls}" var="url" varStatus="status">
								<form:input path="urls[${status.index}]" type="hidden" id="" />
							</c:forEach>
						</div>
						<!--
						<div class="form-group row col-md-12">
							<form:label path="keywords" class="col-form-label col-md-2"><spring:message code="profile.edit.modal.keywords" /></form:label>
							<form:input path="keywords" class="form control col-md-10" id="" data-role="tagsinput" />
						</div>
						-->
						<div class="website-data col-md-12">
							<c:forEach items="${profile.keywords}" var="keyword">
								<span class="badge badge-dark keyword" keyword="${keyword.word}">${keyword.word} <a href="javascript:void(0)" onclick="removeBadge(this);">&times;</a></span>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="col-md-12">
					<a class="btn btn-dark btn-lg profile-btn float-left" data-dismiss="modal"><spring:message code="common.back" /></a>
					<a class="btn btn-dark btn-lg profile-btn float-right" onclick="saveProfileChanges();"><spring:message code="common.save" /></a>
				</div>
			</div>
		</form:form>
	</div>
</div>