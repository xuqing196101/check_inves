<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<head>
<jsp:include page="/index_head.jsp"></jsp:include>
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/iss/ps/index/index_expPublicity.js"></script>
<script type="text/javascript">
	$(function(){
		list(1);
	});
</script>
</head>

<body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="${pageContext.request.contextPath}/"> 首页</a></li><li><a href="javascript:void(0);">拟入库公示</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>

  <div class="container job-content ">
     <form id="queryForm" method="get">
      <div class="search_box col-md-12 col-sm-12 col-xs-12">
         	专家名称 : <input name="relName" type="text" id="relName"/>
        	<button type="button" onclick="query()" class="btn btn-u-light-grey">查询</button>
      </div>
     </form>
        <div class="report_list_box">
            <div class="col-md-12 col-sm-12 col-xs-12 report_list_title">
          		<div class="col-md-3 col-xs-3 col-sm-3 f16">专家名称</div>
              <div class="col-md-2 col-xs-2 col-sm-2 f16">类别</div>
              <div class="col-md-3 col-xs-3 col-sm-3 f16">初审单位</div>
              <div class="col-md-4 col-xs-4 col-sm-4 f16">审核结果</div>
            </div>
                <ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new" id="expPublicityList">
                </ul>
           <div id="pagediv" align="right"></div>
        </div>
	  </div>
  <!--底部代码开始-->
  <jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
