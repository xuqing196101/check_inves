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
%>
<script type="text/javascript">
	    var treeObj;
		var datas;
		
		
		function func(){
			var parentId = $("#addr").val();
			$.ajax({
				url : "${pageContext.request.contextPath}/area/find_by_parent_id.do",
				data:{"id":parentId}, 
				success:function(obj){
					$("#add").empty();
					//var data = eval('(' + obj + ')');
					$("#add").append("<option  value=''>-请选择-</option>");
					$.each(obj,function(i,result){
						$("#add").append("<option value='"+result.id+"'>"+result.name+"</option>");
					});
				}
			});
		}
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
		}
		   var setting={
					async:{
								enable:true,
								url:"${pageContext.request.contextPath}/category/createtree.do",
								autoParam:["id", "name=n", "level=lv"],  
					            otherParam:{"otherParam":"zTreeAsyncTest"},  
					            dataFilter: filter,  
								dataType:"json",
								type:"post"
							},
							callback:{
						    	onClick:zTreeOnClick,//点击节点触发的事件
						    	//onAsyncSuccess: zTreeOnAsyncSuccess
						    	beforeAsync: beforeAsync,  
				                onAsyncSuccess: onAsyncSuccess  
						    }, 
							data:{
								keep:{
									parent:true
								},					
								simpleData:{
									enable:true,
									idKey:"id",
									pIdKey:"pId",
									rootPId:0
								}
						    },
						   check:{
								enable: true,
								chkStyle:"checkbox"
						   }
			  };
	var listId;
	$(function(){
		var parentId ;
		var addressId="${expert.address}";
		//地区回显和数据显示
		 $.ajax({
		url : "${pageContext.request.contextPath}/area/find_by_id.do",
		data:{"id":addressId},
		success:function(obj){
			//var data = eval('(' + obj + ')');
			$.each(obj,function(i,result){
				if(addressId == result.id){
					//alert(JSON.stringify(result));
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
				//var data = eval('(' + obj + ')');
				//alert(data);
				//alert(parentId);
				$.each(obj,function(i,result){
					 if(parentId == result.id){
						$("#addr").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
						$("#addr2").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
					}else{ 
					$("#addr").append("<option value='"+result.id+"'>"+result.name+"</option>");
					$("#addr2").append("<option value='"+result.id+"'>"+result.name+"</option>");
					}
				});
			},
			error:function(obj){
			}
		});
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
				  url:"${pageContext.request.contextPath}/expert/getCategoryByExpertId.do?expertId="+id,
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
				//延迟加载 
		         setTimeout(function(){
		        	 expandAll("ztree");
		        	 },500);
					 $("#ztree").show();
				 }else{
					 treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
					 $("#ztree").hide();
				 }
			}); 
	function filter(treeId, parentNode, childNodes) {  
        if (!childNodes) {return null;}  
        for (var i=0, l=childNodes.length; i<l; i++) {  
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');  
        }  
        return childNodes;  
    }  
    function beforeAsync(){  
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
    var curStatus = "init"; var curAsyncCount = 0; var goAsync = false;  
    function expandAll(){  
        if (!check()){  
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
    function check(){  
        if (curAsyncCount > 0) {  
            return false;  
        }  
        return true;  
    }  
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
     var nodes = treeNode.children;
     for(var i=0;i<nodes.length;i++){
         treeObj.expandNode(nodes[i],true,true,true);
     }
 }
	var treeid=null;
    /*树点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id;
		
    }
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
				  alert("出错啦！");
			  }
		});
	}
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
				  alert("出错啦！");
			  }
		});
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
	}
	function fun(){
		var ty1 = document.getElementById('ty1'); 
		var classname1 = ty1.className;
		//alert(classname1);
		if(classname1 != "new_step current fl"){
		ty1.setAttribute("class", "new_step current fl"); 
		}
		var ty2 = document.getElementById('ty2'); 
		var classname2 = ty2.className;
		//alert(classname1);
		if(classname2 != "new_step current fl"){
		ty2.setAttribute("class", "new_step current fl"); 
		}
		supplierRegist('reg_box_id', 3, 'next'); 
		editTable();
	}
	function fun1(){
		var jg1 = document.getElementById('jg1'); 
		var classname1 = jg1.className;
		//alert(classname1);
		if(classname1 != "new_step current fl"){
		jg1.setAttribute("class", "new_step current fl"); 
		}
		var jg2 = document.getElementById('jg2'); 
		var classname2 = jg2.className;
		//alert(classname1);
		if(classname2 != "new_step current fl"){
		jg2.setAttribute("class", "new_step current fl"); 
		}
		var jg3 = document.getElementById('jg3'); 
		var classname3 = jg3.className;
		//alert(classname1);
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
		//alert(classname1);
		if(classname1 != "new_step current fl"){
		ty1.setAttribute("class", "new_step current fl"); 
		}
		var ty2 = document.getElementById('ty2'); 
		var classname2 = ty2.className;
		//alert(classname1);
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
		//alert(classname1);
		if(classname1 != "new_step current fl"){
		jg1.setAttribute("class", "new_step current fl"); 
		}
		var jg2 = document.getElementById('jg2'); 
		var classname2 = jg2.className;
		//alert(classname1);
		if(classname2 != "new_step current fl"){
		jg2.setAttribute("class", "new_step current fl"); 
		}
		var jg3 = document.getElementById('jg3'); 
		var classname3 = jg3.className;
		//alert(classname1);
		if(classname3 != "new_step current fl"){
		jg3.setAttribute("class", "new_step current fl"); 
		}
		
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
		//alert(classname1);
		if(classname1 != "new_step current fl"){
		dy1.setAttribute("class", "new_step current fl"); 
		}
		var dy2 = document.getElementById('dy2'); 
		var classname2 = dy2.className;
		//alert(classname1);
		if(classname2 != "new_step current fl"){
		dy2.setAttribute("class", "new_step current fl"); 
		}
		var dy3 = document.getElementById('dy3'); 
		var classname3 = dy3.className;
		//alert(classname1);
		if(classname3 != "new_step current fl"){
		dy3.setAttribute("class", "new_step current fl"); 
		}
		var dy4 = document.getElementById('dy4'); 
		var classname4 = dy4.className;
		//alert(classname1);
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
		//alert(classname1);
		if(classname1 != "new_step current fl"){
		sc1.setAttribute("class", "new_step current fl"); 
		}
		var sc2 = document.getElementById('sc2'); 
		var classname2 = sc2.className;
		//alert(classname1);
		if(classname2 != "new_step current fl"){
		sc2.setAttribute("class", "new_step current fl"); 
		}
		var sc3 = document.getElementById('sc3'); 
		var classname3 = sc3.className;
		//alert(classname1);
		if(classname3 != "new_step current fl"){
		sc3.setAttribute("class", "new_step current fl"); 
		}
		var sc4 = document.getElementById('sc4'); 
		var classname4 = sc4.className;
		//alert(classname1);
		if(classname4 != "new_step current fl"){
		sc4.setAttribute("class", "new_step current fl"); 
		}
		var sc5 = document.getElementById('sc5'); 
		var classname5 = sc5.className;
		//alert(classname1);
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
	//回显采购机构信息
	function addPurList(){
	/* var radio = $("input[type='radio']:checked");
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
	$("#phone_2").text(phone); */
		var dy1 = document.getElementById('dy1'); 
		var classname1 = dy1.className;
		//alert(classname1);
		if(classname1 != "new_step current fl"){
		dy1.setAttribute("class", "new_step current fl"); 
		}
		var dy2 = document.getElementById('dy2'); 
		var classname2 = dy2.className;
		//alert(classname1);
		if(classname2 != "new_step current fl"){
		dy2.setAttribute("class", "new_step current fl"); 
		}
		var dy3 = document.getElementById('dy3'); 
		var classname3 = dy3.className;
		//alert(classname1);
		if(classname3 != "new_step current fl"){
		dy3.setAttribute("class", "new_step current fl"); 
		}
		var dy4 = document.getElementById('dy4'); 
		var classname4 = dy4.className;
		//alert(classname1);
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
		 }else{
			 $("#ztree").hide();
		 }
		
	}
	function four(){
		var sc1 = document.getElementById('sc1'); 
		var classname1 = sc1.className;
		//alert(classname1);
		if(classname1 != "new_step current fl"){
		sc1.setAttribute("class", "new_step current fl"); 
		}
		var sc2 = document.getElementById('sc2'); 
		var classname2 = sc2.className;
		//alert(classname1);
		if(classname2 != "new_step current fl"){
		sc2.setAttribute("class", "new_step current fl"); 
		}
		var sc3 = document.getElementById('sc3'); 
		var classname3 = sc3.className;
		//alert(classname1);
		if(classname3 != "new_step current fl"){
		sc3.setAttribute("class", "new_step current fl"); 
		}
		var sc4 = document.getElementById('sc4'); 
		var classname4 = sc4.className;
		//alert(classname1);
		if(classname4 != "new_step current fl"){
		sc4.setAttribute("class", "new_step current fl"); 
		}
		var sc5 = document.getElementById('sc5'); 
		var classname5 = sc5.className;
		//alert(classname1);
		if(classname5 != "new_step current fl"){
		sc5.setAttribute("class", "new_step current fl"); 
		}
		supplierRegist('reg_box_id', 6, 'next');
	}
</script>
</head>
<body>
		<form id="form1" action="${pageContext.request.contextPath}/expert/add.html" method="post">
		<input type="hidden" name="userId" value="${user.id }"/>
		<input type="hidden" id="purchaseDepId" value="${expert.purchaseDepId }"/>
		<input type="hidden" name="id" id="id" value="${expert.id }"/>
		<input type="hidden" name="zancun" id="zancun"/>
		<input type="hidden" name="sysId" value="${sysId }"/>
		<%
			session.setAttribute("tokenSession", tokenValue);
		%>
		<input type="hidden" id="categoryId" name="categoryId" value=""/>
		 <input type="hidden"  name="token2" value="<%=tokenValue%>"/>
		<div id="reg_box_id_3" class="container clear margin-top-30 job-content">
			<h2 class="padding-20 mt40">
					<!-- <span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> -->
					<span id="sp1" class="new_step current fl" onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span id="sp2" class="new_step <c:if test="${expert.expertsTypeId != null}">current</c:if> fl"   onclick='tab2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
					<span id="sp3" class="new_step <c:if test="${expert.purchaseDepId != null}">current</c:if> fl" onclick='tab3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span id="sp4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab4()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
					<span id="sp5" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab5()'><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
		<div class="container container_box">
							<div class="tab-pane fade active in height-500"  id="tab-1">
								<div>
								    <h2 class="count_flow"><i>1</i>专家基本信息</h2>
									<ul class="ul_list">
										<li class="col-md-3 margin-0 padding-0"><span class="col-md-12 padding-left-5"><i class="red">*</i> 专家姓名</span>
											<div class="input-append">
												<input class="span5" id="relName" name="relName" value="${expert.relName }"   type="text"/>
											    <span class="add-on">i</span>
											</div>
										</li>
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 出生日期</span>
											<div class="input-append">
       											 <input class="span5"   readonly="readonly" value="<fmt:formatDate type='date' value='${expert.birthday }' dateStyle='default' pattern='yyyy-MM-dd'/>" name="birthday" id="birthday" type="text" onclick='WdatePicker()'/>
      										<span class="add-on">i</span>
      										</div>
										</li>
										
                                        <li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>民族</span>
                                            <div class="input-append">
                                            <input class="span5" maxlength="10" value="${expert.nation }"  name="nation" id="nation" type="text"/>
                                            <span class="add-on">i</span>
                                            </div>
                                        </li>
                                        
                                        <li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i> 性别</span>
                                            <div class="select_common">
                                                 <select class="span5" name="gender" id="gender">
                                                    <option selected="selected" value="">-请选择-</option>
                                                    <c:forEach items="${sexList }" var="sex" varStatus="vs">
                                                      <option <c:if test="${expert.gender eq sex.id }">selected="selected"</c:if> value="${sex.id }">${sex.name }</option>
                                                    </c:forEach>
                                                  </select>
                                                  <!-- <span class="add-on">i</span> -->
                                            </div>
                                        </li> 
                                        <li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i> 证件类型</span>
                                            <div class="select_common">
                                            <select  name="idType" id="idType">
                                            <option selected="selected" value="">-请选择-</option>
                                            <c:forEach items="${idTypeList }" var="idType" varStatus="vs">
                                              <option <c:if test="${expert.idType eq idType.id }">selected="selected"</c:if> value="${idType.id }">${idType.name }</option>
                                            </c:forEach>
                                           </select>
                                            </div>
                                        </li>
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>证件号码</span>
											<div class="input-append">
												 <input class="span5" maxlength="30" value="${expert.idNumber }"  name="idNumber" id="idNumber" type="text"/>
        									<span class="add-on">i</span>
        									</div>
										</li>
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>省</span>
                                            <div class="select_common">
                                             <select id="addr" onchange="func();">
                                                    <option value="">-请选择-</option>
                                             </select>
                                            </div>
                                        </li>
                                        <li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>市</span>
                                            <div class="select_common">
                                             <select  name="address" id="add">
                                                    <option value="">-请选择-</option>
                                             </select>
                                            </div>
                                        </li>
										
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">政治面貌</span>
                                            <div class="select_common">
                                                <select  name="politicsStatus" id="politicsStatus">
                                                <option selected="selected" value="">-请选择-</option>
                                                <c:forEach items="${zzList }" var="zz" varStatus="vs">
                                                  <option <c:if test="${expert.politicsStatus eq zz.id }">selected="selected"</c:if> value="${zz.id }">${zz.name }</option>
                                                </c:forEach>
                                               </select>
                                               </div>
                                        </li>
										
										
                                        <li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>最高学历</span>
                                            <div class="select_common">
                                             <select  name="hightEducation" id="hightEducation" >
                                                <option selected="selected" value="">-请选择-</option>
                                                <c:forEach items="${xlList }" var="xl" varStatus="vs">
                                                <option <c:if test="${expert.hightEducation eq xl.id }">selected="selected"</c:if> value="${xl.id }">${xl.name }</option>
                                                </c:forEach>
                                              </select>
                                              <!-- <span class="add-on">i</span> -->
                                            </div>
                                        </li>
                                        
                                        <li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>毕业院校</span>
                                            <div class="input-append">
                                            <input class="span5" maxlength="40" value="${expert.graduateSchool }"  name="graduateSchool" id="graduateSchool" type="text"/>
                                            <span class="add-on">i</span>
                                            </div>
                                        </li>
										
                                        <li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>专业</span>
                                            <div class="input-append">
                                            <input class="span5" maxlength="20" value="${expert.major }"  name="major" id="major" type="text"/>
                                            <span class="add-on">i</span>
                                            </div>
                                        </li>
                                        <li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 学位</span>
                                            <div class="input-append">
                                            <input class="span5" maxlength="10" value="${expert.degree }"  name="degree" id="degree" type="text"/>
                                            <span class="add-on">i</span>
                                            </div>
                                        </li>
										<li class="col-md-3 margin-0 padding-0"><span class="col-md-12 padding-left-5"><i class="red">*</i>专家来源</span>
											<div class="select_common">
												<select  name="expertsFrom" id="expertsFrom">
												<option selected="selected" value="">-请选择-</option>
												<c:forEach items="${lyTypeList }" var="ly" varStatus="vs"> 
											     	<option <c:if test="${expert.expertsFrom eq ly.id }">selected="selected"</c:if> value="${ly.id }">${ly.name }</option>
												</c:forEach>
											   </select>
											</div>
										</li>
										
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 专家技术职称</span>
											<div class="input-append">
											<input class="span5" maxlength="20" value="${expert.professTechTitles }"  name="professTechTitles" id="professTechTitles" type="text"/>
											<span class="add-on">i</span>
											</div>
										</li>
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 参加工作时间</span>
											<div class="input-append">
											<input class="span5"   readonly="readonly" value="<fmt:formatDate type='date' value='${expert.timeToWork }' dateStyle='default' pattern='yyyy-MM-dd'/>" name="timeToWork"  type="text" onclick='WdatePicker()'/>
											<span class="add-on">i</span>
											</div>
										</li>
										
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 从事专业起始年度</span>
											<div class="input-append">
											 <input class="span5" value="<fmt:formatDate type='date' value='${expert.timeStartWork }' dateStyle='default' pattern='yyyy-MM-dd'/>"  readonly="readonly" name="timeStartWork" id="timeStartWork" type="text" onclick='WdatePicker()'/>
											<span class="add-on">i</span>
											</div>
										</li>
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5">工作单位</span>
											<div class="input-append">
											<input class="span5" maxlength="40" value="${expert.workUnit }"  name="workUnit" id="workUnit" type="text"/>
											<span class="add-on">i</span>
											</div>
										</li>
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>单位地址</span>
											<div class="input-append">
											 <input class="span5" maxlength="40" value="${expert.unitAddress }"  name="unitAddress" id="unitAddress" type="text"/>
											<span class="add-on">i</span>
											</div>
										</li>
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>联系电话（固话）</span>
											<div class="input-append">
											<input class="span5" maxlength="15" value="${expert.telephone }"  name="telephone" id="telephone" type="text"/>
											<span class="add-on">i</span>
											</div>
										</li>
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>手机</span>
											<div class="input-append">
											<input class="span5" maxlength="15" value="${expert.mobile }"  name="mobile" id="mobile" type="text"/>
											<span class="add-on">i</span>
											</div>
										</li>
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 传真</span>
											<div class="input-append">
											<input class="span5" maxlength="10"  value="${expert.fax }"  name="fax" id="fax" type="text"/>
											<span class="add-on">i</span>
											</div>
										</li> 
        								<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 邮编</span>
											<div class="input-append">
											<input class="span5" maxlength="6" value="${expert.postCode }"  name="postCode" id="postCode" type="text"/>
											<span class="add-on">i</span>
											</div>
										</li>
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 获得技术时间</span>
											<div class="input-append">
											<input class="span5" value="<fmt:formatDate type='date' value='${expert.makeTechDate }' dateStyle='default' pattern='yyyy-MM-dd'/>"  readonly="readonly" name="makeTechDate" id="makeTechDate" type="text" onclick='WdatePicker()'/>
											<span class="add-on">i</span>
											</div>
										</li>
										
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>健康状态</span>
											<div class="input-append">
											<input class="span5" maxlength="10" value="${expert.healthState }"  name="healthState" id="healthState" type="text"/>
											<span class="add-on">i</span>
											</div>
										</li>
										<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"> 现任职务</span>
											<div class="input-append">
											<input class="span5" maxlength="10" value="${expert.atDuty }"  name="atDuty" id="appendedInput" type="text"/>
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
										  	   <td class="bggrey"><i class="red">*</i>身份证</td>
										  	   <td>
										  	      <u:upload id="expert1"  groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID }"   auto="true"/>
										          <u:show showId="show1"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID }"/>
										  	   </td>
										  	   <td class="bggrey"><i class="red">*</i>学历证书</td>
										  	   <td>
										  	      <u:upload id="expert2" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId }" sysKey="${expertKey }"  typeId="${typeMap.EXPERT_ACADEMIC_TYPEID }" auto="true"/>
										          <u:show showId="show2"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID }"/>
										  	   </td>
										  	</tr>
										  	<tr>
										  	   <td class="bggrey"><i class="red">*</i>职称证书</td>
										  	   <td>
										  	      <u:upload id="expert3" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId }" sysKey="${expertKey }"  typeId="${typeMap.EXPERT_TITLE_TYPEID }" auto="true"/>
										          <u:show  showId="show3"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_TITLE_TYPEID }"/>
										  	   </td>
										  	   <td class="bggrey"><i class="red">*</i>学位证书</td>
										  	   <td>
										  	      <u:upload id="expert4" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId }" sysKey="${expertKey }"   typeId="${typeMap.EXPERT_DEGREE_TYPEID }" auto="true"/>
										          <u:show showId="show4" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }"  typeId="${typeMap.EXPERT_DEGREE_TYPEID }"/>
										  	   </td>
										  	</tr>
										  	<tr>
										  	   <td class="bggrey" ><i class="red">*</i>个人照片</td>
										  	   <td colspan="3">
										  	      <u:upload id="expert5" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7"  businessId="${sysId }" sysKey="${expertKey }"  typeId="${typeMap.EXPERT_PHOTO_TYPEID }" auto="true"/>
										          <u:show showId="show5" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_PHOTO_TYPEID }"/>
										  	   </td>
										  	</tr>
										  	</tbody>
										  </table>
										  </ul>
										   </div>
									<div class="tc mt20 clear col-md-11">
									        <button class="btn btn-windows git" onclick='submitForm1()'  type="button">暂存</button>
											<button class="btn btn-windows git" id="nextBind"  type="button" onclick='fun()' >下一步</button>
									</div>
								</div>
							</div>
		</div>
		<div id="reg_box_id_4" class="container clear margin-top-30 yinc" style="display: none;">
		  		<h2 class="padding-20 mt40">
					<!-- <span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> -->
					<span id="ty1" class="new_step current fl"  onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span id="ty2" class="new_step <c:if test="${expert.expertsTypeId != null}">current</c:if> fl"   onclick='tab2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
					<span id="ty3" class="new_step <c:if test="${expert.purchaseDepId != null}">current</c:if> fl" onclick='tab3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span id="ty4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab4()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
					<span id="ty5" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab5()'><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
			 
			<div class="container container_box">
			<div class="headline-v2">
                 <h2>专家类型</h2>
               </div>
			<div>
		       
			<ul class="ul_list">
     		<li class="col-md-3 margin-0 padding-0 " >
			   <span class="col-md-12 padding-left-5">专家类型</span>
			   <input type="hidden" id="expertsTypeIds" value="" >
			   <div class="select_common">
			   <select name="expertsTypeId" id="expertsTypeId" onchange="typeShow();">
			   		<option value="">-请选择-</option>
			   		<option <c:if test="${expert.expertsTypeId == '1' }">selected="true"</c:if> value="1">技术</option>
			   		<option <c:if test="${expert.expertsTypeId == '2' }">selected="true"</c:if> value="2">法律</option>
			   		<option <c:if test="${expert.expertsTypeId == '3' }">selected="true"</c:if> value="3">商务</option>
			   </select>
			   </div>
			 </li>
   			 </ul>
   			   <div id="ztree" style="margin-left: 100px;" class="ztree"></div>
		    <div class="tc mt20 clear col-md-11">
				<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 4, 'pre')">上一步</button>
				<button class="btn btn-windows git" onclick='submitForm1()'  type="button">暂存</button>
				<button class="btn btn-windows git"   type="button" onclick='fun1()'>下一步</button>
			</div>
		</div>
		</div>
		</div>
		<!-- 项目戳开始 -->
		<div id="reg_box_id_5" class="container clear margin-top-30 yinc" style="display: none;">
		  		<h2 class="padding-20 mt40">
					<!-- <span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> -->
					<span id="jg1" class="new_step current fl"  onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span id="jg2" class="new_step <c:if test="${expert.expertsTypeId != null}">current</c:if> fl"   onclick='tab2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span>
					<span id="jg3" class="new_step <c:if test="${expert.purchaseDepId != null}">current</c:if> fl" onclick='tab3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span id="jg4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab4()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
					<span id="jg5" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab5()'><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
			<div class="container container_box">
		      <div class="headline-v2">
			     <h2>选择采购机构</h2>
			   </div>  
			<table id="tb1"  class="table table-bordered table-condensed table-hover table-striped">
			<ul class="ul_list">
			<li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>省</span>
                <div class="select_common">
                 <select id="addr2" onchange="func2();">
                        <option value="">-请选择-</option>
                 </select>
                </div>
            </li>
            <li class="col-md-3 margin-0 padding-0 "><span class="col-md-12 padding-left-5"><i class="red">*</i>市</span>
                <div class="select_common">
                 <select  name="address2" id="add2">
                        <option value="">-请选择-</option>
                 </select>
                </div>
            </li>
            </ul>
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
				<c:forEach items="${ purchase}" var="p" varStatus="vs">
					<tr align="center">
						<td><input type="radio" name="purchaseDepId" <c:if test="${expert.purchaseDepId eq p.id }">checked</c:if>  value="${p.id }" /></td>
						<td>${vs.count}</td>
						<td><input border="0" readonly="readonly" value="${p.name }" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
						<td><input border="0" readonly="readonly" value="${p.princinpal }" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
						<td><input border="0" readonly="readonly" value="${p.detailAddr }" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
						<td><input border="0" readonly="readonly" value="${p.mobile }" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;text-align: center; '></td>
					</tr>
				</c:forEach> 
			</table>
			 <!-- <h2 class="f16 jbxx">
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
				</thead> -->
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
				<!-- <tr>
				  <td class="tc w30"><input type="radio" name="purchaseDepId" id="checked" alt="" value="3"></td>
				  <td class="tc w50">1</td>
				  <td class="tc"><input border="0" disabled="disabled" value="哈哈"></td>
				  <td class="tc"><input border="0" disabled="disabled" value="飒飒"></td>
				  <td class="tc"><input border="0" disabled="disabled" value="北京"></td>
				 <td class="tc"><input border="0" disabled="disabled" value="13333333333"></td>
				</tr>
			</table> -->
			<h6>
		               友情提示：请专家记录好初审采购机构的相关信息，以便进行及时沟通
		    </h6>
		    <div class="tc mt20 clear col-md-11">
				<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 5, 'pre')">上一步</button>
				<button class="btn btn-windows git" onclick='submitForm1()'  type="button">暂存</button>
				<button class="btn btn-windows git"   type="button" onclick='addPurList()'>下一步</button>
			</div>
		</div>
		</div>
	<div id="reg_box_id_6" class="container clear margin-top-30 yinc" style="display: none;">
		  <h2 class="padding-20 mt40">
					<!-- <span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> -->
					<span id="dy1" class="new_step current fl" onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span id="dy2" class="new_step current fl" onclick='tab2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span id="dy3" class="new_step current fl" onclick='tab3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span id="dy4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab4()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
					<span id="dy5" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab5()'><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
			<div class="tab-content padding-top-20">
			<div class="headline-v2">
			     <h2>打印专家申请表</h2>
			   </div>  
			<!-- 供应商申请书上传：<input type="file" name=""/>
			供应商承诺书上传：<input type="file" name=""/> -->
				   	<div ><br/><br/>
