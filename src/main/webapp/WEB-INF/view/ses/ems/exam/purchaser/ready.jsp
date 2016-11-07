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
	<script type="text/javascript" src="${pageContext.request.contextPath }/public/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/public/layer/extend/layer.ext.js"></script>
	<link href="${pageContext.request.contextPath }/public/layer/skin/layer.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath }/public/layer/skin/layer.ext.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		//生成考卷方法
		function generatePaper(){
			var paperNo = $("#paperNo").val().trim();
			if(paperNo==null||paperNo==""){
				layer.alert("请输入考试编号",{offset: ['222px', '390px']});
				$(".layui-layer-shade").remove();
				return;
			}
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"${pageContext.request.contextPath }/purchaserExam/entryPaperNumber.do?paperNo="+paperNo,
		       	success:function(data){
			    	if(data==1){
			    		window.location.href="${pageContext.request.contextPath }/purchaserExam/timing.html?paperNo="+paperNo;
			    	}else if(data==0){
			    		layer.alert("请输入正确的考试编号",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}else if(data==2){
			    		layer.alert("该考卷考试时间未开始",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}else if(data==3){
			    		layer.alert("该考卷考试时间已结束",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}else if(data==5){
			    		layer.alert("对不起,您不是该考卷的参考人员",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}else if(data==6){
			    		layer.alert("对不起,该考卷还没有设置参考人员",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}else if(data==7){
			    		layer.alert("您已参加本场考试,无法再登录",{offset: ['222px', '390px']});
						$(".layui-layer-shade").remove();
			    	}
		       	}
		    });
		}
	</script>

  </head>
  
  <body>
    <div class="container tc">
      <div class="border1 mt20 center p15_10 w350">
        <div class="mt20"><span class="fl mt5">输入考试编号：</span><input type="text" id="paperNo" class="mb0 fl"/>
        <div class="clear"></div>
        </div>
        <div class="tc mt20 clear"><input type="button" class="btn" value="生成考卷" onclick="generatePaper()"/></div>
      </div>
    </div>
  </body>
</html>
