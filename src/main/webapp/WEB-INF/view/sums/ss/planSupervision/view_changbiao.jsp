<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en">

  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      $(function() {
        var allTable = document.getElementsByTagName("table");
        for(var i = 0; i < allTable.length; i++) {
          var totalMoney = 0;
          for(var j = 1; j < allTable[i].rows.length - 1; j++) { //遍历Table的所有Row
            var num = $(allTable[i].rows).eq(j).find("td").eq("5").text();
            var price = $(allTable[i].rows).eq(j).find("td").eq("6").text();
            $(allTable[i].rows).eq(j).find("td").eq("7").text(parseFloat(price * num).toFixed(2));
            totalMoney += parseFloat(price * num);
            $(allTable[i].rows).eq(allTable[i].rows.length - 1).find("td").eq("1").text(parseFloat(totalMoney).toFixed(2));
          };
        };
      });
    </script>
  </head>

  <body onload="addTotal()">
    <!-- 表格开始-->
    <div class="clear">
      <div>
        <h2 class="count_flow shrink hand">包名:<span class="f14 blue">${packages.name }</span>
          <span>项目预算报价(万元)：${packages.projectBudget}</span>
          </h2>
      </div>
      <c:forEach items="${packages.suList}" var="suList" varStatus="vs">
        <span class="fl">供应商名称：<span class="f14 blue">${suList.supplierName}</span></span>
        <table id="${suList.id}" class="table table-bordered table-condensed mt5">
          <thead>
            <tr>
              <th class="info w50">序号</th>
              <th class="info">物资名称</th>
              <th class="info">规格<br/>型号</th>
              <th class="info">质量技术<br/>标准</th>
              <th class="info">计量<br/>单位</th>
              <th class="info">采购<br/>数量</th>
              <th class="info">单价（元）</th>
              <th class="info">小计</th>
              <th class="info">交货时间</th>
              <th class="info">备注</th>
            </tr>
          </thead>
          <c:forEach items="${suList.quoteList}" var="quoteList" varStatus="vs">
            <tr id="${quoteList.id }" class="hand">
              <td class="tc w50">${vs.index + 1}</td>
              <td class="tl">${quoteList.projectDetail.goodsName}</td>
              <td class="tl">${quoteList.projectDetail.stand}</td>
              <td class="tl w200">${quoteList.projectDetail.qualitStand}</td>
              <td class="tc w50">${quoteList.projectDetail.item}</td>
              <td class="tc w50">${quoteList.projectDetail.purchaseCount}</td>
              <td class="tr w50">${quoteList.quotePrice}</td>
              <td class="tr w50">${quoteList.total}</td>
              <td class="tc w80">${quoteList.deliveryTime }</td>
              <td class="tc">${quoteList.remark}</td>
            </tr>
          </c:forEach>
          <tr>
            <td class="tr" colspan="2"><b>总金额(元):</b></td>
            <td class="tl" colspan="8"></td>
          </tr>
        </table>
      </c:forEach>
    </div>
  </body>

</html>