<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>打印预览页面</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			//打印功能
			function dayin() {
				/**var LODOP = getLodop();
				if (LODOP) {
					LODOP.PRINT_INIT("打印表格"); 
					LODOP.ADD_PRINT_TABLE("0","0","100%","100%",document.getElementById("div_print").innerHTML);
					
				    LODOP.PREVIEW(); 
				}*/
				window.print();
			}
		</script>
	</head>

	<body>
		<div class="container">
			<div class="col-md-12 pl20 mt10">
				<input type="button" class="btn" value="打印表格" onclick="dayin()" id="print" />
			</div>

			<!-- 表格开始 -->
			<div class="content table_box" id="div_print">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50">序号</th>
							<th class="w100">姓名</th>
							<th>身份证号</th>
							<th>试卷编号</th>
							<th>所属单位</th>
							<th>得分</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paperUserList }" var="paper" varStatus="p">
							<tr>
								<td class="tc">${p.index+1 }</td>
								<td class="tc">${paper.relName }</td>
								<td class="tc">${paper.card }</td>
								<td class="tc">${paper.code }</td>
								<td class="tl pl20">${paper.unitName }</td>
								<td class="tc">${paper.score }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</body>

</html>