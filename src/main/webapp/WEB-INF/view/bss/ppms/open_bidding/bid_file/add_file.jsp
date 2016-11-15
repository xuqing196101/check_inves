<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>编制招标文件</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript">
	function OpenFile(fileId) {
		var obj = document.getElementById("TANGER_OCX");
		obj.Menubar = true;
		obj.Caption = "( 双击可放大 ! )";
		if(fileId != 0){
			obj.BeginOpenFromURL("${pageContext.request.contextPath}/open_bidding/loadFile.html?fileId="+fileId, true);// 异步加载, 服务器文件路径
		} else {
	    	obj.BeginOpenFromURL("${pageContext.request.contextPath}/stash/bidFileTemp.docx", true);// 异步加载, 服务器文件路径
		}
		
		//obj.OpenFromURL("http://localhost/${pageContext.request.contextPath}/stash/bidFileTemp.doc");
		
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
	
	function inputTemplete(){
		var obj = document.getElementById("TANGER_OCX");
	}
	
	function saveFile(){
		var projectId = $("#projectId").val();
		var flowDefineId = $("#flowDefineId").val();
		var obj = document.getElementById("TANGER_OCX");
		//参数说明
		//1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
		obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html?projectId="+projectId+"&flowDefineId="+flowDefineId, "ntko", "", "bid.doc", "MyFile");
	}
	
	function closeFile(){
		var obj = document.getElementById("TANGER_OCX");
		obj.close();
	}
	
	//标记
	function mark(){
		var obj = document.getElementById("TANGER_OCX");
		obj.ActiveDocument.BookMarks.Add("标记");
	}	
	
	//获取标记内容并且定位
	function searchMark(){
		var obj = document.getElementById("TANGER_OCX");
		//判断标记是否存在
		if(obj.ActiveDocument.Bookmarks.Exists("标记")){}
		alert(obj.GetBookmarkValue("标记"));
		//alert(obj.ActiveDocument.GetCurPageStart());
		//定位到书签内容
		obj.ActiveDocument.Bookmarks.Item("标记").Select();
		//alert(obj.ActiveDocument.GetPagesCount());
	}
	
	//删除标记
	function delMark(){
		var obj = document.getElementById("TANGER_OCX");
		obj.ActiveDocument.BookMarks.Item("标记").Delete();
	}
</script>

<!-- 打开文档后只读 -->
<!-- <script type="text/javascript" for="TANGER_OCX" event="OnDocumentOpened(a,b)">
		var obj = document.getElementById("TANGER_OCX");
		obj.SetReadOnly(true);
</script> -->
</head>

<body onload="OpenFile('${fileId}')">
	 <div class="col-md-12 p0">
	   <ul class="flow_step">
	     <li >
		   <a  href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${project.id}&flowDefineId=${flowDefineId}" >01、符合性</a>
		   <i></i>
		 </li>
		 
		 <li >
		   <a  href="${pageContext.request.contextPath}/firstAudit/toPackageFirstAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}" >02、符合性关联</a>
		   <i></i>							  
		 </li>
	     <li>
		   <a  href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${project.id}&flowDefineId=${flowDefineId}">03、评标细则</a>
		   <i></i>
		 </li>
		 <li class="active">
		   <a  href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${project.id}&flowDefineId=${flowDefineId}" >04、招标文件</a>
		 </li>
	   </ul>
	 </div>
	 <!-- 按钮 -->
     <div class="fr pr15 mt10">
      	 <!-- <input type="button" class="btn btn-windows cancel" onclick="delMark()" value="删除标记"></input>
      	 <input type="button" class="btn btn-windows cancel" onclick="searchMark()" value="查看标记"></input>
      	 <input type="button" class="btn btn-windows cancel" onclick="mark()" value="标记"></input>
      	 <input type="button" class="btn btn-windows cancel" onclick="closeFile()" value="关闭当前文档"></input> -->
      	 <!-- <input type="button" class="btn btn-windows " onclick="queryVersion()" value="版本查询"></input> -->
     	<input type="button" class="btn btn-windows input" onclick="inputTemplete()" value="模板导入"></input>
        <input type="button" class="btn btn-windows save" onclick="saveFile()" value="保存到服务器"></input>
    </div>
	<form id="MyFile" method="post">
		<input type="hidden" id="flowDefineId" value="${flowDefineId }">
    	<input type="hidden" id="projectId" value="${project.id }">
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
	</form>
</body>
</html>
