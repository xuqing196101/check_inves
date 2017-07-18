<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加财务信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
 
 <%@ include file="/WEB-INF/view/front.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">
    function saveOrBack(status) {
    	$("#status").val(status);
    	form1.submit();
    }
	function checknums(obj,nonNum){
		/* var vals=$(obj).val();
		var reg= /^\d+\.?\d*$/;  
		if(!reg.exec(vals)){
			$(obj).val("");
			 $("#err_fund").text("数字非法");
		}else{
			$("#err_fund").text();
			$("#err_fund").empty();
		} */
		
		 var _val = $(obj).val();
			    if(_val!="" && nonNum!=3){//如果可以为负数的话设置3;净资产总额不进行负数校验
			        if(parseInt(_val)<0){
                   $(obj).val("");
                   layer.msg("请输入正确的金额,非负数保留4位小数", {
                       offset: '300px'
                   });
                   return false;
               }
           }
           if(_val.indexOf('.')!=-1){
               var reg = /\d+\.\d{0,4}?$/;
               if(!reg.test(_val)) {
                   $(obj).val("");
                   if(nonNum==3){
                       layer.msg("请输入正确的金额,保留4位小数", {
                           offset: '300px'
                       });
                   }else{
                       layer.msg("请输入正确的金额,非负数保留4位小数", {
                           offset: '300px'
                       });
                   }
               }
           }else{
               if(!positiveRegular(_val)){
                   $(obj).val("");
                   if(nonNum==3){
                       layer.msg("请输入正确的金额,保留4位小数", {
                           offset: '300px'
                       });
                   }else{
                       layer.msg("请输入正确的金额,非负数保留4位小数", {
                           offset: '300px'
                       });
                   }
               }
           }
	}
</script>
</head>
<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">个人信息</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);">提报每年财务审计</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!--基本信息-->
		<div class="container">
		<form id="form1" action="${pageContext.request.contextPath}/supplier_finance/update.html" method="post">
					<div class="headline-v2">
						<h2>财务审计报告修改</h2>
					</div>
						<ul class="ul_list">
							<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>年份：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
										<input type="hidden" name="id" value="${supplierFinance.id }" readonly="readonly" />
										<input type="hidden" name="status" id="status"  readonly="readonly" />
										<input type="text" name="year" value="${supplierFinance.year }" readonly="readonly" />
										<div class="cue">${map.year}</div>
									</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>会计事务所名称：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input type="text" name="name" value="${supplierFinance.name}" />
									<div class="cue">${map.name}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>事务所联系电话：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input type="text" name="telephone" value="${supplierFinance.telephone}" />
									<div class="cue">${map.phone}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>审计人姓名：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input type="text" name="auditors" value="${supplierFinance.auditors}" />
									<div class="cue">${map.auditors}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>资产总额（万元）：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input  onkeyup="checknums(this,1)" type="text" name="totalAssets" value="${supplierFinance.totalAssets}" />
									<div class="cue">${map.assets}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>负债总额（万元）：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input onkeyup="checknums(this,2)" type="text" name="totalLiabilities" value="${supplierFinance.totalLiabilities}" />
									<div class="cue">${map.bilit}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>净资产总额（万元）：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input onkeyup="checknums(this,3)" type="text" name="totalNetAssets" value="${supplierFinance.totalNetAssets}" />
									<div class="cue">${map.noAssets}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>营业收入（万元）：</span>
								<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
									<input onkeyup="checknums(this,4)" type="text" name="taking" value="${supplierFinance.taking}" />
									<div class="cue">${map.taking}</div>
								</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>财务审计报告意见：</span>
								<u:upload  multiple="true" id="auditopinion_up" groups="auditopinion_up,liabilities_up,profit_up,cashflow_up,ownerchange_up" businessId="${supplierFinance.id }" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierAuditOpinion}" auto="true" />
								<u:show showId="auditopinion_show" groups="auditopinion_show,liabilities_show,profit_show,cashflow_show,ownerchange_show" businessId="${supplierFinance.id }" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierAuditOpinion}"/>
								<div class="cue">${map.err_taxCert}</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>资产负债表：</span>
								<u:upload  multiple="true" id="liabilities_up" groups="auditopinion_up,liabilities_up,profit_up,cashflow_up,ownerchange_up" businessId="${supplierFinance.id }" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierLiabilities}" auto="true" />
								<u:show showId="liabilities_show" groups="auditopinion_show,liabilities_show,profit_show,cashflow_show,ownerchange_show" businessId="${supplierFinance.id }" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierLiabilities}"/>
								<div class="cue">${map.err_bil}</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>利润表：</span>
								<u:upload  multiple="true" id="profit_up" groups="auditopinion_up,liabilities_up,profit_up,cashflow_up,ownerchange_up" businessId="${supplierFinance.id }" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProfit}" auto="true" />
								<u:show showId="profit_show" groups="auditopinion_show,liabilities_show,profit_show,cashflow_show,ownerchange_show" businessId="${supplierFinance.id }" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProfit}"/>
								<div class="cue">${map.err_security}</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><a class="star_red">*</a>现金流量表：</span>
								<u:upload  multiple="true" id="cashflow_up" groups="auditopinion_up,liabilities_up,profit_up,cashflow_up,ownerchange_up" businessId="${supplierFinance.id }" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierCashFlow}" auto="true" />
								<u:show showId="cashflow_show" groups="auditopinion_show,liabilities_show,profit_show,cashflow_show,ownerchange_show" businessId="${supplierFinance.id }" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierCashFlow}"/>
								<div class="cue">${map.err_bearch}</div>
							</li>
							<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">所有者权益变动表：</span>
								<u:upload  multiple="true" id="ownerchange_up" groups="auditopinion_up,liabilities_up,profit_up,cashflow_up,ownerchange_up" businessId="${supplierFinance.id }" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierOwnerChange}" auto="true" />
								<u:show showId="ownerchange_show" groups="auditopinion_show,liabilities_show,profit_show,cashflow_show,ownerchange_show" businessId="${supplierFinance.id }" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierOwnerChange}"/>
							</li>
						</ul>
			<div class="tc mt10 col-md-12 col-xs-12">
				<button type="button" class="btn btn-windows save" onclick="saveOrBack(1)">暂存</button>
				<button type="button" class="btn btn-windows save" onclick="saveOrBack(2)">提交</button>
				<button type="button" class="btn btn-windows back" onclick="history.go(-1)">返回</button>
			</div>
		</form>
     </div>
</body>
</html>
