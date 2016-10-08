<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加物资销售证书信息</title>

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
		var action = "${pageContext.request.contextPath}/supplier_cert_sell/";
		if (sign) {
			action += "save_or_update_cert_sell.html";
		} else {
			action += "back_to_professional.html";
		}
		$("#cert_sell_form_id").attr("action", action);
		$("#cert_sell_form_id").submit();
	}
</script>

</head>

<body>
<div class="wrapper">

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="cert_sell_form_id" method="post" target="_parent"  enctype="multipart/form-data">
							<input name="supplierId" value="${supplierId}" type="hidden" />
							<input name="matSellId" value="${matSellId}" type="hidden" />
							<div class="tab-content padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 资质证书名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="name" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 资质等级：</span>
												<div class="input-append">
													<input class="span3" type="text" name="levelCert" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 供发证机关：</span>
												<div class="input-append">
													<input class="span3" type="text" name="licenceAuthorith" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 有效开始时间 ：</span>
												<div class="input-append">
													<input class="span3" type="text" name="expStartDate" readonly="readonly" onClick="WdatePicker()" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 有效结束时间 ：</span>
												<div class="input-append">
													<input class="span3" type="text" name="expEndDate" readonly="readonly" onClick="WdatePicker()" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 是否年检：</span>
												<div class="input-append">
													<input class="span3" type="text" name="mot" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 证书附件：</span>
												<div class="input-append">
													<div class="uploader orange h32 m0">
														<input type="text" class="filename fz11 h32" readonly="readonly"/>
														<input type="button" name="file" class="button" value="选择..."/>
														<input name="attachFile" type="file" size="30"/>
													</div>
												</div>
											</li>
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
