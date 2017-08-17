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
  	function goBack(){
  		var projectId = $("#projectId").val();
  		var flowDefineId = $("#flowDefineId").val();
  		$("#tab-5").load("${pageContext.request.contextPath}/packageExpert/toFirstAudit.html?projectId="+projectId+"&flowDefineId="+flowDefineId);
  		//window.location.href="${pageContext.request.contextPath}/packageExpert/toFirstAudit.html?projectId="+projectId+"&flowDefineId="+flowDefineId;
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
	
	/* $(function(){ 
		var html = "<tr><th class='info'colspan='2'>评审结果</th>";
		var tdCount = document.getElementById("tabId").rows.item(0).cells.length;
		for ( var int = 2; int < tdCount; int++) {
			var isPass = 0;
			var notPass = 0;
			var notaudit = 0;
			$('#tabId tr').find('td').each(function(){
				if ($(this).index() == int) { // 假设要获取第一列的值
	                var v = $(this).find("input").val();
	                if (v == 2){
	                	notaudit += 1;
	                }
	                if (v == 1) {
						isPass += 1;
					}
					if(v == 0){
						notPass += 1;
					}
	            }
			});
			if (notaudit > 0) {
				html += "<th class='info'>评审未完成</th>";
			} else if (notPass > isPass) {
				html += "<th class='info'>不合格</th>";
			} else if (isPass > notPass){
				html += "<th class='info'>合格</th>";
			}
		}
		html += "</tr>";
		$("#content").append(html);
	}); */
	  var pack_id="";
  	function bynSub(packageId,projectId,flowDefineId){
  		pack_id=packageId;
  		$.ajax({
			url:"${pageContext.request.contextPath}/termination/flowDefineId.do",
			data:{"currFlowDefineId":flowDefineId},
			type:"post",
			dataType:"json",
	   	success:function(data){
	   		$("#openDiv_checkFlw").empty();
	   		var html='<div class="tl">';
	   		if(data!=null){
	   			html+='<div class=" mt10 ml40"><input type="radio" name="flw" value="XMLX"/>项目立项</div>';
	   			html+='<div class=" mt10 ml40"><input type="radio" name="flw" value="XMFB"/>项目分包</div>';
	   			for(var i=0;i<data.length;i++){
	   				if(i>=2){
	   					html+='<div class=" mt10 ml40"><input type="radio" name="flw" value="'+data[i].id+'"/>'+data[i].name+'</div>';
	   				}
	   			}
	   		}
	   		html+='</div>';
	   		$("#openDiv_checkFlw").append(html);
	   		openDetailFlw();
	   	 }
  		});
  	}
	var indexFlw;
  	function openDetailFlw(){
  		indexFlw =  layer.open({
  	    shift: 1, //0-6的动画形式，-1不开启
  	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
  	    title: ['终止环节','border-bottom:1px solid #e5e5e5'],
  	    shade:0.01, //遮罩透明度
	  		type : 1,
	  		area : [ '20%', '400px'  ], //宽高
	  		content : $('#openDivFlw'),
	  	  });
      }
  	function cancelFlw(){
		  layer.close(indexFlw);
	  }
  	function bynSubFlw(){
  		var val=[];
  		$('input[type="radio"]:checked').each(function(){ 
  			val.push($(this).val()); 
  		});
  		if(val.length==0){
  			layer.alert("请选择终止流程");
  			return false;
  		}
  		$.ajax({
			url:"${pageContext.request.contextPath}/termination/ter_package.do",
			data:{"packagesId":pack_id,"projectId":'${projectId}',"currFlowDefineId":val.join(','),"oldCurrFlowDefineId":'${flowDefineId}'},
			type:"post",
			dataType:"json",
	   	success:function(data){
	   		if(data=="ok"){
	   			layer.alert("终止成功");
	   			layer.close(indexFlw);
	   			}
	   		}
  		});
  	}
	//结束符合性审查
	function isFirstGather(projectId, packageId,flowDefineId){
		var count=0;
		var supplierNumber='${supplierNumber}';
		$('#tabId tr:last td').each(function(){ 
  			var s=$(this).find('div').text();
  			if(s=='合格'){
  				count++;
  			}
  		});
		if(count<supplierNumber){
			layer.open({
		  	    shift: 1, //0-6的动画形式，-1不开启
		  	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
		  	    title: ['操作','border-bottom:1px solid #e5e5e5'],
		  	    shade:0.01, //遮罩透明度
			  		type : 1,
			  		area : [ '40%', '200px'  ], //宽高
			  		content : $('#openDivjc'),
			});
		}else{
			$.ajax({
				url: "${pageContext.request.contextPath}/packageExpert/isFirstGather.do",
				data: {"projectId": projectId, "packageId": packageId, "flowDefineId":flowDefineId},
				dataType:'json',
				success:function(result){
				    	if(!result.success){
	                    	layer.msg(result.msg,{offset: ['150px']});
				    	}else{
				    		layer.msg("符合性检查结束",{offset: ['150px']});
				    		$("#tab-5").load("${pageContext.request.contextPath}/packageExpert/toFirstAudit.html?projectId="+projectId+"&flowDefineId="+flowDefineId);
				    	}
	                },
	            error: function(result){
	                layer.msg("符合性检查结束失败",{offset: ['222px']});
	            }
			});
		}
	}
	//转为竞争性谈判
	function updateJZ(projectId, packageId,flowDefineId){
		$.ajax({
			url:"${pageContext.request.contextPath}/open_bidding/transformationJZXTP.do",
			data:{"packageIds":packageId,
				  "projectId":projectId,
				  "currentFlowDefineId":flowDefineId
				 },
			type:"post",
			dataType:"json",
	   		success:function(data){
	   		//layer.close(index);
	   		//$("#alertId").val(data.status);
	   		if(data.status=="ok"){
	   			layer.msg('转为竞争性谈判');
	   			$.ajax({
	   				url: "${pageContext.request.contextPath}/packageExpert/isFirstGather.do",
	   				data: {"projectId": projectId, "packageId": packageId, "flowDefineId":flowDefineId},
	   				dataType:'json',
	   				success:function(result){
	   				    	if(!result.success){
	   	                    	layer.msg(result.msg,{offset: ['150px']});
	   				    	}else{
	   				    		layer.msg("符合性检查结束",{offset: ['150px']});
	   				    		$("#tab-5").load("${pageContext.request.contextPath}/packageExpert/toFirstAudit.html?projectId="+projectId+"&flowDefineId="+flowDefineId);
	   				    	}
	   	                },
	   	            error: function(result){
	   	                layer.msg("符合性检查结束失败",{offset: ['222px']});
	   	            }
	   			});
	   			} else if (data.status=="failed"){
	   				layer.msg("不能转为竞争性谈判");
	   			}
	   		}
  		});
	}
	//继续实施
	function continueSs(projectId, packageId,flowDefineId){
		$.ajax({
				url: "${pageContext.request.contextPath}/packageExpert/isFirstGather.do",
				data: {"projectId": projectId, "packageId": packageId, "flowDefineId":flowDefineId},
				dataType:'json',
				success:function(result){
				    	if(!result.success){
	                    	layer.msg(result.msg,{offset: ['150px']});
				    	}else{
				    		layer.msg("符合性检查结束",{offset: ['150px']});
				    		$("#tab-5").load("${pageContext.request.contextPath}/packageExpert/toFirstAudit.html?projectId="+projectId+"&flowDefineId="+flowDefineId);
				    	}
	                },
	            error: function(result){
	                layer.msg("符合性检查结束失败",{offset: ['222px']});
	            }
			});
	}
	//退回复核
	function sendBack(projectId,packageId,flowDefineId){
		var ids =[]; 
		$('input[name="chkItemExp"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要退回复核吗?', {title:'提示',offset: '100px',shade:0.01}, function(index){
				$.ajax({
					url: "${pageContext.request.contextPath}/packageExpert/isSendBack.do?expertIds="+ids,
					data: {"projectId": projectId, "packageId": packageId},
					dataType:'json',
					success:function(result){
					    	if(!result.success){
		                    	layer.msg(result.msg,{offset: ['100px']});
					    	}else{
					    		if (result.msg == '' || result.msg == null) {
									$.ajax({
										url: "${pageContext.request.contextPath}/packageExpert/sendBack.do?expertIds="+ids,
										data: {"projectId": projectId, "packageId": packageId},
										dataType:'json',
										success:function(result){
										    	if(!result.success){
							                    	layer.msg(result.msg,{offset: ['100px']});
										    	}else{
										    		layer.close(index);
										    		$("#tab-5").load("${pageContext.request.contextPath}/packageExpert/toFirstAudit.html?projectId="+projectId+"&flowDefineId="+flowDefineId);
										    	}
							                },
							            error: function(result){
							                layer.msg("退回复核失败",{offset: ['100px']});
							            }
									});
								} else {
						    		layer.confirm(result.msg, {title:'提示',offset: '100px',shade:0.01}, function(index){
							    		$.ajax({
											url: "${pageContext.request.contextPath}/packageExpert/sendBack.do?expertIds="+ids,
											data: {"projectId": projectId, "packageId": packageId},
											dataType:'json',
											success:function(result){
											    	if(!result.success){
								                    	layer.msg(result.msg,{offset: ['100px']});
											    	}else{
											    		layer.close(index);
											    		$("#tab-5").load("${pageContext.request.contextPath}/packageExpert/toFirstAudit.html?projectId="+projectId+"&flowDefineId="+flowDefineId);
											    	}
								                },
								            error: function(result){
								                layer.msg("退回复核失败",{offset: ['100px']});
								            }
										});
									});
								}
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
	
	function endPrice(projectId, packId, flowDefineId) {
	    /* if ('${pack.isEndPrice == 1}') {
	    	layer.msg("报价已结束",{offset: ['100px']});
	    	return;
	    } */
		$.ajax({
			url: "${pageContext.request.contextPath}/packageExpert/endPrice.do",
			data: {"packageId": packId},
			dataType:'json',
			success:function(result){
				$('#againPrice').attr("disabled",true);
                layer.msg("已结束报价",{offset: ['100px']});
            },
            error: function(result){
                layer.msg("结束报价失败",{offset: ['100px']});
            }
		});
	}
	
	function openPrint(projectId,packageId){
		window.open("${pageContext.request.contextPath}/packageExpert/openPrint.html?packageId="+packageId+"&projectId="+projectId, "打印检查汇总表");
	}
	
	function openDetailPrint(projectId,packageId){
		window.open("${pageContext.request.contextPath}/packageExpert/openAllPrint.html?packageId="+packageId+"&projectId="+projectId, "打印所有检查表");
	}
	
  </script>
  <body>
	    <h2 class="list_title">${pack.name}符合性审查查看</h2>
	    <div class="mb5 fr">
	    	<c:if test="${isEnd != 1}">
			    <button class="btn" onclick="sendBack('${projectId}','${pack.id}','${flowDefineId}')" type="button">复核检查</button>
			    <button class="btn" onclick="isFirstGather('${projectId}','${pack.id}','${flowDefineId}');" type="button">结束符合性检查</button>
			    <c:if test="${purcahseCode == 'JZXTP' || purcahseCode == 'DYLY'}">
				    <button <c:if test="${pack.isEndPrice == '1'}">disabled="disabled"</c:if> id="againPrice" class="btn" onclick="endPrice('${projectId}','${pack.id}','${flowDefineId}');" type="button">结束报价</button>
			    </c:if>
	    	</c:if>
	    	<c:if test="${isEnd == 1}">
			    <button class="btn" disabled="disabled" onclick="sendBack('${projectId}','${pack.id}','${flowDefineId}')" type="button">复核检查</button>
			    <button class="btn" disabled="disabled" onclick="isFirstGather('${projectId}','${pack.id}','${flowDefineId}');" type="button">结束符合性检查</button>
			    <c:if test="${purcahseCode == 'JZXTP' || purcahseCode == 'DYLY'}">
				    <button  disabled="disabled" class="btn" onclick="endPrice('${projectId}','${pack.id}','${flowDefineId}');" type="button">结束报价</button>
			    </c:if>
	    	</c:if>
		    <button class="btn" onclick="openPrint('${projectId}','${pack.id}')" type="button">检查汇总表</button>
		    <button class="btn" onclick="openDetailPrint('${projectId}','${pack.id}')" type="button">打印检查数据</button>
	   	</div>
	   	<input type="hidden" id="projectId" value="${projectId}">
	   	<input type="hidden" id="flowDefineId" value="${flowDefineId}">
	   	<div class="over_scroll col-md-12 col-xs-12 col-sm-12 p0 m0">
		  	<table id="tabId" class="table table-bordered table-condensed table-hover table-striped  p0 m_resize_table_width">
	 		  <thead>
			      <tr>
			      	<th class="info" width="30"><input id="checkAllExp" type="checkbox" onclick="selectAll()" /></th>
			        <th class="info" width="120">评委/供应商</th>
			        <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
			        	<th class="info" width="120">${supplier.suppliers.supplierName }</th>
			        </c:forEach>
			        <%-- <th class="tc w30"><button class="btn" onclick="viewByExpert(this,'${packageId}','${projectId}','${flowDefineId}');" type="button">查看明细</button></th> --%>
			      </tr>
		      </thead>
		      <tbody id="content">
		      <c:forEach items="${packExpertExtList}" var="ext" varStatus="vs">
			       <tr>
			       	<td class="tc"><input onclick="check()" type="checkbox" name="chkItemExp" value="${ext.expert.id}" /></td>
			        <td class="tc"><a href="${pageContext.request.contextPath}/packageExpert/printView.html?projectId=${projectId}&packageId=${pack.id}&expertId=${ext.expert.id}" target="view_window" title="评审明细">${ext.expert.relName}</a></td>
			        <c:forEach items="${supplierList}" var="supplier" varStatus="vs">
			        	<td class="tc">
			        	  <c:forEach items="${supplierExtList}" var="supplierExt">
			        	  	<c:if test="${supplierExt.supplierId eq supplier.suppliers.id && ext.expert.id eq supplierExt.expertId}">
				        	  	<c:if test="${supplierExt.suppIsPass == 0}">
					        	  	<input type="hidden" value="${supplierExt.suppIsPass}">
					        	  	不合格
				        	  	</c:if>
				        	  	<c:if test="${supplierExt.suppIsPass == 1}">
					        	  	<input type="hidden" value="${supplierExt.suppIsPass}">
					        	  	合格
				        	  	</c:if>
			        	  		<c:if test="${supplierExt.suppIsPass == 2}">
					        	  	<input type="hidden" value="${supplierExt.suppIsPass}">
					        	  	未提交
				        	  	</c:if>
			        	  	</c:if>
			        	  </c:forEach>
			        	</td>
		            </c:forEach>
		           <%--  <td class="tc"><input type="radio" name="firstAuditByExpert" value="${ext.expert.id}"></td> --%>
			      </tr>
	      	 </c:forEach>
	      	 <tr>
	      	 	<th class='info'colspan='2'>评审结果</th>
	      	 	<c:forEach items="${supplierList}" var="supplier" varStatus="vs">
	      	 		<td class="tc">
	      	 			<c:if test="${supplier.isFirstPass == 0}"><div class='red'>不合格</div></c:if>
	      	 			<c:if test="${supplier.isFirstPass == 1}"><div>合格</div></c:if>
	      	 			<c:if test="${supplier.isFirstPass == null}"><div>暂无</div></c:if>
	      	 		</td>
	      	 	</c:forEach>
	      	 </tr>
		     </tbody>
	      	 	  <%-- <tr>
	      	 		<td class="tc"><button class="btn" onclick="viewBySupplier(this,'${packageId}','${projectId}','${flowDefineId}')" type="button">查看明细</button></td>
	      	 		 <c:forEach items="${supplierList}" var="supplier" varStatus="vs">
				       	<td class="tc w30"><input type="radio" name="firstAuditBySupplier" value="${supplier.suppliers.supplierName}"/></td>
				     </c:forEach>
				    <td></td>
		      	  </tr> --%>
	  		</table>
	  	</div>
  		<div class="clear col-md-12 pl20 mt10 tc">
		    <button class="btn btn-windows back" onclick="goBack();" type="button">返回</button>
	   	</div>
	   	<div id="openDivFlw" class="dnone layui-layer-wrap">
        <div class="drop_window tc" id="openDiv_checkFlw">
         
        </div>
        <div class="tc  col-md-12 mt350">
          <input class="btn"  id = "inputbFLw" name="addr"  type="button" onclick="bynSubFlw();" value="确定"> 
	      <input class="btn"  id = "inputaFlw" name="addr"  type="button" onclick="cancelFlw();" value="取消"> 
        </div>
    </div>
	  <div id="openDivjc" class="dnone layui-layer-wrap">
       <h4> ${pack.name} 供应商数量不满足项目要求最少供应商数量,请选择！</h4>
        <div class="tc  col-md-12 mt50">
          <input class="btn"  id = "inputb" name="addr"  type="button" onclick="updateJZ('${projectId}','${pack.id}','${flowDefineId}');" value="转为竞争性谈判"> 
	      <input class="btn"  id = "inputa" name="addr"  type="button" onclick="bynSub('${pack.id}','${projectId}','${flowDefineId}');" value="终止实施"> 
	      <input class="btn"  id = "inputa" name="addr"  type="button" onclick="continueSs('${projectId}','${pack.id}','${flowDefineId}');" value="继续实施"> 
        </div>
    </div>
	   <script type="text/javascript">
		function resize_table_width() {
	        $('.m_resize_table_width').each(function () {
	            var table_width = 0;
	            var parent_width = $(this).parent().width();
	            $(this).find('thead th').each(function () {
	            	if(typeof($(this).attr('width')) != 'undefined') {
	            		table_width +=  parseInt($(this).attr('width'));
		            }
	            });
	            if (table_width > parent_width) {
		            $(this).css({
		                width: table_width,
		                maxWidth: table_width
		            });
	            }
	        });
	    }
	    $(function () {
	        resize_table_width();
	    });
	   	</script>
  </body>
</html>
