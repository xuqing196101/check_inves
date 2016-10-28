<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>" target="open_bidding_main">

<title>上传</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


</head>
<script src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">
	function upload(){
		$("form:first").submit();
	}
</script>
<body>
	<!-- 表格开始-->
	<div class="content padding-left-25 padding-right-25 padding-top-0">
		<form action="<%=basePath%>saleTender/upload.do"
			enctype="multipart/form-data" method="post">
			<input name="projectId" type="hidden" value="${projectId}" />
			<input name="saleId" type="hidden" value="${saleId}" />
			发票上传：<input name="bill" type="file"> 打印凭证： <input
				name="voucher" type="file">
				是否缴纳标书费： <input
                name="statusBid" value="2" type="radio">是
                <input
                name="statusBid" value="1" type="radio">否
<!-- 		<div class="container padding-left-50 "> -->
<!-- 			<div class="col-md-12 pl20"> -->
<!-- 				<input class="btn btn-windows withdraw" type="submit" value="提交"/> -->
<!-- 				<input class="btn btn-windows add" type="submit" value="关闭"/> -->
<!-- 			</div> -->
<!-- 	</div> -->
	</form>
	</div>
</body>
</html>
