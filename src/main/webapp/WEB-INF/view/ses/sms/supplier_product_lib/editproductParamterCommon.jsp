<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <h2 class="count_flow"><i>2</i>产品参数信息</h2>
    <ul class="ul_list" id="paramter">
		<c:forEach items="${ smsProductInfo.smsProductArguments }" var="categoryParam" varStatus="vs">
	        <c:if test="${'字符' eq categoryParam.parameterType }">
				<li class="col-md-3 col-sm-6 col-xs-12">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
	              <c:if test="${ categoryParam.categoryParameter.paramRequired == 1 }">
	              	<div class="star_red">*</div>${ categoryParam.categoryParameter.paramName }（字符）：</span>
	              </c:if>
	              <c:if test="${ categoryParam.categoryParameter.paramRequired == 0 }">
	              	${ categoryParam.categoryParameter.paramName }（字符）：</span>
	              </c:if>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
	                <input name="productArguments[${ vs.index }].id" value='${ categoryParam.id }' type="hidden" />
	                <input name="productArguments[${ vs.index }].paramName" value='${ categoryParam.categoryParameter.paramName }' type="hidden"/>
	                <input name="productArguments[${ vs.index }].parameterValue" value='${ categoryParam.parameterValue }' type="text" />
	                <input name="productArguments[${ vs.index }].parameterType" type="hidden" value="${ categoryParam.parameterType }">
	                <input name="productArguments[${ vs.index }].required" value='${ categoryParam.categoryParameter.paramRequired }' type="hidden" />
	                <span class="add-on">i</span>
	                <span class="input-tip">${ categoryParam.categoryParameter.paramName }</span>
	                <div class="cue"></div>
	              </div>
	            </li>
	        </c:if>
	        <c:if test="${'日期' eq categoryParam.parameterType }">
				<li class="col-md-3 col-sm-6 col-xs-12">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
	              <c:if test="${ ategoryParam.categoryParameter.paramRequired == 1 }">
	              	<div class="star_red">*</div>${ categoryParam.categoryParameter.paramName }（日期）：</span>
	              </c:if>
	              <c:if test="${ categoryParam.categoryParameter.paramRequired == 0 }">
	              	${ categoryParam.categoryParameter.paramName }（日期）：</span>
	              </c:if>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
	                <input name="productArguments[${ vs.index }].id" value='${ categoryParam.id }' type="hidden">
	                <input name="productArguments[${ vs.index }].paramName" value='${ categoryParam.categoryParameter.paramName }' type="hidden"/>
	              	<input class="Wdate w200 mb0" value="<fmt:formatDate value="${ categoryParam.parameterValue }" pattern="yyyy-MM-dd"/>" type="text" id="d17" onfocus="WdatePicker({firstDayOfWeek:1})"/>
	              	<input name="productArguments[${ vs.index }].parameterType" type="hidden" value="${ categoryParam.parameterType }">
	                <input name="productArguments[${ vs.index }].required" value='${ categoryParam.categoryParameter.paramRequired }' type="hidden">
	                <span class="add-on">i</span>
	                <span class="input-tip">${ categoryParam.categoryParameter.paramName }</span>
	                <div class="cue"></div>
	              </div>
	            </li>
	        </c:if>
	        <c:if test="${'正整数' eq categoryParam.parameterType }">
				<li class="col-md-3 col-sm-6 col-xs-12">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
	              <c:if test="${ categoryParam.categoryParameter.paramRequired == 1 }">
	              	<div class="star_red">*</div>${ categoryParam.categoryParameter.paramName }（正整数）：</span>
	              </c:if>
	              <c:if test="${ categoryParam.categoryParameter.paramRequired == 0 }">
	              	${ categoryParam.categoryParameter.paramName }（正整数）：</span>
	              </c:if>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 ">
	              	<input name="productArguments[${ vs.index }].id" value='${ categoryParam.id }' type="hidden" />
	              	<input name="productArguments[${ vs.index }].paramName" value='${ categoryParam.categoryParameter.paramName }' type="hidden"/>
	                <input name="productArguments[${ vs.index }].parameterValue" value='${ categoryParam.parameterValue }' onkeyup="this.value=this.value.replace(/\D/g,'')" type="text" />
	                <input name="productArguments[${ vs.index }].parameterType" type="hidden" value="${ categoryParam.parameterType }">
	                <input name="productArguments[${ vs.index }].required" value='${ categoryParam.categoryParameter.paramRequired }' type="hidden">
	                <span class="add-on">i</span>
	                <span class="input-tip">${ categoryParam.categoryParameter.paramName }</span>
	                <div class="cue"></div>
	              </div>
	            </li>
	        </c:if>
	        <c:if test="${'附件' eq categoryParam.parameterType }">
	            <li class="col-md-3 col-sm-6 col-xs-12">
	              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">
	              <c:if test="${ categoryParam.categoryParameter.paramRequired == 1 }">
	              	<div class="star_red">*</div>${ categoryParam.categoryParameter.paramName }（附件）：</span>
	              </c:if>
	              <c:if test="${ categoryParam.categoryParameter.paramRequired == 0 }">
	              	${ categoryParam.categoryParameter.paramName }（附件）：</span>
	              </c:if>
	              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 " >
	                <input name="productArguments[${ vs.index }].paramName" value='${ categoryParam.categoryParameter.paramName }' type="hidden"/>
	              	<input name="productArguments[${ vs.index }].parameterValue" type="hidden" value="${ categoryParam.parameterValue }">
	              	<input name="productArguments[${ vs.index }].parameterType" type="hidden" value="${ categoryParam.parameterType }">
	              	<input name="productArguments[${ vs.index }].id" value='${ categoryParam.id }' type="hidden" />
	                <input name="productArguments[${ vs.index }].required" value='${ categoryParam.categoryParameter.paramRequired }' type="hidden">
	                <u:upload id="param_picture${ vs.index }" businessId="${ categoryParam.parameterValue }" sysKey="${ sysKey }" typeId="${ typeId }" buttonName="附件上传" auto="true"/>
	              	<u:show showId="param_picture${ vs.index }" businessId="${ categoryParam.parameterValue }" sysKey="${ sysKey }" typeId="${typeId }" />
	                <span class="input-tip">${ categoryParam.categoryParameter.paramName }</span>
	                <div class="cue"></div>
	              </div>
	            </li>
	        </c:if>
		</c:forEach>
	</ul>