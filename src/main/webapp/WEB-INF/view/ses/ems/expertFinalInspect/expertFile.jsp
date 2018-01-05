<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
        <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertFinalInspect/merge_jump.js"></script>
		<script type="text/javascript">
			$(function () {
			    // 导航栏显示
                $("#reverse_of_four").attr("class","active");
                $("#reverse_of_four").removeAttr("onclick");
            })
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/finalInspect/expertAttachment.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/finalInspect/product.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
		<script type="text/javascript">
			function trim(str){ //删除左右两端的空格
				return str.replace(/(^\s*)|(\s*$)/g, "");
				}

		
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<c:if test="${sign == 1}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=1')">专家初审</a>
						</li>
					</c:if>
					<c:if test="${sign == 2}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAgainAudit/findBatchDetailsList.html?batchId=${batchId}')">专家复审</a>
						</li>
					</c:if>
					<c:if test="${sign == 3}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=3')">专家复查</a>
						</li>
					</c:if>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<%@include file="/WEB-INF/view/ses/ems/expertFinalInspect/common_jump.jsp" %>
					<ul class="ul_list hand count_flow">
						<li class="col-md-6 col-sm-6 col-xs-12 p0 mt10 mb25">
							<span <c:if test="${fn:contains(fileModify,'14')}"> style="border: 1px solid #FF8C00;"</c:if> class="col-md-5 padding-left-5" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="application">军队评审专家承诺书：</span>
								<up:show showId="14" delete="false" businessId="${expertId}" sysKey="${expertKey}" typeId="14" />
								<a style="visibility:hidden" id="application1" class='abolish'><img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
								<c:if test="${fn:contains(conditionStr,'军队评审专家承诺书')}">
								  <a id="application11" class='abolish'>
								    <img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
								  </a>
								</c:if>
						</li>
						<li class="col-md-6 col-sm-6 col-xs-12  p0 mt10 mb25">
							<span <c:if test="${fn:contains(fileModify,'13')}"> style="border: 1px solid #FF8C00;"</c:if> class="col-md-5 padding-left-5" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="contract">军队评审专家入库申请表：</span>
								<up:show showId="13"  delete="false" businessId="${expertId}" sysKey="${expertKey}" typeId="13" />
								<a style="visibility:hidden" id="contract1" class='abolish'><img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
								<c:if test="${fn:contains(conditionStr,'军队评审专家入库申请表')}"> 
								  <a id="contract11" class='abolish'>
								    <img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
								  </a>
								</c:if>
						</li>
					</ul>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12  add_regist tc m_btn_ab">
					<a class="btn" type="button" onclick="lastStep();">上一步</a>
					<c:if test="${expert.finalInspectCount>0}"><a class="btn" type="button" onclick="tojump('expertAttachment',1)">下一步</a></c:if>
					<c:if test="${expert.finalInspectCount==null||expert.finalInspectCount<=0}"><a class="btn" type="button" onclick="nextStep();">下一步</a></c:if>
					<a class="btn" type="button" onclick="jump('expertAttachment')">转至复查</a>
				</div>
				
			</div>
		</div>

		<input value="${expertId}" id="expertId" type="hidden">

		<form id="form_id" action="" method="post">
			<input name="expertId" value="${expertId}" type="hidden">
			<input id="over" name="over" value="${over}" type="hidden">
			<input name="sign" value="${sign}" type="hidden">
			<input name="batchId" value="${batchId}" type="hidden">
			<input name="isReviewRevision" value="${isReviewRevision}" type="hidden">
			<input name="isCheck" value="${isCheck}" type="hidden">
			 <input id="notCount" name="notCount" value="${notCount}" type="hidden">
			<input id="finalInspectNumber" name="finalInspectNumber" value="${expert.finalInspectCount==null?1:expert.finalInspectCount+1}" type="hidden">
		</form>
        <input value="${status}" id="status" type="hidden">
	</body>

</html>