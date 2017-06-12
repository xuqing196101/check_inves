<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>综合费用汇总分配计算明细</title>

    <script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
    <script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>

    <script type="text/javascript">
      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/productQuota/view.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/auditSummary/view.do?proId=" + proId;
      }
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
            <a href="javascript:void(0)">综合费用汇总分配计算明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>综合费用汇总分配计算明细</h2>
      </div>

    </div>

    <form id="formID" name="form1" action="${pageContext.request.contextPath}/comCostDis/update.html?proId=${proId }" method="post" enctype="multipart/form-data">

      <div class="container margin-top-5">
        <div class="container padding-left-25 padding-right-25">
          <table class="table table-bordered table-condensed">
            <thead>
              <tr>
                <th rowspan="2" class="info">序号</th>
                <th rowspan="2" class="info">项目名称</th>
                <th colspan="2" class="info">报价前2年</th>
                <th colspan="2" class="info">报价前1年</th>
                <th colspan="2" class="info">报价当年</th>
                <th rowspan="2" class="info">备 注</th>
              </tr>
              <tr>
                <th class="info">发生额</th>
                <th class="info">费用率(元/小时)</th>
                <th class="info">发生额</th>
                <th class="info">费用率(元/小时)</th>
                <th class="info">发生额</th>
                <th class="info">费用率(元/小时)</th>
              </tr>
            </thead>

            <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
            <tbody>
              <c:forEach items="${list}" var="ccd" varStatus="vs">
                <c:if test="${ccd.status==0}">
                  <tr>
                    <td class="tc" class="w30">
                      <input type="hidden" name="plccd[${(vs.index)}].id" value="${ccd.id }" />
                      <input type="hidden" name="plccd[${(vs.index)}].status" value="${ccd.status }" /> ${vs.index+1 }
                    </td>
                    <td class="tc"><input type="text" name="plccd[${(vs.index)}].projectName" value="${ccd.projectName }" readonly class="border0 mb0 w100" readonly/></td>
                    <td class="tc"><input type="text" name="plccd[${(vs.index)}].tyaAmount" value="${ccd.tyaAmount }" class="border0 w50 mb0" readonly/></td>
                    <td class="tc"><input type="text" name="plccd[${(vs.index)}].tyaFee" value="${ccd.tyaFee }" class="border0 w50 mb0" readonly/></td>
                    <td class="tc"><input type="text" name="plccd[${(vs.index)}].oyaAmout" value="${ccd.oyaAmout }" class="border0 w50 mb0" readonly/></td>
                    <td class="tc"><input type="text" name="plccd[${(vs.index)}].oyaFee" value="${ccd.oyaFee }" class="border0 w50 mb0" readonly/></td>
                    <td class="tc"><input type="text" name="plccd[${(vs.index)}].newAmount" value="${ccd.newAmount }" class="border0 w50 mb0" readonly/></td>
                    <td class="tc"><input type="text" name="plccd[${(vs.index)}].newFee" value="${ccd.newFee }" class="border0 w50 mb0" readonly/></td>
                    <td class="tc"><input type="text" name="plccd[${(vs.index)}].remark" value="${ccd.remark }" class="border0 mb0" readonly/></td>
                  </tr>
                </c:if>
              </c:forEach>
            </tbody>

          </table>
        </div>
      </div>

      <div class="container margin-top-5">
        <div class="container padding-left-25 padding-right-25">
          <table class="table table-bordered table-condensed">
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

            <input type="hidden" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
            <tbody>
              <c:forEach items="${list}" var="ccd" varStatus="vss">
                <c:if test="${ccd.status==1 }">
                  <tr>
                    <td class="tc" class="w30">
                      <input type="hidden" name="plccd[${(vss.index)}].id" value="${ccd.id }" />
                      <input type="hidden" name="plccd[${(vss.index)}].status" value="${ccd.status }" /> ${vs.index+1 }
                    </td>
                    <td class="tc"><input type="text" name="plccd[${(vss.index)}].projectName" value="${ccd.projectName }" readonly class="border0 mb0 w120" readonly/></td>
                    <td class="tc"><input type="text" name="plccd[${(vss.index)}].tyaActual" value="${ccd.tyaActual }" class="border0 w50 mb0" readonly/></td>
                    <td class="tc"><input type="text" name="plccd[${(vss.index)}].oyaActual" value="${ccd.oyaActual }" class="border0 w50 mb0" readonly/></td>
                    <td class="tc"><input type="text" name="plccd[${(vss.index)}].newActual" value="${ccd.newActual }" class="border0 w50 mb0" readonly/></td>
                    <td class="tc"><input type="text" name="plccd[${(vss.index)}].remark" value="${ccd.remark }" class="border0 mb0" readonly/></td>
                  </tr>
                </c:if>
              </c:forEach>
            </tbody>
          </table>
        </div>

        <div class="col-md-12">
          <div class="mt40 tc mb50">
            <button class="btn" type="button" onclick="onStep()">上一步</button>
            <button class="btn" id="button" type="button" onclick="nextStep()">下一步</button>
          </div>
        </div>

      </div>
    </form>

  </body>

</html>