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

      function level() {
          var idArray = [];
          var count = 0;
          $("input[name='chkItem']:checked").each(function () {
              idArray.push($(this).val());
              count++;
          });

          if (count != 1) {
              layer.msg("请选择一条记录进行编辑");
              return;
          } else {
              layer.open({
                  type: 2,
                  title: "资质证书等级",
                  skin: 'layui-layer-rim',
                  shadeClose: true,
                  area: ['580px', '400px'],
                  content: "${pageContext.request.contextPath}/qualification/getlevle.html?id=" + idArray,
              });
          }
      }
	  
	 
   </script>
  </head>
  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
	    <ul class="breadcrumb margin-left-0">
		  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
		  <li><a href="javascript:void(0);">支撑系统</a></li>
		  <li><a href="javascript:void(0);">数据字典</a></li>
		  <li><a href="javascript:jumppage('${pageContext.request.contextPath}/qualification/init.html?type=${type}')" class="active">资质管理</a></li>
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
          	<li id="tabPoject" onclick="loadTab(this,4);" ><a href="javascript:void(0)"  data-toggle="tab" class="f18">工程资质</a></li>
          
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
				<c:if test="${type==4}">
				<button class="btn btn-windows add" type="button" onclick="level();">维护等级</button>
				</c:if>
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
	
	<div id="dicDiv" class="dnone layui-layer-wrap" >
	   <div class="drop_window">
	     <ul class="list-unstyled">
		   <li class="mt10 col-md-12 p0">
			 <span class="col-md-12">
			  <!--  <input  id="quaName"  name="name"  class="title col-md-12 pl20"   type="text"> -->
			  	<c:forEach items="${kind }" var="obj">
			  	<span> <input name="did"    type="checkbox" value="${obj.id }"> ${obj.name } </span> 
			     </c:forEach>
			              
			 </span>
	       </li>
	       <div class="clear"></div>
	    </ul>
	    <div class="tc  mt10 col-md-12">
          <button class="btn btn-windows save"  onclick="saveAp();" type="button">确定 </button>
		  <button class="btn btn-windows cancel"   onclick="cancel();"  type="button">取消</button>
        </div>
	  </div>
	</div>
	
	<form  id="qua_form" action="${pageContext.request.contextPath}/qualification/update.html" method="post">
		<input type="hidden" name="did" id="adid" value="">
		<input type="hidden" name="qid" id="qid" value="">
	</form>
	
	
  </body>
</html>
