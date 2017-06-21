<%@ page language="java" import="java.util.*,bss.util.PropUtil" pageEncoding="UTF-8"%>

<%
  //生产环境
  String environment = PropUtil.getProperty("environment");
  //内外网
  String ipAddressType = PropUtil.getProperty("ipAddressType");
%>
<meta name="viewport" content="user-scalable=no, width=device-width" />  
<script src="${pageContext.request.contextPath}/public/ueditor/ueditor.config.js"></script>
<script src="${pageContext.request.contextPath}/public/ueditor/ueditor.all.js"> </script>
<script src="${pageContext.request.contextPath}/public/ueditor/lang/zh-cn/zh-cn.js"></script>
