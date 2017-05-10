<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<script type="text/javascript">
				$(function(){
					laypage({
						cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
						pages: "${list.pages}", //总页数
						skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
						skip: true, //是否开启跳页
						total: "${list.total}",
						startRow: "${list.startRow}",
						endRow: "${list.endRow}",
						groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
						curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
							var page = location.search.match(/page=(\d+)/);
				      if(page == null) {
					      page = {};
					      page[0] = "${list.pageNum}";
					      page[1] = "${list.pageNum}";
				      }
				      return page ? page[1] : 1;
						}(),
						jump: function(e, first) { //触发分页后的回调
							if(!first) { //一定要加此判断，否则初始时会无限刷新
								$("#page").val(e.curr);
								$("#form").submit();
							}
						}
					});
				});
				
				//新增
				function add(){
					window.location.href = "${pageContext.request.contextPath }/templateDownload/add.html";
				}
				
				//重置
				function resetResult(){
					$("#form").find("input").val("");
				}
				
				//删除
				function deleteById(){
					var count = 0;
					var ids = "";
					var info = document.getElementsByName("info");
					for(var i = 0; i < info.length; i++) {
						if(info[i].checked == true) {
							count++;
						}
					}
					if(count == 0) {
						layer.alert("请选择需要删除的资料", {
							offset: ['30%', '40%']
						});
						$(".layui-layer-shade").remove();
						return;
					}
					for(var i = 0; i < info.length; i++) {
						if(info[i].checked) {
							ids += info[i].value + ',';
						}
					}
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "${pageContext.request.contextPath }/templateDownload/judgeDelete.html?id=" + ids,
						success: function(data) {
							if(data==0){
								layer.alert("已发布的资料不能删除", {
									offset: ['30%', '40%']
								});
								$(".layui-layer-shade").remove();
							}else if(data==1){
								layer.confirm('您确定要删除吗?', {
									title: '提示',
									offset: ['30%', '40%'],
									shade: 0.01
								}, function(index) {
									layer.close(index);
									$.ajax({
										type: "POST",
										url: "${pageContext.request.contextPath }/templateDownload/deleteById.html?id=" + ids,
										success: function(data) {
											layer.msg('删除成功', {
												offset: ['40%', '45%']
											});
											window.setTimeout(function() {
												window.location.href = "${pageContext.request.contextPath }/templateDownload/getList.html";
											}, 1000);
										}
									});
								});
							}
						}
					});
				}
				
				//发布
				function publishData(){
					var count = 0;
					var ids = "";
					var info = document.getElementsByName("info");
					for(var i = 0; i < info.length; i++) {
						if(info[i].checked == true) {
							count++;
						}
					}
					if(count == 0) {
						layer.alert("请选择需要发布的资料", {
							offset: ['30%', '40%']
						});
						$(".layui-layer-shade").remove();
						return;
					}
					for(var i = 0; i < info.length; i++) {
						if(info[i].checked) {
							ids += info[i].value + ',';
						}
					}
					layer.confirm('您确定要发布吗?', {
						title: '提示',
						offset: ['30%', '40%'],
						shade: 0.01
					}, function(index) {
						layer.close(index);
						$.ajax({
							type: "POST",
							url: "${pageContext.request.contextPath }/templateDownload/publishData.html?id=" + ids,
							success: function(data) {
								layer.msg('发布成功', {
									offset: ['40%', '45%']
								});
								window.setTimeout(function() {
									window.location.href = "${pageContext.request.contextPath }/templateDownload/getList.html";
								}, 1000);
							}
						});
					});
				}
				
				//取消发布
				function publishCancel(){
					var count = 0;
					var ids = "";
					var info = document.getElementsByName("info");
					for(var i = 0; i < info.length; i++) {
						if(info[i].checked == true) {
							count++;
						}
					}
					if(count == 0) {
						layer.alert("请选择需要取消发布的资料", {
							offset: ['30%', '40%']
						});
						$(".layui-layer-shade").remove();
						return;
					}
					for(var i = 0; i < info.length; i++) {
						if(info[i].checked) {
							ids += info[i].value + ',';
						}
					}
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "${pageContext.request.contextPath }/templateDownload/publishCancel.html?id=" + ids,
						success: function(data) {
							if(data==0){
								layer.alert("请选择已发布的资料操作", {
									offset: ['30%', '40%']
								});
								$(".layui-layer-shade").remove();
							}else if(data==1){
								layer.confirm('您确定要取消发布吗?', {
									title: '提示',
									offset: ['30%', '40%'],
									shade: 0.01
								}, function(index) {
									layer.close(index);
									$.ajax({
										type: "POST",
										url: "${pageContext.request.contextPath }/templateDownload/cancelPublish.html?id=" + ids,
										success: function(data) {
											layer.msg('取消发布成功', {
												offset: ['40%', '45%']
											});
											window.setTimeout(function() {
												window.location.href = "${pageContext.request.contextPath }/templateDownload/getList.html";
											}, 1000);
										}
									});
								});
							}
						}
					});
				}
				
				//全选方法
				function selectAll() {
					var info = document.getElementsByName("info");
					var selectAll = document.getElementById("selectAll");
					if(selectAll.checked) {
						for(var i = 0; i < info.length; i++) {
							info[i].checked = true;
						}
					} else {
						for(var i = 0; i < info.length; i++) {
							info[i].checked = false;
						}
					}
				}
				
				//检查全选
				function check() {
					var count = 0;
					var info = document.getElementsByName("info");
					var selectAll = document.getElementById("selectAll");
					for(var i = 0; i < info.length; i++) {
						if(info[i].checked == false) {
							selectAll.checked = false;
							break;
						}
					}
					for(var i = 0; i < info.length; i++) {
						if(info[i].checked == true) {
							count++;
						}
					}
					if(count == info.length) {
						selectAll.checked = true;
					}
				}
				
				//查看资料
				function view(obj){
					window.location.href = "${pageContext.request.contextPath}/templateDownload/view.html?id="+obj;
				}
				
				//修改资料
				function edit(){
					var count = 0;
					var info = document.getElementsByName("info");
					var str = "";
					for(var i = 0; i < info.length; i++) {
						if(info[i].checked == true) {
							count++;
						}
					}
					if(count > 1) {
						layer.alert("只能选择一项", {
							offset: ['30%', '40%']
						});
						$(".layui-layer-shade").remove();
						return;
					} else if(count == 0) {
						layer.alert("请先选择一项", {
							offset: ['30%', '40%']
						});
						$(".layui-layer-shade").remove();
						return;
					} else {
						for(var i = 0; i < info.length; i++) {
							if(info[i].checked == true) {
								str = info[i].value;
							}
						}
						$.ajax({
							type: "POST",
							dataType: "json",
							url: "${pageContext.request.contextPath }/templateDownload/judgeEdit.html?id=" + str,
							success: function(data) {
								if(data==0){
									layer.alert("请选择暂存的资料修改", {
										offset: ['30%', '40%']
									});
									$(".layui-layer-shade").remove();
								}else if(data==1){
									window.location.href = "${pageContext.request.contextPath }/templateDownload/edit.html?id="+str;
								}
							}
						});
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
						<a href="#">首页</a>
					</li>
					<li>
						<a href="#">信息服务</a>
					</li>
					<li>
						<a href="#">采购模板管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>采购模板列表</h2>
			</div>
			
				<form action="${pageContext.request.contextPath }/templateDownload/getList.html" method="post" id="form">
					<input type="hidden" name="page" value="" id="page"/>
					<h2 class="search_detail">
						<ul class="demand_list">
				    	<li>
					    	<label class="fl">采购模板名称：</label><span><input type="text" id="name" name="name" value="${name }"/></span>
					    </li>
					    <button type="submit" class="btn fl mt1">查询</button>
					    <button type="button" onclick="resetResult()" class="btn fl mt1">重置</button>
				    </ul>
				    <div class="clear"></div>
			 	</h2>
			</form>	
			
			<!-- 按钮开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="button" onclick="add()">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="edit()">修改</button>
				<button class="btn btn-windows delete" type="button" onclick="deleteById()">删除</button>
				<button class="btn btn-windows apply" type="button" onclick="publishData()">发布</button>
				<button class="btn btn-windows withdraw" type="button" onclick="publishCancel()">取消发布</button>
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w30"><input type="checkbox" id="selectAll" onclick="selectAll()" /></th>
							<th class="w50">序号</th>
							<th class="w300">采购模板名称</th>
							<th class="w180">发布时间</th>
							<th class="w180">创建时间</th>
							<th class="w110">状态</th>
							<th class="w110">发布范围</th>
							<th class="w110">下载</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list.list }" var="data" varStatus="vs">
							<tr class="pointer">
								<td class="tc"><input type="checkbox" name="info" value="${data.id }" onclick="check()" /></td>
								<td class="tc" onclick="view('${data.id }')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
								<c:if test="${fn:length(data.name)>22}">
									<td class="tl pl20" onclick="view('${data.id }')" title="${data.name }">${fn:substring(data.name,0,22)}...</td>
								</c:if>
								<c:if test="${fn:length(data.name)<=22}">
									<td class="tl pl20" onclick="view('${data.id }')">${data.name }</td>
								</c:if>
								<td class="tc" onclick="view('${data.id }')">
									<fmt:formatDate value="${data.publishAt}" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
								</td>
								<td class="tc" onclick="view('${data.id }')">
									<fmt:formatDate value="${data.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
								</td>
								<c:if test="${data.status==1}">
									<td class="tl pl20" onclick="view('${data.id }')">暂存</td>
								</c:if>
								<c:if test="${data.status==2}">
									<td class="tl pl20" onclick="view('${data.id }')">已发布</td>
								</c:if>
								<c:if test="${data.status==3}">
									<td class="tl pl20" onclick="view('${data.id }')">已取消发布</td>
								</c:if>
								<td class="tc" onclick="view('${data.id }')">
									<c:if test="${ data.ipAddressType == 0 }">
										内网
									</c:if>
									<c:if test="${ data.ipAddressType == 1 }">
										内外网
									</c:if>
								</td>
								<td class="release">
									<u:show showId="${data.groupShow }" groups="${data.groupsUploadId }" delete="false" businessId="${data.id }" sysKey="${sysKey}" typeId="${dataTypeId }" zipFileName="${data.name}" fileName="${data.name}" />
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>
  </body>
</html>
