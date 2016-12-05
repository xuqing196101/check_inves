<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../common.jsp"%>

<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<script type="text/javascript">
//文件下載
  function downloadFile(fileName) {
    $("input[name='fileName']").val(fileName);
    $("#download_form_id").submit();
  }
  function tijiao(str){
  var action;
  if(str=="essential"){
     action ="${pageContext.request.contextPath}/supplierQuery/essential.html";
  }
  if(str=="financial"){
    action = "${pageContext.request.contextPath}/supplierQuery/financial.html";
  }
  if(str=="shareholder"){
    action = "${pageContext.request.contextPath}/supplierQuery/shareholder.html";
  }
  if(str=="materialProduction"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialProduction.html";
  }
  if(str=="materialSales"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialSales.html";
  }
  if(str=="engineering"){
    action = "${pageContext.request.contextPath}/supplierQuery/engineering.html";
  }
  if(str=="service"){
    action = "${pageContext.request.contextPath}/supplierQuery/serviceInformation.html";
  }
  if(str=="chengxin"){
    action = "${pageContext.request.contextPath}/supplierQuery/list.html";
  }
  if(str=="item"){
     action = "${pageContext.request.contextPath}/supplierQuery/item.html";
  }
   if(str=="product"){
     action = "${pageContext.request.contextPath}/supplierQuery/product.html";
  }
     if(str=="updateHistory"){
     action = "${pageContext.request.contextPath}/supplierQuery/showUpdateHistory.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}
</script>

</head>
  
<body>
  <!-- 项目戳开始 -->
  <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="#"> 首页</a>
                </li>
                <li><a href="#">支撑系统</a>
                </li>
                <li><a href="#">供应商查看</a>
                </li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
    <!-- 项目戳开始 -->
    <div class="container clear margin-top-30">
        <!-- <div class="container">
   <div class="col-md-12">
    <button class="btn btn-windows back" onclick="fanhui()">返回</button> 
    </div>
    </div> -->
        <!--详情开始-->
        <div class="container content pt0">
            <div class="tab-v2">
                <ul class="nav nav-tabs bgwhite">
             <li class=""><a aria-expanded="false" class="f18"  href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
             <li class=""><a aria-expanded="false" class="f18"  href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
             <li class=""><a aria-expanded="fale" class="f18"  href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
             <c:if test="${fn:contains(suppliers.supplierType, '生产')}">
            <li class=""><a aria-expanded="fale" class="f18"  href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '销售')}">
            <li class=""><a aria-expanded="fale" href="#tab-3" class="f18"  data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
            </c:if>
            <c:if test="${fn:contains(suppliers.supplierType, '工程')}">
            <li class=""><a aria-expanded="false" href="#tab-3" class="f18"  data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '服务')}">
            <li class=""><a aria-expanded="false" href="#tab-3" class="f18"  data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
            </c:if>
             <li class=""><a aria-expanded="false" href="#tab-2" class="f18"  data-toggle="tab" onclick="tijiao('item');">品目信息</a></li>
             <li class="active"><a aria-expanded="true" href="#tab-3"  class="f18" data-toggle="tab" onclick="tijiao('product');" >产品信息</a></li>
             <li class=""><a aria-expanded="false" href="#tab-2"  class="f18" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a></li>
             <li class=""><a aria-expanded="false" href="#tab-2" class="f18"  data-toggle="tab" onclick="tijiao('updateHistory');">历史修改记录</a></li>
            </ul>
              <div class="tab-content padding-top-20">
                    <div class="tab-pane fade active in">
                        <form id="form_id" action="" method="post">
                            <input id="supplierId" name="supplierId" value="${supplierId}"
                                type="hidden">
                        </form>
              <c:forEach items="${suppliers.listSupplierItems}" var="category" varStatus="vs">
	              <h2 class="count_flow"><i>${vs.index + 1}</i>${category.categoryName}产品信息表</h2>
	              <ul class="ul_list">
	                <table class="table table-bordered table-condensed table-hover">
	                  <thead>
	                    <tr>
	                      <!--这是所有的品目参数  -->
	                      <th class="info w50">序号</th>
		                    <c:forEach items="${suppliers.categoryParam}" var="item" varStatus="vs"> 
		                      <c:if test="${category.categoryId==item.cateId }">
		                        <th class="info">${item.paramName}</th>
		                      </c:if>
		                    </c:forEach>
	                    </tr>
	                  </thead>
	                  <tr >
		                  <!--这是所有的品目参数值  -->
		                  <td class="tc w50">${vs.index + 1}</td> 
		                  <c:forEach items="${suppliers.categoryParam}" var="cate" varStatus="vs">
		                    <c:forEach items="${suppliers.paramVleu}" var="obj"  > 
		                      <c:if test="${category.categoryId==cate.cateId and obj.categoryParamId==cate.id }"> 
		                        <td  align="center" onclick="reason('${obj.id}');">${obj.paramValue}</td>
		                      </c:if>
		                      <input type="hidden" id="${obj.id}_name" value="${category.categoryName}">
		                    </c:forEach> 
		                  </c:forEach>
	                  </tr>  
	                </table>
                </ul>
              </c:forEach>
                </div>
              </div>
          </div>
        </div>
      </div>
  <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
   <input type="hidden" name="fileName" />
  </form>
</body>
</html>
