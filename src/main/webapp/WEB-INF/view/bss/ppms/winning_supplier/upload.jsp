<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="up" uri="/tld/upload" %>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${pageContext.request.contextPath}/">

<title>确定中标供应商</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


</head>
<script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
<script type="text/javascript">

  	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		   if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}
  	function view(id){
  		window.location.href="${pageContext.request.contextPath}/templet/view.do?id="+id;
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
                      <label class="fl"><span class="red textspan">*</span>变更依据:</label>
                        <span>
                               <up:upload btnClass="fl" id="bgyj" groups="bgyj" businessId="${packageId}" sysKey="${tenderKey}" typeId="${checkPassBgyj}" auto="true" />
                        </span>
                        <up:show showId="bgyj"  groups="bgyj" businessId="${packageId}" sysKey="${tenderKey}" typeId="${saleTenderFpsc}"/>           
                </li>
            </ul>
  </form>
  </div>
</body>
</html>
