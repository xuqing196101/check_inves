<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file ="/WEB-INF/view/common/tags.jsp" %>
<%@include file="/WEB-INF/view/front.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
$(function() {
	$("#page_ul_id").find("li").click(function() {
		var id = $(this).attr("id");
		var page = "tab-" + id.charAt(id.length - 1);
		$("input[name='defaultPage']").val(page);
	});
	var defaultPage = "${defaultPage}";
	if (defaultPage) {
		var num = defaultPage.charAt(defaultPage.length - 1);
		$("#page_ul_id").find("li").each(function(index) {
			if (index == num - 1) {
				$(this).attr("class", "active");
			} else {
				$(this).removeAttr("class");
			}
		});
		$("#tab_content_div_id").find(".tab-pane").each(function() {
			var id = $(this).attr("id");
			if (id == defaultPage) {
				$(this).attr("class", "tab-pane fade height-200 active in");
			} else {
				$(this).attr("class", "tab-pane fade height-200");
			}
		});
	}

	loadRootArea();
	autoSelected("business_select_id", "${currSupplier.businessType}");
	autoSelected("overseas_branch_select_id", "${currSupplier.overseasBranch}");

	if ("${currSupplier.status}" == 7) {
		showReason();
	}
});

/** 加载地区根节点 */
function loadRootArea() {
	$.ajax({
		url : globalPath + "/area/find_root_area.do",
		type : "post",
		dataType : "json",
		success : function(result) {
			var html = "";
			html += "<option value=''>请选择</option>";
			for ( var i = 0; i < result.length; i++) {
				html += "<option id='" + result[i].id + "' value='" + result[i].id + "'>" + result[i].name + "</option>";
			}
			$("#root_area_select_id").append(html);

			// 自动选中
			var rootArea = "${currSupplier.address}";
			if (rootArea)
				rootArea = rootArea.split(",")[0];
			if (rootArea) {
				autoSelected("root_area_select_id", rootArea);
				loadChildren();
			}

		},
	});
}

function loadChildren() {
	var id = $("#root_area_select_id").find("option:selected").attr("id");
	if (id) {
		$.ajax({
			url : globalPath + "/area/find_area_by_parent_id.do",
			type : "post",
			dataType : "json",
			data : {
				id : id
			},
			success : function(result) {
				var html = "";
				for ( var i = 0; i < result.length; i++) {
					html += "<option value='" + result[i].id + "'>" + result[i].name + "</option>";
				}
				$("#children_area_select_id").empty();
				$("#children_area_select_id").append(html);

				// 自动选中
				var childrenArea = "${currSupplier.address}";
				if (childrenArea)
					childrenArea = childrenArea.split(",")[1];
				if (childrenArea) {
					autoSelected("children_area_select_id", childrenArea);
				}
			},
		});
	}
}

/** 全选 */
function checkAll(ele, id) {
	var checked = $(ele).prop("checked");
	$("#" + id).find("input:checkbox").each(function(index) {
		$(this).prop("checked", checked);
	});
}

/** 保存基本信息 */
function saveBasicInfo(obj) {
	$("input[name='flag']").val(obj);
	$("#basic_info_form_id").submit();

}

function openStockholder() {
	var supplierId = $("input[name='id']").val();
	if (!supplierId) {
		layer.msg("请暂存供应商基本信息 !", {
			offset : '300px',
		});
	} else {
		layer.open({
			type : 2,
			title : '添加供应商股东信息',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '700px', '420px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : globalPath + '/supplier_stockholder/add_stockholder.html?&supplierId=' + supplierId + '&sign=1', //url
			closeBtn : 1, //不显示关闭按钮
		});
	}
}

