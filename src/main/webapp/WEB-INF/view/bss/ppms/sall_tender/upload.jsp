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

<script type="text/javascript">
	function upload(){
	    $.ajax({
            cache: true,
            type: "POST",
            dataType : "json",
            url:'<%=basePath%>saleTender/upload.do',
            data:$('#form1').serialize(),// 你的formid
            async: false,
            success: function(data) {
                var map =data;
                if(map=="sccuess"){
                      window.location.href = '<%=basePath%>/saleTender/list.html?projectId=${projectId}';
                }else{
                	layer.msg("请上传");
                }
                
            }
        });
		
	}
</script>
<body>
	<!-- 表格开始-->
	<div class="content padding-left-25 padding-right-25 padding-top-0">
		<form  method="post" id="form1">
		  <input name="saleId" type="hidden" value="${saleId}"  />
		  <input name="projectId" type="hidden" value="${projectId}" />
			发票上传:<up:upload id="bid" groups="bid,bond" businessId="${saleId}" sysKey="${tenderKey}" typeId="${saleTenderFpsc}" auto="true" />
                   <up:show showId="bid" groups="bid,bond" businessId="${saleId}" sysKey="${tenderKey}" typeId="${saleTenderFpsc}"/>           
		      打印凭证: 	<up:upload id="bond" groups="bid,bond" businessId="${saleId}" sysKey="${tenderKey}" typeId="${saleTenderDypz}" auto="true" />
            <up:show showId="bond" groups="bid,bond" businessId="${saleId}" sysKey="${tenderKey}" typeId="${saleTenderDypz}"/>
			
			
		
				是否缴纳标书费： <input
                name="statusBid" value="2" type="radio">是
                <input
                name="statusBid" value="1" type="radio">否
	</form>
	</div>
</body>
</html>
