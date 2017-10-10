<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<%@ include file="/WEB-INF/view/ses/ems/expertQuery/common.jsp"%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertQuery/merge_jump.js"></script>
		<script type="text/javascript">
			function backOld() {
				window.location.href = "${pageContext.request.contextPath}/expert/findAllExpert.html";
			}

			function initData() {
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
						$("#expertsType").html(response.expertsTypeId);
						$("#degree").html(response.degree);
					}
				});
			}
		</script>
	</head>

	<body onload="initData()">
		<!--面包屑导航开始-->
		<%-- <jsp:include page="navigation.jsp" flush="ture" /> --%>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<li>
						<c:if test="${sign == 1}">
							<a  href="javascript:jumppage('${pageContext.request.contextPath}/expert/findAllExpert.html')">全部专家查询</a>
						</c:if>
						<c:if test="${sign == 2}">
							<a  href="javascript:jumppage('${pageContext.request.contextPath}/expertQuery/list.html')">入库专家查询</a>
						</c:if>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgwhite">
						<li class="active">
							<a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18" onclick="jump('basicInfo');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertType');">专家类别</a>
						</li>
						<li class="">
							<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="jump('product');">产品类别</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertFile');">承诺书和申请表</a>
						</li>
						<li class="">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('auditInfo');">审核意见</a>
            </li>
					</ul>

					<input type="hidden" name="id" id="id" value="${expert.id}" />
					<ul class="ul_list hand count_flow">
						<h2 class="count_flow"><i>1</i>专家个人信息</h2>
						<table class="table table-bordered table-condensed ">
							<tr>
								<td width="12%" class="bggrey">专家姓名</td>
								<td width="25%">${expert.relName}</td>

								<td width="12%" class="bggrey">近期免冠彩色证件照</td>
								<td width="25%">
									<up:show showId="show50" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="50" />
								</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">性别</td>
								<td width="25%" id="tSex"></td>
								<td width="12%" class="bggrey">出生日期</td>
								<td width="25%" id="tBirthday">
									<fmt:formatDate value="${expert.birthday}" pattern="yyyy-MM-dd" />
								</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">政治面貌</td>
								<td width="25%" id="tFace"></td>
								<td width="12%" class="bggrey">民族</td>
								<td width="25%">${expert.nation}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">居民身份证号码</td>
								<td width="25%">${expert.idCardNumber}</td>
								<td width="12%" class="bggrey">身份证复印件</td>
								<td width="25%">
									<up:show showId="show3" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="3" />
								</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">健康状态</td>
								<td width="25%">${expert.healthState}</td>
								<td width="12%" class="bggrey">固定电话</td>
								<td width="25%">${expert.telephone}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">传真电话</td>
								<td width="25%">${expert.fax}</td>
								<td width="12%" class="bggrey">个人邮箱</td>
								<td width="25%">${expert.email}</td>
							</tr>

							<c:if test="${froms eq 'LOCAL'}">
								<tr>
									<td width="12%" class="bggrey">是否缴纳社会保险</td>
									<td width="25%">
										<c:if test="${expert.coverNote eq '1'}">是</c:if>
										<c:if test="${expert.coverNote eq '2'}">否</c:if>
									</td>
									<c:if test="${expert.coverNote eq '1'}">
										<td width="12%" class="bggrey">缴纳社保证明</td>
										<td width="25%">
											<up:show showId="show2" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="1" />
										</td>
									</c:if>
									<c:if test="${expert.coverNote eq '2'}">
										<td width="12%" class="bggrey">退休证书或退休证明</td>
										<td width="25%">
											<up:show showId="show2" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="2" />
										</td>
									</c:if>
								</tr>		
							</c:if>
							<c:if test="${froms eq 'ARMY'}">
								<tr>
									<td width="12%" class="bggrey">军队人员身份证件类型</td>
									<td width="25%">${idType }</td>
									<td width="12%" class="bggrey">证件号码</td>
									<td width="25%">${expert.idNumber }</td>
								</tr>
								<tr>
									<td width="12%" class="bggrey">军队人员身份证件</td>
									<td width="25%">
										<up:show showId="show1" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="12" />
									</td>
								</tr>
							</c:if>
						</table>
					</ul>
					<ul class="ul_list hand count_flow">
						<h2 class="count_flow"><i>2</i>专业信息（包括学历和专业）</h2>
						<table class="table table-bordered table-condensed ">
							<tr>
								<td width="12%" class="bggrey">所在单位</td>
								<td width="25%">${expert.workUnit}</td>
								<td width="12%" class="bggrey">地区</td>
								<td width="25%">${parentName }${sonName }</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">单位地址</td>
								<td width="25%">${expert.unitAddress}</td>
								<td width="12%" class="bggrey">单位邮编</td>
								<td width="25%">${expert.postCode}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">现任职务</td>
								<td width="25%">${expert.atDuty}</td>
								<td width="12%" class="bggrey">从事专业</td>
								<td width="25%">${expert.major}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">从事专业起始年月</td>
								<td width="25%">
									<fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle='default' pattern='yyyy-MM-dd' />
								</td>
								<c:if test="${expert.teachTitle eq '2'}">
	                <td width="12%" class="bggrey">有无专业技术职称</td>
	                <td width="25%">无</td>
                </c:if>
							</tr>
							<c:if test="${froms eq 'LOCAL'}">
							  <tr>
                  <td width="12%" class="bggrey">专家技术职称</td>
                  <td width="25%">${expert.professTechTitles}</td>
                  <td width="12%" class="bggrey">专业技术职称证书</td>
                  <td width="25%">
                    <up:show showId="show4" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="4" />
                  </td>
                </tr>
                <tr>
                  <td width="12%" class="bggrey">取得技术职称时间</td>
                  <td width="25%">
                    <fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM-dd' />
                  </td>
                </tr>
							</c:if>
							<c:if test="${froms eq 'ARMY'}">
						    <c:if test="${expert.teachTitle eq '1'}">
	                <tr>
		                 <td width="12%" class="bggrey">专家技术职称</td>
		                 <td width="25%">${expert.professTechTitles}</td>
		                 <td width="12%" class="bggrey">专业技术职称证书</td>
		                 <td width="25%">
		                   <up:show showId="show4" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="4" />
		                 </td>
	                </tr>
	                <tr>
	                  <td width="12%" class="bggrey">取得技术职称时间</td>
	                  <td width="25%">
	                    <fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM-dd' />
	                  </td>
	                </tr>
	               </c:if>
							</c:if>
							
							<tr>
								<td width="12%" class="bggrey">毕业院校及专业</td>
								<td width="25%">${expert.graduateSchool}</td>
								<td width="12%" class="bggrey">最高学历</td>
								<td width="25%">${hightEducation}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">毕业证书</td>
								<td width="25%">
									<up:show showId="show5" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="5" />
								</td>
								<td width="12%" class="bggrey">最高学位</td>
								<td width="25%">${degree}</td>
							</tr>
							<tr>
								<td width="12%" class="bggrey">学位证书</td>
								<td width="25%">
									<up:show showId="show6" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="6" />
								</td>
								<td width="12%" class="bggrey">参加工作时间</td>
								<td width="25%">
									<fmt:formatDate type='date' value='${expert.timeToWork}' dateStyle='default' pattern='yyyy-MM-dd' />
								</td>
							</tr>
						</table>
					</ul>
					<ul class="ul_list hand count_flow">
						<h2 class="count_flow"><i>3</i>推荐信</h2>
						<table class="table table-bordered table-condensed ">
							<tr>
								<td width="25%">
									<c:if test="${expert.isReferenceLftter eq '2'}">否</c:if>
									<c:if test="${expert.isReferenceLftter eq '1'}">
										<up:show showId="show8" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="8" />
									</c:if>
								</td>
							</tr>
						</table>
					</ul>
					<ul class="ul_list hand count_flow">
						<h2 class="count_flow"><i>4</i>主要工作经历</h2>
						<table class="table table-bordered table-condensed ">
							<tr>
								<td>${expert.jobExperiences}</td>
							</tr>
						</table>
						<table class="table table-bordered table-condensed ">
							<tr>
								<td class="bggrey" width="50%">获奖证书(限国家科技进步三等或军队科技进步二等以上奖项)</td>
								<td>
									<up:show showId="show7" delete="false" businessId="${sysId}" sysKey="${expertKey}" typeId="7" />
								</td>
							</tr>
						</table>
					</ul>
					<ul class="ul_list hand count_flow">
						<h2 class="count_flow"><i>5</i>专业学术成果</h2>
						<table class="table table-bordered table-condensed ">
							<tr>
								<td>${expert.academicAchievement}</td>
							</tr>
						</table>
					</ul>
					<ul class="ul_list hand count_flow">
						<h2 class="count_flow"><i>6</i>参加军队地方采购评审情况</h2>
						<table class="table table-bordered table-condensed ">
							<tr>
								<td>${expert.reviewSituation}</td>
							</tr>
						</table>
					</ul>
					<ul class="ul_list hand count_flow">
						<h2 class="count_flow"><i>7</i>需要申请回避的情况</h2>
						<table class="table table-bordered table-condensed ">
							<tr>
								<td>${expert.avoidanceSituation}</td>
							</tr>
						</table>
					</ul>
					<div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">
						<%-- <c:if test="${ empty reqType }"> --%>
							<c:if test="${sign == 1}">
								<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expert/findAllExpert.html">返回列表</a>
							</c:if>
							<c:if test="${sign == 2}">
								<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expertQuery/list.html">返回列表</a>
							</c:if>
						<%-- </c:if> --%>
						<%-- <c:if test="${not empty reqType }">
								<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expertQuery/readOnlyList.html?address=${expertAnalyzeVo.address}&expertsTypeId=${expertAnalyzeVo.expertsTypeId}&expertsFrom=${expertAnalyzeVo.expertsFrom}&orgId=${expertAnalyzeVo.orgId}">返回列表</a>
						</c:if> --%>
					</div>
				</div>
			</div>
		</div>
		<form id="form_id" action="" method="post">
			<input name="expertId" value="${expertId}" type="hidden">
			<input name="sign" value="${sign}" type="hidden">
		</form>
	</body>

</html>