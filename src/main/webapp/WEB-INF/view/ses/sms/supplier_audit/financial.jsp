<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<title>财务信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<style type="text/css">
			td {
				cursor: pointer;
			}
		</style>
		<script type="text/javascript">
			//默认不显示叉
			$(function() {
				$("td").each(function() {
					$(this).find("p").hide();
				});
			});

			function reason(id, auditFieldName) {
				/* var offset = "";
	    if (window.event) {
		    e = event || window.event;
		    var x = "";
		    var y = "";
		    x = e.clientX + 20 + "px";
		    y = e.clientY + 20 + "px";
		    offset = [y, x];
	    } else {
	      offset = "200px";
	    } */
				var supplierId = $("#supplierId").val();
				if(auditFieldName == "财务信息") {
					var auditContent = $("#" + id).text() + "年财务信息"; //审批的字段内容
				} else {
					var auditContent = $("#" + id).text() + "年财务附件";
				}

				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px'
					},
					function(text) {
						$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
							type: "post",
							data: "auditType=finance_page" + "&auditFieldName=" + auditFieldName + "&auditContent=" + auditContent + "&suggest=" + text + "&supplierId=" + supplierId + "&auditField=" + id,
							dataType: "json",
							success: function(result) {
								result = eval("(" + result + ")");
								if(result.msg == "fail") {
									layer.msg('该条信息已审核过！', {
										shift: 6, //动画类型
										offset: '100px'
									});
								}
							}
						});

						if(auditFieldName == "财务信息") {
							$("#" + id + "_hidden1").hide();
							$("#" + id + "_show").show();
						} else {
							$("#" + id + "_hidden2").hide();
							$("#" + id + "_fileShow").show();
						}
						layer.close(index);
					});
			}

			/* function reason1(year, ele,auditField){
			  var offset = "";
			  if (window.event) {
			    e = event || window.event;
			    var x = "";
			    var y = "";
			    x = e.clientX + 20 + "px";
			    y = e.clientY + 20 + "px";
			    offset = [y, x];
			  } else {
			      offset = "200px";
			  }
			  var supplierId=$("#supplierId").val();
			  var value = $(ele).parents("li").find("span").text().replace("：","");//审批的字段名字
			  var auditFieldName=year+"年";//审批的字段名字
			  var fail = false;
			  var index = layer.prompt({
			      title: '请填写不通过的理由：', 
			      formType: 2, 
			      offset : offset,
			    }, 
			      function(text){
					      $.ajax({
					          url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
					          type:"post",
					          data:"auditType=finance_page"+"&auditFieldName="+auditFieldName+"&auditContent=附件"+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
					          dataType:"json",
						          success:function(result){
						          result = eval("(" + result + ")");
						          if(result.msg == "fail"){
						           layer.msg('该条信息已审核过！', {
					                shift: 6, //动画类型
					                offset:'300px'
					            });
					            }
					          }
					        });
						        $(ele).parent("li").find("div").eq(1).show(); //隐藏勾
						        layer.close(index);
			        });
			    } */

			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/essential.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//文件下載
			/*   function downloadFile(fileName) {
			    $("input[name='fileName']").val(fileName);
			    $("#download_form_id").submit();
			  } */

			function download(id, key) {
				var form = $("<form>");
				form.attr('style', 'display:none');
				form.attr('method', 'post');
				form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
				$('body').append(form);
				form.submit();
			}
		</script>

		<script type="text/javascript">
			function jump(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierAudit/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
				}
				if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
				}
				if(str == "materialProduction") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
				}
				if(str == "materialSales") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
				}
				if(str == "engineering") {
					action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
				}
				if(str == "serviceInformation") {
					action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
				}
				if(str=="items"){
			    action = "${pageContext.request.contextPath}/supplierAudit/items.html";
			  }
				if(str == "product") {
					action = "${pageContext.request.contextPath}/supplierAudit/product.html";
				}
				if(str == "applicationForm") {
					action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
				}
				if(str == "reasonsList") {
					action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
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
						<a href="#">供应商管理</a>
					</li>
					<li>
						<a href="#">供应商审核</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class="content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<%-- <ul class="nav nav-tabs bgdd">
	        <li class=""><a>详细信息</a></li>
	        <li class="active"><a id="financial">财务信息</a></li>
	        <li class=""><a >股东信息</a></li>
	        <c:if test="${fn:contains(supplierTypeNames, '生产')}">
	        <li class=""><a >物资-生产专业信息</a></li>
	        </c:if>
	        <c:if test="${fn:contains(supplierTypeNames, '销售')}">
	        <li class=""><a >物资-销售专业信息</a></li>
	        </c:if>
	        <c:if test="${fn:contains(supplierTypeNames, '工程')}">
	        <li class=""><a >工程-专业信息</a></li>
	        </c:if>
	        <c:if test="${fn:contains(supplierTypeNames, '服务')}">
	        <li class=""><a >服务-专业信息</a></li>
	        </c:if>
	        <li class=""><a >品目信息</a></li>
	        <li class=""><a >产品信息</a></li>
	        <li class=""><a >申请表</a></li>
	        <li class=""><a >审核汇总</a></li>
	        </ul> --%>

					<ul class="nav nav-tabs bgdd">
						<li onclick="jump('essential')">
							<a aria-expanded="false" href="#tab-1">详细信息</a>
							<i></i>
						</li>
						<li class="active" onclick="jump('financial')">
							<a aria-expanded="true" href="#tab-2" data-toggle="tab">财务信息</a>
							<i></i>
						</li>
						<li onclick="jump('shareholder')">
							<a aria-expanded="false" href="#tab-3">股东信息</a>
							<i></i>
						</li>
						<c:if test="${fn:contains(supplierTypeNames, '生产')}">
							<li onclick="jump('materialProduction')">
								<a aria-expanded="false" href="#tab-4">生产信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '销售')}">
							<li onclick="jump('materialSales')">
								<a aria-expanded="false" href="#tab-4">销售信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '工程')}">
							<li onclick="jump('engineering')">
								<a aria-expanded="false" href="#tab-4">工程信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '服务')}">
							<li onclick="jump('serviceInformation')">
								<a aria-expanded="false" href="#tab-4">服务信息</a>
								<i></i>
							</li>
						</c:if>
						<li onclick = "jump('items')">
	              <a aria-expanded="false" href="#tab-4" >品目信息</a>
	               <i></i>
	          </li>
						<li onclick="jump('product')">
							<a aria-expanded="false" href="#tab-4">产品信息</a>
							<i></i>
						</li>
						<li onclick="jump('applicationForm')">
							<a aria-expanded="false" href="#tab-4">申请表</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-4">审核汇总</a>
						</li>
					</ul>

					<form id="form_id" action="" method="post">
						<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
					</form>

					<c:forEach items="${financial}" var="f" varStatus="vs">
						<h2 class="count_flow"><i>${vs.index + 1}</i>${f.year }年财务</h2>
						<ul class="ul_list count_flow">
							<table class="table table-bordered  table-condensed table-hover">
								<thead>
									<tr>
										<!-- <th class="info">序号</th> -->
										<th class="info w50">年份</th>
										<th class="info">会计事务所名称</th>
										<th class="info">事务所联系电话</th>
										<th class="info">审计人姓名</th>
										<th class="info">指标</th>
										<th class="info">资产总额(万元)</th>
										<th class="info">负债总额(万元)</th>
										<th class="info">净资产总额(万元)</th>
										<th class="info">营业收入(万元)</th>
										<th class="info w50">操作</th>
									</tr>
								</thead>
								<tr>
									<%-- <td class="tc">${vs.index + 1}</td> --%>
									<td class="tc w50" id="${f.id }">${f.year } </td>
									<td class="tc">${f.name }</td>
									<td class="tc">${f.telephone }</td>
									<td class="tc">${f.auditors }</td>
									<td class="tc">${f.quota }</td>
									<td class="tc">${f.totalAssets }</td>
									<td class="tc">${f.totalLiabilities }</td>
									<td class="tc">${f.totalNetAssets}</td>
									<td class="tc">${f.taking}</td>
									<td class="tc w50">
										<a onclick="reason('${f.id}','财务信息');" id="${f.id}_hidden1" class="btn">审核</a>
										<p id="${f.id}_show" class="b red">×</p>
									</td>
								</tr>
							</table>
							<table class="table table-bordered  table-condensed table-hover">
								<thead>
									<tr>
										<!-- <th class="info">序号</th> -->
										<th class="info w50">年份</th>
										<th class="info">财务利润表</th>
										<th class="info">审计报告的审计意见</th>
										<th class="info">资产负债表</th>
										<th class="info">现金流量表</th>
										<th class="info">所有者权益变动表</th>
										<th class="info w50">操作</th>
									</tr>
								</thead>
								<tbody id="finance_attach_list_tbody_id">
									<tr>
										<%-- <td class="tc">${vs.index + 1}</td> --%>
										<td class="tc w50">${f.year}</td>
										<td class="tc">
											<a class="mt3 color7171C6" href="javascript:download('${f.auditOpinionId}', '${sysKey}')">${f.auditOpinion}</a>
										</td>
										<td class="tc">
											<a class="mt3 color7171C6" href="javascript:download('${f.liabilitiesListId}', '${sysKey}')">${f.liabilitiesList}</a>
										</td>
										<td class="tc">
											<a class="mt3 color7171C6" href="javascript:download('${f.profitListId}', '${sysKey}')">${f.profitList}</a>
										</td>
										<td class="tc">
											<a class="mt3 color7171C6" href="javascript:download('${f.cashFlowStatementId}', '${sysKey}')">${f.cashFlowStatement}</a>
										</td>
										<td class="tc">
											<a class="mt3 color7171C6" href="javascript:download('${f.changeListId}', '${sysKey}')">${f.changeList}</a>
										</td>
										<td class="tc w50">
											<a onclick="reason('${f.id}','财务附件');" id="${f.id}_hidden2" class="btn">审核</a>
											<p id="${f.id}_fileShow" class="b red">×</p>
										</td>
									</tr>
								</tbody>
							</table>
						</ul>
					</c:forEach>
				</div>
				<div class="col-md-12 add_regist tc">
					<!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
					<a class="btn" type="button" onclick="lastStep();">上一步</a>
					<a class="btn" type="button" onclick="nextStep();">下一步</a>
				</div>
			</div>
		</div>

		<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
			<input type="hidden" name="fileName" />
		</form>
	</body>

</html>