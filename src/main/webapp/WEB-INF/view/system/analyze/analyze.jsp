<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript" src= "${pageContext.request.contextPath}/js/system/analyze/analyze.js"></script>
		<script type="text/javascript" src= "${pageContext.request.contextPath}/js/system/analyze/echartsTemplate.js"></script>
		<title>统计页面</title>
	</head>
<body>
	<!--  -->
	<div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">首页</a></li><li><a href="javascript:void(0)">信息服务</a></li><li><a href="javascript:void(0)">统计管理</a></li>
		   <li class="active"><a href="${pageContext.request.contextPath}/analyze/list.html">统计图列表</a>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
	<!--  -->
	<div class="container content job-content">
	     <ul class="container container_box">
	     
     		<h2 class="count_flow"><i>1</i>用户登录统计图(<c:if test="${ '1' eq ipAddressType }">外网</c:if><c:if test="${ '0' eq ipAddressType }">内网</c:if>)</h2>
	     	<ul class="ul_list mb10">
	     		<button class="btn btn-windows day_order mb20" type="submit" onclick="analyzeAll('DAY','C_LOGIN','uploadPic','用户登录统计图')">按天统计</button>
	     		<button class="btn btn-windows week_order mb20" type="submit" onclick="analyzeAll('WEEK','C_LOGIN','uploadPic','用户登录统计图')">按周统计</button>
	     		<button class="btn btn-windows mon_order mb20" type="submit" onclick="analyzeAll('MONTH','C_LOGIN','uploadPic','用户登录统计图')">按月统计</button>
	     		<div>
	     			<input name="handHandleLoginAnalyze" id="handHandleLoginAnalyze" class="Wdate w200 mb0" type="text" onfocus="WdatePicker({maxDate:'%y-%M-%d',firstDayOfWeek:1})"/>
	     			<button class="btn btn-windows day_order mb0" id="handHandleLoginAnalyzeBut">开始统计</button>
	     		</div>
         		<div id="uploadPic" class="analyze"></div>
         	</ul>
       		<h2 class="count_flow clear"><i>2</i>用户注册统计图(<c:if test="${ '1' eq ipAddressType }">外网</c:if><c:if test="${ '0' eq ipAddressType }">内网</c:if>)</h2>
         	<ul class="ul_list mb10">
         		<button class="btn btn-windows day_order mb20" type="submit" onclick="analyzeAll('DAY','C_REGISTER','uploadPic1','用户注册统计图')">按天统计</button>
	     		<button class="btn btn-windows week_order mb20" type="submit" onclick="analyzeAll('WEEK','C_REGISTER','uploadPic1','用户注册统计图')">按周统计</button>
	     		<button class="btn btn-windows mon_order mb20" type="submit" onclick="analyzeAll('MONTH','C_REGISTER','uploadPic1','用户注册统计图')">按月统计</button>
	     		<div>
	     			<input name="handHandleRegisterAnalyze" id="handHandleRegisterAnalyze" class="Wdate w200 mb0" type="text" onfocus="WdatePicker({maxDate:'%y-%M-%d',firstDayOfWeek:1})"/>
	     			<button class="btn btn-windows day_order mb0" id="handHandleRegisterAnalyzeBut">开始统计</button>
	     		</div>
	        	<div id="uploadPic1" class="analyze"></div>
	        </ul>
        	<h2 class="count_flow clear"><i>3</i>图片上传统计图(<c:if test="${ '1' eq ipAddressType }">外网</c:if><c:if test="${ '0' eq ipAddressType }">内网</c:if>)</h2>
	        <ul class="ul_list">
	        	<button class="btn btn-windows day_order mb20" type="submit" onclick="analyzeAll('DAY','C_ATT_UPLOAD','uploadPic2','图片上传统计图')">按天统计</button>
	     		<button class="btn btn-windows week_order mb20" type="submit" onclick="analyzeAll('WEEK','C_ATT_UPLOAD','uploadPic2','图片上传统计图')">按周统计</button>
	     		<button class="btn btn-windows mon_order mb20" type="submit" onclick="analyzeAll('MONTH','C_ATT_UPLOAD','uploadPic2','图片上传统计图')">按月统计</button>
	     		<div>
	     			<input name="handHandleAttAnalyze" id="handHandleAttAnalyze" class="Wdate w200 mb0" type="text" onfocus="WdatePicker({maxDate:'%y-%M-%d',firstDayOfWeek:1})"/>
	     			<button class="btn btn-windows day_order mb0" id="handHandleAttAnalyzeBut">开始统计</button>
	     		</div>
	        	<div id="uploadPic2" class="analyze"></div>
	        </ul>
         </ul>
    </div>
</body>
</html>
