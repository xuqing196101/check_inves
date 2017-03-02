<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">
	$(function(){
	 var obj = document.getElementById("TANGER_OCX");
	 obj.Statusbar=false;
	 obj.Caption="处理文档中...";
	 obj.TitleBar=false;
	 obj.Menubar=false;
	 obj.ToolBars=false;
	 obj.IsResetToolbarsOnOpen=false;
	 obj.CustomToolBar=false;
	 obj.IsShowHelpMenu=false;
	 obj.IsShowInsertMenu=false;
	 obj.IsShowEditMenu=false;
	 obj.FileNew =false; 
	 obj.FileOpen=false; 
	 obj. FileClose=false;
		 var fileId="${fileId}";
		 if(fileId != '0'){
		 	 obj.BeginOpenFromURL("${pageContext.request.contextPath}/open_bidding/loadFile.html?fileId="+fileId, false, false, 'word.document');// 异步加载, 服务器文件路径
		 }
	});
	
	function closeFile(){
		var obj = document.getElementById("TANGER_OCX");
		obj.close();
	}
	function savePackages(){
	 var obj = document.getElementById("TANGER_OCX");
	//1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
  	 var v=obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html?flag=2", "ntko", "", "temp.doc", "MyFile");
	  closeFile();
      parent.download(v);
      var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
      parent.layer.close(index); 
	}
</script>
<!-- 打开文档后调用  -->
<script type="text/javascript"  for="TANGER_OCX" event="OnDocumentOpened(a,b)">
        //声明控件
		var obj = document.getElementById("TANGER_OCX");
// 转换日期格式  如果是CST 日期  转换 GMT 日期
function getTaskTime(strDate) { 
    if(null==strDate || ""==strDate){  
        return "";  
    }
    if(strDate.indexOf("GMT")>0){
      return new Date(strDate).Format("yyyy年MMdd日hh时mm分");
    }
    var dateStr=strDate.trim().split(" ");  
    var strGMT = dateStr[0]+" "+dateStr[1]+" "+dateStr[2]+" "+dateStr[5]+" "+dateStr[3]+" GMT+0800";  
    var date = new Date(Date.parse(strGMT));  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;    
    m = m < 10 ? ('0' + m) : m;  
    var d = date.getDate();    
    d = d < 10 ? ('0' + d) : d;  
    var h = date.getHours();  
    var minute = date.getMinutes();    
    minute = minute < 10 ? ('0' + minute) : minute;  
    var second = date.getSeconds();  
    second = second < 10 ? ('0' + second) : second;  
    return y+"年"+m+"月"+d+"日"+h+"时"+minute+"分";  
};  
       //通用方法 判断是否存在 存在则行
	function replaceContent(begin,end,date) {
	   if(obj.ActiveDocument.Bookmarks.Exists(begin) && obj.ActiveDocument.Bookmarks.Exists(end)){
		obj.ActiveDocument.Range(ActiveDocument.Bookmarks(begin).Range.End,ActiveDocument.Bookmarks(end).Range.End).Select();
		obj.ActiveDocument.Application.Selection.Editors.Add(-1);//增加可编辑区域
		obj.ActiveDocument.Application.Selection.TypeText(date);
		obj.ActiveDocument.Bookmarks.Add(end);
	   }
	}
    function loadWord(begin,end,url){
     	obj.ActiveDocument.Range(ActiveDocument.Bookmarks(begin).Range.End,ActiveDocument.Bookmarks(end).Range.Start).Select();
		obj.ActiveDocument.Application.Selection.Editors.Add(-1);//增加可编辑区域
		obj.AddTemplateFromURL(url, false, true);
			
    }
	/**
	 * ntko 控件加载玩之后调用
	 * **/
	$(function() {
		// 组合word文档
		var marks = obj.ActiveDocument.Bookmarks;//获取所有的书签
		var filePath ="${filePath}";
		if (filePath != null && filePath != "") {
			var pathArray = filePath.split(",");
			if (pathArray.length > 1) {
				//项目名称
				replaceContent("SYS_1", "SYS_1_1", "${project.name}");
				//项目编号
				replaceContent("SYS_2", "SYS_2_2", "${project.projectNumber}");
				//招标人
				replaceContent("SYS_3", "SYS_3_1", "${project.sectorOfDemand}");
				//项目名称
				replaceContent("SYS_20171200", "SYS_20171201", "${project.name}");
				//项目编号
				replaceContent("SYS_20171202", "SYS_20171203", "${project.projectNumber}");
				//投标截止时间
				replaceContent("SYS_20171204", "SYS_20171205", "${project.deadline}");
				// 投标地点
				replaceContent("SYS_20171206", "SYS_20171207", "${project.bidAddress}");
				// 开标时间
				replaceContent("SYS_20171208", "SYS_20171209", "${project.bidDate}");
				//开标地点
				replaceContent("SYS_20171210", "SYS_20171211", "${project.bidAddress}");
				//招标人
				replaceContent("SYS_20171212", "SYS_20171213", "${project.sectorOfDemand}");
				//招标人
				replaceContent("SYS_20171214", "SYS_20171215", "${project.sectorOfDemand}");
				//招标人
				replaceContent("SYS_20171216", "SYS_20171217", "${project.sectorOfDemand}");

				//定位定义标签位置
				loadWord("DW_TWO_TWO", "DW_TWO_THREE", "${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath="+ pathArray[1]);
				loadWord("DW_THREE_2", "DW_THREE_3", "${pageContext.request.contextPath}/open_bidding/downloadFile.html?filePath="+ pathArray[0]);
				obj.ActiveDocument.DeleteAllEditableRanges(-1);//取消编辑
			}
		}
		for ( var i = 1; i <= marks.Count; i++) {
			// 判读 标签 可编辑
			if (marks(i).Name.indexOf("EDITOR") == 0) {
				obj.ActiveDocument.Bookmarks(marks(i).Name).Range.Select();//选取书签区域保护
				obj.ActiveDocument.Application.Selection.Editors.Add(-1);//增加可编辑区域
				//添加 内容标识显示
				obj.ActiveDocument.ActiveWindow.View.ShadeEditableRanges = true;
				obj.ActiveDocument.ActiveWindow.View.ShowBookmarks = true;
			}
		}
		if (obj.ActiveDocument.ProtectionType == -1) {
			obj.ActiveDocument.Protect(3);//实现文档保护
		}
		obj.ActiveDocument.Bookmarks("OLE_LINK_TOP").Select();
		
			
	});
    savePackages();
</script>
</head>

<body>
<div style="visibility:none; ">
<div style="width: 0px;height: 0px;">
<form id="MyFile" method="post" >
<script type="text/javascript"  src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
</form>
 </div>
 <div>
</body>
</html>
