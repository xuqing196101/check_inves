<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
  <head>
  	<jsp:include page="/WEB-INF/view/common.jsp"/>
    <title>项目列表</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
	<link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
  <script type="text/javascript">
  function demandDateil(id,contractId){
     location.href = "${pageContext.request.contextPath}/contractSupervision/demandDateil.html?id="+id+"&contractId=${contractId}";
    }
    </script>
  </head>
  
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">业务监管系统</a></li><li><a href="javascript:void(0);">采购业务监督</a></li><li><a href="javascript:void(0);">采购合同监督</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container">
   <div class="headline-v2">
        <h2>计划列表
	    </h2>
   </div> 
<!-- 项目戳开始 -->
    <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="w50">序号</th>
              <th width="15%">需求编号</th>
              <th width="20%">需求名称</th>
              <th width="15%">填报人</th>
              <th width="12%">填报时间</th>
              <th width="10%">金额</th>
              <th>状态</th>
              <th>查看进度</th>
            </tr>
          </thead>
          <c:forEach items="${list}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50">${(vs.index+1)}</td>
               <td class="tl">
                ${obj.planNo}
              </td>
              <td class="tl">
                <a href="javascript:void(0)" onclick="demandDateil('${obj.id}','${contractId}');">${obj.planName}</a>
              </td>
              <td class="tr">${obj.userId}</td>
              <td class="tc">
                <fmt:formatDate value="${obj.createdAt }" pattern="yyyy-MM-dd" />
              </td>
    			<td class="tr">
                <fmt:formatNumber>${obj.budget }</fmt:formatNumber>
              </td>
				<td class="tl">
                   <c:if test="${obj.status eq '1'}">未提交</c:if>
                   <c:if test="${obj.status eq '4'}">受理退回</c:if> 
                   <c:if test="${obj.status eq '2' || obj.status eq '3' || obj.status eq '5'}">已提交</c:if>
                </td>              
                <td class="tc">
                <a href="javascript:void(0)" onclick="demandDateil('${obj.id}','${contractId}');">进入</a>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
  <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
   </div>
   
</body>
</html>
