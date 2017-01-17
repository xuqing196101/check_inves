<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">
	/** 保存基本信息 */
	function saveTemplate(flag) {
		if (flag == "commit") {
			$.ajax({
				url: "${pageContext.request.contextPath}/supplier/isCommit.do",
				data: {"id" : "${currSupplier.id}"},
				async: false,
				success: function(response){
					if (response != 1) {
						$("input[name='jsp']").val(flag);
						if (flag == "commit") {
							layer.confirm('您已成功提交,请等待审核结果!', {
								btn : [ '确定' ],
								shade: false //不显示遮罩
							//按钮
							}, function() {
								$("#template_upload_form_id").submit();
							});	
						}
					} else {
						layer.msg("还有附件未上传!",{offset: ['300px', '750px']});
					}
				}
			});
		} else {
			$("input[name='jsp']").val(flag);
			$("#template_upload_form_id").submit();
		}
	}
	
	function uploadNew(id) {
		$("#" + id).find("div").remove();
		var name = "";
		if (id == "level_li_id") {
			name = "supplierLevelFile";
		} else if (id == "pledge_li_id") {
			name = "supplierPledgeFile";
		} else if (id == "reglist_li_id") {
			name = "supplierRegListFile";
		} else if (id == "extracts_li_id") {
			name = "supplierExtractsListFile";
		} else if (id == "inspectlist_li_id") {
			name = "supplierInspectListFile";
		} else if (id == "reviewlist_li_id") {
			name = "supplierReviewListFile";
		} else if (id == "changelist_li_id") {
			name = "supplierChangeListFile";
		} else if (id == "exitlist_li_id") {
			name = "supplierExitListFile";
		}
		var html = "<div class='input-append'>";
		html += "<div class='uploader orange h32 m0 fz8'>";
		html += "<input type='text' class='filename fz8 h32' readonly='readonly'/>";
		html += "<input type='button' name='file' class='button' value='选择...'/>";
		html += "<input name='"+ name +"' type='file' size='30'/>";
		html += "</div>";
		html += "</div>";
		$("#" + id).append(html);
		loadFilePlug();
	}
	
	function downloadFile(fileName) {
		$("input[name='fileName']").val(fileName);
		$("#download_form_id").submit();
	}
</script>

</head>

<body>
	<div class="wrapper">
		<!-- 项目戳开始 -->
		<c:if test="${currSupplier.status != 7}">
			<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span class="new_step current fl"><i class="">1</i>
