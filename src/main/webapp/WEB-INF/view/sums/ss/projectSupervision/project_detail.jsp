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
  function projectView(id){
      window.location.href = "${pageContext.request.contextPath}/contractSupervision/projectView.html?id="+id;
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
        <h2>项目信息
	    </h2>
   </div> 
<!-- 项目戳开始 -->
    
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
                <td width="25%" class="info">采购管理部门：</td>
                <td width="25%">${org}</td>
                <td width="25%" class="info">项目状态：</td>
                <td width="25%">${project.status}</td>
              </tr>
              <tr>
                <td width="25%" class="info">创建人：</td>
                <td width="25%">${project.appointMan}</td>
                <td width="25%" class="info">创建日期：</td>
                <td width="25%" colspan="3">
                  <fmt:formatDate value='${project.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                </td>
              </tr>
            </tbody>
          </table>
        </ul>
      </div>
      <div class="padding-top-10 clear">
        <h2 class="count_flow"><i>2</i>项目明细</h2>
        <c:forEach items="${project.packagesList}" var="list" varStatus="vs">
          
          <h2 class="count_flow" >
            <span class="f14 blue">${list.name}</span>
          </h2>
          
          <table id="detailtable" class="table table-striped table-bordered table-hover">
			 <thead>
				<tr>
				 <td class="info w50">序号</td>
				 <td class="info tc">编号</td>
				 <td class="info tc">物资名称</td>
				 <td class="info tc">规格型号</td>
				 <td class="info tc">计量单位</td>
				 <td class="info tc">采购数量</td>
				 <td class="info tc">单价</td>
				 <td class="info tc">预算金额</td>
				 <td class="info tc">交货日期</td>
				 <td class="info tc">采购方式</td>
				 <td class="info tc">供应商名称</td>
				 <td class="info tc">进度</td>
				</tr>
			 </thead>
			 <c:forEach items="${list.projectDetails}" var="detail" varStatus="vt">
			    <tr>
			       <td class="tr pr20 pointer">${vt.index+1}</td>
			       <td class="tr pr20 pointer">${detail.serialNumber}</td>
			       <td>${detail.goodsName}</td>
			       <td>${detail.stand}</td>
			       <td>${detail.item}</td>
			       <td class="tr">${detail.purchaseCount}</td>
			       <td class="tr">${detail.price}</td>
			       <td class="tr">${detail.budget}</td>
			       <td>${detail.deliverDate}</td>
			       <td>${detail.purchaseType}</td>
			       <td>${detail.supplier}</td>
			       <td><div name="p" onclick="projectView('${detail.id}');"  class="easyui-progressbar" data-options="value:60" style="width:80px;"></div></td>
			    </tr>
			 
			 </c:forEach>
		   </table>
          
        </c:forEach>
      </div>
    </div>
  <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
   </div>
   
</body>
</html>
