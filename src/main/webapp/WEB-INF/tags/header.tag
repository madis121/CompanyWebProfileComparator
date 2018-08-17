<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="header">
	<nav class="navbar navbar-expand navbar-light bg-light">
		<div class="collapse navbar-collapse">
			<ul class="navbar-nav ml-auto" id="language-list">
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="javascript:void(0)" id="languages" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${currentLanguage} </a>
					<div class="dropdown-menu" aria-labelledby="languages">
						<c:forEach var="language" items="${supportedLanguages}">
							<a class="dropdown-item language" href="javascript:void(0)" onclick="changeLanguage('${language}');">${language}</a>
						</c:forEach>
					</div>
				</li>
			</ul>
		</div>
	</nav>
</div>

<form id="changeLanguage" action="changeLanguage" method="post">
	<input name="languageCode" type="hidden">
</form>

<script>
	$.each($('a.language'), function(i, obj) {
		if ($(this).html() == '${currentLanguage}') {
			$(this).remove();
		}
	});
	
	function changeLanguage(languageCode) {
		$('#changeLanguage [name="languageCode"]').val(languageCode);
		$('#changeLanguage').submit();
	}
</script>