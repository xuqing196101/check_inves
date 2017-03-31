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
  function planDateil(id){
     location.href = "${pageContext.request.contextPath}/contractSupervision/planDateil.html?id="+id+"&contractId=${contractId}";
    }
  function contractDateil(id){
		location.href="${pageContext.request.contextPath }/contractSupervision/contractDateil.html?id="+id;
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
    <div class="content table_box  over_auto table_wrap">
        <table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				
				<th class="tnone"></th>
			    <th class="info w50">序号</th>
			    <th class="info">合同编号</th>
				<th class="info">合同名称</th>
				<th class="info">合同金额(万元)</th>
				<th class="info">项目名称</th>
				<th class="info">计划文件号</th>
				<th class="info">预算(万元)</th>
				<th class="info">年度</th>
				<th class="info">项级预算科目</th>
				<th class="info">甲方单位</th>
				<th class="info">供应商</th>
				<th class="info">状态</th>
				<th class="info">查看</th>
			</tr>
		</thead>
		<c:forEach items="${list}" var="draftCon" varStatus="vs">
			<tr>
				<td class="tnone">${draftCon.status}</td>
				<td class="pl20 pointer" >${vs.index+1}</td>
				<c:set value="${draftCon.code}" var="code"></c:set>
				<c:set value="${fn:length(code)}" var="length"></c:set>
				<c:if test="${length>7}">
					<td  class="pointer pl20" title="${code}" >${fn:substring(code,0,7)}...</td>
				</c:if>
				<c:if test="${length<=7}">
					<td  class="pointer pl20" title="${code}" >${code}</td>
				</c:if>
				<c:set value="${draftCon.name}" var="name"></c:set>
				<c:set value="${fn:length(name)}" var="length"></c:set>
				<c:if test="${length>9}" >
					<td  class="pointer pl20" title="${name}" ><a>${fn:substring(name,0,9)}...</a></td>
				</c:if>
				<c:if test="${length<=9}" >
					<td  class="pointer pl20" title="${name}" ><a>${name}</a></td>
				</c:if>
				<td class="tr pr20 pointer" >${draftCon.money}</td>
				<td class="tl pl20 pointer" >${draftCon.projectName}</td>
				<td class="tl pl20 pointer" >${draftCon.documentNumber}</td>
				<td class="tr pr20 pointer" >${draftCon.budget}</td>
				<td class="tc pointer" >${draftCon.year}</td>
				<td class="tl pl20 pointer" >${draftCon.budgetSubjectItem}</td>
				<td class="tl pl20 pointer" >${draftCon.purchaseDepName}</td>
				<td class="tl pl20 pointer" >${draftCon.supplierDepName}</td>
				<%--<c:if test="${draftCon.status==0}">
					<td class="tc pointer" >暂存</td>
				</c:if>
				--%><c:if test="${draftCon.status==1}">
					<td class="tc pointer" >草案</td>
				</c:if>
				<c:if test="${draftCon.status==2}">
					<td class="tc pointer" >正式</td>
				</c:if>
				<td class="tl pl20 pointer" ><a onclick="contractDateil('${draftCon.id}');">进入</a></td>
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
