<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
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
			function saveItems() {
				$("input[name='flag']").val("file");
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/temporarySave.do",
					type: "post",
					data: $("#items_info_form_id").serializeArray(),
					contextType: "application/x-www-form-urlencoded",
					success: function(msg) {
						$.ajax({
							url: "${pageContext.request.contextPath}/supplier/saveItemsInfo.do",
							type: "post",
							data: $("#item_form").serializeArray(),
							success: function(){
								if(msg == 'ok') {
									layer.msg('暂存成功');
								}
								if(msg == 'failed') {
									layer.msg('暂存失败');
								}
							}
						});
					}
				});
			}
			
			// 无提示暂存
			function tempSave() {
				$("input[name='flag']").val("file");
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/temporarySave.do",
					type: "post",
					data: $("#items_info_form_id").serializeArray(),
					contextType: "application/x-www-form-urlencoded",
					success: function(msg) {
						$.ajax({
							url: "${pageContext.request.contextPath}/supplier/saveItemsInfo.do",
							type: "post",
							data: $("#item_form").serializeArray(),
						});
					}
				});
			}

			function next() {
				var supplierId = "${currSupplier.id}";
				var supplierTypeIds=$("#supplierTypeIds").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier_item/isaAtitude.do",
					data: {"supplierId": supplierId, "supplierTypeIds": supplierTypeIds},
					dataType: "json",
					success: function(msg){
					 	if(msg=="0"){
					 		layer.alert("资质文件没有上传完毕，请继续上传！");
					 	}else{
					 		tempSave();
							$("input[name='flag']").val("2");
							sessionStorage.formE=JSON.stringify($("#items_info_form_id").serializeArray());
							var flag=isAptitue();
							if(flag==true){
								$("#items_info_form_id").submit();
							}else{
								layer.alert("请完善工程资质证书信息！");
							}
							
					 	}
					}
				});
			
			}

			function prev() {
				tempSave();
				$("input[name='flag']").val("1");
				$("#items_info_form_id").submit();
			}

			/*获取内层div的最大高度赋予外层div*/
			function psize() {
				var temp_heights = []
				$(".fades").each(function() {
					temp_heights.push($(this).outerHeight());
				})
				$("#tab_content_div_id").outerHeight(Math.max.apply(null, temp_heights));
			}
			
			//显示不通过的理由
			function errorMsg(auditField, auditType){
				var supplierId = "${currSupplier.id}";
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier/audit.html",
					data: {"supplierId": supplierId, "auditField": auditField, "auditType": auditType},
					dataType: "json",
					success: function(data){
					layer.msg("不通过理由：" + data.suggest , {offset: '200px'});
					}
				});
			}
			
			// 根据证书编号获取附件信息
			function getFileByCode(obj, number, flag){
				var supplierId = $("#supplierId").val();
				var certCode = "";
				var professType="";
				if (flag == "1") {
					certCode = $(obj).parent().next().find("input").val();
					// 清空等级和附件
					$(obj).parent().next().find("input[type='text']").val("");
					$(obj).parent().next().next().find("input[type='text']").val("");
					 
					$(obj).parent().next().next().next().find("input[type='text']").val("");
				    $(obj).parent().next().next().next().find("input[type='hidden']").val("");
				    
					 
					$(obj).parent().next().next().next().next().html("");
					professType=$(obj).parent().next().next().children().val();
				} else if(flag=="2"){
					certCode = $(obj).val();
					typeId = $(obj).parent().prev().find("select").val();
					// 清空等级和附件
					///$(obj).parent().next().find("input[type='text']").val("");
						$(obj).parent().next().children().empty();
					//清空资质等级
					$(obj).parent().next().next().find("input[type='text']").val("");
				    $(obj).parent().next().next().find("input[type='hidden']").val("");
					
					$.ajax({
						url : "${pageContext.request.contextPath}/supplier/getProType.do",
						type:"post",
						data: {
							"typeId": typeId,
							"certCode": certCode,
							"supplierId": supplierId
						},
						dataType: "json",
						success: function(data){
							var select=$(obj).parent().next().children();
							var html="<option value=''>请选择</option>";
							$(data).each(function(i){
								html+="<option value="+data[i]+">"+data[i]+"</option>";
							});
							$(select).append(html);
						}
					});
					$(obj).parent().next().next().next().html("");
					// professType=$(obj).parent().next().children().val();
				}else{
					 $(obj).parent().next().find("input[type='text']").val("");
					 $(obj).parent().next().find("input[type='hidden']").val("");
					 $(obj).parent().next().next().html("");
					certCode = $(obj).parent().prev().children().val();
					professType=$(obj).val();
				}
			
				var typeId = "";
				if (flag == "1") {
					typeId = $(obj).val();
				} else if(flag=="2") {
					typeId = $(obj).parent().prev().find("select").val();
				}
				else if(flag=="3") {
					typeId = $(obj).parent().prev().prev().find("select").val();
				}
				if (typeId != null && typeId != "" && typeId != "undefined" && certCode != null && certCode != "" && certCode != "undefined"&&professType!=null&&professType!="") {
					$.ajax({
						url : "${pageContext.request.contextPath}/supplier/getLevel.do",
						type:"post",
						data: {
							"typeId": typeId,
							"certCode": certCode,
							"supplierId": supplierId,
							"professType":professType
						},
						dataType: "json",
						success: function(result){
							if (result != null && result != "") {
								if (flag == "1") {
									$(obj).parent().next().next().find("input[type='text']").val(result.name);
									$(obj).parent().next().next().find("input[type='hidden']").val(result.id);
								} else {
									$(obj).parent().next().find("input[type='text']").val(result.name);
									$(obj).parent().next().find("input[type='hidden']").val(result.id);
								}
								// 通过append将附件信息追加到指定位置
								$.ajax({
									url : "${pageContext.request.contextPath}/supplier/getFileByCode.do",
									type:"post",
									async : false,
									dataType : "html",
									data : {
										"typeId": typeId,
										"certCode" : certCode,
										"supplierId" : supplierId,
										"number" : number,
										"professType":professType
									},
									success : function(data) {
										if (flag == "1") {
											$(obj).parent().next().next().next().next().html(data);
										} else {
											$(obj).parent().next().next().html(data);
										}
										init_web_upload();
									}
								});
							}
						}
					});
				}
				tempSave();
			}
			
			function isAptitue(){
				var flag=true;
				$("input[type='text']").each(function() {
							if ($(this).val() == "") {
								flag = false;
							}
						});
				return flag;
				
			}
			
			// 控制其它等级的显示和影藏
			function disLevel(obj){
				if ($(obj).val() == "其它") {
					$(obj).next().removeClass("dis_none");
				} else {
					$(obj).next().addClass("dis_none");
				}
				tempSave();
			}
			function updateStep(step){
				var supplierId = "${currSupplier.id}";
				location.href = "${pageContext.request.contextPath}/supplier/updateStep.html?step=" + step + "&supplierId=" + supplierId;
			}
			$(function(){
				var cateList = "${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT') and fn:length(cateList) > 0}";
				var saleQua = "${fn:contains(currSupplier.supplierTypeIds, 'SALES') and fn:length(saleQua) > 0}";
				var projectQua = "${fn:contains(currSupplier.supplierTypeIds, 'PROJECT') and fn:length(allTreeList) > 0}";
				var serviceQua = "${fn:contains(currSupplier.supplierTypeIds, 'SERVICE') and fn:length(serviceQua) > 0}";
				if (cateList == "false" && saleQua == "false" && projectQua == "false" && serviceQua == "false") {
					layer.alert("没有需要上传的资质文件，请直接点击下一步！");
				}
			});
			
				sessionStorage.locationD=true;
				sessionStorage.index=4;
		</script>

	</head>

	<body onload="psize()">
		<div class="wrapper">
			<div class="container clear margin-top-30">
				<h2 class="padding-20 mt40 ml30">
					<span id="sp1" class="new_step current fl" onclick="updateStep('1')"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span>
		            <span id="sp2" class="new_step current fl" onclick="updateStep('2')"><i class="">2</i><div class="line"></div> <span class="step_desc_01">供应商类型</span> </span>
		            <span id="ty3" class="new_step current fl" onclick="updateStep('3')"><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
		            <span id="sp4" class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">资质文件维护</span> </span>
		            <span id="sp5" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">销售合同</span> </span>
		            <span id="sp6" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span>
		            <span id="sp7" class="new_step fl"><i class="">7</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span>
		            <span id="sp8" class="new_step fl"><i class="">8</i> <span class="step_desc_01">提交审核</span> </span>
		            <div class="clear"></div>
				</h2>
			</div>
			<!--基本信息-->
			<div class="container content ">
				<div class="row magazine-page">
					<div class="col-md-12 tab-v2 job-content">
						<div class="padding-top-10">
							<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
								<c:set value="0" var="liCount" />
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT') and fn:length(cateList) > 0}">
									<c:set value="${liCount+1}" var="liCount" />
									<li id="li_id_1" class="active">
										<a aria-expanded="true" onmouseup="init_web_upload_in('#tab-1')" href="#tab-1" data-toggle="tab" class="f18">物资-生产型资质信息</a>
									</li>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES') and fn:length(saleQua) > 0}">
									<li id="li_id_2" class='<c:if test="${liCount == 0}">active</c:if>'>
										<a aria-expanded="false" onmouseup="init_web_upload_in('#tab-2')" href="#tab-2" data-toggle="tab" class="f18">物资-销售型资质信息</a>
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
									<li id="li_id_3" class='<c:if test="${liCount == 0}">active</c:if>'>
										<a aria-expanded="false" onmouseup="init_web_upload_in('#tab-3')" href="#tab-3" data-toggle="tab" class="f18">工程资质信息</a>
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE') and fn:length(serviceQua) > 0}">
									<li id="li_id_4" class='<c:if test="${liCount == 0}">active</c:if>'>
										<a aria-expanded="false" onmouseup="init_web_upload_in('#tab-4')" href="#tab-4" data-toggle="tab" class="f18">服务资质信息</a>
									</li>
									<c:set value="${liCount+1}" var="liCount" />
								</c:if>
							</ul>
							<div class="tab-content padding-top-20 pr border0" id="tab_content_div_id">
								<c:set value="0" var="divCount" />
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT') and fn:length(cateList) > 0}">
									<!-- 物资生产型 -->
									<c:set value="0" var="prolength" />
									<div class="fades active" id="tab-1">
										<h2 class="f16  ">
										      	<font color="red">*</font> 上传物资-生产型资质文件
										</h2>
										<table class="table table-bordered">
											<c:forEach items="${cateList }" var="obj">
												<tr>
													<td width="18%">${obj.categoryName }</td>
													<td>
														<c:forEach items="${obj.list }" var="quaPro">
															<c:set value="${prolength+1}" var="prolength"></c:set>
															<div class="mr5 fl" <c:if test="${fn:contains(audit,quaPro.flag)}">style="border: 1px solid red;" onmouseover="errorMsg('${quaPro.flag}','aptitude_page')"</c:if>>
																<u:upload singleFileSize="300" exts="${properties['file.picture.type']}" id="pUp${prolength}" multiple="true" buttonName="${quaPro.name}" groups="${saleUp}" businessId="${quaPro.flag}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
																<div class="clear"></div>
																<u:show showId="pShow${prolength}" groups="${saleShow}" businessId="${quaPro.flag}" sysKey="${sysKey}" typeId="${typeId}" />
															</div>
														</c:forEach>
													</td>
												</tr>
											</c:forEach>
										</table>
										<c:set value="${divCount+1}" var="divCount" />
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES') and fn:length(saleQua) > 0}">
									<!-- 物资销售型 -->
									<c:set value="0" var="length"> </c:set>
									<div class="tab-pane <c:if test="${divCount==0 }">active in</c:if>fade height-300" id="tab-2">
										<h2 class="f16  ">
										      	<font color="red">*</font> 上传物资-销售型资质文件
										</h2>
										<table class="table table-bordered">
											<c:forEach items="${saleQua }" var="sale">
												<tr>
													<td class="w200">${sale.categoryName } </td>
													<td>

														<c:forEach items="${sale.list }" var="saua">
															<c:set value="${length+1}" var="length"></c:set>
															<div class="mr5 fl" <c:if test="${fn:contains(audit,saua.flag)}">style="border: 1px solid red;" onmouseover="errorMsg('${saua.flag}','aptitude_page')"</c:if>>
																<u:upload singleFileSize="300" exts="${properties['file.picture.type']}" id="saleUp${length}" multiple="true" buttonName="${saua.name}" groups="${saleUp}" businessId="${saua.flag}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
																<div class="clear"></div>
																<u:show showId="saleShow${length}" groups="${saleShow}" businessId="${saua.flag}" sysKey="${sysKey}" typeId="${typeId}" />
															</div>
														</c:forEach>
													</td>
												</tr>
											</c:forEach>
										</table>
										<c:set value="${divCount+1}" var="divCount" />
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}">
									<div class="tab-pane <c:if test="${divCount==0 }">active in</c:if> fade height-300" id="tab-3">
										<h2 class="f16  ">
										      	<font color="red">*</font> 上传工程资质文件
										</h2>
										<form id="item_form" method="post" class="col-md-12 col-xs-12 col-sm-12 over_auto p0">
										  <table class="table table-bordered table_input">
											<thead>
												<tr>
											      <th class="info tc w50">序号</th>
											      <%--<th class="info tc w50">类别</th>
											      <th class="info tc">大类</th>
											      <th class="info tc">中类</th>
											      <th class="info tc">小类</th>--%>
											      <th class="info tc">产品类别</th>
											      <th class="info tc w200">资质类型</th>
											      <th class="info tc w100">证书编号</th>
											       <th class="info tc w100">专业类别</th>
											      <th class="info tc w80">资质等级</th>
											      <th class="info tc">证书图片</th>
										   		</tr>
										    </thead>
										    <c:forEach items="${allTreeList}" var="cate" varStatus="vs">
										      <tr <c:if test="${fn:contains(audit,cate.itemsId)}">onmouseover="errorMsg('${cate.itemsId}','aptitude_page')"</c:if>>
										        <td class="tc" <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
										        <div class="w50"> ${vs.index + 1}</div>
										          <input type="hidden" name="listSupplierItems[${vs.index}].id" value="${cate.itemsId}">
										        </td>
										        <%--<td class="tc" <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
										        	<div class="w80 lh30"> ${cate.rootNode}</div>
										        </td>
										        <td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
										            <div class="w150 lh30">${cate.firstNode}</div>
										        </td>
										        <td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
										            <div class="w200 lh30">${cate.secondNode}</div>
										        </td>
										        <td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
										            <div class="w200 lh30">${cate.thirdNode}</div>
										        </td>--%>
										        <td class="tc" <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
										            <div class="w200 lh30">
                                                        <c:choose>
                                                            <c:when test="${cate.fourthNode!=null}">
                                                                ${cate.fourthNode}
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:choose>
                                                                    <c:when test="${cate.thirdNode!=null}">
                                                                        ${cate.thirdNode}
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:choose>
                                                                            <c:when test="${cate.secondNode!=null}">
                                                                                ${cate.secondNode}
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                ${cate.firstNode}
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
										        </td>
										        <td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
										        	<select class="border0 p0 w200" name="listSupplierItems[${vs.index}].qualificationType" onchange="getFileByCode(this, '${vs.index}', '1')">
										        		<c:forEach items="${cate.typeList}" var="type">
										        			<option value="${type.id}" <c:if test="${cate.qualificationType eq type.id}">selected</c:if>>${type.name}</option>
										        		</c:forEach>
										        	</select>
										        	
										        </td>
										     	<td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>><input type="text" class="border0" name="listSupplierItems[${vs.index}].certCode" value="${cate.certCode}" onblur="getFileByCode(this, '${vs.index}', '2')"></td>
										     
										    	<td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
										    	<select class="border0 p0 w200" name="listSupplierItems[${vs.index}].professType" onchange="getFileByCode(this, '${vs.index}', '3')">
										        			<option value="${cate.proName}">${cate.proName}</option>
										        	</select>
