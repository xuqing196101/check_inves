<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${pageContext.request.contextPath}/" >
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<%@ include file="../../../common.jsp"%>
<title>上传</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
    type="text/css">

</head>

<script type="text/javascript">
	function uploadBsf(){
	    $.ajax({
            cache: true,
            type: "POST",
            dataType : "json",
            url:'${pageContext.request.contextPath}/saleTender/uploadBsfAjax.do',
            data:$('#form1').serialize(),// 你的formid
            async: false,
            success: function(data) {
                var map =data;
                if(map=="success"){
                    $("#a").attr("href","${pageContext.request.contextPath}/saleTender/list.html?projectId=${projectId}");
                    var el=document.getElementById('a');
                  el.click();//触发打开事件
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
	<a id="a" target="_parent"></a>
		<form  method="post" id="form1">
		  <input name="saleId" type="hidden" value="${saleId}"  />
		  <input name="projectId" type="hidden" value="${projectId}" />
	           <ul class="demand_list">
	              <li>
                      <label class="fl"><span class="red textspan">*</span>发票上传:</label>
                        <span>
                               <up:upload btnClass="fl" id="bid" groups="bid,bond" businessId="${saleId}" sysKey="${tenderKey}" typeId="${saleTenderBsfFpsc}" auto="true" />
                        </span>
                        <up:show showId="bid"  groups="bid,bond" businessId="${saleId}" sysKey="${tenderKey}" typeId="${saleTenderBsfFpsc}"/>           
                   </li>
                </ul>
	</form>
	</div>
</body>
</html>
