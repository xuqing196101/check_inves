<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
 <jsp:include page="../../ses/bms/page_style/backend_common.jsp"></jsp:include>	
<script type="text/javascript">
	function getNumScore(expertListLength){
		for (var i = 0; i < parseInt(expertListLength); i++) { 
			var scores = document.getElementsByName("score_"+i);
			var scoreNum = 0;
			for (var j = 0; j < scores.length; i++) {
				if(scores[j].innerHTML != "暂未评分" && scores[j].innerHTML.trim() != ""){
					scoreNum = scoreNum + parseInt(scores[j].innerHTML.trim());
				}
			}
			document.getElementById("scoreNum_"+i).innerHTML = scoreNum;
		}
	}
</script>
</head>
<body onload="getNumScore('${length}')">
  <!-- 我的订单页面开始-->
  <div class="container">
  <div class="headline-v2">
    <h2>${supplierName }</h2>
  </div>   
  <!-- 表格开始-->
  <div class="content table_box">
    <table class="table table-bordered table-condensed table-hover table-striped">
	  <tr>
	    <th class="w150 tc">评审项/专家</th>
		<c:forEach items="${expertList}" var="expert">
		  <td class="tc">${expert.relName}</td>
        </c:forEach>
      </tr>
      <c:forEach items="${auditModelList}" var="auditModel">
	    <c:if test="${packageId eq auditModel.packageId and projectId eq auditModel.projectId}">
		  <tr>
            <th class="tc w150">${auditModel.markTermName}</td>
            <c:set var="count1" value="0"/>
            <c:forEach items="${expertList}" var="expert" varStatus="vs">
              <td class="tc">
              <c:forEach items="${scores}" var="score">
                <c:if test="${score.EXPERTID eq expert.id and supplierid eq score.SUPPLIERID and auditModel.markTermId eq score.MARKTERMID}">
                  <c:set var="count1" value="1"/>
                  <span name="score_${vs.index }">${score.SCORE}</span>
                </c:if>
              </c:forEach>
              <c:if test="${count1 ne '1'}"><span name="score_${vs.index }">暂未评分</span></c:if>
              </td>
            </c:forEach>
		  </tr>
		</c:if>
      </c:forEach>
      <c:forEach items="${expertList}" var="expert" varStatus="vs">
      <tr>
        <th class="tc w150">合计</th>
        <td class="tc" id="scoreNum_${vs.index }"></td>
      </tr>
      </c:forEach>
	</table>

   </div>
      <div id="pagediv" align="center"><input type="button" class="btn btn-windows back" value="返回" onclick="javascript:window.location.href='${pageContext.request.contextPath}/packageExpert/detailedReview.html?projectId=${projectId }&packageId=${packageId}'"></div>
   </div>
</body>
</html>
