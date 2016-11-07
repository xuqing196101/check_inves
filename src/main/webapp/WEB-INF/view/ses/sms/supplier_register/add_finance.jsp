<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加财务信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>

<script type="text/javascript">
	
	function saveOrBack(sign) {
		var action = "${pageContext.request.contextPath}/supplier_finance/";
		if (sign) {
			action += "save_or_update_finance.html";
		} else {
			action += "back_to_basic_info.html";
		}
		$("#finance_form_id").attr("action", action);
		$("#finance_form_id").submit();
	}
</script>

</head>

<body>
<div class="wrapper">

		<!--基本信息-->
		<div class="container content height-300">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="finance_form_id" method="post" target="_parent">
							<input name="supplierId" value="${supplierId}" type="hidden" />
							<div class="tab-content padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-200" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 年份：</span>
												<div class="input-append">
													<input class="span3" type="text" name="year" readonly="readonly" onClick="WdatePicker({dateFmt : 'yyyy'})" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 会计实务所名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="name" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 事务所联系电话：</span>
												<div class="input-append">
													<input class="span3" type="text" name="telephone" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 审计人姓名：</span>
												<div class="input-append">
													<input class="span3" type="text" name="auditors" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 指标：</span>
												<div class="input-append">
													<input class="span3" type="text" name="quota" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 资产总额：</span>
												<div class="input-append">
													<input class="span3" type="text" name="totalAssets" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 负债总额：</span>
												<div class="input-append">
													<input class="span3" type="text" name="totalLiabilities" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 净资产总额：</span>
												<div class="input-append">
													<input class="span3" type="text" name="totalNetAssets" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 营业收入：</span>
												<div class="input-append">
													<input class="span3" type="text" name="taking" />
												</div>
											</li>
											<%--<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 财务审计报告的审计意见：</span>
												<div class="input-append">
													<div class="uploader orange h32 m0 fz8">
														<input type="text" class="filename h32 fz8" readonly="readonly"/>
														<input type="button" name="file" class="button" value="选择..."/>
														<input name="auditOpinionFile" type="file" size="30"/>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 资产负债表：</span>
												<div class="input-append">
													<div class="uploader orange h32 m0 fz8">
														<input type="text" class="filename h32 fz8" readonly="readonly"/>
														<input type="button" name="file" class="button" value="选择..."/>
														<input name="liabilitiesListFile" type="file" size="30"/>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 利润表：</span>
												<div class="input-append">
													<div class="uploader orange h32 m0 fz8">
														<input type="text" class="filename h32 fz8" readonly="readonly"/>
														<input type="button" name="file" class="button" value="选择..."/>
														<input name="profitListFile" type="file" size="30"/>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 现金流量表：</span>
												<div class="input-append">
													<div class="uploader orange h32 m0 fz8">
														<input type="text" class="filename h32 fz8" readonly="readonly"/>
														<input type="button" name="file" class="button" value="选择..."/>
														<input name="cashFlowStatementFile" type="file" size="30"/>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w220"><i class="red">＊</i> 所有者权益变动表：</span>
												<div class="input-append">
													<div class="uploader orange h32 m0 fz8">
														<input type="text" class="filename h32 fz8" readonly="readonly"/>
														<input type="button" name="file" class="button" value="选择..."/>
														<input name="changeListFile" type="file" size="30"/>
													</div>
												</div>
											</li>--%>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveOrBack(1)">保存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveOrBack(0)">返回</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
