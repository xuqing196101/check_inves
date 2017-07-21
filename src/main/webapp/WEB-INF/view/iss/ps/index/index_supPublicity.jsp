<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<head>
<jsp:include page="/index_head.jsp"></jsp:include>
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/iss/ps/index/index_supPublicity.js"></script>
<script type="text/javascript">
  $(function(){
	  list(1);
  })
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
                 <label>供应商名称：</label>
                 <input type="text" name="supplierName" id="supplierName" class="form-control"/>
             </div>
             <div class="form-group">
                 <label>类型：</label>
                 <select id="supplierTypeId" name="supplierTypeId">
                     <option value="">--请选择--</option>
                     <c:forEach var="b" items="${listSupplierTypes}">
                         <option value="${b.id}">${b.name}</option>
                     </c:forEach>
                 </select>
             </div>
             <div class="form-group">
                 <label>企业性质：</label>
                 <select id="businessNature" name="businessNature">
                     <option value="">--请选择--</option>
                     <c:forEach var="b" items="${businessNatureList}">
                         <option value="${b.id}">${b.name}</option>
                     </c:forEach>
                 </select>
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
          	  <div class="col-md-2 col-xs-2 col-sm-2 f16">供应商名称</div>
              <div class="col-md-2 col-xs-2 col-sm-2 f16">类型</div>
              <div class="col-md-1 col-xs-1 col-sm-1 f16">企业性质</div>
              <div class="col-md-1 col-xs-1 col-sm-1 f16">初审单位</div>
              <div class="col-md-4 col-xs-4 col-sm-4 f16">审核结果</div>
              <div class="col-md-2 col-xs-2 col-sm-2 f16">公示时间</div>
              <div class="clear"></div>
            </div>
                <ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new" id="supPublicityList">
                </ul>
            <h5><span class="red">如果对公示内容有异议，请拨打举报电话：010-66880147</span></h5>
           <div id="pagediv" align="right"></div>
        </div>
	  </div>
  <!--底部代码开始-->
  <jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