<table class="table table-bordered">
<div align="right">
<a class="btn btn-windows git" onclick='downloadTable()' href="javascript:void(0)">下载</a>
</div>
   	<tr>
   		<td width="250px;" class="bggrey">姓名</td>
   		<td width="250px;" id="tName"></td>
   		<td width="250px;" class="bggrey">性别</td>
		<td width="250px;" id="tSex" ></td>
   		<!-- <td align="center" rowspan="4">照片</td> -->
   	</tr>
   <tr>
   		<td width="250px;" class="bggrey">出生日期</td>
   		<td width="250px;" id="tBirthday"></td>
   		<td width="250px;" class="bggrey">政治面貌</td>
   		<td width="250px;"  id="tFace"></td>
   </tr>
   
   <tr>
   		<td width="250px;" class="bggrey">所在地区</td>
   		<td width="250px;"></td>
   		<td width="250px;" class="bggrey">职称</td>
   		<td width="250px;" id="tHey" ></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">身份证号码<td>
   		<td id="tNumber" colspan="3"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">从事专业类别</td>
   		<td width="250px;" id="tExpertsTypeId"></td>
   		<td width="250px;" class="bggrey">从事年限</td>
   		<td width="250px;" id="tTimeStartWork"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">最高学历</td>
   		<td width="250px;" id="tHight"></td>
   		<td width="250px;" class="bggrey">最高学位</td>
   		<td width="250px;" id="tWei"></td>
   
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">执业资格1</td>
   		<td width="250px;" > </td>
   		<td width="250px;" class="bggrey">注册证书编号1</td>
   		<td width="250px;" > </td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">执业资格2</td>
   		<td width="250px;" ></td>
   		<td width="250px;" class="bggrey">注册证书编号2</td>
   		<td width="250px;"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">执业资格3</td>
   		<td width="250px;"></td>
   		<td width="250px;" class="bggrey">注册证书编号3</td>
   		<td width="250px;" ></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">近两年是否接受过评标业务培训</td>
   		<td width="250px;"></td>
   		<td width="250px;" class="bggrey">是否愿意成为应急专家</td>
   		<td width="250px;"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">所属行业</td>
   		<td width="250px;"></td>
   		<td width="250px;" class="bggrey">报送部门</td>
   		<td width="250px;"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">手机号码</td>
   		<td width="250px;" id="tMobile"></td>
   		<td width="250px;" class="bggrey">单位电话</td>
   		<td width="250px;" id="tTelephone"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">住宅电话</td>
   		<td width="250px;"></td>
   		<td width="250px;" class="bggrey">电子邮箱</td>
   		<td width="250px;"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">毕业院校及专业</td>
   		<td id="tGraduateSchool" colspan="3"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">单位名称</td>
   		<td id="tWorkUnit" colspan="3"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">单位地址 </td>
   		<td width="250px;" id="tUnitAddress"></td>
   		<td width="250px;" class="bggrey">单位邮编</td>
   		<td width="250px;" id="tPostCode"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">家庭地址 </td>
   		<td width="250px;"></td>
   		<td width="250px;" class="bggrey">家庭邮编</td>
   		<td width="250px;"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">评标专业一</td>
   		<td width="250px;" colspan="3"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">评标专业二</td>
   		<td colspan="3"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">评标专业三</td>
   		<td colspan="3"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">评标专业四</td>
   		<td colspan="3"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">评标专业五</td>
   		<td colspan="3"></td>
   </tr>
   <tr>
   		<td width="250px;" class="bggrey">评标专业六</td>
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
				<!-- <button class="btn btn-windows git"   type="button" onclick="window.print()">打印</button> -->
				
				<!-- <button class="btn btn-windows git" onclick='submitForm1()'  type="button">暂存</button> -->
				<button class="btn btn-windows git"   type="button" onclick='four()'>下一步</button>
			</div>
		</div>
		</div>
		</div>
		<div id="reg_box_id_7" class="container clear margin-top-30 yinc" style="display: none;">
		 <h2 class="padding-20 mt40">
					<!-- <span class="new_step current fl"><i class="">1</i><div class="line"></div> <span class="step_desc_01">用户名密码</span> </span> -->
					<span id="sc1" class="new_step current fl" onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span id="sc2" class="new_step current fl" onclick='tab2()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类型</span> </span> 
					<span id="sc3" class="new_step current fl" onclick='tab3()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span id="sc4" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab4()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">下载申请表</span> </span> 
					<span id="sc5" class="new_step <c:if test="${att eq '1'}">current</c:if> fl" onclick='tab5()'><i class="">5</i> <span class="step_desc_02">上传申请表</span> </span> 
				<div class="clear"></div>
			</h2>
			<div class="tab-content padding-top-20">
			<div class="headline-v2">
			     <h2>专家申请表、合同书</h2>
			   </div>  
				   	  <table class="table table-bordered">
				   	  <tr>
				   	    <td class="bggrey "><i class="red">*</i>专家申请表：</td>
				   	    <td>
				   	       <up:upload id="expert6"  groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_APPLICATION_TYPEID }" auto="true"/>
						   <up:show showId="show6"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_APPLICATION_TYPEID }"/>
				   	    </td>
				   	    <td class="bggrey "><i class="red">*</i>专家合同书：</td>
				   	    <td>
				   	       <up:upload id="expert7" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_CONTRACT_TYPEID }" auto="true"/>
						   <up:show showId="show7"  groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId }" sysKey="${expertKey }" typeId="${typeMap.EXPERT_CONTRACT_TYPEID }"/>
				   	    </td>
				   	  </tr>
								    
					 </table>
			<!-- <div class="col-md-12 add_regist" style="margin-left:170px;">
				 <div class="fl mr20"><label class="regist_name">采购机构名称：</label><span id="depName_" class="regist_desc"></span></div>
				 <div class="fl mr20"><label class="regist_name">采购机构联系人：</label><span id="person_" class="regist_desc"></span></div>
				 <div class="fl mr20"><label class="regist_name">采购机构地址：</label><span id="address_" class="regist_desc"></span></div>
				 <div class="fl mr20"><label class="regist_name">联系电话：</label><span id="phone_" class="regist_desc"></span></div>
			 </div> -->
		    <div class="tc mt20 clear col-md-11">
		   		<button class="btn btn-windows back"   type="button" onclick="pre('reg_box_id', 7, 'pre')">上一步</button>
				<!-- <button class="btn btn-windows git" onclick='submitForm1()'  type="button">暂存</button> -->
				<input  class="btn btn-windows git" type="button" onclick="addSubmitForm()" value="提交" />
			</div>
		</div>
		</form>
</body>
</html>
