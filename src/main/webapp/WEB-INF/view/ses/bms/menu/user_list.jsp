<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
  
  	<script type="text/javascript">
/* 	  $(function() {
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${result.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${result.total}",
					startRow: "${result.startRow}",
					endRow: "${result.endRow}",
					groups: "${result.pages}" >= 3 ? 3 : "${result.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						return "${result.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#page").val(e.curr);
							$("#form1").submit();
						}
					}
				});
			}); */
  </script>
  </head>
  <body>
      <div class="container">
      	<h2 class="search_detail dnone">
		       	<form action="${pageContext.request.contextPath}/preMenu/getUserByMid.html" id="form1" method="post" class="mb0">
		       		<input type="hidden" name="page" id="page">
		       		<input type="hidden" name="mid" id="mid">
		        </form>
		     </h2>
	    <div class="content table_box">
            <table class="table table-bordered table-condensed table-hover table-striped">
            <thead>
					<tr>
					  <!-- <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th> -->
					  <th class="info w50">序号</th>
					  <th class="info">用户名</th>
					  <th class="info">姓名</th>
					  <th class="info">单位</th>
					  <th class="info">联系电话</th>
					</tr>
		      </thead>
		      <tbody>
				<c:forEach items="${result.list}" var="user" varStatus="vs">
					<tr>
					  <%-- <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${user.id}" /></td> --%>
					  <td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
					  <td class="tl pl20" >${user.loginName}</td>
					  <td class="tl pl20">${user.relName}</td>
					  <td class="tl pl20">
					  	<c:if test="${user.org != null }">${user.org.name}</c:if>
					  	<c:if test="${user.org == null }">${user.orgName}</c:if>
					  </td>
					  <td class="tc">${user.mobile}</td>
					</tr>
				</c:forEach>
				</tbody>
		       </table>
		    </div>
	  </div>
  </body>
</html>
