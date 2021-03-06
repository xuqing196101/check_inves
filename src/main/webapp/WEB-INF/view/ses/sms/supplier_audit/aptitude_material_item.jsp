<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<style type="text/css">
	.icon_edit,.icon_sc{
		cursor: pointer;
   	margin-bottom: 5px;
  }
</style>
<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/common.js"></script>
<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude_material_item.js"></script>
<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude_items.js"></script>
<title>产品类别物资item</title>
</head>
<body>
    <div class="margin-top-10  ">
    <div class="tab-pane fade active in p20" >
    <input id="supplierId"  type="hidden" value="${supplierId }">
    <input id="auditType"  type="hidden" value="${auditType }">
    <input id="ind"  type="hidden" value="${ind }">
    <input id="tablerId"  type="hidden" value="${tablerId}">
      <c:choose>
      <c:when test="${not empty beanList }">
      	<table class="table table-bordered m_table_fixed_border">
        	<tbody>
						<c:forEach items="${beanList }" var="obj" varStatus="vs">
							<tr><%-- <td class="tc info">${obj.categoryName}</td>  --%>
								<td class="tc info">
									<c:forEach items="${obj.list }" var="qua" varStatus="vss">
										<div class="tc info fl w400">
											<span class="tc info fl" <c:if test="${fn:contains(fileModifyField,qua.flag)}">style="border: 1px solid #FF8C00;"</c:if>>
									  		<input id="count${qua.id}"  type="hidden" value="${qua.auditCount}">
									  		<%-- <div class="m_inline" onclick="reasonProject('${ids }','${obj.categoryId }','${obj.categoryName }','${vss.index + 1}','${qua.id}','${qua.name}')">
									  			<a href="javascript:void(0);"><img id="show_td${qua.id}" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></a>&nbsp;&nbsp;${qua.name}
									  		</div> --%>
									  		<%-- <c:if test="${!fn:contains(unableField,obj.categoryId.concat('_').concat(qua.id))}">
                          <div class="m_inline" onclick="reasonProject('${ind}','${obj.categoryId }','${obj.categoryName }','${vss.index + 1}','${qua.id}','${qua.name}')">
										  			<c:if test="${!fn:contains(auditField,obj.categoryId.concat('_').concat(qua.id))}">
										  				<a href="javascript:void(0);"><img id="show_td${qua.id}" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></a>&nbsp;&nbsp;${qua.name}
										  			</c:if>
										  			<c:if test="${fn:contains(auditField,obj.categoryId.concat('_').concat(qua.id))}">
										  				<a href="javascript:void(0);"><img id="show_td${qua.id}" src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'></a>&nbsp;&nbsp;${qua.name}
										  			</c:if>
										  		</div>
                        </c:if>
									  		<c:if test="${fn:contains(unableField,obj.categoryId.concat('_').concat(qua.id))}">
                          <div class="m_inline" onclick="javascript:layer.msg('该条信息已审核并退回过！');">
										  			<a href="javascript:void(0);"><img id="show_td${qua.id}" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>&nbsp;&nbsp;${qua.name}
										  		</div>
                        </c:if> --%>
                        <c:set var="curField" value="${obj.categoryId.concat('_').concat(qua.id)}" />
                        <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
				                <c:set var="iconCls" value="icon_edit" />
				                <c:if test="${!fn:contains(unableField,curField) && fn:contains(auditField,curField)}">
				                	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
				                </c:if>
				                <c:if test="${fn:contains(unableField,curField)}">
				                  <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
				                  <c:set var="iconCls" value="icon_sc" />
				                </c:if>
				                <div class="m_inline" onclick="reasonProject(this,'${ind }','${obj.categoryId }','${obj.categoryName }','${vss.index + 1}','${qua.id}','${qua.name}');">
									  			<img src="${iconUrl}" class="${iconCls}"/>&nbsp;&nbsp;${qua.name}
									  		</div>
									  		<div class="m_inline"><u:show showId="showfile${qua.flag}" delete="false" businessId="${qua.flag}" sysKey="${sysKey}" typeId="${typeId }"/></div>
										 	</span>
										</div>
							   	</c:forEach>
							  </td>
						    <%-- td class="tc info"  onclick="reasonProject('${ids }','${obj.categoryId }','${obj.categoryName }','${vs.index + 1}')">
						      <a href="javascript:void(0);"><img id="show_td" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></a>
						    </td> --%>
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