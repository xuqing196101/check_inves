<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>My JSP 'add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
    <link href="${ pageContext.request.contextPath }/public/select2/css/select2.css"  rel="stylesheet">

    <script src="${ pageContext.request.contextPath }/public/select2/js/select2.js"></script>
    <script src="${ pageContext.request.contextPath }/public/select2/js/select2_locale_zh-CN.js"></script>
	<script type="text/javascript">
	$(function () {
			 $.ajax({
	                url:"${ pageContext.request.contextPath }/park/getUserForSelect.do",   
	                contentType: "application/json;charset=UTF-8", 
	                dataType:"json",   //返回格式为json
	                type:"POST",   //请求方式           
	                success : function(users) {     
	                    if (users) {           
	                      $("#user").html("<option></option>");                
	                      $.each(users, function(i, user) {  
	                    	  if(user.relName != null && user.relName!=''){
	                    		  $("#user").append("<option  value="+user.id+">"+user.relName+"</option>"); 
	                    	  }	                                              
	                      });  
	                    }
	                    $("#user").select2();
	                    $("#user").select2("val", "${park.user.id}"); 
	                }
	            });			 

	});
	function change(id){
		$("#userId").val(id);
	}
	 		 
	</script>

  </head>
  
  <body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a >论坛管理</a></li><li class="active"><a >版块管理</a></li><li class="active"><a >增加版块</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <!-- 新增页面开始 -->
    <div class="container container_box"> 
	<form action="${ pageContext.request.contextPath }/park/save.html" method="post">  
    <div>	   
    <div class="headline-v2"> 
	   	<h2 class="count_flow">新增版块</h2>
	   </div>
	   <ul class="ul_list mb20">
	   		  
	   		 <li class="col-md-3 margin-0 padding-0 ">
			   <span class="col-md-12 padding-left-5 "><div class="red star_red">*</div> 版块名称：</span>
			   <div class="input-append">
		        <input class="span5" name="name" type="text" value = '${park.name}'>
		        <span class="add-on">i</span>
		        <div class="cue">${ERR_name}</div>
		       </div>
			 </li>
			 
			 <li class="col-md-3 margin-0 padding-0 ">
			 
			   <span class="col-md-12 padding-left-5 "> 版主：</span>
			         <div class="select_common">
			    	    <select id="user"  class="w250" onchange="change(this.options[this.selectedIndex].value)"></select> 
			    	</div>
			 </li>
			
             <input  type ="hidden" id="userId" name="userId"></input>
             
             <li class="col-md-11 margin-0 padding-0 ">
	   			<span class="col-md-12 padding-left-5 "> 版块介绍：</span>
        			<textarea class="col-md-12 h130" title="不超过800个字" name="content">${park.content}</textarea>
	 		</li> 
	 
			
	  	 </ul> 	
	<!-- 底部按钮 -->
			<div class="col-md-12 tc">		
	    		<button class="btn btn-windows save " type="submit">保存</button>
	    		<button class="btn btn-windows back " onclick="history.go(-1)" type="button">返回</button>
		
	  		</div>
    		</div>    
     	</form>
     </div>
  </body>
</html>
