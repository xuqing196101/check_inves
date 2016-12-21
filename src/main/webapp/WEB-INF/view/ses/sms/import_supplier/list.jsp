<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
		<%@ include file="../../../common.jsp"%>
		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${isList.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${isList.total}",
					startRow: "${isList.startRow}",
					endRow: "${isList.endRow}",
					groups: "${isList.pages}" >= 5 ? 5 : "${isList.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							location.href = '${pageContext.request.contextPath}/importSupplier/list.do?page=' + e.curr;
						}
					}
				});
			});

			function check() {
				var count = 0;
				var checklist = document.getElementsByName("chkItem");
				var checkAll = document.getElementById("checkAll");
				for(var i = 0; i < checklist.length; i++) {
					if(checklist[i].checked == false) {
						checkAll.checked = false;
						break;
					}
					for(var j = 0; j < checklist.length; j++) {
						if(checklist[j].checked == true) {
							checkAll.checked = true;
							count++;
						}
					}
				}
			}

			function selectAll() {
				var checklist = document.getElementsByName("chkItem");
				var checkAll = document.getElementById("checkAll");
				if(checkAll.checked) {
					for(var i = 0; i < checklist.length; i++) {
						checklist[i].checked = true;
					}
				} else {
					for(var j = 0; j < checklist.length; j++) {
						checklist[j].checked = false;
					}
				}
			}

			function show(id) {
				window.location.href = "${pageContext.request.contextPath}/importSupplier/show.html?id=" + id;
			}

			function add() {
				window.location.href = "${pageContext.request.contextPath}/importSupplier/register.html";
			}

			function edit() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.location.href = "${pageContext.request.contextPath}/importSupplier/edit.html?id=" + id;
				} else if(id.length > 1) {
					layer.alert("只能选择一个", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				} else {
					layer.alert("请选择需要修改的用户", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}
			}

			function del() {
				var ids = [];
				$('input[name="chkItem"]:checked').each(function() {
					ids.push($(this).val());
				});
				if(ids.length > 0) {
					layer.confirm('您确定要删除吗?', {
						title: '提示',
						offset: ['222px', '360px'],
						shade: 0.01
					}, function(index) {
						layer.close(index);
						window.location.href = "${pageContext.request.contextPath}/importSupplier/delete.html?ids=" + ids;
					});
				} else {
					layer.alert("请选择要删除的用户", {
						offset: ['222px', '390px'],
						shade: 0.01
					});
				}
			}

			function chongzhi() {
				$("#supName").val('');
				$("#supType").val('');
			}
		</script>
	</head>

	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">支撑系统</a>
					</li>
					<li>
						<a href="#">进口供应商登记</a>
					</li>
					<li class="active">
						<a href="#">进口供应商列表</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 我的页面开始-->
		<div class="container">
			<div class="headline-v2">
				<h2>进口供应商列表</h2>
			</div>
			<h2 class="search_detail ">
      <form id="form1" action="${pageContext.request.contextPath}/importSupplier/list.html" method="post" class="mb0" > 
    	<ul class="demand_list">
    	  <li class="fl">
	    	<label class="fl">进口供应商名称：</label><span><input class="span2" id="supName" name="supName" value="${name }" type="text"></span>
	      </li>
    	  <li class="fl">
	    	<label class="fl">供应商类别：</label><span><input class="span2" id="supType" name="supType"  value="${supplierType }" type="text"></span>
	      </li>
	    	<button type="button" onclick="submit()" class="btn">查询</button>
	    	<button type="button" onclick="chongzhi()" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </h2>
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
				<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
			</div>
			<div class="content table_box">
				<table id="tb1" class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
							<th class="info w50">序号</th>
							<th class="info">进口供应商名称</th>
							<th class="info">企业类别</th>
							<th class="info">法定代表人</th>
							<th class="info">状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${isList.list }" var="list" varStatus="vs">
							<tr>
								<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${list.id}" /></td>
								<td class="tc">${(vs.index+1)+(isList.pageNum-1)*(isList.pageSize)}</td>
								<td>
									<a onclick="show('${list.id}')" class="pointer">${list.name }</a>
								</td>
								<td class="tc">${list.supplierType }</td>
								<td class="tc">${list.legalName }</td>
								<td class="tc">
									<c:if test="${list.status==0 }"><span class="label rounded-2x label-dark">未审核</span></c:if>
									<c:if test="${list.status==1 }"><span class="label rounded-2x label-u">审核通过</span></c:if>
									<c:if test="${list.status==2 }"><span class="label rounded-2x label-dark">审核退回</span></c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="pagediv" align="right"></div>
			</div>
		</div>
	</body>

</html>