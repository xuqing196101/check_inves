<%@ page language="java" import="java.util.*,bss.util.PropUtil" pageEncoding="UTF-8"%>

<%
  //生产环境
  String environment = PropUtil.getProperty("environment");
  //内外网
  String ipAddressType = PropUtil.getProperty("ipAddressType");
%>

<!-- 前端css样式 -->
<link href="${pageContext.request.contextPath}/public/front/images/favicon.ico" rel="shortcut icon" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/public/front/css/bootstrap.min.css" rel="stylesheet"  type="text/css" />
<link href="${pageContext.request.contextPath}/public/front/css/common.css" rel="stylesheet"  type="text/css" />
<link href="${pageContext.request.contextPath}/public/front/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/public/front/css/global.css" rel="stylesheet"  type="text/css" />
<link href="${pageContext.request.contextPath}/public/front/css/btn.css" rel="stylesheet"  type="text/css" />
<link href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" rel="stylesheet" >
<link href="${pageContext.request.contextPath}/public/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<!-- 前端论坛Css颜色样式 -->
<link href="${pageContext.request.contextPath}/public/front/css/forum.css" rel="stylesheet" type="text/css">
<script>
	var globalPath = "${pageContext.request.contextPath}";
</script>


<!-- 前端js -->
<script src="${pageContext.request.contextPath}/public/front/js/jquery.min.js"></script>
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

<!-- front -->
<!--[if lt IE 9]>
  <script src="${pageContext.request.contextPath}/public/common/respond.src.js"></script>
<![endif]-->

