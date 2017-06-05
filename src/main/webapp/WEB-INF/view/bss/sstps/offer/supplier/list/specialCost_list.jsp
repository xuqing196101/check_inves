<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>专项费用明细</title>

    <script type="text/javascript">
      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/outsourcingCon/view.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/burningPower/view.do?proId=" + proId;
      }

      $(function() {
        var totalRow1 = 0;
        $("#table1 tr:not(:last)").each(function() {
          $(this).find("td:eq(8)").each(function() {
            totalRow1 += parseFloat($(this).text());
          });
        });
        $("#total1").val(totalRow1);
      });
    </script>

  </head>

  <body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0);"> 保障作业</a>
          </li>
          <li>
            <a href="javascript:void(0);"> 单一来源审价</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/offer/list.html')">供应商报价</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品报价</a>
          </li>
          <li>
            <a href="javascript:void(0)">专项费用明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>专项费用明细</h2>
      </div>
    </div>

    <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>

    <div class="container margin-top-5">
      <div class="container padding-left-25 padding-right-25">
        <table id="table1" class="table table-bordered table-condensed">
          <thead>
            <tr>
              <th class="info">序号</th>
              <th class="info">项目名称</th>
              <th class="info">项目明细</th>
              <th class="info">名称</th>
              <th class="info">规格型号</th>
              <th class="info">计量单位</th>
              <th class="info">数量(消耗使用)</th>
              <th class="info">单价</th>
              <th class="info">金额</th>
              <th class="info">分摊数量</th>
              <th class="info">单位产品分摊额</th>
              <%--<th class="info">核减金额</th>
            <th class="info">复核金额</th>
            --%>
              <th class="info">备 注</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${list}" var="sc" varStatus="vs">
              <tr>
                <td><input type="hidden" value="${sc.id }" />${vs.index+1 }</td>
                <td class="tc">${sc.projectName }</td>
                <td class="tc">${sc.productDetal }</td>
                <td class="tc">${sc.name }</td>
                <td class="tc">${sc.norm }</td>
                <td class="tc">${sc.measuringUnit }</td>
                <td class="tc">${sc.amount }</td>
                <td class="tc">${sc.price }</td>
                <td class="tc">${sc.money }</td>
                <td class="tc">${sc.proportionAmout }</td>
                <td class="tc">${sc.proportionPrice }</td>
                <%--<td class="tc">${sc.subtractMoney }</td>
            <td class="tc">${sc.checkMoney }</td>
            --%>
                <td class="tc">${sc.remark }</td>
              </tr>
            </c:forEach>
            <tr id="totalRow">
              <td class="tc" colspan="6">总计：</td>
              <td colspan="2"></td>
              <td class="tc"><input type="text" id="total1" class="border0 tc w50 mb0" readonly="readonly"></td>
              <td colspan="2"></td>
              <td></td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="col-md-12">
        <div class="mt40 tc mb50">
          <button class="btn" type="button" onclick="onStep()">上一步</button>
          <button class="btn" type="button" onclick="nextStep()">下一步</button>
        </div>
      </div>

    </div>

  </body>

</html>