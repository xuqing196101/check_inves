<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
	<jsp:include page="/WEB-INF/view/common.jsp"/>
    <title>页签</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
  	<script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
    <link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
    <script type="text/javascript">
  
	 $(document).ready(function(){ 
	      var status="${status}";
	      if(status=="ok"){
	    	  var obj = document.getElementById("TANGER_OCX");
			  obj.SetReadOnly(false);
	    	  OpenFile(obj);  
	      } 
	 }); 
	 
	 function OpenFile(obj) {
		    var projectId = $("#contractId").val();
			obj.Menubar = false;
			obj.Caption = "( 双击可放大 ! )";
				obj.BeginOpenFromURL("${pageContext.request.contextPath}"
				+"/purchaseContract/loadFile.html?id="+projectId,true,false, 'word.document');// 异步加载, 服务器文件路径
			
		}
		
		
</script>
<body>
<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0);"> 首页</a></li>
				<li><a href="javascript:void(0);">业务监管系统</a></li>
				<li><a href="javascript:void(0);">采购业务监督</a></li>
				<li><a href="javascript:void(0);">采购合同监督</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
   
 <!-- 页签开始  -->  
 <div class="container content pt0">
 <div class="row magazine-page">
   <div class="col-md-12 tab-v2">
   <div class="col-md-12 col-xs-12 col-sm-12  mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
        <div class="padding-top-10">
				<form id="myForm" action="${pageContext.request.contextPath}/purchaseContract/validAddRe.html">
			    </div>
              </div>
            </div>
            <div class="tab-pane fade h800" id="tab-3">
             <div class="mt10 mb10">
	        <input type="button" class="btn btn-windows save" onclick="saveFile()" value="存至服务器"></input>
	    	</div>
            <form id="MyFile" method="post"  >
				<input type="hidden" id="ope" value="view">
    			<input type="hidden" id="contractId" value="${id}">
    			<input type="hidden" id="contractName" value="">
				<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
			</form>
          </div> 
		</div> 
		</form>
</body>
</html>
