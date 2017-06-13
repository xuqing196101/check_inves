<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
        <%@ include file="/WEB-INF/view/common.jsp"%>
		<title>采购档案查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
			$(function() {
				$("#name").val("${name}");
				$("#archiveCode").val("${archiveCode}");
				$("#contractCode").val("${contractCode}");
				$("#year").val("${year}");
				$("#purchaseDep").val("${purchaseDep}");
				$("#purchaseType").val("${purchaseType}");
				$("#supplierName").val("${supplierName}");
				$("#status").val("${status}");
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${archiveList.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${archiveList.total}",
					startRow: "${archiveList.startRow}",
					endRow: "${archiveList.endRow}",
					groups: "${archiveList.pages}" >= 5 ? 5 : "${archiveList.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							var name = "${name}";
							var archiveCode = "${archiveCode}";
							var contractCode = "${contractCode}";
							var year = "${year}";
							var purchaseDep = "${purchaseDep}";
							var purchaseType = "${purchaseType}";
							var supplierName = "${supplierName}";
							var status = "${status}";
							location.href = "${pageContext.request.contextPath }/purchaseArchive/queryArchive.do?name=" + name + "&archiveCode=" + archiveCode + "&contractCode=" + contractCode + "&year=" + year + "&purchaseDep=" + purchaseDep + "&purchaseType=" + purchaseType + "&supplierName=" + supplierName + "&status=" + status + "&page=" + e.curr;
						}
					}
				});
			})

			//重置
			function resetResult() {
				$("#form").find("input").val("");
				var purchaseMode = document.getElementById("purchaseType").options;
				purchaseMode[0].selected = true;
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
						<a href="javascript:void(0);">保障作业</a>
					</li>
					<li>
						<a href="javascript:void(0)">产品质量管理</a>
					</li>
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/purchaseArchive/queryArchive.html');">采购档案查询</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>采购档案查询</h2>
			</div>

			<form action="${pageContext.request.contextPath }/purchaseArchive/queryArchive.html" method="post" id="form">
				<h2 class="search_detail">
			<ul class="demand_list">
				<li>
			    	<label class="fl">档案名称：</label><span><input type="text" id="name" name="name"/></span>
			    </li>
		  		<li>
			    	<label class="fl">档案编号：</label><span><input type="text" id="archiveCode" name="archiveCode"/></span>
			    </li>
		  		<li>
			    	<label class="fl">合同编号：</label><span><input type="text" id="contractCode" name="contractCode"/></span>
			    </li>
			    <li>
			    	<label class="fl">采购方式：</label>
			    	<span>
			    		<select id="purchaseType" name="purchaseType">
		  					<option value="">请选择</option>
		  					<option value="单一来源">单一来源</option>
		  					<option value="公开招标">公开招标</option>
		  					<option value="邀请招标">邀请招标</option>
		  					<option value="询价">询价</option>
		  					<option value="竞争性谈判">竞争性谈判</option>
		  				</select>
			    	</span>
			    </li>
			    <li>
			    	<label class="fl">预算年度：</label><span><input type="text" id="year" name="year"/></span>
			    </li>
			    <li>
			    	<label class="fl">采购机构：</label><span><input type="text" id="purchaseDep" name="purchaseDep"/></span>
			    </li>
		  		<li>
			    	<label class="fl">供应商名称：</label><span><input type="text" id="supplierName" name="supplierName"/></span>
			    </li>
			    <li>
			    	<label class="fl">状态：</label>
			    	<span>
			    		<select id="status" name="status">
				  			<option value="">请选择</option>
				  			<option value="1">暂存</option>
				  			<option value="2">审核通过</option>
				  			<option value="3">审核不通过</option>
				  			<option value="4">已归档</option>
				  			<option value="5">已提交</option>
				  		</select>
			    	</span>
			    </li>
			    <div class="tc col-md-12">
			  		<button class="btn" type="submit">查询</button>
			  		<button class="btn" type="button" onclick="resetResult()">重置</button>
		  		</div>
	  		</ul>
	  		<div class="clear"></div>
	  	</h2>
			</form>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th>序号</th>
							<th>档案名称</th>
							<th>档案编号</th>
							<th>合同编号</th>
							<th>预算年度</th>
							<th>采购机构</th>
							<th>采购方式</th>
							<th>产品名称</th>
							<th>供应商名称</th>
							<th>采购文件上报时间</th>
							<th>采购文件批复时间</th>
							<th>合同草案上报时间</th>
							<th>合同草案批复时间</th>
							<th>正式合同上报时间</th>
							<th>正式合同批复时间</th>
							<th>首件检验和出厂验收时间</th>
							<th>发运和结算时间</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${archiveList.list }" var="archive" varStatus="vs">
							<tr class="tc">
								<td class="w50">${(vs.index+1)+(archiveList.pageNum-1)*(archiveList.pageSize)}</td>
								<td class="tl pl20">${archive.name }</td>
								<td class="tl pl20">${archive.code }</td>
								<td class="tl pl20">${archive.contractCode }</td>
								<td class="tl pl20">${archive.year }</td>
								<td class="tl pl20">${archive.purchaseDep }</td>
								<td>
									<c:forEach items="${kind}" var="kind">
										<c:if test="${kind.id == archive.purchaseType}">${kind.name}</c:if>
									</c:forEach>
								</td>
								<td class="tl pl20">${archive.productName }</td>
								<td class="tl pl20">${archive.supplierName }</td>
								<td class="tl pl20">
								  <fmt:formatDate value='${archive.reportAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								</td>
								<td class="tl pl20">
								  <fmt:formatDate value='${archive.applyAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								</td>
								<td class="tl pl20">
								  <fmt:formatDate value='${archive.draftGitAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								</td>
								<td class="tl pl20">
								  <fmt:formatDate value='${archive.draftReviewedAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								</td>
								<td class="tl pl20">
								  <fmt:formatDate value='${archive.formalGitAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								</td>
								<td class="tl pl20">
								  <fmt:formatDate value='${archive.formalReviewedAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								</td>
								<td></td>
								<td></td>
								<c:if test="${archive.status==1 }">
									<td>暂存</td>
								</c:if>
								<c:if test="${archive.status==2 }">
									<td>审核通过</td>
								</c:if>
								<c:if test="${archive.status==3 }">
									<td>审核不通过</td>
								</c:if>
								<c:if test="${archive.status==4 }">
									<td>已归档</td>
								</c:if>
								<c:if test="${archive.status==5 }">
									<td>已提交</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>
	</body>

</html>