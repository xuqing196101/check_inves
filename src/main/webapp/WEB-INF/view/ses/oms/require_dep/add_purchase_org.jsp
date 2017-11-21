<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
	var treeObj = null;
	$(function() {
		var datas;
		var type = $("#typeName").val();
		var setting = {
			async : {
				autoParam : [ "id" ],
				enable : true,
				url : globalPath + "/purchaseManage/getTree.do?typeName="
						+ type,
				dataFilter : ajaxDataFilter,
				dataType : "json",
				type : "post",
	
			},
			data : {
				key : {
					title : "title",
					name : "name",
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId",
					rootPId : "0",
				}
			},
			check : {
				enable : true,
				chkStyle : "checkbox",
				chkboxType : {
					"Y" : "",
					"N" : ""
				}
			},
			view : {
				selectedMulti : false,
				showTitle : false,
			},
		};
		treeObj = $.fn.zTree.init($("#departTree"), setting, datas);
		treeObj.expandAll(false);
	});
	
	function ajaxDataFilter(treeId, parentNode, responseData) {
		var notIds = $("#notIds").val();
		var arr = notIds.split(",");// 在每个逗号(,)处进行分解。
		for (var i = 0; i < responseData.length; i++) {
			for (var j = 0; j < arr.length; j++) {
				if (responseData[i].id == arr[j]) {
					responseData[i].chkDisabled = true;
				}
			}
		}
		return responseData;
	}
	
	function fanhui() {
		window.location.href = "${pageContext.request.contextPath}/purchaseManage/add.html";
	}
	
	function chongzhi() {
		$("input[name='name']").val('');
	}

	/** 添加 */
	function add() {

		var index = parent.layer.getFrameIndex(window.name);

		var parentTabArray = [];
		var parentIndexArray = [];
		parent.$("input[name='selectedItem']").each(
				function() {
					parentTabArray.push($(this).val());
					parentIndexArray.push($(this).parents('tr').find('td')
							.eq(1).text());
				});
		var parentIndex = 0;
		if (parentIndexArray.length > 0) {
			parentIndex = Math.max.apply(null, parentIndexArray);
		}

		var count = 1;
		if (parentIndex != 0) {
			count = count + parentIndex;
		}
		var treeObj = $.fn.zTree.getZTreeObj("departTree");
        nodes = treeObj.getCheckedNodes(true);
        for(var i=0;i<nodes.length;i++){
        	var id = nodes[i].id;
        	var name = nodes[i].name;
        	if ($.inArray(id, parentTabArray) < 0) {
				addTables(count, id, name);
				count++;
			}
        }  
		parent.layer.close(index); //执行关闭
	}

	/** 添加table */
	function addTables(index, id, name) {
		parent.$("#tab").append(
			"<tr id="+id+" align='center'>"
					+ "<td><input type='checkbox' name='selectedItem' onclick='checkAdd()' value="
					+ id + " /></td>" + "<td>" + index + "</td>"
					+ "<td class='tl pl20'>" + name + "</td>"
					+ "</tr>");
	}

	//关闭弹出层
	function closess() {
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index);
	}
</script>
</head>
<body>
  <!-- <h2 class="search_detail">
    <ul class="demand_list">
      <li>
        <label class="fl">采购管理部门名称：</label>
        <span><inputtype="text" name="name" id="name"></span>
      </li>
    </ul>
    <button class="btn fl" onclick="submit()" type="button">查询</button>
    <button class="btn fl" onclick="chongzhi()" type="button">重置</button>
    <div class="clear"></div>
  </h2> -->
  <input type="hidden" name="notIds" id="notIds" value="${notIds }">
  <input type="hidden" name="typeName" id="typeName" value="${typeName }">
  <input type="hidden" name="checkedIds" id="checkedIds">
  <!-- 采购管理部门树 -->
  <div class="tag-box mb0">
    <ul id="departTree" class="ztree m0 w100p"></ul>
  </div>
  <div class="tc">
    <button class="btn btn-windows add mb0" type="button" onclick="add()">添加</button>
    <button class="btn btn-windows cancel mb0 mr0" type="button" onclick="closess();">取消</button>
  </div>
</body>
</html>
