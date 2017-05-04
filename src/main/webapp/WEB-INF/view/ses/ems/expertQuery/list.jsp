<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${result.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${result.total}",
					startRow: "${result.startRow}",
					endRow: "${result.endRow}",
					groups: "${result.pages}" >= 5 ? 5 : "${result.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						//var page = location.search.match(/page=(\d+)/);
						//return page ? page[1] : 1;
						return "${result.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#page").val(e.curr);
							$("#formSearch").submit();
						}
					}
				});
			});
		</script>
		<script type="text/javascript">
			function clearSearch() {
				$("#relName").attr("value", "");
				$("#status option:selected").removeAttr("selected");
				$("#mobile").attr("value", "");
				$("#formSearch").submit();
			}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0)"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家入库查询</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 我的订单页面开始-->
		<div class="container">
			<div class="headline-v2">
				<h2>入库专家列表</h2>
			</div>
			<h2 class="search_detail">  
   		<form action="${pageContext.request.contextPath}/expertQuery/list.html"  method="post" id="formSearch"  class="mb0"> 
		  	 <input type="hidden" name="page" id="page">
		  	<ul class="demand_list">
     		<li>
          <label class="fl">专家姓名：</label><span><input class="w220" type="text" id="relName" name="relName" value="${expert.relName }"></span>
        </li>
        <li>
          <label class="fl">手机号：</label><span><input class="w220" type="text" id="mobile" name="mobile" value="${expert.mobile }"></span>
        </li>
  			<li>
        <label class="fl">审核状态：</label>
        <span class="fl">
          <select name="status" id="status" class="w220">
             <option selected="selected" value=''>全部</option>
             <option <c:if test="${expert.status =='4' }">selected</c:if> value="4">复审通过</option>
             <option <c:if test="${expert.status =='5' }">selected</c:if> value="5">复审未通过</option>
             <option <c:if test="${expert.status =='6' }">selected</c:if> value="6">待复查</option>
             <option <c:if test="${expert.status =='7' }">selected</c:if> value="7">复查通过</option>
             <option <c:if test="${expert.status =='8' }">selected</c:if> value="8">复查未通过</option>
           </select>
        </span>
     	 </li>
		  </ul>
		  <div class="col-md-12 clear tc mt10">
			  <input class="btn mt1"  value="查询" type="submit">
	      <input class="btn mt1" id="button" onclick="clearSearch();" value="重置" type="reset">
	    </div>
	    <div class="clear"></div>
    </form>
   </h2>
			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w50">序号</th>
							<th class="info">专家姓名</th>
							<th class="info w50">性别</th>
							<th class="info">毕业院校及专业</th>
							<th class="info">手机</th>
							<th class="info">类别</th>
							<th class="info">审核状态</th>
						</tr>
					</thead>
					<c:forEach items="${result.list }" var="e" varStatus="vs">
						<tr class="pointer">
							<td class="tc w50"  class="tc w50">${(vs.index+1)+(result.pageNum-1)*(result.pageSize)}</td>
							<td class="tl pl20" >
								<a href="${pageContext.request.contextPath}/expert/view.html?id=${e.id}">${e.relName}</a>
							</td>
							<td class="tc w50" >${e.gender}</td>
							<td class="tl pl20" >${e.graduateSchool }</td>
							<td class="tl pl20" >${e.mobile }</td>
							<td class="tl pl20" >${e.expertsTypeId}</td>
							<td  class="tc" id="${e.id}">
								<c:if test="${e.status eq '4' and e.isProvisional eq '1'}">
									<span class="label rounded-2x label-dark">临时</span>
								</c:if>
								<c:if test="${e.status eq '4' and e.isProvisional eq '0'}">
									<span class="label rounded-2x label-u">复审通过</span>
								</c:if>
								<c:if test="${e.status eq '5' }">
									<span class="label rounded-2x label-dark">复审未通过</span>
								</c:if>
								<c:if test="${e.status eq '6' }">
									<span class="label rounded-2x label-dark">待复查</span>
								</c:if>
								<c:if test="${e.status eq '7' }">
									<span class="label rounded-2x label-u">复查通过</span>
								</c:if>
								<c:if test="${e.status eq '8' }">
									<span class="label rounded-2x label-dark">复查未通过</span>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div id="pagediv" align="right"></div>
			</div>
		</div>

	</body>

</html>