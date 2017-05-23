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
				<li><a href="javascript:void(0);"> 首页</a></li>
				<li><a href="javascript:void(0);">业务监管系统</a></li>
				<li><a href="javascript:void(0);">采购业务监督</a></li>
				<li><a href="javascript:void(0);">采购合同监督</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	
	<div class="container">
		<div class="headline-v2">
			<h2>合同监督</h2>
		</div>
		
		<div class="container container_box">
				<div id="contract1">
				<h2 class="count_flow fl">
					<i>01</i>合同基础信息
				</h2>
				<div class="col-md-12 col-sm-12 col-xs-12" >
				   <ul class="ul_list">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="15%" class="bggrey ">合同名称：</td>
								<td width="35%">${contract.name}</td>
								<td width="15%" class="bggrey ">负责人：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">创建人：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">创建时间：</td>
								<td width="35%"><fmt:formatDate value="${contract.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">签订时间：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">签订地点：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">状态：</td>
								<td width="35%" colspan="3"></td>
								
							</tr>
						</tbody>
					</table>
				  </ul>
				</div>
				<h2 class="count_flow fl">
					<i>02</i>甲方乙方
				</h2>
				<div class="col-md-12 col-sm-12 col-xs-12" >
				   <ul class="ul_list">
				    <a>委托方(需求单位)</a>
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="15%" class="bggrey ">委托方：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey ">法定代表人：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">联系电话：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">委托代理人：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">邮编：</td>
								<td width="35%"></td>
								<td width="15%" class="bggrey  ">传真：</td>
								<td width="35%"></td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">通讯地址：</td>
								<td width="35%" colspan="3"></td>
								
							</tr>
						</tbody>
					</table>
				  </ul>
				  <ul class="ul_list">
				    <a>采购方(采购单位)</a>
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="15%" class="bggrey ">采购方：</td>
								<td width="35%">${contract.purchaseDepName }</td>
								<td width="15%" class="bggrey ">法定代表人：</td>
								<td width="35%">${contract.purchaseLegal}</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">联系电话：</td>
								<td width="35%">${contract.purchaseContactTelephone}</td>
								<td width="15%" class="bggrey  ">委托代理人：</td>
								<td width="35%">${contract.purchaseAgent}</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">邮编：</td>
								<td width="35%">${contract.purchaseUnitpostCode}</td>
								<td width="15%" class="bggrey  ">传真：</td>
								<td width="35%">${contract.purchaseBankAccount_string}</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">通讯地址：</td>
								<td width="35%" colspan="3">${contract.purchaseContactAddress}</td>
								
							</tr>
						</tbody>
					</table>
				  </ul>
				  <ul class="ul_list">
				    <a>付款方</a>
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="15%" class="bggrey ">付款方：</td>
								<td width="35%">${contract.purchasePayDep}</td>
								<td width="15%" class="bggrey ">法定代表人：</td>
								<td width="35%">${contract.purchaseLegal}</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">联系电话：</td>
								<td width="35%">${contract.purchaseContactTelephone}</td>
								<td width="15%" class="bggrey  ">委托代理人：</td>
								<td width="35%">${contract.purchaseAgent}</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">邮编：</td>
								<td width="35%">${contract.purchaseUnitpostCode}</td>
								<td width="15%" class="bggrey  ">传真：</td>
								<td width="35%">${contract.purchaseBankAccount_string}</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">开户银行：</td>
								<td width="35%">${contract.purchaseBank}</td>
								<td width="15%" class="bggrey  ">开户账号：</td>
								<td width="35%">${contract.purchaseBankAccount}</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">通讯地址：</td>
								<td width="35%" colspan="3">${contract.purchaseContactAddress}</td>
							</tr>
						</tbody>
					</table>
				  </ul>
				  <ul class="ul_list">
				    <a>供货方</a>
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="15%" class="bggrey ">供货方：</td>
								<td width="35%">${contract.supplierDepName}</td>
								<td width="15%" class="bggrey ">法定代表人：</td>
								<td width="35%">${contract.supplierLegal}</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">联系电话：</td>
								<td width="35%">${contract.supplierContactTelephone}</td>
								<td width="15%" class="bggrey  ">委托代理人：</td>
								<td width="35%">${contract.supplierAgent}</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">邮编：</td>
								<td width="35%">${contract.supplierUnitpostCode}</td>
								<td width="15%" class="bggrey  ">传真：</td>
								<td width="35%">${contract.supplierBankAccount_string}</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">开户银行：</td>
								<td width="35%">${contract.supplierBank}</td>
								<td width="15%" class="bggrey  ">开户账号：</td>
								<td width="35%">${contract.supplierBankAccount}</td>
							</tr>
							<tr>
								<td width="15%" class="bggrey  ">通讯地址：</td>
								<td width="35%" colspan="3">${contract.supplierContactAddress}</td>
							</tr>
						</tbody>
					</table>
				  </ul>
				  
				</div>
				<h2 class="count_flow fl">
					<i>03</i>合同明细
				</h2>
				<div class="col-md-12 col-sm-12 col-xs-12">
				   <ul class="ul_list">
					   <table id="detailtable" name="proList" class="table table_input table-bordered table-condensed left_table mb0 mt10">
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
							<th class="info">合计金额(元)</th>
							<th class="info">交付时间</th>
							<th class="info">备注</th>
						</tr>
					</thead>
					<c:forEach items="${conRequList}" var="reque" varStatus="vs">
						<tr>	
						    <td class="tc h30">${vs.index+1}</td>
							<td class="tc">${reque.planNo}</td>
							<td class="tc">${reque.goodsName}</td>
							<td class="tc">${reque.brand}</td>
							<td class="tc">${reque.stand}</td>
							<td class="tc">${reque.item}</td>
							<td class="tc">${reque.purchaseCount}</td>
							<td class="tc">${reque.price}</td>
							<td class="tc">${reque.amount}</td>
							<td class="tc">${reque.deliverDate}</td>
							<td class="tc">${reque.memo}</td>
						</tr>
			   		</c:forEach>
				</table>
				  </ul>
				</div>
				</div>
			</div>
		</div>
    <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>

</body>
</html>