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

	// loadRootArea();
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

function loadChildren(obj) {
	var id = $(obj).val();
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
				var select=$(obj).parent().next().children();
				$(select).empty();
				$(select).append(html);

				// 自动选中
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
		
		
		var infotd;
		var filetd;
		function openFinance(obj,year) {
			infotd=$(obj).parent().next().children(":first").children(":last");
			filetd=$(obj).parent().next().children(":last").children(":last");
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
					content : globalPath + '/supplier_finance/add_finance.html?&supplierId=' + supplierId + '&sign=1&&year='+year, //url
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
		var reg= /^\d+\.?\d*$/;  
		if(!reg.exec(vals)){
			$(obj).val("");
			 $("#err_fund").text("数字非法");
		}else{
			$("#err_fund").text();
			$("#err_fund").empty();
		}
	}
	
	function increaseAddress(obj){
		var ind=$("#index").val();
		ind++;
		$("#index").val(ind);
		var li=$(obj).parent().parent();
		$(li).after("<li class='col-md-3 col-sm-6 col-xs-12 pl10'>"+
				"<span class='col-md-12 col-xs-12 col-sm-12  padding-left-5'><i class='red'>*</i> 生产公司邮编</span>"+
				   "<div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>"+
			        "<input type='text' name='addressList["+ind+"].code' value='' />"+
			        "<span class='add-on cur_point'>i</span>"+
			        " <div class='cue'> </div>"+
			       "</div>"+
				"</li> "+
			 	"<li class='col-md-3 col-sm-6 col-xs-12'>"+
			    "<span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>*</i> 生产公司地址</span>"+
			    "<div class='col-md-12 col-xs-12 col-sm-12 select_common p0'>"+
			     "<div class='col-md-5 col-xs-5 col-sm-5 mr5 p0'><select id='root_area_select_id' onchange='loadChildren(this)'  name='addressList["+ind+"].provinceId' >"+
			     " <option value=''>请选择</option>"+
			      " <c:forEach  items='${privnce }' var='prin'>"+
				       " <option value='${prin.id }'  >${prin.name }</option>"+
			        " </c:forEach>"+
			        " </select></div> "+
			         "<div class='col-md-5 col-xs-5 col-sm-5 mr5 p0'><select id='children_area_select_id' name='addressList["+ind+"].address'>"+
			          " <c:forEach  items='${city }' var='city'>"+
				         "<option value='${city.id }'  >${city.name }</option>"+
			         "</c:forEach>"+
			         
			         
			        " </select></div>"+
			         "<div class='cue'>  </div>"+
		        "</div>"+        
			" </li> "+
			 
			" <li class='col-md-3 col-sm-6 col-xs-12'>"+
			   "<span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'><i class='red'>*</i> 生产公司详细地址</span>"+
			   " <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>"+
		        "<input type='text' name='addressList["+ind+"].detailAddress'  value=''>"+
		        "<span class='add-on cur_point'>i</span>"+
		         "<div class='cue'>  </div>"+
		       "</div>"+
			 "</li>"+
			 "<li class='col-md-3 col-sm-6 col-xs-12'>"+
			 "	<span class='col-md-12 col-xs-12 col-sm-12 padding-left-5 white'>操作</span>"+
				"<div class='col-md-12 col-xs-12 col-sm-12 p0 mb25 h30'>"+
				"	<input type='button' onclick='increaseAddress(this)' class='btn' value='十'/>"+
				"	<input type='button' onclick='delAddress(this)'class='btn' value='一'/>"+
				"</div></li>"
				);
	}
	
	function delAddress(obj){
	 	var tag=$(obj).parent().parent();
 	    var li_1=$(obj).parent().parent().prev();  
		$(li_1).prev().prev().remove(); //邮编
		$(li_1).prev().remove();//省市
		$(li_1).remove();//详细地址
		$(tag).remove(); //按钮  
	}
	
	function addBranch(obj){
		var li=$(obj).parent().parent().next();
		var inde=$("#branchIndex").val();
		inde++;
		$("#branchIndex").val(inde);
		$(li).after("<li class='col-md-3 col-sm-6 col-xs-12'>"+
				" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'>所在国家（地区）</span>"+
				"  <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>"+
		    	 " <input name='branchList["+inde+"].country' id='sup_country' type='text' value='' />"+
		    	 	"  <span class='add-on cur_point'>i</span>"+
			        " </div>"+
			 " </li>"+
			
			 "  <li class='col-md-3 col-sm-6 col-xs-12'>"+
			 " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'>详细地址</span>"+
				" <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>"+
		    	 " <input type='text' name='branchList["+inde+"].detailAddress'  id='sup_branchAddress' value='' />"+
		    	 	"  <span class='add-on cur_point'>i</span>"+
			        " </div>"+
	       	 " </li>"+
			 "  <li class='col-md-3 col-sm-6 col-xs-12'>"+
			 " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'>机构名称</span>"+
				" <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>"+
		    	 " <input type='text' name='branchList["+inde+"].organizationName' id='sup_branchName'  value='' />"+
		    	 	"   <span class='add-on cur_point'>i</span>"+
			        "   </div>"+
	       	 "  </li>"+
			 " <li class='col-md-3 col-sm-6 col-xs-12'>"+
			 " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5 white'>操作</span>"+
				 	" <div class='col-md-12 col-xs-12 col-sm-12 p0 mb25 h30'>"+
					" <input type='button' onclick='addBranch(this)' class='btn' value='十'/>"+
					" <input type='button' onclick='delBranch(this)'class='btn' value='一'/>"+
					" </div>"+
					" </li>"+
			
			"  <li  class='col-md-12 col-xs-12 col-sm-12 mb25'>"+
			  " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'> 生产经营范围</span>"+
		    	" <div class='col-md-12 col-xs-12 col-sm-12 p0'>"+
		    	"  <textarea class='col-md-12 col-xs-12 col-sm-12 h80'  id='sup_businessScope' title='不超过80个字' name='branchList["+inde+"].businessSope'></textarea>"+
			       " </div>"+
			" </li>");
		
	}
	
	function delBranch(obj){
		var li=$(obj).parent().parent().next();
		var pre=$(obj).parent().parent().prev();
		$(li).remove();
		$(pre).prev().prev().remove();
		$(pre).prev().remove();
		$(pre).remove();
		$(obj).parent().parent().remove();
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
    	  <h2 class="count_flow"> <i>1</i> 基本信息</h2>
    	  	<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font">
	 			<legend>企业信息</legend>
	 			<ul class="list-unstyled" style="font-size: 14">
				<li class="col-md-3 col-sm-6 col-xs-12 pl10">
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
			        <input type="text" name="website"  value="${currSupplier.website}">
			        <span class="add-on cur_point">i</span>
			        <span class="input-tip">例如：http://www.baidu.com</span>
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
						    <option value="${obj.id }" <c:if test="${obj.id==currSupplier.businessType }" >selected="selected"</c:if> >${obj.name }</option>
						</c:forEach>
						<!-- <option>外资企业</option>
						<option>民营企业</option>
						<option>股份制企业</option>
						<option>私营企业</option> -->
					</select>
			       </div>
				 </li>
			<%-- 	 
				  <li class="col-md-3 col-sm-6 col-xs-12">
					   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 生产经营地址</span>
					   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
				        <input type="text" name="businessAddress" value="${currSupplier.businessAddress}" />
				        <span class="add-on cur_point">i</span>
				        <div class="cue"> ${err_bAddress } </div>
			       	   </div>
				  </li> --%> 
		    
		    
			<%-- 	  <li class="col-md-3 col-sm-6 col-xs-12">
				    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 公司地址</span>
				    <div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="root_area_select_id" onchange="loadChildren(this)">
				     
				         <c:forEach  items="${privnce }" var="prin">
					         <c:if test="${prin.id==area.parentId }">
					          <option value="${prin.id }" selected="selected" >${prin.name }</option>
					         </c:if>
				           <c:if test="${prin.id!=area.parentId }">
					          <option value="${prin.id }"  >${prin.name }</option>
					         </c:if>
				         </c:forEach>
				         
				         
				         </select></div> 
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="children_area_select_id" name="address" >
				         
				           <c:forEach  items="${city }" var="city">
					         <c:if test="${city.id==currSupplier.address }">
					          <option value="${city.id }" selected="selected" >${city.name }</option>
					         </c:if>
				           <c:if test="${city.id!=currSupplier.address }">
					          <option value="${city.id }"  >${city.name }</option>
					         </c:if>
				         </c:forEach>
				         
				         
				         </select></div>
				         <div class="cue"> ${err_msg_address } </div>
			        </div>		        
				 </li>   --%>
				 
				  <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 基本账户开户行</span>
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
			        <div class="cue"> ${err_msg_postCode } </div>
			       </div>
				 </li> 
				
				
				<li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
				   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 基本账户开户许可证</span> 
				   <div class="col-md-12 col-sm-12 col-xs-12 p0">
					 <u:upload id="bank_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" auto="true" /> 
				     <u:show showId="bank_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" />
				   </div>
				  <%--  <div class="cue"> ${err_bearch } </div> --%>
				</li>
			
			
				 
	<%-- 			 <li id="breach_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
				   <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 基本账户开户许可证</span> 
				   <div class="col-md-6 col-sm-12 col-xs-12 p0">
				     <u:upload id="bank_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" auto="true" /> 
				     <u:show showId="bank_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" />
				   </div>
				    <div class="cue"> ${err_bearch } </div>
				</li> --%>
			
			
			<%-- 	 
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 col-xs-12 col-sm-12  padding-left-5"><i class="red">*</i>邮编</span>
				   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			        <input type="text" name="postCode" value="${currSupplier.postCode}" />
			        <span class="add-on cur_point">i</span>
			         <div class="cue"> ${err_msg_bankAccount } </div>
			       </div>
				 </li>   --%>
				 
				 
			<%-- 	<li class="col-md-12 col-xs-12 col-sm-12 mb25">
			    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>详细地址</span>
			    	<div class="col-md-12 col-xs-12 col-sm-12 p0">
				       <textarea class="col-md-12 col-xs-12 col-sm-12 h130"  name="detailAddress">${currSupplier.detailAddress}</textarea>
				       <div class="cue"> ${err_detailAddress } </div>
		       	    </div>
				</li>  --%>
			</ul>
	       </fieldset>
	       
	       
	           <fieldset class="col-md-12 col-xs-12 col-sm-12 border_font mt20">
	 			<legend>地址信息</legend>
	 			   <ul class="list-unstyled" style="font-size: 14">
	 		   		<li class="col-md-3 col-sm-6 col-xs-12 pl10">
					   <span class="col-md-12 col-xs-12 col-sm-12  padding-left-5"><i class="red">*</i>注册地址邮编</span>
					   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
				        <input type="text" name="postCode" value="${currSupplier.postCode}" />
				        <span class="add-on cur_point">i</span>
				         <div class="cue"> ${err_msg_bankAccount } </div>
				       </div>
					</li> 
				 
				 	<li class="col-md-3 col-sm-6 col-xs-12">
				    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>注册公司地址</span>
				    	<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="root_area_select_id" onchange="loadChildren(this)">
				    	 <option value="">请选择</option>
				         <c:forEach  items="${privnce }" var="prin">
					         <c:if test="${prin.id==area.parentId }">
					          <option value="${prin.id }" selected="selected" >${prin.name }</option>
					         </c:if>
				           <c:if test="${prin.id!=area.parentId }">
					          <option value="${prin.id }"  >${prin.name }</option>
					         </c:if>
				         </c:forEach>
				         
				         </select></div> 
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="children_area_select_id" name="address" >
				         
				           <c:forEach  items="${city }" var="city">
					         <c:if test="${city.id==currSupplier.address }">
					          <option value="${city.id }" selected="selected" >${city.name }</option>
					         </c:if>
				           <c:if test="${city.id!=currSupplier.address }">
					          <option value="${city.id }"  >${city.name }</option>
					         </c:if>
				         </c:forEach>
				         </select></div>
				         <div class="cue"> ${err_msg_address } </div>
			        </div>		        
				 </li>  
				 
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 注册公司详细地址</span>
				    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			        <input type="text" name="detailAddress"  value="${currSupplier.detailAddress}">
			        <span class="add-on cur_point">i</span>
			         <div class="cue">${err_detailAddress }  </div>
			       </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				 	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
					<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
					<!-- 	<input type="button" class="btn" value="新增"/>
						<input type="button" class="btn" value="删除"/> -->
					</div>
				 </li>
				 
				 <c:forEach items="${currSupplier.addressList}" var="addr" varStatus="vs">
				 <li class="col-md-3 col-sm-6 col-xs-12 pl10">
					   <span class="col-md-12 col-xs-12 col-sm-12  padding-left-5"><i class="red">*</i> 生产经营地址邮编</span>
					   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
					   <input type="hidden" name="addressList[${vs.index }].id" value="${addr.id}" />
					<%--     <c:if test="${addr.code!=null}"> --%>
					     <input type="text" name="addressList[${vs.index }].code" value="${addr.code}" />
					 <%--   </c:if>
					   <c:if test="${addr.code==null}">
				        <input type="text" name="addressList[0].code" value="${addr.code}" />
				        </c:if> --%>
				        <span class="add-on cur_point">i</span>
				         <div class="cue">   </div>
				       </div>
					</li> 
				 
				 	<li class="col-md-3 col-sm-6 col-xs-12">
				    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 生产经营地址</span>
				    	<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="root_area_select_id" onchange="loadChildren(this)" name="addressList[${vs.index }].provinceId" >
				     	 <option value="">请选择</option>
				         <c:forEach  items="${privnce }" var="prin">
					         <c:if test="${prin.id==addr.provinceId }">
					          <option value="${prin.id }" selected="selected" >${prin.name }</option>
					         </c:if>
				           <c:if test="${prin.id!=addr.provinceId }">
					          <option value="${prin.id }"  >${prin.name }</option>
					         </c:if>
				         </c:forEach>
				         
				         </select></div> 
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0">
				         <select id="children_area_select_id"  name="addressList[${vs.index }].address" >
				           <c:forEach  items="${addr.areaList }" var="city">
					         <c:if test="${city.id==addr.address }">
					          <option value="${city.id }" selected="selected" >${city.name }</option>
					         </c:if>
				           <c:if test="${city.id!=addr.address }">
					          <option value="${city.id }"  >${city.name }</option>
					         </c:if>
				         </c:forEach>
				         </select>
				         </div>
				         <div class="cue">  </div>
			        </div>		        
				 </li>  
				 
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>生产经营详细地址</span>
				    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
				<%--     <c:if test="${addr.detailAddress!=null  }"> --%>
			        <input type="text" name="addressList[${vs.index }].detailAddress"  value="${addr.detailAddress }">
			  <%--       </c:if>
			        <c:if test="${addr.detailAddress==null }">
			           <input type="text" name="addressList[0].detailAddress"  value="${addr.detailAddress }">
			         </c:if> --%>
			        <span class="add-on cur_point">i</span>
			         <div class="cue">  </div>
			       </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				 	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
					<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
						<input type="button" onclick="increaseAddress(this)" class="btn" value="十"/>
						<input type="button" onclick="delAddress(this)"class="btn" value="一"/>
					</div>
				 </li>
				</c:forEach> 
				 
				<%--  <li class="col-md-12 col-xs-12 col-sm-12 mb25">
			    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>详细地址</span>
			    	<div class="col-md-12 col-xs-12 col-sm-12 p0">
				       <textarea class="col-md-12 col-xs-12 col-sm-12 h130"  name="detailAddress">${currSupplier.detailAddress}</textarea>
				       <div class="cue"> ${err_detailAddress } </div>
		       	    </div>
				</li>  --%>	 
		 	</ul>	 
	      </fieldset>
	      
	       <fieldset class="col-md-12 col-xs-12 col-sm-12 border_font mt20">
	 		  <legend>资质资信</legend>
	 		   <ul class="list-unstyled" style="font-size: 14">
				<li class="col-md-6 col-sm-12 col-xs-12 mb25 pl10">
				    <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三个月完税凭证</span> 
				    <div class="col-md-6 col-sm-12 col-xs-12 p0">
		    		  <u:upload id="taxcert_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" auto="true" /> 
		        	  <u:show showId="taxcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" />
			        </div>
			        <div class="cue"> ${err_taxCert } </div>
			    </li> 
				
				<li id="bill_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
				   <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三年银行基本账户年末对账单</span> 
				   <div class="col-md-6 col-sm-12 col-xs-12 p0">
					   <u:upload id="billcert_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" auto="true" /> 
					   <u:show showId="billcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
				   </div>
				    <div class="cue"> ${err_bil } </div>
				</li>
												
			   <li id="security_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
			      <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三个月缴纳社会保险金凭证</span> 
			      <div class="col-md-6 col-sm-12 col-xs-12 p0">
			        <u:upload id="curitycert_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" auto="true" /> 
			        <u:show showId="curitycert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" />
			      </div>
			      <div class="cue"> ${err_security } </div>
			   </li>
												
			 <li id="breach_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
			   <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三年内无重大违法记录声明</span> 
			   <div class="col-md-6 col-sm-12 col-xs-12 p0">
			     <u:upload id="bearchcert_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" auto="true" /> 
			     <u:show showId="bearchcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
			   </div>
			   <div class="cue"> ${err_bearch } </div>
			</li>
			
		<%-- 	 <li id="breach_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
			   <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 基本账户开户许可证</span> 
			   <div class="col-md-6 col-sm-12 col-xs-12 p0">
			     <u:upload id="bank_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" auto="true" /> 
			     <u:show showId="bank_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" />
			   </div>
			   <div class="cue"> ${err_bearch } </div>
			</li> --%>
		  </ul>						
		</fieldset>
		<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
	 	  <legend>法定代表信息</legend>
	 	  <ul class="list-unstyled" style="font-size: 14">
		 	 <li class="col-md-3 col-sm-6 col-xs-12 pl10">
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
		    
		    
		     <li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证正面</span> 
			   <div class="col-md-12 col-sm-12 col-xs-12 p0 h30">
			     <u:upload id="bearchcert_up_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" auto="true" /> 
			     <u:show showId="bearchcert_up_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
			   </div>
			  <%--  <div class="cue"> ${err_bearch } </div> --%>
			</li>
			
			 <li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证反面</span> 
			   <div class="col-md-12 col-sm-12 col-xs-12 p0 h30">
			     <u:upload id="identity_down_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" auto="true" /> 
			     <u:show showId="identity_down_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" />
			   </div>
			  <%--  <div class="cue"> ${err_bearch } </div> --%>
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
		    
		 <%--   <li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证正面</span> 
			   <div class="col-md-12 col-sm-12 col-xs-12 p0">
			     <u:upload id="bearchcert_up_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" auto="true" /> 
			     <u:show showId="bearchcert_up_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
			   </div>
			   <div class="cue"> ${err_bearch } </div> 
			</li>--%>
			
			  <%--  <li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证反面</span> 
			   <div class="col-md-12 col-sm-12 col-xs-12 p0">
			     <u:upload id="identity_down_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" auto="true" /> 
			     <u:show showId="identity_down_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" />
			   </div>
			 <div class="cue"> ${err_bearch } </div> 
			</li>--%>
		  </ul>
	    </fieldset>
	    
	    <fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
	 	  <legend>注册联系人</legend>
	 	   <ul class="list-unstyled" style="font-size: 14">
		    <li class="col-md-3 col-sm-6 col-xs-12 pl10">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 姓名</span>
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
		        <input type="text" name="mobile" value="${currSupplier.mobile}" />
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
				    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>地址</span>
				    	<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="root_area_select_id" name="concatProvince" onchange="loadChildren(this)">
				     
				         <c:forEach  items="${privnce }" var="prin">
					         <c:if test="${prin.id==currSupplier.concatProvince }">
					          <option value="${prin.id }" selected="selected" >${prin.name }</option>
					         </c:if>
				           <c:if test="${prin.id!=currSupplier.concatProvince }">
					          <option value="${prin.id }"  >${prin.name }</option>
					         </c:if>
				         </c:forEach>
				         
				         </select></div> 
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="children_area_select_id" name="concatCity" >
				         
				           <c:forEach  items="${currSupplier.concatCityList }" var="city">
					         <c:if test="${city.id==currSupplier.concatCity}">
					          <option value="${city.id }" selected="selected" >${city.name }</option>
					         </c:if>
				           <c:if test="${city.id!=currSupplier.concatCity}">
					          <option value="${city.id }"  >${city.name }</option>
					         </c:if>
				         </c:forEach>
				         </select></div>
				         <div class="cue">${err_city} </div>
			        </div>		        
				 </li>
				 
				 
	    	<li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 详细地址</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="contactAddress" value="${currSupplier.contactAddress}" />
		        <span class="add-on cur_point">i</span>
		         <div class="cue"> ${err_conAddress } </div>
	       	   </div>
		    </li>
		  </ul>   
	    </fieldset>
	    
	    
	    <fieldset class="col-md-12 border_font mt20">
	 	  <legend>军队业务联系人</legend>
	 	    <ul class="list-unstyled" style="font-size: 14"> 
	 		 <li class="col-md-3 col-sm-6 col-xs-12 pl10">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 姓名</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="armyBusinessName" value="${currSupplier.armyBusinessName}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_armName} </div>
	       	   </div>
		   	 </li> 
		    
		   	 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 传真电话</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="armyBusinessFax" value="${currSupplier.armyBusinessFax}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_armFax } </div>
	       	   </div>
		     </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 固定电话</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="armyBuinessMobile" value="${currSupplier.armyBuinessMobile}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_armMobile } </div>
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 手机</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="armyBuinessTelephone" value="${currSupplier.armyBuinessTelephone}" />
		        <span class="add-on cur_point">i</span>
			    <div class="cue"> ${err_armTelephone } </div>		        
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 邮箱</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="armyBuinessEmail" value="${currSupplier.armyBuinessEmail}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_armEmail } </div>
	       	   </div>
		    </li> 
		    
		    
		      	<li class="col-md-3 col-sm-6 col-xs-12">
				    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>地址</span>
				    	<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="root_area_select_id" name="armyBuinessProvince" onchange="loadChildren(this)">
				     
				         <c:forEach  items="${privnce }" var="prin">
					         <c:if test="${prin.id==currSupplier.armyBuinessProvince }">
					          <option value="${prin.id }" selected="selected" >${prin.name }</option>
					         </c:if>
				           <c:if test="${prin.id!=currSupplier.armyBuinessProvince }">
					          <option value="${prin.id }"  >${prin.name }</option>
					         </c:if>
				         </c:forEach>
				         
				         </select></div> 
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="children_area_select_id" name="armyBuinessCity" >
				         
				           <c:forEach  items="${currSupplier.armyCity }" var="city">
					         <c:if test="${city.id==currSupplier.armyBuinessCity }">
					          <option value="${city.id }" selected="selected" >${city.name }</option>
					         </c:if>
				           <c:if test="${city.id!=currSupplier.armyBuinessCity }">
					          <option value="${city.id }"  >${city.name }</option>
					         </c:if>
				         </c:forEach>
				         </select></div>
				         <div class="cue"> ${err_armCity }</div>
			        </div>		        
				 </li>
				 
				 
	        <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 详细地址</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="armyBuinessAddress" value="${currSupplier.armyBuinessAddress}" />
		        <span class="add-on cur_point">i</span>
		         <div class="cue"> ${err_armAddress } </div>
	       	   </div>
		    </li> 
		  </ul>
	 	</fieldset>
	    
	    <fieldset class="col-md-12 border_font mt20">
	 	  <legend>营业执照</legend>
	 	   <ul class="list-unstyled" style="font-size: 14">
		    <li class="col-md-3 col-sm-6 col-xs-12 pl10">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 统一社会信用代码</span>
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
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 注册资本（万元）</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="registFund" onkeyup="checknums(this)" value="${currSupplier.registFund}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue" id="err_fund"> ${err_fund } </div>
	       	   </div>
		    </li> 
		    
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业有效期</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			   	<fmt:formatDate value="${currSupplier.businessStartDate}" pattern="yyyy-MM-dd" var="businessStartDate" />
		        <input type="text" readonly="readonly" onClick="WdatePicker()" name="businessStartDate" value="${businessStartDate}"  />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_sDate } </div>
	       	   </div>
		    </li> 
		    
	<%-- 	    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业截止时间</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			   	<fmt:formatDate value="${currSupplier.businessEndDate}" pattern="yyyy-MM-dd" var="businessEndDate" />
		        <input type="text" readonly="readonly" onClick="WdatePicker()" name="businessEndDate" value="${businessEndDate}"   />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_eDate } </div>
	       	   </div>
		    </li>  --%>
		    
		<%--     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 生产经营地址</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="businessAddress" value="${currSupplier.businessAddress}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_bAddress } </div>
	       	   </div>
		    </li>  --%>
		    
		    <li class="col-md-3 col-sm-6 col-xs-12 pl10">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 邮编</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			      <input type="text" name="businessPostCode" onkeyup="checknums(this)" value="${currSupplier.businessPostCode}" />
			      <span class="add-on cur_point">i</span>
			       <div class="cue"> ${err_bCode } </div>
	       	   </div>
	       	 
		    </li> 
		    
		<%--     <li class="col-md-3 col-sm-6 col-xs-12">
		     <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照:</span> 
				   <div class="col-md-12 col-sm-12 col-xs-12 p0 mb25">
					 <u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /> 
		   	   		 <u:upload id="business_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
				   </div>
				   <div class="cue"> ${err_business } </div>
		    </li>  --%>
		    
		    	<li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
				   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照:</span> 
				   <div class="col-md-12 col-sm-12 col-xs-12 p0 h30">
					 <u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /> 
		   	   		 <u:upload id="business_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
				   </div>
				  <%--  <div class="cue"> ${err_bearch } </div> --%>
				</li>
				
		    
		    <li class="col-md-12 col-xs-12 col-sm-12 mb25">
		    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 营业范围</span>
		    	<div class="col-md-12 col-xs-12 col-sm-12 p0">
			       <textarea class="col-md-12 col-xs-12 col-sm-12 h80" title="不超过80个字" name="businessScope">${currSupplier.bankName}</textarea>
	       	    </div>
			</li>
		  </ul> 
		</fieldset>
		
		
		<fieldset class="col-md-12 border_font mt20">
	 	  <legend>境外分支</legend>
		   <ul class="list-unstyled" style="font-size: 14">
     		<!-- <li class="col-md-3 col-sm-6 col-xs-12 pl10">
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red"></i>境外分支机构</span>
		    	<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
		    	   <select  name="overseasBranch" onchange="dis(this)"  id="overseas_branch_select_id">
		    	        <option value="0">无</option>
						<option value="1">有</option>
					</select>
	       	    </div>
			 </li>  -->
			
			<c:forEach items="${currSupplier.branchList }" var="bran"  varStatus="vs">
		  	 <li class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">所在国家（地区）</span>
		    	 <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		    	 <%-- 	<input name="branchList[${vs.index }].country" id="sup_country" type="text" value="${bran.country}" />
			        <span class="add-on cur_point">i</span> --%>
		  <select name="branchList[${vs.index }].country"  id="overseas_branch_select_id">	        
				<option value="AL" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >阿尔巴尼亚</option>  
		        <option value="DZ" <c:if test="${bran.country=='DZ'}">selected='selected' </c:if> >阿尔及利亚</option>  
		        <option value="AF" <c:if test="${bran.country=='AF'}">selected='selected' </c:if> >阿富汗</option>  
		        <option value="AR" <c:if test="${bran.country=='AR'}">selected='selected' </c:if> >阿根廷</option>  
