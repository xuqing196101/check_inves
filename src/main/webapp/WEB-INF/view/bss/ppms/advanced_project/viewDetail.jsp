<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
  <%@ include file="/WEB-INF/view/common.jsp"%>
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js" ></script>
  <script type="text/javascript">
</script>
</head>
  
<body>
  <!-- 录入采购计划开始-->
  <div class="container">
    <!-- 项目戳开始 -->
    <div class="content" id="content">
      <c:if test="${lists != null }">
        <table id="table" class="table table-bordered table-condensed table-hover table_wrap">
          <thead>
            <tr>
              <th class="info w50">序号</th>
              <th class="info">需求部门</th>
              <th class="info">物资名称</th>
              <th class="info">规格型号</th>
              <th class="info">质量技术标准</th>
              <th class="info">计量单位</th>
              <th class="info">采购数量</th>
              <th class="info">交货期限</th>
              <th class="info">采购方式建议</th>
              <th class="info">供应商名称</th>
              <th class="info">是否申请办理免税</th>
              <th class="info">物资用途（进口）</th>
              <th class="info">使用单位（进口）</th>
              <th class="info">备注</th>
            </tr>
          </thead>
          <c:forEach items="${lists}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50">${obj.serialNumber}</td>
              <td class="tc">${obj.department}</td>
              <td class="tc">${obj.goodsName}</td>
              <td class="tc">${obj.stand}</td>
              <td class="tc">${obj.qualitStand}</td>
              <td class="tc">${obj.item}</td>
              <td class="tc">${obj.purchaseCount}</td>
              <td class="tc">${obj.deliverDate}</td>
              <td class="tc">
                <c:forEach items="${kind}" var="kind" >
                  <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}
                </c:forEach>
              </td>
              <td class="tc">${obj.supplier}</td>
              <td class="tc">${obj.isFreeTax}</td>
              <td class="tc">${obj.goodsUse}</td>
              <td class="tc">${obj.useUnit}</td>
              <td class="tc">${obj.memo}</td>
            </tr>
          </c:forEach>  
        </table> 
      </c:if>
    </div>
  </div>
</body>
</html>
