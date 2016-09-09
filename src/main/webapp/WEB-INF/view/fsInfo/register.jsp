<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>进口供应商注册</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/upload/upload.css" type="text/css" />
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/supplier/validateSupplier.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript">
	function kaptcha() {
		$("#kaptchaImage").hide().attr('src', '${pageContext.request.contextPath}/Kaptcha.jpg').fadeIn();
	}
	$(function() {
		// 注册须知
		$("#registration_input_id").change(function() {
			var flag = $(this).prop("checked");
			if (!flag) {
				$("#register_input_id").attr("disabled", "disabled");
			} else {
				$("#register_input_id").removeAttr("disabled", "disabled");
			}
		});
	});
		/** 供应商完善注册信息页面 */
	function supplierRegist(name, i, position) {
		if(i==3){
			if (!validateRegSupplierInfo()) {
				return;
			}
		}
		if(i==4){
			if (!validateBusinessSupplierInfo()) {
				return;
			}
		}
		var t = null;
		var l = null;
		if (position == "pre") {
			t = name + "_" + i;
			l = name + "_" + (i - 1);
		}
		if (position == "next") {
			t = name + "_" + i;
			l = name + "_" + (i + 1);
		}
		$("#" + t).hide();
		$("#" + l).show();
	}
	$(function() {
	/** 校验用户名是否存在 */
		$("#loginName").blur(function() {
			var loginName = $(this).val();
			if(loginName) {
				$.ajax({
					url : "${pageContext.request.contextPath}/supplierFsInfo/checkLoginName.do",
					type : "post",
					data : {
						loginName : loginName
					},
					success : function(result) {
						 if(result == false) {
							layer.tips("用户名已存在，请重新填写.", "#loginName", {
								tips : 1
							});
							$("#loginName").val();
						}
					},
				});
			}
		});
		
		/** 校验手机号是否存在 */
		$("#mobile").blur(function() {
			var mobile = $(this).val();
			if(mobile) {
				$.ajax({
					url : "${pageContext.request.contextPath}/supplierFsInfo/checkMobile.do",
					type : "post",
					data : {
						mobile : mobile
					},
					success : function(result) {
						 if(result == false) {
							layer.tips("手机号已注册，请重新填写.", "#mobile", {
								tips : 1
							});
							$("#mobile").val();
						}
					},
				});
			}
		});
		
		$("#password").change(function() {
			var password = $("#password").val();
			if(!password) {
				layer.tips("请输入密码", "#password", {
					tips : 1
				});
				return false;
			} else if(!password.match(/^(?!(?:\d*$))[A-Za-z0-9_]{6,20}$/)) {
				layer.tips("密码由6-20位字母 数字组成 !", "#password", {
					tips : 1
				});
				return false;
			}
		});
		$("#confirmPassword").change(function() {
			var confirmPassword = $("#confirmPassword").val();
			var password = $("#password").val();
			if (!confirmPassword) {
				layer.tips("请输入确认密码 !", "#confirmPassword", {
					tips : 1
				});
				return false;
			} else if (confirmPassword != password) {
				layer.tips("密码不一致 !", "#confirmPassword", {
					tips : 1
				});
				return false;
			}
		});
	});
</script>

</head>

