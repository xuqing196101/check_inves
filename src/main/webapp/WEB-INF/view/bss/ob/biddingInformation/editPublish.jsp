<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
	    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<title>查看竞价信息页面</title>
<script type="text/javascript">
	  var number=10000001;
	//定义采购集合
	var list=null;
	//定义产品集合
	var productList=null;
	//加载采购机构 下拉数据
	$(function(){
		$.ajax({
			url: "${pageContext.request.contextPath }/ob_project/mechanism.html",
			contentType: "application/json;charset=UTF-8",
			dataType: "json", //返回格式为json
			type: "POST", //请求方式           
			success: function(data) {
				if (data) {
				list=data;
					$.each(data, function(i, user) {
						$("#orgId").append("<option  value=" + user.id + ">" + user.shortName + "</option>");
					});
				} 
			 $("#orgId").select2();
			 $("#orgId").select2('val','${list.orgId}');
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
   
       var plists='${listinfo}';
         if(plists){
          var temp=eval(plists);
  		  $.each( temp, function(i, value) {
		    addTr(value.productId,value.productId,value.limitedPrice,value.purchaseCount,value.remark);
		  }) ; 
		  }
				} 
			}
		 });
		 $("#tradedSupplierCount").select2();
		 $("#tradedSupplierCount").select2('val','${list.tradedSupplierCount}');
		 tradedCount();
	});
	//根据下拉框信息改变 采购联系人 采购联系电话
	function changSelect(){
	   if(list){
	  	var value=  $("#orgId").val();
	  	$.each(list, function(i, user) {
	  	   if(user.id==value){
	    	$("#orgContactTel").val(user.contactMobile);
	    	 $("#orgContactName").val(user.contactName);
	    	 $("#showorgContactTel").val(user.contactMobile);
	    	 $("#showorgContactName").val(user.contactName);
	    	 }
	 	});
	  }
	}
	 //存储选中的产品的全部供应商id
     var supplielist=[];
	//根据定型产品更新 
	function changSelectCount(){
	 if(productList){
	 	changSupplier();
	 	
	  }else{
	   $("#gys_count").text(0);
	  }
	}
	function changSupplier(){
	     //存储选中的产品id
	     var ds=[];
			//获取选中全部的产品id
			$('*[name="productName"]').each(function(){
			  if($(this).val()){
		      ds.push($(this).val());
			  }
		  });
		  
		  
		  //存储 list;
		  var list=[];
		  var dslength=ds.length;
		  //根据选中的 产品id获取 获取供应商
		  if(dslength>0){
		    //遍历 选中产品id
		  for(var i=0;i<ds.length;i++) { 
		    var temp=ds[i];
		    //便利选中产品 获取选中产品集合
		   	$.each(productList, function(i, user) {
		   	   if(temp==user.id){
		   	   if(user.obSupplierList){
		   	   $.each(user.obSupplierList, function(i, user) {
		   	     list.push(user.supplierId);
	    	     });
		   	    }
		   	   }
		     });
		   }
		    //并集供应商数量 有可能是多个
		   var union=0;
		 if(dslength==1){
		 union=list.length;
		 }else{
		  var tempArray=[];
		   for(var i=0;i<list.length;i++){
		   var count=1;
		      var  templist=list[i];
		      for(var j=0;j<list.length;j++){
		      if(i!=j){
		        //循环比较并集供应商数量
		        if(templist==list[j]){
		          count++;
		        };
		        }
		      };
		        if(count==dslength){
		        //如果相等的数量 等于选中产品的数据 那么就是并集 存储
		        tempArray.push(templist);
		        }
		     }; 
			supplielist=[];
		    //去重复
              for(var i = 0; i < tempArray.length-1; i++){    //从数组第二项开始循环遍历此数组  
                if(supplielist.indexOf(tempArray[i]) == -1){  
                    supplielist.push(tempArray[i]);  
                  }
                }  
   					union= supplielist.length;
		   }
		    $("#gys_count").text(union);
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
	    changSelectCount(number);
	    }
	  } 
	  
	 function addTr(productId,productName,productMoney,producCount,productRemark){
	      ++number;
		   $("#table2").append("<tr><td class=\"tc w30\"><input onclick=\"check()\" type=\"checkbox\" name=\"productId\" id=\"productId\" value=\""+productId+"\" /></td>"+
		  "<td class=\"p0\"><select id=\"productName_"+number+"\" disabled=\"disabled\"  name=\"productName\" onchange=\"changSelectCount("+number+")\" ><option value=\"\"></option></select>"+
		  "</td>"+
		  "<td class=\"p0\"><input id=\"productMoney\" maxlength=\"10\"  disabled=\"disabled\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" name=\"productMoney\" value=\""+productMoney+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		  "<td class=\"p0\"><input id=\"productCount\" maxlength=\"4\" disabled=\"disabled\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" name=\"productCount\" value=\""+producCount+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		  "<td class=\"p0\"><input id=\"productRemark\" maxlength=\"1000\" disabled=\"disabled\" name=\"productRemark\" value=\""+productRemark+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		"</tr>").clone(true);   
		//加载数据
		loads(number,productId);
	} 
	
	//导入excl 
	function fileUpload(){
	 $.ajaxFileUpload ({
	               url: "${pageContext.request.contextPath}/ob_project/upload.do?",  
	               secureuri: false,  
	               fileElementId: 'fileName', 
	               dataType: 'json',
	               success: function (data) { 
	             
	               var bool=true;
	               var chars = ['A','B','C','D'];
	               if(data=="1"){
				     layer.alert("文件格式错误",{offset: ['222px', '390px'], shade:0.01});
					 } 
					 for(var i = 0; i < chars.length ; i ++) {
						 if(data.indexOf(chars[i])!=-1){
						  	 bool=false;
						}
						 }
						if(bool!=true){
						 	   layer.alert(data,{offset: ['222px', '390px'], shade:0.01});
						  }else{
						 	   layer.alert("上传成功",{offset: ['222px', '390px'], shade:0.01});
						       layer.close(index);
						         $.each(data, function(index, value) {
									addTr(value.id,value.code,value.standardModel,value.isDeleted,value.remark);
								}); 
	                 }
	             }
	         }); 
	     }
	
	function tradedCount(){
	var tradedCount=$("#tradedSupplierCount").val();
	if(tradedCount){
      $.ajax({
				url: "${pageContext.request.contextPath }/ob_project/proportion.do",
				type: "POST",
				data: {
					supplierCount: tradedCount
				},
				success: function(data) {
				 $("#tradedSupplier").val(data);
		 }
     });
     }
	}
