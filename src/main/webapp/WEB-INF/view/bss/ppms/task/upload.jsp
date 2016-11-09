<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tld/upload" prefix="f"%>
<%@ include file="../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${pageContext.request.contextPath}/">
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>

<title>..</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript">
function cancel(){
     var index=parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
     
}
</script>  
</head>

<body>
    <form id="att" action="${pageContext.request.contextPath}/task/deleteTask.html" id="myForm"
        method="post" name="form1" class="simple" target="_parent">
        <input type="hidden" name="id" value="${task.id}"/>
        <div id="delTask">
            <f:upload id="upload_id" businessId="${task.id}" typeId="${dataId}" sysKey="2"/>
            <f:show showId="upload_id" businessId="${task.id}" sysKey="2" typeId="${dataId}"/>
        </div>
        <input class="btn btn-windows save" type="submit" value="确认"/>
         <input class="btn btn-windows reset" value="取消" type="button" onclick="cancel();">
    </form>
</body>
</html>
