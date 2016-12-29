<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="../../../common.jsp"%>
		<script type="text/javascript">
			/* 		var setting = {
						check : {
							enable : true,
							chkboxType : {
								"Y" : "s",
								"N" : "s"
							}
						},
						data : {
							simpleData : {
								enable : true,
								idKey : "id",
								pIdKey : "parentId"
							}
						},
						view: {
							fontCss: getFontCss
						}
					};
					var zNodes =${json};
					function focusKey(e) {
						if (key.hasClass("empty")) {
							key.removeClass("empty");
						}
					}
					function blurKey(e) {
						if (key.get(0).value === "") {
							key.addClass("empty");
						}
					}
					var lastValue = "", nodeList = [], fontCss = {};
					function clickRadio(e) {
						lastValue = "";
						searchNode(e);
					}
					function searchNode(e) {
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						var value = $.trim(key.get(0).value);
						var keyType ="name";
						if (key.hasClass("empty")) {
							value = "";
						}
						if (lastValue === value) return;
						lastValue = value;
						if (value === "") return;
						updateNodes(false);
						nodeList = zTree.getNodesByParamFuzzy(keyType, value);
						updateNodes(true);
					}
					function updateNodes(highlight) {
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						for( var i=0, l=nodeList.length; i<l; i++) {
							nodeList[i].highlight = highlight;
							zTree.updateNode(nodeList[i]);
						}
					}
					function getFontCss(treeId, treeNode) {
						return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
					}
					function filter(node) {
						return !node.isParent && node.isFirstNode;
					}
					var key;
					$(document).ready(function(){
						$.fn.zTree.init($("#treeDemo"), setting, zNodes);
						key = $("#key");
						key.bind("focus", focusKey)
						.bind("blur", blurKey)
						.bind("propertychange", searchNode)
						.bind("input", searchNode);
					}); */
		</script>
		<script type="text/javascript">
			var key;
			$(function() {
				var zTreeObj;
				var zNodes;
				loadZtree();

				function loadZtree() {
					var setting = {
						async: {
							autoParam: ["id"],
							enable: true,
							url: "${pageContext.request.contextPath}/category/query_category.do",
							otherParam: {
								categoryIds: "${categoryIds}",
							},
							dataType: "json",
							type: "post",
						},
						check: {
							enable: true,
							chkboxType: {
								"Y": "s",
								"N": "s"
							}
						},
						data: {
							simpleData: {
								enable: true,
								idKey: "id",
								pIdKey: "parentId"
							}
						},
						view: {
							fontCss: getFontCss
						}
					};
					zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
					key = $("#key");
					key.bind("focus", focusKey)
						.bind("blur", blurKey)
						.bind("propertychange", searchNode)
						.bind("input", searchNode);
				}
			});

			function focusKey(e) {
				if(key.hasClass("empty")) {
					key.removeClass("empty");
				}
			}

			function blurKey(e) {
				if(key.get(0).value === "") {
					key.addClass("empty");
				}
			}
			var lastValue = "",
				nodeList = [],
				fontCss = {};

			function clickRadio(e) {
				lastValue = "";
				searchNode(e);
			}

			function searchNode(e) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var value = $.trim(key.get(0).value);
				var keyType = "name";
				if(key.hasClass("empty")) {
					value = "";
				}
				if(lastValue === value) return;
				lastValue = value;
				if(value === "") return;
				updateNodes(false);
				nodeList = zTree.getNodesByParamFuzzy(keyType, value);
				updateNodes(true);
			}

			function updateNodes(highlight) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				for(var i = 0, l = nodeList.length; i < l; i++) {
					nodeList[i].highlight = highlight;
					zTree.updateNode(nodeList[i]);
				}
			}

			function getFontCss(treeId, treeNode) {
				return(!!treeNode.highlight) ? {
					color: "#A60000",
					"font-weight": "bold"
				} : {
					color: "#333",
					"font-weight": "normal"
				};
			}

			function filter(node) {
				return !node.isParent && node.isFirstNode;
			}
			$(function() {
				laypage({
					cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages: "${listSupplier.pages}", //总页数
					skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip: true, //是否开启跳页
					total: "${listSupplier.total}",
					startRow: "${listSupplier.startRow}",
					endRow: "${listSupplier.endRow}",
					groups: "${listSupplier.pages}" >= 5 ? 5 : "${listSupplier.pages}", //连续显示分页数
					curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1;
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							location.href = '${pageContext.request.contextPath}/supplierQuery/selectByCategory.do?page=' + e.curr;
						}
					}
				});
			});

			function tijiao() {
				var Obj = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = Obj.getCheckedNodes(true);
				var ids = new Array();
				for(var i = 0; i < nodes.length; i++) {
					if(!nodes[i].isParent) {
						//获取选中节点的值  
						ids.push(nodes[i].id);
					}
				}
				$("#categoryIds").val(ids);
				form1.submit();
			}

			function resetQuery() {
				var Obj = $.fn.zTree.getZTreeObj("treeDemo");
				Obj.checkAllNodes(false);
				$("#supplierName").val("");
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
						<a href="#">支撑系统</a>
					</li>
					<li>
						<a href="#">供应商管理</a>
					</li>
					<li class="active">
						<a href="#">按照品目查询供应商</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>

		<div class="container content height-350">
			<div class="row">
				<!-- Begin Content -->
				<div class="col-md-12" style="min-height:400px;">
					<div class="col-md-3 md-margin-bottom-40" id="show_tree_div">
						<div class="tag-box tag-box-v3">
							<input type="text" id="key"  value="" class="empty w220" /><br/>
							<ul id="treeDemo" class="ztree" />
						</div>
					</div>
					<div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
						<form id="form1" action="${pageContext.request.contextPath}/supplierQuery/selectByCategory.html" method="post">
							<input type="hidden" name="page" id="page">
							<input type="hidden" id="categoryIds" name="categoryIds" />
							<ul class="demand_list">
								<li>
									<label class="fl">供应商名称：</label><span><input id="supplierName" name="supplierName" value="${supplier.supplierName }" type="text"></span>
								</li>
							</ul>
							<input class="btn fl mt1" onclick="tijiao()" type="button" value="查询">
							<button type="button" class="btn fl mt1" onclick="resetQuery()">重置</button>
						</form>
						<div class="content table_box pl0">
							<table id="tb1" class="table table-bordered table-condensed table-hover table-striped">
								<thead>
									<tr>
										<th class="info w50">序号</th>
										<th class="info">供应商名称</th>
										<th class="info">联系人</th>
										<th class="info">供应商类别</th>
										<th class="info">供应商状态</th>
										<th class="info">电话</th>
										<th class="info">级别</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listSupplier.list }" var="list" varStatus="vs">
										<tr>
											<td class="tc">${(vs.index+1)+(listSupplier.pageNum-1)*(listSupplier.pageSize)}</td>
											<td class="pl20">
												<a href="${pageContext.request.contextPath}/supplierQuery/essential.html?isRuku=2&supplierId=${list.id}">${list.supplierName }</a>
											</td>
											<td class="tc">${list.contactName}</td>
											<td class="tl">${list.supplierType }</td>
											<td class="tc">
												<c:if test="${list.status==-1 }">
													暂存、未提交
												</c:if>
												<c:if test="${list.status==0 }">
													待初审
												</c:if>
												<c:if test="${list.status==1 }">
													待复审
												</c:if>
												<c:if test="${list.status==2 }">
													初审不通过
												</c:if>
												<c:if test="${list.status==3 }">
													复审通过
												</c:if>
												<c:if test="${list.status==4 }">
													复审不通过
												</c:if>
											</td>
											<td class="tc">${list.contactTelephone}</td>
											<td class="tc">${list.level}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div id="pagediv" align="right"></div>
					</div>
					<!--  <form  id="form" action="" name="fm" method="post"  enctype="multipart/form-data">
	    <input type="hidden"  onclick="check()" value="submit"/>
	    <input type="hidden"  onclick="mysubmit()" value="submit"/>
    <table id="result"  class="table table-bordered table-condensedb mt15" ></table>
    </form> -->
				</div>
			</div>
		</div>
	</body>

</html>