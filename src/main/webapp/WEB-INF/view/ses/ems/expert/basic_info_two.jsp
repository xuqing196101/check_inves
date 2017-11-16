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
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: $("#formExpert").serialize(),
					type: "post",
					async: true,
					success: function(result) {
						$("#id").val(result.id);
						//layer.msg("已暂存");
					},
					error: function(result) {
						layer.msg("抱歉,字数超过限制,暂存失败!");
					}
				});
			}
			//无提示暂存
			function submitForm2() {
				updateStepNumber("seven");
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/zanCun.do",
					data: $("#formExpert").serialize(),
					type: "post",
					async: true,
					success: function(result) {
						$("#id").val(result.id);
						window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
					}
				});
			}
			/** 专家完善注册信息页面 */
			function supplierRegist() {
				if(!validateField()) {
					return;
				} else {
					//暂存无提示
					submitForm2();
				}
			}

			function pre() {
				updateStepNumber("one");
				window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}

			function fun1() {
				//选中的子节点
				supplierRegist();
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
			//校验基本信息 不能为空的字段
			function validateField() {
				 var jobExperiences = $("#jobExperiences").val();
				if(!jobExperiences) {
					layer.msg("请填写主要工作经历!", {
						offset: ['300px', '750px']
					});
					return false;
				} 
				if(jobExperiences != "" && jobExperiences.length > 999) {
					layer.msg("工作经历不能超过999字!", {
						offset: ['300px', '750px']
					});
					return false;
				}

				/* var academicAchievement = $("#academicAchievement").val();
				if(!academicAchievement) {
					layer.msg("请填写专业学术成果!", {
						offset: ['300px', '750px']
					});
					return false;
				}
				if(academicAchievement != "" && academicAchievement.length > 999) {
					layer.msg("专业学术成果不能超过999字!", {
						offset: ['300px', '750px']
					});
					return false;
				} */

				/* var reviewSituation = $("#reviewSituation").val();
				if(!reviewSituation) {
					layer.msg("请填写参加军队地方采购评审情况!", {
						offset: ['300px', '750px']
					});
					return false;
				}
				if(reviewSituation != "" && reviewSituation.length > 999) {
					layer.msg("参加军队地方采购评审情况不能超过999字!", {
						offset: ['300px', '750px']
					});
					return false;
				} */

				/* var avoidanceSituation = $("#avoidanceSituation").val();
				if(!avoidanceSituation) {
					layer.msg("请填写需要申请回避的情况!", {
						offset: ['300px', '750px']
					});
					return false;
				}
				if(avoidanceSituation != "" && avoidanceSituation.length > 999) {
					layer.msg("需要申请回避的情况不能超过999字!", {
						offset: ['300px', '750px']
					});
					return false;
				} */
				return true;
			}

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
			$(function() {
				$("textarea").bind("change", submitformExpert);
			});

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
			<input type="hidden" value="${errorMap.expertsFrom}" id="error10">
			<input type="hidden" value="${errorMap.unitAddress}" id="error11">
			<input type="hidden" value="${errorMap.telephone}" id="error12">
			<input type="hidden" value="${errorMap.mobile}" id="error13">
			<input type="hidden" value="${errorMap.healthState}" id="error14">
			<input type="hidden" value="${errorMap.mobile2}" id="error15">
			<input type="hidden" value="${errorMap.idNumber2}" id="error16">
			<input type="hidden" id="categoryId" name="categoryId" value="" />
			<input type="hidden" id="expertsTypeId" name="expertsTypeId" value="" />
			<input type="hidden" name="token2" value="<%=tokenValue%>" />
			<div id="reg_box_id_4" class="container clear margin-top-30 yinc">
			 <div class="col-md-12 col-xs-12 col-sm-12 p0 mb10">
				<h2 class="step_flow">
					<span id="ty1" class="new_step current fl"  onclick='pre()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span id="ty2" class="new_step current fl"><i class="">2</i><div class="line"></div> <span class="step_desc_01">经历经验</span> </span>
					<span id="sp7" class="new_step fl"><i class="">3</i><div class="line"></div> <span class="step_desc_02">专家类别</span> </span>
					<span id="ty6" class="new_step fl"><i class="">4</i><div class="line"></div> <span class="step_desc_01">参评类别</span> </span>
					<span id="ty3" class="new_step fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">采购机构</span> </span> 
					<span id="ty4" class="new_step fl"><i class="">6</i><div class="line"></div> <span class="step_desc_01">承诺书和申请表</span> </span> 
					<span id="ty5" class="new_step fl new_step_last"><i class="">7</i> <span class="step_desc_02">提交审核</span> </span> 
					<div class="clear"></div>
				</h2>
			  </div>
				<div class="col-md-12 col-xs-12 col-sm-12 container_box p0_15 clear">
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>1</i><font color=red>*</font> 主要工作经历</h2>
						<ul class="ul_list">
							<li>
								<textarea <c:if test="${fn:contains(errorField,'主要工作经历')}">onmouseover="errorMsg('主要工作经历')"</c:if> rows="10" name="jobExperiences" id="jobExperiences" style='height: 150px; width: 100%; resize: none; <c:if test="${fn:contains(errorField,'主要工作经历')}">border: 1px solid #ef0000;</c:if>' placeholder="包括时间、工作单位、职务、工作内容等">${expert.jobExperiences}</textarea>
							</li>
						</ul>
					</div>
					<!-- 专业学术成果 -->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>2</i><font color=red></font> 专业学术成果</h2>
						<ul class="ul_list">
							<li>
								<textarea <c:if test="${fn:contains(errorField,'专业学术成果')}">onmouseover="errorMsg('专业学术成果')"</c:if> rows="10" name="academicAchievement" id="academicAchievement" style='height: 150px; width: 100%; resize: none; <c:if test="${fn:contains(errorField,'专业学术成果')}">border: 1px solid #ef0000;</c:if>' placeholder="">${expert.academicAchievement}</textarea>
							</li>
						</ul>
					</div>
					<!-- 获奖证书-->
					 <div class="padding-top-10 clear">
						<h2 class="count_flow"><i>3</i><font color=red></font>获奖证书(限国家科技进步三等或军队科技进步二等以上奖项)</h2>
							<div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0" <c:if test="${fn:contains(errorField,'学位证书')}">style="border: 1px solid #ef0000;" onmouseover="errorMsg('学位证书')"</c:if>>
								<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="expert4" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7,expert8" multiple="true" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_DEGREE_TYPEID}" auto="true" />
								<u:show showId="show4" groups="show1,show2,show3,show4,show5,show6,show7,show8" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_DEGREE_TYPEID}" />
							</div>
					 </div> 
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>4</i><font color=red></font> 参加军队地方采购评审情况</h2>
						<ul class="ul_list">
							<li>
								<textarea <c:if test="${fn:contains(errorField,'参加军队地方采购评审情况')}">onmouseover="errorMsg('参加军队地方采购评审情况')"</c:if> rows="10" name="reviewSituation" id="reviewSituation" style='height: 150px; width: 100%; resize: none; <c:if test="${fn:contains(errorField,'参加军队地方采购评审情况')}">border: 1px solid #ef0000;</c:if>' placeholder="">${expert.reviewSituation}</textarea>
							</li>
						</ul>
					</div>
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>5</i><font color=red></font> 需要申请回避的情况</h2>
						<ul class="ul_list">
							<li>
								<textarea <c:if test="${fn:contains(errorField,'需要申请回避的情况')}">onmouseover="errorMsg('需要申请回避的情况')"</c:if> rows="10" name="avoidanceSituation" id="avoidanceSituation" style='height: 150px; width: 100%; resize: none; <c:if test="${fn:contains(errorField,'需要申请回避的情况')}">border: 1px solid #ef0000;</c:if>' placeholder="近3年内,存在劳动关系的供应商,或者担任过供应商的董事、监事,或者是供应商的控股股东（实际控制人）；与供应商法定代表人或者主要负责人有夫妻、直系血亲、三代以内旁系血亲或者近姻亲关系；发生过法律纠纷的供应商；其它需要回避的情况。">${expert.avoidanceSituation}</textarea>
							</li>
						</ul>
					</div>
					<div class="btmfix">
						<div class="mt15 tc">
							<button class="btn" type="button" onclick="pre()">上一步</button>
							<button class="btn" onclick='zc()' type="button">暂存</button>
							<button class="btn" type="button" onclick='fun1()'>下一步</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<jsp:include page="/index_bottom.jsp"></jsp:include>
	</body>

</html>