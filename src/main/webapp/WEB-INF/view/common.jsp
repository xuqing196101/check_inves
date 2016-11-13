<%@ page language="java" import="java.util.*,bss.util.PropUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link href="${pageContext.request.contextPath}/public/backend/css/bootstrap.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/backend/css/common.css" media="screen" rel="stylesheet" type="text/css">	
<link href="${pageContext.request.contextPath}/public/backend/css/unify.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/global.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/backend/css/btn.css" media="screen" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css">
<link href="${pageContext.request.contextPath}/public/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
    


<!-- js -->
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/> 
<script>
	var globalPath = "${contextPath}";
</script>
<script src="${pageContext.request.contextPath}/public/backend/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/browser.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/jquery.ba-hashchange.min.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/common.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/masterslider.js"></script>
<!-- 待删除的 -->
<script src="${pageContext.request.contextPath}/public/accordion/SpryAccordion.js"></script>

<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script src="${pageContext.request.contextPath}/public/select2/js/select2.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/main-menu.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
<script src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.all.js"></script>
<!-- 时间插件 -->
<script src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>


<!-- 文本编辑器 -->  
<script src="${pageContext.request.contextPath}/public/ueditor/ueditor.config.js"></script>
<script src="${pageContext.request.contextPath}/public/ueditor/ueditor.all.js"> </script>
<script src="${pageContext.request.contextPath}/public/ueditor/lang/zh-cn/zh-cn.js"></script>

<!-- echars -->
<script src="${pageContext.request.contextPath}/public/echarts/echarts.js"></script>
<!-- 验证-->
<script src="${pageContext.request.contextPath}/public/backend/js/jquery.validate.min.js"></script>