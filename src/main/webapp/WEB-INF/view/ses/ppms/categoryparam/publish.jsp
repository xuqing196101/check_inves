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
		   <li><a href="javascript:void(0);">首页</a></li><li><a href="javascript:void(0);">产品管理</a></li><li><a href="javascript:void(0);">产品参数发布</a></li>
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
   	  <div class="col-md-12 col-sm-12 col-xs-12 hide" id="publishBtnDiv">
   	    <div class="pull-left">
 		  <button class="btn btn-windows apply" onclick="publishParam();" type="button">发布</button>
	    </div>
   	  </div>
	  <!-- <div class="col-md-12 col-sm-12 col-xs-12 mt20 clear">
		  <table class="table table-bordered mt10" id="uListId">
		  
		  </table>
	  </div> -->
	  
	  
	  <div class="col-md-12 col-sm-12 col-xs-12 clear hide" id="baseInfoDiv">
	    <h2 class="count_flow"><i>1</i>基本信息</h2>
	    <div class=" col-md-12 col-sm-12 col-xs-12" >
	    	<table class="table table-bordered" >
	    		<tbody id="baseInfoTbody">
	    			<tr id="trs"><td class="bggrey w100">产品名称:</td><td class="productName">测试2</td><td class="bggrey w100">发布状态：</td><td class="publishStatus">已发布</td></tr>
	    			<tr><td class="bggrey w100">是否公开：</td><td class="isOpen">公开</td><td class="bggrey w100">类型：</td><td class="type">物资生产,物资销售</td></tr>
	    		</tbody>
		  	</table>
	    </div>
	  </div>
	  <div class="col-md-12 col-sm-12 col-xs-12 clear hide" id="productParamDiv">
	    <h2 class="count_flow"><i>2</i>产品参数</h2>
	    <div class=" col-md-12 col-sm-12 col-xs-12" >
	    	<table class="table table-bordered" >
	    		<thead>
	    			<tr>
	    				<td class="info w50 tc">序号</td>
	    				<td class="info tc">参数名称</td>
	    				<td class="info tc">参数类型</td>
	    			</tr>
	    		</thead>
	    		<tbody id="productParamTbody">
	    			<!-- <tr><td class="tc">1</td><td class="tc">面积</td><td class="tc">字符</td></tr> -->
	    		</tbody>
		  	</table>
	    </div>
	  </div>
	  
	 </div>
    </div>
  </body>
</html>
