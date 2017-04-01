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
	</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">提供单价</a></li><li><a href="javascript:void(0)">报价信息查看</a></li>
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
	  <div>
	    <h2 class="count_flow"><i>2</i>产品报价信息</h2>
	  		<%@ include file="/WEB-INF/view/bss/ob/supplier/findQuotoIssueInfoCommon.jsp" %>
	  </div>	 
  </form>
 </div>
</body>
</html>