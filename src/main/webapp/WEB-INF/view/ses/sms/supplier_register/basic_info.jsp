<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商完善基本信息</title>
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
	$(function() {
		document.getElementById("supplierName_input_id").focus();// 获取焦点

		var id = "${supplierId}";
		if (id) {
			layer.alert("恭喜您, 注册成功 ! 请完善相关信息 !", {
				offset : '200px'
			});
		}
		
		$(".ready_write_input").val(currYear);
	});
	
	/** 下拉框的内容写到 inpput 中 */
	function checkText(ele, id) {
		$("#" + id).val($(ele).text());
	}
	
	/** 动态获取年份 */
	var date = new Date();
	var currYear = date.getFullYear();
	var sinceYear = 2010;
	
	/** 创建财务信息表 */
	var count1 = "${financeSize}";
	function createFinance() {
		if (!count1) {
			count1 = 1;
		}
		var tr = "";
		tr += "<tr id='tr_id_"+ count1 + "'>";
		tr += "<td class='tc'><input type='checkbox' id='checkbox_input_id_" + count1 +"'><input type='hidden' name='listSupplierFinances[" + count1 + "].supplierId' value='${supplierId}' /></td>";
		tr += "<td class='tc'>";
		tr += "<input id='curr_select_id_" + count1 + "' class='maxw100 mt10' type='text' readonly='readonly' onClick=WdatePicker({dateFmt:'yyyy'}) name='listSupplierFinances[" + count1 + "].year' onselect='changeYear(this)' /> ";
		tr += "</td>";
		tr += "<td class='tc'><input name='listSupplierFinances[" + count1 + "].name' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc'><input name='listSupplierFinances[" + count1 + "].telephone' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc'><input name='listSupplierFinances[" + count1 + "].auditors' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc'><input name='listSupplierFinances[" + count1 + "].quota' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc'><input name='listSupplierFinances[" + count1 + "].totalAssets' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc'><input name='listSupplierFinances[" + count1 + "].totalLiabilities' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc'><input name='listSupplierFinances[" + count1 + "].totalNetAssets' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc'><input name='listSupplierFinances[" + count1 + "].taking' class='maxw100 mt10' type='text'></td>";
		tr += "</tr>";
		$("#tbody_id_1").append(tr);

		var html = "";
		var upload = "";
		upload += "<div class='uploader orange m0'>";
		upload += "<input type='text' class='filename h32 m0 fz11' readonly='readonly' value='未选择任何文件...'/>";
		upload += "<input type='button' class='button' value='选择文件...'/>";
		upload += "<input type='file' size='30' accept='image/*'/>";
		upload += "</div>";

		html += "<h5 id='year_h_id_"+ count1 +"'>未选择年份</h5>";
		html += "<ul class='list-unstyled list-flow' id='ul_id_"+ count1 + "'>";
		html += "<li class='col-md-6 p0'><span class='zzzx'><i class='red'>＊</i> 财务审计报告的审计意见：</span>";
		html += "<div class='input-append'>" + upload + "</div></li>";
		html += "<li class='col-md-6 p0'><span class='zzzx'><i class='red'>＊</i> 资产负债表：</span>";
		html += "<div class='input-append'>" + upload + "</div></li>";
		html += "<li class='col-md-6 p0'><span class='zzzx'><i class='red'>＊</i> 利润表：</span>";
		html += "<div class='input-append'>" + upload + "</div></li>";
		html += "<li class='col-md-6 p0'><span class='zzzx'><i class='red'>＊</i> 现金流量表：</span>";
		html += "<div class='input-append'>" + upload + "</div></li>";
		html += "<li class='col-md-6 p0'><span class='zzzx'><i class='red'>＊</i> 所有者权益变动表：</span>";
		html += "<div class='input-append'>" + upload + "</div></li>";
		html += "<div class='clear'></div>";
		html += "</ul>";
		$("#list_div_id").append(html);
		count1++;
	}
	
	/** 动态展示年份 */
	function changeYear(ele) {
		var value = $(ele).val();
		var id = $(ele).attr("id");
		var arr = id.split("_");
		var index = arr[arr.length - 1];
		$("#year_h_id_" + index).text(value + "年");
	}
	
	/** 移除财务信息表 */
	function removeFinance() {
		$("#tbody_id_1").find("input:checked").each(function(index) {
			var id = $(this).attr("id");
			if (id) {
				var arr = id.split("_");
				var index = arr[arr.length - 1];
				$("tr").remove("#tr_id_" + index);
				$("h5").remove("#year_h_id_" + index);
				$("ul").remove("#ul_id_" + index);
			}
		});
	}
	
	/** 全选 */
	function checkAll(ele, id) {
		var checked = $(ele).prop("checked");
		$("#" + id).find("input:checkbox").each(function(index) {
			$(this).prop("checked", checked);
		});
	}
	
	/** 创建股东信息表 */
	var count2 = "${stockholderSize}";
	function createShare() {
		if (!count2) {
			count2 = 1;
		}
		var tr = "";
		tr += "<tr id='share_tr_id_" + count2 + "'>";
		tr += "<td class='tc'><input type='checkbox' id='checkbox_input_id_" + count2 + "'><input type='hidden' name='listSupplierStockholders[" + count2 + "].supplierId' value='${supplierId}' /></td>";
		tr += "<td class='tc'><input name='listSupplierStockholders[" + count2 + "].name' class='maxw150 mt10' type='text'></td>";
		tr += "<td class='tc'><input name='listSupplierStockholders[" + count2 + "].nature' class='maxw150 mt10' type='text'></td>";
		tr += "<td class='tc'><input name='listSupplierStockholders[" + count2 + "].identity' class='maxw150 mt10' type='text'></td>";
		tr += "<td class='tc'><input name='listSupplierStockholders[" + count2 + "].shares' class='maxw150 mt10' type='text'></td>";
		tr += "<td class='tc'><input name='listSupplierStockholders[" + count2 + "].proportion' class='maxw150 mt10' type='text'></td>";
		tr += "</tr>";
		$("#tbody_id_2").append(tr);
		count2 ++;
	}
	
	/** 移除股东信息表 */
	function removeShare() {
		$("#tbody_id_2").find("input:checked").each(function(index) {
			var id = $(this).attr("id");
			if (id) {
				var arr = id.split("_");
				var index = arr[arr.length - 1];
				$("tr").remove("#share_tr_id_" + index);
			}
		});
	}
	
	/** 保存基本信息 */
	function saveBasicInfo(sign) {
		var action = "${pageContext.request.contextPath}/supplier/";
		if (sign) {
			action += "nextStep.html";
		} else {
			action += "stashStep.html";
		}
		$("#basic_info_form_id").attr("action", action);
		$("#basic_info_form_id").submit();
		
	}
	
