<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//表单标示
	String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>专家查看</title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/animate.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/ui-dialog.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/dialog-select.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/font-awesome.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/jquery.fileupload-ui.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/zTreeStyle.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/sky-forms.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/custom-sky-forms.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/jquery.fancybox.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/owl.carousel.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/owl.theme.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style-switcher.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shortcode_timeline2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/blocks.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/datepicker.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/select2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons-inversed.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/blog_magazine.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_log_reg_v1.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/masterslider.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/james.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">


<script type="text/javascript" src="<%=basePath%>public/ZHH/js/hm.js"></script><script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/back-to-top.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.query.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/dialog-plus-min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/smoothScroll.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.parallax.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/app.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/dota.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.dragsort-0.5.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/fancy-box.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/style-switcher.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/owl.carousel.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/owl-carousel.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/owl-recent-works.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.form.min.js"></script>

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/masking.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/datepicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/timepicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/dialog-select.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/locale.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/load-image.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/canvas-to-blob.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/tmpl.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fileupload-fp.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.fileupload-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery-fileupload.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/select2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/application.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.counterup.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/modernizr.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/touch.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/product-quantity.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/master-slider.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/shop.app.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/masterslider.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.easing.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/james.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ztree/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>public/ztree/css/zTreeStyle.css"> 
<script type="text/javascript">
var treeObj;
var datas;

var parentId ;
var addressId="${expert.address}"
//alert(addressId);
//地区回显和数据显示
$.ajax({
	url : "<%=basePath%>area/find_by_id.do",
	data:{"id":addressId},
	success:function(obj){
		//alert(JSON.stringify(obj));
		var data = eval('(' + obj+ ')');
		$.each(data,function(i,result){
			if(addressId == result.id){
				parentId = result.areaType;
			$("#haha").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
			}else{
				$("#haha").append("<option value='"+result.id+"'>"+result.name+"</option>");
			}
			
		});
		//alert(JSON.stringify(data));
		//alert(parentId);
		
	},
	error:function(obj){
		
	}
	
});

$(function(){
	$.ajax({
		url : "<%=basePath%>area/listByOne.do",
		success:function(obj){
			var data = eval('(' + obj + ')');
			$.each($.parseJSON(data),function(i,result){
				if(parentId == result.id){
					$("#hehe").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
				}else{
				$("#hehe").append("<option value='"+result.id+"'>"+result.name+"</option>");
				}
			});
			
			//alert(JSON.stringify(obj));
		},
		error:function(obj){
			
		}
		
	});
	
	
});	

