<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
</head>
<script type="text/javascript">

</script>
<body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
	   <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   	<li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">支撑系统</a></li><li><a href="javascript:void(0);">后台管理</a></li><li class="active"><a href="javascript:void(0);">附件类型管理</a></li><li class="active"><a href="javascript:void(0);">修改附件类型</a></li>
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
