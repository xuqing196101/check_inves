<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<head>
<jsp:include page="/WEB-INF/view/common.jsp" />
<title>合同详细</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript">
	
</script>
</head>
<body>
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">业务监管系统</a></li>
				<li><a href="#">采购业务监督</a></li>
				<li><a href="#">采购合同监督</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2">
			<h2>合同监督</h2>
		</div>
		<div class="container container_box">
			<div class="col-md-12 tab-pane active">
				<h2 class="count_flow fl">
					<i>01</i>需求计划
				</h2>
				<div class="col-md-12 col-sm-12 col-xs-12">
				   <ul class="ul_list">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="15%" class="bggrey ">需求计划：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey ">填制单位：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">预算金额：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">计划状态：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">编制时间：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">创建人：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">审批时间：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">审核人：</td>
								<td width="35%"></td>
							</tr>
						</tbody>
					</table>
				  </ul>
				</div>
				<h2 class="count_flow fl">
					<i>02</i>采购计划
				</h2>
				<div class="col-md-12 col-sm-12 col-xs-12">
				 <ul class="ul_list">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="15%" class="bggrey ">文件名称：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">计划编号：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">总金额：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">状态：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">编制时间：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">创建人：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">审批时间：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">审核人：</td>
								<td width="35%"></td>
							</tr>
						</tbody>
					</table>
					</ul>
				</div>
				<h2 class="count_flow fl">
					<i>03</i>采购任务
				</h2>
				<div class="col-md-12 col-sm-12 col-xs-12">
				 <ul class="ul_list">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="15%" class="bggrey ">文件名称：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">计划文号：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">总金额：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">状态：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">下达时间：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">创建人：</td>
								<td width="35%"></td>
							</tr>
						</tbody>
					</table>
					</ul>
				</div>
				<h2 class="count_flow fl">
					<i>04</i>采购计项目
				</h2>
				<div class="col-md-12 col-sm-12 col-xs-12">
				 <ul class="ul_list">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="15%" class="bggrey ">项目名称：</td>
								<td width="35%">${project.name}</td>
								<td  width="15%" class="bggrey  ">创建时间：</td>
								<td width="35%"><fmt:formatDate value="${project.createAt}" pattern="yyyy-MM-dd hh:mm:ss" /> </td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">招标单位：</td>
								<td width="35%">${project.principal }</td>
								<td  width="15%" class="bggrey  ">负责人：</td>
								<td width="35%">${project.principal }</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">联系人：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">联系电话：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">联系地址：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">当前状态：</td>
								<td width="35%"></td>
							</tr>
						</tbody>
					</table>
					</ul>
				</div>
				<h2 class="count_flow fl">
					<i>05</i>合同信息
				</h2>
				<div class="col-md-12 col-sm-12 col-xs-12">
				 <ul class="ul_list">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="15%" class="bggrey ">合同名称：</td>
								<td width="35%">${contract.name}</td>
								<td width="15%" class="bggrey  ">合同状态：</td>
								<td width="35%">
								  <c:if test="${contract.status==1}">草案</c:if>
								  <c:if test="${contract.status==2}">正式</c:if>
								  <c:if test="${contract.status==0}">暂存</c:if>
								</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">签订时间：</td>
								<td width="35%"></td>
								<td width="15%"class="bggrey  ">合同编号：</td>
								<td width="35%">${contract.code}</td>
							</tr>
							<tr>
								<td width="15%"  class="bggrey  ">创建人：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">负责人：</td>
								<td width="35%"></td>
							</tr>
						</tbody>
					</table>
					</ul>
				</div>
			</div>
		</div>
	</div>


</body>
</html>