<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>合同草稿查看</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css"> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
    <script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">采购合同管理</a></li><li class="active"><a href="#">合同草稿查看</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 新增模板开始-->
    <div class="container content pt0">
	 <div class="row magazine-page">
	   <div class="col-md-12 col-sm-12 col-cs-12 tab-v2">
	        <div class="padding-top-10">
	        <ul class="nav nav-tabs bgwhite">
	            <li class="active"><a aria-expanded="true" href="javascript:void(0)" data-toggle="tab" class="f18">合同草案详情</a></li>
            </ul>
   		<div class="tab-content padding-top-20 over_hideen">
	<div class="tab-pane fade active in" id="tab-1">
	<h2 class="count_flow jbxx">基本信息</h2>
	    <table class="table table-bordered">
	        <tbody>
	        <tr>
	            <td class="bggrey"  width="13%">合同名称：</td>
	            <td width="37%">${draftCon.name}</td>
	            <td class="bggrey" width="13%">合同编号：</td>
	            <td width="37%"> ${draftCon.code}</td>
	           
	           
	        </tr>
	        <tr>
	            <td class="bggrey" width="13%">需求部门：</td>
	            <td width="37%">${draftCon.demandSector}</td>
	            <td class="bggrey" width="13%">采购机构资质证号：</td>
	            <td width="37%">${draftCon.quaCode}</td>
	        </tr>
	        <tr>
	            <td class="bggrey" width="13%">项目预算科目：</td>
	            <td width="37%">${draftCon.budgetSubjectItem}</td>
	            <td class="bggrey" width="13%">计划任务文号：</td>
	            <td width="37%">${draftCon.documentNumber}</td>
	        </tr>
	        <tr>
	            <td class="bggrey" width="13%">合同金额：</td>
	            <td width="37%">${draftCon.money}</td>
	            <td class="bggrey" width="13%">合同预算：</td>
	            <td width="37%">${draftCon.budget}</td>
	        </tr>
	        </tbody>
	        </table>
		  <h2 class="count_flow jbxx">甲方信息</h2>
	        <table class="table table-bordered">
	        <tbody>
	        <tr>
	            <td class="bggrey" width="13%">甲方单位：</td>
	            <td width="37%">${draftCon.purchaseDepName}</td>
	            <td class="bggrey" width="13%">甲方法人：</td>
	            <td width="37%">${draftCon.purchaseLegal}</td>
	        </tr>
	         <tr>
	            <td class="bggrey" width="13%">甲方委托代理人：</td>
	            <td width="37%">${draftCon.purchaseAgent}</td>
	            <td class="bggrey" width="13%">甲方通讯地址：</td>
	            <td width="37%">${draftCon.purchaseContactAddress}</td>
	        </tr>
	         <tr>
	            <td class="bggrey" width="13%">甲方联系人：</td>
	            <td width="37%">${draftCon.purchaseContact}</td>
	            <td class="bggrey" width="13%">甲方联系电话：</td>
	            <td width="37%">${draftCon.purchaseContactTelephone}</td>
	        </tr>
	         <tr>
	            <td class="bggrey" width="13%">甲方付款单位：</td>
	            <td width="37%">${draftCon.purchasePayDep}</td>
	            <td class="bggrey" width="13%">甲方邮政编码：</td>
	            <td width="37%">${draftCon.purchaseUnitpostCode}</td>
	        </tr>
	         <tr>
	            <td class="bggrey" width="13%">甲方开户银行：</td>
	            <td width="37%">${draftCon.purchaseBank}</td>
	            <td class="bggrey" width="13%">甲方银行账号：</td>
	            <td width="37%">${draftCon.purchaseBankAccount}</td>
	        </tr>
	        </tbody>
	        </table>
   		 <h2 class="count_flow jbxx">乙方信息</h2>
	        <table class="table table-bordered">
	        <tbody>
	        <tr>
	            <td class="bggrey" width="13%">乙方单位：</td>
	            <td width="37%">${draftCon.supplierDepName}</td>
	            <td class="bggrey" width="13%">乙方法人：</td>
	            <td width="37%">${draftCon.supplierLegal}</td>
	        </tr>
	         <tr>
	            <td class="bggrey" width="13%">乙方委托代理人：</td>
	            <td width="37%">${draftCon.supplierAgent}</td>
	            <td class="bggrey" width="13%">乙方通讯地址：</td>
	            <td width="37%">${draftCon.supplierContactAddress}</td>
	        </tr>
	         <tr>
	            <td class="bggrey" width="13%">乙方联系人：</td>
	            <td width="37%">${draftCon.supplierContact}</td>
	            <td class="bggrey" width="13%">乙方联系电话：</td>
	            <td width="37%">${draftCon.supplierContactTelephone}</td>
	        </tr>
	         <tr>
	            <td class="bggrey" width="13%">乙方开户单位：</td>
	            <td width="37%">${draftCon.supplierBankName}</td>
	            <td class="bggrey" width="13%">乙方邮政编码：</td>
	            <td width="37%">${draftCon.supplierUnitpostCode}</td>
	        </tr>
	         <tr>
	            <td class="bggrey" width="13%">乙方开户银行：</td>
	            <td width="37%">${draftCon.supplierBank}</td>
	            <td class="bggrey" width="13%">乙方银行账号：</td>
	            <td width="37%">${draftCon.supplierBankAccount}</td>
	        </tr>
	        </tbody>
	        </table>  
		 <h2 class="count_flow jbxx">项目明细</h2>
    	<table id="detailtable" name="" class="table table-bordered table-condensed mb0 mt10">
		 <thead>
			<tr>
				<th class="info w50">序号</th>
				<th class="info">编号</th>
				<th class="info">物资名称</th>
				<th class="info">品牌商标</th>
				<th class="info">规格型号</th>
				<th class="info">计量单位</th>
				<th class="info">数量</th>
				<th class="info">单价(元)</th>
				<th class="info">合计金额(万元)</th>
				<th class="info">交付时间</th>
				<th class="info">备注</th>
			</tr>
		</thead>
		<c:forEach items="${draftCon.contractReList}" var="reque" varStatus="vs">
			<tr>
				<td class="tc w50">${(vs.index+1)}</td>
				<td class="tc">${reque.planNo}</td>
				<td class="tc">${reque.goodsName}</td>
				<td class="tc">${reque.brand}</td>
				<td class="tc">${reque.stand}</td>
				<td class="tc">${reque.item}</td>
				<td class="tc">${reque.purchaseCount}</td>
				<td class="tc">${reque.price}</td>
				<td class="tc">${reque.amount}</td>
				<td class="tc">${reque.deliverDate}</td>
				<td class="tc">${reque.memo}</td>
			</tr>
   		</c:forEach>
	</table>
   <h2 class="count_flow jbxx">合同正文</h2>
   	<div class="mt10">
       <script id="editor" name="content" type="text/plain" class= ""></script>
    </div>
		<div class="col-md-12 tc mt10">
   			<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
 		</div>
  	</form>
 </div>
	<script type="text/javascript">
   		//实例化编辑器
   		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
   		var option ={
  		        toolbars: [[
	                'undo', 'redo', '|',
	                'bold', 'italic', 'underline',  'formatmatch', 'autotypeset', '|', 'forecolor', 'backcolor',                
	                 'fontfamily', 'fontsize', '|',
	                 'indent', '|',
	                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|','emotion'
  		        ]]
    		}
	        var ue = UE.getEditor('editor',option);  
   		var content='${draftCon.content}';
   		ue.ready(function(){
   	  		ue.setContent(content);    
   	  		ue.setDisabled([]);
   		});
	</script>
</body>
</html>
