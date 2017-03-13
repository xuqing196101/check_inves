<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>投诉页面</title>
<script type="text/javascript">
	function dealWith(){
		window.location.href = "${pageContext.request.contextPath }/onlineComplaints/dealWith.do";
	}
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)"> 首页</a></li>
				<li><a href="javascript:void(0)">业务监管</a></li>
				<li><a href="javascript:void(0)">网上投诉处理</a></li>
				<li class="active"><a href="javascript:void(0)">网上投诉</a></li>
				<li class="active"><a href="javascript:void(0)">投诉处理</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 投诉列表页 -->
	<div class="container">
		<div>
			<form action="" method="post" class="mb0">
			<div class="col-md-12 pl20 mt10">
				<button class="btn" type="button" onclick="dealWith()">查看</button>
		   </div>
			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
						    <th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
							<th>序号</th>
							<th>投诉人名称</th>
							<th>投诉人类型</th>
							<th>投诉对象</th>
							<th>投诉事项</th>
						</tr>
					</thead>
					
				</table>
			</div>
		<div id="pageDiv" align="right"></div>
		</form>
	</div>
	</div>
</body>
</html>