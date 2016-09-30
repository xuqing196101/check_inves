<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
    <title>合同基本信息修改页</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css"> 
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/ueditor.all.min.js"> </script>
	<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="<%=basePath%>/public/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
    <script language="javascript" type="text/javascript" src="<%=basePath%>/public/layer/layer.js"></script>
	<script type="text/javascript" src="<%=basePath%>/public/layer/extend/layer.ext.js"></script>
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
   	 	var datas;
   	 $(document).ready(function(){
   		 var setting={
   			async:{
   						autoParam:["id","name"],
   						enable:true,
   						url:"<%=basePath%>category/createtree.do",
   						dataType:"json",
   						type:"post",
   					},
   					callback:{
   				    	onClick:zTreeOnClick,//点击节点触发的事件
   	       			  /*    onNodeCreated: zTreeOnNodeCreated, */
   	       			   
   				    }, 
   					data:{
   						keep:{
   							parent:true
   						},
   						key:{
   							title:"title"
   						},
   						simpleData:{
   							enable:true,
   							idKey:"id",
   							pIdKey:"pId",
   							rootPId:"a",
   						}
   				    },
   				    edit:{
   				    	enable:true,
   						editNameSelectAll:true,
   						showRemoveBtn: true,
   						showRenameBtn: true,
   						removeTitle: "删除",
   						renameTitle:"重命名",
   					},
   				    check:{
   						enable: true
   				    },
   				
   	  };
   	    $.fn.zTree.init($("#treeDemo"),setting,datas);
   	   // $("#treeDemo").hide();
   	});
   	function next(){
   		var ids = "${ids}";
   		window.location.href="<%=basePath%>purchaseContract/createDetailContract.html?ids="+ids;
   	}
   	/*点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
   		treeid=treeNode.id
    }
   	
    function showMenu() {
		var cityObj = $("#citySel4");
		var cityOffset = $("#citySel4").offset();
		$("#menuContent").css({left: "162px", top: "25px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
    function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	
	var index;
	function openDetail(){
	  index =  layer.open({
	    shift: 1, //0-6的动画形式，-1不开启
	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
	    title: ['新增明细','border-bottom:1px solid #e5e5e5'],
	    shade:0.01, //遮罩透明度
		type : 1,
		skin : 'layui-layer-rim', //加上边框
		area : [ '40%', '400px' ], //宽高
		content : $('#openDiv'),
		offset: ['600px', '350px']
	});
}
    </script>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">采购合同管理</a></li><li class="active"><a href="#">合同文本修改</a></li>
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
		        <input class="span2 contract_code" value="${project.name}" id="contract_code" type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">合同编号：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" value="${project.projectNumber}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">合同金额：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" value="${project.amount}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">计划任务文号：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" value="${planNos}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">采购机构资格证号：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" value="${project.purchaseDep.quaCode}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">需求部门：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" value="${project.sectorOfDemand}" type="text">
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
		        	<input class="span2 supplier_id" value="${project.purchaseDep.depName}" type="text">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">甲方法人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" value="${project.purchaseDep.legal}" type="text">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">甲方委托代理人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" value="${project.purchaseDep.agent}" type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">甲方联系人：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.contact}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方联系电话：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.contactTelephone}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方通讯地址：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.contactAddress}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方邮政编码：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.unitPostCode}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方付款单位：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.payDep}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方开户银行：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.bank}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方银行账号：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.purchaseDep.bankAccount}" type="text">
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
		        	<input class="span2 supplier_id" type="text" value="${project.dealSupplier.supplierName}">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">乙方法人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" type="text" value="${project.dealSupplier.legalName}">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">乙方委托代理人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">乙方联系人：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.contactName }" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方联系电话：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.contactTelephone}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方通讯地址：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.address}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方邮政编码：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.postCode}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方开户名称：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name"  type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方开户银行：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.bankName}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方银行账号：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" value="${project.dealSupplier.bankAccount}" type="text">
		        </div>
			 </li>
			 <div class="clear"></div>
		</ul>
		<div class="headline-v2">
   			<h2>项目明细</h2>
   		</div>
		<div class="clear container">
		<div class="p10_25">
		<div>
			<button class="btn btn-windows add" onclick="openDetail()">添加</button>
			<button class="btn btn-windows delete" onclick="delDetail()">删除</button>
		</div>
    	<table class="table table-bordered table-condensed mt5">
		 <thead>
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
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
		<c:forEach items="${requList}" var="reque" varStatus="vs">
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
				<td class="tc">${(vs.index+1)}</td>
				<td class="tc">${reque.planNo}</td>
				<td class="tc">${reque.goodsName}</td>
				<td class="tc"></td>
				<td class="tc">${stand}</td>
				<td class="tc">${item}</td>
				<td class="tc">${purchaseCount}</td>
				<td class="tc">${price}</td>
				<td class="tc">${budget}</td>
				<td class="tc">${deliverDate}</td>
				<td class="tc">${memo}</td>
			</tr>
   		</c:forEach>
	</table>
     </div>
    </div>
  		<div  class="col-md-12 tc mt20">
   			<button class="btn btn-windows save" onclick="next()">暂存</button>
   			<button class="btn btn-windows save" onclick="next()">生成草案</button>
   			<button class="btn btn-windows save" onclick="next()">打印</button>
   			<button class="btn btn-windows cancel" onclick="history.go(-1)" type="button">取消</button>
  		</div>
  	<%--</form>
 --%></div>
 	<div id="openDiv" class="dnone">
	<div id="menuContent" class="menuContent dw188">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
			<div align="center">
			<table>
			<tr align="left">
				<th><span class="spredm">*</span>品目划分:</th>
				<td>
				<input type="hidden" id="categorieId4" name="categoryId" value="">
				<input id="citySel4" type="text"  readonly name="categoryName"  value=""  class="title" onclick=" showMenu(); return false;"/>&nbsp;&nbsp;&nbsp;
				<span class="sred" id="categores"></span>
				</td>
				</tr>
				<tr align="left">
				<th><span class="spredm">*</span>数量:</th>
				 <td><input maxlength="11" id="purNum" name="purNum" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"   type="text" id="num" >&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr align="left">
				<th><span class="spredm">*</span>财政预算:</th>
				<td><input maxlength="11" id="purBudget" name="purBudget" onblur="sum1();"  type="text"  >&nbsp;&nbsp;&nbsp;</td>
				 </tr>
				 <tr align="left">
				 <th><span class="spredm">*</span>非财政预算 :</th>
				 <td><input maxlength="11" id="purOtherBudget" name="purOtherBudget"  onblur="sum2();"  type="text"   >&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr align="left">
				<th><span class="spredm">*</span>预算合计:</th>
				 <td><input maxlength="11" id="purBudgetSum" name="purBudgetSum"  value="0" readonly="readonly" type="text" >&nbsp;&nbsp;&nbsp;</td>
			
				</tr >
			<tr align="left" >
			<th>备注(200字以内):</th>
				<td align="left">
					<textarea class="textAreaSize" rows="5" cols="1"></textarea>
				</td>
			</tr>
			<tr align="left">
			
				<td colspan="6" align="center">
				<input class="btn"  id = "inputb" name="addr"  type="button" onclick="bynSub();" value="确定"> 
				<input class="btn"  id = "inputa" name="addr"  type="button" onclick="quxiao();" value="取消"> 
				</td>
			</tr>
			</table>
			</div>
		</div>
</body>
</html>
