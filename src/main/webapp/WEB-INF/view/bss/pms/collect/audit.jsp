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
				});
			}
			
				//数量
			 	function sum2(obj) { 
				    var id = $(obj).prev().val();
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
					});
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
						});
					}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">保障作业系统</a>
					</li>
					<li>
						<a href="#">采购计划管理</a>
					</li>
					<li class="active">
						<a href="#">采购计划审核</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		
		
		
			<div class="container">
				<div class="col-md-12 pl20 mt10 tab-v2">
					<button class="btn padding-left-10 padding-right-10 btn_back" onclick="sets()">调整审核人员</button>
					<a class="btn padding-left-10 padding-right-10 btn_back" href="${pageContext.request.contextPath}/look/report.html?id=${id}">生成评审报告页面</a>
				<%-- 	<div class="fl">
						<u:upload id="cgjh" groups="cgjh,audit" businessId="${id }" sysKey="2" typeId="${aid }" />
						<u:show showId="cgjh" groups="cgjh,audit" businessId="${id }" sysKey="2" typeId="${aid }" />
					</div> --%>
				</div>
		 
		  <div class="tab-content mt10">
          <div class="tab-v2">
				<ul class="nav nav-tabs bgwhite">
					<li class="active">
						<a aria-expanded="true" href="#tab-1" data-toggle="tab" class="s_news f18">所有明细</a>
					</li>
					<li>
						<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="fujian f18">按需求部门</a>
					</li>
				</ul>
				
			<form id="audit_form" action="${pageContext.request.contextPath}/look/audit.html" method="post">
				<div class="tab-content">
				<div class="tab-pane fade active in" id="tab-1">
				<div class="col-md-8 col-sm-8 col-xs-12 over_scroll">
					<table class="table table-bordered table-condensed mt5">
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
							<tr class="info">
								<th class="w50">序号</th>
								<th>需求部门</th>
								<th>物资类别及物种名称</th>
								<th>规格型号</th>
								<th>质量技术标准（技术参数）</th>
								<th>计量单位</th>
								<th>采购数量</th>
								<th>单位（元）</th>
								<th>预算金额（万元）</th>
								<th>交货期限</th>
								<th>采购方式建议</th>
								<th>采购机构</th>
								<th>供应商名称</th>
								<th>是否申请办理免税</th>
								<th>物资用途（仅进口）</th>
								<th>使用单位（仅进口）</th>
								<th>备注</th>
								
								<!-- 		<th class="info" colspan="17">事业部门需求</th> -->
								<%-- <c:forEach items="${bean }" var="obj">
									<th class="info" colspan="${obj.size}q">${obj.name }</th>
								</c:forEach> --%>
								<%-- 	<c:if test="${audit==1 || audit==2 || audit==3 }">
									<th>采购方式</th>
									<th>采购机构</th>
									<th>其他建议</th>
									</c:if>
									<c:if test="${audit==2 || audit==3 }">
										 <th>审核技术参数</th>
										 <th>其他建议</th>
									</c:if>
									<c:if test="${audit==3 }">
										<th>采购方式</th>
										<th>采购机构</th>
										<th>其他建议</th>
									</c:if> --%>
									
									
							<%-- 	<c:forEach items="${all }" var="p">
									<th>
										<c:if test="${p.param=='1'}">
											采购方式
										</c:if>
										<c:if test="${p.param=='2'}">
											采购机构
										</c:if>
										<c:if test="${p.param=='3'}">
											其他建议
										</c:if>
										<c:if test="${p.param=='4'}">
											技术参数意见
										</c:if>
									</th>
								</c:forEach> --%>
							</tr>
						</thead>
						<tbody id="table">
						<c:forEach items="${list }" var="obj" varStatus="vs">
							<tr>
								<td class="tc w50"><input readonly="readonly" type="text" name="list[${vs.index }].seq" onblur="checks(this)" value="${obj.seq }"><input style="border: 0px;" type="hidden" name="list[${vs.index }].id" value="${obj.id }">
								</td>
								<td>
								<c:forEach items="${requires }" var="re">
				        			<c:if test="${re.id==obj.department }">
										<input type="hidden"  name="list[${vs.index }].department" value="${obj.id }">
										<input readonly="readonly" type="text" value="${re.name}">
									</c:if>
								</c:forEach>
								</td>
								<td><input readonly="readonly" type="text" name="list[${vs.index }].goodsName" value="${obj.goodsName }"></td>
								<td class="tc"><input readonly="readonly" type="text" name="list[${vs.index }].stand" value="${obj.stand }"></td>
								<td class="tc"><input readonly="readonly" type="text" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }"></td>
								<td class="tc"><input readonly="readonly" type="text" name="list[${vs.index }].item" value="${obj.item }"></td>
								<td class="tc">
									<c:if test="${obj.purchaseCount!=null }">
					  				<input type="hidden" name="ss" value="${obj.id }">
					  				<input readonly="readonly" maxlength="11" id="purchaseCount" onblur="sum2(this)" 
					    			onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" name="lists[${vs.index }].purchaseCount"
										value="${obj.purchaseCount}" />
					  				<input type="hidden" name="ss" value="${obj.parentId }">
									</c:if> 
									<c:if test="${obj.purchaseCount==null }">
					  				<input disabled="disabled" type="text" name="lists[${vs.index }].purchaseCount" value="${obj.purchaseCount }">
									</c:if>
								</td>
								<td class="tc">
									<c:if test="${obj.price!=null }">
					  				<input type="hidden" name="ss" value="${obj.id }">
				      			<input readonly="readonly" maxlength="11" id="price" name="lists[${vs.index }].price" onblur="sum1(this)" value="${obj.price}" />
					  				<input type="hidden" name="ss" value="${obj.parentId }">
									</c:if>
				    			<c:if test="${obj.price==null}">
					  				<input readonly="readonly"  type="text" name="lists[${vs.index }].price" value="${obj.price }">
									</c:if>
								</td>
								<td class="tc">
									<input type="hidden" name="ss" value="${obj.id }">
									<input type="text" name="list[${vs.index }].budget" value="${obj.budget }" readonly="readonly">
									<input type="hidden" name="ss" value="${obj.parentId }">
								</td>
								<td><input type="text" name="list[${vs.index }].deliverDate" value="${obj.deliverDate }" readonly="readonly"></td>
								<td>
									<select name="list[${vs.index }].purchaseType" >
										<c:forEach items="${mType }" var="mt">
											<option value="${mt.id }" <c:if test="${mt.id==obj.purchaseType }"> selected="selected"</c:if> >${mt.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tc">
									<select class="org" name="list[${vs.index }].organization">
										<c:forEach items="${org }" var="ss">
											<option value="${ss.name }" <c:if test="${ss.name==obj.organization }">selected="selected" </c:if> >${ss.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tc"><input readonly="readonly" type="text" name="list[${vs.index }].supplier" value="${obj.supplier }"></td>
								<td class="tc"><input readonly="readonly" type="text" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax }"></td>
								<td class="tc"><input readonly="readonly" type="text" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }"></td>
								<td class="tc"><input readonly="readonly" type="text" name="list[${vs.index }].useUnit" value="${obj.useUnit }"></td>
								<td class="tc"><input readonly="readonly" type="text" name="list[${vs.index }].memo" value="${obj.memo }">
									<input type="hidden" name="list[${vs.index }].planName" value="${obj.planName }">
									<input type="hidden" name="list[${vs.index }].planNo" value="${obj.planNo }">
									<input type="hidden" name="list[${vs.index }].planType" value="${obj.planType }">
									<input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
									<input type="hidden" name="list[${vs.index }].historyStatus" value="${obj.historyStatus }">
									<input type="hidden" name="list[${vs.index }].goodsType" value="${obj.goodsType }">
									<input type="hidden" name="list[${vs.index }].organization" value="${obj.organization }">
									<input type="hidden" name="list[${vs.index }].auditDate" value="${obj.auditDate }">
									<input type="hidden" name="list[${vs.index }].isMaster" value="${obj.isMaster }">
									<input type="hidden" name="list[${vs.index }].isDelete" value="${obj.isDelete }">
									<input type="hidden" name="list[${vs.index }].status" value="${obj.status }">
								</td>
							<%-- <c:if test="${audit==1 || audit==2 || audit==3 }">
								<td class="tc">
									<select name="list[${vs.index }].onePurchaseType" >
										<c:forEach items="${mType }" var="mt">
											<option value="${mt.id }" <c:if test="${mt.id==obj.onePurchaseType }"> selected="selected"</c:if> >${mt.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tc">
									<select name="list[${vs.index}].oneOrganiza" >
										<c:forEach items="${org }" var="ss">
											<option value="${ss.name }">${ss.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tc">
									<input type="text"  name="list[${vs.index }].oneAdvice" value="${obj.oneAdvice }">
								</td>
								</c:if>
					  <c:if test="${audit==2 || audit==3 }">
								<td class="tc">
									<input type="text"   name="list[${vs.index }].twoTechAdvice" value="${obj.twoTechAdvice }">
								</td>
								<td class="tc">
									<input type="text"   name="list[${vs.index }].twoAdvice" value="${obj.twoAdvice }">
								</td>
								</c:if>
							<c:if test="${audit==3 }">
								<td class="tc">
									<input type="text"   name="list[${vs.index }].threeAdvice" value="${obj.threeAdvice }">
								</td>
								<td class="tc">
									<select name="list[${vs.index }].threePurchaseType" >
										<c:forEach items="${mType }" var="mt">
											<option value="${mt.id }" <c:if test="${mt.id==obj.threePurchaseType }"> selected="selected"</c:if> >${mt.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tc">
									<select name="list[${vs.index}].threeOrganiza" >
										<c:forEach items="${org }" var="ss">
											<option value="${ss.name }">${ss.name}</option>
										</c:forEach>
									</select>
								</td>
							</c:if> --%>
				 </tr>
						</c:forEach>
						</tbody>
					</table>
					</div>
					
			<c:if test="${audit!=null ||audit!=0 }">
				<div class="col-md-4 col-sm-4 col-xs-12 over_scroll">
					<table id="table" class="table table-bordered table-condensed mt5">
						<thead>
							<tr>
										<c:if test="${audit==1 || audit==2 || audit==3 }">
										<th class="info" colspan="3">一轮审核</th>
									</c:if>
									<c:if test="${audit==2 || audit==3 }">
										<th class="info" colspan="2">二轮审核</th>
									</c:if>
										<c:if test="${audit==3 }">
										<th class="info" colspan="3">二轮审核</th>
									</c:if>
							</tr>
							<tr>
									<c:if test="${audit==1 || audit==2 || audit==3 }">
									<th>采购方式</th>
									<th>采购机构</th>
									<th>其他建议</th>
									</c:if>
									<c:if test="${audit==2 || audit==3 }">
										 <th>审核技术参数</th>
										 <th>其他建议</th>
									</c:if>
									<c:if test="${audit==3 }">
										<th>采购方式</th>
										<th>采购机构</th>
										<th>其他建议</th>
									</c:if>
									
									
								<%-- <c:forEach items="${all }" var="p">
									<th class="info">
										<c:if test="${p.param=='1'}">
											采购方式
										</c:if>
										<c:if test="${p.param=='2'}">
											采购机构
										</c:if>
										<c:if test="${p.param=='3'}">
											其他建议
										</c:if>
										<c:if test="${p.param=='4'}">
											技术参数意见
										</c:if>
									</th>
								</c:forEach> --%>
							</tr>
						</thead>
						<c:forEach items="${list }" var="objs" varStatus="vs">
						<tr>
							<c:if test="${audit==1 || audit==2 || audit==3 }">
							<td>
							<select name="list[${vs.index }].onePurchaseType" >
										<c:forEach items="${mType }" var="mt">
											<option value="${mt.id }" <c:if test="${mt.id==obj.onePurchaseType }"> selected="selected"</c:if> >${mt.name}</option>
										</c:forEach>
									</select>
									
							</td>		
						<td class="tc">
									<select name="list[${vs.index}].oneOrganiza">
										<c:forEach items="${org }" var="ss">
											<option value="${ss.name }">${ss.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tc">
									<input type="text" name="list[${vs.index }].oneAdvice" value="${obj.oneAdvice }" >
								</td>
								</c:if>
								
							<c:if test="${audit==2 || audit==3 }">
								<td class="tc">
									<input type="text" name="list[${vs.index }].twoTechAdvice" value="${obj.twoTechAdvice }" >
								</td>
								<td class="tc">
									<input type="text" name="list[${vs.index }].twoAdvice" value="${obj.twoAdvice }" >
								</td>
							</c:if>
							<c:if test="${audit==3 }">	
								
								<td class="tc">
									<select name="list[${vs.index }].threePurchaseType">
										<c:forEach items="${mType }" var="mt">
											<option value="${mt.id }" <c:if test="${mt.id==obj.threePurchaseType }"> selected="selected"</c:if> >${mt.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tc">
									<select name="list[${vs.index}].threeOrganiza">
										<c:forEach items="${org }" var="ss">
											<option value="${ss.name }">${ss.name}</option>
										</c:forEach>
									</select>
								</td>
								<td class="tc">
									<input type="text" name="list[${vs.index }].threeAdvice" value="${obj.threeAdvice }">
								</td>
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
							</c:forEach>
						--%></tr>
						</c:forEach>
					</table>
				</div>
				</c:if>
				
				
				</div>
				<div class="tab-pane fade in" id="tab-2">
				<div class="content table_box ">
					<table class="table table-bordered table-condensed mt5">
						<thead>
							<tr>
								<th class="info" colspan="2">事业部门需求</th>
							</tr>
							<tr class="info">
								<th class="w50">序号</th>
								<th>需求部门</th>
	  
						</thead>
						<c:forEach items="${depList }" var="objs" varStatus="vs">
							<%-- <c:if test="${dep==obj.department }"> --%>
							<tbody id="table">
							<tr>
								<td class="tc w50">
							 
								</td>
								<td>
							 
								<c:forEach items="${requires }" var="re">
									<c:if test="${re.id==objs}">
										<%-- <input type="hidden"  name="list[${vs.index }].department" value="${obj.id }"> --%>
									 ${re.name}
									</c:if>
								</c:forEach>
								</td>
	 
								</tbody>
								 
						 </c:forEach> 
					</table>
					</div>
			
				
				</div>
				</div>
				 </div>
				 </div>
					<input type="hidden" name="id" value="${id }"> 
					<input type="hidden" name="planNo" value="${planNo }">
					<input type="hidden" id="status" name="status" value="${status }">
					<input class="btn btn-windows save" style="margin-left: 100px;" type="submit" value="保存">
			 
					<input class="btn btn-windows reset" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
			</form>
			
		</div>
	</body>
</html>