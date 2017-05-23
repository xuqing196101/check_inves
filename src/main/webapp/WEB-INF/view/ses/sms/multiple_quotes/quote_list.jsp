<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="../../../common.jsp"%>
		<title>标书管理</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript">
			function OpenFile() {
				var obj = document.getElementById("TANGER_OCX");
				obj.Menubar = true;
				obj.Caption = "( 双击可放大 ! )";
			}

			function queryVersion() {
				var obj = document.getElementById("TANGER_OCX");
				var v = obj.GetProductVerString();
				obj.ShowTipMessage("当前ntko版本", v);
			}

			function inputTemplete() {
				var obj = document.getElementById("TANGER_OCX");
			}

			function saveFile() {
				var obj = document.getElementById("TANGER_OCX");
				var s = obj.GetBookmarkValue("书签");
				alert(s);
			}

			function closeFile() {
				var obj = document.getElementById("TANGER_OCX");
				obj.close();
			}

			//标记
			function mark() {
				var obj = document.getElementById("TANGER_OCX");
				obj.ActiveDocument.BookMarks.Add("标记");
			}

			//获取标记内容并且定位
			function searchMark() {
				var obj = document.getElementById("TANGER_OCX");
				//判断标记是否存在
				if(obj.ActiveDocument.Bookmarks.Exists("标记")) {}
				alert(obj.GetBookmarkValue("标记"));
				//alert(obj.ActiveDocument.GetCurPageStart());
				//定位到书签内容
				obj.ActiveDocument.Bookmarks.Item("标记").Select();
				//alert(obj.ActiveDocument.GetPagesCount());
			}

			//删除标记
			function delMark() {
				var obj = document.getElementById("TANGER_OCX");
				obj.ActiveDocument.BookMarks.Item("标记").Delete();
			}

			function add() {
				var id = [];
				var packageName = $('input[name="chkItem"]:checked').parent().parent().children("td").eq(4).text();
				var packageId = $('input[name="chkItem"]:checked').parent().parent().children("td").eq(4).attr("id");
				packageName = encodeURI(packageName);
				packageName = encodeURI(packageName);
				$('input[name="chkItem"]:checked').each(function() {
					id.push($(this).val());
				});
				if(id.length == 1) {
					window.location.href = "${pageContext.request.contextPath}/mulQuo/baojia.html?id=" + id + "&packageName=" + packageName + "&packageId=" + packageId;
				} else if(id.length > 1) {
					layer.alert("只能选择一个", {
						offset: ['122px', '390px'],
						shade: 0.01
					});
				} else {
					layer.alert("请选择需要报价的项目", {
						offset: ['122px', '390px'],
						shade: 0.01
					});
				}
			}
			const ONE = "1";
			const FIVE = "5";
			const SIX = "6";
			const SEVEN = "7";
			const EIGHT = "8";
			const NINE = "9";

			function addTotal() {
				var allTable = document.getElementsByTagName("table");
				for(var i = 0; i < allTable.length; i++) {
					var totalMoney = 0;
					for(var j = 1; j < allTable[i].rows.length - 1; j++) { //遍历Table的所有Row
						var num = $(allTable[i].rows).eq(j).find("td").eq(FIVE).text();
						var price = $(allTable[i].rows).eq(j).find("td").eq(SIX).find("input").val();
						var reg = /^\d+\.?\d*$/;
						if(!reg.exec(price)) {
							$(allTable[i].rows).eq(j).find("td").eq(SIX).find("input").val('');
							return;
						}
						var total = $(allTable[i].rows).eq(j).find("td").eq(SEVEN).text();
						if(price == "" || price.trim() == "") {
							continue;
						} else {
							$(allTable[i].rows).eq(j).find("td").eq("7").text(parseFloat(price * num).toFixed(2));
							totalMoney += parseFloat(price * num);
							$(allTable[i].rows).eq(allTable[i].rows.length - 1).find("td").eq(ONE).text(parseFloat(totalMoney).toFixed(2));
						};
					};
				};
			};

			function eachTable() {
				var allTable = document.getElementsByTagName("table");
				var priceStr = "";
				var error = 0;
				for(var i = 0; i < allTable.length; i++) {
					for(var j = 1; j < allTable[i].rows.length - 1; j++) { //遍历Table的所有Row
						var num = $(allTable[i].rows).eq(j).find("td").eq(FIVE).text();
						var price = $(allTable[i].rows).eq(j).find("td").eq(SIX).find("input").val();
						var total = $(allTable[i].rows).eq(j).find("td").eq(SEVEN).text();
						var deliveryTime = $(allTable[i].rows).eq(j).find("td").eq(EIGHT).find("input").val();
						var remark = $(allTable[i].rows).eq(j).find("td").eq(NINE).find("input").val();
						if(remark == "" || remark.trim() == "") {
							remark = null;
						}
						if(deliveryTime == "") {
							layer.msg("页签" + (i + 1) + ",表格第" + (j + 1) + "行,交货时间未填写");
							return;
						}
						if(price == "" || price.trim() == "") {
							layer.msg("页签" + (i + 1) + ",表格第" + (j + 1) + "行,未报价");
							return;
							error++;
						} else {
							priceStr += price + "," + total + "," + deliveryTime + "," + remark + ",";
						};
					};
				}
				if(error == 0) {
					$("#priceStr").val(priceStr);
					form.submit();
				};
			}

			function showQuoteHistory(data) {
				var projectId = $("#projectId").val();
				location.href = "${pageContext.request.contextPath}/mulQuo/quoteHistory.html?timestamp=" + data + "&projectId=" + projectId;
			}
		</script>

		<!-- 打开文档后只读 -->
		<!-- <script type="text/javascript" for="TANGER_OCX" event="OnDocumentOpened(a,b)">
		var obj = document.getElementById("TANGER_OCX");
		obj.SetReadOnly(true);
