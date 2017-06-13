<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<title>供应商注册</title>
		<style type="text/css">
.current {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	/** 保存基本信息 */
	function saveTemplate(flag) {
		var count=0;
		if (flag == "commit") {
			$.ajax({
				url: "${pageContext.request.contextPath}/supplier/isCommit.do",
				data: {"id" : "${currSupplier.id}"},
				async: false,
				dataType:"json",
				success: function(response){
                    var token = "";
                    if(!response instanceof Object){//如果不是JSON对象
                        response.split(",");
                    }
					if (response == "1") {
                        layer.msg("还有附件未上传!", {offset: ['300px', '750px']});
                    }else if(response == "0"){
                        layer.msg("数据异常!", {offset: ['300px', '750px']});
                    }else if(token[0]=="supplier_logout"){
                        layer.confirm("您未在 "+token[1]+" 天内提交审核,注册信息已失效", {
                            btn: ['确定'],
                            shade: false //不显示遮罩
                            //按钮
                        }, function() {
                            window.location.href = '${pageContext.request.contextPath}/';
                        });
					} else {
                        $("input[name='jsp']").val(flag);
                        if (flag == "commit") {
                            layer.confirm("<span style='margin-left:26px;'> 您已成功提交,请等待审核结果！</span>" + "<br/><span style='margin-left:26px;'> 您选择的采购机构：" + response.shortName + "；联系人姓名：" + response.supplierContact + "；" + "联系方式：" + response.supplierPhone + "；联系地址：" + response.supplierAddress + "；邮编：" + response.supplierPostcode, {
                                btn: ['确定'],
                                shade: false
                            }, function () {
                                count++;
                                if (count == 1) {
                                    $("#template_upload_form_id").submit();
                                }


                            });
                        }
					}
				}
			});
		} else {
			$("input[name='jsp']").val(flag);
			$("#template_upload_form_id").submit();
		}
	}
	
	function updateStep(step){
		var supplierId = "${currSupplier.id}";
		location.href = "${pageContext.request.contextPath}/supplier/updateStep.html?step=" + step + "&supplierId=" + supplierId;
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
		sessionStorage.locationH=true;
		sessionStorage.index=8;
		
		
			//退回理由
			function errorMsg(auditField) {
				var supplierId = "${currSupplier.id}";
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/audit.html",
					data: {
						"supplierId": supplierId,
						"auditField": auditField,
						"auditType": "download_page"
					},
					dataType: "json",
					success: function(data) {
						/* alert(data.suggest); */
						layer.msg("不通过理由：" + data.suggest, {
							offset: '300px'
						});
					}
				});
			}
</script>

</head>

<body>
	<div class="wrapper">
		<div class="container clear margin-top-30">
			<h2 class="step_flow">
				<span id="sp1" class="new_step current fl" onclick="updateStep('1')"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
	            <span id="sp2" class="new_step current fl" onclick="updateStep('2')"><i class="">2</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
	            <span id="ty3" class="new_step current fl" onclick="updateStep('3')"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
	            <span id="sp4" class="new_step current fl" onclick="updateStep('4')"><i class="">4</i><div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
	            <span id="sp5" class="new_step current fl" onclick="updateStep('5')"><i class="">5</i><div class="line"></div> <span class="step_desc_02">销售合同</span> </span>
	            <span id="sp6" class="new_step current fl" onclick="updateStep('6')"><i class="">6</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
	            <span id="sp7" class="new_step current fl" onclick="updateStep('7')"><i class="">7</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
	            <span id="sp8" class="new_step current fl new_step_last"><i class="">8</i> <span class="step_desc_01">提交审核</span> </span>
	            <div class="clear"></div>
			</h2>
		</div>
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
							
							<div class="tab-content padding-top-20 w100p fl">
								<div class="tab-pane fade active in " id="tab-1">
									<div class="margin-bottom-0  categories col-md-12 col-sm-12 col-xs-12 p0 over_auto">
										<div class="headline-v2">
			  								<h2>上传供应商申请表、承诺书  (将第七步下载的申请表、承诺书签字盖章后,扫描为彩色图片上传)</h2>
										</div>
										<table class="table table-bordered">
									   	   <tr>
									   	     <td class="bggrey" width="15%"><i class="red">*</i>供应商申请表：</td>
									   	     <td <c:if test="${fn:contains(audit,'supplierRegList')}">style="border: 1px solid red;" onmouseover="errorMsg('supplierRegList')"</c:if>>
									   	     	<div class="w200 fl">
									   	     		<c:choose>
									   	     			<c:when test="${!fn:contains(audit,'supplierRegList') && currSupplier.status==2}">
											   		 			<u:show showId="promise_show" delete="false" groups="promise_show,application_show"  businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierRegList}" />
									   	     			</c:when>
									   	     			<c:otherwise>
									   	     				<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="promise_up"  groups="promise_up,application_up" multiple="true" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierRegList}" auto="true" /> 
											   		 			<u:show showId="promise_show"  groups="promise_show,application_show"  businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierRegList}" />
									   	     			</c:otherwise>
									   	     		</c:choose>
										        </div>
										     </td>
									   	     <td class="bggrey" width="15%" ><i class="red">*</i>供应商承诺书：</td>
									   	     <td <c:if test="${fn:contains(audit,'supplierPledge')}">style="border: 1px solid red;" onmouseover="errorMsg('supplierPledge')"</c:if>>
									   	       <div class="w200 fl">
									   	     	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="application_up" groups="promise_up,application_up" maxcount="1"  businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierPledge}" auto="true" /> 
											  	<u:show showId="application_show" groups="promise_show,application_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierPledge}" />
									   	       </div>
									   	     </td>
									   	   </tr>
										 </table>
										<div class="clear"></div>
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
	
	<div class="footer_margin">
   		<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
    </div>
</body>
</html>
