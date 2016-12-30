<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/front.jsp" %>
<script type="text/javascript">

 //显示生产的信息
 function product(obj){
	 if (obj =="PRODUCT"){
		 $("#productId").show();
	 } 
 }
 //显示销售的信息
 function sales(obj){
	 if (obj =="SALES"){
		 $("#salesId").show();
	 } 
 }
 //显示工程的信息
 function project(obj){
	 if (obj =="PROJECT"){
		 $("#projectId").show();
	 }
 }
 //显示服务的信息
 function services(obj){
	 if (obj =="SERVICE"){
		 $("#serviceId").show();
	 } 
 }
 
 //初始化所有的tab标题
 function hideTabTitle(){
	 $("#productId").hide();
	 $("#salesId").hide();
	 $("#projectId").hide();
	 $("#serviceId").hide();
 }
 
 //初始化tab的标题样式
 function initTabTitleCss(){
	 $("#productId").removeClass("active");
	 $("#salesId").removeClass("active");
	 $("#projectId").removeClass("active");
	 $("#serviceId").removeClass("active");
 }

 //选中信息头
 function checks(obj){
	 var selectedArray = [];
	 hideTabTitle();
	 $("input[name='chkItem']:checked").each(function(){
		 var value = $(this).val();
		 $("#tab_div").show();
		 selectedArray.push(value);
		 product(value);
		 sales(value);
		 project(value);
		 services(value)
	 });
	 
	 if (selectedArray.length == 0){
		 $("#tab_div").hide();
	 }
	 var first = selectedArray[0] ;
	 
	 if (first != null && first !="" && first !="undefined"){
		 loadTab(first);
	 }
 }
 
 //选中对应的信息
 function loadTab(val){
	 initTabTitleCss();
	 $("#tab_content_div_id").find(".tab-pane").each(function(index) {
		 $("#production_div").attr("class", "tab-pane fade");
		 $("#sale_div").attr("class", "tab-pane fade");
		 $("#project_div").attr("class", "tab-pane fade ");
		 $("#server_div").attr("class", "tab-pane fade ");
		 
		 if (val == 'PRODUCT') {
			 $("#productId").addClass("active");
			 $("#production_div").attr("class", "tab-pane fade  active in");
		 }
		 if (val == 'SALES') {
			 $("#salesId").addClass("active");
			 $("#sale_div").attr("class", "tab-pane fade  active in");
		 } 
		 if (val == 'PROJECT') {
			 $("#projectId").addClass("active");
		 	 $("#project_div").attr("class", "tab-pane fade  active in");
		 } 
		 if (val == 'SERVICE') {
			 $("#serviceId").addClass("active");
			 $("#server_div").attr("class", "tab-pane fade  active in");
		 } 
		});
 }
 
 
 //上一步
 function prev(){
	var id = $("#sid").val();
	window.location.href="${pageContext.request.contextPath}/supplier/register.html?id=" + id;
 }
 
 
 //暂存
 function ajaxSave(){
	 var id =[]; 
	 $('input[name="chkItem"]:checked').each(function(){ 
		id.push($(this).val()); 
	 }); 
	 $("input[name='supplierTypeIds']").val(id);
	 
	 if (id.length == 0){
		 layer.msg("请选择供应商类型");
		 return false;
	 }
	
	 $.ajax({
			url : "${pageContext.request.contextPath}/supplier/saveSupplierType.do",
			type : "post",
			data : $("#save_pro_form_id").serializeArray(),
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
	    var flag = true;
		$("input[name='supplierTypeIds']").val(id);
	    $("input[name='flag']").val(obj);
	    if(bool==true&&boo!=true){
	    	layer.msg("请勾选产品货物类属性");
	    }else{
	   		if(id.length>0){
	    		flag = true;
	    	}else{
	    		flag = false;
	    		layer.msg("请选择供应商类型");
	    	}
		}
	    $("#cert_pro_list_tbody_id").find("input").each(function(index,element){
	    	if (element.value == "") {
	    		flag = false;
	    		layer.msg("物资生产资质证书信息不能为空! ");
	    	}
	    });
	    if (flag == true) {
		    $("#save_pro_form_id").submit();
	    }
	    
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
		/* var matProId = $("input[name='supplierMatPro.id']").val();
		var supplierId = $("input[name='id']").val();
		if (!matProId) {
			layer.msg("请暂存物资生产专业信息 !", {
				offset : '300px',
			});
		} else { */
			proIndex=layer.open({
				type : 2,
				title : '添加物资生产证书信息',
				// skin : 'layui-layer-rim', //加上边框
				area : [ '50%', '500px' ], //宽高
				offset : '100px',
				scrollbar : false,  
				content : '${pageContext.request.contextPath}/supplier_cert_pro/add_cert_pro.html?matProId=' + matProId + '&supplierId=' + supplierId+'&sign='+1, //url
				closeBtn : 1, //不显示关闭按钮
			});
		/* } */
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
			  var checkeds = $("#supplierTypes").val();
				if(checkeds!=""){
					$("#tab_div").show();
			 		$("#tab_content_div_id").show();
				}
	 		  var arrays =checkeds.split(",");
	 		  var checkedArray = [];
	 		   var checkBoxAll = $("input[name='chkItem']"); 
	 		   if(arrays.length>0){
	 			  initTabTitleCss();
			  for(var i=0;i<arrays.length;i++){
				  $.each(checkBoxAll,function(j,checkbox){
					    //获取复选框的value属性
					      var checkValue=$(checkbox).val();
					            if(arrays[i]==checkValue){
				                      $(checkbox).attr("checked",true);
				                      if(arrays[i]!='PROJECT'){
					                     $("#project_div").attr("class", "tab-pane fade ");
					                  }
				                      if(arrays[i]!='PRODUCT'){
				                    	  $("#production_div").attr("class", "tab-pane fade ");
				                      }
				                      if(arrays[i]!='SALES'){
					                      $("#sale_div").attr("class", "tab-pane fade ");
					                  }
				                      if(arrays[i]!='SERVICE'){
				                          $("#server_div").attr("class", "tab-pane fade ");
				                      }
				                      
				                      if(arrays[i]=='PRODUCT'){
				                    		$("#productId").show();
				                    		$("#production_div").attr("class", "tab-pane fade  active in");
				                    	  
				                      }
				                      else if(arrays[i]=='SALES'){
				                        $("#salesId").show();
				                    	$("#sale_div").attr("class", "tab-pane fade  active in");
				                      }
				                      else if(arrays[i]=='PROJECT'){
				                    	  $("#projectId").show();
				                    	  $("#project_div").attr("class", "tab-pane fade  active in");
				                      }
				                        else  if(arrays[i]=='SERVICE'){
				                    		 $("#serviceId").show();
				                    	 	$("#server_div").attr("class", "tab-pane fade  active in");
				                      }
				                      checkedArray.push(arrays[i]);     
				               }
				      });
			  }
			  
			  
	 		 }
	 		   
	 		 if (checkedArray.length == 0){
	 			 $("#tab_div").hide();
	 		 }
	 		 var first = checkedArray[0] ;
	 		 
	 		 if (first != null && first !="" && first !="undefined"){
	 			 loadTab(first);
	 		 }
	 		   
			if ("${currSupplier.status}" == 7) {
				showReason();
			}
			
		 
			 
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
	
	function seach(obj){
		var id=$(obj).next().val();
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
				var liId = $(this).attr("id");
				var liNum = liId.charAt(liId.length - 1);
				if (liNum == num) {
					$(this).attr("class", "active");
				} else {
					$(this).removeAttr("class");
				}
			});
			$("#tab_content_div_id").find(".tab-pane").each(function() {
				var id = $(this).attr("id");
				if (id == defaultPage) {
					$(this).attr("class", "tab-pane fade  active in");
				} else {
					$(this).attr("class", "tab-pane fade ");
				}
			});
		} else {
			$("#page_ul_id").find("li").each(function(index) {
				/* if (index == 0) {
					$(this).attr("class", "active");
				} else {
					$(this).removeAttr("class");
				} */
			});
			$("#tab_content_div_id").find(".tab-pane").each(function(index) {
				if (index == 0) {
					$(this).attr("class", "tab-pane fade  active in");
				} else {
					$(this).attr("class", "tab-pane fade ");
				}
			});
		}
		
 
	});  
    
