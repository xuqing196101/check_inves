<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>供应商列表页面</title>
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
      		location.href = "${pageContext.request.contextPath }/product/supplier.do?page=" + e.curr;
        }
      }
    });
  });

//重置
function resetQuery() {
	var prodid = $("#prodid").val();
	$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	window.location.href = "${pageContext.request.contextPath}/product/supplier.html?prodid="+prodid;
}
</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">保障作业</a></li><li><a href="javascript:void(0)">定型产品竞价</a></li>
		   <li class="active"><a href="javascript:void(0)">定型产品管理</a></li><li class="active"><a href="javascript:void(0)">供应商列表</a></li>
		</ul>
        <div class="clear"></div>
      </div>
    </div>
    
<!-- 供应商列表页面开始 -->
	<div class="container">
    <div class="search_detail">
       <form action="${pageContext.request.contextPath}/product/supplier.html" method="post" class="mb0" id = "form1">
    	<input id = "prodid" name = "prodid" value = "${prodid }" style="display: none;">
    	<ul class="demand_list">
		<li>
			<label class="fl">供应商证书状态：</label>
			<select class="w178" name = "status">
				<option value="0" <c:if test="${'0'==status}">selected="selected"</c:if>>-请选择-</option>
	    	    <option value="1" <c:if test="${'1'==status}">selected="selected"</c:if>>过期</option>
	    	    <option value="2" <c:if test="${'2'==status}">selected="selected"</c:if>>未过期</option>
			</select>
		</li>
		<button type="submit" class="btn">查询</button>
		<button type="reset" class="btn" onclick="resetQuery()">重置</button>  	
		</ul>
    	  <div class="clear"></div>
       </form>
     </div>
     
<!-- 表格开始 -->
	<div class="col-md-12 pl20 mt10">
	<button class="btn btn-windows back" type="button" onclick="window.location.href = '${pageContext.request.contextPath}/product/list.html'">返回</button>
	</div>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w50 info">序号</th>
		  <th class="info">供应商名称</th>
		  <th class="info">证书有效期至</th>
		  <th class="info">是否过期</th>
		</tr>
		</thead>
		<c:forEach items="${info.list }" var="supplier" varStatus="vs">
			<tr>
				<td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
				<td class="tc">${supplier.supplier.supplierName }</td>
				<td class="tc">
					<fmt:formatDate value="${supplier.certValidPeriod }" pattern="yyyy-MM-dd" /> 
				</td>
				<td class="tc">
				<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
					<c:choose>  
         	 			<c:when test="${nowDate-supplier.certValidPeriod.getTime() > 0}">过期</c:when>  
          				<c:when test="${nowDate-supplier.certValidPeriod.getTime() < 0}">未过期</c:when>  
          			</c:choose> 
				</td>
			</tr> 
		</c:forEach>
		</table>
   </div>
      <div id="pagediv" align="right"></div>
   </div>
</body>
</html>