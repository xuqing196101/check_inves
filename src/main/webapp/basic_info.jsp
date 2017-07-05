<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<jsp:include page="/WEB-INF/view/front.jsp"></jsp:include>
<%@ taglib uri="/tld/upload" prefix="up"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script src="${pageContext.request.contextPath}/js/ems/expert/validate_expert_basic_info.js"></script>
<script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
<%
//表单标示
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
session.setAttribute("tokenSession", tokenValue);
%>
<script type="text/javascript">
		function func(){
			var parentId = $("#addr").val();
			$.ajax({
				url : "${pageContext.request.contextPath}/area/find_by_parent_id.do",
				data:{"id":parentId}, 
				success:function(obj){
					$("#add").empty();
					$("#add").append("<option  value=''>-请选择-</option>");
					$.each(obj,function(i,result){
						$("#add").append("<option value='"+result.id+"'>"+result.name+"</option>");
					});
					$("#addr2").val(parentId);
					func2();
				}
			});
			
		}
		//第一个字地区事件
		function copySel(){
			$("#add2").val($("#add").val());
		}
		
		//第二个select事件
		function func2(){
			var parentId = $("#addr2").val();
			$.ajax({
				url : "${pageContext.request.contextPath}/area/find_by_parent_id.do",
				data:{"id":parentId},
				success:function(obj){
					$("#add2").empty();
					//var data = eval('(' + obj + ')');
					$("#add2").append("<option  value=''>-请选择-</option>");
					$.each(obj,function(i,result){
						$("#add2").append("<option value='"+result.id+"'>"+result.name+"</option>");
					});
				}
			});
			showJiGou();
		}
		   
	
	$(function(){
		//回显已选产品
		   var id="${expert.id}";
		   var count=0;
		   var expertsTypeId = $("#expertsTypeId").val();
			  //控制品目树的显示和隐藏
		   if(expertsTypeId==1 || expertsTypeId=="1"){
			  $.ajax({
				  url:"${pageContext.request.contextPath}/expert/getCategoryByExpertId.do?expertId="+id,
				  success:function(code){
					  var checklist = document.getElementsByName ("chkItem");
					  for(var i=0;i<checklist.length;i++){
							var vals=checklist[i].value;
							 if(code.length>0){
									$.each(code,function(i,result){
										if(vals==result){
						 				checklist[i].checked=true;
						 			    }
										if("GOODS"==result){
											count++;
										}
									});
							} 
						   } 
					    if(count>0){
							$("#hwType").show(); 
						}else{
							$("#hwType").hide(); 
						}
				  }
			  }); 
			  $("#ztree").show();
			}else{
			  $("#ztree").hide();
			}
				 showJiGou();
			}); 
	
	function submitForm1(){
		getChildren();
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#form1").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				  layer.msg("已暂存");
			  },
			  error:function(result){
			  }
		});
	}
	//无提示暂存
	function submitForm2(){
		getChildren();
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#form1").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
			  },
			  error:function(result){
			  }
		});
	}
	//获取选中子节点id
	function getChildren(){
		 var checklist = document.getElementsByName ("chkItem");
		 var count=0;
		 var ids=[];
		 for(var i=0;i<checklist.length;i++)
		   {
	 			var vals=checklist[i].value;
	 			if(checklist[i].checked){
	 				ids.push(vals);
	 				if(vals=="GOODS"){
	 				 count++;
	 				}
	 			}
		   } 
		if(count>0){
			 $("#hwType").show();  
		}else{
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
			 $("#hwType").hide();  
		}
	     $("#categoryId").val(ids);
		
	}
		/** 专家完善注册信息页面 */
	function supplierRegist(name, i, position) {
		  if(i==3){
			 if (!validateForm1()){
				return;
			} 
		} 
		if(i==4){
			if (!validateType()){
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
		//暂存无提示
		submitForm2();
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

	//回显基本信息到表中
	function editTable(){
		var name = $("#relName").val();
		$("#tName").text(name);
		//性别
		var obj = document.getElementById("gender"); //selectid

		var index = obj.selectedIndex; // 选中索引

		var text = obj.options[index].text;
		  $("#tSex").text(text);
		var birthday = $("#birthday").val();
		$("#tBirthday").text(birthday);
		//政治面貌
		var obj3 = document.getElementById("politicsStatus"); //selectid

		var index3 = obj3.selectedIndex; // 选中索引

		var tFace = obj3.options[index3].text;
		$("#tFace").text(tFace);
		var professTechTitles = $("#professTechTitles").val();
		$("#tHey").text(professTechTitles);
		var idNumber = $("#idNumber").val();
		$("#tNumber").text(idNumber);
		//最高学历
		var obj2 = document.getElementById("hightEducation"); //selectid

		var index2 = obj2.selectedIndex; // 选中索引

		var text2 = obj2.options[index2].text;
		
		$("#tHight").text(text2);
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
		//父地区
		var add= document.getElementById("addr"); //selectid

		var addiIdex = add.selectedIndex; // 选中索引

		var addValue1 =add.options[addiIdex].text;
		//子地区
		var add2= document.getElementById("add"); //selectid

		var addiIdex2 = add2.selectedIndex; // 选中索引

		var addValue2 =add2.options[addiIdex2].text;
		
		$("#Taddress").text(addValue1+","+addValue2);
	}
	function fun(){
		var ty1 = document.getElementById('ty1'); 
		var classname1 = ty1.className;
		if(classname1 != "new_step current fl"){
		ty1.setAttribute("class", "new_step current fl"); 
		}
		var ty2 = document.getElementById('ty2'); 
		var classname2 = ty2.className;
		if(classname2 != "new_step current fl"){
		ty2.setAttribute("class", "new_step current fl"); 
		}
		supplierRegist('reg_box_id', 3, 'next'); 
		editTable();
	}
	function fun1(){
		var jg1 = document.getElementById('jg1'); 
		var classname1 = jg1.className;
		if(classname1 != "new_step current fl"){
		jg1.setAttribute("class", "new_step current fl"); 
		}
		var jg2 = document.getElementById('jg2'); 
		var classname2 = jg2.className;
		if(classname2 != "new_step current fl"){
		jg2.setAttribute("class", "new_step current fl"); 
		}
		var jg3 = document.getElementById('jg3'); 
		var classname3 = jg3.className;
		if(classname3 != "new_step current fl"){
		jg3.setAttribute("class", "new_step current fl"); 
		}
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
		showJiGou();
	}
	//下载
	function downloadTable(){
		$("#form1").attr("action","${pageContext.request.contextPath}/expert/download.html");
		$("#form1").submit();
	}
	
	//提交
	function addSubmitForm(){
				 if (!validateForm1()) {
					 tab1();
					return;
				} 
				if (!validateType()) {
					tab2();
					return;
				}
				if(!validateJiGou()){
					tab3();
					return;
				}
				if(!validateHeTong()){
					tab5();
					return;
				}
				getChildren();
		$("#form1").attr("action","${pageContext.request.contextPath}/expert/add.html");
		$("#form1").submit();
	}
	//页签点击跳转
	function tab1(){
		var sp1 = document.getElementById('sp1'); 
		var classname1 = sp1.className;
		if(classname1 != "new_step current fl"){
		sp1.setAttribute("class", "new_step current fl"); 
		}
		$("#reg_box_id_3").show();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}
	function tab2(){
		if (!validateForm1()){
			return;
		} 
		
		var ty1 = document.getElementById('ty1'); 
		var classname1 = ty1.className;
		if(classname1 != "new_step current fl"){
		ty1.setAttribute("class", "new_step current fl"); 
		}
		var ty2 = document.getElementById('ty2'); 
		var classname2 = ty2.className;
		if(classname2 != "new_step current fl"){
		ty2.setAttribute("class", "new_step current fl"); 
		}
		
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").show();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}
	function tab3(){
		if (!validateForm1()){
			return;
		} 
		getChildren();
		if (!validateType()){
			tab2();
			return;
		}
		var jg1 = document.getElementById('jg1'); 
		var classname1 = jg1.className;
		if(classname1 != "new_step current fl"){
		jg1.setAttribute("class", "new_step current fl"); 
		}
		var jg2 = document.getElementById('jg2'); 
		var classname2 = jg2.className;
		if(classname2 != "new_step current fl"){
		jg2.setAttribute("class", "new_step current fl"); 
		}
		var jg3 = document.getElementById('jg3'); 
		var classname3 = jg3.className;
		if(classname3 != "new_step current fl"){
		jg3.setAttribute("class", "new_step current fl"); 
		}
		showJiGou();
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").show();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}
	function tab4(){
		if (!validateForm1()){
			return;
		} 
		getChildren();
		if (!validateType()){
			tab2();
			return;
		}
		if(!validateJiGou()){
			tab3();
			return;
		}
		editTable();
		var dy1 = document.getElementById('dy1'); 
		var classname1 = dy1.className;
		if(classname1 != "new_step current fl"){
		dy1.setAttribute("class", "new_step current fl"); 
		}
		var dy2 = document.getElementById('dy2'); 
		var classname2 = dy2.className;
		if(classname2 != "new_step current fl"){
		dy2.setAttribute("class", "new_step current fl"); 
		}
		var dy3 = document.getElementById('dy3'); 
		var classname3 = dy3.className;
		if(classname3 != "new_step current fl"){
		dy3.setAttribute("class", "new_step current fl"); 
		}
		var dy4 = document.getElementById('dy4'); 
		var classname4 = dy4.className;
		if(classname4 != "new_step current fl"){
		dy4.setAttribute("class", "new_step current fl"); 
		}
		
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").show();
		$("#reg_box_id_7").hide();
		$("#reg_box_id_8").hide();
	}
	function tab5(){
		if (!validateForm1()){
			return;
		} 
		getChildren();
		if (!validateType()){
			tab2();
			return;
		}
		if(!validateJiGou()){
			tab3();
			return;
		}
		var sc1 = document.getElementById('sc1'); 
		var classname1 = sc1.className;
		if(classname1 != "new_step current fl"){
		sc1.setAttribute("class", "new_step current fl"); 
		}
		var sc2 = document.getElementById('sc2'); 
		var classname2 = sc2.className;
		if(classname2 != "new_step current fl"){
		sc2.setAttribute("class", "new_step current fl"); 
		}
		var sc3 = document.getElementById('sc3'); 
		var classname3 = sc3.className;
		if(classname3 != "new_step current fl"){
		sc3.setAttribute("class", "new_step current fl"); 
		}
		var sc4 = document.getElementById('sc4'); 
		var classname4 = sc4.className;
		if(classname4 != "new_step current fl"){
		sc4.setAttribute("class", "new_step current fl"); 
		}
		var sc5 = document.getElementById('sc5'); 
		var classname5 = sc5.className;
		if(classname5 != "new_step current fl"){
		sc5.setAttribute("class", "new_step current fl"); 
		}
		$("#reg_box_id_3").hide();
		$("#reg_box_id_4").hide();
		$("#reg_box_id_5").hide();
		$("#reg_box_id_6").hide();
		$("#reg_box_id_7").show();
		$("#reg_box_id_8").hide();
	}
	function addPurList(){
		var dy1 = document.getElementById('dy1'); 
		var classname1 = dy1.className;
		if(classname1 != "new_step current fl"){
		dy1.setAttribute("class", "new_step current fl"); 
		}
		var dy2 = document.getElementById('dy2'); 
		var classname2 = dy2.className;
		if(classname2 != "new_step current fl"){
		dy2.setAttribute("class", "new_step current fl"); 
		}
		var dy3 = document.getElementById('dy3'); 
		var classname3 = dy3.className;
		if(classname3 != "new_step current fl"){
		dy3.setAttribute("class", "new_step current fl"); 
		}
		var dy4 = document.getElementById('dy4'); 
		var classname4 = dy4.className;
		if(classname4 != "new_step current fl"){
		dy4.setAttribute("class", "new_step current fl"); 
		}
	supplierRegist('reg_box_id', 5, 'next'); 
	}
	//显示隐藏树
	function typeShow(){
		 var expertsTypeId = $("#expertsTypeId").val();
		 if(expertsTypeId==1 || expertsTypeId=="1"){
			 $("#ztree").show();
			 getChildren();
		 }else{
			 $("#ztree").hide();
		 }
		
	}
	function four(){
		var sc1 = document.getElementById('sc1'); 
		var classname1 = sc1.className;
		if(classname1 != "new_step current fl"){
		sc1.setAttribute("class", "new_step current fl"); 
		}
		var sc2 = document.getElementById('sc2'); 
		var classname2 = sc2.className;
		if(classname2 != "new_step current fl"){
		sc2.setAttribute("class", "new_step current fl"); 
		}
		var sc3 = document.getElementById('sc3'); 
		var classname3 = sc3.className;
		if(classname3 != "new_step current fl"){
		sc3.setAttribute("class", "new_step current fl"); 
		}
		var sc4 = document.getElementById('sc4'); 
		var classname4 = sc4.className;
		if(classname4 != "new_step current fl"){
		sc4.setAttribute("class", "new_step current fl"); 
		}
		var sc5 = document.getElementById('sc5'); 
		var classname5 = sc5.className;
		if(classname5 != "new_step current fl"){
		sc5.setAttribute("class", "new_step current fl"); 
		}
		supplierRegist('reg_box_id', 6, 'next');
	}
	
	function showJiGou(){
		$("#thead").empty();
		var shengId = $("#addr2").val();
		var shiId = $("#add2").val();
		//采购机构
		var sup = $("#purchaseDepId").val();
		var purDepId="";
		var expertId="${expert.id }";
		if(expertId){
			$.ajax({
				url:'${pageContext.request.contextPath}/expert/getPurDepIdByExpertId.html',
				data:{"expertId":expertId},
				success:function(data){
					purDepId=data;
				}
			});
		}else{
			purDepId=sup;
		}
		
		$.ajax({
			url:'${pageContext.request.contextPath}/expert/showJiGou.html',
			data:{"pId":shengId,"zId":shiId},
			type:"post",
			dataType:'json',
			cache: false,
	        async: false,
			success:function(obj){
				$.each(obj,function(i,result){
					i=i+1;
					var name=result.name;
					var princinpal=result.princinpal;
					var detailAddr=result.detailAddr;
					var mobile = result.mobile;
					if(name==null)name="";
					if(princinpal==null)princinpal="";
					if(detailAddr==null)detailAddr="";
					if(mobile==null)mobile="";
					if(purDepId==result.id){
						$("#thead").append(
								"<tr align='center' ><td><input checked='checked' type='radio' name='purchaseDepId'  value='"+result.id+"' /></td>"+
								"<td>"+i+"</td>"+
								"<td>"+name+"</td>"+
								"<td>"+princinpal+"</td>"+
								"<td>"+detailAddr+"</td>"+
								"<td>"+mobile+"</td></tr>"
							);
					}else{
						$("#thead").append(
								"<tr align='center' ><td><input type='radio' name='purchaseDepId'  value='"+result.id+"' /></td>"+
								"<td>"+i+"</td>"+
								"<td>"+name+"</td>"+
								"<td>"+princinpal+"</td>"+
								"<td>"+detailAddr+"</td>"+
								"<td>"+mobile+"</td></tr>"
							);
					}
				});
			}
		});
	}
	
	var parentId ;
	var addressId="${expert.address}";
	window.onload=function(){
		//地区回显和数据显示
		 $.ajax({
		url : "${pageContext.request.contextPath}/area/find_by_id.do",
		data:{"id":addressId},
		success:function(obj){
			$.each(obj,function(i,result){
				if(addressId == result.id){
					parentId = result.parentId;
				$("#add").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
				$("#add2").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
				}else{
					$("#add").append("<option value='"+result.id+"'>"+result.name+"</option>");
					$("#add2").append("<option value='"+result.id+"'>"+result.name+"</option>");
				}
			});
		}
	}); 
		//地区
		$.ajax({
			url : "${pageContext.request.contextPath}/area/listByOne.do",
			success:function(obj){
				$.each(obj,function(i,result){
					 if(parentId == result.id){
						$("#addr").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
						$("#addr2").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
					}else{ 
					$("#addr").append("<option value='"+result.id+"'>"+result.name+"</option>");
					$("#addr2").append("<option value='"+result.id+"'>"+result.name+"</option>");
					}
				});
			}
		});
		validateBase();
		showJiGou();
	}
	
	//校验基本信息 不能为空的字段
	function validateForm1(){
		var relName = $("#relName").val();
		if(!relName){
			layer.msg("请输入姓名 !",{offset: ['222px', '390px']});
			return false;
		}
		var gender = $("#gender").val();
		if(!gender){
			layer.msg("请选择性别 !",{offset: ['222px', '390px']});
			return false;
		}
		var expertsFrom = $("#expertsFrom").val();
		if(!expertsFrom){
			layer.msg("请选择来源 !",{offset: ['222px', '390px']});
			return false;
		}
		
		var nation = $("#nation").val();
		if(!nation){
			layer.msg("请填写民族 !",{offset: ['222px', '390px']});
			return false;
		}
		var graduateSchool = $("#graduateSchool").val();
		if(!graduateSchool){
			layer.msg("请填写毕业院校 !",{offset: ['222px', '390px']});
			return false;
		}
		var hightEducation = $("#hightEducation").val();
		if(!hightEducation){
			layer.msg("请选择最高学历!",{offset: ['222px', '390px']});
			return false;
		}
		
		var major = $("#major").val();
		if(!major){
			layer.msg("请填写专业!",{offset: ['222px', '390px']});
			return false;
		}
		
		var unitAddress = $("#unitAddress").val();
		if(!unitAddress){
			layer.msg("请填写单位地址!",{offset: ['222px', '390px']});
			return false;
		}
		var telephone = $("#telephone").val();
		if(!telephone){
			layer.msg("请填写联系电话!",{offset: ['222px', '390px']});
			return false;
		}
		
		var mobile = $("#mobile").val();
		if(!mobile){
			layer.msg("请填写手机号!",{offset: ['222px', '390px']});
			return false;
		}
		
		var healthState = $("#healthState").val();
		if(!healthState){
			layer.msg("请填写健康状态!",{offset: ['222px', '390px']});
			return false;
		}
		
		var idType = $("#idType").val();
		if(!idType){
			layer.msg("请选择证件类型 !",{offset: ['222px', '390px']});
			return false;
		}
		var idNumber = $("#idNumber").val();
		if(!idNumber){
			layer.msg("请填写证件号码 !",{offset: ['222px', '390px']});
			return false;
		}
		var id_areaSelect = $("#add").val();
		if(!id_areaSelect){
			layer.msg("请选择区域 !",{offset: ['222px', '390px']});
			return false;
		}
		var sysId = $("#sysId").val();
		var flag;
		$.ajax({
			url:'${pageContext.request.contextPath}/expert/findAttachment.html',
			data:{"sysId":sysId},
			dataType:"json",
			cache: false,
	        async: false,
			success:function(data){
				if(data.length<5){
					layer.msg("还有附件未上传!",{offset: ['222px', '390px']});
					flag=false;
				}else{
					flag=true;
				}
			}
		});
		return flag;
	}
	//判断申请表  合同书
	function validateHeTong(){
		var flag;
		var sysId = $("#sysId").val();
		$.ajax({
			url:'${pageContext.request.contextPath}/expert/findAttachment.html',
			data:{"sysId":sysId},
			dataType:"json",
			cache: false,
	        async: false,
			success:function(data){
				if(data.length<7){
					layer.msg("还有附件未上传!",{offset: ['222px', '390px']});
					flag=false;
				}else{
					flag=true;
				}
			}
		});
		return flag;
	}
</script>
</head>
<body>
 <form id="form1" action="${pageContext.request.contextPath}/expert/add.html" method="post">
  <input type="hidden" name="userId" value="${user.id}"/>
  <input type="hidden" id="purchaseDepId" value="${expert.purchaseDepId}"/>
  <input type="hidden" name="id" id="id" value="${expert.id}"/>
  <input type="hidden" name="zancun" id="zancun" value=""/>
  <input type="hidden" name="sysId" id="sysId" value="${sysId}"/>
  <input type="hidden" value="${errorMap.realName}" id="error1">
  <input type="hidden" value="${errorMap.nation}" id="error2">
  <input type="hidden" value="${errorMap.gender}" id="error3">
  <input type="hidden" value="${errorMap.idType}" id="error4">
  <input type="hidden" value="${errorMap.idNumber}" id="error5">
  <input type="hidden" value="${errorMap.address}" id="error6">
  <input type="hidden" value="${errorMap.hightEducation}" id="error7">
  <input type="hidden" value="${errorMap.graduateSchool}" id="error8">
  <input type="hidden" value="${errorMap.major}" id="error9">
  <input type="hidden" value="${errorMap.expertsFrom}" id="error10">
  <input type="hidden" value="${errorMap.unitAddress}" id="error11">
  <input type="hidden" value="${errorMap.telephone}" id="error12">
  <input type="hidden" value="${errorMap.mobile}" id="error13">
  <input type="hidden" value="${errorMap.healthState}" id="error14">
  <input type="hidden" value="${errorMap.mobile2}" id="error15">
  <input type="hidden" value="${errorMap.idNumber2}" id="error16">
  <input type="hidden" id="categoryId" name="categoryId" value=""/>
  <input type="hidden"  name="token2" value="<%=tokenValue%>"/>
    <div id="reg_box_id_3" class="container clear margin-top-30 job-content">
	  <h2 class="step_flow">
	    <span id="sp1" class="new_step current fl" onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
	    <span id="sp2" class="new_step <c:if test="${expert.expertsTypeId != null}">current</c:if> fl"   onclick='tab2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
	    <span id="sp3" class="new_step <c:if test="${expert.purchaseDepId != null}">current</c:if> fl" onclick='tab3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
	    <span id="sp4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab4()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
	    <span id="sp5" class="new_step new_step_last <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab5()'><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
	    <div class="clear"></div>
	  </h2>
	<div class="container container_box">
	  <div class="tab-pane fade active in"  id="tab-1">
	    <div>
		  <h2 class="count_flow"><i>1</i>专家基本信息</h2>
			<ul class="ul_list">
				<li class="col-md-3 col-sm-6 col-xs-12 pl15">
				    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 专家姓名</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
						<input id="relName" name="relName" value="${expert.relName}"   type="text"/>
					    <span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
				    <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 出生日期</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0 input_group col-sm-12 col-xs-12 col-md-12 p0">
 					  <input   readonly="readonly" value="<fmt:formatDate type='date' value='${expert.birthday}' dateStyle='default' pattern='yyyy-MM-dd'/>" name="birthday" id="birthday" type="text" onclick='WdatePicker()'/>
					  <span class="add-on">i</span>
					</div>
				</li>
                <li class="col-md-3 col-xs-12 col-sm-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>民族</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0 input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <input maxlength="10" value="${expert.nation}"  name="nation" id="nation" type="text"/>
                    <span class="add-on">i</span>
                    </div>
                </li>
                <li class="col-md-3 col-xs-12 col-sm-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 性别</span>
                    <div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
                       <select name="gender" id="gender">
                          <option selected="selected" value="">-请选择-</option>
                          <c:forEach items="${sexList }" var="sex" varStatus="vs">
                            <option <c:if test="${expert.gender eq sex.id}">selected="selected"</c:if> value="${sex.id}">${sex.name}</option>
                          </c:forEach>
                        </select>
                    </div>
                </li> 
                <li class="col-md-3 col-xs-12 col-sm-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i> 证件类型</span>
                    <div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
                    <select  name="idType" id="idType">
                    <option selected="selected" value="">-请选择-</option>
                    <c:forEach items="${idTypeList }" var="idType" varStatus="vs">
                      <option <c:if test="${expert.idType eq idType.id }">selected="selected"</c:if> value="${idType.id}">${idType.name}</option>
                    </c:forEach>
                   </select>
                    </div>
                </li>
				<li class="col-md-3 col-xs-12 col-sm-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>证件号码</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0 ">
						<input maxlength="30" value="${expert.idNumber}"  name="idNumber" id="idNumber" type="text"/>
  						<span class="add-on">i</span>
  					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
				   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>省</span>
                   <div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
                    <select id="addr" onchange="func();">
                           <option value="">-请选择-</option>
                    </select>
                   </div>
                </li>
                <li class="col-md-3 col-xs-12 col-sm-6">
                	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>市</span>
                    <div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
                     <select  name="address" id="add" onchange="copySel()">
                            <option value="">-请选择-</option>
                     </select>
                    </div>
                </li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">政治面貌</span>
                    <div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
                        <select  name="politicsStatus" id="politicsStatus">
                        <option selected="selected" value="">-请选择-</option>
                        <c:forEach items="${zzList }" var="zz" varStatus="vs">
                          <option <c:if test="${expert.politicsStatus eq zz.id }">selected="selected"</c:if> value="${zz.id}">${zz.name}</option>
                        </c:forEach>
                       </select>
                    </div>
                </li>
                <li class="col-md-3 col-xs-12 col-sm-6">
                	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>最高学历</span>
                    <div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
                     <select  name="hightEducation" id="hightEducation" >
                        <option selected="selected" value="">-请选择-</option>
                        <c:forEach items="${xlList}" var="xl" varStatus="vs">
                        <option <c:if test="${expert.hightEducation eq xl.id }">selected="selected"</c:if> value="${xl.id}">${xl.name}</option>
                        </c:forEach>
                      </select>
                    </div>
                </li>
                <li class="col-md-3 col-xs-12 col-sm-6">
                	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>毕业院校</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0 input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <input maxlength="40" value="${expert.graduateSchool}"  name="graduateSchool" id="graduateSchool" type="text"/>
                    <span class="add-on">i</span>
                    </div>
                </li>
                <li class="col-md-3 col-xs-12 col-sm-6">
                	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>专业</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <input maxlength="20" value="${expert.major}"  name="major" id="major" type="text"/>
                    <span class="add-on">i</span>
                    </div>
                </li>
                <li class="col-md-3 col-xs-12 col-sm-6">
                	<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 学位</span>
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                    <input maxlength="10" value="${expert.degree}"  name="degree" id="degree" type="text"/>
                    <span class="add-on">i</span>
                    </div>
                </li>
				<li class="col-md-3 margin-0 padding-0">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>专家来源</span>
					<div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
						<select  name="expertsFrom" id="expertsFrom">
						<option selected="selected" value="">-请选择-</option>
						<c:forEach items="${lyTypeList }" var="ly" varStatus="vs"> 
					     	<option <c:if test="${expert.expertsFrom eq ly.id }">selected="selected"</c:if> value="${ly.id}">${ly.name}</option>
						</c:forEach>
					   </select>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 专家技术职称</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input maxlength="20" value="${expert.professTechTitles}"  name="professTechTitles" id="professTechTitles" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 参加工作时间</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input   readonly="readonly" value="<fmt:formatDate type='date' value='${expert.timeToWork}' dateStyle='default' pattern='yyyy-MM-dd'/>" name="timeToWork"  type="text" onclick='WdatePicker()'/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 从事专业起始年度</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					 <input value="<fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle='default' pattern='yyyy-MM-dd'/>"  readonly="readonly" name="timeStartWork" id="timeStartWork" type="text" onclick='WdatePicker()'/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">工作单位</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input maxlength="40" value="${expert.workUnit}"  name="workUnit" id="workUnit" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>单位地址</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0" >
					 <input maxlength="40" value="${expert.unitAddress}"  name="unitAddress" id="unitAddress" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>联系电话（固话）</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input maxlength="15" value="${expert.telephone}"  name="telephone" id="telephone" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>手机</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input maxlength="15" value="${expert.mobile}"  name="mobile" id="mobile" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 传真</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input maxlength="10"  value="${expert.fax}"  name="fax" id="fax" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li> 
  				<li class="col-md-3 col-xs-12 col-sm-6">
  					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 邮编</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input maxlength="6" value="${expert.postCode}"  name="postCode" id="postCode" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 获得技术时间</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input value="<fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM-dd'/>"  readonly="readonly" name="makeTechDate" id="makeTechDate" type="text" onclick='WdatePicker()'/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">*</i>健康状态</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input maxlength="10" value="${expert.healthState}"  name="healthState" id="healthState" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
				<li class="col-md-3 col-xs-12 col-sm-6">
					<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"> 现任职务</span>
					<div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
					<input maxlength="10" value="${expert.atDuty}"  name="atDuty" id="appendedInput" type="text"/>
					<span class="add-on">i</span>
					</div>
				</li>
			</ul>
			  </div>
			  <!-- 附件信息-->
			  <div class="padding-top-10 clear">
			    <h2 class="count_flow"><i>2</i>上传附件</h2>
			    <ul class="ul_list">
				 <table class="table table-bordered">
				  <tbody>
				  	<tr>
				  	   <td class="bggrey" width="15%"><i class="red">*</i>身份证</td>
				  	   <td class="">
				  	      <u:upload id="expert1"  groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}"   auto="true"/>
				          <u:show showId="show1"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}"/>
				  	   </td>
				  	   <td class="bggrey" width="15%"><i class="red">*</i>学历证书</td>
				  	   <td class="">
				  	      <u:upload id="expert2" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId}" sysKey="${expertKey}"  typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}" auto="true"/>
				          <u:show showId="show2"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey }" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}"/>
				  	   </td>
				  	</tr>
				  	<tr>
				  	   <td class="bggrey"><i class="red">*</i>职称证书</td>
				  	   <td>
				  	      <u:upload id="expert3" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId}" sysKey="${expertKey}"  typeId="${typeMap.EXPERT_TITLE_TYPEID}" auto="true"/>
				          <u:show  showId="show3"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}"/>
				  	   </td>
				  	   <td class="bggrey"><i class="red">*</i>学位证书</td>
				  	   <td>
				  	      <u:upload id="expert4" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId}" sysKey="${expertKey}"   typeId="${typeMap.EXPERT_DEGREE_TYPEID}" auto="true"/>
				          <u:show showId="show4" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}"  typeId="${typeMap.EXPERT_DEGREE_TYPEID}"/>
				  	   </td>
				  	</tr>
				  	<tr>
				  	   <td class="bggrey" ><i class="red">*</i>个人照片</td>
				  	   <td>
				  	      <u:upload id="expert5" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId}" sysKey="${expertKey}"  typeId="${typeMap.EXPERT_PHOTO_TYPEID}" auto="true"/>
				          <u:show showId="show5" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}"/>
				  	   </td>
				  	</tr>
				  	</tbody>
				  </table>
			    </ul>
			   </div>
				<div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12 ">
			        <button class="btn btn-windows git" onclick='submitForm1()'  type="button">暂存</button>
					<button class="btn btn-windows git" id="nextBind"  type="button" onclick='fun()' >下一步</button>
				</div>
				  </div>
					</div>
					  </div>
		<div id="reg_box_id_4" class="container clear margin-top-30 yinc" style="display: none;">
	  		<h2 class="step_flow">
				<span id="ty1" class="new_step current fl"  onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
				<span id="ty2" class="new_step <c:if test="${expert.expertsTypeId != null}">current</c:if> fl"   onclick='tab2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
				<span id="ty3" class="new_step <c:if test="${expert.purchaseDepId != null}">current</c:if> fl" onclick='tab3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
				<span id="ty4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab4()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
				<span id="ty5" class="new_step new_step_last <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab5()'><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
			<div class="container container_box">
			<div class="headline-v2">
                 <h2>专家类型</h2>
            </div>
			<div>
			  <ul class="ul_list" >
     		    <li class="col-md-3 col-xs-12 col-sm-6 pl15" >
			      <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">
			      <span class="star_red fl">*</span>专家类型</span>
			      <input type="hidden" id="expertsTypeIds" value="" >
			      <div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
			        <select name="expertsTypeId" id="expertsTypeId" onchange="typeShow();">
			   		  <option value="">-请选择-</option>
			   		  <option <c:if test="${expert.expertsTypeId == '1' }">selected="true"</c:if> value="1">技术</option>
			   		  <option <c:if test="${expert.expertsTypeId == '2' }">selected="true"</c:if> value="2">法律</option>
			   		  <option <c:if test="${expert.expertsTypeId == '3' }">selected="true"</c:if> value="3">商务</option>
			        </select>
			      </div>
			    </li>
   			 </ul>
   			 <div class="" id="ztree" >
   			   <div class="sevice_list col-md-12 col-xs-12 col-sm-12 container" class="dnone" >
			    <div class="col-md-5 col-sm-6 col-xs-12 title"><span class="star_red fl">*</span>产品服务/分类：</div>
				  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
					  <c:forEach items="${spList}" var="obj" >
						    <span><input type="checkbox" name="chkItem" onclick="getChildren()" value="${obj.code}" />${obj.name} </span>
				      </c:forEach>
				  </div>
			    </div>
				<div class="sevice_list col-md-12 col-xs-12 col-sm-12 container" id="hwType">
				  <div class="col-md-5 col-sm-6 col-xs-12 title"><span class="star_red fl">*</span>货物分类：</div>
				  <div class="col-md-7 col-sm-6 col-xs-12 service_list">
					  <c:forEach items="${hwList}" var="hw" >
						    <span><input type="checkbox" name="chkItem" onclick="getChildren()"  value="${hw.code}" />${hw.name} </span>
				      </c:forEach>
				  </div>
				</div>
				</div>
   			   
		    <div class="tc mt20 clear col-md-12 col-xs-12 col-sm-12">
				<button class="btn btn-windows back"  type="button" onclick="pre('reg_box_id', 4, 'pre')">上一步</button>
				<button class="btn btn-windows git" onclick='submitForm1()'  type="button">暂存</button>
				<button class="btn btn-windows git"  type="button" onclick='fun1()'>下一步</button>
			</div>
		</div>
		</div>
		</div>
		<!-- 项目戳开始 -->
		<div id="reg_box_id_5" class="container clear margin-top-30 yinc" style="display: none;">
		  		<h2 class="step_flow">
					<span id="jg1" class="new_step current fl"  onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span id="jg2" class="new_step <c:if test="${expert.expertsTypeId != null}">current</c:if> fl"   onclick='tab2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
					<span id="jg3" class="new_step <c:if test="${expert.purchaseDepId != null}">current</c:if> fl" onclick='tab3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span id="jg4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab4()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
					<span id="jg5" class="new_step new_step_last <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab5()'><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
			<div class="container container_box">
		      <div class="headline-v2">
			     <h2>选择采购机构</h2>
			   </div>  
			<table id="tb1"  class="table table-bordered table-condensed table-hover table-striped">
			  <ul class="ul_list">
				<li class="col-md-3 col-xs-12 col-sm-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">省</span>
	                <div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
	                 <select id="addr2" onchange="func2();">
	                    <option value="">-请选择-</option>
	                 </select>
	                </div>
	            </li>
	            <li class="col-md-3 col-xs-12 col-sm-6"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">市</span>
	                <div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
	                 <select  name="address2" id="add2" onchange="showJiGou()">
	                    <option value="">-请选择-</option>
	                 </select>
	                </div>
	            </li>
              </ul>
            </table>
            <table class="table table-bordered table-condensed table-hover table-striped">
				<thead>
					<tr>
					  <th class="info w30"><input type="radio"   disabled="disabled"  id="purchaseDepId2" ></th>
					  <th class="info w50">序号</th>
					  <th class="info">采购机构</th>
					  <th class="info">联系人</th>
					  <th class="info">联系地址</th>
					  <th class="info">联系电话</th>
					</tr>
				</thead>
				<tbody id="thead"></tbody>
			</table>
			<h6>
		               友情提示：请专家记录好初审采购机构的相关信息，以便进行及时沟通
		    </h6>
		    <div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">
				<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 5, 'pre')">上一步</button>
				<button class="btn btn-windows git" onclick='submitForm1()'  type="button">暂存</button>
				<button class="btn btn-windows git"   type="button" onclick='addPurList()'>下一步</button>
			</div>
		</div>
	  </div>
	<div id="reg_box_id_6" class="container clear margin-top-30 yinc" style="display: none;">
		  <h2 class="step_flow">
			<span id="dy1" class="new_step current fl" onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
			<span id="dy2" class="new_step current fl" onclick='tab2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
			<span id="dy3" class="new_step current fl" onclick='tab3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
			<span id="dy4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab4()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
			<span id="dy5" class="new_step new_step_last <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab5()'><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
			<div class="clear"></div>
		  </h2>
