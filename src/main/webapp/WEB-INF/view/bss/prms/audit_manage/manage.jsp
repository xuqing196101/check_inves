<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <title>My JSP 'expert_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
  	  //显示或隐藏各包评审项
	  function viewAndHidden(obj,index){
		  var classNames = $(obj).attr("class");
		  if (classNames.indexOf("jbxx") != -1) {
	          $("#"+index).removeAttr("class");
	          //隐藏
	          $("#"+index).attr("class","dnone");
	          $(obj).removeAttr("class");
	          $(obj).attr("class","count_flow hand zhxx");
	      }
	      if (classNames.indexOf("zhxx") != -1) {
	          //显示
	          $("#"+index).removeAttr("class");
	          $(obj).removeAttr("class");
	          $(obj).attr("class","count_flow jbxx hand");
	      }
	  }
	  
	  //加载页面
	  function loadPage(id,url){
	  	$.ajaxSetup({cache:false});
	    var projectId = $("#projectId").val();
  	    var flowDefineId = $("#flowDefineId").val();
	  	var path = "${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
	  	$("#"+id).load(path);
	  }
	  
	  //加载页面,不知道为什么不能一个方法调用。。
	  function loadPageOne(id,url){
	  	$.ajaxSetup({cache:false}); 
	    var projectId = $("#projectId").val();
  	    var flowDefineId = $("#flowDefineId").val();
	  	var path = "${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
	  	$("#"+id).load(path);
	  }
	  /* function loadPageTwo(id,url){
	    var projectId = $("#projectId").val();
  	    var flowDefineId = $("#flowDefineId").val();
	  	var path = "${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
	  	$("#"+id).load(path);
	  } */
	  function loadPageThree(id,url){
	  	$.ajaxSetup({cache:false}); 
	    var projectId = $("#projectId").val();
  	    var flowDefineId = $("#flowDefineId").val();
	  	var path = "${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
	  	$("#"+id).load(path);
	  }
	  function loadPageFour(id,url){
	  	$.ajaxSetup({cache:false}); 
	    var projectId = $("#projectId").val();
  	    var flowDefineId = $("#flowDefineId").val();
	  	var path = "${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
	  	$("#"+id).load(path);
	  }
	  function loadPageFive(id,url){
	  	$.ajaxSetup({cache:false}); 
	    var projectId = $("#projectId").val();
  	    var flowDefineId = $("#flowDefineId").val();
	  	var path = "${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
	  	$("#"+id).load(path);
	  }
	  function loadPageSix(id,url){
	  	$.ajaxSetup({cache:false}); 
	    var projectId = $("#projectId").val();
  	    var flowDefineId = $("#flowDefineId").val();
	  	var path = "${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
	  	$("#"+id).load(path);
	  }
	  function loadPageSeven(id,url){
	  	$.ajaxSetup({cache:false}); 
		var projectId = $("#projectId").val();
	  	var flowDefineId = $("#flowDefineId").val();
		var path = "${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
		$("#"+id).load(path);
	  }
	  function loadPageEight(id,url){
	  	$.ajaxSetup({cache:false}); 
		var projectId = $("#projectId").val();
	  	var flowDefineId = $("#flowDefineId").val();
		var path = "${pageContext.request.contextPath}/"+url+"?projectId="+projectId+"&flowDefineId="+flowDefineId;
		//alert(path);
		$("#"+id).load(path);
	  }
	  
	  //页面默认加载第一页内容
	  $(function() { 
	  	$.ajaxSetup({cache:false});
	  	var projectId = $("#projectId").val();
  	    var flowDefineId = $("#flowDefineId").val();
	  	var path = "${pageContext.request.contextPath}/packageExpert/assignedExpert.html?projectId="+projectId+"&flowDefineId="+flowDefineId;
	  	$("#tab-1").load(path);
	  });
  </script>
  <body>
  	<input type="hidden" id="projectId" value="${projectId}">
  	<input type="hidden" id="flowDefineId" value="${flowDefineId}">
 	<div class="magazine-page">
	   	<div class="col-md-12 tab-v2 p0">
	        <div class="padding-top-10">
	          <ul class="nav nav-tabs bgwhite expert_tab">
	            <li class="active" onclick="loadPageOne('tab-1','packageExpert/assignedExpert.html')"><a aria-expanded="true" href="#tab-1" data-toggle="tab">专家名单</a></li>
	            <!-- <li class="" onclick="loadPageTwo('tab-2','packageExpert/toAuditProgress.html');"><a aria-expanded="false" href="#tab-2" data-toggle="tab">各包分配评委</a></li> -->
							<li class="" onclick="loadPageThree('tab-3','packageExpert/toSupplierQuote.html');"><a aria-expanded="false" href="#tab-3" data-toggle="tab">供应商报价表</a></li>
				<!-- <li class="" onclick="loadPageFour('tab-4','packageExpert/toAuditProgress.html');"><a aria-expanded="false" href="#tab-4" data-toggle="tab">评审进度</a></li> -->
							<c:if test="${kind ne 'DYLY'}">
	            	<li class="" onclick="loadPageFive('tab-5','packageExpert/toFirstAudit.html');"><a aria-expanded="false" href="#tab-5" data-toggle="tab">符合性和资格性检查</a></li>
								<li class="" onclick="loadPageEight('tab-8','packageExpert/confirmSupplier.html');"><a aria-expanded="false" href="#tab-8" data-toggle="tab">合格供应商</a></li>
								<li class="" onclick="loadPageSix('tab-6','packageExpert/toScoreAudit.html');"><a aria-expanded="false" href="#tab-6" data-toggle="tab">经济技术评审(审查)</a></li>
								<li class="" onclick="loadPageSeven('tab-7','packageExpert/supplierRank.html');"><a aria-expanded="false" href="#tab-7" data-toggle="tab">供应商排名</a></li>
							</c:if>	
	          </ul>
	          <div class="tab-content padding-top-20">
	            <div class="tab-pane fade active in" id="tab-1">
	            </div>
	            <div class="tab-pane fade " id="tab-2">
	            	
	            </div>
	            <div class="tab-pane fade " id="tab-3">
	            	
	            </div>
	            <!-- <div class="tab-pane fade " id="tab-4">
	            	
	            </div> -->
	            <div class="tab-pane fade " id="tab-5">
	            	
	            </div>
	            <div class="tab-pane fade " id="tab-8">
	            	
	            </div>
	            <div class="tab-pane fade " id="tab-6" style="position：relative">
				  
	          	</div>
	          	<div class="tab-pane fade " id="tab-7">
	            	
	            </div> 
		      </div> 
	     	</div>
	  	</div>
	</div>
  </body>
</html>
