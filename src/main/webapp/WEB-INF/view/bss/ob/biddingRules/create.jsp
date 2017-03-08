<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="/WEB-INF/view/common.jsp" %>
   <%@ include file="/WEB-INF/view/common/validate.jsp"%>
<title>创建竞价规则</title>

<script type="text/javascript">
		
	function submitForm(){
		
		$("#nameErr").html("");
		$("#intervalWorkdayErr").html("");
		$("#definiteTimeErr").html("");
		$("#quoteTimeErr").html("");
		$("#confirmTimeErr").html("");
		$("#confirmTimeSecond").html("");
		
		if($("#name").val()==''){
			$("#nameErr").html("*请输入竞价规则名称");
			return;
		}
		
		var intervalWorkdayStr = document.getElementById('intervalWorkday').value.trim();
		if(intervalWorkdayStr.length==0){
			$("#intervalWorkdayErr").html("*间隔工作日不能为空");
			return;
		}
		if(intervalWorkdayStr.length!=0){
			reg=/^[-+]?\d*$/; 
			if(!reg.test(intervalWorkdayStr)){ 
				$("#intervalWorkdayErr").html("*您输入的整数类型格式不正确");
				return;
			} 
		}

		if($("#d242").val()==''){
			$("#definiteTimeErr").html("*具体时间点不能为空");
			return;
		}
		
		var quoteTimeStr = document.getElementById('quoteTime').value.trim();
		if(quoteTimeStr.length==0){
			$("#quoteTimeErr").html("*报价时间不能为空");
			return;
		}
		if(quoteTimeStr.length!=0){
			reg=/^[-+]?\d*$/; 
			if(!reg.test(quoteTimeStr)){ 
				$("#quoteTimeErr").html("*您输入的整数类型格式不正确");
				return;
			} 
		}
		
		var confirmTimeStr = document.getElementById('confirmTime').value.trim();
		if(confirmTimeStr.length==0){
			$("#confirmTimeErr").html("*确认时间(第一轮)不能为空");
			return;
		}
		if(confirmTimeStr.length!=0){
			reg=/^[-+]?\d*$/; 
			if(!reg.test(confirmTimeStr)){ 
				$("#confirmTimeErr").html("*您输入的整数类型格式不正确");
				return;
			} 
		}
		
		var confirmTimeSecondStr = document.getElementById('confirmTimeSecond').value.trim();
		if(confirmTimeSecondStr.length==0){
			$("#confirmTimeSecondErr").html("*确认时间(第二轮)不能为空");
			return;
		}
		if(confirmTimeSecondStr.length!=0){
			reg=/^[-+]?\d*$/; 
			if(!reg.test(confirmTimeSecondStr)){ 
				$("#confirmTimeSecondErr").html("*您输入的整数类型格式不正确");
				return;
			} 
		}
		
		 $.post("${pageContext.request.contextPath}/obrule/addRule.do", $("#ruleForm").serialize(), function(data) {
				if (data.status == 200) {
					layer.confirm(data.data,{
						btn:['确定']
					},function(){
							window.location.href="${pageContext.request.contextPath}/obrule/ruleList.html";
						}
					) 
				}
				if(data.status == 500){
					layer.alert(data.msg);
				}
			});  
	}
</script>
</head>
<body>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">竞价规则管理</a></li><li class="active"><a href="javascript:void(0)">创建竞价规则</a></li>
		</ul>
        <div class="clear"></div>
      </div>
   </div>
   <!-- 发布定型产品页面开始 -->
  <div class="wrapper mt10">
  <div class="container">
  <div class="headline-v2">
     	<h2>添加竞价规则</h2>
  </div> 
	  <div class="mt10">
  		<form id="ruleForm" action="" method="post">
		  <table class="table table-bordered mt10">
		    <tbody>
			  <tr>
			    <td class="bggrey tr">竞价规则名称：</td>
			    <td >
			    	<input name="name" id="name" type="text" class="w230 mb0 border0">
			    	<span><font id="nameErr" style="color: red"></font></span>
			    </td>
			  </tr>
			  <tr>
			    <td class="bggrey tr">间隔工作日：</td>
			    <td >
			    	<input name="intervalWorkday" id="intervalWorkday" type="text" class="w230 mb0 border0">（天）
			    	<span><font id="intervalWorkdayErr" style="color: red"></font></span>
			    </td>
			  </tr>
			  <tr>
			  	<td class="bggrey tr">具体时间点：</td>
			    <td >
			    	<input type="text" name="definiteTime"  id="d242" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm:ss'})" class="Wdate"/>
			    	<span><font id="definiteTimeErr" style="color: red"></font></span>
			    </td>
			  </tr>
			  
			  <tr>
			    <td class="bggrey tr">报价时间：</td>
			    <td >
			    	<input  name="quoteTime" id="quoteTime" type="text" class="w230 mb0 border0">（分钟）
			    	<span><font id="quoteTimeErr" style="color: red"></font></span>
			    </td>
			   <!--  <td class="bggrey tr">二次报价时间（分钟）：</td>
			    <td >
			    	<input id="" name="" value="" type="text" class="w230 mb0 border0">
			    </td> -->
			  </tr>
			  <tr>
			    <td class="bggrey tr">确认时间（第一轮）：</td>
			    <td >
			    	<input name="confirmTime" id="confirmTime" type="text" class="w230 mb0 border0">（分钟）
			    	<span><font id="confirmTimeErr" style="color: red"></font></span>
			    </td>
			  </tr>
			  <tr>
			    <td class="bggrey tr">确认时间（第二轮）：</td>
			    <td >
			    	<input name="confirmTimeSecond" id="confirmTimeSecond" type="text" class="w230 mb0 border0">（分钟）
			    	<span><font id="confirmTimeSecondErr" style="color: red"></font></span>
			    </td>
			  </tr>
			</tbody>
		 </table>
	</form>
	 <div class="col-md-12 clear tc mt10">
   		<button class="btn btn-windows save" onclick="submitForm()">保存</button>
   		<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
	 </div>
	</div>
  </div>
 </div> 
</body>
</html>