<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude_items.js"></script>
<script
	src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude_project_item.js"></script>
<title>产品类别工程</title>
</head>
<body>
	<div class="margin-top-10  ">
		<div class="tab-pane fade active in">
		<input id="supplierId"  type="hidden" value="${supplierId }">
        <input id="auditType"  type="hidden" value="${auditType }">
        <input id="ids"  type="hidden" value="${ids }">
        <input id="count"  type="hidden" value="0">
        <input id="tablerId"  type="hidden" value="${tablerId}">
			<c:choose>
				<c:when test="${not empty showProject }">
					<table class="table table-bordered m_table_fixed_border">
						<thead>
							<tr>
								<th class="info tc w50">序号</th>
								<th class="info tc" >产品类别</th>
								<th class="info tc">资质类型</th>
								<th class="info tc" >证书编号</th>
								<th class="info tc" >专业类别</th>
								<th class="info tc" >资质等级</th>
								<th class="info tc">证书图片</th>
								<th class="info tc">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${showProject }" var="cate" varStatus="vs">
								<tr>
									<td class="w50">${vs.index + 1}</td>
									<td><c:choose>
										<c:when test="${cate.fourthNode!=null}">
                      ${cate.fourthNode}
                    </c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${cate.thirdNode!=null}">
                          ${cate.thirdNode}
                        </c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${cate.secondNode!=null}">
                              ${cate.secondNode}
                            </c:when>
														<c:otherwise>
                              ${cate.firstNode}
                            </c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
									</td>
									<%--<td><- c:forEach items="${cate.typeList}" var="type">
											<c:if test="${cate.qualificationType eq type.id}">${type.name}</c:if>
										</c:forEach> </td> --%>
									<td <c:if test="${fn:contains(field,'certType')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('certType','certType_${vs.index}');"</c:if> id="certType_${vs.index}">${cate.supplierAptitute.certType}</td>
									<td <c:if test="${fn:contains(field,'certCode')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('certCode','certCode_${vs.index}');"</c:if> id="certCode_${vs.index}">${cate.supplierAptitute.certCode}</td>
									<td <c:if test="${fn:contains(field,'professType')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('professType','professType_${vs.index}');"</c:if> id="professType_${vs.index}">${cate.supplierAptitute.professType}</td>
									<td <c:if test="${fn:contains(field,'aptituteLevel')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('aptituteLevel','aptituteLevel_${vs.index}');"</c:if> id="aptituteLevel_${vs.index}">${cate.supplierAptitute.aptituteLevel}</td>
									<td <c:if test="${fn:contains(fileModifyField,cate.fileId) and !empty cate.fileId}">style="border: 1px solid #FF8C00;"</c:if>><div class="w110 fl">
											<u:show showId="eng_show_${vs.index}"
												businessId="${cate.fileId}" typeId="${typeId}"
												sysKey="${sysKey}" delete="false" />
										</div>
									</td>
                  <c:if test="${!fn:contains(unableField,cate.supplierItemId)}">
                  	<td class="tc info" onclick="reasonProject('${ind}','${cate.supplierItemId}','${cate.itemsName}','${cate.supplierAptitute.certType}')">
                  		<c:if test="${!fn:contains(auditField,cate.supplierItemId)}">
                  			<a href="javascript:void(0);"><img id="show_td" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></a>
                  		</c:if>
                  		<c:if test="${fn:contains(auditField,cate.supplierItemId)}">
                  			<a href="javascript:void(0);"><img id="show_td" src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'></a>
                  		</c:if>
	                  </td>
                  </c:if>
                  <c:if test="${fn:contains(unableField,cate.supplierItemId)}">
                  	<td class="tc info" onclick="javascript:layer.msg('该条信息已审核并退回过！');">
	                    <a href="javascript:void(0);"><img id="show_td" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
	                  </td>
                  </c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<span class="tc info fl w200">没有数据</span>
				</c:otherwise>
			</c:choose>
		</div>
		<div id="showdiv" align="right"></div>
	</div>
</body>
</html>