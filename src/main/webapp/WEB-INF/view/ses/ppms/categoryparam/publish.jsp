<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'publish.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css"> 
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>

<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>
	<script type="text/javascript">
	$(function(){
	    var name  = "${cateParam.name}";
	  
	    var value = "${cateParam.valueType}";
	    var names = name.split(",");
	  
	    var values = value.split(",");
	     var html = "";
	     for ( var i = 0 ; i< names.length-1; i++){
				html = html +"<tr><td>参数名称：<input type='text' value='"+names[i]+"'/></td><td>"
				+"<select  name='valueType'>"
				+"<option value='' selected='selected'>"+values[i]+"</option>"
				+"<option value='字符型'>字符型</option>"
				+"<option value='数字型'>数字型</option>"
				+"<option value='日期'>日期</option><select/></td></tr>";
	     }
	  
	      $("#result").prepend(html);
	});
	 $(function(){
	       var obj ="${category.paramPublishRange}";
	       var v2= obj.split(',');
	       for ( var i = 0; i < v2.length; i++) {
	       $("input[name='range']").each(function(){
	       if($(this).val()==v2[i])
	           $(this).attr("checked",true);
	       });
		} 
	   });
	
	</script>
  </head>
  
  <body>
 	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品参数管理</a></li><li><a href="#">参数发布</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
  
	<div class=" tag-box tag-box-v3 mt10 col-md-9">
	
	 <span><input type="submit" value="发布" class="btn"  /></span>
	 <span><a href="javascript:void(0);" onclick="location.href='javascript:history.go(-1);'" class="btn btn-windows back">返回</a></span>
	       
	      <form action="<%=basePath%>categoryparam/publish_param.html" method="post">
	          <input type="hidden" id="id" name="id" value="${category.id }"/>
	          <table id="result" class="table table-bordered table-condensedb mt15">
	          <tr><td>验收规范</td>
	              <td><textare>${category.acceptRange}</textare></td>          
	          </tr>
	          <tr><td >是否公开</td>
					 <td>
					 <span class="ml30"><input type="radio" value="0" name="ispublish" <c:if test="${category.isPublish eq 0}">checked</c:if>/>是</span>
					 <span class="ml60"><input type="radio" value="1" name="ispublish" <c:if test="${category.isPublish eq 1}">checked</c:if> />否</span>
					 </td></tr>
	          <tr><td>公示范围</td>
	                 <td>
	                 <span class="ml30"><input  type="checkbox" name="range" value="0"/>外网</span>
	                  <span class="ml60"><input  type="checkbox" name="range" value="1"/>内网</span>
	          </td></tr>
	          </table>
	      </form>
	</div>
	</div>
  </body>
</html>