function fun(){
	var parentId = $("#hehe").val();
	$.ajax({
		url : "<%=basePath%>area/find_area_by_parent_id.do",
		data:{"id":parentId},
		success:function(obj){
			$("#haha").empty();
			var data = eval('(' + obj + ')');
			$("#haha").append("<option value=''>-请选择-</option>");
			$.each(data,function(i,result){
				
				$("#haha").append("<option value='"+result.id+"'>"+result.name+"</option>");
			});
			
			//alert(JSON.stringify(obj));
		},
		error:function(obj){
			
		}
		
	});
}


   var setting={
			async:{
						//autoParam:["id"],
						enable:true,
						url:"<%=basePath%>category/createtree.do",
						autoParam:["id", "name=n", "level=lv"],  
			            otherParam:{"otherParam":"zTreeAsyncTest"},  
			            dataFilter: filter,  
						dataType:"json",
						type:"post",
					},
					callback:{
				    	onClick:zTreeOnClick,//点击节点触发的事件
				    	//onAsyncSuccess: zTreeOnAsyncSuccess
				    	beforeAsync: beforeAsync,  
		                onAsyncSuccess: onAsyncSuccess,
		                beforeCheck: zTreeBeforeCheck
				    }, 
					data:{
						keep:{
							parent:true,
						},					
						simpleData:{
							enable:true,
							idKey:"id",
							pIdKey:"pId",
							rootPId:0,
						}
				    },
				   check:{
						enable: true,
						chkStyle:"checkbox"
				   }
	  };
   var listId;
   $(function(){
	   var id="${expert.id}";
	   
		  $.ajax({
			  url:"<%=basePath%>expert/getCategoryByExpertId.do?expertId="+id,
			  success:function(result){
				  listId=result;
			  },
			  error:function(result){
				  alert("出错啦！");
			  }
		  }); 
	  var expertsTypeId = $("#expertsTypeId").val();
	 if(expertsTypeId==1 || expertsTypeId=="1"){
	 //treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
	treeObj = $.fn.zTree.init($("#ztree"), setting);  
     setTimeout(function(){  
         expandAll("ztree");  
     },500);//延迟加载  
		 $("#ztree").show();
	 }else{
		 treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
		 $("#ztree").hide();
	 }
}); 
   function zTreeBeforeCheck(treeId, treeNode) {
	    return false;
	};
   
   function filter(treeId, parentNode, childNodes) {  
       if (!childNodes) return null;  
       for (var i=0, l=childNodes.length; i<l; i++) {  
           childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');  
       }  
       return childNodes;  
   }  

   function beforeAsync() {  
       curAsyncCount++;  
   }  
     
   function onAsyncSuccess(event, treeId, treeNode, msg) {  
       curAsyncCount--;  
       if (curStatus == "expand") {  
           expandNodes(treeNode.children);  
       } else if (curStatus == "async") {  
           asyncNodes(treeNode.children);  
       }  

       if (curAsyncCount <= 0) {  
           curStatus = "";  
       }  
   }  

   var curStatus = "init", curAsyncCount = 0, goAsync = false;  
   function expandAll() {  
       if (!check()) {  
           return;  
       }  
       var zTree = $.fn.zTree.getZTreeObj("ztree");  
       expandNodes(zTree.getNodes());  
       if (!goAsync) {  
           curStatus = "";  
       }  
   }  
   function expandNodes(nodes) {  
       if (!nodes) return;  
       curStatus = "expand";  
       var zTree = $.fn.zTree.getZTreeObj("ztree");  
       for (var i=0, l=nodes.length; i<l; i++) {
    	   for(var a=0;a<listId.length;a++){
    		   if(listId[a].categoryId==nodes[i].id){
    			   zTree.checkNode(nodes[i], true, true); 
    		   }
    	   }
           zTree.expandNode(nodes[i], true, false, false);//展开节点就会调用后台查询子节点 
            if (nodes[i].isParent && nodes[i].zAsync) {  
               expandNodes(nodes[i].children);//递归  
           } else {  
               goAsync = true;  
           }  
       }  
   }  

   function check() {  
       if (curAsyncCount > 0) {  
           return false;  
       }  
       return true;  
   }  

function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
    var nodes = treeNode.children;
    for(var i=0;i<nodes.length;i++){
        treeObj.expandNode(nodes[i],true,false,true,true);
    }

} 	

function zTreeOnClick(event,treeId,treeNode){
	treeid=treeNode.id
}


//地区联动js
	function loadProvince(regionId){
		  $("#id_provSelect").html("");
		  $("#id_provSelect").append("<option value=''>请选择省份</option>");
		  var jsonStr = getAddress(regionId,0);
		  for(var k in jsonStr) {
			$("#id_provSelect").append("<option value='"+k+"'>"+jsonStr[k]+"</option>");
		  }
		  if(regionId.length!=6) {
			$("#id_citySelect").html("");
		    $("#id_citySelect").append("<option value=''>请选择城市</option>");
			$("#id_areaSelect").html("");
		    $("#id_areaSelect").append("<option value=''>请选择区域</option>");
		  } else {
			 $("#id_provSelect").val(regionId.substring(0,2)+"0000");
			 loadCity(regionId);
		  }
	}
	
	function loadCity(regionId){
	  $("#id_citySelect").html("");
	  $("#id_citySelect").append("<option value=''>请选择城市</option>");
	  if(regionId.length!=6) {
		$("#id_areaSelect").html("");
	    $("#id_areaSelect").append("<option value=''>请选择区域</option>");
	  } else {
		var jsonStr = getAddress(regionId,1);
	    for(var k in jsonStr) {
		  $("#id_citySelect").append("<option value='"+k+"'>"+jsonStr[k]+"</option>");
	    }
		if(regionId.substring(2,6)=="0000") {
		  $("#id_areaSelect").html("");
	      $("#id_areaSelect").append("<option value=''>请选择区域</option>");
		} else {
		   $("#id_citySelect").val(regionId.substring(0,4)+"00");
		   loadArea(regionId);
		}
	  }
	}
	
	function loadArea(regionId){
	  $("#id_areaSelect").html("");
	  $("#id_areaSelect").append("<option value=''>请选择区域</option>");
	  if(regionId.length==6) {
	    var jsonStr = getAddress(regionId,2);
	    for(var k in jsonStr) {
		  $("#id_areaSelect").append("<option value='"+k+"'>"+jsonStr[k]+"</option>");
	    }
		if(regionId.substring(4,6)!="00") {$("#id_areaSelect").val(regionId);}
	  }
	}
