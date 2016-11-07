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
		if(typeof(obj.ActiveDocument) == "undefined"){
			obj.ShowTipMessage("提示","文档加载失败或者未加载文档",true);
			return;
		}
		//获取当前页码
		var page = obj.ActiveDocument.Application.Selection.information(1);
		if(confirm("确定【"+target+"】指标的绑定内容从第"+page+"页开始吗？")){
			obj.ActiveDocument.BookMarks.Add(target);
			var html = "<div class='shanchu light_icon'><a href='javascript:void(0)' onclick='delMark(this,"+'"'+target+'"'+");'>删除</a></div>";
			html+= "<div class='dinwei light_icon'><a href='javascript:void(0)' onclick='searchMark("+'"'+target+'"'+");'>定位</a></div>";
			$(e).parent().after(html);
			$(e).parent().remove();
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
			var html = "<div class='bdzb light_icon'><a href='javascript:void(0)' onclick='mark(this,"+'"'+target+'"'+");'>定位</a></div>";
			$(e).parent().next().remove();
			$(e).parent().after(html);
			$(e).parent().remove();
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
			  <a href="<%=basePath%>supplierProject/bidDocument.html?projectId=${projectId}" class="img-v1 green_link">编制标书</a>
			  <span class="green_link">→</span>
			</span>
			<span>
			  <a href="<%=basePath%>supplierProject/toBindingIndex.html?projectId=1" class="img-v2 orange_link">绑定指标</a>
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
          <div class="col-md-3 md-margin-bottom-40">
           	<div class="tag-box tag-box-v3">	
			   <div class="light_main">
			    <div class="light_list">
				 初审项      
				</div>
			    <ul class="light_box"> 
				  <li>
				    <span class="light_desc">法人代表</span>
				    <div class='bdzb light_icon'>
				    	<a href='javascript:void(0)' onclick="mark(this,'法人代表');">定位</a>
				    </div>
				  </li>
				  <li>
				    <span class="light_desc">营业执照...</span>
				    <div class='bdzb light_icon'>
				    	<a href='javascript:void(0)' onclick="mark(this,'营业执照编号');">定位</a>
				    </div>
				  </li>
				  <li>
				    <span class="light_desc">年利润</span>
				    <div class='bdzb light_icon'>
				    	<a href='javascript:void(0)' onclick="mark(this,'年利润');">定位</a>
				    </div>
				  </li>
				</ul>
		       </div>
			   <div class="light_main">
			    <div class="light_list">
				 评分细则
				</div>
			    <ul class="light_box"> 
				  <li>
				    <span class="light_desc">企业规模</span>
					<div class='bdzb light_icon'>
				    	<a href='javascript:void(0)' onclick="mark(this,'企业规模');">定位</a>
				    </div>
				  </li>
				  <li>
				    <span class="light_desc">技术人数</span>
					<div class='bdzb light_icon'>
				    	<a href='javascript:void(0)' onclick="mark(this,'技术人数');">定位</a>
				    </div>
				  </li>
				</ul>
		       </div>
        	</div>   
		  </div>
		  <div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
			<form id="MyFile" method="post"  enctype="multipart/form-data">
				 <!-- 按钮 -->
		        <div class="fr pr15 mt10">
		        	 <!-- <input type="button" class="btn btn-windows cancel" onclick="delMark()" value="删除标记"></input>
		        	 <input type="button" class="btn btn-windows cancel" onclick="searchMark()" value="查看标记"></input>
		        	 <input type="button" class="btn btn-windows cancel" onclick="mark()" value="标记"></input> -->
		        	 <!-- <input type="button" class="btn btn-windows cancel" onclick="closeFile()" value="关闭当前文档"></input> -->
		        	 <!-- <input type="button" class="btn btn-windows " onclick="queryVersion()" value="版本查询"></input> -->
				     <input type="button" class="btn btn-windows input" onclick="inputTemplete()" value="模板导入"></input>
			         <input type="button" class="btn btn-windows save" onclick="saveFile()" value="保存绑定操作"></input>
			    </div>
				<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
			</form>
		  </div>
	   </div>
	</div>
</body>
</html>
