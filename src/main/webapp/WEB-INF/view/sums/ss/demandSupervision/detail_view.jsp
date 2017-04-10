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
            <a href="javascript:void(0)">采购需求监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container">
      <div class="tab-content">
        <div class="tab-pane fade in active" id="dep_tab-0">
            <h2 class="count_flow jbxx">基本信息</h2>
            <c:if test="${'0' eq type}">
            <table class="table table-bordered mt10">
            <tbody>
              <tr>
                <td width="15%" class="info">需求名称：</td>
                <td width="35%">${demand.planName}</td>
                <td width="15%" class="info">需求编号：</td>
                <td width="35%">${demand.planNo}</td>
              </tr>
              <tr>
                <td width="15%" class="info">需求状态：</td>
                <td width="25%">
                  <c:if test="${demand.status eq '1'}">未提交</c:if>
                  <c:if test="${demand.status eq '4'}">受理退回</c:if> 
                  <c:if test="${demand.status eq '2' || demand.status eq '3' || demand.status eq '5'}">已提交</c:if>
                 </td>
                 <td width="15%" class="info">创建人：</td>
                <td width="25%">${demand.userId}</td>
              </tr>
              <tr>
                <td width="15%" class="info">创建日期：</td>
                <td width="25%" colspan="3">
                  <fmt:formatDate value='${demand.createdAt}' pattern='yyyy-MM-dd HH:mm:ss' />
                </td>
              </tr>
            </tbody>
          </table>
          </c:if>
          <c:if test="${'1' eq type}">
            <table class="table table-bordered mt10">
            <tbody>
              <tr>
                <td width="25%" class="info">计划名称：</td>
                <td width="25%">${collectPlan.fileName}</td>
                <td width="25%" class="info">计划编号：</td>
                <td width="25%">${collectPlan.planNo}</td>
              </tr>
              <tr>
                <td width="25%" class="info">计划状态：</td>
                <td width="25%">
                <c:if test="${collectPlan.status == 1}">审核轮次设置</c:if>
                <c:if test="${collectPlan.status == 2}">已下达</c:if>
                <c:if test="${collectPlan.status == 3}">第一轮审核</c:if>
                <c:if test="${collectPlan.status == 4}">第二轮审核人员设置</c:if>
                <c:if test="${collectPlan.status == 5}">第二轮审核</c:if>
                <c:if test="${collectPlan.status == 6}">第三轮审核人员设置</c:if>
                <c:if test="${collectPlan.status == 7}">第三轮审核</c:if>
                <c:if test="${collectPlan.status == 12}">未下达</c:if>
                 </td>
                 <td width="25%" class="info">创建人：</td>
                <td width="25%">${collectPlan.userId}</td>
              </tr>
              <tr>
                <td width="25%" class="info">创建日期：</td>
                <td width="25%" colspan="3">
                  <fmt:formatDate value='${collectPlan.createdAt}' pattern='yyyy-MM-dd HH:mm:ss' />
                </td>
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
                  <th class="info " width="10%">物资类别及名称</th>
                  <th class="info ">规格型号</th>
                  <th class="info " width="15%">质量技术标准<br/>(技术参数)</th>
                  <th class="info item">计量<br/>单位</th>
                  <th class="info ">采购<br/>数量</th>
                  <th class="info w80">单价<br/>（元）</th>
                  <th class="info w120" >预算<br/>金额<br/>（万元）</th>
                  <th class="info " width="10%" >交货期限</th>
                  <th class="info " width="8%">采购方式</th>
                  <th class="info " width="10%">供应商名称</th>
                  <th class="info ">进度</th>
                </tr>
              </thead>
              <tbody id="tbody_id">
                <c:forEach items="${list}" var="obj" varStatus="vs">
                  <tr class="pointer">
                    <td class="tc w50">${obj.seq}</td>
                    <td title="${obj.goodsName}" class="tl pl20">
                      ${obj.goodsName}
                    </td>
                    <td title="${obj.stand}" class="tl pl20">
                      ${obj.stand}
                    </td>
                    <td title="${obj.qualitStand}" class="tl pl20">
                      ${obj.qualitStand}
                    </td>
                    <td title="${obj.item}" class="tl pl20">
                     ${obj.item}
                    </td>
                    <td class="tl pl20">${obj.purchaseCount}</td>
                    <td class="tr pr20">${obj.price}</td>
                    <td class="tr pr20">${obj.budget}</td>
                    <td class="tl pl20">${obj.deliverDate}</td>
                    <td class="tl pl20">
                      <c:forEach items="${kind}" var="kind">
		                    <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
		                  </c:forEach>
                    </td>
                    <td title="${obj.supplier}" class="tl pl20">
                      ${obj.supplier}
                    </td>
                    <td class="tc" onclick="view('${obj.id}')">
                      <c:if test="${obj.price != null}">
                        <div id="p" class="easyui-progressbar" data-options="value:${obj.progressBar}" style="width:80px;"></div>
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