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
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${ pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${ pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
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
			    		layer.alert("请输入正确的考试编号",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}else if(data==2){
			    		layer.alert("该考卷考试时间未开始",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}else if(data==3){
			    		layer.alert("该考卷考试时间已结束",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}else if(data==4){
			    		layer.alert("该考卷登录有效期已过",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}
		       	}
		    });
		}
	</script>

  </head>
  
  <body>
    <div class="container tc">
      <div class="border1 mt20 padding-bottom-20">
        <div class="mt20"><span>输入考试编号：</span><input type="text" id="paperNo" class="mb0"/></div>
        <div class="tc mt20"><input type="button" class="btn" value="生成考卷" onclick="generatePaper()"/></div>
      </div>
    </div>
  </body>
</html>
