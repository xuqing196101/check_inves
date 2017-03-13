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
	  var number=1;
	
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
		       changGYSCount();
			});
		}else{
			layer.alert("请选择",{offset: '222px', shade:0.01});
		}
    }
    //下载模板
    function down(){
     window.location.href ="${pageContext.request.contextPath}/ob_project/download.html";
    }
	//选择采购机构
	function getMechanism(){
	       $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/ob_project/mechanism.html",
				dataType: "json", //返回格式为json
                data:{"currFlowDefineId":nextFlowDefineId ,"currUpdateUserId":nextUpdateUserId, "projectId":projectId},
                success: function(data) {
                    if(data.success){
                    	layer.msg(data.flowDefineName+ "经办人设置成功",{offset: '100px'});
                    }
                },
                error: function(data){
                    layer.msg("请稍后再试",{offset: '100px'});
                }
            });
	}
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
						$("#orgId").append("<option  value=" + user.id + ">" + user.name + "</option>");
					});
				} 
			 $("#orgId").select2();
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
				productList=data;
				} 
			}
		 });
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
	
	//根据定型产品更新 
	function changSelectCount(number){
	 if(productList){
	 	var temp=0;
	 	var value=$("select[id=\"productName_"+number+"\"]").val();
	  	$.each(productList, function(i, user) {
	   	if(user.id==value){
	  	   temp=user.obSupplierList.length;
	    	$("input[name=\"count_"+number+"\"]").val(temp);
	    	changGYSCount();
	       }
	 	});
	  }else{
	   $("#gys_count").text(0);
	  }
	}
	//改变供应商数量
	function changGYSCount(){
	       var ds=[];
			//获取选中全部的产品id
			$('*[name="productName"]').each(function(){
			  if($(this).val()){
		      ds.push($(this).val());
			  }
		  });
		  var list=[];
		  if(ds.length>1){
		  var temp =null;
		  //遍历 选中产品id
		  for(var i=0;i<ds.length;i++) { 
		    temp=ds[i];
		    //便利选中产品 获取选中产品集合
		   	$.each(productList, function(i, user) {
		   	   if(temp==user.id){
		   	     list.push(user);
		   	   }
		   });
		   }
		   var count=0;
		   //便利选中的集合 是否有共同的供应商
		   for(var i=0;i<list.length;i++){
		      var  templist=list[i];
		      for(var j=1;j<list.length;j++){
		        var within=list[j];
		        if(templist.obSupplierList.id!=within.obSupplierList.id){
		         count++;
		         break;
		        };
		      };
		   };
		   $("#gys_count").text((list.length)-parseInt(count));
		  }else{
		     $("#gys_count").text(1);
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
	function loads(number){
	$.each(productList, function(i, user) {
		    $("select[id=\"productName_"+number+"\"]").append("<option  value=" + user.id + ">" + user.name+ "</option>");
	     });
	     $("select[id=\"productName_"+number+"\"]").select2();
	  } 
	  
	 function addTr(productId,productName,productMoney,producCount,productRemark,conut){
	      ++number;
		   $("#table2").append("<tr><td class=\"tc w30\"><input onclick=\"check()\" type=\"checkbox\" name=\"productId\" id=\"productId\" value=\""+productId+"\" /></td>"+
		  "<td class=\"p0\"><select id=\"productName_"+number+"\"  name=\"productName\" onchange=\"changSelectCount("+number+")\" ><option value=\"\"></option></select>"+
		  "<input id=\"count\" name=\"count_"+number+"\" value=\""+conut+"\" type=\"hidden\" >"+
		  "</td>"+
		  "<td class=\"p0\"><input id=\"productMoney\" maxlength=\"20\" onkeyup=\"this.value=this.value.replace(/\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\D/g,'')\" name=\"productMoney\" value=\""+productMoney+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		  "<td class=\"p0\"><input id=\"productCount\" maxlength=\"4\" onkeyup=\"this.value=this.value.replace(/\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\D/g,'')\" name=\"productCount\" value=\""+producCount+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		  "<td class=\"p0\"><input id=\"productRemark\" maxlength=\"2000\" name=\"productRemark\" value=\""+productRemark+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		"</tr>").clone(true);   
			  loads(number);
			 $("select[name=\"productName_"+number+"\"]").select2("val",productId);
			 /*  */
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
									addTr(value.id,value.code,value.standardModel,value.isDeleted,value.remark,value.name);
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
		   $("#deliveryDeadlineErr").html("交货截至日期不能为空");
		     show("交货截至日期不能为空");
		   return;
		  }
		   if(!$("#deliveryAddress").val().trim()){
		   $("#deliveryAddressErr").html("交货地点不能为空");
		     show("交货地点不能为空");
		   return;
		  }
		  var supplierCount=!$("#tradedSupplierCount").val().trim();
		    if(supplierCount){
		   $("#tradedSupplierCountErr").html("成交供应商数量不能为空");
		     show("成交供应商数量不能为空");
		   return;
		  }
		  if(parseInt(supplierCount)>parseInt(4)){
		  $("#tradedSupplierCountErr").html("成交供应商数量不能大于4");
		    show("成交供应商数量不能大于4");
		   return;
		  }
		   if(parseInt(supplierCount)<parseInt(1)){
		  $("#tradedSupplierCountErr").html("成交供应商数量不能小于1");
		  show("成交供应商数量不能小于1");
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
		    if(!$("#startTime").val().trim()){
		   $("#startTimeErr").html("竞价开始时间不能为空");
		     show("竞价开始时间不能为空");
		   return;
		     } 
		   if(!$("#endTime").val().trim()){
		   $("#endTimeErr").html("竞价结束时间不能为空");
		     show("竞价结束时间不能为空");
		   return;
		  }
		   if(!$("#content").val().trim()){
		   $("#contentErr").html("竞价内容不能为空");
		   show("竞价内容不能为空");
		   return;
		  }  
		  
		   if($('[name="productName"]').length == 0){
		   $("#buttonErr").html("竞价产品不能为空");
		   show("竞价产品不能为空");
		   return;
		  }else{ 
		   var temp=0;
		  $('*[name="productName"]').each(function(){
		    if(!$(this).val().trim()){
		      $("#buttonErr").html("竞价产品名称不能为空");
		      show("竞价产品名称不能为空");
		      return;
		    }
		  });
		
		   $('*[id="productMoney"]').each(function(){
		    if(!$(this).val().trim()){
		      $("#buttonErr").html("竞价产品限价不能为空");
		       show("竞价产品限价信息不能为空");
		      return;
		    }else{
		      temp=1;
		    }
		  });
		     $('*[id="productCount"]').each(function(){
		    if(!$(this).val().trim()){
		      $("#buttonErr").html("竞价产品数量不能为空");
		      show("竞价产品数量不能为空");
		      return;
		      }else{
		      temp=1;
		    }
		  });
		     $('*[id="productRemark"]').each(function(){
		    if(!$(this).val().trim()){
		      $("#buttonErr").html("竞价产品备注不能为空");
		      show("竞价产品备注不能为空");
		      return;
		      }else{
		      temp=1;
		    }
		  });
		    if(temp==0){return};
		  }  
    			if('${rule}' ){
		    var index = layer.load(0,{
    				  shade: [0.1,'#fff'],
    				  offset:['45%','53%']
    			}); 
    			$("#status").val(status);
    			$("#ruleId").val('${rule.id}');
	       $.post("${pageContext.request.contextPath}/ob_project/addProject.html", $("#myForm").serialize(), function(data) {
	                            if (data) {
									var json = JSON.parse(data);
									var name = json.attributeName;
									var context = json.show;
									if (name == "success") {
										window.location.href = "${pageContext.request.contextPath}/ob_project/list.html";
										layer.close(index);
									} else {
										layer.close(index);
										$("#" + name).html(context);
										show(context);
									}
								} else {
									show("错误！");
								}
							});
		} else {
			show("竞价规则不能没有默认");
		}
	}

	function show(content) {
		layer.alert(content, {
			offset : [ '30%', '40%' ]
		});
	}
	//根据规则 生成时间
	function getValue() {
		var rule = '${rule.quoteTime}';
		var stattime = $("#startTime").val().trim();
		if (stattime) {
			var date = new Date(stattime);//
			//分
			var monute = date.setMinutes(date.getMinutes() + 30);
			var d = new Date(monute).Format("yyyy-MM-dd hh:mm:ss");
			$("#endTime").val(d);
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
   <div class="wrapper mt10">
  <form id="myForm" action="${pageContext.request.contextPath}/ob_project/addProject.html" method="post" class="mb0">
  <div class="container container_box">
     <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
     <div class="ul_list">
  		<table class="table table-bordered left_table">
			<input id="fileid" name="fileid" value="${fileid}" type="hidden">
			<input id="status" name="status" type="hidden">
			<input id="ruleId" name="ruleId" type="hidden">
		<tbody>
		  <tr>
			<td class="bggrey tr"><span><font id="nameErr" class="red star_red"></font></span><span class="red star_red">*</span>竞价标题：</td>
			<td class="p0"><input id="name" name="name" value="" type="text" maxlength="180" class="w230 mb0">
			</td>
			<td class="bggrey tr"><span><font id="deliveryDeadlineErr" class="red star_red"></font></span><span class="red star_red">*</span>交货截止时间：</td>
			<td class="p0"><input value=""
			 name="deliveryDeadline" id="deliveryDeadline" type="text"  readonly="readonly"   maxlength="7" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  class="Wdate" /></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span><font id="deliveryAddressErr" class="red star_red"></font></span><span class="red star_red">*</span>交货地点：</td>
			<td class="p0"><input id="deliveryAddress" name="deliveryAddress" value="" type="text" class="w230 mb0"></td>
			<td class="bggrey tr"><span><font id="tradedSupplierCountErr" class="red star_red"></font></span><span class="red star_red">*</span>成交供应商数：</td>
			<td class="p0"><input id="tradedSupplierCount" name="tradedSupplierCount" value="" maxlength="1" type="text" class="w230 mb0" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span><font id="transportFeesErr" class="red star_red"></font></span><span class="red star_red">*</span>运杂费：</td>
			<td class="p0"><input id="transportFees" name="transportFees" value="" type="text"  maxlength="20" class="w230 mb0" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
			<td class="bggrey tr"><span><font id="demandUnitErr" class="red star_red"></font></span><span class="red star_red">*</span>需求单位：</td>
			<td class="p0"><input id="demandUnit" name="demandUnit" value="" type="text"  maxlength="50" class="w230 mb0"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span><font id="contactNameErr" class="red star_red"></font></span><span class="red star_red">*</span>联系人：</td>
			<td class="p0"><input id="contactName" name="contactName" value=""  type="text" maxlength="20"  class="w230 mb0"></td>
			<td class="bggrey tr"><span><font id="contactTelErr" class="red star_red"></font></span><span class="red star_red" >*</span>联系电话：</td>
			<td class="p0"><input id="contactTel" name="contactTel" value="" type="text" class="w230 mb0" maxlength="11"   onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span><font id="orgIdErr" class="red star_red"></font></span><span class="red star_red">*</span>采购机构：</td>
			<td class="p0"><div class="w200">
			<select id="orgId" name="orgId" onchange="changSelect()" >
			  <option value=""></option>
			</select></div></td>
			<td class="bggrey tr"><span><font id="orgContactTelErr" class="red star_red"></font></span><span class="red star_red">*</span>采购联系电话：</td>
			<td class="p0"><input id="orgContactTel" name="orgContactTel" readonly="readonly" type="text" class="w230 mb0">
			</td>
		  </tr>
		   <tr>
			<td class="bggrey tr"><span><font id="orgContactNameErr" class="red star_red"></font></span><span class="red star_red">*</span>采购联系人：</td>
			<td class="p0" colspan="3" ><input id="orgContactName" readonly="readonly" name="orgContactName"  type="text" class="w230 mb0">
			</td>
		  </tr>
		  <tr>
			<td class="bggrey tr"><span><font id="startTimeErr" class="red star_red"></font></span><span class="red star_red">*</span>竞价开始时间：</td>
			<td class="p0"><input value="" name="startTime" id="startTime" type="text" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
			onfocus="getValue()" class="Wdate" /></td>
		  <td class="bggrey tr"><span><font id="endTimeErr" class="red star_red"></font></span><span class="red star_red">*</span>竞价结束时间：</td>
			<td class="p0"><input id="endTime" name="endTime" value="" class="Wdate" type="text" readonly="readonly" ></td>
		  </tr>
		 
		  <tr>
			<td class="bggrey tr"><span><font id="contentErr" class="red star_red"></font></span><span class="red star_red">*</span>竞价内容：</td>
			<td colspan="3" class="p0">
  					<textarea class="col-md-12 col-sm-12 col-xs-12" id="content"  maxlength="3000" name="content" style="height:130px"></textarea>
			 </td>
		  </tr>
		  <tr>
			<td class="bggrey tr">竞价文件：</td>
			<td colspan="3" class="p0">
			<div>
                <u:upload id="project" buttonName="上传文档"  businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" multiple="true" auto="true" />
                <u:show showId="project" groups="b,c,d"  businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" />
              </div>
			
			</td>
		  </tr>
		 </tbody>
	 </table>
	 </div>
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
		  		<th class="info"><span class="red star_red">*</span>限价（元）</th>
		  		<th class="info"><span class="red star_red">*</span>采购数量</th>
		  		<th class="info" width="30%"><span class="red star_red">*</span>备注</th>
			</tr>
		  </table>
		</div>
   <h2 class="tc">温馨提示：能够提供当前产品的供应商数量为<span id="gys_count" >0</span>家</h2>
  </form>
  <div class="col-md-12 clear tc mt10">
	<button class="btn btn-windows save mb20" type="submit" onclick="submitProject(0)">暂存</button>
	<button class="btn btn-windows apply mb20" type="submit" onclick="submitProject(1)">发布</button>
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
</div>
</body>
</html>