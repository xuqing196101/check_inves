<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>标书管理</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet">
    <link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
	<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
    <script src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
	 $(function addTotal(){
    	var allTable=document.getElementsByTagName("table");
		for(var i=0;i<allTable.length;i++){
		    var totalMoney=0;
			for (var j = 1; j < allTable[i].rows.length-1; j++) {    //遍历Table的所有Row
				  var num= $(allTable[i].rows).eq(j).find("td").eq("5").text();
		          var price= $(allTable[i].rows).eq(j).find("td").eq("6").text();
		          var total= $(allTable[i].rows).eq(j).find("td").eq("7").text();
		          if(price==""||price.trim()==""){
		          	continue;
		          } else{
		          	 totalMoney+=parseFloat(price*num);
		          	 $(allTable[i].rows).eq(allTable[i].rows.length-1).find("td").eq("1").text(parseFloat(totalMoney).toFixed(2));
		          }
		    } 
		}
    });
</script>


</head>

<body onload="OpenFile()">
	<div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">我的项目</a></li><li><a href="#">标书管理</a></li>
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
          <ul class="nav nav-tabs bgdd">
          <c:forEach items="${listPackage }"  var="obj" varStatus="vs" >
		     <c:if test="${vs.index==0 }">
		     	<li class="active">
		     		<a aria-expanded="true" href="#tab-${vs.index+1 }" data-toggle="tab" title="${obj.name }" >
		     			<c:choose>
		     				<c:when test="${fn:length(obj.name)>3}">${fn:substring(obj.name, 0, 3)}...</c:when>
		     				<c:otherwise>${obj.name}</c:otherwise>
		     			</c:choose>
		     		</a>
		     	</li>
		     </c:if>
		     <c:if test="${vs.index>0 }">
		     	<li class="">
		     		<a aria-expanded="true" href="#tab-${vs.index+1 }" data-toggle="tab" title="${obj.name }" >
		     			<c:choose>
		     				<c:when test="${fn:length(obj.name)>3}">${fn:substring(obj.name, 0, 3)}...</c:when>
		     				<c:otherwise>${obj.name}</c:otherwise>
		     			</c:choose>
		     		</a>
		    	 </li>
		      </c:if>
		  </c:forEach>
          </ul>
            <div class="tab-content">
             <c:forEach items="${listQuote }" var="listQuote1" varStatus="vs" >
                  <c:choose>
                  		<c:when test="${vs.index==0 }">
	                  		<div class="tab-pane fade active in height-450" id="tab-${vs.index+1 }">
									<table id="tb${vs.index }" class="table table-bordered table-condensed mt5">
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
							          <c:forEach items="${listQuote1}" var="lq" varStatus="vs">
							            <tr class="hand">
							              <td class="tc w50">${lq.projectDetail.serialNumber }</td>
							              <td class="tc">${lq.projectDetail.goodsName }</td>
							              <td class="tc">${lq.projectDetail.stand }</td>
							              <td class="tc">${lq.projectDetail.qualitStand }</td>
							              <td class="tc">${lq.projectDetail.item }</td>
							              <td class="tc">${lq.projectDetail.purchaseCount }</td>
							              <td class="tc">${lq.quotePrice }</td>
							              <td class="tc">${lq.total }</td>
							              <td class="tc"><fmt:formatDate value="${lq.delivery }" pattern="YYYY-MM-dd"/></td>
							              <td class="tc">${lq.remark }</td>
							            </tr>
							         </c:forEach>  
							         <tr>
							         	<td class="tr" colspan="2"><b>总金额(元):</b></td>
							         	<td class="tl" colspan="7"></td>
							         </tr>
							      </table>
		                    </div>
                  		</c:when>
                  		<c:otherwise>
                  		  <div class="tab-pane fade in height-450" id="tab-${vs.index+1 }">
									<table id="tb${vs.index }" class="table table-bordered table-condensed mt5">
							        <thead>
							        <tr>
							          <th class="info w50">序号</th>
							          <th class="info">物资名称</th>
							          <th class="info">规格型号</th>
							          <th class="info">质量技术标准</th>
							          <th class="info">计量单位</th>
							          <th class="info">采购数量</th>
							          <th class="info">报价金额</th>
							          <th class="info">报价小计</th>
							          <th class="info">交货时间</th>
							          <th class="info">备注</th>
							        </tr>
							        </thead>
							           <c:forEach items="${listQuote1}" var="lq" varStatus="vs">
							            <tr class="hand">
							              <td class="tc w50">${lq.projectDetail.serialNumber }</td>
							              <td class="tc">${lq.projectDetail.goodsName }</td>
							              <td class="tc">${lq.projectDetail.stand }</td>
							              <td class="tc">${lq.projectDetail.qualitStand }</td>
							              <td class="tc">${lq.projectDetail.item }</td>
							              <td class="tc">${lq.projectDetail.purchaseCount }</td>
							              <td class="tc">${lq.quotePrice }</td>
							              <td class="tc">${lq.total }</td>
							              <td class="tc"><fmt:formatDate value="${lq.delivery }" pattern="YYYY-MM-dd"/></td>
							              <td class="tc">${lq.remark }</td>
							            </tr>
							         </c:forEach>  
							         <tr>
							         	<td class="tr" colspan="2"><b>总金额(元):</b></td>
							         	<td class="tl" colspan="7"></td>
							         </tr>
							      </table>
	                 	  </div>
                  		</c:otherwise>
                  </c:choose>
		     </c:forEach>
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
