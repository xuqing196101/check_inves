<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../../common.jsp"%>
		<script type="text/javascript">
			function tijiao(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierQuery/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierQuery/financial.html";
				}
				if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierQuery/shareholder.html";
				}
				if(str == "materialProduction") {
					action = "${pageContext.request.contextPath}/supplierQuery/materialProduction.html";
				}
				if(str == "materialSales") {
					action = "${pageContext.request.contextPath}/supplierQuery/materialSales.html";
				}
				if(str == "engineering") {
					action = "${pageContext.request.contextPath}/supplierQuery/engineering.html";
				}
				if(str == "service") {
					action = "${pageContext.request.contextPath}/supplierQuery/serviceInformation.html";
				}
				if(str == "chengxin") {
					action = "${pageContext.request.contextPath}/supplierQuery/list.html";
				}
				if(str == "item") {
					action = "${pageContext.request.contextPath}/supplierQuery/item.html";
				}
				if(str == "product") {
					action = "${pageContext.request.contextPath}/supplierQuery/product.html";
				}
				if(str == "updateHistory") {
					action = "${pageContext.request.contextPath}/supplierQuery/showUpdateHistory.html";
				}
				if (str == "zizhi") {
					action = "${pageContext.request.contextPath}/supplierQuery/aptitude.html";
				}
				if (str == "contract") {
					action = "${pageContext.request.contextPath}/supplierQuery/contract.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
		<style type="text/css">

		</style>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">支撑系统</a>
					</li>
					<li>
						<a href="#">供应商查看</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
			<!-- <div class="container">
   <div class="col-md-12">
    <button class="btn btn-windows back" onclick="fanhui()">返回</button> 
    </div>
    </div> -->
			<!--详情开始-->
			<div class="container content pt0">
				<div class="tab-v2">
					<ul class="nav nav-tabs bgwhite">
						<li class="">
							<a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18" onclick="tijiao('essential');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('financial');">财务信息</a>
						</li>
						<li class="">
							<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('shareholder');">股东信息</a>
						</li>
						<c:if test="${fn:contains(suppliers.supplierType, '生产')}">
							<li class="">
								<a aria-expanded="fale" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('materialProduction');">物资-生产型专业信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(suppliers.supplierType, '销售')}">
							<li class="">
								<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('materialSales');">物资-销售型专业信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(suppliers.supplierType, '工程')}">
							<li class="active">
								<a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('engineering');">工程-专业信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(suppliers.supplierType, '服务')}">
							<li class="">
								<a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('service');">服务-专业信息</a>
							</li>
						</c:if>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">品目信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('contract');">品目合同</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('chengxin');">诚信记录</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('updateHistory');">历史修改记录</a>
						</li>
					</ul>
					<div class="tab-content padding-top-20">
						<div class="tab-pane fade active in">
							<form id="form_id" action="" method="post">
								<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
							</form>
							<h2 class="count_flow jbxx">供应商资质证书信息</h2>
							<table class="table table-bordered table-condensed table-hover">
								<thead>
									<tr>
										<th class="info">资质资格类型</th>
										<th class="info">证书编号</th>
										<th class="info">资质资格最高等级</th>
										<th class="info">技术负责人姓名</th>
										<th class="info">技术负责人职称</th>
										<th class="info">技术负责人职务</th>
										<th class="info">单位负责人姓名</th>
										<th class="info">单位负责人职称</th>
										<th class="info">单位负责人职务</th>
										<th class="info">发证机关</th>
										<th class="info">发证日期</th>
										<th class="info">证书有效期截止日期</th>
										<th class="info">证书状态</th>
										<th class="info">附件</th>
									</tr>
								</thead>
								<c:forEach items="${supplierCertEng}" var="s">
									<tr>
										<td class="tc" onclick="reason('${s.id}','工程-资质证书信息');">${s.certType }</td>
										<td class="tc" id="${s.id }" onclick="reason('${s.id}','工程-资质证书信息');">${s.certCode }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质证书信息');">${s.certMaxLevel }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质证书信息');">${s.techName }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质证书信息');">${s.techPt }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质证书信息');">${s.techJop }</td>
										<td class="tc" onclick="reason('${s.id}','供应商资质证书信息');">${s.depName }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质证书信息');">${s.depPt }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质证书信息');">${s.depJop }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质证书信息');">${s.licenceAuthorith }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质证书信息');">
											<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' />
										</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质证书信息');">
											<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' /> 至
											<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
										</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质证书信息');">
											<c:if test="${s.certStatus==0 }">无效</c:if>
											<c:if test="${s.certStatus==1 }">有效</c:if>
										</td>
										<td class="tc">
											<c:if test="${s.attachCert !=null}">
												<a class="green" onclick="downloadFile('${s.attachCert}')">附件下载</a>
											</c:if>
											<c:if test="${s.attachCert ==null}">
												<a class="red">无附件下载</a>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>

						<div class="tab-pane fade active in">
							<h2 class="count_flow jbxx">供应商资质证书信息</h2>
							<table class="table table-bordered table-condensed table-hover">
								<thead>
									<tr>
										<th class="info">资质资格类型</th>
										<th class="info">证书编号</th>
										<th class="info">资质资格序列</th>
										<th class="info">专业类别</th>
										<th class="info">资质资格等级</th>
										<th class="info">是否主项资质</th>
										<th class="info">批准资质资格内容</th>
										<th class="info">首次批准资质资格文号</th>
										<th class="info">首次批准资质资格日期</th>
										<th class="info">资质资格取得方式</th>
										<th class="info">资质资格状态</th>
										<th class="info">资质资格状态变更时间</th>
										<th class="info">资质资格状态变更原因</th>
										<th class="info">附件</th>
									</tr>
								</thead>
								<c:forEach items="${supplierAptitutes}" var="s">
									<tr>
										<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">${s.certType }</td>
										<td class="tc" id="${s.id }" onclick="reason('${s.id}','工程-资质资格证书信息');">${s.certCode }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">${s.aptituteSequence }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">${s.professType }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">${s.aptituteLevel }</td>
										<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">
											<c:if test="${s.isMajorFund==0 }">否</c:if>
											<c:if test="${s.isMajorFund==1 }">是</c:if>
											<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">${s.aptituteContent }</td>
											<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">${s.aptituteCode }</td>
											<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">
												<fmt:formatDate value="${s.aptituteDate }" pattern='yyyy-MM-dd' />
											</td>
											<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">${s.aptituteWay }</td>
											<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">
												<c:if test="${s.aptituteStatus==0 }">无效</c:if>
												<c:if test="${s.aptituteStatus==1 }">有效</c:if>
											</td>
											<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">
												<fmt:formatDate value="${s.aptituteChangeAt }" pattern='yyyy-MM-dd' />
											</td>
											<td class="tc" onclick="reason('${s.id}','工程-资质资格证书信息');">${s.aptituteChangeReason }</td>
											<td class="tc">
												<c:if test="${s.attachCert !=null}">
													<a class="green" onclick="downloadFile('${s.attachCert}')">附件下载</a>
												</c:if>
												<c:if test="${s.attachCert ==null}">
													<a class="red">无附件下载</a>
												</c:if>
											</td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<div class="tab-pane fade active in">
							<table class="table table-bordered table-condensed table-hover">
								<thead>
									<tr>
										<th class="info w50">序号</th>
										<th class="info">注册名称</th>
										<th class="info">注册人数</th>
									</tr>
								</thead>
								<c:forEach items="${listRegPerson}" var="regPrson" varStatus="vs">
									<tr onclick="reason('${regPrson.id}','工程-注册人员登记信息');">
										<td class="tc">${vs.index + 1}</td>
										<td class="tc">${regPrson.regType}</td>
										<td class="tc">${regPrson.regNumber}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<div class="tab-pane fade active in">
							<h2 class="count_flow jbxx">供应商注册人员登记</h2>
							<table class="table table-bordered table-condensed table-hover">
						          <thead>
					              <tr>
					                <th class="info w50">序号</th>
					                <th class="info">注册名称</th>
					                <th class="info">注册人数</th>
					                <th class="info w50">操作</th>
					              </tr>
						          </thead>
				              <c:forEach items="${listRegPerson}" var="regPrson" varStatus="vs">
				                <tr>
				                  <td class="tc">${vs.index + 1}</td>
				                  <td class="tc">${regPrson.regType}</td>
				                  <td class="tc">${regPrson.regNumber}</td>
				                  <td class="tc w50">
				                    <p onclick="reason('${regPrson.id}','工程-注册人员登记信息');" id="${regPrson.id}_hidden2" class="btn">审核</p>
				                    <a id="${regPrson.id }_show2"><img src='/zhbj/public/backend/images/sc.png'></a>
				                  </td>
				                </tr>
				              </c:forEach>
				            </table>
						</div>
						<div class="tab-pane fade active in">
							<h2 class="count_flow jbxx">法人代表信息</h2>
							<table class="table table-bordered table-condensed table-hover">
								<tbody>
									<tr>
										<td class="bggrey">组织机构：</td>
										<td onmouseover="out('${supplierMatEngs.orgName}')">${supplierMatEngs.orgName}</td>
										<td class="bggrey">技术负责人数：</td>
										<td>${supplierMatEngs.totalTech }</td>
									</tr>
									<tr>
										<td class="bggrey">管理人员：</td>
										<td onmouseover="out('${supplierMatEngs.totalMange}')">${supplierMatEngs.totalMange}</td>
										<td class="bggrey">技术工人：</td>
										<td colspan="3">${supplierMatEngs.totalTechWorker }</td>
									</tr>
									<tr>
										<td class="bggrey">中级及以上职称人员：</td>
										<td colspan="3">${supplierMatEngs.totalGlNormal }</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>