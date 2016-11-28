<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../common.jsp"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>审核汇总</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<script type="text/javascript">
    //只读
    $(function() {
        $(":input").each(function() {
          $(this).attr("readonly", "readonly");
        });
        
        //审核按钮
        var num = ${num};
        if(num == 0){
            $("#tuihui").attr("disabled", true);
        }
        if(num != 0){
            $("#tonguo").attr("disabled", true);
        }
      });


	function tijiao(status){
	  $("#status").val(status);
	  form1.submit();
	}

/* //导航
function tijiao(str){
  var action;
  if(str=="essential"){
     action ="${pageContext.request.contextPath}/supplierAudit/essential.html";
  }
  if(str=="financial"){
    action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
  }
  if(str=="shareholder"){
    action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
  }
  if(str=="materialProduction"){
    action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
  }
  if(str=="materialSales"){
    action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
  }
  if(str=="engineering"){
    action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
  }
  if(str=="service"){
    action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
  }
  if(str=="items"){
  action = "${pageContext.request.contextPath}/supplierAudit/items.html";
  }
  if(str=="applicationFrom"){
    action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
  }
  if(str=="reasonsList"){
    action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
  }
  if(str=="product"){
    action = "${pageContext.request.contextPath}/supplierAudit/product.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}

 */
	//审核
	function shenhe(status){
	  var auditId = $("#auditId").val();
	  $("input[name='id']").val(auditId);
	
	  $("#status").val(status);
	  $("#form_shen").submit();
	}






