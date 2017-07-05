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
    <script type="text/javascript">
    var obj="";
    function OpenFile() {
     var projectId="${pur.id}";
		  obj = document.getElementById("TANGER_OCX");
			obj.Caption = "( 双击可放大 ! )";
			obj.BeginOpenFromURL("${pageContext.request.contextPath}"
			    +"/purchaseContract/loadFile.html?id=" + projectId,true,false, 'word.document');// 异步加载, 服务器文件路径
		}
		function exportWord() {
			var obj = document.getElementById("TANGER_OCX");
			// 参数说明
			// 1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
			//obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html", "bidFile", "", "bid.doc", "MyFile");
		}
		
		function queryVersion(){
		
			var obj = document.getElementById("TANGER_OCX");
			var v = obj.GetProductVerString();
			obj.ShowTipMessage("当前ntko版本",v);
		}
		
		function saveFile(){
			var projectId = $("#contractId").val();
			var obj = document.getElementById("TANGER_OCX");
			var projectName = $("#contract_code").val();
			//参数说明
			//1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
			obj.SaveToURL("${pageContext.request.contextPath}/purchaseContract/saveContractFile.html?projectId="+projectId,"ntko", "", projectName+"_合同文件.doc", "MyFile");
			obj.ShowTipMessage("提示","已上传至服务器");
		}
		
		function closeFile(){
			var obj = document.getElementById("TANGER_OCX");
			obj.close();
		}
		
		function abandoned(){
			window.history.back(-1); 
		}
</script>
<body onload="OpenFile()">
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">保障作业</a></li><li><a href="javascript:void(0);">采购合同管理</a></li><li class="active"><a href="javascript:void(0);">打印合同</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
 <!-- 页签开始  -->  
 <div class="container content pt0">
 <div class="row magazine-page">
   <div class="col-md-12 tab-v2">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgwhite">
			<li class="active"><a aria-expanded="true" href="#tab-3" data-toggle="tab" class="record f18">合同文本</a></li>
          </ul>
          <form id="contractForm" action="${pageContext.request.contextPath}/purchaseContract/addPurchaseContract.html?ids=${id}" method="post">
          <div class="tab-content padding-top-20">
            <div class="tab-pane fade active in h800" id="tab-3">
             <div class="mt10 mb10">
	      	 <!-- <input type="button" class="btn btn-windows cancel" onclick="delMark()" value="删除标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="searchMark()" value="查看标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="mark()" value="标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="closeFile()" value="关闭当前文档"></input> -->
	      	 <!-- <input type="button" class="btn btn-windows " onclick="queryVersion()" value="版本查询"></input> -->
	     	<!-- <input type="button" class="btn btn-windows input" onclick="inputTemplete()" value="模板导入"></input> -->
	        <%--<input type="button" class="btn btn-windows save" onclick="saveFile()" value="存至服务器"></input>
	    	--%></div>
            <form id="MyFile" method="post" class="h800">
				<input type="hidden" id="ope" value="${ope }">
    			<input type="hidden" id="contractId" value="${id}">
    			<input type="hidden" id="contractName" value="">
				<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
			</form>
          </div> 
		</div> 
		</form>
		<div  class="col-md-12 tc mt20">
   			<input type="button" class="btn btn-windows back mb20" onclick="abandoned()" value="返回">
  		</div>
     </div>
  </div>
</div>
</div>
<!-- 页签结束 -->

</body>
</html>
