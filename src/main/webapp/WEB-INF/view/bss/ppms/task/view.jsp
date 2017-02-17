<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
    <%-- <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js"></script> --%>

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
              <div class="col-md-12 col-sm-12 col-xs-12 p0 over_scroll" id="content">
                <table id="table" class="table table-bordered table-condensed"  style="width: 1600px; color: #000000; border-right-color: black; font-size: medium; border-left-color: black">
                  <thead>
                    <tr class="space_nowrap">
                      <th class="info w50">序号</th>
                      <th class="info w80">需求部门</th>
                      <th class="info w80">物资类别及<br/>物资名称</th>
                      <th class="info w80">规格型号</th>
                      <th class="info w80">质量技术标准</th>
                      <th class="info w80">计量<br>单位</th>
                      <th class="info w80">采购<br>数量</th>
                      <th class="info w80">交货期限</th>
                      <th class="info w100">采购方式</th>
                      <th class="info w80">采购机构</th>
                      <th class="info w100">供应商名称</th>
                      <th class="info w80">是否申请<br>办理免税</th>
                      <th class="info w80">物资用途<br>（进口）</th>
                      <th class="info w80">使用单位<br>（进口）</th>
                      <th class="info w160">备注</th>
                    </tr>
                  </thead>
                  <c:if test="${lists != null}">
                    <c:forEach items="${lists}" var="obj" varStatus="vs">
                      <tr class="pointer">
                        <td class="tc w50">${obj.seq}</td>
                        <td class="tl">
                         <div class="w80">
                           ${obj.department}
                         </div>
                        </td>
                        <td class=" tl">
                          <div class="w80">${obj.goodsName}</div>
                        </td>
                        <td class=" tl">
                          <div class="w80">${obj.stand}</div>
                        </td>
                        <td class="tl">
                          <div class="w80">${obj.qualitStand}</div>
                        </td>
                        <td class="tc">
                          <div class="w80">${obj.item}</div>
                        </td>
                        <td class="tc">
                          <div class="w80">${obj.purchaseCount}</div>
                        </td>
                        <td class="tl">
                          <div class="w80">${obj.deliverDate}</div>
                        </td>
                        <td class="tc">
                          <div class="w100">
                            <c:forEach items="${kind}" var="kind">
                              <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                            </c:forEach>
                          </div>
                        </td>
                        <td class=" tl">
                           <div class="w80">
                             <c:forEach items="${list2}" var="list">
                               <c:if test="${obj.organization eq list.id}">${list.name}</c:if>
                             </c:forEach>
                           </div>
                        </td>
                        <td class=" tl">
                           <div class="w100">${obj.supplier}</div>
                        </td>
                        <td class="tc">
                           <div class="w80">${obj.isFreeTax}</div>
                        </td>
                        <td class="tl">
                           <div class="w80">${obj.goodsUse}</div>
                        </td>
                        <td class="tl">
                           <div class="w80">${obj.useUnit}</div>
                        </td>
                        <td class="tl">
                           <div class="w160">${obj.memo}</div>
                        </td>
                      </tr>
                    </c:forEach>
                  </c:if>
                  <c:if test="${list != null}">
                    <c:forEach items="${list}" var="obj" varStatus="vs">
                      <tr style="cursor: pointer;">
                        <td class="tc w50">${obj.serialNumber}</td>
                        <td class="tl"><div class="w80">${obj.department}</div></td>
                        <td class="tl"><div class="w80">${obj.goodsName}</div></td>
                        <td class="tl"><div class="w80">${obj.stand}</div></td>
                        <td class="tl"><div class="w80">${obj.qualitStand}</div></td>
                        <td class="tc"><div class="w80">${obj.item}</div></td>
                        <td class="tc"><div class="w80">${obj.purchaseCount}</div></td>
                        <td class="tl"><div class="w80">${obj.deliverDate},/</td>
                        <td class="tc">
                              <c:forEach items="${kind}" var="kind">
                                <c:if test="${kind.id == obj.purchaseType}">
                                  <div class="w100 tc">${kind.name}</div>
                                </c:if>
                              </c:forEach>
                        </td>
                        <td class="tl">
                          <c:forEach items="${list2}" var="list">
                            <c:if test="${obj.organization eq list.id}">
                              <div class="w80">${list.name}</div>
                            </c:if>
                          </c:forEach>
                        </td>
                        <td class="tl">
                           <div class="w100">${obj.supplier}</div>
                        </td>
                        <td class="tc">
                           <div class="w80">${obj.isFreeTax}</div>
                        </td>
                        <td class="tl">
                           <div class="w80">${obj.goodsUse}</div>
                        </td>
                        <td class="tl">
                           <div class="w80">${obj.useUnit}</div>
                        </td>
                        <td class="tl pl20">
                           <div class="w160">${obj.memo}</div>
                        </td>
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