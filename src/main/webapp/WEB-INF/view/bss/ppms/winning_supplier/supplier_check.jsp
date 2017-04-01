<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<base href="${pageContext.request.contextPath}/">
<%@ include file="../../../common.jsp"%>
<title>确定中标供应商</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
	type="text/css">
</head>
<script type="text/javascript">
	$(function() {
		var quote = "${quote}";

		// 	  0总价 1明细
		if (quote == 0) {

		} else {
			var singQuotelist = document.getElementsByName("singQuote");
			for (var i = 0; i < singQuotelist.length; i++) {
				$(singQuotelist[i]).attr("class", "inputborder");
				$(singQuotelist[i]).attr("disabled", true);
			}
		}
	});

	/** 全选全不选 */
	function selectAll() {
		var checklist = document.getElementsByName("chkItem");
		var checkAll = document.getElementById("checkAll");
		if (checkAll.checked) {
			for (var i = 0; i < checklist.length; i++) {
				checklist[i].checked = true;
				var associate = document.getElementsByName("associate"
						+ checklist[i].value);
				for (var k = 0; k < associate.length; k++) {
					associate[k].checked = true;
				}

			}

		} else {
			for (var j = 0; j < checklist.length; j++) {
				checklist[j].checked = false;
				var associate = document.getElementsByName("associate"
						+ checklist[j].value);
				for (var k = 0; k < associate.length; k++) {
					associate[k].checked = false;
				}
			}
		}

		ratio();
	}

	/** 单选 */
	function check(index) {
		var count = 0;
		var checklist = document.getElementsByName("chkItem");
		var checkAll = document.getElementById("checkAll");
		for (var i = 0; i < checklist.length; i++) {
			if (checklist[i].checked == false) {

				checkAll.checked = false;
				break;
			}
			for (var j = 0; j < checklist.length; j++) {
				if (checklist[j].checked == true) {
					checkAll.checked = true;
					count++;
				}

			}
		}

		var associate = document.getElementsByName("associate" + index);
		for (var i = 0; i < associate.length; i++) {
			if ($("#rela" + index).prop("checked")) {
				$(associate[i]).prop("checked", "checked");
			} else {
				$(associate[i]).prop("checked", false);
			}

		}

		ratio(index);

	}

	/**移除供应商 */
	function del(btn) {
		var ids = [];
		$('input[name="chkItem"]:checked').each(function() {
			ids.push($(this).val());
		});
		if (ids.length == 1) {

			layer.confirm(
				'您确定要移除吗?',
				{
					title : '提示',
					offset : [ '222px', '360px' ],
					shade : 0.01
				},
				function(index) {
					layer.close(index);

					var type = 0;
					layer.open({
						type : 2,
						title : '上传',
						shadeClose : false,
						shade : 0.01,
						area : [ '367px', '180px' ], //宽高
						content : '${pageContext.request.contextPath}/winningSupplier/supplierUpload.html?packageId=${packageId}&&flowDefineId=${flowDefineId}&&projectId=${projectId}&&checkPassId='
								+ ids,
						success : function(layero, index) {
							iframeWin = window[layero
									.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						},
						btn : [ '保存', '关闭' ],
						yes : function() {
							iframeWin.upload();
							type = 1;
						},
						btn2 : function() {
							delFileAjax();
						},
						end : function() {
							if (type != 1) {
								delFileAjax();
							}
						}
					});

				});
		} else if (ids.length > 1) {
			layer.alert("只能选择一个供应商", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		} else {
			layer.alert("请选择要移除的供应商", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		}
	}

	/** 计算金额*/
	function ratio(index) {
		var quote = "${quote}";
		var checklist = document.getElementsByName("chkItem");
		for (var j = 0; j < checklist.length; j++) {
			$("#" + checklist[j].value).find("#priceRatio").text("");
			$("#" + checklist[j].value).find("#wonPrice").text("");
			if (quote == 0) {
				$("#" + checklist[j].value).find("#singQuote").val("");
			} else {
				$("#" + checklist[j].value).find("#singQuote").text("");
				$("#" + checklist[j].value).find("#singQuotehhide").val("");

			}

		}

		var lengths = $("input[name='chkItem']:checked").length;
		if (lengths != 0) {
			var id = [];
			var ratio = [];
			if (lengths == 1) {
				ratio.push("100");
			} else if (lengths == 2) {
				ratio.push("70");
				ratio.push("30");
			} else if (lengths == 3) {
				ratio.push("50");
				ratio.push("30");
				ratio.push("20");
			} else if (lengths == 4) {
				ratio.push("40");
				ratio.push("30");
				ratio.push("20");
				ratio.push("10");
			}
			var i = 0;
			//第一名的报价金额
			var onePrice = [];
			//算出实际成交金额
			$('input[name="chkItem"]:checked').each(
					function() {
						$("#" + $(this).val()).find("#priceRatio").text(
								ratio[i]);
						//                var totalprice = $("#"+id[0]).find("#totalPrice").text();
						var price = 0;

						var id = $(this).val();
						var j = 0;
						$('input[name="associate' + id + '"]:checked').each(
								function() {
									//报价id
									var quote = $("#" + id + $(this).val())
											.find("#Quotedamount").text();
									var count = $("#" + id + $(this).val())
											.find("#purchaseCount").text();

									//                  alert(onePrice[length-1]);
									//                  alert(quote);
									//                  alert(onePrice);
									//第一名赋值报价
									if (onePrice[j] == null
											|| onePrice[j] == '') {
										onePrice[j] = quote;
										//                   alert(onePrice[i]);
									} else {

										if (quote >= onePrice[j]) {
											quote = onePrice[j];
										}

									}
									price = parseFloat(price)
											+ toDecimal((ratio[i] / 100)
													* count * quote);

									//                  alert(quote);     
									//                  alert(count);
									j++;

								});
						if (price == 0) {
							price = "";
						}
						var quote = "${quote}";
						if (quote == 0) {
							$("#" + $(this).val()).find("#singQuote")
									.val(price);
						} else {
							$("#" + $(this).val()).find("#singQuote").text(
									price);
							$("#" + $(this).val()).find("#singQuotehhide").val(
									price);
						}

						i++;
					});
		}
	}

	//保留两位小数  
	//功能：将浮点数四舍五入，取小数点后2位 
	function toDecimal(x) {
		var f = parseFloat(x);
		if (isNaN(f)) {
			return;
		}
		f = Math.round(x * 100) / 100;
		return f;
	}

	function save() {
		var id = "";
		var wonPrice = [];
		var isNull = 0;
		var priceRatio = [];
		var quote = "${quote}";
		var supplierIds = [];

		$('input[name="chkItem"]:checked').each(function() {
			id += $(this).val() + ",";
			supplierIds.push($(this).attr("class"));
			priceRatio.push($(this).parent().parent().find("[title='priceRatio']").text());

			var sq = 0;
			if (quote == 0) {
				sq = $("#" + $(this).val()).find("#singQuote").val();
				if (isNull == 0) {
					if (sq == null || sq == '') {
						isNull = 1;
					}
				}
			} else {
				sq = $("#" + $(this).val()).find("#singQuote").text();
			}
			wonPrice.push(sq);
		});
		id = id.substring(0, id.length - 1);

		if (id.length >= 1) {
			layer.confirm(
				'确定后将跳转到录入标的,是否确定',
				{
					title : '提示',
					offset : [ '100px', '300px' ],
					shade : 0.01
				},
				function(index) {
					//var json = '${supplierCheckPassJosn}';
					var json = '';
					layer.close(index);
					window.location.href = "${pageContext.request.contextPath}/winningSupplier/packageSupplier.html?packageId=" + supplierIds + "&&ids=" + id + "&&flowDefineId=${flowDefineId}&&passquote=${quote}&&pid=${packageId}&&projectId=${projectId}&&priceRatios="+priceRatio;
				}
			);
			/*
			if (isNull == 1) {
				layer.alert("请选择填写实际成交金额", {
					offset : [ '100px', '390px' ],
					shade : 0.01
				});
			} else {
				
			}*/
		} else {
			layer.alert("请选择供应商", {
				offset : [ '100px', '390px' ],
				shade : 0.01
			});
		}
	}

	/**ajax 删除文件 **/
	function delFileAjax() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : '${pageContext.request.contextPath}/winningSupplier/deleFile.do?packageId=${packageId}',
			success : function(data) {
				var map = data;
				alert(map);
				if (map == "SCCUESS") {
					window.location.href = '${pageContext.request.contextPath}/winningSupplier/selectSupplier.do?projectId=${projectId}&&flowDefineId=${flowDefineId}';
				} else {
					layer.msg("请上传");
				}

			}
		});
	}

	/** 中标供应商 */
	function tabone() {
		window.location.href = "${pageContext.request.contextPath}/winningSupplier/selectSupplier.html?projectId=${projectId}";
	}

	/** 中标通知 */
	function tabtwo() {
		var error = "${error}";
		if (error != null && error == "ERROR") {
			layer.alert("请选择中标供应商", {
				offset : [ '100', '300px' ],
				shade : 0.01
			});
		} else {
			window.location.href = "${pageContext.request.contextPath}/winningSupplier/template.do?projectId=${projectId}";
		}

	}

	/** 未中标通知 */
	function tabthree() {
		var error = "${error}";
		if (error != null && error == "ERROR") {
			layer.alert("请选择中标供应商", {
				offset : [ '100', '300px' ],
				shade : 0.01
			});
		} else {
			window.location.href = "${pageContext.request.contextPath}/winningSupplier/notTemplate.do?projectId=${projectId}";
		}
	}

	//点击中标供应商隐藏显示所属明细
	function ycDiv(obj, index) {
		//var bfb = parseFloat($(obj).parent().parent().find("td:eq(7)").text())/100;
		if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
			$(obj).removeClass("shrink");
			$(obj).addClass("spread");
		} else {
			if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
				$(obj).removeClass("spread");
				$(obj).addClass("shrink");
			}
		}
		if ($(obj).parent().parent().next().hasClass("hide")) {
			$(obj).parent().parent().next().removeClass("hide");
		} else {
			$(obj).parent().parent().next().addClass("hide");
		}
		var detail = document.getElementsByName("detail" + index);
		for (var i = 0; i < detail.length; i++) {
			if ($(detail[i]).hasClass("hide")) {
				$(detail[i]).removeClass("hide");
			} else {
				$(detail[i]).addClass("hide");
			}
		}
	}
	

	//录入表的
	function InputBD() {
		window.location.href = "${pageContext.request.contextPath}/winningSupplier/inputList.do?projectId=${projectId}&packageId=${packageId}";
	}
	//关联选中
	function associateSelected(id, obj, index) {
		var associate = document.getElementsByName("associate" + index);
		for (var i = 0; i < associate.length; i++) {
			if (associate[i].checked) {
				$("#rela" + index).prop("checked", "checked");
				break;
			} else if (i == associate.length - 1) {
				$("#rela" + index).prop("checked", false);
			}
		}
		var count = 0;
		var checklist = document.getElementsByName("chkItem");
		var checkAll = document.getElementById("checkAll");
		for (var i = 0; i < checklist.length; i++) {
			if (checklist[i].checked == false) {
				checkAll.checked = false;
				break;
			}
			for (var j = 0; j < checklist.length; j++) {
				if (checklist[j].checked == true) {
					checkAll.checked = true;
					count++;
				}
			}
		}
		ratio();
		$.ajax({
			type : "POST",
			dataType : "json",
			async : false, //请求是否异步，默认为异步
			url : "${pageContext.request.contextPath }/project/findDetailById.do?id="
					+ id,
			success : function(data) {
				var purchaseCount = data.purchaseCount;
				var bfb = parseFloat($("#rela" + index).parent()
						.parent().find("td:eq(7)").text()) / 100;
				if ($("#rela" + index).prop("checked")) {
					if ("${quote}" == 0) {
						$(obj).parent().parent().find("td:eq(7)").text(
								"");
						$(obj).parent().parent().find("td:eq(8)").text(
								"");
					} else {
						var price = $(obj).parent().parent().find(
								"td:eq(8)").text();
						$(obj).parent().parent().find("td:eq(7)").text(
								parseFloat(bfb * purchaseCount));
						$(obj).parent().parent().find("td:eq(9)")
								.text(
										parseFloat(bfb * purchaseCount
												* price) / 10000);
					}
				} else {
					$(obj).parent().parent().find("td:eq(7)").text("");
					$(obj).parent().parent().find("td:eq(9)").text("");
				}
				if ("${quote}" == 1) {
					var aociate = document
							.getElementsByName("associate" + index);
					var realPrice = 0;
					for (var i = 0; i < aociate.length; i++) {
						var detailPrice = $(aociate[i]).parent()
								.parent().find("td:eq(9)").text();
						if (detailPrice != "") {
							realPrice = realPrice
									+ parseFloat(detailPrice);
						}
					}
					if (realPrice == 0) {
						$("#singQuote" + index).val("");
					} else {
						$("#singQuote" + index).val(realPrice);
					}
				} else {
					$(obj).parent().parent().find("td:eq(9)").text(
							$("#wonPrice" + index).text());
				}
			}
		});
	}
	function hrefGO(){
		location.href="${pageContext.request.contextPath}/winningSupplier/selectSupplier.do?projectId=${projectId}&&flowDefineId=${flowDefineId}";
	}
