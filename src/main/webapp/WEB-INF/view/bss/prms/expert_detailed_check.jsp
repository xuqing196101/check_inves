<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'view.jsp' starting page</title>
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
  	$(function() {
  		//获取查看或操作权限
       	var isOperate = $('#isOperate', window.parent.document).val();
       	if(isOperate == 0) {
       		//只具有查看权限，隐藏操作按钮
			$(":button").each(function(){ 
				$(this).hide();
            }); 
		}
    })
  	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItemExp");
		 var checkAll = document.getElementById("checkAllExp");
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
		 var checklist = document.getElementsByName ("chkItemExp");
		 var checkAll = document.getElementById("checkAllExp");
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
  
  	//返回
	function goBack(url){
		$("#tab-6").load(url);
	}
  	//查看专家对所有供应商的初审明细
	function viewByExpert(obj, packageId, projectId, flowDefineId){
		var expertId = $('input:radio[name="firstAuditByExpert"]:checked').val();
		if (typeof(expertId) == "undefined") {
			 layer.msg("请选择一名评委的初审记录",{offset: "100px", shade:0.01});
		}
		if (typeof(expertId) != "undefined") {
			window.location.href="${pageContext.request.contextPath}/packageExpert/viewByExpert.html?id="+expertId+"&packageId="+packageId+"&projectId="+projectId+"&flowDefineId="+flowDefineId;
		}
	}
	
	//查看所有专家对供应商的初审明细
	function viewBySupplier(obj, packageId, projectId, flowDefineId){
		var supplierId = $('input:radio[name="firstAuditBySupplier"]:checked').val();
		if (typeof(supplierId) == "undefined") {
			 layer.msg("请选择一名供应商的初审记录",{offset: "100px", shade:0.01});
		}
		if (typeof(supplierId) != "undefined") {
			window.location.href="${pageContext.request.contextPath}/packageExpert/viewBySupplier.html?supplierId="+supplierId+"&packageId="+packageId+"&projectId="+projectId+"&flowDefineId="+flowDefineId;
		}
	}
	
	var y = 150;
	var x = 300;
	function endCheck(projectId,packageId){
	    $.ajax({
			url:"${pageContext.request.contextPath}/packageExpert/isEndCheck.do",
			data:{"packageId":packageId, "projectId":projectId},
			async:false,
			success:function (response) {
				if (response == "ok") {
					layer.confirm('是否确认结束评审?结束后将不能复核!', {
						btn : [ '确定', '取消' ]
					//按钮
					}, function() {
						$.ajax({
							 url:'${pageContext.request.contextPath}/packageExpert/endCheck.do',
							 data:{"packageId":packageId,"projectId":projectId},
							 async:false,
							 success:function(){
								 layer.alert("已结束",{offset: [y, x], shade:0.01});
								 $("#backId").attr("disabled",true);
								 $("#endId").attr("disabled",true);
							 },
							 error: function(){
								 layer.alert("结束失败,请稍后重试!",{offset: [y, x], shade:0.01});
							 }
						 });
					});
				} else {
					layer.alert(response, {
						offset : [ y, x ],
						shade : 0.01
					});
				}
			}
		}); 
	}
	
	//退回复核
	function backCheck(projectId,packageId,flowDefineId){
		var ids =[]; 
		$('input[name="chkItemExp"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要退回复核吗?', {title:'提示',offset: '100px',shade:0.01}, function(index){
				$.ajax({
					url: "${pageContext.request.contextPath}/packageExpert/isBackCheck.do?expertIds="+ids,
					data: {"projectId": projectId, "packageId": packageId},
					dataType:'json',
					success:function(result){
				    	if(!result.success){
	                    	layer.msg(result.msg,{offset: ['100px']});
				    	}else{
				    		$.ajax({
								url: "${pageContext.request.contextPath}/packageExpert/backCheck.do?expertIds="+ids,
								data: {"projectId": projectId, "packageId": packageId},
								dataType:'json',
								success:function(result){
								    	if(!result.success){
					                    	layer.msg(result.msg,{offset: ['100px']});
								    	}else{
								    		layer.close(index);
								    		$("#tab-6").load('${pageContext.request.contextPath}/packageExpert/checkAuditView.html?packageId='+packageId+'&projectId='+projectId);
								    	}
					                },
					            error: function(result){
					                layer.msg("退回复核失败",{offset: ['100px']});
					            }
							});
	                	}
	                },
		            error: function(result){
		                layer.msg("退回复核失败",{offset: ['100px']});
		            }
				});
			});
		}else{
			layer.alert("请选择专家",{offset: '100px', shade:0.01});
		}
	}
	
	
	function openPrint(projectId,packageId){
		window.open("${pageContext.request.contextPath}/packageExpert/expertConsult.html?packageId="+packageId+"&projectId="+projectId+"&flag=1", "评审汇总表");
	}
	
	function openDetailPrint(projectId,packageId){
		window.open("${pageContext.request.contextPath}/packageExpert/openAllPrint.html?packageId="+packageId+"&projectId="+projectId+"&auditType=1", "打印所有评审数据");
	}
	
	function expertConsult(projectId,packageId){
		window.open("${pageContext.request.contextPath}/packageExpert/expertConsult.html?packageId="+packageId+"&projectId="+projectId, "专家咨询委员会");
	}
	
	 //定义callback方法，用于回调
    function callback() {
		refreshWin();
	}
		
	//刷新当前页面
	function refreshWin() {
		var projectId = $("#projectId").val();
		var packageId = $("#packageId").val();
		//调用刷新页面的方法，此处RefreshSocket为刷新页面对应的方法，也就是说，如果页面有个刷新按钮，则，点击按钮提交的类名就是此处的类名
		var url = '${pageContext.request.contextPath}/packageExpert/checkAuditView.html?packageId='+packageId+'&projectId='+projectId;
		$("#tab-6").load(url);
	}
	function refur(packageId,projectId){
		layer.msg("刷新成功",{offset: ['100px']});
		$("#tab-6").load('${pageContext.request.contextPath}/packageExpert/checkAuditView.html?packageId='+packageId+'&projectId='+projectId);
	}
  </script>
  <body>
	    <h2 class="list_title">${pack.name}经济技术评审管理</h2>
	    <div class="mb5 fr">
	    	<c:if test="${isEnd != 1}">
			    <button class="btn" id="endId" onclick="endCheck('${projectId}','${pack.id}')" type="button">结束评审</button>
			    <button <c:if test="${isSubmitCheck == 2}">disabled="disabled"</c:if> class="btn" id="backId" onclick="backCheck('${projectId}','${pack.id}')" type="button">复核评审</button>
	    		<%-- <button <c:if test="${isSubmitCheck == 2}">disabled="disabled"</c:if> class="btn" onclick="expertConsult('${projectId}','${pack.id}')" type="button">专家咨询委员会</button> --%>
	    	</c:if>
	    	<c:if test="${isEnd == 1}">
			      <button disabled="disabled" class="btn" id="endId" type="button">结束评审</button>
				  <button disabled="disabled" class="btn" id="backId" onclick="backScore()" type="button">复核评审</button>
				  <%-- <button disabled="disabled" class="btn" onclick="expertConsult('${projectId}','${pack.id}')" type="button">专家咨询委员会</button> --%>
	    	</c:if> 
		    <%-- <button class="btn" onclick="openPrint('${projectId}','${pack.id}')" type="button">评审汇总表</button> --%>
		    <button class="btn" onclick="openDetailPrint('${projectId}','${pack.id}')" type="button">打印评审数据</button>
		    <button class="btn" onclick="refur('${pack.id}','${projectId}')" type="button">刷新</button>
	   	</div>
	   	<input type="hidden" id="packageId" value="${pack.id}">
	   	<input type="hidden" id="projectId" value="${projectId}">
	   	<input type="hidden" id="flowDefineId" value="${flowDefineId}">
	   	<div class="over_scroll col-md-12 col-xs-12 col-sm-12 p0 m0">
	  	<table id="tabId" class="table table-bordered table-condensed table-hover table-striped  p0 space_nowrap">
 		  <thead>
		      <tr>
		      	<th class="info w30"><input id="checkAllExp" type="checkbox" onclick="selectAll()" /></th>
		        <th class="info">评委/供应商</th>
		        <c:forEach items="${supplierList}" var="supplier" varStatus="vs">
		        	<th class="info">${supplier.suppliers.supplierName }</th>
		        </c:forEach>
		        <%-- <th class="tc w30"><button class="btn" onclick="viewByExpert(this,'${packageId}','${projectId}','${flowDefineId}');" type="button">查看明细</button></th> --%>
		      </tr>
	      </thead>
	      <tbody id="content">
	      <c:forEach items="${packExpertExtList}" var="ext" varStatus="vs">
		       <tr>
		       	<td class="tc"><input onclick="check()" type="checkbox" name="chkItemExp" value="${ext.expert.id}" /></td>
		        <td class="tc"><a href="${pageContext.request.contextPath}/packageExpert/printView.html?projectId=${projectId}&packageId=${pack.id}&expertId=${ext.expert.id}&auditType=1" target="view_window" title="评审明细">${ext.expert.relName}</a></td>
		        <c:forEach items="${supplierList}" var="supplier" varStatus="vs">
		        	<td class="tc">
		        	  	${ext.isPass}
		        	</td>
	            </c:forEach>
		      </tr>
      	 </c:forEach>
      	 <tr>
      	 	<th class='info'colspan='2'>评审结果</th>
      	 	<c:forEach items="${supplierList}" var="supplier" varStatus="vs">
      	 		<td class="tc">
      	 			<c:if test="${(isSubmitCheck == 2 || isSubmitCheck == 1) && supplier.economicScore == 0 && supplier.technologyScore == 0}"><div class='red'>不合格</div></c:if>
      	 			<c:if test="${(isSubmitCheck == 2 || isSubmitCheck == 1) && supplier.economicScore == 100 && supplier.technologyScore == 100}">合格</c:if>
      	 			<c:if test="${isSubmitCheck == 0}">暂无</c:if>
      	 		</td>
      	 	</c:forEach>
      	 </tr>
	     </tbody>
  		</table>
  		</div>
  		<div class="clear col-md-12 pl20 mt10 tc">
		    <button class="btn btn-windows back" onclick="goBack('${pageContext.request.contextPath}/packageExpert/toScoreAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}')" type="button">返回</button>
	   	</div>
  </body>
</html>
