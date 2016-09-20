<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>供应商专业信息</title>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<script type="text/javascript">

	/** 打开物资证书 */
	function openCertPro() {
		
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
				<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
				<span class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
				<span class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
			 	<span class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">专业信息</span> </span>
			 	<span class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">品目信息</span></span> 
			 	<span class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_02">产品信息</span> </span>
			 	<span class="new_step fl"><i class="">7</i><div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span>
			 	<span class="new_step fl"><i class="">8</i><div class="line"></div> <span class="step_desc_02">打印申请表</span> </span>
			 	<span class="new_step fl"><i class="">9</i> <span class="step_desc_01">申请表承诺书上传</span> </span>
				<div class="clear"></div>
			</h2>
		</div>

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgdd">
							<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">物资-生产型专业信息</a></li>
							<li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">物资-销售型专业信息</a></li>
							<li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="fujian f18">生产专业信息</a></li>
							<li class=""><a aria-expanded="false" href="#tab-4" data-toggle="tab" class="fujian f18">服务专业信息</a></li>
						</ul>
						<form action="" method="post">
							<input type="hidden" name="id" value="${supplierId}" />
							<input type="hidden" name="sign" value="4" />
							<div class="tab-content padding-top-20">
								<!-- 物资生产型专业信息 -->
								<div class="tab-pane fade active in height-450" id="tab-1">
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>供应商组织机构和人员
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 组织机构：</span>
												<div class="input-append">
													<input class="span3" id="supplierName_input_id" type="text" name="supplierName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 人员总数：</span>
												<div class="input-append">
													<input class="span3" type="text" name="website" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 管理人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="website" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>技术人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>工人：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>


										<h2 class="f16 jbxx mt40">
											<i>02</i>产品研发能力
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>技术人员比例(%)：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>高级技术人员比例：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>研发部门名称：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>研发部门人数：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>研发部门负责人：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>国家军队科研项目：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>国家军队科技奖项：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>

										<h2 class="f16 jbxx mt40">
											<i>03</i>供应商生产能力
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 生产线名称数量：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legalName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 生产设备名称数量：</span>
												<div class="input-append">
													<input class="span3" type="text" name="legaIdCard" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>04</i>物资生产型供应商质量检测能力登记
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 质量检测部门：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 质量部门人数：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactFax" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 质监部门负责人：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactTelephone" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 质量检测设备名产：</span>
												<div class="input-append">
													<input class="span3" type="text" name="contactMobile" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>05</i>供应商资质证书
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openCertPro()">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"/></th>
													<th class="info">资质证书名称</th>
													<th class="info">资质等级</th>
													<th class="info">发证机关</th>
													<th class="info">有效期（起止时间）</th>
													<th class="info">是否年检</th>
													<th class="info">附件</th>
												</tr>
											</thead>
										</table>
										<h2 class="f16 jbxx mt40">
											<i>06</i>可提供品目类别
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"/></th>
													<th class="info">品目类别</th>
													<th class="info">大类名称</th>
													<th class="info">中类名称</th>
													<th class="info">小类名称</th>
													<th class="info">品种名称</th>
												</tr>
											</thead>
										</table>
										
									</div>
								</div>
								
								<!-- 物资销售型专业信息 -->
								<div class="tab-pane fade height-450" id="tab-2">
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>供应商组织机构和人员
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 组织机构：</span>
												<div class="input-append">
													<input class="span3" id="supplierName_input_id" type="text" name="supplierName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 人员总数：</span>
												<div class="input-append">
													<input class="span3" type="text" name="website" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 管理人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="website" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>技术人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>工人（职员）：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>02</i>供应商资质证书
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"/></th>
													<th class="info">资质证书名称</th>
													<th class="info">资质等级</th>
													<th class="info">发证机关</th>
													<th class="info">有效期（起止时间）</th>
													<th class="info">是否年检</th>
													<th class="info">附件</th>
												</tr>
											</thead>
										</table>
										<h2 class="f16 jbxx mt40">
											<i>03</i>可提供品目类别
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"/></th>
													<th class="info">品目类别</th>
													<th class="info">大类名称</th>
													<th class="info">中类名称</th>
													<th class="info">小类名称</th>
													<th class="info">品种名称</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
								
								<!-- 生产专业信息 -->
								<div class="tab-pane fade height-200" id="tab-3">
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>供应商组织机构和人员
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 组织机构：</span>
												<div class="input-append">
													<input class="span3" id="supplierName_input_id" type="text" name="supplierName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 技术负责人：</span>
												<div class="input-append">
													<input class="span3" type="text" name="website" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 中级以上职称人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="website" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>现场管理人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>技术和工人：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>02</i>供应商注册人员登记
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"/></th>
													<th class="info">注册名称</th>
													<th class="info">注册人数</th>
												</tr>
											</thead>
										</table>
										<h2 class="f16 jbxx mt40">
											<i>03</i>可提供品目类别
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"/></th>
													<th class="info">品目类别</th>
													<th class="info">大类名称</th>
													<th class="info">中类名称</th>
													<th class="info">小类名称</th>
													<th class="info">品种名称</th>
												</tr>
											</thead>
										</table>
										<h2 class="f16 jbxx mt40">
											<i>04</i>供应商资质资格证书信息
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"/></th>
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
										</table>
										<h2 class="f16 jbxx mt40">
											<i>05</i>供应商资质资格信息
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"/></th>
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
										</table>
									</div>
								</div>
								
								<!-- 服务专业信息 -->
								<div class="tab-pane fade height-200" id="tab-4">
									<div class=" margin-bottom-0">
										<h2 class="f16 jbxx">
											<i>01</i>供应商组织机构和人员
										</h2>
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 组织机构：</span>
												<div class="input-append">
													<input class="span3" id="supplierName_input_id" type="text" name="supplierName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 人员总数：</span>
												<div class="input-append">
													<input class="span3" type="text" name="website" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i> 管理人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="website" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>技术人员：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankName" />
												</div>
											</li>
											<li class="col-md-6 p0"><span class=""><i class="red">＊</i>工人（职员）：</span>
												<div class="input-append">
													<input class="span3" type="text" name="bankAccount" />
												</div>
											</li>
											<div class="clear"></div>
										</ul>
										<h2 class="f16 jbxx mt40">
											<i>02</i>供应商资质证书
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"/></th>
													<th class="info">资质证书名称</th>
													<th class="info">资质等级</th>
													<th class="info">发证机关</th>
													<th class="info">有效期（起止时间）</th>
													<th class="info">是否年检</th>
													<th class="info">附件</th>
												</tr>
											</thead>
										</table>
										<h2 class="f16 jbxx mt40">
											<i>03</i>可提供品目类别
										</h2>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">删除</button>
										<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr">新增</button>
										<table class="table table-bordered table-condensed">
											<thead>
												<tr>
													<th class="info"><input type="checkbox"/></th>
													<th class="info">品目类别</th>
													<th class="info">大类名称</th>
													<th class="info">中类名称</th>
													<th class="info">小类名称</th>
													<th class="info">品种名称</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
							<div class="mt40 tc mb50">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5">上一步</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5">暂存</button>
								<button type="submit" class="btn padding-left-20 padding-right-20 btn_back margin-5">下一步</button>
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
