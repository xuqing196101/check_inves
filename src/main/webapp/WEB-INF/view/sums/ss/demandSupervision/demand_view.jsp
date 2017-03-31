<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
      function view(id){
    	 window.location.href = "${pageContext.request.contextPath}/supervision/demandDetail.html?requiredId="+id;
      }
      function planDetail(id){
     	 window.location.href = "${pageContext.request.contextPath}/supervision/planDetail.html?requiredId="+id;
       }
      function projectDetail(id){
      	 window.location.href = "${pageContext.request.contextPath}/supervision/projectDetail.html?requiredId="+id;
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
          <td class="h365 tc" onclick="view('${requiredId}')">
            <img alt="" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
            <%-- <c:forEach items="${lists}" var="obj">
              <p class="ml20 tl">需求部门：${obj.department}</p>
              <p class="ml20 tl">需求名称：${obj.planName}</p>
              <p class="ml20 tl">编报时间：<fmt:formatDate type='date' value='${obj.createdAt}' pattern=" yyyy-MM-dd HH:mm:ss " /></p>
              <p class="ml20 tl">联系人：${obj.userId}</p>
            </c:forEach> --%>
          </td>
          <td class="h365 tc" onclick="planDetail('${requiredId}')">
            <img alt="" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
            <%-- <c:forEach items="${list}" var="obj">
	            <p class="ml20 tl">采购管理部门：${obj.purchaseId}</p>
	            <p class="ml20 tl">计划名称：${obj.fileName}</p>
	            <p class="ml20 tl">计划编号：${obj.planNo}</p>
	            <p class="ml20 tl">计划下达时间：<fmt:formatDate type='date' value='${obj.updatedAt}' pattern=" yyyy-MM-dd HH:mm:ss " /></p>
	            <p class="ml20 tl">联系人：${obj.userId}</p>
            </c:forEach> --%>
          </td>
          <td class="h365 tc"  onclick="projectDetail('${requiredId}')">
            <img alt="" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
          </td>
          <td class="h365 tc">
            <img alt="" onclick="" src="${pageContext.request.contextPath}/public/backend/images/u43.png">
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
