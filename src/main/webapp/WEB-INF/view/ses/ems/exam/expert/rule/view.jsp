<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>考试规则查看</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			var single = document.getElementsByName("single");
			var multiple = document.getElementsByName("multiple");
			var errorSingle = $("#errorSingle").val();
			var errorMultiple = $("#errorMultiple").val();
			if(errorSingle==null||errorSingle==""){
				$("#sin").hide();
			}else if(errorSingle=="无"){
				$(single[1]).attr("checked","checked");
				$("#sin").hide();
			}else if(errorSingle=="有"){
				$(single[0]).attr("checked","checked");
				$("#sin").show();
			}
			if(errorMultiple==null||errorMultiple==""){
				$("#mul").hide();
			}else if(errorMultiple=="无"){
				$(multiple[1]).attr("checked","checked");
				$("#mul").hide();
			}else if(errorMultiple=="有"){
				$(multiple[0]).attr("checked","checked");
				$("#mul").show();
			}
		})
	</script>

  </head>
  
  <body>
     <!--面包屑导航开始-->
   	<div class="margin-top-10 breadcrumbs ">
      	<div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">考试规则管理</a></li>
		   </ul>
		<div class="clear"></div>
	  	</div>
   	</div>
   	
   	<input type="hidden" value="${errorSingle }" id="errorSingle"/>
  	<input type="hidden" value="${errorMultiple }" id="errorMultiple"/>
   	
   <div class="container container_box">
   <h2 class="count_flow">查看专家考试规则</h2>
   <ul class="ul_list">
	  	<ul class="list-unstyled p0_20">
	    	<li class="col-md-12 p0 mb20">
	  			<span class="col-md-12 p0"><div class="red star_red">*</div>题型分布：</span>
	  			<div class="col-md-12 p0">
	  			   	<div class="col-md-6 p0 input-append">
		  			   	<label class="fl">单选题：</label>
		  			   	<div class="fl">
		  			   	    <input type="radio" name="single" class="mt0" value="有" disabled="disabled"/>有
		  			   	    <input type="radio" name="single" class="mt0" value="无" disabled="disabled"/>无 
		  			   	</div>
		  			   	<div class="fl" id="sin">
		  			   	  	<input type="text" disabled="disabled" value="${singleNum }" name="singleNum" id="singleNum" class="ml10 w50"/>条<input type="text" disabled="disabled" value="${singlePoint }" name="singlePoint" id="singlePoint" class="ml10 w50"/>分/条
		  			   	</div>
	  			   	</div>
	  			   	<div class="col-md-6 p0 input-append">
		    	   	    <label class="fl">多选题：</label>
		    	   	    <div class="fl">
			    	   	    <input type="radio" name="multiple" class="mt0" value="有" disabled="disabled"/>有
			    	   	    <input type="radio" name="multiple" class="mt0" value="无" disabled="disabled"/>无
		    	   	    </div>
			    	   	<div class="fl" id="mul">
			    	   	  	<input type="text" disabled="disabled" value="${multipleNum }" name="multipleNum" id="multipleNum" class="ml10 w50"/>条<input type="text" disabled="disabled" value="${multiplePoint }" name="multiplePoint" id="multiplePoint" class="ml10 w50"/>分/条
		  		        </div>
	  		        </div>
	  		    </div>
	  		</li>
	  		
	    	<li class="col-md-3 p0">
	  			<span class="col-md-12 p0"><div class="red star_red">*</div>试卷分值：</span>
	  			<div class="col-md-12 input-append p0">
	  				<input type="text" disabled="disabled" name="paperScore" id="paperScore" value="${examRule.paperScore }" readonly="readonly"/>分
	    		</div>
	    	</li>
	    	
	    	<li class="col-md-3 p0">
	  			<span class="col-md-12 p0"><div class="red star_red">*</div>及格标准：</span>
	  			<div class="col-md-12 input-append p0">
	  				<div class="fl"><input type="text" disabled="disabled" name="passStandard" id="passStandard" value="${examRule.passStandard }"/>分</div>
	  			</div>
	    	</li>
	    	
	  		<li class="col-md-3 p0">
	  			<span class="col-md-12 p0"><div class="red star_red">*</div>考试开始时间：</span>
	  			<div class="col-md-12 input-append p0">
			  		<input type="text" disabled="disabled" name="startTime" id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate fl" value="${startTime }"/>
	  			</div>
	  		</li>
	    	
	    	<li class="col-md-3 p0">
	  			<span class="col-md-12 p0"><div class="red star_red">*</div>考试截止时间：</span>
	  			<div class="col-md-12 input-append p0">
		  			<input type="text" disabled="disabled" name="offTime" id="offTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate fl" value="${offTime }"/>
	  			</div>
	    	</li>
	    </ul>
	    </ul>
	    
	    <!-- 底部按钮 -->
	    <div class="col-md-12 mt10 tc ">
			<button class="btn btn-windows back" type="button" onclick="location.href='javascript:history.go(-1);'">返回</button>
	  	</div>
	</div>
  </body>
</html>
