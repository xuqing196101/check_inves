<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script
    src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/aptitude_material_item.js"></script>
<title>产品类别物资item</title>
</head>
<body>
    <div class="margin-top-10  ">
    <div class="tab-pane fade active in p20" >
    <input id="supplierId"  type="hidden" value="${supplierId }">
    <input id="auditType"  type="hidden" value="${auditType }">
    <input id="ids"  type="hidden" value="${ids }">
    <input id="count"  type="hidden" value="0">
    <input id="tablerId"  type="hidden" value="${tablerId}">
      <c:choose>
      <c:when test="${not empty beanList }">
       <table class="table table-bordered">
         <tbody>
         <c:forEach items="${beanList }" var="obj" varStatus="vs">
         <tr><%-- <td class="tc info">${obj.categoryName}</td>  --%>
         <td class="tc info">
         <c:forEach items="${obj.list }" var="qua">
            <div class="tc info fl w400">
            <span class="tc info fl">
            <div class="m_inline">${qua.name}</div>
            <div class="m_inline"><u:show showId="showfile${qua.flag}" delete="false" businessId="${qua.flag}" sysKey="${sysKey}" typeId="${typeId }"/></div>
            </span>
            </div>
            </c:forEach>
            </td>
             <td class="tc info"  onclick="reasonProject('${ids }','${obj.categoryId }','${obj.categoryName }','${vs.index + 1}')">
               <a href="javascript:void(0);"><img id="show_td" src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></a>
             </td>
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