<!-- css -->
<link href="${pageContext.request.contextPath}/public/backend/images/favicon.ico"  rel="shortcut icon" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/public/backend/css/bootstrap.min.css" media="screen" rel="stylesheet">

<link href="${pageContext.request.contextPath}/public/backend/css/common.css" media="screen" rel="stylesheet" type="text/css">	

<link href="${pageContext.request.contextPath}/public/backend/css/header-v4.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/backend/css/header-v5.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/backend/css/footer-v4.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/backend/css/footer-v2.css" media="screen" rel="stylesheet">

<!-- js -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/> 
<script>
	var globalPath = "${contextPath}";
</script>
<script src="${pageContext.request.contextPath}/public/backend/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/common.js"></script>
<script src="${pageContext.request.contextPath}/public/accordion/SpryAccordion.js"></script>

<!-- front -->
<!--[if lt IE 9]>
  <script src="${pageContext.request.contextPath}/public/common/respond.src.js"></script>
<![endif]-->