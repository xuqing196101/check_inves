<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
  <%@ include file="/WEB-INF/view/common.jsp"%>
  <script type="text/javascript">
</script>
</head>
  
<body>
  <!-- 录入采购计划开始-->
  <div class="container">
    <!-- 项目戳开始 -->
    <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto" id="content">
      <c:if test="${lists != null }">
        <table id="table" class="table table-bordered table-condensed lockout">
          <thead>
            <tr class="space_nowrap">
              <th class="info seq">序号</th>
              <th class="info department">需求部门</th>
              <th class="info goodsname">物资名称</th>
              <th class="info stand">规格型号</th>
              <th class="info qualitstand">质量技术标准<br/>(技术参数)</th>
              <th class="info item">计量<br/>单位</th>
              <th class="info purchasecount">采购<br/>数量</th>
              <th class="info deliverdate">交货期限</th>
              <th class="info purchasetype">采购方式</th>
              <th class="info purchasename">供应商名称</th>
              <th class="info freetax">是否申请<br/>办理免税</th>
              <th class="info goodsuse">物资用途<br/>（进口）</th>
              <th class="info useunit">使用单位<br/>（进口）</th>
              <th class="info memo">备注</th>
            </tr>
          </thead>
          <c:forEach items="${lists}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc seq">${obj.serialNumber}</td>
              <td class="">
                <div class="department">${obj.department}</div>
              </td>
              <td class="">
                <div class="goodsname">${obj.goodsName}</div>
              </td>
              <td class="">
                <div class="stand">${obj.stand}</div>
              </td>
              <td class="">
                <div class="qualitstand">${obj.qualitStand}</div>
              </td>
              <td class="tc">
                <div class="item">${obj.item}</div>
              </td>
              <td class="tc">
                <div class="purchasecount">${obj.purchaseCount}</div>
              </td>
              <td class="">
                <div class="deliverdate">${obj.deliverDate}</div>
              </td>
              <td class="tc">
                <div class="purchasetype">
                 <c:forEach items="${kind}" var="kind" >
                   <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}
                 </c:forEach>
                </div>
              </td>
              <td class="">
                <div class="purchasename">${obj.supplier}</div>
              </td>
              <td>
                <div class="freetax">${obj.isFreeTax}</div>
              </td>
              <td class="">
                <div class="goodsuse">${obj.goodsUse}</div>
              </td>
              <td class="">
                <div class="useunit">${obj.useUnit}</div>
              </td>
              <td class="">
                <div class="memo">${obj.memo}</div>
              </td>
            </tr>
          </c:forEach>  
        </table> 
      </c:if>
    </div>
  </div>
</body>
</html>