<body>
	<div class="wrapper">
		<div class="header-v4">
			<!-- Navbar -->
			<div class="navbar navbar-default mega-menu" role="navigation">
				<div class="container">
					<!-- logo和搜索 -->
					<div class="navbar-header">
						<div class="row container margin-bottom-10">
							<div class="col-md-8">
								<a href=""> <img alt="Logo" src="${pageContext.request.contextPath}/public/ZHQ/images/logo.png" id="logo-header"> </a>
							</div>
							<!--搜索开始-->
							<div class="col-md-4 mt50">
								<div class="search-block-v2">
									<div class="">
										<form accept-charset="UTF-8" action="" method="get">
											<div style="display:none">
												<input name="utf8" value="" type="hidden">
											</div>
											<input id="t" name="t" value="search_products" type="hidden">
											<div class="col-md-12 pull-right">
												<div class="input-group bround4">
													<input class="form-control h38" id="k" name="k" placeholder="" type="text"> <span class="input-group-btn"> <input class="btn-u h38" name="commit" value="搜索" type="submit"> </span>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
							<!--搜索结束-->
						</div>
					</div>

					<button data-target=".navbar-responsive-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
						<span class="full-width-menu">全部商品分类</span> <span class="icon-toggle"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </span>
					</button>
				</div>

				<div class="clearfix"></div>

				<div style="height: 0px;" aria-expanded="false" class="navbar-collapse navbar-responsive-collapse collapse">
					<div class="container">
						<ul class="nav navbar-nav">
							<!-- 通知 -->
							<li class="active dropdown tongzhi_li"><a class=" dropdown-toggle p0_30" href=""><i class="tongzhi nav_icon"></i>通知</a></li>
							<!-- End 通知 -->

							<!-- 公告 -->
							<li class="dropdown gonggao_li"><a class=" dropdown-toggle p0_30" href=""><i class="gonggao nav_icon"></i>公告</a></li>
							<!-- End 公告 -->

							<!-- 公示 -->
							<li class="dropdown gongshi_li"><a data-toggle="dropdown" class="dropdown-toggle p0_30 " href=""><i class="gongshi nav_icon"></i>公示</a></li>
							<!-- End 公示 -->

							<!-- 专家 -->
							<li class="dropdown zhuanjia_li"><a href="#" class="dropdown-toggle p0_30 " data-toggle="dropdown"><i class="zhuanjia nav_icon"></i>专家</a></li>
							<!-- End 专家 -->

							<!-- 投诉 -->
							<li class="dropdown tousu_li"><a data-toggle="dropdown" class="dropdown-toggle p0_30" href=""><i class="tousu nav_icon"></i>投诉</a></li>
							<!-- End 投诉 -->

							<!-- 法规 -->
							<li class="dropdown  fagui_li"><a href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="fagui nav_icon"></i>法规</a></li>
							<!-- End 法规 -->

							<li class="dropdown luntan_li"><a aria-expanded="false" href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="luntan nav_icon"></i>论坛</a></li>

						</ul>
					</div>
				</div>
				</div>
				<!--/end container-->
			</div>
		</div>
		<div id="reg_box_id_2" class="container content height-350 job-content">
			<div class="col-md-12 p20 border1 margin-top-20 mb40">
				<div class="tab-v1">
					<h2 class="tc bbgrey">阅读军队物资供应商须知</h2>
				</div>
				<div class="tab-content margin-bottom-20 margin-top-20 lh24">
					加入军队物资供应商库须知   一、需要准备的资质材料 （一）原件 1、营业执照副本，法定代表人身份证，组织机构代码证副本，税务登记证副本，各类认证证书，产品代理销售授权书（现场审验后退回）。 2、最近三年年终会计师事务所审计报告（包括资产负债表、损益表），5个工作日审验后退回。 3、税务部门出具的最近三年完税证明(完税发票收据无效)，社会保障机构出具的最近三年缴纳社会保障资金证明(发票收据无效)，开户银行出具的基本账户资信证明。 （二）复印件 1、营业执照副本，法定代表人身份证，组织机构代码证副本，税务登记证副本，各类认证证书，产品代理销售授权书。 2、最近三年年终会计师事务所审计报告（包括资产负债表、损益表）。 注：所有复印件必须装订成册，审计报告结论页和其他所有证明材料复印件均需要盖公章和法定代表人（或被授权人）签字或加盖有效个人签名章（其它印章无效）。 （三）供应商注册申请表一份。 二、申请注册程序 1、登录军队采购网（http://www.plap.cn/）,打开“供应商申请注册”页面； 2、选择受理供应商入库申请的军队物资采购机构； 3、按要求填写有关注册信息、上传营业执照副本、法定代表人身份证、组织机构代码证副本、各类认证证书、产品代理销售授权书的电子图片； 4、在线打印“军队物资供应商注册申请表”； 5、向军队物资采购机构提交“军队物资供应商注册申请表”、上述证明材料、以及资质材料的原件和复印件、复印件需要法定代表人签字（或签名章）并加盖公章。 三、其他注册事项 1、生产多种产品的集团企业,由具有企业法人资格的下属公司申请注册； 2、从事产品代理销售的供应商,应当是产品生产企业的全国总代理或者地区一级代理。 3、资质材料原件采购机构审核后退回,复印件留存； 4、每页复印件需要法定代表人或授权代理人签字并加盖公章,由授权代理人签字的、应有法定代表人授权书； 5、装订要求：“军队物资供应商注册申请表”装订成一册,法定代表人授权书,证明材料和资质材料复印件装订成一册。 6、注册咨询电话：010-66946342,联系人：陈工； 四、用户名和密码查询方法 如果忘记注册时使用的用户名和密码,可通过下面方法查询： 1、拟制用户名与密码查询申请（格式自拟）并加盖公章、注明申请人,联系方式和电子信箱地址（必须提供、否则无法恢复）；
					2、提交组织机构代码证,营业执照副本复印件,加盖公章； 3、将以上文件邮寄至：北京市丰台区丰台西路15号信息中心，收件人:陈工，邮编100071，咨询电话010-66945675； 4、我们在收到后密码查询申请函后。于每周一集中办理（如遇节假日顺延）以邮件的方式将查询到的用户名和密码发送给您； 5、受理时间：周一至周五上午8:30-11:00、下午2:30-3:30、周六、周日及节假日不受理 五、注销注册信息 凡在军队采购网提交网上注册申请的供应商,因资质材料填报有误,在库状态为已注册、暂存和退回供应商,可申请注销,已经通过采购机构审核的供应商不能办理网上注销。 2015年一月一日起注销注册信息按下列程序办理： 1、按照要求填写《军队采购网注册信息注销申请表》并加盖公章。附件下载：军队采购网注册信息注销申请表.rar 2、提交组织机构代码证和营业执照复印件，以及法人委托书，以及被委托人身份证复印件联系方式。加盖公章； 3、将注销申请表、组织机构代码证和营业执照请邮寄至：北京市丰台区丰台西路15号信息中心，收件人：华高工，邮编100071，咨询电话010-66945606； 4、我们将自收到注销申请资料后，集中办理，在采购网主页进行公告，提交注销申请的供应商自行上网查询。注销后供应商即可在网上重新注册； 5、申报时间：周一至周四上午8:30-11:00下午2:30-3:30周六周日及节假日不受理。 六、部分指标填写说明 （一）会计报表 每年3月1日起，需提交自上年度起经会计师事务所审计的年度会计报表。 （二）完税证明 主要是证明按照国家税法足额纳税，没有欠税、偷税、漏税的行为。如果国税和地税机构分开，则应当分别开具国税和地税完税证明。不能使用缴税发票代替完税证明。 （三）社保证明 主要是证明按照国家有关规定，足额缴纳员工的社会保险金。由当地社会保险经办机构出具并盖单。 （四）资信证明 基本帐户银行出具的资信证明。开户银行有固定资信证明格式的，使用银行的制式证明。银行无固定格式的，可参照下列格式出具。 采购机构名称：     XX公司在XX银行开立基本结算帐户，账号：XXXXX；至XX年XX月XX止，在我行办理的各项信贷业务，无逾期和欠息记录，资金结算方面无不良记录，执行结算纪律情况良好。特此证明。 XX银行（盖章）    XXX年XX月XX日   
					负责人或授权代理人签字    （五）质量体系认证证书 由国家质量检验监督总局认证认可委员会批准的可在中国大陆从事认证业务的国内和国外认证机构出具的质量体系认证证书。如果是外国认证机构，其出具证书必须为中文。认证证书可在认证认可委员会或认证机构网站查询。如正在年检，应当有认证机构出具的受理年检证明。执行的质量认证标准一般为国标（GB\T19001）或国军标（GJB\T19001）。有些行业（如食品、药品等）执行特殊的质量认证标准。 （六）环境体系认证证书 认证机构和证书查询方法同质量体系认证证书。执行的环境保护认证标准一般为国标GB\T24001。 （七）国家行业准入证书 国家对有准入要求的特殊行业需要提供国家行业准入证书。如医疗器械、药品的生产企业应当有《医疗器械生产企业许可证》或《药品生产许可证》，医疗器械、药品的经营企业应当有《医疗器械经营企业许可证》或《药品经营许可证》。   推荐使用IE8浏览器进行注册，其他浏览器的注册兼容会陆续上线！
					<div class="mt40">
						<div class="fl">
							文件下载：<span class="ml10">供应商注册须知</span><a href="#" class="download"></a>
						</div>
						<div class="fl ml20">
							产品分类目录<a href="#" class="download"></a>
						</div>
						<div class="clear"></div>
					</div>
					<div class="mt40">
						<input id="registration_input_id" type="checkbox" checked="checked" class="radio_orange"><span class="ml10">我已阅读，并且完全遵守相关规定</span>
					</div>
					<div class="mt40 tc">
					    <input type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/index.jsp'" value="返回">
						<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 2, 'next')">开始注册</button>
					</div>
				</div>
			</div>
		</div>
		<form id="form1" action="${pageContext.request.contextPath}/supplierFsInfo/registerEnd.html" method="post"  enctype="multipart/form-data">
		<div id="reg_box_id_3" class="container clear margin-top-30 yinc">
			<h2 class="padding-20 mt40">
				<span class="new_step current fl ml170"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表和承诺书</span> </span> <span class="new_step fl"><i class="">5</i><span class="step_desc_01">申请表和承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>

			<div  class="col-md-12 margin-top-40">
				<div class="row">
					<div class="login_main mt20">
							<div class="login_item">
								<label class="col-md-3 p0"><i class="red mr5">*</i>用 户 名：</label> <input type="text" id="loginName" name="loginName" value="${loginName }" class="fl"> <span class="fl warning">（用户名由字母、数字、－等字符组成）</span>
								<div class="clear" id="loginNameMsg"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>登录密码：</label> <input type="password" id="password" name="password" class="fl"> <span class="fl warning">（密码由6-20位，由字母、数字组成）</span>
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>确认密码：</label> <input type="password" id="confirmPassword" class="fl">
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>手机号码：</label> <input type="text" id="mobile" name="mobile" class="fl">
								<button class="btn padding-left-10 padding-right-10 btn_back ml10">发送验证码</button>
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>手机验证码：</label> <input type="text" id="mobileCode_input_id" class="fl">
								<div class="clear"></div>
							</div>
							<div class="login_item margin-top-10">
								<label class="col-md-3 p0"><i class="red mr5">*</i>验证码：</label> <input type="text" id="identityCode_input_id" class="fl input-yzm">
								<div class="fl">
									<div class="yzm fl">
										<img src="Kaptcha.jpg" onclick="kaptcha();" id="kaptchaImage" /> 
									</div>
									<button class="btn padding-left-10 padding-right-10 btn_back ml10 fl" onclick="kaptcha();">换一张</button>
								</div>
								<div class="clear"></div>
							</div>
						<div class="tc mt20 clear col-md-11">
							<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 3, 'pre')">上一步</button>
							<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 3, 'next')">下一步</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="reg_box_id_4" class="container clear margin-top-30 yinc">
			<h2 class="padding-20 mt40">
				<span class="new_step current fl ml170"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表和承诺书</span> </span> <span class="new_step fl"><i class="">5</i><span class="step_desc_01">申请表和承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<div class="tab-content padding-top-20  h730">
							<div class="tab-pane fade active in height-450" id="tab-1">
								<div class=" margin-bottom-0">
									<h2 class="f16 jbxx">
										<i>01</i>企业基本信息
									</h2>
									<ul class="list-unstyled list-flow">
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 企业名称：</span>
											<div class="input-append">
												<input class="span3" id="supplierName" name="supplierName" value="${sfi.supplierName }" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 企业类别：</span>
											<div class="input-append">
												<input class="span3" id="supplierTepe" name="supplierTepe" value="${sfi.supplierTepe }"  type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 中文译名：</span>
											<div class="input-append">
												<input class="span3" id="supplierChinesrName" name="supplierChinesrName" value="${sfi.supplierChinesrName }" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 法定代表人：</span>
											<div class="input-append">
												<input class="span3" id="legalName" name="legalName" value="${sfi.legalName }" type="text">
											</div>
										</li>
										<li class="col-md-6 p0"><span class=""><i class="red">＊</i>企业注册地址：</span>
											<div class="input-append">
												<input class="span3" id="address" name="address" value="${sfi.address }" type="text">
											</div>
											<%-- <div class="fl">
												<div class="input-append mr18">
												    <input id="address" name="address" value="${sfi.address }" type="hidden">
													<input class="span4" id="address" name="address" value="${sfi.address }" type="text">
													<div class="btn-group ">
														<button class="btn dropdown-toggle add-on" data-toggle="dropdown">
															<img src="${pageContext.request.contextPath}/public/ZHQ/images/down.png" class="margin-bottom-5" />
														</button>
														<ul class="dropdown-menu list-unstyled">
															<li>fasdfasdfa</li>
														</ul>
													</div>
												</div>
												<div class="input-append">
													<input class="span4" id="appendedInput" type="text">
													<div class="btn-group ">
														<button class="btn dropdown-toggle add-on" data-toggle="dropdown">
															<img src="${pageContext.request.contextPath}/public/ZHQ/images/down.png" class="margin-bottom-5" />
														</button>
														<ul class="dropdown-menu list-unstyled">
														<li>fasdfasdfa</li>
														</ul>
													</div>
												</div>
											</div> --%>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>邮政编码：</span>
											<div class="input-append">
												<input class="span3" id="supplierZipCode" name="supplierZipCode" value="${sfi.supplierZipCode }"  type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>经营产品大类：</span>
											<div class="input-append">
												<input class="span3" id="productType" name="productType" value="${sfi.productType }" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>主营产品：</span>
											<div class="input-append">
												<input class="span3" id="majorProduct" name="majorProduct" value="${sfi.majorProduct }" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>兼营产品：</span>
											<div class="input-append">
												<input class="span3" id="sideProduct" name="sideProduct" value="${sfi.sideProduct }" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>生产商名称：</span>
											<div class="input-append">
												<input class="span3" id="producerName" name="producerName" value="${sfi.producerName }"  type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 联系人：</span>
											<div class="input-append">
												<input class="span3" id="contactPerson" name="contactPerson" value="${sfi.contactPerson }" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 电话：</span>
											<div class="input-append">
												<input class="span3" id="supplierTele" name="supplierTele" value="${sfi.supplierTele }" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 传真：</span>
											<div class="input-append">
												<input class="span3" id="supplierFax" name="supplierFax" value="${sfi.supplierFax }" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 电子邮件：</span>
											<div class="input-append">
												<input class="span3" id="supplierEmail" name="supplierEmail" value="${sfi.supplierEmail }" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 企业网址：</span>
											<div class="input-append">
												<input class="span3" id="netUrl" name="netUrl" value="${sfi.netUrl }" type="text">
											</div>
										</li>
										<li class="col-md-12 p0 mt10"><span class="fl"><i class="red">＊</i>国内供货业绩：</span>
											<div class="col-md-9 mt5">
												<div class="row">
													<textarea class="text_area col-md-12" id="supplyLevel" name="supplyLevel"  title="不超过800个字" placeholder=""> ${sfi.supplyLevel }</textarea>
												</div>
											</div>
											<div class="clear"></div>
										</li>
										<li class="col-md-12 p0 mt10"><span class="fl"><i class="red">＊</i>企业简介：</span>
											<div class="col-md-9 mt5">
												<div class="row">
													<textarea class="text_area col-md-12" id="supplierRemark" name="supplierRemark" title="不超过800个字" placeholder="">${sfi.supplierRemark }</textarea>
												</div>
											</div>
											<div class="clear"></div>
										</li>
									</ul>
									</div>
									<div class="tc mt20 clear col-md-11">
											<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 4, 'pre')">上一步</button>
											<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 4, 'next')">下一步</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="reg_box_id_5" class="container clear margin-top-30 yinc">
		  	<h2 class="padding-20 mt40">
				<span class="new_step current fl ml170"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表和承诺书</span> </span> <span class="new_step fl"><i class="">5</i><span class="step_desc_01">申请表和承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
		    <h2 class="f16 jbxx">
				可选择的采购机构
			</h2>
			 <input type="hidden" id="orgmanId" name="orgmanId" value="${orgmanId }" />
			<table id="tb1"  class="table table-bordered table-condensed">
				<tr>
					<td>选择</td>
					<td>序号</td>
					<td>采购机构名称</td>
					<td>机构代称</td>
					<td>是否可审核</td>
					<td>所在城市</td>
				</tr>
				<%-- <c:forEach items="" var="" varStatus="vs">
					<tr>
						<td><input type="checkbox" name="cbox" onclick="box(this)" /></td>
						<td>${(l-1)*10+vs.index+1}</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach> --%>
			</table>
			 <h2 class="f16 jbxx">
				其他采购机构
			</h2>
			<table id="tb2" class="table table-bordered table-condensed">
				<tr>
					<td>选择</td>
					<td>序号</td>
					<td>采购机构名称</td>
					<td>机构代称</td>
					<td>是否可审核</td>
					<td>所在城市</td>
				</tr>
				<%-- <c:forEach items="" var="" varStatus="vs">
					<tr>
						<td><input type="checkbox"  name="cbox" onclick="box(this)" /></td>
						<td>${(l-1)*10+vs.index+1}</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach> --%>
			</table>
			<h6>
		               友情提示：请供应商记录好初审采购机构的相关信息，以便进行及时沟通
		    </h6>
		    <div class="tc mt20 clear col-md-11">
				<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 5, 'pre')">上一步</button>
				<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 5, 'next')">下一步</button>
			</div>
		</div>
		
		<!-- 项目戳开始 -->
		<div id="reg_box_id_6" class="container clear margin-top-30 yinc">
			<h2 class="padding-20 mt40">
				<span class="new_step current fl ml170"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step current fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表和承诺书</span> </span> <span class="new_step fl"><i class="">5</i><span class="step_desc_01">申请表和承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
		<!--详情开始-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgdd">
							<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">详细信息</a></li>
							<li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">财务信息</a></li>
						</ul>
							<div class="tab-content padding-top-20 h1030">
								<div class="tab-pane fade active in height-450" id="tab-1">
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx tc">
										军队物资进口供应商入库信息申请表
									</h2>
									<ul class="list-unstyled list-flow">
									     <li class="col-md-6 p0">
										   <span class="">企业名称：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li>
									     <li class="col-md-6  p0 ">
										   <span class="">企业类别：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li>
									     <li class="col-md-6  p0 ">
										   <span class="">中文译名：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li> 
									     <li class="col-md-6  p0 ">
										   <span class="">法定代表人：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li> 
									     <li class="col-md-6  p0 ">
										   <span class="">企业注册地址：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li> 
									     <li class="col-md-6  p0 ">
										   <span class="">邮政编码：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li> 
									     <li class="col-md-6  p0 ">
										   <span class="">营业执照注册号：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li>  
									     <li class="col-md-6  p0 ">
										   <span class="">发证机关：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li>
										  <li class="col-md-6  p0 ">
										   <span class="">经营产品大类：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li>  
									   </ul>
									  </div> 
									   
									  <!-- 产品明细开始-->
									  <div class="padding-top-10 clear">
									   <ul class="list-unstyled list-flow p0_20 ">
									
									     <li class="col-md-6 p0 ">
										   <span class="">主营产品：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li>  
									     <li class="col-md-6 p0 ">
										   <span class="">兼营产品：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li> 
										 </ul> 
									  </div>
									    <div class="padding-top-10 clear">
									   <ul class="list-unstyled list-flow p0_20 ">
									
									     <li class="col-md-6 p0 ">
										   <span class="">联系人：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li>  
									     <li class="col-md-6 p0 ">
										   <span class="">电子邮件：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li>  
									     <li class="col-md-6 p0 ">
										   <span class="">企业网址：</span>
										   <div class="input-append">
									        <input class="span2" id="appendedInput" type="text">
									        <span class="add-on">i</span>
									       </div>
										 </li>  
									     <li class="col-md-12 p0">
										   <span class="fl">国内供货业绩：</span>
										   <div class="col-md-9 p0 fn mt5 pwr9">
									        <textarea class="text_area col-md-12 " title="" placeholder=""></textarea>
									       </div>
										 </li> 
									     <li class="col-md-12 p0">
										   <span class="fl">企业简介：</span>
										   <div class="col-md-9 p0 fn mt5 pwr9">
									        <textarea class="text_area col-md-12 " title="" placeholder=""></textarea>
									       </div>
										 </li> 
										</ul> 
									  </div>
									 <div class="padding-top-10 clear">
									   <ul class="list-unstyled list-flow p0_20 ">
										 <li class="col-md-12 p0">
										   <span class="fl">物资进口单位或物&nbsp;<br/>资采购机构初审意见：</span>
										   <div class="col-md-9 p0 fn mt5 pwr9">
									        <textarea class="text_area col-md-12 " title="" placeholder=""></textarea>
									       </div>
										 </li> 
										  <li class="col-md-12 p0">
										   <span class="fl">军区级物资进口&nbsp;<br/>管理部门审查意见：</span>
										   <div class="col-md-9 p0 fn mt5 pwr9">
									        <textarea class="text_area col-md-12 " title="" placeholder=""></textarea>
									       </div>
										 </li>  
										 <li class="col-md-12 p0">
										   <span class="fl">总后勤部军区物&nbsp;<br/>资油料部审核意见：</span>
										   <div class="col-md-9 p0 fn mt5 pwr9">
									        <textarea class="text_area col-md-12 " title="" placeholder=""></textarea>
									       </div>
										 </li> 
									</ul>
									</div>
											<div class="tc mt20 clear col-md-11">
											<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 6, 'pre')">上一步</button>
											<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 6, 'next')">下一步</button>
											<button class="btn btn-windows git"   type="button" onclick="window.print()">打印</button>
											</div>
								</div>
								<div class="tab-pane fade height-450" id="tab-2">
									<div class="margin-bottom-0  categories">
									</div>
								</div>
							</div>
					</div>
				</div>
			</div>
			</div>
	</div>
	<div id="reg_box_id_7" class="container clear margin-top-30 yinc">
		  	<h2 class="padding-20 mt40" style="margin-left:280px;">
				<span class="new_step current fl"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step current fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表和承诺书</span> </span> <span class="new_step current fl"><i class="">5</i><span class="step_desc_01">申请表和承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
			<!-- 供应商申请书上传：<input type="file" name=""/>
			供应商承诺书上传：<input type="file" name=""/> -->
				   	<div class="input-append mt40" style="margin-left:280px;">
						<div class="uploader orange m0">
							<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
							<input type="button" name="file" class="button" value="选择文件..."/>
							<input id="regIdentity" type="file" name="files" size="30" accept="image/*"/>
						</div>
						<!-- <div class="uploader orange m0">
							<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
							<input type="button" name="file" class="button" value="选择文件..."/>
							<input id="regIdentity" type="file" name="files" size="30" accept="image/*"/>
						</div> -->
					</div>
			<div class="col-md-12 add_regist" style="margin-left:170px;">
				 <div class="fl mr20"><label class="regist_name">采购机构名称：</label><span class="regist_desc">广州军区物资采购站</span></div>
				 <div class="fl mr20"><label class="regist_name">采购机构联系人：</label><span class="regist_desc">张三</span></div>
				 <div class="fl mr20"><label class="regist_name">采购机构地址：</label><span class="regist_desc">广州军区物资采购站</span></div>
				 <div class="fl mr20"><label class="regist_name">联系电话：</label><span class="regist_desc">1882928798</span></div>
			 </div>
		    <div class="tc mt20 clear col-md-11">
		   		<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 7, 'pre')">上一步</button>
				<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 7, 'next')">下一步</button>
			</div>
		</div>
		
		
		
		<div id="reg_box_id_8" class="container content height-350 yinc">
		 <div class="row magazine-page pt40 mb40">
		   <div class="login_cl fl col-md-3">
		    <img src="${pageContext.request.contextPath}/public/ZHQ/images/success.jpg"/>
		   </div>
		   <div class="login_cr fl col-md-9 pt20">
		    <div class="col-md-12">
		     <p>
			  <span class="regist_info f18 b">恭喜您注册成功！</span>正在等待审核人员审批。
			 </p>
			 <p>以下是系统默认为您生成的账号和密码</p>
			 <p>您的<span class="red">登录账号</span>为：<span class="red">18688164815</span>敬请查收</p>
			 <p>如您忘记<span class="red">登录密码</span>，请在门户网站首页处通过<a class="red">找回密码方式</a>获取    </p>
		    </div>
			<div class="col-md-12 add_regist">
			 <div class="fl mr20"><label class="regist_name">采购机构名称：</label><span class="regist_desc">广州军区物资采购站</span></div>
			 <div class="fl mr20"><label class="regist_name">采购机构联系人：</label><span class="regist_desc">张三</span></div>
			 <div class="fl mr20"><label class="regist_name">采购机构地址：</label><span class="regist_desc">广州军区物资采购站</span></div>
			 <div class="fl mr20"><label class="regist_name">联系电话：</label><span class="regist_desc">1882928798</span></div>
			 </div>
			 <div class="mt20 col-md-12">
			  <button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 8, 'pre')">上一步</button>
			  <input  class="btn btn-windows git" type="submit" value="提交" />
			 </div>
			</div>
		   </div>
		 </div>
		</form>
		<!--底部代码开始-->
		<div class="footer-v2 clear " id="footer-v2">
			<div class="footer">
				<!-- Address -->
				<address class="">Copyright &#169 2016 版权所有：中央军委后勤保障部 京ICP备09055519号</address>
				<div class="">浏览本网主页，建议将电脑显示屏的分辨率调为1024*768</div>
				<!-- End Address -->
				<!--/footer-->
			</div>
		</div>
</body>
</html>
