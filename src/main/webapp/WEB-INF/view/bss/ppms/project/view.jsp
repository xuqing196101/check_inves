<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
  <%@ include file="/WEB-INF/view/common.jsp"%>
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js" ></script>
  <style type="text/css">
    td {
	width: 200px;
	height:50px;
	word-wrap:break-word;
	word-break:break-all;
    }
    #wrap{word-break:break-all; width:200px;}
   /*  #wrap{word-wrap:break-word; width:200px;} */
  </style>
  <script type="text/javascript">
</script>
</head>
  
<body>
  <!-- 录入采购计划开始-->
  <div class="container">
    <!-- 项目戳开始 -->
    <div class="content" id="content">
      <c:if test="${lists != null }">
        <table id="table" class="table table-bordered table-condensed table-hover table_wrap" >
          <thead>
            <tr class="space_nowrap">
              <th class="info w50">序号</th>
              <th class="info w80">需求部门</th>
              <th class="info w80">物资名称</th>
              <th class="info w80">规格型号</th>
              <th class="info w80">质量技术标准<br/>(技术参数)</th>
              <th class="info w80">计量<br/>单位</th>
              <th class="info w80">采购<br/>数量</th>
              <th class="info w80">交货期限</th>
              <th class="info w100">采购方式</th>
              <th class="info w100">供应商名称</th>
              <th class="info w80">是否申请<br/>办理免税</th>
              <th class="info w80">物资用途<br/>（进口）</th>
              <th class="info w80">使用单位<br/>（进口）</th>
              <th class="info w160">备注</th>
            </tr>
          </thead>
          <c:forEach items="${lists}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50"><div class="w50">${obj.serialNumber}</div></td>
              <td class=""><div class="w80">${obj.department}</div></td>
              <td class=""><div class="w80" id="wrap">${obj.goodsName}</div></td>
              <td class=""><div class="w80">${obj.stand}</div></td>
              <td class=""><div class="w80">${obj.qualitStand}</div></td>
              <td class="tc"><div class="w80">${obj.item}</div></td>
              <td class="tc"><div class="w80">${obj.purchaseCount}</div></td>
              <td class=""><div class="w80">${obj.deliverDate}</div></td>
              <td class="tc">
                <div class="w100">
                 <c:choose>
              <c:when test="${obj.detailStatus==0 }">
              
              </c:when>
              <c:otherwise>
                    <c:forEach items="${kind}" var="kind" >
                      <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if> ${kind.name}
                    </c:forEach>
              </c:otherwise>
            </c:choose>
               </div>
              </td>
              <td class=""><div class="w100">${obj.supplier}</div></td>
              <td class="tc"><div class="w80">${obj.isFreeTax}</div></td>
              <td class=""><div class="w80">${obj.goodsUse}</div></td>
              <td class=""><div class="w80">${obj.useUnit}</div></td>
              <td class=""><!-- <div class="w160" id="wrap"> -->${obj.memo}<!-- </div> --></td>
            </tr>
          </c:forEach>  
        </table> 
      </c:if>
    </div>
  </div>
</body>
</html>
