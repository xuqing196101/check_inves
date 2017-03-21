<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
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
      <div class="headline-v2 fl">
        <h2>采购计划查看</h2>
      </div>
      <table class="table table-bordered">
        <tbody>
          <tr>
            <td class="bggrey">计划名称：</td>
            <td>${collectPlan.fileName}</td>
            <td class="bggrey ">计划编号：</td>
            <td>${collectPlan.planNo}</td>
          </tr>
          <tr>
            <td class="bggrey ">预算金额：</td>
            <td>${collectPlan.budget}</td>
            <td class="bggrey ">状态：</td>
            <td>
              <c:if test="${collectPlan.status eq '1'}">审核轮次设置</c:if>
              <c:if test="${collectPlan.status eq '3'}">第一轮审核</c:if>
              <c:if test="${collectPlan.status eq '4'}">第二轮审核人员设置</c:if>
              <c:if test="${collectPlan.status eq '5'}">第二轮审核</c:if>
              <c:if test="${collectPlan.status eq '6'}">第三轮审核人员设置</c:if>
              <c:if test="${collectPlan.status eq '7'}">第三轮审核</c:if>
              <c:if test="${collectPlan.status eq '8'}">审核结束</c:if>
              <c:if test="${collectPlan.status eq '12'}">未下达</c:if>
              <c:if test="${collectPlan.status eq '2'}">已下达</c:if>
            </td>
          </tr>
          <tr>
            <td class="bggrey">编制时间：</td>
            <td><fmt:formatDate value='${collectPlan.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss' /></td>
            <td class="bggrey">创建人：</td>
            <td>${collectPlan.userId}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </body>

</html>