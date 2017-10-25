<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<base href="${pageContext.request.contextPath}/">

<title>录入标的</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

</head>
<script type="text/javascript">
	/** 全选全不选 */
	function selectAll() {
		var checklist = document.getElementsByName("chkItem");
		var checkAll = document.getElementById("checkAll");
		if (checkAll.checked) {
			for (var i = 0; i < checklist.length; i++) {
				checklist[i].checked = true;
			}
		} else {
			for (var j = 0; j < checklist.length; j++) {
				checklist[j].checked = false;
			}
		}
	}

	/** 单选 */
	function check() {
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
	}
	/**展示信息**/
	function view(id) {
		window.location.href = "${pageContext.request.contextPath}/winningSupplier/packageSupplier.html?packageId="
				+ id
				+ "&&flowDefineId=${flowDefineId}&&projectId=${projectId}&&view=1";
	}
	var addFlag = 0;
	
	//明细的复选框只能选一个
	$(function() {
		$(".ck").click(function() {
			$(".ck").toggle(
					function() {
						$(".ck").each(function() {
							$(this).attr("checked",false);
						});
						$(this).prop("checked",true);
					},
					function() {
						$(".ck").each(function() {
							$(this).attr("checked",false);
						});
						$(this).prop("checked",false);
					},true
				);
		});
	});
	
	var tempStrForAdd = 3;
	//新增行
	function add(btns) {
		var checkboxStatus = false,detailId,appendObj;
		
		/**	这里注释掉当时明细可以添加多个标的
		$(".ck").each(function() {
			if($(this).is(":checked")) {
				detailId = $(this).attr("title");
				checkboxStatus = true;
				appendObj = $(this);
			}
		});
		
		if(!checkboxStatus) {
			layer.alert("请选择要添加对应的明细");
		} else if(checkboxStatus) {
			var tempFlag = 0;
			$(".ck").each(function() {
				if($(this).is(":checked")) {
					tempFlag += 1;
				}
			});
			if(tempFlag > 1) {
				layer.alert("只能选择一条");
			} else {
				appendObj.parent().parent().after('<tr class="tc"><td><input style="display:none;" type="checkbox" class="kkkkk" title="'+detailId+'"/></td><td><input type="hidden" value="'+detailId+'"/><input name="goodsName" type="text"></td><td><input name="stand" type="text"></td><td><input name="qualitStand" type="text"></td><td><input name="item" type="text"></td><td><input name="purchaseCount"  onkeyup="this.value=this.value.replace(/\\D/g,' + "''" + ')" type="text"></td><td><input name="unitPrice" onkeyup="this.value=this.value.replace(/\\D/g,' + "''" + ')" type="text"></td></tr>');//.replace(/\D/g,'')
				tempStrForAdd = 1;
			}
		}
		**/
		var tableObj = $("#forAppendTr");
		//tableObj.show();
		var appendStr = '<tr class="tc">'
			+ '<td><input type="checkbox" name="appendCK"/></td>'
			+ '<td><input type="text" class="tc w50" name="serialNumber"></td>'
			+ '<td><input type="text" name="goodsName"></td>'
			+ '<td><input type="text" name="stand"></td>'
			+ '<td><input type="text" name="trademark"></td>'
			+ '<td><input type="text" name="qualitStand"></td>'
			+ '<td><input type="text" name="item"></td>'
			+ '<td><input type="text" name="purchaseCount" onkeyup="this.value=this.value.replace(/\\D/g,' + "''" + ')"></td>'
			+ '<td><input type="text" name="unitPrice"></td>'
		+ '</tr>';
		tableObj.append(appendStr);
	}
	//删除选中新增标的
	function del(obj) {
		//删除选中行
		$("input[name='appendCK']:checked").each(function() {
			$(this).parent().parent().remove();
		});
		//标的的行删除完，把此table隐藏
		/**
		if($("input[name='appendCK']").size() == 0) {
			$("#appendTable").hide();
		}
		**/
	}
	
	function checkTotal(obj){
	   var num = $(obj).val();
	   if(num == 0){
	     $("input[name='unitPrice']").val("");
	   }
	}

	//保存
	function saveOrUpdate(btns) {
		var btnVal = $(btns).html();
		
		if(btnVal == "保存" && tempStrForAdd == 3) {
			var sid = "${supplierId}";
			var subjectList = [];
			var validateFlag = "pass";
			//‘正规’标的信息循环放入数组
			$("input[name='ck']").each(function(index , element) {
				/****/
				if($(this).parent().parent().find(":input[name='goodsName']").val() == null || $(this).parent().parent().find(":input[name='goodsName']").val() == "") {
					validateFlag = "goodsName";
				}
				/* if($(this).parent().parent().find(":input[name='stand']").val() == null || $(this).parent().parent().find(":input[name='stand']").val() == "") {
					validateFlag = "stand";
				}
				if($(this).parent().parent().find(":input[name='qualitStand']").val() == null || $(this).parent().parent().find(":input[name='qualitStand']").val() == "") {
					validateFlag = "qualitStand";
				}
				if($(this).parent().parent().find(":input[name='purchaseCount']").val() == null || $(this).parent().parent().find(":input[name='purchaseCount']").val() == "") {
					validateFlag = "purchaseCount";
				} */
				if($(this).parent().parent().find(":input[name='unitPrice']").val() == null || $(this).parent().parent().find(":input[name='unitPrice']").val() == "") {
					validateFlag = "unitPrice";
				}
				
				var data = {
					detailId : $(this).attr("title"),
					serialNumber : $(this).parent().parent().find(":input[name='serialNumber']").val(),
					supplierId : sid,
					packageId : "${pid}",
					goodsName : $(this).parent().parent().find(":input[name='goodsName']").val(),
					stand : $(this).parent().parent().find(":input[name='stand']").val(),
					qualitStand : $(this).parent().parent().find(":input[name='qualitStand']").val(),
					item : $(this).parent().parent().find(":input[name='item']").val(),
					trademark : $(this).parent().parent().find(":input[name='trademark']").val(),
					purchaseCount : $(this).parent().parent().find(":input[name='purchaseCount']").val(),
					unitPrice : $(this).parent().parent().find(":input[name='unitPrice']").val()
				};
				subjectList.push(data);
			});
			//附赠标的信息循环放入同一个数组，此标的只关联供应商
			$("input[name='appendCK']").each(function(index , element) {
				if($(this).parent().parent().find(":input[name='goodsName']").val() == null || $(this).parent().parent().find(":input[name='goodsName']").val() == "") {
					validateFlag = "goodsName";
				}
				/* if($(this).parent().parent().find(":input[name='stand']").val() == null || $(this).parent().parent().find(":input[name='stand']").val() == "") {
					validateFlag = "stand";
				}
				if($(this).parent().parent().find(":input[name='qualitStand']").val() == null || $(this).parent().parent().find(":input[name='qualitStand']").val() == "") {
					validateFlag = "qualitStand";
				}
				if($(this).parent().parent().find(":input[name='purchaseCount']").val() == null || $(this).parent().parent().find(":input[name='purchaseCount']").val() == "") {
					validateFlag = "purchaseCount";
				} */
				if($(this).parent().parent().find(":input[name='unitPrice']").val() == null || $(this).parent().parent().find(":input[name='unitPrice']").val() == "") {
					validateFlag = "unitPrice";
				}
				
				var data = {
					serialNumber : $(this).parent().parent().find(":input[name='serialNumber']").val(),
					supplierId : sid,
					packageId : "${pid}",
					goodsName : $(this).parent().parent().find(":input[name='goodsName']").val(),
					stand : $(this).parent().parent().find(":input[name='stand']").val(),
					qualitStand : $(this).parent().parent().find(":input[name='qualitStand']").val(),
					item : $(this).parent().parent().find(":input[name='item']").val(),
					trademark : $(this).parent().parent().find(":input[name='trademark']").val(),
					purchaseCount : $(this).parent().parent().find(":input[name='purchaseCount']").val(),
					unitPrice : $(this).parent().parent().find(":input[name='unitPrice']").val()
				};
				subjectList.push(data);
			});
			if(validateFlag == "pass") {
				layer.confirm("保存后不可以修改",{
					title : '提示',
					offset : ['222px','360px'],
					shade : 0.01
				},function(index) {
					layer.close(index);
					$.ajax({
						url : "${pageContext.request.contextPath}/theSubject/batchInsert.do",
						data : JSON.stringify(subjectList),
						type : "post",
						contentType:"application/json",
						success : function(obj) {
							layer.alert(
								'添加成功',
								{
									btn:['确定']
								},
								function() {
									window.location.href = "${pageContext.request.contextPath}/winningSupplier/packageSupplier.html?inputSubjectBtn=no&packageId=" + "${packageId}" + "&&flowDefineId=${flowDefineId}&&pid=${pid}&&projectId=${projectId}&&priceRatios=priceRatios";
								}
							);
						},
						error : function(obj) {
							layer.alert("添加失败");
						}
					});
				});
			} else {
				layer.alert("单价不可以为空");
			}
		} else if(btnVal == "修改") {
			
		} else if(btnVal == "保存" && tempStrForAdd == 0) {
			layer.alert("请先添加一条标的");
		}
		
	}
