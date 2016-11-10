<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../common.jsp"%>

<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<link href="${pageContext.request.contextPath}/public/ZHH/css/shop.style.css" media="screen" rel="stylesheet">
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
              <li class="active"><a aria-expanded="true" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('financial');">财务信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('shareholder');">股东信息</a></li>
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
                <h2 class="count_flow jbxx">基本信息</h2>
                  <form id="form_id" action="" method="post">
                      <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                    <table class="table table-bordered table-condensed table-hover">
                   <thead>
                     <tr>
                       <th class="info">年份</th>
                       <th class="info">会计事务所名称</th>
                       <th class="info">事务所联系电话</th>
                       <th class="info">审计人姓名</th>
                       <th class="info">指标</th>
                       <th class="info">资产总额</th>
                       <th class="info">负债总额</th>
                       <th class="info">净资产总额</th>
                       <th class="info">营业收入</th>
                     </tr>
                   </thead>
                     <c:forEach items="${financial}" var="f" >
                       <tr>
                         <td class="tc" id="${f.id }">${f.year }</td>
                         <td class="tc">${f.name }</td>
                         <td class="tc">${f.telephone }</td>
                         <td class="tc">${f.auditors }</td>
                         <td class="tc">${f.quota }</td>
                         <td class="tc">${f.totalAssets }</td>
                         <td class="tc">${f.totalLiabilities }</td>
                         <td class="tc">${f.totalNetAssets}</td>
                         <td class="tc">${f.taking}</td>
                       </tr>
                     </c:forEach>
                  </table>
                  <h2 class="count_flow jbxx">附件下载</h2>
                   <c:forEach items="${financial}" var="f" varStatus="vs">
	                  <h2 class="count_flow">
	                  <i>0${vs.index+1 }</i>${f.year }年
	                  </h2>
	                  <ul class="ul_list">
	                   <li class="col-md-3 margin-0 padding-0 ">
					        <div class="fl">文件下载：<span class="ml10">财务审计报告意见表</span><a href="#" onclick="downloadFile('${f.auditOpinion}')" class="download"></a></div>
					   </li>
					   <li class="col-md-3 margin-0 padding-0 ">
                            <div class="fl">文件下载：<span class="ml10">资产负债表</span><a href="#" onclick="downloadFile('${f.auditOpinion}')" class="download"></a></div>
                       </li>
                       <li class="col-md-3 margin-0 padding-0 ">
                            <div class="fl">文件下载：<span class="ml10">利润表</span><a href="#" onclick="downloadFile('${f.auditOpinion}')" class="download"></a></div>
                       </li>
                       <li class="col-md-3 margin-0 padding-0 ">
                            <div class="fl">文件下载：<span class="ml10">现金流量表</span><a href="#" onclick="downloadFile('${f.auditOpinion}')" class="download"></a></div>
                       </li>
                       <li class="col-md-3 margin-0 padding-0 ">
                            <br/>
                            <div class="fl">文件下载：<span class="ml10">所有者权益变动表</span><a href="#" onclick="downloadFile('${f.auditOpinion}')" class="download"></a></div>
                       </li>
					   </ul>
                  </c:forEach> 
                  
                </div>
              </div>
          </div>
          </div>
</body>
</html>
