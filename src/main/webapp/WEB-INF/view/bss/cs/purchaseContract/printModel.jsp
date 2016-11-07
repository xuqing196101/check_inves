<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<script type="text/javascript">
	function dayin() {
		var LODOP = getLodop();
		if (LODOP) {
			LODOP.PRINT_INIT("打印表格"); 
			LODOP.ADD_PRINT_TABLE("0","0","100%","100%",document.getElementById("newTable").innerHTML);
			
		    LODOP.PREVIEW(); 
		}
	}
	</script>
  </head>
<body>
 <div class="container" id="newTable">
 <h2 class="tc"><span class="tdu">${purCon.name}</span>采购合同<span class="fr">密级</span></h2>
 <div class="mt25 tc"><span class="fl">合同编号：${purCon.code}</span><span>签订日期：</span><span class="fr">签订地点：</span></div> 
  <table class="table table-bordered mb0 mt5 contract_table">
    <thead>
	  <tr>
	    <th colspan="2" width="50%">甲方</th>
		<th colspan="2" width="50%">乙方</th>
	  </tr>
	 </thead>
	 <tbody>
	  <tr>
	   <th width="15%">单位名称</th>
	   <td width="35%">${purCon.purchaseDepName}</td>
	   <th width="15%">单位名称</th>
	   <td width="35%">${purCon.supplierDepName}</td>
	  </tr>
	  <tr>
	   <th width="15%">法定代表人</th>
	   <td width="35%">${purCon.purchaseLegal}</td>
	   <th width="15%">法定代表人</th>
	   <td width="35%">${purCon.supplierLegal}</td>
	  </tr>
	  <tr>
	   <th width="15%">委托代理人</th>
	   <td width="35%">${purCon.purchaseAgent}</td>
	   <th width="15%">委托代理人</th>
	   <td width="35%">${purCon.supplierAgent}</td>
	  </tr>
	  <tr>
	   <th width="15%">联系人</th>
	   <td width="35%">${purCon.purchaseContact}</td>
	   <th width="15%">联系人</th>
	   <td width="35%">${purCon.supplierContact}</td>
	  </tr>
	  <tr>
	   <th width="15%">联系电话</th>
	   <td width="35%">${purCon.purchaseContactTelephone}</td>
	   <th width="15%">联系电话</th>
	   <td width="35%">${purCon.supplierContactTelephone}</td>
	  </tr>
	  <tr>
	   <th width="15%">通讯地址</th>
	   <td width="35%">${purCon.purchaseContactAddress}</td>
	   <th width="15%">通讯地址</th>
	   <td width="35%">${purCon.supplierContactAddress}</td>
	  </tr>
	  <tr>
	   <th width="15%">邮政编码</th>
	   <td width="35%">${purCon.purchaseUnitpostCode}</td>
	   <th width="15%">邮政编码</th>
	   <td width="35%">${purCon.supplierUnitpostCode}</td>
	  </tr>
	  <tr>
	   <th width="15%">付款单位</th>
	   <td width="35%">${purCon.purchasePayDep}</td>
	   <th width="15%">开户名称</th>
	   <td width="35%">${purCon.supplierBankName}</td>
	  </tr>
	  <tr>
	   <th width="15%">开户银行</th>
	   <td width="35%">${purCon.purchaseBank}</td>
	   <th width="15%">开户银行</th>
	   <td width="35%">${purCon.supplierBank}</td>
	  </tr>
	  <tr>
	   <th width="15%">银行账号</th>
	   <td width="35%">${purCon.purchaseBankAccount}</td>
	   <th width="15%">银行账号</th>
	   <td width="35%">${purCon.supplierBankAccount}</td>
	  </tr>
	  <tr class="btomnone">
	    <td colspan="4" class="btomnone" >
	     <p><div class="fl">一、</div><div class="fl mr10">计划任务文号：<span class="tdu">${purCon.documentNumber}</span></div>
		     <div class="fl mr10">合同批准文号：<span class="tdu">${purCon.approvalNumber}</span></div>
			 <div class="fl mr10">采购机构资格证号：<span class="tdu">${purCon.quaCode}</span></div>
		 </p>
		 <p class="clear"><div class="fl">二、</div><div class="mr10">合同标的：</div></p> 
        </td>
	  </tr>
	  </table>
	  
	  <table class="table table-bordered contract_table btomnone">
	   <tr>
	    <th>序号</th>
		<th>编号</th>
		<th>物资名称</th>
		<th>品牌商标</th>
		<th>规格型号</th>
		<th>计量单位</th>
		<th>数量</th>
		<th>单价(元)</th>
		<th>合计金额(元)</th>
		<th>交付时间</th>
		<th>备注</th>
	  </tr>
	  <c:forEach items="${requList}" var="requ" varStatus="vs">
	  <tr>
	    <td>${(vs.index+1)}</td>
		<td>${requ.planNo}</td>
		<td>${requ.goodsName}</td>
		<td>${requ.brand}</td>
		<td>${requ.stand}</td>
		<td>${requ.item}</td>
		<td>${requ.purchaseCount}</td>
		<td>${requ.price}</td>
		<td>${requ.amount}</td>
		<td>${requ.deliverDate}</td>
		<td>${requ.memo}</td>
	  </tr>
	  </c:forEach>
	  <tr>
	    <td  colspan="11" >
	     <p class="mb0 b">合计：${purCon.money}人民币（      ）金额（大写）    亿    仟    佰    拾    万    仟    佰    拾    元    角    分   （小写）￥：
		 </p>
        </td>
	  </tr>
	  <tr>
	    <td  colspan="11" class="contract_desc">
	    ${purCon.content}
        </td>
	  </tr>
	  </table>
		<input type="button" class="btn" value="打印" onclick="dayin()"/>
	 </tbody>
  </table>
 </div>
</body>
</html>