</script>

<body>
	<h2 class="list_title mb0 clear">标的录入</h2>
	<div style="margin-top: 10px;">
		<button class="btn btn-windows add "
			onclick="add(this);" type="button">新增</button>
		<button class="btn btn-windows add "
			onclick="del(this);" type="button">删除</button>
	</div>
	<div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto pr mt20">
		<table class="table table-hover table-bordered" id="forAppendTr">
			<tr class="tc">
				<th class="w30"><!-- -->
				<input type="checkbox" id="checkAll" disabled="disabled" onclick="selectAll()" /><!-- -->
				</th><!-- -->
				<!--<th class="w30">序号</th> -->
				<th class="tc w50"><input type="hidden" name="cks"/>编号</th>
				<th>物资名称</th>
				<th>规格型号</th>
				<th>品牌商标</th>
				<th>质量技术标准</th>
				<th>计量单位</th>
				<th>采购数量</th>
				<th>单价（元）</th>
			</tr>
			<c:forEach items="${detailList }" var="detail" varStatus="p">
				<tr class="tc ">
					<%--                       <td class=""> <input type="checkbox" value="${pack.id }" name="chkItem" onclick="check()"></td> --%>
					<%--                       <td>${detail.serialNumber}</td> --%>
					<td>
					<input type="checkbox" id="checkAll" disabled="disabled" />
					</td>
					<td class="tc w50" title="${detail.serialNumber }">
						<input type="hidden" name="ck" class="ck" title="${detail.id }"/>
						<input type="text"
						name="serialNumber" class="tc w50" value="${detail.serialNumber }"></td>
					<td title="${detail.goodsName}"><input type="hidden"
						name="detailId" value="${detail.id }"> <input
						type="hidden" name="detailId" value="${detail.id }"> <input
						type="hidden" name="detailId" value="${detail.id }"> <input
						type="text" name="goodsName"
						value="${detail.goodsName }"></td>
					<td title=" ${detail.stand }"><input type="text"
						name="stand" value=" ${detail.stand }"></td>
					<td title=""><input type="text"
						name="trademark" value=""></td>
					<td title="${detail.brand }"><input type="text"
						name="qualitStand" value="${detail.brand }"></td>
					<td><input type="text" name="item" readonly="readonly"
						value="${detail.item }"></td>
					<td id="purchaseCount"><input type="text" name="purchaseCount"
						value="<fmt:formatNumber type="number" value="${detail.purchaseCount*pass.priceRatio/100}" pattern="0.00" maxFractionDigits="2"/>" readonly="readonly"></td>
					<td>
					<%-- <c:if test="${quote == 0 }"> --%>
					
					<input type="text" name='unitPrice' value="${detail.budget }" onkeyup="this.value=this.value.replace(/\D/g,'')" onblur="checkTotal(this)">
					<%-- </c:if>
					<c:if test="${quote == 1 }">
					<input type="text" name='unitPrice' value="${detail.price}" readonly="readonly"/>
					</c:if> --%>
					</td>
				</tr>
				<!-- 
				<c:forEach items="${detail.subjectList }" var="subject" varStatus="s">
					<tr class="tc ">
						<td>${s.index + 1 }
						</td>
						<td title="${subject.goodsName }">
						<input type="text" value="${subject.goodsName }"/>
						</td>
						<td>${subject.stand }
						</td>
						<td>${subject.qualitStand }
						</td>
						<td>${subject.item }
						</td>
						<td>${subject.purchaseCount }
						</td>
						<td>${subject.unitPrice }
						</td>
						
						<td>${subject.goodsName }
						</td>
						
					</tr> 
				</c:forEach>-->
			</c:forEach>
		</table>
		<table 
			class="table table-bordered table-condensed table_input table_input" 
			id="appendTable"
			style="display: none;">
			<tr class="tc">
				<th>
				<input type="checkbox" disabled="disabled" name="ck111s"/>
				</th>
				<th width="20%">编号</th>
				<th width="20%">物资名称</th>
				<th width="20%">规格型号</th>
				<th width="20%">品牌商标</th>
				<th width="20%">质量技术标准</th>
				<th>计量单位</th>
				<th>采购数量</th>
				<th>单价（元）</th>
			</tr>
		</table>
	</div>
	<div style="text-align: center;">
		<button class="btn btn-windows add "
			onclick="saveOrUpdate(this);" type="button">保存</button>
		<button class="btn btn-windows back" onclick="history.go(-1)"
				type="button">返回</button>
	</div>
</body>

</html>