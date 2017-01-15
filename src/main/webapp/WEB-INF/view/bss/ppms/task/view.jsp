<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js"></script>

    <script type="text/javascript"></script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购任务管理</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">查看采购计划</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container mt20">
      <div class="tab-content">
        <div class="tab-v2">
          <ul class="nav nav-tabs bgwhite">
            <li class="active">
              <a href="#dep_tab-0" data-toggle="tab" class="f18">详细信息</a>
            </li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade in active" id="dep_tab-0">
              <h2 class="count_flow jbxx">基本信息</h2>
              <table class="table table-bordered">
                <tbody>
                  <tr>
                    <td class="bggrey">计划名称：</td>
                    <td>${task.name}</td>
                    <td class="bggrey">计划编号：</td>
                    <td>${task.documentNumber}</td>
                    <c:if test="${projectId != null}">
                      <td class="bggrey">预研通知书：</td>
                      <td><u:show showId="upload_id" businessId="${projectId}" sysKey="2" delete="false" typeId="${advancedAdvice}" /></td>
                    </c:if>
                  </tr>
                </tbody>
              </table>
              <h2 class="count_flow jbxx">需求明细查看</h2>
              <div class="content" id="content">
                <table id="table" class="table table-bordered table-condensed table-hover table-striped table_wrap">
                  <thead>
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info">需求部门</th>
                      <th class="info">物资名称</th>
                      <th class="info">规格型号</th>
                      <th class="info">质量技术标准</th>
                      <th class="info">计量单位</th>
                      <th class="info">采购数量</th>
                      <th class="info">交货期限</th>
                      <th class="info">采购方式</th>
                      <th class="info">采购管理部门</th>
                      <th class="info">供应商名称</th>
                      <th class="info">是否申请办理免税</th>
                      <th class="info">物资用途（进口）</th>
                      <th class="info">使用单位（进口）</th>
                      <th class="info">备注</th>
                    </tr>
                  </thead>
                  <c:if test="${lists != null}">
                    <c:forEach items="${lists}" var="obj" varStatus="vs">
                      <tr class="pointer">
                        <td class="tc w50">${obj.seq}</td>
                        <td class="tl pl20">
                          ${obj.department}
                        </td>
                        <td class=" tl pl20">${obj.goodsName}</td>
                        <td class=" tl pl20">${obj.stand}</td>
                        <td class="tl pl20">${obj.qualitStand}</td>
                        <td class="tc">${obj.item}</td>
                        <td class="tc">${obj.purchaseCount}</td>
                        <td class="rl pl20">${obj.deliverDate}</td>
                        <td class="tc">
                          <c:forEach items="${kind}" var="kind">
                            <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                          </c:forEach>
                        </td>
                        <td class=" tl pl20">
                          <c:forEach items="${list2}" var="list">
                            <c:if test="${obj.organization eq list.id}">${list.name}</c:if>
                          </c:forEach>
                        </td>
                        <td class=" tl pl20">${obj.supplier}</td>
                        <td class="tc">${obj.isFreeTax}</td>
                        <td class="tl pl20">${obj.goodsUse}</td>
                        <td class="tl pl20">${obj.useUnit}</td>
                        <td class="tl pl20">${obj.memo}</td>
                      </tr>
                    </c:forEach>
                  </c:if>
                  <c:if test="${list != null}">
                    <c:forEach items="${list}" var="obj" varStatus="vs">
                      <tr style="cursor: pointer;">
                        <td class="tc w50">${obj.serialNumber}</td>
                        <td class="tl pl20">${obj.department}</td>
                        <td class="tl pl20">${obj.goodsName}</td>
                        <td class="tl pl20">${obj.stand}</td>
                        <td class="tl pl20">${obj.qualitStand}</td>
                        <td class="tc">${obj.item}</td>
                        <td class="tc">${obj.purchaseCount}</td>
                        <td class="tl pl20">${obj.deliverDate}</td>
                        <td class="tc">
                              <c:forEach items="${kind}" var="kind">
                                <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                              </c:forEach>
                        </td>
                        <td class=" tl pl20">
                          <c:forEach items="${list2}" var="list">
                            <c:if test="${obj.organization eq list.id}">${list.name}</c:if>
                          </c:forEach>
                        </td>
                        <td class="tl pl20">${obj.supplier}</td>
                        <td class="tl pl20">${obj.isFreeTax}</td>
                        <td class="tl pl20">${obj.goodsUse}</td>
                        <td class="tl pl20">${obj.useUnit}</td>
                        <td class="tl pl20">${obj.memo}</td>
                      </tr>
                    </c:forEach>
                  </c:if>
                </table>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-12 tc col-xs-12 col-sm-12 mt10">
          <button class="btn btn-windows back" onclick="location.href='javascript:history.go(-1);'">返回</button>
        </div>
      </div>

    </div>
  </body>

</html>