</script>

</head>

<body>
	<div class="wrapper">
		<!-- header -->
		<jsp:include page="../../../../../index_head.jsp"></jsp:include>

		<!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
			<h2 class="padding-20 mt40 ml30">
				<span class="new_step current fl"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">专业信息</span> </span> <span class="new_step fl"><i class="">5</i>
					<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step fl"><i class="">6</i>
					<div class="line"></div> <span class="step_desc_02">产品信息</span> </span> <span class="new_step fl"><i class="">7</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">8</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">9</i>
					<span class="step_desc_01">申请表承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
		</div>

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgdd">
							<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">详细信息</a></li>
							<li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">财务信息</a></li>
							<li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="fujian f18">股东信息</a></li>
						</ul>
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
								
								<!-- 财务信息 -->
								<div class="tab-pane fade height-450" id="tab-2">
									<div class="margin-bottom-0  categories">

										<h2 class="f16 jbxx mt40">
											<i>01</i>财务状况登记表
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="removeFinance()">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="createFinance()">新增</button>
										<table id="finance_table_id" class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input id="finance_checkbox_id" type="checkbox" onchange="checkAll(this, 'tbody_id_1')" />
													</th>
													<th class="info">年份</th>
													<th class="info">会计实务所名称</th>
													<th class="info">事务所联系电话</th>
													<th class="info">审计人姓名</th>
													<th class="info">指标</th>
													<th class="info">资产总额</th>
													<th class="info">负债总额</th>
													<th class="info">净资产总额</th>
													<th class="info">营业收入</th>
												</tr>
											</thead>
											<tbody id="tbody_id_1">
												<c:if test="${currObject.listSupplierFinances != null}">
													<c:forEach items="${currObject.listSupplierFinances}" var="finances" varStatus="vs">
														<tr id="tr_id_${vs.index}">
															<td class="tc">
																<input type="checkbox" id="checkbox_input_id_${vs.index}" />
																<input type="hidden" name="listSupplierFinances[${vs.index}].supplierId" value="${supplierId}" />
															</td>
															<td class="tc">
																<input id="curr_select_id_${vs.index}" class="maxw100 mt10" type="text" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy'})" name="listSupplierFinances[${vs.index}].year" value="${finances.year}" onselect="changeYear(this)" /> 
															</td>
															<td class="tc"><input style="border: 0" name="listSupplierFinances[${vs.index}].name" class="maxw100 mt10" type="text" value="${finances.name}"></td>
															<td class="tc"><input name="listSupplierFinances[${vs.index}].telephone" class="maxw100 mt10" type="text" value="${finances.telephone}"></td>
															<td class="tc"><input name="listSupplierFinances[${vs.index}].auditors" class="maxw100 mt10" type="text" value="${finances.auditors}"></td>
															<td class="tc"><input name="listSupplierFinances[${vs.index}].quota" class="maxw100 mt10" type="text" value="${finances.quota}"></td>
															<td class="tc"><input name="listSupplierFinances[${vs.index}].totalAssets" class="maxw100 mt10" type="text" value="${finances.totalAssets}"></td>
															<td class="tc"><input name="listSupplierFinances[${vs.index}].totalLiabilities" class="maxw100 mt10" type="text" value="${finances.totalLiabilities}"></td>
															<td class="tc"><input name="listSupplierFinances[${vs.index}].totalNetAssets" class="maxw100 mt10" type="text" value="${finances.totalNetAssets}"></td>
															<td class="tc"><input name="listSupplierFinances[${vs.index}].taking" class="maxw100 mt10" type="text" value="${finances.taking}"></td>
														</tr>
													</c:forEach>
												</c:if>
												<c:if test="${currObject.listSupplierFinances == null}">
													<tr id="tr_id_0">
														<td class="tc">
															<input type="checkbox" id="checkbox_input_id_0" />
															<input type="hidden" name="listSupplierFinances[0].supplierId" value="${supplierId}" />
														</td>
														<td class="tc">
															<input id="curr_select_id_0" class="maxw100 mt10" type="text" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy'})" name="listSupplierFinances[0].year" onselect="changeYear(this)" /> 
														</td>
														<td class="tc"><input name="listSupplierFinances[0].name" class="maxw100 mt10" type="text"></td>
														<td class="tc"><input name="listSupplierFinances[0].telephone" class="maxw100 mt10" type="text"></td>
														<td class="tc"><input name="listSupplierFinances[0].auditors" class="maxw100 mt10" type="text"></td>
														<td class="tc"><input name="listSupplierFinances[0].quota" class="maxw100 mt10" type="text"></td>
														<td class="tc"><input name="listSupplierFinances[0].totalAssets" class="maxw100 mt10" type="text"></td>
														<td class="tc"><input name="listSupplierFinances[0].totalLiabilities" class="maxw100 mt10" type="text"></td>
														<td class="tc"><input name="listSupplierFinances[0].totalNetAssets" class="maxw100 mt10" type="text"></td>
														<td class="tc"><input name="listSupplierFinances[0].taking" class="maxw100 mt10" type="text"></td>
													</tr>
												</c:if>
											</tbody>
										</table>
										<div id="list_div_id">
											<c:if test="${currObject.listSupplierFinances != null}">
												<c:forEach items="${currObject.listSupplierFinances}" var="finances" varStatus="vs">
													<h5 id="year_h_id_${vs.index}">${finances.year}年</h5>
													<ul class="list-unstyled list-flow" id="ul_id_${vs.index}">
														<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i> 财务审计报告的审计意见：</span>
															<div class="input-append">
																<div class="uploader orange m0">
																	<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
																	<input type="button" class="button" value="选择文件..." /> 
																	<input type="file" size="30" accept="image/*" />
																</div>
															</div>
														</li>
														<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>资产负债表：</span>
															<div class="input-append">
																<div class="uploader orange m0">
																	<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
																	<input type="button" class="button" value="选择文件..." /> 
																	<input type="file" size="30" accept="image/*" />
																</div>
															</div>
														</li>
														<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>利润表：</span>
															<div class="input-append">
																<div class="uploader orange m0">
																	<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
																	<input type="button" class="button" value="选择文件..." /> 
																	<input type="file" size="30" accept="image/*" />
																</div>
															</div>
														</li>
														<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>现金流量表：</span>
															<div class="input-append">
																<div class="uploader orange m0">
																	<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
																	<input type="button" class="button" value="选择文件..." /> 
																	<input type="file" size="30" accept="image/*" />
																</div>
															</div>
														</li>
														<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>所有者权益变动表：</span>
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
												</c:forEach>
											</c:if>
											
											<c:if test="${currObject.listSupplierFinances == null}">
												<h5 id="year_h_id_0" class="ready_write_h5">未选择年份</h5>
												<ul class="list-unstyled list-flow" id="ul_id_0">
													<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i> 财务审计报告的审计意见：</span>
														<div class="input-append">
															<div class="uploader orange m0">
																<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
																<input type="button" class="button" value="选择文件..." /> 
																<input type="file" size="30" accept="image/*" />
															</div>
														</div>
													</li>
													<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>资产负债表：</span>
														<div class="input-append">
															<div class="uploader orange m0">
																<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
																<input type="button" class="button" value="选择文件..." /> 
																<input type="file" size="30" accept="image/*" />
															</div>
														</div>
													</li>
													<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>利润表：</span>
														<div class="input-append">
															<div class="uploader orange m0">
																<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
																<input type="button" class="button" value="选择文件..." /> 
																<input type="file" size="30" accept="image/*" />
															</div>
														</div>
													</li>
													<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>现金流量表：</span>
														<div class="input-append">
															<div class="uploader orange m0">
																<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
																<input type="button" class="button" value="选择文件..." /> 
																<input type="file" size="30" accept="image/*" />
															</div>
														</div>
													</li>
													<li class="col-md-6 p0"><span class="zzzx"><i class="red">＊</i>所有者权益变动表：</span>
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
											</c:if>
										</div>
										
									</div>
								</div>
								
								<!-- 股东信息 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<div class="margin-bottom-0  categories">

										<h2 class="f16 jbxx mt40">
											<i>01</i>股东信息表
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="removeShare()">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="createShare()">新增</button>
										<table id="share_table_id" class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input id="share_checkbox_id" type="checkbox" onchange="checkAll(this, 'tbody_id_2')" /></th>
													<th class="info">出资人名称或姓名</th>
													<th class="info">出资人性质</th>
													<th class="info">统一社会信用代码或身份证号码</th>
													<th class="info">出资金额或股份（万元/万份）</th>
													<th class="info">比例</th>
												</tr>
											</thead>
											<tbody id="tbody_id_2">
												<c:if test="${currObject.listSupplierStockholders != null}">
													<c:forEach items="${currObject.listSupplierStockholders}" var="stockholder" varStatus="vs">
														<tr id="share_tr_id_${vs.index}">
															<td class="tc">
																<input type="checkbox" id="share_input_id_${vs.index}">
																<input type="hidden" name="listSupplierStockholders[${vs.index}].supplierId" value="${supplierId}">
															</td>
															<td class="tc"><input name="listSupplierStockholders[${vs.index}].name" class="maxw150 mt10" type="text" value="${stockholder.name}"></td>
															<td class="tc"><input name="listSupplierStockholders[${vs.index}].nature" class="maxw150 mt10" type="text" value="${stockholder.nature}"></td>
															<td class="tc"><input name="listSupplierStockholders[${vs.index}].identity" class="maxw150 mt10" type="text" value="${stockholder.identity}"></td>
															<td class="tc"><input name="listSupplierStockholders[${vs.index}].shares" class="maxw150 mt10" type="text" value="${stockholder.shares}"></td>
															<td class="tc"><input name="listSupplierStockholders[${vs.index}].proportion" class="maxw150 mt10" type="text" value="${stockholder.proportion}"></td>
														</tr>
													</c:forEach>
												</c:if>
												<c:if test="${currObject.listSupplierStockholders == null}">
													<tr id="share_tr_id_0">
														<td class="tc">
															<input type="checkbox" id="share_input_id_0">
															<input type="hidden" name="listSupplierStockholders[0].supplierId" value="${supplierId}">
														</td>
														<td class="tc"><input name="listSupplierStockholders[0].name" class="maxw150 mt10" type="text"></td>
														<td class="tc"><input name="listSupplierStockholders[0].nature" class="maxw150 mt10" type="text"></td>
														<td class="tc"><input name="listSupplierStockholders[0].identity" class="maxw150 mt10" type="text"></td>
														<td class="tc"><input name="listSupplierStockholders[0].stockholder" class="maxw150 mt10" type="text"></td>
														<td class="tc"><input name="listSupplierStockholders[0].proportion" class="maxw150 mt10" type="text"></td>
													</tr>
												</c:if>
											</tbody>
										</table>
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
