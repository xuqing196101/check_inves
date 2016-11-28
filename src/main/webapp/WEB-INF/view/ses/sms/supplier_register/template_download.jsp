<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/front.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
	/** 保存基本信息 */
	function otherPage(flag) {
		$("input[name='flag']").val(flag);
		$("#template_download_form_id").submit();
	}
</script>

</head>

<body>
	<c:if test="${currSupplier.status != 7}">
		<%@ include file="/index_head.jsp"%>
	</c:if>
	<div class="wrapper">

		<!-- 项目戳开始 -->
		<c:if test="${currSupplier.status != 7}">
			<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
						<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_02">基本信息</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">供应商类型</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step current fl"><i class="">5</i>
						<div class="line"></div> <span class="step_desc_01">初审采购机构</span> </span> <span class="new_step current fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_02">打印申请表</span> </span> <span class="new_step fl"><i class="">7</i> 
						<span class="step_desc_01">申请表承诺书上传</span> 
					</span>
					<div class="clear"></div>
				</h2>
			</div>
		</c:if>

		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="template_download_form_id" action="${pageContext.request.contextPath}/supplier/perfect_download.html" method="post">
							<input name="id" value="${currSupplier.id}" type="hidden" /> 
							<input name="jsp" type="hidden" />
							<input name="flag" type="hidden" />
							<div class="tab-content padding-top-20">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class="margin-bottom-0  categories">
										<h2 class="f16 jbxx mt40">
											<i>01</i>申请表和承诺书下载
										</h2>
										<table class="table table-bordered">
										  <tr>
										    <td class="info">军队供应商分级方法：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										    <td class="info">军队供应商承诺书：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										  </tr>
										  <tr>
										    <td class="info">军队供应商入库申请表：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										    <td class="info">军队供应商抽取记录表：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										  </tr>
										  <tr>
										    <td class="info">军队供应商实地考察记录表：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										    <td class="info">军队供应商实地考察廉政意见函：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										  </tr>
										  <tr>
										    <td class="info">军队供应商注册信息变更申请表：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										    <td class="info">军队供应商退库申请表：</td>
										    <td><a class="mt3 color7171C6" href="javascript:void(0)"><i class="download mr5"></i>下载附件</a></td>
										  </tr>
										
										</table>
										<%--
										<ul class="list-unstyled list-flow">
											<li class="col-md-6 p0"><span class="zzzx w245"> 军队供应商分级方法：</span>
												<div class="input-append">
													<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商承诺书：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商入库申请表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商抽取记录表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商实地考察记录表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商实地考察廉政意见函：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商注册信息变更申请表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<li class="col-md-6 p0"><span class="zzzx w245">军队供应商退库申请表：</span>
												<div class="input-append">
													<div class="input-append">
														<a class="mt3 color7171C6" href="javascript:void(0)"><i class="download"></i>下载附件</a>
													</div>
												</div>
											</li>
											<div class="clear"></div>
										</ul>
									--%></div>
								</div>
							</div>
						
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	  <div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
			  <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="otherPage('prve')">上一步</button>
			  <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="otherPage('next')">下一步</button>
	  	  </div>
	  </div>
	  
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}">
		<jsp:include page="/index_bottom.jsp" />
	</c:if>
</body>
</html>
