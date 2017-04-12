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
  <script type="text/javascript">
  function planDateil(id,type){
     location.href = "${pageContext.request.contextPath}/contractSupervision/planDateil.html?id="+id+"&type="+type+"&contractId=${contractId}";
    }
    </script>
  </head>
  
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">业务监管系统</a></li><li><a href="#">采购业务监督</a></li><li><a href="#">采购合同监督</a></li>
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
              <th class="info w50">序号</th>
              <th class="info">采购计划编号</th>
              <th class="info">采购计划名称</th>
              <th class="info">预算总金额（万元）</th>
              <th class="info">编制人</th>
              <th class="info">编制时间</th>
              <th class="info">状态</th>
              <th class="info">查看进度</th>
            </tr>
          </thead>
          <c:forEach items="${list}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50">${(vs.index+1)}</td>
               <td class="tl pl20" width="10%">
                ${obj.planNo}
              </td>
              <td class="tl pl20" width="25%">
                <a href="javascript:void(0)" onclick="planDateil('${obj.id}');">${obj.fileName}</a>
              </td>
              <td class="tr pr20 w140">
                <fmt:formatNumber>${obj.budget }</fmt:formatNumber>
              </td>
              <td class="tr pr20 w140">${obj.userId}</td>
              <td class="tc">
                <fmt:formatDate value="${obj.createdAt }" pattern="yyyy-MM-dd" />
              </td>
              <td class="tl pl20">
                <c:if test="${obj.status == 1}">审核轮次设置</c:if>
                <c:if test="${obj.status == 2}">已下达</c:if>
                <c:if test="${obj.status == 3}">第一轮审核</c:if>
                <c:if test="${obj.status == 4}">第二轮审核人员设置</c:if>
                <c:if test="${obj.status == 5}">第二轮审核</c:if>
                <c:if test="${obj.status == 6}">第三轮审核人员设置</c:if>
                <c:if test="${obj.status == 7}">第三轮审核</c:if>
                <c:if test="${obj.status == 12}">未下达</c:if>
              </td>
              <td class="tc">
                <a href="javascript:void(0)" onclick="planDateil('${obj.id}','1');">进入</a>
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
