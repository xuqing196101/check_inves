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
<jsp:include page="../../common.jsp"></jsp:include>	
<script type="text/javascript">
	function getNumScore(listLength){
		for (var i = 1; i <= listLength; i++) {
			var scores = document.getElementsByName("score_"+i);
			var scoreNum = 0;
			for (var j = 0; j < scores.length; j++) {
				if(scores[j].innerHTML != "暂未评分" && scores[j].innerHTML != ""){
					scoreNum = scoreNum + parseInt(scores[j].innerHTML);
				}
			}
			document.getElementById("scoreNum_"+i).innerHTML = scoreNum;
		}
	}
	
	 //退回
	function isBack(obj){
		//得到点击坐标。
	    var x,y;  
	    oRect = obj.getBoundingClientRect();  
		x=oRect.left;  
		y=oRect.top;  
	    var table = obj.parentNode.parentNode.parentNode.parentNode;
	    var checkbox = $("input[name='chkItem']");
		var ids; 
		var count = 0;
		$(checkbox).each(function(){ 
			if($(this).is(":checked")){
				ids=ids+$(this).val()+",";
				count++;
			}
		});
		var expertIds; 
		$(checkbox).each(function(){ 
			expertIds=expertIds+$(this).val()+",";
		});
		if(count>0){
			var index = layer.confirm('确定退回吗?', {icon: 3, title:'提示',offset: [y, x + 200], shade:0.01}, function(index){
				var url="${pageContext.request.contextPath}/packageExpert/backScore.html?projectId=${projectId}&expertId="+ids+"&packageId=${packageId}&supplierId=${supplierId}&expertIds="+expertIds;
				window.location.href=url;
			});
		} else {
			layer.alert("请至少选择一位专家",{offset: [y, x + 200], shade:0.01});
		}
		
	}
	 // 全选反选
	 $(function(){
		 $("#checkAll").change(function(){
			$("input[name='chkItem']").each(function(i,result){
				result.checked = !result.checked;
		 	});
		 });
	 });
</script>
</head>
<body onload="getNumScore('${length}')">
  <!-- 我的订单页面开始-->
  <div class="container">
  <div class="headline-v2">
    <h2>${supplierName }</h2>
  </div>
  <div class="ml20">
    <input type="button" value="退回" class="btn" onclick="isBack(this)">
  </div>   
  <!-- 表格开始-->
  <div class="content table_box">
    <table class="table table-bordered table-condensed table-hover table-striped">
	  <tr>
	    <th class="w150 tc"><span class="mr8">评审项/</span><input type="checkbox" value="" id="checkAll">专家</th>
		<c:forEach items="${expertList}" var="expert">
		  <td class="tc"><input type="checkbox" value="${expert.id }" name="chkItem"><span class="ml8">${expert.relName}</span></td>
        </c:forEach>
      </tr>
      <c:forEach items="${auditModelList}" var="auditModel">
	    <c:if test="${packageId eq auditModel.packageId and projectId eq auditModel.projectId}">
		  <tr>
            <th class="tc w150">${auditModel.markTermName}</th>
            
            <c:set var="countLength" value="0"/>
            <c:forEach items="${expertList}" var="expert" varStatus="vs">
              
              <c:set var="count1" value="0"/>
              <c:forEach items="${scores}" var="score">
                <c:if test="${score.EXPERTID eq expert.id and supplierId eq score.SUPPLIERID and auditModel.markTermId eq score.MARKTERMID}">
                  <c:set var="count1" value="1"/>
                  <c:set var="scoreNum" value="${score.SCORE }"></c:set>
                </c:if>
              </c:forEach>
              
              <c:if test="${count1 ne '1'}">
                <c:set var="countLength" value="${countLength + 1}"/>
                <td name="score_${countLength}" class="tc">暂未评分</td>
              </c:if>
              
              <c:if test="${count1 eq '1'}">
                <c:set var="countLength" value="${countLength + 1}"/>
                <td name="score_${countLength}" class="tc">${scoreNum }</td>
              </c:if>
              
            </c:forEach>
            
		  </tr>
		</c:if>
      </c:forEach>
      <c:forEach items="${expertList}" var="expert" varStatus="vs">
      </c:forEach>
      <tr>
        <th class="tc w150">合计</th>
        <c:forEach items="${expertList}" var="expert" varStatus="vs">
          <td class="tc" id="scoreNum_${vs.index+1}"></td>
        </c:forEach>
      </tr>
	</table>

   </div>
      <div align="center"><input type="button" class="btn btn-windows back" value="返回" onclick="javascript:window.location.href='${pageContext.request.contextPath}/packageExpert/detailedReview.html?projectId=${projectId }&packageId=${packageId}'"></div>
   </div>
</body>
</html>
