<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/WEB-INF/view/common.jsp"%>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/system/log/list.js"></script>
  <script type="text/javascript">
	  $(function(){
		  list(1);
		  loadCss();
	  });
	
   </script>
  </head>
  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
	    <ul class="breadcrumb margin-left-0">
		  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
		  <li><a href="javascript:void(0);">支撑系统</a></li>
		  <li><a href="javascript:void(0);">系统管理</a></li>
		  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/systemLog/init.html?type=1');" class="active">日志管理</a></li>
		</ul>
		<div class="clear"></div>
	  </div>
    </div>
    <div class="container">
      <div class="tab-content mt10">
        <div class="tab-v2">
          <input type="hidden" id="type"  name="type" value="${type}"/>
          <ul class="nav nav-tabs bgwhite" id="tabUl">
            <li id="tabNormald" onclick="loadTab(this,1);" ><a href="javascript:void(0)"  data-toggle="tab" class="f18">系统操作</a></li>
			<li id="tabSpeciId" onclick="loadTab(this,2);" ><a href="javascript:void(0)"  data-toggle="tab" class="f18">系统异常</a></li>
          </ul>
          <div class="tab-content">
            <div class="headline-v2">
	          <h2 id="titleId"></h2>
	        </div>
		      <h2 class="search_detail">
				<ul class="demand_list">
		    	  <li>
			    	<label class="fl">操作人：</label>
			    	<span>
			    	  <input type="text" id="operPerson"  name="name" />
			    	</span>
			      </li>
			       <li>
			    	 <label class="fl">操作：</label>
			    	 <span>
			    	   <input type="text" id="desc"  name="desc" />
			    	 </span>
			       </li>
			       <li>
			    	 <label class="fl">请求IP：</label>
			    	 <span>
			    	   <input type="text" id="ip"  name="ip" />
			    	 </span>
			       </li>
			       <li>
			    	 <label class="fl">操作时间：</label>
			    	   <input type="text" id="startTime" class="Wdate w180"  name="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'});" />
			    	   <span class="f12">至 </span>
			    	   <input type="text" id="endTime" class="Wdate w180"  name="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'});" />
				    </li>
				    
				    <div class="col-md-12 col-sm-12 col-xs-12 tc">
				      <button type="button" onclick="search();" class="btn  mt1">查询</button>
			    	  <button type="button" onclick="resetQuery()" class="btn mt1">重置</button>  
				    </div>
				  </ul>
				  <div class="clear"></div>
			  </h2>
	      
			  <div class="content table_box">
		        <table class="table table-bordered table-condensed table-hover table-striped" id="dataTable">
		          <thead>
					<tr>
					  <th class="info w50">序号</th>
					  <th class="info" width="25%">操作</th>
					  <th class="info">操作人</th>
					  <th class="info">操作时间</th>
					  <th class="info">请求IP</th>
					  <c:if test="${type == 1}">
					    <th class="info">响应时间（ms）</th>
					  </c:if>
					  <th class="info">详情</th>
					</tr>
				 </thead>
				 <tbody></tbody>
				</table>
			  </div>
		      <div id="pagediv" align="right"></div>
		   </div>
	    </div>
	  </div>
	</div>
  </body>
</html>
