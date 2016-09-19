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
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/supplier/validateSupplier.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	var LODOP; 
	function prn3_preview(){
		LODOP = getLodop();
		if(LODOP) {
			LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.documentElement.innerHTML);
			LODOP.SET_PRINT_PAGESIZE(3,1000,1000,"A3");
			LODOP.PREVIEW();
		}
	};
	
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
			}else{
				form2.submit();
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
					url : "${pageContext.request.contextPath}/importSupplier/checkLoginName.do",
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
					url : "${pageContext.request.contextPath}/importSupplier/checkMobile.do",
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
	function box(cb){
			var obj = document.getElementsByName("cbox");
				 	for (i=0; i<obj.length; i++){   
		             if (obj[i]!=cb) 
		                 obj[i].checked = false;      
			            else {
			               obj[i].checked = true;  
			               var tdId=obj[0].value;
			               document.getElementById("orgId").value=tdId;
			            } 
	         	}   
} 
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
													<input class="form-control h38" id="k" name="k" placeholder="" type="text"> 
													<span class="input-group-btn"> <input class="btn-u h38" name="commit" value="搜索" type="submit"> </span>
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
						<span class="full-width-menu">全部商品分类</span> 
						<span class="icon-toggle"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </span>
					</button>
				</div>

				<div class="clearfix"></div>

				<div style="height: 0px;" aria-expanded="false" class="navbar-collapse navbar-responsive-collapse collapse">
					<div class="container">
						<ul class="nav navbar-nav">
							<!-- 通知 -->
							<li class="active dropdown tongzhi_li">
								<a class=" dropdown-toggle p0_30" href=""><i class="tongzhi nav_icon"></i>通知</a>
							</li>
							<!-- End 通知 -->

							<!-- 公告 -->
							<li class="dropdown gonggao_li">
								<a class=" dropdown-toggle p0_30" href=""><i class="gonggao nav_icon"></i>公告</a>
							</li>
							<!-- End 公告 -->

							<!-- 公示 -->
							<li class="dropdown gongshi_li">
								<a data-toggle="dropdown" class="dropdown-toggle p0_30 " href=""><i class="gongshi nav_icon"></i>公示</a>
							</li>
							<!-- End 公示 -->

							<!-- 专家 -->
							<li class="dropdown zhuanjia_li">
								<a href="#" class="dropdown-toggle p0_30 " data-toggle="dropdown"><i class="zhuanjia nav_icon"></i>专家</a>
							</li>
							<!-- End 专家 -->

							<!-- 投诉 -->
							<li class="dropdown tousu_li">
								<a data-toggle="dropdown" class="dropdown-toggle p0_30" href=""><i class="tousu nav_icon"></i>投诉</a>
							</li>
							<!-- End 投诉 -->

							<!-- 法规 -->
							<li class="dropdown  fagui_li">
								<a href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="fagui nav_icon"></i>法规</a>
							</li>
							<!-- End 法规 -->

							<li class="dropdown luntan_li">
								<a aria-expanded="false" href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="luntan nav_icon"></i>论坛</a>
							</li>

						</ul>
					</div>
				</div>
				</div>
				<!--/end container-->
			</div>
		</div>
		<form id="form1" action="${pageContext.request.contextPath}/importSupplier/registerEnd.html" method="post"  enctype="multipart/form-data">
		<div id="reg_box_id_4" class="container clear margin-top-30">
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
							<div class="tab-pane fade active in height-450">
								<div class=" margin-bottom-0">
									<h2 class="f16 jbxx">
										<i>01</i>企业基本信息
									</h2>
									<ul class="list-unstyled list-flow">
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 企业名称：</span>
											<div class="input-append">
											    <input type="hidden" name="id" value="${id }" />
												<input class="span3" id="name" name="name" value="${is.name }" type="text">
												<div class="red">${isa.nameReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 企业类别：</span>
											<div class="input-append">
												<input class="span3" id="supplierType" name="supplierType" value="${is.supplierType }"  type="text">
												<div class="red">${isa.supplierTypeReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 中文译名：</span>
											<div class="input-append">
												<input class="span3" id="chinesrName" name="chinesrName" value="${is.chinesrName }" type="text">
												<div class="red">${isa.chinesrNameReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 法定代表人：</span>
											<div class="input-append">
												<input class="span3" id="legalName" name="legalName" value="${is.legalName }" type="text">
												<div class="red">${isa.legalNameReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0"><span class=""><i class="red">＊</i>企业注册地址：</span>
											<div class="input-append">
												<input class="span3" id="address" name="address" value="${is.address }" type="text">
												<div class="red">${isa.addressReason }</div>
											</div>
											<%-- <div class="fl">
												<div class="input-append mr18">
												    <input id="address" name="address" value="${is.address }" type="hidden">
													<input class="span4" id="address" name="address" value="${is.address }" type="text">
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
												<input class="span3" id="postCode" name="postCode" value="${is.postCode }"  type="text">
												<div class="red">${isa.postCodeReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>经营产品大类：</span>
											<div class="input-append">
												<input class="span3" id="productType" name="productType" value="${is.productType }" type="text">
												<div class="red">${isa.productTypeReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>主营产品：</span>
											<div class="input-append">
												<input class="span3" id="majorProduct" name="majorProduct" value="${is.majorProduct }" type="text">
												<div class="red">${isa.majorpRoductReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>兼营产品：</span>
											<div class="input-append">
												<input class="span3" id="byproduct" name="byproduct" value="${is.byproduct }" type="text">
												<div class="red">${isa.byproductReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>生产商名称：</span>
											<div class="input-append">
												<input class="span3" id="producerName" name="producerName" value="${is.producerName }"  type="text">
												<div class="red">${isa.producerNameReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 联系人：</span>
											<div class="input-append">
												<input class="span3" id="contactPerson" name="contactPerson" value="${is.contactPerson }" type="text">
												<div class="red">${isa.contactPersonReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 电话：</span>
											<div class="input-append">
												<input class="span3" id="telephone" name="telephone" value="${is.telephone }" type="text">
												<div class="red">${isa.telephoneReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 传真：</span>
											<div class="input-append">
												<input class="span3" id="fax" name="fax" value="${is.fax }" type="text">
												<div class="red">${isa.faxReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 电子邮件：</span>
											<div class="input-append">
												<input class="span3" id="email" name="email" value="${is.email }" type="text">
												<div class="red">${isa.emailReason }</div>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 企业网址：</span>
											<div class="input-append">
												<input class="span3" id="website" name="website" value="${is.website }" type="text">
												<div class="red">${isa.websiteReason }</div>
											</div>
										</li>
										<li class="col-md-12 p0 mt10"><span class="fl"><i class="red">＊</i>国内供货业绩：</span>
											<div class="col-md-9 mt5">
												<div class="row">
													<textarea class="text_area col-md-12" id="civilAchievement" name="civilAchievement"  title="不超过800个字" placeholder=""> ${is.civilAchievement }</textarea>
													<div class="red">${isa.civilAchievementReason }</div>
												</div>
											</div>
											<div class="clear"></div>
										</li>
										<li class="col-md-12 p0 mt10"><span class="fl"><i class="red">＊</i>企业简介：</span>
											<div class="col-md-9 mt5">
												<div class="row">
													<textarea class="text_area col-md-12" id="remark" name="remark" title="不超过800个字" placeholder="">${is.remark }</textarea>
													<div class="red">${isa.remarkReason }</div>
												</div>
											</div>
											<div class="clear"></div>
										</li>
									</ul>
									</div>
									<div class="tc mt20 clear col-md-11">
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
			 <input type="hidden" id="orgId" name="orgId" value="${orgId }" />
			<table id="tb1"  class="table table-bordered table-condensed tc">
			  <thead>
				<tr>
					<td class="w50">选择</td>
					<td class="w50">序号</td>
					<td>采购机构名称</td>
					<td>机构代称</td>
					<td>是否可审核</td>
					<td>所在城市</td>
				</tr>
			  </thead>
			  <tbody>
			    <c:forEach items="${findPurchaseDepList }" var="fpd" varStatus="vs">
					<tr>
						<td><input value="${fpd.id }" type="checkbox" name="cbox" onclick="box(this)" /></td>
						<td>${vs.index+1}</td>
						<td>${fpd.depName }</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach> 
			  </tbody>
			</table>
			 <h2 class="f16 jbxx">
				其他采购机构
			</h2>
			<table id="tb2" class="table table-bordered table-condensed tc">
			  <thead>
				<tr>
					<td class="w50">选择</td>
					<td class="w50">序号</td>
					<td>采购机构名称</td>
					<td>机构代称</td>
					<td>是否可审核</td>
					<td>所在城市</td>
				</tr>
			  </thead>
			  <tbody>
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
			  </tbody>
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
							<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">打印申请表</a></li>
							<li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">打印承诺书</a></li>
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
											<button class="btn btn-windows git"   type="button" onclick="location='${pageContext.request.contextPath}/importSupplier/dayin.html'">打印</button>
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
