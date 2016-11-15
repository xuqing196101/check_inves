<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../common.jsp"%>

<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<script type="text/javascript">
function reason(id){
  var supplierId=$("#supplierId").val();
  var auditField="证书编号是"+$("#"+id).text(); //审批的字段名字
   layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"&auditField="+auditField+"&suggest="+text+"&supplierId="+supplierId,
      });
        $("#"+id+"_hide").hide();
        $("#"+id+"_hide1").hide();
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
               <li class=""><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18" onclick="tijiao('essential');">基本信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('financial');">财务信息</a></li>
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18"  onclick="tijiao('shareholder');">股东信息</a></li>
            <c:if test="${fn:contains(suppliers.supplierType, '生产型')}">
            <li class=""><a aria-expanded="fale" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '销售型')}">
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
            </c:if>
            <c:if test="${fn:contains(suppliers.supplierType, '工程')}">
            <li class="active"><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18" onclick="tijiao('engineering');">工程-专业信息</a></li>
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
                    <div class="tab-pane fade active in">
                        <form id="form_id" action="" method="post">
                            <input id="supplierId" name="supplierId" value="${supplierId}"
                                type="hidden">
                        </form>
                        <h2 class="count_flow jbxx">供应商资质证书信息</h2>
	                  <table class="table table-bordered table-condensed table-hover">
	                    <thead>
	                      <tr>
	                        <th class="info">资质资格类型</th>
	                        <th class="info">证书编号</th>
	                        <th class="info">资质资格最高等级</th>
	                        <th class="info">技术负责人姓名</th>
	                        <th class="info">技术负责人职称</th>
	                        <th class="info">技术负责人职务</th>
	                        <th class="info">单位负责人姓名</th>
	                        <th class="info">单位负责人职称</th>
	                        <th class="info">单位负责人职务</th>
	                        <th class="info">发证机关</th>
	                        <th class="info">发证日期</th>
	                        <th class="info">证书有效期截止日期</th>
	                        <th class="info">证书状态</th>
	                       <!--  <th class="info">附件上传</th> -->
	                      </tr>
	                    </thead>
	                    <c:forEach items="${supplierCertEng}" var="s" >
	                      <tr>
	                        <td class="tc">${s.certType }</td>
	                        <td class="tc" id="${s.id }">${s.certCode }</td>
	                        <td class="tc">${s.certMaxLevel }</td>
	                        <td class="tc">${s.techName }</td>
	                        <td class="tc">${s.techPt }</td>
	                        <td class="tc">${s.techJop }</td>
	                        <td class="tc">${s.depName }</td>
	                        <td class="tc">${s.depPt }</td>
	                        <td class="tc">${s.depJop }</td>
	                        <td class="tc">${s.licenceAuthorith }</td>
	                        <td class="tc">
	                          <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>
	                        </td>
	                        <td class="tc">${s.certStatus }
	                           <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>  至  
	                           <fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd'/>
	                        </td>
	                        <td class="tc">
	                          <c:if test="${s.certStatus==0 }">无效</c:if>
	                          <c:if test="${s.certStatus==1 }">有效</c:if>
	                        </td>
	                        <%-- <td class="tc">${s.attachCert }</td> --%>
	                       </tr>
	                     </c:forEach>
	                   </table>
	                 </div>
	                 
	                 <div class="tab-pane fade active in">
                        <h2 class="count_flow jbxx">供应商资质证书信息</h2>
                   <table class="table table-bordered table-condensed table-hover">
                    <thead>
                      <tr>
                        <th class="info">资质资格类型</th>
                        <th class="info">证书编号</th>
                        <th class="info">资质资格序列</th>
                        <th class="info">专业类别</th>
                        <th class="info">资质资格等级</th>
                        <th class="info">是否主项资质</th>
                        <th class="info">批准资质资格内容</th>
                        <th class="info">首次批准资质资格文号</th>
                        <th class="info">首次批准资质资格日期</th>
                        <th class="info">资质资格取得方式</th>
                        <th class="info">资质资格状态</th>
                        <th class="info">资质资格状态变更时间</th>
                        <th class="info">资质资格状态变更原因</th>
                       <!--  <th class="info">附件上传</th> -->
                      </tr>
                    </thead>
                    <c:forEach items="${supplierAptitutes}" var="s" >
                      <tr>
                        <td class="tc">${s.certType }</td>
                        <td class="tc" id="${s.id }">${s.certCode }</td>
                        <td class="tc">${s.aptituteSequence }</td>
                        <td class="tc">${s.professType }</td>
                        <td class="tc">${s.aptituteLevel }</td>
                        <td class="tc">
                          <c:if test="${s.isMajorFund==0 }">否</c:if>
                          <c:if test="${s.isMajorFund==1 }">是</c:if>
                        <td class="tc">${s.aptituteContent }</td>
                        <td class="tc">${s.aptituteCode }</td>
                        <td class="tc">
                          <fmt:formatDate value="${s.aptituteDate }" pattern='yyyy-MM-dd'/>
                        </td>
                        <td class="tc">${s.aptituteWay }</td>
                        <td class="tc">
                          <c:if test="${s.aptituteStatus==0 }">无效</c:if>
                          <c:if test="${s.aptituteStatus==1 }">有效</c:if>
                        </td>
                        <td class="tc">
                          <fmt:formatDate value="${s.aptituteChangeAt }" pattern='yyyy-MM-dd'/>
                        </td>
                        <td class="tc">${s.aptituteChangeReason }</td>
                        <%-- <td class="tc">${s.attachCert }</td> --%>
                        <td class="tc">
                        </td>
                      </tr>
                    </c:forEach>
                  </table>
                </div>
                
               <div class="tab-pane fade active in">
                        <h2 class="count_flow jbxx">供应商组织机构</h2>
                        <table class="table table-bordered table-condensed table-hover">
						<tbody>
	                        <tr>
								<td class="bggrey" >组织机构：</td>
								<td onmouseover="out('${supplierMatEngs.orgName}')">${supplierMatEngs.orgName}</td>
								<td class="bggrey" >技术负责人：</td>
								<td >${supplierMatEngs.totalTech }</td>
								<td  class="bggrey">中级及以上职称人员：</td>
								<td >${supplierMatEngs.totalGlNormal }</td>
							</tr>
							 <tr>
								<td class="bggrey">管理人员：</td>
								<td onmouseover="out('${supplierMatEngs.totalMange}')">${supplierMatEngs.totalMange}</td>
								<td class="bggrey">技术工人：</td>
								<td colspan="3">${supplierMatEngs.totalTechWorker }</td>
							</tr>
							</tbody>
							</table>
                    </div>
              </div>
            </div>
          </div>
        </div>
</body>
</html>
