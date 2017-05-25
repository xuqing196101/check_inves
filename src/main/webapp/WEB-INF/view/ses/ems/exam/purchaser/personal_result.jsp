<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript">
			$(function() {
				$("#code").val("${code}");
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${list.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${list.total}",
					startRow: "${list.startRow}",
					endRow: "${list.endRow}",
					groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							var code = "${code}";
							location.href = "${pageContext.request.contextPath }/purchaserExam/personalResult.do?code=" + code + "&page=" + e.curr;
						}
					}
				});
			})

			//查询
			function query() {
				var code = $("#code").val();
				if(code == "" || code == null) {
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/personalResult.do";
				} else {
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/personalResult.do?code=" + code;
				}

			}

			//重置
			function reset() {
				$("#code").val("");
			}
		</script>

	</head>

	<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
	  <ul class="breadcrumb margin-left-0">
	    <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
		<li><a href="javascript:void(0)">考试系统</a></li>
		<li><a href="javascript:jumppage('${pageContext.request.contextPath}/purchaserExam/personalResult.html')">成绩查询</a></li>
	  </ul>
	  <div class="clear"></div>
    </div>
  </div>
		<div class="container">
			<div class="headline-v2">
				<h2>成绩信息</h2>
			</div>

			<h2 class="search_detail">
			<ul class="demand_list">
				<li>
			    	<label class="fl">考试编号：</label><span><input type="text" id="code"/></span>
			    </li>
    		</ul>
    		   <button type="button" onclick="query()" class="btn fl mt1">查询</button>
		    	<button type="button" onclick="reset()" class="btn fl mt1">重置</button>
    		<div class="clear"></div>
    	</h2>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr class="info">
							<th class="w50">序号</th>
							<th width="20%">采购人员姓名</th>
							<th width="20%">考试编号</th>
							<th width="15%">得分</th>
							<th width="20%">考试状态</th>
							<th>考试时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list.list}" var="l" varStatus="vs">
							<tr>
								<td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
								<td class="tl">${l.relName }</td>
								<td class="tc">${l.code }</td>
								<td class="tc">${l.score }</td>
								<td class="tc">${l.status }</td>
								<td class="tc">${l.formatDate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>
	</body>

</html>