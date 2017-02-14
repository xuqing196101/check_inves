<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
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
		<script type="text/javascript">
			$(function() {
				option = {
					/*   title : {
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
						data: ${data}
					}]
				};

				var myChart = echarts.init(document.getElementById("container"));
				myChart.setOption(option);
				myChart.hideLoading();
				myChart.on('click', function(params) {
					var address = encodeURI(params.name);
					address = encodeURI(address);
					window.location.href = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address=" + address + "&judge=5";
				});

			});
		</script>
		<script type="text/javascript">
			function submit() {
				form1.submit();
			}

			function chongzhi() {
				$("#supplierName").val('');
				$("#startDate").val('');
				$("#endDate").val('');
				$("#contactName").val('');
				$("#category").val('');
				$("#supplierType").val('');
				$("#categoryIds").val('');
				$("#supplierTypeIds").val('');
				$("option")[0].selected = true;
				window.location.href = "${pageContext.request.contextPath}/supplierQuery/highmaps.html?judge=5";
			}
			$(function() {
				var optionNodes = $("option");
				for(var i = 1; i < optionNodes.length; i++) {
					if("${supplier.score}" == $(optionNodes[i]).val()) {
						optionNodes[i].selected = true;
					}
				}
			});
		</script>
		<script type="text/javascript">
			function showSupplierType() {
				var setting = {
					check: {
						enable: true,
						chkboxType: {
							"Y": "",
							"N": ""
						}
					},
					view: {
						dblClickExpand: false
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId"
						}
					},
					callback: {
						beforeClick: beforeClick,
						onCheck: onCheck
					}
				};
				$.ajax({
					type: "GET",
					async: false,
					url: "${pageContext.request.contextPath}/supplier_type/find_supplier_type.do?supplierId=''",
					dataType: "json",
					success: function(zNodes) {
						for(var i = 0; i < zNodes.length; i++) {
							if(zNodes[i].isParent) {

							} else {
								//zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
							}
						}
						tree = $.fn.zTree.init($("#treeSupplierType"), setting, zNodes);
						tree.expandAll(true); //全部展开
					}
				});
				var cityObj = $("#supplierType");
				var cityOffset = $("#supplierType").offset();
				$("#supplierTypeContent").css({
					left: cityOffset.left + "px",
					top: cityOffset.top + cityObj.outerHeight() + "px"
				}).slideDown("fast");
				$("body").bind("mousedown", onBodyDownSupplierType);
			}

			function onBodyDownSupplierType(event) {
				if(!(event.target.id == "menuBtn" || $(event.target).parents("#supplierTypeContent").length > 0)) {
					hideSupplierType();
				}
			}

			function hideSupplierType() {
				$("#supplierTypeContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDownSupplierType);

			}

			function beforeClick(treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("treeSupplierType");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
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
							"N": "s"
						}
					},
					callback: {
						beforeClick: beforeClickCategory,
						onCheck: onCheckCategory
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
					top: cityOffset.top + cityObj.outerHeight() + "px"
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
	</head>
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
					<a href="#">入库供应商查询</a>
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

	<body>
		<div class="container">
			<div class="headline-v2">
				<h2>供应商数量统计</h2>
			</div>
			<h2 class="search_detail">
  			<form id="form1" action="${pageContext.request.contextPath}/supplierQuery/highmaps.html?judge=5" method="post" class="mb0">
		       <input type="hidden" name="page" id="page">
		       <ul class="demand_list">
                  <li>
                    <label class="fl">供应商名称：</label><span><input id="supplierName"  class="w220" name="supplierName" value="${supplier.supplierName }" type="text"></span>
                  </li>
                  <li>
                    <label class="fl">联系人：</label><span><input id="contactName"  class="w220" name="contactName" value="${supplier.contactName }" type="text"></span>
                  </li> 
                  <li>
                    <label class="fl">供应商类型：</label><span><input id="supplierType" class="mt5" type="text" readonly name="supplierType" value="${supplierType }" onclick="showSupplierType();" />
                             <input   type="hidden" name="supplierTypeIds"  id="supplierTypeIds" value="${supplierTypeIds }" /></span>
                  </li>
                  <li>
                    <label class="fl">品目：</label><span> <input id="category" class="span2 mt5" type="text" readonly name="categoryNames" value="${categoryNames }" onclick="showCategory();" />
                           <input   type="hidden" name="categoryIds"  id="categoryIds" value="${categoryIds }"   /></span>
                  </li>
                  <li>
                    <label class="fl">注册时间：</label><span><input id="startDate" name="startDate" class="Wdate w150" type="text"  value='<fmt:formatDate value="${supplier.startDate }" pattern="YYYY-MM-dd"/>'
                        onFocus="var endDate=$dp.$('endDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
                        <span class="f14 fl">至</span>
                        <input id="endDate" cl name="endDate" value='<fmt:formatDate value="${supplier.endDate }" pattern="YYYY-MM-dd"/>'  class="Wdate w150" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"/>
                        </span>
                  </li>
                  <li>
		            <label class="fl">供应商级别：</label>
		            <span>
		              <select name="score">
                                    <option  selected="selected" class="" value=''>-请选择-</option>
                                    <option  value="1">一级</option>
                                    <option  value="2">二级</option>
                                    <option  value="3">三级</option>
                                    <option  value="4">四级</option>
                                    <option  value="5">五级</option>
                       </select>
		            </span>
		          </li>
                </ul>
                 <div class="col-md-12 clear tc">
                    <button type="button" onclick="submit()" class="btn">查询</button>
                    <button type="button" onclick="chongzhi()" class="btn">重置</button> 
                </div>
                <div class="clear"></div>
		     </form>
		  </h2>
		</div>
		<div id="container" style="height: 700px;min-width: 310px;margin: 0 auto;width: 800px;"></div>
	</body>

</html>