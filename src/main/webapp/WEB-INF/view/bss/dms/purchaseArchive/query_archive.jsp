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
			<div class="m_row_5">
	    <div class="row">
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">档案名称：</div>
	          <div class="col-xs-8 f0 lh0">
							<input type="text" id="name" name="name" class="w100p h32 f14 mb0">
	          </div>
	        </div>
	      </div>
	      
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">档案编号：</div>
	          <div class="col-xs-8 f0 lh0">
							<input type="text" id="archiveCode" name="archiveCode" class="w100p h32 f14 mb0">
	          </div>
	        </div>
	      </div>
	      
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">合同编号：</div>
	          <div class="col-xs-8 f0 lh0">
							<input type="text" id="contractCode" name="contractCode" class="w100p h32 f14 mb0">
	          </div>
	        </div>
	      </div>
	      
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购方式：</div>
	          <div class="col-xs-8 f0 lh0">
							<select id="purchaseType" name="purchaseType" class="w100p h32 f14">
		  					<option value="">请选择</option>
		  					<option value="单一来源">单一来源</option>
		  					<option value="公开招标">公开招标</option>
		  					<option value="邀请招标">邀请招标</option>
		  					<option value="询价">询价</option>
		  					<option value="竞争性谈判">竞争性谈判</option>
		  				</select>
	          </div>
	        </div>
	      </div>
				
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">预算年度：</div>
	          <div class="col-xs-8 f0 lh0">
							<input type="text" id="year" name="year" class="w100p h32 f14 mb0">
	          </div>
	        </div>
	      </div>
				
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购机构：</div>
	          <div class="col-xs-8 f0 lh0">
							<input type="text" id="purchaseDep" name="purchaseDep" class="w100p h32 f14 mb0">
	          </div>
	        </div>
	      </div>
				
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商名称：</div>
	          <div class="col-xs-8 f0 lh0">
							<input type="text" id="supplierName" name="supplierName" class="w100p h32 f14 mb0">
	          </div>
	        </div>
	      </div>
				
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">状态：</div>
	          <div class="col-xs-8 f0 lh0">
							<select id="status" name="status" class="w100p h32 f14">
				  			<option value="">请选择</option>
				  			<option value="1">暂存</option>
				  			<option value="2">审核通过</option>
				  			<option value="3">审核不通过</option>
				  			<option value="4">已归档</option>
				  			<option value="5">已提交</option>
				  		</select>
	          </div>
	        </div>
	      </div>
	    </div>
	    </div>
			
			<div class="tc">
				<button class="btn mb0" type="submit">查询</button>
				<button class="btn mb0 mr0" type="button" onclick="resetResult()">重置</button>
			</div>
	  	</h2>
			</form>

			<div class="content table_box over_auto">
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
								<td><div class="w30">${(vs.index+1)+(archiveList.pageNum-1)*(archiveList.pageSize)}</div></td>
								<td class="tl"><div class="w200">${archive.name }</div></td>
								<td class="tl"><div class="w100">${archive.code }</div></td>
								<td class="tl"><div class="w100">${archive.contractCode }</div></td>
								<td class="tl"><div class="w100">${archive.year }</div></td>
								<td class="tl"><div class="w100">${archive.purchaseDep }</div></td>
								<td>
									<div class="w100">
									<c:forEach items="${kind}" var="kind">
										<c:if test="${kind.id == archive.purchaseType}">${kind.name}</c:if>
									</c:forEach>
									</div>
								</td>
								<td class="tl"><div class="w100">${archive.productName }</div></td>
								<td class="tl"><div class="w100">${archive.supplierName }</div></td>
								<td class="tl">
									<div class="w100">
								  	<fmt:formatDate value='${archive.reportAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								  	</div>
								</td>
								<td class="tl">
									<div class="w100">
								  	<fmt:formatDate value='${archive.applyAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								  	</div>
								</td>
								<td class="tl">
									<div class="w100">
								  	<fmt:formatDate value='${archive.draftGitAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								  	</div>
								</td>
								<td class="tl">
									<div class="w100">
								  	<fmt:formatDate value='${archive.draftReviewedAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								  	</div>
								</td>
								<td class="tl">
									<div class="w100">
								  	<fmt:formatDate value='${archive.formalGitAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								  	</div>
								</td>
								<td class="tl">
									<div class="w100">
								  	<fmt:formatDate value='${archive.formalReviewedAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
								  	</div>
								</td>
								<td><div class="w100"></div></td>
								<td><div class="w100"></div></td>
								<c:if test="${archive.status==1 }">
									<td><div class="w30">暂存</div></td>
								</c:if>
								<c:if test="${archive.status==2 }">
									<td><div class="w100">审核通过</div></td>
								</c:if>
								<c:if test="${archive.status==3 }">
									<td><div class="w100">审核不通</div>过</td>
								</c:if>
								<c:if test="${archive.status==4 }">
									<td><div class="w100">已归档</div></td>
								</c:if>
								<c:if test="${archive.status==5 }">
									<td><div class="w100">已提交</div></td>
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