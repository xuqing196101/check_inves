<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
	<jsp:include page="/WEB-INF/view/common.jsp"/>
    <title>合同草稿修改</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css"> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
    <script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/My97DatePicker/WdatePicker.js"></script>
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
    var treeid = null , nodeName=null;
	var datas;
	 $(document).ready(function(){  
          $.fn.zTree.init($("#treeDemo"),setting,datas);
	      var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	      var nodes =  treeObj.transformToArray(treeObj.getNodes()); 
	      for(var i=0 ;i<nodes.length;i++){
		     if (nodes[i].status==1) {
				 check==true;
		      }
	       }
	      var conTy = "${draftCon.contractType}";
	 	   $("#contractType").val(conTy);
	 }); 
	 var setting={
		   async:{
					autoParam:["id"],
					enable:true,
					url:"${pageContext.request.contextPath}/category/createtree.do",
					otherParam:{"otherParam":"zTreeAsyncTest"},  
					dataType:"json",
					datafilter:filter,
					type:"get",
				},
				callback:{
			    	onClick:zTreeOnClick,//点击节点触发的事件
       			    
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
						rootPId:"0",
					}
			    },
			   view:{
			        selectedMulti: false,
			        showTitle: false,
			   },
         };
	
	 
	 function filter(treeId,parentNode,childNode){
		 if (!childNodes) return null;
			for(var i = 0; i<childNodes.length;i++){
				childNodes[i].name = childNodes[i].name.replace(/\.n/g,'.');
			}
		return childNodes;
	 }
	 
	 /*点击事件*/
	    function zTreeOnClick(event,treeId,treeNode){
	  	  if (isRoot(treeNode)){
	  		  layer.msg("不可选择根节点");
	  		  return;
	  	  }
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
			layer.alert("请选择要删除的信息",{offset: ['50%', '390px'], shade:0.01});
		}
    }
   	
    function showMenu() {
		var cityObj = $("#citySel4");
		var cityOffset = $("#citySel4").offset();
		$("#menuContent").css({left: "79px", top: "78px"}).slideDown("fast");

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
		var sum1 = $("#purBudgetSum").val()-0;
		var sumbudget = $("#budget").val();
		var sum2 = null;
		var tds = $(".ss");
		for(var i=0;i<tds.length;i++){
			var num1 = $(tds[i]).val()-0;
			sum2 = sum2+num1;
		}
		var sumAll = sum1+sum2;
		if(sumAll>sumbudget){
			layer.close(index);
			layer.alert("明细总价不得超过预算",{offset: ['50%', '40%'], shade:0.01});
		}else{
			
			$.ajax({
				url:"${pageContext.request.contextPath}/purchaseContract/validAddRe.html",
				type:"post",
				dataType:"json",
				data:$('#myForm').serialize(),
				success:function(data){
					if(data==1){
						var detab = $("#detailtable tr:last td:eq(1)");
						var vstab = Number(detab.html());
						if($("#detailtable tr").length<=1){
							vstab = 0;
						}
						var html = "";
						var tabl = $("#detailtable");
						html += "<tr><td class='tc w30'><input onclick='check()' type='checkbox' name='chkItem' value='' /></td>";
						html += "<td class='tc w50'>"+(vstab+1)+"</td>";
						html += "<td class='tc w30'><input type='text' name='proList["+(vstab+1)+"].planNo' readonly='readonly' value='"+$('#planNo').val()+"' class='w50'/></td>";
						html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].goodsName' readonly='readonly' value='"+$('#citySel4').val()+"'/></td>";
						html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].brand' readonly='readonly' value='"+$('#bra').val()+"'/></td>"
						html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].stand' readonly='readonly' value='"+$('#model').val()+"' class='w60'/></td>"
						html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].item' readonly='readonly' value='"+$('#unit').val()+"' class='w50'/></td>"
						html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].purchaseCount' readonly='readonly' value='"+$('#purNum').val()+"' class='w50'/></td>"
						html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].price' readonly='readonly' value='"+$('#univalent').val()+"' class='w50'/></td>"
						html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].amount' readonly='readonly' value='"+$('#purBudgetSum').val()+"' class='w50'/></td>"
						html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].deliverDate' readonly='readonly' value='"+$('#givetime').val()+"' class='w100'/></td>"
						html += "<td class='tc'><input type='text' name='proList["+(vstab+1)+"].memo' readonly='readonly' value='"+$('#remarks').val()+"'/></td>"
						html += "<td class='tnone'></td>"
						tabl.append(html);
						layer.close(index);
					}else{
						var obj = new Function("return" + data)();
						$("#wzmc").text(obj.wzmc);
						$("#bh").text(obj.bh);
						$("#jfsj").text(obj.jfsj);
						$("#ppsb").text(obj.ppsb);
						$("#ggxh").text(obj.ggxh);
						$("#jldw").text(obj.jldw);
						$("#sl").text(obj.sl);
						$("#dj").text(obj.dj);
					}
				}
			});
		}
	}
	
	function abandoned(){
		window.location.href="${pageContext.request.contextPath}/purchaseContract/selectDraftContract.html";
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
		area : [ '50%', '400px' ], //宽高
		content : $('#openDiv'),
		offset: ['10%', '10%']
	  });
    }
	
	function quxiao(){
	     layer.close(index);
	}
	
	function staging(){
		$("#status").val("0");
		$("#contractForm").submit();
	}

	var ind;
	function formalContract(){	
		ind = layer.open({
			shift: 1, //0-6的动画形式，-1不开启
		    moveType: 1, //拖拽风格，0是默认，1是传统拖动
		    title: ['请输入合同批准文号','border-bottom:1px solid #e5e5e5'],
		    shade:0.01, //遮罩透明度
			type : 1,
			skin : 'layui-layer-rim', //加上边框
			area : [ '45%', '300px' ], //宽高
			content : $('#numberWin'),
			offset: ['70%', '25%']
		});
	}
	
	function save(){
		$("#status").val("2");
		var apN = $("#apN").val();
		var picFile = $("#fi").val();
		var picFiles = picFile.split(".");
		var pic = picFiles[picFiles.length-1];
		var formalGitAt = $("#formalGitAt").val();
		var formalReviewedAt = $("#formalReviewedAt").val();
		var flag = false;
		var news = "";
		if(apN!=null && apN!=''){
			flag = true;
		}else{
			flag = false;
			news+="请先填写合同批准文号,";
		}
		if(formalReviewedAt!=null && formalReviewedAt!=""){
			flag = true;
		}else{
			flag = false;
			news+="请填写报批时间";
		}
		if(pic!=null && pic!=''){
			if(pic=='bmp' || pic=='png' || pic=='gif' && pic=='jpg' && pic=='jpeg'){
				flag=true;
			}else{
				flag=false;
				news+="上传的附件类型不正确";
			}
		}else{
			flag=false;
			news+="请上传批准电子扫描件,";
		}
		
		if(flag){
			$("#appN").val(apN);
			$("#contractForm").submit();
		}else{
			layer.alert(news,{offset: ['70%', '40%'], shade:0.01});
		}
	}
	
	function cancel(){
		layer.close(ind);
	}
	
	function printdra(){
		$("#contractForm").attr("action","${pageContext.request.contextPath}/purchaseContract/printDraftContract.html?ids=${ids}");
		$("#contractForm").submit();
	}
	
	function imTemplet(){
		var iframeWin;
        layer.open({
          type: 2, //page层
          area: ['800px', '500px'],
          title: '引用模板',
          closeBtn: 1,
          shade:0.01, //遮罩透明度
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['70%', '20%'],
          shadeClose: false,
          content: '${pageContext.request.contextPath}/resultAnnouncement/getAll.html',
          success: function(layero, index){
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
          }
        });
    }
    </script>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">保障作业</a></li><li><a href="#">采购合同管理</a></li><li class="active"><a href="#">合同草稿修改</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
