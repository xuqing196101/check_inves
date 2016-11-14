<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>基本信息</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript">
	function shenhe(str){
		if(str=='tongguo'){
			window.location.href='${pageContext.request.contextPath}/supplier_edit/auditEnd.html?auditStatus=1&id='+'${supplierId}';
		}else{
		    var table =document.getElementById("tb");
   			var rows = table.rows.length;
			if(rows==1){
   				layer.alert("请填写审核不通过理由",{offset: ['222px', '390px'], shade:0.01});
			}else{
				window.location.href='${pageContext.request.contextPath}/supplier_edit/auditEnd.html?auditStatus=2&id='+'${supplierId}';
			}
		}
	}
</script>
</head>
  
<body>
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">供应商管理</a></li><li class="active"><a href="#">供应商变更</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <!-- 项目戳开始 -->
  <div class="container clear">
  <!--详情开始-->
  <form action="${pageContext.request.contextPath}/supplier_edit/save.html" method="post">
  <div class="container content height-350">
    <div class="row magazine-page">
      <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgdd">
			<li class=""><a aria-expanded="true" href="#tab-1" data-toggle="tab" id="essential"  onclick="window.location.href='${pageContext.request.contextPath}/supplier_edit/audit.html?id=${supplierId }'" >基本信息</a></li>
			<li class="active"><a aria-expanded="true" href="#tab-2" data-toggle="tab" id="essential" onclick="window.location.href='${pageContext.request.contextPath}/supplier_edit/reasonList.html'" >问题汇总</a></li>
          </ul>
            <div class="tab-content padding-top-20">
              <div class="tab-pane fade  height-450" id="tab-1">
            </div>
          	  <div class="tab-pane fade active in height-450" id="tab-2">
          	   <table id="tb" class="table table-bordered table-condensed">
                   <thead>
                     <tr>
                       <th class="info w50">序号</th>
                       <th class="info">审批字段</th>
                       <th class="info">审批内容</th>
                       <th class="info">不通过理由</th>
                     </tr>
                   </thead>
                     <c:forEach items="${srList }" var="list" varStatus="vs">
                       <tr>
                         <td class="tc">${vs.index + 1}</td>
                         <td class="tc">${list.name }</td>
                         <td class="tc">${list.content }</td>
                         <td class="tc">${list.auditReason}</td>
                       </tr>
                     </c:forEach>
                  </table>
                   <div class="col-md-12 tc">
			  							<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			  							<input class="btn btn-windows git" value="审核通过" type="button" onclick="shenhe('tongguo')">
			  							<input class="btn btn-windows git" value="审核退回" type="button" onclick="shenhe('tuihui')">
			 						</div>
          	  </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</form>
</div>
</body>
</html>
