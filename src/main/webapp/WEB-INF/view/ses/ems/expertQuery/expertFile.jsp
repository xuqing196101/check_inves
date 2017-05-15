<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
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
		<jsp:include page="navigation.jsp" flush="ture"/>
		
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgwhite">
						<li class="">
							<a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18" onclick="jump('basicInfo');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertType');">专家类别</a>
						</li>
						<li class="">
							<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="jump('product');">产品类别</a>
						</li>
						<li class="active">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertFile');">承诺书和申请表</a>
						</li>
					</ul>
					
					<ul class="ul_list hand count_flow">
						<li class="col-md-6 p0 mt10 mb25">
							<span class="col-md-5 padding-left-5">军队评审专家承诺书：</span>
								<up:show showId="14" delete="false" businessId="${expertId}" sysKey="${expertKey}" typeId="14" />
						</li>
						<li class="col-md-6 p0 mt10 mb25">
							<span class="col-md-5 padding-left-5">军队评审专家入库申请表：</span>
								<up:show showId="13"  delete="false" businessId="${expertId}" sysKey="${expertKey}" typeId="13" />
						</li>
					</ul>
					<div class="tc mt20 clear col-md-12 col-sm-12 col-xs-12">
						<c:if test="${sign == 1}">
							<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expert/findAllExpert.html">返回</a>
						</c:if>
						<c:if test="${sign == 2}">
							<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expertQuery/list.html">返回</a>
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