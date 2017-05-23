<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>技术类专家题库</title>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
		<script type="text/javascript">
			$(function() {
				$("#topic").val("${topic}");
				var type_options = document.getElementById("questionTypeId").options;
				for(var i = 0; i < type_options.length; i++) {
					if($(type_options[i]).attr("value") == "${questionTypeId}") {
						type_options[i].selected = true;
					}
				}
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${technicalList.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${technicalList.total}",
					startRow: "${technicalList.startRow}",
					endRow: "${technicalList.endRow}",
					groups: "${technicalList.pages}" >= 5 ? 5 : "${technicalList.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							var topic = "${topic}";
							var questionTypeId = "${questionTypeId}";
							location.href = "${pageContext.request.contextPath }/expertExam/searchTecExpPool.do?topic=" + topic + "&questionTypeId=" + questionTypeId + "&page=" + e.curr;
						}
					}
				});
			})

			//删除题库中的题目
			function deleteById() {
				var topic = $("#topic").val();
				var questionTypeId = $("#questionTypeId").val();
				var count = 0;
				var ids = "";
				var info = document.getElementsByName("info");
				for(var i = 0; i < info.length; i++) {
					if(info[i].checked == true) {
						count++;
					}
				}
				if(count == 0) {
					layer.alert("请选择删除内容", {
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
				layer.confirm('您确定要删除吗?', {
					title: '提示',
					offset: ['30%', '40%'],
					shade: 0.01
				}, function(index) {
					layer.close(index);
					$.ajax({
						type: "POST",
						dataType: "json",
						url: "${pageContext.request.contextPath }/expertExam/deleteById.do?ids=" + ids,
						success: function(data) {
							layer.msg('删除成功', {
								offset: ['40%', '45%']
							});
							window.setTimeout(function() {
								window.location.href = "${pageContext.request.contextPath }/expertExam/searchTecExpPool.do?topic=" + topic + "&questionTypeId=" + questionTypeId;
							}, 1000);
						}
					});
				});
			}

			//增加题库
			function addTechnical() {
				window.location.href = "${pageContext.request.contextPath }/expertExam/addTechnical.html";
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

			//修改题库
			function editTechnical() {
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
					window.location.href = "${pageContext.request.contextPath }/expertExam/editTechnical.html?id=" + str;
				}
			}

			//查看功能
			function view(obj) {
				window.location.href = "${pageContext.request.contextPath }/expertExam/viewTec.html?id=" + obj;
			}

			//下载模板
			function download() {
				window.location.href = "${pageContext.request.contextPath }/expertExam/loadExpertTemplet.html";
			}

			//endsWith方法
			if(typeof String.prototype.endsWith != 'function') {
				String.prototype.endsWith = function(str) {
					return this.slice(-str.length) == str;
				};
			}

			//导入技术类题目
			function poiExcel() {
				var file = $("#excelFile").val();
				if(file == null || file == "") {
					layer.alert("请选择文件", {
						offset: ['30%', '40%']
					});
					$(".layui-layer-shade").remove();
					return;
				}
				if(!file.endsWith(".xls") && !file.endsWith(".xlsx")) {
					layer.alert("请选择以.xls结尾或者以.xlsx结尾的文件", {
						offset: ['30%', '40%']
					});
					$(".layui-layer-shade").remove();
					return;
				}
				$.ajaxFileUpload({
					url: "${pageContext.request.contextPath }/expertExam/importTec.html",
					secureuri: false,
					fileElementId: "excelFile",
					type: "POST",
					dataType: "text",
					success: function(data) {
						if(data.length <= 5) {
							layer.msg('导入成功', {
								offset: ['40%', '45%']
							});
							window.setTimeout(function() {
								window.location.href = "${ pageContext.request.contextPath }/expertExam/searchTecExpPool.html";
							}, 1000);
						} else {
							var array = data.split(";");
							var html = "";
							for(var i = 0; i < array.length - 1; i++) {
								html = html + "<tr>";
								html = html + "<td class='tc'>" + (i + 1) + "</td>";
								if(i == 0) {
									html = html + "<td class='tc'>" + array[i].split(",")[0].substring(1) + "</td>";
								} else {
									html = html + "<td class='tc'>" + array[i].split(",")[0] + "</td>";
								}
								html = html + "<td>" + array[i].split(",")[1] + "</td>";
								html = html + "</tr>";
							}
							$("#errorResult").html(html);
							$("#errorNews").html("Excel表中以下题目的题干已存在");
							layer.open({
								type: 1,
								title: '错误信息',
								skin: 'layui-layer-rim',
								shadeClose: true,
								area: ['580px', '460px'],
								content: $("#error")
							});
							$(".layui-layer-shade").remove();
						}
					}
				});
			}

			//重置方法
			function reset() {
				$("#topic").val("");
				var questionTypeId = document.getElementById("questionTypeId").options;
				questionTypeId[0].selected = true;
			}

			//按条件查询采购人题库
			function query() {
				var topic = $("#topic").val();
				var questionTypeId = $("#questionTypeId").val();
				if((topic == "" || topic == null) && (questionTypeId == "" || questionTypeId == null)) {
					window.location.href = "${pageContext.request.contextPath }/expertExam/searchTecExpPool.do";
				} else {
					window.location.href = "${pageContext.request.contextPath }/expertExam/searchTecExpPool.do?topic=" + topic + "&questionTypeId=" + questionTypeId;
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
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑环境</a>
					</li>
					<li>
						<a href="javascript:void(0);">专家管理</a>
					</li>
					<li>
						<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/expertExam/searchTecExpPool.html');">题库管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>技术类专家题库列表</h2>
			</div>

			<div class="search_detail">
				<ul class="demand_list">
					<li>
						<label class="fl">题干：</label><span><input type="text" id="topic" class=""/></span>
					</li>
					<li>
						<label class="fl">题型：</label>
						<span>
				    	<select id="questionTypeId" class="w178">
						    <option value="">请选择</option>
						    <option value="1">单选题</option>
						    <option value="2">多选题</option>
				    	</select>
			   		</span>
					</li>
					<button type="button" onclick="query()" class="btn fl mt1">查询</button>
					<button type="button" onclick="reset()" class="btn fl mt1">重置</button>
				</ul>
				<div class="clear"></div>
			</div>

			<!-- 表格开始-->
			<div class="col-md-12 pl20 mt10">
				<button class="btn btn-windows add" type="button" onclick="addTechnical()">新增</button>
				<button class="btn btn-windows edit" type="button" onclick="editTechnical()">修改</button>
				<button class="btn btn-windows delete" type="button" onclick="deleteById()">删除</button>
				<button class="btn btn-windows output" type="button" onclick="download()">模板下载</button>
				<button class="btn btn-windows input" type="button" onclick="poiExcel()">导入</button>
				<input type="file" name="file" id="excelFile" style="display:inline" />
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50"><input type="checkbox" id="selectAll" onclick="selectAll()" /></th>
							<th class="w50">序号</th>
							<th>题型</th>
							<th>题干</th>
							<th>选项</th>
							<th>答案</th>
							<th>创建时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${technicalList.list }" var="t" varStatus="vs">
							<tr class="pointer">
								<td class="tc"><input type="checkbox" name="info" value="${t.id }" onclick="check()" /></td>
								<td class="tc" onclick="view('${t.id }')">${(vs.index+1)+(technicalList.pageNum-1)*(technicalList.pageSize)}</td>
								<td class="tc" onclick="view('${t.id }')">${t.examQuestionType.name }</td>
								<c:if test="${fn:length(t.topic)>25}">
									<td class="tl pl20" onclick="view('${t.id }')" onmouseover="titleMouseOver('${t.topic}',this)" onmouseout="titleMouseOut()">${fn:substring(t.topic,0,25)}...</td>
								</c:if>
								<c:if test="${fn:length(t.topic)<=25}">
									<td class="tl pl20" onclick="view('${t.id }')">${t.topic }</td>
								</c:if>
								<c:if test="${fn:length(t.items)>25}">
									<td class="tl pl20" onclick="view('${t.id }')" onmouseover="titleMouseOver('${t.items}',this)" onmouseout="titleMouseOut()">${fn:substring(t.items,0,25)}...</td>
								</c:if>
								<c:if test="${fn:length(t.items)<=25}">
									<td class="tl pl20" onclick="view('${t.id }')">${t.items }</td>
								</c:if>
								<td class="tc" onclick="view('${t.id }')">${t.answer}</td>
								<td class="tc" onclick="view('${t.id }')">
									<fmt:formatDate value="${t.createdAt}" pattern="yyyy/MM/dd" />
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageDiv" align="right"></div>
		</div>

		<div class="dnone layui-layer-wrap col-md-12" id="error">
			<div id="errorNews" class="col-md-12 tc red mb5 f18"></div>
			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50">序号</th>
						<th class="w60">题型</th>
						<th>题干</th>
					</tr>
				</thead>
				<tbody id="errorResult">
				</tbody>
			</table>
		</div>
	</body>

</html>