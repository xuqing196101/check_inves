<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ page import="ses.constants.SupplierConstants" %>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../../../common.jsp"%>
		<%@ include file="/WEB-INF/view/common/map.jsp"%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/functionchar/fusionCharts_evaluation/js/FusionCharts.js"></script>
		<script src="${pageContext.request.contextPath}/public/highmap/js/highcharts.js"></script>
		<script src="${pageContext.request.contextPath}/public/highmap/js/modules/map.js"></script>
		<script src="${pageContext.request.contextPath}/public/highmap/js/modules/data.js"></script>
		<script src="${pageContext.request.contextPath}/public/highmap/js/modules/drilldown.js"></script>
		<script src="${pageContext.request.contextPath}/public/highmap/js/modules/exporting.js"></script>
		<script src="${pageContext.request.contextPath}/public/highmap/js/cn-china-by-peng8.js"></script>
		<script src="${pageContext.request.contextPath}/public/echarts/china.js"></script>
		<link href="${pageContext.request.contextPath}/public/highmap/js/font-awesome.css" media="screen" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/sms/supplier_query/select_supplier_common.js"></script>
		<script type="text/javascript">
			function submit() {
				form1.submit();
			}

			function chongzhi() {
				window.location.href = "${pageContext.request.contextPath}/supplierQuery/highmaps.html";
			}
			$(function() {
				var optionStatus = $("#status").find("option");
				for(var i = 1; i < optionStatus.length; i++) {
					if("${supplier.status}" == $(optionStatus[i]).val()) {
						optionStatus[i].selected = true;
					}
				}
				var optionScore = $("#score").find("option");
				for(var i = 1; i < optionScore.length; i++) {
					if("${supplier.score}" == $(optionScore[i]).val()) {
						optionScore[i].selected = true;
					}
				}
			});
		</script>
		<script type="text/javascript">
			$(function() {
				option = {
					/*    title : {
					       text: '供应商数量统计',
					       x:'center'
					   }, */
					tooltip: {
						trigger: 'item'
					},
					legend: {
						orient: 'vertical',
						x: 'left',
						data: ['']
					},
					dataRange: {
						min: 0,
						max: '${maxCount}',
						x: 'left',
						y: 'bottom',
						text: ['高', '低'], // 文本，默认为数值文本
						calculable: true
					},
					toolbox: {
						show: true,
						orient: 'vertical',
						x: 'right',
						y: 'center',
						feature: {
							mark: {
								show: true
							},
							dataView: {
								show: true,
								readOnly: false
							},
							restore: {
								show: true
							},
							saveAsImage: {
								show: true
							}
						}
					},
					roamController: {
						show: true,
						x: 'right',
						mapTypeControl: {
							'china': true
						}
					},
					series: [{
						name: '中国',
						type: 'map',
						mapType: 'china',
						roam: false,
						itemStyle: {
							normal: {
								label: {
									show: true
								}
							},
							emphasis: {
								label: {
									show: true
								}
							}
						},
						data:eval('${data}'),
					}]
				};

				var myChart = echarts.init(document.getElementById("container"));
				myChart.setOption(option);
				myChart.hideLoading();
				myChart.on('click', function(params) {
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address=" + params.data.id;
				});

			});
		</script>
		<script type="text/javascript">
			var key;

			function showCategory() {
				var zTreeObj;
				var zNodes;
				var setting = {
					async: {
						autoParam: ["id"],
						enable: true,
						url: "${pageContext.request.contextPath}/category/createtree.do",
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
							"N": "s",
						}
					},
					callback: {
						beforeClick: beforeClickCategory,
						onCheck: onCheckCategory,
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId",
						}
					},
					view: {
						fontCss: getFontCss,
					}
				};
				zTreeObj = $.fn.zTree.init($("#treeRole"), setting, zNodes);
				key = $("#key");
				key.bind("focus", focusKey)
					.bind("blur", blurKey)
					.bind("propertychange", searchNode)
					.bind("input", searchNode);

				var cityObj = $("#category");
				var cityOffset = $("#category").offset();
				$("#roleContent").css({
					left: cityOffset.left + "px",
					top: cityOffset.top + cityObj.outerHeight() + "px",
				}).slideDown("fast");
				$("body").bind("mousedown", onBodyDownOrg);
			}

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
				var zTree = $.fn.zTree.getZTreeObj("treeRole");
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
				var zTree = $.fn.zTree.getZTreeObj("treeRole");
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

			function beforeClickCategory(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeRole");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
			}

			function onCheckCategory(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeRole"),
					nodes = zTree.getCheckedNodes(true),
					v = "";
				var rid = "";
				for(var i = 0, l = nodes.length; i < l; i++) {
					v += nodes[i].name + ",";
					rid += nodes[i].id + ",";
				}
				if(v.length > 0) v = v.substring(0, v.length - 1);
				if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
				var cityObj = $("#category");
				cityObj.attr("value", v);
				$("#categoryIds").val(rid);
			}

			function onBodyDownOrg(event) {
				if(!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length > 0)) {
					hideRole();
				}
			}

			function hideRole() {
				$("#roleContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDownOrg);

			}
		</script>
		<script type="text/javascript">
		</script>
		<script type="text/javascript">
			function showSupplierType() {
				var setting = {
					check: {
						enable: true,
						chkboxType: {
							"Y": "",
							"N": "",
						}
					},
					view: {
						dblClickExpand: false,
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId",
						}
					},
					callback: {
						beforeClick: beforeClick,
						onCheck: onCheck,
					}
				};
				$.ajax({
					type: "GET",
					async: false,
					url: "${pageContext.request.contextPath}/supplierQuery/find_supplier_type.do?supplierId=''",
					dataType: "json",
					success: function(zNodes) {
						/* for(var i = 0; i < zNodes.length; i++) {
							 if(zNodes[i].isParent) {
									
							} else {
								//zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
							};
						} */
						
						tree = $.fn.zTree.init($("#treeSupplierType"), setting, zNodes);
						tree.expandAll(true); //全部展开
					}
				});
				var cityObj = $("#supplierType");
				var cityOffset = $("#supplierType").offset();
				$("#supplierTypeContent").css({
					left: cityOffset.left + "px",
					top: cityOffset.top + cityObj.outerHeight() + "px",
				}).slideDown("fast");
				$("body").bind("mousedown", onBodyDownSupplierType);
			}

			function onCheck(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeSupplierType"),
					nodes = zTree.getCheckedNodes(true),
					v = "";
				var rid = "";
				for(var i = 0, l = nodes.length; i < l; i++) {
					v += nodes[i].name + ",";
					rid += nodes[i].id + ",";
				}
				if(v.length > 0) v = v.substring(0, v.length - 1);
				if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
				var cityObj = $("#supplierType");
				cityObj.attr("value", v);
				$("#supplierTypeIds").val(rid);
			}

			function beforeClick(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeSupplierType");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
			}

			function onBodyDownSupplierType(event) {
				if(!(event.target.id == "menuBtn" || event.target.id == "supplierTypeContent" || $(event.target).parents("#supplierTypeContent").length > 0)) {
					hideSupplierType();
				}
			}

			function hideSupplierType() {
				$("#supplierTypeContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDownSupplierType);

			}
		</script>
	</head>
	<!--面包屑导航开始-->
	<body>
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0);">供应商管理</a>
					</li>
					<li class="active">
						<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierQuery/highmaps.html')">全部供应商查询</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div id="roleContent" class="roleContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<input type="text" id="key" value="" class="empty" /><br/>
			<ul id="treeRole" class="ztree" style="margin-top:0;"></ul>
		</div>
		<div id="supplierTypeContent" class="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
		</div>
		
		<div class="container">
			<div class="headline-v2">
				<h2>供应商数量统计</h2>
			</div>
			<div class="search_detail">
			<form id="form1" action="${pageContext.request.contextPath}/supplierQuery/highmaps.html" method="post" class="mb0">
      <input type="hidden" name="page" id="page">
			<div class="m_row_5">
	    <div class="row">
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商名称：</div>
	          <div class="col-xs-8 f0 lh0">
							<input id="supplierName" class="w100p h32 f14 mb0" name="supplierName" value="${supplier.supplierName }" type="text">
	          </div>
	        </div>
	      </div>
	      
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">企业性质：</div>
	          <div class="col-xs-8 f0 lh0">
							<select name="businessNature" id="businessNature" class="w100p h32 f14">
								<option value=''>全部</option>
								<c:forEach items="${businessNature}" var="list">
									<option <c:if test="${supplier.businessNature eq list.id }">selected</c:if> value="${list.id }">${list.name }</option>
								</c:forEach>
							</select>
	          </div>
	        </div>
	      </div>
				
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商类型：</div>
	          <div class="col-xs-8 f0 lh0">
							<input id="supplierType" class="w100p h32 f14 mb0" type="text" name="supplierType" readonly value="${supplierType }" onclick="showSupplierType();" />
							<input type="hidden" name="supplierTypeIds" id="supplierTypeIds" value="${supplierTypeIds }" />">
	          </div>
	        </div>
	      </div>
				
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">供应商状态：</div>
	          <div class="col-xs-8 f0 lh0">
							<select id="status" name="status" class="w100p h32 f14">
								<option selected="selected" value=''>全部</option>
								<c:forEach items="<%=SupplierConstants.STATUSMAP %>" var="item">
									<option value="${item.key}">${item.value}</option>
								</c:forEach>
							</select>
	          </div>
	        </div>
	      </div>
				
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">临时供应商：</div>
	          <div class="col-xs-8 f0 lh0">
							<select name="isProvisional" id="isProvisional" class="w100p h32 f14">
								<option value=''>全部</option>
								<option value='1' <c:if test="${supplier.isProvisional eq '1' }">selected</c:if>>是</option>
								<option value='0' <c:if test="${supplier.isProvisional eq '0' }">selected</c:if>>否</option>
							</select>
	          </div>
	        </div>
	      </div>
				
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">社会信用代码：</div>
	          <div class="col-xs-8 f0 lh0">
							<input class="w100p h32 f14 mb0" id="creditCode" name="creditCode" value="${supplier.creditCode }" type="text">
	          </div>
	        </div>
	      </div>
				
				<div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购机构：</div>
	          <div class="col-xs-8 f0 lh0">
							<select name="orgName" id="orgName" class="w100p h32 f14">
								<option value=''>全部</option>
								<c:forEach items="${allOrg}" var="org">
									<c:if test="${org.isAuditSupplier == 1}">
										<option value="${org.shortName}" <c:if test="${supplier.orgName eq org.shortName}">selected</c:if>>${org.shortName}</option>
									</c:if>
								</c:forEach>
							</select>
	          </div>
	        </div>
	      </div>
	      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
	        <div class="row">
	          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">工程业务地域：</div>
	          <div class="col-xs-8 f0 lh0">
							<select name="businessScope" id="businessScope" class="w100p h32 f14">
								<option value=''>全部</option>
								<c:forEach items="${province}" var="list">
										<option value="${list.id}" <c:if test="${supplier.businessScope eq list.id}">selected</c:if>>${list.name }</option>
								</c:forEach>
							</select>
	          </div>
	        </div>
	      </div>
	    </div>
	    </div>
			
			<div class="tc">
				<button type="button" onclick="submit()" class="btn mb0">查询</button>
				<button type="button" onclick="chongzhi()" class="btn mb0">重置</button>
				<a href="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?sign=1" class="btn mb0 mr0">切换到列表</a>
			</div>
			</form>
		     
     	</div>
		</div>
		<div id="container" style="height: 700px;min-width: 310px;margin: 0 auto;width: 800px;"></div>
	</body>

</html>