</script>

<body>

	<h2 class="list_title mb0 clear">确认中标供应商</h2>

	<c:if test="${view != 1 }">
		<div class="col-md-12 col-xs-12 col-sm-12 mt10 p0">
			<button class="btn " onclick="save();" type="button">确定</button>
			<button class="btn " onclick="del(this);" type="button">移除</button>
		</div>
	</c:if>
	<div class="content table_box pl0">
		<table
			class="table table-bordered table-condensed table-hover table-striped">
			<thead>
				<tr class="info">
					<c:if test="${view != 1}">
						<th class="w30"><input id="checkAll" type="checkbox"
							onclick="selectAll()" /></th>
					</c:if>
					<th class="w200">供应商名称</th>
					<!--               <th class="w100">参加时间</th> -->
					<th style="width: 110px;">&nbsp;总报价&nbsp;（万元）</th>
					<th style="width: 50px;">总得分</th>
					<th style="width: 20px;">排名</th>
					<c:if test="${view == 1}">
						<th style="width: 50px;">中标状态</th>
					</c:if>
					<th class="w50">占比（%）</th>
				</tr>
			</thead>
			<c:forEach items="${supplierCheckPass}" var="checkpass"
				varStatus="vs">
				<tr id="${checkpass.id}">
					<c:if test="${view != 1}">
						<td class="tc opinter"><c:if
								test="${checkpass.isDeleted == 0 }">

								<input onclick="check('${checkpass.id}');"
									id="rela${checkpass.id}" type="checkbox" name="chkItem" class="${checkpass.supplier.id}"
									value="${checkpass.id}" />
								</c:if>
							</td>
					</c:if>
					<td class="opinter" title="${checkpass.supplier.supplierName }">
						<c:choose>
							<c:when test="${fn:length(checkpass.supplier.supplierName) >10}">
                    ${fn:substring(checkpass.supplier.supplierName , 0, 10)}...
                 </c:when>
							<c:otherwise>
                  ${checkpass.supplier.supplierName}
                 </c:otherwise>
						</c:choose>
					</td>
					<!--               <td class="tc opinter" onclick=""> -->
					<%--                 <fmt:formatDate value='${checkpass.joinTime}' pattern="yyyy-MM-dd " /> --%>
					<!--               </td> -->
					<td class="tc opinter" id="totalPrice" onclick="">${checkpass.totalPrice}</td>
					<td class="tc opinter" onclick="">${checkpass.totalScore}</td>
					<td class="tc opinter" onclick="">${vs.index+1}</td>
					<c:if test="${view == 1}">
						<c:if test="${checkpass.isWonBid != 1}">
							<td class="tc opinter">未中标</td>
						</c:if>
						<c:if test="${checkpass.isWonBid == 1}">
							<td class="tc opinter">已中标</td>
						</c:if>
					</c:if>
					<td class="tc opinter" id="priceRatio" title="priceRatio">${checkpass.priceRatio}</td>
				</tr>
			</c:forEach>
		</table>
		<div class="col-md-12 tc">
			<button class="btn btn-windows back" onclick="hrefGO();"
				type="button">返回</button>
		</div>
	</div>
</body>

</html>