<%-- 		        <option value="AE" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >阿拉伯联合酋长国</option>  
		        <option value="AW" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >阿鲁巴</option>  
		        <option value="OM" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >阿曼</option>  
		        <option value="AZ" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >阿塞拜疆</option>  
		        <option value="EG" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >埃及</option>  
		        <option value="ET" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >埃塞俄比亚</option>  
		        <option value="IE" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >爱尔兰</option>  
		        <option value="EE" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >爱沙尼亚</option>  
		        <option value="AD" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >安道尔</option>  
		        <option value="AO" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >安哥拉</option>  
		        <option value="AI" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >安圭拉岛</option>  
		        <option value="AG" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >安提瓜和巴布达</option>   --%>
		        <option value="AT" <c:if test="${bran.country=='AT'}">selected='selected' </c:if> >奥地利</option>  
		        <option value="AX" <c:if test="${bran.country=='AX'}">selected='selected' </c:if> >奥兰岛</option>  
		        <option value="AU" <c:if test="${bran.country=='AU'}">selected='selected' </c:if> >澳大利亚</option>  
		        <option value="MO" <c:if test="${bran.country=='MO'}">selected='selected' </c:if> >澳门特别行政区</option>  
		        <option value="BB" <c:if test="${bran.country=='BB'}">selected='selected' </c:if> >巴巴多斯</option>  
		        <option value="PG" <c:if test="${bran.country=='PG'}">selected='selected' </c:if> >巴布亚新几内亚</option>  
		        <option value="BS" <c:if test="${bran.country=='BS'}">selected='selected' </c:if> >巴哈马</option>  
		        <option value="PK" <c:if test="${bran.country=='PK'}">selected='selected' </c:if> >巴基斯坦</option>  
		        <option value="PY" <c:if test="${bran.country=='PY'}">selected='selected' </c:if> >巴拉圭</option>  
		        <option value="PS" <c:if test="${bran.country=='PS'}">selected='selected' </c:if> >巴勒斯坦民族权力机构</option>  
		        <option value="BH" <c:if test="${bran.country=='BH'}">selected='selected' </c:if> >巴林</option>  
		        <option value="PA" <c:if test="${bran.country=='PA'}">selected='selected' </c:if> >巴拿马</option>  
		        <option value="BR" <c:if test="${bran.country=='BR'}">selected='selected' </c:if> >巴西</option>  
		        <option value="BY" <c:if test="${bran.country=='BY'}">selected='selected' </c:if> >白俄罗斯</option>  
		        <option value="BM" <c:if test="${bran.country=='BM'}">selected='selected' </c:if> >百慕大群岛</option>  
		        <option value="BG" <c:if test="${bran.country=='BG'}">selected='selected' </c:if> >保加利亚</option>  
		        <option value="MP"  <c:if test="${bran.country=='MP'}">selected='selected' </c:if> >北马里亚纳群岛</option>  
		        <option value="BJ" <c:if test="${bran.country=='BJ'}">selected='selected' </c:if> >贝宁</option>  
		        <option value="BE" <c:if test="${bran.country=='BE'}">selected='selected' </c:if> >比利时</option>  
		        <option value="IS" <c:if test="${bran.country=='IS'}">selected='selected' </c:if> >冰岛</option>  
		        <option value="PR" <c:if test="${bran.country=='PR'}">selected='selected' </c:if> >波多黎各</option>  
