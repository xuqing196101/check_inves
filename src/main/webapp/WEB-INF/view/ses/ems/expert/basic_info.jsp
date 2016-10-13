<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//表单标示
	String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
%>
<title>评审专家基本信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css"/>
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/layer/skin/layer.ext.css" media="screen" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/upload.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplier.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/public/ZHQ/js/expert/validate_expert_basic_info.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/expert/TestAddress.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/expert/TestChooseAddress.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css">
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>
<script type="text/javascript">
	function kaptcha() {
		$("#kaptchaImage").hide().attr('src', '${pageContext.request.contextPath}/Kaptcha.jpg').fadeIn();
	}
	    var treeObj;
		var datas;
		   var setting={
					async:{
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
				                onAsyncSuccess: onAsyncSuccess  
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
	$(function() {
		
		//采购机构
		var sup = $("#purchaseDepId").val();
		   var radio=document.getElementsByName("purchaseDepId");
		   for(var i=0;i<radio.length;i++){
		 		if(sup==radio[i].value){
		 			radio[i].checked=true;
		 	 		break;
		 		}
		   }
		   //回显已选品目树
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
			  //控制品目树的显示和隐藏
				 if(expertsTypeId==1 || expertsTypeId=="1"){
				 //treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
				treeObj = $.fn.zTree.init($("#ztree"), setting);  
		         setTimeout(function(){  
		             expandAll("ztree");  
		         },1000);//延迟加载  
					 $("#ztree").show();
				 }else{
					 treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
					 $("#ztree").hide();
				 }
			}); 
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
	var treeid=null;
    /*树点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id;
		
    }
	function submitForm1(){
		//if(validateForm1()){
			$("#zancun").val(1);
			getChildren();
			$("#form1").submit();
		//}
	}
	//获取选中子节点id
	function getChildren(){
		var Obj=$.fn.zTree.getZTreeObj("ztree");  
	     var nodes=Obj.getCheckedNodes(true);  
	     var ids = new Array();  
	     for(var i=0;i<nodes.length;i++){ 
	    	 if(!nodes[i].isParent){
	        //获取选中节点的值  
	         ids.push(nodes[i].id); 
	    	 }
	     } 
	     $("#categoryId").val(ids);
		
	}
		/** 专家完善注册信息页面 */
	function supplierRegist(name, i, position) {
		 if(i==3){
			if (!validateForm1()) {
				return;
			}
		}
		if(i==4){
			if (!validateType()) {
				return;
			}
		} 
		if(i==5){
			if(!validateJiGou()){
				return;
			}
		}
		if(i==7){
			if(!validateHeTong()){
				return;
			}
		}
		
		var t = null;
		var l = null;
		if (position == "pre") {
			t = name + "_" + i;
			l = name + "_" + (i - 1);
		}
		if (position == "next") {
			t = name + "_" + i;
			l = name + "_" + (i + 1);
		}
		$("#zancun").val(0);
		$("#" + t).hide();
		$("#" + l).show();
	}
	function pre(name, i, position) {
		var t = null;
		var l = null;
		if (position == "pre") {
			t = name + "_" + i;
			l = name + "_" + (i - 1);
		}
		if (position == "next") {
			t = name + "_" + i;
			l = name + "_" + (i + 1);
		}
		$("#zancun").val(0);
		$("#" + t).hide();
		$("#" + l).show();
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
	//回显基本信息到表中
	function editTable(){
		var name = $("#relName").val();
		$("#tName").text(name);
		var sex = $("#gender").val();
		if(sex=="M"){
		  $("#tSex").text("男");
		}
		if(sex=="F"){
			$("#tSex").text("女");
		}
		var birthday = $("#birthday").val();
		$("#tBirthday").text(birthday);
		var tFace = $("#politicsStatus").val();
		$("#tFace").text(tFace);
		var professTechTitles = $("#professTechTitles").val();
		$("#tHey").text(professTechTitles);
		var idNumber = $("#idNumber").val();
		$("#tNumber").text(idNumber);
		var hightEducation = $("#hightEducation").val();
		$("#tHight").text(hightEducation);
		var degree = $("#degree").val();
		$("#tWei").text(degree);
		var mobile = $("#mobile").val();
		$("#tMobile").text(mobile);
		var telephone = $("#telephone").val();
		$("#tTelephone").text(telephone);
		var workUnit = $("#workUnit").val();
		$("#tWorkUnit").text(workUnit);
		var graduateSchool = $("#graduateSchool").val();
		$("#tGraduateSchool").text(graduateSchool);
		var unitAddress = $("#unitAddress").val();
		$("#tUnitAddress").text(unitAddress);
		var postCode = $("#postCode").val();
		$("#tPostCode").text(postCode);
		var timeStartWork = $("#timeStartWork").val();
		$("#tTimeStartWork").text(timeStartWork);
	}
	function fun(){
		supplierRegist('reg_box_id', 3, 'next'); 
		editTable();
	}
	function fun1(){
		//选中的子节点
		getChildren();
		supplierRegist('reg_box_id', 4, 'next');
		var expertsTypeId = $("#expertsTypeId").val();
		if(expertsTypeId == "1"){
			$("#tExpertsTypeId").text("技术");
		}
		if(expertsTypeId == "2"){
			$("#tExpertsTypeId").text("法律");
		}
		if(expertsTypeId == "3"){
			$("#tExpertsTypeId").text("商务");
		}
	}
	//下载
	function downloadTable(){
		$("#form1").attr("action","<%=basePath %>expert/download.html");
		$("#form1").submit();
	}
	//提交
	function addSubmitForm(){
		$("#form1").attr("action","<%=basePath %>expert/add.html");
		$("#form1").submit();
	}
	//回显采购机构信息
	function addPurList(){
	var radio = $("input[type='radio']:checked");
	var depName = $(radio).parent().next().next().children().val();
	var person =  $(radio).parent().next().next().next().children().val();
	var address = $(radio).parent().next().next().next().next().children().val();
	var phone =  $(radio).parent().next().next().next().next().next().children().val();
	$("#depName_").text(depName);
	$("#person_").text(person);
	$("#address_").text(address);
	$("#phone_").text(phone);
	$("#depName_2").text(depName);
	$("#person_2").text(person);
	$("#address_2").text(address);
	$("#phone_2").text(phone);
	supplierRegist('reg_box_id', 5, 'next'); 
	}
	//显示隐藏树
	function typeShow(){
		 var expertsTypeId = $("#expertsTypeId").val();
		 if(expertsTypeId==1 || expertsTypeId=="1"){
			 $("#ztree").show();
		 }else{
			 $("#ztree").hide();
		 }
		
	}
</script>

</head>

<body>
	<div class="wrapper">
		</div>
		<form id="form1" action="${pageContext.request.contextPath}/expert/add.html" method="post"  enctype="multipart/form-data" >
		<input type="hidden" name="userId" value="${user.id }">
		<input type="hidden" id="purchaseDepId" value="">
		<input type="hidden" name="id" value="">
		<input type="hidden" name="zancun" id="zancun">
		<%
			session.setAttribute("tokenSession", tokenValue);
		%>
		<input type="hidden" id="categoryId" name="categoryId">
		 <input type="hidden"  name="token2" value="<%=tokenValue%>">
		<div id="reg_box_id_3" class="container clear margin-top-30 job-content">
			<h2 class="padding-20 mt40">
					<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
					<span class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> 
					<span class="new_step fl"><i class="">6</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
		<div class="container content height-350">
			<div class="row magazine-page">
				<div class="col-md-12 tab-v2 job-content">
					<div class="padding-top-10">
						<div class="tab-content padding-top-20  h900">
							<div class="tab-pane fade active in height-500" id="tab-1">
								<div class=" margin-bottom-0"><br/>
									<h2 class="f16 jbxx">
										<i>01</i>专家基本信息
									</h2>
									<ul class="list-unstyled list-flow">
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 专家姓名：</span>
											<div class="input-append">
												<input class="span3" id="relName" name="relName" value="${expert.relName }"   type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 性别：</span>
											<div class="input-append">
												 <select class="span3" name="gender" id="gender">
												    <option selected="selected" value="">-请选择-</option>
												   	<option <c:if test="${expert.gender eq 'M' }">selected="selected"</c:if> value="M">男</option>
												   	<option <c:if test="${expert.gender eq 'F' }">selected="selected"</c:if> value="F">女</option>
												  </select>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""> 出生日期：</span>
											<div class="input-append">
       											 <input class="span3 Wdate"   readonly="readonly" value="<fmt:formatDate type='date' value='${expert.birthday }' dateStyle="default" pattern="yyyy-MM-dd"/>" name="birthday" id="birthday" type="text" onclick='WdatePicker()'>
      										</div>
										</li>
										<li class="col-md-6 p0"><span class=""><i class="red">＊</i>专家来源：</span>
											<div class="input-append">
												<select class="span3" name="expertsFrom" id="expertsFrom">
												<option selected="selected" value="">-请选择-</option>
											   	<option <c:if test="${expert.expertsFrom eq '军队' }">selected="selected"</c:if> value="军队">军队</option>
											   	<option <c:if test="${expert.expertsFrom eq '地方' }">selected="selected"</c:if> value="地方">地方</option>
											   	<option <c:if test="${expert.expertsFrom eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
											   </select>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i> 证件类型：</span>
											<div class="input-append">
											<select class="span3" name="idType" id="idType">
										   	<option selected="selected" value="">-请选择-</option>
										   	<option <c:if test="${expert.idType eq '身份证' }">selected="selected"</c:if> value="身份证">身份证</option>
										   	<option <c:if test="${expert.idType eq '士兵证' }">selected="selected"</c:if> value="士兵证">士兵证</option>
										   	<option <c:if test="${expert.idType eq '驾驶证' }">selected="selected"</c:if> value="驾驶证">驾驶证</option>
										   	<option <c:if test="${expert.idType eq '工作证' }">selected="selected"</c:if> value="工作证">工作证</option>
										   	<option <c:if test="${expert.idType eq '护照' }">selected="selected"</c:if> value="护照">护照</option>
										   	<option <c:if test="${expert.idType eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
										   </select>
											</div>
										</li>
										
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>证件号码：</span>
											<div class="input-append">
												 <input class="span3" maxlength="30" value="${expert.idNumber }"  name="idNumber" id="idNumber" type="text">
        									</div>
										</li>
										<li class="col-md-6 p0 "><span class="">政治面貌：</span>
											<div class="input-append">
												<select class="span3" name="politicsStatus" id="politicsStatus">
												<option selected="selected" value="">-请选择-</option>
											   	<option <c:if test="${expert.politicsStatus eq '党员' }">selected="selected"</c:if> value="党员">党员</option>
											   	<option <c:if test="${expert.politicsStatus eq '团员' }">selected="selected"</c:if> value="团员">团员</option>
											   	<option <c:if test="${expert.politicsStatus eq '群众' }">selected="selected"</c:if> value="群众">群众</option>
											   	<option <c:if test="${expert.politicsStatus eq '其他' }">selected="selected"</c:if> value="其他">其他</option>
											   </select></div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>民族：</span>
											<div class="input-append">
											<input class="span3" maxlength="10" value="${expert.nation }"  name="nation" id="nation" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>所在地区：</span>
											<div class="input-append">
											  <select id="id_provSelect" name="provSelect" onChange="loadCity(this.value);"><option value="">请选择省份</option></select>
											  <select id="id_citySelect" name="citySelect" onChange="loadArea(this.value);"><option value="">请选择城市</option></select>
											  <select id="id_areaSelect" name="address" ><option value="">请选择区域</option></select>
											 <SCRIPT LANGUAGE="JavaScript"> loadProvince('${expert.address }');</SCRIPT> 
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>毕业院校：</span>
											<div class="input-append">
											<input class="span3" maxlength="40" value="${expert.graduateSchool }"  name="graduateSchool" id="graduateSchool" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""> 专家技术职称：</span>
											<div class="input-append">
											<input class="span3" maxlength="20" value="${expert.professTechTitles }"  name="professTechTitles" id="professTechTitles" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""> 参加工作时间：</span>
											<div class="input-append">
											<input class="span3 Wdate"   readonly="readonly" value="<fmt:formatDate type='date' value='${expert.timeToWork }' dateStyle="default" pattern="yyyy-MM-dd"/>" name="timeToWork" id="appendedInput" type="text" onclick='WdatePicker()'>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>最高学历：</span>
											<div class="input-append">
											 <select class="span3" name="hightEducation" id="hightEducation" >
											 	<option selected="selected" value="">-请选择-</option>
											   	<option <c:if test="${expert.hightEducation eq '博士' }">selected="selected"</c:if> value="博士">博士</option>
											   	<option <c:if test="${expert.hightEducation eq '硕士' }">selected="selected"</c:if> value="硕士">硕士</option>
											   	<option <c:if test="${expert.hightEducation eq '本科' }">selected="selected"</c:if> value="研究生">本科</option>
											  </select>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>专业：</span>
											<div class="input-append">
											<input class="span3" maxlength="20" value="${expert.major }"  name="major" id="major" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""> 从事专业起始年度：</span>
											<div class="input-append">
											 <input class="span3 Wdate" value="<fmt:formatDate type='date' value='${expert.timeStartWork }' dateStyle="default" pattern="yyyy-MM-dd"/>"  readonly="readonly" name="timeStartWork" id="timeStartWork" type="text" onclick='WdatePicker()'>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class="">工作单位：</span>
											<div class="input-append">
											<input class="span3" maxlength="40" value="${expert.workUnit }"  name="workUnit" id="workUnit" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>单位地址：</span>
											<div class="input-append">
											 <input class="span3" maxlength="40" value="${expert.unitAddress }"  name="unitAddress" id="unitAddress" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>联系电话（固话）：</span>
											<div class="input-append">
											<input class="span3" maxlength="15" value="${expert.telephone }"  name="telephone" id="telephone" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>手机：</span>
											<div class="input-append">
											<input class="span3" maxlength="15" value="${expert.mobile }"  name="mobile" id="mobile" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""> 传真：</span>
											<div class="input-append">
											<input class="span3" maxlength="10"  value="${expert.fax }"  name="fax" id="fax" type="text">
											</div>
										</li> 
        								<li class="col-md-6 p0 "><span class=""> 邮编：</span>
											<div class="input-append">
											<input class="span3" maxlength="6" value="${expert.postCode }"  name="postCode" id="postCode" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""> 获得技术时间：</span>
											<div class="input-append">
											<input class="span3 Wdate" value="<fmt:formatDate type='date' value='${expert.makeTechDate }' dateStyle="default" pattern="yyyy-MM-dd"/>"  readonly="readonly" name="makeTechDate" id="makeTechDate" type="text" onclick='WdatePicker()'>
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""> 学位：</span>
											<div class="input-append">
											<input class="span3" maxlength="10" value="${expert.degree }"  name="degree" id="degree" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""><i class="red">＊</i>健康状态：</span>
											<div class="input-append">
											<input class="span3" maxlength="10" value="${expert.healthState }"  name="healthState" id="healthState" type="text">
											</div>
										</li>
										<li class="col-md-6 p0 "><span class=""> 现任职务：</span>
											<div class="input-append">
											<input class="span3" maxlength="10" value="${expert.atDuty }"  name="atDuty" id="appendedInput" type="text">
											</div>
										</li>
									</ul>
									</div>
									<!-- 附件信息-->
										  <div class="padding-top-10 clear">
										   <div class="headline-v2 clear">
										   <h2>上传附件</h2>
										   </div>
										  </div>
										  <div class="padding-left-40 padding-right-20 clear">
										   <ul class="list-unstyled list-flow p0_20">
										   <li class="col-md-6  p0 ">
											   <span class="" id="files1"><i class="red">＊</i>身份证：</span>
											   <div class="input-append mt5" >
													<a href="javascript:void(0)"><i></i><input accept="image/*" type="file" name="files" id ="file1" class="fl"/></a>
												</div>
											 </li>
											 
											 <li class="col-md-6  p0 ">
											   <span class="" id="files2"><i class="red" >＊</i>学历证书：</span>
											     <div class="input-append mt5">
													<a href="javascript:void(0)"><i></i><input accept="image/*" type="file" name="files" id ="file2" class="fl"/></a>
												</div>
											 </li>
											 <li class="col-md-6  p0 ">
											   <span class="" id="files3"><i class="red">＊</i>职称证书：</span>
											      <div class="input-append mt5">
													<a href="javascript:void(0)"><i></i><input accept="image/*" type="file" name="files" id ="file3" class="fl"/></a>
												</div>
											 </li>
											  <li class="col-md-6  p0 ">
											   <span class="" id="files4"><i class="red">＊</i>学位证书：</span>
											      <div class="input-append mt5">
													<a href="javascript:void(0)"><i></i><input accept="image/*"  type="file" name="files" id ="file4" class="fl"/></a>
												</div>
											  </li>
											  <li class="col-md-6  p0 ">
											   <span class="" id="files5"><i class="red">＊</i>本人照片：</span>
											      <div class="input-append mt5">
													<a href="javascript:void(0)"><i></i><input accept="image/*" type="file" name="files" id ="file5" class="fl"/></a>
												</div>
											 </li>
										   </ul>
										   </div>
									<div class="tc mt20 clear col-md-11">
									
									        <button class="btn btn-windows git" onclick="submitForm1();"  type="button">暂存</button>
											<button class="btn btn-windows git" id="nextBind"  type="button" onclick="fun();" >下一步</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="reg_box_id_4" class="container clear margin-top-30 yinc">
		  		<h2 class="padding-20 mt40">
					<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
					<span class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> 
					<span class="new_step fl"><i class="">6</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
			<br/>
		    <h2 class="f16 jbxx">
			<i>02</i>专家类型
			</h2>
			<ul class="list-unstyled list-flow" style="margin-left: 250px;">
     		<li class="p0">
			   <span class="">专家类型：</span>
			   <input type="hidden" id="expertsTypeIds" value="" >
			   <select name="expertsTypeId" id="expertsTypeId" onchange="typeShow();">
			   		<option value="">-请选择-</option>
			   		<option value="1">技术</option>
			   		<option value="2">法律</option>
			   		<option value="3">商务</option>
			   </select>
			 </li>
   			 </ul>
   			
   			   <div id="ztree" style="margin-left: 100px;" class="ztree"></div>
   			
   			 
		    <div class="tc mt20 clear col-md-11">
				<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 4, 'pre')">上一步</button>
				<button class="btn btn-windows git" onclick="submitForm1();"  type="button">暂存</button>
				<button class="btn btn-windows git"   type="button" onclick="fun1();">下一步</button>
			</div>
		</div>
		
		<!-- 项目戳开始 -->
		<div id="reg_box_id_5" class="container clear margin-top-30 yinc">
		  		<h2 class="padding-20 mt40">
					<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
					<span class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> 
					<span class="new_step fl"><i class="">6</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2><br/>
		      <h2 class="f16 jbxx">
			    <i>03</i>选择采购机构
			  </h2>
			    <h2 class="f16 jbxx">
				  推荐采购机构
			    </h2>
			<table id="tb1"  class="table table-bordered table-condensed">
			
				<thead>
					<tr>
					  <th class="info w30"><input type="radio"  disabled="disabled"  id="purchaseDepId2" ></th>
					  <th class="info w50">序号</th>
					  <th class="info">采购机构</th>
					  <th class="info">联系人</th>
					  <th class="info">联系地址</th>
					  <th class="info">联系电话</th>
					</tr>
				</thead>
				<c:forEach items="${ purchase}" var="p" varStatus="vs">
					<tr align="center">
						<td><input type="radio" name="purchaseDepId"  value="${p.id }" /></td>
						<td>${vs.count}</td>
						<td><input border="0" disabled="disabled" value="${p.name }"></td>
						<td><input border="0" disabled="disabled" value="${p.princinpal }"></td>
						<td><input border="0" disabled="disabled" value="${p.detailAddr }"></td>
						<td><input border="0" disabled="disabled" value="${p.mobile }"></td>
					</tr>
				</c:forEach> 
			</table>
			 <h2 class="f16 jbxx">
				其他采购机构
			</h2>
			<table id="tb2" class="table table-bordered table-condensed">
				<thead>
					<tr>
					  <th class="info w30"><input type="radio" disabled="disabled"  alt=""></th>
					  <th class="info w50">序号</th>
					  <th class="info">采购机构</th>
					  <th class="info">联系人</th>
					  <th class="info">联系地址</th>
					  <th class="info">联系电话</th>
					</tr>
				</thead>
				<%-- <c:forEach items="" var="" varStatus="vs">
					<tr>
						<td><input type="checkbox"  name="cbox" onclick="box(this)" /></td>
						<td>${(l-1)*10+vs.index+1}</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach> --%>
				<tr>
				  <td class="tc w30"><input type="radio" name="purchaseDepId" id="checked" alt="" value="3"></td>
				  <td class="tc w50">1</td>
				  <td class="tc"><input border="0" disabled="disabled" value="哈哈"></td>
				  <td class="tc"><input border="0" disabled="disabled" value="飒飒"></td>
				  <td class="tc"><input border="0" disabled="disabled" value="北京"></td>
				 <td class="tc"><input border="0" disabled="disabled" value="13333333333"></td>
				</tr>
			</table>
			<h6>
		               友情提示：请专家记录好初审采购机构的相关信息，以便进行及时沟通
		    </h6>
		    <div class="tc mt20 clear col-md-11">
				<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 5, 'pre')">上一步</button>
				<button class="btn btn-windows git" onclick="submitForm1();"  type="button">暂存</button>
				<button class="btn btn-windows git"   type="button" onclick="addPurList();">下一步</button>
			</div>
		</div>
	<div id="reg_box_id_6" class="container clear margin-top-30 yinc">
		  <h2 class="padding-20 mt40">
					<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
					<span class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step current fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> 
					<span class="new_step fl"><i class="">6</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2><br/>
			<h2 class="f16 jbxx">
			<i>04</i>打印专家申请表
			</h2>
			<!-- 供应商申请书上传：<input type="file" name=""/>
			供应商承诺书上传：<input type="file" name=""/> -->
				   	<div ><br/><br/>
<table class="table table-bordered table-condensed">
   	<tr>
   		<td align="center" width="100px">姓名</td>
   		<td align="center" width="150px" id="tName"></td>
   		<td align="center">性别</td>
		  	<td class="tc" id="tSex"></td>
   		
   		<td align="center" rowspan="4">照片</td>
   	</tr>
   <tr>
   		<td align="center">出生日期</td>
   		<td align="center" width="150px" id="tBirthday"></td>
   		<td align="center">政治面貌</td>
   		<td align="center" width="150px" id="tFace"></td>
   </tr>
   <tr>
   		<td align="center">所在地区</td>
   		<td align="center" width="150px"></td>
   		<td align="center">职称</td>
   		<td align="center" width="150px" id="tHey"></td>
   </tr>
   <tr>
   		<td align="center">身份证号码</td>
   		<td align="center" id="tNumber" colspan="3"></td>
   </tr>
   <tr>
   		<td align="center">从事专业类别</td>
   		<td align="center" id="tExpertsTypeId" width="150px"></td>
   		<td align="center">从事年限</td>
   		<td align="center" id="tTimeStartWork" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">最高学历</td>
   		<td align="center" id="tHight" width="150px"></td>
   		<td align="center">最高学位</td>
   		<td align="center" id="tWei" colspan="2"></td>
   
   </tr>
   <tr>
   		<td align="center">执业资格1</td>
   		<td align="center" width="150px"> </td>
   		<td align="center">注册证书编号1</td>
   		<td align="center" colspan="2"> </td>
   </tr>
   <tr>
   		<td align="center">执业资格2</td>
   		<td align="center" width="150px"></td>
   		<td align="center">注册证书编号2</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">执业资格3</td>
   		<td align="center" width="150px"></td>
   		<td align="center">注册证书编号3</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">近两年是否接受过评标业务培训</td>
   		<td align="center" width="150px"></td>
   		<td align="center">是否愿意成为应急专家</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">所属行业</td>
   		<td align="center" width="150px"></td>
   		<td align="center">报送部门</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">手机号码</td>
   		<td align="center" id="tMobile" width="150px"></td>
   		<td align="center">单位电话</td>
   		<td align="center" id="tTelephone" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">住宅电话</td>
   		<td align="center" width="150px"></td>
   		<td align="center">电子邮箱</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">毕业院校及专业</td>
   		<td align="center" id="tGraduateSchool" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">单位名称</td>
   		<td align="center" id="tWorkUnit" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">单位地址 </td>
   		<td align="center" id="tUnitAddress" width="150px"></td>
   		<td align="center">单位邮编</td>
   		<td align="center" id="tPostCode" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">家庭地址 </td>
   		<td align="center" width="150px" ></td>
   		<td align="center">家庭邮编</td>
   		<td align="center" colspan="2"></td>
   </tr>
   <tr>
   		<td align="center">评标专业一</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业二</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业三</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业四</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业五</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center">评标专业六</td>
   		<td align="center" colspan="4"></td>
   </tr>
   <tr>
   		<td align="center" colspan="5">工作经历</td>
   </tr>
   <tr>
   		<td align="center">起止年月</td>
   		<td align="center" colspan="3">单位及职务</td>
   		<td align="center">证明人</td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center" width="150px"> </td>
   </tr>
   <tr>
   		<td align="center"> 至</td>
   		<td align="center" colspan="3"> </td>
   		<td align="center" width="150px"> </td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center" width="150px"> </td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center" width="150px"> </td>
   </tr>
   <tr>
   		<td align="center">至 </td>
   		<td align="center" colspan="3"> </td>
   		<td align="center" width="150px"> </td>
   </tr>
   </table>
		    <div class="tc mt20 clear col-md-11">
		   		<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 6, 'pre')">上一步</button>
				<!-- <button class="btn btn-windows git"   type="button" onclick="window.print()">打印</button> -->
				<a class="btn btn-windows git" onclick="downloadTable();" href="javascript:void(0)">下载</a>
				<button class="btn btn-windows git" onclick="submitForm1();"  type="button">暂存</button>
				<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 6, 'next')">下一步</button>
			</div>
		</div>
		</div>
		<div id="reg_box_id_7" class="container clear margin-top-30 yinc">
		 <h2 class="padding-20 mt40">
					<span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span>
					<span class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span class="new_step current fl"><i class="">5</i><div class="line"></div> <span class="step_desc_01">打印申请表</span> </span> 
					<span class="new_step current fl"><i class="">6</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2><br/>
			<h2 class="f16 jbxx">
			<i>05</i>专家申请表、合同书
			</h2>
			<!-- 供应商申请书上传：<input type="file" name=""/>
			供应商承诺书上传：<input type="file" name=""/> -->
				   	<div class="input-append mt40" style="margin-left:280px;">
						<li class="col-md-6  p0 ">
								<i class="red">＊</i><span class="" >专家申请表上传：</span>
									<div class="input-append mt5">
										<a href="javascript:void(0)"><input type="file" accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document" name="files" id ="regIdentitys1" class="fl"/></a>
									</div>
							</li>
							 <li class="col-md-6  p0 ">
								<i class="red">＊</i><span>专家合同书上传：</span>
									<div class="input-append mt5">
										<a href="javascript:void(0)"><input type="file"  name="files" id ="regIdentitys2" class="fl" accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document" /></a>
									</div>
							</li>
					</div>
			<div class="col-md-12 add_regist" style="margin-left:170px;">
				 <div class="fl mr20"><label class="regist_name">采购机构名称：</label><span id="depName_" class="regist_desc"></span></div>
				 <div class="fl mr20"><label class="regist_name">采购机构联系人：</label><span id="person_" class="regist_desc"></span></div>
				 <div class="fl mr20"><label class="regist_name">采购机构地址：</label><span id="address_" class="regist_desc"></span></div>
				 <div class="fl mr20"><label class="regist_name">联系电话：</label><span id="phone_" class="regist_desc"></span></div>
			 </div>
		    <div class="tc mt20 clear col-md-11">
		   		<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 7, 'pre')">上一步</button>
				<button class="btn btn-windows git"   type="button" onclick="supplierRegist('reg_box_id', 7, 'next')">下一步</button>
			</div>
		</div>
		
		<div id="reg_box_id_8" class="container content height-350 yinc">
		 <div class="row magazine-page pt40 mb40">
		   <div class="login_cl fl col-md-3">
		    <img src="${pageContext.request.contextPath}/public/ZHQ/images/success.jpg"/>
		   </div>
		   <div class="login_cr fl col-md-9 pt20">
		    <div class="col-md-12">
		     <p>
			  <span class="regist_info f18 b">信息填写完成！确认无误后请提交生效，提交后将不能更改！</span>正在等待审核人员审批。
			 </p>
		    </div>
			<div class="col-md-12 add_regist">
			 <div class="fl mr20"><label class="regist_name">采购机构名称：</label><span id="depName_2" class="regist_desc"></span></div>
			 <div class="fl mr20"><label class="regist_name">采购机构联系人：</label><span id="person_2" class="regist_desc"></span></div>
			 <div class="fl mr20"><label class="regist_name">采购机构地址：</label><span id="address_2" class="regist_desc"></span></div>
			 <div class="fl mr20"><label class="regist_name">联系电话：</label><span id="phone_2" class="regist_desc"></span></div>
			 </div>
			 <div class="mt20 col-md-12">
			  <button class="btn btn-windows back"   type="button" onclick="supplierRegist('reg_box_id', 8, 'pre')">上一步</button>
			  <input  class="btn btn-windows git" type="button" onclick="addSubmitForm();" value="提交" />
			 </div>
			</div>
		   </div>
		 </div>
		</form>
</body>
</html>
