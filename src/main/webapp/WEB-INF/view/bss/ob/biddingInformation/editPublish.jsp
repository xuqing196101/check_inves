<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
	    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<title>发布竞价信息页面</title>
<script type="text/javascript">
	  var number=10000001;
	  //选择数量
	  var suppCount=0;
	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("productId");
		 var checkAll = document.getElementById("checkAll");
		   if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("productId");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
    //下载模板
    function down(){
     window.location.href ="${pageContext.request.contextPath}/ob_project/download.html";
    }
	//定义采购集合
	var list=null;
	//定义产品集合
	var productList=null;
	//加载采购机构 下拉数据
	
	$(function(){
	 var index = layer.load(0, {
				shade : [ 0.1, '#fff' ],
				offset : [ '45%', '53%' ]
			});
		$.ajax({
			url: "${pageContext.request.contextPath }/ob_project/mechanism.html",
			contentType: "application/json;charset=UTF-8",
			dataType: "json", //返回格式为json
			type: "POST", //请求方式           
			success: function(data) {
				if (data) {
				list=data;
					$.each(data, function(i, user) {
					//需求
					if(user.typeName==0){
					  $("#demandUnit").append("<option  value=" + user.id + ">" + user.shortName + "</option>");
					}
					//采购
					if(user.typeName==1){
					$("#orgId").append("<option  value=" + user.id + ">" + user.shortName + "</option>");
					 }
				  });
				} 
			 $("#orgId").select2();
			 $("#orgId").select2('val','${list.orgId}');
			  $("#demandUnit").select2();
			 $("#demandUnit").select2('val','${list.demandUnit}');
			}
		});
		
		//加载产品相关信息 下拉数据
		  $.ajax({
			url: "${pageContext.request.contextPath }/ob_project/product.html",
			contentType: "application/json;charset=UTF-8",
			dataType: "json", //返回格式为json
			type: "POST", //请求方式           
			success: function(data) {
				if (data) {
				productList=data;//延迟加载 数据
   
     /*   var plists='${listinfo}';
         if(plists){
        var i=0;
  		  $.each( plists, function(i, value) {
  		    addTr(value.productId,value.productId,value.limitedPrice,value.purchaseCount,value.remark);
		       $("#productName_"+number).combobox('select',value.productId); 
		      }) ;  
		     } */
			} 
		   }
		 });
		 $("#tradedSupplierCount").select2();
		 $("#tradedSupplierCount").select2('val','${list.tradedSupplierCount}');
		 // 供应商成交比例数据回显
		 tradedCount();
		 
		  //加载运杂费 数据
		 $.ajax({
			url: "${pageContext.request.contextPath }/ob_project/transportFeesType.html",
			contentType: "application/json;charset=UTF-8",
			dataType: "json", //返回格式为json
			type: "POST", //请求方式           
			success: function(data) {
				if (data) {
					$.each(data, function(i, user) {
					  $("#transportFees").append("<option  value=" + user.id + ">" + user.name + "</option>");
				  });
				} 
			 $("#transportFees").select2();
			 $("#transportFees").select2('val','${list.transportFees}');
			}
		});
		// 获取乙方包干使用运杂费
		/* if(transportFees != null){
			$("#transportFeesPriceLi").css("display","block");
		} */
		layer.close(index);
		  $("#radio [name='isEmergency']").each(function(){
		  if($(this).val()=='${list.isEmergency}'){
		  $(this).attr("checked",true);
		  }
		 });
	});
	
	//动态改变 比例--数据回显
	function tradedCount(){
	var count=$("#tradedSupplierCount").val();
	// 如果未选择则置为空
	if(count == ''){
		$("#tradedSupplier").val("");
		return;
	}
	if(count){
      $.ajax({
				url: "${pageContext.request.contextPath }/ob_project/proportion.do",
				type: "POST",
				data: {
					supplierCount: count
				},
				success: function(data) {
				 $("#tradedSupplier").val(data);
		 }
     });
     }
	}
	
	//根据下拉框信息改变 采购联系人 采购联系电话
	function changSelect(){
	   if(list){
	  	var value=  $("#orgId").val();
	  	$.each(list, function(i, user) {
	  	   if(user.id==value){
	    	$("#orgContactTel").val(user.contactMobile);
	    	 $("#orgContactName").val(user.contactName);
	    	 }
	 	});
	  }
	}//根据下拉跟新 需求单位联系人 联系电话
	function changDemandUnit(){
	if(list){
	  	var value=  $("#demandUnit").val();
	  	$.each(list, function(i, user) {
	  	   if(user.id==value){
	    	$("#contactTel").val(user.telephone);
	    	 $("#contactName").val(user.princinpal);
	    	 }
	 	});
	  }
	}
	
		// 弹出导入框
	var index;
	function uploadExcl(){
	 index = layer.open({
		type: 1, //page层
		area: ['400px', '300px'],
		title: '导入定型产品',
		closeBtn: 1,
		shade: 0.01, //遮罩透明度
		moveType: 1, //拖拽风格，0是默认，1是传统拖动
		shift: 1, //0-6的动画形式，-1不开启
		offset: ['80px', '400px'],
		content: $('#file_div'),
		});
	}
	//根据选中获取name
	function getSelectName(checkID){
	     var name='';
	    //获取选中全部的产品id
			$('*[name="productName"]').each(function(){
			  if($(this).val()==checkID){
		        name=$(this).find("option:selected").text();
			  }
		  });
		return name;
	}
	function loads(number,id){
	$.each(productList, function(i, user) {
		    $("select[id=\"productName_"+number+"\"]").append("<option  value=" + user.id + ">" + user.name+ "</option>");
	     });
	     $("select[id=\"productName_"+number+"\"]").select2();
	     if(id){
		 $("#productName_"+number+"").select2('val',id); 
	    }
	  } 
	  
	 function addTr(productId,productName,productMoney,producCount,productRemark){
	      ++number;
	      if(!productMoney){
	      productMoney='';
	      }
	      if(!productRemark){
	      productRemark='';
	      }
		 $("#table2").append("<tr><td class=\"tc w30\"><input onclick=\"check()\" type=\"checkbox\" name=\"productId\" id=\"productId\" value=\""+productId+"\" /></td>"+
		  "<td class=\"p0\" ><div id=\"selectDiv"+number+"\" onmouseover='showPrompt(\"selectDiv"+number+"\",\"productName_"+number+"\")'  onmouseout=\"closePrompt()\" name=\"selectDiv\"><select id=\"productName_"+number+"\" disabled=\"disabled\"  name=\"productName\" onchange=\"changSelectCount("+number+")\" ><option value=\"\"></option></select>"+
		  "</div></td>"+
		  "<td class=\"p0\" id=\"t"+number+"\"><input id=\"productMoney\" maxlength=\"20\" disabled=\"disabled\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" name=\"productMoney\" value=\""+productMoney+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		  "<td class=\"p0\"><input id=\"productCount\" maxlength=\"38\" disabled=\"disabled\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" name=\"productCount\" value=\""+producCount+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		  "<td class=\"p0\"><input id=\"productRemark\" maxlength=\"1000\" disabled=\"disabled\" name=\"productRemark\" value=\""+productRemark+"\"  title=\""+productRemark+"\" type=\"text\" class=\"w230 mb0\">"+
		  "  </td>"+
		"</tr>").clone(true);   
		//加载数据
	} 
	 
		  //关闭
	function closePrompt(){
	layer.closeAll('tips');
	}
	  // 显示
    function showPrompt(id,productId){
   		  if(productId){
   		  $.ajax({
   				async: false,
				url: "${pageContext.request.contextPath }/product/productType.do",
				type: "POST",
				data: {productId:productId},
				success: function(data) {
					if(data){
						var quality = data.qualityTechnicalStandard;
						if(quality == null){
							quality = "无";
						}
	   	  			layer.tips("产品规格型号："+data.standardModel+"<br/>"+"质量技术标准："+quality, 
	   	    		'#'+id, {tips: [1, '#78BA32'],time:-1,area: ['500px', 'auto'],});
				}else{
				 inder=layer.tips("", 
       	    '#'+id, {tips: [1, '#78BA32']});
				}
		      },error:function(){
		       layer.tips("错误！", 
       	    '#'+id, {tips: [1, '#78BA32']});
		      }
           });
           }
       	}
	function show(content) {
		layer.alert(content, {
			offset : [ '30%', '40%' ]
		});
	}
	function printWord(){
			window.location.href="${pageContext.request.contextPath}/ob_project/printResult.html?print=print&&id=${list.id}";
		}
