<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
	<%@ include file="../../../common.jsp"%>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
<link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
		<script type="text/javascript">
			var parentId;
			var addressId = "${is.address}";

			$(document).ready(function() {
				for(var i = 0; i < document.getElementById("type").options.length; i++) {
					if(document.getElementById("type").options[i].value == '${ir.type}') {
						document.getElementById("type").options[i].selected = true;
						break;
					}
				}
			});

			$(function() {
				$.ajax({
					url: "${pageContext.request.contextPath}/area/listByOne.do",
					success: function(obj) {
						$.each(obj, function(i, result) {
							if(parentId == result.id) {
								$("#choose1").append("<option selected='true' value='" + result.id + "'>" + result.name + "</option>");
							} else {
								$("#choose1").append("<option value='" + result.id + "'>" + result.name + "</option>");
							}
						});
					},
					error: function(obj) {}
				});
			});
			
			function fun() {
				var parentId = $("#choose1").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/area/find_area_by_parent_id.do",
					data: {
						"id": parentId
					},
					success: function(obj) {
						var data = eval('(' + obj + ')');
						$("#choose2").empty();
						$("#choose2").append("<option value=''>-请选择-</option>");
						$.each(data, function(i, result) {
							$("#choose2").append("<option value='" + result.id + "'>" + result.name + "</option>");
						});
					},
					error: function(obj) {}
				});
			}
			function changeSupplierDep(){
		 		var supplierId = $("#supplierDeps").select2("val");
				 	$.ajax({
	          			contentType: "application/json;charset=UTF-8",
	         			url: "${pageContext.request.contextPath}/purchaseContract/changeSupplierDep.do?id="+supplierId,
	          			type: "POST",
	          			dataType: "json",
	          			success: function(org) {
	        	 			$("#supplierLegal").val(org.legalName);
	        	  			$("#supplierContact").val(org.contactName);
	        	  			$("#supplierContactTelephone").val(org.contactTelephone);
	        	  			$("#supplierContactAddress").val(org.area.name);
	        	 			$("#supplierUnitpostCode").val(org.postCode);
	        	 			$("#supplierBank").val(org.bankName);
	        	  			$("#supplierBankAccount_string").val(org.bankAccount);
	          			}
	    			});
			 }
		</script>
	</head>

	<body>

		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">售后服务采购管理</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);">售后服务登记</a>
					</li>
				</ul>
				
		</div>

		<div class="container container_box ">
			<sf:form action="${pageContext.request.contextPath}/after_sale_ser/update.html" method="post">
			<input type="hidden" id="id" name="id" value="${after.id}">
				<div>
			        <h2 class="list_title">售后服务修改</h2> 
					<ul class="ul_list">
					
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
				  			 <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="star_red">*</span>合同编号：</span>
			       			 <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
                				<input class="easyui-combobox" name="contractCode" id="contractCode" data-options="valueField:'code',textField:'code',panelHeight:'auto',panelMaxHeight:200,panelMinHeight:100" value="${contractCode}" style="width: 100%;height: 29px"/>
			        			<script>
				    $('#contractCode').combobox({  
				        prompt:'',  
				        required:false,  
				        url: "${pageContext.request.contextPath }/after_sale_ser/getContract.do",  
				        editable:true,  
				        hasDownArrow:true,  
				        filter: function(L, row){  
				            var opts = $(this).combobox('options');  
				            return row[opts.textField].indexOf(L) == 0;  
				        },
				        onSelect: function (org) { 
				        $("#contractAmount").val(org.money);
				        $('#product').combobox({  
					        prompt:'',  
					        required:false,  
					        url: "${pageContext.request.contextPath }/after_sale_ser/getProduct.do?id="+org.id,  
					        editable:true,  
					        value:'${after.requiredId}',
					        hasDownArrow:true,  
					        filter: function(L, row){  
					            var opts = $(this).combobox('options');  
					            return row[opts.textField].indexOf(L) == 0;  
					        }
					    });  
				        }
				    });  
				 </script>
			        			<div class="cue">${ERR_contract_code}</div>
			        		<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><span class="star_red">*</span> 产品名称</span>
										<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
										<input class="easyui-combobox" name="requiredId" id="product" data-options="valueField:'id',textField:'goodsName',panelHeight:'auto',panelMaxHeight:200,panelMinHeight:100"  style="width: 100%;height: 29px"/>
									</div>
	       					</div>
						 </li> 
						 
						<li class="col-md-3 col-sm-6 col-xs-12">
			   				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">合同金额：</span>
			   				<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			   		  			<input class="span5 projectType" type="text" id="contractAmount" name="" readonly="readonly" value="${afterSaleSer.money}">
		       		  			<span class="add-on">i</span>
								<span class="input-tip">填写合同编号合同信息自动生成</span>
			 	    		</div>
			 			</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>技术参数</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="technicalParameters" name="technicalParameters" value="${afterSaleSer.technicalParameters }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_technicalParameters}</div>
							</div>
						</li>
						<li class="col-md-12 col-sm-12 col-xs-12">
			   				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>全国售后服务地址：</span>
			   				<div class="col-md-12 col-sm-12 col-xs-12 p0">
		        				<textarea class="h130 col-md-12 col-sm-12 col-xs-12 " name="address" title="不超过800个字" placeholder="不超过800个字">${afterSaleSer.address}</textarea>
		       				</div>
		       				<div class="clear red">${ERR_address}</div>
			 			</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>联系人</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="contactName" name="contactName" value="${afterSaleSer.contactName }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_contactName}</div>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>联系方式</span>
							<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
								<input class="input_group" id="mobile" name="mobile" value="${afterSaleSer.mobile }" type="text">
								<span class="add-on">i</span>
								<div class="cue">${ERR_mobile}</div>
							</div>
						</li>
						<li id="bill_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
									<span class="col-md-5 col-sm-12 col-xs-12 padding-left-5 w250" <c:if test="${fn:contains(audit,'billCert')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('billCert')"</c:if>><span class="star_red">*</span>产品使用说明或用户操作手册</span>
									<div class="col-md-6 col-sm-12 col-xs-12 p0">
										<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="billcert_up" multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${afterSaleSerID}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" auto="true" />
										<u:show showId="billcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${afterSaleSerID}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
										<div class="cue"> ${err_bil } </div>
									</div>
								</li>
					</ul>
				</div>
				<div class="col-md-12 tc mt20">
					<button class="btn btn-windows save" type="submit">保存</button>
					<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
				</div>
			</sf:form>
		</div>
	</body>

</html>