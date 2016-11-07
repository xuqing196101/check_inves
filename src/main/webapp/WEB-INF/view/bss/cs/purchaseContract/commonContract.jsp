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
    		var id = "${id}";
    		var supid = "${supid}";
    		window.location.href="<%=basePath%>purchaseContract/createDetailContract.html?id="+id+"&supid="+supid;
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
   <div class="container bggrey border1 mt20">
   		<%--<form action="<%=basePath %>pqinfo/save.html" method="post">
   		--%><%--
   		<div class="headline-v2">
   			<h2>基本信息</h2>
   		</div>
   		--%>
   		<h2 class="f16 count_flow mt40"><i>01</i>基本信息</h2>
   		<ul class="list-unstyled list-flow ul_list">
   			<input type="hidden" class="contract_id" name="contract_id">
		     <li class="col-md-6  p0 ">
			   <span class="">项目名称：</span>
			   <div class="mt5">
		        ${project.name}
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">项目编号：</span>
		        <div class="mt5">
		        	${project.projectNumber}
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">项目金额：</span>
		        <div class="mt5">
		        ${project.amount}
       			</div>
			 </li>
			 <div class="clear"></div>
		 </ul>
   		<%--<div class="headline-v2">
   			<h2>甲方信息</h2>
   		</div>
   		--%><h2 class="f16 count_flow mt40"><i>02</i>甲方信息</h2>
		 <ul class="list-unstyled list-flow ul_list">
    		 <li class="col-md-6 p0">
			   <span class="">甲方单位：</span>
		        <div class="mt5">
		        ${project.purchaseDep.depName}
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">甲方法人：</span>
			   <div class="mt5">
		        ${project.purchaseDep.legal}
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">甲方委托代理人：</span>
			   <div class="mt5">
		        ${project.purchaseDep.agent}
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">甲方联系人：</span>
		        <div class="mt5">
		         ${project.purchaseDep.contact}
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方联系电话：</span>
		        <div class="mt5">
		         ${project.purchaseDep.contactTelephone}
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方通讯地址：</span>
		        <div class="mt5">
		         ${project.purchaseDep.contactAddress}
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方邮政编码：</span>
		        <div class="mt5">
		         ${project.purchaseDep.unitPostCode}
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方付款单位：</span>
		        <div class="mt5">
		         ${project.purchaseDep.payDep}
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方开户银行：</span>
		        <div class="mt5">
		         ${project.purchaseDep.bank}
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方银行账号：</span>
		        <div class="mt5">
		         ${project.purchaseDep.bankAccount}
		        </div>
			 </li>
			 <div class="clear"></div>
		 </ul>
   		<%--<div class="headline-v2">
   			<h2>乙方信息</h2>
   		</div>
   		--%><h2 class="f16 count_flow mt40"><i>03</i>乙方信息</h2>
		 <ul class="list-unstyled list-flow ul_list">
			 <li class="col-md-6 p0">
			   <span class="">乙方单位：</span>
		        <div class="mt5">
		        ${project.dealSupplier.supplierName}
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">乙方法人：</span>
			   <div class="mt5">
		        ${project.dealSupplier.legalName}
		       </div>
			 </li>
			 <%--<li class="col-md-6  p0 ">
			   <span class="fl">乙方委托代理人：</span>
			   <div class="mt5">
		        <input class="span2 supplier_name" type="text" readonly="readonly">
		       </div>
			 </li>
    		 --%><li class="col-md-6 p0">
			   <span class="">乙方联系人：</span>
		        <div class="mt5">
		         ${project.dealSupplier.contactName}
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方联系电话：</span>
		        <div class="mt5">
		         ${project.dealSupplier.contactTelephone}
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方通讯地址：</span>
		        <div class="mt5">
		         ${project.dealSupplier.address}
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方邮政编码：</span>
		        <div class="mt5">
		         ${project.dealSupplier.postCode}
		        </div>
			 </li>
			 <%--<li class="col-md-6 p0">
			   <span class="">乙方开户名称：</span>
		        <div class="mt5">
		         <input class="span2 supplier_name"  type="text" readonly="readonly">
		        </div>
			 </li>
			 --%><li class="col-md-6 p0">
			   <span class="">乙方开户银行：</span>
		        <div class="mt5">
		         ${project.dealSupplier.bankName}
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方银行账号：</span>
		        <div class="mt5">
		         ${project.dealSupplier.bankAccount}
		        </div>
			 </li>
			 <div class="clear"></div>
		</ul>
  		<div  class="col-md-12 tc mt20">
   			<button class="btn" onclick="next()">下一步</button>
   			<button class="btn btn-windows cancel" onclick="history.go(-1)" type="button">取消</button>
  		</div>
  		
  	<%--</form>
 --%></div>
</body>
</html>
