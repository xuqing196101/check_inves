<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>
<head>
	<jsp:include page="/WEB-INF/view/common.jsp"/>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>页签</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
  	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/public/select2/js/select2.js"></script>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet" />
  	<script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
    <link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
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
	      var conTy = "${purCon.contractType}";
	      var putTy = "${purCon.purchaseType}";
	 	  $("#contractType").val(conTy);
	 	  $("#purchaseType").val(putTy);
	 	  
	 	  
	 	 $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	          // 获取已激活的标签页的名称
	          var activeTab = $(e.target).text(); 
	          // 获取前一个激活的标签页的名称
	          var previousTab = $(e.relatedTarget).text(); 
	          if(activeTab=="合同文本"){
	        	  $.ajax({
	    	          url: "${pageContext.request.contextPath }/purchaseContract/createPrintPage.do",
	    	          type: "POST",
	    	          dataType: "json",
	    	          data: $("#contractForm").serialize(),
	    	          success: function(data) {
	    	        	  var obj = new Function("return" + data)();
	    	        	  OpenFile(obj.filePath);
	    	          }   
	    	    });
	          };
	       });
	      $.ajax({
	          contentType: "application/json;charset=UTF-8",
	          url: "${pageContext.request.contextPath }/purchaseContract/findAllUsefulOrg.do",
	          type: "POST",
	          dataType: "json",
	          success: function(orgs) {
	            if(orgs) {
	              $("#purchaseDeps").append("<option></option>");
	              $.each(orgs, function(i, org) {
	                if(org.name != null && org.name != '') {
	                  $("#purchaseDeps").append("<option value=" + org.id + ">" + org.name + "</option>");
	                }
	              });
	            }
	            $("#purchaseDeps").select2();
	            $("#purchaseDeps").select2("val", "${purCon.purchaseDepName}");
	          }
	    });
	      
	    $.ajax({
	          contentType: "application/json;charset=UTF-8",
	          url: "${pageContext.request.contextPath }/purchaseContract/findAllUsefulSupplier.do",
	          type: "POST",
	          dataType: "json",
	          success: function(orgs) {
	            if(orgs) {
	              $("#supplierDeps").append("<option></option>");
	              $.each(orgs, function(i, org) {
	                if(org.supplierName != null && org.supplierName != '') {
	                  $("#supplierDeps").append("<option value=" + org.id + ">" + org.supplierName + "</option>");
	                }
	              });
	            }
	            $("#supplierDeps").select2();
	            $("#supplierDeps").select2("val", "${purCon.supplierDepName}");
	          }
	    });
	    
	    /*$.ajax({
	          contentType: "application/json;charset=UTF-8",
	          url: "${pageContext.request.contextPath }/purchaseContract/findAllUsefulPurDep.do",
	          type: "POST",
	          dataType: "json",
	          success: function(orgs) {
	            if(orgs) {
	              $("#bingDeps").append("<option></option>");
	              $.each(orgs, function(i, org) {
	                if(org.depName != null && org.depName != '') {
	                  $("#bingDeps").append("<option value=" + org.id + ">" + org.depName + "</option>");
	                }
	              });
	            }
	            $("#bingDeps").select2();
	            $("#bingDeps").select2("val", "${purCon.bingDepName}");
	          }
	    });*/
	    //$("#sup").val($('#supplierList').combobox('getText'))
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
	 var obj="";
	 function OpenFile(filePath) {
		 var projectId = $("#contractId").val();
			obj = document.getElementById("TANGER_OCX");
			obj.Menubar = true;
			obj.Caption = "( 双击可放大 ! )"
			if(filePath != 0){
				obj.BeginOpenFromURL("${pageContext.request.contextPath}"
				+"/purchaseContract/loadFile.html?filePath="+filePath+"&id="+projectId,true,false, 'word.document');// 异步加载, 服务器文件路径
			} 			
		}
	//BeginOpenFromURL成功回调
	   function OnComplete(type,code,html)
	   {
	     /* var doc=obj.ActiveDocument;
	     var pageSetup=doc.PageSetup;
	     pageSetup.TogglePortrait(); */
		   var data= "合同名称:"+$("#contract_code").val()+"编号:"+$("#contract_codes").val();
		   var doc=obj.ActiveDocument;
		   var doca=doc.Application;
		   var as=doca.Selection;
		   //goto参数，1：不知道，2：不知道，3：页数，4：当前页里面存在的字符串
		   as.GoTo(1,2,as.Information(4),"条形码");
		   obj.Add2DCodePic(1, data, true, 35, 460, 1, 100, 1, true); 
	   }
		
		function exportWord() {
			var obj = document.getElementById("TANGER_OCX");
			// 参数说明
			// 1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
			//obj.SaveToURL("${pageContext.request.contextPath}/open_bidding/saveBidFile.html", "bidFile", "", "bid.doc", "MyFile");
		}
		
		function queryVersion(){
		
			var obj = document.getElementById("TANGER_OCX");
			var v = obj.GetProductVerString();
			obj.ShowTipMessage("当前ntko版本",v);
		}
		
		function saveFile(){
			var projectId = $("#contractId").val();
			var obj = document.getElementById("TANGER_OCX");
			var projectName = $("#contract_code").val();
			//参数说明
			//1.url	2.后台接收的文件的变量	3.可选参数(为空)		4.文件名		5.form表单的ID
			obj.SaveToURL("${pageContext.request.contextPath}/purchaseContract/saveContractFile.html?projectId="+projectId,"ntko", "", projectName+"_合同文件.doc", "MyFile");
			obj.ShowTipMessage("提示","已上传至服务器");
		}
		
		function closeFile(){
			var obj = document.getElementById("TANGER_OCX");
			obj.close();
		}
	 
	 function changeXuqiuDep(){
		 var purchaseDepId = $("#purchaseDeps").select2("val");
		 $.ajax({
	          contentType: "application/json;charset=UTF-8",
	          url: "${pageContext.request.contextPath}/purchaseContract/changeXuqiu.do?id="+purchaseDepId,
	          type: "POST",
	          dataType: "json",
	          success: function(org) {
	        	  $("#purchaseLegal").val(org.legal);
	        	  $("#purchaseAgent").val(org.agent);
	        	  $("#purchaseContact").val(org.contact);
	        	  $("#purchaseContactTelephone").val(org.contactTelephone);
	        	  $("#purchaseContactAddress").val(org.contactAddress);
	        	  $("#purchaseUnitpostCode").val(org.unitPostCode);
	        	  $("#purchasePayDep").val(org.payDep);
	        	  $("#purchaseBank").val(org.bank);
	        	  $("#purchaseBankAccount_string").val(org.bankAccount);
	          }
	    });
	 }
	 
	 function changeSupplierDep(){
		 var supplierId = $("#supplierDeps").select2("val");
		 $.ajax({
	          contentType: "application/json;charset=UTF-8",
	          url: "${pageContext.request.contextPath}/purchaseContract/changeSupplierDep.do?id="+supplierId,
	          type: "POST",
	          dataType: "json",
	          success: function(org) {
	        	  $("#supplierLegal").val(org.legalName);
	        	  $("#supplierContact").val(org.contactName);
	        	  $("#supplierContactTelephone").val(org.contactTelephone);
	        	  $("#supplierContactAddress").val(org.area.name);
	        	  $("#supplierUnitpostCode").val(org.postCode);
	        	  $("#supplierBank").val(org.bankName);
	        	  $("#supplierBankAccount_string").val(org.bankAccount);
	          }
	    });
	 }
	 
	 function createContract(){
		/*  var text = $("#post_attach_show_disFileId").find("a");
			var flag = true;
			if(text.length<=0){
				flag = false;
				layer.alert("请先上传授权书",{offset: ['222px', '390px'], shade:0.01});
			}
			if(flag){ */
			 $("#contractForm").attr("action","${pageContext.request.contextPath}/purchaseContract/createTransFormal.html?ids=${id}");
			 $("#contractForm").submit();
			/* } */
	 }
	 
	 function changePurDep(){
		 var purDepId = $("#bingDeps").select2("val");
		 $.ajax({
	          contentType: "application/json;charset=UTF-8",
	          url: "${pageContext.request.contextPath}/purchaseContract/changePurDep.do?id="+purDepId,
	          type: "POST",
	          dataType: "json",
	          success: function(org) {
	        	  $("#bingContact").val(org.contact);
	        	  $("#bingContactTelephone").val(org.contactTelephone);
	        	  $("#bingContactAddress").val(org.contactAddress);
	        	  $("#bingUnitpostCode").val(org.unitPostCode);
	          }
	    });
	 }
	 
	 /** 判断是否为根节点 */
	    function isRoot(node){
	    	if (node.pId == 0){
	    		return true;
	    	} 
	    	return false;
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
   	 
   	function next(){
   		var ids = "${ids}";
   		window.location.href="${pageContext.request.contextPath}/purchaseContract/createDetailContract.html?ids="+ids;
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
		$("#menuContent").css({left: "81px", top: "79px"}).slideDown("fast");

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
		var sumAll = (sum1+sum2)/10000;
		if(sumAll>sumbudget){
			layer.close(index);
			layer.alert("明细总价不得超过预算",{offset: ['50%', '40%'], shade:0.01});
			return;
		}
		
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
	
	function quxiao(){
	     layer.close(index);
	}
	
	function sum1(){
		var budget = $("#univalent").val()-0;
		var other = $("#purNum").val()-0;
		var sum = budget*other;
		$("#purBudgetSum").val(sum);
	}
	
	var index;
	function openDetail(){
	  /* index =  layer.open({
	    shift: 1, //0-6的动画形式，-1不开启
	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
	    title: ['新增明细','border-bottom:1px solid #e5e5e5'],
	    shade:0.01, //遮罩透明度
		type : 1,
		area : [ '50%', '400px' ], //宽高
		content : $('#openDiv'),
		offset: ['10%', '10%']
	  }); */
		 var trs=$('#trs').children();
		    if(trs.length==0){
		      html=htmlText(1,null);
		      $('#trs').append(html);
		     }else{
		       var tr=trs[trs.length-1];
		       var index=parseInt($($(tr).children()[1]).text());
		       html=htmlText(index+1,null);
		       $(tr).after(html);
		     }
		}
	function htmlText(index,data){
	    var html="";
	    if(data!=null){
	      html += "<tr><td class='tc w30'><input onclick='check()' type='checkbox' name='chkItem' value='' /></td>";
	            html += "<td class='tc w50'>"+index+"</td>";
	            html += "<td class='tc w50'><input type='text' name='proList["+index+"].planNo'  value='"+data.planNo+"' class='w50'/></td>";
	            html += "<td class='tc'><input type='text' name='proList["+index+"].goodsName'  value='"+data.goodsName+"'/></td>";
	            html += "<td class='tc'><input type='text' name='proList["+index+"].brand'  value='"+data.brand+"'/></td>"
	            html += "<td class='tc'><input type='text' name='proList["+index+"].stand'  value='"+data.stand+"' class='w60'/></td>"
	            html += "<td class='tc w80'><input type='text' name='proList["+index+"].item'  value='"+data.item+"' class='w50'/></td>"
	            html += "<td class='tc'><input type='text' name='proList["+index+"].purchaseCount' value='"+data.purchaseCount+"' onchange='change(this,\"1\")'   class='w50'/></td>"
	            html += "<td class='tc'><input type='text' name='proList["+index+"].price' onchange='change(this,\"2\")'   value='"+data.price+"' class='w50'/></td>"
	            html += "<td class='tc'><input type='text' name='proList["+index+"].amount' readonly='readonly' value='"+data.amount+"' class='w50'/></td>"
	            html += "<td class='tc'><input type='text' name='proList["+index+"].deliverDate'  value='"+data.deliverDate+"' class='w100'/></td>"
	            html += "<td class='tc'><input type='text' name='proList["+index+"].memo'  value='"+data.memo+"'/></td>"
	            html += "<td class='tnone'></td></tr>";
	    }else{
	      html += "<tr><td class='tc w30'><input onclick='check()' type='checkbox' name='chkItem' value='' /></td>";
	          html += "<td class='tc w50'>"+index+"</td>";
	          html += "<td class='tc w50'><input type='text' name='proList["+index+"].planNo'  value='' class='w50'/></td>";
	          html += "<td class='tc'><input type='text' name='proList["+index+"].goodsName'  value=''/></td>";
	          html += "<td class='tc'><input type='text' name='proList["+index+"].brand'  value=''/></td>"
	          html += "<td class='tc'><input type='text' name='proList["+index+"].stand'  value='' class='w60'/></td>"
	          html += "<td class='tc w80'><input type='text' name='proList["+index+"].item'  value='' class='w50'/></td>"
	          html += "<td class='tc'><input type='text' name='proList["+index+"].purchaseCount' onchange='change(this,\"1\")'  value='' class='w50'/></td>"
	          html += "<td class='tc'><input type='text' name='proList["+index+"].price' onchange='change(this,\"2\")'   value='' class='w50'/></td>"
	          html += "<td class='tc'><input type='text' name='proList["+index+"].amount' readonly='readonly' value='' class='w50'/></td>"
	          html += "<td class='tc'><input type='text' name='proList["+index+"].deliverDate'  value='' class='w100'/></td>"
	          html += "<td class='tc'><input type='text' name='proList["+index+"].memo'  value=''/></td>"
	          html += "<td class='tnone'></td></tr>";
	    }
	    return html;
	  }
	function change(objInput,index){
	    var count=0;
	    var price=0;
	    if(index=='1'){
	      if($(objInput).val()!=""){
	        count=parseFloat($(objInput).val());
	      }
	      if($(objInput).parent().next().children(":first").val()!=""){
	        price=parseFloat($(objInput).parent().next().children(":first").val());
	      }
	      $(objInput).parent().next().next().children(":first").val((count*price).toFixed(2))
	    }else{
	      if($(objInput).val()!=""){
	         price=parseFloat($(objInput).val());
	        }
	      if($(objInput).parent().prev().children(":first").val()!=""){
	            count=parseFloat($(objInput).parent().prev().children(":first").val());
	          }
	      $(objInput).parent().next().children(":first").val((count*price).toFixed(2))
	    }
	  }
	function staging(){
		/* var text = $("#post_attach_show_disFileId").find("a");
		var flag = true;
		if(text.length<=0){
			flag = false;
			layer.alert("请先上传授权书",{offset: ['222px', '390px'], shade:0.01});
		}
		if(flag){ */
			$("#status").val("0");
			$("#contractForm").attr("action","${pageContext.request.contextPath}/purchaseContract/addzancun.html?ids=${id}");
			$("#contractForm").submit();
		/* } */
	}
	
	var ind = null;
	function protocol(){
		/* var text = $("#post_attach_show_disFileId").find("a");
		var flag = true;
		if(text.length<=0){
			flag = false;
			layer.alert("请先上传授权书",{offset: ['222px', '390px'], shade:0.01});
		}
		if(flag){ */
			ind = layer.open({
				shift: 1, //0-6的动画形式，-1不开启
			    moveType: 1, //拖拽风格，0是默认，1是传统拖动
			    title: ['生成草案信息','border-bottom:1px solid #e5e5e5'],
			    shade:0.01, //遮罩透明度
				type : 1,
				skin : 'layui-layer-rim', //加上边框
				area : [ '40%', '250px' ], //宽高
				content : $('#numberWin'),
				offset: ['30%', '25%']
			});
		/* } */
	}
	
	function save(){
		var draftGitAt = $("#draftGitAt").val();
		var draftReviewedAt = $("#draftReviewedAt").val();
		$.ajax({
			url:"${pageContext.request.contextPath}/purchaseContract/addDraftGit.html",
			type:"post",
			dataType:"json",
			data:{"draftGitAt":draftGitAt,"draftReviewedAt":draftReviewedAt},
			success:function(data){
				if(data==1){
					$("#status").val("1");
					var draftGitAt = $("#draftGitAt").val();
					var draftReviewedAt = $("#draftReviewedAt").val();
					$("#dga").val(draftGitAt);
					$("#dra").val(draftReviewedAt);
					$("#contractForm").submit();
				}else{
					var obj = new Function("return" + data)();
					$("#gitTime").text(obj.gitAt);
					$("#reviewTime").text(obj.reviewAt);
					$("#draftGitAt").val(obj.gitStr);
					$("#draftReviewedAt").val(obj.reviewedStr);
				}
			}
		});
	}
	
	function cancel(){
		layer.close(ind);
	}
	
	function imTemplet(){
		var iframeWin;
        layer.open({
          type: 2, //page层
          area: ['800px', '400px'],
          title: '引用模板',
          closeBtn: 1,
          shade:0.01, //遮罩透明度
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['10%', '20%'],
          shadeClose: false,
          content: '${pageContext.request.contextPath}/resultAnnouncement/getAll.html',
          success: function(layero, index){
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
          }
        });
    }
	
	function printContract(){
		$("#draftrevi").attr("class","list-unstyled mt10");
		layer.open({
		shift: 1, //0-6的动画形式，-1不开启
	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
	    title: ['生成草案信息','border-bottom:1px solid #e5e5e5'],
	    shade:0.01, //遮罩透明度
		type : 1,
		skin : 'layui-layer-rim', //加上边框
		area : [ '35%', '250px' ], //宽高
		content : $('#draftrevi'),
		offset: ['30%', '25%']
	   });
	}
	
	function toprintmodel(){
		var text = $("#post_attach_show_disFileId").find("a").text();
		if(text==null || text==''){
			layer.alert("请先上传附件",{offset: ['222px', '390px'], shade:0.01});
		}else{
			$("#contractForm").attr("action","${pageContext.request.contextPath}/purchaseContract/printContract.html?ids=${id}");
			$("#contractForm").submit();
		}
	}
	
	function abandoned(){
			window.location.href="${pageContext.request.contextPath}/purchaseContract/selectDraftContract.html";
	}
	function getProjectName(id){
		$.ajax({
			url:"${pageContext.request.contextPath}/purchaseContract/getProjectName.do",
			type:"post",
			dataType:"json",
			data:{"code":$("#projectCode").val()},
			success:function(data){
				if(data!=null){
					$("#projectName").val(data.name);
				}
			}
		});
	}
	function down(){
	    window.location.href="${pageContext.request.contextPath}/purchaseContract/downdetail.do";
	  }
	  var indexs;
	    function uploadExcel() {
	      indexs = layer.open({
	        type: 1, //page层
	        area: ['400px', '300px'],
	        title: '导入标的',
	        closeBtn: 1,
	        shade: 0.01, //遮罩透明度
	        moveType: 1, //拖拽风格，0是默认，1是传统拖动
	        shift: 1, //0-6的动画形式，-1不开启
	        offset: ['80px', '400px'],
	        content: $('#file_div'),
	      });
	    }
	    function fileup(){
	         $.ajaxFileUpload ({
	             url: "${pageContext.request.contextPath}/purchaseContract/upload.do",  
	             secureuri: false,  
	             fileElementId: 'fileName', 
	             dataType: 'json',
	             success: function (data) { 
	               if(data!=null&&data!=''){
	                 var html="";
	                 var trs=$('#trs').children();
	                 var tr="";
	                 var index="";
	                 if(trs.length>0){
	                   tr=trs[trs.length-1];
	                   index=parseInt($($(tr).children()[1]).text());
	                 }else{
	                   index=0;
	                 }
	                 for(var i=0;i<data.length;i++){
	                   html+=htmlText(index+i+1,data[i]);
	                 }
	                 if(trs.length==0){
	                    $('#trs').append(html);
	                    }else{
	                      $(tr).after(html);
	                   }
	               }
	             }
	         })
	    }
</script>
<!-- ie中的回调 -->
<script language="JScript" for="TANGER_OCX" event="ondocumentopened(File, Document)">
  /* var activeDeoc=obj.ActiveDocument;
  var pageSetup=activeDeoc.PageSetup;
  pageSetup.TogglePortrait(); */
  var data= "合同名称:"+$("#contract_code").val()+"编号:"+$("#contract_codes").val();
  var doc=obj.ActiveDocument;
  var doca=doc.Application;
  var as=doca.Selection;
  //goto参数，1：不知道，2：不知道，3：页数，4：当前页里面存在的字符串
  as.GoTo(1,2,as.Information(4),"条形码");
  obj.Add2DCodePic(1, data, true, 35, 460, 1, 100, 1, true); 
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
   
 <!-- 页签开始  -->  
 <div class="container content pt0">
 <div class="row magazine-page">
   <div class="col-md-12 tab-v2">
        <div class="padding-top-10">
          <ul class="flow_step">
            <li class="active"><a aria-expanded="true" href="#tab-1" data-toggle="tab" class="">基本信息</a><i></i></li>
            <li class=""><a aria-expanded="false" href="#tab-2" data-toggle="tab" class="">标的信息</a><i></i></li>
			<li class=""><a aria-expanded="false" href="#tab-3" data-toggle="tab" class="">合同文本</a></li>
          </ul>
          <form id="contractForm" action="${pageContext.request.contextPath}/purchaseContract/addPurchaseContract.html?ids=${id}" method="post">
          <div class="tab-content padding-top-20 mt20">
            <div class="tab-pane fade active in" id="tab-1">
	   		<input type="hidden" name="status" value="" id="status"/>
	   		<input type="hidden" name="supplierPurId" value="${purCon.supplierPurId}"/>
	   		<input type="hidden" name="projectId" value="${purCon.projectId}"/>
	   		<input type="hidden" name="isImport" value="${purCon.isImport}">
	   		<input type="hidden" name="supcheckid" value="${supcheckid}">
	   		<input  type="hidden" name="demandSector" value="${purCon.demandSector}" >
	   		<input type="hidden" name="manualType" value="${purCon.manualType}"/>
	   		<%-- <input type="hidden" name="manual" value="${manual}"/> --%>
	   		<input type="hidden" id="dga" name="dga" value=""/>
	   		<input type="hidden" id="dra" name="dra" value="">
	   		<h2 class="f16 count_flow mt0"><i>01</i>基本信息</h2>
	   		<ul class="list-unstyled ul_list">
	   			<input type="hidden" class="contract_id" name="contract_id">
			     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>合同名称：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <input class=" contract_code" id="contract_code" value="${purCon.name}" name="name" type="text">
			        <div class="cue">${ERR_name}</div>
			       </div>
				 </li>
	    		 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>合同编号：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<input class=" contract_name" id="contract_codes" name="code" value="${purCon.code}" type="text">
			        	<div class="cue">${ERR_code}</div>
	       			</div>
				 </li>
				  <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>项目编号：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<%-- <c:if test="${purCon.manualType==null}">
				        	<c:if test="${manual!=null}">
				        		<input class=" contract_name" name="projectCode" value="${purCon.projectCode}" type="text" onblur="getProjectName('projectCode');">
				        	</c:if>
				        	<c:if test="${manual==null}">
				        	   <input class=" contract_name" name="projectCode" value="${purCon.projectCode}" type="text" >
				               <input type="hidden" name="projectName" value="${purCon.projectName}"/>
			        		</c:if>
			        	</c:if> --%>
			        	<c:if test="${purCon.manualType==1}">
			        	<input class=" contract_name" name="projectCode" value="${purCon.projectCode}" type="text" onblur="getProjectName('projectCode');">
			        	</c:if>
			        	<c:if test="${purCon.manualType==0}">
			        	    <input class=" contract_name" name="projectCode" value="${purCon.projectCode}" type="text" >
			                <input type="hidden" name="projectName" value="${purCon.projectName}"/>
			        	</c:if>
			        	<div class="cue">${ERR_proCode}</div>
	       			</div>
				 </li>
				 <%-- <c:if test="${purCon.manualType==0}">
					 <c:if test="${manual!=null}">
					 <li class="col-md-3 col-sm-6 col-xs-12">
					   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>项目名称：</span>
				        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
				        	<input class=" contract_name" name="projectName" value="${purCon.projectName}" type="text">
				        	<div class="cue">${ERR_projectName}</div>
		       			</div>
					 </li>
					 </c:if>
				 </c:if> --%>
				 <c:if test="${purCon.manualType==1}">
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>项目名称：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<input class=" contract_name" name="projectName" value="${purCon.projectName}" type="text">
			        	<div class="cue">${ERR_projectName}</div>
	       			</div>
				 </li>
				 </c:if>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>合同金额(万元)：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<input class=" contract_name" name="money_string" value="${purCon.money_string}" type="text">
			        	<div class="cue">${ERR_money}</div>
	       			</div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>计划任务文号：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<input class=" contract_name" name="documentNumber" value="${purCon.documentNumber}" type="text">
			        	<div class="cue">${ERR_documentNumber}</div>
	       			</div>
				 </li>
				 <%-- <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>采购机构资格证号：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<input class=" contract_name" name="quaCode" value="${purCon.quaCode}" type="text">
			        	<div class="cue">${ERR_quaCode}</div>
	       			</div>
				 </li> --%>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>预算(万元)：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<input class=" contract_name" id="budget" name="budget_string" value="${purCon.budget_string}" type="text">
			        	<div class="cue">${ERR_budget}</div>
	       			</div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">项级预算科目：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<input class=" contract_name" name="budgetSubjectItem" value="${purCon.budgetSubjectItem}" type="text">
			        	<div class="cue">${ERR_budgetSubjectItem}</div>
	       			</div>
				 </li>
				 <%--<li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>需求部门：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<input class=" contract_name" name="demandSector" value="${purCon.demandSector}" type="text">
			        	<div class="cue">${ERR_demandSector}</div>
	       			</div>
				 </li>
				 --%><li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>合同类型：</span>
				     <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
			        	<select name="contractType" id="contractType" class=" contract_name">
			        	
			        		<option value="0" <c:if test="purCon.contractType==0">selected="selected"</c:if> >正常采购合同</option>
			        		<option value="1" <c:if test="purCon.contractType==1">selected="selected"</c:if>>以厂代储合同</option>
			        		<option value="2" <c:if test="purCon.contractType==2">selected="selected"</c:if>>合同储备合同</option>
			        	</select>
			        	<div class="cue">${ERR_contractType}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>采购方式：</span>
				     <div class="select_common col-sm-12 col-xs-12 col-md-12 p0">
			        	<select name="purchaseType" id="purchaseType"  class="contract_name">
			        		<c:forEach items="${kinds}" var="kind">
			        			<option value="${kind.id}" <c:if test="purCon.purchaseType==kind.id">selected="selected"</c:if>>${kind.name}</option>
			        		</c:forEach>
			        	</select>
			        	<div class="cue">${ERR_purchaseType}</div>
			        </div>
			 	</li>
			 	<li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><!-- <div class="red star_red">*</div> -->授权书：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        <u:upload id="post_attach_up" businessId="${attachuuid}" sysKey="${bookattachsysKey}" typeId="${bookattachtypeId}" multiple="true" auto="true" />
					<u:show showId="post_attach_show" businessId="${attachuuid}" sysKey="${bookattachsysKey}" typeId="${bookattachtypeId}"/>
	       			</div>
				 </li>
				 <div class="clear"></div>
			 </ul>
			 <div class="clear"></div>
	   		<h2 class="f16 count_flow mt40"><i>02</i>甲方信息</h2>
			 <ul class="list-unstyled ul_list">
	    		 <li class="col-md-3 col-sm-6 col-xs-12 pl15">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>甲方单位：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<!-- <select id="purchaseDeps" name="purchaseDepName" class="select col-md-12 col-sm-12 col-xs-12 p0" onchange="changeXuqiuDep()">
                		</select> -->
                		<input class="supplier_ids" readonly="readonly" name="purchaseDepNames" value="${purchaseDep.depName}" type="text">
                    <input type="hidden" name="purchaseDepName" value="${purCon.purchaseDepName}" >
			        	<!-- <input class=" supplier_id" name="purchaseDepName" value="${project.orgnization.name}" type="text"> -->
			        	<div class="cue">${ERR_purchaseDepName}</div>
	       			</div>
				 </li>
			     <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><!-- <div class="red star_red">*</div> -->甲方法人：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <input class=" supplier_name" name="purchaseLegal" value="${purCon.purchaseLegal}" type="text">
			        <div class="cue">${ERR_purchaseLegal}</div>
			       </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><!-- <div class="red star_red">*</div> -->甲方委托代理人：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <input class=" supplier_name" name="purchaseAgent" value="${purCon.purchaseAgent}" type="text">
			        <div class="cue">${ERR_purchaseAgent}</div>
			       </div>
				 </li>
	    		 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>甲方联系人：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" name="purchaseContact" value="${purCon.purchaseContact}" type="text">
			         <div class="cue">${ERR_purchaseContact}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>甲方联系电话：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="purchaseContactTelephone" maxlength="50" name="purchaseContactTelephone" value="${purCon.purchaseContactTelephone}" type="text">
			         <div class="cue">${ERR_purchaseContactTelephone}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12"><div class="red star_red">*</div>甲方通讯地址：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="purchaseContactAddress" name="purchaseContactAddress" value="${purCon.purchaseContactAddress}" type="text">
			         <div class="cue">${ERR_purchaseContactAddress}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>甲方邮政编码：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" name="purchaseUnitpostCode" value="${purCon.purchaseUnitpostCode}" type="text">
			         <div class="cue">${ERR_purchaseUnitpostCode}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><!-- <div class="red star_red">*</div> -->甲方付款单位：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" name="purchasePayDep" value="${purCon.purchasePayDep}" type="text">
			         <div class="cue">${ERR_purchasePayDep}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>甲方开户银行：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" name="purchaseBank" value="${purCon.purchaseBank}" type="text">
			         <div class="cue">${ERR_purchaseBank}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>甲方银行账号：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" name="purchaseBankAccount_string" maxlength="30" value="${purCon.purchaseBankAccount_string}" type="text">
			         <div class="cue">${ERR_purchaseBankAccount}</div>
			        </div>
				 </li>
				 <div class="clear"></div>
			 </ul>
	      <div class="clear"></div>
	   		<h2 class="f16 count_flow mt40"><i>03</i>乙方信息</h2>
			 <ul class="list-unstyled ul_list">
				 <%-- <li class="col-md-3 col-sm-6 col-xs-12 pl15">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>乙方单位：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<select id="supplierDeps" name="supplierDepName" class="select col-md-12 col-sm-12 col-xs-12 p0" onchange="changeSupplierDep()">
                		</select>
			        	<!-- <input class=" supplier_id" name="supplierDepName" type="text" value="${project.dealSupplier.supplierName}"> -->
			        	<div class="cue">${ERR_supplierDepName}</div>
	       			</div>
				 </li> --%>
				 <li class="col-md-3 col-sm-6 col-xs-12 pl15">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>乙方单位：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	    <input class="easyui-combobox" name="supplierDepName" id="supplierList" data-options="valueField:'id',textField:'supplierName',panelHeight:'auto',panelMaxHeight:200,panelMinHeight:100"  style="width: 100%;height: 29px"/>  
			        	    <input type="hidden" id="sup" />
			        	<div class="cue">${ERR_supplierDepName}</div>
	       			</div>
				 </li> 
				 <script>
				 var num=0;
				    $('#supplierList').combobox({  
				        prompt:'',  
				        required:false,  
				        url: "${pageContext.request.contextPath }/purchaseContract/findAllUsefulSupplier.do",  
				        editable:true,  
				        hasDownArrow:true,
				        value:"${purCon.supplierDepName}",
				        filter: function(L, row){  
				            var opts = $(this).combobox('options');  
				            return row[opts.textField].indexOf(L) == 0;  
				        },
				        onSelect: function (org) {
				        	if(num!=0&&num!=1){
				        		$("#sup").val(org.supplierName);
				        	 $("#supplierLegal").val(org.legalName);
				        	 $("#supplierContact").val(org.contactName);
				        	 $("#supplierContactTelephone").val(org.contactTelephone);
				        	 $("#supplierContactAddress").val(org.area.name);
				        	 $("#supplierUnitpostCode").val(org.postCode);
				        	 $("#supplierBank").val(org.bankName);
				        	 $("#supplierBankAccount_string").val(org.bankAccount);
				        	}
				        	num++
				        },
				        onLoadSuccess:function(){
				        	$('#supplierList').next('.combo').find('input').blur(function (){
				        		if($("#sup").val()!=$(this).val()){
				        			$("#sup").val("");
                                  $("input[name='supplierDepName']").val($(this).val())
                                  $("#supplierLegal").val("");
					    				        	  $("#supplierContact").val("");
					    				        	  $("#supplierContactTelephone").val("");
					    				        	  $("#supplierContactAddress").val("");
					    				        	  $("#supplierUnitpostCode").val("");
					    				        	  $("#supplierBank").val("");
					    				        	  $("#supplierBankAccount_string").val("");
				        		}
				        		
				        	 });
				        	},
				    });  
				 </script>
			     <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><!-- <div class="red star_red">*</div> -->乙方法人：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <input class=" supplier_name" id="supplierLegal" name="supplierLegal" type="text" value="${purCon.supplierLegal}">
			        <div class="cue">${ERR_supplierLegal}</div>
			       </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><!-- <div class="red star_red">*</div> -->乙方委托代理人：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <input class=" supplier_name" name="supplierAgent" value="${purCon.supplierAgent}" type="text">
			        <div class="cue">${ERR_supplierAgent}</div>
			       </div>
				 </li>
	    		 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>乙方联系人：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="supplierContact" name="supplierContact" value="${purCon.supplierContact}" type="text">
			         <div class="cue">${ERR_supplierContact}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>乙方联系电话：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="supplierContactTelephone" maxlength="50" name="supplierContactTelephone" value="${purCon.supplierContactTelephone}" type="text">
			         <div class="cue">${ERR_supplierContactTelephone}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>乙方通讯地址：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="supplierContactAddress" name="supplierContactAddress" value="${purCon.supplierContactAddress}" type="text">
			         <div class="cue">${ERR_supplierContactAddress}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>乙方邮政编码：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="supplierUnitpostCode" name="supplierUnitpostCode" value="${purCon.supplierUnitpostCode}" type="text">
			         <div class="cue">${ERR_supplierUnitpostCode}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>乙方开户名称：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="supplierBankName" name="supplierBankName" value="${purCon.supplierBankName}" type="text">
			         <div class="cue">${ERR_supplierBankName}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>乙方开户银行：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="supplierBank" name="supplierBank"  value="${purCon.supplierBank}" type="text">
			         <div class="cue">${ERR_supplierBank}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>乙方银行账号：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="supplierBankAccount_string" name="supplierBankAccount_string" maxlength="30" value="${purCon.supplierBankAccount_string}" type="text">
			         <div class="cue">${ERR_supplierBankAccount}</div>
			        </div>
				 </li>
				 <div class="clear"></div>
			</ul>
			<%--<h2 class="f16 count_flow mt40"><i>04</i>丙方信息</h2>
			 <ul class="list-unstyled ul_list">
				 <li class="col-md-3 col-sm-6 col-xs-12 pl15">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>丙方单位：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0 ">
			        	<select id="bingDeps" name="bingDepName" class="select col-md-12 col-sm-12 col-xs-12 p0" onchange="changePurDep()">
                		</select>
			        	<!--  <input class=" supplier_id" name="bingDepName" type="text" value="${project.purchaseDep.depName}">-->
			        	<div class="cue">${ERR_bingDepName}</div>
	       			</div>
				 </li>
			     <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>丙方联系人：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0">
			        <input class=" supplier_name" id="bingContact" name="bingContact" type="text" value="${purCon.bingContact}">
			        <div class="cue">${ERR_bingContact}</div>
			       </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>丙方联系电话：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="bingContactTelephone" name="bingContactTelephone" value="${purCon.bingContactTelephone}" type="text">
			         <div class="cue">${ERR_bingContactTelephone}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>丙方通讯地址：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="bingContactAddress" name="bingContactAddress" value="${purCon.bingContactAddress}" type="text">
			         <div class="cue">${ERR_bingContactAddress}</div>
			        </div>
				 </li>
				 <li class="col-md-3 col-sm-6 col-xs-12">
				   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>丙方邮政编码：</span>
			        <div class="input-append input_group col-sm-12 col-xs-12 p0">
			         <input class=" supplier_name" id="bingUnitpostCode" name="bingUnitpostCode" value="${purCon.bingUnitpostCode}" type="text">
			         <div class="cue">${ERR_bingUnitpostCode}</div>
			        </div>
				 </li>
			</ul>
            --%></div>
            <div class="tab-pane fade " id="tab-2">
              <div class="margin-bottom-0  categories over_hideen">
              
                <c:if test="${purCon.manualType==1}">
	                <div class="col-md-12 col-xs-12 col-sm-12 p0">
						<input type="button" class="btn btn-windows add" onclick="openDetail()" value="添加"/>
						<input type="button" class="btn btn-windows delete" onclick="delDetail()" value="删除"/>
						<input type="button" class="btn btn-windows input" onclick="down()" value="下载模板"/>
            <input type="button" class="btn btn-windows input" onclick="uploadExcel()" value="导入"/>
					</div>
                </c:if>
              
				
					<div class="col-md-12 col-sm-12 col-xs-12 p0">
			    	<table id="detailtable" name="proList" class="table table-bordered table-condensed table_input left_table mb0 mt10">
					 <thead >
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
							<th class="info">合计金额(万元)</th>
							<th class="info">交付时间</th>
							<th class="info">备注</th>
						</tr>
					</thead>
					<tbody id="trs">
					<c:forEach items="${requList}" var="reque" varStatus="vs">
						<tr>
				 <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="" /></td> 
				<td class="tc w50">${(vs.index+1)}</td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].planNo" readonly="readonly" value="${reque.planNo}" class="w50 tc"/><input type="hidden" name="proList[${(vs.index)}].detailId" value="${reque.detailId}" /></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].goodsName" readonly="readonly" value="${reque.goodsName} " class="tl"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].brand" readonly="readonly" value="${reque.brand}" class="tl"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].stand" readonly="readonly" value="${reque.stand}" class="w60 tl"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].item" readonly="readonly" value="${reque.item}" class="w50 tc"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].purchaseCount" readonly="readonly" value="${reque.purchaseCount}" class="w50 tc"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].price" readonly="readonly" value="${reque.price}" class="w50 tr"/></td>
				<td class="tr pr20"><input type="text" name="proList[${(vs.index)}].amount" readonly="readonly" value="${reque.amount}" class="w50 tr"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].deliverDate" readonly="readonly" value="${reque.deliverDate}" class="tl w100"/></td>
				<td class="tc"><input type="text" name="proList[${(vs.index)}].memo" readonly="readonly" value="${reque.memo}" class="tl"/></td>
						</tr>
			   		</c:forEach>
			   		</tbody>
				</table>
				</form>
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
	                   <input type="hidden" id="categorieId4" name="categoryId" value=""><!-- onclick=" showMenu(); return false;" -->
					   <input id="citySel4" type="text"  name="goodsName"  value=""  class="title col-md-12" />
					   <div class="cue" id="wzmc"></div>
					   </div>
					  </span>
		            </li>
				    <li class="col-md-6">
		    	      <label class="col-md-12 padding-left-5">
		    	        <div class="red star_red">*</div>编号：</label>
		    	        <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12 p0">
	                      <input id="planNo" name="planNo" type="text" class="col-md-12 p0">
	                      <div class="cue" id="bh"></div>
	                    </div>
		            </li>
				    <li class="col-md-6">
		    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>交付时间</label>
		    	       <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12 p0">
	                   <input id="givetime" name="deliverDate" value="" type="text" class="col-md-12 p0">
	                   <div class="cue" id="jfsj"></div>
	                   </div>
	                  </span>
		            </li>
				    <li class="col-md-6">
		    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>品牌商标</label>
		    	       <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12 p0">
	                    <input id="bra" name="brand" value="" type="text" class="col-md-12 p0">
	                    <div class="cue" id="ppsb"></div>
	                  </div>
		            </li>
				    <li class="col-md-6">
		    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>规格型号</label>
		    	       <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12 p0">
	                   <input id="model" name="stand" value="" type="text" class="col-md-12 p0">
	                   <div class="cue" id="ggxh"></div>
		            </li> 
				    <li class="col-md-3">
		    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>计量单位</label>
	                  <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12 p0">
	                   <input id="unit" name="item" value="" type="text" class="col-md-12 p0">
	                   <div class="cue" id="jldw"></div>
	                  </div>
		            </li>
					<li class="col-md-3">
		    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>数量</label>
	                  <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12 p0">
	                   <input id="purNum" name="purchaseCount_string" onblur="sum1()" type="text"class="col-md-12 p0">
	                   <div class="cue" id="sl"></div>
		              </div>
		            </li>
				    <li class="col-md-3">
		    	      <label class="col-md-12 padding-left-5"><div class="red star_red">*</div>单价</label>
	                  <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12 p0">
	                   <input id="univalent" name="price_string" onblur="sum1()" value="" type="text" class="col-md-12 p0">
	                   <div class="cue" id="dj"></div>
		              </div>
		            </li>
				    <li class="col-md-3">
		    	      <label class="col-md-12 padding-left-5">合计</label>
	                  <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12 p0">
	                   <input id="purBudgetSum" name="amount_string" value="" readonly="readonly" type="text" class="col-md-12 p0">
		              </div>
		            </li> 
				    <li class="col-md-12">
		    	      <label class="col-md-12 padding-left-5">备注</label>
	                  <div class="col-sm-12 col-xs-12 p0 col-md-12">
	                    <textarea id="remarks" name="memo" class="col-md-12 h80 p0" rows="3" cols="1"></textarea>
		              </div>
		            </li> 
		            <div class="clear"></div>
				  </ul>
				</div>
              <div class="tc  col-md-12 mb20">
                <input class="btn"  id = "inputb" name="addr"  type="button" onclick="bynSub();" value="确定"> 
				<input class="btn"  id = "inputa" name="addr"  type="button" onclick="quxiao();" value="取消"> 
              </div>
			</div>
			    </div>
              </div>
            </div>
            <div class="tab-pane fade h800" id="tab-3">
              <div class="mt10 mb10">
	      	 <!-- <input type="button" class="btn btn-windows cancel" onclick="delMark()" value="删除标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="searchMark()" value="查看标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="mark()" value="标记"></input>
	      	 <input type="button" class="btn btn-windows cancel" onclick="closeFile()" value="关闭当前文档"></input> -->
	      	 <!-- <input type="button" class="btn btn-windows " onclick="queryVersion()" value="版本查询"></input> -->
	     	<!-- <input type="button" class="btn btn-windows input" onclick="inputTemplete()" value="模板导入"></input> -->
	        <input type="button" class="btn btn-windows save" onclick="saveFile()" value="存至服务器"></input>
	    	</div>
            <form id="MyFile" method="post">
				<input type="hidden" id="ope" value="${ope }">
    			<input type="hidden" id="contractId" value="${id }">
    			<input type="hidden" id="contractName" value="">
				<script type="text/javascript" src="${pageContext.request.contextPath}/public/ntko/ntkoofficecontrol.js"></script>
			</form>
          </div> 
		</div> 
		</form>
		<div  class="col-md-12 tc mt20">
   			<input type="button" class="btn btn-windows save mb20" onclick="staging()" value="暂存"/>
   			<input type="button" class="btn btn-windows save mb20" onclick="protocol()" value="生成草案"/>
   			  <input type="button" class="btn btn-windows save mb20" onclick="createContract()" value="生成正式合同"/>
   			<%--<input type="button" class="btn btn-windows save mb20" onclick="printContract()" value="打印"/>
   			--%><input type="button" class="btn btn-windows cancel mb20" onclick="abandoned()" value="取消">
  		</div>
  		
  		<ul class="list-unstyled mt10 dnone" id="numberWin">
	  		    <li class="col-md-6 col-sm-12 col-xs-12 pl15">
				   <span class="col-md-12 col-sm-12 col-xs-12"><div class="red star_red">*</div>草案合同上报时间：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12">
				     <input type="text" name="draftGitAt" id="draftGitAt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w220"/>
				     <div id='gitTime' class="cue"></div>
				   </div>
				</li>
				<li class="col-md-6 col-sm-12 col-xs-12">
				   <span class="col-md-12 col-sm-12 col-xs-12"><div class="red star_red">*</div>草案合同批复时间：</span>
				   <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12">
				     <input type="text" name="draftReviewedAt" id="draftReviewedAt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w220"/>
				     <div id='reviewTime' class="cue"></div>
				   </div>
				</li>
				<li class="tc col-md-12 col-sm-12 col-xs-12 mt20">
				 <input type="button" class="btn" onclick="save()" value="生成"/>
				 <input type="button" class="btn" onclick="cancel()" value="取消"/>
				</li>
		 </ul>
  		<ul class="list-unstyled mt10 dis_none" id="draftrevi">
		 		<li class="tc col-md-12 col-sm-12 col-xs-12 mt20">
					<span class="col-md-4 col-sm-6 col-xs-6 p0 tc mt5">
						<div class="red star_red">*</div>草案批复意见上传：
					</span>
			    	<div class="col-md-8 col-sm-6 col-xs-6 p0">
			        <u:upload id="post_attach_up" businessId="${attachuuid}" sysKey="${attachsysKey}" typeId="${attachtypeId}" auto="true" />
					<u:show showId="post_attach_show" businessId="${attachuuid}" sysKey="${attachsysKey}" typeId="${attachtypeId}"/>
					</div>
				</li>
				<li class="tc col-md-12 col-sm-12 col-xs-12 mt20">
				 <input type="button" class="btn" onclick="toprintmodel()" value="确定"/>
				</li>
			</ul>
     </div>
  </div>
</div>
</div>
<!-- 页签结束 -->
<div  class=" clear margin-top-30" id="file_div"  style="display:none;" >
   <div class="col-md-12 col-sm-12 col-xs-12">
      <input type="file" id="fileName" class="input_group" name="file" >
    </div>
    <div class="col-md-12 col-sm-12 col-xs-12 mt20 tc">
       <input type="button" class="btn input" onclick="fileup()"   value="导入" />
     </div>
 </div>

</body>
</html>
