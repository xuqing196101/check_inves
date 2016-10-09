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
    	 if (treeNode) {
            $("#citySel4").val(treeNode.name);
            $("#categorieId4").val(treeNode.id);
            hideMenu();
    	 }
    }
   	
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
	
	function delDetail(){
		var checkedInp = $('input[name="chkItem"]:checked');
		if(checkedInp.length>0){
			$('input[name="chkItem"]:checked').each(function(){
				$(this).parent().parent().remove();
			})
		}else{
			layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
		}
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
	
	function bynSub(){
		var detab = $("#detailtable tr:last td:eq(1)");
		var vstab = Number(detab.html());
		var html = "";
		var tabl = $("#detailtable");
		html += "<tr><td class='tc w30'><input onclick='check()' type='checkbox' name='chkItem' value='' /></td>";
		html += "<td class='tc w50'>"+(vstab+1)+"</td>";
		html += "<td class='tc w30'><input type='text' name='proList["+(vstab+1)+"].planNo' value='"+$('#planNo').val()+"'/></td>";
		html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].goodsName' value='"+$('#citySel4').val()+"'/></td>";
		html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].brand' value='"+$('#citySel4').val()+"'/></td>"
		html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].stand' value='"+$('#model').val()+"'/></td>"
		html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].item' value='"+$('#unit').val()+"'/></td>"
		html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].purchaseCount' value='"+$('#purNum').val()+"'/></td>"
		html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].price' value='"+$('#univalent').val()+"'/></td>"
		html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].amount' value='"+$('#purBudgetSum').val()+"'/></td>"
		html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].deliverDate' value='"+$('#givetime').val()+"'/></td>"
		html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].memo' value='"+$('#remarks').val()+"'/></td>"
		tabl.append(html);
		layer.close(index);
	}
	
	function quxiao(){
	     layer.close(index);
	}
	
	function sum2(){
		var budget = $("#univalent").val()-0;
		var other = $("#purNum").val()-0;
		var sum = budget*other;
		$("#purBudgetSum").val(sum);
	}
	
	function sum1(){
		var budget = $("#univalent").val()-0;
		var other = $("#purNum").val()-0;
		var sum = budget*other;
		$("#purBudgetSum").val(sum);
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
	
	function staging(){
		alert(1111);
		alert($("#contractForm"));
		$("#contractForm").submit();
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
   		<form id="contractForm" action="<%=basePath%>purchaseContract/addPurchaseContract.html" method="post">
   		<input type="hidden" name="supplierPurId" value="${project.dealSupplier.procurementDepId}"/>
   		<div class="headline-v2">
   			<h2>基本信息</h2>
   		</div>
   		<ul class="list-unstyled list-flow p0_20">
   			<input type="hidden" class="contract_id" name="contract_id">
		     <li class="col-md-6 p0 ">
			   <span class="">合同名称：</span>
			   <div class="input-append">
		        <input class="span2 contract_code" id="contract_code" value="${project.name}" name="name" type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">合同编号：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" name="code" value="${project.projectNumber}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">合同金额：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" name="money" value="${project.amount}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">计划任务文号：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" name="documentNumber" value="${planNos}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">采购机构资格证号：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" name="quaCode" value="${project.purchaseDep.quaCode}" type="text">
       			</div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">需求部门：</span>
		        <div class="input-append ">
		        	<input class="span2 contract_name" name="demandSector" value="${project.sectorOfDemand}" type="text">
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
		        	<input class="span2 supplier_id" name="purchaseDepName" value="${project.purchaseDep.depName}" type="text">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">甲方法人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" name="purchaseLegal" value="${project.purchaseDep.legal}" type="text">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">甲方委托代理人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" name="purchaseAgent" value="${project.purchaseDep.agent}" type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">甲方联系人：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="purchaseContact" value="${project.purchaseDep.contact}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方联系电话：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="purchaseContactTelephone" value="${project.purchaseDep.contactTelephone}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方通讯地址：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="purchaseContactAddress" value="${project.purchaseDep.contactAddress}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方邮政编码：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="purchaseUnitpostCode" value="${project.purchaseDep.unitPostCode}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方付款单位：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="purchasePayDep" value="${project.purchaseDep.payDep}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方开户银行：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="purchaseBank" value="${project.purchaseDep.bank}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">甲方银行账号：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="purchaseBankAccount" value="${project.purchaseDep.bankAccount}" type="text">
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
		        	<input class="span2 supplier_id" name="supplierDepName" type="text" value="${project.dealSupplier.supplierName}">
       			</div>
			 </li>
		     <li class="col-md-6  p0 ">
			   <span class="">乙方法人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" name="supplierLegal" type="text" value="${project.dealSupplier.legalName}">
		       </div>
			 </li>
			 <li class="col-md-6  p0 ">
			   <span class="fl">乙方委托代理人：</span>
			   <div class="input-append">
		        <input class="span2 supplier_name" name="supplierAgent" type="text">
		       </div>
			 </li>
    		 <li class="col-md-6 p0">
			   <span class="">乙方联系人：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="supplierContact" value="${project.dealSupplier.contactName }" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方联系电话：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="supplierContactTelephone" value="${project.dealSupplier.contactTelephone}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方通讯地址：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="supplierContactAddress" value="${project.dealSupplier.address}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方邮政编码：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="supplierUnitpostCode" value="${project.dealSupplier.postCode}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方开户名称：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="supplierBankName" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方开户银行：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="supplierBank" value="${project.dealSupplier.bankName}" type="text">
		        </div>
			 </li>
			 <li class="col-md-6 p0">
			   <span class="">乙方银行账号：</span>
		        <div class="input-append">
		         <input class="span2 supplier_name" name="supplierBankAccount" value="${project.dealSupplier.bankAccount}" type="text">
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
			<input type="button" class="btn btn-windows add" onclick="openDetail()" value="添加"/>
			<input type="button" class="btn btn-windows delete" onclick="delDetail()" value="删除"/>
		</div>
    	<table id="detailtable" name="" class="table table-bordered table-condensed mb0">
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
				<td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
				<td class="tc w50">${(vs.index+1)}</td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].planNo" value="${reque.planNo}"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].goodsName" value="${reque.goodsName}"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].brand" value="${reque.brand}"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].stand" value="${reque.stand}"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].item" value="${reque.item}"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].purchaseCount" value="${reque.purchaseCount}"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].price" value="${reque.price}"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].amount" value="${reque.budget}"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].deliverDate" value="${reque.deliverDate}"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].memo" value="${reque.memo}"/></td>
			</tr>
   		</c:forEach>
	</table>
     </div>
    </div>
    <div class="headline-v2">
   		<h2>合同正文</h2>
   	</div>
   	<div class="container">
   	  <div class="p10_25 col-md-11">
       <script id="editor" name="content" type="text/plain" class= ""></script>
      </div>
    </div>
  		<div  class="col-md-12 tc mt20">
   			<input type="button" class="btn btn-windows save" onclick="staging()" value="暂存"/>
   			<input type="button" class="btn btn-windows save" onclick="next()" value="生成草案"/>
   			<input type="button" class="btn btn-windows save" onclick="next()" value="打印"/>
   			<input type="button" class="btn btn-windows cancel" onclick="history.go(-1)" value="取消">
  		</div>
  	</form>
 </div>
 	<div id="openDiv" class="dnone">
	<div id="menuContent" class="menuContent dw188">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
			<div align="center">
			<table>
			<tr align="left">
				<th><span class="spredm">*</span>物资名称:</th>
				<td>
				<input type="hidden" id="categorieId4" name="categoryId" value="">
				<input id="citySel4" type="text"  readonly name="categoryName"  value=""  class="title" onclick=" showMenu(); return false;"/>&nbsp;&nbsp;&nbsp;
				<span class="sred" id="categores"></span>
				</td>
				</tr>
				<tr align="left">
				<th><span class="spredm">*</span>编号:</th>
				 <td><input maxlength="11" id="planNo" name="planNo" type="text" >&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr align="left">
				<th><span class="spredm">*</span>数量:</th>
				 <td><input maxlength="11" id="purNum" name="purNum" onblur="sum2()" type="text" >&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<%--<tr align="left">
				<th><span class="spredm">*</span>财政预算:</th>
				<td><input maxlength="11" id="purBudget" name="purBudget" onblur="sum1();"  type="text"  >&nbsp;&nbsp;&nbsp;</td>
				 </tr>
				 <tr align="left">
				 <th><span class="spredm">*</span>非财政预算 :</th>
				 <td><input maxlength="11" id="purOtherBudget" name="purOtherBudget"  onblur="sum2();"  type="text"   >&nbsp;&nbsp;&nbsp;</td>
				</tr>
				--%>
				<tr align="left">
				<th><span class="spredm">*</span>品牌商标:</th>
				 <td><input maxlength="11" id="bra" name="bra"  value="" type="text" >&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr align="left">
				<th><span class="spredm">*</span>规格型号:</th>
				 <td><input maxlength="11" id="model" name="model"  value="" type="text" >&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr align="left">
				<th><span class="spredm">*</span>计量单位:</th>
				 <td><input maxlength="11" id="unit" name="unit"  value="" type="text" >&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr align="left">
				<th><span class="spredm">*</span>单价:</th>
				 <td><input maxlength="11" id="univalent" onblur="sum1()" name="univalent"  value="" type="text" >&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr align="left">
				<th><span class="spredm">*</span>交付时间:</th>
				 <td><input maxlength="11" id="givetime" name="givetime"  value="" type="text" >&nbsp;&nbsp;&nbsp;</td>
				</tr>
			<tr align="left" >
			<th>备注(200字以内):</th>
				<td align="left">
					<textarea id="remarks" name="remarks" class="textAreaSize" rows="5" cols="1"></textarea>
				</td>
			</tr>
			<tr align="left">
				<th><span class="spredm">*</span>合计:</th>
				 <td><input maxlength="11" id="purBudgetSum" name="purBudgetSum"  value="0" readonly="readonly" type="text" >&nbsp;&nbsp;&nbsp;</td>
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
		<script type="text/javascript">
    		//实例化编辑器
    		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    		var ue = UE.getEditor('editor');
		</script>
</body>
</html>
