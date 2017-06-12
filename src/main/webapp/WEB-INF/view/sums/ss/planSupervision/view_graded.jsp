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
    <div class="col-md-12 col-xs-12 col-sm-12 mt20">
      <c:if test="${'PBFF_JZJF' eq methodCode}">
        基准价：
        <fmt:formatNumber type="number" value="${supplier.jzjf.benchmarkPrice}" pattern="0.0000" maxFractionDigits="4" /> 浮动比例：
        <fmt:formatNumber type="number" value="${supplier.jzjf.floatingRatio}" pattern="0.00" maxFractionDigits="2" />% 中标参考价：
        <fmt:formatNumber type="number" value="${supplier.jzjf.bidPrice}" pattern="0.0000" maxFractionDigits="4" /> 有效平均报价：
        <fmt:formatNumber type="number" value="${supplier.jzjf.effectiveAverageQuotation}" pattern="0.0000" maxFractionDigits="4" />
      </c:if>
      <c:if test="${'OPEN_ZHPFF' eq methodCode}">
        <c:forEach var="msg" items="${fn:split(supplier.reviewResult, '_')}">
          有效平均报价：
          <fmt:formatNumber type="number" value="${msg}" pattern="0.0000" maxFractionDigits="4" /> 有效经济技术平均分（不含价格因素）：
          <fmt:formatNumber type="number" value="${msg}" pattern="0.00" maxFractionDigits="2" />
        </c:forEach>
      </c:if>
      <table class="table table-bordered table-condensed table-hover    p0 space_nowrap">
        <tr>
          <td class="tc" rowspan="2">分类</td>
          <td class="tc" rowspan="2">评委名称</td>
          <td class="tc" colspan="2">${supplier.suppliers.supplierName}</td>
        </tr>
        <tr>
          <td class="info tc" colspan="2">检查结果</td>
        </tr>
        <!-- 综合评分法 -->
        <c:if test="${'OPEN_ZHPFF' eq methodCode}">
          <c:forEach items="${expertList}" var="expert">
            <tr>
              <td class="tc w100" rowspan="${expert.count}" <c:if test="${expert.count eq '0' or expert.count == 0}">style="display: none"</c:if> >${expert.reviewTypeId}</td>
        <td class="tc w100">${expert.expert.relName}</td>
        <td class="tc" colspan="2">
          <c:forEach items="${expertScoreList}" var="score">
            <c:if test="${expert.expert.id eq score.expertId}">
              ${score.score}
            </c:if>
          </c:forEach>
        </td>
        </tr>
        </c:forEach>
        <tr>
          <td class="tc" colspan="2">总分</td>
          <td class="tc" colspan="2">
            ${rank.econScore}(经济)+${rank.techScore}(技术)=${rank.sumScore}
          </td>
        </tr>
        </c:if>
        <!-- 最低价法 -->
        <c:if test="${'PBFF_ZDJF' eq methodCode}">
          <c:forEach items="${expertList}" var="expert">
            <tr>
              <td class="tc w100" rowspan="${expert.count}" <c:if test="${expert.count eq '0' or expert.count == 0}">style="display: none"</c:if> >${expert.reviewTypeId}</td>
        <td class="tc w100">${expert.expert.relName}</td>
        <c:if test="${supplier.packages eq pack.id}">
          <td class="tc" colspan="2">符合</td>
        </c:if>
        </tr>
        </c:forEach>
        <tr>
          <td class="tc" colspan="2">报价</td>
          <c:if test="${supplier.packages eq pack.id}">
            <td class="tr" colspan="2">
              <fmt:formatNumber type="number" value="${fn:substringBefore(supplier.reviewResult,'_')}" pattern="0.0000" maxFractionDigits="4" />
            </td>
          </c:if>
        </tr>
        <tr>
          <td class="tc" colspan="2">总结</td>
          <c:if test="${supplier.packages eq pack.id}">
            <td class="tc" colspan="2">符合</td>
          </c:if>
        </tr>
        </c:if>
        <!-- 基准价法 -->
        <c:if test="${'PBFF_JZJF' eq methodCode}">
          <tr>
            <td class="tc w100">报价</td>
            <td class="tc w100">差价（与中标参考价的差价）</td>
            <c:if test="${supplier.packages eq pack.id}">
              <td class="tr">
                <fmt:formatNumber type="number" value="${supplier.jzjf.supplierPrice}" pattern="0.0000" maxFractionDigits="4" />
              </td>
              <td class="tr">
                <c:if test="${supplier.jzjf.supplierPrice >= supplier.jzjf.bidPrice}">
                  <fmt:formatNumber type="number" value="${supplier.jzjf.supplierPrice - supplier.jzjf.bidPrice}" pattern="0.0000" maxFractionDigits="4" />
                </c:if>
                <c:if test="${supplier.jzjf.supplierPrice <= supplier.jzjf.bidPrice}">
                  <fmt:formatNumber type="number" value="${supplier.jzjf.bidPrice - supplier.jzjf.supplierPrice}" pattern="0.0000" maxFractionDigits="4" />
                </c:if>
              </td>
            </c:if>
          </tr>
          <tr>
            <td class="tc" colspan="2">总结</td>
            <c:if test="${supplier.packages eq pack.id}">
              <td class="tc" colspan="2">符合</td>
            </c:if>
          </tr>
        </c:if>
        <tr>
          <td class="tc" colspan="2">排名</td>
          <c:if test="${'PBFF_JZJF' eq methodCode}">
            <c:if test="${supplier.packages eq pack.id}">
              <td class="tc" colspan="2">
                ${supplier.jzjf.rank}
              </td>
            </c:if>
          </c:if>
          <c:if test="${'OPEN_ZHPFF' eq methodCode}">
            <c:if test="${supplier.packages eq pack.id}">
              <td class="tc" colspan="2">
                <c:if test="${rank.packageId eq pack.id and rank.supplierId eq supplier.suppliers.id and (rank.reviewResult == null or rank.reviewResult eq '')}">
                  ${rank.rank}
                </c:if>
                <c:if test="${rank.packageId eq pack.id and rank.supplierId eq supplier.suppliers.id and rank.reviewResult != null and rank.reviewResult ne ''}">
                  <c:set var="num2" value="0" scope="page"></c:set>
                  <c:forEach var="msg" items="${fn:split(rank.reviewResult, '_')}">
                    <c:set var="num2" value="${num2+1}" scope="page"></c:set>
                    <c:if test="${num2 eq '3' }">
                      ${msg}
                    </c:if>
                  </c:forEach>
                </c:if>
              </td>
            </c:if>
          </c:if>
          <c:if test="${'PBFF_ZDJF' eq methodCode}">
            <c:if test="${supplier.packages eq pack.id}">
              <td class="tc" colspan="2">
                ${fn:substringAfter(supplier.reviewResult,"_")}
              </td>
            </c:if>
          </c:if>
        </tr>
      </table>
    </div>
  </body>

</html>