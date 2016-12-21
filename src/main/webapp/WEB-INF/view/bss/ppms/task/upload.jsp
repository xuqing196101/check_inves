<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
  function cancel(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
  }
  
  
  function delTask(id){
    var upload_id = $("#upload_id").val();
    $("#myForm").submit();
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
          <u:upload id="upload_id"  businessId="${task.id}" auto="true" typeId="${dataId}" sysKey="2"/>
          <u:show showId="upload_id" businessId="${task.id}" sysKey="2" typeId="${dataId}"/>
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
