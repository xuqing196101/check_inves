<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>供应列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/common.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/style.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/line-icons.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/app.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/application.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/header-v4.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/footer-v2.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/img-hover.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/page_job.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/public/ZHQ/css/shop.style.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/ZHQ/js/bootstrap.min.js"></script>
<script type="text/javascript">
		/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		   if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
	
	//更新
   function shenHe(){
    	<%-- var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="<%=basePath%>expert/editBlacklist.html?id="+id;
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的信息",{offset: ['222px', '390px'], shade:0.01});
		} --%>
		
		window.location.href="<%=basePath%>supplierAudit/essential.html";
    }
</script>
</head>
<body>

<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">供应商注册管理</a></li><li><a href="#">供应商审核</a></li><li class="active"><a href="#">供应商列表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
<!-- 搜索 -->
	<div class="container">
		<form action="${pageContext.request.contextPath}/expert/queryBlacklist.html"  method="post" id="form1" enctype="multipart/form-data" class="registerform"> 
		<input id="page_id" type="hidden" name="page" value="1">
		 <ul class="list-unstyled list-flow p0_20">
    		<li class="col-md-6 p0">
	  		 <span>供应商名称：</span>
        		<input class="span2" maxlength="10" name="relName" type="text">
	 		</li>
    		<li class="col-md-6 p0">
	  		 <span>企业类型：</span>
				<select name="punishDate" class="span2">
					<option value="">请选择</option>
	  				<option value="生产型">生产型</option>
	  				<option value="销售型">销售型</option>
				</select>
      			&nbsp;&nbsp;&nbsp;<input class="btn-u" name="commit" value="搜索" type="submit">
			</li>
		</ul>
		</form>
	</div>
   <!-- 表格开始-->
<!--    <div class="container">
    	 <div class="col-md-8">
     		<button class="btn btn-windows edit" onclick="shenHe();">初审</button>
		</div>
   </div> -->
   <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
        <table class="table table-bordered table-condensed">
		<thead>
		<tr>
		  <th class="info w50">序号</th>
		  <th class="info">供应商名称</th>
		  <th class="info">企业类型</th>
		  <th class="info">企业性质</th>
		  <th class="info">企业状态</th>
		  <th class="info">审核状态</th>
		</tr>
		</thead>
		<c:forEach items="${supplierList}" var="list" >
		<tr>
		  <td class="tc w50"></td>
		  <td class="tc w50">${list.supplierName }</td>
		  <td class="tc">${list.supplierTypeId }</td>
		  <td class="tc">${list.businessType }</td>
		  <td class="tc"></td>
		  <td>
			<input type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/supplierAudit/essential.html?supplierId=${list.id }'" value="初审" />
			<%-- <c:if test="${list.status==1 }"><input type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/importSupplier/audit.html?id=${list.id }'" value="复审" /></c:if>
			<c:if test="${list.status==2 }"><input type="button" class="btn padding-left-20 padding-right-20 btn_back" onclick="location='${pageContext.request.contextPath}/importSupplier/audit.html?id=${list.id }'" value="已审核" /></c:if> --%>
		  </td>
		</tr>
		</c:forEach>
        </table>
     </div>
   
   </div>
<!--底部代码开始-->

</body>
</html>
