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
	
	var tempStrForAdd = 0;
	//新增行
	function add(btns) {
		var checkboxStatus = false,detailId,appendObj;
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
				appendObj.parent().parent().after('<tr class="tc"><td><input style="display:none;" type="checkbox" class="kkkkk" title="'+detailId+'"/></td><td><input type="hidden" value="'+detailId+'"/><input name="goodsName" type="text"></td><td><input name="stand" type="text"></td><td><input name="qualitStand" type="text"></td><td><input name="item" type="text"></td><td><input name="purchaseCount" type="text"></td><td><input name="unitPrice" type="text"></td></tr>');
				tempStrForAdd = 1;
			}
			
		}
	}

	//保存
	function saveOrUpdate(btns) {
		var btnVal = $(btns).html();
		
		if(btnVal == "保存" && tempStrForAdd == 1) {
			var sid = "${supplierId}";
			var subjectList = [];
			$(".kkkkk").each(function(index , element) {
				var data = {
					detailId : $(this).attr("title"),
					supplierId : sid,
					goodsName : $(this).parent().parent().find(":input[name='goodsName']").val(),
					stand : $(this).parent().parent().find(":input[name='stand']").val(),
					qualitStand : $(this).parent().parent().find(":input[name='qualitStand']").val(),
					item : $(this).parent().parent().find(":input[name='item']").val(),
					purchaseCount : $(this).parent().parent().find(":input[name='purchaseCount']").val(),
					unitPrice : $(this).parent().parent().find(":input[name='unitPrice']").val()
				};
				subjectList.push(data);
			});
			$.ajax({
				url : "${pageContext.request.contextPath}/theSubject/batchInsert.do",
				data : JSON.stringify(subjectList),
				type : "post",
				contentType:"application/json",
				success : function(obj) {
					layer.confirm(
						'添加成功',
						{
							btn:['确定']
						},
						function() {
							window.history.go(-1);
						}
					);
					//layer.alert("添加成功");
					
				},
				error : function(obj) {
					layer.alert("添加失败");
				}
			});
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
			onclick="add(this);" type="button">录入标的</button>
	</div>
	<div class="content table_box pl0">
		<table class="table table-bordered table-condensed table_input left_table table_input">
			<tr class="tc">
				<!-- 		                <th class="w30"> -->
				<!-- 		                  <input type="checkbox" id="checkAll"  onclick="selectAll()" /> -->
				<!-- 		                </th> -->
				<!--                     <th class="w30">序号</th> -->
				<th>
				<input type="checkbox" name="cks"/>
				</th>
				<th width="20%">物资名称</th>
				<th width="20%">规格型号</th>
				<th width="20%">质量技术标准</th>
				<th>计量单位</th>
				<th>采购数量</th>
				<th>单价（元）</th>
			</tr>
			<c:forEach items="${detailList }" var="detail" varStatus="p">
				<tr class="tc ">
					<%--                       <td class=""> <input type="checkbox" value="${pack.id }" name="chkItem" onclick="check()"></td> --%>
					<%--                       <td>${detail.serialNumber}</td> --%>
					<td >
						<input type="checkbox" name="ck" class="ck" title="${detail.id }"/>
					</td>
					<td title="${detail.goodsName}"><input type="hidden"
						name="detailId" value="${detail.id }"> <input
						type="hidden" name="detailId" value="${detail.id }"> <input
						type="hidden" name="detailId" value="${detail.id }"> <input
						type="text" disabled="disabled" name="goodsName"
						value="${detail.goodsName }"></td>
					<td class="w150" title=" ${detail.stand }"><input type="text"
						name="stand" value=" ${detail.stand }"></td>
					<td title="${detail.qualitStand }"><input type="text"
						name="qualitStand" value="${detail.qualitStand }"></td>
					<td><input type="text" name="qualitStand"
						value="${detail.item }"></td>
					<td id="purchaseCount"><input type="text" name="item"
						value="${detail.item}"></td>
					<td><input type="text" name="price" value="${detail.price }"></td>
				</tr>
				
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
						<!-- 
						<td>${subject.goodsName }
						</td>
						 -->
					</tr>
				</c:forEach>
			</c:forEach>
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