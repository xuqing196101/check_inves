<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>物资-生产型专业信息</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script type="text/javascript">
function reason(id){
  var supplierId=$("#supplierId").val();
  var auditField=$("#"+id).text()+"生产资质证书信息"; //审批的字段名字
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


function reason1(id){
  var supplierId=$("#supplierId").val();
  var id2=id+"2";
  var id1=id+"1";
  var auditField=$("#"+id2+"").text().replaceAll("＊","").replaceAll("：",""); //审批的字段名字
  layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId,
      });
     layer.msg("审核不通过的理由是："+text);
     $("#"+id1+"").hide();
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
  if(str=="item"){
     action = "${pageContext.request.contextPath}/supplierQuery/item.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}
</script>
<style type="text/css">
.jbxx1{
  background:url(../images/down_icon.png) no-repeat 5px !important;
  padding-left:40px !important;
}
.jbxx1 i{
    width: 24px;
    height: 30px;
    background: url(../../../../../zhbj/public/ZHQ/images/round.png) no-repeat center;
    color: #ffffff;
    font-size: 12px;
    text-align: center;
    display: block;
    float: left;
    line-height: 30px;
    font-style: normal;
    margin-right: 10px;
}
</style>
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
    <!--详情开始-->
    <div class="container content height-350">
      <div class="row magazine-page">
        <div class="col-md-12 tab-v2 job-content">
          <div class="padding-top-10">
            <ul class="nav nav-tabs bgdd">
              <li class=""><a aria-expanded="fale" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
              <li class="active"><a aria-expanded="true" href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
              <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('item');">品目信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" >产品信息</a></li>
              <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a></li>
            </ul>
              <div class="tab-content padding-top-20">
                <div class="tab-pane fade active in height-450" id="tab-1">
                  <form id="form_id" action="" method="post"  enctype="multipart/form-data">
                    <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                  </form>
                  <h5>
                  <i>一、</i>供应商资质证书
                  </h5>
                  <table class="table table-bordered table-condensed">
							      <thead>
							        <tr>
							          <th class="info">资质证书名称</th>
							          <th class="info">资质等级</th>
							          <th class="info">发证机关</th>
							          <th class="info">有效期(起止时间)</th>
							          <th class="info">是否年检</th>
							          <th class="info">附件上传</th>
							        </tr>
							        </thead>
							        <c:forEach items="${materialProduction}" var="m" >
							          <tr>
							            <td class="tc" id="${m.id}">${m.name }</td>
							            <td class="tc">${m.levelCert}</td>
							            <td class="tc">${m.licenceAuthorith }</td>
							            <td class="tc">
								            <fmt:formatDate value="${m.expStartDate }" pattern='yyyy-MM-dd'/>  至  
								            <fmt:formatDate value="${m.expEndDate }" pattern='yyyy-MM-dd'/>
								          </td>
							            <td class="tc">
							             <c:if test="${m.mot==0 }">否</c:if>
							             <c:if test="${m.mot==1 }">是</c:if>
							            </td>
							            <td class="tc">${m.attach }</td>
							          </tr>
							        </c:forEach>
							    </table>
							    
							    <div class=" margin-bottom-0">
					<table class="table table-bordered">
					<tbody>
					<tr><td colspan="6" class="bggrey tl">二、组织结构和人员：</td></tr>
                        <tr>
							<td class="bggrey tr" style="width:17%">组织机构：</td>
							<td style="width:16%" onmouseover="out('${supplierMatPros.orgName}')">${supplierMatPros.orgName}</td>
							<td class="bggrey tr" style="width:17%">人员总数：</td>
							<td style="width:17%">${supplierMatPros.totalMange }</td>
							<td style="width:17%" class="bggrey tr">管理人员：</td>
							<td style="width:17%">${supplierMatPros.totalMange }</td>
						</tr>
						
						 <tr>
							<td class="bggrey tr">技术人员：</td>
							<td onmouseover="out('${supplierMatPros.orgName}')">${supplierMatPros.totalTech}</td>
							<td class="bggrey tr">工人(职员)：</td>
							<td colspan="3">${supplierMatPros.totalWorker }</td>
						</tr>
						    <tr><td colspan="6" class="bggrey tl">三、产品研发能力：</td></tr>
                       <tr>
							<td class="bggrey tr">技术人员比例：</td>
							<td onmouseover="out('${supplierMatPros.scaleTech}')">${supplierMatPros.scaleTech}</td>
							<td class="bggrey tr">高级技术人员比例：</td>
							<td>${supplierMatPros.scaleHeightTech }</td>
							<td class="bggrey tr">研发部门名称：</td>
							<td>${supplierMatPros.researchName }</td>
						</tr>
						 <tr>
							<td class="bggrey tr">研发部门人数：</td>
							<td onmouseover="out('${supplierMatPros.totalResearch}')">${supplierMatPros.totalResearch}</td>
							<td class="bggrey tr">研发部门负责人：</td>
							<td>${supplierMatPros.researchLead }</td>
							<td class="bggrey tr">承担国家军队科研项目：</td>
							<td>${supplierMatPros.countryPro }</td>
						</tr>
						 <tr>
							<td class="bggrey tr">获得国家军队科技项目：</td>
							<td colspan="5" onmouseover="out('${supplierMatPros.totalResearch}')">${supplierMatPros.countryReward}</td>
						</tr>
						        <tr><td colspan="6" class="bggrey tl">四、供应商生产能力：</td></tr>
                       <tr>
							<td class="bggrey tr">生产线名称数量：</td>
							<td onmouseover="out('${supplierMatPros.totalBeltline}')">${supplierMatPros.totalBeltline}</td>
							<td class="bggrey tr">生产设备名称数量：</td>
							<td colspan="3">${supplierMatPros.totalDevice }</td>
						</tr>
						  <tr><td colspan="6" class="bggrey tl">五、物资生产型供应商质量检测登记</td></tr>
                       <tr>
							<td class="bggrey tr">质量检测部门：</td>
							<td onmouseover="out('${supplierMatPros.qcName}')">${supplierMatPros.qcName}</td>
							<td class="bggrey tr">质量检测人数：</td>
							<td>${supplierMatPros.totalQc }</td>
							<td class="bggrey tr">质检部门负责人：</td>
							<td>${supplierMatPros.qcLead }</td>
						</tr>
						 <tr>
							<td class="bggrey tr">质量检测设备名称：</td>
							<td colspan="5" onmouseover="out('${supplierMatPros.qcDevice}')">${supplierMatPros.qcDevice}</td>
						</tr>
					 </tbody>
					</table>
                  </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
