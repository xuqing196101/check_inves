<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>合同草稿修改</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css"> 
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
    <script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/public/ueditor/lang/zh-cn/zh-cn.js"></script>
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
  function showPic(){
		layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 0,
			  area: 'auto',
			  skin: 'layui-layer-nobg', //没有背景色
			  shadeClose: true,
			  content: $("#photo")
			});
	};
  </script>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">采购合同管理</a></li><li class="active"><a href="#">正式合同查看</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 新增模板开始-->
   <div class="container">
   		<form id="contractForm">
   		<div class="headline-v2 bggrey">
   			<h2>基本信息</h2>
   		</div>
   		<ul class="list-unstyled list-flow ul_list">
   			<input type="hidden" class="contract_id" name="contract_id">
		     <li class="col-md-6 p0 ">
			   <span class="">合同名称：</span>
			   <div class="input-append">
		        <input class="span2 contract_code" readonly="readonly" id="contract_code" value="${draftCon.name}" name="name" type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">合同编号：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" readonly="readonly" name="code" value="${draftCon.code}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">合同金额：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" readonly="readonly" name="money" value="${draftCon.money}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">计划任务文号：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" readonly="readonly" name="documentNumber" value="${draftCon.documentNumber}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">采购机构资格证号：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" readonly="readonly" name="quaCode" value="${draftCon.quaCode}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">需求部门：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" readonly="readonly" name="demandSector" value="${draftCon.demandSector}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">预算：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" readonly="readonly" name="budget" value="${draftCon.budget}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">项级预算科目：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" readonly="readonly" name="budget" value="${draftCon.budgetSubjectItem}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">合同批准文号：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" readonly="readonly" name="budget" value="${draftCon.approvalNumber}" type="text">
       			</div>
			 </li>
			 <div class="clear"></div>
		 </ul>
   		<div class="headline-v2 bggrey">
   			<h2>甲方信息</h2>
   		</div>
		 <ul class="list-unstyled list-flow ul_list">
    		 <li class="col-md-6 p0">
			   <span class="">甲方单位：</span>
		        <div class="input-append ">
		        	<input class="span2 supplier_id" readonly="readonly" name="purchaseDepName" value="${draftCon.purchaseDepName}" type="text">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">甲方法人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" readonly="readonly" name="purchaseLegal" value="${draftCon.purchaseLegal}" type="text">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">甲方委托代理人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" readonly="readonly" name="purchaseAgent" value="${draftCon.purchaseAgent}" type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">甲方联系人：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="purchaseContact" value="${draftCon.purchaseContact}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方联系电话：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="purchaseContactTelephone" value="${draftCon.purchaseContactTelephone}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方通讯地址：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="purchaseContactAddress" value="${draftCon.purchaseContactAddress}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方邮政编码：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="purchaseUnitpostCode" value="${draftCon.purchaseUnitpostCode}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方付款单位：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="purchasePayDep" value="${draftCon.purchasePayDep}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方开户银行：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="purchaseBank" value="${draftCon.purchaseBank}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方银行账号：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="purchaseBankAccount" value="${draftCon.purchaseBankAccount}" type="text">
		        </div>
			 </li>
			 <div class="clear"></div>
		 </ul>
   		<div class="headline-v2 bggrey">
   			<h2>乙方信息</h2>
   		</div>
		 <ul class="list-unstyled list-flow ul_list">
			 <li class="col-md-6 p0">
			   <span class="">乙方单位：</span>
		        <div class="input-append ">
		        	<input class="span2 supplier_id" readonly="readonly" name="supplierDepName" type="text" value="${draftCon.supplierDepName}">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">乙方法人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" readonly="readonly" name="supplierLegal" type="text" value="${draftCon.supplierLegal}">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">乙方委托代理人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" readonly="readonly" name="supplierAgent" value="${draftCon.supplierAgent}" type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">乙方联系人：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="supplierContact" value="${draftCon.supplierContact}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方联系电话：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="supplierContactTelephone" value="${draftCon.supplierContactTelephone}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方通讯地址：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="supplierContactAddress" value="${draftCon.supplierContactAddress}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方邮政编码：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="supplierUnitpostCode" value="${draftCon.supplierUnitpostCode}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方开户名称：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="supplierBankName" value="${draftCon.supplierBankName}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方开户银行：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="supplierBank" value="${draftCon.supplierBank}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方银行账号：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" readonly="readonly" name="supplierBankAccount" value="${draftCon.supplierBankAccount}" type="text">
		        </div>
			 </li>
			 <div class="clear"></div>
		</ul>
		<div class="headline-v2 bggrey">
   			<h2>项目明细</h2>
   		</div>
		<div class="clear container">
		<div class="p10_25">
    	<table id="detailtable" name="" class="table table-bordered table-condensed mb0">
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
				<th class="info">合计金额(元)</th>
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
     </div>
    </div>
    <div class="headline-v2 bggrey">
   		<h2>合同正文</h2>
   	</div>
   	<div class="container">
   	  <div class="p10_25 col-md-11">
       <script id="editor" name="content" type="text/plain" class= ""></script>
      </div>
    </div>
    <div class="headline-v2 bggrey">
   		<h2>批准文件电子扫描件</h2>
   	</div>
   	<div class="container">
   	  <div class="p10_25 col-md-11">
   	  	<a class="pointer" onclick="showPic()">${fn:split(draftCon.approvePic, '_')[1]}</a>
       	<img class="hide" id="photo" src="${draftCon.approvePic}"/>
      </div>
    </div>
  	</form>
  	<div class="col-md-12 tc mt20">
   		<button class="btn btn-windows git" onclick="history.go(-1)" type="button">返回</button>
  	</div>
 </div>
	<script type="text/javascript">
   		//实例化编辑器
   		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
   		var ue = UE.getEditor('editor');
   		var content="${draftCon.content}";
   		ue.ready(function(){
   	  		ue.setContent(content);    
   	  		ue.setDisabled([]);
   		});
	</script>
</body>
</html>
