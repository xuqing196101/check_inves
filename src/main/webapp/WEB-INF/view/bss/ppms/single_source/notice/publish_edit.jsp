<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
   
    function addAttach(){
        html="<input id='pic' type='file' class='toinline' name='files'/><a href='#' onclick='deleteattach(this)' class='toinline red redhover'>x</a><br/>";
        $("#uploadAttach").append(html);
    }
    
    function deleteattach(obj){
        $(obj).prev().remove();
        $(obj).next().remove();
        $(obj).remove();
    }
    
	</script>    
  </head>
  
  <body>
   <div class="container">
    <form  id ="form" action="${pageContext.request.contextPath}/single_source/publish.html" method="post" target="_parent" enctype="multipart/form-data">
       <input type="hidden" name="id" id="articleId" value="${articleId }">
       <ul class="list-unstyled list-flow p0_20">
	     <li class="col-md-12 p0">
	        <span class="fl">上传审批附件：</span>
	        <div class="fl" id="uploadAttach" >
	          <input id="pic" type="file" class="toinline" name="files"/>
	          <input class="toinline" type="button" value="添加" onclick="addAttach()"/><br/>
	        </div>
	     </li>
      </ul> 
             
      <div  class="">
       	<div class="">
        <button class="btn btn-windows apply" type="submit">发布</button>
        <input class="btn btn-windows back" value="取消" type="button" onclick="location.href='javascript:history.go(-1);'">
      	</div>
      </div>
	</form>
   </div>     
  </body>
</html>

