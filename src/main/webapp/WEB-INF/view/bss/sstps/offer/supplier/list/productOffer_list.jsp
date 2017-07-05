<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>产 品 报 价(审价)详 细 情 况</title>

    <script type="text/javascript">
      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/comCostDis/view.do?proId=" + proId;
      }

      function cancel() {
        window.location.href = "${pageContext.request.contextPath}/auditSummary/cancel.html";
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
            <a href="javascript:void(0)">产 品 报 价(审价)详 细 情 况</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>产 品 报 价(审价)详 细 情 况</h2>
      </div>
    </div>

    <form action="${pageContext.request.contextPath}/auditSummary/update.html" method="post" enctype="multipart/form-data">

      <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
      <input type="hidden" id="apid" name="id" value="${ap.id }" />
      <div class="container margin-top-5">
        <div class="container padding-left-25 padding-right-25">
          <table class="table table-bordered table-condensed">
            <tr>
              <th class="info">产品名称</th>
              <td><input type="text" name="contractProduct.name" class="border0" value="${ap.contractProduct.name }" readonly/></td>
            </tr>
            <tr>
              <th class="info">生产单位</th>
              <td><input type="text" name="produceUnit" class="border0" value="${ap.produceUnit }" readonly></td>
            </tr>
            <tr>
              <th class="info">订货数量</th>
              <td><input type="text" name="orderAcount" class="border0" value="${ap.orderAcount }" readonly/></td>
            </tr>
            <tr>
              <th class="info">计量单位</th>
              <td><input type="text" name="measuringUnit" class="border0" value="${ap.measuringUnit }" readonly/></td>
            </tr>
            <tr>
              <th class="info">审核人员</th>
              <td><input type="text" name="auditUser" class="border0" value="${ap.auditUser }" / readonly></td>
            </tr>
          </table>
        </div>
      </div>

      <div class="container margin-top-5">
        <div class="container padding-left-25 padding-right-25">
          <table class="table table-bordered table-condensed">
            <tobdy>
              <tr>
                <th class="info">序号</th>
                <th class="info">项目类型</th>
                <th class="info">项目名称</th>
                <th class="info">单台报价</th>
                <th class="info">备注</th>
              </tr>
            </tobdy>
            <c:forEach items="${list}" var="cc" varStatus="vs">
              <tr>
                <td class="tc">
                  <input type="hidden" name="plcc[${(vs.index)}].id" value="${cc.id }" />${vs.index+1 }
                  <input type="hidden" name="plcc[${(vs.index)}].status" value="${cc.status }" />
                </td>
                <td class="tc"><input type="text" class="border0" name="plcc[${(vs.index)}].projectName" value="${cc.projectName }" / readonly></td>
                <td class="tc"><input type="text" class="border0" name="plcc[${(vs.index)}].secondProject" value="${cc.secondProject }" readonly/></td>
                <td class="tc"><input type="text" class="border0" name="plcc[${(vs.index)}].singleOffer" value="${cc.singleOffer }" readonly/></td>
                <td class="tc"><input type="text" class="border0" name="plcc[${(vs.index)}].remark" value="${cc.remark }" readonly/></td>
              </tr>
            </c:forEach>
          </table>
        </div>

        <div class="col-md-12">
          <div class="mt40 tc mb50">
            <button class="btn" type="button" onclick="onStep()">上一步</button>
            <button class="btn btn-windows cancel" type="button" onclick="cancel()">关闭</button>
          </div>
        </div>

    </form>

  </body>

</html>