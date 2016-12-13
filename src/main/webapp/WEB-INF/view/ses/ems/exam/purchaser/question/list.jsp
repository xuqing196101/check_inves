<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/view/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<title>采购人员题库</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
		<script type="text/javascript">
			$(function() {
				$("#error").hide();
				$("#topic").val("${topic}");
				var type_options = document.getElementById("questionTypeId").options;
				for(var i = 0; i < type_options.length; i++) {
					if($(type_options[i]).attr("value") == "${questionTypeId}") {
						type_options[i].selected = true;
					}
				}
				laypage({
					cont: $("#pageDiv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${purchaserQuestionList.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${purchaserQuestionList.total}",
					startRow: "${purchaserQuestionList.startRow}",
					endRow: "${purchaserQuestionList.endRow}",
					groups: "${purchaserQuestionList.pages}" >= 5 ? 5 : "${purchaserQuestionList.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							var topic = "${topic}";
							var questionTypeId = "${questionTypeId}";
							location.href = "${pageContext.request.contextPath }/purchaserExam/purchaserList.do?topic=" + topic + "&questionTypeId=" + questionTypeId + "&page=" + e.curr;
						}
					}
				});
			})

			//采购人新增题库
			function add() {
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/addPurQue.html";
			}

			//采购人修改题库
			function edit() {
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
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/editPurQue.html?id=" + str;
				}
			}

			//采购人员删除题库
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
						url: "${pageContext.request.contextPath }/purchaserExam/deleteById.do?ids=" + ids,
						success: function(data) {
							layer.msg('删除成功', {
								offset: ['40%', '45%']
							});
							window.setTimeout(function() {
								window.location.href = "${pageContext.request.contextPath }/purchaserExam/purchaserList.do?topic=" + topic + "&questionTypeId=" + questionTypeId;
							}, 1000);
						}
					});
				});
			}

			//按条件查询采购人员题库
			function query() {
				var topic = $("#topic").val();
				var questionTypeId = $("#questionTypeId").val();
				if((topic == "" || topic == null) && (questionTypeId == "" || questionTypeId == null)) {
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/purchaserList.do";
				} else {
					window.location.href = "${pageContext.request.contextPath }/purchaserExam/purchaserList.do?topic=" + topic + "&questionTypeId=" + questionTypeId;
				}
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

			//endsWith方法
			if(typeof String.prototype.endsWith != 'function') {
				String.prototype.endsWith = function(str) {
					return this.slice(-str.length) == str;
				};
			}

			//导入Excel
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
					url: "${pageContext.request.contextPath }/purchaserExam/importExcel.do",
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
								window.location.href = "${pageContext.request.contextPath }/purchaserExam/purchaserList.html";
							}, 1000);
						} else {
							var array = data.split(";");
							var html = "";
							for(var i = 0; i < array.length - 1; i++) {
								html = html + "<tr class='tc'>";
								html = html + "<td>" + (i + 1) + "</td>";
								if(i == 0) {
									html = html + "<td>" + array[i].split(",")[0].substring(1) + "</td>";
								} else {
									html = html + "<td>" + array[i].split(",")[0] + "</td>";
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

			//下载模板
			function download() {
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/loadPurchaserTemplet.html";
			}

			//重置方法
			function reset() {
				$("#topic").val("");
				var questionTypeId = document.getElementById("questionTypeId").options;
				questionTypeId[0].selected = true;
			}

			//查看采购人员题库
			function view(obj) {
				window.location.href = "${pageContext.request.contextPath }/purchaserExam/view.html?id=" + obj;
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
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="#">首页</a>
					</li>
					<li>
						<a href="#">支撑环境</a>
					</li>
					<li>
						<a href="#">题库管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>采购人员题库列表</h2>
			</div>

			<h2 class="search_detail">
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
						    <option value="3">判断题</option>
				    	</select>
			   		</span>
			     </li>
			    <button type="button" onclick="query()" class="btn">查询</button>
			    <button type="button" onclick="reset()" class="btn">重置</button>
		    </ul>
		    <div class="clear"></div>
	 	</h2>

			<div class="col-md-12 pl20 mt10">
				<input type="button" class="btn btn-windows add" value="新增" onclick="add()" />
				<input type="button" class="btn btn-windows edit" value="修改" onclick="edit()" />
				<input type="button" class="btn btn-windows delete" value="删除" onclick="deleteById()" />
				<button type="button" class="btn btn-windows output" onclick="download()">模板下载</button>
				<input type="button" value="导入" class="btn btn-windows input" onclick="poiExcel()" />
				<input type="file" name="file" id="excelFile" style="display:inline;" />
			</div>

			<div class="content table_box">
				<table class="table table-bordered table-condensed table-hover">
					<thead>
						<tr class="info">
							<th class="w50"><input type="checkbox" id="selectAll" onclick="selectAll()" /></th>
							<th class="w50">序号</th>
							<th class="w60">题型</th>
							<th>题干</th>
							<th>选项</th>
							<th>答案</th>
							<th>创建时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${purchaserQuestionList.list }" var="purchaser" varStatus="vs">
							<tr class="pointer">
								<td class="tc"><input type="checkbox" name="info" value="${purchaser.id }" onclick="check()" /></td>
								<td class="tc w50" onclick="view('${purchaser.id }')">${(vs.index+1)+(purchaserQuestionList.pageNum-1)*(purchaserQuestionList.pageSize)}</td>
								<td class="w60 tc" onclick="view('${purchaser.id }')">${purchaser.examQuestionType.name }</td>
								<c:if test="${fn:length(purchaser.topic)>28}">
									<td onclick="view('${purchaser.id }')" onmouseover="titleMouseOver('${purchaser.topic}',this)" onmouseout="titleMouseOut()">${fn:substring(purchaser.topic,0,28)}...</td>
								</c:if>
								<c:if test="${fn:length(purchaser.topic)<=28}">
									<td onclick="view('${purchaser.id }')">${purchaser.topic }</td>
								</c:if>
								<c:if test="${fn:length(purchaser.items)>28}">
									<td onclick="view('${purchaser.id }')" onmouseover="titleMouseOver('${purchaser.items}',this)" onmouseout="titleMouseOut()">${fn:substring(purchaser.items,0,28)}...</td>
								</c:if>
								<c:if test="${fn:length(purchaser.items)<=28}">
									<td onclick="view('${purchaser.id }')">${purchaser.items }</td>
								</c:if>
								<td class="tc" onclick="view('${purchaser.id }')">${purchaser.answer }</td>
								<td class="tc" onclick="view('${purchaser.id }')">
									<fmt:formatDate value="${purchaser.createdAt}" pattern="yyyy/MM/dd" />
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