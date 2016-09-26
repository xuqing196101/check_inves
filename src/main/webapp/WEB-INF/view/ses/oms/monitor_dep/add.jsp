<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
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
	
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/oms/css/consume.css">
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
    <!--导航js-->
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery_ujs.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/oms/js/select-tree.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript">
	
	var setting = {
		view : {
			dblClickExpand : false
		},
		async : {
			autoParam : [ "id" ],
			enable : true,
			url : "${pageContext.request.contextPath}/purchaseManage/gettree.do",
			dataType : "json",
			type : "post",
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pId : "pId",
				rootPId : -1,
			}
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClick
		}
	};
	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, datas);
	});
	function save() {
		var index = parent.layer.getFrameIndex(window.name);
		var pid = parent.$("#parentid").val();
		console.dir(pid);
		$
				.ajax({
					type : 'post',
					url : "${pageContext.request.contextPath}/purchaseManage/saveOrg.do?",
					data : $.param({
						'parentId' : pid
					}) + '&' + $('#formID').serialize(),
					//data: {'pid':pid,$("#formID").serialize()},
					success : function(data) {
						truealert(data.message, data.success == false ? 5 : 1);
					}
				});

	}
	function truealert(text, iconindex) {
		layer.open({
			content : text,
			icon : iconindex,
			shade : [ 0.3, '#000' ],
			yes : function(index) {
				//do something
				parent.location.reload();
				layer.closeAll();
				parent.layer.close(index); //执行关闭
				//parent.location.href="${pageContext.request.contextPath}/purchaseManage/list.do";
			}
		});
	}
</script>
</head>
<body>

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a>
				</li>
				<li><a href="#">支撑系统</a>
				</li>
				<li><a href="#">后台管理</a>
				</li>
				<li class="active"><a href="#">需求部门管理</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 修改订列表开始-->
	<div class="container">
		<form action="${pageContext.request.contextPath}/purchaseManage/create.do" method="post" id="formID">
			<div>
				<div class="headline-v2">
					<h2>新增需求部门</h2>
				</div>
				<input type="hidden" name="typeName" value="2"/>
				<ul class="list-unstyled list-flow p0_20">
					<li class="col-md-6 p0"><span class="">名称：</span>
						<div class="input-append">
							<input class="span2" name="name" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 "><span class="">地址：</span>
						<div class="input-append">
							<input class="span2" name="address" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 "><span class="">手机号：</span>
						<div class="input-append">
							<input class="span2" name="mobile" 
								type="text"> <span class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 "><span class="">邮编：</span>
						<div class="input-append">
							<input class="span2" name="postCode" type="text"> <span
								class="add-on">i</span>
						</div></li>
					</li>
					<li class="col-md-6  p0 "><span class="">上级：</span>
						<div class="input-append">
							<input id="proSec" type="text" readonly value="${orgnization.parentName }" name="parentName" style="width:120px;" onclick="showMenu(); return false;"/>
							<input type="hidden"  id="treeId" name="parentId" value="${orgnization.parentId }"  class="text"/>
						</div></li>
					</li>
				</ul>
			</div>
			<div class="col-md-12">
				<div class="mt40 tc mb50">
					<button type="submit" class="btn  padding-right-20 btn_back margin-5">保存</button>
				</div>
			</div>
		</form>
		<!-- tree -->
		<div id="menuContent" class="menuContent divpopups menutree">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
</body>
</html>