<%-- 										    	<input type="text" class="border0" name="listSupplierItems[${vs.index}].professType" value="${cate.proName}" onblur="getFileByCode(this, '${vs.index}', '3')">
 --%>										    	</td>
										     
										     	<td <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
										     		<input type="hidden" name="listSupplierItems[${vs.index}].level" value="${cate.level.id}" class="w80">
										     		<input type="text" readonly="readonly" class="border0" value="${cate.level.name}">
										     	</td>
										      	<td class="tc" <c:if test="${fn:contains(audit,cate.itemsId)}">style="border: 1px solid red;" </c:if>>
										      	 <div class="w110 fl">
										      	  <u:show showId="eng_show_${vs.index}"  delete="false" businessId="${cate.fileId}" typeId="${engTypeId}" sysKey="${sysKey}"/>
										         </div>
										      	</td>
										      </tr>
										    </c:forEach>
										  </table>
										</form>
										<c:set value="${divCount+1}" var="divCount" />
									</div>
								</c:if>
								<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE') and fn:length(serviceQua) > 0}">
									<div class="tab-pane <c:if test="${divCount==0 } ">active in</c:if> fade height-300" id="tab-4">
										<h2 class="f16  ">
										      	<font color="red">*</font> 上传服务资质文件
										</h2>
										<table class="table table-bordered">
											<c:set value="0" var="slength"> </c:set>

											<c:forEach items="${serviceQua }" var="server">
												<tr>
													<td class="w200">${server.categoryName }
													</td>
													<td>
														<c:forEach items="${server.list }" var="ser">
															<c:set value="${slength+1}" var="slength"></c:set>
															<div class="fl mr5" <c:if test="${fn:contains(audit,ser.flag)}">style="border: 1px solid red;" onmouseover="errorMsg('${ser.flag}','aptitude_page')"</c:if>>
																<u:upload singleFileSize="300" exts="${properties['file.picture.type']}" id="serverUp${slength}" multiple="true" buttonName="${ser.name}" groups="${saleUp}" businessId="${ser.flag}" sysKey="${sysKey}" typeId="${typeId}" auto="true" />
																<div class="clear"></div>
																<u:show showId="serverShow${slength}" groups="${saleShow}" businessId="${ser.flag}" sysKey="${sysKey}" typeId="${typeId}" />
															</div>
														</c:forEach>
													</td>
												</tr>
											</c:forEach>
										</table>
										<c:set value="${divCount+1}" var="divCount" />
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="btmfix">
			<div style="margin-top: 15px;text-align: center;">
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="prev()">上一步</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="saveItems()">暂存</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="next()">下一步</button>
			</div>
		</div>
		
		<form id="items_info_form_id" action="${pageContext.request.contextPath}/supplier/contract.html" method="post">
			<input name="supplierId" id="supplierId" value="${currSupplier.id}" type="hidden" />
			<input name="categoryId" value="" id="categoryId" type="hidden" />
			<input name="flag" value="" id="flag" type="hidden" />
			<input name="supplierTypeIds" id="supplierTypeIds" value="${supplierTypeIds }" type="hidden" />
		</form>
		<div class="footer_margin">
   			<jsp:include page="../../../../../index_bottom.jsp"></jsp:include>
  		</div>
	</body>
</html>