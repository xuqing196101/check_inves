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
		<div class="content table_box">
			<div class="col-md-12 tab-pane active">
				<h2 class="count_flow fl">
					<i>01</i>需求计划
				</h2>
				<div class="col-md-12 col-sm-12 col-xs-12">
				   <ul class="ul_list">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td class="bggrey w100">需求计划：</td>
								<td class="w500"></td>
								<td class="bggrey w100">填制单位：</td>
								<td class="w500"></td>
							</tr>
							<tr>
								<td class="bggrey w100 ">预算金额：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">计划状态：</td>
								<td class="w500"></td>
							</tr>
							<tr>
								<td class="bggrey w100 ">编制时间：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">创建人：</td>
								<td class="w500"></td>
							</tr>
							<tr>
								<td class="bggrey w100 ">审批时间：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">审核人：</td>
								<td class="w500"></td>
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
								<td class="bggrey w100">文件名称：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">计划编号：</td>
								<td class="w500"></td>
							</tr>
							<tr>
								<td class="bggrey w100 ">总金额：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">状态：</td>
								<td class="w500"></td>
							</tr>
							<tr>
								<td class="bggrey w100 ">编制时间：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">创建人：</td>
								<td class="w500"></td>
							</tr>
							<tr>
								<td class="bggrey w100 ">审批时间：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">审核人：</td>
								<td class="w500"></td>
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
								<td class="bggrey w100">文件名称：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">计划文号：</td>
								<td class="w500"></td>
							</tr>
							<tr>
								<td class="bggrey w100 ">总金额：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">状态：</td>
								<td class="w500"></td>
							</tr>
							<tr>
								<td class="bggrey w100 ">下达时间：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">创建人：</td>
								<td class="w500"></td>
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
								<td class="bggrey w100">项目名称：</td>
								<td class="w500">${project.name}</td>
								<td class="bggrey w100 ">创建时间：</td>
								<td class="w500"><fmt:formatDate value="${project.createAt}" pattern="yyyy-MM-dd hh:mm:ss" /> </td>
							</tr>
							<tr>
								<td class="bggrey w100 ">招标单位：</td>
								<td class="w500">${project.principal }</td>
								<td class="bggrey w100 ">负责人：</td>
								<td class="w500">${project.principal }</td>
							</tr>
							<tr>
								<td class="bggrey w100 ">联系人：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">联系电话：</td>
								<td class="w500"></td>
							</tr>
							<tr>
								<td class="bggrey w100 ">联系地址：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">当前状态：</td>
								<td class="w500"></td>
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
								<td class="bggrey w100">合同名称：</td>
								<td class="w500">${contract.name}</td>
								<td class="bggrey w100 ">合同状态：</td>
								<td class="w500">
								  <c:if test="${contract.status==1}">草案</c:if>
								  <c:if test="${contract.status==2}">正式</c:if>
								  <c:if test="${contract.status==0}">暂存</c:if>
								</td>
							</tr>
							<tr>
								<td class="bggrey w100 ">签订时间：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">合同编号：</td>
								<td class="w500">${contract.code}</td>
							</tr>
							<tr>
								<td class="bggrey w100 ">创建人：</td>
								<td class="w500"></td>
								<td class="bggrey w100 ">负责人：</td>
								<td class="w500"></td>
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