<%-- 		        <option value="PL" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >波兰</option>  
		        <option value="BA" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >波斯尼亚和黑塞哥维那</option>  
		        <option value="BO" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >玻利维亚</option>  
		        <option value="BZ" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >伯利兹</option>  
		        <option value="BW" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >博茨瓦纳</option>  
		        <option value="BQ" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >博内尔</option>   --%>
		        <option value="BT" <c:if test="${bran.country=='BT'}">selected='selected' </c:if> >不丹</option>  
		        <option value="BF" <c:if test="${bran.country=='BF'}">selected='selected' </c:if> >布基纳法索</option>  
		        <option value="BI" <c:if test="${bran.country=='BI'}">selected='selected' </c:if> >布隆迪</option>  
		        <option value="BV" <c:if test="${bran.country=='BV'}">selected='selected' </c:if> >布韦岛</option>  
		        <option value="KP" <c:if test="${bran.country=='KP'}">selected='selected' </c:if> >朝鲜</option>  
		        <option value="GQ" <c:if test="${bran.country=='GQ'}">selected='selected' </c:if> >赤道几内亚</option>  
		        <option value="DK" <c:if test="${bran.country=='DK'}">selected='selected' </c:if> >丹麦</option>  
		        <option value="DE" <c:if test="${bran.country=='DDE'}">selected='selected' </c:if> >德国</option>  
		        <option value="TL"  <c:if test="${bran.country=='TL'}">selected='selected' </c:if> >东帝汶</option>  
		        <option value="TG" <c:if test="${bran.country=='TG'}">selected='selected' </c:if> >多哥</option>  
		        <option value="DO" <c:if test="${bran.country=='DO'}">selected='selected' </c:if> >多米尼加共和国</option>  
		        <option value="DM" <c:if test="${bran.country=='DM'}">selected='selected' </c:if> >多米尼克</option>  
		        <option value="RU" <c:if test="${bran.country=='RU'}">selected='selected' </c:if> >俄罗斯</option>  
		        <option value="EC" <c:if test="${bran.country=='EC'}">selected='selected' </c:if> >厄瓜多尔</option>  
		        <option value="ER" <c:if test="${bran.country=='ER'}">selected='selected' </c:if> >厄立特里亚</option>  
		        <option value="FR" <c:if test="${bran.country=='FR'}">selected='selected' </c:if> >法国</option>  
