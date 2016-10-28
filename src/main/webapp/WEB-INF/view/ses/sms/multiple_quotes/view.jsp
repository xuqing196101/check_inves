<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/public/ZHH/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"  src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript">
    $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${quoteList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${quoteList.total}",
			    startRow: "${quoteList.startRow}",
			    endRow: "${quoteList.endRow}",
			    groups: "${quoteList.pages}">=5?5:"${quoteList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			             location.href = '<%=basePath%>mulQuo/quoteHistory.do?page='+e.curr;
			        }
			    }
			});
	  });
</script>
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商管理</a></li><li><a href="#">供应商报价</a></li><li class="active"><a href="#">报价历史</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 我的订单页面开始-->
    <div class="container">
     <div class="p10_25">
    
   </div>
</div>
<!-- 表格开始-->
   <div class="container">
   <div class="col-md-12">
     <span class="fr option_btn margin-top-10">
       <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
      </span>
	</div>
    </div>
   
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-striped table-bordered table-hover tc">
		<thead>
		<tr>
		  <th class="w50 info">序号</th>
		  <th class="info">项目名称</th>
		  <th class="info">包名</th>
		  <th class="info">报价金额</th>
		  <th class="info">报价时间</th>
		</tr>
		</thead>
		<c:forEach items="${quoteList.list }" var="list" varStatus="vs">
			<tr>
			    <td>${(vs.index+1)+(quoteList.pageNum-1)*(quoteList.pageSize)}</td>
			    <td>${list.project.name }</td>
				<td>${list.packages.name }</td>
				<td>${list.quotePrice }</td>
				<td><fmt:formatDate value='${list.createdAt}' pattern="yyyy年MM月dd日 HH:mm " /></td>
			</tr>
		</c:forEach> 
        </table>
        <div id="pagediv" align="right"></div>
     </div>
   </div>
</body>
</html>
