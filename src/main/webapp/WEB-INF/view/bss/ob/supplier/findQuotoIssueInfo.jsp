<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
	<head>
	<title>竞价信息查看页面</title>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
	<script type="text/javascript">
		// 查看文件
		function findFile(filePath){
			$.ajax({
				url: "${pageContext.request.contextPath }/open_bidding/downloadFile.do",
				type: "POST",
				data: {
					filePath: filePath
				},
				success: function(data) {
				}
			});
		}
		
		function showPrompt(id,selectID){
	 		  if(id){
	 		  $.ajax({
					async: false,
					url: "${pageContext.request.contextPath }/product/productType.do",
					type: "POST",
					data: {productId:id},
					success: function(data) {
					if(data){
						var quality = data.qualityTechnicalStandard;
						if(quality == null){
							quality = "无";
						}
		   	  			layer.tips("产品规格型号："+data.standardModel+"<br/>"+"质量技术标准："+quality, 
		   	    		'#'+selectID, {tips: [1, '#78BA32'],time:-1,area: ['500px', 'auto'],});
							}else{
							 inder=layer.tips("", 
			       	    '#'+selectID, {tips: [1, '#78BA32']});
							}
					      },error:function(){
					       layer.tips("错误！", 
			       	    '#'+selectID, {tips: [1, '#78BA32']});
					}
	         });
	     }
	 	}
		  //关闭
		function closePrompt(){
			layer.closeAll('tips');
		}
	</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">提供单价</a></li><li><a href="javascript:void(0)">信息查看</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
   <!-- 修改订列表开始-->
   <div class="container container_box">
   <div class="mt10">
	    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   </div> 
   <div>
    <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
		<ul class="ul_list">
			<%@ include file="/WEB-INF/view/bss/ob/supplier/biddingInfoCommon.jsp" %>
		</ul>
  </div> 
  <div class="clear" ></div>
  <form id="productForm" name="" method="post">
  	<input type="hidden" name="titleId" value="${ obProject.id }">
  	<c:if test="${ empty oBResultsInfoSecond }">
	  <div>
	    <h2 class="count_flow"><i>2</i>产品报价信息</h2>
	  		<%@ include file="/WEB-INF/view/bss/ob/supplier/findQuotoIssueInfoCommon.jsp" %>
	  </div>
	</c:if>
	 
	<c:if test="${ not empty oBResultsInfoSecond }">
	  <div>
	    <h2 class="count_flow"><i>2</i>第一轮产品报价信息</h2>
	  		<%@ include file="/WEB-INF/view/bss/ob/supplier/findQuotoIssueInfoCommon.jsp" %>
	  </div>
  	  <div>
      <h2 class="count_flow"><i>3</i>第二轮产品报价信息</h2>
  		    <%@ include file="/WEB-INF/view/bss/ob/supplier/findQuotoIssueInfoCommonSecond.jsp" %>
      </div>
	</c:if> 
  </form>
  
  <!-- 第一轮确认结果信息 -->
 	<c:if test="${not empty confirmResult && confirmFlag=='firstConfirm' }">
 		<c:if test="${ not empty oBResultsInfoSecond }">
 			<h2 class="count_flow"><i>4</i>第一轮确认结果信息</h2>
 		</c:if>
 		<c:if test="${ empty oBResultsInfoSecond }">
 			<h2 class="count_flow"><i>3</i>第一轮确认结果信息</h2>
 		</c:if>
		<%@ include file="/WEB-INF/view/bss/ob/supplier/resultIssueInfoCommon.jsp" %>
  	</div>
	</c:if>	
  <!-- 第二轮确认结果信息 -->
 	<c:if test="${not empty confirmResult && not empty confirmResultSecond && confirmFlag=='secondConfirm' }">
 		<c:if test="${ not empty oBResultsInfoSecond }">
 			<h2 class="count_flow"><i>4</i>第二轮确认结果信息</h2>
 		</c:if>
 		<c:if test="${ empty oBResultsInfoSecond }">
 			<h2 class="count_flow"><i>3</i>第二轮确认结果信息</h2>
 		</c:if>
		<%@ include file="/WEB-INF/view/bss/ob/supplier/resultIssueInfoCommon.jsp" %>
		
		<c:if test="${ not empty oBResultsInfoSecond }">
 			<h2 class="count_flow"><i>5</i>第二轮确认结果信息</h2>
 		</c:if>
 		<c:if test="${ empty oBResultsInfoSecond }">
 			<h2 class="count_flow"><i>4</i>第二轮确认结果信息</h2>
 		</c:if>
		<%@ include file="/WEB-INF/view/bss/ob/supplier/secondResultIssueInfoCommon.jsp" %>
	</c:if>	
 </div>
</body>
</html>