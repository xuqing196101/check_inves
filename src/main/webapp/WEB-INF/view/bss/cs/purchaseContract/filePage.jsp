<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
function toprintmodel(){
	var text = $("#post_attach_show_disFileId").find("a").text();
	var flag = true;
	if(text==null || text==''){
		flag = false;
		layer.alert("请先上传附件",{offset: ['222px', '390px'], shade:0.01});
	}
	if(flag){
		$("#myForm").submit();
	}
}
</script>  
</head>

<body>
<form action="${pageContext.request.contextPath}/purchaseContract/printContract.html?id=${attachuuid}&status=${status}" method="post" target="_parent" id="myForm">
    <ul class="list-unstyled mt10" id="draftrevi">
 		<li class="tc col-md-12 col-sm-12 col-xs-12 mt20">
			<span class="col-md-4 col-sm-6 col-xs-4 p0 tc mt5">
				<div class="red star_red">*</div>草案批复意见上传：
			</span>
	    	<div class="col-md-8 col-sm-6 col-xs-8 p0">
	        <u:upload id="post_attach_up" businessId="${attachuuid}" sysKey="${attachsysKey}" typeId="${attachtypeId}" auto="true" />
			<u:show showId="post_attach_show" businessId="${attachuuid}" sysKey="${attachsysKey}" typeId="${attachtypeId}"/>
			</div>
		</li>
		<li class="tc col-md-12 col-sm-12 col-xs-12 mt20">
		 <input type="button" class="btn" onclick="toprintmodel()" value="确定"/>
		</li>
	</ul>
</form>
</body>
</html>
