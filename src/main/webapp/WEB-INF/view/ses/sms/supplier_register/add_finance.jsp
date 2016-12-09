<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>添加财务信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
 
 <%@ include file="/WEB-INF/view/front.jsp" %>
<script type="text/javascript">
	
	function saveOrBack() {
		
		
	/* 	var action = "${pageContext.request.contextPath}/supplier_finance/";
		if (sign) {
			action += "save_or_update_finance.html";
		} else {
			action += "back_to_basic_info.html";
		}
		$("#finance_form_id").attr("action", action);
		$("#finance_form_id").submit(); */
		 var index=parent.layer.getFrameIndex(window.name);
 
		 $.ajax({
			   type: "POST",  
               url: "${pageContext.request.contextPath}/supplier_finance/save_or_update_finance.html",  
               data: $("#finance_form_id").serialize(),  
               dataType:"json",
               success:function(result){
            	  var boo=result.bool;
            	  var obj=result.fiance;
	 	          if(boo==false){
                	 //   layer.msg(",{offset: ['150px', '180px']});     	 
                 } else{
                	  //parent.location.reload();
                	  /* parent.window.location.href = "${pageContext.request.contextPath}/supplier/perfect_basic.html?id="+id; */
                	   parent.$('#finance_list_tbody_id').append("<tr> <td class='tc'><input type='checkbox' value="+obj.id+"/></td>"+
                		"<td class='tc'>"+obj.year+"</td>"+	   
                		"<td class='tc'>"+obj.name+"</td>"+	
                		"<td class='tc'>"+obj.telephone+"</td>"+	
                		"<td class='tc'>"+obj.auditors+"</td>"+	
                		"<td class='tc'>"+obj.quota+"</td>"+	
                		"<td class='tc'>"+obj.totalAssets+"</td>"+	
                		"<td class='tc'>"+obj.totalLiabilities+"</td>"+	
                		"<td class='tc'>"+obj.totalNetAssets+"</td>"+	
                		"<td class='tc'>"+obj.taking+"</td> </tr>");
                	  
                	   parent.$('#finance_attach_list_tbody_id').append(" <tr> <td class='tc'><input type='checkbox' value="+obj.id+"></td>"+
                		    "<td class='tc'>"+obj.year+"</td>"+	
                       		"<td class='tc'><a class='mt3 color7171C6' onClick='javascript:downloadFile(this)'>"+obj.auditOpinion+"</a> <input type='hidden' value="+ obj.auditOpinionId+"></td>"+	   
                       		"<td class='tc'><a class='mt3 color7171C6' onClick='javascript:downloadFile(this)'>"+obj.liabilitiesList+"</a> <input type='hidden' value="+ obj.liabilitiesListId+"></td>"+	   
                       		"<td class='tc'><a class='mt3 color7171C6' onClick='javascript:downloadFile(this)'>"+obj.profitList+"</a> <input type='hidden' value="+ obj.profitListId+"></td>"+	   
                       		"<td class='tc'><a class='mt3 color7171C6' onClick='javascript:downloadFile(this)'>"+obj.cashFlowStatement+"</a><input type='hidden' value="+ obj.cashFlowStatementId+"> </td>"+	   
                       		"<td class='tc'><a class='mt3 color7171C6' onClick='javascript:downloadFile(this)'>"+obj.changeList+"</a> <input type='hidden' value="+ obj.changeListId+"> </td> </tr>"   

                       		);
                	    parent.layer.close(index);  
                 }
            	   
                },
                error: function(result){
                    layer.msg("添加失败",{offset: ['150px', '180px']});
                }
                
                
		 });
		
	}
	function cancels(){
		 var index=parent.layer.getFrameIndex(window.name);

		    parent.layer.close(index);

	}
	
	function checknums(obj){
		var vals=$(obj).val();
		var reg= /^\d+\.?\d*$/;  
		if(!reg.exec(vals)){
			$(obj).val("");
			 $("#err_fund").text("数字非法");
		}else{
			$("#err_fund").text();
			$("#err_fund").empty();
		}
	}
	
	
</script>

</head>

