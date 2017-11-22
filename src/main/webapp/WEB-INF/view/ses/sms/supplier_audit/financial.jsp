<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>财务信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/essential.js"></script>
		<style type="text/css">
			td {
				cursor: pointer;
			}
			.icon_edit,.icon_sc{
       	padding: 5px;
      }
		</style>
		<script type="text/javascript">

			function download(id, key) {
				var form = $("<form>");
				form.attr('style', 'display:none');
				form.attr('method', 'post');
				form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
				$('body').append(form);
				form.submit();
			}

		</script>

	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a>支撑环境</a>
					</li>
					<li>
						<a>供应商管理</a>
					</li>
					<c:if test="${sign == 1}">
						<li>
							<a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1')">供应商审核</a>
						</li>
					</c:if>
					<c:if test="${sign == 2}">
						<li>
							<a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=2')">供应商复核</a>
						</li>
					</c:if>
					<c:if test="${sign == 3}">
						<li>
							<a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=3')">供应商实地考察</a>
						</li>
					</c:if>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class="content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<%-- <%@include file="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp"%> --%>
          <jsp:include page="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp">
          	<jsp:param value="two" name="currentStep"/>
           	<jsp:param value="${supplierId }" name="supplierId"/>
           	<jsp:param value="${supplierStatus }" name="supplierStatus"/>
           	<jsp:param value="${sign }" name="sign"/>
          </jsp:include>
					<%-- <form id="form_id" action="" method="post">
						<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
						<input id="status" name="supplierStatus" value="${supplierStatus}" type="hidden">
						<input type="hidden" name="sign" value="${sign}">
					</form> --%>

					<c:forEach items="${financial}" var="f" varStatus="vs">
						<h2 class="count_flow"><i>${vs.index + 1}</i>${f.year }年财务（金额单位：万元）</h2>
						<ul class="ul_list count_flow">
							<table class="table table-bordered  table-condensed table-hover m_table_fixed_border">
								<thead>
									<tr>
										<!-- <th class="info">序号</th> -->
										<th class="info w50">年份</th>
										<th class="info" width="23%">会计事务所名称</th>
										<th class="info" width="13%">事务所联系电话</th>
										<th class="info" width="23%">审计人姓名（2人）</th>
										<!-- <th class="info">指标</th> -->
										<th class="info">资产总额</th>
										<th class="info">负债总额</th>
										<th class="info">净资产总额</th>
										<th class="info">营业收入</th>
										<th class="info w50">操作</th>
									</tr>
								</thead>
								<tr>
									<%-- <td class="tc">${vs.index + 1}</td> --%>
									<td class="tc w50" id="${f.id }">${f.year } </td>
									<td class="tl" id="name_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_name'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'finance_page','name','${f.id}','3');"</c:if>>${f.name }</td>
									<td class="tc" id="telephone_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_telephone'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'finance_page','telephone','${f.id}','3');"</c:if>>${f.telephone }</td>
									<td class="tl" id="auditors_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_auditors'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'finance_page','auditors','${f.id}','3');"</c:if>>${f.auditors }</td>
									<%-- <td class="tc">${f.quota }</td> --%>
									<td class="tc" id="totalAssets_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_totalAssets'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'finance_page','totalAssets','${f.id}','3');"</c:if>>${f.totalAssets }</td>
									<td class="tc" id="totalLiabilities_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_totalLiabilities'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'finance_page','totalLiabilities','${f.id}','3');"</c:if>>${f.totalLiabilities }</td>
									<td class="tc" id="totalNetAssets_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_totalNetAssets'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'finance_page','totalNetAssets','${f.id}','3');"</c:if>>${f.totalNetAssets}</td>
									<td class="tc" id="taking_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_taking'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'finance_page','taking','${f.id}','3');"</c:if>>${f.taking}</td>
									<td class="tc w50">
										<%-- <c:if test="${!fn:contains(unableField,f.id.concat('_info'))}">
											<a onclick="reason('${f.id}_info','财务信息','${f.year}');" id="${f.id}_info_hidden" class="editItem">
												<c:if test="${!fn:contains(auditField,f.id.concat('_info'))}">
	                        <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                      </c:if>
	                      <c:if test="${fn:contains(auditField,f.id.concat('_info'))}">
	                        <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                      </c:if>
											</a>
										</c:if>
										<c:if test="${fn:contains(unableField,f.id.concat('_info'))}">
                      <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                    </c:if> --%>
                    <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
                    <c:set var="iconCls" value="icon_edit" />
                    <c:if test="${!fn:contains(unableField,f.id.concat('_info')) && fn:contains(auditField,f.id.concat('_info'))}">
                    	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
                    </c:if>
                    <c:if test="${fn:contains(unableField,f.id.concat('_info'))}">
                      <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
                      <c:set var="iconCls" value="icon_sc" />
                    </c:if>
                    <img src="${iconUrl}" class="${iconCls}"
                    onclick="auditList(this,'basic_page','${f.id}_info','财务信息','${f.year}年');" />
									</td>
								</tr>
							</table>
							
							<table class="table table-bordered  table-condensed table-hover m_table_fixed_border">
							<thead>
								<tr>
									<th class="w50 info">年份</th>
									<th class="info">审计报告书中的审计报告</th>
									<th class="info">资产负债表</th>
									<th class="info">财务利润表</th>
									<th class="info">现金流量表</th>
									<th class="info">所有者权益变动表</th>
									<th class="info w50">操作</th>
								</tr>
							</thead>
							<tbody id="finance_attach_list_tbody_id">
								<tr class="tc">
									<td class="tc w50" id="${f.id }_file">${f.year}</td>
									<td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierAuditOpinion))}">style="border: 1px solid #FF8C00;"</c:if>>
										<u:show showId="fina_${vs.index}_audit" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierAuditOpinion}" sysKey="${sysKey}" />
									</td>
									<td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierLiabilities))}">style="border: 1px solid #FF8C00;"</c:if>>
										<u:show showId="fina_${vs.index}_lia" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierLiabilities}" sysKey="${sysKey}" />
									</td>
									<td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierProfit))}">style="border: 1px solid #FF8C00;"</c:if>>
										<u:show showId="fina_${vs.index}_pro" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierProfit}" sysKey="${sysKey}" />
									</td>
									<td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierCashFlow))}">style="border: 1px solid #FF8C00;"</c:if>>
										<u:show showId="fina_${vs.index}_cash" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierCashFlow}" sysKey="${sysKey}" />
						 		  </td>
									<td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierOwnerChange))}">style="border: 1px solid #FF8C00;"</c:if>>
										<u:show showId="fina_${vs.index}_change" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierOwnerChange}" sysKey="${sysKey}" />
								  </td>
								  <td class="tc w50">
								  	<%-- <c:if test="${!fn:contains(unableField,f.id.concat('_file'))}">
											<a onclick="reason('${f.id}_file','财务附件','${f.year}');" id="${f.id}_file_hidden" class="editItem">
												<c:if test="${!fn:contains(auditField,f.id.concat('_file'))}">
	                        <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                      </c:if>
	                      <c:if test="${fn:contains(auditField,f.id.concat('_file'))}">
	                        <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                      </c:if>
											</a>
										</c:if>
										<c:if test="${fn:contains(unableField,f.id.concat('_file'))}">
                      <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                    </c:if> --%>
                    <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
                    <c:set var="iconCls" value="icon_edit" />
                    <c:if test="${!fn:contains(unableField,f.id.concat('_file')) && fn:contains(auditField,f.id.concat('_file'))}">
                    	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
                    </c:if>
                    <c:if test="${fn:contains(unableField,f.id.concat('_file'))}">
                      <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
                      <c:set var="iconCls" value="icon_sc" />
                    </c:if>
                    <img src="${iconUrl}" class="${iconCls}"
                    onclick="auditList(this,'basic_page','${f.id}_file','财务附件','${f.year}年');" />
								  </td>
								</tr>
							</tbody>
						</table>
							
							
							<%-- <table class="table table-bordered  table-condensed table-hover m_table_fixed_border">
								<thead>
									<tr>
										<!-- <th class="info">序号</th> -->
										<th class="info w50">年份</th>
										<th class="info">财务利润表</th>
										<th class="info">审计报告书中的审计报告</th>
										<th class="info">资产负债表</th>
										<th class="info">现金流量表</th>
										<th class="info">所有者权益变动表</th>
										<th class="info w50">操作</th>
									</tr>
								</thead>
								<tbody id="finance_attach_list_tbody_id">
									<tr>
										<td class="tc">${vs.index + 1}</td>
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
							</table> --%>
						</ul>
					</c:forEach>
				</div>
				<div class="col-md-12 add_regist tc">
					<a class="btn" type="button" onclick="toStep('one');">上一步</a>
					<c:if test="${isStatusToAudit}">
            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="tempAudit();">暂存</a>
          </c:if>
					<a class="btn" type="button" onclick="toStep('three');">下一步</a>
				</div>
			</div>
		</div>

		<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
			<input type="hidden" name="fileName" />
		</form>
	</body>

</html>