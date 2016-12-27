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
				    pages: "${departmentList.pages}", //总页数
				    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
				    skip: true, //是否开启跳页
				    total:"${departmentList.total}",
				    startRow:"${departmentList.startRow}",
				    endRow:"${departmentList.endRow}",
				    groups: "${departmentList.pages}">=5?5:"${departmentList.pages}", //连续显示分页数
				    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
				        var page = location.search.match(/page=(\d+)/);
				        return page ? page[1] : 1;
				    }(), 
				    jump: function(e, first){ //触发分页后的回调
				        if(!first){ //一定要加此判断，否则初始时会无限刷新
				            	location.href = '${pageContext.request.contextPath}/pqinfo/getAll.do?page='+e.curr;
				        }
				    }
				});
		  })
			function show(department){
			 	var id = $("#id").val();
				window.location.href = "${pageContext.request.contextPath }/look/auditByDepartment.html?department=" + department+"&id="+id;
			}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0)"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">保障作业系统</a>
					</li>
					<li>
						<a href="javascript:void(0)">采购计划管理</a>
					</li>
					<li class="active">
						<a href="javascript:void(0)">采购计划审核</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		
		<div class="container">
	   		<div class="headline-v2">
	   			<h2>需求部门列表</h2>
	   		</div>
	   
	<!-- 表格开始-->
	   <div class="content table_box">
	    	<table class="table table-bordered table-condensed">
			<thead>
			<tr>
			  <th class="info w50">序号</th>
			  <th class="info">需求部门名称</th>
			</tr>
			</thead>
			<input type="hidden" id="id" value="${id}">
			<c:forEach items="${departmentList.list }" var="obj" varStatus="vs">
				<tr>
					<td class="tc pointer" onclick="show('${obj.department}')">${(vs.index+1)+(departmentList.pageNum-1)*(departmentList.pageSize)}</td>
					<td class="tl pl20 pointer" onclick="show('${obj.department}')">${obj.department}</td>
				</tr>
			</c:forEach>
	        </table>
	     </div>
	   <div id="pagediv" align="right"></div>
	   
	  </div>	
	</body>
</html>