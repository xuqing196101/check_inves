<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>标书管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
	function OpenFile(fileId) {
		var obj = document.getElementById("TANGER_OCX");
		obj.Menubar = true;
		obj.Caption = "( 双击可放大 ! )";
		if(fileId != 0){
			obj.BeginOpenFromURL("${pageContext.request.contextPath}/supplierProject/loadFile.html?fileId="+fileId, true, false, 'word.document');// 异步加载, 服务器文件路径
		}
	}
	
	function queryVersion(){
		var obj = document.getElementById("TANGER_OCX");
		var v = obj.GetProductVerString();
		obj.ShowTipMessage("当前ntko版本",v);
	}
	
	function inputTemplete(){
		var obj = document.getElementById("TANGER_OCX");
	}
	
	function saveFile(){
		if(confirm("保存后将不能修改！")){
			var projectId = $("#projectId").val();
			var supplierName = $("#supplierName").val();
			var obj = document.getElementById("TANGER_OCX");
			//参数说明
			//1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
			var s = obj.SaveToURL("${pageContext.request.contextPath}/supplierProject/saveBidFile.html?projectId="+projectId, "ntko", "", supplierName+"_投标文件.doc", "MyFile");
			obj.ShowTipMessage("提示","投标文件已上传至服务器");
			window.location.href = "${pageContext.request.contextPath}/supplierProject/bidIndex.html?projectId="+projectId;
		}
	}
	
	function closeFile(){
		var obj = document.getElementById("TANGER_OCX");
		obj.close();
	}
	
	function bd(projectId){
		var obj = document.getElementById("TANGER_OCX");
		$.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/supplierProject/isExistFile.html?projectId="+projectId,  
               dataType: 'json',  
               success:function(result){
               		if(result.isExist == 0){
               			obj.ShowTipMessage("提示","请先保存标书到服务器");
               			return;
               		}
               		if(result.isExist == 1){
               			window.location.href = "${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId="+projectId;
               		}
                },
                error: function(result){
                    layer.msg("操作失败",{offset: '222px'});
                }
            });
	}
	
	function tishi(v){
		var obj = document.getElementById("TANGER_OCX");
		obj.ShowTipMessage("提示",v,true);
	}
	
</script>

<!-- 打开文档后只读 -->
<script type="text/javascript" for="TANGER_OCX" event="OnDocumentOpened(a,b)">
		var obj = document.getElementById("TANGER_OCX");
		var stat = $("#status").val();
		if(stat == 1){
			obj.SetReadOnly(true);
		}
</script>
</head>

<body onload="OpenFile('${fileId}')">
	<div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">我的项目</a></li><li><a href="javascript:void(0);">标书管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
    </div>
	<div class="container clear mt20">
   		<div class="list-unstyled padding-10 breadcrumbs-v3">
   			<span>
				  <a href="${pageContext.request.contextPath}/mulQuo/openBid.html?projectId=${project.id}" class="img-v1">开标一览表</a>
				  <span class="green_link">→</span>
			</span>
			<span>
				  <a href="${pageContext.request.contextPath}/mulQuo/priceBuild.html?projectId=${project.id}" class="img-v1">价格构成表</a>
				  <span class="green_link">→</span>
			</span>
			<span>
				  <a href="${pageContext.request.contextPath}/mulQuo/priceView.html?projectId=${project.id}" class="img-v1">明细表</a><!--货物材料、部件、工具价格明细表  -->
				  <span class="green_link">→</span>
			</span>
		    <span>
		    	<c:if test="${std.bidFinish == 0}">
				  <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v2 orange_link">编制标书</a>
				  <span class="green_link">→</span>
		    	</c:if>
		    	<c:if test="${std.bidFinish != 0}">
				  <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v1">编制标书</a>
				  <span class="">→</span>
		    	</c:if>
			</span>
			<span>
				<c:if test="${std.bidFinish == 1}">
				  <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v2 orange_link">绑定指标</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 2 || std.bidFinish == 3 || std.bidFinish == 4}">
				  <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v1">绑定指标</a>
				  <span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 0}">
				  <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">绑定指标</a>
				  <span class="">→</span>
				</c:if>
			</span>
			<span>
				<c:if test="${std.bidFinish == 2 }">
				  <a href="${pageContext.request.contextPath}/mulQuo/list.html?projectId=${project.id}"  class="img-v2 orange_link">填写报价</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 0}">
				  <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">填写报价</a>
				  <span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 1}">
				  <a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">填写报价</a>
				  <span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 3 || std.bidFinish == 4}">
				  <a href="${pageContext.request.contextPath}/mulQuo/list.html?projectId=${project.id}" class="img-v1">填写报价</a>
				  <span class="">→</span>
				</c:if>
			</span>
		    <span>
		    	<c:if test="${std.bidFinish == 3 }">
			  		<a href="${pageContext.request.contextPath}/supplierProject/result.html?projectId=${project.id}" class="img-v2  orange_link">完成</a>
		    	</c:if>
		    	<c:if test="${std.bidFinish == 0}">
				  <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 1}">
				  <a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 2}">
				  <a href="javascript:void(0);" onclick="tishi('请先填写报价');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 4}">
				  <a href="${pageContext.request.contextPath}/supplierProject/result.html?projectId=${project.id}"  class="img-v1">完成</a>
				</c:if>
			</span>
   		</div>
  	</div>
  	<div class="container content height-350 pt0 mt20">
	<form id="MyFile" method="post"  enctype="multipart/form-data">
		<c:if test="${std.bidFinish == 0 }">
			<!-- 按钮 -->
	        <div class="mt5 mb5">
	        	 <!-- <input type="button" class="btn btn-windows cancel" onclick="delMark()" value="删除标记"></input>
	        	 <input type="button" class="btn btn-windows cancel" onclick="searchMark()" value="查看标记"></input>
	        	 <input type="button" class="btn btn-windows cancel" onclick="mark()" value="标记"></input> -->
	        	 <!-- <input type="button" class="btn btn-windows cancel" onclick="closeFile()" value="关闭当前文档"></input> -->
	        	 <!-- <input type="button" class="btn btn-windows " onclick="queryVersion()" value="版本查询"></input> -->
			     <!-- <input type="button" class="btn btn-windows input" onclick="inputTemplete()" value="模板导入"></input> -->
		         <input type="button" class="btn btn-windows save" onclick="saveFile()" value="保存到服务器"></input>
		    </div>
		</c:if>
		<input type="hidden" id="status" value="${std.bidFinish }">
	    <input type="hidden" id="fileId" value="${fileId }">
	    <input type="hidden" id="projectId" value="${project.id }">
	    <input type="hidden" id="supplierName" value="${supplier.supplierName }">
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
	</form>
	</div>
</body>
</html>
