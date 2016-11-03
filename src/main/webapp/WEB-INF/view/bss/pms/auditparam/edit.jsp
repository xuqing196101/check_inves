<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
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
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
	<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>public/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath%>public/ztree/css/zTreeStyle.css" type="text/css">
	
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/lodop/LodopFuncs.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.excheck.js"></script>
</head>
<script type="text/javascript">

</script>
<body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
	   <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   	<li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">附件类型管理</a></li><li class="active"><a href="#">修改附件类型</a></li>
		   </ul>
		   <div class="clear"></div>
	   </div>
   </div>
   <div class="container bggrey border1 mt20">
   	   <div id="pContent" class="pContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treep" class="ztree"  ></ul>
	   </div>
   	   <sf:form action="${pageContext.request.contextPath}/param/update.html" method="post" modelAttribute="dd">
		   <div>
			   <div class="headline-v2 bggrey">
			   		<h2>修改附件类型</h2>
			   </div>
			   <input type="hidden" name="id" id="dId" value="${aparam.id }">
			   <input type="hidden" name="page" id="currpage" value="${page }">
			   <ul class="list-unstyled list-flow ul_list">
			  
			   	  
				 	<li class="col-md-6 p0">
					   	<span class="span2"><div class="fr">审核轮次：</div><div class="red">*</div></span>
					     <select name="dictioanryId" >
				    	
				    		<option value="">请选择</option>
				    		<c:forEach items="${dic }" var="obj">
				    			<option value="${obj.id }" <c:if test="${aparam.dictioanryId==obj.id}"> selected="selected"</c:if> >${obj.name }</option>
				    		</c:forEach>
				    	</select>
				 
				 	</li>
				 	
				 	
				 	
				 	<li class="col-md-6 p0">
					   	<span class="span2"><div class="fr">审核参数：</div><div class="red">*</div></span>
				<!-- 	   	<div class="input-append pr"> -->
					       
					       <select name="param" >
				    	    	<option value="1" <c:if test="${'1'==aparam.param}"> selected="selected"</c:if>>采购方式</option>
							    <option value="2" <c:if test="${'2'==aparam.param}"> selected="selected"</c:if>>采购机构</option>
							    <option value="3" <c:if test="${'3'==aparam.param}"> selected="selected"</c:if>>其他意见</option>
								<option value="4" <c:if test="${'4'==aparam.param}"> selected="selected"</c:if>>技术参数意见</option>
				    	</select>
					       
					      <!--   <span class="add-on">i</span> -->
					      <%--   <div class="b f14 red tip pa l260"><sf:errors path="name"/></div> --%>
				      <!--  	</div> -->
				 	</li>
			   	</ul>
		   </div> 
	       <div class="col-md-12 tc mt20" >
				<input class="btn btn-windows save"   type="submit" value="修改"> 
				<input type="button"  class="btn padding-left-20 padding-right-20 btn_back" value="返回"  onclick="location.href='javascript:history.go(-1);'"> 

       	   </div>
  	   </sf:form>
   </div>
</body>
</html>