<div class="tab-content padding-top-20">
  <div class="headline-v2">
	 <h2>打印专家申请表</h2>
  </div>  
<div><br/><br/>
  <table class="table table-bordered">
    <div align="left">
      <a class="btn btn-windows git" onclick='downloadTable()' href="javascript:void(0)">下载</a>
    </div><br/>
   	<tr>
 	  <td width="25%" class="bggrey">姓名</td>
 	  <td width="25%" id="tName"></td>
 	  <td width="25%" class="bggrey">性别</td>
      <td width="25%" id="tSex" ></td>
   	</tr>
   <tr>
	 <td width="25%" class="bggrey">出生日期</td>
	 <td width="25%" id="tBirthday"></td>
	 <td width="25%" class="bggrey">政治面貌</td>
	 <td width="25%"  id="tFace"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">所在地区</td>
	 <td width="25%" id="Taddress"></td>
	 <td width="25%" class="bggrey">职称</td>
	 <td width="25%" id="tHey" ></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">证件号码<td>
	 <td id="tNumber" colspan="3"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">从事专业类别</td>
	 <td width="25%" id="tExpertsTypeId"></td>
	 <td width="25%" class="bggrey">从事年限</td>
	 <td width="25%" id="tTimeStartWork"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">最高学历</td>
	 <td width="25%" id="tHight"></td>
	 <td width="25%" class="bggrey">最高学位</td>
	 <td width="25%" id="tWei"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">执业资格1</td>
	 <td width="25%" > </td>
	 <td width="25%" class="bggrey">注册证书编号1</td>
	 <td width="25%" > </td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">执业资格2</td>
	 <td width="25%" ></td>
	 <td width="25%" class="bggrey">注册证书编号2</td>
	 <td width="25%"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">执业资格3</td>
	 <td width="25%"></td>
	 <td width="25%" class="bggrey">注册证书编号3</td>
	 <td width="25%" ></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">近两年是否接受过评标业务培训</td>
	 <td width="25%"></td>
	 <td width="25%" class="bggrey">是否愿意成为应急专家</td>
	 <td width="25%"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">所属行业</td>
	 <td width="25%"></td>
	 <td width="25%" class="bggrey">报送部门</td>
	 <td width="25%"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">手机号码</td>
	 <td width="25%" id="tMobile"></td>
	 <td width="25%" class="bggrey">单位电话</td>
	 <td width="25%" id="tTelephone"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">住宅电话</td>
	 <td width="25%"></td>
	 <td width="25%" class="bggrey">电子邮箱</td>
	 <td width="25%"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">毕业院校及专业</td>
	 <td id="tGraduateSchool" colspan="3"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">单位名称</td>
	 <td id="tWorkUnit" colspan="3"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">单位地址 </td>
	 <td width="25%" id="tUnitAddress"></td>
	 <td width="25%" class="bggrey">单位邮编</td>
	 <td width="25%" id="tPostCode"></td>
   </tr>
   <tr>
	 <td width="25%" class="bggrey">家庭地址 </td>
	 <td width="25%"></td>
	 <td width="25%" class="bggrey">家庭邮编</td>
	 <td width="25%"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业一</td>
   	 <td width="25%" colspan="3"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业二</td>
   	 <td colspan="3"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业三</td>
   	 <td colspan="3"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业四</td>
   	 <td colspan="3"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业五</td>
   	 <td colspan="3"></td>
   </tr>
   <tr>
   	 <td width="25%" class="bggrey">评标专业六</td>
   	 <td colspan="3"></td>
   </tr>
   <tr>
   	 <td  style="font-size: 20px; font-weight: bold;" class="bggrey" align="center" colspan="4">工作经历</td>
   </tr>
   <tr>
	 <td class="bggrey" align="center" style="font-weight: bold;">起止年月</td>
	 <td class="bggrey" colspan="2" align="center" style="font-weight: bold;">单位及职务</td>
	 <td class="bggrey" align="center" style="font-weight: bold;">证明人</td>
   </tr>
   <tr>
	 <td align="center">至 </td>
	 <td align="center" colspan="2"> </td>
	 <td align="center" > </td>
   </tr>
   <tr>
	 <td align="center"> 至</td>
	 <td align="center" colspan="2"> </td>
	 <td align="center" > </td>
   </tr>
   <tr>
	 <td align="center">至 </td>
	 <td align="center" colspan="2"> </td>
	 <td align="center" > </td>
   </tr>
   <tr>
	 <td align="center">至 </td>
	 <td align="center" colspan="2"> </td>
	 <td align="center" > </td>
   </tr>
   <tr>
     <td align="center">至 </td>
	 <td align="center" colspan="2"> </td>
	 <td align="center"> </td>
   </tr>
 </table>
    <div class="tc mt20 clear col-md-11">
   		<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 6, 'pre')">上一步</button>
		<button class="btn btn-windows git"   type="button" onclick='four()'>下一步</button>
	</div>
	  </div>
	    </div>
	      </div>
		<div id="reg_box_id_7" class="container clear margin-top-30 yinc" style="display: none;">
		   <h2 class="step_flow">
			 <span id="sc1" class="new_step current fl" onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
			 <span id="sc2" class="new_step current fl" onclick='tab2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
			 <span id="sc3" class="new_step current fl" onclick='tab3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
			 <span id="sc4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab4()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
			 <span id="sc5" class="new_step new_step_last <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab5()'><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
			 <div class="clear"></div>
		  </h2>
		<div class="tab-content padding-top-20">
			<div class="headline-v2">
			  <h2>专家申请表、合同书</h2>
			</div>  
	   	  <table class="table table-bordered">
	   	    <tr>
	   	      <td class="bggrey" width="15%"><i class="red">*</i>专家申请表：</td>
	   	      <td class="">
	   	        <up:upload id="expert6"  groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_APPLICATION_TYPEID }" auto="true"/>
			    <up:show showId="show6"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_APPLICATION_TYPEID }"/>
	   	      </td>
	   	      <td class="bggrey" width="15%" ><i class="red">*</i>专家合同书：</td>
	   	      <td class="">
	   	        <up:upload id="expert7" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_CONTRACT_TYPEID }" auto="true"/>
			    <up:show showId="show7"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_CONTRACT_TYPEID }"/>
	   	      </td>
	   	    </tr>
		 </table>
		    <div class="tc mt20 clear col-md-11">
		   		<button class="btn"   type="button" onclick="pre('reg_box_id', 7, 'pre')">上一步</button>
				<input  class="btn" type="button" onclick="addSubmitForm()" value="提交" />
			</div>
		</div>
	</form>
</body>
</html>
