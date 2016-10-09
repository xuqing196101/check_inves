<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/purchase/css/purchase.css" media="screen" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>初审项定义</title>
<script type="text/javascript">

	function close(){
		parent.layer.close(index);
	}
</script>
</head>
<body>
<div>
	<form action="<%=basePath %>firstAudit/edit.html" method="post" id="form1">
     <table class="table table-bordered table-condensed">
      <tr>
        <th>初审项名称:</th><td><input type="text" name="name" value="${firstAudit.name }"></td>
        <th>要求类型:</th><td><input type="checkbox" name="kind" <c:if test="${fn:contains(firstAudit.name,'商务')}">selected</c:if> value="商务" >商务&nbsp;<input type="checkbox" name="kind"<c:if test="${fn:contains(firstAudit.name,'技术')}">selected</c:if> value="技术" >技术</td>
        <th>创建人:</th><td><input name="creater" type="text" value="${firstAudit.kind }"></td>
      </tr>
      <tr>
      <input type="hidden" name="projectId" value="${firstAudit.projectId }">
      <input type="hidden" name="id" value="${firstAudit.id }">
      <input type="submit"  value="修改"  class="btn btn-windows add"/>
      <input type="button"  value="取消"  class="btn btn-windows add" onclick="close();"/>
      <a class="btn btn-windows reset"  onclick="location.href='javascript:history.go(-1);'">返回</a>
      </tr>
    </table>
  </form>
</div>
</body>
</html>