<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>准备页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		//生成考卷方法
		function generatePaper(){
			var paperNo = $("#paperNo").val().trim();
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"<%=path%>/purchaserExam/entryPaperNumber.do?paperNo="+paperNo,
		       	success:function(data){
			    	if(data==1){
			    		window.location.href="<%=path%>/purchaserExam/timing.html?paperNo="+paperNo;
			    	}else if(data==0){
			    		alert("没有当前考卷");
			    	}
		       	}
		    });
		}
	</script>

  </head>
  
  <body>
    <div>输入考试编号<input type="text" id="paperNo"/></div>
    <input type="button" value="生成考卷" onclick="generatePaper()"/>
  </body>
</html>
