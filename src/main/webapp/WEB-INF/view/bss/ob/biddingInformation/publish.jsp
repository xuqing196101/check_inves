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
	/*选择删除*/
	function del(){
	var ids =[];
	 $('input[name="productId"]:checked').each(function(){ 
	 ids.push($(this).val());
	  }); 
	  if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: '222px',shade:0.01}, function(index){
				layer.close(index);
				$('input[name="productId"]:checked').each(function(){ 
		       $(this).parent().parent().remove(); 
		       });
		       gysCount();
			});
		}else{
			layer.alert("请选择",{offset: '222px', shade:0.01});
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
		 //加载运杂费 数据
			 $("#transportFees").select2();
				var id='';
		 $.ajax({
			url: "${pageContext.request.contextPath }/ob_project/transportFeesType.html",
			contentType: "application/json;charset=UTF-8",
			dataType: "json", //返回格式为json
			type: "POST", //请求方式           
			success: function(data) {
				if (data) {
					$.each(data, function(i, user) {
					  $("#transportFees").append("<option  value=" + user.id + ">" + user.name + "</option>");
					  if(i=1){
					   id=user.id;
					  }
				  });
				} 
			}
		});
		var fees='${list.transportFees}';
		if(fees){
			 $("#transportFees").select2('val','${list.transportFees}');
		}else{
		     $("#transportFees").select2('val',id );
		}
		 
		 
		 layer.close(index);
	});
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
	 //存储选中的产品的全部供应商id
     var supplielist=[];
	//根据定型产品更新 
	function changSelectCount(){
	 if(productList){
	 	gysCount();
	  }else{
	   $("#gys_count").text(0);
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
	      if(!productMoney){
	      productMoney='';
	      }
	      if(!productRemark){
	      productRemark='';
	      }
		   $("#table2").append("<tr><td class=\"tc w30\"><input onclick=\"check()\" type=\"checkbox\" name=\"productId\" id=\"productId\" value=\""+productId+"\" /></td>"+
		  "<td class=\"p0\" ><div id=\"selectDiv"+number+"\" onmouseover='showPrompt(\"selectDiv"+number+"\",\"productName_"+number+"\")'  onmouseout=\"closePrompt()\" onblur=\"closePrompt()\" name=\"selectDiv\"><select id=\"productName_"+number+"\"   name=\"productName\" onchange=\"changSelectCount("+number+")\" ><option value=\"\"></option></select>"+
		  "</div></td>"+
		  "<td class=\"p0\" id=\"t"+number+"\"><input id=\"productMoney\" maxlength=\"20\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" name=\"productMoney\" value=\""+productMoney+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		  "<td class=\"p0\"><input id=\"productCount\" maxlength=\"38\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" name=\"productCount\" value=\""+producCount+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		  "<td class=\"p0\"><input id=\"productRemark\" maxlength=\"1000\" name=\"productRemark\" value=\""+productRemark+"\" type=\"text\" class=\"w230 mb0\">"+
		  "  </td>"+
		"</tr>").clone(true);   
		//加载数据
		loads(number,productId);
	} 
	  //关闭
	function closePrompt(){
	layer.closeAll('tips');
	}
	  // 显示
    function showPrompt(id,selectID){
   		 var productId=$("#"+selectID).val();
   		  if(productId){
   		  $.ajax({
   				async: false,
				url: "${pageContext.request.contextPath }/product/productType.do",
				type: "POST",
				data: {productId:productId},
				success: function(data) {
				if(data){
       	  layer.tips("产品规格型号："+data.standardModel+"<br/>"+"质量技术标准："+data.qualityTechnicalStandard, 
       	    '#'+id, {tips: [2, '#78BA32'],time:-1});
				}else{
				 inder=layer.tips("", 
       	    '#'+id, {tips: [2, '#78BA32']});
				}
		      },error:function(){
		       layer.tips("错误！", 
       	    '#'+id, {tips: [2, '#78BA32']});
		      }
           });
           }
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
	     //提交
	     function submitProject(status){
	    
	      $("#nameErr").html("");
		  $("#deliveryDeadlineErr").html("");
		  $("#deliveryAddressErr").html("");
		  $("#tradedSupplierCountErr").html("");
		  $("#transportFeesErr").html("");
		  $("#demandUnitErr").html("");
		  
		  $("#contactNameErr").html("");
		  $("#contactTelErr").html("");
		  $("#orgIdErr").html("");
		  $("#orgContactTelErr").html("");
		  $("#orgContactNameErr").html("");
		  
		  $("#startTimeErr").html("");
		  $("#endTimeErr").html("");
		  $("#contentErr").html("");
		  $("#buttonErr").html("");
		   var name=$("#name").val().trim();
		   if(!name){
		   $("#nameErr").html("竞价标题不能为空");
		     show("竞价标题不能为空");
		   return;
		  }
		   if(!$("#deliveryDeadline").val().trim()){
		   $("#deliveryDeadlineErr").html("交货时间不能为空");
		     show("交货时间不能为空");
		   return;
		  }
		   if(!$("#deliveryAddress").val().trim()){
		   $("#deliveryAddressErr").html("交货地点不能为空");
		     show("交货地点不能为空");
		   return;
		  }
		  var supplierCount=$("#tradedSupplierCount").val().trim();
		    if(!supplierCount){
		   $("#tradedSupplierCountErr").html("成交供应商数量不能为空");
		     show("成交供应商数量不能为空");
		   return;
		  }
		   if(!$("#transportFees").val().trim()){
		   $("#transportFeesErr").html("运杂费不能为空");
		    show("运杂费不能为空");
		   return;
		  }
		  
		   if(!$("#demandUnit").val().trim()){
		   $("#demandUnitErr").html("需求单位不能为空");
		   show("需求单位不能为空");
		   return;
		  }
		   if(!$("#contactName").val().trim()){
		   $("#contactNameErr").html("联系人不能为空");
		   show("联系人不能为空");
		   return;
		  } if(!$("#contactTel").val().trim()){
		   $("#contactTelErr").html("联系人电话不能为空");
		   show("联系人电话不能为空");
		   return;
		  }
		  if(!$("#orgId").val().trim()){
		   $("#orgIdErr").html("采购机构不能为空");
		   show("采购机构不能为空");
		   return;
		  }
		  
		   if(!$("#orgContactTel").val().trim()){
		   $("#orgContactTelErr").html("采购联系人电话不能为空");
		   show("采购联系人电话不能为空");
		   return;
		  }
		   if(!$("#orgContactName").val().trim()){
		   $("#orgContactNameErr").html("采购联系人不能为空");
		     show("采购联系人不能为空");
		   return;
		  }
		   if($("#content").html()=='undefined' && $("#content").html()==''){
		   $("#contentErr").html("竞价内容不能为空");
		   show("竞价内容不能为空");
		   return;
		  }  
		  
		  
	if ($('[name="productName"]').length == 0) {
			$("#buttonErr").html("竞价产品不能为空");
			show("竞价产品不能为空");
			return;
		} else {
			var temp = 0;
			var names = [];
			$('*[name="productName"]').each(function() {
				if (!$(this).val().trim()) {
					temp = 1;
					return;
				} else {
					names.push($(this).val());
				}
			});
			//验证 选中产品唯一 产品名称不能为空
			if (temp == 1) {
				$("#buttonErr").html("竞价产品名称不能为空");
				show("竞价产品名称不能为空");
				return;
			} else {
				if (names.length > 1) {
					var tempName = null;
					for ( var i = 0; names.length > i; i++) {
						var name = names[i];
						if (names.indexOf(name) == 0) {
							temp++;
						}
					}
					if (parseInt(temp) > 1) {
						$("#buttonErr").html("竞价产品不可重复");
						show("竞价产品不可重复");
						return;
					}
				}
			}
			temp = 0;
			names = [];
			$('*[id="productCount"]').each(function() {
				var count = $(this).val().trim();
				if (!count) {
					temp = 1;
					return;
				} else {
					names.push($(this).val());
				}
			});

			if (temp == 1) {
				$("#buttonErr").html("竞价产品数量不能为空");
				show("竞价产品数量不能为空");
				return;
			} else {
				if (names.length > 0) {
					var tempName = null;
					for ( var i = 0; names.length > i; i++) {
						var name = names[i];
						if (parseInt(parseInt) < 1) {
							temp++;
						}
					}
					if (parseInt(temp) >= 1) {
						$("#buttonErr").html("竞价产品数量必须大于0");
						show("竞价产品数量不能为空");
						return;
					}
				}
			}
		}
		var supplierCount='${supplierCount}';
		var gyscount=$('#gys_count').html();
		 if(parseInt(supplierCount)>parseInt(gyscount)){
		    show("提供当前产品的供应商不能少于"+supplierCount+"家");
				return;
		 }
		 var productid=[];
	    //获取选中全部的产品id
	   $('*[name="productName"]').each(function(){
			if($(this).val()){
		      productid.push($(this).val());
			  }
		  });
		  if(productid.length!=suppCount){
   		 	 show("提供当前产品的供应商不能少于"+supplierCount+"家");
		     return;
		   }
		  exec(status);
			
	}
    /**执行 */
    function exec(status){
    var index = layer.load(0, {
				shade : [ 0.1, '#fff' ],
				offset : [ '45%', '53%' ]
			});
			$("#status").val(status);
			         $.post("${pageContext.request.contextPath}/ob_project/addProject.html",
							$("#myForm").serialize(),
							function(data) {
								if (data) {
									var json = JSON.parse(data);
									var name = json.attributeName;
									var context = json.show;
									if (name == "success") {
										window.location.href = "${pageContext.request.contextPath}/ob_project/list.html";
										layer.close(index);
									} else {
									  var tempContext="";
									    if(name=="pName"){
									    tempContext=getSelectName(context);
									    context="成交供应商数量不得超过该产品注册的供应商数量的1/4";
									     $("#buttonErr").html(context);
									    }else if(name=="catalog"){
									      tempContext=getSelectName(context);
									      context="产品:"+tempContext+"不属于同一个目录";
									      $("#buttonErr").html(context);
									    }else{
										$("#" + name).html(context);
									    }
										layer.close(index);
										show(context);
									}
								} else {
									show("错误！");
								}
							});
    }
	function show(content) {
		layer.alert(content, {
			offset : [ '30%', '40%' ]
		});
	}
	//动态改变 比例
	function tradedCount(){
	var count=$("#tradedSupplierCount").val();
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
	// 动态获取供应商 数量
	function gysCount(){
	  var productid=[];
	    //获取选中全部的产品id
	   $('*[name="productName"]').each(function(){
			if($(this).val()){
		      productid.push($(this).val());
			  }
		  });
		  
		  if(productid.length>0){
		     var temp="";
            for(var i=0;i<productid.length;i++) { 
             temp=temp+productid[i]+",";
           }
	    if(productid){
           $.ajax({
				url: "${pageContext.request.contextPath }/ob_project/unionSupplier.do",
				type: "POST",
				data: {productid:temp},
				success: function(data) {
				if(data){
				 if(data){
   		 	    suppCount=data.sum;
				 if(productid.length==suppCount){
   		 	    $("#gys_count").text(data.count);
				 }else{
				 $("#gys_count").text(0);
				 }
   		 	     }else{
   		 	      $("#gys_count").text(0);
   		 	     }
				}
		      }
           });
		 }
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
  <div class="mt10">
	   <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div>
  <form id="myForm" action="" method="post" class="mb0">
  <input type="hidden" id="status" name="status">
  <input type="hidden" id="attachmentId" name="attachmentId" value="${fileid}" >
  <input type="hidden" id="id" name="id" value="${list.id}">
  <input type="hidden" id="ruleId" name="ruleId" value="${ruleId}" >
  <!-- <input type="hidden" id="supplieId" name="supplieId" > -->
  <input type="hidden" id="suppliePrimaryId" name="suppliePrimaryId" >
     <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
     <ul class="ul_list">
       <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价项目编号(保存后生成)</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="number"  value="${list.projectNumber}" name="number" type="text" readonly="readonly"  maxlength="100">
        <span class="add-on">i</span>
        <span class="input-tip">保存后自动生成</span>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价名称</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="name"  value="${list.name}" name="name" type="text"  maxlength="100">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="nameErr">${nameErr}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>交货时间</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" name="deliveryDeadline" id="deliveryDeadline" maxlength="19" value="<fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd HH:ss:mm"/>"  readonly="readonly"
         onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  type="text">
        <span class="add-on">i</span>
         <span class="input-tip">不能为空</span>
        <div class="cue" id="deliveryDeadlineErr">${deliveryDeadlineErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>交货地点</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="deliveryAddress" value="${list.deliveryAddress }" maxlength="150" name="deliveryAddress" type="text" >
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="deliveryAddressErr">${deliveryAddressErr}</div>
       </div>
	 </li> 
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>成交供应商数</span>
	   <select class="input_group" id="tradedSupplierCount" name="tradedSupplierCount" onchange="tradedCount()" >
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
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>需求单位</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<select id="demandUnit" name="demandUnit" onchange="changDemandUnit()" >
			  <option value="">--请选择--</option>
			</select></div>
        <div class="cue" id="demandUnitErr">${demandUnitErr}</div>
	 </li> 
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>联系人</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="contactName" name="contactName" readonly="readonly" value="${list.contactName }" maxlength="20" type="text">
        <span class="add-on">i</span>
           <span class="input-tip">不能为空</span>
        <div class="cue" id="contactNameErr">${contactNameErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>联系电话</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="contactTel" name="contactTel" value="${list.contactTel }" readonly="readonly" maxlength="20" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="contactTelErr">${contactTelErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">成交供应比例</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="tradedSupplier" value="" name=""  readonly="readonly" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">自动获取</span>
       </div>
	 </li>
	  
	<li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>采购机构</span>
			<select id="orgId" name="orgId" onchange="changSelect()" >
			  <option value="">--请选择--</option>
			</select>
			 <div class="cue" id="orgIdErr">${orgIdErr}</div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>采购联系电话</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="orgContactTel" name="orgContactTel" value="${list.orgContactTel }" readonly="readonly" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空,只可以是数字</span>
        <div class="cue" id="orgContactTelErr">${orgContactTelErr}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>采购联系人</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="orgContactName" name="orgContactName" value="${list.orgContactName }"  readonly="readonly" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="orgContactNameErr">${orgContactNameErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>运杂费(元)</span>
			<select id="transportFees" name="transportFees"  >
			</select>
        <div class="cue" id="transportFeesErr">${transportFeesErr}</div>
	 </li> 
	 
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价文件</span>
	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
        <u:upload id="project" buttonName="上传附件"  businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" multiple="true" auto="true" />
                <u:show showId="project" groups="b,c,d"  businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" />
       <div class="cue" id="fileUploadErr">${fileUploadErr}</div>
       </div>
	 </li> 

	  <li class="col-md-12 col-sm-12 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价内容</span>
	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
       <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:100px"  name="content" title="不超过1000个字" maxlength="1000">${list.content}</textarea>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="contentErr">${contentErr}</div>
       </div>
	 </li>
	 </ul>
	<h2 class="count_flow"><i>2</i>产品信息</h2>
	 <div class="ul_list">
  		<div class="col-md-12 col-sm-12 col-xs-12 p0 mt10 mb10">
			<input type="button" class="btn btn-windows add" onclick="addTr('productId','','','','',0)" value="添加">
			<input type="button"  class="btn btn-windows delete" value="删除" onclick="del()">
			<input type="button"  class="btn btn-windows output" value="下载EXCEL模板" onclick="down()">
			<input type="button"  class="btn btn-windows input" value="导入EXCEL"  onclick="uploadExcl()">
			<span><font id="buttonErr" class="red star_red"></font></span>
		</div>   
    	  <table class="table table-bordered left_table" id ="table2">
			<tr>
		  		<th class="w50 info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  		<th class="info" width="30%"><span class="red star_red">*</span>定型产品名称</th>
		  		<th class="info">限价（元）</th>
		  		<th class="info"><span class="red star_red">*</span>采购数量</th>
		  		<th class="info" width="30%">备注</th>
			</tr>
		  </table>
		</div>
   <h2 class="tc">温馨提示：能够提供当前产品的供应商数量为<span id="gys_count" >0</span>家</h2>
  </form>
  <div class="col-md-12 clear tc mt10">
	<button class="btn btn-windows save mb20" type="submit" onclick="submitProject(0)">
	<c:if test="${list.status!=null}">
	编辑
	</c:if>
	<c:if test="${list.status==null}">
	暂存
	</c:if></button>
	<button class="btn btn-windows apply mb20" type="submit" onclick="submitProject(1)">发布</button>
	<button class="btn btn-windows back mb20" type="button" onclick="history.go(-1)">返回</button>
   </div>
  
  <div  class=" clear margin-top-30" id="file_div"  style="display:none;" >
   	  <div class="col-md-12 col-sm-12 col-xs-12">
 		   <input type="file" id="fileName" class="input_group" name="file" >
 		</div>
 		<div class="col-md-12 col-sm-12 col-xs-12 mt20 tc">
    	    <input type="button" class="btn input" onclick="fileUpload()" value="导入" />
    	</div>
    </div>
</div>
</body>
</html>