<!-- 						<div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> <span class="new_step current fl"><i class="">2</i> -->
						<div class="line"></div> <span class="step_desc_01">基本信息</span> </span> <span class="new_step current fl"><i class="">2</i>
						<div class="line"></div> <span class="step_desc_02">供应商类型</span> </span> <span class="new_step current fl"><i class="">3</i>
						<div class="line"></div> <span class="step_desc_01">品目信息</span> </span> <span class="new_step current fl"><i class="">4</i>
						<div class="line"></div> <span class="step_desc_02">资质文件维护</span> </span> <span class="new_step current fl"><i class=""> 5</i>
						<div class="line"></div> <span class="step_desc_01">品目合同上传</span> </span> <span class="new_step current fl"><i class="">6</i>
						<div class="line"></div> <span class="step_desc_02">初审采购机构</span> </span> <span class="new_step current fl"><i class="">7</i>
						<div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> <span class="new_step current fl"><i class="">8</i> 
						<span class="step_desc_02">申请表承诺书上传</span> 
					</span>
					<div class="clear"></div>
				</h2>
			</div>
		</c:if>

		<!--基本信息-->
		<div class="container content">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="template_upload_form_id" action="${pageContext.request.contextPath}/supplier/perfect_upload.html" method="post">
							<input name="id" value="${currSupplier.id}" type="hidden" /> 
							<input name="jsp" type="hidden" />
								<input name="flag" type="hidden" />
							<input name="status" type="hidden" value="${currSupplier.status}" />
							<input name="supplierTypeIds" value="${supplierTypeIds }"  type="hidden" /> 
							
							<div class="tab-content padding-top-20">
								<!-- 物资生产型 -->
								<div class="tab-pane fade active in " id="tab-1">
									<div class="margin-bottom-0  categories">
										<div class="headline-v2">
			  								<h2>上传供应商申请表、承诺书  (将第七步下载的申请表、承诺书签字盖章后,扫描为彩色图片上传)</h2>
										</div>
										
											<li id="bill_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
												   <span class="col-md-4 col-sm-12 col-xs-12 padding-left-5 mt15"><i class="red">*</i>供应商申请表上传</span> 
												   <div class="col-md-8 col-sm-12 col-xs-12 p0">
													   <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="promise_up"  groups="promise_up,application_up" multiple="true" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierRegList}" auto="true" /> 
													   <u:show showId="promise_show"  groups="promise_show,application_show"  businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierRegList}" />
												   </div>
												   
												     <div class="cue"> ${err_geglist } </div>
											</li>
				
											<li id="bill_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
												   <span class="col-md-4 col-sm-12 col-xs-12 padding-left-5 mt15"><i class="red">*</i>供应商承诺书上传</span> 
												   <div class="col-md-8 col-sm-12 col-xs-12 p0">
													   <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="application_up" groups="promise_up,application_up" multiple="true"  businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierPledge}" auto="true" /> 
													   <u:show showId="application_show" groups="promise_show,application_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierPledge}" />
												   </div>
												   
												     <div class="cue"> ${ err_pledge} </div>
											</li>
											
				
				
								<!-- 		<ul class="list-unstyled list-flow">
											<li id="level_li_id" class="col-md-6 p0"><span class="w245"><i class="red">＊</i> 军队供应商分级方法：</span>
												 --><%--<c:if test="${currSupplier.supplierLevel != null}">
													<div>
														<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${currSupplier.supplierLevel}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('level_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.supplierLevel == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="supplierLevelFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>--%>
									<%-- 			<up:upload id="aa" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
												<up:show showId="bb" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${typeId}"/>
											</li> --%>
											<%--<li id="pledge_li_id" class="col-md-6 p0"><span class="w245"><i class="red">＊</i>军队供应商承诺书：</span>
												<c:if test="${currSupplier.supplierPledge != null}">
													<div>
														<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${currSupplier.supplierPledge}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('pledge_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.supplierPledge == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="supplierPledgeFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<li id="reglist_li_id" class="col-md-6 p0"><span class="w245"><i class="red">＊</i>军队供应商入库申请表：</span>
												<c:if test="${currSupplier.supplierRegList != null}">
													<div>
														<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${currSupplier.supplierRegList}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('reglist_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.supplierRegList == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="supplierRegListFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<li id="extracts_li_id" class="col-md-6 p0"><span class="w245">军队供应商抽取记录表：</span>
												<c:if test="${currSupplier.supplierExtractsList != null}">
													<div>
														<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${currSupplier.supplierExtractsList}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('extracts_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.supplierExtractsList == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="supplierExtractsListFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<li id="inspectlist_li_id" class="col-md-6 p0"><span class="w245">军队供应商实地考察记录表：</span>
												<c:if test="${currSupplier.supplierInspectList != null}">
													<div>
														<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${currSupplier.supplierInspectList}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('inspectlist_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.supplierInspectList == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="supplierInspectListFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<li id="reviewlist_li_id" class="col-md-6 p0"><span class="w245">军队供应商实地考察廉政意见函：</span>
												<c:if test="${currSupplier.supplierReviewList != null}">
													<div>
														<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${currSupplier.supplierReviewList}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('reviewlist_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.supplierReviewList == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="supplierReviewListFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<li id="changelist_li_id" class="col-md-6 p0"><span class="w245">军队供应商注册信息变更申请表：</span>
												<c:if test="${currSupplier.supplierChangeList != null}">
													<div>
														<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${currSupplier.supplierChangeList}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('changelist_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.supplierChangeList == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="supplierChangeListFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>
											<li id="exitlist_li_id" class="col-md-6 p0"><span class="w245">军队供应商退库申请表：</span>
												<c:if test="${currSupplier.supplierExitList != null}">
													<div>
														<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${currSupplier.supplierExitList}')">下载附件</a>
														<a title="重新上传" class="ml10 red fz17" href="javascript:void(0)" onclick="uploadNew('exitlist_li_id')">☓</a>
													</div>
												</c:if>
												<c:if test="${currSupplier.supplierExitList == null}">
													<div class="input-append">
														<div class="uploader orange h32 m0 fz8">
															<input type="text" class="filename h32 fz8" readonly="readonly"/>
															<input type="button" name="file" class="button" value="选择..."/>
															<input name="supplierExitListFile" type="file" size="30"/>
														</div>
													</div>
												</c:if>
											</li>--%>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	 	<div class="mt40 tc mb50">
				 <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveTemplate('prev')">上一步</button>
				 <button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveTemplate('commit')">提交审核</button>
		 </div>
							
							
	<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplier/download.html" method="post">
		<input type="hidden" name="fileName" />
	</form>
	
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}">
		<jsp:include page="/index_bottom.jsp" />
	</c:if>
</body>
</html>
