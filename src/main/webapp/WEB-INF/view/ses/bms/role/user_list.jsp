<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
  
  <script type="text/javascript">
	  $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${list.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${list.total}",
			    startRow: "${list.startRow}",
			    endRow: "${list.endRow}",
			    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			       
			        return "${list.pageNum}";
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	$("#page").val(e.curr);
                		$("#form1").submit();
			        }
			    }
			});
	  });
	
  	function view(id){
  		window.location.href="${pageContext.request.contextPath}/user/show.html?id="+id;
  	}
  	
    function goBack(){
    	window.location.href="${pageContext.request.contextPath}/role/list.html";
    }
    
	function resetQuery(){
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
	
  </script>
  </head>
  <body>
   <!--面包屑导航开始-->
	   <div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			   <ul class="breadcrumb margin-left-0">
			   <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')" target="_blank"> 首页</a></li><li><a href="javascript:void(0);">支撑系统</a></li><li><a href="javascript:void(0);">后台管理</a></li><li class="active"><a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/role/list.html')">角色管理</a></li>
			   </ul>
			<div class="clear"></div>
		  </div>
	   </div>
      <div class="container">
		  <div class="headline-v2">
			  <h2>用户查询</h2>
		  </div>
		     <h2 class="search_detail">
		       	<form action="${pageContext.request.contextPath}/user/listByRole.html" id="form1" method="post" class="mb0">
		       		<input type="hidden" name="page" id="page">
		       		<input type="hidden" name="rId" value="${rid}">
			    	<ul class="demand_list">
			    	  <li>
				    	<label class="fl">用户名：</label><span><input type="text" id="loginName" value="${user.loginName}" name="loginName" class=""/></span>
				      </li>
			    	  <li>
				    	<label class="fl">姓名：</label><span><input type="text" id="relName" value="${user.relName}" name="relName" class=""/></span>
				      </li>
				    	<button type="submit"  class="btn fl mt1">查询</button>
				    	<button type="button" onclick="resetQuery()" class="btn fl mt1">重置</button>  	
			    	</ul>
		    	  	<div class="clear"></div>
		        </form>
		     </h2>
      
      	<!-- 表格开始-->
	    <div class="col-md-12 pl20 mt10">
		    <button class="btn btn-windows back" type="button" onclick="goBack()">返回</button>
	    </div>
	    <div class="content table_box">
            <table class="table table-bordered table-condensed table-hover table-striped">
            <thead>
					<tr>
					  <th class="info w50">序号</th>
					  <th class="info" width="12%">用户名</th>
					  <th class="info" width="15%">姓名</th>
					  <th class="info" width="23%">单位</th>
					  <th class="info" width="140">联系电话</th>
					  <th class="info">角色</th>
					</tr>
		      </thead>
		      <tbody>
				<c:forEach items="${list.list}" var="user" varStatus="vs">
					<tr>
					  <td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
					  <td class="tl" ><a href="#" onclick="view('${user.id}');">${user.loginName}</a></td>
					  <td class="tl">${user.relName}</td>
					  <td class="tl">
					  	<c:if test="${user.org != null }">${user.org.name}</c:if>
					  	<c:if test="${user.org == null }">${user.orgName}</c:if>
					  </td>
					  <td class="tc">${user.mobile}</td>
					  <td class="tl">
					  	<c:set var="roleCode" value=""/>
					  	<c:forEach items="${user.roles}" var="r" varStatus="vs">
			        		<c:if test="${vs.index == 0 }">
			        			${r.name}
			        			<c:set var="roleCode" value="${roleCode}${r.code}"/>
			        		</c:if>
			        		<c:if test="${vs.index > 0 }">
			        			,${r.name}
			        			<c:set var="roleCode" value="${roleCode},${r.code}"/>
			        		</c:if>
			        	</c:forEach>
			        		<input type="hidden" id="role_code" value="${roleCode}">
					  </td>
					</tr>
				</c:forEach>
				</tbody>
		       </table>
		    </div>
		  <div id="pagediv" align="right"></div>
	  </div>
  </body>
</html>
