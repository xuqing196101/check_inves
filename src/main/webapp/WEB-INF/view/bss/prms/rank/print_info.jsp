<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>打印汇总表</title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
<jsp:include page="../../../ses/bms/page_style/backend_common.jsp"></jsp:include>
<jsp:include page="../../../common.jsp"></jsp:include>
  <script type="text/javascript">
  </script>
</head>
  <body>
      <div class="container">
  <div class="headline-v2">
    <h2>${pack.name}</h2>
  </div>
  <div align="right">
	<button class="btn" onclick="window.print();" type="button">打印</button>
  </div>
  <!-- 表格开始-->
  <div>
            <table class="table table-bordered table-condensed table-hover table-striped">
              <tr>
                <td class="tc" colspan="2">专家/供应商</td>
                <c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td class="tc">${supplier.suppliers.supplierName}</td>
                  </c:if>
                </c:forEach>
              </tr>
              <c:forEach items="${expertList}" var="expert">
                <c:if test="${expert.packageId eq pack.id}">
                  <tr>
                  	<td class="tc w100" rowspan="${expert.count}" <c:if test="${expert.count eq '0' or expert.count == 0}">style="display: none"</c:if> >${expert.reviewTypeId}</td>
                    <td class="tc w100">${expert.expert.relName}</td>
                    <c:forEach items="${supplierList}" var="supplier">
                  	  <c:if test="${supplier.packages eq pack.id}">
	                    <td class="tc">
	                      <c:forEach items="${expertScoreList}" var="score">
	                        <c:if test="${score.packageId eq pack.id and score.supplierId eq supplier.suppliers.id and score.expertId eq expert.expert.id}">
	                          ${score.score}
	                        </c:if>
	                      </c:forEach>
	                    </td>
                      </c:if>
                    </c:forEach>
                  </tr>
                </c:if>
              </c:forEach>
              <tr>
                <td class="tc" colspan="2">总分</td>
                <c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td class="tc">
	                  <c:forEach items="${rankList}" var="rank">
	                    <c:if test="${rank.packageId eq pack.id && rank.supplierId eq supplier.suppliers.id}">
	                      ${rank.econScore}(经济)+${rank.techScore}(技术)=${rank.sumScore}
	                    </c:if>
	                  </c:forEach>
	                </td>
                  </c:if>
                </c:forEach>
              </tr>
              <tr>
                <td class="tc" colspan="2">排名</td>
                <c:forEach items="${supplierList}" var="supplier">
                  <c:if test="${supplier.packages eq pack.id}">
	                <td class="tc">
	                  <c:forEach items="${rankList}" var="rank">
	                    <c:if test="${rank.packageId eq pack.id and rank.supplierId eq supplier.suppliers.id and (rank.reviewResult == null or rank.reviewResult eq '')}">
	                      ${rank.rank}
	                    </c:if>
	                    <c:if test="${rank.packageId eq pack.id and rank.supplierId eq supplier.suppliers.id and rank.reviewResult != null and rank.reviewResult ne ''}">
	                      ${rank.reviewResult}
	                    </c:if>
	                  </c:forEach>
	                </td>
                  </c:if>
                </c:forEach>
              </tr>
			</table>
</div></div>			
  </body>
</html>
