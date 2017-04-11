<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">

	function back(){
		var kind = $("#k").val();
		window.location.href = '${pageContext.request.contextPath}/dictionaryData/dictionaryDataList.html?page=1&kind='+kind;
	}
</script>
</head>
<body>
   <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
	   <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   	<li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">数据字典</a></li><li class="active"><a href="#">增加数据字典</a></li>
		   </ul>
		   <div class="clear"></div>
	   </div>
   </div>
   <div class="container container_box">
   	   <sf:form action="${pageContext.request.contextPath}/dictionaryData/save.html" method="post" modelAttribute="dictionaryData">
		   <div>
			   <h2 class=" list_title">添加数据</h2>
			   <input type="hidden" name="kind" id="k"  value="${kind }">
			   <ul class="ul_list">
			   	 	<li class="col-md-3 col-sm-6 col-xs-12 pl15"> 
			   	 		<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>编码</span>
					   	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
					        <input class="input_group" name="code" value="${dd.code }" maxlength="40" type="text">
					        <span class="add-on">i</span>
					        <div class="cue"><sf:errors path="code"/></div>
					        <div class="cue">${exist}</div>
				       	</div>
				 	</li>
				 	<li class="col-md-3 col-sm-6 col-xs-12">
					   	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>名称</span>
					   	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
					        <input class="input_group" name="name" value="${dd.name }"  type="text">
					        <span class="add-on">i</span>
					        <div class="cue"><sf:errors path="name"/></div>
				       	</div>
				 	</li>
				 	<li class="col-md-3 col-sm-6 col-xs-12">
					   	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>排序</span>
					   	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
					        <input class="input_group" name="position" value="${dd.position }"  type="text" onkeyup="value=value.replace(/[^\d]/g,'')">
					        <span class="add-on">i</span>
					        <div class="cue"><sf:errors path="position"/></div>
				       	</div>
				 	</li>
				 	<li class="col-md-12 col-sm-12 col-xs-12">
                        <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">描述</span>
			 	   		<div class="col-md-12 col-sm-12 col-xs-12 p0">
			        		<textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" name="description"  title="" placeholder="请输入100字以内中文描述">${dd.description }</textarea>
			      		</div>
				 	</li>
			   	</ul>
		   </div> 
	       <div class="col-md-12 tc mt20" >
			   <button class="btn btn-windows save"  type="submit">保存</button>
			   <button class="btn btn-windows back" onclick="back()" type="button">返回</button>
       	   </div>
  	   </sf:form>
   </div>
</body>
</html>
