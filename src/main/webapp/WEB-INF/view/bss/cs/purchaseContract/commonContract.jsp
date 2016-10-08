<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
    <title>合同基本信息修改页</title>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
    <script type="text/javascript">
    	function next(){
    		var ids = "${ids}";
    		window.location.href="<%=basePath%>purchaseContract/createDetailContract.html?ids="+ids;
    	}
    </script>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">采购合同管理</a></li><li class="active"><a href="#">合同基本信息</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 新增模板开始-->
   <div class="container">
   		<%--<form action="<%=basePath %>pqinfo/save.html" method="post">
   		--%><div class="headline-v2">
   			<h2>基本信息</h2>
   		</div>
   		<ul class="list-unstyled list-flow p0_20">
   			<input type="hidden" class="contract_id" name="contract_id">
		     <li class="col-md-6  p0 ">
			   <span class="">合同名称：</span>
			   <div class="input-append">
		        <input class="span2 contract_code" value="${project.name}" id="contract_code" type="text" readonly="readonly">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">合同编号：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" value="${project.projectNumber}" type="text" readonly="readonly">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">合同金额：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" value="${project.amount}" type="text" readonly="readonly">
       			</div>
			 </li>
			 <div class="clear"></div>
		 </ul>
   		<div class="headline-v2">
   			<h2>甲方信息</h2>
   		</div>
		 <ul class="list-unstyled list-flow p0_20">
    		 <li class="col-md-6 p0">
			   <span class="">甲方单位：</span>
		        <div class="input-append ">
		        	<input class="span2 supplier_id" value="${project.purchaseDep.depName}" type="text" readonly="readonly">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">甲方法人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" value="${project.purchaseDep.legal}" type="text" readonly="readonly">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">甲方委托代理人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" value="${project.purchaseDep.agent}" type="text" readonly="readonly">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">甲方联系人：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.contact}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方联系电话：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.contactTelephone}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方通讯地址：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.contactAddress}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方邮政编码：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.unitPostCode}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方付款单位：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.payDep}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方开户银行：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.bank}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方银行账号：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.bankAccount}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <div class="clear"></div>
		 </ul>
   		<div class="headline-v2">
   			<h2>乙方信息</h2>
   		</div>
		 <ul class="list-unstyled list-flow p0_20">
			 <li class="col-md-6 p0">
			   <span class="">乙方单位：</span>
		        <div class="input-append ">
		        	<input class="span2 supplier_id" type="text" value="${project.dealSupplier.supplierName}" readonly="readonly">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">乙方法人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" type="text" value="${project.dealSupplier.legalName}" readonly="readonly">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">乙方委托代理人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" type="text" readonly="readonly">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">乙方联系人：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.contactName }" type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方联系电话：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.contactTelephone}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方通讯地址：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.address}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方邮政编码：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.postCode}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方开户名称：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name"  type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方开户银行：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.bankName}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方银行账号：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.bankAccount}" type="text" readonly="readonly">
		        </div>
			 </li>
			 <div class="clear"></div>
		</ul>
  		<div  class="col-md-12 tc mt20">
   			<button class="btn btn-windows save" onclick="next()">下一步</button>
   			<button class="btn btn-windows git" onclick="history.go(-1)" type="button">取消</button>
  		</div>
  		
  	<%--</form>
 --%></div>
</body>
</html>
