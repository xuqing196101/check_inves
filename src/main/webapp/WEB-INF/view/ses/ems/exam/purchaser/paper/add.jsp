<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新增考卷</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			$("#time").hide();
		})
		
		function countScore(){
			document.getElementById("singleNum").value=document.getElementById("singleNum").value.replace(/\D+/g,'');
			document.getElementById("multipleNum").value=document.getElementById("multipleNum").value.replace(/\D+/g,'');
			document.getElementById("judgeNum").value=document.getElementById("judgeNum").value.replace(/\D+/g,'');
			var sn = $("#singleNum").val();
			var sp =$("#singlePoint").val();
			var mn = $("#multipleNum").val();
			var mp = $("#multiplePoint").val();
			var jn = $("#judgeNum").val();
			var jp = $("#judgePoint").val();
			$("#paperScore").val(sn*sp+mn*mp+jn*jp);
			var paperScore = document.getElementById("paperScore").value;
			if(paperScore=="NaN"){
				$("#paperScore").val("0");
			}
		}
		
		//勾选重考
		function checkTrue(obj){
			if($(obj).prop("checked")){
				$("#time").show();
			}
		}
		
		//勾选不重考
		function checkFalse(obj){
			if($(obj).prop("checked")){
				$("#time").hide();
			}
		}
	</script>

  </head>
  
  <body>
    <!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
	   	<div class="container">
			<ul class="breadcrumb margin-left-0">
			   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">考卷管理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	 </div>
	   
     <div class="container margin-top-5">
     <div class="content padding-left-25 padding-right-25 padding-top-5">
     	<div>
		   <div class="headline-v2">
		   		<h2>新增考卷</h2>
		   </div>
    <form action="<%=path %>/purchaserExam/saveToExamPaper.html" method="post">
    	<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-12 p0">
	  			<span class="fl"><div class="red star_red">*</div>试卷名称：</span>
		  		<input type="text" name="name"/>
		  		<div class="validate">${ERR_name }</div>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl"><div class="red star_red">*</div>试卷编号：</span>
		  		<input type="text" name="code"/>
		  		<div class="validate">${ERR_code }</div>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl"><div class="red star_red">*</div>题型分布：</span>
	  			<div class="fl">
	  			   <label class="fl mt5">单选题：</label><input type="text" name="singleNum" id="singleNum" class="ml10 w50" onkeyup="countScore()"/>条<input type="text" name="singlePoint" id="singlePoint" class="ml10 w50" onkeyup="countScore()"/>分/条<br/>
		    	   <label class="fl mt5">多选题：</label><input type="text" name="multipleNum" id="multipleNum" class="ml10 w50" onkeyup="countScore()"/>条<input type="text" name="multiplePoint" id="multiplePoint" class="ml10 w50" onkeyup="countScore()"/>分/条<br/>
		    	   <label class="fl mt5">判断题：</label> <input type="text" name="judgeNum" id="judgeNum" class="ml10 w50" onkeyup="countScore()"/>条<input type="text" name="judgePoint" id="judgePoint" class="ml10 w50" onkeyup="countScore()"/>分/条<br/>
	  		    </div>
	  		    <div class="clear red">${ERR_typeDistribution }</div>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl mt5"><div class="red star_red">*</div>总分值：</span>
		  		<input class="w50 mt5" type="text" name="paperScore" id="paperScore" readonly="readonly"/>分
		  		<div class="validate"></div>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl mt5"><div class="red star_red">*</div>及格标准：</span>
		  		<input class="w50 mt5" type="text" name="passStandard" id="passStandard"/>分
		  		<div class="clear red">${ERR_passStandard }</div>
	  		</li>
    		
    		<li class="col-md-12 p0">
	  			<span class="fl mt5"><div class="red star_red">*</div>考试开始时间：</span>
		  		<input type="text" name="startTime" id="startTime" class="Wdate mt5" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
	  			<div class="clear red">${ERR_startTime }</div>
	  		</li>
	  		
	  		<li class="col-md-12 p0">
	  			<span class="fl mt5"><div class="red star_red">*</div>考试截止时间：</span>
		  		<input type="text" name="offTime" id="offTime" class="Wdate mt5" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
	  			<div class="clear red">${ERR_offTime }</div>
	  		</li>
	  		
	  		<li class="col-md-12 p0">
	  			<span class="fl mb5"><div class="red star_red">*</div>允许30分钟内重考：</span>
		  		<input class="mt0" type="radio" name="isAllow" id="isAllowTrue" value="是" onclick="checkTrue(this)">是
    			<input class="mt0" type="radio" name="isAllow" id="isAllowFalse" value="否" onclick="checkFalse(this)"/>否
	  			<div class="clear red">${ERR_isAllow }</div>
	  			<div class="clear red">${ERR_testTime }</div>
	  		</li>
	  		
	  		<li class="col-md-12 p0" id="time">
	  			<span class="fl mt5"><div class="red star_red">*</div>考试用时：</span>
		  		<input class="w50 mt5" type="text" name="testTime" id="testTime"/>分钟
	  		</li>
	  		
	  		<%--<li class="col-md-12 p0 mt10 red">
	  			*注意:每份考卷的登录有效期为15分钟,如:考卷开始时间上午9点,请相关参考人员于上午9点至上午9点15分内登录考试系统考试,否则视为弃考,计0分。
	  		</li>
    	--%></ul>
   
   
  		<!-- 按钮 -->
  		<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
				 	<button class="btn btn-windows save" type="submit">保存</button>
	    			<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
				</div>
	  		</div>
	  	</div>
    
    			</form>
    		</div>
    	</div>
    </div>
  </body>
</html>