function deleteStockholder() {
	var checkboxs = $("#stockholder_list_tbody_id").find(":checkbox:checked");
	var stockholderIds = "";
	var supplierId = $("input[name='id']").val();
	$(checkboxs).each(function(index) {
		if (index > 0) {
			stockholderIds += ",";
		}
		stockholderIds += $(this).val();
	});
	var size = checkboxs.length;
	if (size > 0) {
		layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
			offset : '200px',
			scrollbar : false,
		}, function(index) {
			window.location.href = globalPath + "/supplier_stockholder/delete_stockholder.html?stockholderIds=" + stockholderIds + "&supplierId=" + supplierId;
			layer.close(index);

		});
	} else {
		layer.alert("请至少勾选一条记录 !", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

function openFinance() {
	var supplierId = $("input[name='id']").val();
	if (!supplierId) {
		layer.msg("请暂存供应商基本信息 !", {
			offset : '300px',
		});
	} else {
		layer.open({
			type : 2,
			title : '添加供应商财务信息',
			// skin : 'layui-layer-rim', //加上边框
			area : [ '650px', '420px' ], //宽高
			offset : '100px',
			scrollbar : false,
			content : globalPath + '/supplier_finance/add_finance.html?&supplierId=' + supplierId + '&sign=1', //url
			closeBtn : 1, //不显示关闭按钮
		});
	}
}

function deleteFinance() {
	var checkboxs = $("#finance_list_tbody_id").find(":checkbox:checked");
	var financeIds = "";
	var supplierId = $("input[name='id']").val();
	$(checkboxs).each(function(index) {
		if (index > 0) {
			financeIds += ",";
		}
		financeIds += $(this).val();
	});
	var size = checkboxs.length;
	if (size > 0) {
		layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
			offset : '200px',
			scrollbar : false,
		}, function(index) {
			window.location.href = globalPath + "/supplier_finance/delete_finance.html?financeIds=" + financeIds + "&supplierId=" + supplierId;
			layer.close(index);

		});
	} else {
		layer.alert("请至少勾选一条记录 !", {
			offset : '200px',
			scrollbar : false,
		});
	}
}

	function autoSelected(id, v) {
		if (v) {
			$("#" + id).find("option").each(function() {
				var value = $(this).val();
				if (value == v) {
					$(this).prop("selected", true);
				} else {
					$(this).prop("selected", false);
				}
			});
		}
	}

	function checkAllForFinance(ele) {
		var flag = $(ele).prop("checked");
		$("#finance_list_tbody_id").find("input:checkbox").prop("checked", flag);
		$("#finance_attach_list_tbody_id").find("input:checkbox").prop("checked", flag);
	}

	function showReason() {
		var supplierId = "${currSupplier.id}";
		var left = document.body.clientWidth - 500;
		var top = window.screen.availHeight / 2 - 150;
		layer.open({
			type : 2,
			title : '审核反馈',
			closeBtn : 0, //不显示关闭按钮
			skin : 'layui-layer-lan', //加上边框
			area : [ '500px', '300px' ], //宽高
			offset : [ top, left ],
			shade : 0,
			maxmin : true,
			shift : 2,
			content : globalPath + '/supplierAudit/showReasonsList.html?&auditType=basic_page,finance_page,stockholder_page' + '&jsp=dialog_basic_reason' + '&supplierId=' + supplierId, //url
		});
	}

	function downloadFile(obj){
		var id=$(obj).parent().children(":last").val();
	 	var key=1;
	    var form = $("<form>");   
	        form.attr('style', 'display:none');   
	        form.attr('method', 'post');
	        form.attr('action', globalPath + '/file/download.html?id='+ id +'&key='+key);
	        $('body').append(form); 
	        form.submit();
	}

	function dis(obj){
		var vals=$(obj).val();
		if(vals==1){
			$('#sup_country').removeAttr('disabled');
			$('#sup_businessScope').removeAttr('disabled');
			$('#sup_branchName').removeAttr('disabled');
			$('#sup_branchAddress').removeAttr('disabled');
		}else{
			$('#sup_country').attr('disabled',"true");
			$('#sup_businessScope').attr('disabled',"true");
			$('#sup_branchName').attr('disabled',"true");
			$('#sup_branchAddress').attr('disabled',"true");
			
		}
	}
	
	function checknums(obj){
		var vals=$(obj).val();
		var reg=/^[0-9].*$/;
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
	<c:if test="${currSupplier.status != 7}">
		<%@ include file="/index_head.jsp"%>
    </c:if>
   <div class="wrapper">
	<%@include file="supplierNav.jsp" %>
	<!--基本信息-->
	<div class="container container_box">
	  <form id="basic_info_form_id" action="${pageContext.request.contextPath}/supplier/perfect_basic.html" method="post">
		<input name="id" value="${currSupplier.id}" type="hidden" /> 
	<%-- 	<input name="defaultPage" value="${defaultPage}" type="hidden" />  --%>
		<input name="flag" type="hidden" />
		<div>
    	  <h2 class="count_flow"><i>01</i>基本信息</h2>
    	  <ul class="list-unstyled" style="font-size: 14">
    	  	<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font">
	 			<legend>企业信息</legend>
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 公司名称</span>
					<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
				        <input id="supplierName_input_id" type="text" name="supplierName" value="${currSupplier.supplierName}" /> 
				        <span class="add-on cur_point">i</span>
				        <div class="cue"> ${err_msg_supplierName } </div>
				     </div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 公司网址</span>
				    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			        <input type="text" name="website" value="${currSupplier.website}">
			        <span class="add-on cur_point">i</span>
			         <div class="cue"> ${err_msg_website } </div>
			       </div>
				 </li>
				 
				 <li class="col-md-3 col-sm-6 col-xs-12">
				    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 成立日期</span>
				    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
				    <fmt:formatDate value="${currSupplier.foundDate}" pattern="yyyy-MM-dd" var="foundDate" />
			        <input type="text" readonly="readonly" onClick="WdatePicker()" name="foundDate" value="${foundDate}" />
			        <span class="add-on cur_point">i</span>
			         <div class="cue"> ${err_msg_foundDate } </div>
			       </div>
				 </li> 
				 
				  <li class="col-md-3 col-sm-6 col-xs-12">
				    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照类型</span>
				    <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			       	<select  name="businessType" id="business_select_id">
			         	<c:forEach items="${company }" var="obj">
						    <option value="${obj.id }">${obj.name }</option>
						</c:forEach>
						<!-- <option>外资企业</option>
						<option>民营企业</option>
						<option>股份制企业</option>
						<option>私营企业</option> -->
					</select>
					 
					  
			       </div>
				 </li>
				  <li class="col-md-3 col-sm-6 col-xs-12">
				    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 公司地址</span>
				    <div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="root_area_select_id" onchange="loadChildren()" name="address"></select></div> 
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="children_area_select_id" name="address" ></select></div>
				         <div class="cue"> ${err_msg_address } </div>
			        </div>		        
				 </li>  
				 
				  <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 开户行名称</span>
				   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			        <input type="text" name="bankName" value="${currSupplier.bankName}" />
			        <span class="add-on cur_point">i</span>
			        <div class="cue"> ${err_msg_bankName } </div>
			       </div>
				 </li> 
						 
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 col-xs-12 col-sm-12  padding-left-5"><i class="red">*</i> 开户行账号</span>
				   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			        <input type="text" name="bankAccount" value="${currSupplier.bankAccount}" />
			        <span class="add-on cur_point">i</span>
			        <div class="cue"> ${err_msg_bankAccount } </div>
			       </div>
				 </li> 
				 
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 col-xs-12 col-sm-12  padding-left-5"><i class="red">*</i>邮编</span>
				   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			        <input type="text" name="postCode" value="${currSupplier.postCode}" />
			        <span class="add-on cur_point">i</span>
			         <div class="cue"> ${err_msg_postCode } </div>
			       </div>
				 </li>  
	       </fieldset>
	       <fieldset class="col-md-12 col-xs-12 col-sm-12 border_font mt20">
	 			<legend>资质资信</legend>
				<li class="col-md-6 col-sm-12 col-xs-12 mb25">
				    <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三个月完税凭证</span> 
				    <div class="col-md-6 col-sm-12 col-xs-12 p0">
		    		  <u:upload id="taxcert_up"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" auto="true" /> 
		        	  <u:show showId="taxcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" />
			        </div>
			        <div class="cue"> ${err_taxCert } </div>
			    </li> 
				
				<li id="bill_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
				   <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三年银行基本账户年末对账单</span> 
				   <div class="col-md-6 col-sm-12 col-xs-12 p0">
					   <u:upload id="billcert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" auto="true" /> 
					   <u:show showId="billcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
				   </div>
				    <div class="cue"> ${err_bil } </div>
				</li>
												
			   <li id="security_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
			      <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三个月缴纳社会保险金凭证</span> 
			      <div class="col-md-6 col-sm-12 col-xs-12 p0">
			        <u:upload id="curitycert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" auto="true" /> 
			        <u:show showId="curitycert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" />
			      </div>
			      <div class="cue"> ${err_security } </div>
			   </li>
												
			 <li id="breach_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
			   <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三年内无重大违法记录声明</span> 
			   <div class="col-md-6 col-sm-12 col-xs-12 p0">
			     <u:upload id="bearchcert_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" auto="true" /> 
			     <u:show showId="bearchcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
			   </div>
			   <div class="cue"> ${err_bearch } </div>
			</li>						
		</fieldset>
		<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
	 	  <legend>法人代表信息</legend>
		  <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 姓名</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="legalName" value="${currSupplier.legalName}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_legalName } </div>
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>身份证号</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="legalIdCard" value="${currSupplier.legalIdCard}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_legalCard } </div>
	       	   </div>
		    </li> 
		    
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="legalMobile" value="${currSupplier.legalMobile}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_legalMobile } </div>
	       	   </div>
		    </li> 
		    
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="legalTelephone" value="${currSupplier.legalTelephone}"  />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_legalPhone } </div>
	       	   </div>
		    </li> 
	    </fieldset>
	    <fieldset class="col-md-12 border_font mt20">
	 	    <legend>联系人信息</legend>
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 联系人姓名</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="contactName" value="${currSupplier.contactName}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_conName } </div>
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 传真电话</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="contactFax" value="${currSupplier.contactFax}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_fax } </div>
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="contactMobile" value="${currSupplier.contactMobile}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_catMobile } </div>
	       	   </div>
		    </li> 
		    
 
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="contactTelephone" value="${currSupplier.contactTelephone}" />
		        <span class="add-on cur_point">i</span>
			    <div class="cue"> ${err_catTelphone } </div>		        
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 邮箱</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="contactEmail" value="${currSupplier.contactEmail}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_catEmail } </div>
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 地址</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="contactAddress" value="${currSupplier.contactAddress}" />
		        <span class="add-on cur_point">i</span>
		         <div class="cue"> ${err_conAddress } </div>
	       	   </div>
		    </li> 
	    </fieldset>
	    <fieldset class="col-md-12 border_font mt20">
	 	    <legend>营业执照</legend>
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 统一信用代码</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="creditCode" value="${currSupplier.creditCode}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_creditCide} </div>
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 登记机关</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="registAuthority" value="${currSupplier.registAuthority}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_reAuthoy } </div>
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 注册资本</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="registFund" onkeyup="checknums(this)" value="${currSupplier.registFund}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue" id="err_fund"> ${err_fund } </div>
	       	   </div>
		    </li> 
		    
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业开始时间</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			   	<fmt:formatDate value="${currSupplier.businessStartDate}" pattern="yyyy-MM-dd" var="businessStartDate" />
		        <input type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="${businessStartDate}"  />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_sDate } </div>
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业截止时间</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			   	<fmt:formatDate value="${currSupplier.businessEndDate}" pattern="yyyy-MM-dd" var="businessEndDate" />
		        <input type="text" readonly="readonly" onClick="WdatePicker()" name="businessEndDate" value="${businessEndDate}"   />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_eDate } </div>
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 生产经营地址</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="businessAddress" value="${currSupplier.businessAddress}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_bAddress } </div>
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 邮编</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			      <input type="text" name="businessPostCode" value="${currSupplier.businessPostCode}" />
			      <span class="add-on cur_point">i</span>
			       <div class="cue"> ${err_bCode } </div>
	       	   </div>
	       	 
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
		     <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照:</span> 
				   <div class="col-md-6 col-sm-12 col-xs-12 p0">
					 <u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /> 
		   	   		 <u:upload id="business_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
				   </div>
				   <div class="cue"> ${err_business } </div>
		    </li> 
		    
		    <li class="col-md-12 col-xs-12 col-sm-12 mb25">
		    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 营业范围</span>
		    	<div class="col-md-12 col-xs-12 col-sm-12 p0">
			       <textarea class="col-md-12 col-xs-12 col-sm-12 h130" title="不超过800个字" name="businessScope">${currSupplier.bankName}</textarea>
	       	    </div>
			</li> 
		</fieldset>
		 <fieldset class="col-md-12 border_font mt20">
	 	    <legend>境外分支</legend>
			<li class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">境外分支结构</span>
		    	<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
		    	   <select  name="overseasBranch" onchange="dis(this)"  id="overseas_branch_select_id">
						<option value="1">有</option>
						<option value="0">无</option>
					</select>
	       	    </div>
			</li>
			<li class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">境外分支所在国家</span>
		    	 <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		    	 	<input name="branchCountry" id="sup_country" type="text" value="${currSupplier.branchCountry}" />
			        <span class="add-on cur_point">i</span>
	       	    </div>
			</li>
			
			<li class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">分支地址</span>
		    	 <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		    	 	<input type="text" name="branchAddress"  id="sup_branchAddress" value="${currSupplier.branchAddress}" />
			        <span class="add-on cur_point">i</span>
	       	    </div>
			</li>
			<li class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">机构名称</span>
		    	 <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		    	 	<input type="text" name="branchName" id="sup_branchName"  value="${currSupplier.branchName}" />
			        <span class="add-on cur_point">i</span>
	       	    </div>
			</li>
			
			<li class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">分支生产经营范围</span>
		    	 <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		    	 	<input type="text" name="branchBusinessScope" id="sup_businessScope"" value="${currSupplier.branchBusinessScope}" />
			        <span class="add-on cur_point">i</span>
	       	    </div>
			</li>
		</fieldset>
	  </ul>
	  <!-- 财务信息 -->
	  <div class="padding-top-10 clear">
	    	<h2 class="count_flow"><i>2</i>财务信息</h2>
	    	<fieldset class="col-md-12 col-xs-12 col-sm-12 border_font">
	 	    <legend>列表</legend>
			<div  class="col-md-12 col-sm-12 col-xs-12 p0" >
				<div class="col-md-12 col-sm-12 col-xs-12 p0 tl">
					<button type="button" class="btn btn-windows add" onclick="openFinance()">新增</button>
					<button type="button" class="btn btn-windows delete" onclick="deleteFinance()">删除</button>
					<span class="red">${finace }</span>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 p0">
					  <table class="table table-bordered table-condensed mt5">
						<thead>
							<tr>
								<th class="w30 info"><input type="checkbox" onchange="checkAllForFinance(this)" />
								</th>
								<th class="w50 info">年份</th>
								<th class="info">会计事务所名称</th>
								<th class="info">事务所联系电话</th>
								<th class="info">审计人姓名</th>
								<th class="info">指标</th>
								<th class="info">资产总额</th>
								<th class="info">负债总额</th>
								<th class="info">净资产总额</th>
								<th class="info">营业收入</th>
							</tr>
						</thead>
						<tbody id="finance_list_tbody_id">
							<c:forEach items="${currSupplier.listSupplierFinances}" var="finance" varStatus="vs">
								<tr>
									<td class="tc"><input type="checkbox" value="${finance.id}" />
									</td>
									<td class="tc">${finance.year}</td>
									<td class="tc">${finance.name}</td>
									<td class="tc">${finance.telephone}</td>
									<td class="tc">${finance.auditors}</td>
									<td class="tc">${finance.quota}</td>
									<td class="tc">${finance.totalAssets}</td>
									<td class="tc">${finance.totalLiabilities}</td>
									<td class="tc">${finance.totalNetAssets}</td>
									<td class="tc">${finance.taking}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				 </div>
			    </div>
			   </fieldset>
			   <fieldset class="col-md-12 border_font mt20">
	 	    	   <legend>附件</legend>
	 	    	   <div>
					  <table id="finance_attach_list_id" class="table table-bordered table-condensed mt5">
						<thead>
							<tr>
								<th class="w30 info"><input type="checkbox" onchange="checkAllForFinance(this)" />
								</th>
								<th class="w50 info">年份</th>
								<th class="info">财务利润表</th>
								<th class="info">审计报告的审计意见</th>
								<th class="info">资产负债表</th>
								<th class="info">现金流量表</th>
								<th class="info">所有者权益变动表</th>
							</tr>
						</thead>
						<tbody id="finance_attach_list_tbody_id">
							<c:forEach items="${currSupplier.listSupplierFinances}" var="finance" varStatus="vs">
								<tr>
									<td class="tc"><input type="checkbox" value="${finance.id}" />
									</td>
									<td class="tc">${finance.year}</td>
									<td class="tc"><a class="mt3 color7171C6" href="javascript:download('${finance.auditOpinionId}', '${sysKey}')">${finance.auditOpinion}</a>
									</td>
									<td class="tc"><a class="mt3 color7171C6" href="javascript:download('${finance.liabilitiesListId}', '${sysKey}')">${finance.liabilitiesList}</a>
									</td>
									<td class="tc"><a class="mt3 color7171C6" href="javascript:download('${finance.profitListId}', '${sysKey}')">${finance.profitList}</a>
									</td>
									<td class="tc"><a class="mt3 color7171C6" href="javascript:download('${finance.cashFlowStatementId}', '${sysKey}')">${finance.cashFlowStatement}</a>
									</td>
									<td class="tc"><a class="mt3 color7171C6" href="javascript:download('${finance.changeListId}', '${sysKey}')">${finance.changeList}</a>
									</td>
								</tr>
							</c:forEach>
						  </tbody>
					  </table>
				</div>
			</fieldset>
			<div class="padding-top-10 clear">
			    <h2 class="count_flow"><i>3</i>股东信息</h2>
				<div  class="col-md-12 p0 ul_list mb50">
				   <div class="col-md-12 p15 mt20">
						<div class="fl">
						    <button class="btn btn-windows add" type="button" onclick="openStockholder()" >新增</button>
							<button class="btn btn-windows delete" type="button" onclick="deleteStockholder()" >删除</button>
							<span class="red">${stock }</span>
						</div>
						<div class="mt40">
							<table id="share_table_id" class="table table-bordered table-condensed mt5">
								<thead>
									<tr>
										<th class="info"><input type="checkbox" onchange="checkAll(this, 'stockholder_list_tbody_id')" />
										</th>
										<th class="info">出资人名称或姓名</th>
										<th class="info">出资人性质</th>
										<th class="info">统一社会信用代码或身份证号码</th>
										<th class="info">出资金额或股份（万元/万份）</th>
										<th class="info">比例</th>
									</tr>
								</thead>
								<tbody id="stockholder_list_tbody_id">
									<c:forEach items="${currSupplier.listSupplierStockholders}" var="stockholder" varStatus="vs">
										<tr>
											<td class="tc"><input type="checkbox" value="${stockholder.id}" />
											</td>
											<td class="tc">${stockholder.name}</td>
											<td class="tc">${stockholder.nature}</td>
											<td class="tc">${stockholder.identity}</td>
											<td class="tc">${stockholder.shares}</td>
											<td class="tc">${stockholder.proportion}</td>
										</tr>
									</c:forEach>
								</tbody>
							  </table>
							</div>
						</div>
					</div>
				 </div>
			</form>
		</div>
	</div>
	<div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	    <button type="button" class="btn save" onclick="saveBasicInfo('2')">暂存</button>
				<button type="button" class="btn" onclick="saveBasicInfo('1')">下一步</button>
	  	  </div>
	</div>
</body>
</html>