<%-- 		        <option value="FO" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >法罗群岛</option>  
		        <option value="PF" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >法属波利尼西亚</option>  
		        <option value="GF" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >法属圭亚那</option>  
		        <option value="TF" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >法属南极地区</option>  
		        <option value="VA" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >梵蒂冈城</option>  
		        <option value="PH" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >菲律宾</option>  
		        <option value="FJ" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >斐济群岛</option>  
		        <option value="FI" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >芬兰</option>  
		        <option value="CV" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >佛得角</option>  
		        <option value="FK" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >福克兰群岛(马尔维纳斯群岛)</option>  
		        <option value="GM" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >冈比亚</option>  
		        <option value="CD" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >刚果(DRC)</option>  
		        <option value="CG" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >刚果共和国</option>  
		        <option value="CO" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >哥伦比亚</option>  
		        <option value="CR" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >哥斯达黎加</option>  
		        <option value="GG" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >格恩西岛</option>  
		        <option value="GD" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >格林纳达</option>  
		        <option value="GL" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >格陵兰</option>  
		        <option value="GE" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >格鲁吉亚</option>  
		        <option value="CU" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >古巴</option>   --%>
		        <option value="GP" <c:if test="${bran.country=='GP'}">selected='selected' </c:if> >瓜德罗普岛</option>  
		        <option value="GU" <c:if test="${bran.country=='GU'}">selected='selected' </c:if> >关岛</option>  
		        <option value="GY" <c:if test="${bran.country=='GY'}">selected='selected' </c:if> >圭亚那</option>  
		        <option value="KZ" <c:if test="${bran.country=='KZ'}">selected='selected' </c:if> >哈萨克斯坦</option>  
		        <option value="HT" <c:if test="${bran.country=='HT'}">selected='selected' </c:if> >海地</option>  
		        <option value="KR" <c:if test="${bran.country=='KR'}">selected='selected' </c:if> >韩国</option>  
		        <option value="NL" <c:if test="${bran.country=='NL'}">selected='selected' </c:if> >荷兰</option>  
		        <option value="HM" <c:if test="${bran.country=='HM'}">selected='selected' </c:if> >赫德和麦克唐纳群岛</option>  
		        <option value="ME" <c:if test="${bran.country=='ME'}">selected='selected' </c:if> >黑山共和国</option>  
		        <option value="HN" <c:if test="${bran.country=='MN'}">selected='selected' </c:if> >洪都拉斯</option>  
		        <option value="KI" <c:if test="${bran.country=='KI'}">selected='selected' </c:if> >基里巴斯</option>  
		        <option value="DJ" <c:if test="${bran.country=='DJ'}">selected='selected' </c:if> >吉布提</option>  
		        <option value="KG" <c:if test="${bran.country=='KG'}">selected='selected' </c:if> >吉尔吉斯斯坦</option>  
		        <option value="GN" <c:if test="${bran.country=='GN'}">selected='selected' </c:if> >几内亚</option>  
		        <option value="GW" <c:if test="${bran.country=='GW'}">selected='selected' </c:if> >几内亚比绍</option>  
		        <option value="CA" <c:if test="${bran.country=='CA'}">selected='selected' </c:if> >加拿大</option>  
		        <option value="GH" <c:if test="${bran.country=='GH'}">selected='selected' </c:if> >加纳</option>  
		        <option value="GA" <c:if test="${bran.country=='GA'}">selected='selected' </c:if> >加蓬</option>  
		        <option value="KH" <c:if test="${bran.country=='KH'}">selected='selected' </c:if> >柬埔寨</option>  
		        <option value="CZ" <c:if test="${bran.country=='CZ'}">selected='selected' </c:if> >捷克共和国</option>  
		        <option value="ZW" <c:if test="${bran.country=='ZW'}">selected='selected' </c:if> >津巴布韦</option>  
		        <option value="CM" <c:if test="${bran.country=='CM'}">selected='selected' </c:if> >喀麦隆</option>  
		        <option value="QA" <c:if test="${bran.country=='QA'}">selected='selected' </c:if> >卡塔尔</option>  
		        <option value="KY" <c:if test="${bran.country=='KY'}">selected='selected' </c:if> >开曼群岛</option>  
		        <option value="CC" <c:if test="${bran.country=='CC'}">selected='selected' </c:if> >科科斯群岛(基灵群岛)</option>  
		        <option value="KM" <c:if test="${bran.country=='KM'}">selected='selected' </c:if> >科摩罗联盟</option>  
		        <option value="CI" <c:if test="${bran.country=='CI'}">selected='selected' </c:if> >科特迪瓦共和国</option>  
		        <option value="KW" <c:if test="${bran.country=='KW'}">selected='selected' </c:if> >科威特</option>  
