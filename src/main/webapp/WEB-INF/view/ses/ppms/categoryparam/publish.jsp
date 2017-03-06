<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC>
<html>
  <head>  
<%@ include file="/WEB-INF/view/common.jsp"%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ppms/categoryparam/publish.js"></script>
  </head>
  <body>
  <form>
  	<input type="hidden" name="orgId" id="orgId" value="${orgId}">
  </form>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#">首页</a></li><li><a href="#">产品管理</a></li><li><a href="#">产品参数发布</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container content height-350">
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
   	  <div class="col-md-12 col-sm-12 col-xs-12">
   	    <div class="pull-left">
 		  <button class="btn btn-windows apply" onclick="publishParam();" type="button">发布</button>
	    </div>
   	  </div>
	  <div class="col-md-12 col-sm-12 col-xs-12 mt20 clear">
	  <table class="table table-bordered mt10" id="uListId">
	  
	  </table>
	    <!-- <ul id="uListId" class="list-unstyled ul_table" >
	    </ul> -->
	  </div>
	 </div>
    </div>
  </body>
</html>
