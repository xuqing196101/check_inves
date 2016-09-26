<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加工程资质资格信息</title>

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
		var action = "${pageContext.request.contextPath}/supplier_aptitute/";
		if (sign) {
			action += "save_or_update_aptitute.html";
		} else {
			action += "back_to_professional.html";
		}
		$("#cert_eng_form_id").attr("action", action);
		$("#cert_eng_form_id").submit();
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
						<form id="cert_eng_form_id" method="post" target="_parent"  enctype="multipart/form-data">
							<input name="supplierId" value="${supplierId}" type="hidden" />
							<input name="matEngId" value="${matEngId}" type="hidden" />
							<div class="tab-content padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 资质资格类型：</span>
												<div class="input-append">
													<input class="span3" type="text" name="certType" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 证书编号：</span>
												<div class="input-append">
													<input class="span3" type="text" name="certCode" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 资质资格序列：</span>
												<div class="input-append">
													<input class="span3" type="text" name="aptituteSequence" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 专业类别：</span>
												<div class="input-append">
													<input class="span3" type="text" name="professType" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 资质资格等级：</span>
												<div class="input-append">
													<input class="span3" type="text" name="aptituteLevel" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 是否主项资质：</span>
												<div class="input-append">
													<input class="span3" type="text" name="isMajorFund" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 批准资质资格内容：</span>
												<div class="input-append">
													<input class="span3" type="text" name="aptituteContent" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 首次批准资质资格文号：</span>
												<div class="input-append">
													<input class="span3" type="text" name="aptituteCode" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 首次批准资质资格日期：</span>
												<div class="input-append">
													<input class="span3" type="text" name="aptituteDate" readonly="readonly" onClick="WdatePicker()" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 资质资格取得方式：</span>
												<div class="input-append">
													<input class="span3" type="text" name="aptituteWay" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 资质资格状态：</span>
												<div class="input-append">
													<input class="span3" type="text" name="aptituteStatus" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 资质资格状态变更时间 ：</span>
												<div class="input-append">
													<input class="span3" type="text" name="aptituteChangeAt" readonly="readonly" onClick="WdatePicker()" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 资质资格状态变更原因：</span>
												<div class="input-append">
													<input class="span3" type="text" name="aptituteChangeReason" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class="w175"><i class="red">＊</i> 附件上传：</span>
												<div class="input-append">
													<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
														<input type="button" class="button" value="选择文件..." /> 
														<input type="file" size="30" accept="image/*" />
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
