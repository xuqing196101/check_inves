<%@ page language="java" import="java.util.*,bss.util.PropUtil" pageEncoding="UTF-8"%>

<%
  //生产环境
  String environment = PropUtil.getProperty("environment");
  //内外网
  String ipAddressType = PropUtil.getProperty("ipAddressType");
%>
<meta name="viewport" content="user-scalable=no, width=device-width" />  
<link href="${pageContext.request.contextPath}/public/portal/images/favicon.ico" rel="shortcut icon" type="image/x-icon" />

<!-- css -->
<link href="${pageContext.request.contextPath}/public/portal/css/portal.bootstrap.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/bxslider/jquery.bxslider.min.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/portal/css/portal_common.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/portal/css/portal_style.css" media="screen" rel="stylesheet">
<link href="${pageContext.request.contextPath}/public/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css" type="text/css" rel="stylesheet" >

<!-- js -->
<script>
  var globalPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/public/portal/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/public/portal/js/browser.js"></script>
<script src="${pageContext.request.contextPath}/public/portal/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/bxslider/jquery.bxslider.min.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
<script src="${ pageContext.request.contextPath }/public/laypage-v1.3/laypage/laypage.js"></script>
<!-- 时间插件 -->
<script src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
<script src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.all.js"></script>

<%-- <script src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.all.js" type="text/javascript"></script> --%>
<!-- portal -->
<!--[if lt IE 9]>
  <script src="${pageContext.request.contextPath}/public/common/respond.src.js"></script>
<![endif]-->