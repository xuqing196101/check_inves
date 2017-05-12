<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<jsp:include page="/WEB-INF/view/common.jsp" />
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<title>正式合同查看</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<script type="text/javascript">
	$(document).ready(function() {
		var obj = document.getElementById("TANGER_OCX");
		$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
			// 获取已激活的标签页的名称
			var activeTab = $(e.target).text();
			// 获取前一个激活的标签页的名称
			var previousTab = $(e.relatedTarget).text();
			if (activeTab == "合同文本") {
				OpenFile(obj);
			}
		});
	});

	function OpenFile(obj) {
		var projectId = "${draftCon.id}";
		obj.Menubar = false;
		obj.Caption = "( 双击可放大 ! )";
		obj.BeginOpenFromURL("${pageContext.request.contextPath}"
				+ "/purchaseContract/loadFile.html?id=" + projectId, true,
				false, 'word.document');// 异步加载, 服务器文件路径

	}
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">保障作业</a></li>
				<li><a href="#">采购合同管理</a></li>
				<li class="active"><a href="#">正式合同查看</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 新增模板开始-->
	<div class="container content pt0">
		<div class="row magazine-page">
			<div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
				<div class="padding-top-10">
					<ul class="nav nav-tabs bgwhite">
						<li class="active"><a aria-expanded="true" href="#tab-1"
							data-toggle="tab" class="f18">正式合同详情</a></li>
						<li class=""><a aria-expanded="false" href="#tab-2"
							data-toggle="tab" class="f18">标的信息</a></li>
						<li class=""><a aria-expanded="false" href="#tab-3"
							data-toggle="tab" class="f18">合同文本</a></li>
					</ul>
					<div class="tab-content padding-top-20 over_hideen">
						<div class="tab-pane fade active in" id="tab-1">
							<h2 class="count_flow jbxx">基本信息</h2>
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="13%">合同名称：</td>
										<td colspan="3">${draftCon.name}</td>
										<%--<td class="bggrey" width="13%">需求部门：</td>
	            <td width="37%">${draftCon.demandSector}</td>
	        --%>
									</tr>
									<tr>
										<td class="bggrey" width="13%">合同批准文号：</td>
										<td width="37%">${draftCon.approvalNumber}</td>
										<td class="bggrey" width="13%">采购机构资质证号：</td>
										<td width="37%">${draftCon.quaCode}</td>
									</tr>
									<tr>
										<td class="bggrey" width="13%">项目预算科目：</td>
										<td width="37%">${draftCon.budgetSubjectItem}</td>
										<td class="bggrey" width="13%">项目编号：</td>
										<td width="37%">${draftCon.projectCode}</td>
									</tr>
									<tr>
										<td class="bggrey" width="13%">计划任务文号：</td>
										<td width="37%">${draftCon.documentNumber}</td>
										<td class="bggrey" width="13%">合同金额：</td>
										<td width="37%">${draftCon.money}</td>

									</tr>
									<tr>
										<td class="bggrey" width="13%">合同预算：</td>
										<td width="37%">${draftCon.budget}</td>
										<td class="bggrey" width="13%">正式合同提报时间：</td>
										<td width="37%"><fmt:formatDate
												value="${draftCon.formalGitAt}"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>

									</tr>
									<tr>
										<td class="bggrey" width="13%">正式合同报批时间：</td>
										<td width="37%"><fmt:formatDate
												value="${draftCon.formalReviewedAt}"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td class="bggrey" width="13%">合同批准文号：</td>
										<td width="37%">${draftCon.approvalNumber}</td>

									</tr>
									<tr>
										<td class="bggrey" width="13%">合同编号：</td>
										<td width="37%">${draftCon.code}</td>
									</tr>
								</tbody>
							</table>
							<h2 class="count_flow jbxx">甲方信息</h2>
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="13%">甲方单位：</td>
										<td width="37%">${draftCon.showDemandSector}</td>
										<td class="bggrey" width="13%">甲方法人：</td>
										<td width="37%">${draftCon.purchaseLegal}</td>
									</tr>
									<tr>
										<td class="bggrey" width="13%">甲方委托代理人：</td>
										<td width="37%">${draftCon.purchaseAgent}</td>
										<td class="bggrey" width="13%">甲方通讯地址：</td>
										<td width="37%">${draftCon.purchaseContactAddress}</td>
									</tr>
									<tr>
										<td class="bggrey" width="13%">甲方联系人：</td>
										<td width="37%">${draftCon.purchaseContact}</td>
										<td class="bggrey" width="13%">甲方联系电话：</td>
										<td width="37%">${draftCon.purchaseContactTelephone}</td>
									</tr>
									<tr>
										<td class="bggrey" width="13%">甲方付款单位：</td>
										<td width="37%">${draftCon.purchasePayDep}</td>
										<td class="bggrey" width="13%">甲方邮政编码：</td>
										<td width="37%">${draftCon.purchaseUnitpostCode}</td>
									</tr>
									<tr>
										<td class="bggrey" width="13%">甲方开户银行：</td>
										<td width="37%">${draftCon.purchaseBank}</td>
										<td class="bggrey" width="13%">甲方银行账号：</td>
										<td width="37%">${draftCon.purchaseBankAccount}</td>
									</tr>
								</tbody>
							</table>
							<h2 class="count_flow jbxx">乙方信息</h2>
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="13%">乙方单位：</td>
										<td width="37%">${draftCon.showSupplierDepName}</td>
										<td class="bggrey" width="13%">乙方法人：</td>
										<td width="37%">${draftCon.supplierLegal}</td>
									</tr>
									<tr>
										<td class="bggrey" width="13%">乙方委托代理人：</td>
										<td width="37%">${draftCon.supplierAgent}</td>
										<td class="bggrey" width="13%">乙方通讯地址：</td>
										<td width="37%">${draftCon.supplierContactAddress}</td>
									</tr>
									<tr>
										<td class="bggrey" width="13%">乙方联系人：</td>
										<td width="37%">${draftCon.supplierContact}</td>
										<td class="bggrey" width="13%">乙方联系电话：</td>
										<td width="37%">${draftCon.supplierContactTelephone}</td>
									</tr>
									<tr>
										<td class="bggrey" width="13%">乙方开户单位：</td>
										<td width="37%">${draftCon.supplierBankName}</td>
										<td class="bggrey" width="13%">乙方邮政编码：</td>
										<td width="37%">${draftCon.supplierUnitpostCode}</td>
									</tr>
									<tr>
										<td class="bggrey" width="13%">乙方开户银行：</td>
										<td width="37%">${draftCon.supplierBank}</td>
										<td class="bggrey" width="13%">乙方银行账号：</td>
										<td width="37%">${draftCon.supplierBankAccount}</td>
									</tr>
								</tbody>
							</table>
							<%--
	        <h2 class="count_flow jbxx">丙方信息</h2>
	        <table class="table table-bordered">
	        <tbody>
	        <tr>
	            <td class="bggrey" width="13%">丙方单位：</td>
	            <td width="37%">${draftCon.showPurchaseDepName}</td>
	             <td class="bggrey" width="13%">丙方邮政编码：</td>
	            <td width="37%">${draftCon.bingUnitpostCode}</td>
	        </tr>
	        <tr>
	            <td class="bggrey" width="13%">丙方联系人：</td>
	            <td width="37%">${draftCon.bingContact}</td>
	            <td class="bggrey" width="13%">丙方联系电话：</td>
	            <td width="37%">${draftCon.bingContactTelephone}</td>
	        </tr>
	        <tr>
	        	<td class="bggrey" width="13%">丙方通讯地址：</td>
	            <td width="37%">${draftCon.bingContactAddress}</td>
	        </tr>
	        </tbody>
	        </table>
	        --%>
							<div class="col-md-12 col-xs-12 col-sm-12 p0">
								<h2 class="count_flow jbxx fl">批准文件电子扫描件</h2>
								<div class="fl mt10">
									<u:show showId="post_attach_show" delete="false"
										groups="post_attach_show,draft_reviewed_show"
										businessId="${draftCon.id}" sysKey="${contractattachsysKey}"
										typeId="${contractattachId}" />
								</div>
							</div>
							<div class="col-md-12 col-xs-12 col-sm-12 p0">
								<h2 class="count_flow jbxx fl">授权书</h2>
								<div class="fl mt10">
									<u:show showId="draft_reviewed_show" delete="false"
										groups="post_attach_show,draft_reviewed_show"
										businessId="${draftCon.id}" sysKey="${bookattachsysKey}"
										typeId="${bookattachtypeId}" />
								</div>
							</div>
						</div>
						<div class="tab-pane fade" id="tab-2">
							<h2 class="count_flow jbxx">项目明细</h2>
							<table id="detailtable" name=""
								class="table table-bordered table-condensed mb0 mt10">
								<thead>
									<tr>
										<th class="info w50">序号</th>
										<th class="info">编号</th>
										<th class="info">物资名称</th>
										<th class="info">品牌商标</th>
										<th class="info">规格型号</th>
										<th class="info">计量单位</th>
										<th class="info">数量</th>
										<th class="info">单价(元)</th>
										<th class="info">合计金额(万元)</th>
										<th class="info">交付时间</th>
										<th class="info">备注</th>
									</tr>
								</thead>
								<c:forEach items="${draftCon.contractReList}" var="reque"
									varStatus="vs">
									<tr>
										<td class="tc w50">${(vs.index+1)}</td>
										<td class="tl pl20">${reque.planNo}</td>
										<td class="tl pl20">${reque.goodsName}</td>
										<td class="tl pl20">${reque.brand}</td>
										<td class="tl pl20">${reque.stand}</td>
										<td class="tl pl20">${reque.item}</td>
										<td class="tl pl20">${reque.purchaseCount}</td>
										<td class="tl pl20">${reque.price}</td>
										<td class="tl pl20">${reque.amount}</td>
										<td class="tl pl20">${reque.deliverDate}</td>
										<td class="tl pl20">${reque.memo}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<div class="tab-pane fade h800" id="tab-3">
							<script type="text/javascript"
								src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
						</div>
						<div class="col-md-12 tc mt20">
							<button class="btn btn-windows back" onclick="history.go(-1)"
								type="button">返回</button>
						</div>
</body>
</html>
