<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="up" uri="/tld/upload" %>
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
			 method="post">
			 
			<up:upload id="bid" groups="bid,bond" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${typeId}" auto="fase" />
            <up:show showId="bid" groups="bid,bond" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${typeId}"/>
            
			<up:upload id="bond" groups="bid,bond" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${typeId}" auto="fase" />
            <up:show showId="bond" groups="bid,bond" businessId="${currSupplier.id}" sysKey="${sysKey}" typeId="${typeId}"/>
			
			<input name="projectId" type="hidden" value="${projectId}" />
			<input name="saleId" type="hidden" value="${saleId}" />
				是否缴纳标书费： <input
                name="statusBid" value="2" type="radio">是
                <input
                name="statusBid" value="1" type="radio">否
	</form>
	</div>
</body>
</html>
