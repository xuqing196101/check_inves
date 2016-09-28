<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>

<title>..</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript">
</script>  
</head>

<body>
    <form id="att" action="<%=basePath%>project/delTask.html" id="myForm"
        method="post" name="form1" class="simple" target="_parent">
        <input type="hidden" name="id" value="${task.id}"/>
        <span class="f14 fl">项目名称：</span>
        <div class="fl" >
          <input id="pic" type="text" class="toinline" name="name"/>
        </div>
        <br/><br/><br/>
         <span class="f14 fl">项目编号：</span>
        <div class="fl" >
          <input id="pic" type="text" class="toinline" name="projectNumber"/>
        </div>
        <br/><br/><br/>
     <input class="btn btn-windows save" type="submit" value="确认"/>
    </form>
</body>
</html>
