<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tld/upload" prefix="f"%>
<%@ include file="../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
  function cancel(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
  }
  
  
  function delTask(id){
    var upload_id = $("#upload_id").val();
    if(upload_id){
      $("#myForm").submit();
    }else{
      layer.tips("请上传附件", "#uuId");
    } 
  }
</script>  
</head>

<body>
  <form  action="${pageContext.request.contextPath}/task/deleteTask.html" id="myForm"  method="post" name="form1" class="" target="_parent">
    <input type="hidden" name="id" value="${task.id}"/>
    <div class="drop_window" id="delTask">
      <ul class="list-unstyled">
        <li class="mt10 col-md-12 p0">
          <span id="uuId"></span>
          <f:upload id="upload_id" businessId="${task.id}" typeId="${dataId}" sysKey="2"/>
          <f:show showId="upload_id" businessId="${task.id}" sysKey="2" typeId="${dataId}"/>
        </li>
	    <div class="clear"></div>
      </ul>
    </div>
    <div class="tc mt10 col-md-12">
      <input class="btn btn-windows save" type="button" value="确认" onclick="delTask();"/>
      <input class="btn btn-windows back" value="取消" type="button" onclick="cancel();">
    </div>
  </form>
</body>
</html>
