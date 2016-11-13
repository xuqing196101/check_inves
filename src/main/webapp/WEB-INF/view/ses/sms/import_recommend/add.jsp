<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<script type="text/javascript">
 var parentId ;
 var addressId="${is.address}";
 $(document).ready(function(){
   for(var i=0;i<document.getElementById("type").options.length;i++)
    {
        if(document.getElementById("type").options[i].value == '${ir.type}')
        {
            document.getElementById("type").options[i].selected=true;
            break;
        }
    }
    });
    $(function(){
    	$.ajax({
			url : "${pageContext.request.contextPath}/area/listByOne.do",
			success:function(obj){
				var data = eval('(' + obj + ')');
				$.each(data,function(i,result){
					if(parentId == result.id){
						$("#choose1").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
					}else{
					$("#choose1").append("<option value='"+result.id+"'>"+result.name+"</option>");
					}
				});
			},
			error:function(obj){
			}
		});
	});
	function fun(){
		var parentId = $("#choose1").val();
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_area_by_parent_id.do",
			data:{"id":parentId},
			success:function(obj){
				$("#choose2").empty();
				$("#choose2").append("<option value=''>-请选择-</option>");
				$.each(obj,function(i,result){
					
					$("#choose2").append("<option value='"+result.id+"'>"+result.name+"</option>");
				});
			},
			error:function(obj){
			}
		});
	}
</script>
</head>
<body>


<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		  <li><a href="#"> 首页</a></li><li><a href="#">进口代理商</a></li><li><a href="#">进口代理商管理</a></li><li class="active"><a href="#">进口代理商增加</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container">
   <sf:form action="${pageContext.request.contextPath}/importRecommend/save.html" method="post">
   <div>
   <div class="headline-v2">
   <h2>进口代理商新增</h2>
   </div>
   <ul class="ul_list">
    <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">登录名：</span>
	   <div class="input-append">
        <input class="span2" id="loginName" name="loginName" value="${ir.loginName }" type="text">
        <span class="add-on">i</span>
        <div class="validate">${ERR_loginName}</div>
       </div>
	 </li> 
	 
     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">登录密码：</span>
	   <div class="input-append">
         <input class="span2" id="password" name="password" value="${ir.password }" type="text">
        <span class="add-on">i</span>
        <div class="validate">${ERR_password}</div>
       </div>
       
	 </li> 
	 
	  <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">企业名称：</span>
	   <div class="input-append">
         <input class="span2" id="name" name="name"  value="${ir.name }" type="text"> 
        <span class="add-on">i</span>
        <div class="validate">${ERR_name}</div>
       </div>
	 </li> 
	  <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">法定代表人：</span>
	   <div class="input-append">
         <input class="span2" id="legalName" name="legalName"  value="${ir.legalName }"   type="text">
        <span class="add-on">i</span>
        <div class="validate">${ERR_legalName}</div>
       </div>
	 </li> 
	
	  <li class="col-md-3 margin-0 padding-0 ">
		   <span class="col-md-12 padding-left-5">企业地址：</span>
     		   <div class="select_common">
     		    <select id="choose1" class="w100" onchange="fun();">
					<option  class="w100" >-请选择-</option>
				</select>
				<select name="address" class="w100" id="choose2">
					<option class="w100">-请选择-</option>
				</select>
			    <div class="validate">${ERR_address}</div>
			    </div>
	   </li> 
	 
	     <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">进口代理商类型：</span>
	   <div class="select_common">
        <select id="type" class="w220" name="type">
           <option value="1">正式代理商</option>
           <option value="2">临时代理商</option>
        </select>
       </div>
	 </li> 
	  <li class="col-md-3 margin-0 padding-0 ">
	   <span class="col-md-12 padding-left-5">推荐单位：</span>
	   <div class="input-append">
         <input class="span2" id="recommendDep" name="recommendDep"  value="${ir.recommendDep }"  type="text">
        <span class="add-on">i</span>
        <div class="">${ERR_recommendDep}</div>
       </div>
	 </li> 
   </ul>
   </div>
		   <div class="col-md-12 tc mt20" >
			   <button class="btn btn-windows save"  type="submit">保存</button>
			   <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
       	   </div>
   </sf:form>
  </div> 
</body>
</html>
