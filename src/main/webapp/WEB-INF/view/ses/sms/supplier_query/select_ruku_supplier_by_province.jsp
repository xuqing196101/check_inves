<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ page import="ses.constants.SupplierConstants" %>

<!DOCTYPE HTML >
<html>

	<head>
        <%@ include file="../../../common.jsp"%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/sms/supplier_query/select_ruku_supplier_by_province.js"></script>
        <script type="text/javascript">
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
						/* var page = location.search.match(/page=(\d+)/);
						return page ? page[1] : 1; */
						return "${listSupplier.pageNum}";
					}(),
					jump: function(e, first) { //触发分页后的回调
						if(!first) { //一定要加此判断，否则初始时会无限刷新
							$("#page").val(e.curr);
							$("#form1").submit();
							/* location.href = '${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.do?page=' + e.curr + "&judge=5" + "&address="  + encodeURI(encodeURI('${address}')); */
						}
					}
				});
			});

			function fanhui() {
				window.location.href = "${pageContext.request.contextPath}/supplierQuery/highmaps.html?judge=5";
			}

			function chongzhi() {
				$("#supplierName").val('');
				/* $("#loginName").val(''); */
				$("#startAuditDate").val('');
				$("#endAuditDate").val('');
				$("#contactName").val('');
				$("#category").val('');
				$("#supplierType").val('');
				$("#categoryIds").val('');
				$("#supplierTypeIds").val('');
				$("#mobile").val('');
				$("#isProvisional").val('');
                $("#supplierGradeInputVal").val('');
                $("#supplierGradeInput").val('');
                $("#supplierLevel").val();
				$("#status option:selected").removeAttr("selected");
				$("#address option:selected").removeAttr("selected");
				$("#businessNature option:selected").removeAttr("selected");
				$("#orgName option:selected").removeAttr("selected");
				
				/* var address = '${address}';
				address = encodeURI(address);
				address = encodeURI(address);
				window.location.href = "${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?address=" + address + "&judge=5"; */
				$("#form1").submit();
			}
			
			//回显下拉框
			$(function() {
				/* var optionNodes = $("option");
				for(var i = 1; i < optionNodes.length; i++) {
					if("${supplier.score}" == $(optionNodes[i]).val()) {
						optionNodes[i].selected = true;
					}
				} */
				
				var optionStatus = $("#status").find("option");
				for(var i = 1; i < optionStatus.length; i++) {
					if("${supplier.status}" == $(optionStatus[i]).val()) {
						optionStatus[i].selected = true;
					}
				}
                var supplierLevel = '${supplier.supplierLevel}';
				// 获取供应商品目
                var supplierCateQuery = $("#supplierGradeInput").val();
				if(supplierCateQuery != ''){
                    $("#supplierLevelLi").css("display", "block");
                    $("#supplierLevel").val(supplierLevel);
                }
			});
		</script>
		<script type="text/javascript">
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
					url: "${pageContext.request.contextPath}/supplierQuery/find_supplier_type.do?supplierId=''",
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

			function hideSupplierType() {
				$("#supplierTypeContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDownSupplierType);

			}

			function onBodyDownSupplierType(event) {
				if(!(event.target.id == "menuBtn" || $(event.target).parents("#supplierTypeContent").length > 0)) {
					hideSupplierType();
				}
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
						<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?judge=5&sign=2')">入库供应商列表</a>
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
        <div id="supplierGradeTreeContent" class="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
            <div class="col-md-12 col-xs-8 col-sm-8 p0">
                <input type="text" id="search" class="input_group" value="">
                <img src="${pageContext.request.contextPath }/public/backend/images/view.png" onclick="loadZtree()">
            </div>
            <ul id="supplierGradeTree" class="ztree" style="margin-top:0;"></ul>
        </div>
		<div class="container">
			<div class="headline-v2">
				<h2>供应商信息</h2>
			</div>
			<h2 class="search_detail p20">
				<!--下载Excel查询条件表单-->
				<form id="exportExcelCond">
					<input type="hidden" name="supplierName" value="${supplier.supplierName}"/>
					<input type="hidden" name="businessNature" value="${supplier.businessNature}"/>
					<input type="hidden" name="supplierTypeIds" value="${supplierTypeIds}"/>
					<input type="hidden" name="status" value="${supplier.status}"/>
					<input type="hidden" name="isProvisional" value="${supplier.isProvisional}"/>
					<input type="hidden" name="creditCode" value="${supplier.creditCode}"/>
					<input type="hidden" name="orgName" value="${supplier.orgName}"/>
					<input type="hidden" name="address" value="${supplier.address}"/>
					<input type="hidden" name="queryCategory" value="${supplier.queryCategory }"/>
					<input type="hidden" name="supplierLevel" value="${supplier.supplierLevel }"/>
					<input type="hidden" name="sign" value="${sign}"/>
					<input type="hidden" name="judge" value="${judge}"/>
				</form>
  			<form id="form1" action="${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html" method="post">
		    	<input type="hidden" name="page" id="page">
		      <input type="hidden" name="judge" value="5">
		      <%-- <input type="hidden" name="orgName" value="${ supplier.orgName }"> --%>
		      <input type="hidden" name="reqType" value="${ reqType }">
		      <c:if test="${sign != 2 }">
		      	<input type="hidden" name="address" value="${address }">
		      </c:if>
		      <input type="hidden" name="sign" value="${sign }">
					<div class="row mauto_n5 f14">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="供应商名称">供应商名称：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<input id="supplierName" class="w100p mb0" name="supplierName" value="${supplier.supplierName }" type="text">
								</div>
							</div>
						</div>
						
						<%-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl">用户名：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<input id="loginName" class="w100p" name="loginName" value="${supplier.loginName }" type="text">
								</div>
							</div>
						</div> --%>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="联系人">联系人：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<input id="contactName" class="w100p mb0" name="contactName" value="${supplier.contactName }" type="text">
								</div>
							</div>
						</div>
						
						<%-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl">手机号：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<input id="mobile" class="w100p" name="mobile" value="${supplier.mobile }" type="text">
								</div>
							</div>
						</div> --%>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="企业性质">企业性质：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<select name="businessNature" id="businessNature" class="w100p mb0">
			              <option value=''>全部</option>
			              <c:forEach items="${businessNature}" var="list">
			              	<option <c:if test="${supplier.businessNature eq list.id }">selected</c:if> value="${list.id }">${list.name }</option>
			              </c:forEach>
			            </select>
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="供应商类型">供应商类型：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<input id="supplierType" class="w100p mb0" name="supplierType" readonly value="${supplierType }" onclick="showSupplierType();" type="text">
									<input type="hidden" name="supplierTypeIds" id="supplierTypeIds" value="${supplierTypeIds }">
								</div>
							</div>
						</div>
						
						<%-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="品目">品目：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<input id="category" class="w100p mb0" name="categoryNames" value="${categoryNames }" readonly onclick="showCategory();" type="text">
		              <input type="hidden" name="categoryIds" id="categoryIds" value="${categoryIds }">
								</div>
							</div>
						</div> --%>
						
						<%-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="供应商级别">供应商级别：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<select name="score" class="w100p mb0">
			              <option selected="selected" value=''>-请选择-</option>
			              <option value="1">一级</option>
			              <option value="2">二级</option>
			              <option value="3">三级</option>
			              <option value="4">四级</option>
			              <option value="5">五级</option>
			            </select>
								</div>
							</div>
						</div> --%>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="供应商状态">供应商状态：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<select id="status" name="status" class="w100p mb0">
										<option value=''>全部</option>
										<!-- <option value="1">审核通过</option>
										<option value="4">待复核</option>
										<option value="5">复核通过</option>
										<option value="6">复核未通过</option>
										<option value="7">考察合格</option>
										<option value="8">考察不合格</option> -->
										<c:forEach items="<%=SupplierConstants.STATUSMAP_RUKU %>" var="item">
											<option value="${item.key}">${item.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						
						<%-- <div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="临时供应商">临时供应商：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<select name="isProvisional" id="isProvisional" class="w100p mb0">
			              <option value=''>全部</option>
			              <option value='1' <c:if test="${supplier.isProvisional eq '1' }">selected</c:if>>是</option>
			              <option value='0' <c:if test="${supplier.isProvisional eq '0' }">selected</c:if>>否</option>
			            </select>
								</div>
							</div>
						</div> --%>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="审核日期">审核日期：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<div class="row mauto_n5">
										<div class="col-xs-5 pauto_5">
											<input id="startAuditDate" name="startAuditDate" class="Wdate w100p mb0" type="text"  value='<fmt:formatDate value="${supplier.startAuditDate }" pattern="YYYY-MM-dd"/>' onFocus="var endDate=$dp.$('startAuditDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'startAuditDate\')}'})">
										</div>
				            <div class="col-xs-2 pauto_5 tc">至</div>
										<div class="col-xs-5 pauto_5">
											<input id="endAuditDate" name="endAuditDate" value='<fmt:formatDate value="${supplier.endAuditDate }" pattern="YYYY-MM-dd"/>' class="Wdate w100p mb0" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'endAuditDate\')}'})">
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="采购机构">采购机构：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<select name="orgName" id="orgName" class="w100p mb0">
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
						
						<c:if test ="${sign == 2 }">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="地区">地区：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<select name="address" id="address" class="w100p mb0">
	                  <option value=''>全部</option>
	                  <c:forEach items="${privnce}" var="list">
	                    <option <c:if test="${supplier.address eq list.name }">selected</c:if> value="${list.name }">${list.name }</option>
	                  </c:forEach>
	                </select>
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="供应商品目">供应商品目：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<input class="span2 w100p mb0" name="queryCategoryName" id="supplierGradeInput" type="text" name="" readonly value="${supplier.queryCategoryName }" onclick="initZtree(true);">
									<input type="hidden" name="queryCategory" id="supplierGradeInputVal" value="${supplier.queryCategory}">
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-6 pauto_5 mb10 hide" id="supplierLevelLi">
							<div class="row mauto_n5">
								<div class="col-md-3 col-xs-5 pauto_5 tr text-nowrapEl" title="供应商等级">供应商等级：</div>
								<div class="col-md-9 col-xs-7 pauto_5">
									<select name="supplierLevel" id="supplierLevel" class="w100p mb0">
									  <option selected="selected" value=''>全部</option>
									  <option value="一级">一级</option>
									  <option value="二级">二级</option>
									  <option value="三级">三级</option>
									  <option value="四级">四级</option>
									  <option value="五级">五级</option>
									  <option value="六级">六级</option>
									  <option value="七级">七级</option>
									  <option value="八级">八级</option>
								  </select>
								</div>
							</div>
						</div>
						</c:if>
					</div>
		       <div class="clear tc mt10">
	           <button type="button" onclick="submit()" class="btn">查询</button>
	           <button type="button" class="btn" onclick="chongzhi()">重置</button>
	           <c:if test="${ empty reqType }">
		           <c:choose>
						 			<c:when test="${sign == 2 }">
	                                      <button class="btn" type="button" onclick="fanhui();">切换到地图</button>
	                                      <a href="javascript:;" class="btn" id="export_result">将结果导出Excel</a>
						 			</c:when>
						 			<c:otherwise>
						 					<button class="btn btn-windows back" type="button" onclick="fanhui();">返回</button>
						 			</c:otherwise>
					 		</c:choose>
	           </c:if> 
	           <c:if test="${ not empty reqType }">
	           	<a class="btn btn-windows reset" onclick="history.go(-1)">返回</a>
	           </c:if> 
	         </div>
	         <div class="clear"></div>
		     </form>
		   </h2>

			<div class="content table_box">
				<table id="tb1" class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w50">序号</th>
							<th class="info w170">供应商名称</th>
							<!-- <th class="info">用户名</th> -->
							<th class="info w80">联系人</th>
							<th class="info w100">手机号</th>
							<th class="info w90">注册日期</th>
							<th class="info w90">提交日期</th>
							<th class="info w90">审核日期</th>
							<th class="info w80">地区</th>
							<th class="info w80">供应商类型</th>
							<th class="info w70">企业性质</th>
							<th class="info w100">采购机构</th>
							<th class="info w100">供应商状态</th>
<!--
							<th class="info" width="15%">供应商名称</th>
							<th class="info">用户名</th>
							<th class="info" width="7%">联系人</th>
							<th class="info" width="10%">手机号</th>
							<th class="info" width="10%">注册日期</th>
							<th class="info" width="10%">提交日期</th>
							<th class="info" width="10%">审核日期</th>
							<th class="info" width="7%">地区</th>
							<th class="info" width="13%">供应商类型</th>
							<th class="info" width="7%">企业性质</th>
							<th class="info" width="15%">采购机构</th>
							<th class="info">供应商状态</th>
-->
						</tr>
					</thead>
					<tbody>
						<c:set var="supplierStatusMap" value="<%=SupplierConstants.STATUSMAP %>"/>
						<c:set var="supplierAuditTemporaryStatusMap" value="<%=SupplierConstants.STATUSMAP_AUDITTEMPORARY %>"/>
						<c:forEach items="${listSupplier.list }" var="list" varStatus="vs">
							<tr>
								<td class="tc">${(vs.count)+(listSupplier.pageNum-1)*(listSupplier.pageSize)}</td>
								<td class="hand" title="${list.supplierName}">
									<a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/supplierQuery/essential.html?judge=5&supplierId=${list.id}&sign=${sign}')">
										<c:if test="${fn:length (list.supplierName) > 11}">${fn:substring(list.supplierName,0,11)}...</c:if>
	                  <c:if test="${fn:length (list.supplierName) <= 11}">${list.supplierName}</c:if>
								  </a>
								</td>
								<%-- <td class="">${list.loginName }</td> --%>
								<td class="">${list.contactName }</td>
								<td class="tc">${list.mobile }</td>
								<td class="tc">
                  <fmt:formatDate value="${list.createdAt }" pattern="yyyy-MM-dd" />
                </td>
                <td class="tc">
                  <fmt:formatDate value="${list.submitAt }" pattern="yyyy-MM-dd" />
                </td>
								<td class="tc">
									<fmt:formatDate value="${list.auditDate }" pattern="yyyy-MM-dd" />
								</td>
								<td class="">${list.name }</td>							
								<td class="hand" title="${list.supplierType}">
								  <c:if test="${fn:length (list.supplierType) > 4}">${fn:substring(list.supplierType,0,4)}...</c:if>
                  <c:if test="${fn:length (list.supplierType) <= 4}">${list.supplierType}</c:if>
								</td>
								<td class="tc">${list.businessNature}</td>
								<td class="hand" title="${list.orgName}">
								  <c:if test="${fn:length (list.orgName) > 10}">${fn:substring(list.orgName,0,10)}...</c:if>
                  <c:if test="${fn:length (list.orgName) <= 10}">${list.orgName}</c:if>
								</td>
								<td class="tc">
									<%-- <c:if test="${list.status==1 }"><span class="label rounded-2x label-u">审核通过</span></c:if>
									<c:if test="${list.status==4 }"><span class="label rounded-2x label-dark">待复核</span></c:if>
									<c:if test="${list.status==5 and list.isProvisional == 0 }"><span class="label rounded-2x label-u">复核通过</span></c:if>
									<c:if test="${list.status==6 }"><span class="label rounded-2x label-dark">复核未通过</span></c:if>
									<c:if test="${list.status==7 }"><span class="label rounded-2x label-u">考察合格</span></c:if>
									<c:if test="${list.status==8 }"><span class="label rounded-2x label-dark">考察不合格</span></c:if> --%>
									
									<%-- <c:set var="label_color" value="label-dark"/>
									<c:if test="${list.status==5 || list.status==7 }"><c:set var="label_color" value="label-u"/></c:if>
									<span class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span> --%>
									
									<c:set var="label_color" value="label-dark"/>
									<c:if test="${list.status==5 || list.status==7 }"><c:set var="label_color" value="label-u"/></c:if>
									<c:if test="${list.status == 0 and list.auditTemporary != 1}"><span class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span></c:if>
									<c:if test="${list.status == 9 and list.auditTemporary != 1}"><span class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span></c:if>
									<c:if test="${(list.status == 0 or list.status == 9) and list.auditTemporary == 1}"><span class="label rounded-2x ${label_color}">${supplierAuditTemporaryStatusMap[list.auditTemporary]}</span></c:if>
									<c:if test="${list.status == 1 and list.auditTemporary != 2}"><span class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span></c:if>
									<c:if test="${list.status == 1 and list.auditTemporary == 2}"><span class="label rounded-2x ${label_color}">${supplierAuditTemporaryStatusMap[list.auditTemporary]}</span></c:if>
									<c:if test="${list.status == 5 and list.auditTemporary != 3 and list.isProvisional != 1}"><span class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span></c:if>
									<c:if test="${list.status == 5 and list.auditTemporary == 3 and list.isProvisional != 1}"><span class="label rounded-2x ${label_color}">${supplierAuditTemporaryStatusMap[list.auditTemporary]}</span></c:if>
									<c:if test="${list.status != 0 && list.status != 9 && list.status != 1 && list.status != 5 }"><span class="label rounded-2x ${label_color}">${supplierStatusMap[list.status]}</span></c:if>
									
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