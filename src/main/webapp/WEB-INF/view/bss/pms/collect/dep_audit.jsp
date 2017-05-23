 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript">
			$(function() {
				$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
					// 获取已激活的标签页的名称
					var activeTab = $(e.target).text();
					// 获取前一个激活的标签页的名称
					var previousTab = $(e.relatedTarget).text();
				});
			})

			/** 全选全不选 */
			function selectAll() {
				var checklist = document.getElementsByName("chkItem");
				var checkAll = document.getElementById("checkAll");
				if(checkAll.checked) {
					for(var i = 0; i < checklist.length; i++) {
						checklist[i].checked = true;
					}
				} else {
					for(var j = 0; j < checklist.length; j++) {
						checklist[j].checked = false;
					}
				}
			}

			/** 单选 */
			function check() {
				var count = 0;
				var checklist = document.getElementsByName("chkItem");
				var checkAll = document.getElementById("checkAll");
				for(var i = 0; i < checklist.length; i++) {
					if(checklist[i].checked == false) {
						checkAll.checked = false;
						break;
					}
					for(var j = 0; j < checklist.length; j++) {
						if(checklist[j].checked == true) {
							checkAll.checked = true;
							count++;
						}
					}
				}
			}

			function view(no) {

				window.location.href = "${pageContext.request.contextPath}/purchaser/queryByNo.html?planNo=" + no;
			}

			function aadd() {
				var s = $("#count").val();
				s++;
				$("#count").val(s);
				var tr = $("input[name=dyadds]").parent().parent();
				$(tr).before("<tr><td class='tc'><input type='text' name='list[" + s + "].seq' /></td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].department' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].goodsName' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].stand' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].qualitStand' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].item' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseCount' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].price' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].budget' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].deliverDate' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].purchaseType' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].supplier' /> </td>" +
					"<td class='tc'> <input style='border: 0px;'type='text' name='list[" + s + "].isFreeTax' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].goodsUse' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].useUnit' /> </td>" +
					"<td class='tc'> <input style='border: 0px;' type='text' name='list[" + s + "].memo' /> </td>" +
					+"<tr/>");
			}

			function sets() {
				var id = $("#cid").val();
				window.location.href = "${pageContext.request.contextPath}/set/list.html?id=" + id;
			}

			function sel(obj) {
				var val = $(obj).val();
				$("select option").each(function() {
					var opt = $(this).val();
					if(val == opt) {
						$(this).attr("selected", "selected");
					}
				});
			}

			function ss() {
				var value = $("#reson").val();
				if(value != null && value != "") {
					$("#status").val(3);
					$("#acc_form").submit();
				} else {
					layer.tips("退回理由不允许为空", "#reson");
				}
			}
			/* 	function ss(obj){
			  	   var val=$(obj).val();
			  	   $("select option").each(function(){
			  		   var opt=$(this).val();
			  		   if(val==opt){
			  			   $(this).attr("selected", "selected");  
			  		   }
			  	   });
			     } */

			/* function org(obj){
    		 
    	   var val=$(obj).val();
    
    	   $(".org option").each(function(){
    	 
    		   var opt=$(this).val();
    		   if(val==opt){
    			   $(this).attr("selected", "selected");  
    		   }
    	   });
       } */

			var flag = true;
			function checks(obj) {
				var name = $(obj).attr("name");
				var planNo = $("#pNo").val();
				var val = $(obj).val();
				var defVal = obj.defaultValue;
				if(val != defVal) {
					$.ajax({
						url: "${pageContext.request.contextPath}/adjust/filed.html",
						type: "post",
						data: {
							planNo: planNo,
							name: name
						},
						success: function(data) {
							if(data == 'exit') {
								flag = false;
								layer.tips("该字段不允许修改", obj);
							}
						}
					});
				}
			}

			function audits() {
				if(flag == true) {
					$("#audit_form").submit();
				}
			}
			
			//只能输入数字
			function checkNum(obj,num){
				var vals = $(obj).val();
				var reg = /^\d+\.?\d*$/;  
				if(!reg.exec(vals)){
					$(obj).val("");
				}else{
					if(num==1){
						var count = $(obj).val();
						var price = $(obj).parent().next().find("input").val();
						$(obj).parent().next().next().find("input").val(count*price);
					}else if(num==2){
						var count = $(obj).parent().prev().find("input").val();
						var price = $(obj).val();
						$(obj).parent().next().find("input").val(count*price);
					}
				}
				
			}
			
				//单价
			 function sum1(obj) {
				 change(obj);
/* 			    var id = $(obj).prev().val();
			    var pId = $(obj).next().val();
					$.ajax({
				  	url : "${pageContext.request.contextPath}/purchaser/viewIds.html",
				  	type : "post",
				  	data : "id=" + id,
				  	dataType : "json",
				  	success : function(data) {
							var purchaseCount = $(obj).val() - 0; //价钱
							var price2 = $(obj).parent().prev().children(":last").prev().val() - 0;//数量
							var sum = purchaseCount * price2;
							$(obj).parent().next().children(":last").prev().val(sum);
							var budget = 0;
							$("#table tr").each(function() {
						 		var cid = $(this).find("td:eq(8)").children(":last").val();
						  	var same = $(this).find("td:eq(8)").children(":last").prev().val() - 0;
						  	if (pId == cid) {
						    	budget = budget + same; //查出所有的子节点的值
						  	}
							});
							for ( var i = 0; i < data.length; i++) {
					  		var v1 = data[i].id;
					  		$("#table tr").each(function() {
									var pid = $(this).find("td:eq(8)").children(":first").val();//上级id
									if (data[i].id == pid) {
						 	 		$(this).find("td:eq(8)").children(":first").next().val(budget);
									}
			 		  		});
							}
				  	},
				}); */
			}
			
				//数量
			 	function sum2(obj) { 
			 		 change(obj);
		/* 		    var id = $(obj).prev().val();
				    var pId = $(obj).next().val();
						$.ajax({
					  	url : "${pageContext.request.contextPath}/purchaser/viewIds.html",
					  	type : "post",
					  	data : "id=" + id,
					  	dataType : "json",
					  	success : function(data) {
								var purchaseCount = $(obj).val() - 0;//数量
								var price2 = $(obj).parent().next().children(":last").prev();//价钱
								var price = $(price2).val() - 0;
								var sum = purchaseCount * price;
								var budgets = $(obj).parent().next().next().children(":last").prev();
								$(budgets).val(sum);
								var budget = 0;
								$("#table tr").each(function() {
						  		var cid = $(this).find("td:eq(8)").children(":last").val();
						  		var same = $(this).find("td:eq(8)").children(":last").prev().val() - 0;
						  		if (pId == cid) {
						    		budget = budget + same; //查出所有的子节点的值
						  		}
								});
								for ( var i = 0; i < data.length; i++) {
						  		var v1 = data[i].id;
						  		$("#table tr").each(function() {
										var pid = $(this).find("td:eq(8)").children(":first").val();//上级id
										if (data[i].id == pid) {
							 	 			$(this).find("td:eq(8)").children(":first").next().val(budget);
										}
						  		});
								}
					 	},
					}); */
				}
				
			 	//单价
				 function sumPrice(obj,num) {
				    var id = $(obj).prev().val();
				    var pId = $(obj).next().val();
						$.ajax({
					  	url : "${pageContext.request.contextPath}/purchaser/viewIds.html",
					  	type : "post",
					  	data : "id=" + id,
					  	dataType : "json",
					  	success : function(data) {
								var purchaseCount = $(obj).val() - 0; //价钱
								var price2 = $(obj).parent().prev().children(":last").prev().val() - 0;//数量
								var sum = purchaseCount * price2;
								$(obj).parent().next().children(":last").prev().val(sum);
								var budget = 0;
								$("#table"+num+" tr").each(function() {
							 		var cid = $(this).find("td:eq(8)").children(":last").val();
							  	var same = $(this).find("td:eq(8)").children(":last").prev().val() - 0;
							  	if (pId == cid) {
							    	budget = budget + same; //查出所有的子节点的值
							  	}
								});
								for ( var i = 0; i < data.length; i++) {
						  		var v1 = data[i].id;
						  		$("#table"+num+" tr").each(function() {
										var pid = $(this).find("td:eq(8)").children(":first").val();//上级id
										if (data[i].id == pid) {
							 	 		$(this).find("td:eq(8)").children(":first").next().val(budget);
										}
				 		  		});
								}
					  	},
					});
				}
				
					//数量
				 	function sumCount(obj,num) { 
					    var id = $(obj).prev().val();
					    var pId = $(obj).next().val();
						
					}
					
					
				// var defVal = obj.defaultValue;获得默认值
				
				function change(obj){
					var status=$("input[name='status']").val();
				
					var tr=obj.parentNode.parentNode;
					var val = $(obj).val();
					var defVal = obj.defaultValue;
					var index=tr.rowIndex; //获取第几行，然后给赋值
					var td=obj.parentNode;
					var tdIndex=td.cellIndex;
					var tdVal1= $("#dep_table tr:eq(1)").find("th:eq("+tdIndex+")").text();
			  	   if(val!=defVal){
			  		
			  		   if(status=='3'){
			  			   var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(); 
				  			var curval=tdVal1+"由"+defVal+"变成"+val;
				  			var newVal=inpval+curval;
			  			 $("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(newVal); 
			  		   }
			  		 if(status=='5'){
			  		   var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(); 
			  			var curval=tdVal1+"由"+defVal+"变成"+val;
			  			var newVal=inpval+curval;
				  		 $("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(newVal);
				  	   }
				  	 if(status=='7'){
				  	   var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(); 
			  			var curval=tdVal1+"由"+defVal+"变成"+val;
			  			var newVal=inpval+curval;
				  		 $("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(newVal);
				  	   }  
					} 
				  	  
				}
				
				function typeChange(obj){
					var status=$("input[name='status']").val();		
					var val = $(obj).find("option:selected").text();
					var defVal;
					var opts=obj.getElementsByTagName('option'); 
						for (var i in opts) {
							if (opts[i].defaultSelected) {
								defVal = opts[i].text;
								break;
							}
						}
						var tr=obj.parentNode.parentNode;	
						var index=tr.rowIndex; //获取第几行，然后给赋值
						var td=obj.parentNode;
						var tdIndex=td.cellIndex;
						var tdVal1= $("#dep_table tr:eq(1)").find("th:eq("+tdIndex+")").text();
						 if(val!=defVal){
							  if(status=='3'){
								  var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(); 
						  			var curval=tdVal1+"由"+defVal+"变成"+val;
						  			var newVal=inpval+curval;
							       $("#audit_table tr:eq("+index+")").find("td:eq(0)").children(":first").val(newVal);
							  }
							  if(status=='5'){
								  var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(); 
						  			var curval=tdVal1+"由"+defVal+"变成"+val;
						  			var newVal=inpval+curval;
							  		 $("#audit_table tr:eq("+index+")").find("td:eq(1)").children(":first").val(newVal);
							  	   }
							 if(status=='7'){
								   var inpval=$("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(); 
						  			var curval=tdVal1+"由"+defVal+"变成"+val;
						  			var newVal=inpval+curval;
							  		 $("#audit_table tr:eq("+index+")").find("td:eq(2)").children(":first").val(newVal);
							  	 }
							 
						} 
						 
						  	 
						  	 
				}
		</script>
	</head>

<body>
<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs ">
	<div class="container">
		<ul class="breadcrumb margin-left-0">
			<li>
				<a href="javascript:void(0);"> 首页</a>
			</li>
			<li>
				<a href="javascript:void(0);">保障作业系统</a>
			</li>
			<li>
				<a href="javascript:void(0);">采购计划管理</a>
			</li>
			<li class="active">
				<a href="javascript:void(0);">采购计划审核</a>
			</li>
		</ul>
		<div class="clear"></div>
		</div>
</div>
		
<div class="container">
	<div class="col-md-12 mt10 tab-v2 col-xs-12 col-sm-12 p0">
		<button class="btn padding-left-10 padding-right-10 btn_back" onclick="sets()">调整审核人员</button>
		<a class="btn padding-left-10 padding-right-10 btn_back" href="${pageContext.request.contextPath}/look/report.html?id=${id}">生成评审报告页面</a>
		<%-- 	<div class="fl">
		<u:upload id="cgjh" groups="cgjh,audit" businessId="${id }" sysKey="2" typeId="${aid }" />
		<u:show showId="cgjh" groups="cgjh,audit" businessId="${id }" sysKey="2" typeId="${aid }" />
		</div> --%>
	</div>	 
	<div class="row magazine-page">
		<div class="col-md-12 pt10 tab-v2">
			<ul class="nav nav-tabs bgwhite">
				<li class="active">
					<a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">所有明细</a>
				</li>
				<li>
					<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">按需求部门</a>
				</li>
			</ul>	
			<form id="audit_form" action="${pageContext.request.contextPath}/look/audit.html" method="post">
				<div class="tab-content over_hideen">
					<div class="tab-pane fade active in" id="tab-1">
						<div class="col-md-8 col-sm-8 col-xs-12 over_auto">
							<table id="dep_table" class="table table-bordered table-condensed mt5 table_input table_wrap">
								<thead>
									<tr>
										<th class="info" colspan="17">事业部门需求</th>
								<%-- 		<c:if test="${audit==1 || audit==2 || audit==3 }">
										<th class="info" colspan="3">一轮审核</th>
									</c:if>
									<c:if test="${audit==2 || audit==3 }">
										<th class="info" colspan="2">二轮审核</th>
									</c:if>
										<c:if test="${audit==3 }">
										<th class="info" colspan="3">二轮审核</th>
									</c:if> --%>
									
									
							<%-- 	<c:forEach items="${bean }" var="obj">
									<th class="info" colspan="${obj.size}q">${obj.name }</th>
								</c:forEach> --%>
									</tr>
									<tr class="info h91">
									<th class="info w50">序号</th>
									<th>需求部门</th>
									<th>物资类别及<br>物种名称</th>
									<th>规格型号</th>
									<th>质量技术标准<br>（技术参数）</th>
									<th>计量单位</th>
									<th>采购数量</th>
									<th>单价<br>（元）</th>
									<th>预算金额<br>（万元）</th>
									<th>交货期限</th>
									<th>采购方式</th>
									<th>采购机构</th>
									<th>供应商名称</th>
									<th>是否申请<br>办理免税</th>
									<th>物资用途<br>（仅进口）</th>
									<th>使用单位<br>（仅进口）</th>
									<th>备注</th>
									</tr>
								</thead>
								<tbody >
									<c:forEach items="${list }" var="obj" varStatus="vs">
										<tr>
											<td class="tc w50"><input readonly="readonly" type="text" class="w50" name="listDetail[${vs.index }].seq" onblur="checks(this)" value="${obj.seq }"><input style="border: 0px;" type="hidden" name="listDetail[${vs.index }].id" value="${obj.id }"></td>
											<td class="tl pl20">
											
											${obj.department }
											<%-- 	<c:forEach items="${requires }" var="re">
								        			<c:if test="${re.id==obj.department }">
														<input type="hidden"  name="list[${vs.index }].department" value="${obj.id }">
														<input readonly="readonly" type="text" value="${re.name}">
													</c:if>
												</c:forEach> --%>
											</td>
											<td class="tl pl20"><input onblur="change(this)"  type="text" name="listDetail[${vs.index }].goodsName" value="${obj.goodsName }"></td>
											<td class="tl pl20"><input onblur="change(this)"   type="text" name="listDetail[${vs.index }].stand" value="${obj.stand }"></td>
											<td class="tl pl20"><input onblur="change(this)"   type="text" name="listDetail[${vs.index }].qualitStand" value="${obj.qualitStand }"></td>
											<td class="tl pl20"><input onblur="change(this)"   type="text" name="listDetail[${vs.index }].item" value="${obj.item }" class="w50"></td>
											<td class="tl pl20">
												<c:if test="${obj.purchaseCount!=null }">
								  				<input type="hidden" name="ss" value="${obj.id }">
								  				<input  maxlength="11" id="purchaseCount" onblur="sum2(this)" 
								    			onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="listDetail[${vs.index }].purchaseCount"
													value="${obj.purchaseCount}" type="text" class="w80"/>
								  				<input type="hidden" name="ss" value="${obj.parentId }">
												</c:if> 
												<c:if test="${obj.purchaseCount==null }">
								  				<input disabled="disabled" type="text" name="listDetail[${vs.index }].purchaseCount" value="${obj.purchaseCount }" class="w80">
												</c:if>
											</td>
											<td class="tl pl20">
												<c:if test="${obj.price!=null }">
									  				<input type="hidden" name="ss" value="${obj.id }">
								      				<input  maxlength="11" id="price" name="listDetail[${vs.index }].price" onblur="sum1(this)" value="${obj.price}" type="text" class="w80"/>
									  				<input type="hidden" name="ss" value="${obj.parentId }">
												</c:if>
							    				<c:if test="${obj.price==null}">
								  					<input readonly="readonly"  type="text" name="listDetail[${vs.index }].price" value="${obj.price }" class="w80">
												</c:if>
											</td>
											<td class="tr pr20">
												<input type="hidden" name="ss" value="${obj.id }">
												<input type="text" name="listDetail[${vs.index }].budget" value="${obj.budget }" readonly="readonly" class="w80">
												<input type="hidden" name="ss" value="${obj.parentId }">
											</td>
											<td><input type="text" onblur="change(this)"   name="listDetail[${vs.index }].deliverDate" value="${obj.deliverDate }"  class="w100"></td>
											<td>
											<%-- <c:if test="${obj.purchaseCount!=null }">  --%>
												<select name="listDetail[${vs.index }].purchaseType" onchange="typeChange(this)">
													<c:forEach items="${mType }" var="mt">
														<option value="${mt.id }" <c:if test="${mt.id==obj.purchaseType }"> selected="selected"</c:if> >${mt.name}</option>
													</c:forEach>
												</select>
												
											<%-- </c:if>	 --%>
											</td>
											<td class="tc">
											<%-- <c:if test="${obj.purchaseCount!=null }">  --%>
												<select class="org" name="listDetail[${vs.index }].organization" onchange="typeChange(this)">
													<c:forEach items="${org }" var="ss">
														<option value="${ss.orgId }" <c:if test="${ss.orgId==obj.organization }">selected="selected" </c:if> >${ss.name}</option>
													</c:forEach>
												</select>
										<%--     </c:if> --%>
											</td>
											<td class="tl pl20"><input onblur="change(this)"  type="text" name="listDetail[${vs.index }].supplier" value="${obj.supplier }"></td>
											<td class="tl pl20"><input onblur="change(this)"  type="text" name="listDetail[${vs.index }].isFreeTax" value="${obj.isFreeTax }"></td>
											<td class="tl pl20"><input onblur="change(this)"  type="text" name="listDetail[${vs.index }].goodsUse" value="${obj.goodsUse }"></td>
											<td class="tl pl20"><input onblur="change(this)"  type="text" name="listDetail[${vs.index }].useUnit" value="${obj.useUnit }"></td>
											<td class="tl pl20"><input onblur="change(this)"  type="text" name="listDetail[${vs.index }].memo" value="${obj.memo }">
											<%-- 	<input type="hidden" name="list[${vs.index }].planName" value="${obj.planName }">
												<input type="hidden" name="list[${vs.index }].planNo" value="${obj.planNo }">
												<input type="hidden" name="list[${vs.index }].planType" value="${obj.planType }">
												<input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
												<input type="hidden" name="list[${vs.index }].historyStatus" value="${obj.historyStatus }">
												<input type="hidden" name="list[${vs.index }].goodsType" value="${obj.goodsType }">
												<input type="hidden" name="list[${vs.index }].organization" value="${obj.organization }">
												<input type="hidden" name="list[${vs.index }].auditDate" value="${obj.auditDate }">
												<input type="hidden" name="list[${vs.index }].isMaster" value="${obj.isMaster }">
												<input type="hidden" name="list[${vs.index }].isDelete" value="${obj.isDelete }">
												<input type="hidden" name="list[${vs.index }].status" value="${obj.status }"> --%>
											</td>
						 				</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
			
					<c:if test="${audit!=null ||audit!=0 }">
						<div class="col-md-4 col-sm-4 col-xs-12 over_auto">
							<table id="audit_table" class="table table-bordered table-condensed mt5 table_input table_wrap">
								<thead>
									<tr>
										<c:if test="${status==3 || status==5 || status==7 }">
											<th class="info" colspan="3">一轮审核</th>
										</c:if>
										<c:if test="${status==5 || status==7 }">
											<th class="info" colspan="2">二轮审核</th>
										</c:if>
										<c:if test="${status==7 }">
											<th class="info" colspan="3">三轮审核</th>
										</c:if>
									</tr>
									<tr class="h91">
										<c:if test="${status==3 || status==5 || status==7 }">
											<th colspan="3" >审核意见</th>
										 
										</c:if>
										<c:if test="${status==5 || status==7 }">
											 <th colspan="2" >审核意见</th>
										</c:if>
										<c:if test="${status==7 }">
										  <th colspan="3" >审核意见</th>
										</c:if>
									
									
						 
							</tr>
						</thead>
						<c:forEach items="${list }" var="objs" varStatus="vs">
							<tr>
								<c:if test="${status==3 || status==5 || status==7 }">
								<td colspan="3">
							<%-- 		<select name="listDetail[${vs.index }].onePurchaseType" >
										<c:forEach items="${mType }" var="mt">
											<option value="${mt.id }" <c:if test="${mt.id==obj.onePurchaseType }"> selected="selected"</c:if> >${mt.name}</option>
										</c:forEach>
									</select>
									
								</td>		
								<td class="tc">
									<select name="listDetail[${vs.index}].oneOrganiza">
										<c:forEach items="${org }" var="ss">
											<option value="${ss.name }">${ss.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tc"> --%>
									<input type="text"  <c:if test="${status==5 || status==7 }"> readonly="readonly" </c:if>    name="listDetail[${vs.index }].oneAdvice"  value="${objs.oneAdvice }" >
								</td>
								</c:if>
								
							<c:if test="${status==5 || status==7 }">
								<td colspan="2">
							<%-- 		<input type="text" name="listDetail[${vs.index }].twoTechAdvice" value="${obj.twoTechAdvice }" >
								</td>
								<td class="tc" --%>
									<input type="text"  <c:if test="${status==7 }"> readonly="readonly" </c:if>  name="listDetail[${vs.index }].twoAdvice"   value="${objs.twoAdvice }" >
								<!-- </td> -->
							</c:if>
							<c:if test="${status==7 }">	
								
								<td colspan="3">
						<%-- 			<select name="listDetail[${vs.index }].threePurchaseType">
										<c:forEach items="${mType }" var="mt">
											<option value="${mt.id }" <c:if test="${mt.id==obj.threePurchaseType }"> selected="selected"</c:if> >${mt.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tl pl20">
									<select name="listDetail[${vs.index}].threeOrganiza">
										<c:forEach items="${org }" var="ss">
											<option value="${ss.id }">${ss.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tl pl20"> --%>
									<input type="text" name="listDetail[${vs.index }].threeAdvice" value="${objs.threeAdvice }">
								<!-- </td> -->
								</c:if>
								
							<%--<c:forEach items="${all }" var="al" varStatus="avs">
								<td class="tc">
								<c:forEach items="${audits }" var="as">
									<c:if test="${as.purchaseId==obj.id and as.auditParamId==al.id }">
									 	<c:if test="${al.param=='1' }">
									 		<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
									 		<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 		<select onchange="ss(this)" name="audit[${vs.index*5+avs.index}].paramValue">
												<option value="gkzb" <c:if test="${as.paramValue =='公开招标' }">  selected="selected" </c:if> >公开招标</option>
												<option value="yqzb" <c:if test="${as.paramValue =='邀请招标' }">  selected="selected" </c:if> >邀请招标</option>
												<option value="dyly" <c:if test="${as.paramValue =='单一来源'  }">  selected="selected" </c:if> >单一来源</option>
												<option value="jzxtp" <c:if test="${as.paramValue =='竞争性谈判' }">  selected="selected" </c:if> >竞争性谈判</option>
												<option value="xj" <c:if test="${as.paramValue =='询价' }">  selected="selected" </c:if> >询价</option> 
												<c:forEach items="${dicType }" var="mt">
													<option value="${mt.id }"<c:if test="${mt.id==as.paramValue  }"> selected="selected"</c:if> >${mt.name}</option>
												</c:forEach>
											</select>
									 	</c:if>
									  	<c:if test="${al.param=='2' }">
										  	<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
										  	<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 		<select onchange="ss(this)"  name="audit[${vs.index*5+avs.index }].paramValue">
												<c:forEach items="${org }" var="ss">
												  <option value="${ss.name }" <c:if test="${as.paramValue ==ss.name }">  selected="selected" </c:if> >${ss.name}</option>
												</c:forEach>
											</select>
									 	</c:if>
									 	<c:if test="${al.param=='3' or al.param=='4' }">
									 		<input type="hidden" name="audit[${vs.index*5+avs.index }].purchaseId" value="${obj.id }">
									  		<input type="hidden" name="audit[${vs.index*5+avs.index }].auditParamId" value="${al.id }">
									 		<input type="text" name="audit[${vs.index*5+avs.index }].paramValue" value="${as.paramValue }">
									 	</c:if>
									 </c:if>
								</c:forEach>
								</td>
							</c:forEach>--%>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>
						</div>
						<div class="tab-pane fade in" id="tab-2">
							<div class="content table_box ">
								<table class="table table-bordered table-condensed mt5">
									<thead>
									<!-- 	<tr>
											<th class="info" colspan="2">事业部门需求</th>
										</tr> -->
										<tr class="info">
										<th>需求部门</th>
									</thead>
									<c:forEach items="${depList }" var="objs" varStatus="vs">
										<%-- <c:if test="${dep==obj.department }"> --%>
										
											<tr>
												
												<td>			 
														${objs }	
												</td>
											</tr>
																	 
						 			</c:forEach> 
									</table>
								</div>
							</div>
						</div>
						<div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
							<input type="hidden" name="id" id="cid" value="${id }"> 
							<input type="hidden" name="planNo" value="${planNo }">
							<input type="hidden" name="auditTurn" value="${audit }">
							<input type="hidden" id="status" name="status" value="${status }">
							<input class="btn btn-windows save" type="submit" value="保存">
							<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
					</div>
					</form>			
					</div>
				</div>
		
		</div>
	</body>
<script>
   window.onload=function(){
       /*
       * $('#sourceDiv').scroll( function() {
        $('#targetDiv').scrollTop($(this).scrollTop());
        $('#targetDiv').scrollLeft($(this).scrollLeft());
        });
        $('#targetDiv').scroll( function() {
        $('#sourceDiv').scrollTop($(this).scrollTop());
        $('#sourceDiv').scrollLeft($(this).scrollLeft());
        });
       * */


  console.info($("#table").get(0))
       console.info($("#audit_table").get(0))
   }
</script>
</html>
