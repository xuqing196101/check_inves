<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
  <%@ include file="/WEB-INF/view/common.jsp" %>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title></title>

    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <script type="text/javascript">
      function loadPageFirst(id,url){
	    var projectId = $("#projectId").val();
	  	var path = "${pageContext.request.contextPath}/"+url+"?projectId="+projectId;
	  	$("#"+id).load(path);
	  }
	  function loadPageSecond(id,url){
	    var projectId = $("#projectId").val();
	  	var path = "${pageContext.request.contextPath}/"+url+"?projectId="+projectId;
	  	$("#"+id).load(path);
	  }
	  
	  //页面默认加载第一页内容
	  $(function() { 
	  	var projectId = $("#projectId").val();
	  	var path = "${pageContext.request.contextPath}/open_bidding/selectSupplierByProject.html?projectId="+projectId;
	  	$("#tab-1").load(path);
	  });
    </script>
  </head>

  <body>
	<input type="hidden" id="projectId" value="${projectId}">
 	<div class="row magazine-page">
	   	<div class="col-md-12 tab-v2">
	        <div class="padding-top-10">
	          <ul class="nav nav-tabs bgwhite">
	            <li class="active" onclick="loadPageFirst('tab-1','open_bidding/selectSupplierByProject.html')"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18">上传投标文件</a></li>
				<li class="" onclick="loadPageSecond('tab-2','open_bidding/changtotal.html');"><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18">总价报价</a></li>
	          </ul>
	          <div class="tab-content padding-top-20">
	            <div class="tab-pane fade active in" id="tab-1">
	            </div>
	            <div class="tab-pane fade " id="tab-2" style="position：relative">
	          	</div> 
		      </div> 
	     	</div>
	  	</div>
	</div>
  </body>
</html>