<body>


		<!--基本信息-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<form id="finance_form_id" method="post" target="_parent">
							<input name="id" value="${uuid}" type="hidden" />
							<input name="supplierId" value="${supplierFinance.supplierId}" type="hidden" />
							<input name="sign" value="${supplierFinance.sign}" type="hidden" />
					<!-- 		<input name="auditOpinionId"  type="hidden" />
							<input name="liabilitiesListId"  type="hidden" />
							<input name="profitListId"  type="hidden" />
							<input name="cashFlowStatementId"  type="hidden" />
							<input name="changeListId"  type="hidden" />
							<input name="createdAt"  type="hidden" />
							<input name="updatedAt"  type="hidden" />
							<input name="listUploadFiles"  type="hidden" /> -->
									
							<div class="tab-content padding-top-20">
								<!-- 详细信息 -->
								<div class="tab-pane fade active in height-300" id="tab-1">
									<div class=" margin-bottom-0">
										<ul class="list-unstyled">
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>年份：</span>
												<div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
<!-- 													<input type="text" name="year" readonly="readonly" onClick="WdatePicker({dateFmt : 'yyyy'})" />
 -->												<select name="year">
 														<c:forEach items="${yearList }" var="y">
 															<option value="${y}">${y }</option>
 														</c:forEach>
 													</select>
 												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>会计实务所名称：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="name" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>事务所联系电话：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="telephone" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>审计人姓名：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="auditors" />
												</div>
											</li>
											<!-- <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>指标：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input type="text" name="quota" />
												</div>
											</li> -->
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>资产总额（万元）：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input  onkeyup="checknums(this)" type="text" name="totalAssets" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>负债总额（万元）：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input onkeyup="checknums(this)" type="text" name="totalLiabilities" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>净资产总额（万元）：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input onkeyup="checknums(this)" type="text" name="totalNetAssets" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>营业收入（万元）：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<input onkeyup="checknums(this)" type="text" name="taking" />
												</div>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>财务审计报告意见：</span>
												<up:upload id="auditopinion_up" groups="auditopinion_up,liabilities_up,profit_up,cashflow_up,ownerchange_up" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierAuditOpinion}" auto="true" />
												<up:show showId="auditopinion_show" groups="auditopinion_show,liabilities_show,profit_show,cashflow_show,ownerchange_show" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierAuditOpinion}"/>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>资产负债表：</span>
												<up:upload id="liabilities_up" groups="auditopinion_up,liabilities_up,profit_up,cashflow_up,ownerchange_up" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierLiabilities}" auto="true" />
												<up:show showId="liabilities_show" groups="auditopinion_show,liabilities_show,profit_show,cashflow_show,ownerchange_show" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierLiabilities}"/>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>利润表：</span>
												<up:upload id="profit_up" groups="auditopinion_up,liabilities_up,profit_up,cashflow_up,ownerchange_up" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProfit}" auto="true" />
												<up:show showId="profit_show" groups="auditopinion_show,liabilities_show,profit_show,cashflow_show,ownerchange_show" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProfit}"/>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>现金流量表：</span>
												<up:upload id="cashflow_up" groups="auditopinion_up,liabilities_up,profit_up,cashflow_up,ownerchange_up" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierCashFlow}" auto="true" />
												<up:show showId="cashflow_show" groups="auditopinion_show,liabilities_show,profit_show,cashflow_show,ownerchange_show" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierCashFlow}"/>
											</li>
											<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">所有者权益变动表：</span>
												<up:upload id="ownerchange_up" groups="auditopinion_up,liabilities_up,profit_up,cashflow_up,ownerchange_up" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierOwnerChange}" auto="true" />
												<up:show showId="ownerchange_show" groups="auditopinion_show,liabilities_show,profit_show,cashflow_show,ownerchange_show" businessId="${uuid}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierOwnerChange}"/>
											</li>
											<div class="clear"></div>
										</ul>
									</div>
								</div>
							</div>
							<div class="tc mt10 col-md-12 col-xs-12">
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="saveOrBack()">保存</button>
								<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="cancels()">取消</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
