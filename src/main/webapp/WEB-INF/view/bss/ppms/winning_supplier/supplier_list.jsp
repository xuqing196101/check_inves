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

			layer
					.confirm(
							'您确定要移除吗?',
							{
								title : '提示',
								offset : [ '222px', '360px' ],
								shade : 0.01
							},
							function(index) {
								layer.close(index);

								var type = 0;
								layer
										.open({
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
		var quote = "${quote}";//0提示唱总价//1提示唱明细
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
			$('input[name="chkItem"]:checked').each(function() {
				$("#" + $(this).val()).find("#priceRatio").text(ratio[i]);
				//                var totalprice = $("#"+id[0]).find("#totalPrice").text();
				var price = 0;

				var id = $(this).val();
				var j = 0;
				$('input[name="associate' + id + '"]:checked').each(
						function() {
							//报价id
							var quote = $("#" + id + $(this).val()).find("#Quotedamount").text();
							var count = $("#" + id + $(this).val()).find("#purchaseCount").text();
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
							j++;

						});
				if (price == 0) {
					price = "";
				}
				var quote = "${quote}";
				if (quote == 0) {
					$("#" + $(this).val()).find("#singQuote").val(price);
				} else {
					$("#" + $(this).val()).find("#singQuote").text(price);
					$("#" + $(this).val()).find("#singQuotehhide").val(price);
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
		var id = [];
		var wonPrice = [];
		var isNull = 0;
		var quote = "${quote}";

		$('input[name="chkItem"]:checked').each(function() {
			id.push($(this).val());

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

		if (id.length >= 1) {
			if (isNull == 1) {
				layer.alert("请选择填写实际成交金额", {
					offset : [ '100px', '390px' ],
					shade : 0.01
				});
			} else {
				layer.confirm(
					'确定后将不可修改,是否确定',
					{
						title : '提示',
						offset : [ '100px', '300px' ],
						shade : 0.01
					},
					function(index) {
						var json = '${supplierCheckPassJosn}';
						layer.close(index);
						$.ajax({
							type : "post",
							url : "${pageContext.request.contextPath}/winningSupplier/comparison.do",
							data : "checkPassId=" + id
									+ "&&jsonCheckPass="
									+ json + "&&wonPrice="
									+ wonPrice,
							dataType : "json",
							success : function(data) {
								//                           alert(data);
								if (data == "SCCUESS") {
									window.location.href = '${pageContext.request.contextPath}/winningSupplier/selectSupplier.do?projectId=${projectId}&&flowDefineId=${flowDefineId}';
								} else {
									var iframeWin;
									var type = 0;
									layer.open({
										type : 2,
										title : '上传',
										shadeClose : false,
										shade : 0.01,
										area : [
												'367px',
												'180px' ], //宽高
										content : '${pageContext.request.contextPath}/winningSupplier/upload.html?packageId=${packageId}&&flowDefineId=${flowDefineId}&&projectId=${projectId}&&checkPassId='
												+ id
												+ '&&wonPrice='
												+ wonPrice,
										success : function(
												layero,
												index) {
											iframeWin = window[layero
													.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
										},
										btn : [
												'保存',
												'关闭' ],
										yes : function() {
											iframeWin
													.upload();
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
								}
							}
						});
					});
			}
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
	
	//双击占比tr时，改变占比
	function changePriceRatio(objTD) {
		var $obj = $(objTD);
		var $childInput = $obj.children("input");
		var changeStatus = $childInput.attr("title");
		var changeStatusNum = 1;
		var changeStatusJudge = "";
		for(var i = 0;i < changeStatus.length;i++) {
			if(changeStatus.charAt(i) > 0 && changeStatus.charAt(i) < 10) {
				changeStatusNum = changeStatus.substr(i,changeStatus.length);
				changeStatusJudge = changeStatus.substr(0,i);
			}
		}
		var isContinue = 1;
		var tempNum = 1 + parseInt(changeStatusNum);
		
		$("input[class^='forChangeRatio']").each(function(index) {
			var tempStr = "changed" + tempNum;
			if($(this).attr("title") == tempStr) {
				isContinue = 0;
			}
			
		});
		if($("input[class^='forChangeRatio']").size() == (tempNum - 1)) {
			isContinue = 0;
		}
		if(isContinue == 0) {
			if(changeStatusJudge == "unchanged") {
				$childInput.css({
					border : "1px solid black"
				});
				$childInput.removeAttr("readonly");
				$childInput.attr("title","changed" + changeStatusNum);
			} else if(changeStatusJudge == "changed") {
				$childInput.css({
					border : "0px solid grey"
				});
				$childInput.attr("readonly","readonly");
				$childInput.attr("title","unchanged" + changeStatusNum);
			}
		}
	}
	var tempTextValue = 0;
	//占比改变调用
	function priceRatioConfirm(obj) {
		var $obj = $(obj);
		var objVal = $obj.val();
		var objTitleValue = $obj.attr("title");
		var currentStatus = 0;
		for(var i = 0;i < objTitleValue.length;i++) {
			if(objTitleValue.charAt(i) > 0 && objTitleValue.charAt(i) < 10) {
				currentStatus = parseInt(objTitleValue.substr(i,objTitleValue.length));
				//changeStatusJudge = changeStatus.substr(0,i);
				break;
			}
		}
		
		if(objVal > 0 && objVal <= tempTextValue) {
			var diffCount = parseInt(tempTextValue) - parseInt(objVal);
			$("input[class^='forChangeRatio']").each(function() {
				var tempStr = $(this).attr("title");
				var tempStatus = 0;
				for(var i = 0;i < tempStr.length;i++) {
					if(tempStr.charAt(i) > 0 && tempStr.charAt(i) < 10) {
						tempStatus = parseInt(tempStr.substr(i,tempStr.length));
						//changeStatusJudge = changeStatus.substr(0,i);
						break;
					}
				}
				if(currentStatus == 1) {
					if(tempStatus == (currentStatus + 1)) {
						$(this).val(parseInt($(this).val()) + diffCount);
					}
				} else if(currentStatus > 1) {
					if(tempStatus == (currentStatus - 1)) {
						$(this).val(parseInt($(this).val()) + diffCount);
					}
				}
			});
		} else {
			layer.alert("请输入小于当前数的值");
			$obj.val(tempTextValue);
		}
	}
	
	function priceRatioFocus(obj) {
		tempTextValue = $(obj).val();
	}

	//录入表的
	function InputBD(obj) {
		var supplierId = $(obj).parent().parent().find(".supplierId").val();
		window.location.href = "${pageContext.request.contextPath}/winningSupplier/inputList.do?projectId=${projectId}&packageId=${packageId}&quote=${quote}&pid=${pid}&supplierId=" + supplierId;
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
						$(obj).parent().parent().find("td:eq(7)").text("");
						$(obj).parent().parent().find("td:eq(8)").text("");
					} else {
						var price = $(obj).parent().parent().find("td:eq(8)").text();
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
	
	//去掉str前后空格、空白
	function trim(str){  
		return str.replace(/^(\s|\u00A0)+/,'').replace(/(\s|\u00A0)+$/,'');  
	}

	function ratioPrice() {
		//单价unitPrice
		$("[class^='forChangeRatio']").each(function() {
			var unitPrice = 0;//单价
			var purchaseCount = 0;//采购数量
			var ratioVal = trim($(this).val());//占比
			
			var unitPriceStrClass = $(this).attr("class");
			var calculateStatus = 0;
			for(var i = 0;i < unitPriceStrClass.length;i++) {
				if(unitPriceStrClass.charAt(i) > 0 && unitPriceStrClass.charAt(i) < 10) {
					calculateStatus = parseInt(unitPriceStrClass.substr(i,unitPriceStrClass.length));
					break;
				}
			}
			
			$("[class^='unitPrice']").each(function() {
				var currentStatus = 0;
				var currentStr = $(this).attr("class");
				for(var i = 0;i < currentStr.length;i++) {
					if(currentStr.charAt(i) > 0 && currentStr.charAt(i) < 10) {
						currentStatus = parseInt(currentStr.substr(i,currentStr.length));
						break;
					}
				}
				if(currentStatus == calculateStatus) {
					unitPrice = trim($(this).text());
				}
			});
			
			$("[class^='purchaseCount']").each(function() {
				var currentStatus = 0;
				var currentStr = $(this).attr("class");
				for(var i = 0;i < currentStr.length;i++) {
					if(currentStr.charAt(i) > 0 && currentStr.charAt(i) < 10) {
						currentStatus = parseInt(currentStr.substr(i,currentStr.length));
						break;
					}
				}
				if(currentStatus == calculateStatus) {
					purchaseCount = trim($(this).text());
				}
			});
			var calResult = parseInt(purchaseCount) * parseInt(unitPrice) * (parseInt(ratioVal)/100);
			//生成总价为万元保留4位
			calResult = (calResult / 1000).toFixed(4);
			$(this).parent().next().text(calResult);
		});
		
	}
</script>

<body>

	<h2 class="list_title mb0 clear">已中标供应商</h2>

	<c:if test="${view != 1 }">
		<div class="col-md-12 col-xs-12 col-sm-12 mt10 p0">
			<!-- <button class="btn " onclick="save();" type="button">确定</button>
			<button class="btn " onclick="del(this);" type="button">移除</button> -->
		</div>
	</c:if>
	<div class="content table_box pl0">
		<table
			class="table table-bordered table-condensed table-hover table-striped">
			<thead>
				<tr class="info">
					<!-- 
					<c:if test="${view != 1}">
						<th class="w30"><input id="checkAll" type="checkbox"
							onclick="selectAll()" /></th>
					</c:if> -->
					<th class="w200">供应商名称</th>
					<!--               <th class="w100">参加时间</th> -->
					<th style="width: 110px;">&nbsp;总报价&nbsp;（万元）</th>
					<th style="width: 50px;">总得分</th>
					<th style="width: 20px;">排名</th>
					<c:if test="${view == 1}">
						<th style="width: 50px;">中标状态</th>
					</c:if>
					<th class="w50">占比（%）</th>
					<th class="w100">实际成交总价（万元）</th>
					<th style="width: 80px;">操作</th>
				</tr>
			</thead>
			<c:forEach items="${supplierCheckPass}" var="checkpass"
				varStatus="vs">
				<tr id="${checkpass.id}">
					<!-- 
					<c:if test="${view != 1}">
						<td class="tc opinter"><c:if
								test="${checkpass.isDeleted == 0}">

								<input onclick="check('${checkpass.id}');"
									id="rela${checkpass.id}" type="checkbox" name="chkItem"
									value="${checkpass.id}" />

							</c:if></td>
					</c:if> -->
					<td class="opinter" title="${checkpass.supplier.supplierName }">
						<input type="hidden" class="supplierId" value="${checkpass.supplier.id }"/>
						<span onclick="ycDiv(this,'${checkpass.id}')"
						class="count_flow shrink hand"></span> <c:choose>
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
					<td class="tc opinter" onclick="">${(vs.index+1)}</td>
					<c:if test="${view == 1}">
						<c:if test="${checkpass.isWonBid != 1}">
							<td class="tc opinter">未中标</td>
						</c:if>
						<c:if test="${checkpass.isWonBid == 1}">
							<td class="tc opinter">已中标</td>
						</c:if>
					</c:if>
					<td class="tc opinter" title="双击单元格 修改占比" id="priceRatio" ondblclick="changePriceRatio(this)">
						<input type="text" class="forChangeRatio${(vs.index+1)}" onfocus="priceRatioFocus(this)" onchange="priceRatioConfirm(this)" title="unchanged${(vs.index+1) }" style="width: 32px;height: 26px;text-align: center;border: none;margin-top: 8px;" readonly="readonly" value="${checkpass.priceRatio}"/>
					</td>
					<c:if test="${quote==0 }">
					   
						<td class="tc opinter">
						<!-- 
						<input type="text" name="singQuote" id="singQuote" class="singQuote${(vs.index+1)}" value="${checkpass.wonPrice }" />
						 点击生成-->
						  ${checkpass.money}
						</td>
					</c:if>
					<c:if test="${quote==1 }">
						<td class="tc opinter" id="singQuote">${checkpass.totalPrice*checkpass.priceRatio }
							<input type="hidden" class="singQuote${(vs.index+1)}" name="singQuote" id="singQuotehhide">
						</td>
					</c:if>

					<td class="tc opinter"><button class="btn btn-windows add"
							onclick="InputBD(this);" type="button">录入标的</button></td>
				</tr>
				<tr class="tc hide">
					<td colspan="10">
						<table
							class="table table-bordered table-condensed table-hover table-striped">
							<tr class="tc ">
								<th class="hide"></th>
								<th class="w30">序号</th>
								<th class="150">物资名称</th>
								<th>规格型号</th>
								<th>质量技术标准</th>
								<th>计量单位</th>
								<th>采购数量</th>
								<th>单价（元）</th>
								<!-- 
								<c:if test="${quote == 1 }">
									<th>报价（万元）</th>
								</c:if> -->
							</tr>
							<c:forEach items="${detailList }" var="detail" varStatus="p">
								<!--
								<tr name="detail${checkpass.id}"
									id="${checkpass.id}${detail.id}" class="tc hide">
									<td class="hide"><input type="checkbox"
										value="${detail.id}"
										onclick="associateSelected('${detail.id}',this,'${checkpass.id}')"
										name="associate${checkpass.id}" /></td>
									<td>${detail.serialNumber}</td>
									<td title="${detail.goodsName}"><c:choose>
											<c:when test="${fn:length(detail.goodsName ) > 20}">
                           ${fn:substring(detail.goodsName  , 0, 20)}......
                       </c:when>
											<c:otherwise>
                                ${detail.goodsName }
                        </c:otherwise>
										</c:choose></td>
									<td class="w150" title=" ${detail.stand }"><c:choose>
											<c:when test="${fn:length(detail.stand) > 20}">
                           ${fn:substring(detail.stand , 0, 20)}......
                       </c:when>
											<c:otherwise>
                           ${detail.stand }
                        </c:otherwise>
										</c:choose></td>
									<td title="${detail.qualitStand }"><c:choose>
											<c:when test="${fn:length(detail.qualitStand ) > 20}">
                           ${fn:substring(detail.qualitStand , 0, 20)}......
                       </c:when>
											<c:otherwise>
                            ${detail.qualitStand }
                        </c:otherwise>
										</c:choose></td>
									<td>${detail.item }</td>
									<%-- 	                    <c:if test="${quote==0 }"> --%>
									<%-- 	                      <td>${checkpass.wonPrice }</td> --%>
									<%-- 	                    </c:if> --%>
									<td id="purchaseCount">${detail.purchaseCount}</td>
									<td>${detail.price }</td>
									 
									<c:if test="${quote == 1 }">
										<td id="Quotedamount"><c:forEach var="listQuote"
												items="${checkpass.supplier.listQuote }">
												<c:if test="${detail.id == listQuote.productId }">
	                         ${listQuote.quotePrice} 
	                       </c:if>
											</c:forEach></td>
									</c:if> 
								</tr>-->
								<c:forEach items="${detail.subjectList }" var="subject" varStatus="s">
								
								<c:if test="${subject.supplierId==checkpass.supplier.id }">
								<tr class="tc ">
									<td>${s.index + 1 }
									</td>
									<td>${subject.goodsName }
									</td>
									<td>${subject.stand }
									</td>
									<td>${subject.qualitStand }
									</td>
									<td>${subject.item }
									</td>
									<td class="purchaseCount${(vs.index+1)}">${subject.purchaseCount }
									</td>
									<td class="unitPrice${(vs.index+1)}">${subject.unitPrice }
									</td>
									<!-- 
									<td>${subject.goodsName }
									</td>
									 -->
								</tr>
								</c:if>
								</c:forEach>
							</c:forEach>
						</table>
					</td>
				</tr>
				
			</c:forEach>

		</table>
		<div class="col-md-12 tc">
			<c:if test="${quote == 0 }">
			<button class="btn btn-windows add" onclick="ratioPrice()"
				type="button">生成总价</button>
			</c:if>
			<button class="btn btn-windows back" onclick="history.go(-1)"
				type="button">返回</button>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		
	});
</script>

</html>