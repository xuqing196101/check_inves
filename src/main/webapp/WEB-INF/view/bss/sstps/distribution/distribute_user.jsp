<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>分配任务人员信息</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
    <script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
    
  </head>
<script type="text/javascript">
  function sure(){
	  $.ajax({
		  type: "POST",  
          url: "<%=basePath %>appraisalContract/updateDistribution.html",  
          data: $("#form1").serializeArray(),  
          dataType: 'json',  
          success:function(result){
        	  if(!result.success){
                  layer.msg(result.msg,{offset: ['150px', '180px']});
              }else{
                  parent.window.setTimeout(function(){
                	  parent.window.location.href = "<%=basePath%>appraisalContract/selectDistribution.html";
                  }, 1000);
                  layer.msg(result.msg,{offset: ['150px', '180px']});
              }
          	},
	  });
  }
  
  function cancel(){
	  window.parent.location.reload();
  }
  
  
</script>
  
  <body>
  
  <div class="container mt20">
  	<form id="form1" action="<%=basePath %>appraisalContract/updateDistribution.html" method="post">
  		<input type="hidden" value="${id }" name="id" id="id">
  		
	 	<ul class="list-unstyled p0_20 list-flow">
	 		<li class="col-md-12 p0">
			   <span class="w100 tr">审价员：</span>
		       	<select class="w220 fl" name="user.id">
					<option></option>
					<c:forEach items="${user }" var="user" varStatus="vs">
					<option value="${user.id }">${user.relName }</option>
					</c:forEach>
				</select>
			 </li>
			 <li class="col-md-12 p0 clear">
			 	<span class="fl tr w100">审价任务：</span>
		        	<textarea class="text_area w220 mt10" id="appraisalTask" name="appraisalTask" title="不超过250个字" placeholder="不超过250个字"></textarea>
			 </li>
	 	</ul>
	 	
	 	<%--<div class="container padding-left-25 padding-right-25">
	 		<table class="table table-bordered">
	 			<tobody>
	 				<tr>
	 					<td width="25%" class="bggrey tr">审价员：</td>
	 					<td width="25%"><select>
					    		<option></option>
					    		<c:forEach items="${user }" var="user" varStatus="vs">
					    			<option value="${user.id }">${user.relName }</option>
					    		</c:forEach>
					    	</select>
    					</td>
	 				</tr>
	 					<td width="25%" class="bggrey tr">审价任务</td>
	 					<td width="25%"></td>
	 				<tr>
	 				</tr>
	 			</tobody>
	 		</table>
	 	</div>--%>
	 	
    	<div  class="col-md-12 tc mt20 clear">
	   			<button class="btn btn-windows add" type="button" onclick="sure()">确定</button>
			    <button class="btn btn-windows cancel" type="button" onclick="cancel()">取消</button>
	   	</div>
   </form>	
    
 </div>
    
</body>
</html>
