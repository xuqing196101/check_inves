<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
  </head>
<body>
 
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   		<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank"> 首页</a></li><li><a href="javascript:void(0)">支撑系统</a></li><li><a href="javascript:void(0)">后台管理</a></li><li class="active"><a href="javascript:void(0)">修改须知文档</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 修改订列表开始-->
   <div class="container container_box">
   		<form action="${pageContext.request.contextPath}/noticeDocument/update.do" method="post">
   		<div>
        <h2 class="list_title">修改须知文档</h2>
   		<ul class="ul_list">
   		<input class="span2" name="id" type="hidden" value="${noticeDocument.id}">
     		  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>须知文档名称</span>
               <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
		        	<input class="input_group" name="name" type="text" value="${noticeDocument.name}">
		        	 <span class="add-on">i</span>
		        	<div id="contractCodeErr" class="cue">${ERR_name}</div>
		       </div>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12">
                  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>须知文档类型</span>
                  <div class="col-md-12 col-sm-12 col-xs-12 p0 select_common">
                    <select id="docType" name =docType>
          			   <c:forEach items="${noticeType}" var="type">
          			     <option value="${type.id}" <c:if test="${type.id == noticeDocument.docType }"> selected="selected"</c:if >>${type.name}</option>
          			   </c:forEach>
	  				 </select>
                    <div id="contractCodeErr" class="cue">${ERR_docType}</div>
                  </div>
             </li>
		       <li class="col-md-12 col-sm-12 col-xs-12">
                      <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>须知文档内容</span>
                        <div class="col-md-12 col-sm-12 col-xs-12 p0">
	  				 <script id="editor" name="content" type="text/plain" class=""></script>
	  				 <div id="contractCodeErr" class="clear red">${ERR_content}</div>
       			</div>
			 </li> 
	 		
			 
   			</ul>
  		<div  class="col-md-12 col-sm-12 col-xs-12 tc">
    		<button class="btn btn-windows save" type="submit">保存</button>
    		<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
  		</div>
  		</div>
  		</form>
 	</div>
 
<script type="text/javascript">
    var ue = UE.getEditor('editor');
    var content='${noticeDocument.content}';
	ue.ready(function(){
  		ue.setContent(content);    
	});
    
</script>
</body>
</html>
