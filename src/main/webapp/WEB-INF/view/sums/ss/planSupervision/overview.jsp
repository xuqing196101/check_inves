<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
    </script>
  
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
    
    <div class="container container_box">
      <div>
        <h2 class="count_flow"><i>1</i>项目基本信息</h2>
        <ul class="ul_list">
          <table class="table table-bordered mt10">
            <tbody>
              <tr>
                <td width="25%" class="info">项目名称：</td>
                <td width="25%">${project.name}</td>
                <td width="25%" class="info">项目编号：</td>
                <td width="25%">${project.projectNumber}</td>
              </tr>
              <tr>
                <td width="25%" class="info">计划名称：</td>
                <td width="25%">${collectPlan.fileName}</td>
                <td width="25%" class="info">计划编号：</td>
                <td width="25%">${collectPlan.planNo}</td>
              </tr>
              <tr>
                <td width="25%" class="info">需求部门：</td>
                <td width="25%">${collectPlan.department}</td>
                <td width="25%" class="info">采购管理部门：</td>
                <td width="25%">${collectPlan.purchaseId}</td>
              </tr>
              <tr>
                <td width="25%" class="info">项目状态：</td>
                <td width="25%">${project.status}</td>
                <td width="25%" class="info">创建人：</td>
                <td width="25%">${project.appointMan}</td>
              </tr>
              <tr>
                <td width="25%" class="info">创建日期：</td>
                <td width="25%">
                  <fmt:formatDate value='${project.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                </td>
                <td width="25%" class="info"></td>
                <td width="25%"></td>
              </tr>
            </tbody>
          </table>
        </ul>
      </div>
      <div class="padding-top-10 clear">
        <h2 class="count_flow"><i>2</i>流程进度</h2>
      </div>
      <div class="padding-top-10 clear">
        <h2 class="count_flow"><i>3</i>进度详情</h2>
        <ul class="ul_list">
          <table class="table table-bordered mt10">
            <tbody>
              <tr>
                <th class="info">采购需求名称</th>
                <th class="info">需求部门</th>
                <th class="info">编报人</th>
                <th class="info">提报时间</th>
              </tr>
              <tr>
                <td>${purchaseRequired.planName}</td>
                <td>${purchaseRequired.department}</td>
                <td>${purchaseRequired.userId}</td>
                <td><fmt:formatDate value='${purchaseRequired.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss'/></td>
              </tr>
            </tbody>
          </table>
          
          <table class="table table-bordered mt10">
            <tbody>
              <tr>
                <th class="info">受理结果</th>
                <th class="info">采购管理部门</th>
                <th class="info">受理人</th>
                <th class="info">受理时间</th>
              </tr>
              <tr>
                <td><button class="btn" onclick="" type="button">返回</button></td>
                <td>${purchaseRequired.department}</td>
                <td>${purchaseRequired.userId}</td>
                <td><fmt:formatDate value='${purchaseRequired.createdAt}' pattern='yyyy年MM月dd日  HH:mm:ss'/></td>
              </tr>
            </tbody>
          </table>
        </ul>
      </div>
    </div>
  </body>
</html>
