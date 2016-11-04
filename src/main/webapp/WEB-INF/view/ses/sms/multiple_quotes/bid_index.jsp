<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>标书管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
    <script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
	function OpenFile() {
		var obj = document.getElementById("TANGER_OCX");
		obj.Menubar = true;
		obj.Caption = "( 双击可放大 ! )";
	   // obj.BeginOpenFromURL("<%=basePath%>stash/bidFileTemp.doc");// 异步加载, 服务器文件路径
		
		//obj.OpenFromURL("http://localhost/${pageContext.request.contextPath}/stash/bidFileTemp.doc");
		
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
		var obj = document.getElementById("TANGER_OCX");
	}
	
	function closeFile(){
		var obj = document.getElementById("TANGER_OCX");
		obj.close();
	}
	 
	//标记
	function mark(e,target){
		var obj = document.getElementById("TANGER_OCX");
		//obj.ShowTipMessage("提示","绑定指标时请把光标停在选中内容的起始处");
		//获取当前页码
		var page = obj.ActiveDocument.Application.Selection.information(1);
		if(confirm("确定【"+target+"】指标的绑定内容从第"+page+"页开始吗？")){
			obj.ActiveDocument.BookMarks.Add(target);
			var html = "<img alt='定位' onclick='searchMark("+'"'+target+'"'+");' src='<%=basePath%>public/ZHH/images/dw.png'>";
			html+= "<img alt='删除' onclick='delMark(this,"+'"'+target+'"'+");' src='<%=basePath%>public/ZHH/images/sc.png'>";
			$(e).after(html);
			$(e).remove();
			obj.ShowTipMessage("提示","【"+target+"】"+"指标内容绑定成功，请在 刷新 或者 关闭 页面前保存文件");
		}
	}	
	
	//获取标记内容并且定位
	function searchMark(target){
		var obj = document.getElementById("TANGER_OCX");
		//判断标记是否存在
		if(obj.ActiveDocument.Bookmarks.Exists(target)){
			//定位到书签内容
			obj.ActiveDocument.Bookmarks.Item(target).Select();
		}else{
			obj.ShowTipMessage("提示","获取失败，请重新绑定指标！",true);
		}
	}
	
	//删除标记
	function delMark(e,target){
		var obj = document.getElementById("TANGER_OCX");
		//判断标记是否存在
		if(obj.ActiveDocument.Bookmarks.Exists(target)){
			obj.ActiveDocument.BookMarks.Item(target).Delete();
			obj.ShowTipMessage("提示","删除绑定成功！",false);
			var html = "<img alt='绑定指标' onclick='mark(this,"+'"'+target+'"'+");' src='<%=basePath%>public/ZHH/images/bdzb.png'>";
			$(e).after(html);
			$(e).prev().remove();
			$(e).remove();
		}else{
			obj.ShowTipMessage("提示","删除失败，该指标未被绑定！",true);
		}
	}
</script>

<!-- 打开文档后只读 -->
<!-- <script type="text/javascript" for="TANGER_OCX" event="OnDocumentOpened(a,b)">
		var obj = document.getElementById("TANGER_OCX");
		obj.SetReadOnly(true);
</script> -->
</head>

<body onload="OpenFile()">
	<div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">我的项目</a></li><li><a href="#">标书管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
    </div>
    <div class="container clear mt20">
   		<div class="list-unstyled padding-10 breadcrumbs-v3">
		    <span>
			  <a href="<%=basePath%>mulQuo/bidDocument.html?projectId=${projectId}" class="img-v1 green_link">编制标书</a>
			  <span class="green_link">→</span>
			</span>
			<span>
			  <a href="#" class="img-v2 orange_link">绑定指标</a>
			  <span class="">→</span>
			</span>
			<span>
			  <a href="<%=basePath%>mulQuo/list.html?projectId=${project.id}" class="img-v3">填写报价</a>
			  <span class="">→</span>
			</span>
		    <span>
			  <a href="#" class="img-v5">完成</a>
			</span>
   		</div>
  	</div>
    
    <div class="container content height-350">
       <div class="row">
           <!-- Begin Content -->
           <div class="col-md-12" style="min-height:400px;">
                <div class="col-md-3 md-margin-bottom-40" id="show_tree_div">
					<div class="tag-box tag-box-v3">
						<ul >
						  <li >
							<span >资格等级</span>
							<img alt="绑定指标" onclick="mark(this,'资格等级');" src="<%=basePath%>public/ZHH/images/bdzb.png">
							
						  </li>
						</ul>
					</div>
				</div>
				<div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
					<form id="MyFile" method="post"  enctype="multipart/form-data">
						 <!-- 按钮 -->
				        <div class="fr pr15 mt10">
				        	 <!-- <input type="button" class="btn btn-windows cancel" onclick="delMark()" value="删除标记"></input>
				        	 <input type="button" class="btn btn-windows cancel" onclick="searchMark()" value="查看标记"></input>
				        	 <input type="button" class="btn btn-windows cancel" onclick="mark()" value="标记"></input> -->
				        	 <input type="button" class="btn btn-windows cancel" onclick="closeFile()" value="关闭当前文档"></input>
				        	 <!-- <input type="button" class="btn btn-windows " onclick="queryVersion()" value="版本查询"></input> -->
						     <input type="button" class="btn btn-windows input" onclick="inputTemplete()" value="模板导入"></input>
					         <input type="button" class="btn btn-windows save" onclick="saveFile()" value="保存到服务器"></input>
					    </div>
						<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
					</form>
				</div>
			</div>
		</div>
  </div>
</body>
</html>
