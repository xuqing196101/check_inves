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
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">产品管理</a></li><li><a href="javascript:void(0);">产品参数审核</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container content height-350 container_box">
	  <!-- left tree -->
    <div class="col-md-3">
	  <div class="tag-box tag-box-v3">
	    <div>
		  <ul id="ztree" class="ztree" style="height: 400px;" />
		</div>
	  </div>
	</div>
	   
	<!-- right -->
    <div class="tag-box tag-box-v4 col-md-9 col-xs-12 col-sm-9" >
    <div class="col-md-12 col-sm-12 col-xs-12 clear" id="baseParamId">
	    <h2 class="count_flow"><i>1</i>基本信息</h2>
	    <!-- <ul id="uListId" class="list-unstyled ul_table" >
	    </ul> -->
	    <div class=" col-md-12 col-sm-12 col-xs-12" id="name" ></div>
	   <!--  <table class="table table-bordered mt10" id="uListId">
	  
	  </table> -->
	  </div>
	  <div class="col-md-12 col-sm-12 col-xs-12 clear" id="baseParamIditem">
	    <h2 class="count_flow"><i>2</i>产品参数</h2>
	    <!-- <ul id="uListId" class="list-unstyled ul_table" >
	    </ul> -->
	    <div class=" col-md-12 col-sm-12 col-xs-12" id="uListId" ></div>
	   <!--  <table class="table table-bordered mt10" id="uListId">
	  
	  </table> -->
	  </div>
	  <div class="col-md-12 col-sm-12 col-xs-12  padding-top-10 clear" id="auditParamId">
	    <h2 class="count_flow"><i>3</i>审核信息</h2>
	    <div class=" col-md-12 col-sm-12 col-xs-12" id="tableId" ></div>
	     <div class="col-md-12 col-sm-12 col-xs-12  padding-top-10 clear">
	    <ul class="ul_list" id="urlId">
	     <li class="col-md-12 col-sm-12 col-xs-12 pl15">
                   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>审核</span>
                   <div class="input-append input_group col-sm-12 col-xs-12 p0">
	                   <select style="width: 150px;" class="col-md-12 col-sm-12 col-xs-12" name="auditStatus" onchange="loadAuditText(this)">
		    			<option value="3">通过</option>
		    			<option value="1">不通过</option>
	    		       </select>
                   </div>
                 </li>
                 <li class="col-md-12 col-sm-12 col-xs-12 pl15">
                   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red" id="markId">*</span>审核意见</span>
                   <div class="input-append input_group col-sm-12 col-xs-12 p0">
	                  <textarea class="col-md-12 col-sm-12 col-xs-12" style="height: 100px;" id="textId"></textarea>
                   </div>
                 </li>
	    </ul>
	    </div>
	  </div>
	  <div id="auditBtnId" class="textc col-md-12 col-xs-12 col-sm-12 mt20">
	    <button class="btn btn-windows git" onclick="auditParams();" type="button">提交</button>
	  </div>
	 </div>
    </div>
     
  </body>
</html>
