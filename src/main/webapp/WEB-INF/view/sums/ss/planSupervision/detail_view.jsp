<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
    <link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
    <script type="text/javascript">
      function view(id) {
        window.location.href = "${pageContext.request.contextPath}/planSupervision/overview.html?id=" + id;
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)">首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">业务监管系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购业务监督</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">采购计划监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container">
      <div class="tab-content">
        <div class="tab-pane fade in active" id="dep_tab-0">
          <c:if test="${'0' != type}">
            <h2 class="count_flow jbxx">基本信息</h2>
            <table class="table table-bordered">
              <tbody>
                <tr>
                  <td class="bggrey">采购管理部门：</td>
                  <td>${collectPlan.purchaseId}</td>
                  <td class="bggrey">计划名称：</td>
                  <td>${collectPlan.fileName}</td>
                  <td class="bggrey">计划编号：</td>
                  <td>${collectPlan.planNo}</td>
                </tr>
                <tr>
                  <td class="bggrey">计划下达时间：</td>
                  <td>
                    <fmt:formatDate type='date' value='${collectPlan.orderAt}' pattern=" yyyy-MM-dd HH:mm:ss " />
                  </td>
                  <td class="bggrey">联系人：</td>
                  <td>${collectPlan.userId}</td>
                  <td class="bggrey"></td>
                  <td></td>
                </tr>
              </tbody>
            </table>
          </c:if>
          <h2 class="count_flow jbxx">
              <c:choose>
            <c:when test="${'0' eq type}">需求明细</c:when>
            <c:otherwise>采购明细</c:otherwise>
          </c:choose>
             </h2>
          <div class="col-md-12 col-sm-12 col-xs-12 p0">
            <table class="table table-bordered table-condensed table-hover ">
              <thead>
                <tr class="info">
                  <th class="w50">序号</th>
                  <th class="info goodsname">物资类别<br/>及名称</th>
                  <th class="info stand">规格型号</th>
                  <th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
                  <th class="info item">计量<br/>单位</th>
                  <th class="info purchasecount">采购<br/>数量</th>
                  <th class="info w150">单价<br/>（元）</th>
                  <th class="info w150">预算金额<br/>（万元）</th>
                  <th class="info deliverdate">交货<br/>期限</th>
                  <th class="info purchasetype">采购方式</th>
                  <th class="info purchasename">供应商名称</th>
                  <th class="info">进度</th>
                </tr>
              </thead>
              <tbody id="tbody_id">
                <c:forEach items="${list}" var="obj" varStatus="vs">
                  <tr class="pointer">
                    <td class="tc w50">${obj.seq}</td>
                    <td title="${obj.goodsName}" class="tl pl20">
                      <c:if test="${fn:length (obj.goodsName) > 8}">${fn:substring(obj.goodsName,0,7)}...</c:if>
                      <c:if test="${fn:length(obj.goodsName) <= 8}">${obj.goodsName}</c:if>
                    </td>
                    <td title="${obj.stand}" class="tl pl20">
                      <c:if test="${fn:length (obj.stand) > 8}">${fn:substring(obj.stand,0,7)}...</c:if>
                      <c:if test="${fn:length(obj.stand) <= 8}">${obj.stand}</c:if>
                    </td>
                    <td title="${obj.qualitStand}" class="tl pl20">
                      <c:if test="${fn:length (obj.qualitStand) > 8}">${fn:substring(obj.qualitStand,0,7)}...</c:if>
                      <c:if test="${fn:length(obj.qualitStand) <= 8}">${obj.qualitStand}</c:if>
                    </td>
                    <td title="${obj.item}" class="tl pl20">
                      <c:if test="${fn:length (obj.item) > 8}">${fn:substring(obj.item,0,7)}...</c:if>
                      <c:if test="${fn:length(obj.item) <= 8}">${obj.item}</c:if>
                    </td>
                    <td class="tl pl20">${obj.purchaseCount}</td>
                    <td class="tr pr20">${obj.price}</td>
                    <td class="tr pr20">${obj.budget}</td>
                    <td class="tl pl20">${obj.deliverDate}</td>
                    <td class="tl pl20">${obj.purchaseType}</td>
                    <td title="${obj.supplier}" class="tl pl20">
                      <c:if test="${fn:length (obj.supplier) > 8}">${fn:substring(obj.supplier,0,7)}...</c:if>
                      <c:if test="${fn:length(obj.supplier) <= 8}">${obj.supplier}</c:if>
                    </td>
                    <td class="tc" onclick="view('${obj.id}')">
                      <c:if test="${obj.price != null}">
                        <div id="p" class="easyui-progressbar" data-options="value:60" style="width:80px;"></div>
                      </c:if>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
    </div>
  </body>

</html>