<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>分配任务人员信息</title>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
	<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
    
    
  </head>
<script type="text/javascript">
  function sure(){
	 var user = $("#users").select2("val");
	  if(user.trim()==""){
		  layer.alert("审价人员不能为空",{offset: ['100px', '90px'], shade:0.01});
	  }else{
		  $.ajax({
			  type: "POST",  
	          url: "${pageContext.request.contextPath}/appraisalContract/updateDistribution.html",  
	          data: $("#form1").serializeArray(),  
	          dataType: 'json',  
	          success:function(result){
	        	  if(!result.success){
	                  layer.msg(result.msg,{offset: ['150px', '180px']});
	              }else{
	                  parent.window.setTimeout(function(){
	                	  parent.window.location.href = "${pageContext.request.contextPath}/appraisalContract/selectDistribution.html";
	                  }, 1000);
	                  layer.msg(result.msg,{offset: ['150px', '180px']});
	              }
	          	},
		  });
	  }
  }
  
  
  $(function(){
	  $.ajax({
		  contentType: "application/json;charset=UTF-8",
		  url:"${pageContext.request.contextPath }/appraisalContract/selectUser.html",
	      type:"POST",
	      dataType: "json",
	      success:function(users){
	    	  if(users){
	    		  $("#users").append("<option></option>");
	    		  $.each(users,function(i,user){
	    			  if(user.relName != null && user.relName != ''){
	    				  $("#users").append("<option value="+user.id+">"+user.relName+"</option>");
	    			  }
	    		  });
	    	  }
	    	  $("#users").select2();
	      }
	  });
  })
 
  
  
  function cancel(){
	  window.parent.location.reload();
  }
  
  
</script>
  
  <body>
  
  <div class="container mt20">
  	<form id="form1" action="${pageContext.request.contextPath}/appraisalContract/updateDistribution.html" method="post">
  		<input type="hidden" value="${id }" name="id" id="id">
  		
	 	<ul class="list-unstyled mb20">
	 		<li class="col-md-12 margin-0 padding-0">
			   <div class="col-md-12 padding-left-5"><div class="star_red">＊</div>审价员：</div>
			   <div class="col-md-12 input-append p0">
		       	 <select class="w220" id="users" name="user.id">
				 </select>
			   </div>
			 </li>
			 <li class="col-md-12 margin-0 padding-0 clear">
			 	<div class="col-md-12 padding-left-5">审价任务：</div>
			 	<div class="col-md-12 padding-left-5">
		          <textarea class="col-md-12 h80 w220" id="appraisalTask" name="appraisalTask" title="不超过250个字" placeholder="不超过250个字"></textarea>
			    </div>
			 </li>
	 	</ul>
	 	
    	<div  class="col-md-12 tc mt20 clear">
	   			<button class="btn btn-windows add" type="button" onclick="sure()">确定</button>
			    <button class="btn btn-windows cancel" type="button" onclick="cancel()">取消</button>
	   	</div>
   </form>	
    
 </div>
    
</body>
</html>
