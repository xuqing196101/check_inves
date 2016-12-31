<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/front.jsp"%>
<script src="${pageContext.request.contextPath}/js/ems/expert/validate_expert_basic_info.js"></script>
<script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
<%
//表单标示
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
session.setAttribute("tokenSession", tokenValue);
%>
<script type="text/javascript">
	var expertStatus = "${expert.status}";
	var errorField = "${errorField}";
	// 如果状态为退回,判断品目有没有被退回
	function setFontCss(treeId, treeNode){
		// 如果状态是为退回才进行判断
		if (expertStatus == '3' || expertStatus == 3) {
			return errorField.indexOf(treeNode.id) != -1 ? {color:"red"} : {};
		} else {
			return {};
		}
	}
	function updateStepNumber(stepNumber){
		$.ajax({
			url:"${pageContext.request.contextPath}/expert/updateStepNumber.do",
			data:{"expertId":$("#id").val(),"stepNumber":stepNumber},
			async:false,
		});
	}
	function showTree(tabId) {
		var id = $("#" + tabId + "-value").val();
		var zTreeObj;
		var zNodes;
		var expertId="${expert.id}";
		var setting = {
			async: {
				autoParam: ["id"],
				enable: true,
				url: "${pageContext.request.contextPath}/expert/getCategory.do",
				otherParam: {
					"categoryId": id,
					"expertId": expertId
				},
				dataFilter: ajaxDataFilter,
				dataType: "json",
				type: "get"
			},
			check: {
				enable : true,
				chkStyle:"checkbox",  
				chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parentId"
				}
			},
			callback: {
				onCheck: saveCategory
			},
			view: {
				fontCss: setFontCss
			},
		};
		zTreeObj = $.fn.zTree.init($("#" + tabId), setting, zNodes);
		zTreeObj.expandAll(true);//全部展开
	}
	function ajaxDataFilter(treeId, parentNode, childNodes) {
		// 判断是否为空
		if (childNodes) {
			// 判断如果父节点是第三极,则将查询出来的子节点全部改为isParent = false
	    	if (parentNode != null && parentNode != "undefined" && parentNode.level == 2) {
				for(var i =0; i < childNodes.length; i++) {
		        	childNodes[i].isParent += false;
		      	}
	    	}
	    }	
	    return childNodes;
	}
	function saveCategory(event, treeId, treeNode) {
		var clickFlag;
		if (treeNode.checked) {
			clickFlag = "1";
		} else {
			clickFlag = "0";
		}
		var expertId = "${expert.id}";
		var typeId = $("#" + treeId + "-value").val();
		$.ajax({
			url: "${pageContext.request.contextPath}/expert/saveCategory.do",
			async: false,
			data: {"expertId" : expertId, "categoryId" : treeNode.id, "type" : clickFlag, "typeId" : typeId}
		});
	}
	function zc(){
		layer.msg("已暂存",{offset: ['300px', '750px']});
	}
