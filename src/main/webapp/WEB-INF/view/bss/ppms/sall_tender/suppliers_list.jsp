<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<base href="${pageContext.request.contextPath}/" target="_self">

		<title>模版管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
  <link rel="stylesheet" type="text/css" href="styles.css">
  -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">

	</head>
	<script type="text/javascript">
		$(function() {
			laypage({
				cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
				pages: "${list.pages}", //总页数
				skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
				skip: true, //是否开启跳页
				total: "${list.total}",
				startRow: "${list.startRow}",
				endRow: "${list.endRow}",
				groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
				curr: function() { //通过url获取当前页，也可以同上（pages）方式获取

					return "${list.pageNum}";
				}(),
				jump: function(e, first) { //触发分页后的回调
					if(!first) { //一定要加此判断，否则初始时会无限刷新
						$.ajaxSetup({
							cache: false
						});
						var packages = "${packId }";
						var projectId = "${projectId }";
						var path = "${pageContext.request.contextPath}/saleTender/showAllSuppliers.html?packId=" + packages + "&projectId=" + projectId + "&supplierName=" + $("#supplierName").val() + "&page=" + e.curr + "&ix=${ix}" + "&selectValue=" + $("#hiddenValue").val() + "&isProvisional=" + $("#isProvisional").val();
						$("#tab-1").load(path);

					}
				}
			});
			var selectValue = "${selectValue}";
			if(selectValue != null && selectValue != '') {
				Selected(selectValue);
			}
		});

		/**查詢*/
		function query() {
			$.ajaxSetup({
				cache: false
			});
			var packages = "${packId }";
			var projectId = "${projectId }";
			var path = "${pageContext.request.contextPath}/saleTender/showAllSuppliers.html?packId=" + packages + "&projectId=" + projectId + "&supplierName=" + $("#supplierName").val() + "&ix=${ix}&isProvisional=" + $("#isProvisional").val();
			$("#tab-1").load(path);
		}

		function selectAll() {
			var value = "";
			var checklist = document.getElementsByName("chkItem");
			var checkAll = document.getElementById("checkAll");
			if(checkAll.checked) {
				for(var i = 0; i < checklist.length; i++) {
					checklist[i].checked = true;
					var hiddenValue = $("#hiddenValue").val();
					if(hiddenValue != null && hiddenValue != '') {
						var newStr = hiddenValue.replace(checklist[i].value + ",", "");
						$("#hiddenValue").val(newStr);
					}
					value = value + checklist[i].value + ",";
				}
			} else {
				for(var j = 0; j < checklist.length; j++) {
					checklist[j].checked = false;
					var hiddenValue = $("#hiddenValue").val();
					var newStr = hiddenValue.replace(checklist[j].value + ",", "");
					$("#hiddenValue").val(newStr);
				}
			}
			$("#hiddenValue").val($("#hiddenValue").val() + value);
		}

		function showSupplier() {
			var id = [];
			$('input[name="chkItem"]:checked').each(function() {
				id.push($(this).val());
			});
			var kind = $("#kind").val();
			var packages = "${packId }";
			var projectId = "${projectId }";
			if(kind == 'DYLY') {
				if(id.length == 1) {
					var path = "${pageContext.request.contextPath}/saleTender/saveSupplier.html?ids=" + id.toString() + "&packages=" + packages + "&projectId=" + projectId + "&ix=${ix}";
					$.ajaxSetup({
						cache: false
					});
					$("#tab-1").load(path);
				} else {
					layer.alert("只能选择一个供应商", {
						offset: ['30%', '40%'],
						shade: 0.01,
					});
				}
			} else {
				var idstr = $("#hiddenValue").val();
				var path = "${pageContext.request.contextPath}/saleTender/saveSupplier.html?ids=" + idstr + "&packages=" + packages + "&projectId=" + projectId + "&ix=${ix}";
				$.ajaxSetup({
					cache: false
				});
				$("#tab-1").load(path);
			}

		}

		function Selected(value) {
			var count = 0;
			$("#hiddenValue").val(value);
			var strs = new Array(); //定义一数组
			strs = value.split(","); //字符分割 
			var checkAll = document.getElementById("checkAll");
			var checklist = document.getElementsByName("chkItem");
			for(var j = 0; j < checklist.length; j++) {
				for(var k = 0; k < strs.length; k++) {
					if(checklist[j].value == strs[k]) {
						checklist[j].checked = true;
						count++;
					}
				}
			}
			if(count != 0 && checklist.length == count) {
				checkAll.checked = true;
			}
		}

		function check() {
			var checklist = document.getElementsByName("chkItem");
			var checkAll = document.getElementById("checkAll");
			for(var i = 0; i < checklist.length; i++) {

				if(checklist[i].checked == false) {

					var hiddenValue = $("#hiddenValue").val();

					// != null 去除重复
					if(hiddenValue != null && hiddenValue != '') {
						var newStr = hiddenValue.replace(checklist[i].value + ",", "");
						$("#hiddenValue").val(newStr);
					}

				}
				var value = "";
				for(var j = 0; j < checklist.length; j++) {
					if(checklist[j].checked == true) {
						//         alert();
						var hiddenValue = $("#hiddenValue").val();
						// != null 去除重复
						if(hiddenValue != null && hiddenValue != '') {
							var newStr = hiddenValue.replace(checklist[j].value + ",", "");
							$("#hiddenValue").val(newStr);
						}
						value = value + checklist[j].value + ",";
					}
				}
				$("#hiddenValue").val($("#hiddenValue").val() + value);
			}
			var len = $("input[name='chkItem']:checked").length;
			if(len != 0 && (len == 0 && checklist.length == 0)) {
				checkAll.checked = true;
			}
		}

		function resetQuery() {
			$("#supplierName").val("");
		}

		function goBack() {
			$.ajaxSetup({
				cache: false
			});
			var path = '${pageContext.request.contextPath}/saleTender/view.html?projectId=${projectId }&ix=${ix}';
			$("#tab-1").load(path);
		}
	</script>

	<body>
		<!--面包屑导航开始-->
		<div class="search_detail ml0">
			<ul class="demand_list">
				<li class="fl"><label class="fl">供应商名称：</label><span><input
                type="text" id="supplierName" class="" value="${supplierName}"  name="supplierName"/></span></li>
				<li class="fl">
					<label class="fl">状态：</label><span>
                   <select name="isProvisional" id="isProvisional">
                     <option value="">--请选择--</option>
                     <option value="1" <c:if test="${isProvisional=='1'}">selected="selected"</c:if> >临时</option> 
                     <option value="2" <c:if test="${isProvisional=='2'}">selected="selected"</c:if>>入库(待复核)</option>
                     <option value="7" <c:if test="${isProvisional=='7'}">selected="selected"</c:if>>考察合格</option>
                     <option value="5" <c:if test="${isProvisional=='5'}">selected="selected"</c:if>>复核合格(待考察)</option>
                   </select>
                </li>
          </ul>
            <input type="submit" onclick="query();" class="btn fl" value="查询"/>
            <input type="reset" class="btn fl" onclick="resetQuery();" value="重置">
          <div class="clear"></div>
      </div>
    <input type="hidden" id="hiddenValue"  value="" />
    <input type="hidden" name="packages" value="${packId }" />
    <input type="hidden" name="projectId" value="${projectId }" />
    <input type="hidden" id="kind" value="${kind }" />

    <table class="table table-bordered table-condensed mt5">
      <thead>
        <tr>
          <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
          <th class="info">供应商名称</th>
          <th class="info">军队业务联系人姓名</th>
          <th class="info">军队业务联系人电话</th>
          <!-- <th class="info">注册地址</th> -->
          <th class="info">状态</th>
        </tr>
      </thead>
      <c:forEach items="${list.list}" var="ext" varStatus="vs">
        <tr>
          <td class="tc opinter"><input onclick="check()" type="checkbox" name="chkItem" value="${ext.id}" /></td>

          <td class="tc opinter">${ext.supplierName }</td>

          <td class="tc opinter">${ext.armyBusinessName}</td>

          <td class="tc opinter">${ext.armyBuinessTelephone}</td>

          <%-- <td class="tc opinter">${ext.addressName}</td> --%>
          <td class="tc opinter">
          <c:if test="${ext.isProvisional=='1'}">临时</c:if>
          <c:if test="${ext.isProvisional=='0'}">
              <c:if test="${ext.status==1 }">入库(待复核)</c:if>
              <c:if test="${ext.status==7 }">考察合格</c:if>
              <c:if test="${ext.status==5 }">复核合格(待考察)</c:if>
           </c:if>  </td>
        </tr>
      </c:forEach>
    </table>
    <div id="pagediv" align="right"></div>
  
  
    <div class="col-md-12 tc mt5">
      <button class="btn btn-windows save" onclick="showSupplier()">保存</button>
      <button class="btn btn-windows back" onclick="goBack()" type="button">返回</button>
    </div>
    
</body>
</html>