<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<title>各包分配专家</title>
<script type="text/javascript">
	$(function() {
  		//获取查看或操作权限
       	var isOperate = $('#isOperate', window.parent.document).val();
       	if(isOperate == 0) {
       		//只具有查看权限，隐藏操作按钮
			$(":button").each(function(){ 
				$(this).hide();
            }); 
		}
    })
	function selectAll(obj){
		// 全选/全不选
		$("input[name='checkItem']").each(function(i,result){
			result.checked = obj.checked;
		});
	}
	var y = 150;
	var x = 300;
	function toTotal(){
	   var packageId = "${packageId}";
	   var projectId = "${projectId}";
	   $.ajax({
			url:"${pageContext.request.contextPath}/packageExpert/isGather.do",
			data:{"packageIds":packageId, "projectId":projectId},
			async:false,
			success:function (response) {
				if (response == "ok") {
					layer.confirm('是否确认结束评审?结束后将不能复核!', {
						btn : [ '确定', '取消' ]
					//按钮
					}, function() {
						$.ajax({
							 url:'${pageContext.request.contextPath}/packageExpert/scoreTotal.do',
							 data:{"packageId":packageId,"projectId":projectId},
							 async:false,
							 success:function(){
								 layer.alert("已结束",{offset: [y, x], shade:0.01});
								 $("#backId").attr("disabled",true);
								 $("#endId").attr("disabled",true);
							 },
							 error: function(){
								 layer.alert("结束失败,请稍后重试!",{offset: [y, x], shade:0.01});
							 }
						 });
					});
				} else {
					layer.alert(response, {
						offset : [ y, x ],
						shade : 0.01
					});
				} /* else {
					layer.alert(response + "已结束,请勿重复操作!", {
						offset : [ y, x ],
						shade : 0.01
					});
				} */
			}
		});
	}
	//查看供应商报价
	function supplierView(supplierId) {
		var projectId = $("#projectId").val();
		location.href = "${pageContext.request.contextPath}/packageExpert/supplierQuote.html?projectId="
				+ projectId + "&supplierId=" + supplierId;
	}
	
	//返回
	function goBack(url){
		$("#tab-6").load(url);
	}
	// 复核(退回)
	function backScore(){
		var count = 0;
		var expertIds = "";
		$("input[name='checkItem']").each(function(i,result){
			if (result.checked) {
				expertIds = expertIds + result.value + ",";
				count++;
			}
		});
		if (count == 0) {
			layer.alert("请至少选择一项再进行此操作!", {
				offset : [ y, x ],
				shade : 0.01
			});
		} else {
			$.ajax({
				url: "${pageContext.request.contextPath}/packageExpert/backScore.html?projectId=${projectId}&packageId=${packageId}&expertId=" + expertIds,
				async: true,
				success: function () {
					layer.alert("复核成功!", {
						offset : [ y, x ],
						shade : 0.01
					});
					$("#tab-6").load("${pageContext.request.contextPath}/packageExpert/detailedReview.html?packageId=${packageId}&projectId=${projectId}");
				}		
			});
			//window.location.href="${pageContext.request.contextPath}/packageExpert/backScore.html?projectId=${projectId}&packageId=${packageId}&expertId=" + expertIds;
		}
	}
	function showViewByExpertId(expertId){
		$.ajax({
			url: "${pageContext.request.contextPath}/packageExpert/isGrade.do",
			async: false,
			data: {"packageId": "${packageId}", "expertId": expertId},
			success: function (response) {
				if (response == '1') {
					window.open("${pageContext.request.contextPath}/packageExpert/showViewByExpertId.html?projectId=${projectId}&packageId=${packageId}&expertId=" + expertId, "评分详情");
				} else {
					layer.alert("该专家暂未评分!", {
						offset : [ y, x ],
						shade : 0.01
					});
				}
			}
		});
	}
	function showViewBySupplierId(supplierId){
		window.open("${pageContext.request.contextPath}/packageExpert/showViewBySupplierId.html?projectId=${projectId}&packageId=${packageId}&supplierId=" + supplierId, "评分详情");
	}
	function printRank(){
		var packageId = "${packageId}";
		var isEnd = "${isEnd}";
		if (isEnd != "1") {
			layer.alert("该包暂未结束评分!", {
				offset : [ y, x ],
				shade : 0.01
			});
		} else {
			window.open("${pageContext.request.contextPath}/packageExpert/printRank.html?packages=${packageId}", "打印汇总表");
		}
	}
