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
	
    <link href="${ pageContext.request.contextPath }/public/select2/css/select2.min.css"  rel="stylesheet">
    <script src="${ pageContext.request.contextPath }/public/select2/jquery-2.1.0.js"></script>
    <script src="${ pageContext.request.contextPath }/public/select2/js/select2.min.js"></script>
 
	<script type="text/javascript">
	$(function () {
			 $.ajax({
	                url:"<%=basePath %>park/getUserForSelect.do",   
	                contentType: "application/json;charset=UTF-8", 
	                dataType:"json",   //返回格式为json
	                type:"POST",   //请求方式           
	                success : function(users) {     
	                    if (users) {           
	                      $("#user").html("<option></option>");                
	                      $.each(users, function(i, user) {  
	                          $("#user").append("<option  value="+user.id+">"+user.relName+"</option>");                     
	                      });  
	                    }
	                }
	            });			 
		        $("#user").select2(); 
		        

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
     <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
    <form action="<%=basePath %>park/save.html" method="post">  
    <div>
	    <div class="headline-v2">
	   		<h2>新增版块</h2>
	   </div>
	   <ul class="list-unstyled list-flow p0_20">
	   		  
	   		   <li class="col-md-6  p0 ">
			   <span class="fl"><div class="red star_red">*</div> 版块名称：</span>
			   <div class="input-append">
		        <input class="span2" name="name" type="text" value = '${park.name}'>
		        <div class="validate">${ERR_name}</div>
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 
			  
			 <li class="col-md-6  p0 ">
			   
			   <span class="fl"> 版主：</span>
				<select id="user"  class="w230" onchange="change(this.options[this.selectedIndex].value)">
				</select> 
				   
                 </li>
                 <input  type ="hidden" id="userId" name="userId"></input>
			<li class="col-md-12  p0 ">	  	 			
				<span class="fl"> 版块介绍：</span>
				<div class="col-md-12 mt5 fn pl200 pwr9">
				<textarea  class="text_area col-md-12" name="content" >${park.content}</textarea>		
				</div>			
	  	 	</li>
	  	 </ul>
	</div>  	
	<!-- 底部按钮 -->
	<div class="padding-top-10 clear">		          
	  <div  class="col-md-12 pl185 ">
	   <div class="mt40 tc mb50">
	    <button class="btn btn-windows save " type="submit">保存</button>
	    <button class="btn btn-windows back " onclick="history.go(-1)" type="button">返回</button>
		</div>
	  </div>
    </div>    
     </form>
     </div>
     </div>
  </body>
</html>
