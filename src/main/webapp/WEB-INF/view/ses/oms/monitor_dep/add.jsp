<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp" %> 
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
				<li><a href="javascript:void(0);"> 首页</a>
				</li>
				<li><a href="javascript:void(0);">支撑系统</a>
				</li>
				<li><a href="javascript:void(0);">后台管理</a>
				</li>
				<li class="active"><a href="javascript:void(0);">需求部门管理</a>
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
				<ul class="list-unstyled list-flow ">
					<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="">名称：</span>
						<div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
							<input name="name" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">地址：</span>
						<div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
							<input name="address" type="text"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">手机号：</span>
						<div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
							<input name="mobile" 
								type="text"> <span class="add-on">i</span>
						</div></li>
					<li class="col-md-3 col-sm-6 col-xs-12">
					    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮编：</span>
						<div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
							<input name="postCode" type="text"> <span
								class="add-on">i</span>
						</div></li>
					</li>
					<li class="col-md-3 col-sm-6 col-xs-12 ">
					    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">上级：</span>
						<div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
							<input id="proSec" type="text" readonly value="${orgnization.parentName }" name="parentName" style="width:120px;" onclick="showMenu();"/>
							<input type="hidden"  id="treeId" name="parentId" value="${orgnization.parentId }"  class="text"/>
						</div>
					</li>
					</li>
				</ul>
			</div>
			<div class="col-md-12 col-sm-12 col-xs-12 tc mt20">
			  <button type="submit" class="btn  padding-right-20 btn_back margin-5">保存</button>
			</div>
		</form>
		<!-- tree -->
		<div id="menuContent" class="menuContent divpopups menutree">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
</body>
</html>
