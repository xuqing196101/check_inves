<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查看法律类题目</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		var opt = "";
		var obj = "";
		$(function(){
			opt = ${opt};
			obj = eval(opt);
			var options = $("#options").val();
			var array = obj[options].split(",");
			var content = $("#optContent").val();
			var queType = $("#queType").val();
			var ct = content.split("&@#$");
			var queAnswer = $("#queAnswer").val();
			var ohtml="";
			var ahtml="";
			for(var i=0;i<array.length;i++){
				ohtml = ohtml+"<div class='clear mt10 col-md-12 p0'><div class='fl mt5'>"+array[i]+"</div><textarea name='option' class='ml5 col-md-9 p0' disabled>"+ct[i]+"</textarea></div>";
				if(queType==1){
					if(queAnswer.indexOf(array[i])>-1){
						ahtml = ahtml+"<input type='radio' name='answer' value='"+array[i]+"' class='mt0' checked='checked' disabled/>"+array[i]+"&nbsp;";
					}else{
						ahtml = ahtml+"<input type='radio' name='answer' value='"+array[i]+"' class='mt0' disabled/>"+array[i]+"&nbsp;";
					}
				}else if(queType==2){
					if(queAnswer.indexOf(array[i])>-1){
						ahtml = ahtml+"<input type='checkbox' name='answer' value='"+array[i]+"' class='mt0' checked='checked' disabled/>"+array[i]+"&nbsp;";
					}else{
						ahtml = ahtml+"<input type='checkbox' name='answer' value='"+array[i]+"' class='mt0' disabled/>"+array[i]+"&nbsp;";
					}
				}
			}
			$("#items").html(ohtml);
			$("#answers").html(ahtml);
		})
	</script>
  </head>
  
  <body>
  	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">支撑环境</a></li><li><a href="#">题库管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   	<input type="hidden" value="${optContent }" id="optContent"/>
   	<input type="hidden" value="${lawAnswer }" id="queAnswer"/>
   <div class="container container_box">
     <div>
       <h2 class="count_flow"><i>1</i>查看法律类题目</h2>
     <ul class="ul_list">
		<ul class="list-unstyled list-flow p0_20">
		     <li class="col-md-12 p0">
	  			<span class="fl">请选择题型：</span>
		  		<select id="queType" name="queType" disabled="disabled" class="w178">
		  			<option value="">请选择</option>
		  			<c:forEach items="${examPoolType }" var="e">
		  				<c:choose>
		  					<c:when test="${e.id==lawQue.questionTypeId }">
		  						<option value="${e.id }" selected="selected">${e.name }</option>
		  					</c:when>
		  					<c:otherwise>
		  						<option value="${e.id }">${e.name }</option>
		  					</c:otherwise>
		  				</c:choose>
		  			</c:forEach>
		  		</select>
	  		</li>
		
			<li class="col-md-12 p0">
			   <span class="fl">题干：</span>
			   <div class="">
		        	<textarea class="text_area col-md-8" name="topic" id="queTopic" disabled="disabled">${lawQue.topic }</textarea>
		       </div>
			 </li> 
		   
	  	
	  		<li class="col-md-12 p0">
				<span class="fl">请选择选项数量：</span>
				<div class="fl col-md-9 p0">
					<select id="options" name="options" disabled="disabled" class="w178 fl">
			  			<option value="">请选择</option>
			  			<c:if test="${optNum==3 }">
			  				<option value="three" selected>3</option>
			  			</c:if>
			  			<c:if test="${optNum!=3 }">
			  				<option value="three">3</option>
			  			</c:if>
			  			<c:if test="${optNum==4 }">
			  				<option value="four" selected>4</option>
			  			</c:if>
			  			<c:if test="${optNum!=4 }">
			  				<option value="four">4</option>
			  			</c:if>
			  			<c:if test="${optNum==5 }">
			  				<option value="five" selected>5</option>
			  			</c:if>
			  			<c:if test="${optNum!=5 }">
			  				<option value="five">5</option>
			  			</c:if>
			  			<c:if test="${optNum==6 }">
			  				<option value="six" selected>6</option>
			  			</c:if>
			  			<c:if test="${optNum!=6 }">
			  				<option value="six">6</option>
			  			</c:if>
			  			<c:if test="${optNum==7 }">
			  				<option value="seven" selected>7</option>
			  			</c:if>
			  			<c:if test="${optNum!=7 }">
			  				<option value="seven">7</option>
			  			</c:if>
			  			<c:if test="${optNum==8 }">
			  				<option value="eight" selected>8</option>
			  			</c:if>
			  			<c:if test="${optNum!=8 }">
			  				<option value="eight">8</option>
			  			</c:if>
			  			<c:if test="${optNum==9 }">
			  				<option value="nine" selected>9</option>
			  			</c:if>
			  			<c:if test="${optNum!=9 }">
			  				<option value="nine">9</option>
			  			</c:if>
			  			<c:if test="${optNum==10 }">
			  				<option value="ten" selected>10</option>
			  			</c:if>
			  			<c:if test="${optNum!=10 }">
			  				<option value="ten">10</option>
			  			</c:if>
		  			</select>
					<div class="col-md-9 clear p0" id="items"></div>
				</div>
			 </li> 
		   
		  		<li class="col-md-12 p0">
					<span class="fl">答案：</span>	
					<div class="fl ml5 mt5" id="answers"></div>
				</li>
				
  			</ul>
  		</ul>
  		<!-- 底部按钮 -->
	  	<div class="padding-top-10 clear">
			<div class="col-md-12 pl200 ">
				<div class="mt40 tc mb50">
		  			<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
	  			</div>
	  		</div>
	  	</div>
	  	
  		
	  		</div>
		</div>
  </body>
</html>
