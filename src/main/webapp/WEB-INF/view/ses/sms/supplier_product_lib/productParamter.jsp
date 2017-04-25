<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
<script type="text/javascript">

</script>
</head>
<body>
	<h2 class="count_flow"><i>2</i>产品参数信息</h2>
    <ul class="ul_list" id="paramter">
		<c:forEach items="${ categoryParamlist }" var="categoryParam" varStatus="vs">
	        <c:if test="${'字符' eq categoryParam.paramTypeName }">
				<li class="col-md-3 col-sm-6 col-xs-12">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
	              <c:if test="${ categoryParam.paramRequired == 1 }">
	              	<div class="star_red">*</div>${ categoryParam.paramName }（字符）：</span>
	              </c:if>
	              <c:if test="${ categoryParam.paramRequired == 0 }">
	              	${ categoryParam.paramName }（字符）：</span>
	              </c:if>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
	              	<input name="productArguments[${ vs.index }].paramName" value='${ categoryParam.paramName }' type="hidden">
	                <input name="productArguments[${ vs.index }].categoryParameterId" value='${ categoryParam.id }' type="hidden">
	                <input name="productArguments[${ vs.index }].parameterType" value='${ categoryParam.paramTypeName }' type="hidden">
	                <input name="productArguments[${ vs.index }].parameterValue" value='' type="text">
	                <input name="productArguments[${ vs.index }].required" value='${ categoryParam.paramRequired }' type="hidden">
	                <span class="add-on">i</span>
	                <span class="input-tip">${ categoryParam.paramName }</span>
	                <div class="cue"></div>
	              </div>
	            </li>
	        </c:if>
	        <c:if test="${'日期' eq categoryParam.paramTypeName }">
				<li class="col-md-3 col-sm-6 col-xs-12">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
	              <c:if test="${ categoryParam.paramRequired == 1 }">
	              	<div class="star_red">*</div>${ categoryParam.paramName }（日期）：</span>
	              </c:if>
	              <c:if test="${ categoryParam.paramRequired == 0 }">
	              	${ categoryParam.paramName }（日期）：</span>
	              </c:if>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
	              	<input name="productArguments[${ vs.index }].paramName" value='${ categoryParam.paramName }' type="hidden">
	                <input name="productArguments[${ vs.index }].categoryParameterId" value='${ categoryParam.id }' type="hidden">
	                <input name="productArguments[${ vs.index }].parameterType" value='${ categoryParam.paramTypeName }' type="hidden">
	                <input name="productArguments[${ vs.index }].parameterValue" id="productArguments[${ vs.index }].parameterValue" class="Wdate w200 mb0" type="text" id="d17" onfocus="WdatePicker({firstDayOfWeek:1})"/>
	                <input name="productArguments[${ vs.index }].required" value='${ categoryParam.paramRequired }' type="hidden">
	                <span class="add-on">i</span>
	                <span class="input-tip">${ categoryParam.paramName }</span>
	                <div class="cue"></div>
	              </div>
	            </li>
	        </c:if>
	        <c:if test="${'正整数' eq categoryParam.paramTypeName }">
				<li class="col-md-3 col-sm-6 col-xs-12">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
	              <c:if test="${ categoryParam.paramRequired == 1 }">
	              	<div class="star_red">*</div>${ categoryParam.paramName }（正整数）：</span>
	              </c:if>
	              <c:if test="${ categoryParam.paramRequired == 0 }">
	              	${ categoryParam.paramName }（正整数）：</span>
	              </c:if>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
	              	<input name="productArguments[${ vs.index }].paramName" value='${ categoryParam.paramName }' type="hidden">
	                <input name="productArguments[${ vs.index }].categoryParameterId" value='${ categoryParam.id }' type="hidden">
	                <input name="productArguments[${ vs.index }].parameterType" value='${ categoryParam.paramTypeName }' type="hidden">
	                <input name="productArguments[${ vs.index }].parameterValue" value='' type="text">
	                 <input name="productArguments[${ vs.index }].required" value='${ categoryParam.paramRequired }' type="hidden">
	                <span class="add-on">i</span>
	                <span class="input-tip">${ categoryParam.paramName }</span>
	                <div class="cue"></div>
	              </div>
	            </li>
	        </c:if>
	        <c:if test="${'附件' eq categoryParam.paramTypeName }">
	            <li class="col-md-3 col-sm-6 col-xs-12">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
	              <c:if test="${ categoryParam.paramRequired == 1 }">
	              	<div class="star_red">*</div>${ categoryParam.paramName }（附件）：</span>
	              </c:if>
	              <c:if test="${ categoryParam.paramRequired == 0 }">
	              	${ categoryParam.paramName }（附件）：</span>
	              </c:if>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 " >
	              	<% String paramuuid = UUID.randomUUID().toString().toUpperCase().replace("-", ""); %>
	              	<input name="productArguments[${ vs.index }].paramName" value='${ categoryParam.paramName }' type="hidden">
	              	<input name="productArguments[${ vs.index }].categoryParameterId" value='${ categoryParam.id }' type="hidden">
	                <input name="productArguments[${ vs.index }].parameterType" value='${ categoryParam.paramTypeName }' type="hidden">
	                <input name="productArguments[${ vs.index }].parameterValue" value='<%=paramuuid %>' type="hidden">
	                <input name="productArguments[${ vs.index }].required" value='${ categoryParam.paramRequired }' type="hidden">
	                <u:upload id="param_picture${ vs.index }" businessId="<%=paramuuid %>" sysKey="${ sysKey }" typeId="${ typeId }" buttonName="附件上传" auto="true"/>
	              	<u:show showId="param_picture${ vs.index }" businessId="<%=paramuuid %>" sysKey="${ sysKey }" typeId="${typeId }" />
	                <span class="input-tip">${ categoryParam.paramName }</span>
	                <div class="cue"></div>
	              </div>
	            </li>
	        </c:if>
		</c:forEach>
	</ul>
</body>
</html>