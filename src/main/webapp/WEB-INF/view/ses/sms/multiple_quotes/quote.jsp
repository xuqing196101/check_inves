<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		var s = obj.GetBookmarkValue("书签");
		alert(s);
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
			  <a href="<%=basePath%>supplierProject/bidDocument.html?projectId=${project.id}" class="img-v2 orange_link">编制标书</a>
			  <span class="green_link">→</span>
			</span>
			<span>
			  <a href="<%=basePath%>supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v3">绑定指标</a>
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
  	<div class="container">
<!-- 项目戳开始 -->
   <div class="clear"></div>
   <div class="headline-v2 fl">
      <h2>报价明细
      </h2>
       </div> 
       <span class="fr option_btn margin-top-10">
       <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
        <button class="btn btn-windows save" onclick="cancel();">确定</button>
        <button class="btn btn-windows save" onclick="select();">报价记录</button>
      </span>
     
   <div class="container clear margin-top-30">
        <table id="tb" class="table table-bordered table-condensed mt5">
        <thead>
        <tr>
          <th class="info w50">序号</th>
          <th class="info">物资名称</th>
          <th class="info">规格型号</th>
          <th class="info">质量技术标准</th>
          <th class="info">计量单位</th>
          <th class="info">采购数量</th>
          <th class="info">单价（元）</th>
          <th class="info">小计</th>
        </tr>
        </thead>
          <c:forEach items="${list}" var="obj" varStatus="vs">
            <tr class="hand">
              <td class="tc w50">${obj.serialNumber}</td>
              <td class="tc">${obj.goodsName}</td>
              <td class="tc">${obj.stand}</td>
              <td class="tc">${obj.qualitStand}</td>
              <td class="tc">${obj.item}</td>
              <td class="tc"><input name="quantity${vs.index }" readonly="readonly" value="${obj.purchaseCount}" /></td>
              <td class="tc"><input maxlength="12" name="price${vs.index }" onkeyup="addTotal('${vs.index}')" /></td>
              <td class="tc"><input readonly="readonly" name="total${vs.index }" /></td>
            </tr>
         </c:forEach>  
         <tr>
         	<td class="tc" colspan="2"><b>总金额</b>
         	</td>
         	<td class="tl" colspan="7">
         		<form id="form1" action="<%=basePath%>mulQuo/save.html">
	         		<input readonly="readonly" id="quotePrice" name="quotePrice"  />
	         		<input type="hidden" id="projectId" name="projectId" value="${id }" />
	         		<input type="hidden" id="packageId" name="packageId" value="${packageId }" />
         		</form>
         	</td>
         </tr>
      </table>
   </div>
 </div>
</body>
</html>