</script>
<script type="text/javascript">
function showDivTree(obj){
	$("#tab-1").attr("style", "display: none");
	$("#tab-2").attr("style", "display: none");
	$("#tab-3").attr("style", "display: none");
	var id = obj.id;
	var page = "tab-" + id.charAt(id.length - 1);
	$("#" + page).attr("style", "");
	showTree(page);
}
function initTree(){
	showTree("tab-1");
	$("#tab-1").attr("style", "");
	$("li_id_1").attr("class", "active");
	$("li_1").attr("aria-expanded", "true");
	$("#tab-2").attr("style", "display: none");
	$("#tab-3").attr("style", "display: none");
}
function zancunCategory(count){
	var ids = new Array();
	for (var i = 1; i <= count; i++) {
		var id = "tab-" + i;
		var tree = $.fn.zTree.getZTreeObj(id);
		nodes = tree.getCheckedNodes(true);
		for (var j = 0; j < nodes.length; j++) {
			if (!nodes[j].isParent) {
				ids.push(nodes[j].id);
			}
		}
	}
	$("#categoryId").val(ids);
	zancunMsg();
}
function nextCategory(){
	var expertId = "${expert.id}";
	$.ajax({
		url: "${pageContext.request.contextPath}/expert/isHaveCategory.do",
		data: {"expertId" : expertId},
		success: function(response){
			if (response == '0') {
				layer.msg("请至少选择一项!");	
			} else if (response == '1') {
				updateStepNumber("three");
				window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}
		}
	});
}
// 有提示msg暂存
function zancunMsg(){
	$.ajax({
		url:"${pageContext.request.contextPath}/expert/zanCun.do",
		data:$("#formExpert").serialize(),
		type: "post",
		async: true,
		success:function(result){
			$("#id").val(result.id);
			layer.msg("已暂存");
		}
	});
}
// 无提示暂存
function zancun(){
	$.ajax({
		url:"${pageContext.request.contextPath}/expert/zanCun.do",
		data:$("#formExpert").serialize(),
		type: "post",
		async: true,
		success:function(result){
			window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
		}
	});
}
function pre(){
	updateStepNumber("seven");
	window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
}
function pre7(name, i, position) {
	updateStepNumber("seven");
	window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
}
function one(){
	updateStepNumber("one");
	window.location.href="${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
}
function errorMsg(auditField){
	$.ajax({
		url: "${pageContext.request.contextPath}/expert/findAuditReason.do",
		data: {"expertId": $("#id").val(), "auditField": auditField},
		dataType: "json",
		success: function(response){
			layer.msg("不通过理由:" + response.auditReason ,{offset: ['400px', '730px']});
		}
	});
}
</script>
</head>
<body onload="initTree()">
<form method="post" action="">
  <jsp:include page="/reg_head.jsp"></jsp:include>
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
  <div id="reg_box_id_4" class="container clear margin-top-30 yinc">
    <h2 class="padding-20 mt40">
	  <span id="ty1" class="new_step current fl" onclick="one()"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
	  <span id="ty2" class="new_step current fl" onclick="pre()"><i class="">2</i><div class="line"></div> <span class="step_desc_01">经历经验</span> </span>
	  <span id="sp7" class="new_step current fl" onclick='pre7()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">专家类别</span> </span>
	  <span id="ty6" class="new_step current fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">产品目录</span> </span>
	  <span id="ty3" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
	  <span id="ty4" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">文件下载</span> </span> 
	  <span id="ty5" class="new_step fl"><i class="">7</i> <span class="step_desc_02">文件上传</span> </span> 
	  <div class="clear"></div>
	</h2>
	<div class="col-md-12 tab-v2 job-content">
	  <div class="padding-top-10">
	  	<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
	  	<c:set value="0" var="liCount"/>
		  <c:forEach items="${allCategoryList}" var="cate" varStatus="vs">	  	
			<c:if test="${cate.code eq 'GOODS'}">
				<c:set value="${liCount+1}" var="liCount"/>
			  <li id="li_id_${vs.index + 1}" class="active" onclick="showDivTree(this);"><a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">物资</a></li>
			</c:if>
			<c:if test="${cate.code eq 'PROJECT'}">
			  <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree(this);"><a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程</a></li>
				<c:set value="${liCount+1}" var="liCount"/>
			</c:if>
			<c:if test="${cate.code eq 'SERVICE'}">
			  <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree(this);"><a id="li_${vs.index + 1}" aria-expanded="false" data-toggle="tab" class="f18">服务</a></li>
				<c:set value="${liCount+1}" var="liCount"/>
			</c:if>
		  </c:forEach>
		</ul>
		  <c:set var="count" value="0"></c:set>
		  <div class="tag-box tag-box-v3 center" id="content_ul_id">
		    <c:forEach items="${allCategoryList}" var="cate" varStatus="vs">
			  <c:if test="${cate.code eq 'GOODS'}">
			  	<c:set var="count" value="${count + 1}"></c:set>
			    <ul id="tab-${vs.index + 1}" class="ztree_supplier mt30"></ul>
			    <input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
			  </c:if>
			  <c:if test="${cate.code eq 'PROJECT'}">
			  	<c:set var="count" value="${count + 1}"></c:set>
			    <ul id="tab-${vs.index + 1}" class="ztree_supplier mt30"></ul>
			    <input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
			  </c:if>
			  <c:if test="${cate.code eq 'SERVICE'}">
			  	<c:set var="count" value="${count + 1}"></c:set>
			    <ul id="tab-${vs.index + 1}" class="ztree_supplier mt30"></ul>
			    <input id="tab-${vs.index + 1}-value" value="${cate.id}" type="hidden">
			  </c:if>
		    </c:forEach>
		  </div>
	  </div>
	</div>  
    <div class="btmfix">
	  	  	  <div style="margin-top: 15px;text-align: center;">
	  	  	    <button class="btn"  type="button" onclick="pre()">上一步</button>
	  <button class="btn" onclick="zc()"  type="button">暂存</button>
	  <button class="btn"  type="button" onclick="nextCategory()">下一步</button>
	  	  	  </div>
			</div>
  </div>
  <div></div>
</form>
<jsp:include page="/index_bottom.jsp"></jsp:include>
</form>
</body>
</html>
