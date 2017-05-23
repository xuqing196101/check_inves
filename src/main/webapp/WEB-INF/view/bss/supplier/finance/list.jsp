<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
	 <%@ include file="/WEB-INF/view/front.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title></title>
		<!-- Meta -->
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">

		<script type="text/javascript">
			$(function() {
				laypage({
					cont: $("#pagediv"), 
					pages: "${listSf.pages}", 
					skin: '#2c9fA6', 
					skip: true, 
					total: "${listSf.total}",
					startRow: "${listSf.startRow}",
					endRow: "${listSf.endRow}",
					groups: "${listSf.pages}" >= 5 ? 5 : "${listSf.pages}", 
					curr: function() {
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { 
						if(!first) {
							location.href = '${pageContext.request.contextPath}/supplier_finance/list.do?page=' + e.curr;
						}
					}
				});
			});

			/** 全选全不选 */
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

			/** 单选 */
			function check() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
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

			function show(id) {
				window.location.href = "${pageContext.request.contextPath}/supplier_finance/show.html?id=" + id;
			}
			function add() {
				window.location.href = "${pageContext.request.contextPath}/supplier_finance/add.html";
			}
			
			function edit() {
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.location.href = "${pageContext.request.contextPath}/supplier_finance/edit.html?id=" + id;
				} else if(id.length > 1) {
					layer.alert("只能选择一个", {
						offset: ['122px', '390px'],
						shade: 0.01
					});
				} else {
					layer.alert("请选择需要修改的用户", {
						offset: ['122px', '390px'],
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
						offset: ['122px', '360px'],
						shade: 0.01
					}, function(index) {
						layer.close(index);
						window.location.href = "${pageContext.request.contextPath}/supplier_finance/delete.html?id=" + ids;
					});
				} else {
					layer.alert("请选择要删除的用户", {
						offset: ['122px', '390px'],
						shade: 0.01
					});
				}
			}
			
			function tijiao(obj) {
			var x,y;  
		    oRect = obj.getBoundingClientRect();  
		    x=oRect.left + 100;  
		    y=oRect.top + 100;  
				var id = [];
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					$.ajax({
						url:"${pageContext.request.contextPath}/supplier_finance/tijiao.do?id="+id,
						type:"POST",
						dataType:"text",
						success:function(data){
						    if (data == 'true') {
								layer.msg("提交成功", {offset: [y, x],shade: 0.01});
								$('input[name="chkItem"]:checked').parent().parent().children("td").eq(9).find("span").text("未审核");
						    } else {
						    	layer.msg("只能提交暂存和退回修改的数据", {offset: [y, x],shade: 0.01});
						    }
						}
					});
				} else if(id.length > 1) {
					layer.alert("只能选择一个", {
						offset: ['122px', '390px'],
						shade: 0.01
					});
				} else {
					layer.alert("请选择需要修改的用户", {
						offset: ['122px', '390px'],
						shade: 0.01
					});
				}
			}

			function resetQuery() {
				$("#name").val("");
			}

			function query() {
				form1.submit();
			}
		</script>
	</head>

	<body>

		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">个人信息</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);">提报每年财务审计</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<!-- 我的页面开始-->
		<div class="container">
			<div class="headline-v2">
				<h2>财务审计列表</h2>
			</div>
			<h2 class="search_detail">
       <form id="form1" action="${pageContext.request.contextPath}/supplier_finance/list.html" method="post" class="mb0">
    	<ul class="demand_list">
    	  <li class="fl">
	    	<label class="fl">会计事务所名称：</label><span><input type="text" id="name" name="name" value="${finance.name }" class=""/></span>
	      </li>
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="button" class="btn" onclick="resetQuery()">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
       </form>
     </h2>
			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="submit" onclick="add()">提报</button>
				<button class="btn btn-windows edit" type="submit" onclick="edit()">修改</button>
				<button class="btn btn-windows delete" type="submit" onclick="del();">删除</button>
				<button class="btn btn-windows git" type="submit" onclick="tijiao(this);">提交</button>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="w30 info"><input id="checkAll" type="checkbox" onclick="selectAll()"></th>
							<th class="w50 info">年份</th>
							<th class="info">会计事务所名称</th>
							<th class="info">事务所联系电话</th>
							<th class="info">审计人姓名</th>
							<th class="info">资产总额（万元）</th>
							<th class="info">负债总额（万元）</th>
							<th class="info">净资产总额（万元）</th>
							<th class="info">营业收入（万元）</th>
							<th class="info">状态</th>
						</tr>
					</thead>
					<c:forEach items="${listSf.list}" var="finance" varStatus="vs">
						<tr>
							<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${finance.id}" /></td>
							<td class="tc">${finance.year}</td>
							<td class="tl"><a onclick="show('${finance.id}')" class="hand">${finance.name}</a></td>
							<td class="tc">${finance.telephone}</td>
							<td class="tc">${finance.auditors}</td>
							<td class="tr">${finance.totalAssets}</td>
							<td class="tr">${finance.totalLiabilities}</td>
							<td class="tr">${finance.totalNetAssets}</td>
							<td class="tr">${finance.taking}</td>
							<td class="tc">
								<c:if test="${finance.status==1 }"><span class="label rounded-2x label-u">暂存</span></c:if>
								<c:if test="${finance.status==2 }"><span class="label rounded-2x label-u">未审核</span></c:if>
								<c:if test="${finance.status==3 }"><span class="label rounded-2x label-u">审核通过</span></c:if>
								<c:if test="${finance.status==5 }"><span class="label rounded-2x label-dark">审核不通过</span></c:if>
								<c:if test="${finance.status==4 }"><span class="label rounded-2x label-dark">退回修改</span></c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div id="pagediv" align="right"></div>
			</div>
		</div>
	</body>

</html>