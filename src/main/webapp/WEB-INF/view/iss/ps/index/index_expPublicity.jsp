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
         <div class="search_box form-inline">
             <div class="form-group">
                 <label>专家名称：</label>
                 <input type="text" name="relName" id="relName" class="form-control"/>
             </div>
             <div class="form-group">
                 <label>类型：</label>
                 <select id="expertsTypeId" name="expertsTypeId">
                     <option value="">--请选择--</option>
                     <c:forEach var="b" items="${expTypeList}">
                         <option value="${b.id}">${b.name}</option>
                     </c:forEach>
                 </select>
             </div>
             <div class="form-group">
                 <label>初审单位：</label>
                 <select id="orgId" name="orgId">
                     <option value="">--请选择--</option>
                     <c:forEach var="b" items="${orgDepList}">
                         <option value="${b.id}">${b.name}</option>
                     </c:forEach>
                 </select>
             </div>
             <button type="button" onclick="query()"  class="btn btn-u-light-grey">查询</button>
             <input onclick="resetAll()" type="button" class="btn btn-u-light-grey" value="重置"></input>
         </div>
     </form>
        <div class="report_list_box">
            <div class="report_list_title">
          	  <div class="col-xs-12 w8p f16 tc">专家名称</div>
          	  <div class="col-xs-12 w12p f16 tc">专家编号</div>
              <div class="col-xs-12 w22p f16">类别</div>
              <div class="col-xs-12 w10p f16 tc">初审单位</div>
              <div class="col-xs-12 w30p f16 tc">审核结果</div>
              <div class="col-xs-12 w18p f16 tc">公示时间</div>
              <div class="clear"></div>
            </div>
                <ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new" id="expPublicityList">
                </ul>
            <h5><span class="red">如果对公示内容有异议，请拨打举报电话：010-66880147</span></h5>
           <div id="pagediv" align="right"></div>
        </div>
	  </div>
  <!--底部代码开始-->
  <jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
