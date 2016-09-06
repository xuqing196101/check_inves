<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


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

		var supplierId = "${supplierId}";
		if (supplierId) {
			layer.alert("恭喜您, 注册成功 ! 请完善相关信息 !", {
				offset : '200px'
			});
		}

		$("#curr_select_id_0").append(getOptios());

	});

	function checkText(ele, id) {
		$("#" + id).val($(ele).text());
	}

	function getOptios() {
		var html = "";
		var date = new Date();
		var currYear = date.getFullYear();
		var sinceYear = 2010;
		var optios = "";
		for ( var i = sinceYear; i <= currYear; i++) {
			if (i == currYear) {
				html += "<option value='" + i +"' selected='selected'>" + i + "</option>";
			} else {
				html += "<option value='" + i +"'>" + i + "</option>";
			}
		}
		return html;
	}
	var count = 1;
	function create() {
		var options = getOptios();
		var tr = "";
		tr += "<tr id='tr_id_"+ count + "'>";
		tr += "<td class='tc'><input type='checkbox' id='checkbox_input_id_" + count +"'></td>";
		tr += "<td class='tc'>";
		tr += "<select id='curr_select_id_" + count +"' onchange='changeYear(this)'>";
		tr += options;
		tr += "</select>";
		tr += "</td>";
		tr += "<td class='tc'><input id='appendedInput' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc mt10'><input id='appendedInput' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc mt10'><input id='appendedInput' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc mt10'><input id='appendedInput' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc mt10'><input id='appendedInput' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc mt10'><input id='appendedInput' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc mt10'><input id='appendedInput' class='maxw100 mt10' type='text'></td>";
		tr += "<td class='tc mt10'><input id='appendedInput' class='maxw100 mt10' type='text'></td>";
		tr += "</tr>";
		$("#tbody_id").append(tr);
		
		var html = "";
		var upload = "";
		upload += "<div class='uploader orange m0'>";
		upload += "<input type='text' class='filename h32 m0 fz11' readonly='readonly' value='未选择任何文件...'/>";
		upload += "<input type='button' name='file' class='button' value='选择文件...'/>";
		upload += "<input id='regIdentity' type='file' name='regIdentity' size='30' accept='image/*'/>";
		upload += "</div>";
		
		html += "<h5 id='year_h_id_"+ count +"'>2016年</h5>";
		html += "<ul class='list-unstyled list-flow' id='ul_id_"+ count + "'>";
		html += "<li class='col-md-6 p0 '><span class='zzzx'><i class='red'>＊</i> 财务审计报告的审计意见：</span>";
		html += "<div class='input-append'>"+ upload +"</div></li>";
		html += "<li class='col-md-6 p0 '><span class='zzzx'><i class='red'>＊</i> 资产负债表：</span>";
		html += "<div class='input-append'>"+ upload +"</div></li>";
		html += "<li class='col-md-6 p0 '><span class='zzzx'><i class='red'>＊</i> 利润表：</span>";
		html += "<div class='input-append'>"+ upload +"</div></li>";
		html += "<li class='col-md-6 p0 '><span class='zzzx'><i class='red'>＊</i> 现金流量表：</span>";
		html += "<div class='input-append'>"+ upload +"</div></li>";
		html += "<li class='col-md-6 p0 '><span class='zzzx'><i class='red'>＊</i> 所有者权益变动表：</span>";
		html += "<div class='input-append'>"+ upload +"</div></li>";
		html += "<div class='clear'></div>";
		html += "</ul>";
		$("#list_div_id").append(html);
		count ++;
	}
	function changeYear(ele) {
		var value = $(ele).val();
		var id = $(ele).attr("id");
		var arr = id.split("_");
		var index = arr[arr.length -1];
		$("#year_h_id_" + index).text(value + "年");
	}
	
	function removeEle() {
		$("input:checked").each(function(index) {
			var id = $(this).attr("id");
			if (id) {
				var arr = id.split("_");
				var index = arr[arr.length -1];
				$("tr").remove("#tr_id_" + index);
				$("h5").remove("#year_h_id_" + index);
				$("ul").remove("#ul_id_" + index);
			}
		});
	}
	function checkAll(ele) {
		var checked = $(ele).prop("checked");
		$("input:checkbox").each(function(index) {
			$(this).prop("checked", checked);
		});
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
							<li class="active dropdown tongzhi_li"><a class=" dropdown-toggle p0_30" href=""><i class="tongzhi nav_icon"></i>通知</a>
							</li>
							<!-- End 通知 -->

							<!-- 公告 -->
							<li class="dropdown gonggao_li"><a class=" dropdown-toggle p0_30" href=""><i class="gonggao nav_icon"></i>公告</a>
							</li>
							<!-- End 公告 -->

							<!-- 公示 -->
							<li class="dropdown gongshi_li"><a data-toggle="dropdown" class="dropdown-toggle p0_30 " href=""><i class="gongshi nav_icon"></i>公示</a>
							</li>
							<!-- End 公示 -->

							<!-- 专家 -->
							<li class="dropdown zhuanjia_li"><a href="#" class="dropdown-toggle p0_30 " data-toggle="dropdown"><i class="zhuanjia nav_icon"></i>专家</a>
							</li>
							<!-- End 专家 -->

							<!-- 投诉 -->
							<li class="dropdown tousu_li"><a data-toggle="dropdown" class="dropdown-toggle p0_30" href=""><i class="tousu nav_icon"></i>投诉</a>
							</li>
							<!-- End 投诉 -->

							<!-- 法规 -->
							<li class="dropdown  fagui_li"><a href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="fagui nav_icon"></i>法规</a>
							</li>
							<!-- End 法规 -->

							<li class="dropdown luntan_li"><a aria-expanded="false" href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="luntan nav_icon"></i>论坛</a>
							</li>

						</ul>
					</div>
				</div>

				<!--/end container-->
			</div>
		</div>



		<!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
			<h2 class="padding-20 mt40">
				<span class="new_step current fl"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">专业信息</span> </span> <span class="new_step fl"><i class="">5</i>
					<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step fl"><i class="">6</i>
					<div class="line"></div> <span class="step_desc_02">产品信息</span> </span> <span class="new_step fl"><i class="">7</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">8</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">9</i><span class="step_desc_01">申请表承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
		</div>


		<!--详情开始-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgdd">
							<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">详细信息</a></li>
							<li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">财务信息</a></li>
						</ul>
						<form action="">
							<div class="tab-content padding-top-20">
								<div class="tab-pane fade active in height-450" id="tab-1">
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>基本信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 供应商名称：</span>
												<div class="input-append">
													<input class="span3" id="supplierName_input_id" type="text" name="supplierName">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 公司网址：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6  p0 "><span class=""><i class="red">＊</i>成立日期：</span>
												<div class="input-append">
													<input class="span2" id="appendedInput" type="text" readonly="readonly" onClick="WdatePicker()"> <span class="add-on"> <img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>营业执照登记类型：</span>
												<div class="input-append">
													<input class="span2" id="businessType_input_id" name="businessType" type="text" readonly="readonly">
													<div class="btn-group">
														<button class="btn dropdown-toggle add-on" data-toggle="dropdown">
															<img src="${pageContext.request.contextPath}/public/ZHQ/images/down.png" class="margin-bottom-5" />
														</button>
														<ul class="dropdown-menu list-unstyled tc">
															<li class="hand" onclick="checkText(this, 'businessType_input_id')">国有企业</li>
															<li class="hand" onclick="checkText(this, 'businessType_input_id')">外资企业</li>
															<li class="hand" onclick="checkText(this, 'businessType_input_id')">民营企业</li>
															<li class="hand" onclick="checkText(this, 'businessType_input_id')">股份制企业</li>
															<li class="hand" onclick="checkText(this, 'businessType_input_id')">私营企业</li>
														</ul>
													</div>
												</div>
											</li>

											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>公司地址：</span>
												<div class="fl">
													<div class="input-append mr18">
														<input class="span4" id="address_input_id1" type="text" readonly="readonly">
														<div class="btn-group">
															<button class="btn dropdown-toggle add-on" data-toggle="dropdown">
																<img src="${pageContext.request.contextPath}/public/ZHQ/images/down.png" class="margin-bottom-5" />
															</button>
															<ul class="dropdown-menu list-unstyled tc">
																<li class="hand" onclick="checkText(this, 'address_input_id1')">河北</li>
															</ul>
														</div>
													</div>
													<div class="input-append">
														<input class="span4" id="address_input_id2" type="text" readonly="readonly">
														<div class="btn-group">
															<button class="btn dropdown-toggle add-on" data-toggle="dropdown">
																<img src="${pageContext.request.contextPath}/public/ZHQ/images/down.png" class="margin-bottom-5" />
															</button>
															<ul class="dropdown-menu list-unstyled tc">
																<li class="hand" onclick="checkText(this, 'address_input_id2')">保定</li>
															</ul>
														</div>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>开户行名称：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>

											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>开户行账号：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>

											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 邮编：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<div class="clear"></div>
										</ul>


										<h2 class="f16 jbxx mt40">
											<i>02</i>资质资信
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0 "><span class="zzzx"><i class="red">＊</i> 近三个月完税凭证：</span>
												<div class="input-append">
													<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
														<input type="button" name="file" class="button" value="选择文件..."/>
														<input id="regIdentity" type="file" name="regIdentity" size="30" accept="image/*"/>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0 "><span class="zzzx"><i class="red">＊</i>近三年银行基本账户年末对账单：</span>
												<div class="input-append">
													<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
														<input type="button" name="file" class="button" value="选择文件..."/>
														<input id="regIdentity" type="file" name="regIdentity" size="30" accept="image/*"/>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0 "><span class="zzzx"><i class="red">＊</i>近三个月缴纳社会保险金凭证：</span>
												<div class="input-append">
													<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
														<input type="button" name="file" class="button" value="选择文件..."/>
														<input id="regIdentity" type="file" name="regIdentity" size="30" accept="image/*"/>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0 "><span class="zzzx"><i class="red">＊</i>近三年内无重大违法记录声明：</span>
												<div class="input-append">
													<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
														<input type="button" name="file" class="button" value="选择文件..."/>
														<input id="regIdentity" type="file" name="regIdentity" size="30" accept="image/*"/>
													</div>
												</div>
											</li>
											<div class="clear"></div>
										</ul>

										<h2 class="f16 jbxx mt40">
											<i>03</i>法定代表人信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 姓名：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 身份证号：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 固定电话：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 手机：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>04</i>联系人信息
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 姓名：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 传真电话：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 固定电话：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 手机：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 邮箱：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 地址：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>05</i>营业执照
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 统一社会信用代码：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 登记机关：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 注册资本：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 境外分支结构：</span>
												<div class="input-append">
													<input class="span2" id="overseasBranch_input_id" name="overseasBranch" type="text" readonly="readonly">
													<div class="btn-group">
														<button class="btn dropdown-toggle add-on" data-toggle="dropdown">
															<img src="${pageContext.request.contextPath}/public/ZHQ/images/down.png" class="margin-bottom-5" />
														</button>
														<ul class="dropdown-menu list-unstyled tc">
															<li class="hand" onclick="checkText(this, 'overseasBranch_input_id')">是</li>
															<li class="hand" onclick="checkText(this, 'overseasBranch_input_id')">否</li>
														</ul>
													</div>
												</div>
											</li>
											<li class="col-md-6  p0 "><span class=""><i class="red">＊</i>营业开始时间：</span>
												<div class="input-append">
													<input class="span2" id="appendedInput" type="text" readonly="readonly" onClick="WdatePicker()"> <span class="add-on"> <img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div>
											</li>
											<li class="col-md-6  p0 "><span class=""><i class="red">＊</i>营业结束时间：</span>
												<div class="input-append">
													<input class="span2" id="appendedInput" type="text" readonly="readonly" onClick="WdatePicker()"> <span class="add-on"> <img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 经营范围：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 邮编：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 国家：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 详细地址：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 机构名称：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 生产经营范围：</span>
												<div class="input-append">
													<input class="span3" id="appendedInput" type="text">
												</div>
											</li>
											<li class="col-md-12 p0 mt10"><span class="fl"><i class="red">＊</i>公司简介：</span>
												<div class="col-md-9 mt5">
													<div class="row">
														<textarea class="text_area col-md-12 " title="不超过800个字" placeholder=""></textarea>
													</div>
												</div>
												<div class="clear"></div>
											</li>
											<div class="clear"></div>
										</ul>
										<div class="mt40 tc mb50">
											<button class="btn padding-left-20 padding-right-20 btn_back margin-5">下一步</button>
											<button class="btn padding-left-20 padding-right-20 btn_back margin-5">暂存</button>
											<button class="btn padding-left-20 padding-right-20 btn_back margin-5">返回</button>
										</div>
									</div>
								</div>
								<div class="tab-pane fade height-450" id="tab-2">
									<div class="margin-bottom-0  categories">

										<h2 class="f16 jbxx mt40">
											<i>01</i>财务状况登记表
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="removeEle()">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="create()">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox" onchange="checkAll(this)" /></th>
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
											<tbody id="tbody_id">
												<tr id="tr_id_0">
													<td class="tc"><input type="checkbox" id="checkbox_input_id_0">
													</td>
													<td class="tc"><select id="curr_select_id_0" onchange="changeYear(this)">
													</select></td>
													<td class="tc"><input id="appendedInput" class="maxw100 mt10" type="text">
													</td>
													<td class="tc mt10"><input id="appendedInput" class="maxw100 mt10" type="text">
													</td>
													<td class="tc mt10"><input id="appendedInput" class="maxw100 mt10" type="text">
													</td>
													<td class="tc mt10"><input id="appendedInput" class="maxw100 mt10" type="text">
													</td>
													<td class="tc mt10"><input id="appendedInput" class="maxw100 mt10" type="text">
													</td>
													<td class="tc mt10"><input id="appendedInput" class="maxw100 mt10" type="text">
													</td>
													<td class="tc mt10"><input id="appendedInput" class="maxw100 mt10" type="text">
													</td>
													<td class="tc mt10"><input id="appendedInput" class="maxw100 mt10" type="text">
													</td>
												</tr>

											</tbody>
										</table>
										<div id="list_div_id">
											<h5 id="year_h_id_0">2016年</h5>
											<ul class="list-unstyled list-flow" id="ul_id_0">
												<li class="col-md-6 p0 "><span class="zzzx"><i class="red">＊</i> 财务审计报告的审计意见：</span>
													<div class="input-append">
														<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
														<input type="button" name="file" class="button" value="选择文件..."/>
														<input id="regIdentity" type="file" name="regIdentity" size="30" accept="image/*"/>
													</div>
													</div>
												</li>
												<li class="col-md-6 p0 "><span class="zzzx"><i class="red">＊</i>资产负债表：</span>
													<div class="input-append">
														<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
														<input type="button" name="file" class="button" value="选择文件..."/>
														<input id="regIdentity" type="file" name="regIdentity" size="30" accept="image/*"/>
													</div>
													</div>
												</li>
												<li class="col-md-6 p0 "><span class="zzzx"><i class="red">＊</i>利润表：</span>
													<div class="input-append">
														<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
														<input type="button" name="file" class="button" value="选择文件..."/>
														<input id="regIdentity" type="file" name="regIdentity" size="30" accept="image/*"/>
													</div>
													</div>
												</li>
												<li class="col-md-6 p0 "><span class="zzzx"><i class="red">＊</i>现金流量表：</span>
													<div class="input-append">
														<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
														<input type="button" name="file" class="button" value="选择文件..."/>
														<input id="regIdentity" type="file" name="regIdentity" size="30" accept="image/*"/>
													</div>
													</div>
												</li>
												<li class="col-md-6 p0 "><span class="zzzx"><i class="red">＊</i>所有者权益变动表：</span>
													<div class="input-append">
														<div class="uploader orange m0">
														<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..."/>
														<input type="button" name="file" class="button" value="选择文件..."/>
														<input id="regIdentity" type="file" name="regIdentity" size="30" accept="image/*"/>
													</div>
													</div>
												</li>
												<div class="clear"></div>
											</ul>
										</div>
										<div class="mt40 tc mb50">
											<button class="btn padding-left-20 padding-right-20 btn_back margin-5">下一步s</button>
											<button class="btn padding-left-20 padding-right-20 btn_back margin-5">暂存</button>
											<button class="btn padding-left-20 padding-right-20 btn_back margin-5">返回</button>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--底部代码开始-->
	<div class="footer-v2" id="footer-v2">

		<div class="footer">

			<!-- Address -->
			<address class="">Copyright &#38;#169 2016 版权所有：中央军委后勤保障部 京ICP备09055519号</address>
			<div class="">浏览本网主页，建议将电脑显示屏的分辨率调为1024*768</div>
			<!-- End Address -->

			<!--/footer-->
		</div>
	</div>
</body>
</html>
