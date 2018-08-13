<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="modal-dialog modal-lg" role="document">
	<div class="modal-content">
		<div class="modal-header">
			<h3 class="modal-title col-11 text-center" id="profile-edit-label"><spring:message code="profile.edit.modal.title" /></h3>
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true"><i class="fas fa-times fa-1x"></i></span>
			</button>
		</div>
		<div class="modal-body">
			<div class="row">
				<div id="profile-edit-content" class="col-md-12">
					<div class="website-data">
					<h5>${profile.name}</h5>
					<hr>
					<c:forEach items="${profile.keywords}" var="keyword">
						<span class="badge badge-dark keyword" keyword="${keyword.word}">${keyword.word} <a href="javascript:void(0)" onclick="removeBadge(this);">&times;</a></span>
					</c:forEach>
				</div>
				<a class="btn btn-dark btn-lg profile-btn float-left" data-dismiss="modal"><spring:message code="common.back" /></a>
				<a class="btn btn-dark btn-lg profile-btn float-right" onclick="saveProfileChanges();"><spring:message code="common.save" /></a>
				</div>
			</div>
		</div>
		<div class="modal-footer"></div>
	</div>
</div>