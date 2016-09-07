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
				<span class="new_step current fl ml170"><i class="">1</i>
					<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
					<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
					<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step fl"><i class="">4</i>
					<div class="line"></div> <span class="step_desc_02">打印申请表和承诺书</span> </span> <span class="new_step fl"><i class="">5</i><span class="step_desc_01">申请表和承诺书上传</span> </span>
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
											<div class="mt40 tc mb50">
											<button class="btn padding-left-20 padding-right-20 btn_back margin-5"   type="button" onclick="location='${pageContext.request.contextPath}/supplierFsInfo/registerStep6.html'">下一步</button>
											</div>
								</div>
								<div class="tab-pane fade height-450" id="tab-2">
									<div class="margin-bottom-0  categories">
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
