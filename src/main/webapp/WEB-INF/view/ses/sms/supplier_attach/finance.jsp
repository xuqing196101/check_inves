<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
  </head>

  <body>
    <div class="container">
      <!-- 列表 -->
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="w50 info">年份</th>
              <th class="info">审计报告书中的审计报告</th>
              <th class="info">资产负债表</th>
              <th class="info">财务利润表</th>
              <th class="info">现金流量表</th>
              <th class="info">所有者权益变动表</th>
            </tr>
          </thead>
          <c:forEach items="${finance}" var="f" varStatus="vs">
            <tr>
              <td class="tc w50" id="${f.id }_file">${f.year}</td>
              <td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierAuditOpinion))}">style="border: 1px solid #FF8C00;"</c:if>>
                <u:show showId="fina_${vs.index}_audit" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierAuditOpinion}" sysKey="${sysKey}" />
              </td>
              <td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierLiabilities))}">style="border: 1px solid #FF8C00;"</c:if>>
                <u:show showId="fina_${vs.index}_lia" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierLiabilities}" sysKey="${sysKey}" />
              </td>
              <td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierProfit))}">style="border: 1px solid #FF8C00;"</c:if>>
                <u:show showId="fina_${vs.index}_pro" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierProfit}" sysKey="${sysKey}" />
              </td>
              <td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierCashFlow))}">style="border: 1px solid #FF8C00;"</c:if>>
                <u:show showId="fina_${vs.index}_cash" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierCashFlow}" sysKey="${sysKey}" />
              </td>
              <td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierOwnerChange))}">style="border: 1px solid #FF8C00;"</c:if>>
                <u:show showId="fina_${vs.index}_change" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierOwnerChange}" sysKey="${sysKey}" />
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </body>

</html>