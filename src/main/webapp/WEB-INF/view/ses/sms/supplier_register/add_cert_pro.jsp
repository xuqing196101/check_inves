<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加物资生产品目信息</title>

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


</head>

<body>
<div class="wrapper">
		<!-- header -->
		<jsp:include page="../../../../../index_head.jsp"></jsp:include>

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="basic_info_form_id" action="${pageContext.request.contextPath}/supplier/nextStep.html" method="post"  enctype="multipart/form-data">
							<input name="id" value="${supplierId}" type="hidden" />
							<input name="sign" value="2" type="hidden" />
							<div class="tab-content padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-450" id="tab-1">
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>基本信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 供应商名称：</span>
												<div class="input-append">
													<input class="span3" id="supplierName_input_id" type="text" name="supplierName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 公司网址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="website" />
												</div>
											</li>
											<li class="col-md-6  p0 "><span class=""><i class="red">＊</i>成立日期：</span>
												<div class="input-append">
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="foundDate" /> 
													<span class="add-on"> <img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>营业执照登记类型：</span>
												<div class="input-append">
													<input class="span2" id="businessType_input_id" name="businessType" type="text" readonly="readonly" />
													<div class="btn-group">
														<button class="btn dropdown-toggle add-on" data-toggle="dropdown">
															<img src="${pageContext.request.contextPath}/public/ZHQ/images/down.png" class="margin-bottom-5" />
														</button>
														<ul class="dropdown-menu list-unstyled">
															<li class="hand tc" onclick="checkText(this, 'businessType_input_id')">国有企业</li>
															<li class="hand tc" onclick="checkText(this, 'businessType_input_id')">外资企业</li>
															<li class="hand tc" onclick="checkText(this, 'businessType_input_id')">民营企业</li>
															<li class="hand tc" onclick="checkText(this, 'businessType_input_id')">股份制企业</li>
															<li class="hand tc" onclick="checkText(this, 'businessType_input_id')">私营企业</li>
														</ul>
													</div>
												</div>
											</li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>公司地址：</span>
												<div class="fl">
													<div class="input-append mr18">
														<input class="span4" id="address_input_id1" type="text" readonly="readonly" name="address" />
														<div class="btn-group">
															<button class="btn dropdown-toggle add-on" data-toggle="dropdown">
																<img src="${pageContext.request.contextPath}/public/ZHQ/images/down.png" class="margin-bottom-5" />
															</button>
															<ul class="dropdown-menu list-unstyled">
																<li class="hand tc" onclick="checkText(this, 'address_input_id1')">河北</li>
															</ul>
														</div>
													</div>
													<div class="input-append">
														<input class="span4" id="address_input_id2" type="text" readonly="readonly">
														<div class="btn-group">
															<button class="btn dropdown-toggle add-on" data-toggle="dropdown">
																<img src="${pageContext.request.contextPath}/public/ZHQ/images/down.png" class="margin-bottom-5" />
															</button>
															<ul class="dropdown-menu list-unstyled">
																<li class="hand tc" onclick="checkText(this, 'address_input_id2')">保定</li>
															</ul>
														</div>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>开户行名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankName" />
												</div>
											</li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>开户行账号：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 邮编：</span>
												<div class="input-append">
													<input class="span3" type="text" name="postCode" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>


										<h2 class="f16 jbxx mt40">
											<i>02</i>资质资信
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i> 近三个月完税凭证：</span>
												<div class="input-append">
													<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
														<input type="button" class="button" value="选择文件..." /> 
														<input type="file" size="30" accept="image/*" />
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>近三年银行基本账户年末对账单：</span>
												<div class="input-append">
													<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
														<input type="button" class="button" value="选择文件..." /> 
														<input type="file" size="30" accept="image/*" />
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>近三个月缴纳社会保险金凭证：</span>
												<div class="input-append">
													<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
														<input type="button" class="button" value="选择文件..." /> 
														<input type="file" size="30" accept="image/*" />
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>近三年内无重大违法记录声明：</span>
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

										<h2 class="f16 jbxx mt40">
											<i>03</i>法定代表人信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 姓名：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 身份证号：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legaIdCard" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 固定电话：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalTelephone" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 手机：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalMobile" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>04</i>联系人信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 姓名：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 传真电话：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactFax" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 固定电话：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactTelephone" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 手机：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactMobile" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 邮箱：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactEmail" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 地址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactAddress" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>05</i>营业执照
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 统一社会信用代码：</span>
												<div class="input-append">
													<input class="span3" type="text" name="creditCode" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 登记机关：</span>
												<div class="input-append">
													<input class="span3" type="text" name="registAuthority" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 注册资本：</span>
												<div class="input-append">
													<input class="span3" type="text" name="registFund" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>营业开始时间：</span>
												<div class="input-append">
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" /> 
													<span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>营业截止时间：</span>
												<div class="input-append">
													<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="businessEndDate" /> 
													<span class="add-on"><img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div>
											</li>
											
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 生产经营地址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="businessAddress" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 邮编：</span>
												<div class="input-append">
													<input class="span3" type="text" name="businessPostCode" />
												</div>
											</li>
											<li class="col-md-12 p0 mt10"><span class="fl"><i class="red">＊</i>经营范围：</span>
												<div class="col-md-9 mt5">
													<div class="row _mr20">
														<textarea class="text_area col-md-12" title="不超过800个字" name="businessScope"></textarea>
													</div>
												</div>
												<div class="clear"></div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>05</i>境外分支
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 境外分支结构：</span>
												<div class="input-append">
													<input class="span2" id="overseasBranch_input_id" name="overseasBranch" type="text" readonly="readonly" />
													<div class="btn-group">
														<button class="btn dropdown-toggle add-on" data-toggle="dropdown">
															<img src="${pageContext.request.contextPath}/public/ZHQ/images/down.png" class="margin-bottom-5" />
														</button>
														<ul class="dropdown-menu list-unstyled">
															<li class="hand tc" onclick="checkText(this, 'overseasBranch_input_id')">是</li>
															<li class="hand tc" onclick="checkText(this, 'overseasBranch_input_id')">否</li>
														</ul>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 境外分支所在国家：</span>
												<div class="input-append">
													<input class="span3" type="text">
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 分支地址：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchAddress" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 机构名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 分支生产经营范围：</span>
												<div class="input-append">
													<input class="span3" type="text" name="branchBusinessScope" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveBasicInfo(0)">暂存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveBasicInfo(1)">下一步</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- footer -->
	<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
</body>
</html>
