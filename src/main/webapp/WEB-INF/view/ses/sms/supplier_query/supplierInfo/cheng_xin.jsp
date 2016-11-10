<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<link
    href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css"
    media="screen" rel="stylesheet">
<script type="text/javascript">
function reason(id){
  var supplierId=$("#supplierId").val();
  var auditField=$("#"+id).text()+"年财务信息"; //审批的字段名字
   layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId,
      });
      $("#"+id+"_hide").hide();
      layer.msg("审核不通过的理由是："+text);
    });
}

function reason1(year, ele){
  var supplierId=$("#supplierId").val();
  var value = $(ele).parents("li").find("span").text().replaceAll("＊","").replaceAll("：","");//审批的字段名字
  var auditField=year+"年"+value;
	  layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
	    $.ajax({
	        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
	        type:"post",
	        data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId,
	      });
	      $(ele).parent("div").find("div").eq(0).hide(); //隐藏勾
	      layer.msg("审核不通过的理由是："+text);
	    });
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
function downloadFile(fileName){
	  fileName=encodeURI(fileName);
      fileName=encodeURI(fileName);
	  window.location.href="${pageContext.request.contextPath}/supplierQuery/downLoadFile.html?fileName="+fileName;
}
</script>
<style type="text/css">
</style>
</head>
  
<body>
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
              <li class=""><a aria-expanded="fale" class="f18"  href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
              <li class=""><a aria-expanded="true" class="f18"  href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
              <li class=""><a aria-expanded="fale" class="f18"  href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
              <c:if test="${fn:contains(suppliers.supplierType, '生产型')}">
            <li class=""><a aria-expanded="fale" href="#tab-2"  class="f18" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '销售型')}">
            <li class=""><a aria-expanded="fale" href="#tab-3"  class="f18" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
            </c:if>
            <c:if test="${fn:contains(suppliers.supplierType, '工程')}">
            <li class=""><a aria-expanded="false" href="#tab-3" class="f18"  data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '服务')}">
            <li class=""><a aria-expanded="false" href="#tab-3"  class="f18" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
            </c:if>
              <li class=""><a aria-expanded="false" href="#tab-2" class="f18"  data-toggle="tab" onclick="tijiao('item');">品目信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" class="f18"  data-toggle="tab" onclick="tijiao('product');" >产品信息</a></li>
              <li class="active"><a aria-expanded="false" href="#tab-2" class="f18"  data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a></li>
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
							<th class="info">供应商名称</th>
							<th class="info">企业等级</th>
							<th class="info">分数</th>
							<th class="info">企业类型</th>
							<th class="info">企业性质</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listSuppliers.list}" var="supplier" varStatus="vs">
							<tr>
								<td class="tc">${vs.index + 1}</td>
								<td class="tc">${supplier.supplierName}</td>
								<td class="tc">${supplier.level}</td>
								<td class="tc">${supplier.score}</td>
								<td class="tc">
									<c:forEach items="${supplier.listSupplierTypeRelates}" var="relate">
										${relate.supplierTypeName}
									</c:forEach>
								</td>
								<td class="tc">${supplier.businessType}</td>
							</tr>
						</c:forEach>
					</tbody>
                  </table>
                </div>
              </div>
          </div>
        </div>
      </div>
</body>
</html>
