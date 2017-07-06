<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE >
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>制造费用明细</title>

    <script type="text/javascript">
      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/wagesPayable/view.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/periodCost/view.do?proId=" + proId;
      }

      $(function() {
        var totalRow1 = 0;
        var totalRow2 = 0;
        var totalRow3 = 0;
        $("#table1 tr:not(:last)").each(function() {
          $(this).find("td:eq(2)").each(function() {
            totalRow1 += parseFloat($(this).text());
          });
          $(this).find("td:eq(3)").each(function() {
            totalRow2 += parseFloat($(this).text());
          });
          $(this).find("td:eq(4)").each(function() {
            totalRow3 += parseFloat($(this).text());
          });
        });

        if(totalRow1 != null) {
          $("#total1").val(totalRow1);
        }
        if(totalRow2 != null) {
          $("#total2").val(totalRow2);
        }
        if(totalRow3 != null) {
          $("#total3").val(totalRow3);
        }
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
            <a href="javascript:void(0)">制造费用明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>制造费用明细</h2>
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
              <th class="info">报价前2年</th>
              <th class="info">报价前1年</th>
              <th class="info">报价当年</th>
              <th class="info">备 注</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${list}" var="mc" varStatus="vs">
              <tr>
                <td class="tc"><input type="hidden" value="${mc.id }" />${vs.index+1 }</td>
                <td class="tc">${mc.projectName }</td>
                <td class="tc">${mc.tyaQuoteprice }</td>
                <td class="tc">${mc.oyaQuoteprice }</td>
                <td class="tc">${mc.newQuoteprice }</td>
                <td class="tc">${mc.remark }</td>
              </tr>
            </c:forEach>
            <tr>
              <td class="tc" colspan="2">总计：</td>
              <td class="tc"><input type="text" id="total1" class="border0 tc w50 m0" readonly="readonly"></td>
              <td class="tc"><input type="text" id="total2" class="border0 tc w50 m0" readonly="readonly"></td>
              <td class="tc"><input type="text" id="total3" class="border0 tc w50 m0" readonly="readonly"></td>
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