<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/reg_head.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>评审专家注册</title>
		<script src="${pageContext.request.contextPath}/js/ems/expert/validate_expert_basic_info.js"></script>
		<script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
		<%
//表单标示
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
session.setAttribute("tokenSession", tokenValue);
%>
		<script type="text/javascript">
			function submitformExpert() {
				getChildren();
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: $("#formExpert").serialize(),
					type: "post",
					async: true,
					success: function(result) {
						$("#id").val(result.id);
						//layer.msg("已暂存",{offset: ['300px', '750px']});
					}
				});
			}
			//无提示暂存
			function submitForm2() {
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: $("#formExpert").serialize(),
					type: "post",
					async: false,
					success: function(result) {
						$("#id").val(result.id);
						$.ajax({
							url: "${pageContext.request.contextPath}/expert/getAllCategory.do",
							data: {
								"expertId": $("#id").val()
							},
							async: false,
							dataType: "json",
							success: function(response) {
								if(!$.isEmptyObject(response)) {
									updateStepNumber("six");
								} else {
									updateStepNumber("three");
								}
							}
						});
						window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
					}
				});
			}
			/** 专家完善注册信息页面 */
			function fun() {
				if(!validateType()) {
					return;
				} else {
					//暂存无提示
					submitForm2();
				}
			}

			function updateStepNumber(stepNumber) {
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/updateStepNumber.do",
					data: {
						"expertId": $("#id").val(),
						"stepNumber": stepNumber
					},
					async: false,
				});
			}
			//获取选中子节点id
			function getChildren() {
				var ids = new Array();
				var checklist1 = document.getElementsByName("chkItem_1");
				for(var i = 0; i < checklist1.length; i++) {
					var vals = checklist1[i].value;
					if(checklist1[i].checked) {
						ids.push(vals);
					}
				}
				var checklist2 = document.getElementsByName("chkItem_2");
				for(var i = 0; i < checklist2.length; i++) {
					var vals = checklist2[i].value;
					if(checklist2[i].checked) {
						ids.push(vals);
					}
				}
				$("#expertsTypeId").val(ids);
			}

			function pre() {
				updateStepNumber("two");
				window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}

			function pre1() {
				updateStepNumber("one");
				window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}
			//判断专家类型
			function validateType() {
				getChildren();
				var categoryId = $("#expertsTypeId").val();
				if(categoryId == "") {
					layer.msg("请选择专家类别 !", {
						offset: ['222px', '390px']
					});
					return false;
				}
				return true;
			}
			$(function() {
				$("input").bind("change", submitformExpert);
				var typeIds = "${expert.expertsTypeId}";
				var ids = typeIds.split(",");
				//回显
				var checklist1 = document.getElementsByName("chkItem_1");
				for(var i = 0; i < checklist1.length; i++) {
					var vals = checklist1[i].value;
					for(var j = 0; j < ids.length; j++) {
						if(ids[j] == vals) {
							checklist1[i].checked = true;
						}
					}
				}
				var checklist2 = document.getElementsByName("chkItem_2");
				for(var i = 0; i < checklist2.length; i++) {
					var vals = checklist2[i].value;
					for(var j = 0; j < ids.length; j++) {
						if(ids[j] == vals) {
							checklist2[i].checked = true;
						}
					}
				}
			});

			function errorMsg(auditField) {
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/findAuditReason.do",
					data: {
						"expertId": $("#id").val(),
						"auditField": auditField
					},
					dataType: "json",
					success: function(response) {
						layer.msg("不通过理由:" + response.auditReason, {
							offset: ['400px', '730px']
						});
					}
				});
			}

			function zc() {
				layer.msg("已暂存", {
					offset: ['300px', '750px']
				});
			}
		</script>
	</head>

	<body>
		<form id="formExpert" action="${pageContext.request.contextPath}/expert/add.html" method="post">
			<input type="hidden" name="userId" value="${user.id}" />
			<input type="hidden" id="purchaseDepId" value="${expert.purchaseDepId}" />
			<input type="hidden" name="id" id="id" value="${expert.id}" />
			<input type="hidden" name="zancun" id="zancun" value="" />
			<input type="hidden" name="sysId" id="sysId" value="${sysId}" />
			<input type="hidden" value="${errorMap.realName}" id="error1">
			<input type="hidden" value="${errorMap.nation}" id="error2">
			<input type="hidden" value="${errorMap.gender}" id="error3">
			<input type="hidden" value="${errorMap.idType}" id="error4">
			<input type="hidden" value="${errorMap.idNumber}" id="error5">
			<input type="hidden" value="${errorMap.address}" id="error6">
			<input type="hidden" value="${errorMap.hightEducation}" id="error7">
			<input type="hidden" value="${errorMap.graduateSchool}" id="error8">
			<input type="hidden" value="${errorMap.major}" id="error9">
			<input type="hidden" value="${errorMap.unitAddress}" id="error11">
			<input type="hidden" value="${errorMap.telephone}" id="error12">
			<input type="hidden" value="${errorMap.mobile}" id="error13">
			<input type="hidden" value="${errorMap.healthState}" id="error14">
			<input type="hidden" value="${errorMap.mobile2}" id="error15">
			<input type="hidden" value="${errorMap.idNumber2}" id="error16">
			<input type="hidden" id="categoryId" name="categoryId" value="" />
			<input type="hidden" id="expertsTypeId" name="expertsTypeId" value="" />
			<input type="hidden" name="token2" value="<%=tokenValue%>" />
			<div id="reg_box_id_3" class="container clear margin-top-30 job-content">
				<h2 class="padding-20 mt40">
	    			<span id="sp1" class="new_step current fl" onclick="pre1()"><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
	    			<span id="sp2" class="new_step current fl" onclick="pre()"><i class="">2</i><div class="line"></div> <span class="step_desc_01">经历经验</span> </span>
	    			<span id="sp7" class="new_step current fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">专家类别</span> </span> 
	    			<span id="ty6" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">产品目录</span> </span>
	    			<span id="sp3" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
	    			<span id="sp4" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">文件下载</span> </span> 
	   				<span id="sp5" class="new_step fl"><i class="">7</i><span class="step_desc_02">文件上传</span> </span> 
	    			<div class="clear"></div>
	  			</h2>
				<div class="container container_box">
					<h2 class="count_flow">专家类别</h2>
					<!-- 专家专业信息 -->
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl10">
							<div class="input-append col-sm-12 col-xs-12 col-md-12 p0">
								<c:forEach items="${spList}" var="sp">
									<span <c:if test="${fn:contains(errorField,sp.id)}">style="color: #ef0000;" onmouseover="errorMsg('${sp.id}')"</c:if> class="margin-left-30"><input type="checkbox" name="chkItem_1" value="${sp.id}" />${sp.name}技术 </span>
								</c:forEach>
								<c:forEach items="${jjList}" var="jj">
									<span <c:if test="${fn:contains(errorField,jj.id)}">style="color: #ef0000;" onmouseover="errorMsg('${jj.id}')"</c:if> class="margin-left-30"><input type="checkbox" name="chkItem_2"  value="${jj.id}" />${jj.name} </span>
								</c:forEach>
							</div>
						</li>
					</ul>
					<div class="btmfix">
						<div style="margin-top: 15px;text-align: center;">
							<button class="btn" id="nextBind" type="button" onclick='pre()'>上一步</button>
							<button class="btn" onclick='zc()' type="button">暂存</button>
							<button class="btn" id="nextBind" type="button" onclick='fun()'>下一步</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>

</html>