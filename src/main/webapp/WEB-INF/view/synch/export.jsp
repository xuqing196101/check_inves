<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/synch/export.js"></script>
  </head>
  <body>
   <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
	  <div class="container">
	    <ul class="breadcrumb margin-left-0">
		  <li><a href="#"> 首页</a></li>
		  <li><a>保障系统</a></li>
		  <li><a>数据同步</a></li>
		  <li class="active"><a>数据导出</a></li>
	    </ul>
	    <div class="clear"></div>
    </div>
  </div>

  <div class="container container_box">
    <input type="hidden" id="operType" name="operType" value="${operType}"/>
    <div>
      <h2 class="count_flow"><i>1</i>导出设置</h2>
      <ul class="ul_list">
        <li class="col-md-3 col-sm-6 col-xs-12 pl15">
		  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>开始时间</span>
		  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group Wdate mb0 w220" id="startTime" name="startTime" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"> 
		  </div>
		</li>
		
		<li class="col-md-3 col-sm-6 col-xs-12 pl15">
		  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>开始时间</span>
		  <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
			<input class="input_group Wdate mb0 w220" id="endTime" name="endTime" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"> 
		  </div>
		</li>
		
		<li class="col-md-12 col-sm-12 col-xs-12 pl15">
		  <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="star_red">*</span>同步类型</span>
		  <div>
			<c:forEach  items="${dataTypeList}" var="type">
			  <input type="checkbox" name="dataType" value="${type.code}"/> ${type.name}
			</c:forEach> 
		  </div>
		</li>
		
		<div class="clear mt10 tc">
		  <button class="btn" onclick="synchExport();">导出</button>
	    </div>
      </ul>
    </div>
    
    <div class="padding-top-10 clear" id="relaDeptId">
      <h2 class="count_flow"><i>2</i>导出日志</h2>
      <ul class="ul_list">
		<div class="content table_box">
		  <table class="table table-bordered table-condensed table-hover table-striped" id="dataTable" >
		    <thead>
			  <tr>
			    <th class="info w50">序号</th>
				<th class="info w120">同步类型</th>
			    <th class="info w200">同步时间</th>
				<th class="info">描述</th>
			  </tr>
			</thead>
			<tbody></tbody>
		  </table>
		</div>
	   </ul>
	  <div id="pagediv" align="right"></div>
     </div>
    </div>
  </body>

</html>