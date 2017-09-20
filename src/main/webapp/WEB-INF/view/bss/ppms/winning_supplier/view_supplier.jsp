<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <base href="${pageContext.request.contextPath}/">
    <%@ include file="/WEB-INF/view/common.jsp"%>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">
  </head>
  <script type="text/javascript">
      //录入表的
    function InputBD(supplierId,passId) {
      window.location.href = "${pageContext.request.contextPath}/winningSupplier/inputList.do?projectId=${projectId}&packageId=${packageId}&supplierId=" + supplierId 
      + "&passId=" + passId + "&supplierIds=${supplierIds}";
    }
    function hrefGO() {
      location.href = "${pageContext.request.contextPath}/winningSupplier/selectSupplier.do?projectId=${projectId}&flowDefineId=${flowDefineId}";
    }
    function openTheDetail(passId, type) {
      var dis = $($($(type).parent().nextAll()[$(type).parent().nextAll().length - 1]).children()[0]).attr("disabled");
      if(dis != "disabled") {
        layer.alert("请先录入标的");
      } else {
        location.href = "${pageContext.request.contextPath }/winningSupplier/openTheDetail.do?passId=" + passId;
      }
    }
  </script>

  <body>

    <h2 class="list_title mb0 clear">确认中标供应商</h2>
    <div class="content table_box pl0">
      <table class="table table-bordered table-condensed table-hover table-striped">
        <thead>
          <tr class="info">
            <th class="w200">供应商名称</th>
            <th style="width: 110px;">&nbsp;总报价&nbsp;（万元）</th>
            <th style="width: 50px;">总得分</th>
            <th style="width: 20px;">排名</th>
            <th style="width: 50px;">中标状态</th>
            <th class="w50">占比（%）</th>
            <th class="w100">实际成交总价（万元）</th>
            <th style="width: 80px;">操作</th>
          </tr>
        </thead>
        <c:forEach items="${supplierCheckPass}" var="checkpass" varStatus="vs">
          <tr id="${checkpass.id}">
            <td class="opinter" title="${checkpass.supplier.supplierName }">
              <c:choose>
                <c:when test="${fn:length(checkpass.supplier.supplierName) >10}">
                  <a href="javascript:void(0)" onclick="openTheDetail('${checkpass.id}',this)">${fn:substring(checkpass.supplier.supplierName , 0, 10)}...</a>
                </c:when>
                <c:otherwise>
                  <a href="javascript:void(0)" onclick="openTheDetail('${checkpass.id}',this)">${checkpass.supplier.supplierName}</a>
                </c:otherwise>
              </c:choose>
            </td>
            <td class="tc opinter" id="totalPrice">${checkpass.totalPrice}</td>
            <td class="tc opinter">${checkpass.totalScore}</td>
            <td class="tc opinter">${vs.index+1}</td>
              <c:if test="${checkpass.isWonBid != 1}">
                <td class="tc opinter">未中标</td>
              </c:if>
              <c:if test="${checkpass.isWonBid == 1}">
                <td class="tc opinter">已中标</td>
              </c:if>
            <td class="tc opinter">
              ${checkpass.priceRatio}
            </td>
            <c:if test="${quote==0 }">
              <td class="tc opinter">
                <fmt:formatNumber type="number" value="${checkpass.money}" pattern="0.0000" maxFractionDigits="4" />
              </td>
            </c:if>
            <c:if test="${quote==1 }">
              <td class="tc opinter" id="singQuote">
                <fmt:formatNumber type="number" value="${checkpass.money}" pattern="0.0000" maxFractionDigits="4" />
              </td>
            </c:if>
            <td class="tc opinter"><button class="btn btn-windows add" <c:if test="${checkpass.subjects ne null}">disabled='disabled'</c:if>
              onclick="InputBD('${checkpass.supplier.id}','${checkpass.id}');" type="button">录入标的</button>
            </td>
          </tr>
        </c:forEach>
      </table>
      <div class="col-md-12 tc">
        <button class="btn btn-windows back" onclick="hrefGO();" type="button">返回</button>
      </div>
    </div>
  </body>

</html>