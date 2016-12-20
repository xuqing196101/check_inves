<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
<%@ include file="/WEB-INF/view/common.jsp"%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ppms/categoryparam/auditParameter.js"></script>
  </head>
  <body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品管理</a></li><li><a href="#">产品参数审核</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container content height-350 container_box">
	  <!-- left tree -->
    <div class="col-md-3">
	  <div class="tag-box tag-box-v3">
	    <div>
		  <ul id="ztree" class="ztree" />
		</div>
	  </div>
	</div>
	   
	<!-- right -->
    <div class="tag-box tag-box-v4 col-md-9 col-xs-12 col-sm-9" >
	  <div class="col-md-12 col-sm-12 col-xs-12 clear" id="baseParamId">
	    <h2 class="count_flow"><i>1</i>产品参数</h2>
	    <ul id="uListId" class="list-unstyled ul_table" >
	    </ul>
	  </div>
	  <div class="col-md-12 col-sm-12 col-xs-12  padding-top-10 clear" id="auditParamId">
	    <h2 class="count_flow"><i>2</i>审核信息</h2>
	    <ul class="ul_list">
	      <li class="col-md-6">
	        <label class="col-md-12 padding-left-5">审核<span class="red">*</span></label>
	          <span>
	    		<select name="auditStatus" onchange="loadAuditText(this)">
	    			<option value="3">通过</option>
	    			<option value="1">不通过</option>
	    		</select>
	    	  </span>
	      </li>
	      <li class="col-md-6">
			  <label class="col-md-12 padding-left-5">审核意见<span class="red" id="markId">*</span></label>
			  <span>
			  	<textarea class="col-md-12 col-sm-12 col-xs-12" id="textId"></textarea>
			  </span>
		  </li>
	    </ul>
	  </div>
	  <div id="auditBtnId" class="textc col-md-12 col-xs-12 col-sm-12 mt20">
	    <button class="btn btn-windows git" onclick="auditParams();" type="button">提交</button>
	  </div>
	 </div>
    </div>
     
  </body>
</html>
