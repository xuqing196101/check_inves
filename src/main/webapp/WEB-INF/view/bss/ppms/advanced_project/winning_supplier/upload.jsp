<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="up" uri="/tld/upload" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${pageContext.request.contextPath}/">

<title>确定中标供应商</title>
<%@ include file="/WEB-INF/view/common.jsp"%>
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
  	            url:'${pageContext.request.contextPath}/winningSupplier/getFilesOther.do',
  	            data:$('#form1').serialize(),// 你的formid
  	            async: false,
  	            success: function(data) {
  	                var map =data;
  	                if(map=="SCCUESS"){
  	                	var el = document.createElement("a");
  	                	document.body.appendChild(el);
  	                	el.href = "${pageContext.request.contextPath}/winningSupplier/selectSupplier.do?projectId=${projectId}&&flowDefineId=${flowDefineId}"; //url 是你得到的连接
  	                	el.target = '_parent'; //指定在新窗口打开
  	                	el.click();
  	                	document.body.removeChild(el);
  	                }else{
  	                  layer.msg("请上传");
  	                }
  	                
  	            }
  	        });
  	    
  	  }
  
</script>
<body>
	<!-- 表格开始-->
  <div class="content">
    <form  method="post" id="form1">
      <input name="saleId" type="hidden" value="${saleId}"  />
      <input name="projectId" type="hidden" value="${projectId}" />
      <input name="packageId" type="hidden" value="${packageId}">
      <input name="typeId" type="hidden" value="${checkPassBgyj}">
      <input name="checkPassId" type="hidden" value="${checkPassId}">
      <input name="wonPrice" type="hidden" value="${wonPrice }">
             <ul class="demand_list">
                <li>
                      <label class="fl"><span class="red textspan fl">*</span>变更依据:</label>
                        <up:upload  id="bgyj" btnClass="fl"  businessId="${packageId}" sysKey="${tenderKey}" typeId="${checkPassBgyj}" auto="true" />
                        <up:show showId="bgyj"   businessId="${packageId}" sysKey="${tenderKey}" typeId="${checkPassBgyj}"/>           
                </li>
            </ul>
  </form>
  </div>
</body>
</html>
