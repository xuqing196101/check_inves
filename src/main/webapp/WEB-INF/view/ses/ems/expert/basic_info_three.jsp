<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/reg_head.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script src="${pageContext.request.contextPath}/js/ems/expert/validate_expert_basic_info.js"></script>
<script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
<%
//表单标示
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
session.setAttribute("tokenSession", tokenValue);
%>
<script type="text/javascript">
		//第二个select事件
		function func2(){
			var parentId = $("#addr2").val();
			$.ajax({
				url : "${pageContext.request.contextPath}/area/find_by_parent_id.do",
				data:{"id":parentId},
				async:false,
				dataType:"json",
				success:function(response,status,request){
					$("#add2").empty();
					$("#add2").append("<option  value=''>-请选择-</option>");
					$.each(response,function(i,result){
						$("#add2").append("<option value='"+result.id+"'>"+result.name+"</option>");
					});
				}
			});
			showJiGou();
		}
		   
	function submitformExpert(){
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#formExpert").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				  //layer.msg("已暂存");
			 },
			  error:function(result){
			 }
		});
	}
	//无提示暂存
	function submitForm2(){
		updateStepNumber("four");
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#formExpert").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			 }
		});
	}
	//无提示暂存
	function submitForm5(){
		updateStepNumber("five");
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/zanCun.do",
			data:$("#formExpert").serialize(),
			type: "post",
			async: true,
			success:function(result){
				$("#id").val(result.id);
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			 }
		});
	}
		/** 专家完善注册信息页面 */
	function supplierRegist() {
		if(!validateJiGou()){
			return false;
		}
		//暂存无提示
		submitForm2();
	}
	/** 专家完善注册信息页面 */
	function supplierRegist5() {
		if(!validateJiGou()){
			return false;
		}
		//暂存无提示
		submitForm5();
	}
	function pre() {
		updateStepNumber("two");
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
	}
	function pre() {
		updateStepNumber("seven");
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
	}
	function pre6(name, i, position) {
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/getAllCategory.do",
			data:{"expertId":$("#id").val()},
			async:false,
			dataType:"json",
			success:function(response){
				if (!$.isEmptyObject(response)) {
					updateStepNumber("six");
				} else {
					updateStepNumber("two");					
				}
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}
		});
	}
	function tab1() {
		updateStepNumber("one");
		window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
	}
	
	function purDepBack(){
		$("#purchase_orgs").empty();
		$("#purchase_orgs2").empty();
		var purDepId = "${expert.purchaseDepId}";
		$.ajax({
			url:'${pageContext.request.contextPath}/expert/getPIdandCIdByPurDepId.do',
			data:{"purDepId":purDepId},
			type:"post",
			dataType:"json",
			cache: false,
			async: false,
			success:function(data){
				if(data != null){
					$("#addr2").val(data.PROVINCEID);
					$.ajax({
						url : "${pageContext.request.contextPath}/area/find_by_parent_id.do",
						data:{"id":data.PROVINCEID},
						async:false,
						dataType:"json",
						success:function(response,status,request){
							$("#add2").empty();
							$("#add2").append("<option  value=''>-请选择-</option>");
							$.each(response,function(i,result){
								$("#add2").append("<option value='"+result.id+"'>"+result.name+"</option>");
							});
						}
					});
					$("#add2").val(data.CITYID);
				}
			}
		});
		var shengId = $("#addr2").val();
		var shiId = $("#add2").val();
		$.ajax({
			url:'${pageContext.request.contextPath}/expert/showJiGou.do',
			data:{"pId":shengId,"zId":shiId},
			//type:"post",
			dataType:"json",
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
					var flag;
					if (result.flag == '1') {
						flag = "purchase_orgs";
					} else {
						flag = "purchase_orgs2";
					}
					if(purDepId==result.id){
						$("#"+flag).append(
								"<tr align='center' ><td><input checked='checked' type='radio' name='purchaseDepId'  value='"+result.id+"' /></td>"+
								"<td>"+i+"</td>"+
								"<td>"+name+"</td>"+
								"<td>"+princinpal+"</td>"+
								"<td>"+detailAddr+"</td>"+
								"<td>"+mobile+"</td></tr>"
							);
					}else{
						$("#"+flag).append(
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
	function addPurList(){
		supplierRegist(); 
	}
	
	function showJiGou(){
		$("#purchase_orgs").empty();
		$("#purchase_orgs2").empty();
		//采购机构
		var sup = $("#purchaseDepId").val();
		var purDepId="";
		var expertId="${expert.id}";
		if(expertId){
			$.ajax({
				url:'${pageContext.request.contextPath}/expert/getPurDepIdByExpertId.do',
				data:{"expertId":expertId},
				cache: false,
				async: false,
				success:function(data){
					purDepId=data;
				}
			});
		}else{
			purDepId=sup;
		}
		var shengId = $("#addr2").val();
		var shiId = $("#add2").val();
		$.ajax({
			url:'${pageContext.request.contextPath}/expert/showJiGou.do',
			data:{"pId":shengId,"zId":shiId},
			//type:"post",
			dataType:"json",
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
					var flag;
					if (result.flag == '1') {
						flag = "purchase_orgs";
					} else {
						flag = "purchase_orgs2";
					}
					if(purDepId==result.id){
						$("#"+flag).append(
								"<tr align='center' ><td><input checked='checked' type='radio' name='purchaseDepId'  value='"+result.id+"' /></td>"+
								"<td>"+i+"</td>"+
								"<td>"+name+"</td>"+
								"<td>"+princinpal+"</td>"+
								"<td>"+detailAddr+"</td>"+
								"<td>"+mobile+"</td></tr>"
							);
					}else{
						$("#"+flag).append(
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
	$(function(){
		$("input").bind("change", submitformExpert);
	});
	window.onload=function(){
		//地区回显和数据显示
		$.ajax({
			url : "${pageContext.request.contextPath}/area/find_by_id.do",
			data:{"id":addressId},
			async:false,
			success:function(obj){
				$.each(obj,function(i,result){
					if(addressId == result.id){
						parentId = result.parentId;
						$("#add2").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
					}else{
						$("#add2").append("<option value='"+result.id+"'>"+result.name+"</option>");
					}
				});
			}
		}); 
		//地区
		$.ajax({
			url : "${pageContext.request.contextPath}/area/listByOne.do",
			async:false,
			success:function(obj){
				$.each(obj,function(i,result){
					 if(parentId == result.id){
						$("#addr2").append("<option selected='true' value='"+result.id+"'>"+result.name+"</option>");
					}else{ 
					$("#addr2").append("<option value='"+result.id+"'>"+result.name+"</option>");
					}
				});
			}
		});
		//validateBase();
		showJiGou();
		purDepBack();
	}
	function tab4(att){
		if (att == '1') {
			supplierRegist2();
		}
	}
	function tab5(att){
		if (att == '1') {
			supplierRegist();
		}
	}
	function updateStepNumber(stepNumber){
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/updateStepNumber.do",
			data:{"expertId":$("#id").val(),"stepNumber":stepNumber},
			async:false,
		});
	}
	function zc(){
		layer.msg("已暂存",{offset: ['300px', '750px']});
	}
</script>
</head>
<body>
 <form id="formExpert" action="${pageContext.request.contextPath}/expert/add.html" method="post">
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
		<!-- 项目戳开始 -->
		<div id="reg_box_id_5" class="container clear margin-top-30 yinc">
		  		<h2 class="padding-20 mt40">
					<span id="jg1" class="new_step current fl" onclick='tab1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span id="jg2" class="new_step current fl" onclick='pre()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">经历经验</span> </span>
					<span id="sp7" class="new_step current fl" onclick='pre7()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">专家类别</span> </span>
					<span id="ty6" class="new_step current fl" onclick='pre6()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">产品目录</span> </span>
					<span id="jg3" class="new_step current fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span id="jg4" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">文件下载</span> </span> 
					<span id="jg5" class="new_step fl"><i class="">7</i> <span class="step_desc_02">文件上传</span> </span> 
				<div class="clear"></div>
			</h2>
			<div class="container container_box">
			  <ul class="ul_list mt20">
				<li class="col-md-3 col-sm-6 col-xs-12">
				    <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">省</span>
	                <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
	                 <select id="addr2" onchange="func2();">
	                    <option value="">-请选择-</option>
	                 </select>
	                </div>
	            </li>
	            <li class="col-md-3 col-sm-6 col-xs-12">
	            <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">市</span>
	                <div class="select_common col-md-12 col-xs-12 col-sm-12 p0">
	                 <select  name="address2" id="add2" onchange="showJiGou()">
	                    <option value="">-请选择-</option>
	                 </select>
	                </div>
	            </li>
              </ul>
			  <h2 class="list_title">推荐采购机构</h2>
            <table class="table table-bordered table-condensed table-hover table-striped">
				<thead>
					<tr>
					  <th class="info w30"><input type="radio"   disabled="disabled"></th>
					  <th class="info w50">序号</th>
					  <th class="info">采购机构</th>
					  <th class="info">联系人</th>
					  <th class="info">联系地址</th>
					  <th class="info">联系电话</th>
					</tr>
				</thead>
				<tbody id="purchase_orgs"></tbody>
			</table>
			 <h2 class="list_title">其他采购机构</h2>
			<table class="table table-bordered table-condensed table-hover table-striped">
				<thead>
					<tr>
					  <th class="info w30"><input type="radio" disabled="disabled"></th>
					  <th class="info w50">序号</th>
					  <th class="info">采购机构</th>
					  <th class="info">联系人</th>
					  <th class="info">联系地址</th>
					  <th class="info">联系电话</th>
					</tr>
				</thead>
				<tbody id="purchase_orgs2">
				</tbody>
			</table>
			<h6>
		               友情提示：请专家记录好初审采购机构的相关信息，以便进行及时沟通
		    </h6>
			<div class="btmfix">
	  	  	  <div class="mt15 tc">
	  	  	    <button class="btn"   type="button" onclick="pre6()">上一步</button>
				<button class="btn" onclick='zc()'  type="button">暂存</button>
				<button class="btn"   type="button" onclick='addPurList()'>下一步</button>
	  	  	  </div>
			</div>
		  </div>
	    </div>
	  </form>
	  <jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