</script> -->
	</head>

	<body onload="OpenFile()">
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">我的项目</a>
					</li>
					<li>
						<a href="javascript:void(0);">报价管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container clear mt20">
			<div class="list-unstyled padding-10 breadcrumbs-v3">
				<span>
					  <a href="${pageContext.request.contextPath}/mulQuo/openBid.html?projectId=${project.id}" class="img-v1">开标一览表</a>
					  <span class="green_link">→</span>
				</span>
				<span>
					  <a href="${pageContext.request.contextPath}/mulQuo/priceBuild.html?projectId=${project.id}" class="img-v1">价格构成表</a>
					  <span class="green_link">→</span>
				</span>
				<span>
					  <a href="${pageContext.request.contextPath}/mulQuo/priceView.html?projectId=${project.id}" class="img-v1">明细表</a><!--货物材料、部件、工具价格明细表  -->
					  <span class="green_link">→</span>
				</span>
				<span>
		    	<c:if test="${std.bidFinish == 0}">
				  <a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v2 orange_link">编制标书</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish != 0}">
					<a href="${pageContext.request.contextPath}/supplierProject/bidDocument.html?projectId=${project.id}" class="img-v1">编制标书</a>
					<span class="">→</span>
				</c:if>
				</span>
				<span>
				<c:if test="${std.bidFinish == 1}">
				  <a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v2 orange_link">绑定指标</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 2 || std.bidFinish == 3 || std.bidFinish == 4}">
					<a href="${pageContext.request.contextPath}/supplierProject/toBindingIndex.html?projectId=${project.id}" class="img-v1">绑定指标</a>
					<span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 0}">
					<a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">绑定指标</a>
					<span class="">→</span>
				</c:if>
				</span>
				<span>
				<c:if test="${std.bidFinish == 2 }">
				  <a href="${pageContext.request.contextPath}/mulQuo/list.html?projectId=${project.id}"  class="img-v2 orange_link">填写报价</a>
				  <span class="green_link">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 0}">
					<a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">填写报价</a>
					<span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 1}">
					<a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">填写报价</a>
					<span class="">→</span>
				</c:if>
				<c:if test="${std.bidFinish == 3 || std.bidFinish == 4}">
					<a href="${pageContext.request.contextPath}/mulQuo/list.html?projectId=${project.id}" class="img-v1">填写报价</a>
					<span class="">→</span>
				</c:if>
				</span>
				<span>
		    	<c:if test="${std.bidFinish == 3 }">
			  		<a href="${pageContext.request.contextPath}/supplierProject/result.html?projectId=${project.id}" class="img-v2  orange_link">完成</a>
		    	</c:if>
		    	<c:if test="${std.bidFinish == 0}">
				  <a href="javascript:void(0);" onclick="tishi('请先编制保存标书到服务器');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 1}">
				  <a href="javascript:void(0);" onclick="tishi('请先绑定指标');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 2}">
				  <a href="javascript:void(0);" onclick="tishi('请先填写报价');" class="img-v3">完成</a>
				</c:if>
				<c:if test="${std.bidFinish == 4}">
				  <a href="${pageContext.request.contextPath}/supplierProject/result.html?projectId=${project.id}"  class="img-v1">完成</a>
				</c:if>
			</span>
			</div>
		</div>

		<!-- 项目戳开始 -->
		<div class="container clear">
			<!--详情开始-->
			<form id="form" action="${pageContext.request.contextPath}/mulQuo/save.html" method="post">
				<input id="priceStr" name="priceStr" type="hidden" />
				<input id="projectId" name="projectId" value="${projectId }" type="hidden" />
				<div class="row magazine-page">
					<div class="col-md-12 tab-v2 job-content">
						<div class="padding-top-10">
							<ul class="nav nav-tabs bgdd">
								<c:forEach items="${listPackage }" var="obj" varStatus="vs">
									<c:if test="${vs.index==0 }">
										<li class="active">
											<a aria-expanded="true" href="#tab-${vs.index+1 }" data-toggle="tab" title="${obj.name }">
												<c:choose>
													<c:when test="${fn:length(obj.name)>3}">${fn:substring(obj.name, 0, 3)}...</c:when>
													<c:otherwise>${obj.name}</c:otherwise>
												</c:choose>
											</a>
										</li>
									</c:if>
									<c:if test="${vs.index>0 }">
										<li class="">
											<a aria-expanded="true" href="#tab-${vs.index+1 }" data-toggle="tab" title="${obj.name }">
												<c:choose>
													<c:when test="${fn:length(obj.name)>3}">${fn:substring(obj.name, 0, 3)}...</c:when>
													<c:otherwise>${obj.name}</c:otherwise>
												</c:choose>
											</a>
										</li>
									</c:if>
								</c:forEach>
							</ul>
							<div class="tab-content">
								<c:forEach items="${listPd }" var="listProDel" varStatus="vs">
									<c:choose>
										<c:when test="${vs.index==0 }">
											<div class="tab-pane fade active in height-450" id="tab-${vs.index+1 }">
												<table id="tb${vs.index }" class="table table-bordered table-condensed mt5">
													<thead>
														<tr>
															<th class="info w50">序号</th>
															<th class="info">物资名称</th>
															<th class="info">规格型号</th>
															<th class="info">质量技术标准</th>
															<th class="info">计量单位</th>
															<th class="info">采购数量</th>
															<th class="info">单价（元）</th>
															<th class="info">小计</th>
															<th class="info">交货时间</th>
															<th class="info">备注</th>
														</tr>
													</thead>
													<c:forEach items="${listProDel }" var="proDel" varStatus="vs">
														<tr class="hand">
															<td class="tc w50">${proDel.serialNumber}</td>
															<td class="tc">${proDel.goodsName}</td>
															<td class="tc">${proDel.stand}</td>
															<td class="tc">${proDel.qualitStand}</td>
															<td class="tc">${proDel.item}</td>
															<td class="tc">${proDel.purchaseCount}</td>
															<td class="tc"><input maxlength="16" onblur="addTotal()" /></td>
															<td class="tc"></td>
															<td class="tc"><input readonly="readonly" onClick="WdatePicker()" /></td>
															<td class="tc"><input /></td>
														</tr>
													</c:forEach>
													<tr>
														<td class="tr" colspan="2"><b>总金额(元):</b></td>
														<td class="tl" colspan="7"></td>
													</tr>
												</table>
											</div>
										</c:when>
										<c:otherwise>
											<div class="tab-pane fade in height-450" id="tab-${vs.index+1 }">
												<table id="tb${vs.index }" class="table table-bordered table-condensed mt5">
													<thead>
														<tr>
															<th class="info w50">序号</th>
															<th class="info">物资名称</th>
															<th class="info">规格型号</th>
															<th class="info">质量技术标准</th>
															<th class="info">计量单位</th>
															<th class="info">采购数量</th>
															<th class="info">单价（元）</th>
															<th class="info">小计</th>
															<th class="info">交货时间</th>
															<th class="info">备注</th>
														</tr>
													</thead>
													<c:forEach items="${listProDel }" var="proDel" varStatus="vs">
														<tr class="hand">
															<td class="tc w50">${proDel.serialNumber}</td>
															<td class="tc">${proDel.goodsName}</td>
															<td class="tc">${proDel.stand}</td>
															<td class="tc">${proDel.qualitStand}</td>
															<td class="tc">${proDel.item}</td>
															<td class="tc">${proDel.purchaseCount}</td>
															<td class="tc"><input maxlength="16" onblur="addTotal()" /></td>
															<td class="tc"></td>
															<td class="tc"><input readonly="readonly" onClick="WdatePicker()" /></td>
															<td class="tc"><input /></td>
														</tr>
													</c:forEach>
													<tr>
														<td class="tr" colspan="2"><b>总金额(元):</b></td>
														<td class="tl" colspan="7"></td>
													</tr>
												</table>
											</div>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</div>
						</div>
						<div class="col-md-12 tc">
							<input class="btn btn-windows save" value="保存" type="button" onclick="eachTable()">
							<span>报价历史查看：</span>
							<select>
								<c:if test="${empty listDate }">
									<option value=''>暂无报价历史</option>
								</c:if>
								<c:forEach items="${listDate }" var="ld" varStatus="vs">
									<option value='<fmt:formatDate value="${ld}" pattern="YYYY-MM-dd HH:mm:ss"/>' onclick="showQuoteHistory('<fmt:formatDate value=" ${ld} " pattern="YYYY-MM-dd HH:mm:ss "/>')">第${vs.index+1 }次报价</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</form>
		</div>
	</body>

</html>