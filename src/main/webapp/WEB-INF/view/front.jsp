<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!-- 前端css样式 -->
<link href="${pageContext.request.contextPath}/public/front/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/front/css/common.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/front/css/style.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/front/css/header-v4.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/front/css/footer-v2.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/front/css/btn.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/public/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" rel="stylesheet" >   

<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application" />
<script>
	var globalPath = "${contextPath}";
</script>


<!-- 前端js -->
<script src="${pageContext.request.contextPath}/public/backend/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/front/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/front/js/common.js"></script>
<script src="${pageContext.request.contextPath}/public/front/js/main-menu.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<!-- 文本编辑器 -->  
<script src="${pageContext.request.contextPath}/public/ueditor/ueditor.config.js"></script>
<script src="${pageContext.request.contextPath}/public/ueditor/ueditor.all.js"> </script>
<script src="${pageContext.request.contextPath}/public/ueditor/lang/zh-cn/zh-cn.js"></script>
<script src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.all.js"></script>
<script src="${pageContext.request.contextPath}/public/front/js/jquery.validate.min.js"></script>

