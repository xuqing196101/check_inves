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
      $(function() {
        var index = 0;
        var divObj = $(".p0" + index);
        $(divObj).removeClass("hide");
        $("#package").removeClass("shrink");
        $("#package").addClass("spread");

      });

      function ycDiv(obj, index) {
        if($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
          $(obj).removeClass("shrink");
          $(obj).addClass("spread");
        } else {
          if($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
            $(obj).removeClass("spread");
            $(obj).addClass("shrink");
          }
        }

        var divObj = new Array();
        divObj = $(".p0" + index);
        for(var i = 0; i < divObj.length; i++) {
          if($(divObj[i]).hasClass("p0" + index) && $(divObj[i]).hasClass("hide")) {
            $(divObj[i]).removeClass("hide");
          } else {
            if($(divObj[i]).hasClass("p0" + index)) {
              $(divObj[i]).addClass("hide");
            };
          };
        };
      }
      
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
      <div class="headline-v2">
        <h2>项目明细</h2>
      </div>
      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
      <div class="content table_box">
        <!-- 包明细 -->
        <c:if test="${packages != null}">
          <c:forEach items="${packages}" var="list" varStatus="vs">
            <c:set value="${vs.index}" var="index"></c:set>
            <h2 onclick="ycDiv(this,'${index}')" class="count_flow shrink hand" id="package">
              <span class="f14 blue">${packages[index].name}</span>
            </h2>
            <div class="p0${index} hide">
              <table class="table table-bordered table-condensed table-hover table-striped">
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
                  <c:forEach items="${list.projectDetails}" var="obj" varStatus="vs">
                    <tr class="pointer">
                      <td class="tc w50">${obj.serialNumber}</td>
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
                        <div id="p" class="easyui-progressbar" data-options="value:60" style="width:80px;"></div>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </c:forEach>
        </c:if>
        <!-- 项目明细 -->
        <c:if test="${details != null}">
          <table class="table table-bordered table-condensed table-hover table-striped">
            <thead>
              <tr class="info">
                <th class="w50">序号</th>
                <th class="info goodsname">物资类别<br/>及名称</th>
                <th class="info stand">规格型号</th>
                <th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
                <th class="info item">计量<br/>单位</th>
                <th class="info purchasecount">采购<br/>数量</th>
                <th class="info w150">单位<br/>（元）</th>
                <th class="info w150">预算金额<br/>（万元）</th>
                <th class="info deliverdate">交货<br/>期限</th>
                <th class="info purchasetype">采购方式</th>
                <th class="info purchasename">供应商名称</th>
                <th class="info">进度</th>
              </tr>
            </thead>
            <tbody id="tbody_id">
              <c:forEach items="${details}" var="obj" varStatus="vs">
                <tr class="pointer">
                  <td class="tc w50">${obj.serialNumber}</td>
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
                  <td class="tc">
                    <div id="p" class="easyui-progressbar" data-options="value:60" style="width:80px;"></div>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:if>
      </div>
    </div>
  </body>

</html>