<%-- 		        <option value="HR" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >克罗地亚</option>  
		        <option value="KE" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >肯尼亚</option>  
		        <option value="CK" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >库可群岛</option>  
		        <option value="CW" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >库拉索</option>  
		        <option value="LV" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >拉脱维亚</option>  
		        <option value="LS" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >莱索托</option>   --%>
		        <option value="LA" <c:if test="${bran.country=='LA'}">selected='selected' </c:if> >老挝</option>  
		        <option value="LB" <c:if test="${bran.country=='LB'}">selected='selected' </c:if> >黎巴嫩</option>  
		        <option value="LT" <c:if test="${bran.country=='LT'}">selected='selected' </c:if> >立陶宛</option>  
		        <option value="LR" <c:if test="${bran.country=='LR'}">selected='selected' </c:if> >利比里亚</option>  
		        <option value="LY" <c:if test="${bran.country=='LY'}">selected='selected' </c:if> >利比亚</option>  
		        <option value="LI" <c:if test="${bran.country=='LI'}">selected='selected' </c:if> >列支敦士登</option>  
		        <option value="RE" <c:if test="${bran.country=='RE'}">selected='selected' </c:if> >留尼汪岛</option>  
		        <option value="LU" <c:if test="${bran.country=='LU'}">selected='selected' </c:if> >卢森堡</option>  
		        <option value="RW" <c:if test="${bran.country=='RW'}">selected='selected' </c:if> >卢旺达</option>  
		        <option value="RO" <c:if test="${bran.country=='RO'}">selected='selected' </c:if> >罗马尼亚</option>  
		        <option value="MG" <c:if test="${bran.country=='MG'}">selected='selected' </c:if> >马达加斯加</option>  
		        <option value="IM" <c:if test="${bran.country=='IM'}">selected='selected' </c:if> >马恩岛</option>  
		        <option value="MV" <c:if test="${bran.country=='MV'}">selected='selected' </c:if> >马尔代夫</option>  
		        <option value="MT" <c:if test="${bran.country=='MT'}">selected='selected' </c:if> >马耳他</option>  
		        <option value="MW" <c:if test="${bran.country=='MW'}">selected='selected' </c:if> >马拉维</option>  
		        <option value="MY" <c:if test="${bran.country=='MY'}">selected='selected' </c:if> >马来西亚</option>  
		        <option value="ML" <c:if test="${bran.country=='ML'}">selected='selected' </c:if> >马里</option>  
		        <option value="MK" <c:if test="${bran.country=='MK'}">selected='selected' </c:if> >马其顿, 前南斯拉夫共和国</option>  
		        <option value="MH" <c:if test="${bran.country=='MH'}">selected='selected' </c:if> >马绍尔群岛</option>  
		        <option value="MQ" <c:if test="${bran.country=='MQ'}">selected='selected' </c:if> >马提尼克岛</option>  
		        <option value="YT" <c:if test="${bran.country=='YT'}">selected='selected' </c:if> >马约特岛</option>  
		        <option value="MU" <c:if test="${bran.country=='MU'}">selected='selected' </c:if> >毛里求斯</option>  
		        <option value="MR" <c:if test="${bran.country=='MR'}">selected='selected' </c:if> >毛利塔尼亚</option>  
		        <option value="US" <c:if test="${bran.country=='US'}">selected='selected' </c:if> >美国</option>  
