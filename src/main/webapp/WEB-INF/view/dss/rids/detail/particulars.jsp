<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      function back(){
        var purchaseType = "${project.purchaseType}";
        var purchaseDepId = "${project.purchaseDepId}";
        window.location.href = "${pageContext.request.contextPath}/project/selectByProject.html?purchaseType=" + purchaseType + "&purchaseDepId=" + purchaseDepId;
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
            <a href="javascript:void(0)">决策支持</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购资源综合展示</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/resAnalyze/list.html')">采购资源展示</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">采购项目列表</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">项目详情</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <!-- 录入采购计划开始-->
    <div class="container">
    <div class="tab-content">
      <div class="tab-pane fade in active" id="dep_tab-0">
        <h2 class="count_flow jbxx">项目信息</h2>
        <table class="table table-bordered">
          <tbody>
            <tr>
              <td class="bggrey">项目名称：</td>
              <td>${project.name}</td>
              <td class="bggrey">项目编号：</td>
              <td>${project.projectNumber}</td>
            </tr>
          </tbody>
        </table>
        <h2 class="count_flow jbxx">项目明细查看</h2>

        <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto" id="content">
          <!-- 项目戳开始 -->
          <c:if test="${list ne null}">
            <table id="table" class="table table-bordered table-condensed lockout">
              <thead>
                <tr class="space_nowrap">
                  <th class="info w50">序号</th>
                  <th class="info w80">需求部门</th>
                  <th class="info w80">产品名称</th>
                  <th class="info w80">规格型号</th>
                  <th class="info w80">质量技术标准<br/>(技术参数)</th>
                  <th class="info w80">计量<br/>单位</th>
                  <th class="info w80">采购<br/>数量</th>
                  <th class="info w80">交货期限</th>
                  <th class="info w100">采购方式</th>
                  <th class="info w100">供应商名称</th>
                  <th class="info w160">备注</th>
                </tr>
              </thead>
              <c:forEach items="${list}" var="obj" varStatus="vs">
                <tr style="cursor: pointer;">
                  <td class="tc w50">
                    <div class="w50">${obj.serialNumber}</div>
                  </td>
                  <td class="">
                    <div class="w80">${obj.department}</div>
                  </td>
                  <td class="">
                    <div class="w80">${obj.goodsName}</div>
                  </td>
                  <td class="">
                    <div class="w80">${obj.stand}</div>
                  </td>
                  <td class="">
                    <div class="w80">${obj.qualitStand}</div>
                  </td>
                  <td class="tc">
                    <div class="w80">${obj.item}</div>
                  </td>
                  <td class="tc">
                    <div class="w80">${obj.purchaseCount}</div>
                  </td>
                  <td class="">
                    <div class="w80">${obj.deliverDate}</div>
                  </td>
                  <td class="tc">
                    <div class="w100">${obj.purchaseType}</div>
                  </td>
                  <td class="">
                    <div class="w100">${obj.supplier}</div>
                  </td>
                  <td class="">
                    <div class="w160">${obj.memo}</div>
                  </td>
                </tr>
              </c:forEach>
            </table>
          </c:if>
        </div>
        <div class="col-md-12 tc col-xs-12 col-sm-12 mt10">
          <button class="btn btn-windows back" onclick="back();">返回</button>
        </div>
      </div>
    </div>
    </div>
  </body>

</html>