<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>
	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>

<script type="text/javascript">
	$(function(){
		/*这是要初始化成树的后台数据*/
		var datas;
		/*页面初始化时加载数据转换成json数据*/
		
		
		/*树的设置*/
		var setting={
			async:{
				autoParam:["id"],
				enable:true,
				url:"<%=basePath%>purchaseManage/gettree.html",
				dataType:"json",
				type:"post",
			},
			data:{
				simpleData:{
					enable:true,
					idKey:"id",
					pId:"pId",
					rootPId:-1,
				}
			},
			callback:{
				onClick:zTreeOnClick
			}
		};
		var treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
		treeObj.expandAll(false);
		var id="${id}";
		if(id!=''&&id!=null){
			getList(id);
		}else{
			id="1";
			getList(id);
		}
	});
	
	/*这个方法的treeNode.id就是对应的id。treeNode.name就是对应的name*/
	function zTreeOnClick(event,treeId,treeNode){
		getList(treeNode.id);
	};
	function getList(id){
		$.ajax({   
            type: "POST",  
            dataType: "json",
            async:false,
            url: "${pageContext.request.contextPath}/purchaseManage/getDetail.html?id="+id,      //提交到一般处理程序请求数据   
            success: function(data) {
            	//var html ="";
                //$("#Result tr:gt(0)").remove();        //移除Id为Result的表格里的行，从第二行开始（这里根据页面布局不同页变）
               //$("#Result").append(html); 
            }  
        });
	}
	
	function add(id){
		
	}
	function update(){
		
    }
</script>
</head>

<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">机构管理</a></li><li class="active"><a href="#">需求部门管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div id="ztree" class="ztree ww35 fl mt10"></div>
	<table id="Result" class="common mt10 change ww100" cellpadding="0" cellspacing="0" border="0">
		<caption>需求部门信息</caption>
		<thead>
			<tr>
				<th class="w30"><input id="checkedAll" type="checkbox"
					name="checkedAll" onclick="selectAll()">
				</th>
				<th class="w50">序号</th>
				<th>品目名称</th>
				<th class="w100">参数</th>
				<th class="w100">排序</th>
			</tr>
		</thead>
	</table>
	上架时间上架就
</body>
</html>