</script>
	
</head>

<body>
	<c:if test="${currSupplier.status != 7}">
		<%@ include file="/reg_head.jsp"%>
    </c:if>
	<div class="wrapper">
		<!-- 项目戳开始 -->
		<%@include file="supplierNav.jsp" %>
		<!--详情开始-->
	 <div class="sevice_list container mt60">
		  <h2>供应商类型</h2>
		  <div class="col-md-12 col-sm-12 col-xs-12">
		    <div class="col-md-5 col-sm-6 col-xs-6 title tr"></div> 
			  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
			       <c:forEach items="${wlist }" var="obj" >
					    <span><input type="checkbox" name="chkItem" onclick="checks(this)"  value="${obj.code}" /> ${obj.name }</span>
			      </c:forEach>
			      <c:forEach items="${supplieType }" var="obj" >
					    <span><input type="checkbox" name="chkItem" onclick="checks(this)" value="${obj.code }" />${obj.name } </span>
			      </c:forEach>
			      
			  </div>
		  </div>  
	 </div>
		
		
	
						
	  <div class="container content height-350" id="tab_div" style="display:none;">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
					   <ul id="page_ul_id" class="nav nav-tabs supplier_tab">
							 <li id="productId" style="display:none;" ><a aria-expanded="true" href="#production_div" data-toggle="tab" class=" f18">物资-生产型专业信息</a></li>
							 <li id="salesId" style="display:none;" ><a aria-expanded="false" href="#sale_div" data-toggle="tab" class="f18">物资-销售型专业信息</a></li>
							 <li id="projectId" style="display:none;" ><a aria-expanded="false" href="#project_div" data-toggle="tab" class="  f18">工程专业信息</a></li>
							 <li id="serviceId" style="display:none;"><a aria-expanded="false" href="#server_div" data-toggle="tab" class="  f18">服务专业信息</a></li>
					  </ul>			
						
 

	<div style="margin-top: 40px;">
		<form id="save_pro_form_id"  action="${pageContext.request.contextPath}/supplier/perfect_professional.html" method="post">
							<input type="hidden" name="id" id="sid" value="${currSupplier.id}" />
							<input type="hidden" name="flag" />
			 				<input type="hidden" name="defaultPage" value="${defaultPage}" />
				            <div id="tab_content_div_id" class="tab-content padding-top-20 overflow_h container_box">
						<!-- 物资生产型专业信息 -->
			         <%--  <c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PRODUCT')}"> --%>
			              <div class=""    id="production_div">
			              <div class="container container_box">
			              	  <h2 class="list_title">物资-生产型专业信息</h2>
			              	    <ul class="list-unstyled f14">
									<input type="hidden" name="supplierMatPro.id" value="${currSupplier.supplierMatPro.id}" />
									<input type="hidden" name="supplierMatPro.supplierId" value="${currSupplier.id}" />
								 <fieldset class="col-md-12 col-sm-12 col-xs-12 border_font">
										<legend>供应商组织机构和人员</legend>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>组织机构：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input id="supplierName_input_id" type="text" name="supplierMatPro.orgName" value="${currSupplier.supplierMatPro.orgName}" />
													   <span class="add-on cur_point">i</span>
													    <div class="cue"> ${org } </div>
													</div>
												</li>
												
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>人员总数：</span>
													<div class="input-append co-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalPerson" onkeyup="checknums(this)"  value="${currSupplier.supplierMatPro.totalPerson}" />
													   <span class="add-on cur_point">i</span>
													   <div class="cue"> ${person } </div>
													</div>
												</li>
												
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>管理人员数量：</span>
													<div class="input-append co-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalMange" onkeyup="checknums(this)"  value="${currSupplier.supplierMatPro.totalMange}" />
												     	<span class="add-on cur_point">i</span>
												     	<div class="cue"> ${mange } </div>
													</div>
												</li>
												
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术人员数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalTech" onkeyup="checknums(this)"  value="${currSupplier.supplierMatPro.totalTech}" />
													    <span class="add-on cur_point">i</span>
													    <div class="cue"> ${tech } </div>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>工人数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalWorker" onkeyup="checknums(this)" value="${currSupplier.supplierMatPro.totalWorker}" />
													   <span class="add-on cur_point">i</span>
													   <div class="cue"> ${work } </div>
													</div>
												</li>
												<!-- <div class="clear"></div> -->
									</fieldset>
	 
						            <fieldset class="col-md-12 border_font mt20">
	 											<legend>产品研发能力 </legend>
	 			
										<!-- 	<ul class="list-unstyled list-flow"> -->
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术人员数量比例(%)：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.scaleTech" value="${currSupplier.supplierMatPro.scaleTech}" />
													    <span class="add-on cur_point">i</span>
													    <div class="cue"> ${stech } </div>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>高级技术人员数量比例(%)：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.scaleHeightTech" value="${currSupplier.supplierMatPro.scaleHeightTech}" />
													   <span class="add-on cur_point">i</span>
													    <div class="cue"> ${height } </div>
													</div>
												</li>
												
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>研发部门名称：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.researchName" value="${currSupplier.supplierMatPro.researchName}" />
													   <span class="add-on cur_point">i</span>
													   <div class="cue"> ${reName } </div>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>研发部门人数：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalResearch" onkeyup="checknums(this)" value="${currSupplier.supplierMatPro.totalResearch}" />
													    <span class="add-on cur_point">i</span>
													    <div class="cue"> ${tRe } </div>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>研发部门负责人：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.researchLead" value="${currSupplier.supplierMatPro.researchLead}" />
													   <span class="add-on cur_point">i</span>
													   <div class="cue"> ${leader } </div>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>国家军队科研项目：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.countryPro" value="${currSupplier.supplierMatPro.countryPro}" />
													   <span class="add-on cur_point">i</span>
													   <div class="cue"> ${contry } </div>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>国家军队科技奖项：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.countryReward" value="${currSupplier.supplierMatPro.countryReward}" />
													   <span class="add-on cur_point">i</span>
													    <div class="cue"> ${reward } </div>
													</div>
												</li>
												<!-- <div class="clear"></div> -->
										</fieldset>
	
	
	
										<fieldset class="col-md-12 border_font mt20">
	 											<legend>供应商生产能力  </legend>
											 
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>生产线数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalBeltline" onkeyup="checknums(this)" value="${currSupplier.supplierMatPro.totalBeltline}" />
														   <span class="add-on cur_point">i</span>
														   <div class="cue"> ${line } </div>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>生产设备数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.totalDevice" onkeyup="checknums(this)" value="${currSupplier.supplierMatPro.totalDevice}" />
													  	   <span class="add-on cur_point">i</span>
													  	   <div class="cue"> ${device } </div>
													</div>
												</li>
										 
											</fieldset>
											
											<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
	 											<legend>物资生产型供应商质量检测能力登记  </legend>
	 											
										 
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>质量检测部门：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.qcName" value="${currSupplier.supplierMatPro.qcName}" />
													    <span class="add-on cur_point">i</span>
													     <div class="cue"> ${qcName } </div>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>质量部门人数：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
													 	<input type="text" name="supplierMatPro.totalQc" onkeyup="checknums(this)" value="${currSupplier.supplierMatPro.totalQc}" />
													  <span class="add-on cur_point">i</span>
													  <div class="cue"> ${tQc } </div>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>质监部门负责人：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.qcLead" value="${currSupplier.supplierMatPro.qcLead}" />
														 <span class="add-on cur_point">i</span>
														 	 <div class="cue"> ${tqcLead } </div>
													</div>
												
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>质量检测设备名称：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatPro.qcDevice" value="${currSupplier.supplierMatPro.qcDevice}" />
												     <span class="add-on cur_point">i</span>
												     	 <div class="cue"> ${tqcDevice } </div>
													</div>
												
												</li>
											 
											 
											 </fieldset>
									</ul>					 
											 
							 	<fieldset class="col-md-12 border_font mt20">
						 	      <legend>供应商物资生产资质证书</legend>
						 	      	<div  class="col-md-12 col-sm-12 col-xs-12 p0 mb50">
											<div class="col-md-12 col-sm-12 col-xs-12 mb10 p0">
											  <button type="button" class="btn btn-windows add" onclick="openCertPro()">新增</button>
											  <button type="button" class="btn btn-windows delete" onclick="deleteCertPro()">删除</button>
											  <span class="red">${cert_pro }</span>
											</div>
											<table class="table table-bordered table-condensed mt5">
											<!-- <div class="col-md-12 col-xs-12 col-sm-12 over_scroll">
					                       <table class="table table-bordered table-condensed mt5 table_input table_wrap"> -->
												<thead>
													<tr>
														<th class="info"><input type="checkbox" onchange="checkAll(this, 'cert_pro_list_tbody_id')"/></th>
														<th class="info">资质证书名称</th>
														<th class="info">资质等级</th>
														<th class="info">发证机关</th>
														<th class="info">有效期（起止时间）</th>
														<th class="info">有效期（结束时间）</th>
														<th class="info">是否年检</th>
														<th class="info">附件</th>
													</tr>
												</thead>
												<tbody id="cert_pro_list_tbody_id">
													<c:forEach items="${currSupplier.supplierMatPro.listSupplierCertPros}" var="certPro" varStatus="vs">
														<tr>
															<td class="tc"><input type="checkbox" value="${certPro.id}" /></td>
															<td class="tc"><input type="text" nam="listSupplierCertPros[${vs.index }].name" value="${certPro.name}"/> </td>
															<td class="tc"><input type="text" nam="listSupplierCertPros[${vs.index }].levelCert" value="${certPro.levelCert}" /> </td>
															<td class="tc"><input type="text" nam="listSupplierCertPros[${vs.index }].licenceAuthorith" value="${certPro.levelCert}"  value="${certPro.licenceAuthorith}"/></td>
															<td class="tc">
															<input type="text" readonly="readonly" onClick="WdatePicker()" name="listSupplierCertPros[${vs.index }].expStartDate" value="<fmt:formatDate value="${certPro.expEndDate}"/>"   />
														  </td>
															<td class="tc">
																<input type="text" readonly="listSupplierCertPros[${vs.index }].expEndDate" onClick="WdatePicker()" name="businessStartDate" value="<fmt:formatDate value="${certPro.expEndDate}"/>"  />
														 </td>
															<td class="tc">
															   <select name="listSupplierCertPros[${vs.index }].mot">
														          <option value="1" <c:if test="${certPro.mot=='1'}"> selected="selected"</c:if> >是</option>
														          <option value="0"  <c:if test="${certPro.mot=='0'}"> selected="selected"</c:if>>无</option>
														        </select>
															<%-- ${certPro.mot} --%>
															
															</td>
															<td class="tc">
															 <u:upload id="pro_up" multiple="true"   businessId="${certPro.id}" typeId="${attid}" sysKey="1"  auto="true" />
															 <u:show showId="pro_show" businessId="${certPro.id}"  typeId="${attid}" sysKey="1" />
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									  </div>
									</fieldset>
								</div>
				<%-- 			</c:if>
				
 
				<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SALES')}">		 --%>
								<!-- 物资销售型专业信息 -->
						<div   class="tab-pane fade" id="sale_div">
						  <div class="">
			              	  <h2 class="list_title" >物资-销售专业信息</h2>
			              	    <ul class="list-unstyled" style="font-size: 14px">
										<input type="hidden" name="supplierMatSell.id" value="${currSupplier.supplierMatSell.id}" />
										<input type="hidden" name="supplierMatSell.supplierId" value="${currSupplier.id}" />
								        <fieldset class="col-md-12 col-sm-12 col-xs-12 border_font">
	 			 						     <legend>供应商组织机构和人员 </legend>
												<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>组织机构：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatSell.orgName" value="${currSupplier.supplierMatSell.orgName}" />
														 <span class="add-on cur_point">i</span>
														 <div class="cue"> ${sale_org } </div>
													</div>
													
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>人员总数：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatSell.totalPerson" onkeyup="checknums(this)" value="${currSupplier.supplierMatSell.totalPerson}" />
														 <span class="add-on cur_point">i</span>
														 	<div class="cue"> ${sale_person } </div>
													</div>
												
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>管理人员数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatSell.totalMange" onkeyup="checknums(this)" value="${currSupplier.supplierMatSell.totalMange}" />
													    <span class="add-on cur_point">i</span>
													    <div class="cue"> ${sale_mange } </div>
													</div>
													
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术人员数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatSell.totalTech" onkeyup="checknums(this)"  value="${currSupplier.supplierMatSell.totalTech}" />
														   <span class="add-on cur_point">i</span>
														   	<div class="cue"> ${sale_tech } </div>
													</div>
												
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>工人（职员）数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatSell.totalWorker" onkeyup="checknums(this)" value="${currSupplier.supplierMatSell.totalWorker}" />
														   <span class="add-on cur_point">i</span>
														   <div class="cue"> ${sale_work } </div>
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
											<button type="button" class="btn btn-windows add" onclick="openCertSell()">新增</button>
											<button type="button" class="btn btn-windows delete" onclick="deleteCertSell()">删除</button>
											<span class="red">${sale_cert }</span>
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
															<td class="tc">
															<c:if test="${certSell.mot==1}">
															是
															</c:if>
															<c:if test="${certSell.mot==0}">
															否 
															</c:if>
															</td>
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
		 		<%-- 	</c:if>
				 <c:if test="${fn:contains(currSupplier.supplierTypeIds, 'PROJECT')}"> --%>
								
								<!-- 工程专业信息 -->
		 			<div class="tab-pane fade height-200"    id="project_div">
   						<div class="">
			              	  <h3 class="headline-v2" style="background-color: #FBFBFB " >工程专业信息:</h3>
			              	    <ul class="list-unstyled" style="font-size: 14">
									   <!--   <div class="col-md-5 title"><span class="star_red fl">*</span>工程专业信息：</div> -->
										<input type="hidden" name="supplierMatEng.id" value="${currSupplier.supplierMatEng.id}" />
										<input type="hidden" name="supplierMatEng.supplierId" value="${currSupplier.id}" />
										 
										 	<fieldset class="col-md-12 border_font mt20">
											 	  <legend>法人代表信息</legend>
	 	  
										
												<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>组织机构：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatEng.orgName" value="${currSupplier.supplierMatEng.orgName}" />
													    <span class="add-on cur_point">i</span>
													    <div class="cue"> ${eng_org } </div>
													</div>
													
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术负责人：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatEng.totalTech" onkeyup="checknums(this)" value="${currSupplier.supplierMatEng.totalTech}" />
													    <span class="add-on cur_point">i</span>
													    <div class="cue"> ${eng_tech } </div>
													</div>
													
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>中级以上职称人员数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatEng.totalGlNormal" onkeyup="checknums(this)" value="${currSupplier.supplierMatEng.totalGlNormal}" />
												        <span class="add-on cur_point">i</span>
												        <div class="cue"> ${eng_normal } </div>
													</div>
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>现场管理人员数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatEng.totalMange" onkeyup="checknums(this)"  value="${currSupplier.supplierMatEng.totalMange}" />
													    <span class="add-on cur_point">i</span>
													    <div class="cue"> ${eng_manage } </div>
													</div>
													
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术和工人数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatEng.totalTechWorker" onkeyup="checknums(this)" value="${currSupplier.supplierMatEng.totalTechWorker}" />
													    <span class="add-on cur_point">i</span>
													    <div class="cue"> ${eng_worker } </div>
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
											<span class="red">${eng_persons }</span>
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
	 	              				<legend>添加供应商工程证书信息</legend>
											
										 <div class="col-md-12 p0">
											<div class="fl">
												<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteCertEng()">删除</button>
												<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openCertEng()">新增</button>
											    <span class="red">${eng_cert}</span>
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
													<!-- 	<th class="info">附件</th> -->
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
															<td class="tc">
															<c:if test="${certEng.certStatus==1}">
															  有效
															</c:if>
															<c:if test="${certEng.certStatus==0}">
															  无效
															</c:if>
															
															</td>
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
									 <div class="col-md-12 p0">
											  <div class="fl">
												<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteAptitute()">删除</button>
												<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openAptitute()">新增</button>
												<span class="red">${eng_aptitutes }</span>
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
															<td class="tc"> 
														   <c:if test="${aptitute.isMajorFund==1}">
															    是
															</c:if>
															<c:if test="${aptitute.isMajorFund==0}">
														         否
															</c:if>
															
															</td>
															<td class="tc">${aptitute.aptituteContent}</td>
															<td class="tc">${aptitute.aptituteCode}</td>
															<td class="tc"><fmt:formatDate value="${aptitute.aptituteDate}" pattern="yyyy-MM-dd" /></td>
															<td class="tc">${aptitute.aptituteWay}</td>
															<td class="tc">
															<c:if test="${aptitute.aptituteStatus==1}">
															   有效
															</c:if>
															<c:if test="${aptitute.aptituteStatus==0}">
														             无效
															</c:if>
															
															</td>
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
						
		<%-- 		 </c:if>
				 	<c:if test="${fn:contains(currSupplier.supplierTypeIds, 'SERVICE')}"> --%>
							 <div class="tab-pane fade height-200"  id="server_div">
									<div class="">
					              	  <h3 class="headline-v2" style="background-color: #FBFBFB ">服务专业信息:</h3>
					              	    <ul class="list-unstyled" style="font-size: 14">
							 	
									 
										<input type="hidden" name="supplierMatSe.id" value="${currSupplier.supplierMatSe.id}" />
										<input type="hidden" name="supplierMatSe.supplierId" value="${currSupplier.id}" />
												<fieldset class="col-md-12 border_font mt20">
											 	  <legend>法人代表信息</legend>
												<li class="col-md-3 col-sm-6 col-xs-12 pl10"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>组织机构：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatSe.orgName" value="${currSupplier.supplierMatSe.orgName}" />
													   <span class="add-on cur_point">i</span>
													     <div class="cue"> ${fw_org } </div>
													</div>
												  
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>人员总数：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatSe.totalPerson" onkeyup="checknums(this)" value="${currSupplier.supplierMatSe.totalPerson}" />
													     <span class="add-on cur_point">i</span>
													     <div class="cue"> ${fw_person } </div>
													</div>
													
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>管理人员数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatSe.totalMange" onkeyup="checknums(this)" value="${currSupplier.supplierMatSe.totalMange}" />
													    <span class="add-on cur_point">i</span>
													    <div class="cue"> ${fw_mange } </div>
													</div>
													
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>技术人员数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatSe.totalTech" onkeyup="checknums(this)" value="${currSupplier.supplierMatSe.totalTech}" />
													     <span class="add-on cur_point">i</span>
													     	<div class="cue"> ${fw_tech } </div>
													</div>
												
												</li>
												<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>工人（职员）数量：</span>
													<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
														<input type="text" name="supplierMatSe.totalWorker" onkeyup="checknums(this)" value="${currSupplier.supplierMatSe.totalWorker}" />
													    <span class="add-on cur_point">i</span>
													    	<div class="cue"> ${fw_work } </div>
													</div>
												
												</li>
										 	</fieldset>
										 </ul>
										 
										 
										 
									<fieldset class="col-md-12 col-sm-12 col-xs-12 border_font mt20">
	 	              					<legend> 供应商资服务质证书 </legend>
										<div class="col-md-12 p0">
													<div class="fl">
														<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="deleteCertSe()">删除</button>
														<button type="button" class="btn padding-left-20 padding-right-20 btn_back margin-5 fr" onclick="openCertSe()">新增</button>
														<span class="red">${fw_cert }</span>
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
					<%-- </c:if> --%>
							
						</div>
						  <input name="supplierTypeIds" type="hidden" />
						  
						  <input type="hidden" value="${currSupplier.supplierTypeIds }" id="supplierTypes">	 
						   </div>
						</form>
					</div>		
				  </div>
				</div>
			</div>
		</div>
					
		</div>
		
	 <div class="btmfix">
	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	   	<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="prev();">上一步</button>
				<button type="button" class="btn padding-left-20 padding-right-20 margin-5" onclick="ajaxSave();">暂存</button>
				<input type="button" class="btn padding-left-20 padding-right-20 margin-5" value="下一步" onclick="next(1)"></input>
	  	  </div>
	</div>
	
		
<!-- 		
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
	</div> -->
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
