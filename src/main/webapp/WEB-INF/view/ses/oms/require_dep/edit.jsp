<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../common.jsp"%>
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
<script src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript">
	function update(){
		var index = parent.layer.getFrameIndex(window.name); 
		var pid = parent.$("#parentid").val();
		console.dir(pid);
		$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchaseManage/updateOrg.do?",
		    data : $.param({'parentId':pid}) + '&' + $('#formID').serialize(),
		    //data: {'pid':pid,$("#formID").serialize()},
		    success: function(data) {
		        truealert(data.message,data.success == false ? 5:1);
		    }
		});
		
	}
	function truealert(text,iconindex){
		layer.open({
		    content: text,
		    icon: iconindex,
		    shade: [0.3, '#000'],
		    yes: function(index){
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
		<form action="" method="post" id="formID">
			<input type="hidden" value="${orgnization.typeName }" name="typeName"/>
			<input type="hidden" value="${orgnization.id }" name="id"/>
			<div>
				<div class="headline-v2">
					<h2>修改需求部门</h2>
				</div>
				<ul class="list-unstyled list-flow p0_20">
					<li class="col-md-6 p0"><span class="">名称：</span>
						<div class="input-append">
							<input class="span2" name="name" type="text" value="${orgnization.name }"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 "><span class="">地址：</span>
						<div class="input-append">
							<input class="span2" name="addr" type="text" value="${orgnization.address }"> <span
								class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 "><span class="">手机号：</span>
						<div class="input-append">
							<input class="span2" name="mobile" value="${orgnization.mobile }"
								type="text"> <span class="add-on">i</span>
						</div></li>
					<li class="col-md-6  p0 "><span class="">邮编：</span>
						<div class="input-append">
							<input class="span2" name="postCode" type="text" value="${orgnization.postCode }"> <span
								class="add-on">i</span>
						</div></li>
					</li>

				</ul>
			</div>

			<div class="col-md-12">
				<div class="fl padding-10">
					<button onclick="update();" class="btn btn-windows save" type="button">更新</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
