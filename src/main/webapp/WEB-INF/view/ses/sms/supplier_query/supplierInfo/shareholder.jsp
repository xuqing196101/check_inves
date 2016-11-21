<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../common.jsp"%>

<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<script type="text/javascript">
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
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商查看</a></li>
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
              <li class=""><a aria-expanded="fale" href="#tab-1" data-toggle="tab" class="f18" onclick="tijiao('essential');">基本信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('financial');">财务信息</a></li>
              <li class="active"><a aria-expanded="true" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('shareholder');">股东信息</a></li>
            <c:if test="${fn:contains(suppliers.supplierType, '生产型')}">
            <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '销售型')}">
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
            </c:if>
            <c:if test="${fn:contains(suppliers.supplierType, '工程')}">
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('engineering');">工程-专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '服务')}">
            <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('service');">服务-专业信息</a></li>
            </c:if>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">品目信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('product');" >产品信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('chengxin');">诚信记录</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('updateHistory');">历史修改记录</a></li>
            </ul>
              <div class="tab-content padding-top-20">
                  <form id="form_id" action="" method="post">
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  <table class="table table-bordered table-condensed table-hover">
                    <thead>
                      <tr>
                        <th class="info">出资人</th>
                        <th class="info">出资人性质</th>
                        <th class="info">统一社会信用代码或身份证</th>
                        <th class="info">出资金额或股份(万元/份)</th>
                        <th class="info">比例</th>
                      </tr>
                    </thead>
                    <c:forEach items="${shareholder}" var="s" >
                      <tr>
                        <td class="tc" id="${s.id }">${s.name}</td>
                        <td class="tc">${s.nature}</td>
                        <td class="tc">${s.identity}</td>
                        <td class="tc">${s.shares}</td>
                        <td class="tc">${s.proportion}</td>
                      </tr>
                    </c:forEach>
                  </table>
              </div>
          </div>
        </div>
      </div>
</body>
</html>
