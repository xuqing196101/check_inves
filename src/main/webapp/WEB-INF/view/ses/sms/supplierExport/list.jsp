<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->

<head>
<%@ include file="../../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<script type="text/javascript">
			$(function() {
				if("${type}"==2){
				    $("#liActive2").attr("class","active");
				    $("#liActive1").attr("class","");
				    $("#dep_tab-1").attr("class","tab-pane fade in active");
				    $("#dep_tab-0").attr("class","tab-pane fade in");
				  }else{
					  $("#liActive1").attr("class","active");
					  $("#liActive2").attr("class","");
					  $("#dep_tab-0").attr("class","tab-pane fade in active");
					  $("#dep_tab-1").attr("class","tab-pane fade in");
				  }
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${list.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${list.total}",
					startRow: "${list.startRow}",
					endRow: "${list.endRow}",
					groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						return "${list.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#page").val(e.curr);
							$("#type").val(1);
						  $("#form1").submit();
						}
					}
				});
				laypage({
					cont: $("#pagediv1"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${listExpert.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${listExpert.total}",
					startRow: "${listExpert.startRow}",
					endRow: "${listExpert.endRow}",
					groups: "${listExpert.pages}" >= 5 ? 5 : "${listExpert.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						return "${listExpert.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#pageEx").val(e.curr);
						  $("#type").val(2);
						  $("#formEx").submit();
						}
					}
				});
			});
			function clearValue(){
				$("#name").val("");
			}
			function clearExValue(){
				$("#nameEx").val("");
			}
		</script>
</head>

<body>

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a
					href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">
						首页</a></li>
				<li><a href="javascript:void(0);">支撑环境</a></li>
				<%--<li>
						<a href="javascript:void(0);">进口代理商</a>
					</li>
					<li>
						<a href="javascript:void(0);">进口代理商管理</a>
					</li>--%>
				<li class="active"><a href="javascript:void(0);">两库审核状态</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 我的页面开始-->
	<div class="container">
		<div class="headline-v2">
			<h2>两库审核状态列表</h2>
		</div>

		<div class="tab-content mt10">
			<div class="tab-v2">
				<ul class="nav nav-tabs bgwhite">
					<li class="active" id="liActive1"><a href="#dep_tab-0" data-toggle="tab"
						class="f18">供应商审核状态</a></li>
					<li id="liActive2"><a href="#dep_tab-1" data-toggle="tab" class="f18">专家审核状态</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane fade in active" id="dep_tab-0">
						<h2 class="search_detail">
							<form id="form1"
								action="${pageContext.request.contextPath}/supplierExport/list.html"
								method="post" class="mb0">
								<input type="hidden" name="page" id="page"/>
								<input type="hidden" name="type" id="type" value="1"/>
								<ul class="demand_list">
									<li class="fl"><label class="fl">采购机构：</label><span>
									<select name="name" id="name" class="w220">
									  <option value=''>全部</option>
					           <c:forEach items="${allOrg}" var="org">
					             <option value="${org.shortName}" <c:if test="${name eq org.shortName}">selected</c:if>>${org.shortName}</option>
					           </c:forEach>
					           </select>
									</li>
								</ul>
								<button type="submit" class="btn fl">查询</button>
								<button type="button" onclick="clearValue();" class="btn fl">重置</button>
								<div class="clear"></div>
							</form>
						</h2>
						<!-- 表格开始-->
						<div class="content table_box">
							<table
								class="table table-bordered table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th class="w50 info">序号</th>
										<th class="info" width="30%">采购机构</th>
										<th class="info">注册数量</th>
										<th class="info">待审核数量</th>
										<th class="info">审核通过数量</th>
										<th class="info">退回修改数量</th>
										<th class="info">审核不通过数量</th>
									</tr>
								</thead>
								<c:forEach items="${list.list}" var="su" varStatus="vs">
								 <tr>
								  <td>${(vs.index+1)+(list.pageNum-1)*(list.pageSize)} </td>
								  <td>${su.shortName}</td>
								  <td>${su.reg}</td>
								  <td>${su.statusOne}</td>
								  <td>${su.statusTwo}</td>
								  <td>${su.statusThree}</td>
								  <td>${su.statusFour}</td>
								 </tr>
								</c:forEach>
							</table>
							<div id="pagediv" align="right"></div>
						</div>
					</div>
					<div class="tab-pane fade in" id="dep_tab-1">
					   <h2 class="search_detail">
							<form id="formEx"
								action="${pageContext.request.contextPath}/supplierExport/list.html"
								method="post" class="mb0">
								<input type="hidden" name="pageEx" id="pageEx"/>
								<input type="hidden" name="type" id="type" value="2"/>
								<ul class="demand_list">
									<li class="fl"><label class="fl">采购机构：</label><span>
									<select name="nameEx" id="nameEx" class="w220">
									  <option value=''>全部</option>
					           <c:forEach items="${allOrg}" var="org">
					             <option value="${org.shortName}" <c:if test="${nameEx eq org.shortName}">selected</c:if>>${org.shortName}</option>
					           </c:forEach>
					           </select>
									</span>
									</li>
								</ul>
								<button type="submit" class="btn fl">查询</button>
								<button type="button" onclick="clearExValue();" class="btn fl">重置</button>
								<div class="clear"></div>
							</form>
						</h2>
						<!-- 表格开始-->
						<div class="content table_box">
							<table
								class="table table-bordered table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th class="w50 info">序号</th>
										<th class="info" width="20%">采购机构</th>
										<th class="info">注册数量</th>
										<th class="info">待审核数量</th>
										<th class="info">审核通过数量</th>
										<th class="info">退回修改数量</th>
										<th class="info">审核不通过数量</th>
										<th class="info">军队数量</th>
										<th class="info">地方数量</th>
									</tr>
								</thead>
								<c:forEach items="${listExpert.list}" var="su" varStatus="vs">
								 <tr>
								  <td>${(vs.index+1)+(list.pageNum-1)*(list.pageSize)} </td>
								  <td>${su.shortName}</td>
								  <td>${su.reg}</td>
								  <td>${su.statusOne}</td>
								  <td>${su.statusTwo}</td>
								  <td>${su.statusThree}</td>
								  <td>${su.statusFour}</td>
								  <td>${su.expertArmy}</td>
								  <td>${su.expertsLocal}</td>
								 </tr>
								</c:forEach>
							</table>
							<div id="pagediv1" align="right"></div>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>

</html>