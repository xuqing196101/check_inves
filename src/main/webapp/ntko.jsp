<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>编制招标文件和公告</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<script type="text/javascript">
	function OpenFile() {
		var obj = document.getElementById("TANGER_OCX");
		obj.Menubar = true;
		obj.Caption = "( 双击可放大 ! )";
		obj.BeginOpenFromURL("${pageContext.request.contextPath}/stash/bidFileTemp.doc");// 异步加载, 服务器文件路径
		// obj.OpenFromURL("http://localhost:8080/${pageContext.request.contextPath}/stash/1.doc");
	}
	
	
	function saveFile() {
		var obj = document.getElementById("TANGER_OCX");
		// 参数说明
		// 1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
		//obj.SaveToURL("${pageContext.request.contextPath}/xxx/xxx.do", "ntkoFile", "", "bid.doc", "MyFile");
	}
	
	
	function searchMark(){
		var obj = document.getElementById("TANGER_OCX");
		//获取当前页码
		var page = obj.ActiveDocument.Application.Selection.information(1);
		
	//alert(obj.GetBookmarkValue("测试书签"));
		//alert(obj.ActiveDocument.GetCurPageStart());
		//定位到书签内容
		//obj.ActiveDocument.Bookmarks.Item("测试书签").Select();
		
	}
</script>
</head>

<body onload="OpenFile()">
	 <input type="button" class="btn btn-windows cancel" onclick="searchMark()" value="查看标记"></input>
     <input type="button" class="btn btn-windows cancel" onclick="mark()" value="标记"></input>
	<form id="MyFile" method="post"  enctype="multipart/form-data">
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
	</form>
</body>
</html>