<%-- 		        <option value="AS" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >美属萨摩亚</option>  
		        <option value="UM" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >美属外岛</option>  
		        <option value="VI" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >美属维尔京群岛</option>  
		        <option value="MN" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >蒙古</option>   --%>
		      <%--   <option value="MS" <c:if test="${bran.country=='MS'}">selected='selected' </c:if> >蒙特塞拉特</option>  
		        <option value="BD" <c:if test="${bran.country==''}">selected='selected' </c:if> >孟加拉国</option>  
		        <option value="PE" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >秘鲁</option>  
		        <option value="FM" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >密克罗尼西亚</option>  
		        <option value="MM" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >缅甸</option>  
		        <option value="MD" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >摩尔多瓦</option>  
		        <option value="MA" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >摩洛哥</option>  
		        <option value="MC" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >摩纳哥</option>  
		        <option value="MZ" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >莫桑比克</option>  
		        <option value="MX" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >墨西哥</option>  
		        <option value="NA" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >纳米比亚</option>  
		        <option value="ZA" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >南非</option>  
		        <option value="AQ" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >南极洲</option>  
		        <option value="GS" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >南乔治亚和南德桑威奇群岛</option>  
		        <option value="NR" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >瑙鲁</option>   --%>
		        <option value="NP" <c:if test="${bran.country=='NP'}">selected='selected' </c:if> >尼泊尔</option>  
		        <option value="NI" <c:if test="${bran.country=='NI'}">selected='selected' </c:if> >尼加拉瓜</option>  
		        <option value="NE" <c:if test="${bran.country=='NE'}">selected='selected' </c:if> >尼日尔</option>  
		        <option value="NG" <c:if test="${bran.country=='NG'}">selected='selected' </c:if> >尼日利亚</option>  
<%-- 		        <option value="NU" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >纽埃</option>  
		        <option value="NO" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >挪威</option>  
		        <option value="NF" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >诺福克岛</option>  
		        <option value="PW" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >帕劳群岛</option>  
		        <option value="PN" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >皮特凯恩群岛</option>   --%>
		        <option value="PT" <c:if test="${bran.country=='PT'}">selected='selected' </c:if> >葡萄牙</option>  
		        <option value="JP" <c:if test="${bran.country=='JP'}">selected='selected' </c:if> >日本</option>  
		        <option value="SE" <c:if test="${bran.country=='SE'}">selected='selected' </c:if> >瑞典</option>  
		        <option value="CH" <c:if test="${bran.country=='CH'}">selected='selected' </c:if> >瑞士</option>  
		        <option value="SV" <c:if test="${bran.country=='SV'}">selected='selected' </c:if> >萨尔瓦多</option>  
		        <option value="WS" <c:if test="${bran.country=='WS'}">selected='selected' </c:if> >萨摩亚</option>  
		        <option value="RS" <c:if test="${bran.country=='RS'}">selected='selected' </c:if> >塞尔维亚共和国</option>  
		        <option value="SL" <c:if test="${bran.country=='SL'}">selected='selected' </c:if> >塞拉利昂</option>  
