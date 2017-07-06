<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

    <head>
        <%@ include file="/WEB-INF/view/common.jsp"%>
        <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
        <title>品目合同</title>
        <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude_items.js"></script>
        <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude_contract_item.js"></script>
    </head>
    <body>
            <input type="hidden" name="pageNum" id="pageNum">
            <input type="hidden" name="supplierTypeId" id="supplierTypeId" value="${supplierTypeId}">
            <input id="supplierId"  type="hidden" value="${supplierId }">
            <input id="auditType"  type="hidden" value="${auditType }">
            <input id="ids"  type="hidden" value="${ids }">
            <input id="count"  type="hidden" value="0">
            <input id="tablerId"  type="hidden" value="${tablerId}">
            <table class="table table-bordered">
                <tr>
                    <!-- <td class="tc info" colspan="4">产品名称</td> -->
                    <td colspan="3" class="tc info">销售合同(体现甲乙双方盖章及标的相关页)</td>
                    <td colspan="3" class="tc info">证明合同有效履行的相应银行收款进账单</td>
                    <td rowspan="2" class="tc info">操作</td>
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
                        <td class="m_upload_file">
                            <u:upload id="${fileShow}${(vs.index + 1)*6-1}" buttonName="上传附件" multiple="true" auto="true" businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.oneContract}" />
                            <div class="m_uploadFiles"><u:show showId="${fileShow}${(vs.index + 1)*6-1}"  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.oneContract}" /></div>
                        </td>
                        <td class="m_upload_file">
                         <u:upload id="${fileShow}${(vs.index + 1)*6-2}" buttonName="上传附件" multiple="true" auto="true" businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.twoContract}" />
                            <u:show showId="${fileShow}${(vs.index + 1)*6-2}"  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.twoContract}" /></div>
                        </td>
                        <td class="m_upload_file">
                         <u:upload id="${fileShow}${(vs.index + 1)*6-3}" buttonName="上传附件" multiple="true" auto="true" businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.threeContract}" />
                            <div class="m_uploadFiles"><u:show showId="${fileShow}${(vs.index + 1)*6-3}"  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.threeContract}" /></div>
                        </td>
                        <td class="m_upload_file">
                         <u:upload id="${fileShow}${(vs.index + 1)*6-4}" buttonName="上传附件" multiple="true" auto="true" businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.oneBil}" />
                            <div class="m_uploadFiles"><u:show showId="${fileShow}${(vs.index + 1)*6-4}"  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.oneBil}" /></div>
                        </td>
                        <td class="m_upload_file">
                         <u:upload id="${fileShow}${(vs.index + 1)*6-5}" buttonName="上传附件" multiple="true" auto="true" businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.twoBil}" />
                            <div class="m_uploadFiles"><u:show showId="${fileShow}${(vs.index + 1)*6-5}"  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.twoBil}" /></div>
                        </td>
                        <td class="m_upload_file">
                         <u:upload id="${fileShow}${(vs.index + 1)*6-6}" buttonName="上传附件" multiple="true" auto="true" businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.threeBil}" />
                            <div class="m_uploadFiles"><u:show showId="${fileShow}${(vs.index + 1)*6-6}"  businessId="${obj.supplierItemId}" sysKey="${sysKey}" typeId="${obj.threeBil}" /></div>
                       <td class="tc info"  onclick="reasonProject('${ids }','${obj.itemsId }','${obj.itemsName }')">
                          <a href="javascript:void(0);"><img id="show_td" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></a>
                       </td>
                    </tr>
                </c:forEach>
            </table>
    </body>

</html>