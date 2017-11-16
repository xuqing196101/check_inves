<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

<head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
    <title>品目销售合同</title>
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude_items.js"></script>
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude_contract_item.js"></script>
</head>
<body>
<input type="hidden" name="pageNum" id="pageNum">
<input id="supplierId" type="hidden" value="${supplierId }">
<input id="auditType" type="hidden" value="${auditType }">
<input id="ind" type="hidden" value="${ind}">
<input id="count" type="hidden" value="0">
<input id="tablerId" type="hidden" value="${tablerId}">
<table class="table table-bordered m_table_fixed_border m_table_fixed_border">
    <tr>
        <!-- <td class="tc info" colspan="4">产品名称</td> -->
        <td colspan="3" class="tc info">销售合同(体现甲乙双方盖章及标的相关页)</td>
        <td colspan="3" class="tc info">证明合同有效履行的相应银行收款进账单</td>
    </tr>
    <tr>
        <!-- <td class="info tc">大类</td>
        <td class="info tc">中类</td>
        <td class="info tc">小类</td>
        <td class="info tc">名称</td> -->
        <c:forEach items="${years}" var="year">
            <td class="tc info">${year}年度</td>
        </c:forEach>
        <c:forEach items="${years}" var="year">
            <td class="tc info">${year}年度</td>
        </c:forEach>
    </tr>
    <c:forEach items="${contract}" var="obj" varStatus="vs">
        <tr>
                <%-- <td class="info tc">${obj.firstNode}</td>
                <td class="info tc">${obj.secondNode}</td>
                <td class="info tc">${obj.thirdNode}</td>
                <td class="info tc">${obj.fourthNode}</td> --%>
            <td class="m_upload_file" id="td1${vs.index + 1}"
                <c:if test="${fn:contains(fileModifyField,obj.supplierItemId.concat(obj.oneContract))}">style="border: 1px solid #FF8C00;"</c:if>
                <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.oneContract))}">style="border: 1px solid #FF0000;"</c:if>
                >
                <c:if test="${!fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.oneContract))}">
	                <a href="javascript:void(0);"
	                  onclick="reasonProject('${ind}','${obj.supplierItemId}_${obj.oneContract}','${obj.itemsName }','${vs.index + 1}')">
	                  <c:if test="${!fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.oneContract))}">
	                  	<img id="show_td${vs.index + 1}" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                  </c:if>
	                  <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.oneContract))}">
	                  	<img id="show_td${vs.index + 1}" src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                  </c:if>
	                </a>
                </c:if>
                <c:if test="${fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.oneContract))}">
                	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                </c:if>
                <%-- <input type="hidden" id="fileId${vs.index + 1}" value="${obj.oneContract}">
                <input type="hidden" id="count1" value="${obj.isAptitudeProductPageAudit}"> --%>
                <u:upload id="${fileShow}${(vs.index + 1)*6-1}" buttonName="上传附件" multiple="true" auto="true"
                  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.oneContract}"/>
                <u:show showId="${fileShow}${(vs.index + 1)*6-1}"
                  businessId="${obj.supplierItemId}" sysKey="${sysKey}"
                  typeId="${obj.oneContract}"/>
            </td>
            <td class="m_upload_file" id="td1${vs.index + 2}"
                <c:if test="${fn:contains(fileModifyField,obj.supplierItemId.concat(obj.twoContract))}">style="border: 1px solid #FF8C00;"</c:if>
                <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.twoContract))}">style="border: 1px solid #FF0000;"</c:if>
                >
                <c:if test="${!fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.twoContract))}">
	                <a href="javascript:void(0);"
	                  onclick="reasonProject('${ind}','${obj.supplierItemId}_${obj.twoContract}','${obj.itemsName }','${vs.index + 2}')">
	                  <c:if test="${!fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.twoContract))}">
	                  	<img id="show_td${vs.index + 2}" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                  </c:if>
	                  <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.twoContract))}">
	                  	<img id="show_td${vs.index + 2}" src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                  </c:if>
	                </a>
                </c:if>
                <c:if test="${fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.twoContract))}">
                	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                </c:if>
                <%-- <input type="hidden" id="fileId${vs.index + 2}" value="${obj.twoContract}">
                <input type="hidden" id="count2" value="${obj.isAptitudeProductPageAudit}"> --%>
                <u:upload id="${fileShow}${(vs.index + 1)*6-2}" buttonName="上传附件" multiple="true" auto="true"
                  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.twoContract}"/>
                <u:show showId="${fileShow}${(vs.index + 1)*6-2}" businessId="${obj.supplierItemId}"
                  sysKey="${sysKey}" typeId="${obj.twoContract}"/>
            </td>
            <td class="m_upload_file" id="td1${vs.index + 3}"
                <c:if test="${fn:contains(fileModifyField,obj.supplierItemId.concat(obj.threeContract))}">style="border: 1px solid #FF8C00;"</c:if>
                <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.threeContract))}">style="border: 1px solid #FF0000;"</c:if>
                >
                <c:if test="${!fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.threeContract))}">
	                <a href="javascript:void(0);"
	                  onclick="reasonProject('${ind}','${obj.supplierItemId}_${obj.threeContract}','${obj.itemsName }','${vs.index + 3}')">
	                  <c:if test="${!fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.threeContract))}">
	                  	<img id="show_td${vs.index + 3}" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                  </c:if>
	                  <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.threeContract))}">
	                  	<img id="show_td${vs.index + 3}" src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                  </c:if>
	                </a>
                </c:if>
                <c:if test="${fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.threeContract))}">
                	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                </c:if>
                <%-- <input type="hidden" id="fileId${vs.index + 3}" value="${obj.threeContract}">
                <input type="hidden" id="count3" value="${obj.isContractProductPageAudit}"> --%>
                <u:upload id="${fileShow}${(vs.index + 1)*6-3}" buttonName="上传附件" multiple="true" auto="true"
                  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.threeContract}"/>
                <u:show showId="${fileShow}${(vs.index + 1)*6-3}"
                  businessId="${obj.supplierItemId}" sysKey="${sysKey}"
                  typeId="${obj.threeContract}"/>
            </td>
            <td class="m_upload_file" id="td1${vs.index + 4}"
                <c:if test="${fn:contains(fileModifyField,obj.supplierItemId.concat(obj.oneBil))}">style="border: 1px solid #FF8C00;"</c:if>
                <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.oneBil))}">style="border: 1px solid #FF0000;"</c:if>
                >
                <c:if test="${!fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.oneBil))}">
	                <a href="javascript:void(0);"
	                  onclick="reasonProject('${ind}','${obj.supplierItemId}_${obj.oneBil}','${obj.itemsName }','${vs.index + 4}')">
	                  <c:if test="${!fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.oneBil))}">
	                  	<img id="show_td${vs.index + 4}" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                  </c:if>
	                  <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.oneBil))}">
	                  	<img id="show_td${vs.index + 4}" src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                  </c:if>
	                </a>
                </c:if>
                <c:if test="${fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.oneBil))}">
                	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                </c:if>
                <%-- <input type="hidden" id="fileId${vs.index + 4}" value="${obj.oneBil}">
                <input type="hidden" id="count4" value="${obj.isContractSalesPageAudit}"> --%>
                <u:upload id="${fileShow}${(vs.index + 1)*6-4}" buttonName="上传附件" multiple="true" auto="true"
                  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.oneBil}"/>
                <u:show showId="${fileShow}${(vs.index + 1)*6-4}"
                  businessId="${obj.supplierItemId}" sysKey="${sysKey}"
                  typeId="${obj.oneBil}"/>
            </td>
            <td class="m_upload_file" id="td1${vs.index + 5}"
                <c:if test="${fn:contains(fileModifyField,obj.supplierItemId.concat(obj.twoBil))}">style="border: 1px solid #FF8C00;"</c:if>
                <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.twoBil))}">style="border: 1px solid #FF0000;"</c:if>
                >
                <c:if test="${!fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.twoBil))}">
	                <a href="javascript:void(0);"
	                  onclick="reasonProject('${ind}','${obj.supplierItemId}_${obj.twoBil}','${obj.itemsName }','${vs.index + 5}')">
	                  <c:if test="${!fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.twoBil))}">
	                  	<img id="show_td${vs.index + 5}" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                  </c:if>
	                  <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.twoBil))}">
	                  	<img id="show_td${vs.index + 5}" src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                  </c:if>
	                </a>
                </c:if>
                <c:if test="${fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.twoBil))}">
                	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                </c:if>
                <%-- <input type="hidden" id="fileId${vs.index + 5}" value="${obj.twoBil}">
                <input type="hidden" id="count5" value="${obj.isItemsProductPageAudit}"> --%>
                <u:upload id="${fileShow}${(vs.index + 1)*6-5}" buttonName="上传附件" multiple="true" auto="true"
                  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.twoBil}"/>
                <u:show showId="${fileShow}${(vs.index + 1)*6-5}"
                  businessId="${obj.supplierItemId}" sysKey="${sysKey}"
                  typeId="${obj.twoBil}"/>
            </td>
            <td class="m_upload_file" id="td1${vs.index + 6}"
                <c:if test="${fn:contains(fileModifyField,obj.supplierItemId.concat(obj.threeBil))}">style="border: 1px solid #FF8C00;"</c:if>
                <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.threeBil))}">style="border: 1px solid #FF0000;"</c:if>
                >
                <c:if test="${!fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.threeBil))}">
	                <a href="javascript:void(0);"
	                  onclick="reasonProject('${ind}','${obj.supplierItemId}_${obj.threeBil}','${obj.itemsName }','${vs.index + 6}')">
	                  <c:if test="${!fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.threeBil))}">
	                  	<img id="show_td${vs.index + 6}" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                  </c:if>
	                  <c:if test="${fn:contains(auditField,obj.supplierItemId.concat('_').concat(obj.threeBil))}">
	                  	<img id="show_td${vs.index + 6}" src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                  </c:if>
	                </a>
                </c:if>
                <c:if test="${fn:contains(unableField,obj.supplierItemId.concat('_').concat(obj.threeBil))}">
                	<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                </c:if>
                <%-- <input type="hidden" id="fileId${vs.index + 6}" value="${obj.threeBil}">
                <input type="hidden" id="count6" value="${obj.isItemsSalesPageAudit}"> --%>
                <u:upload id="${fileShow}${(vs.index + 1)*6-6}" buttonName="上传附件" multiple="true" auto="true"
                  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.threeBil}"/>
                <u:show showId="${fileShow}${(vs.index + 1)*6-6}"
                  businessId="${obj.supplierItemId}" sysKey="${sysKey}"
                  typeId="${obj.threeBil}"/>
        </tr>
    </c:forEach>
</table>
</body>
</html>