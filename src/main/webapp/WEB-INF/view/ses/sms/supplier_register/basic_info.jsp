<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/front.jsp" %>
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
	if ("${currSupplier.overseasBranch}"== '0'||"${currSupplier.overseasBranch}"== null) {
		$("li[name='branch']").hide();
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
	
	/** 暂存 */
	function temporarySave(){
		$.ajax({
			url : "${pageContext.request.contextPath}/supplier/temporarySave.do",
			type : "post",
			data : $("#basic_info_form_id").serializeArray(),
			contextType: "application/x-www-form-urlencoded",
			success:function(msg){
				if (msg == 'ok'){
					layer.msg('暂存成功');
				}
				if (msg == 'failed'){
					layer.msg('暂存失败');
				}
			}
		});
	}
	//listSupplierStockholders

	function openStockholder() {
		
		var stocIndex=$("#stockIndex").val();
		var supplierId = $("input[name='id']").val();
		$("#stockholder_list_tbody_id").append("<tr>"+
				"<td class='tc'><input type='checkbox' value='' /><input type='hidden' style='border:0px;' name='listSupplierStockholders["+stocIndex+"].supplierId' value="+supplierId+">"+
				"</td>"+
				"<td class='tc'>  <select  name='listSupplierStockholders["+stocIndex+"].nature'>"+
				 "<option value='1'>法人</option>"+
					" <option value='2'>自然人</option>"+
				"</select> </td>"+
				"<td class='tc'><input type='text' style='border:0px;' name='listSupplierStockholders["+stocIndex+"].name' value=''> </td>"+
			    "<td class='tc'><input type='text' style='border:0px;' name='listSupplierStockholders["+stocIndex+"].identity' value=''> </td>"+
				"<td class='tc'> <input type='text' style='border:0px;' name='listSupplierStockholders["+stocIndex+"].shares' value=''></td>"+
				"<td class='tc'> <input type='text' style='border:0px;' name='listSupplierStockholders["+stocIndex+"].proportion' value=''> </td>"+ "</tr>");
	
	
		
		
	/* 	if (!supplierId) {
			layer.msg("请暂存供应商基本信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加供应商股东信息',
				// skin : 'layui-layer-rim', //加上边框
				area : [ '50%', '420px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : globalPath + '/supplier_stockholder/add_stockholder.html?&supplierId=' + supplierId + '&sign=1', //url
				closeBtn : 1, //不显示关闭按钮
			});
		} */
	}

		function deleteStockholder() {
			var checkboxs = $("#stockholder_list_tbody_id").find(":checkbox:checked");
	    	var stockholderIds = "";
			var supplierId = $("input[name='id']").val(); 
			$(checkboxs).each(function(index) {
			 	var tr=$(this).parent().parent();
			 	$(tr).remove();
			    if (index > 0) {
					stockholderIds += ",";
				}
				stockholderIds += $(this).val();   
			});
     	var size = checkboxs.length;
			if (size > 0) {
			/* 	layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
					offset : '200px',
					scrollbar : false,
				}, function(index) {
					//window.location.href = globalPath + "/supplier_stockholder/delete_stockholder.html?stockholderIds=" + stockholderIds + "&supplierId=" + supplierId;
					
				
					
					
					
					layer.close(index);
		
				}); */
				$.ajax({
					url: "${pageContext.request.contextPath}/supplier_stockholder/delete_stockholder.do",
					async: false,
					data: {
						"stockholderIds":stockholderIds,
						"supplierId":supplierId
					},
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
			$("li[name='branch']").show();
		/* 	$('#sup_country').removeAttr('disabled');
			$('#sup_businessScope').removeAttr('disabled');
			$('#sup_branchName').removeAttr('disabled');
			$('#sup_branchAddress').removeAttr('disabled'); */
		}
		  else{
			$("li[name='branch']").hide();
	/* 		$('#sup_country').attr('disabled',"true");
			$('#sup_businessScope').attr('disabled',"true");
			$('#sup_branchName').attr('disabled',"true");
			$('#sup_branchAddress').attr('disabled',"true"); */
			
		}  
	}
	
	function checknums(obj){
		var vals=$(obj).val();
		var reg= /^\d+\.?\d*$/;  
		if(!reg.exec(vals)){
			$(obj).val("");
			 $("#err_fund").text("数字非法");
		}  else{
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
				"	<input type='button' onclick='increaseAddress(this)' class='btn list_btn' value='十'/>"+
				"	<input type='button' onclick='delAddress(this)'class='btn list_btn' value='一'/>"+
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
		$(li).after("<li name='branch' class='col-md-3 col-sm-6 col-xs-12'>"+
				 " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'>机构名称</span>"+
					" <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>"+
			    	 " <input type='text' name='branchList["+inde+"].organizationName' id='sup_branchName'  value='' />"+
			    	 	"   <span class='add-on cur_point'>i</span>"+
				        "   </div>"+
		       	 "  </li>"+
				"<li name='branch'  class='col-md-3 col-sm-6 col-xs-12'>"+
				" <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'>所在国家（地区）</span>"+
				"  <div class='select_common col-md-12 col-sm-12 col-xs-12 input_group p0'>"+
				 	"<select name='branchList["+inde +"].country'  id='overseas_branch_select_id'>"+
				 	 "<c:forEach items='${foregin }' var='fr'>"+
						"<option value='${fr.id }' <c:if test='${bran.country==fr.id}'>selected='selected' </c:if> >${fr.name }</option>"+  
			 		" </c:forEach> 	</select>"+
			        " </div>"+
			 " </li>"+
			
			 "  <li name='branch'  class='col-md-3 col-sm-6 col-xs-12'>"+
			 " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5'>详细地址</span>"+
				" <div class='input-append col-md-12 col-sm-12 col-xs-12 input_group p0'>"+
		    	 " <input type='text' name='branchList["+inde+"].detailAddress'  id='sup_branchAddress' value='' />"+
		    	 	"  <span class='add-on cur_point'>i</span>"+
			        " </div>"+
	       	 " </li>"+
	
			 " <li name='branch'  class='col-md-3 col-sm-6 col-xs-12'>"+
			 " <span class='col-md-12 col-xs-12 col-sm-12 padding-left-5 white'>操作</span>"+
				 	" <div class='col-md-12 col-xs-12 col-sm-12 p0 mb25 h30'>"+
					" <input type='button' onclick='addBranch(this)' class='btn list_btn' value='十'/>"+
					" <input type='button' onclick='delBranch(this)'class='btn list_btn' value='一'/>"+
					" </div>"+
					" </li>"+
			
			"  <li name='branch'  class='col-md-12 col-xs-12 col-sm-12 mb25'>"+
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
	
	function errorMsg(auditField){
		alert("hehe");
		var supplierId = "${currSupplier.id}";
		$.ajax({
			url: "${pageContext.request.contextPath}/supplier/audit.html",
			data: {"id": supplierId, "fieldName": auditField},
			dataType: "json",
			success: function(data){
			alert(data.suggest);
			//	layer.msg(response ,{offset: ['400px', '750px']});
			}
		});
	}
	
	
</script>
</head>

<body>
	<c:if test="${currSupplier.status != 7}">
		<%@ include file="/reg_head.jsp"%>
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
	 			<ul class="list-unstyled f14">
				<li class="col-md-3 col-sm-6 col-xs-12 pl10">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" ><i class="red">*</i> 公司名称</span>
					<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
				        <input  id="supplierName_input_id" type="text" name="supplierName" value="${currSupplier.supplierName}"  <c:if test="${fn:contains(errorField,'supplierName')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('supplierName')"</c:if>  /> 
				      	 <c:if test="${fn:contains(errorField,'supplierName')}">
						    <span class="add-on red" style="border-right: 1px solid #ef0000; border-top: 1px solid #ef0000; border-bottom:  1px solid #ef0000;">×</span>
					    </c:if>
					     <c:if test="${!fn:contains(errorField,'supplierName')}">
							<span class="add-on">i</span>
   					    </c:if>
					    
				     
				        <div class="cue"> ${err_msg_supplierName } </div>
				     </div>
				</li>
				<li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">公司网址</span>
				    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
			        <input type="text" name="website"  value="${currSupplier.website}" <c:if test="${fn:contains(audit,'website')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('website')"</c:if> >
			       <c:if test="${fn:contains(audit,'website')}">
						    <span class="add-on" style="color: red; border-right: 1px solid #ef0000; border-top: 1px solid #ef0000; border-bottom:  1px solid #ef0000;">×</span>
					    </c:if>
					     <c:if test="${!fn:contains(audit,'website')}">
							<span class="add-on cur_point">i</span>
			       			<span class="input-tip">例如：www.baidu.com</span>
   					    </c:if>
			         <div class="cue"> ${err_msg_website } </div>
			       </div>
				 </li>
				 
				 <li class="col-md-3 col-sm-6 col-xs-12">
				    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 成立日期</span>
				    <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
				    <fmt:formatDate value="${currSupplier.foundDate}" pattern="yyyy-MM-dd" var="foundDate" />
			        <input type="text" readonly="readonly" onClick="WdatePicker()" name="foundDate" value="${foundDate}" <c:if test="${fn:contains(errorField,'supplierName')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('foundDate')"</c:if>  />
			        <span class="add-on cur_point">i</span>
			       	<span class="input-tip">成立时间须大于三年</span>
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
			        <div class="cue"> <%-- ${err_msg_postCode }  --%></div>
			       </div>
				 </li> 
				
				
				<li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
				   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 基本账户开户许可证</span> 
				   <div class="col-md-12 col-sm-12 col-xs-12 p0">
					 <u:upload id="bank_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" auto="true" /> 
				     <u:show showId="bank_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBank}" />
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
					   <span class="col-md-12 col-xs-12 col-sm-12  padding-left-5 "><i class="red">*</i> 注册地址邮编</span>
					   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
				        <input type="text" name="postCode" value="${currSupplier.postCode}" />
				        <span class="add-on cur_point">i</span>
				         <div class="cue"> ${err_msg_postCode } </div>
				       </div>
					</li> 
				 
				 	<li class="col-md-3 col-sm-6 col-xs-12">
				    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 注册公司地址</span>
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
				   <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 生产经营详细地址</span>
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
						<input type="button" onclick="increaseAddress(this)" class="btn list_btn" value="十"/>
						<input type="button" onclick="delAddress(this)"class="btn list_btn" value="一"/>
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
		    		  <u:upload id="taxcert_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" auto="true" /> 
		        	  <u:show showId="taxcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}" />
			        </div>
			        <div class="cue"> ${err_taxCert } </div>
			    </li> 
				
				<li id="bill_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
				   <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三年银行基本账户未对账单</span> 
				   <div class="col-md-6 col-sm-12 col-xs-12 p0">
					   <u:upload id="billcert_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" auto="true" /> 
					   <u:show showId="billcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}" />
				   </div>
				    <div class="cue"> ${err_bil } </div>
				</li>
												
			   <li id="security_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
			      <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三个月缴纳社会保险金凭证</span> 
			      <div class="col-md-6 col-sm-12 col-xs-12 p0">
			        <u:upload id="curitycert_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" auto="true" /> 
			        <u:show showId="curitycert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}" />
			      </div>
			      <div class="cue"> ${err_security } </div>
			   </li>
												
			 <li id="breach_li_id" class="col-md-6 col-sm-12 col-xs-12 mb25">
			   <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 近三年内无重大违法记录声明</span> 
			   <div class="col-md-6 col-sm-12 col-xs-12 p0">
			     <u:upload id="bearchcert_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" auto="true" /> 
			     <u:show showId="bearchcert_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}" />
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
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证号</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="legalIdCard" value="${currSupplier.legalIdCard}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_legalCard } </div>
	       	   </div>
		   	 </li> 
		    
	<%-- 	    
		     <li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证正面</span> 
			   <div class="col-md-12 col-sm-12 col-xs-12 p0 h30">
			     <u:upload id="bearchcert_up_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" auto="true" /> 
			     <u:show showId="bearchcert_up_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
			   </div>
			   <div class="cue"> ${err_bearch } </div>
			</li> --%>
			
			    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 身份证复印件（正反面）</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
					     <u:upload id="bearchcert_up_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" auto="true" /> 
					     <u:show showId="bearchcert_up_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
                    </div>
                </li>
              
         <%--        <li class="col-md-3 col-sm-6 col-xs-12 mb25"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 居民身份证附件</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
					     <u:upload id="bearchcert_up_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" auto="true" /> 
					     <u:show showId="bearchcert_up_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentityUp}" />
                    </div>
                </li> --%>
                
                  
            <%--      <li class="col-md-3 col-sm-6 col-xs-12 mb25"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 身份证反面</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
					   			     <u:upload id="identity_down_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" auto="true" /> 
			    					 <u:show showId="identity_down_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" />
                    </div>
                </li> --%>
                
                 
  <%--               
			 <li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 身份证反面</span> 
			   <div class="col-md-12 col-sm-12 col-xs-12 p0 h30">
			     <u:upload id="identity_down_up" multiple="true"  groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" auto="true" /> 
			     <u:show showId="identity_down_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierIdentitydown}" />
			   </div>
			   <div class="cue"> ${err_bearch } </div>
			</li> --%>
			
			
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
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><i class="red">*</i> 姓名</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="contactName" value="${currSupplier.contactName}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_conName } </div>
	       	   </div>
		    </li> 
		    
		    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 传真</span>
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
				    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 地址</span>
				    	<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="root_area_select_id" name="concatProvince" onchange="loadChildren(this)">
				     <option value="" >请选择</option>
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
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><i class="red">*</i> 姓名</span>
			   <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		        <input type="text" name="armyBusinessName" value="${currSupplier.armyBusinessName}" />
		        <span class="add-on cur_point">i</span>
		        <div class="cue"> ${err_armName} </div>
	       	   </div>
		   	 </li> 
		    
		   	 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 传真</span>
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
				    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 地址</span>
				    	<div class="col-md-12 col-xs-12 col-sm-12 select_common p0">
				         <div class="col-md-5 col-xs-5 col-sm-5 mr5 p0"><select id="root_area_select_id" name="armyBuinessProvince" onchange="loadChildren(this)">
				   				   <option value="" >请选择</option>
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
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业有效期   <input type="checkbox" name="branchName" <c:if test="${currSupplier.branchName=='1'}"> checked='true'</c:if>   value="1"> 长期</span>
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
		    
		   <%--  	<li id="breach_li_id" class="col-md-3 col-sm-6 col-xs-12 mb25">
				   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 营业执照:</span> 
				   <div class="col-md-12 col-sm-12 col-xs-12 p0 h30">
					 <u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /> 
		   	   		 <u:upload id="business_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
				   </div>
				   <div class="cue"> ${err_bearch } </div>
				</li>
				 --%>
		    
		        <li class="col-md-3 col-sm-6 col-xs-12 mb25"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i> 营业执照</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
				  	 <u:upload id="business_up" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" auto="true" />
				     <u:show showId="business_show" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBusinessCert}" /> 
                    </div>
                </li>
                
                
		    <li class="col-md-12 col-xs-12 col-sm-12 mb25">
		    	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> <i class="red">*</i>营业范围（按照营业执照上填写）</span>
		    	<div class="col-md-12 col-xs-12 col-sm-12 p0">
			       <textarea class="col-md-12 col-xs-12 col-sm-12 h80" title="不超过80个字" name="businessScope">${currSupplier.bankName}</textarea>
	       	    </div>
			</li>
		  </ul> 
		</fieldset>
		
	 <h2 class="count_flow clear pt20"> <i>2</i> 境外信息</h2>
		<fieldset class="col-md-12 border_font mt20">
	 	  <legend>境外分支</legend>
		   <ul class="list-unstyled" style="font-size: 14">
     		<li class="col-md-3 col-sm-6 col-xs-12 pl10">
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red"></i>境外分支机构</span>
		    	<div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
		    	   <select  name="overseasBranch" onchange="dis(this)"  id="overseas_branch_select_id">
		    	        <option value="0">无</option>
						<option value="1">有</option>
					</select>
	       	    </div>
			 </li> 
			
			 <li class="col-md-3 col-sm-6 col-xs-12">
				 	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
					<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
						 
					</div>
			</li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
				 	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
					<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
					 
					</div>
			</li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
				 	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
					<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
				 
					</div>
			</li>
			
			<c:forEach items="${currSupplier.branchList }" var="bran"  varStatus="vs">
			
			 <li name="branch" style="display: none;" class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">机构名称</span>
		    	 <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		    	 	<input type="text" name="branchList[${vs.index }].organizationName" id="sup_branchName"  value="${bran.organizationName}" />
			        <span class="add-on cur_point">i</span>
	       	    </div>
			 </li>
			 
			 
		  	 <li name="branch" style="display: none;"  class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 ">所在国家（地区）</span>
		    	 <div class="select_common col-md-12 col-sm-12 col-xs-12  p0">
		    	 <%-- 	<input name="branchList[${vs.index }].country" id="sup_country" type="text" value="${bran.country}" />
			        <span class="add-on cur_point">i</span> --%>
		 		 	<select name="branchList[${vs.index }].country"  id="overseas_branch_select_id">
		 		 	 <option value="">请选择</option>
				 	 <c:forEach items="${foregin }" var="fr">
						<option value="${fr.id }" <c:if test="${bran.country==fr.id}">selected='selected' </c:if> >${fr.name }</option>  
				 	 </c:forEach>
        		</select>
	       	    </div>
			 </li>
			
			 <li name="branch"  style="display: none;" class="col-md-3 col-sm-6 col-xs-12">
				<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">详细地址</span>
		    	 <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
		    	 	<input type="text" name="branchList[${vs.index }].detailAddress"  id="sup_branchAddress" value="${bran.detailAddress}" />
			        <span class="add-on cur_point">i</span>
	       	    </div>
			 </li>
		
			
			
			 <li name="branch"  style="display: none;" class="col-md-3 col-sm-6 col-xs-12">
				 	<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
					<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
						<input type="button" onclick="addBranch(this)" class="btn list_btn" value="十"/>
						<input type="button" onclick="delBranch(this)"class="btn list_btn" value="一"/>
					</div>
			</li>
			
			  <li name="branch" style="display: none;" class="col-md-12 col-xs-12 col-sm-12 mb25">
		    	<span class="col-md-12 c ol-xs-12 col-sm-12 padding-left-5"> 生产经营范围</span>
		    	<div class="col-md-12 col-xs-12 col-sm-12 p0">
			       <textarea class="col-md-12 col-xs-12 col-sm-12 h80"  id="branchbusinessSope" title="不超过80个字" name="branchList[${vs.index }].businessSope">${bran.businessSope}</textarea>
	       	    </div>
			  </li>
			  
			  </c:forEach>
			</ul>			
		</fieldset>
	  <!-- 财务信息 -->
	  <h2 class="count_flow clear pt20"> <i>3</i> 近三年财务信息</h2>
	  <div class="padding-top-10 clear">
	  <c:forEach items="${currSupplier.listSupplierFinances}" var="finance" varStatus="vs">
	    	<h2 class="count_flow clear">${finance.year}年财务信息  <span style="float:right" class="b">（金额单位：万元）</span>  </h2>
	    	<div class="col-md-12 col-xs-12 col-sm-12 border_font">
	 	  <!--   <legend>列表</legend> -->
			<div  class="col-md-12 col-sm-12 col-xs-12 p0 over_scroll" >
				<div class="col-md-12 col-sm-12 col-xs-12 p0 tl">
				<%-- 	<button type="button" class="btn btn-windows add" onclick="openFinance(this,'${finance.year}')">维护</button> --%>
				<!-- 	<button type="button" class="btn btn-windows delete" onclick="deleteFinance()">删除</button> -->
					<span class="red"></span>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 p0">
					  <table class="table table-bordered table-condensed mt5 table_wrap">
						<thead>
							<tr>
							<!-- 	infotd=$(obj).parent().next().children().children(":last"); <th class="w30 info"><input type="checkbox" onchange="checkAllForFinance(this)" />
								</th> -->
								<th class="w50 info">年份</th>
								<th class="info">会计事务所名称</th>
								<th class="info">事务所联系电话</th>
								<th class="info">审计人姓名</th>
								<!-- <th class="info">指标</th> -->
								<th class="info">资产总额</th>
								<th class="info">负债总额</th>
								<th class="info">净资产总额</th>
								<th class="info">营业收入</th>
							</tr>
						</thead>
						<tbody id="finance_list_tbody_id">
							<%--  <c:if test="${finance.year!=null}"> --%>
									<tr>
										<%-- <td class="tc">  <input type="checkbox" value="${finance.id}" />  
										</td> --%>
										<td class="tc">
										<input type="hidden"name="listSupplierFinances[${vs.index }].id" value="${finance.id}"> 
										<input type="text" required="required" style="border:0px;width:70px;" name="listSupplierFinances[${vs.index }].year" value="${finance.year}"> </td>
										<td class="tc">
											<input type="text" required="required" style="border:0px;width:200px;" name="listSupplierFinances[${vs.index }].name" value="${finance.name}">
										 </td>
										<td class="tc">
										<input type="text" required="required" style="border:0px;width:200px;" name="listSupplierFinances[${vs.index }].telephone" value="${finance.telephone}">
										</td>
										<td class="tc">
						    				<input type="text" required="required" style="border:0px;width:200px;" name="listSupplierFinances[${vs.index }].auditors" value="${finance.auditors}">
										 
										 </td>
									<%-- 	<td class="tc">${finance.quota}</td> --%>
										<td class="tc">
										<input type="text" required="required" style="border:0px;width:70px;" onkeyup="checknums(this)" name="listSupplierFinances[${vs.index }].totalAssets" value="${finance.totalAssets}">
									 		
									 		</td>
										<td class="tc">
										<input type="text" required="required" style="border:0px;width:70px;" onkeyup="checknums(this)" name="listSupplierFinances[${vs.index }].totalLiabilities" value="${finance.totalLiabilities}">
										 </td>
										<td class="tc">
											<input type="text" required="required" style="border:0px;width:70px;" onkeyup="checknums(this)" name="listSupplierFinances[${vs.index }].totalNetAssets" value="${finance.totalNetAssets}">
										</td>
										<td class="tc">
											<input type="text"required="required"  style="border:0px;width:70px;" onkeyup="checknums(this)" name="listSupplierFinances[${vs.index }].taking" value="${finance.taking}">
									</td>
									</tr>
					 		<%-- </c:if> --%>
						</tbody>
					</table>
					
					<table id="finance_attach_list_id" class="table table-bordered table-condensed mt5 table_wrap">
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
									<td class="tc">
									 <u:upload id="fina_${vs.index}_pro_up"  multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProfit}" auto="true" />
									
									 <u:show showId="fina_${vs.index}_pro"   groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" 
									  businessId="${finance.id}" typeId="${supplierDictionaryData.supplierProfit}" sysKey="${sysKey}"/>
						<%-- 			 <a class="mt3 color7171C6" href="javascript:download('${finance.profitListId}', '${sysKey}')">${finance.profitList} </a> --%>
									</td>
									<td class="tc">
									
									<u:upload id="fina_${vs.index}_audit_up"  multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierAuditOpinion}" auto="true" />
									 <u:show showId="fina_${vs.index}_audit" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" 
									  businessId="${finance.id}" typeId="${supplierDictionaryData.supplierAuditOpinion}" sysKey="${sysKey}"/>
									
									<%-- <a class="mt3 color7171C6" href="javascript:download('${finance.liabilitiesListId}', '${sysKey}')">${finance.liabilitiesList}</a> --%>
									</td>
									<td class="tc">
									
									 <u:upload id="fina_${vs.index}_lia_up"  multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierLiabilities}" auto="true" />
									 <u:show showId="fina_${vs.index}_lia" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" 
									  businessId="${finance.id}" typeId="${supplierDictionaryData.supplierLiabilities}" sysKey="${sysKey}"/>
									  
 									</td>
 								
									  
									<td class="tc">
									<u:upload id="fina_${vs.index}_cash_up"  multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierCashFlow}" auto="true" />
									 <u:show showId="fina_${vs.index}_cash" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" 
									  businessId="${finance.id}" typeId="${supplierDictionaryData.supplierCashFlow}" sysKey="${sysKey}"/>
									  
									 <%-- 	<a class="mt3 color7171C6" href="javascript:download('${finance.cashFlowStatementId}', '${sysKey}')">${finance.cashFlowStatement}</a>
 --%>									</td>
									<td class="tc">
									
									<u:upload id="fina_${vs.index}_change_up"  multiple="true" groups="taxcert_up,billcert_up,curitycert_up,bearchcert_up,business_up,bearchcert_up_up,identity_down_up,bank_up,fina_0_pro_up,fina_1_pro_up,fina_2_pro_up,fina_0_audit_up,fina_1_audit_up,fina_2_audit_up,fina_0_lia_up,fina_1_lia_up,fina_2_lia_up,fina_0_cash_up,fina_1_cash_up,fina_2_cash_up,fina_0_change_up,fina_1_change_up,fina_2_change_up" businessId="${finance.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierOwnerChange}" auto="true" />
									 <u:show showId="fina_${vs.index}_change" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,business_show,bearchcert_up_show,identity_down_show,bank_show,fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" 
									  businessId="${finance.id}" typeId="${supplierDictionaryData.supplierOwnerChange}" sysKey="${sysKey}"/>
									  
									  
									  
									
<%-- 									<a class="mt3 color7171C6" href="javascript:download('${finance.changeListId}', '${sysKey}')">${finance.changeList}</a>
 --%>									</td>
								</tr>
					 	<%-- 	</c:if> --%>
						  </tbody>
					  </table>
					  
					  
				 </div>
			    </div>
			   </div>
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
			    <h2 class="count_flow clear pt20"> <i>4</i> 出资人（股东）信息  （说明：出资人（股东）多余10人的，列出出资金额前十位的信息，但出资比例高于50%）</h2>
				<div  class="col-md-12 col-sm-12 col-xs-12 p0 ul_list mb50">
				   <div class="col-md-12 col-sm-12 col-xs-12 p15 mt20">
						<div class="col-md-12 col-sm-12 col-xs-12 p0 mb5">
						    <button class="btn btn-windows add" type="button" onclick="openStockholder()" >新增</button>
							<button class="btn btn-windows delete" type="button" onclick="deleteStockholder()" >删除</button>
							<span class="red">${stock }</span>
						</div>
						<div class="col-md-12 col-sm-12 col-xs-12 p0 over_scroll">
							<table id="share_table_id" class="table table-bordered table-condensed mt5 table_wrap">
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
									<c:forEach items="${currSupplier.listSupplierStockholders}" var="stockholder" varStatus="stockvs">
										<tr>
											<td class="tc"><input type="checkbox" value="${stockholder.id}" />
											</td>
											<td class="tc">
											 <select  name="listSupplierStockholders[${stockvs.index }].nature" >
 														 <option value="1" <c:if test="${stockholder.nature==1}"> selected="selected"</c:if> >法人</option>
 														 <option value="2" <c:if test="${stockholder.nature==2}"> selected="selected" </c:if> >自然人</option>
 											</select>
 											
 												<input type="hidden" name='listSupplierStockholders[${stockvs.index }].id' value="${stockholder.id}" >
 												<input type="hidden" name='listSupplierStockholders[${stockvs.index }].supplierId' value="${currSupplier.id}" >
 												
 												
 												
											</td>
											
											<td class="tc"> <input type='text' style='border:0px;' name='listSupplierStockholders[${stockvs.index }].name' value='${stockholder.name}'> </td>
											
											<td class="tc"> <input type='text' style='border:0px;' name='listSupplierStockholders[${stockvs.index }].identity' value='${stockholder.identity}'>  </td>
											<td class="tr"> <input type='text' style='border:0px;' name='listSupplierStockholders[${stockvs.index }].shares' value='${stockholder.shares}'> </td>
											<td class="tc"> <input type='text' style='border:0px;' name='listSupplierStockholders[${stockvs.index }].proportion' value='${stockholder.proportion}'></td>
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
	<input type="hidden" id="stockIndex" value="0">
	<div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	    <button type="button" class="btn save" onclick="temporarySave();">暂存</button>
				<button type="button" class="btn" onclick="saveBasicInfo('1')">下一步</button>
	  	  </div>
	</div>
</body>
</html>
