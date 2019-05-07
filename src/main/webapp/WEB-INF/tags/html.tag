<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>${title}</title>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap-notify.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/tagsinput.js"></script>
	<script defer src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.7.8/angular.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.7.8/angular-route.js"></script>
	<script src="${pageContext.request.contextPath}/js/appServices.js"></script>
	<script src="${pageContext.request.contextPath}/js/appControllers.js"></script>
	<script src="${pageContext.request.contextPath}/js/app.js"></script>
	
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/tagsinput.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
</head>
<body ng-app="cwpc">
	<script>
		$(document).ready(function() {
			$.ajaxSetup({ async: false });
		});
	
		function initializeTooltips() {
			$("[data-toggle=tooltip").tooltip();
		}
		
		function initializeInputTags() {
			$('input[data-role="tagsinput"]').tagsinput('items');
		}
		
		function addValidationErrorMessage(element, text) {
			element.attr({
				'data-toggle': 'tooltip',
				'title': text
			});
			element.addClass('contains-errors');
			initializeTooltips();
		}
		
		function removeValidationErrorMessage(element) {
			element.removeAttr('data-toggle title');
			element.removeClass('contains-errors');
		}
	</script>
	
	<div class="wrapper">
		<custom:header></custom:header>
	
		<custom:sidebar></custom:sidebar>
		
		<div id="content">
			<jsp:doBody />
		</div>
	</div>
</body>
</html>