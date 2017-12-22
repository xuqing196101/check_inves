<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<c:choose>
	<c:when test="${!empty listStockholders}">
		<c:set var="undoCount" value="0" />
		<c:forEach items="${listStockholders}" var="stockholder" varStatus="stockvs">
			<tr <c:if test="${fn:contains(audit,stockholder.id)}"> onmouseover="errorMsg(this,'${stockholder.id}','basic_page')"</c:if>>
				<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>><input type="checkbox" value="${stockholder.id}" <c:if test="${fn:contains(audit,stockholder.id)}">readonly='readonly'</c:if>  />
					<input type="hidden" name='listSupplierStockholders[${stockvs.index+ind }].id' value="${stockholder.id}" />
					<input type="hidden" name='listSupplierStockholders[${stockvs.index+ind }].supplierId' value="${stockholder.supplierId}" />
				</td>
				<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>>
					<select name="listSupplierStockholders[${stockvs.index+ind }].nature" class="w100p border0" onchange="onchangeNature(this.value,'${stockvs.index}')">
						<option value="1" <c:if test="${stockholder.nature==1}"> selected="selected"</c:if> >法人</option>
						<option value="2" <c:if test="${stockholder.nature==2}"> selected="selected"</c:if> >自然人</option>
					</select>
				</td>
				<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>> <input type='text' style='border:0px;' maxlength="50" name='listSupplierStockholders[${stockvs.index+ind }].name' value='${stockholder.name}'  <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if>  > </td>
				<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>>
					<select name="listSupplierStockholders[${stockvs.index+ind }].identityType" class="w100p border0">
						<option value="1" <c:if test="${stockholder.identityType==1}"> selected="selected"</c:if> >
						  <c:if test="${empty stockholder.nature}">统一社会信用代码</c:if>
							<c:if test="${stockholder.nature==1}">统一社会信用代码</c:if>
							<c:if test="${stockholder.nature==2}">居民二代身份证</c:if>
						</option>
						<option value="2" <c:if test="${stockholder.identityType==2}"> selected="selected"</c:if> >其他</option>
					</select>
				</td>
				<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>>
					<input type='text' style='border:0px;' name='listSupplierStockholders[${stockvs.index+ind }].identity' maxlength="${stockholder.identityType==1?18:60}" onkeyup="value=value.replace(/[^\d|a-zA-Z]/g,'')" onchange="value=value.replace(/[^\d|a-zA-Z]/g,'')" value='${stockholder.identity}' <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if>>
				</td>
				<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>> <input type='text' style='border:0px;' class="shares" name='listSupplierStockholders[${stockvs.index+ind }].shares' onblur="validateMoney(this.value, 4, false)" value='${stockholder.shares}' <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if> > </td>
				<td class="tc" <c:if test="${fn:contains(audit,stockholder.id)}">style="border: 1px solid red;" </c:if>> <input type='text' style='border:0px;' class="proportion_vali" name='listSupplierStockholders[${stockvs.index+ind }].proportion' value='${stockholder.proportion}' <c:if test="${!fn:contains(audit,stockholder.id)&&currSupplier.status==2}">readonly='readonly'</c:if> 
				 	onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="validatePercentage2(this.value)"/></td>
			</tr>
			<c:set var="undoCount" value="${undoCount+1}" />
		</c:forEach>
		<input type="hidden" id="undoCount" value="${undoCount}" />
	</c:when>
</c:choose>