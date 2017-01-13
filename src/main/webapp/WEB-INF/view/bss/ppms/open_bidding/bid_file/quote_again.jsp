<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">



<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">

function back() {
	$("#tab-3").load("${pageContext.request.contextPath}/packageExpert/toSupplierQuote.html?projectId=${projectId}&flowDefineId=${flowDefineId}");
}
	var jsonStr = [];
	function update(obj, supplierId, packageId, projectId, quoteId){
		var reg = /^\d+\.?\d*$/;
		var flag = false;
		var x,y;  
	    oRect = obj.getBoundingClientRect();  
	    x=oRect.left;  
	    y=oRect.top;
	    var total = $(obj).parent().parent().find("td").eq("2").find("input").val();
		if(!reg.exec(total)) {
			$(obj).parent().parent().find("td").eq("2").find("input").val('');
			layer.msg("金额必填且为数字,请正确填写",{offset: [y, x]});
			return;
		}
		var deliveryTime = $(obj).parent().parent().find("td").eq("3").find("input").val();
		deliveryTime = encodeURI(deliveryTime);
		deliveryTime = encodeURI(deliveryTime);
		if (!deliveryTime) {
			layer.msg("交货时间为必填",{offset: [y, x]});
			return;
		}
		var date = '${date}';
		var json = {"total":total, "supplierId":supplierId, "deliveryTime":deliveryTime, "packageId":packageId, "projectId":projectId, "quoteId":quoteId, "date":date};
		jsonStr.push(json);
		console.log(jsonStr); 
	}
	
	var error = 0;
	function eachTable(obj) {
	    //根据保存按钮显示提示信息
	 	var x,y;  
	    oRect = obj.getBoundingClientRect();  
	    x=oRect.left - 150;  
	    y=oRect.top - 150;  
		var allTable = document.getElementsByTagName("table");
		var priceStr = "";
		var count = 0;
		for(var i = 0; i < allTable.length; i++) {
			for(var j = 1; j < allTable[i].rows.length; j++) { //遍历Table的所有Row
				var total = $(allTable[i].rows).eq(j).find("td").eq("2").find("input").val();
				var time = $(allTable[i].rows).eq(j).find("td").eq("3").find("input").val();
				var reg = /^\d+\.?\d*$/;
				if (total == "" || time == "") {
					count ++;
						layer.msg("表单未填写完整,单价和交货时间必须正确填写,请检查表单");
						return ;
				}
				if(!reg.exec(total)) {
						count ++;
						layer.msg("表单未填写完整,单价和交货时间必须正确填写,请检查表单");
						return ;
			   }
			};
		}
		if (count == 0) {
			for(var i = 0; i < allTable.length; i++) {
				for(var j = 1; j < allTable[i].rows.length; j++) { //遍历Table的所有Row
					var inputObj = $(allTable[i].rows).eq(j).find("td").eq("0").find("input");
					$(inputObj).click();	
				};
			}
		}
		$.ajax({
		        type: "POST",
		        url: "${pageContext.request.contextPath}/open_bidding/save.html",
		        data: {quoteList:JSON.stringify(jsonStr)},
		        dataType: "json",
		        success: function (message) {
		        	window.location.href="${pageContext.request.contextPath}/packageExpert/auditManage.html?projectId=${projectId}&flowDefineId=${flowDefineId}";
		        },
		        error: function (message) {
		        }
		    });
	}

	function ycDiv(obj, index){
		if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
			$(obj).removeClass("spread");
			$(obj).addClass("shrink");
		} else {
			if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
				$(obj).removeClass("shrink");
				$(obj).addClass("spread");
			}
		}
		
		var divObj = new Array();
		divObj = $(".p0" + index);
		for (var i =0; i < divObj.length; i++) {
	    	if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
	    		$(divObj[i]).removeClass("hide");
	    	} else {
	    		if ($(divObj[i]).hasClass("p0"+index)) {
	    			$(divObj[i]).addClass("hide");
	    		}
	    	}
		};
	}

	function download(id, key) {
		var form = $("<form>");
		form.attr('style', 'display:none');
		form.attr('method', 'post');
		form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
		$('body').append(form);
		form.submit();
	}
</script>
</head>
<body>
<div id="showDiv" class="clear">
<c:set value="1" var ="count"></c:set>
<c:forEach items="${treeMap }" var="treemap" varStatus="vsKey">
	<c:forEach items="${treemap.key }" var="treemapKey" varStatus="vs">
		<div>
			 <h2 onclick="ycDiv(this,'${index}')" class="count_flow spread hand">包名:<span class="f14 blue">${fn:substringBefore(treemapKey, "|")}</span>
			 <span>项目预算报价(万元)：${fn:substringAfter(treemapKey, "|")}</span>
			 </h2>
        </div>
        <div class="p0${index}">
		<table class="table table-bordered table-condensed mt5">
			<thead>
				<tr>
					<th class="info w50">序号</th>
					<th class="info">供应商名称</th>
					<th class="info">总价(万元)</th>
					<th class="info">交货期限</th>
			    </tr>
			</thead>
		<c:forEach items="${treemap.value}" var="treemapValue" varStatus="vs">
				<c:set value="${count+1 }" var="index"></c:set>
				<tr>
				    <td class="tc w50">${vs.index+1}
			    			<input type="hidden" onclick="update(this,'${treemapValue.suppliers.id}','${treemapValue.packages}','${treemapValue.project.id}','${treemapValue.quoteId}')" />
				    </td>
				    <td class="tl">${treemapValue.suppliers.supplierName}</td>
					<td class="tc"><input class="w60"  maxlength="16" /></td>
					<td class="tc"><input class="w90" value=""/></td>
			    </tr>
		</c:forEach>
		</table>
		</div>
	</c:forEach>
</c:forEach>
		<div class="col-md-12 tc">
			<input class="btn btn-windows save" value="结束唱标" type="button" onclick="eachTable(this)">
			<input class="btn btn-windows reset" value="返回" type="button" onclick="history.go(-1)">
		</div>
</div>
</body>
</html>
