<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>菜单管理</title>
   	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle.css">
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
    <!--导航js-->
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
	<script type="text/javascript">
	    
		$(function(){
			var setting = {
				async:{
					autoParam:["id"],
					enable:true,
					url:"${pageContext.request.contextPath}/preMenu/treedata.do",
					dataType:"json",
					type:"post",
				},
				data: {
					simpleData: {
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
			var treeObj=$.fn.zTree.init($("#menuTree"),setting);
			treeObj.expandAll(true);
			getDetail("0");
		});
		
		function zTreeOnClick(event,treeId,treeNode){
			$("#checkedAll").attr("checked",false);
			getDetail(treeNode.id);
			$("#mid").val(treeNode.id);
		};
		
		function getDetail(id){
			$.ajax({   
	            type: "POST",  
	            dataType: "json",
	            async:false,
	            url: "${pageContext.request.contextPath}/preMenu/get.do?id="+id,         
	            success: function(data) {
	            	if(data != null && data != ''){
		            	var tabhtml = "";
	            		var state;
	            		var kind;
	            		var pName;
	            		if(data[0].status == 0){
	            			state = "可用";
	            		}else if(data[0].status == 1){
	            			state = "暂停";
	            		}
	            		if(data[0].kind = 0){
	            			kind = "采购管理后台";
	            		}else if(data[0].kind = 1){
	            			kind = "供应商后台";
	            		}
	            		else if(data[0].kind = 2){
	            			kind = "专家后台";
	            		}
	            		else if(data[0].kind = 3){
	            			kind = "进口供应商后台";
	            		}
	            		if(data[0].parentId == null){
	            			pName = "";
	            		}else{
	            			pName = data[0].parentId.name;
	            		}
	            		
	            		tabhtml +="<li class='col-md-6 p0 '><span >上级菜单：</span><div class='input-append'><input class='span2' id='appendedInput' readonly='readonly' value='"+pName+"' type='text'></div></li>";
	            		tabhtml +="<li class='col-md-6 p0 '><span >&nbsp;&nbsp;名称：</span><div class='input-append'><input class='span2' id='appendedInput' readonly='readonly' value='"+data[0].name+"' type='text'></div></li>";
	            		tabhtml +="<li class='col-md-6 p0 '><span >&nbsp;&nbsp;路径：</span><div class='input-append'><input class='span2' id='appendedInput' readonly='readonly' value='"+data[0].url+"' type='text'></div></li>";
	            		tabhtml +="<li class='col-md-6 p0 '><span >&nbsp;&nbsp;类型：</span><div class='input-append'><input class='span2' id='appendedInput' readonly='readonly' value='"+data[0].type+"' type='text'></div></li>";
	            		tabhtml +="<li class='col-md-6 p0 '><span >&nbsp;&nbsp;序号：</span><div class='input-append'><input class='span2' id='appendedInput' readonly='readonly' value='"+data[0].position+"' type='text'></div></li>";
	            		tabhtml +="<li class='col-md-6 p0 '><span >&nbsp;&nbsp;级别：</span><div class='input-append'><input class='span2' id='appendedInput' readonly='readonly' value='"+data[0].menulevel+"' type='text'></div></li>";
	            		tabhtml +="<li class='col-md-6 p0 '><span >&nbsp;&nbsp;状态：</span><div class='input-append'><input class='span2' id='appendedInput' readonly='readonly' value='"+state+"' type='text'></div></li>";
	            		tabhtml +="<li class='col-md-6 p0 '><span >&nbsp;&nbsp;图标：</span><div class='input-append'><input class='span2' id='appendedInput' readonly='readonly' value='"+data[0].icon+"' type='text'></div></li>";
	            		tabhtml +="<li class='col-md-6 p0 '><span >所属后台：</span><div class='input-append'><input class='span2' id='appendedInput' readonly='readonly' value='"+kind+"' type='text'></div></li>";
	            		tabhtml +="<li class='col-md-6 p0 '><span >创建时间：</span><div class='input-append'><input class='span2' id='appendedInput' readonly='readonly' value='"+data[0].createdAt+"' type='text'></div></li>";
	            		tabhtml +="<li class='col-md-6 p0 '><span >修改时间：</span><div class='input-append'><input class='span2' id='appendedInput' readonly='readonly' value='"+data[0].updatedAt+"' type='text'></div></li>";
		            	$("#con").html("");
		            	$("#con").append(tabhtml);
	            	}
	            }  
	        });
		}
	</script>
  </head>
  
  <script type="text/javascript">
	
    function edit(){
    	var mid = $("#mid").val();
		if(mid != null && mid != '' ){
			layer.open({
			  type: 2, //page层
			  area: ['430px', '400px'],
			  title: '修改菜单',
			  closeBtn: 1,
			  shade:0.01, //遮罩透明度
			  moveType: 1, //拖拽风格，0是默认，1是传统拖动
			  shift: 1, //0-6的动画形式，-1不开启
			  offset: ['120px', '550px'],
			  shadeClose: false,
			  content: '${pageContext.request.contextPath}/preMenu/edit.html?id='+mid
			});
		}else{
			layer.alert("只能选择一个节点",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function del(){
    	var mid = $("#mid").val();
		if(mid != null && mid != '' ){
			layer.confirm('您确定要删除该菜单吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/preMenu/delete.html?ids="+mid;
			});
		}else{
			layer.alert("请选择要删除的菜单",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function add(){
    	var pid = $("#mid").val();
		layer.open({
		  type: 2, //page层
		  area: ['430px', '400px'],
		  title: '添加菜单',
		  closeBtn: 1,
		  shade:0.01, //遮罩透明度
		  moveType: 1, //拖拽风格，0是默认，1是传统拖动
		  shift: 1, //0-6的动画形式，-1不开启
		  offset: ['120px', '550px'],
		  shadeClose: false,
		  content: '${pageContext.request.contextPath}/preMenu/add.html?pid='+pid
		});
    }
  </script>
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">菜单功能管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
	   <div class="headline-v2">
	   		<h2>菜单功能管理</h2>
	   </div>
   </div>
   
   
   <div class="container content height-350">
       <div class="row">
                <!-- Begin Content -->
                <div class="col-md-12" style="min-height:400px;">
					<div class="col-md-3 md-margin-bottom-40" id="show_tree_div">
						<div class="tag-box tag-box-v3">
							<ul id="ztree_show" class="ztree">
								<!-- 菜单树-->
								<div id="menuTree" class="ztree fl"></div>
							</ul>
						</div>
					</div>
					<div style="margin-bottom: 6px; ">
						<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
						<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
						<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>	
					</div>
					<div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
						<input type="hidden" name="nodeId" id="mid">
						<div class="tab-pane fade in" id="dep_tab-2">
						   <ul class="timeline-v2" id="con">
					       </ul>
      				   </div>
					</div>
				</div>
          </div>
      </div>
      <!--=== End Content Part ===-->
  </body>
</html>
