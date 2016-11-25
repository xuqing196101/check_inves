<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/front.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
 function checks(obj){
	 var bool=$(obj).is(':checked');
	 var val=$(obj).val();
 /*     var id=[];
     var code=[]; */
	 if(bool==true&&val=='PROJECT'){
	/* 	 id.push($(obj).next().val());
		 code.push(val); */
		 $("#project_div").show();
		 $("#project").show();
	 }else if(bool!=true&&val=='PROJECT'){
		 $("#project_div").hide();
		 $("#project").show();
	 }
	 if(bool==true&&val=='SERVICE'){
		/*  code.push(val);
		 id.push($(obj).next().val()); */
		 $("#server_div").show();
		 $("#server").show();
	 }else if(bool!=true&&val=='SERVICE'){
		 $("#server_div").hide();
		 $("#server").show();
	 }
	  
	 if(bool==true&&val=='GOODS'){
		/*  code.push(val);
		 id.push($(obj).next().val()); */
		 $("#material").show();  
	 }else if(bool!=true&&val=='GOODS'){
	/* 	 $('input[name="chkItem"]:checked').each(function(){ 
			var val= $(this).val();
			
			 if(val=='xs'){
				 alert(val);
				 $(this).checked = false;
			 }
			});  */
 	 var checklist = document.getElementsByName ("chkItem");
			 for(var i=0;i<checklist.length;i++)
			   {
		 			var vals=checklist[i].value;
		 			if(vals=='SALES'){
		 				checklist[i].checked = false;
		 			}
		 			if(vals=='PRODUCT'){
		 				checklist[i].checked = false;
		 			} 
			   }  
 	 	$("#material").hide();
 	 	$("#production_div").hide();
 	 	$("#production").hide();
 	 	$("#sale_div").hide();  
 	 	$("#sale").hide();  
	 }
	 if(bool==true&&val=='PRODUCT'){
	/* 	 code.push(val);
		 id.push($(obj).next().val()); */
		 $("#production_div").show();  
			$("#production").show();
	 }else if(bool!=true&&val=='PRODUCT'){
	 	 $("#production_div").hide();  
	 	 $("#production").hide();
	 }
	 if(bool==true&&val=='SALES'){
		/*  code.push(val);
		 id.push($(obj).next().val()); */
		 $("#sale_div").show();  
		 $("#sale").show(); 
	 }else if(bool!=true&&val=='SALES'){
	 	 $("#sale_div").hide();
	 	 $("#sale").hide(); 
	 }
/* 	 $("#seach").val(name());
	 $("#hseach").val(valus()); */
 }
 
 function prev(obj){
	   var id =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val()); 
		}); 
		
		$("input[name='supplierTypeIds']").val(id);
		
	    $("input[name='flag']").val(obj);
		$("#save_pro_form_id").submit();
 }
 function store(obj){
	 
	    var id =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val()); 
		}); 
	 	var bool=false;
	 	var boo=false;
		 for(var i=0;i<id.length;i++){
			if(id[i]=='GOODS'){
				bool=true;
			}
			if(id[i]=='SALES'||id[i]=='PRODUCT'){
				boo=true;
			} 
		 }
 
		$("input[name='supplierTypeIds']").val(id);
		
		
	    $("input[name='flag']").val(obj);
	    if(bool==true&&boo!=true){
	    	layer.alert("请勾选产品货物类属性",{offset: ['222px', '390px'], shade:0.01});
	    }else{
	    	 if(id.length>1){
	    		 
	    		 $("#save_pro_form_id").submit();
	    	    }else{
	    	    	layer.alert("请选择供应商类型",{offset: ['222px', '390px'], shade:0.01});
	    	    	
	    	    }
	    }
	 
}
 function next(obj){
	 
	  var id =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val()); 
		}); 
	 	var bool=false;
	 	var boo=false;
		 for(var i=0;i<id.length;i++){
			if(id[i]=='GOODS'){
				bool=true;
			}
			if(id[i]=='SALES'||id[i]=='PRODUCT'){
				boo=true;
			} 
		 }
		 
		 
		
		$("input[name='supplierTypeIds']").val(id);
		
		
	    $("input[name='flag']").val(obj);
	    
	    if(bool==true&&boo!=true){
	    	layer.alert("请勾选产品货物类属性",{offset: ['222px', '390px'], shade:0.01});
	    }else{
	    	 if(id.length>1){
	    		 
	    		 $("#save_pro_form_id").submit();
	    	    }else{
	    	    	layer.alert("请选择供应商类型",{offset: ['222px', '390px'], shade:0.01});
	    	    	
	    	    }
	    }
	// 	$("#save_pro_form_id").submit();
}
 
	function openRegPerson() {
		var matEngId = $("input[name='supplierMatEng.id']").val();
		var supplierId = $("input[name='id']").val();
		if (!matEngId) {
			layer.msg("请暂存工程专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加注册类型和人数',
				// skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '280px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_reg_person/add_reg_person.html?matEngId=' + matEngId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	function deleteRegPerson() {
		var checkboxs = $("#reg_person_list_tbody_id").find(":checkbox:checked");
		var regPersonIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				regPersonIds += ",";
			}
			regPersonIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_reg_person/delete_reg_person.html?regPersonIds=" + regPersonIds +"&supplierId=" + supplierId;
				layer.close(index);
				
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
	
	
	function openAptitute() {
		var matEngId = $("input[name='supplierMatEng.id']").val();
		var supplierId = $("input[name='id']").val();
		if (!matEngId) {
			layer.msg("请暂存工程专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加资质资格信息',
				// skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_aptitute/add_aptitute.html?matEngId=' + matEngId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	function deleteAptitute() {
		var checkboxs = $("#aptitute_list_tbody_id").find(":checkbox:checked");
		var aptituteIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				aptituteIds += ",";
			}
			aptituteIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_aptitute/delete_aptitute.html?aptituteIds=" + aptituteIds +"&supplierId=" + supplierId;
				layer.close(index);
				
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
	
	/** 打开物资生产证书 */
	var proIndex;
	function openCertPro() {
		var matProId = $("input[name='supplierMatPro.id']").val();
		var supplierId = $("input[name='id']").val();
		if (!matProId) {
			layer.msg("请暂存物资生产专业信息 !", {
				offset : '300px',
			});
		} else {
			proIndex=layer.open({
				type : 2,
				title : '添加物资生产证书信息',
				// skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_cert_pro/add_cert_pro.html?matProId=' + matProId + '&supplierId=' + supplierId+'&sign='+1, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	/** 供应商保存专业生产信息 */	
	function savePro(jsp) {
		$("input[name='jsp']").val(jsp);
		$("#save_pro_form_id").submit();
	}
	
	function checkAll(ele, id) {
		var flag = $(ele).prop("checked");
		$("#" + id).find("input:checkbox").each(function() {
			$(this).prop("checked", flag);
		});

	}
	
	function deleteCertPro() {
		var checkboxs = $("#cert_pro_list_tbody_id").find(":checkbox:checked");
		var certProIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certProIds += ",";
			}
			certProIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_cert_pro/delete_cert_pro.html?certProIds=" + certProIds +"&supplierId=" + supplierId;
				layer.close(index);
				
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
	
	function openCertSell() {
		var matSellId = $("input[name='supplierMatSell.id']").val();
		var supplierId = $("input[name='id']").val();
		if (!matSellId) {
			layer.msg("请暂存物资销售专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加物资生产证书信息',
				// skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_cert_sell/add_cert_sell.html?matSellId=' + matSellId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	function deleteCertSell() {
		var checkboxs = $("#cert_sell_list_tbody_id").find(":checkbox:checked");
		var certSellIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certSellIds += ",";
			}
			certSellIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_cert_sell/delete_cert_sell.html?certSellIds=" + certSellIds +"&supplierId=" + supplierId;
				layer.close(index);
				
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
	
	function openCertSe() {
		var matSeId = $("input[name='supplierMatSe.id']").val();
		var supplierId = $("input[name='id']").val();
		if (!matSeId) {
			layer.msg("请暂存服务专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加物资服务证书信息',
				// skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_cert_se/add_cert_se.html?matSeId=' + matSeId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	function deleteCertSe() {
		var checkboxs = $("#cert_se_list_tbody_id").find(":checkbox:checked");
		var certSeIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certSeIds += ",";
			}
			certSeIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_cert_se/delete_cert_se.html?certSeIds=" + certSeIds +"&supplierId=" + supplierId;
				layer.close(index);
				
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
			
	$(function() {
		  window.onload=function(){
			  var checkeds = $("#st").val();
	 		  var arrays =checkeds.split(",");
	 		   var checkBoxAll = $("input[name='chkItem']"); 
			  for(var i=0;i<arrays.length;i++){
				  $.each(checkBoxAll,function(j,checkbox){
					    //获取复选框的value属性
					      var checkValue=$(checkbox).val();
					            if(arrays[i]==checkValue){
				                      $(checkbox).attr("checked",true);
				                      if(arrays[i]=='PRODUCT'){
				                    	  $("#production_div").show();
				                    	   $("#production").show();
				                      }
				                      if(arrays[i]=='SALES'){
				                    	  $("#sale_div").show();
				                      }
				                      if(arrays[i]=='SERVICE'){
				                    	  $("#server_div").show();
				                    	  $("#server").show();
				                      }
				                      if(arrays[i]=='PROJECT'){
				                    	  $("#project_div").show();
				                    	  $("#project").show();
				                      }
				               }
				      });
			  }
			if ("${currSupplier.status}" == 7) {
				showReason();
			}
			
			 $("#seach").val(name());
			 $("#hseach").val(valus());
			 
		  }
	});
	
	
	function openCertEng() {
		var matEngId = $("input[name='supplierMatEng.id']").val();
		var supplierId = $("input[name='id']").val();
		if (!matEngId) {
			layer.msg("请暂存工程专业信息 !", {
				offset : '300px',
			});
		} else {
			layer.open({
				type : 2,
				title : '添加工程证书信息',
				// skin : 'layui-layer-rim', //加上边框
				area : [ '600px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier_cert_eng/add_cert_eng.html?matEngId=' + matEngId + '&supplierId=' + supplierId, //url
				closeBtn : 1, //不显示关闭按钮
			});
		}
	}
	
	function deleteCertEng() {
		var checkboxs = $("#cert_eng_list_tbody_id").find(":checkbox:checked");
		var certEngIds="";
		var supplierId = $("input[name='id']").val();
		$(checkboxs).each(function(index) {
			if (index > 0) {
				certEngIds += ",";
			}
			certEngIds += $(this).val();
		});
		var size = checkboxs.length;
		if (size > 0) {
			layer.confirm("已勾选" + size + "条记录, 确定删除 !", {
				offset : '200px',
				scrollbar : false,
			},function(index) {
				window.location.href = "${pageContext.request.contextPath}/supplier_cert_eng/delete_cert_eng.html?certEngIds=" + certEngIds +"&supplierId=" + supplierId;
				layer.close(index);
				
			});
		} else {
			layer.alert("请至少勾选一条记录 !", {
				offset : '200px',
				scrollbar : false,
			});
		}
	}
	
	function seach(){
		var id=$("#hseach").val();
		var sid=$("#sid").val();
		if(id.length>0){
			layer.open({
				type : 2,
				title : '查询产品分类',
				// skin : 'layui-layer-rim', //加上边框
				area : [ '800px', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,
				content : '${pageContext.request.contextPath}/supplier/category.html?id=' + id+'&&sid='+sid , //url
				closeBtn : 1, //不显示关闭按钮
			});
		}else{
			layer.alert("请至少勾选一条记录 !", { offset : '200px', scrollbar : false,
			});	
		}
	}
	
 	
    function name(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).next().val());
		});
		return id;
    }
    function valus(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		return id;
    }
</script>
	
</head>

<body>
	<c:if test="${currSupplier.status != 7}">
		<%@ include file="/index_head.jsp"%>
    </c:if>
	<div class="wrapper">
		<!-- 项目戳开始 -->
		<%@include file="supplierNav.jsp" %>
		<!--详情开始-->
	 <div class="sevice_list container mt60">
		  <h2>产品服务/分类</h2>
		  <div class="col-md-12 col-sm-12 col-xs-12">
		      <div class="col-md-5 col-sm-6 col-xs-6 title tr"><span class="star_red">*</span>产品服务/类型：</div>
			  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
				  <c:forEach items="${supplieType }" var="obj" >
					    <span><input type="checkbox" name="chkItem" onclick="checks(this)" value="${obj.code }" /><input type="hidden" value="${obj.name }" >${obj.name } </span>
			      </c:forEach>
			  </div>
		    </div>
	 </div>
		
	 <div class="sevice_list container" class="dnone" id="material" >
		  <h2>产品服务/类型</h2>
		  <div class="col-md-12 col-sm-12 col-xs-12">
		      <div class="col-md-5 col-sm-6 col-xs-6 title tr"><span class="star_red">*</span>产品服务/分类：</div>
			  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
				    <c:forEach items="${wlist }" var="obj" >
					    <span><input type="checkbox" name="chkItem" onclick="checks(this)"  value="${obj.code}" /> <input type="hidden" value="${obj.name }" > ${obj.name }</span>
			      </c:forEach>
			  </div>
		    </div>
	  </div>

      <div class="sevice_list container" class="dnone" id="server" >
		  <h2>产品/服务类</h2>
		  <div class="col-md-12 col-sm-12 col-xs-12">
		      <div class="col-md-5 col-sm-6 col-xs-6 title tr"><span class="star_red">*</span>产品/服务类</div>
			  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
					    <input type="text" readonly="readonly" name="chkItem" id="seach" value="" class="supp_search col-md-6 col-sm-6 col-xs-12 p0" onclick="seach()"/> 
					    <input type="hidden"   id="hseach" value="" />
			  </div>
		    </div>
	 </div>

      <div class="sevice_list container" class="dnone" id="project"  >
		  <h2>产品/工程类</h2>
		  <div class="col-md-12 col-sm-12 col-xs-12">
		      <div class="col-md-5 col-sm-6 col-xs-6 title tr"><span class="star_red">*</span>产品服务/工程类：</div>
			  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
					    <input type="text" readonly="readonly" name="chkItem" id="project_seach" value="" class="fl"/> 
					    <input type="hidden"   id="hseach" value="" />
					    <input type="button" onclick="seach()" class="btn h30" value="搜索">
			  </div>
		    </div>
	  </div>
	 
	  <div class="sevice_list container"  class="dnone" id="sale" >
		  <h2>产品/货物销售类</h2>
		  <div class="col-md-12 col-sm-12 col-xs-12">
		      <div class="col-md-5 col-sm-6 col-xs-6 title tr"><span class="star_red">*</span>产品服务/货物销售类：</div>
			  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
					    <input type="text" readonly="readonly" name="chkItem" id="sale_seach" value="" class="fl"/> 
					    <input type="hidden"   id="hseach" value="" />
					    <input type="button" onclick="seach()" class="btn h30" value="搜索">
			  </div>
		    </div>
	  </div>
	 
	<div class="sevice_list container"  class="dnone" id="production">
		  <h2>产品/货物生产</h2>
		  <div class="col-md-12 col-sm-12 col-xs-12">
		      <div class="col-md-5 col-sm-6 col-xs-6 title tr"><span class="star_red">*</span>产品服务/货物生产类：</div>
			  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
					    <input type="text" readonly="readonly" name="chkItem" id="production_seach" value="" class="fl"/> 
					    <input type="hidden"   id="hseach" value="" />
					    <input type="button" onclick="seach()" class="btn h30" value="搜索">
			  </div>
		    </div>
	 </div>

	<div style="margin-top: 40px;">
		<form id="save_pro_form_id"  action="${pageContext.request.contextPath}/supplier/perfect_professional.html" method="post">
							<input type="hidden" name="id" id="sid" value="${currSupplier.id}" />
							<input type="hidden" name="flag" />
					<%-- 		<input type="hidden" name="defaultPage" value="${defaultPage}" /> --%>
				<div>
								<!-- 物资生产型专业信息 -->
			              <div class="dnone" id="production_div">
			              <div class="container container_box">
			              	  <h3 class="headline-v2" style="background-color: #FBFBFB " >物资-生产型专业信息</h3>
			              	    <ul class="list-unstyled f14">
									<input type="hidden" name="supplierMatPro.id" value="${currSupplier.supplierMatPro.id}" />
									<input type="hidden" name="supplierMatPro.supplierId" value="${currSupplier.id}" />
								 <fieldset class="col-md-12 col-sm-12 col-xs-12 border_font">
										<legend>供应商组织机构和人员</legend>
												<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>组织机构：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group pl15">
														<input id="supplierName_input_id" type="text" name="supplierMatPro.orgName" value="${currSupplier.supplierMatPro.orgName}" />
													   <span class="add-on cur_point">i</span>
													</div>
												</li>
												
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>人员总数：</span>
													<div class="input-append co-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalPerson" value="${currSupplier.supplierMatPro.totalPerson}" />
													   <span class="add-on cur_point">i</span>
													</div>
												</li>
												
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>管理人员：</span>
													<div class="input-append co-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalMange" value="${currSupplier.supplierMatPro.totalMange}" />
												     	<span class="add-on cur_point">i</span>
													</div>
												</li>
												
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术人员：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalTech" value="${currSupplier.supplierMatPro.totalTech}" />
													    <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>工人：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalWorker" value="${currSupplier.supplierMatPro.totalWorker}" />
													   <span class="add-on cur_point">i</span>
													</div>
												</li>
												<!-- <div class="clear"></div> -->
									</fieldset>
	 
						            <fieldset class="col-md-12 border_font mt20">
	 											<legend>产品研发能力 </legend>
	 			
										<!-- 	<ul class="list-unstyled list-flow"> -->
												<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术人员比例(%)：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.scaleTech" value="${currSupplier.supplierMatPro.scaleTech}" />
													    <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>高级技术人员比例：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.scaleHeightTech" value="${currSupplier.supplierMatPro.scaleHeightTech}" />
													   <span class="add-on cur_point">i</span>
													</div>
												</li>
												
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>研发部门名称：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.researchName" value="${currSupplier.supplierMatPro.researchName}" />
													   <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>研发部门人数：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalResearch" value="${currSupplier.supplierMatPro.totalResearch}" />
													    <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>研发部门负责人：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.researchLead" value="${currSupplier.supplierMatPro.researchLead}" />
													   <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>国家军队科研项目：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.countryPro" value="${currSupplier.supplierMatPro.countryPro}" />
													   <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>国家军队科技奖项：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.countryReward" value="${currSupplier.supplierMatPro.countryReward}" />
													   <span class="add-on cur_point">i</span>
													</div>
												</li>
												<!-- <div class="clear"></div> -->
										</fieldset>
	
	
	
										<fieldset class="col-md-12 border_font mt20">
	 											<legend>供应商生产能力  </legend>
											 
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>生产线名称数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalBeltline" value="${currSupplier.supplierMatPro.totalBeltline}" />
														   <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>生产设备名称数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalDevice" value="${currSupplier.supplierMatPro.totalDevice}" />
													  	   <span class="add-on cur_point">i</span>
													</div>
												</li>
										 
											</fieldset>
											
											<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
	 											<legend>物资生产型供应商质量检测能力登记  </legend>
	 											
										 
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>质量检测部门：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.qcName" value="${currSupplier.supplierMatPro.qcName}" />
													    <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>质量部门人数：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
													 	<input type="text" name="supplierMatPro.totalQc" value="${currSupplier.supplierMatPro.totalQc}" />
													  <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>质监部门负责人：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.qcLead" value="${currSupplier.supplierMatPro.qcLead}" />
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>质量检测设备名称：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.qcDevice" value="${currSupplier.supplierMatPro.qcDevice}" />
												     <span class="add-on cur_point">i</span>
													</div>
												</li>
											 
											 
											 </fieldset>
									</ul>					 
											 
		 	<fieldset class="col-md-12 border_font mt20">
	 	    <legend>供应商物资生产资质证书</legend>
			<!-- 	    <h2 class="count_flow">供应商物资生产资质证书 </h2> -->
					   <div class="col-md-12 col-sm-12 col-xs-12 p0">
											<div class="col-md-12 col-sm-12 col-xs-12 p0 tl">
											  <button type="button" class="btn fr mr0" onclick="deleteCertPro()">删除</button>
											  <button type="button" class="btn fr" onclick="openCertPro()">新增</button>
											</div>
					                       <table class="table table-bordered table-condensed mt5">
												<thead>
													<tr>
														<th class="info"><input type="checkbox" onchange="checkAll(this, 'cert_pro_list_tbody_id')"/></th>
														<th class="info">资质证书名称</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关</th>
														<th class="info">有效期（起止时间）</th>
														<th class="info">有效期（结束时间）</th>
														<th class="info">是否年检</th>
														<%--<th class="info">附件</th>
													--%></tr>
												</thead>
												<tbody id="cert_pro_list_tbody_id">
													<c:forEach items="${currSupplier.supplierMatPro.listSupplierCertPros}" var="certPro" varStatus="vs">
														<tr>
															<td class="tc"><input type="checkbox" value="${certPro.id}" /></td>
															<td class="tc">${certPro.name}</td>
															<td class="tc">${certPro.levelCert}</td>
															<td class="tc">${certPro.licenceAuthorith}</td>
															<td class="tc"><fmt:formatDate value="${certPro.expStartDate}" pattern="yyyy-MM-dd" /></td>
															<td class="tc"><fmt:formatDate value="${certPro.expEndDate}" pattern="yyyy-MM-dd" /></td>
															<td class="tc">${certPro.mot}</td>
															<%--<td class="tc">
																<c:if test="${certPro.attach != null}">
																	<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${certPro.attach}')">下载附件</a>
																</c:if>
																<c:if test="${certPro.attach == null}">
																	<span class="fz11">无附件下载</span>
																</c:if>
															</td>--%>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
								 
									 
										</fieldset>
								 
									<!-- </div> -->
								</div>
								</div>
								
								<!-- 物资销售型专业信息 -->
							 <div class="dnone" id="sale_div">
							 
					<!-- 		   <h2> 物资-销售信息：</h2> -->
						 
						    <div class="container container_box">
			              	  <h3 class="headline-v2" style="background-color: #FBFBFB " >物资-销售专业信息</h3>
			              	    <ul class="list-unstyled" style="font-size: 14">
			              	    
										<input type="hidden" name="supplierMatSell.id" value="${currSupplier.supplierMatSell.id}" />
										<input type="hidden" name="supplierMatSell.supplierId" value="${currSupplier.id}" />
								    <fieldset class="col-md-12 col-sm-12 col-xs-12 border_font">
	 			 						<legend>供应商组织机构和人员 </legend>
												<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>组织机构：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
														<input type="text" name="supplierMatSell.orgName" value="${currSupplier.supplierMatSell.orgName}" />
														 <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>人员总数：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatSell.totalPerson" value="${currSupplier.supplierMatSell.totalPerson}" />
														 <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>管理人员：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatSell.totalMange" value="${currSupplier.supplierMatSell.totalMange}" />
													    <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术人员：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatSell.totalTech" value="${currSupplier.supplierMatSell.totalTech}" />
														   <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>工人（职员）：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatSell.totalWorker" value="${currSupplier.supplierMatSell.totalWorker}" />
														   <span class="add-on cur_point">i</span>
													</div>
												</li>
										 </fieldset>
										</ul>
		 
					<fieldset class="col-md-12 border_font mt20">
	 	               <legend>供应商物资销售资质证书</legend>
						 <!--    <h2 class="count_flow">供应商物资销售资质证书 </h2> -->
						<!-- 	<div  class="col-md-12 p0 ul_list mb50"> -->
							   <div class="col-md-12 p0">
									<div class="fl">
											<button type="button" class="btn fr mr0" onclick="deleteCertSell()">删除</button>
											<button type="button" class="btn fr" onclick="openCertSell()">新增</button>
									</div>		
									<div class="mt40">
							      <table id="share_table_id" class="table table-bordered table-condensed mt5">
												<thead>
													<tr>
														<th class="info"><input type="checkbox" onchange="checkAll(this, 'cert_sell_list_tbody_id')" /></th>
														<th class="info">资质证书名称</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关</th>
														<th class="info">有效期（起止时间）</th>
														<th class="info">有效期（结束时间）</th>
														<th class="info">是否年检</th>
														<%--<th class="info">附件</th>--%>
													</tr>
												</thead>
												<tbody id="cert_sell_list_tbody_id">
													<c:forEach items="${currSupplier.supplierMatSell.listSupplierCertSells}" var="certSell" varStatus="vs">
														<tr>
															<td class="tc"><input type="checkbox" value="${certSell.id}" /></td>
															<td class="tc">${certSell.name}</td>
															<td class="tc">${certSell.levelCert}</td>
															<td class="tc">${certSell.licenceAuthorith}</td>
															<td class="tc"><fmt:formatDate value="${certSell.expStartDate}" pattern="yyyy-MM-dd" /></td>
															<td class="tc"><fmt:formatDate value="${certSell.expEndDate}" pattern="yyyy-MM-dd" /></td>
															<td class="tc">${certSell.mot}</td>
															<%--<td class="tc">
																<c:if test="${certSell.attach != null}">
																	<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${certSell.attach}')">下载附件</a>
																</c:if>
																<c:if test="${certSell.attach == null}">
																	<span class="fz11">无附件下载</span>
																</c:if>
															</td>--%>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									 </div>
								 
									 </fieldset>
								 
									</div> 
							 </div>
								
								
								
								<!-- 工程专业信息 -->
		 			<div  class="dnone" id="project_div">
		 							  
   						<div class="container container_box">
			              	  <h3 class="headline-v2" style="background-color: #FBFBFB " >工程专业信息:</h3>
			              	    <ul class="list-unstyled" style="font-size: 14">
			              	    
									   <!--   <div class="col-md-5 title"><span class="star_red fl">*</span>工程专业信息：</div> -->
										<input type="hidden" name="supplierMatEng.id" value="${currSupplier.supplierMatEng.id}" />
										<input type="hidden" name="supplierMatEng.supplierId" value="${currSupplier.id}" />
										 
										 	<fieldset class="col-md-12 border_font mt20">
											 	  <legend>法人代表信息</legend>
	 	  
										
												<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>组织机构：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatEng.orgName" value="${currSupplier.supplierMatEng.orgName}" />
													    <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术负责人：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatEng.totalTech" value="${currSupplier.supplierMatEng.totalTech}" />
													    <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>中级以上职称人员：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatEng.totalGlNormal" value="${currSupplier.supplierMatEng.totalGlNormal}" />
												        <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>现场管理人员：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatEng.totalMange" value="${currSupplier.supplierMatEng.totalMange}" />
													    <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术和工人：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatEng.totalTechWorker" value="${currSupplier.supplierMatEng.totalTechWorker}" />
													    <span class="add-on cur_point">i</span>
													</div>
												</li>
												
										</fieldset>
								  </ul>
									
									 
										<fieldset class="col-md-12 border_font mt20">
	 	              						 <legend>供应商注册人员登记 </legend>
	 	               
										  
											
											   <div class="col-md-12 p0">
											<div class="fl">
											<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteRegPerson()">删除</button>
											<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openRegPerson()">新增</button>
											</div>
											<div class="mt40">
						  						<table  class="table table-bordered table-condensed mt5">
												<thead>
													<tr>
														<th class="info"><input type="checkbox"  onchange="checkAll(this, 'reg_person_list_tbody_id')"/></th>
														<th class="info">注册名称</th>
														<th class="info">注册人数</th>
													</tr>
												</thead>
												<tbody id="reg_person_list_tbody_id">
													<c:forEach items="${currSupplier.supplierMatEng.listSupplierRegPersons}" var="regPerson" varStatus="vs">
														<tr>
															<td class="tc"><input type="checkbox" value="${regPerson.id}" /></td>
															<td class="tc">${regPerson.regType}</td>
															<td class="tc">${regPerson.regNumber}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											</div>
											</div>
											</fieldset>
										
										 
											
											
									 
										   <!--  <h2 class="count_flow">供应商工程资质资格证书信息  </h2> -->
										 <fieldset class="col-md-12 border_font mt20">
	 	              						 <legend>供应商工程资质资格证书信息</legend>
	 	               
										  
											
											   <div class="col-md-12 p0">
											<div class="fl">
												<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteCertEng()">删除</button>
												<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openCertEng()">新增</button>
										  </div>
										   	<div class="mt40">
						  						<table  class="table table-bordered table-condensed mt5">
												<thead>
													<tr>
														<th class="info"><input type="checkbox" onchange="checkAll(this, 'cert_eng_list_tbody_id')"/></th>
														<th class="info">资质资格类型</th>
														<th class="info">证书编号</th>
														<th class="info">资质资格最高等级</th>
														<th class="info">技术负责人姓名</th>
														<th class="info">技术负责人职称</th>
														<th class="info">技术负责人职务</th>
														<th class="info">单位负责人姓名</th>
														<th class="info">单位负责人职称</th>
														<th class="info">单位负责人职务</th>
														<th class="info">发证机关</th>
														<th class="info minw100">发证日期</th>
														<th class="info minw100">证书有效期截止日期</th>
														<th class="info">证书状态</th>
														<th class="info">附件</th>
													</tr>
												</thead>
												<tbody id="cert_eng_list_tbody_id">
													<c:forEach items="${currSupplier.supplierMatEng.listSupplierCertEngs}" var="certEng" varStatus="vs">
														<tr>
															<td class="tc"><input type="checkbox" value="${certEng.id}" /></td>
															<td class="tc">${certEng.certType}</td>
															<td class="tc">${certEng.certCode}</td>
															<td class="tc">${certEng.certMaxLevel}</td>
															<td class="tc">${certEng.techName}</td>
															<td class="tc">${certEng.techPt}</td>
															<td class="tc">${certEng.techJop}</td>
															<td class="tc">${certEng.depName}</td>
															<td class="tc">${certEng.depPt}</td>
															<td class="tc">${certEng.depJop}</td>
															<td class="tc">${certEng.licenceAuthorith}</td>
															<td class="tc"><fmt:formatDate value="${certEng.expStartDate}" pattern="yyyy-MM-dd" /></td>
															<td class="tc"><fmt:formatDate value="${certEng.expEndDate}" pattern="yyyy-MM-dd" /></td>
															<td class="tc">${certEng.certStatus}</td>
															<%--<td class="tc">
																<c:if test="${certEng.attachCert != null}">
																	<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${certEng.attachCert}')">下载附件</a>
																</c:if>
																<c:if test="${certEng.attachCert == null}">
																	<span class="fz11">无附件下载</span>
																</c:if>
															</td>--%>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											</div>
											</div>
											 </fieldset>
										 
											
											
											
						 
									<!-- 	    <h2 class="count_flow">供应商资质资格信息   </h2> -->
										     <fieldset class="col-md-12 border_font mt20">
	 	              						 <legend>供应商资质资格信息  </legend>
	 	              						 
										<!-- 	<div  class="col-md-12 p0 ul_list mb50"> -->
											   <div class="col-md-12 p0">
											<div class="fl">
											
											<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteAptitute()">删除</button>
											<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openAptitute()">新增</button>
											  </div>
										   	<div class="mt40">
						  						<table  class="table table-bordered table-condensed mt5">
												<thead>
													<tr>
														<th class="info"><input type="checkbox" onchange="checkAll(this, 'aptitute_list_tbody_id')"/></th>
														<th class="info">资质资格类型</th>
														<th class="info">证书编号</th>
														<th class="info">资质资格序列</th>
														<th class="info">专业类别</th>
														<th class="info">资质资格等级</th>
														<th class="info">是否主项资质</th>
														<th class="info">批准资质资格内容</th>
														<th class="info">首次批准资质资格文号</th>
														<th class="info">首次批准资质资格日期</th>
														<th class="info">资质资格取得方式</th>
														<th class="info">资质资格状态</th>
														<th class="info">资质资格状态变更时间</th>
														<th class="info">资质资格状态变更原因</th>
														<%--<th class="info">附件</th>--%>
													</tr>
												</thead>
												<tbody id="aptitute_list_tbody_id">
													<c:forEach items="${currSupplier.supplierMatEng.listSupplierAptitutes}" var="aptitute" varStatus="vs">
														<tr>
															<td class="tc"><input type="checkbox" value="${aptitute.id}" /></td>
															<td class="tc">${aptitute.certType}</td>
															<td class="tc">${aptitute.certCode}</td>
															<td class="tc">${aptitute.aptituteSequence}</td>
															<td class="tc">${aptitute.professType}</td>
															<td class="tc">${aptitute.aptituteLevel}</td>
															<td class="tc">${aptitute.isMajorFund}</td>
															<td class="tc">${aptitute.aptituteContent}</td>
															<td class="tc">${aptitute.aptituteCode}</td>
															<td class="tc"><fmt:formatDate value="${aptitute.aptituteDate}" pattern="yyyy-MM-dd" /></td>
															<td class="tc">${aptitute.aptituteWay}</td>
															<td class="tc">${aptitute.aptituteStatus}</td>
															<td class="tc"><fmt:formatDate value="${aptitute.aptituteChangeAt}" pattern="yyyy-MM-dd" /></td>
															<td class="tc">${aptitute.aptituteChangeReason}</td>
															<%--<td class="tc">
																<c:if test="${aptitute.attachCert != null}">
																	<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${aptitute.attachCert}')">下载附件</a>
																</c:if>
																<c:if test="${aptitute.attachCert == null}">
																	<span class="fz11">无附件下载</span>
																</c:if>
															</td>--%>
														</tr>
													</c:forEach>
												</tbody>
											</table>
									 </div>
									 </div>
								<!-- 	 </div> -->
									 </fieldset>
									
								
								</div>	 
							 </div>
								
								<!-- 服务专业信息 -->
							 <div class="dnone" id="server_div">
							 
									<div class="container container_box">
					              	  <h3 class="headline-v2" style="background-color: #FBFBFB ">服务专业信息:</h3>
					              	    <ul class="list-unstyled" style="font-size: 14">
							 	
									 
										<input type="hidden" name="supplierMatSe.id" value="${currSupplier.supplierMatSe.id}" />
										<input type="hidden" name="supplierMatSe.supplierId" value="${currSupplier.id}" />
												<fieldset class="col-md-12 border_font mt20">
											 	  <legend>法人代表信息</legend>
												<li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>组织机构：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatSe.orgName" value="${currSupplier.supplierMatSe.orgName}" />
													   <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>人员总数：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatSe.totalPerson" value="${currSupplier.supplierMatSe.totalPerson}" />
													     <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>管理人员：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatSe.totalMange" value="${currSupplier.supplierMatSe.totalMange}" />
													    <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术人员：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatSe.totalTech" value="${currSupplier.supplierMatSe.totalTech}" />
													     <span class="add-on cur_point">i</span>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>工人（职员）：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group">
														<input type="text" name="supplierMatSe.totalWorker" value="${currSupplier.supplierMatSe.totalWorker}" />
													    <span class="add-on cur_point">i</span>
													</div>
												</li>
										 	</fieldset>
										 </ul>
										 
										 
										 
											   <fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
	 	              						 <legend> 供应商资服务质证书 </legend>
	 	              						 
										     
								 
											   <div class="col-md-12 col-sm-12 col-xs-12 p0">
													<div class="col-md-12 col-sm-12 col-xs-12 p0 tl">
														<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteCertSe()">删除</button>
														<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openCertSe()">新增</button>
													</div>
												<div class="col-md-12 col-sm-12 col-xs-12">
										 <table id="share_table_id" class="table table-bordered table-condensed mt5">
												<thead>
													<tr>
														<th class="info"><input type="checkbox" onchange="checkAll(this, 'cert_se_list_tbody_id')" /></th>
														<th class="info">资质证书名称</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关</th>
														<th class="info">有效期（起始时间）</th>
														<th class="info">有效期（结束时间）</th>
														<th class="info">是否年检</th>
														<%--<th class="info">附件</th>--%>
													</tr>
												</thead>
												<tbody id="cert_se_list_tbody_id">
													<c:forEach items="${currSupplier.supplierMatSe.listSupplierCertSes}" var="certSe" varStatus="vs">
														<tr>
															<td class="tc"><input type="checkbox" value="${certSe.id}" /></td>
															<td class="tc">${certSe.name}</td>
															<td class="tc">${certSe.levelCert}</td>
															<td class="tc">${certSe.licenceAuthorith}</td>
															<td class="tc"><fmt:formatDate value="${certSe.expStartDate}" pattern="yyyy-MM-dd" /></td>
															<td class="tc"><fmt:formatDate value="${certSe.expEndDate}" pattern="yyyy-MM-dd" /></td>
															<td class="tc">${certSe.mot}</td>
															<%--<td class="tc">
																<c:if test="${certSe.attach != null}">
																	<a class="color7171C6 fz11" href="javascript:void(0)" onclick="downloadFile('${certSe.attach}')">下载附件</a>
																</c:if>
																<c:if test="${certSe.attach == null}">
																	<span class="fz11">无附件下载</span>
																</c:if>
															</td>--%>
														</tr>
													</c:forEach>
												</tbody>
											</table>
									 	</div>
									 	</div>
									 
									 	</fieldset>
								 
									 	</div>
									 	
									</div>
							 
							</div>
						  <input name="supplierTypeIds" type="hidden" />
						  
						  <input type="hidden" value="${currSupplier.supplierTypeIds }" id="st">	 
						</form>
						
				</div>		
						
		
		
		
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<div class="padding-top-20">
							<div class="margin-bottom-0 tc">
							 
								<div class="mt40 tc mb50">
									<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="prev(3)">上一步</button>
									<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="store(2)">暂存</button>
									<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="next(1)">下一步</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div> 
	</div>
<%-- 	
	<form id="supplier_type_form_id" action="${pageContext.request.contextPath}/supplier_type_relate/perfect_type.html" method="post">
		<input name="id" type="hidden" value="${currSupplier.id}" />
		<input name="sgin" type="hidden" />
		<input name="supplierTypeIds" type="hidden" />
	</form> --%>
	
	<!-- footer -->
	<c:if test="${currSupplier.status != 7}">
		<jsp:include page="../../../../../index_bottom.jsp" />
	</c:if> 
</body>
</html>