</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">网上竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价信息管理</a></li><li class="active"><a href="javascript:void(0)">发布竞价信息</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="tab-content">
    <!-- 修改订列表开始-->
  <div class="container container_box">
  <form id="myForm" action="" method="post" class="mb0">
  <input type="hidden" id="status" name="status">
  <input type="hidden" id="attachmentId" name="attachmentId" value="${fileid}">
  <input type="hidden" id="id" name="id" value="${list.id}">
  <input type="hidden" id="ruleId" name="ruleId" value="${ruleId}">
  <input type="hidden" id="supplieId" name="supplieId" >
  <input type="hidden" id="suppliePrimaryId" name="suppliePrimaryId" >
     <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
     <ul class="ul_list">
	  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">竞价标题</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="name"  value="${list.name}" name="name" type="text"  disabled="disabled" maxlength="100">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="nameErr">${nameErr}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">交货时间</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" name="deliveryDeadline" id="deliveryDeadline"  disabled="disabled" maxlength="19" value="<fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd HH:ss:mm"/>"  readonly="readonly"
         onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  type="text">
        <span class="add-on">i</span>
         <span class="input-tip">不能为空</span>
        <div class="cue" id="deliveryDeadlineErr">${deliveryDeadlineErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">交货地点</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="deliveryAddress" value="${list.deliveryAddress }" disabled="disabled" maxlength="150" name="deliveryAddress" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="deliveryAddressErr">${deliveryAddressErr}</div>
       </div>
	 </li> 
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">成交供应商数</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	   <div class="w200">
	   <select class="input_group" id="tradedSupplierCount" name="tradedSupplierCount" disabled="disabled" onchange="tradedCount()">
	   <option value=""></option>
	   <option value="1">1</option>
	   <option value="2">2</option>
	   <option value="3">3</option>
	   <option value="4">4</option>
	   <option value="5">5</option>
	   <option value="6">6</option>
	   </select>
	   </div>
        <div class="cue" id="tradedSupplierCountErr">${tradedSupplierCountErr}</div>
       </div>
	 </li> 
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">需求单位</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="demandUnit" disabled="disabled" name="demandUnit" value="${list.demandUnit}"  maxlength="50" type="text">
        <span class="add-on">i</span>
          <span class="input-tip">不能为空</span>
        <div class="cue" id="demandUnitErr">${demandUnitErr}</div>
       </div>
	 </li> 
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">联系人</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="contactName" name="contactName" disabled="disabled" value="${list.contactName }" maxlength="20" type="text">
        <span class="add-on">i</span>
           <span class="input-tip">不能为空</span>
        <div class="cue" id="contactNameErr">${contactNameErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">联系电话</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="contactTel" name="contactTel" disabled="disabled" value="${list.contactTel }" maxlength="20" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="contactTelErr">${contactTelErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">成交供应比例</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="tradedSupplier" value="" name="" disabled="disabled"  readonly="readonly" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">自动获取</span>
       </div>
	 </li>
	   <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">运杂费</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="transportFees" name="transportFees"  disabled="disabled" value="${list.transportFees}" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="10" type="text">
        <span class="add-on">i</span>
         <span class="input-tip">不能为空,只可以是数字</span>
        <div class="cue" id="transportFeesErr">${transportFeesErr}</div>
       </div>
	 </li> 
	<li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">采购机构</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <div class="w200">
			<select id="orgId" name="orgId" onchange="changSelect()" disabled="disabled">
			  <option value=""></option>
			</select></div>
			 <div class="cue" id="orgIdErr">${orgIdErr}</div>
       </div>
	 </li>
	 
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购联系电话</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="orgContactTel" name="orgContactTel" disabled="disabled" value="${list.orgContactTel }"  type="text">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空,只可以是数字</span>
        <div class="cue" id="orgContactTelErr">${orgContactTelErr}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">采购联系人</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="orgContactName" name="orgContactName" value="${list.orgContactName }"  disabled="disabled" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="orgContactNameErr">${orgContactNameErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">竞价文件</span>
	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
         <u:show showId="project" groups="b,c,d"  delete="false" businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" />
       </div>
	 </li> 
	  

	  <li class="col-md-12 col-sm-12 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">竞价内容</span>
	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
       <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:100px" disabled="disabled" name="content" title="不超过1000个字" maxlength="1000">${list.content}</textarea>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="contentErr">${contentErr}</div>
       </div>
	 </li>
	 </ul>
	<h2 class="count_flow"><i>2</i>产品信息</h2>
	 <div class="ul_list">
  		<div class="col-md-12 col-sm-12 col-xs-12 p0 mt10 mb10">
			<span><font id="buttonErr" class="red star_red"></font></span>
		</div>   
    	  <table class="table table-bordered left_table" id ="table2">
			<tr>
		  		<th class="w50 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  		<th class="info" width="30%"><span class="red star_red">*</span>定型产品名称</th>
		  		<th class="info">限价（元）</th>
		  		<th class="info"><span class="red star_red">*</span>采购数量</th>
		  		<th class="info" width="30%"><span class="red star_red">*</span>备注</th>
			</tr>
		  </table>
		</div>
  </form>
  <div class="col-md-12 clear tc mt10">
	<button class="btn btn-windows back mb20" type="button" onclick="history.go(-1)">返回</button>
   </div>
</div>
</body>
</html>