</script>
</head>
<body>
  <div class="wrapper">
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">评标专家信息查看</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <!-- 项目戳开始 -->
		<div class="container clear margin-top-30">
		<!--详情开始-->
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<ul class="nav nav-tabs bgdd">
							<li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">基本信息</a></li>
							<li class="">	   <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">专家类型</a></li>
							<!-- <li class="">	   <a aria-expanded="false" href="#tab-3" data-toggle="tab" class="fujian f18"></a></li> -->
						</ul>
<!-- 修改订列表开始-->
   <div class="container">
   	<div style="margin-left: 1000px;">
   		<img style="width: 80px; height: 100px;" alt="个人照片" src="ftp://${username }:${password }@${host }:${port }/expertFile/${filename }">
    </div>
   <form action=""  method="post" id="form1" enctype="multipart/form-data" class="registerform"> 
   		<%
			session.setAttribute("tokenSession", tokenValue);
		%>
   <input type="hidden"  name="token2" value="<%=tokenValue%>">
   <input type="hidden" name="id" value="${expert.id }">
   <input type="hidden" name="isPass" id="isPass"/>
  <div class="tab-content padding-top-20" style="height: 850px;">
	<div class="tab-pane fade active in height-450" id="tab-1">
	<div class=" f16 count_flow fl">
	<i>01</i>评标专家基本信息
   </div>
   <ul class="list-unstyled list-flow p0_20">
     <li class="col-md-6 p0">
	   <span class="">姓名：</span>
        <input class="span2"  id="relName" maxlength="10" value="${expert.relName }" disabled="disabled"  name="relName" type="text" onblur="validataForm(this,'nameFont');">
          
       <font id="nameFont"></font>
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">性别：</span>
	   <select class="span2" name="gender" disabled="disabled">
	   	<option <c:if test="${expert.gender eq '男' }">selected="selected"</c:if> value="男">男</option>
	   	<option <c:if test="${expert.gender eq '女' }">selected="selected"</c:if> value="女">女</option>
	   	<option value="其他">其他</option>
	   </select>
	   
	 </li>
     <li class="col-md-6  p0 ">
	   <span class="">出生日期：</span>
        <input class="span2 Wdate w220" value="<fmt:formatDate type='date' value='${expert.birthday }' dateStyle="default" pattern="yyyy-MM-dd"/>" disabled="disabled" onfocus="validataForm(this,'nameFont2');" onblur="validataForm(this,'nameFont2');" readonly="readonly" name="birthday" id="appendedInput" type="text" >
      <font id="nameFont2"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">政治面貌：</span>
	   <select class="span2" name="politicsStatus" disabled="disabled">
	   	<option <c:if test="${expert.politicsStatus eq '党员' }">selected="selected"</c:if> value="党员">党员</option>
	   	<option <c:if test="${expert.politicsStatus eq '团员' }">selected="selected"</c:if> value="团员">团员</option>
	   	<option <c:if test="${expert.politicsStatus eq '群众' }">selected="selected"</c:if> value="群众">群众</option>
	   	<option <c:if test="${expert.politicsStatus eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
	   </select>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">证件类型：</span>
	   <select class="span2" name="idType" disabled="disabled">
	   	<option <c:if test="${expert.idType eq '身份证' }">selected="selected"</c:if> value="身份证">身份证</option>
	   	<option <c:if test="${expert.idType eq '士兵证' }">selected="selected"</c:if> value="士兵证">士兵证</option>
	   	<option <c:if test="${expert.idType eq '驾驶证' }">selected="selected"</c:if> value="驾驶证">驾驶证</option>
	   	<option <c:if test="${expert.idType eq '工作证' }">selected="selected"</c:if> value="工作证">工作证</option>
	   	<option <c:if test="${expert.idType eq '护照' }">selected="selected"</c:if> value="护照">护照</option>
	   	<option <c:if test="${expert.idType eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
	   </select>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">证件号码：</span>
        <input class="span2" disabled="disabled" maxlength="30" onblur="validataForm(this,'nameFont3');" value="${expert.idNumber }" name="idNumber" id="appendedInput" type="text">
          
    <font id="nameFont3"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专家来源：</span>
	   <select class="span2" name="expertsFrom" disabled="disabled">
	   	<option <c:if test="${expert.expertsFrom eq '军队' }">selected="selected"</c:if> value="军队">军队</option>
	   	<option <c:if test="${expert.expertsFrom eq '地方' }">selected="selected"</c:if> value="地方">地方</option>
	   	<option <c:if test="${expert.expertsFrom eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
	   </select>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">民族：</span>
        <input class="span2" disabled="disabled" maxlength="10" onblur="validataForm(this,'nameFont4');"  value="${expert.nation }" name="nation" id="appendedInput" type="text">
          
       <font id="nameFont4"></font>
	 </li> 
      <li class=""><span>所在地区：</span>
      			<select id="hehe" onchange="fun();">
					<option>-请选择-</option>
				</select>
				<select name="address" id="haha">
					<option>-请选择-</option>
				</select>
	 </li>  
     <li class="col-md-6  p0 ">
	   <span class="">毕业院校：</span>
        <input class="span2" disabled="disabled" maxlength="40" onblur="validataForm(this,'nameFont6');" value="${expert.graduateSchool }" name="graduateSchool" id="appendedInput" type="text">
          
       <font id="nameFont6"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专业技术职称：</span>
        <input class="span2" disabled="disabled" maxlength="20" onblur="validataForm(this,'nameFont7');" value="${expert.professTechTitles }" name="professTechTitles" id="appendedInput" type="text">
          
       <font id="nameFont7"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">参加工作时间：</span>
        <input class="span2 Wdate w220" value="<fmt:formatDate type='date' value='${expert.timeToWork }' dateStyle="default" pattern="yyyy-MM-dd"/>" disabled="disabled" onfocus="validataForm(this,'nameFont8');" onblur="validataForm(this,'nameFont8');" readonly="readonly" name="timeToWork" id="appendedInput" type="text" >
       <font id="nameFont8"></font>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">最高学历：</span>
	   <select class="span2" name="hightEducation" disabled="disabled">
	   	<option <c:if test="${expert.hightEducation eq '博士' }">selected="selected"</c:if> value="博士">博士</option>
	   	<option <c:if test="${expert.hightEducation eq '硕士' }">selected="selected"</c:if> value="硕士">硕士</option>
	   	<option <c:if test="${expert.hightEducation eq '本科' }">selected="selected"</c:if> value="研究生">本科</option>
	   </select>
	 </li> 
     <li class="col-md-6  p0 ">
	   <span class="">专业：</span>
        <input class="span2" value="${expert.major }" disabled="disabled" maxlength="20" onblur="validataForm(this,'nameFont9');" name="major" id="appendedInput" type="text">
          
       <font id="nameFont9"></font>
	 </li> 
	 <li class="col-md-6  p0 ">
	   <span class="">从事专业起始年度：</span>
        <input class="span2 Wdate w220" value="<fmt:formatDate type='date' value='${expert.timeStartWork }' dateStyle="default" pattern="yyyy-MM-dd"/>" disabled="disabled" onfocus="validataForm(this,'nameFont10');" onblur="validataForm(this,'nameFont10');" readonly="readonly" name="timeStartWork" id="appendedInput" type="text" >
      <font id="nameFont10"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">工作单位：</span>
        <input class="span2" disabled="disabled" maxlength="40" onblur="validataForm(this,'nameFont11');" value="${expert.workUnit }" name="workUnit" id="appendedInput" type="text">
          
       <font id="nameFont11"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">单位地址：</span>
        <input class="span2" maxlength="40" disabled="disabled" onblur="validataForm(this,'nameFont12');" value="${expert.unitAddress }" name="unitAddress" id="appendedInput" type="text">
          
       <font id="nameFont12"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">联系电话（固话）：</span>
        <input class="span2" maxlength="15" disabled="disabled" onblur="validataForm(this,'nameFont13');" value="${expert.telephone }" name="telephone" id="appendedInput" type="text">
          
       <font id="nameFont13"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">传真：</span>
        <input class="span2" maxlength="10" disabled="disabled" onblur="validataForm(this,'nameFont14');" value="${expert.fax }" name="fax" id="appendedInput" type="text">
          
       <font id="nameFont14"></font>
	 </li> 
	  <li class="col-md-6  p0 ">
	   <span class="">邮政编码：</span>
        <input class="span2" maxlength="6" disabled="disabled" onblur="validataForm(this,'nameFont15');"  value="${expert.postCode }" name="postCode" id="appendedInput" type="text">
          
       <font id="nameFont15"></font>
	 </li> 
	<li class="col-md-6  p0 ">
	   <span class="">取得技术职称时间：</span>
        <input class="span2 Wdate w220" value="<fmt:formatDate type='date' value='${expert.makeTechDate }' dateStyle="default" pattern="yyyy-MM-dd"/>" disabled="disabled" onfocus="validataForm(this,'nameFont16');" onblur="validataForm(this,'nameFont16');" readonly="readonly" name="makeTechDate" id="appendedInput" type="text" onclick='WdatePicker()'>
       <font id="nameFont16"></font>
	 </li>  
	  <li class="col-md-6  p0 ">
	   <span class="">学位：</span>
        <input class="span2" disabled="disabled" value="${expert.degree }"  maxlength="10" onblur="validataForm(this,'nameFont17');" name="degree" id="appendedInput" type="text">
          
       <font id="nameFont17"></font>
	 </li>
	  <li class="col-md-6  p0 ">
	   <span class="">健康状态：</span>
        <input class="span2" maxlength="10" value="${expert.healthState }" disabled="disabled" onblur="validataForm(this,'nameFont18');" name="healthState" id="appendedInput" type="text">
          
       <font id="nameFont18"></font>
	 </li>  
	 <li class="col-md-6  p0 ">
	   <span class="">现任职务：</span>
        <input class="span2" maxlength="10" value="${expert.atDuty }" disabled="disabled" onblur="validataForm(this,'nameFont19');" name="atDuty" id="appendedInput" type="text">
        
       <font id="nameFont19"></font>
	 </li>
   </ul>
  	<div class="padding-top-10 clear">
   <div class="headline-v2 clear">
   <h2>采购机构</h2>
   </div>
   <table class="table table-condensed" >
  	<tr>
		<th>采购机构名称：</th><td>${purchase.name }</td>
		<th>采购机构联系人：</th><td>${purchase.princinpal }</td>
		<th>采购机构地址：</th><td>${purchase.detailAddr }</td>
		<th>联系电话：</th><td>${purchase.mobile }</td>
	</tr>
	</table>
  </div>
  <!-- 附件信息-->
  <div class="padding-top-10 clear">
   <div class="headline-v2 clear">
   <h2>附件信息</h2>
   </div>
   <ul class="list-unstyled list-flow p0_20">
    <c:forEach items="${attachmentList }" var="att" varStatus="vs">
       <c:choose>
       		<c:when test="${att.fileType == 0 }">
       			<li class="col-md-6  p0 ">
		   			<span class="">身份证：</span>
				   <div >
				     <h4>
			         <a href="<%=basePath %>expert/downLoadFile.do?attachmentId=${att.id }">${fn:substringAfter(att.fileName, "_")}</a>
			         </h4>
			       </div>
				 </li>
       		</c:when>
       		<c:when test="${att.fileType == 1 }">
       			<li class="col-md-6  p0 ">
		   			<span class="">学历证书：</span>
				   <div >
				     <h4>
			         <a href="<%=basePath %>expert/downLoadFile.do?attachmentId=${att.id }">${fn:substringAfter(att.fileName, "_")}</a>
			         </h4>
			       </div>
				 </li>
       		</c:when>
       		<c:when test="${att.fileType == 2 }">
       			<li class="col-md-6  p0 ">
		   			<span class="">职称证书：</span>
				   <div >
				     <h4>
			         <a href="<%=basePath %>expert/downLoadFile.do?attachmentId=${att.id }">${fn:substringAfter(att.fileName, "_")}</a>
			         </h4>
			       </div>
				 </li>
       		</c:when>
       		<c:when test="${att.fileType == 3 }">
       			<li class="col-md-6  p0 ">
		   			<span class="">学位证书：</span>
				   <div >
				     <h4>
			         <a href="<%=basePath %>expert/downLoadFile.do?attachmentId=${att.id }">${fn:substringAfter(att.fileName, "_")}</a>
			         </h4>
			       </div>
				 </li>
       		</c:when>
       		<c:when test="${att.fileType == 4}">
       			<li class="col-md-6  p0 ">
		   			<span class="">本人照片：</span>
				   <div>
				     <h4>
			         <a href="<%=basePath %>expert/downLoadFile.do?attachmentId=${att.id }">${fn:substringAfter(att.fileName, "_")}</a>
			         </h4>
			       </div>
				 </li>
       		</c:when>
       		<c:when test="${att.fileType == 5 }">
       			<li class="col-md-6  p0 ">
		   			<span class="">专家申请表：</span>
				   <div >
				     <h4>
			         <a href="<%=basePath %>expert/downLoadFile.do?attachmentId=${att.id }">${fn:substringAfter(att.fileName, "_")}</a>
			         </h4>
			       </div>
				 </li>
       		</c:when>
       		<c:when test="${att.fileType == 6 }">
       			<li class="col-md-6  p0 ">
		   			<span class="">专家合同书：</span>
				   <div >
				     <h4>
			         <a href="<%=basePath %>expert/downLoadFile.do?attachmentId=${att.id }">${fn:substringAfter(att.fileName, "_")}</a>
			         </h4>
			       </div>
				 </li>
       		</c:when>
       </c:choose>
   </c:forEach>
   </ul>
  </div>
  </div> 
   <div class="tab-pane fade height-450" id="tab-2">
   <div class=" f16 count_flow fl">
	<i>02</i>评标专家类型
   </div>
		<div class="margin-bottom-0  categories">
		 <ul class="list-unstyled list-flow" style="margin-left: 250px;">
     		<li class="p0">
			   <span class="">专家类型：</span>
			   <input type="hidden" id="expertsTypeIds" value="${expert.expertsTypeId }">
			   <select name="expertsTypeId" id="expertsTypeId" disabled="disabled">
			   		<option value="0">-请选择-</option>
			   		<option <c:if test="${expert.expertsTypeId eq '1' }">selected="selected"</c:if> value="1">技术</option>
			   		<option <c:if test="${expert.expertsTypeId eq '2' }">selected="selected"</c:if> value="2">法律</option>
			   		<option <c:if test="${expert.expertsTypeId eq '3' }">selected="selected"</c:if> value="3">商务</option>
			   </select>
			 </li>
        </ul>
        <div id="ztree" class="ztree"></div>
		</div>
	</div>
	
	<!-- <div class="tab-pane fade height-450" id="tab-3">
			<div class="margin-bottom-0  categories">
			</div>
	</div> -->
  </div>
   <div class="padding-left-40 padding-right-20 clear">
   
   
  </div> 
  <div  class="col-md-12">
   <div class="fl padding-10">
	<a class="btn btn-windows back"  onclick="location.href='javascript:history.go(-1);'">返回</a>
	</div>
  </div>
  </form>
                </div>
 	          </div>
		   </div>
		 </div>
	  </div>
	</div>
</div>
</body>
</html>