<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>供应商注销列表</title>
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
					groups: "${result.pages}" >= 3 ? 3 : "${result.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						return "${result.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#page").val(e.curr);
							$("#form1").submit();
						}
					}
				});
			});
		</script>
		<script type="text/javascript">
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

			//撤销
			function cancellation() {
				var ids = $(":radio:checked").val();
				if(ids != null) {
					layer.confirm('您确定要注销吗?', {
						title: '提示！',
						offset: ['200px']
					}, function(index) {
						layer.close(index);
						$.ajax({
							url: "${pageContext.request.contextPath}/suppliertDelete/cancellation.html",
							data: "supplierId=" + ids,
							type: "post",
							success: function() {
								layer.msg("注销成功!", {
									offset: '100px'
								});
								window.setTimeout(function() {
									$("#form1").submit();
								}, 1000);
							},
							error: function() {
								layer.msg("注销失败！", {
									offset: '100px'
								});
							}
						});
					});
				} else {
					layer.msg("请选择供应商！", {
						offset: '100px'
					});
				}

			}
			
			function resetForm() {
				$("input[name='supplierName']").val("");
				$("input[name='loginName']").val("");
				$("input[name='mobile']").val("");
				$("#form1").submit();
			}
			
			
			
			/**重置密码*/
	 		function resetPwd(){
 	   		var id = $(":radio:checked").val();
        if(id !=null){
     	  	$.ajax({
	          url: "${pageContext.request.contextPath}/user/setPassword.do",
	          data: {"typeId":id},
	          type: "post",
	          dataType: "json",
	          success: function(data){
	      	    if("sccuess" == data){
	              layer.alert("重置成功！默认密码：123456",{offset: '100px'});
	                 }else{
	               	   layer.msg("重置失败！",{offset: '100px'});
	                 }
	               }
            	});
        	}else{
            layer.msg("请选择供应商！",{offset: '100px'});
        	}
	 		}
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#"> 首页</a>
					</li>
					<li>
						<a href="#">支撑环境</a>
					</li>
					<li>
						<a href="#">供应商管理</a>
					</li>
					<li class="active">
						<a href="#">供应商注销</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container">
			<!-- 搜索 -->
			<h2 class="search_detail">
	      <form action="${pageContext.request.contextPath}/suppliertDelete/logoutList.html"  method="post" id="form1" class="mb0"> 
	      <input type="hidden" name="page" id="page">
	      <ul class="demand_list">
		      <li class="fl">
			      <label class="fl">供应商名称：</label> 
			      <input class="" name="supplierName" type="text" value="${supplier.supplierName }">
		      </li>
		      <li class="fl">
			      <label class="fl">用户名：</label> 
			      <input class="" name="loginName" type="text" value="${supplier.loginName }">
		      </li>
		      <li class="fl">
			      <label class="fl">手机号：</label> 
			      <input class="" name="mobile" type="text" value="${supplier.mobile }">
		      </li>
	      </ul>
	        
	        <input type="submit" class="btn fl" value="查询" />
				  <button onclick="resetForm();" class="btn fl" type="button">重置</button>
				  <div class="clear"></div>
	      </form>
    	</h2>
			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows check" type="button" onclick="cancellation();">注销</button>
				<button class="btn btn-windows edit" type="button" onclick="resetPwd()">重置密码</button>
			</div>
			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover hand">
					<thead>
						<tr>
							<th class="info w50">选择</th>
							<th class="info w50">序号</th>
							<th class="info">供应商名称</th>
							<th class="info">用户名</th>
							<th class="info">联系人</th>
							<th class="info">手机号</th>
							<th class="info w100">状态</th>
						</tr>
					</thead>
					<c:forEach items="${result.list }" var="list" varStatus="page">
						<tr>
							<td class="tc w30"><input name="id" type="radio" value="${list.id}"></td>
							<td class="tc w50">${(page.count)+(result.pageNum-1)*(result.pageSize)}</td>
							<td class="tl pl20">${list.supplierName }</td>
							<td class="">${list.loginName }</td>
								<td class="">${list.contactName }</td>
							<td class="tc">${list.mobile }</td>
							<td class="tc w100" id="${list.id}">
								<c:if test="${list.status == -1}"><span class="label rounded-2x label-dark">暂存</span></c:if>
								<c:if test="${list.status == 0}"><span class="label rounded-2x label-dark">待审核</span></c:if>
								<c:if test="${list.status == 2}"><span class="label rounded-2x label-dark">审核退回</span></c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div id="pagediv" align="right"></div>
			</div>
		</div>
	</body>

</html>