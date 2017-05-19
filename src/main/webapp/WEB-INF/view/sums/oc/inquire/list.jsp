<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>投诉记录列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript">
/* 分页 */
$(function() {
    laypage({
      cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
      pages : "${info.pages}", //总页数
      skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
      skip : true, //是否开启跳页
      total : "${info.total}",
      startRow : "${info.startRow}",
      endRow : "${info.endRow}",
      groups : "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
      curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
        return "${info.pageNum}";
      }(),
      jump : function(e, first) { //触发分页后的回调
    	if(!first){ //一定要加此判断，否则初始时会无限刷新
      		location.href = "${pageContext.request.contextPath }/onlineComplaints/recordQuery.do?page=" + e.curr;
        }
      }
    });
  });

//重置
function resetQuery() {
	$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	window.location.href = "${pageContext.request.contextPath }/onlineComplaints/recordQuery.html";
}
</script>
</head>
<body>
  <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">业务监管</a></li><li><a href="javascript:void(0)">网上投诉</a></li>
		   <li><a href="javascript:void(0)">投诉记录查询</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
    <!-- 网上投诉列表页面开始 -->
	<div class="container">
	 <div class="headline-v2">
		<h2>投诉记录列表</h2>
	 </div>
    <div class="search_detail">
       <form action="${pageContext.request.contextPath }/onlineComplaints/recordQuery.html" method="post" class="mb0" id = "form1">
    	<ul class="demand_list">
    	<li>
	    	<label class="fl">投诉人名称：</label>
			<input type="text" id="" class="" name = "name" value="${complaint.name }"/>
	      </li>
    	  <li>
	    	<label class="fl">投诉人类型：</label>
	    	  <select class="w178" name="type">
	    	    <option value="">--请选择--</option>
	    	    <option value="1" <c:if test="${1==complaint.type}">selected="selected"</c:if>>个人</option>
	    	    <option value="0" <c:if test="${0==complaint.type}">selected="selected"</c:if>>单位</option>
	    	  </select>
	      </li>
    	  <li>
	    	<label class="fl">投诉对象：</label>
			<input type="text" id="" class="" name = "complaintObject" value="${complaint.complaintObject }"/>
	      </li>
	      <input class="btn fl mt1" type="submit" value="查询" /> 
	      <input class="btn fl mt1" type="button" onclick="resetQuery()" value="重置"/>	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<!-- <div class="col-md-12 pl20 mt10">
		<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
		<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
		<button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
	</div>  -->  
	<div class="content table_box">
	<table class="table table-bordered table-condensed table-hover">
		<thead>
			<tr class="info">
				<th class="w50">序号</th>
				<th width="16%">投诉人名称</th>
				<th  width="12%">投诉人类型</th>
				<th width="16%">投诉对象</th>
				<th width="37%">投诉事项</th>
				<th>处理情况</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${info.list }" var="complaint" varStatus="vs">
			<tr class="tc" onclick="window.location.href = '${pageContext.request.contextPath }/onlineComplaints/view.html?id=${complaint.id }'">
				<td class="w50" >${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
				<td class="tl" >${complaint.name }</td>
				<td class="tc">
					<c:if test="${complaint.type == 1 }">个人</c:if>
					<c:if test="${complaint.type == 0 }">单位</c:if>
				</td>
				<td class="tl">${complaint.complaintObject }</td>
				<td class="tl" <c:if test="${fn:length(complaint.complaintMatter) > 22 }">title="${complaint.complaintMatter }"</c:if>>  
					<c:if test="${fn:length(complaint.complaintMatter) > 22 }">${fn:substring(complaint.complaintMatter, 0, 22)}...</c:if>
					<c:if test="${fn:length(complaint.complaintMatter) <= 22 }">${complaint.complaintMatter }</c:if>
				</td>
				<td class="tc">
					<c:if test="${complaint.status == 0 }">未处理</c:if>
					<c:if test="${complaint.status == 1 }">已立项</c:if>
					<c:if test="${complaint.status == 2 }">已驳回</c:if>
					<c:if test="${complaint.status == 3 }">已公示</c:if>
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