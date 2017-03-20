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
	$(function() {
		laypage({
			cont : $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
			pages : "${info.pages}", //总页数
			skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			skip : true, //是否开启跳页
			total : "${info.total}",
			startRow : "${info.startRow}",
			endRow : "${info.endRow}",
			groups : "${info.pages}" >= 5 ? 5 : "${info.pages}", //连续显示分页数
			curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),
			jump : function(e, first) { //触发分页后的回调
				if (!first) { //一定要加此判断，否则初始时会无限刷新
					location.href = "${pageContext.request.contextPath }/onlineComplaints/dealWith.do?page="
							+ e.curr;
				}
			}
		});
	})

	

	//检查全选
	function check() {
		
		var count = 0;
		var index =0;
		var info = document.getElementsByName("info");		
		for (var i = 0; i < info.length; i++) {
			if (info[i].checked == true) {
				count++;
				index = i;
			}
		}
		
		if (count == 1) {
			var id = info[index].value;					
			window.location.href="${pageContext.request.contextPath}/onlineComplaints/dealWith.do?id="+id;
		}
	}
	function gongbu() {
		var id = [];
		$('input[name="info"]:checked').each(function() {
			id.push($(this).val());
		});
		if(id.length == 1) {
			window.location.href = "${pageContext.request.contextPath}/onlineComplaints/dealWith.do?id="+id;
		} else if(id.length > 1) {
			layer.alert("只能选择一个", {
				offset: ['122px', '390px'],
				shade: 0.01
			});
		} else {
			layer.alert("请选择需要公布的处理信息", {
				offset: ['122px', '390px'],
				shade: 0.01
			});
		}
	}
	<!--
	$.ajax({
		url:"${pageContext.request.contextPath}/onlineComplaints/Test.do",
		type:"GET",
		data:{"id":id},
		success:function(data){
			if(data != "0"){
				alert("此信息已处理");
			}
			
		},
	});
	-->
</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0)">首页</a></li>
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
		<div class="headline-v2">
			<h2>投诉处理列表</h2>

		</div>

		<form action="" method="post" class="mb0">

			<div class="content table_box">
				<div class="col-md-12 col-sm-12 col-xs-12 mt10 tc">
					<button class="btn" type="button" onclick="check()">立项</button>
					<button class="btn" type="button" onclick="gongbu()">公布</button>
				</div>
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50">选择</th>
							<th class="w50">序号</th>
							<th>投诉人名称</th>
							<th>投诉人类型</th>
							<th>投诉对象</th>
							<th width="35%">投诉事项</th>
							<th>处理情况</th>
						</tr>
					</thead>
					<tbody>
						<!-- 获取对象时list.被封装在list里面了complaint集合-  var就是下面的值从result里获取-->
						<c:forEach items="${info.list }" varStatus="vs" var="result">
							<!-- -ondealwith 的值里面要带‘’ -->
							<tr class="tc" >
								<!-- onclick="check"前面选择这个框的触发事件  value="${list.id}获取result集合里id的值 -->
								<td class="w50"><input type="checkbox"    value="${result.id }" name="info"  /></td>
								<td class="w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
								<td>${result.name }</td>
								<td><c:if test="${result.type=='0'}">
								               单位
								     </c:if> <c:if test="${result.type=='1'}">
								               个人
								     </c:if></td>
								<td>${result.complaintObject }</td>
								<td>${result.complaintMatter }</td>
								<td><c:if test="${result.status=='0'}">
								             未处理
								     </c:if> <c:if test="${result.status=='1'}">
								            立项处理
								     </c:if> <c:if test="${result.status=='2'}">
								           立项驳回
								     </c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</form>
		<div id="pageDiv" align="right"></div>
	</div>
</body>
</html>