<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
		<link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
		<link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
	    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<title>发布竞价信息页面</title>
<script type="text/javascript">
	// 获取乙方包干使用运杂费
	  var number=10000001;
	  //定义选中第一产品
     var productInfo;
	  var productIds;
	  var delProdectList = new Array();
	  //选择数量
	  var suppCount=0;
	  
	  var numberArray = [];
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
				var hehe = [];
	        	 $('*[name="productName"]').each(function(){
	     			if($(this).val()){
	     		      hehe.push($(this).val());
	     			  }
	     		  });
	        	productIds = hehe.toString();
		       gysCount(null);
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
	
	function changeTransportFees(obj){
		var transportFeesStr = $("#transportFees option:selected").val();
		if(transportFeesStr == 'c4684513be9c65c5e8fb923f9ae14e88'){
			$("#transportFeesPriceLi").css("display","block");
			$("#transportFeesPriceErr").html("");
			$("#transportFeesPrice").val("");
		}else{
			$("#transportFeesPriceLi").css("display","none");
			$("#transportFeesPriceErr").html("");
			$("#transportFeesPrice").val("");
		}
	}
	
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
			  var type='${type}';
			  if(type=='1'){
			   $("#demandUnit").select2('val','${list.demandUnit}');
			  }else if(type=='2'){
			     $("#demandUnit").select2('val','${orgId}');
			     changDemandUnit();
			  }
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
         if('${listinfo}' != null){
        	 <c:forEach items='${listinfo}' var = 'values' >  
        	 addTr('${values.productId}','${values.obProduct.name}','${values.limitedPrice}','${values.purchaseCount}','${values.remark}');
		    $("#productName_"+number).combobox('select','${values.productId}'); 
           
        	 </c:forEach>
		      }
			}
			}
		 });
		  loadProduct(number);
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
			 $("#transportFees").select2();
			 $("#transportFees").select2('val','${list.transportFees}');
			 $("#transportFees").select2({
			        minimumResultsForSearch: -1
			 });
			}
		});
		/* var fees='${list.transportFees}';
		if(fees){ */
		$("#transportFees").select2('val','${list.transportFees}');
		/* }else{
		     $("#transportFees").select2('val',id );
		} */
		 
		 layer.close(index);
		  $("#radio [name='isEmergency']").each(function(){
		  if($(this).val()=='${list.isEmergency}'){
		  $(this).attr("checked",true);
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
     //定义存储产品id
     var productTemp=[];
	//根据定型产品更新 
	function changSelectCount(number){
		alert("ss");
	 if(productList){
	   productInfo=$("[name='productName']").val();
	 	gysCount(null);
	  $("#orgId").select2('val',productList[0].procurementId);
	  changSelect();
	  }else{
	  productInfo=null;
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
	//定义临时产品目录
	var productM;
	var width;
	//初始化产品下拉框
	function loads(number,id){
	 if(productInfo){
	 if(!productM){
	 $.each(productList, function(i, user) {
	     if(productInfo==user.id){
	       productM=user.smallPointsId;
	      }
	    });
	   }
	  }
	    
	$.each(productList, function(i, user) {
	    var temp=$.inArray(user.id,productTemp);
	       if(temp==-1){
	        if(productM){
	         if(user.smallPointsId==productM){
		    $("select[id=\"productName_"+number+"\"]").append("<option  value=" + user.id + " >" + user.name+ "</option>");
	         }
	        }
	        if(!productM){
	         $("select[id=\"productName_"+number+"\"]").append("<option  value=" + user.id + " >" + user.name+ "</option>");
	        }
	        }
	     });
	        $("select[id=\"productName_"+number+"\"]").select2(); 
	        if(!width){
	        width=$('#tdID').width()+20;
	        }
	      $("select[id=\"productName_"+number+"\"]").select2({ width: width });
	     if(id){
		 $("#productName_"+number+"").select2('val',id); 
	     changSelectCount(number);
	    }
	  } 
	  
	 function addTr(productId,productName,productMoney,producCount,productRemark){
	      ++number;
	      numberArray.push(number);
	      if(!productMoney){
	      productMoney='';
	      }
	      if(!productRemark){
	      productRemark='';
	      }
		   $("#table2").append("<tr><td class=\"tc w30\"><input onclick=\"check()\" type=\"checkbox\" name=\"productId\" id=\"productId\" value=\""+productId+"\" /></td>"+
		  "<td class=\"p0\" id=\"tdID\" ><div id=\"selectDiv"+number+"\"  onmouseover='showPrompt(\"selectDiv"+number+"\",\"productName_"+number+"\")'  onmouseout=\"closePrompt()\" onblur=\"closePrompt()\" name=\"selectDiv\"><input class=\"easyui-combobox\" id=\"productName_"+number+"\" name=\"productName\" data-options=\"valueField:'id',textField:'name',panelHeight:'auto',panelMaxHeight:200,panelMinHeight:100\"  style=\"width: 100%;height: 30px;\"/>"+
		  "</div></td>"+
		  "<td class=\"p0\" id=\"t"+number+"\" width=\"10%\"><input id=\"productMoney\" maxlength=\"20\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" name=\"productMoney\" value=\""+productMoney+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		  "<td class=\"p0\"  width=\"20%\"><input id=\"productCount\" maxlength=\"38\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\"  onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" name=\"productCount\" value=\""+producCount+"\" type=\"text\" class=\"w230 mb0\"></td>"+
		  "<td class=\"p0\"  width=\"40%\"><input id=\"productRemark\" maxlength=\"1000\" name=\"productRemark\" value=\""+productRemark+"\" title=\""+productRemark+"\" type=\"text\" class=\"w230 mb0\">"+
		  "  </td>"+
		"</tr>").clone(true);   
		//加载数据
	//	loads(number,productId);
		/* var hehe = [];
		        	 $('*[name="productName"]').each(function(){
		     			if($(this).val()){
		     		      hehe.push($(this).val());
		     			  }
		     		  });
		        	productIds = hehe.toString(); */
		loadProduct(number);
		
	}
	 function loadProduct(number){
		 $('#productName_'+number).combobox({  
		        prompt:'',  
		        required:false,  
		       	url: "${pageContext.request.contextPath }/ob_project/product.html?ids="+productIds,
		        //data:productList,
		        hasDownArrow:true,  
		        filter: function(L, row){  
		            var opts = $(this).combobox('options');  
		            return row[opts.textField].indexOf(L) == 0;  
		        },
		        onSelect: function (obj) {
		        	//var list = new Array();
		        	 if(obj.id != null){
		        		$.each(productList, function(index, value) {
		        			if(obj.id == value.id){
		        				$("#orgId").select2("val",value.procurementId);
		        				changSelect();
		        			}
		        			/* if(obj.id != value.id && obj.smallPointsId == value.smallPointsId){
		        				list.push(value);
		        			}
		        			productList = list;  */
						}); 
		        	}
		        	 
		        	// loadProduct(++number);
		        	 
		        },
		        onChange:function(obj){
		        	var hehe = [];
		        	 $('*[name="productName"]').each(function(){
		     			if($(this).val()){
		     		      hehe.push($(this).val());
		     			  }
		     		  });
		        	productIds = hehe.toString();
		        	loadProduct(++number);
		        	/* $.each(numberArray, function(index, hh) {
		        		//alert(hh);
		        		if(hh != number){
		        		var selval = $('#productName_'+number+'option:selected').text();
		        		alert(selval)
		        		loadProduct(hh);
		        		$("#productName_"+number).combobox('select',selval); 
		        		} 
		        	}); */
		        	/* for(var i = 0;i<numberArray.length;i++){
		        		loadProduct(numberArray[i]);
		        	} */
		        	gysCount(obj.id);
		        }
		    });  
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
		  $("#transportFeesPriceErr").html("");
		  $("#isEmergencyErr").html("");
		   if(status==1){
		   var name=$("#name").val().trim();
		   if(!name){
		   $("#nameErr").html("竞价项目名称不能为空");
		     show("竞价项目名称不能为空");
		   return;
		  }
		   var supplierCount=$("#tradedSupplierCount").val().trim();
		   if(!supplierCount){
		   $("#tradedSupplierCountErr").html("成交供应商数量不能为空");
		     show("成交供应商数量不能为空");
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
		   if(!$("#demandUnit").val().trim()){
			   $("#demandUnitErr").html("需求单位不能为空");
			   show("需求单位不能为空");
			   return;
		   }
		   if(!$("#contactName").val().trim()){
			   $("#contactNameErr").html("联系人不能为空");
			   show("联系人不能为空");
			   return;
			}
		   if(!$("#contactTel").val().trim()){
			   $("#contactTelErr").html("联系人电话不能为空");
			   show("联系人电话不能为空");
			   return;
			}
		   
		  if(!$("#transportFees").val().trim()){
		   $("#transportFeesErr").html("运杂费支付方式不能为空");
		    show("运杂费支付方式不能为空");
		   return;
		  }
		   
		   if(!$("#isEmergency:checked").val()){
		   $("#isEmergencyErr").html("是否为应急采购项目选项不能为空");
		     show("是否为应急采购项目选项不能为空");
		  return;
		  }
		  
		  
		  // 验证运杂费输入
		  var transportFeesPriceLiStyle = $("#transportFeesPriceLi").css("display");
		  if(transportFeesPriceLiStyle == 'block'){
			  // 获取输入的价格
			  var transportFeesPrice = $("#transportFeesPrice").val().trim();
			  if(!transportFeesPrice){
			   $("#transportFeesPriceErr").html("运杂费金额不能为空");
			    show("运杂费金额不能为空");
			   	return;
			  }
			  if(transportFeesPrice.substr(0,1) == '0'){
				  $("#transportFeesPriceErr").html("运杂费不能以0开头且最低输入为1元");
				  show("运杂费不能以0开头且最低输入为1元");
				  return;
			  }
			  var priceArr = transportFeesPrice.split(".");
			  if((priceArr.length - 1) > 1){
				  $("#transportFeesPriceErr").html("运杂费输入格式不正确");
				  show("运杂费输入格式不正确");
				  return;
			  }
			  if(priceArr[1] == ''){
				  $("#transportFeesPriceErr").html("运杂费输入格式不正确");
				  show("运杂费输入格式不正确");
				  return;
			  }
			  if( ! /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/.test(transportFeesPrice)){
				  $("#transportFeesPriceErr").html("运杂费输入格式不正确(保留两位小数)");
				  show("运杂费输入格式不正确(保留两位小数)");
				  return;
			  }
			  
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
		 if(!supplierCount){
		 supplierCount=0;
		 }
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
	// 动态获取供应商 数量
	function gysCount(pid){
	 $("#buttonErr").html("");
	  var productid=[];
	  //productTemp=[];
	    //获取选中全部的产品id
	   $('*[name="productName"]').each(function(){
			if($(this).val()){
			var id=$(this).val();
		      productid.push(id);
		      //productTemp.push(id);
			  }
		  });
	   /*  if(pid != null){
	   		productid.push(pid);
	    } */
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
            $.ajax({
				url: "${pageContext.request.contextPath }/ob_project/checkCatalog.do",
				type: "POST",
				contentType:"application/json",
				data: JSON.stringify(productid),
				success: function(data) {
				if(data != null){
				var json = JSON.parse(data);
				var name = json.attributeName;
				var context = json.show;
				tempContext=getSelectName(context);
				 context="产品:"+tempContext+"不属于同一个目录";
				  $("#buttonErr").html(context);
				}
		      }
           });
		 }
     }else{
     productM=null;
     productInfo=null;
     productTemp=[];
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
  <input type="hidden" id="attachmentId" name="attachmentId" value="${fileid}" >
  <input type="hidden" id="id" name="id" value="${list.id}">
  <input type="hidden" id="ruleId" name="ruleId" value="${ruleId}" >
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
        <input class="input_group" id="name"  value="${list.name}" name="name" type="text"  maxlength="100">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="nameErr">${nameErr}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>成交供应商数</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
	   <select class="input_group" id="tradedSupplierCount" name="tradedSupplierCount" onchange="tradedCount()" >
	   <option value="">--请选择--</option>
	   <option value="1">1</option>
	   <option value="2" >2</option>
	   <option value="3" >3</option>
	   <option value="4" >4</option>
	   <option value="5" >5</option>
	   <option value="6">6</option>
	   </select>
        <div class="cue" id="tradedSupplierCountErr">${tradedSupplierCountErr}</div></div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">供应商成交比例</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="tradedSupplier" value="" name=""  readonly="readonly" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">自动获取</span>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>交货时间</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" name="deliveryDeadline" id="deliveryDeadline" maxlength="19" value="<fmt:formatDate value="${list.startTime}" pattern="yyyy-MM-dd HH:ss:mm"/>"  readonly="readonly"
         onclick="WdatePicker({minDate:'%y-%M-{%d}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  type="text">
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
        <input class="input_group" id="deliveryAddress" value="${list.deliveryAddress }" maxlength="150" name="deliveryAddress" type="text" >
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="deliveryAddressErr">${deliveryAddressErr}</div>
       </div>
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
        <input class="input_group" id="contactName" name="contactName"  value="${list.contactName }" maxlength="20" type="text">
        <span class="add-on">i</span>
           <span class="input-tip">不能为空</span>
        <div class="cue" id="contactNameErr">${contactNameErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>联系电话</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="input_group" id="contactTel" name="contactTel" value="${list.contactTel }"  maxlength="20" type="text">
        <span class="add-on">i</span>
        <span class="input-tip">不能为空</span>
        <div class="cue" id="contactTelErr">${contactTelErr}</div>
       </div>
	 </li>
	 <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>运杂费支付方式</span>
			<div class ="select_common col-md-12 col-sm-12 col-xs-12 p0">
			<select id="transportFees" name="transportFees" onchange="changeTransportFees(this)" >
				<option value="">--请选择--</option>
			</select>
	 </div>
        <div class="cue mt20" id="transportFeesErr">${transportFeesErr}</div>
	 </li>
	
	 <li id="transportFeesPriceLi" class="col-md-3 col-sm-6 col-xs-12" style="display:none;">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>运杂费金额(元)</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group" id="transportFeesPrice" value="${ list.transportFeesPrice }" name="transportFeesPrice" onkeyup="this.value=this.value.replace(/[^\d.]/g, '')"  onafterpaste="this.value=this.value.replace(/[^\d.]/g, '')" type="text">
	        <span class="add-on">i</span>
	        <span class="input-tip">不能为空,最低输入一元</span>
	        <div class="cue" id="transportFeesPriceErr">${transportFeesPriceErr}</div>
        </div>
	 </li>
	<li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>采购机构</span>
			<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<select id="orgId" name="orgId" onchange="changSelect()" >
			  <option value="">--请选择--</option>
			</select>
			 <div class="cue" id="orgIdErr">${orgIdErr}</div></div>
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
      <span class="red" style="font-size: 11px" >应急采购项目,只有1家供应商报价的,可以成交</span>
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12" title="应急采购项目，只有1家供应商报价的，可以成交"><span class="red">*</span>是否为应急采购项目</span>
	   <div class="col-md-12 col-sm-12 col-xs-12 p0">
	   <div class="select_check" id="radio">
	   <input type="radio" name="isEmergency" id ="isEmergency" value="-1" checked="checked">否
	   <input type="radio" name="isEmergency" id ="isEmergency" value="0">是
	 </div>
	   
       <div class="cue" id="isEmergencyErr">${isEmergencyErr}</div>
       </div>
	 </li>
	  <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>竞价文件</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
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
	 <h2 class="count_flow"><i>2</i>竞价规则信息</h2>
	 <%@ include file ="/WEB-INF/view/bss/ob/biddingRules/ruleCommon.jsp" %>
	 
	 
	<h2 class="count_flow"><i>3</i>产品信息</h2>
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
		  		<th class="w50 info" width="1%"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
		  		<th class="info" width="45%"><span class="red star_red">*</span>定型产品名称</th>
		  		<th class="info" width="10%">限价（元）</th>
		  		<th class="info" width="14%"><span class="red star_red">*</span>采购数量</th>
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
	<script type="text/javascript">
		$(function(){
			// 乙方包干使用价格显示
			var transportFees = '${list.transportFeesPrice}';
			if(transportFees){
				$("#transportFeesPriceLi").css("display","block");
			}
		});
	</script>
</html>