</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li>
					<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
				</li>
				<li><a href="javascript:void(0)">保障作业</a></li>
				<li><a href="javascript:void(0)">网上竞价</a></li>
				<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/ob_project/list.html')">竞价信息管理</a></li>
				<li class="active"><a href="javascript:void(0)">查看竞价信息</a></li>
			</ul>
			<div class="clear"></div>
		</div>
    </div>
    <!-- 修改订列表开始-->
  <div class="container container_box">
  <div class="mt10">
  <c:if test="${ list.status==3 or  list.status==4}">
   <button class="btn btn-windows print" onclick="printWord()">打印</button>
   </c:if>
	  <a class="btn btn-windows back"  href="${pageContext.request.contextPath}/ob_project/list.html">返回</a>
   </div>
  <form id="myForm" action="" method="post" class="mb0">
  <input type="hidden" id="status" name="status">
  <input type="hidden" id="attachmentId" name="attachmentId" value="${fileid}">
  <input type="hidden" id="id" name="id" value="${list.id}">
  <input type="hidden" id="ruleId" name="ruleId" value="${ruleId}">
  <!-- <input type="hidden" id="supplieId" name="supplieId" > -->
  <input type="hidden" id="suppliePrimaryId" name="suppliePrimaryId" >
     <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
     <ul class="ul_list">
       <%-- <li class="col-md-3 col-sm-6 col-xs-12 pl15">
		   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价项目编号(保存后生成)</span>
		   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	        <input class="input_group" id="number"  value="${list.projectNumber}" name="number" type="text" readonly="readonly"  maxlength="100">
	        <span class="add-on">i</span>
	        <span class="input-tip">保存后自动生成</span>
	       </div>
	 	</li> --%>
	  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价项目名称</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="name" disabled="disabled"  value="${list.name}" name="name" type="text"  maxlength="100">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="nameErr">${nameErr}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>成交供应商数</span>
	   <select class="input_group" id="tradedSupplierCount" disabled="disabled" name="tradedSupplierCount" onchange="tradedCount()" >
	   <option value="">--请选择--</option>
	   <option value="1">1</option>
	   <option value="2" >2</option>
	   <option value="3" >3</option>
	   <option value="4" >4</option>
	   <option value="5" >5</option>
	   <option value="6">6</option>
	   </select>
        <div class="cue" id="tradedSupplierCountErr">${tradedSupplierCountErr}</div>
	 </li>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商成交比例</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="tradedSupplier" value="" name=""  disabled="disabled" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">自动获取</span>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>交货时间</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" name="deliveryDeadline" id="deliveryDeadline" disabled="disabled" maxlength="19" value="<fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd HH:ss:mm"/>"  readonly="readonly"
         onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  type="text">
        <span class="add-on">i</span>
         <span class="input-tip">不能为空</span>
        <div class="cue" id="deliveryDeadlineErr">${deliveryDeadlineErr}</div>
       </div>
	 </li>
	 <%-- <li class="col-md-3 col-sm-6 col-xs-12 pl15">
		   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价项目编号(保存后生成)</span>
		   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	        <input class="input_group" id="number"  value="${list.projectNumber}" name="number" type="text" readonly="readonly"  maxlength="100">
	        <span class="add-on">i</span>
	        <span class="input-tip">保存后自动生成</span>
	       </div>
	 	</li> --%>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>交货地点</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="deliveryAddress" value="${list.deliveryAddress }" disabled="disabled" maxlength="150" name="deliveryAddress" type="text" >
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="deliveryAddressErr">${deliveryAddressErr}</div>
       </div>
	 </li> 
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>需求单位</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<select id="demandUnit" name="demandUnit" disabled="disabled" onchange="changDemandUnit()" >
			  <option value="">--请选择--</option>
			</select>
        <div class="cue" id="demandUnitErr">${demandUnitErr}</div></div>
	 </li> 
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>联系人</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="contactName" name="contactName" disabled="disabled" readonly="readonly" value="${list.contactName }" maxlength="20" type="text">
        <span class="add-on">i</span>
           <span class="input-tip">不能为空</span>
        <div class="cue" id="contactNameErr">${contactNameErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>联系电话</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="contactTel" name="contactTel" disabled="disabled" value="${list.contactTel }" readonly="readonly" maxlength="20" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="contactTelErr">${contactTelErr}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>运杂费支付方式</span>
			<select id="transportFees" name="transportFees" disabled="disabled" >
			</select>
        <div class="cue" id="transportFeesErr">${transportFeesErr}</div>
	 </li>
	 <c:if test="${ !empty list.transportFeesPrice }">
		 <li id="transportFeesPriceLi" class="col-md-3 col-sm-6 col-xs-12">
		   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>运杂费金额(元)</span>
		   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				<input class="input_group" id="transportFeesPrice" disabled="disabled" name="transportFeesPrice" value="${ list.transportFeesPrice }" onkeyup="this.value=this.value.replace(/[^\d.]/g, '')"  onafterpaste="this.value=this.value.replace(/[^\d.]/g, '')" type="text">
		        <span class="add-on">i</span>
		        <span class="input-tip"></span>
		        <div class="cue" id="transportFeesPriceErr">${transportFeesPriceErr}</div>
	        </div>
		 </li> 
	 </c:if>
	<li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>采购机构</span>
			<select id="orgId" name="orgId" onchange="changSelect()" disabled="disabled" >
			  <option value="">--请选择--</option>
			</select>
			 <div class="cue" id="orgIdErr">${orgIdErr}</div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>采购联系电话</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="orgContactTel" name="orgContactTel" disabled="disabled" value="${list.orgContactTel }" readonly="readonly" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空,只可以是数字</span>
        <div class="cue" id="orgContactTelErr">${orgContactTelErr}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>采购联系人</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="orgContactName" name="orgContactName" disabled="disabled" value="${list.orgContactName }"  readonly="readonly" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="orgContactNameErr">${orgContactNameErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价项目编号</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="number"  value="${list.projectNumber}" name="number" type="text" disabled="disabled"  maxlength="100">
        <span class="add-on">i</span>
        <span class="input-tip">自动生成</span>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12" title="应急采购项目，只有1家供应商报价的，可以成交"><span class="red">*</span>是否为应急采购项目</span>
	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
	   <div class="select_check" id="radio">
	   <input type="radio" name="isEmergency" id ="isEmergency" disabled="disabled" value="-1">否
	   <input type="radio" name="isEmergency" id ="isEmergency" disabled="disabled" value="0">是
	 </div>
	   
       <div class="cue" id="isEmergencyErr">${isEmergencyErr}</div>
       </div>
	 </li>
	 
	 
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价项目附件</span>
	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
            <u:show showId="project" groups="b,c,d"  delete="false" businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" />
       <div class="cue" id="fileUploadErr">${fileUploadErr}</div>
       </div>
	 </li>

	  <li class="col-md-12 col-sm-12 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价内容</span>
	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
       <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:100px"  name="content"  disabled="disabled" title="不超过1000个字" maxlength="1000">${list.content}</textarea>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="contentErr">${contentErr}</div>
       </div>
	 </li>
	 </ul>
	 	 <h2 class="count_flow"><i>2</i>竞价规则详情</h2>
	 <%@ include file ="/WEB-INF/view/bss/ob/biddingRules/ruleCommon.jsp" %>
	 
	<h2 class="count_flow"><i>3</i>产品信息</h2>
	 <div class="ul_list" >
    	  <table class="table table-bordered left_table" id ="table2">
			<tr>
		  		<th class="info" width="45%"><span class="red star_red">*</span>定型产品名称</th>
		  		<th class="info" width="10%">限价（元）</th>
		  		<th class="info" width="14%"><span class="red star_red">*</span>采购数量</th>
		  		<th class="info" width="30%">备注</th>
			</tr>
			<c:forEach  items="${listinfo}" var="va" varStatus="vs">
			<tr>
		  <td class="p0" >
		  <div id="selectDiv_${va.productId}" onmouseover='showPrompt("selectDiv_${va.productId}","${va.productId}")'  
		  onmouseout="closePrompt()" >
		  <input id="productMoney" disabled="disabled" 
		  name="productMoney" value="${va.obProduct.name }" type="text" class="w230 mb0">
		  </div></td>
		  <td class="p0" >
		  <input id="productMoney" maxlength="20" disabled="disabled" 
		  onkeyup="this.value=this.value.replace(/\\D/g,'')"  
		  name="productMoney" value="${va.limitedPrice }" type="text" class="w230 mb0"></td>
		  <td class="p0">
		  <input id="productCount" maxlength="38" disabled="disabled" 
		  onkeyup="this.value=this.value.replace(/\\D/g,'')"  
		   name="productCount" value="${va.purchaseCount }" type="text" class="w230 mb0"></td>
		  <td class="p0">
		  <input id="productRemark" maxlength="1000" disabled="disabled"  value="${va.remark }" 
		   title="${va.remark }" type="text" class="w230 mb0">
		    </td>
		</tr>
		</c:forEach>
		  </table>
		</div>
		<c:if test="${selectInfoByPID!=null and selectInfoByPID.size()>0}">
		
	 <h2 class="count_flow"><i>4</i>供应商信息</h2>
	  <span><font size="4">供应商确认中标比例为${countProportion }%，未中标比例为${100 - countProportion }%.</font></span>
	 <span style="display: block;height: 20px"><font size="4"></font></span>
	   <%@ include file ="/WEB-INF/view/bss/ob/supplier/supplierCommon.jsp" %>
		</c:if>
  </form>
  
</div>
</body>
</html>