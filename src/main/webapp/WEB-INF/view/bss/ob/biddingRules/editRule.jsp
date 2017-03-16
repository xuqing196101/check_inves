<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
   <%@ include file="/WEB-INF/view/common.jsp" %>
   <%@ include file="/WEB-INF/view/common/validate.jsp"%>
<title>编辑竞价规则</title>

<script type="text/javascript">
		
	function submitForm(){
		
		$("#nameErr").html("");
		$("#intervalWorkdayErr").html("");
		$("#definiteTimeErr").html("");
		$("#quoteTimeErr").html("");
		$("#confirmTimeErr").html("");
		$("#confirmTimeSecond").html("");
		$("#leastSupplierNum").html("");
		
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
			$("#definiteTimeErr").html("*竞价开始时间不能为空");
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
		
		var leastSupplierNumStr = document.getElementById('leastSupplierNum').value.trim();
		if(leastSupplierNumStr.length==0){
			$("#leastSupplierNumErr").html("*最少供应商数量不能为空");
			return;
		}
		if(leastSupplierNumStr.length!=0){
			reg=/^[-+]?\d*$/; 
			if(!reg.test(leastSupplierNumStr)){ 
				$("#leastSupplierNumErr").html("*您输入的整数类型格式不正确");
				return;
			}
			if(parseInt(leastSupplierNumStr)<=0){
				$("#leastSupplierNumErr").html("*请输入大于0的整数");
				return;
			}
		}
		 $.post("${pageContext.request.contextPath}/obrule/updateobRule.do", $("#ruleForm").serialize(), function(data) {
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
		   <li class="active"><a href="javascript:void(0)">竞价规则管理</a></li><li class="active"><a href="javascript:void(0)">修改竞价规则</a></li>
		</ul>
        <div class="clear"></div>
      </div>
   </div>
   <!-- 发布定型产品页面开始 -->
  <div class="wrapper mt10">
  <div class="container">
	  <div class="mt10">
	  <div class="container container_box">
  		<form id="ruleForm" action="" method="post">
  		  <input name="id" type="hidden" value="${ obRule.id }" />
		  <div>
		    <h2 class="list_title">修改竞价规则</h2>
		   <ul class="ul_list">
		     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>竞价规则名称：</span>
			   <div class="input-append input_group col-sm-12 col-xs-12 p0" id="supplierselect">
					<input class="input_group" name="name" id="name" type="text" value="${ obRule.name }" class="w230 mb0 border0">
		        <div class="cue"><span><font id="nameErr" style="color: red"></font></span></div>
		       </div>
			 </li>
			 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>间隔工作日（天）：</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
			        <input class="input_group" name="intervalWorkday" id="intervalWorkday" type="text" value="${ obRule.intervalWorkday }"  class="w230 mb0 border0">
					<div class="cue"><span><font id="intervalWorkdayErr" style="color: red"></font></span></div>
		       </div>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>竞价开始时间：</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
				    <input type="text" name="definiteTime"  value='<fmt:formatDate value="${ obRule.definiteTime }" pattern="HH:ss:mm"/>' id="d242" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm:ss'})" class="Wdate"/>
					<div class="cue"><span><font id="definiteTimeErr" style="color: red"></font></span></div>
		       </div>
			 </li> 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>报价时间（分钟）：</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
				    <input class="input_group" name="quoteTime" id="quoteTime" type="text" value="${ obRule.quoteTime }" class="mb0 border0">
					<div class="cue"><span><font id="quoteTimeErr" style="color: red"></font></span></div>
		       </div>
			 </li>
			 <!--  <td class="bggrey tr">二次报价时间（分钟）：</td>
			    <td >
			    	<input id="" name="" value="" type="text" class="mb0 border0">
			   </td> -->
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>确认时间（分钟）（第一轮）：</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
				    <input class="input_group" name="confirmTime" id="confirmTime" type="text" value="${ obRule.confirmTime }" class="mb0 border0">
					<div class="cue"><span><font id="confirmTimeErr" style="color: red"></font></span></div>
		       </div>
			 </li> 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>确认时间（分钟）（第二轮）</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
				    <input class="input_group" name="confirmTimeSecond" id="confirmTimeSecond" type="text" value="${ obRule.confirmTimeSecond }" class="mb0 border0" />
					<div class="cue"><span><font id="confirmTimeSecondErr" style="color: red"></font></span></div>
		       </div>
			 </li> 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>最少供应商数量</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
				    <input class="input_group" name="leastSupplierNum" id="leastSupplierNum" type="text" value="${ obRule.leastSupplierNum }" class="mb0 border0" />
					<div class="cue"><span><font id="leastSupplierNumErr" style="color: red"></font></span></div>
		       </div>
			 </li> 
		   </ul>
		       <div class="clear"></div> 
		  </div> 
		</form>
		 <div class="col-md-12 col-sm-12 col-xs-12 clear tc mt10">
	   		<button class="btn btn-windows save" onclick="submitForm()">修改</button>
	   		<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
		 </div>
		</div>
	</div>
  </div>
 </div> 
</body>
</html>