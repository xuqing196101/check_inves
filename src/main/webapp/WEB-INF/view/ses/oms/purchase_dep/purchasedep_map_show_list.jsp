<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
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
<link href="<%=basePath%>public/oms/css/consume.css"  rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<style type="text/css">
.panel-title>a
{
	color: #333
}
	
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
<script src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<script type="text/javascript">
	function save(){
		var index = parent.layer.getFrameIndex(window.name); 
		var pid = parent.$("#parentid").val();
		console.dir(pid);
		$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchaseManage/saveOrg.do?",
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
		<form action="<%=basePath%>purchaseManage/createPurchaseDep.do" method="post" id="formID">
			<input type="hidden" value="2" name="typeName"/>
			<div>
				<div class="headline-v2">
					<h2>新增采购机构</h2>
				</div>
				<!-- 伸缩层 -->
				<div class="panel-group" id="accordion">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseOne">
									基本信息
								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse in">
							<div class="panel-body">
								<ul class="list-unstyled list-flow p0_20">
									<li class="col-md-6 p0"><span class="">采购机构名称：</span>
										<div class="input-append">
											<input class="span2" name="name" type="text" value="${purchaseDep.name }">
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">采购业务范围：</span>
										<div class="input-append">
											<input class="span2" name="businessRange" 
												type="text" value="${purchaseDep.businessRange }"> 
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">单位主要领导及电话：</span>
										<div class="input-append">
											<input class="span2" name="leaderTelephone" type="text" value="${purchaseDep.leaderTelephone }"> 
										</div>
									</li>
									
									
									<li class="col-md-6  p0 "><span class="">单位地址：</span>
										<div class="input-append">
											<input class="span2" name="address" type="text" value="${purchaseDep.address }"> 
												
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<!--  class="panel panel-default" -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseTwo">
									上级部门
								</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse">
							<div class="panel-body">
								<ul class="list-unstyled list-flow p0_20">
									<li class="col-md-6  p0 "><span class="">上级监管部门：</span>
										<div class="input-append">
											<input class="span2" name="" type="text" value="军区采购"> 
										</div>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseThree">
									采购人员
								</a>
							</h4>
						</div>
						<div id="collapseThree" class="panel-collapse collapse">
							<table id="tb1"
								class="table table-striped table-bordered table-hover tc">
								<thead>
									<tr>
										<th class="info w50">序号</th>
										<th class="info">所属采购机构</th>
										<th class="info">类型</th>
										<th class="info">性别</th>
										<th class="info">年龄</th>
										<th class="info">职务</th>
										<th class="info">等级</th>
										<th class="info">学历</th>
										<th class="info">电话</th>
										<th class="info">资质证书类型</th>
										<th class="info">证书编号</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list.list }" var="list" varStatus="vs">
										<tr>
											<td>${vs.index+1 }</td>
											<td><a
												href="<%=basePath%>purchaseManage/purchaseDepdetailList.html?orgId=${list.orgId}">${list.name
													}</a></td>
											<td>${list.quaCode }</td>
											<td>${list.levelDep }</td>
											<%-- <td><fmt:formatDate value="${list.createdAt }" pattern="yyyy-MM-dd HH:mm:ss" /></td> --%>
											<td>${list.address }</td>
											<td><fmt:formatDate value="${list.quaStartDate }"
													pattern="yyyy-MM-dd" /></td>
											<td><fmt:formatDate value="${list.quaEdndate }"
													pattern="yyyy-MM-dd" /></td>
											<td>军区采购</td>
											<td>军区采购</td>
											<td>军区采购</td>
											<td>军区采购</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- 伸缩层 -->
			</div>
			
			<div class="col-md-12">
				
			</div>
		</form>
	</div>
</body>
</html>
