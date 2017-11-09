<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/reg_head.jsp"%>
		<c:if test="${expert.status == 3}">
			<%@ include file="/WEB-INF/view/ses/ems/expert/expert_purchase_dept.jsp"%>
		</c:if>
		<title>评审专家注册</title>
		<script src="${pageContext.request.contextPath}/js/ems/expert/validate_expert_basic_info.js"></script>
		<script src="${pageContext.request.contextPath}/js/ems/expert/validate_regester.js"></script>
		<%
//表单标示
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+""; 
session.setAttribute("tokenSession", tokenValue);
%>
		<script type="text/javascript">
			function pre3(name, i, position) {
				updateStepNumber("three");
				window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}

			function pre6(name, i, position) {
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
							window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
						}
					}
				});
			}

			function pre2(name, i, position) {
				updateStepNumber("two");
				window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}

			function pre1(name, i, position) {
				updateStepNumber("one");
				window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
			}
			function pre7(name, i, position) {
	            updateStepNumber("seven");
	            window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
	        }
			//下载
			function downloadTable() {
				$("#formExpert").attr("action", "${pageContext.request.contextPath}/expert/download.html");
				$("#formExpert").submit();
			}
			//下载
			function downloadBook() {
				$("#formExpert").attr("action", "${pageContext.request.contextPath}/expert/downloadBook.html");
				$("#formExpert").submit();
			}

			function four(att) {
				if(att == '1' || att == 'ok') {
					updateStepNumber("five");
					window.location.href = "${pageContext.request.contextPath}/expert/toAddBasicInfo.html?userId=${userId}";
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

			function initData() {
				var expertsType = "${expert.expertsTypeId}";
				if(expertsType == '1') {
					$("#expertsType").html("技术");
				} else if(expertsType == '3') {
					$("#expertsType").html("经济");
				}
				$.ajax({
					url: "${pageContext.request.contextPath}/expert/initData.do",
					data: {
						"expertId": $("#id").val()
					},
					async: false,
					dataType: "json",
					success: function(response) {
						$("#tSex").html(response.gender);
						$("#tFace").html(response.politicsStatus);
						$("#Taddress").html(response.address);
						$("#tHight").html(response.hightEducation);
						$("#idType").html(response.idType);
						$("#expertsFrom").html(response.expertsFrom);
						$("#expertsTypeId").html(response.expertsTypeId);
						$("#degree").html(response.degree);
					}
				});
			}
		</script>
	</head>

	<body onload="initData()">
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
			<input type="hidden" name="token2" value="<%=tokenValue%>" />
			<div id="reg_box_id_6" class="container clear margin-top-30 yinc">
			  <div class="col-md-12 col-xs-12 col-sm-12 p0 mb10">
				<h2 class="step_flow">
					<span id="dy1" class="new_step current fl" onclick='pre1()'><i class="">1</i><div class="line"></div> <span class="step_desc_02">基本信息</span> </span> 
					<span id="sp7" class="new_step current fl" onclick='pre7()'><i class="">2</i><div class="line"></div> <span class="step_desc_01">专家类别</span> </span>
					<span id="ty6" class="new_step current fl" onclick='pre6()'><i class="">3</i><div class="line"></div> <span class="step_desc_02">产品类别</span> </span>
					<span id="dy3" class="new_step current fl" onclick='pre3()'><i class="">4</i><div class="line"></div> <span class="step_desc_01">采购机构</span> </span> 
					<span id="dy4" class="new_step current fl"><i class="">5</i><div class="line"></div> <span class="step_desc_02">承诺书和申请表</span> </span> 
					<span id="dy5" class="new_step fl new_step_last"><i class="">6</i> <span class="step_desc_01">提交审核</span> </span> 
					<div class="clear"></div>
		  		</h2>
		  	  </div>
		  	  
				<div class="tab-content padding-top-20 clear">
					<div class="headline-v2">
						<h2> 申请表和承诺书下载 </h2>
					</div>
					<div>
						<div class="margin-top-30"></div>
						<div align="left">
							
						    <ul class="list-unstyled f14">					
								<li class="col-md-6 col-sm-12 col-xs-12 mb25 pl10">
								    <span class="col-md-5 col-sm-12 col-xs-12 padding-left-5">下载《军队评审专家承诺书》</span> 
								    <a class="mt3 color7171C6" onclick='downloadBook()' href="javascript:void(0)"><i class="download mr5"></i></a> 
							    </li>
						    </ul>
						    <ul class="list-unstyled f14">					
								<li class="col-md-6 col-sm-12 col-xs-12 mb25 pl10">
								    <span class="col-md-6 col-sm-12 col-xs-12 padding-left-5">下载 《军队评审专家入库申请表》</span> 
								    <a class="mt3 color7171C6" onclick='downloadTable()' href="javascript:void(0)"><i class="download mr5"></i></a>
							    </li>
						    </ul>
						</div>
					
						<div class="btmfix">
							<div class="mt15 tc">
								<button class="btn" type="button" onclick="pre3()">上一步</button>
								<button class="btn" type="button" onclick="four('ok')">下一步</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<jsp:include page="/index_bottom.jsp"></jsp:include>
	</body>
	<!-- 168 行 原位置 -->
	<!--	<table class="table table-bordered table-condensed ">
							<div class="margin-top-30"></div>
							<tr>
								<td width="12%" class="bggrey">姓名</td>
								<td width="25%" id="tName">${expert.relName}</td>
								<td width="12%" class="bggrey">性别</td>
								<td width="25%" id="tSex"></td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">出生日期</td>
								<td width="25%" id="tBirthday">
									<fmt:formatDate value="${expert.birthday}" pattern="yyyy-MM-dd" />
								</td>
								<td width="12%" class="bggrey">政治面貌</td>
								<td width="25%" id="tFace"></td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">所在地区</td>
								<td width="25%" id="Taddress"></td>
								<td width="12%" class="bggrey">专业技术职称/执业资格</td>
								<td width="25%" id="tHey">${expert.professTechTitles}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">居民身份证号码</td>
								<td width="25%">${expert.idCardNumber}</td>
								<td width="12%" class="bggrey">民族</td>
								<td width="25%">${expert.nation}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">健康状况</td>
								<td width="25%">${expert.healthState}</td>
								<td width="12%" class="bggrey">所在单位</td>
								<td width="25%" id="tTimeStartWork">${expert.workUnit}</td>
							</tr>

							<tr>
								<td width="12%" class="bggrey">缴纳社会保险证明</td>
								<td width="25%">${expert.coverNote}</td>
								<td width="12%" class="bggrey">单位邮编</td>
								<td width="25%">${expert.postCode}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">单位地址</td>
								<td colspan="3">${expert.unitAddress}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">军队人员身份证件类型</td>
								<td width="25%" id="idType"></td>
								<td width="12%" class="bggrey">证件号码</td>
								<td width="25%">${expert.idNumber}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">现任职务</td>
								<td width="25%">${expert.atDuty}</td>
								<td width="12%" class="bggrey">从事专业</td>
								<td width="25%">${expert.major}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">从事专业起始年度</td>
								<td width="25%">
									<fmt:formatDate value="${expert.timeStartWork}" pattern="yyyy-MM" />
								</td>
								<td width="12%" class="bggrey">专家类型</td>
								<td width="25%" id="expertsFrom"></td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">专业技术职称/执业资格</td>
								<td width="25%">${expert.professTechTitles}</td>
								<td width="12%" class="bggrey">取得技术职称时间</td>
								<td width="25%">
									<fmt:formatDate value="${expert.makeTechDate}" pattern="yyyy-MM" />
								</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">毕业院校及专业</td>
								<td colspan="3">${expert.graduateSchool}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">专家类别</td>
								<td width="25%" id="expertsTypeId"></td>
								<td width="12%" class="bggrey">最高学历</td>
								<td width="25%" id="tHight"></td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">最高学位</td>
								<td width="25%" id="degree"></td>
								<td width="12%" class="bggrey">个人邮箱</td>
								<td width="25%">${expert.email}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">移动电话</td>
								<td width="25%">${expert.mobile}</td>
								<td width="12%" class="bggrey">固定电话</td>
								<td width="25%">${expert.telephone}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">传真电话</td>
								<td colspan="3">${expert.fax}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">参评的产品类别 </td>
								<td colspan="3">${expert.productCategories}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">主要工作经历</td>
								<td colspan="3">${expert.jobExperiences}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">专业学术成果</td>
								<td colspan="3">${expert.academicAchievement}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">参加军队地方采购评审情况 </td>
								<td colspan="3">${expert.reviewSituation}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">需要申请回避的情况 </td>
								<td colspan="3">${expert.avoidanceSituation}</td>
							</tr>
						</table>  -->
</html>
