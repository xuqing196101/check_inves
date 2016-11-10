<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../common.jsp"%>

<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<link
    href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css"
    media="screen" rel="stylesheet">
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
             <c:if test="${fn:contains(suppliers.supplierType, '生产型')}">
            <li class=""><a aria-expanded="fale" class="f18"  href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '销售型')}">
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
                  <table class="table table-bordered table-condensed table-hover">
                      <thead>
                        <tr>
                          <th class="info w50">序号</th>
                          <th class="info">所属类别</th>
                          <th class="info">产品名称</th>
                          <th class="info">品牌</th>
                          <th class="info">规格型号</th>
                          <th class="info">尺寸</th>
                          <th class="info">生产产地</th>
                          <th class="info">保质期</th>
                          <th class="info">生产商</th>
                          <th class="info">参考价格</th>
                          <th class="info">产品图片</th>
                          <th class="info">商品二维码</th>
                        </tr>
                      </thead>
                      <tbody id="products_tbody_id">
                        <c:forEach items="${productsList}" var="products" varStatus="vs">
                          <tr>
                            <td class="tc" id="${products.id}_index">${vs.index+1 }</td>
                            <td id="${products.categoryId}" class="tc">${products.categoryName}</td>
                            <td class="tc" id="${products.id}_name" onclick="reason('${products.id}');">${products.name}</td>
                            <td class="tc" onclick="reason('${products.id}');">${products.brand}</td>
                            <td class="tc" onclick="reason('${products.id}');">${products.models}</td>
                            <td class="tc" onclick="reason('${products.id}');">${products.proSize}</td>
                            <td class="tc" onclick="reason('${products.id}');">${products.orgin}</td>
                            <td class="tc" onclick="reason('${products.id}');"><fmt:formatDate value="${products.expirationDate }" pattern="yyyy-MM-dd"/></td>
                            <td class="tc" onclick="reason('${products.id}');">${products.producer}</td>
                            <td class="tc" onclick="reason('${products.id}');">${products.referencePrice}</td>
                            <td class="tc">
                              <c:if test="${products.productPic != null}">
                                <a class="green" onclick="downloadFile('${products.productPic}')">下载附件</a>
                              </c:if>
                              <c:if test="${products.productPic == null}"><a class="red">无附件下载</a></c:if>
                            </td>
                            <td class="tc" >
                              <c:if test="${products.qrCode != null}">
                                <a class="green" onclick="downloadFile('${products.qrCode}')">下载附件</a>
                              </c:if>
                              <c:if test="${products.qrCode == null}"><a class="red">无附件下载</a></c:if>
                            </td>
                           
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
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