<%-- function file(){
var supplierInspectListFile = $("#supplierInspectListFile").val();
alert(supplierInspectListFile);
  $.ajax({
        url:"<%=basePath%>supplierAudit/supplierFile.html",
        type:"post",
        data:"supplierInspectListFile="+supplierInspectListFile,
        success:function(){
          layer.msg("上传成功");
        },
         error: function(message){
            layer.msg("上传失败");
          }
      }
      );
       
} --%>
</script>
</head>
    <body>
        <!--面包屑导航开始-->
        <div class="margin-top-10 breadcrumbs ">
            <div class="container">
                <ul class="breadcrumb margin-left-0">
                    <li><a href="#"> 首页</a></li><li><a href="#">供应商管理</a></li><li><a href="#">供应商审核</a></li>
                </ul>
            </div>
        </div> 
        <div class="container container_box">
            <div class="content">
                <div class="col-md-12 tab-v2 job-content">
	            <%-- <ul class="nav nav-tabs bgdd">
	              <li class=""><a >详细信息</a></li>
	              <li class=""><a >财务信息</a></li>
	              <li class=""><a >股东信息</a></li>
	              <c:if test="${fn:contains(supplierTypeNames, '生产')}">
		            <li class=""><a >物资-生产专业信息</a></li>
		            </c:if>
		            <c:if test="${fn:contains(supplierTypeNames, '销售')}">
		            <li class=""><a >物资-销售专业信息</a></li>
		            </c:if>
		            <c:if test="${fn:contains(supplierTypeNames, '工程')}">
		            <li class=""><a >工程-专业信息</a></li>
		            </c:if>
		            <c:if test="${fn:contains(supplierTypeNames, '服务')}">
		            <li class=""><a >服务-专业信息</a></li>
		            </c:if>
	              <li class=""><a >品目信息</a></li>
	              <li class=""><a >产品信息</a></li>
	              <li class=""><a >申请表</a></li>
	              <li class="active"><a >审核汇总</a></li>
	            </ul> --%>
	            
                <ul class="flow_step">
                    <li >
                        <a aria-expanded="false" href="#tab-1" >详细信息</a>
                        <i></i>
                    </li>
                    <li >
                        <a aria-expanded="false" href="#tab-2" >财务信息</a>
                        <i></i>                            
                    </li>
                    <li >
                        <a aria-expanded="false" href="#tab-3" >股东信息</a>
                        <i></i>
                    </li>
                    <c:if test="${fn:contains(supplierTypeNames, '生产')}">
                        <li>
                            <a aria-expanded="false" href="#tab-4">生产信息</a>
                            <i></i>
                        </li>
                    </c:if>
                    <c:if test="${fn:contains(supplierTypeNames, '销售')}">
                        <li >
                            <a aria-expanded="false" href="#tab-4" >销售信息</a>
                            <i></i>
                        </li>
                    </c:if>
                    <c:if test="${fn:contains(supplierTypeNames, '工程')}">
                        <li>
                            <a aria-expanded="false" href="#tab-4" >工程信息</a>
                            <i></i>
                        </li>
                    </c:if>
                    <c:if test="${fn:contains(supplierTypeNames, '服务')}">
                        <li>
                            <a aria-expanded="false" href="#tab-4" >服务信息</a>
                            <i></i>
                        </li>
                    </c:if>
                    <li>
                        <a aria-expanded="false" href="#tab-4" >产品信息</a>
                        <i></i>
                    </li>
                    <li>
                        <a aria-expanded="false" href="#tab-4" >申请表</a>
                        <i></i>
                    </li>
                    <li class="active">
                        <a aria-expanded="true" href="#tab-4" data-toggle="tab">审核汇总</a>
                    </li>
                </ul>
            
	            <form id="form_id" action="" method="post"  enctype="multipart/form-data">
	                <input name="supplierId" value="${supplierId}" type="hidden">
	            </form>
	            <c:if test="${status==1 }">
	               <h2 class="count_flow"><i>1</i>问题汇总</h2>
	            </c:if>
	            <ul class="ul_list count_flow">
		            <table class="table table-bordered table-condensed table-hover">
		             <thead>
		               <tr>
		                 <th class="info w50">序号</th>
		                 <th class="info">审批类型</th>
		                 <th class="info">审批字段名字</th>
		                 <th class="info">审批内容</th>
		                 <th class="info">不通过理由</th>
		               </tr>
		             </thead>
		               <c:forEach items="${reasonsList }" var="list" varStatus="vs">
		                <input id="auditId" value="${list.id}" type="hidden">
		                 <tr>
		                   <td class="tc">${vs.index + 1}</td>
		                   <td class="tc">
		                     <c:if test="${list.auditType == 'basic_page'}">详细信息</c:if>
		                     <c:if test="${list.auditType == 'finance_page'}">财务信息</c:if>
		                     <c:if test="${list.auditType == 'stockholder_page'}">股东信息</c:if>
		                     <c:if test="${list.auditType == 'mat_pro_page'}">生产信息</c:if>
		                     <c:if test="${list.auditType == 'mat_sell_page'}">销售信息</c:if>
		                     <c:if test="${list.auditType == 'mat_eng_page'}">工程信息</c:if>
		                     <c:if test="${list.auditType == 'mat_serve_page'}">服务信息</c:if>
		                     <%-- <c:if test="${list.auditType == 'mat_serve_page' || list.auditType == 'item_sell_page' || list.auditType == 'item_eng_page' || list.auditType == 'item_serve_page'}">品目信息</c:if> --%>
		                     <c:if test="${list.auditType == 'products_page'}">产品信息</c:if>
		                     <c:if test="${list.auditType == 'upload_page'}">申请表信息</c:if>
		                   </td>
		                   <td class="tc">${list.auditFieldName }</td>
		                   <td class="tc">${list.auditContent}</td>
		                   <td class="tc">${list.suggest}</td>
		                 </tr>
		               </c:forEach>
		            </table>
	            </ul>
	            <%-- <h2 class="f16 jbxx1">
	              <i>01</i>问题汇总
	            </h2>
	          <div class=" margin-bottom-0">
	              <c:forEach items="${reasonsList }" var="list" >
	                <ul class="list-unstyled list-flow">
	                  <li class="col-md-6 p0 "><span class="" id="bankAccount2">${list.auditField}：</span>
	                    <div class="input-append">
	                      <input class="span3" id="bankAccount3"  type="text" value="${list.suggest}"/>
	                    </div>
	                  </li>
	                </ul>
	              </c:forEach>
	          </div> --%>
	          </div>
	          <c:if test="${status==1 }">
			     <h2 class="count_flow"><i>2</i>供应商考察表</h2>
				 <ul class="ul_list">
				    <%-- <form id="form_id" action="${pageContext.request.contextPath}/supplierAudit/supplierFile.html" method="post"  enctype="multipart/form-data"> --%>
	                   <li class="col-md-6 p0 mb25">
	                      <input name="supplierId" value="${supplierId}" type="hidden">
		                  <span class="col-md-5 padding-left-5" ><i class="red">*</i>上传考察表:</span>
		                  <div style="margin-bottom: 25px">
		                      <up:upload id="inspect" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierInspectList}" auto="true" /> 
                            <up:show showId="inspect_show" businessId="${suppliers.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierInspectList}" />
                          </div>
		                  <!-- <input class="span3" type="file" name="supplierInspectListFile"/>
		                  <button type="submit" class="btn padding-left-20 padding-right-20 btn_back">上传</button> -->
	                   </li>
	               <!-- </form> -->
                </ul>
	          </c:if>
	          <div class="col-md-12 add_regist tc">
	          <form id="form_shen" action="${pageContext.request.contextPath}/supplierAudit/updateStatus.html"  enctype="multipart/form-data">
	            <input name="supplierId" value="${supplierId}" type="hidden">
	            <input name="id" type="hidden">
	            <input type="hidden" name="status" id="status"/>
	            <div class="margin-bottom-0  categories">
	              <div class="col-md-12 add_regist tc">
	              <c:if test="${status==0 || status==5  || status==8}">
	                  <input class="btn btn-windows git"  type="button" onclick="shenhe(1)" value="初审通过 " id="tonguo">
	                  <input class="btn btn-windows reset"  type="button" onclick="shenhe(2)" value="初审不通过" id="butonguo">
	                  <input class="btn btn-windows reset"  type="button" onclick="shenhe(7)" value="退回修改" id="tuihui">
	              </c:if>
	              <c:if test="${status==1 || status==6}">
	                  <input class="btn btn-windows git"  type="button" onclick="shenhe(3)" value="复审通过 " id="tonguo">
	                  <input class="btn btn-windows edit"  type="button" onclick="shenhe(4)" value="复审不通过" id="butogguo">
	                  <input class="btn btn-windows reset"  type="button" onclick="shenhe(8)" value="退回修改" id="tuihui">
	              </c:if>
	              <%-- <input class="btn btn-windows reset" onclick="location='<%=basePath%>supplierAudit/supplierAll.html'" type="button"  value="完成"> --%>
	              </div>
	            </div>
            </form>
          </div>     
        </div>
      </div>
  </body>
</html>
