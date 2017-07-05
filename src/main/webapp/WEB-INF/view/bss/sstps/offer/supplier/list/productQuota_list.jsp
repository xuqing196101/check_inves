<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>产品工时定额明细</title>

    <script type="text/javascript">
      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/yearPlan/view.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/comCostDis/view.do?proId=" + proId;
      }

      $(function() {
        var totalRow1 = 0;
        var totalRow2 = 0;
        $("#table1 tr:not(:last)").each(function() {
          $(this).find("td:eq(10)").each(function() {
            totalRow1 += parseFloat($(this).text());
          });
          $(this).find("td:eq(13)").each(function() {
            totalRow2 += parseFloat($(this).text());
          });

        });
        $("#total1").html(totalRow1);
        $("#total2").html(totalRow2);
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
            <a href="javascript:void(0)">产品工时定额明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>产品工时定额明细</h2>
      </div>

    </div>

    <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>

    <div class="container margin-top-5">
      <div class="container padding-left-25 padding-right-25">
        <table id="table1" class="table table-bordered table-condensed">
          <thead>
            <tr>
              <th rowspan="3" class="info">序号</th>
              <th rowspan="3" class="info">零组部件名称</th>
              <th rowspan="3" class="info">零组部件图号</th>
              <th rowspan="3" class="info">工序名称</th>
              <th colspan="7" class="info">单位产品工时定额</th>
              <th rowspan="3" class="info">计量单位</th>
              <th rowspan="2" class="info">配套数量</th>
              <th rowspan="2" class="info">单位产品工时审核核定数</th>
              <th rowspan="3" class="info">备 注</th>
            </tr>
            <tr>
              <th class="info">准结工时</th>
              <th class="info">加工工时</th>
              <th class="info">装配工时</th>
              <th class="info">调试工时</th>
              <th class="info">试验工时</th>
              <th class="info">其他工时</th>
              <th class="info">小计</th>

            </tr>
            <tr>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <%--<th class="info">核定</th>
            <th class="info">核减</th>
            <th class="info">复核</th>
          --%>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${list}" var="yp" varStatus="vs">
              <tr>
                <td class="tc"><input type="hidden" name="" value="${yp.id }" />${vs.index+1 }</td>
                <td class="tc">${yp.partsName }</td>
                <td class="tc">${yp.partsDrawingCode }</td>
                <td class="tc">${yp.processName }</td>

                <td class="tc">${yp.offer }</td>
                <td class="tc">${yp.processingOffer }</td>
                <td class="tc">${yp.assemblyOffer }</td>
                <td class="tc">${yp.debuggingOffer }</td>
                <td class="tc">${yp.testOffer }</td>
                <td class="tc">${yp.otherOffer }</td>
                <td class="tc">${yp.subtotalOffer }</td>

                <td class="tc">${yp.measuringUnit }</td>

                <td class="tc">${yp.assortOffer }</td>

                <td class="tc">${yp.approvedOffer }</td>
                <%--<td class="tc">${yp.approvedRatify }</td>
            <td class="tc">${yp.approvedSubtract }</td>
            <td class="tc">${yp.approvedCheck }</td>
            
            --%>
                <td class="tc">${yp.remark }</td>
              </tr>
            </c:forEach>
            <tr>
              <td class="tc" colspan="4">总计：</td>
              <td colspan="6"></td>
              <td class="tc" id="total1"></td>
              <td></td>
              <td></td>
              <td class="tc" id="total2"></td>
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