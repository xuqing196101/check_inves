<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file="../../../../common.jsp"%>

<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<script type="text/javascript">
function reason(id){
  var supplierId=$("#id").val();
  var id1=id+"1";
  var id2=id+"2";
  var id3=id+"3";
  var auditField=$("#"+id2+"").text().replaceAll("＊","").replaceAll("：",""); //审批的字段名字
  var  auditContent= document.getElementById(""+id3+"").value; //审批的字段内容
  var auditType=$("#essential").text(); //审核类型
  layer.prompt({title: '请填写不通过理由', formType: 2}, function(text){
    $.ajax({
        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
        type:"post",
        data:"auditType="+auditType+"&auditField="+auditField+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId,
      });
  $("#"+id1+"").hide();
  layer.msg("审核不通过的理由是："+text);    
/*    $("input[name='auditType']").val(auditType);
   $("input[name='auditField']").val(auditField);
   $("input[name='auditContent']").val(auditContent);
   $("input[name='suggest']").val(text);
    
   $("#save_reaeon").submit(); */
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
function fanhui(){
      if('${category}'==1){
      window.location.href="${pageContext.request.contextPath}/supplierQuery/selectByCategory.html";
      }else{
	  window.location.href="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address="+encodeURI(encodeURI('${suppliers.address}'))+"&status=${status}";
      }
}
	//鼠标移动显示全部内容
	function out(content){
	if(content.length >= 10){
	layer.msg(content, {
	        offset:'200px',
		    skin: 'demo-class',
			shade:false,
			area: ['600px'],
			time : 0    //默认消息框不关闭
		});//去掉msg图标
	}else{
		layer.closeAll();//关闭消息框
	}
}
</script>
<style type="text/css">

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
   <!-- <div class="container">
   <div class="col-md-12">
	<button class="btn btn-windows back" onclick="fanhui()">返回</button>	
	</div>
    </div> -->
  <!--详情开始-->
   <div class="container content pt0">
        <div class="tab-v2">
          <ul class="nav nav-tabs bgwhite">
            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18" onclick="tijiao('essential');">基本信息</a></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('financial');">财务信息</a></li>
            <li class=""><a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18"  onclick="tijiao('shareholder');">股东信息</a></li>
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
            <div class="tab-pane fade active in">
                  <form id="form_id" action="" method="post">
                    <input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
                  </form> 
                  <h2 class="count_flow jbxx">企业基本信息</h2>
                    <table class="table table-bordered">
					<tbody>
					   <tr>
		                  <td class="bggrey">供应商名称：</td>
		                  <td onmouseover="out('${suppliers.supplierName }')">
                                <c:choose>
                                    <c:when test="${fn:length(suppliers.supplierName)>10}">
                                    ${fn:substring(suppliers.supplierName,0,10)}...
                                    </c:when>
                                    <c:otherwise>
                                    ${suppliers.supplierName}
                                    </c:otherwise>
                                </c:choose>
                          </td>
		                  <td class="bggrey ">公司网址：</td>
		                  <td>
                                <c:choose>
                                    <c:when test="${fn:length(suppliers.website)>10}">
                                    ${fn:substring(suppliers.website,0,10)}...
                                    </c:when>
                                    <c:otherwise>
                                    ${suppliers.website}
                                    </c:otherwise>
                                </c:choose>
                           </td>
		                  <td class="bggrey ">成立日期：</td>
		                  <td><fmt:formatDate value='${suppliers.foundDate}' pattern='yyyy-MM-dd'/></td>
		                </tr> 
					
						
						<tr>
							<td class="bggrey">营业执照登记类型：</td>
							<td onmouseover="out('${suppliers.businessType }')">${suppliers.businessType}</td>
							<td class="bggrey">地址：</td>
							<td>
								<c:choose>
									<c:when test="${fn:length(suppliers.address)>10}">
									${fn:substring(suppliers.address,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.address}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey">开户行名称：</td>
							<td onmouseover="out('${suppliers.bankName }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.bankName)>10}">
									${fn:substring(suppliers.bankName,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.bankName}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<tr>
							<td class="bggrey">开户行账户：</td>
							<td onmouseover="out('${suppliers.bankAccount }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.bankAccount)>10}">
									${fn:substring(suppliers.bankAccount,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.bankAccount}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey">邮编：</td>
							<td>${suppliers.postCode }</td>
							<td class="bggrey">近三个月完税凭证：</td>
							<td>
								<up:show showId="taxcert_show" delete="flase" groups="taxcert_show,billcert_show,curitycert_show,bearchcert_show,auditopinion_show,auditopinion_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierTaxCert}"/>
							</td>
						</tr> 
						
						<tr>
							<td class="bggrey">近三年银行账单：</td>
							<td>
								<up:show showId="billcert_show" delete="flase" groups="" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBillCert}"/>
							</td>
							<td class="bggrey">近三个月保险凭证：</td>
							<td>
								<up:show showId="curitycert_show" delete="flase" groups="" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierSecurityCert}"/>
							</td>
							<td class="bggrey">近三年违法记录：</td>
							<td>
								<up:show showId="bearchcert_show" delete="flase" groups="" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierBearchCert}"/>
							</td>
						</tr>
						</tbody>
                </table>
                </div>
                <div class="tab-pane fade active in">
                    <h2 class="count_flow jbxx">法定代表人信息</h2>
                        <table class="table table-bordered">
                            <tbody>			
                            <tr>
			                  <td class="bggrey">法定代表人姓名：</td>
			                  <td>${suppliers.legalName}</td>
			                  <td class="bggrey ">身份证号：</td>
			                  <td onmouseover="out('${suppliers.legalIdCard }')">
                                <c:choose>
                                    <c:when test="${fn:length(suppliers.legalIdCard)>10}">
                                    ${fn:substring(suppliers.legalIdCard,0,10)}...
                                    </c:when>
                                    <c:otherwise>
                                    ${suppliers.legalIdCard}
                                    </c:otherwise>
                                </c:choose>
                              </td>
			                  <td class="bggrey ">固定电话：</td>
			                  <td>${suppliers.legalTelephone}</td>
			                 </tr>			
						<tr>
							<td class="bggrey">手机：</td>
							<td colspan="5">${suppliers.legalMobile }</td>
						</tr>
						</tbody>
                        </table>
                        </div>
                        <div class="tab-pane fade active in">
                        <h2 class="count_flow jbxx">联系人信息</h2>
                        <table class="table table-bordered">
                        <tbody>
						<tr>
							<td class="bggrey">姓名：</td><td>${suppliers.contactName } </td>
							<td class="bggrey">传真：</td><td>${suppliers.contactFax }</td>
							<td class="bggrey">地址：</td>
							<td onmouseover="out('${suppliers.contactAddress }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.contactAddress)>10}">
									${fn:substring(suppliers.contactAddress,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.contactAddress}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<tr>
							<td class="bggrey">固定电话：</td><td>${suppliers.contactTelephone }</td>
							<td class="bggrey">手机：</td><td>${suppliers.contactMobile } </td>
							<td class="bggrey">邮箱：</td><td>${suppliers.contactEmail }</td>
						</tr>
						</tbody>
                        </table>
                        </div>
                        <div class="tab-pane fade active in">
                        <h2 class="count_flow jbxx">营业执照</h2>
                        <table class="table table-bordered">
                        <tbody>
						<tr>
							<td class="bggrey">统一社会信用代码：</td><td>${suppliers.creditCode } </td>
							<td class="bggrey">登记机关：</td>
							<td onmouseover="out('${suppliers.registAuthority }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.registAuthority)>10}">
									${fn:substring(suppliers.registAuthority,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.registAuthority}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey">注册资本：</td><td>${suppliers.registFund }</td>
						</tr>
						
						<tr>
							<td class="bggrey">营业期限：</td>
							<td><fmt:formatDate value="${suppliers.businessStartDate}" pattern="yyyy-MM-dd" />至 <fmt:formatDate value="${suppliers.businessEndDate}" pattern="yyyy-MM-dd" /> </td>
							<td class="bggrey">经营范围：</td>
							<td onmouseover="out('${suppliers.businessScope }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.businessScope)>10}">
									${fn:substring(suppliers.businessScope,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.businessScope}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey">生产或经营地址：</td>
							<td onmouseover="out('${suppliers.businessAddress }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.businessAddress)>10}">
									${fn:substring(suppliers.businessAddress,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.businessAddress}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<tr>
							<td class="bggrey">邮编：</td><td> ${suppliers.businessPostCode } </td>
							<td class="bggrey">境外分支机构：</td><td>${suppliers.overseasBranch }</td>
							<td class="bggrey">国家：</td>
							<td onmouseover="out('${suppliers.branchCountry }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.branchCountry)>10}">
											${fn:substring(suppliers.branchCountry,0,10)}...
									</c:when>
									<c:otherwise>
										${suppliers.branchCountry}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<tr>
							<td class="bggrey">详细地址：</td>
							<td onmouseover="out('${suppliers.branchAddress }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.branchAddress)>10}">
									${fn:substring(suppliers.branchAddress,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.branchAddress}
									</c:otherwise>
								</c:choose>
							 </td>
							<td class="bggrey">机构名称：</td>
							<td onmouseover="out('${suppliers.branchName }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.branchName)>10}">
									${fn:substring(suppliers.branchName,0,10)}...
									</c:when>
									<c:otherwise>
									${suppliers.branchName}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="bggrey">生产经营范围：</td>
							<td onmouseover="out('${suppliers.branchBusinessScope }')">
								<c:choose>
									<c:when test="${fn:length(suppliers.branchBusinessScope)>10}">
										${fn:substring(suppliers.branchBusinessScope,0,10)}...
									</c:when>
									<c:otherwise>
										${suppliers.branchBusinessScope}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						
						<tr>
						<td class="bggrey">营业执照：</td>
						<td colspan="5" class="hand" onclick="downloadFile('${suppliers.businessCert}')" >${suppliers.businessCert}</td>
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
