<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>标书管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="${pageContext.request.contextPath}/public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/public/ZHH/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/ZHH/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
	 
</script>


</head>

<body>
	<div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">项目评审</a></li><li><a href="#">供应商报价信息</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
    </div>
     <!-- 项目戳开始 -->
  <div class="container clear">
  <!--详情开始-->
    <div class="row magazine-page">
      <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
         <div class="tab-pane fade active in height-450">
									<table  class="table table-bordered table-condensed mt5">
							        <thead>
							        <tr>
							          <th class="info w50">序号</th>
							          <th class="info">物资名称</th>
							          <th class="info">规格型号</th>
							          <th class="info">质量技术标准</th>
							          <th class="info">计量单位</th>
							          <th class="info">采购数量</th>
							          <th class="info">单价（元）</th>
							          <th class="info">小计</th>
							          <th class="info">交货时间</th>
							          <th class="info">备注</th>
							        </tr>
							        </thead>
							        <c:set var="TOTAL" value="0"></c:set>
							          <c:forEach items="${historyList}" var="lq" varStatus="vs">
							            <tr class="hand">
							              <td class="tc w50">${lq.projectDetail.serialNumber }</td>
							              <td class="tc">${lq.projectDetail.goodsName }</td>
							              <td class="tc">${lq.projectDetail.stand }</td>
							              <td class="tc">${lq.projectDetail.qualitStand }</td>
							              <td class="tc">${lq.projectDetail.item }</td>
							              <td class="tc">${lq.projectDetail.purchaseCount }</td>
							              <td class="tc">${lq.quotePrice }</td>
							              <c:set var="TOTAL" value="${TOTAL+lq.total }"></c:set>
							              <td class="tc">${lq.total }</td>
							              <td class="tc"><fmt:formatDate value="${lq.deliveryTime }" pattern="YYYY-MM-dd"/></td>
							              <td class="tc">${lq.remark }</td>
							            </tr>
							         </c:forEach>  
							         <tr>
							         	<td class="tr" colspan="2"><b>总金额(元):${TOTAL }</b></td>
							         	<td class="tl" colspan="8"></td>
							         </tr>
							      </table>
		                    </div>
          </div>
      </div>
    </div>
</div>
   <div class=" tc col-md-12">
       <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	</div>
</body>
</html>
