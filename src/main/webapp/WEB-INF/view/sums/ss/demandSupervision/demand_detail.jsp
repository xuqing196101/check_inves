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
  function view(id,type){
      window.location.href = "${pageContext.request.contextPath}/projectSupervision/view.html?id="+id+"&type="+type;
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
        <h2>项目信息
	    </h2>
   </div> 
<!-- 项目戳开始 -->
    
   <div class="container container_box">
      <div>
        <h2 class="count_flow"><i>1</i>需求基本信息</h2>
        <ul class="ul_list">
          <table class="table table-bordered mt10">
            <tbody>
              <tr>
                <td width="25%" class="info">需求名称：</td>
                <td width="25%">${demand.planName}</td>
                <td width="25%" class="info">需求编号：</td>
                <td width="25%">${demand.planNo}</td>
              </tr>
              <tr>
                <td width="25%" class="info">需求状态：</td>
                <td width="25%">
				 <c:if test="${demand.status eq '1'}">未提交</c:if>
                   <c:if test="${demand.status eq '4'}">受理退回</c:if> 
                   <c:if test="${demand.status eq '2' || demand.status eq '3' || demand.status eq '5'}">已提交</c:if>

                 </td>
                 <td width="25%" class="info">创建人：</td>
                <td width="25%">${demand.userId}</td>
              </tr>
              <tr>
                <td width="25%" class="info">创建日期：</td>
                <td width="25%" colspan="3">
                  <fmt:formatDate value='${demand.createdAt}' pattern='yyyy-MM-dd HH:mm:ss' />
                </td>
              </tr>
            </tbody>
          </table>
        </ul>
      </div>
      <div class="padding-top-10 clear">
        <h2 class="count_flow"><i>2</i>计划明细</h2>
        <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr class="info">
              <th class="w50">序号</th>
              <th class="info goodsname">物资类别<br/>及名称</th>
              <th class="info stand">规格型号</th>
              <th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
              <th class="info item">计量<br/>单位</th>
              <th class="info purchasecount">采购<br/>数量</th>
              <th class="info deliverdate">交货<br/>期限</th>
              <th class="info purchasetype">采购方式</th>
              <th class="info purchasename">供应商名称</th>
              <th class="info">查看</th>
            </tr>
          </thead>
          <tbody id="tbody_id">
            <c:forEach items="${list}" var="obj" varStatus="vs">
              <tr class="pointer">
                <td class="tc w50">${obj.seq}</td>
                <td title="${obj.goodsName}" class="tl pl20">
                  <c:if test="${fn:length (obj.goodsName) > 8}">${fn:substring(obj.goodsName,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.goodsName) <= 8}">${obj.goodsName}</c:if>
                </td>
                <td title="${obj.stand}" class="tl pl20">
                  <c:if test="${fn:length (obj.stand) > 8}">${fn:substring(obj.stand,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.stand) <= 8}">${obj.stand}</c:if>
                </td>
                <td title="${obj.qualitStand}" class="tl pl20">
                  <c:if test="${fn:length (obj.qualitStand) > 8}">${fn:substring(obj.qualitStand,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.qualitStand) <= 8}">${obj.qualitStand}</c:if>
                </td>
                <td title="${obj.item}" class="tl pl20">
                  <c:if test="${fn:length (obj.item) > 8}">${fn:substring(obj.item,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.item) <= 8}">${obj.item}</c:if>
                </td>
                <td class="tl pl20">${obj.purchaseCount}</td>
                <td class="tl pl20">${obj.deliverDate}</td>
                <td class="tl pl20">
                  <c:forEach items="${kind}" var="kind">
                    <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                  </c:forEach>
                </td>
                <td title="${obj.supplier}" class="tl pl20">
                  <c:if test="${fn:length (obj.supplier) > 8}">${fn:substring(obj.supplier,0,7)}...</c:if>
                  <c:if test="${fn:length(obj.supplier) <= 8}">${obj.supplier}</c:if>
                </td>
                <td class="tc" onclick="view('${obj.uniqueId}','0')">
                  <div class="easyui-progressbar" data-options="value:60" style="width:80px;"></div>
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
