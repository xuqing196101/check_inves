<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
    </script>
  </head>

  <body>
    <div class="over_scroll col-md-12 col-xs-12 col-sm-12 p0 m0">
      <c:if test="${'PBFF_JZJF' eq methodCode}">
                      基准价：<fmt:formatNumber type="number" value="${supplier.jzjf.benchmarkPrice}" pattern="0.0000" maxFractionDigits="4"/>   
                      浮动比例：<fmt:formatNumber type="number" value="${supplier.jzjf.floatingRatio}" pattern="0.00" maxFractionDigits="2"/>%     
                      中标参考价：<fmt:formatNumber type="number" value="${supplier.jzjf.bidPrice}" pattern="0.0000" maxFractionDigits="4"/>     
                       有效平均报价：<fmt:formatNumber type="number" value="${supplier.jzjf.effectiveAverageQuotation}" pattern="0.0000" maxFractionDigits="4"/>
      </c:if>
      <c:if test="${'OPEN_ZHPFF' eq methodCode}">
        <c:forEach var="msg" items="${fn:split(supplier.reviewResult, '_')}">
                             有效平均报价：<fmt:formatNumber type="number" value="${msg}" pattern="0.0000" maxFractionDigits="4"/>                  
                             有效经济技术平均分（不含价格因素）：<fmt:formatNumber type="number" value="${msg}" pattern="0.00" maxFractionDigits="2"/>
        </c:forEach>
      </c:if>
      <table class="table table-bordered table-condensed table-hover    p0 space_nowrap">
        <tr>
         <td class="tc" rowspan="2">分类</td>
         <td class="tc" rowspan="2">评委名称</td>
         <td class="tc" colspan="2">${suppliers.supplierName}</td>
        </tr>
        <tr><td class="info tc" colspan="2">检查结果</td></tr>
        <!-- 综合评分法 -->
        <c:if test="${'OPEN_ZHPFF' eq methodCode}">
          
        </c:if>
      </table>
    </div>
  </body>

</html>