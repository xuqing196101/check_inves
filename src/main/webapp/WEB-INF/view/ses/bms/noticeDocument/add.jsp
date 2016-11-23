<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<script type="text/javascript">
  	/** 全选全不选 */
	$(function(){
		
		if(${noticeDocument.docType!=null}&&${noticeDocument.docType!=""}){
			$("#docType").val("${noticeDocument.docType}");
		}else{
			$("#docType").val('-请选择-');
		}
		
	});
  </script>
	
  </head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">新增须知文档</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 新增须知文档开始-->
   <div class="container container_box">
   		<form action="${pageContext.request.contextPath}/noticeDocument/save.do" method="post">
   		<div> 
		<h2 class="list_title">新增须知文档</h2> 
   		<ul class="ul_list">
		    <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>须知文档名称</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
		        <input class="input_group" name="name" type="text" value="${noticeDocument.name}">
		        <span class="add-on">i</span>
		       </div>
		       <div id="contractCodeErr" class="clear red">${ERR_name}</div>
			 </li>
    		 <li class="col-md-3 col-sm-6 col-xs-12">
                 <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>须知文档类型</span>
          	     <div class="col-md-12 col-sm-12 col-xs-12 p0 select_common">
          			<select id="docType" name =docType>
						<option value="-请选择-">-请选择-</option>
			  	  	 	<option value="供应商须知文档">供应商须知文档</option>
			  	  	 	<option value="专家须知文档">专家须知文档</option>
	  				</select>
	  				<div id="contractCodeErr" class="clear red">${ERR_docType}</div>
	  			 </div>
			 </li>
		     
			  <li class="col-md-12 col-sm-12 col-xs-12">
                      <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>须知文档内容</span>
                     <div class="col-md-12 col-sm-12 col-xs-12 p0">
	  				 <script id="editor" name="content" type="text/plain"></script>
        			<div id="contractCodeErr" class="clear red">${ERR_content}</div>
        			<!-- <textarea class="text_area col-md-12 " title="不超过800个字" placeholder="不超过800个字"></textarea> -->
       			</div>
			 </li> 
		     
	 		<%-- <li class="col-md-6 p0">
			   <span class="">角色：</span>
			   <div class="input-append">
			   	 <input class="span2" id="roleId" name="roleId" type="hidden">
		         <input class="span2" id="roleName" name="roleName" type="text">
				 <div class="btn-group ">
		          <button class="btn dropdown-toggle add-on" data-toggle="dropdown">
				  <img src="<%=basePath%>public/ZHH/images/down.png" class="margin-bottom-5"/>
		          </button>
		          <ul class="dropdown-menu list-unstyled">
		          	<c:forEach items="${roles}" var="role" varStatus="vs">
		          		<li class="select_opt">
		          			<input type="checkbox" name="chkItem" value="${role.id };${role.name }" onclick="cheClick();" class="select_input">${role.name }
		          		</li>
		          	</c:forEach>
		          </ul>
		       </div>
		      </div>
			 </li> --%>
			 
   		</ul>
   
  		<div  class="col-md-12 tc">
    			<button class="btn btn-windows save" type="submit">保存</button>
    			<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  		</div>
  		</div>
  	</form>
 </div>
 
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    var content='${noticeDocument.content}';
	ue.ready(function(){
  		ue.setContent(content);    
	});
</script>
</body>
</html>
