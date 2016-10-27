<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			    pages: "${stList.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${stList.total}",
			    startRow: "${stList.startRow}",
			    endRow: "${stList.endRow}",
			    groups: "${stList.pages}">=5?5:"${stList.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			             location.href = '<%=basePath%>importRecommend/list.do?page='+e.curr;
			        }
			    }
			});
	  });
	  
  	/** 全选全不选 */
	function selectAll(){
	   var checklist = document.getElementsByName ("chkItem");
	   var checkAll = document.getElementById("checkAll");
	   if(checkAll.checked){
		   for(var i=0;i<checklist.length;i++)
		   {
		      checklist[i].checked = true;
		   } 
	   }else{
		  for(var j=0;j<checklist.length;j++)
		  {
		     checklist[j].checked = false;
		  }
	   }
	}
	
	/** 单选 */
	function check(){
		  var id=[];
		    $('input[name="chkItem"]:checked').each(function(){ 
				id.push($(this).val());
		 	 }); 
		  
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}

  	function add(){
    	var id=[]; 
    	 var packageName=$('input[name="chkItem"]:checked').parent().parent().children("td").eq(4).text();
    	    packageName=encodeURI(packageName);
            packageName=encodeURI(packageName);
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
				window.location.href="<%=basePath%>mulQuo/baojia.html?id="+id+"&packageName="+packageName;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['122px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要报价的项目",{offset: ['122px', '390px'], shade:0.01});
		}
    }

    function resetQuery(){
    	$("#name").val("");
	}
    function query(){
    	form1.submit();
    }
</script>
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商管理</a></li><li><a href="#">供应商报价</a></li><li class="active"><a href="#">多次报价</a></li>
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
    <button class="btn btn-windows add" type="submit" onclick="add()">报价</button>
	</div>
    </div>
   
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-striped table-bordered table-hover tc">
		<thead>
		<tr>
		  <th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()"></th>
		  <th class="w50 info">序号</th>
		  <th class="info">项目名称</th>
		  <th class="info">采购类型</th>
		  <th class="info">包名</th>
		  <th class="info">是否报价</th>
		<!--   <th class="info">企业地址</th>
		  <th class="info">法定代表人</th>
		  <th class="info">推荐单位</th>
		  <th class="info">类别</th>
		  <th class="info">使用次数</th>
		  <th class="info">状态</th> -->
		</tr>
		</thead>
		<c:forEach items="${stList.list }" var="list" varStatus="vs">
			<tr>
			    <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${list.project.id}" /></td>
			    <td>${(vs.index+1)+(stList.pageNum-1)*(stList.pageSize)}</td>
			    <td>${list.project.name }</td>
				<td>${list.purchaseType}</td>
				<td>${list.packages.name }</td>
				<td></td>
				<%-- <td>${list.address }</td>
				<td>${list.legalName }</td>
				<td>${list.recommendDep }</td>
				<td>
				    <c:if test="${list.type==1 }">正式进口代理商</c:if>
					<c:if test="${list.type==2 }">临时进口代理商</c:if>
				</td>
				<td>${list.useCount }</td>
				<td>
					<c:if test="${list.status==0 }"><span class="label rounded-2x label-dark">未激活</span></c:if>
					<c:if test="${list.status==1 }"><span class="label rounded-2x label-u">已激活</span></c:if>
					<c:if test="${list.status==2 }"><span class="label rounded-2x label-dark">暂停</span></c:if>
					<c:if test="${list.status==3 }"><span class="label rounded-2x label-u">启用</span></c:if>
				</td> --%>
			</tr>
		</c:forEach> 
        </table>
        <div id="pagediv" align="right"></div>
     </div>
   
   </div>
</body>
</html>
