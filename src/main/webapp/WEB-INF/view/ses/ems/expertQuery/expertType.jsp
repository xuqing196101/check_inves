<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript">
			$(function() {
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
		</script>
		<script type="text/javascript">
			function jump(str){
			  var action;
			  if(str == "basicInfo") {
					action = "${pageContext.request.contextPath}/expertQuery/view.html";
				}
			  if(str=="expertType"){
			     action ="${pageContext.request.contextPath}/expertQuery/expertType.html";
			  }
			  if(str=="product"){
			    action = "${pageContext.request.contextPath}/expertQuery/product.html";
			  }
			  if(str=="expertFile"){
			    action = "${pageContext.request.contextPath}/expertQuery/expertFile.html";
			  }
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<%-- <jsp:include page="navigation.jsp" flush="ture" /> --%>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0)"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<li>
						<c:if test="${sign == 1}">
							<a  href="${pageContext.request.contextPath}/expert/findAllExpert.html">全部专家查询</a>
						</c:if>
						<c:if test="${sign == 2}">
							<a  href="${pageContext.request.contextPath}/expertQuery/list.html">入库专家查询</a>
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
						<li class="">
							<a aria-expanded="fale" href="#tab-1" data-toggle="tab" class="f18" onclick="jump('basicInfo');">基本信息</a>
						</li>
						<li class="active">
							<a aria-expanded="fale" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertType');">专家类别</a>
						</li>
						<li class="">
							<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="jump('product');">产品类别</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertFile');">承诺书和申请表</a>
						</li>
					</ul>
					<!-- 专家专业信息 -->
					<ul class="ul_list count_flow">
						<li>
							<div>
								<c:forEach items="${spList}" var="sp">
									<span class="margin-left-30 hand" ><input type="checkbox"  disabled="disabled"  name="chkItem_1" value="${sp.id}" />${sp.name}技术 </span>
								</c:forEach>
								<c:forEach items="${jjList}" var="jj">
									<span  class="margin-left-30 hand" ><input type="checkbox"  disabled="disabled" name="chkItem_2"  value="${jj.id}" />${jj.name} </span>
								</c:forEach>
								<c:if test="${isProject eq 'project'}">
									<c:forEach items="${expertTitleList }" var="expertTitle" varStatus="vs">
										<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">执业资格职称：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input class="hand" value="${expertTitle.qualifcationTitle}" readonly="readonly" type="text" />
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12">
											<span class="hand">执业资格：</span>
				             				<up:show showId="expter_${vs.index+1 }" delete="false" businessId="${expertTitle.id}" sysKey="${expertKey}" typeId="9"/>
				           				</li>
										<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">取得执业资格时间：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input class="hand" value="<fmt:formatDate type='date' value='${expertTitle.titleTime}' dateStyle='default' pattern='yyyy-MM'/>" readonly="readonly" type="text" />
											</div>
										</li>
										<div class="clear"></div>
									</c:forEach>
								</c:if>
							</div>
						</li>
					</ul>
					<div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">
						<c:if test="${sign == 1}">
							<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expert/findAllExpert.html">返回列表</a>
						</c:if>
						<c:if test="${sign == 2}">
							<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expertQuery/list.html">返回列表</a>
						</c:if>
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