<!-- 新增模板开始-->
   <div class="container bggrey border1 mt20">
   		<form id="contractForm" action="${pageContext.request.contextPath}/purchaseContract/updateDraftContract.html?ids=${ids}" method="post" >
   		<input type="hidden" name="status" value="" id="status"/>
   		<input type="hidden" name="id" value="${draftCon.id}"/>
   		<input type="hidden" name="supplierPurId" value="${draftCon.supplierPurId}"/>
   		<input type="hidden" name="projectName" value="${draftCon.projectName}"/>
   		<h2 class="f16 count_flow mt40"><i>01</i>基本信息</h2>
   		<input type="hidden" name="approvalNumber" id="appN" value=""/>
   		<ul class="list-unstyled ul_list">
   			<input type="hidden" class="contract_id" name="contract_id">
		     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>合同名称：</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
		        <input class="contract_code" id="contract_code" value="${draftCon.name}" name="name" type="text">
		        <div class="cue">${ERR_name}</div>
		       </div>
			 </li>
    		 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>合同编号：</span>
		        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
		        	<input class="contract_name" name="code" value="${draftCon.code}" type="text">
		        	<div class="cue">${ERR_code}</div>
       			</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>合同金额：</span>
		        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
		        	<input class="contract_name" name="money_string" value="${draftCon.money_string}" type="text">
		        	<div class="cue">${ERR_money}</div>
       			</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>计划任务文号：</span>
		        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
		        	<input class="contract_name" name="documentNumber" value="${draftCon.documentNumber}" type="text">
		        	<div class="cue">${ERR_documentNumber}</div>
       			</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>采购机构资格证号：</span>
		        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
		        	<input class="contract_name" name="quaCode" value="${draftCon.quaCode}" type="text">
		        	<div class="cue">${ERR_quaCode}</div>
       			</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>需求部门：</span>
		        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
		        	<input class="contract_name" name="demandSector" value="${draftCon.demandSector}" type="text">
		        	<div class="cue">${ERR_demandSector}</div>
       			</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>预算：</span>
		        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
		        	<input class="contract_name" id="budget" name="budget_string" value="${draftCon.budget_string}" type="text">
		        	<div class="cue">${ERR_budget}</div>
       			</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>项级预算科目：</span>
		        <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
		        	<input class="contract_name" name="budgetSubjectItem" value="${draftCon.budgetSubjectItem}" type="text">
		        	<div class="cue">${ERR_budgetSubjectItem}</div>
       			</div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>合同类型：</span>
			   <div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
		        	<select name="contractType" id="contractType" class="contract_name">
		        		<option></option>
		        		<option value="0">正常采购合同</option>
		        		<option value="1">以厂代储合同</option>
		        		<option value="2">合同储备合同</option>
		        	</select>
		        	<div class="cue">${ERR_contractType}</div>
		        </div>
			 </li>
			 <div class="clear"></div>
		 </ul>
   		<h2 class="f16 count_flow mt40"><i>02</i>甲方信息</h2>
		 <ul class="list-unstyled ul_list">
    		 <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>甲方单位：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		        	<input class="supplier_id" name="purchaseDepName" value="${draftCon.purchaseDepName}" type="text">
		        	<div class="cue">${ERR_purchaseDepName}</div>
       			</div>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>甲方法人：</span>
			   <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		        <input class="supplier_name" name="purchaseLegal" value="${draftCon.purchaseLegal}" type="text">
		        <div class="cue">${ERR_purchaseLegal}</div>
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>甲方委托代理人：</span>
			   <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		        <input class="supplier_name" name="purchaseAgent" value="${draftCon.purchaseAgent}" type="text">
		        <div class="cue">${ERR_purchaseAgent}</div>
		       </div>
			 </li>
    		 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>甲方联系人：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="purchaseContact" value="${draftCon.purchaseContact}" type="text">
		         <div class="cue">${ERR_purchaseContact}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>甲方联系电话：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="purchaseContactTelephone" value="${draftCon.purchaseContactTelephone}" type="text">
		         <div class="cue">${ERR_purchaseContactTelephone}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>甲方通讯地址：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="purchaseContactAddress" value="${draftCon.purchaseContactAddress}" type="text">
		         <div class="cue">${ERR_purchaseContactAddress}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>甲方邮政编码：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="purchaseUnitpostCode" value="${draftCon.purchaseUnitpostCode}" type="text">
		         <div class="cue">${ERR_purchaseUnitpostCode}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>甲方付款单位：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="purchasePayDep" value="${draftCon.purchasePayDep}" type="text">
		         <div class="cue">${ERR_purchasePayDep}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>甲方开户银行：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="purchaseBank" value="${draftCon.purchaseBank}" type="text">
		         <div class="cue">${ERR_purchaseBank}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>甲方银行账号：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="purchaseBankAccount_string" value="${draftCon.purchaseBankAccount_string}" type="text">
		         <div class="cue">${ERR_purchaseBankAccount}</div>
		        </div>
			 </li>
			 <div class="clear"></div>
		 </ul>
   		<h2 class="f16 count_flow mt40"><i>03</i>乙方信息</h2>
		 <ul class="list-unstyled ul_list">
			 <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>乙方单位：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		        	<input class="supplier_id" name="supplierDepName" type="text" value="${draftCon.supplierDepName}">
		        	<div class="cue">${ERR_supplierDepName}</div>
       			</div>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>乙方法人：</span>
			   <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		        <input class="supplier_name" name="supplierLegal" type="text" value="${draftCon.supplierLegal}">
		        <div class="cue">${ERR_supplierLegal}</div>
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>乙方委托代理人：</span>
			   <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		        <input class="supplier_name" name="supplierAgent" value="${draftCon.supplierAgent}" type="text">
		        <div class="cue">${ERR_supplierAgent}</div>
		       </div>
			 </li>
    		 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>乙方联系人：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="supplierContact" value="${draftCon.supplierContact}" type="text">
		         <div class="cue">${ERR_supplierContact}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>乙方联系电话：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="supplierContactTelephone" value="${draftCon.supplierContactTelephone}" type="text">
		         <div class="cue">${ERR_supplierContactTelephone}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>乙方通讯地址：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="supplierContactAddress" value="${draftCon.supplierContactAddress}" type="text">
		         <div class="cue">${ERR_supplierContactAddress}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>乙方邮政编码：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="supplierUnitpostCode" value="${draftCon.supplierUnitpostCode}" type="text">
		         <div class="cue">${ERR_supplierUnitpostCode}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>乙方开户名称：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="supplierBankName" value="${draftCon.supplierBankName}" type="text">
		         <div class="cue">${ERR_supplierBankName}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>乙方开户银行：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="supplierBank" value="${draftCon.supplierBank}" type="text">
		         <div class="cue">${ERR_supplierBank}</div>
		        </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12 ">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>乙方银行账号：</span>
		        <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
		         <input class="supplier_name" name="supplierBankAccount_string" value="${draftCon.supplierBankAccount_string}" type="text">
		         <div class="cue">${ERR_supplierBankAccount}</div>
		        </div>
			 </li>
			 <div class="clear"></div>
		</ul>
        <h2 class="f16 count_flow mt40"><i>04</i>项目明细</h2>
		<div>
			<input type="button" class="btn btn-windows add" onclick="openDetail()" value="添加"/>
			<input type="button" class="btn btn-windows delete" onclick="delDetail()" value="删除"/>
		</div>
		<div class="over_auto col-md-12 col-sm-12 col-xs-12 p0">
    	<table id="detailtable" name="proList" class="table table-bordered table-condensed mb0 mt10">
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
				<th class="tnone"></th>
			</tr>
		</thead>
		<c:forEach items="${draftCon.contractReList}" var="reque" varStatus="vs">
			<tr>
				<td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td>
				<td class="tc w50">${(vs.index+1)}</td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].planNo" readonly="readonly" value="${reque.planNo}" class="w50"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].goodsName" readonly="readonly" value="${reque.goodsName}"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].brand" readonly="readonly" value="${reque.brand}"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].stand" readonly="readonly" value="${reque.stand}" class="w60"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].item" readonly="readonly" value="${reque.item}" class="w50"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].purchaseCount" readonly="readonly" value="${reque.purchaseCount}" class="w50"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].price" readonly="readonly" value="${reque.price}" class="w50"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].amount" readonly="readonly" value="${reque.amount}" class="ss w50"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].deliverDate" readonly="readonly" value="${reque.deliverDate}" class="w100"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].memo" readonly="readonly" value="${reque.memo}"/></td>
				<td class="tnone"><input type="text" name="proList[${(vs.index)}].id" readonly="readonly" value="${reque.id}"/></td>
			</tr>
   		</c:forEach>
	</table>
	</div>
	<h2 class="f16 count_flow mt40"><i>05</i>合同正文</h2>
   	<div>
		<input type="button" class="btn" onclick="imTemplet()" value="导入模板"/>
	</div>
   	<div class="mt10">
       <script id="editor" name="content" type="text/plain" class= ""></script>
       <div class="red f12 clear">${ERR_content}</div>
    </div>
  		<div  class="col-md-12 tc mt20">
   			<input type="button" class="btn btn-windows save mb20" onclick="staging()" value="保存"/>
   			<%--<input type="button" class="btn" onclick="formalContract()" value="生成正式合同"/>
   			--%>
   			<input type="button" class="btn btn-windows cancel mb20" onclick="abandoned()" value="取消">
  		</div>
  		<%--<ul class="list-unstyled list-flow dnone mt10" id="numberWin">
  		    <li class="col-md-12 ml15">
			   <span class="span3 fl mt5"><div class="red star_red">*</div>合同批准文号：</span>
			   <input type="text" id="apN" name="approvalNumber" value="" class="mb0 w220"/>
			</li>
			<li class="col-md-12">
			   <span class="span3 fl mt5"><div class="red star_red">*</div>正式合同上报时间：</span>
			   <input type="text" name="formalGitAt" id="formalGitAt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w220"/>
			</li>
			<li class="col-md-12">
			   <span class="span3 fl mt5"><div class="red star_red">*</div>正式合同批复时间：</span>
			   <input type="text" name="formalReviewedAt" id="formalReviewedAt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w220"/>
			</li>
			<li class="col-md-12 mt10">
			   <span class="span3 fl"><div class="red star_red">*</div>上传附件：</span>
		  		<up:upload id="post_attach_up"  businessId="${attachuuid}" sysKey="${attachsysKey}" typeId="${attachtypeId}" auto="true" />
          		<up:show showId="post_attach_show" businessId="${attachuuid}" sysKey="${attachsysKey}" typeId="${attachtypeId}"/>
            </li>
			<li class="tc col-md-12 mt20">
			 <input type="button" class="btn" onclick="save()" value="生成"/>
			 <input type="button" class="btn" onclick="cancel()" value="取消"/>
			</li>
	 </ul>
  	--%></form>
 </div>
 	<div id="openDiv" class="dnone layui-layer-wrap">
 	<div id="menuContent" class="menuContent dw188 tree_drop">
		<ul id="treeDemo" class="ztree slect_option"></ul>
		</div>
			<div class="drop_window">
			<form id="myForm" action="${pageContext.request.contextPath}/purchaseContract/validAddRe.html">
			  <ul class="list-unstyled">
			    <li class="mt10 col-md-12 p0">
	    	      <label class="col-md-12 pl20"><div class="red star_red">*</div>物资名称：</label>
	    	      <span class="col-md-12">
	    	      <div class="input-long">
                   <input type="hidden" id="categorieId4" name="categoryId" value="">
				   <input id="citySel4" type="text"  readonly="readonly" name="goodsName"  value=""  class="title col-md-12" onclick=" showMenu(); return false;"/>
				   <div class="cue" id="wzmc"></div>
				   </div>
				  </span>
	            </li>
			    <li class="col-md-6">
	    	      <label class="col-md-12 padding-left-5">
	    	        <div class="red star_red">*</div>编号：</label>
	    	        <div class="input-append col-md-12 p0">
                      <input id="planNo" name="planNo" type="text" class="col-md-12 p0">
                      <div class="cue" id="bh"></div>
                    </div>
	            </li>
			    <li class="col-md-6">
	    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>交付时间</label>
	    	       <div class="input-append col-md-12 p0">
                   <input id="givetime" name="deliverDate" value="" type="text" class="col-md-12 p0">
                   <div class="cue" id="jfsj"></div>
                   </div>
                  </span>
	            </li>
			    <li class="col-md-6">
	    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>品牌商标</label>
	    	       <div class="input-append col-md-12 p0">
                    <input id="bra" name="brand" value="" type="text" class="col-md-12 p0">
                    <div class="cue" id="ppsb"></div>
                  </div>
	            </li>
			    <li class="col-md-6">
	    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>规格型号</label>
	    	       <div class="input-append col-md-12 p0">
                   <input id="model" name="stand" value="" type="text" class="col-md-12 p0">
                   <div class="cue" id="ggxh"></div>
	            </li> 
			    <li class="col-md-3">
	    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>计量单位</label>
                  <div class="input-append col-md-12 p0">
                   <input id="unit" name="item" value="" type="text" class="col-md-12 p0">
                   <div class="cue" id="jldw"></div>
                  </div>
	            </li>
				<li class="col-md-3">
	    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>数量</label>
                  <div class="input-append col-md-12 p0">
                   <input id="purNum" name="purchaseCount" onblur="sum1()" type="text"class="col-md-12 p0">
                   <div class="cue" id="sl"></div>
	              </div>
	            </li>
			    <li class="col-md-3">
	    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>单价</label>
                  <div class="input-append col-md-12 p0">
                   <input id="univalent" name="price" onblur="sum1()" value="" type="text" class="col-md-12 p0">
                   <div class="cue" id="dj"></div>
	              </div>
	            </li>
			    <li class="col-md-3">
	    	      <label class="col-md-12 padding-left-5">合计</label>
                  <div class="input-append col-md-12 p0">
                   <input id="purBudgetSum" name="amount" value="" readonly="readonly" type="text" class="col-md-12 p0">
	              </div>
	            </li> 
			    <li class="col-md-12">
	    	      <label class="col-md-12 padding-left-5">备注</label>
                  <div class="input-append col-md-12 p0">
                    <textarea id="remarks" name="memo" class="col-md-12 h80 p0" rows="3" cols="1"></textarea>
	              </div>
	            </li> 
	            <div class="clear"></div>
			  </ul>
			  </form>
			</div>
              <div class="tc mt20 col-md-12 mb10">
                <input class="btn"  id = "inputb" name="addr"  type="button" onclick="bynSub();" value="确定"> 
				<input class="btn"  id = "inputa" name="addr"  type="button" onclick="quxiao();" value="取消"> 
              </div>
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
    		});
		</script>
</body>
</html>