<%-- 		        <option value="SN" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >塞内加尔</option>  
		        <option value="CY" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >塞浦路斯</option>  
		        <option value="SC" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >塞舌尔</option>  
		        <option value="XS" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >沙巴岛</option>  
		        <option value="SA" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >沙特阿拉伯</option>  
		        <option value="BL"<c:if test="${bran.country=='AL'}">selected='selected' </c:if> >圣巴泰勒米岛</option>  
		        <option value="CX"<c:if test="${bran.country=='AL'}">selected='selected' </c:if> >圣诞岛</option>  
		        <option value="ST"<c:if test="${bran.country=='AL'}">selected='selected' </c:if> >圣多美和普林西比</option>  
		        <option value="SH"<c:if test="${bran.country=='AL'}">selected='selected' </c:if> >圣赫勒拿岛</option>  
		        <option value="KN"<c:if test="${bran.country=='AL'}">selected='selected' </c:if> >圣基茨和尼维斯</option>  
		        <option value="LC" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >圣卢西亚</option>  
		        <option value="MF" <c:if test="${bran.country=='AL'}">selected='selected' </c:if>>法属圣马丁岛</option>  
		        <option value="SX" <c:if test="${bran.country=='AL'}">selected='selected' </c:if>>荷属圣马丁岛</option>  
		        <option value="SM" <c:if test="${bran.country=='AL'}">selected='selected' </c:if>>圣马力诺</option>  
		        <option value="PM" <c:if test="${bran.country=='AL'}">selected='selected' </c:if>>圣皮埃尔岛和密克隆岛</option>  
		        <option value="VC" <c:if test="${bran.country=='AL'}">selected='selected' </c:if>>圣文森特和格林纳丁斯</option>  
		        <option value="XE" <c:if test="${bran.country=='AL'}">selected='selected' </c:if>>圣尤斯特歇斯岛</option>   --%>
		        <option value="LK" <c:if test="${bran.country=='LK'}">selected='selected' </c:if>>斯里兰卡</option>  
		        <option value="SK" <c:if test="${bran.country=='SK'}">selected='selected' </c:if>>斯洛伐克</option>  
		        <option value="SI" <c:if test="${bran.country=='SI'}">selected='selected' </c:if>>斯洛文尼亚</option>  
		        <option value="SZ" <c:if test="${bran.country=='SZ'}">selected='selected' </c:if>>斯威士兰</option>  
		        <option value="SD" <c:if test="${bran.country=='SD'}">selected='selected' </c:if>>苏丹</option>  
		        <option value="SR" <c:if test="${bran.country=='SR'}">selected='selected' </c:if>>苏里南</option>  
		        <option value="SB" <c:if test="${bran.country=='SB'}">selected='selected' </c:if>>所罗门群岛</option>  
		        <option value="SO" <c:if test="${bran.country=='SO'}">selected='selected' </c:if>>索马里</option>  
		        <option value="TJ" <c:if test="${bran.country=='TJ'}">selected='selected' </c:if>>塔吉克斯坦</option>  
		        <option value="TW" <c:if test="${bran.country=='TW'}">selected='selected' </c:if>>台湾</option>  
		        <option value="TH" <c:if test="${bran.country=='TH'}">selected='selected' </c:if>>泰国</option>  
		        <option value="TZ" <c:if test="${bran.country=='TZ'}">selected='selected' </c:if> >坦桑尼亚</option>  
		        <option value="TO" <c:if test="${bran.country=='TO'}">selected='selected' </c:if> >汤加</option>  
		        <option value="TC" <c:if test="${bran.country=='TC'}">selected='selected' </c:if> >特克斯和凯科斯群岛</option>  
		        <option value="TT" <c:if test="${bran.country=='TT'}">selected='selected' </c:if> >特立尼达和多巴哥</option>  
		        <option value="TN" <c:if test="${bran.country=='TN'}">selected='selected' </c:if> >突尼斯</option>  
		        <option value="TV" <c:if test="${bran.country=='TV'}">selected='selected' </c:if> >图瓦卢</option>  
		        <option value="TR" <c:if test="${bran.country=='TR'}">selected='selected' </c:if> >土耳其</option>  
		        <option value="TM" <c:if test="${bran.country=='TM'}">selected='selected' </c:if> >土库曼斯坦</option>  
		        <option value="TK" <c:if test="${bran.country=='TK'}">selected='selected' </c:if> >托克劳</option>  
		        <option value="WF" <c:if test="${bran.country=='WF'}">selected='selected' </c:if> >瓦利斯和富图纳</option>  
		        <option value="VU" <c:if test="${bran.country=='VU'}">selected='selected' </c:if> >瓦努阿图</option>  
		        <option value="GT" <c:if test="${bran.country=='GT'}">selected='selected' </c:if> >危地马拉</option>  
		        <option value="VG" <c:if test="${bran.country=='VG'}">selected='selected' </c:if> >维尔京群岛(英属)</option>  
		        <option value="VE" <c:if test="${bran.country=='VE'}">selected='selected' </c:if> >委内瑞拉</option>  
		        <option value="BN" <c:if test="${bran.country=='BN'}">selected='selected' </c:if> >文莱</option>  
		        <option value="UG" <c:if test="${bran.country=='UG'}">selected='selected' </c:if> >乌干达</option>  
		        <option value="UA" <c:if test="${bran.country=='UA'}">selected='selected' </c:if> >乌克兰</option>  
		        <option value="UY" <c:if test="${bran.country=='UY'}">selected='selected' </c:if> >乌拉圭</option>  
		        <option value="UZ" <c:if test="${bran.country=='UZ'}">selected='selected' </c:if> >乌兹别克斯坦</option>  
		        <option value="ES" <c:if test="${bran.country=='ES'}">selected='selected' </c:if> >西班牙</option>  
		        <option value="GR" <c:if test="${bran.country=='GR'}">selected='selected' </c:if> >希腊</option>  
		        <option value="HK" <c:if test="${bran.country=='HK'}">selected='selected' </c:if> >香港特别行政区</option>  
		        <option value="SG" <c:if test="${bran.country=='SG'}">selected='selected' </c:if> >新加坡</option>  
		        <option value="NC" <c:if test="${bran.country=='NC'}">selected='selected' </c:if> >新喀里多尼亚</option>  
		        <option value="NZ" <c:if test="${bran.country=='NZ'}">selected='selected' </c:if> >新西兰</option>  
		        <option value="HU" <c:if test="${bran.country=='HU'}">selected='selected' </c:if> >匈牙利</option>  
		        <option value="SY" <c:if test="${bran.country=='SY'}">selected='selected' </c:if> >叙利亚</option>  
		        <option value="JM" <c:if test="${bran.country=='JM'}">selected='selected' </c:if> >牙买加</option>  
		        <option value="AM" <c:if test="${bran.country=='AM'}">selected='selected' </c:if> >亚美尼亚</option>  
		        <option value="SJ" <c:if test="${bran.country=='SJ'}">selected='selected' </c:if> >扬马延岛</option>  
		        <option value="YE" <c:if test="${bran.country=='YE'}">selected='selected' </c:if> >也门</option>  
		        <option value="IQ" <c:if test="${bran.country=='IQ'}">selected='selected' </c:if> >伊拉克</option>  
		        <option value="IR" <c:if test="${bran.country=='IR'}">selected='selected' </c:if> >伊朗</option>  
		        <option value="IL" <c:if test="${bran.country=='IL'}">selected='selected' </c:if> >以色列</option>  
		        <option value="IT" <c:if test="${bran.country=='IT'}">selected='selected' </c:if> >意大利</option>  
		        <option value="IN" <c:if test="${bran.country=='IN'}">selected='selected' </c:if> >印度</option>  
		        <option value="ID" <c:if test="${bran.country=='AL'}">selected='selected' </c:if> >印度尼西亚</option>  
		        <option value="UK" <c:if test="${bran.country=='ID'}">selected='selected' </c:if> >英国</option>  
		        <option value="IO" <c:if test="${bran.country=='IO'}">selected='selected' </c:if> >英属印度洋领地</option>  
		        <option value="JO" <c:if test="${bran.country=='JO'}">selected='selected' </c:if> >约旦</option>  
		        <option value="VN" <c:if test="${bran.country=='VN'}">selected='selected' </c:if> >越南</option>  
		        <option value="ZM" <c:if test="${bran.country=='ZM'}">selected='selected' </c:if> >赞比亚</option>  
		        <option value="JE" <c:if test="${bran.country=='JE'}">selected='selected' </c:if> >泽西</option>  
		        <option value="TD" <c:if test="${bran.country=='TD'}">selected='selected' </c:if> >乍得</option>  
		        <option value="GI" <c:if test="${bran.country=='GI'}">selected='selected' </c:if> >直布罗陀</option>  
		        <option value="CL" <c:if test="${bran.country=='CL'}">selected='selected' </c:if> >智利</option>  
		        <option value="CF" <c:if test="${bran.country=='CF'}">selected='selected' </c:if> >中非共和国</option>  
		       <!--  <option value="CN">中国</option>   -->
        		</select>
	       	    </div>
			 </li>
			
			 <li class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">详细地址</span>
		    	 <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		    	 	<input type="text" name="branchList[${vs.index }].detailAddress"  id="sup_branchAddress" value="${bran.detailAddress}" />
			        <span class="add-on cur_point">i</span>
	       	    </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">机构名称</span>
		    	 <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		    	 	<input type="text" name="branchList[${vs.index }].organizationName" id="sup_branchName"  value="${bran.organizationName}" />
			        <span class="add-on cur_point">i</span>
	       	    </div>
			 </li>
			
			
			 <li class="col-md-3 col-sm-6 col-xs-12">
				 	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
					<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
						<input type="button" onclick="addBranch(this)" class="btn" value="十"/>
						<input type="button" onclick="delBranch(this)"class="btn" value="一"/>
					</div>
			</li>
			
			  <li  class="col-md-12 col-xs-12 col-sm-12 mb25">
		    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 生产经营范围</span>
		    	<div class="col-md-12 col-xs-12 col-sm-12 p0">
			       <textarea class="col-md-12 col-xs-12 col-sm-12 h80"  id="branchbusinessSope" title="不超过80个字" name="branchList[${vs.index }].businessSope">${bran.businessSope}</textarea>
	       	    </div>
			  </li>
			  
			  </c:forEach>
			</ul>			
		</fieldset>
	  <!-- 财务信息 -->
	  <div class="padding-top-10 clear">
	  <c:forEach items="${currSupplier.listSupplierFinances}" var="finance" varStatus="vs">
	    	<h2 class="count_flow">${finance.year}年财务信息</h2>
	    	<fieldset class="col-md-12 col-xs-12 col-sm-12 border_font">
	 	  <!--   <legend>列表</legend> -->
			<div  class="col-md-12 col-sm-12 col-xs-12 p0" >
				<div class="col-md-12 col-sm-12 col-xs-12 p0 tl">
					<button type="button" class="btn btn-windows add" onclick="openFinance(this,'${finance.year}')">维护</button>
				<!-- 	<button type="button" class="btn btn-windows delete" onclick="deleteFinance()">删除</button> -->
					<span class="red"></span>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 p0">
					  <table class="table table-bordered table-condensed mt5">
						<thead>
							<tr>
							<!-- 	infotd=$(obj).parent().next().children().children(":last"); <th class="w30 info"><input type="checkbox" onchange="checkAllForFinance(this)" />
								</th> -->
								<th class="w50 info">年份</th>
								<th class="info">会计事务所名称</th>
								<th class="info">事务所联系电话</th>
								<th class="info">审计人姓名</th>
								<!-- <th class="info">指标</th> -->
								<th class="info">资产总额（万元）</th>
								<th class="info">负债总额（万元）</th>
								<th class="info">净资产总额（万元）</th>
								<th class="info">营业收入（万元）</th>
							</tr>
						</thead>
						<tbody id="finance_list_tbody_id">
							<%--  <c:if test="${finance.year!=null}"> --%>
									<tr>
										<%-- <td class="tc">  <input type="checkbox" value="${finance.id}" />  
										</td> --%>
										<td class="tc">${finance.year}</td>
										<td class="tc">${finance.name}</td>
										<td class="tc">${finance.telephone}</td>
										<td class="tc">${finance.auditors}</td>
									<%-- 	<td class="tc">${finance.quota}</td> --%>
										<td class="tc">${finance.totalAssets}</td>
										<td class="tc">${finance.totalLiabilities}</td>
										<td class="tc">${finance.totalNetAssets}</td>
										<td class="tc">${finance.taking}</td>
									</tr>
					 		<%-- </c:if> --%>
						</tbody>
					</table>
					
					<table id="finance_attach_list_id" class="table table-bordered table-condensed mt5">
						<thead>
							<tr>
								<!-- <th class="w30 info"><input type="checkbox" onchange="checkAllForFinance(this)" />
								</th> -->
								<th class="w50 info">年份</th>
								<th class="info">财务利润表</th>
								<th class="info">审计报告的审计意见</th>
								<th class="info">资产负债表</th>
								<th class="info">现金流量表</th>
								<th class="info">所有者权益变动表</th>
							</tr>
						</thead>
						<tbody id="finance_attach_list_tbody_id">
						 	<%-- <c:if test="${finance.year!=null}"> --%>
								<tr>
									<%-- <td class="tc"> <input type="checkbox" value="${finance.id}" /> 
									</td> --%>
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
					 	<%-- 	</c:if> --%>
						  </tbody>
					  </table>
					  
					  
				 </div>
			    </div>
			   </fieldset>
			  <%--  <fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
	 	    	   <legend>附件</legend>
	 	    	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
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
			</fieldset> --%>
		</c:forEach>
	</div>
		 
		
			<div class="padding-top-10 clear">
			    <h2 class="count_flow">出资人（股东）信息</h2>
				<div  class="col-md-12 col-sm-12 col-xs-12 p0 ul_list mb50">
				   <div class="col-md-12 col-sm-12 col-xs-12 p15 mt20">
						<div class="col-md-12 col-sm-12 col-xs-12 p0 mb5">
						    <button class="btn btn-windows add" type="button" onclick="openStockholder()" >新增</button>
							<button class="btn btn-windows delete" type="button" onclick="deleteStockholder()" >删除</button>
							<span class="red">${stock }</span>
						</div>
						<div class="col-md-12 col-sm-12 col-xs-12 p0">
							<table id="share_table_id" class="table table-bordered table-condensed mt5">
								<thead>
									<tr>
										<th class="info"><input type="checkbox" onchange="checkAll(this, 'stockholder_list_tbody_id')" />
										</th>
										<th class="info">出资人性质</th>
										<th class="info">出资人名称或姓名</th>
									
										<th class="info">统一社会信用代码或身份证号码</th>
										<th class="info">出资金额或股份（万元/万份）</th>
										<th class="info">比例（%）</th>
									</tr>
								</thead>
								<tbody id="stockholder_list_tbody_id">
									<c:forEach items="${currSupplier.listSupplierStockholders}" var="stockholder" varStatus="vs">
										<tr>
											<td class="tc"><input type="checkbox" value="${stockholder.id}" />
											</td>
											<td class="tc">
											<c:if test="${stockholder.nature==1}">
											法人
											</c:if>
											<c:if test="${stockholder.nature==2}">
											自然人
											</c:if>
											</td>
											
											<td class="tc">${stockholder.name}</td>
											
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
	
	<input type="hidden" id="index" value="0">
	<input type="hidden" id="branchIndex" value="0">
	<div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	    <button type="button" class="btn save" onclick="saveBasicInfo('2')">暂存</button>
				<button type="button" class="btn" onclick="saveBasicInfo('1')">下一步</button>
	  	  </div>
	</div>
</body>
</html>
