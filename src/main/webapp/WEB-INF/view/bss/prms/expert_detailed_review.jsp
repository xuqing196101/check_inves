<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${pageContext.request.contextPath}/">

<title>各包分配专家</title>
<script type="text/javascript">
	$(function() {
		var packageId = $("input[name='packageId']").val();
		var flag = $("#flag").val();
		if (flag == "success") {
			layer.msg("分配成功。", {
				offset : [ '285px', '550px' ]
			});
		} else if (flag == "error") {
			layer.alert("错误操作！请重新选择！", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		}
		$("#flag").val("");
	})

	/** 全选全不选 */
	function selectAll() {
		var checklist = document.getElementsByName("chkItem");
		var checkAll = document.getElementById("checkAll");
		if (checkAll.checked) {
			for ( var i = 0; i < checklist.length; i++) {
				checklist[i].checked = true;
			}
		} else {
			for ( var j = 0; j < checklist.length; j++) {
				checklist[j].checked = false;
			}
		}
	}
	function submit1(obj) {
		//获取复选框
		var checkbox = $(obj).prev().prev();
		//获取复选框的name
		var checkboxName = $(checkbox).prop("name");
		var count = 0;
		var ids = document.getElementsByName("" + checkboxName + "");
		for (i = 0; i < ids.length; i++) {
			if (document.getElementsByName("" + checkboxName + "")[i].checked) {
				var id = document.getElementsByName("" + checkboxName + "")[i].value;
				count++;
			}
		}
		//获取当前的form对象
		var parent = obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
		//获取按钮上面的下拉框对象
		var select = $(obj).prev();
		if ($(select).val() == "0") {
			layer.alert("请指定一名组长", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
			return;
		}
		if (count > 0) {
			parent.submit();
			// return true;
		} else {
			layer.alert("请选择一名专家", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
			return;
		}

	}
	//下拉框校验
	function selectClick(obj) {
		//下拉框选中的值
		var selectValue = $(obj).val();
		//获取复选框
		var checkbox = $(obj).prev();
		//获取复选框的name
		var checkboxName = $(checkbox).prop("name");
		//定义变量
		var flag;
		//根据复选框的name 获取选中复选框的value值
		$('input[name="' + checkboxName + '"]:checked').each(function() {
			//判断下拉框的选的值是否复选框也选中
			if (selectValue == $(this).val()) {
				flag = 1;
			}
		});
		if (flag != 1) {
			layer.alert("请选择勾选的专家为组长！", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
			$(obj).val("0");
		}
	}
	/** 全选全不选 */
	function selectAll(obj) {
		var table = obj.parentNode.parentNode.parentNode.parentNode;
		var checkbox = $(table).find("input[type='checkbox']");
		var checklist = document.getElementsByName("chkItem");
		if (obj.checked) {
			for ( var i = 0; i < checkbox.length; i++) {
				checkbox[i].checked = true;
			}
		} else {
			for ( var j = 0; j < checkbox.length; j++) {
				checkbox[j].checked = false;
			}
		}
	}
	//符合汇总
	function gather(obj) {
		//得到点击坐标。
		var x, y;
		oRect = obj.getBoundingClientRect();
		x = oRect.left;
		y = oRect.top;
		var table = obj.parentNode.parentNode.parentNode.parentNode;
		var checkbox = $(table).find("input[name='chkItem']");
		var id;
		var count = 0;
		$(checkbox).each(function() {

			if ($(this).is(":checked")) {
				id = $(this).val();
				count++;
			}
		});
		if (count == 1) {
			var index = layer
					.confirm(
							'确定汇总吗?',
							{
								icon : 3,
								title : '提示'
							},
							function(index) {
								var value = id.split(",");
								var projectId = "${project.id}";
								$
										.ajax({
											url : "${pageContext.request.contextPath}/packageExpert/gather.html?projectId="
													+ projectId
													+ "&expertId="
													+ value[0]
													+ "&packageId="
													+ value[1],
											success : function(data) {
												layer.alert(data, {
													offset : [ y, x ],
													shade : 0.01
												});
											}
										});
								layer.close(index);
							});
		} else if (count > 1) {
			layer.alert("只能选择一个", {
				offset : [ y, x ],
				shade : 0.01
			});
		} else {
			layer.alert("请选择一条", {
				offset : [ y, x ],
				shade : 0.01
			});
		}

	}
	//退回
	function isBack(obj) {
		//得到点击坐标。
		var x, y;
		oRect = obj.getBoundingClientRect();
		x = oRect.left;
		y = oRect.top;
		var table = obj.parentNode.parentNode.parentNode.parentNode;
		var checkbox = $(table).find("input[name='chkItem']");
		var id;
		var count = 0;
		$(checkbox).each(function() {

			if ($(this).is(":checked")) {
				id = $(this).val();
				count++;
			}
		});
		if (count == 1) {
			var index = layer
					.confirm(
							'确定退回吗?',
							{
								icon : 3,
								title : '提示'
							},
							function(index) {
								var value = id.split(",");
								var projectId = "${project.id}";
								$
										.ajax({
											url : "${pageContext.request.contextPath}/packageExpert/isBack.html?projectId="
													+ projectId
													+ "&expertId="
													+ value[0]
													+ "&packageId="
													+ value[1],
											success : function(data) {
												if (data == '0') {
													layer.alert("不能退回！", {
														offset : [ y, x ],
														shade : 0.01
													});
												} else {
													layer.alert("已退回！", {
														offset : [ y, x ],
														shade : 0.01
													});
													setTimeout(function() { //使用  setTimeout（）方法设定定时2000毫秒
														window.location
																.reload();//页面刷新
													}, 1000);
												}

											}
										});
								layer.close(index);
							});
		} else if (count > 1) {
			layer.alert("只能选择一个", {
				offset : [ y, x ],
				shade : 0.01
			});
		} else {
			layer.alert("请选择一条", {
				offset : [ y, x ],
				shade : 0.01
			});
		}

	}

	/** 全选全不选 */
	function selectAllExp() {
		var checklist = document.getElementsByName("chkItemExp");
		var checkAll = document.getElementById("checkAllExp");
		if (checkAll.checked) {
			for ( var i = 0; i < checklist.length; i++) {
				checklist[i].checked = true;
			}
		} else {
			for ( var j = 0; j < checklist.length; j++) {
				checklist[j].checked = false;
			}
		}
	}

	/** 单选 */
	function checkExp() {
		var count = 0;
		var checklist = document.getElementsByName("chkItem");
		var checkAll = document.getElementById("checkAll");
		for ( var i = 0; i < checklist.length; i++) {
			if (checklist[i].checked == false) {
				checkAll.checked = false;
				break;
			}
			for ( var j = 0; j < checklist.length; j++) {
				if (checklist[j].checked == true) {
					checkAll.checked = true;
					count++;
				}
			}
		}
	}
	/**重置密码*/
	function resetPwd() {
		var id = [];
		$('input[name="chkItemExp"]:checked').each(function() {
			id.push($(this).val());
		});
		if (id.length == 1) {
			$
					.ajax({
						type : "GET",
						url : "${pageContext.request.contextPath}//ExpExtract/resetPwd.do?eid"
								+ id,
						dataType : "json",
						success : function(data) {
							if ("sccuess" == data) {
								layer.alert("重置成功！默认密码：123456", {
									offset : [ '222px', '390px' ],
									shade : 0.01
								});
							} else {
								layer.alert("重置失败！请尝试重新重置", {
									offset : [ '222px', '390px' ],
									shade : 0.01
								});
							}
						}
					});
		} else if (id.length > 1) {
			layer.alert("只能选择一个", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		} else {
			layer.alert("请选择需要重置密码的专家", {
				offset : [ '222px', '390px' ],
				shade : 0.01
			});
		}
	}
	function addexp() {
		layer
				.open({
					type : 2, //page层
					area : [ '80%', '50%' ],
					title : '添加临时专家',
					closeBtn : 1,
					shade : 0.01, //遮罩透明度
					shadeClose : true,
					offset : '30px',
					move : false,
					content : '${pageContext.request.contextPath}/ExpExtract/showTemporaryExpert.html?projectId=${project.id}'
				});
	}
	function showViewByExpertId(packageId, obj, i) {
		var x, y;
		oRect = obj.getBoundingClientRect();
		x = oRect.left;
		y = oRect.top;
		var expertId;
		var count = 0;
		$("input[name='expertView_" + i + "']").each(function(i, result) {
			if (result.checked == true) {
				expertId = result.value;
				count++;
			}
		});
		if (count > 0) {
			window.location.href = '${pageContext.request.contextPath}/packageExpert/showViewByExpertId.html?expertId='
					+ expertId
					+ '&packageId='
					+ packageId
					+ '&projectId=${project.id}';
		} else {
			layer.alert("请先选择一项！", {
				offset : [ y - 100, x + 200 ],
				shade : 0.01
			});
		}
	}
	function showViewBySupplierId(packageId, obj, i) {
		var x, y;
		oRect = obj.getBoundingClientRect();
		x = oRect.left;
		y = oRect.top;
		var supplierId;
		var count = 0;
		$("input[name='supplierView_" + i + "']").each(function(i, result) {
			if (result.checked == true) {
				supplierId = result.value;
				count++;
			}
		});
		var expertIds;
		var expertId = document.getElementsByName("expertId_" + i);
		for ( var j = 0; j < expertId.length; j++) {
			expertIds = expertIds + expertId[j].value + ",";
		}
		if (count > 0) {
			window.location.href = '${pageContext.request.contextPath}/packageExpert/showViewBySupplierId.html?supplierId='
					+ supplierId
					+ '&packageId='
					+ packageId
					+ '&projectId=${project.id}&expertIds=' + expertIds;
		} else {
			layer.alert("请先选择一项！", {
				offset : [ y - 30, x - 350 ],
				shade : 0.01
			});
		}
	}
	//查看供应商报价
	function supplierView(supplierId) {
		var projectId = $("#projectId").val();
		location.href = "${pageContext.request.contextPath}/packageExpert/supplierQuote.html?projectId="
				+ projectId + "&supplierId=" + supplierId;
	}

	//评分确认或退回
	function querenOrTuiHUi(obj, packageId, supplierId, scoreModelId, flag) {
		//得到点击坐标。
		var x, y;
		oRect = obj.getBoundingClientRect();
		x = oRect.left - 400;
		y = oRect.top - 100;
		var projectId = $("#projectId").val();
		$
				.ajax({
					url : '${pageContext.request.contextPath}/packageExpert/isBackScore.html',
					data : {
						'packageId' : packageId,
						'projectId' : projectId,
						'supplierId' : supplierId,
						'scoreModelId' : scoreModelId,
						'flag' : flag
					},
					success : function(data) {
						if (data == "tuihui") {
							layer.alert("不能退回！", {
								offset : [ y, x ],
								shade : 0.01
							});
						} else if (data == "success") {
							layer.alert("确认成功！", {
								offset : [ y, x ],
								shade : 0.01
							});
						} else if (data == "tuihuisuccess") {
							layer.alert("退回成功！", {
								offset : [ y, x ],
								shade : 0.01
							});
						} else {
							layer.alert("不能确认！", {
								offset : [ y, x ],
								shade : 0.01
							});
						}
					},
					error : function(data) {

					}
				});
	}

	//汇总判断每一个table中的每一行每一列的值是否相等
	function scoreTotal(obj, packageId, projectId) {
		var expertId = $("#expertId").val();
		//得到点击坐标。
		var x, y;
		oRect = obj.getBoundingClientRect();
		x = oRect.left - 400;
		y = oRect.top - 100;
		//查询form下的所有table
		var flag = false;
		var count;
		var coun = 0;
		var trFlag = 0;
		var table = $("#formTable").find("table");
		if (table.length <= 0) {
			layer.alert("不能汇总", {
				offset : [ y, x ],
				shade : 0.01
			});
			return;
		}
		$.each(table, function(a, result) {
			var tr = $(result).find("tr:not(:first)");
			if (tr.length == 0) {
				trFlag++;
			}
			$.each(tr, function(b, trResult) {
				var td = $(trResult).find("td");
				$.each(td, function(i, tdResult) {
					i = i + 1;
					if (i != 1 && i != td.length) {
						if (i == 2) {
							count = tdResult.text;
							if (count != null) {

							} else {
								coun++;
								return false;
							}
						} else {
							var tdValue = tdResult.text;
							if (tdValue != null) {

							} else {
								coun++;
								return false;
							}
							if (count == tdResult.text) {

							} else {
								coun++;
								$(this).css('color', 'red');
							}
						}
					}
				});
			});
		});
		if (coun > 0) {
			layer.alert("还有未评审项,或评审结果不统一", {
				offset : [ y, x ],
				shade : 0.01
			});
		} else if (trFlag > 0) {
			layer.alert("不能汇总", {
				offset : [ y, x ],
				shade : 0.01
			});
		} else {
			$
					.ajax({
						url : '${pageContext.request.contextPath}/packageExpert/scoreTotal.do',
						data : {
							"packageId" : packageId,
							"projectId" : projectId,
							"expertId" : expertId
						},
						success : function() {
							layer.alert("已汇总", {
								offset : [ y, x ],
								shade : 0.01
							});
						}
					});
		}
	}

	//查看专家对所有供应商的初审明细
	function viewByExpert() {
		var expertId = $('input:radio[name="firstAuditByExpert"]:checked')
				.val();
		layer
				.open({
					type : 2, //page层
					area : [ '500px', '400px' ],
					title : '修改角色',
					closeBtn : 1,
					shade : 0.01, //遮罩透明度
					moveType : 1, //拖拽风格，0是默认，1是传统拖动
					shift : 1, //0-6的动画形式，-1不开启
					offset : '180px',
					shadeClose : false,
					content : '${pageContext.request.contextPath}/packageExpert/viewByExpert.html?id='
							+ expertId
				});
	}
</script>
</head>
<body>

<div class="container">
  <!-- 表格开始-->
  <div class="container">
    <c:if test="${packExpertExtList.size()>0 }">
    <!-- 循环包 -->
	<form id="formTable">
	<c:forEach items="${packageList }" var="pack" varStatus="vs">
	  <c:if test="${pack.id eq packageId}">
		<h3>包名称：${pack.name }</h3>
	    <div align="left">
		  <button class="btn btn-windows git" onclick="scoreTotal(this,'${packageId}','${project.id}');" type="button">评分汇总</button>
		  <button class="btn btn-windows input" onclick="window.print();" type="button">打印信息</button>
		  </div>
		  <!--循环供应商  -->
		  <table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
			  <tr>
			    <th class="info">供应商/专家</th>
				<c:forEach items="${packExpertExtList }" var="ext">
				  <c:forEach items="${typeNames }" var="type">
				    <c:if test="${type.TYPENAME eq '0' && packageId eq type.PACKAGEID}">
					  <c:set var="type0" value="1" />
					</c:if>
					<c:if test="${type.TYPENAME eq '1' && packageId eq type.PACKAGEID}">
					  <c:set var="type1" value="1" />
					</c:if>
				  </c:forEach>
					<c:if test="${ext.packageId eq packageId && ext.expert.expertsTypeId eq '1' && type1 eq '1'}">
					  <th class="info">
					    ${ext.expert.relName }<input type="hidden" name="expertId_${vs.index }" value="${ext.expert.id }">
					  </th>
					</c:if>
					<c:if test="${ext.packageId eq packageId && ext.expert.expertsTypeId eq '3' && type0 eq '1'}">
					  <th class="info">
					    ${ext.expert.relName }<input type="hidden" name="expertId_${vs.index }" value="${ext.expert.id }">
					  </th>
					</c:if>
				  </c:forEach>
				  <th class="info">
				    <input type="button" class="btn" onclick="showViewBySupplierId('${packageId}',this,'${vs.index }')" value="查看明细">
				  </th>
			    </tr>
			  </thead>
			  <c:set var="TOTAL" value="0"></c:set>
			  <c:forEach items="${supplierList }" var="supplier">
				<c:if test="${fn:contains(supplier.packages,packageId)}">
				<tr class="tc">
				  <td class="tc">${supplier.suppliers.supplierName }</td>
				  <c:forEach items="${packExpertExtList }" var="ext">
					<c:forEach items="${typeNames }" var="type">
					  <c:if test="${type.TYPENAME eq '0' && packageId eq type.PACKAGEID}">
						<c:set var="type2" value="1" />
					  </c:if>
					  <c:if test="${type.TYPENAME eq '1' && packageId eq type.PACKAGEID}">
						<c:set var="type3" value="1" />
					  </c:if>
					</c:forEach>
					<c:if test="${ext.packageId eq packageId && ext.expert.expertsTypeId eq '1' && type3 eq '1'}">
				      <c:set var="flag" value="0" />
				      <c:forEach items="${expertScoreList }" var="sco">
						<c:if test="${sco.expertId eq ext.expert.id && sco.supplierId eq supplier.suppliers.id && sco.packageId eq packageId}">
						  <c:set var="flag" value="1" />
						  <td class="tc">${sco.score }</td>
						</c:if>
					  </c:forEach>
					  <c:if test="${flag eq '0'}">
					    <td class="tc">暂未评分</td>
					  </c:if>
					</c:if>
					<c:if test="${ext.packageId eq packageId && ext.expert.expertsTypeId eq '3' && type2 eq '1'}">
					  <c:set var="flag" value="0" />
					  <c:forEach items="${expertScoreList }" var="sco">
					    <c:if test="${sco.expertId eq ext.expert.id && sco.supplierId eq supplier.suppliers.id && sco.packageId eq packageId}">
						  <c:set var="flag" value="1" />
						  <td class="tc">${sco.score }</td>
						</c:if>
					  </c:forEach>
					  <c:if test="${flag eq '0'}">
						<td class="tc">暂未评分</td>
					  </c:if>
					</c:if>
				  </c:forEach>
				  <td width="150px">
				    <input type="radio" value="${supplier.suppliers.id}" name="supplierView_${vs.index }">
				  </td>
				</tr>
			  </c:if>
			</c:forEach>
			    <tr>
				  <td class="tc">
				    <input type="button" class="btn" onclick="showViewByExpertId('${packageId}',this,'${vs.index }')" value="查看明细">
				  </td>
				  <c:forEach items="${packExpertExtList }" var="ext">
					<c:forEach items="${typeNames }" var="type">
					  <c:if test="${type.TYPENAME eq '0' && packageId eq type.PACKAGEID}">
						<c:set var="type0" value="1" />
					  </c:if>
					  <c:if test="${type.TYPENAME eq '1' && packageId eq type.PACKAGEID}">
						<c:set var="type1" value="1" />
					  </c:if>
					</c:forEach>
					<c:if test="${ext.packageId eq packageId && ext.expert.expertsTypeId eq '1' && type1 eq '1'}">
					  <td class="tc">
					    <input type="radio" value="${ext.expert.id}" name="expertView_${vs.index }">
					  </td>
					</c:if>
					<c:if test="${ext.packageId eq packageId && ext.expert.expertsTypeId eq '3' && type0 eq '1'}">
					  <td class="tc">
					    <input type="radio" value="${ext.expert.id}" name="expertView_${vs.index }">
					  </td>
					</c:if>
				  </c:forEach>
				  <td class="tc"></td>
				</tr>
			  </table>
			</c:if>
		  </c:forEach>
		</form>
	  </c:if>
	</div>
    <div align="center">
	  <input type="button" class="btn btn-windows back" value="返回" onclick="javascript:window.location.href='${pageContext.request.contextPath}/packageExpert/toScoreAudit.html?projectId=${projectId}&flowDefineId=${flowDefineId}'">
	</div>
  </body>
</html>
