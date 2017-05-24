<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>采购人员成绩查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			$(function() {
				$("#relName").val("${relName}");
				$("#code").val("${code}");
				var status_options = document.getElementById("status").options;
				for(var i = 0; i < status_options.length; i++) {
					if($(status_options[i]).attr("value") == "${status}") {
						status_options[i].selected = true;
					}
				}
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${purchaserResultList.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${purchaserResultList.total}",
					startRow: "${purchaserResultList.startRow}",
					endRow: "${purchaserResultList.endRow}",
					groups: "${purchaserResultList.pages}" >= 5 ? 5 : "${purchaserResultList.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							var relName = "${relName}";
							var status = "${status}";
							var code = "${code}";
							location.href = "${pageContext.request.contextPath }/purchaserExam/result.do?relName=" + relName + "&status=" + status + "&code=" + code + "&page=" + e.curr;
						}
					}
				});
			})

			//查询方法
			function query() {
				var relName = $("#relName").val();
				var status = $("#status").val();
				var code = $("#code").val();
				if((relName == "" || relName == null) && (status == "" || status == null) && (code == "" || code == null)) {
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/result.do";
					return;
				} else {
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/result.do?relName=" + relName + "&status=" + status + "&code=" + code;
				}
			}

			//重置方法
			function reset() {
				$("#relName").val("");
				$("#code").val("");
				var status = document.getElementById("status").options;
				status[0].selected = true;
			}
		</script>

	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0);">人员管理</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/purchaserExam/result.html')">成绩管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container">
			<div class="headline-v2">
				<h2>采购人员成绩列表</h2>
			</div>

			<h2 class="search_detail">
			<ul class="demand_list">
		    	<li>
			    	<label class="fl">采购人员姓名：</label><span><input type="text" id="relName" name="relName" class=""/></span>
			    </li>
			    <li>
			    	<label class="fl">试卷编号：</label>
			    	<span>
				    	<input type="text" id="code" name="code" class=""/>
			   		</span>
			    </li>
			    <li>
			    	<label class="fl">考试状态：</label>
			    	<span>
				    	<select name="status" id="status" class="w178">
				    		<option value="">请选择</option>
				    		<option value="及格">及格</option>
				    		<option value="不及格">不及格</option>
				    	</select>
			   		</span>
			    </li>
			    <button type="button" onclick="query()" class="btn fl mt1">查询</button>
			    <button type="button" onclick="reset()" class="btn fl mt1">重置</button>
		    </ul>
		    <div class="clear"></div>
	 	</h2>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50">序号</th>
							<th class="w160">采购人员姓名</th>
							<th>身份证号</th>
							<th>试卷编号</th>
							<th>考试时间</th>
							<th>得分</th>
							<th>考试状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${purchaserResultList.list }" varStatus="vs" var="result">
							<tr class="tc">
								<td>${(vs.index+1)+(purchaserResultList.pageNum-1)*(purchaserResultList.pageSize)}</td>
								<td>${result.relName }</td>
								<td>${result.card }</td>
								<td>${result.code }</td>
								<td>${result.formatDate }</td>
								<td>${result.score }</td>
								<td>${result.status }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>
	</body>

</html>