</script>
</head>
<body>
		<h2 class="list_title">${pack.name}经济技术评审管理</h2>
	    <div class="mb5 fr">
	      <c:if test="${isEnd != 1}">
			  <button class="btn" id="endId" onclick="toTotal()" type="button">结束评审</button>
			  <button class="btn" id="backId" onclick="backScore()" type="button">复核评分</button>
		  </c:if>
		  <c:if test="${isEnd == 1}">
			  <button disabled="disabled" class="btn" id="endId" onclick="toTotal()" type="button">结束评审</button>
			  <button disabled="disabled" class="btn" id="backId" onclick="backScore()" type="button">复核评分</button>
		  </c:if>
		  <button class="btn btn-windows input" onclick="printRank()" type="button">打印汇总表</button>
		</div>
		<!--循环供应商  -->
		<div class="over_scroll col-md-12 col-xs-12 col-sm-12 p0 m0">
		<table class="table table-bordered table-condensed table-hover table-striped  p0 m_resize_table_width">
		  <thead>
			<tr>
			  <th><input type="checkbox" id="checkAll" onchange="selectAll(this)"></th>
			  <th class="info" width="120">专家/供应商</th>
			  <c:forEach items="${supplierList}" var="supplier">
			    <c:if test="${fn:contains(supplier.packages,packageId)}">
			  	  <th class="info" width="120"><%-- <a title="查看评分详情" href="javascript:showViewBySupplierId('${supplier.suppliers.id}');"> --%>${supplier.suppliers.supplierName}<!-- </a> --></th>
			  	</c:if>
			  </c:forEach>
			</tr>	
		  </thead>
		  <!-- 遍历该包内的专家,控制行数 -->
		  <c:forEach items="${expertList }" var="ext">
			<tr>
			  <td class="tc"><input type="checkbox" name="checkItem" value="${ext.expert.id}"></td>
			  <td class="tc"><a title="查看评分详情" href="javascript:showViewByExpertId('${ext.expert.id}');">${ext.expert.relName}</a></td>
			  <!-- 遍历该包供应商控制分数的显示 -->
			  <c:forEach items="${supplierList}" var="supplier">
			    <c:if test="${fn:contains(supplier.packages,packageId)}">
			      <c:set var="flag" value="0"/>
			      <!-- 遍历专家给供应商打的分数 -->
			  	  <c:forEach items="${expertScoreList}" var="score">
			  	    <c:if test="${score.packageId eq packageId and score.expertId eq ext.expert.id and score.supplierId eq supplier.suppliers.id}">
			  	      <!-- 如果有分数就设置flag=1 -->
			  	      <c:set var="flag" value="1"/>
			  	      <c:set var="scores" value="${score.score}"/>
			  	    </c:if>
			  	  </c:forEach>
			  	  <!-- 根据flag的值判断有没有分数值 -->
			  	  <c:if test="${flag eq '1'}">
			  	    <td class="tc">${scores}</td>
			  	  </c:if>
			  	  <c:if test="${flag eq '0'}">
			  	    <td class="tc">暂无分数</td>
			  	  </c:if>
			  	</c:if>
			  </c:forEach>
			</tr>
		  </c:forEach>
		  <c:if test="${review.isGather == 1}">
		    <tr>
			  	<td>合计:</td>
			  	<c:forEach items="${supplierList}" var="supplier">
     		      <td>
     		        <c:forEach items="${rankList}" var="rank">
     		          <c:if test="${supplier.suppliers.id eq rank.supplierId}">
     		            <span>${rank.econScore}(经济)+${rank.techScore}(技术)=${rank.sumScore}(总分)</span>
     		          </c:if>
     		        </c:forEach>
     		      </td>
	    		</c:forEach>
			  </tr>
			  <tr>
			  	<td>排名:</td>
			  	<c:forEach items="${supplierList}" var="supplier">
     		      <td>
     		        <c:forEach items="${rankList}" var="rank">
     		          <c:if test="${supplier.suppliers.id eq rank.supplierId}">
     		            <span>第${rank.rank}名</span>
     		          </c:if>
     		        </c:forEach>
     		      </td>
	    		</c:forEach>
			  </tr>
		  </c:if>
		</table>
		</div>
  <div class="clear col-md-12 pl20 mt10 tc">
	<input type="button" class="btn btn-windows back" value="返回" onclick="goBack('${pageContext.request.contextPath}/packageExpert/toScoreAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}')">
  </div>
  
  <script type="text/javascript">
		function resize_table_width() {
	        $('.m_resize_table_width').each(function () {
	            var table_width = 0;
	            var parent_width = $(this).parent().width();
	            $(this).find('thead th').each(function () {
	            	if(typeof($(this).attr('width')) != 'undefined') {
	            		table_width +=  parseInt($(this).attr('width'));
		            }
	            });
	            if (table_width > parent_width) {
		            $(this).css({
		                width: table_width,
		                maxWidth: table_width
		            });
	            }
	        });
	    }
	    $(function () {
	        resize_table_width();
	    });
	   	</script>
</body>
</html>
