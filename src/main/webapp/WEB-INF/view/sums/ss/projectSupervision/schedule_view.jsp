<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      function view(id){
        window.location.href = "${pageContext.request.contextPath}/projectSupervision/viewOver.html?projectId="+id;
      }
      function contractList(id){
    	  window.location.href = "${pageContext.request.contextPath}/projectSupervision/contractList.html?projectId="+id;
      }
      function planList(id){
    	  window.location.href = "${pageContext.request.contextPath}/projectSupervision/planList.html?projectId="+id;
      }
      function demandList(id){
    	  window.location.href = "${pageContext.request.contextPath}/projectSupervision/demandList.html?projectId="+id;
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
            <a href="javascript:void(0)">采购项目监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container">
    <div class="headline-v2">
      <h2>进度列表</h2>
    </div>
    <div class="content table_box">
    <table class="table table-bordered">
      <tbody>
        <tr>
          <td class="w350 tc">采购需求</td>
          <td class="w350 tc">采购计划</td>
          <td class="w350 tc">采购项目</td>
          <td class="w350 tc">采购合同</td>
          
        </tr>
        <tr>
          <td class="h365 tc" onclick="demandList('${projectId}')">
            <img alt="" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
          </td>
          <td class="h365 tc" onclick="planList('${projectId}')">
            <img alt="" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
          </td>
          <td class="h365 tc"  onclick="view('${projectId}')">
            <img alt="" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
          </td>
          <td class="h365 tc">
            <img alt="" onclick="contractList('${projectId}')" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
          </td>
          
        </tr>

      </tbody>
    </table>
    </div>
  </div>
  <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
      <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
  </div>
  </body>
</html>
