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
	<script type="text/javascript">
		function cheClick(){
			var userId =$('input:radio[name="item"]:checked').val();
			var userName=$('input:radio[name="item"]:checked').next().html();
			$("#userId").val(userId);
			$("#userName").val(userName);
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
		        <input class="span2" name="name" type="text">
		        <div class="validate">${ERR_name}</div>
		        <%--<span class="add-on">i</span>--%>
		       </div>
			 </li>
			 
			 <li class="col-md-6  p0 ">
			   <span class="fl"> 版主：</span>
                 <%--<select name ="userId" class="w220" >
					<option></option>
			  	  	<c:forEach items="${users}" var="user">
			  	  		<option  value="${user.id}">${user.relName}</option>
			  	  	</c:forEach> 
	  			</select>
	  			
	  			--%>
	  			<div class="input-append">
                   <input class="span2" name ="userId" id="userId" type="hidden">
                   <input class="span2" name ="userName" id="userName" type="text">
                   <div class="btn-group">
                    <button aria-expanded="false" class="btn dropdown-toggle add-on" data-toggle="dropdown">
                      <img src="<%=basePath%>public/ZHH/images/down.png" >
                    </button>
                    <ul class="dropdown-menu list-unstyled" >
                        <c:forEach items="${users}" var="user">
                            <li class="select_opt">
                                <input type="radio" name="item" class="fl mt10" value="${user.id }" onclick="cheClick();" ><div  class="ml10 fl">${user.relName}</div>                              
                            </li>
                        </c:forEach> 
                    </ul>
                   </div>
                 </div>
                 </li>
			<li class="col-md-12  p0 ">	  	 			
				<span class="fl"> 版块介绍：</span>
				<div class="col-md-12 mt5 fn pl200 pwr9">
				<textarea  class="text_area col-md-12" name="content"></textarea>		
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
