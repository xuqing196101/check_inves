<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">
var obj;
	$(function(){
	  obj= document.getElementById("TANGER_OCX");
		 var fileId="${fileId}";
		 if(fileId != '0'){
		 	 obj.BeginOpenFromURL("${pageContext.request.contextPath}/open_bidding/loadFile.html?fileId="+fileId, false, false, 'word.document');// 异步加载, 服务器文件路径
		 }
	});

	function loadWord(begin,end,url){
    obj.ActiveDocument.Range(obj.ActiveDocument.Bookmarks.Item(begin).Range.End,
    		obj.ActiveDocument.Bookmarks.Item(end).Range.Start).Select();
		/* obj.ActiveDocument.Application.Selection.Editors.Add(-1);  *///增加可编辑区域
		obj.AddTemplateFromURL(url, false, true);
    }
	function OnComplete(type,code,html){
    var filePath ="${filePath}";
		var pathArray = filePath.split(",");
		loadWord("DW_TWO_TWO", "DW_THREE_3", "${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath="+ pathArray[0]);
		/* loadWord("DW_THREE_2", "DW_THREE_3", "${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath="+ pathArray[0]); */
		window.setTimeout(show,5000);
		
	}
	function show()
	{
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	      parent.layer.close(index); 
		obj.SaveToLocal("E:\\采购文件.doc",false,true);
	} 
</script>
<!-- 打开文档后调用  -->

</head>

<body>
<div style="width: 0px;height: 0px;">
<form id="MyFile" method="post" >
</form>
<script type="text/javascript"  src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
 <div>
</body>
</html>
