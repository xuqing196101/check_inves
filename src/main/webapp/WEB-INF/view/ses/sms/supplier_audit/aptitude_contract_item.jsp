<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

    <head>
        <%@ include file="/WEB-INF/view/common.jsp"%>
        <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
        <title>品目合同</title>
        <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude_material_item.js"></script>
    </head>
    <body>
    <input id="auditType"  type="hidden" value="${auditType }">
        <c:if test="${supplierTypeId eq 'PRODUCT'}">
            <c:set var="fileShow" value="pShow" />
            <input type="hidden" id="pro_val" value="生产">
        </c:if>
        <c:if test="${supplierTypeId eq 'SALES'}">
            <c:set var="fileShow" value="saleShow" />
            <input type="hidden" id="sal_val" value="销售">
        </c:if>
        <c:if test="${supplierTypeId eq 'PROJECT'}">
            <c:set var="fileShow" value="projectShow" />
            <input type="hidden" id="eng_val" value="工程">
        </c:if>
        <c:if test="${supplierTypeId eq 'SERVICE'}">
            <c:set var="fileShow" value="serShow" />
            <input type="hidden" id="ser_val" value="服务">
        </c:if>
            <input type="hidden" name="pageNum" id="pageNum">
            <input type="hidden" name="supplierId" id="supplierId" value="${supplierId}">
            <input type="hidden" name="supplierTypeId" id="supplierTypeId" value="${supplierTypeId}">
            <table class="table table-bordered">
                <tr>
                    <td class="tc info" colspan="4">产品名称</td>
                    <td colspan="3" class="tc info">销售合同(体现甲乙双方盖章及标的相关页)</td>
                    <td colspan="3" class="tc info">证明合同有效履行的相应银行收款进账单</td>
                </tr>
                <tr>
                    <td class="info tc">大类</td>
              <td class="info tc">中类</td>
              <td class="info tc">小类</td>
              <td class="info tc">名称</td>
                    <c:forEach items="${years}" var="year">
                        <td class="tc info">${year}年度</td>
                    </c:forEach>
                    <c:forEach items="${years}" var="year">
                        <td class="tc info">${year}年度</td>
                    </c:forEach>
                </tr>
                <c:forEach items="${contract}" var="obj" varStatus="vs">
                    <tr>
                        <td class="">${obj.firstNode}</td>
                    <td class="">${obj.secondNode}</td>
                    <td class="">${obj.thirdNode}</td>
                    <td class="">${obj.fourthNode}</td>
                        <td class="">
                            <u:show showId="${fileShow}${(vs.index + 1)*6-1}" delete="false" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.oneContract}" />
                        </td>
                        <td class="">
                            <u:show showId="${fileShow}${(vs.index + 1)*6-2}" delete="false" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.twoContract}" />
                        </td>
                        <td class="">
                            <u:show showId="${fileShow}${(vs.index + 1)*6-3}" delete="false" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.threeContract}" />
                        </td>
                        <td class="">
                            <u:show showId="${fileShow}${(vs.index + 1)*6-4}" delete="false" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.oneBil}" />
                        </td>
                        <td class="">
                            <u:show showId="${fileShow}${(vs.index + 1)*6-5}" delete="false" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.twoBil}" />
                        </td>
                        <td class="">
                            <u:show showId="${fileShow}${(vs.index + 1)*6-6}" delete="false" businessId="${obj.itemsId}" sysKey="${sysKey}" typeId="${obj.threeBil}" />
                    </tr>
                </c:forEach>
            </table>
    </body>

</html>