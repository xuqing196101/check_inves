<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html>
  <head>
  <%@ include file="/WEB-INF/view/common.jsp"%>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/qualification/list.js"></script>
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
		  <li><a href="javascript:void(0);"> 首页</a></li>
		  <li><a href="javascript:void(0);">支撑系统</a></li>
		  <li><a href="javascript:void(0);" class="active">资质管理</a></li>
		</ul>
		<div class="clear"></div>
	  </div>
    </div>
    <div class="container">
      <div class="tab-content mt10">
        <div class="tab-v2">
          <input type="hidden" id="type"  name="type" value="${type}"/>
          <input type="hidden" id="operaType"  name="operaType"/>
          <ul class="nav nav-tabs bgwhite" id="tabUl">
            <li id="tabNormald" onclick="loadTab(this,1);" ><a href="javascript:void(0)"  data-toggle="tab" class="f18">通用资质</a></li>
			<li id="tabSpeciId" onclick="loadTab(this,2);" ><a href="javascript:void(0)"  data-toggle="tab" class="f18">专用资质</a></li>
          </ul>
          <div class="tab-content">
            <div class="headline-v2">
	          <h2 id="titleId"></h2>
	        </div>
		      <h2 class="search_detail">
				<ul class="demand_list">
		    	  <li>
			    	<label class="fl">资质名称：</label>
			    	<span>
			    	  <input type="text" id="name"  name="name" />
			    	</span>
			      </li>
			    	<button type="button" onclick="search();" class="btn fl mt1">查询</button>
			    	<button type="button" onclick="resetQuery()" class="btn fl mt1">重置</button>  	
				  </ul>
				  <div class="clear"></div>
			  </h2>
	      
		   	  <!-- 操作按钮-->
			  <div class="col-md-12 pl20 mt10">
			    <button class="btn btn-windows add" type="button" onclick="add()">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
				<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
			  </div>
			  <div class="content table_box">
		        <table class="table table-bordered table-condensed table-hover table-striped" id="dataTable">
		          <thead>
					<tr>
					  <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
					  <th class="info w50">序号</th>
					  <th class="info">资质名称</th>
					</tr>
				 <thead>
				 <tbody>
				   <c:forEach items="${list.list}" var="qualification" varStatus="vs">
				     <tr>
					   <td class="tc"><input  type="checkbox" name="chkItem" value="${qualification.id}" /></td>
					   <td class="tc">${vs.index+1}</td>
					   <td class="tl pl20">${qualification.name}</td>
					  </tr>
				    </c:forEach>
				  </tbody>
				</table>
			  </div>
		      <div id="pagediv" align="right"></div>
		   </div>
	    </div>
	  </div>
	</div>
	
	<!-- 编辑界面 -->
	<div id="openDiv" class="dnone layui-layer-wrap" >
	   <div class="drop_window">
	     <ul class="list-unstyled">
		   <li class="mt10 col-md-12 p0">
	   	     <label class="col-md-12 pl20">资质名称：</label>
			 <span class="col-md-12">
			   <input  id="quaName"  name="name"  class="title col-md-12 pl20"   type="text">
			 </span>
	       </li>
	       <div class="clear"></div>
	    </ul>
	    <div class="tc  mt10 col-md-12">
          <button class="btn btn-windows save"  onclick="save();" type="button">确定 </button>
		  <button class="btn btn-windows cancel"   onclick="cancel();"  type="button">取消</button>
        </div>
	  </div>
	</div>
	
  </body>
</html>
